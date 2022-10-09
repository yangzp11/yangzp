package com.yzp.thread.async;

import com.gobrs.async.threadpool.GobrsAsyncThreadPoolFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/9 13:46
 */
@Configuration
@RequiredArgsConstructor
public class GobrsAsyncThreadPoolConfig {

    private final GobrsAsyncThreadPoolFactory asyncThreadPoolFactory;

    @PostConstruct
    public void gobrsThreadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue());
        asyncThreadPoolFactory.setThreadPoolExecutor(threadPoolExecutor);
    }

}
