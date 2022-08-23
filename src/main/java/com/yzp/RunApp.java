package com.yzp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Instant;

/**
 * 启动类
 *
 * @author YangZhiPeng
 * @date 2022/8/23 13:11
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@Configuration
@Slf4j
public class RunApp {

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class, args);
//        if (log.isInfoEnabled()) {
//            log.info(">>>>>>>>本次启动时间[{}]<<<<<<<<", Instant.now());
//        }
    }
}
