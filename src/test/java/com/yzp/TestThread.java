package com.yzp;

import com.yzp.thread.ThreadTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/25 14:13
 */
@SpringBootTest
public class TestThread {

    @Resource
    private ThreadTest threadTest;

    @Test
    public void setThreadTest() throws ExecutionException, InterruptedException {
        threadTest.ThreadPoolTaskExecutorTest();
    }
}
