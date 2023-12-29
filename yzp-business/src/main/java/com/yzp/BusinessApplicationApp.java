package com.yzp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author YangZhiPeng
 * @date 2022/8/23 13:11
 */
@SpringBootApplication
@EnableAsync
@Slf4j
@EnableDiscoveryClient
public class BusinessApplicationApp {

    public static void main(String[] args) {
        TimeInterval timeInterval = DateUtil.timer();
        SpringApplication.run(BusinessApplicationApp.class, args);
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>BusinessApplicationApp本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
        }
    }
}
