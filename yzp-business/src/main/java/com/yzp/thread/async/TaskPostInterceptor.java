package com.yzp.thread.async;

import com.gobrs.async.callback.AsyncTaskPostInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/9 13:35
 */
@Component
@Slf4j
public class TaskPostInterceptor implements AsyncTaskPostInterceptor {
    /**
     * @param result   任务结果
     * @param taskName 任务名称
     */
    @Override
    public void postProcess(Object result, String taskName) {
        log.info("{}111111111", taskName);
    }
}
