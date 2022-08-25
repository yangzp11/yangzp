package com.yzp.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.yzp.utils.lambda.CollectorsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
}
