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
 * @date 2022/10/26 17:43
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class RocketMqApplication {
    public static void main(String[] args) {

        TimeInterval timeInterval = DateUtil.timer();
        SpringApplication.run(RocketMqApplication.class, args);
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>RocketMqApplication本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
        }
    }

}
