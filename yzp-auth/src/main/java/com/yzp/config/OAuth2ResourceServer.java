package com.yzp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * desc
 *
 * @author YangZhiPeng
 * @date 2022/9/22 9:43
 */
//@Configuration
//@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .requestMatchers()
//                .antMatchers("/api/**");
//    }

}
