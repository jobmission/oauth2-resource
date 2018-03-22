package com.revengemission.sso.oauth2.resource.coupon.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public TokenStore tokenStore() {
        TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }

    /**
     * Token转换器必须与认证服务一致
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream(), "UTF-8");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        accessTokenConverter.setVerifierKey(publicKey);// 测试用,授权服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        return accessTokenConverter;
    }

    /**
     * 创建一个默认的资源服务token
     *
     * @return
     */
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().antMatchers("/coupon/**", "/user/me")
                .and()
                .authorizeRequests()
                .antMatchers("/coupon/**")
                .access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))");
    }

}
