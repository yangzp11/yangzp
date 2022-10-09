package com.yzp.thread.async;

import com.gobrs.async.TaskSupport;
import com.gobrs.async.anno.Task;
import com.gobrs.async.task.AsyncTask;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.apache.metamodel.DataContext;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/10/8 15:27
 */
@Component
//任务失败后，继续执行子任务  @Task(failSubExec = true)
//任务失败后，重试次数 @Task(retryCount = 10)
//@Task(desc = "itemTask")
//@Task(callback = true)
public class BService extends AsyncTask<Object, Object> {

    @Override
    public void prepare(Object o) {

    }

    @SneakyThrows
    @Override
    public Object task(Object o, TaskSupport support) {
        String result = getResult(support, AService.class, String.class);
        System.out.println("BService拿到A的结果" + result);
        Thread.sleep(500);
        stopAsync(support, 1);
        throw new Exception("B报错了");
        //return "B执行成功";
    }

    @Override
    public boolean nessary(Object o, TaskSupport support) {
        return true;
    }

    @Override
    public void onSuccess(TaskSupport support) {
        System.out.println("执行BService成功");
    }

    @Override
    public void onFail(TaskSupport support) {
        System.out.println("执行BService失败");
    }

    //  事务回滚 具体回滚业务需要自己实现 该方法是一个默认方法 需要自己手动重写
    public void rollback(DataContext dataContext) {
        super.rollback(dataContext);
        //todo rollback business
    }
}
