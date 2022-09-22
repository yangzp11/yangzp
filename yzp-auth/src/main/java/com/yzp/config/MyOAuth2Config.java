package com.yzp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/9/22 9:19
 */
@Configuration
public class MyOAuth2Config {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    /**
//     * password 支持多种编码
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    /**
//     * druid数据源
//     *
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//
//    /**
//     * jdbc管理令牌
//     * 步骤：
//     * 1.创建相关表
//     * 2.添加jdbc相关依赖
//     * 3.配置数据源信息
//     *
//     * @return
//     */
//    @Bean
//    public TokenStore jdbcTokenStore() {
//        return new JdbcTokenStore(druidDataSource());
//    }
//
//    /**
//     * Redis令牌管理
//     * 步骤：
//     * 1.启动redis
//     * 2.添加redis依赖
//     * 3.添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
//     *
//     * @return
//     */
////    @Bean
////    public TokenStore redisTokenStore() {
////        return new RedisTokenStore(redisConnectionFactory);
////    }
//
//    /**
//     * 授权码管理策略
//     *
//     * @return
//     */
//    @Bean
//    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
//        //使用JDBC方式保存授权码到 oauth_code中
//        return new JdbcAuthorizationCodeServices(druidDataSource());
//    }
//
//    /**
//     * 使用 JDBC 方式管理客户端信息
//     *
//     * @return
//     */
//    @Bean
//    public ClientDetailsService jdbcClientDetailsService() {
//        return new JdbcClientDetailsService(druidDataSource());
//    }


}
