package com.yzp.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/8/29 11:11
 */
@Configuration
@MapperScan("com.yzp.mybatis.**.mapper")
public class MyBatisScanConfig {
}
