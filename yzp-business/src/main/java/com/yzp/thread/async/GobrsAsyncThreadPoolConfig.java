//package com.yzp.thread.async;
//
//import com.gobrs.async.threadpool.GobrsAsyncThreadPoolFactory;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * desc
// *
// * @author YangZhiPeng
// * @date 2022/10/9 13:46
// */
//@Configuration
//@RequiredArgsConstructor
//public class GobrsAsyncThreadPoolConfig extends GobrsAsyncThreadPoolFactory {
//
//    @PostConstruct
//    public void gobrsThreadPoolExecutor(GobrsAsyncThreadPoolFactory  factory){
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10,
//                60, TimeUnit.SECONDS, new LinkedBlockingQueue());
//		factory.setThreadPoolExecutor(threadPoolExecutor);
//    }
//
//}
