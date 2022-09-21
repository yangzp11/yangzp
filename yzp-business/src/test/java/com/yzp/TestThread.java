package com.yzp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.yzp.thread.ClothesNumGenerate;
import com.yzp.thread.ThreadTest;
//import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/25 14:13
 */
@SpringBootTest
//@RequiredArgsConstructor
public class TestThread {

//    private final ThreadTest threadTest;
//    private final ClothesNumGenerate clothesNumGenerate;

    @Autowired
    private ThreadTest threadTest;
    @Autowired
    private ClothesNumGenerate clothesNumGenerate;

    @Test
    public void setThreadTest() throws ExecutionException, InterruptedException {
        threadTest.ThreadPoolTaskExecutorTest();
    }

    @Test
    public void ThreadPoolTaskExecutorTest2() throws ExecutionException, InterruptedException {
        threadTest.ThreadPoolTaskExecutorTest2();
    }

    @Test
    public void generateNum() {
        clothesNumGenerate.getOutClothesNumberListNew(20, 1);
    }
}
