package com.yzp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * desc
 *
 * @author:YangZhiPeng
 * @date: 2024/9/12 10:01
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableDubbo
public class BusinessTwoApplicationApp {
	public static void main(String[] args) {

		TimeInterval timeInterval = DateUtil.timer();
		SpringApplication.run(BusinessTwoApplicationApp.class, args);
		log.info(">>>>>>>>BusinessTwoApplicationApp本次启动耗时[{}]毫秒<<<<<<<<", timeInterval.interval());
	}
}