package com.yzp.config;

import com.yzp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2配置类
 *
 * @author YangZhiPeng
 * @date 2022/9/21 17:06
 */
@Configuration
@EnableAuthorizationServer  //认证服务器
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
//    @Autowired
//    private TokenStore tokenStore;
//    @Autowired
//    private AuthorizationCodeServices jdbcAuthorizationCodeServices;
//    @Autowired
//    private ClientDetailsService jdbcClientDetailsService;

    /**
     * 配置被允许访问此认证服务器的客户端详细信息
     * 1.内存管理
     * 2.数据库管理方式
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //clients.withClientDetails(jdbcClientDetailsService);

        clients.inMemory()
                //客户端名称
                .withClient("test-pc")
                //客户端密码
                .secret(passwordEncoder.encode("123456"))
                //资源id,商品资源
                .resourceIds("yzp-auth")
                //授权类型, 可同时支持多种授权类型
                .authorizedGrantTypes("authorization_code", //授权码
                        "password", //密码
                        "implicit", //隐式简化模式
                        "client_credentials", //客户端模式
                        "refresh_token") //令牌刷新策略，密码模式返回的refresh_token
                //授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码
                .autoApprove(false)
                .redirectUris("http://www.baidu.com/");//客户端回调地址
    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        //密码模式需要配置认证管理器
//        endpoints.authenticationManager(authenticationManager);
//        //刷新令牌获取新令牌时需要
//        endpoints.userDetailsService(myUserDetailsService);
//        //令牌管理策略
//        endpoints.tokenStore(tokenStore);
//        //授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
//        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices);
//
//    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        //所有人可访问 /oauth/token_key 后面要获取公钥, 默认拒绝访问
//        security.tokenKeyAccess("permitAll()");
//        // 认证后可访问 /oauth/check_token , 默认拒绝访问
//        security.checkTokenAccess("isAuthenticated()");
//    }

}
