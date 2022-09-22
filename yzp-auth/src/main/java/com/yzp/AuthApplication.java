package com.yzp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/9/22 9:45
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class AuthApplication {

    public static void main(String[] args) {
        TimeInterval timeInterval = DateUtil.timer();
        SpringApplication.run(AuthApplication.class, args);
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>AuthApplication本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
        }
    }

}
