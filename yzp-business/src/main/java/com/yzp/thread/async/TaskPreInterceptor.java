package com.yzp.thread.async;

import com.gobrs.async.callback.AsyncTaskPreInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.metamodel.DataContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/9 13:34
 */
@Component
@Slf4j
public class TaskPreInterceptor implements AsyncTaskPreInterceptor<DataContext> {

    /**
     *
     * @param params 参数
     * @param taskName 任务名称
     */
    @Override
    public void preProcess(DataContext params, String taskName) {
        log.info("{}任务开始执行！", taskName);
    }
}
