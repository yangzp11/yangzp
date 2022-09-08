package com.yzp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.nacos.api.annotation.NacosProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableDiscoveryClient
public class RunApp {

    public static void main(String[] args) {
        TimeInterval timeInterval = DateUtil.timer();
        SpringApplication.run(RunApp.class, args);
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
        }
    }
}
