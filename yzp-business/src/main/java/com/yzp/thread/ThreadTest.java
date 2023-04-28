package com.yzp.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.yzp.utils.lambda.CollectorsUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 多线程类
 *
 * @author YangZhiPeng
 * @date 2022/8/25 13:23
 */
@Component
@Slf4j
public class ThreadTest {

    @Resource(name = "asyncTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private ClothesNumGenerate clothesNumGenerate;

    public void ThreadPoolTaskExecutorTest() throws InterruptedException, ExecutionException {
        TimeInterval timeInterval = DateUtil.timer();
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Future<List<String>> thread1 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();
                for (int i = 0; i < 500; i++) {
                    idList.addAll(clothesNumGenerate.getOutClothesNumberListNew(5, 1));
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        Future<List<String>> thread2 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();

                for (int i = 0; i < 500; i++) {
                    idList.addAll(clothesNumGenerate.getOutClothesNumberListNew(5, 1));
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        Future<List<String>> thread3 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();
                for (int i = 0; i < 500; i++) {
                    idList.addAll(clothesNumGenerate.getOutClothesNumberListNew(5, 2));
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        List<String> thread1List = thread1.get();
        List<String> thread2List = thread2.get();
        List<String> thread3List = thread3.get();
        log.info("生成自增ID耗时：{}", timeInterval.interval());
        List<String> list = Stream.of(thread1List, thread2List, thread3List).flatMap(Collection::stream).collect(Collectors.toList());
        List<String> list2 = list.stream().filter(CollectorsUtil.repeatByKey(item -> item)).collect(Collectors.toList());
        log.info("重复自增ID：{}", list2);
    }

    public void ThreadPoolTaskExecutorTest2() throws InterruptedException, ExecutionException {
        TimeInterval timeInterval = DateUtil.timer();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Snowflake snowflake = IdUtil.getSnowflake(1, 2);

        Future<List<String>> thread1 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();
                for (int i = 0; i < 500000; i++) {
                    idList.add(snowflake.nextIdStr());
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        Future<List<String>> thread2 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();

                for (int i = 0; i < 500000; i++) {
                    idList.add(snowflake.nextIdStr());
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        Future<List<String>> thread3 = threadPoolTaskExecutor.submit(() -> {
            try {
                List<String> idList = new LinkedList<>();
                for (int i = 0; i < 500000; i++) {
                    idList.add(snowflake.nextIdStr());
                }
                return idList;
            } finally {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        List<String> thread1List = thread1.get();
        List<String> thread2List = thread2.get();
        List<String> thread3List = thread3.get();
        log.info("生成ID耗时：{}", timeInterval.interval());
        List<String> list = Stream.of(thread1List, thread2List, thread3List).flatMap(Collection::stream).collect(Collectors.toList());
        List<String> list2 = list.stream().filter(CollectorsUtil.repeatByKey(item -> item)).collect(Collectors.toList());
        log.info("重复雪花ID：{}", list2);
    }

    @SneakyThrows
    public static void main(String[] args) {
 /*       // ## 1. 框架上层逻辑，后续流程框架调用业务 ##
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        context.set("111111");
        threadLocal.set("111111");
        inheritableThreadLocal.set("111111");
//        new Thread(() ->{
//            System.out.println(context.get());
//            System.out.println(threadLocal.get());  //null
//            System.out.println(inheritableThreadLocal.get());
//        }).start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        executorService.submit(() ->{
            System.out.println(context.get());
            System.out.println(threadLocal.get());
            System.out.println(inheritableThreadLocal.get());
            countDownLatch.countDown();
        }).get();
        countDownLatch.await();*/

        long[] array = new long[20000];
        long expectedNum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextLong();
            expectedNum += array[i];
        }
        System.out.println("expectedNum Sum：" + expectedNum);
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = ForkJoinPool.commonPool().invoke(task);
        long endTime = System.currentTimeMillis(); //
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");

    }

    //自定义的任务类
    static class SumTask extends RecursiveTask<Long> {
        static final int THRESHOLD = 500;
        long[] array;
        int start;
        int end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // 如果任务足够小,直接计算:
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += this.array[i];
                    // 故意放慢计算速度:
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
                return sum;
            }
            // 任务太大,一分为二:
            int middle = (end + start) / 2;
            System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
            //①  “分裂”子任务:
            SumTask subtask1 = new SumTask(this.array, start, middle);//自己调用自己  递归
            SumTask subtask2 = new SumTask(this.array, middle, end);//自己调用自己  递归
            //②   invokeAll会并行运行两个子任务:
            invokeAll(subtask1, subtask2);
            //③   获得子任务的结果:
            Long subresult1 = subtask1.join();
            Long subresult2 = subtask2.join();
            //④    汇总结果:
            Long result = subresult1 + subresult2;
            System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
            return result;
        }

    }
}
