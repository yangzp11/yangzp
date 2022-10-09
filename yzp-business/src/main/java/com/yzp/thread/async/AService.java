package com.yzp.thread.async;

import com.gobrs.async.TaskSupport;
import com.gobrs.async.task.AsyncTask;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/8 15:27
 */
@Component
public class AService extends AsyncTask<Object, Object>{

    @Override
    public void prepare(Object o) {

    }

    @SneakyThrows
    @Override
    public Object task(Object o, TaskSupport support) {
        Thread.sleep(300);
        return "A执行成功";
    }

    @Override
    public boolean nessary(Object o, TaskSupport support) {
        return true;
    }

    @Override
    public void onSuccess(TaskSupport support) {
        System.out.println("AService执行完成");
    }

    @Override
    public void onFail(TaskSupport support) {
        System.out.println("AService执行失败");
    }
}
