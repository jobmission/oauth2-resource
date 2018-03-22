package com.revengemission.sso.oauth2.resource.coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${tokenKeyUri}")
    private String tokenKeyUri;

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
        String publicKey = null;
        /*try {
            Resource resource = new ClassPathResource("public.txt");
            publicKey = IOUtils.toString(resource.getInputStream(), "UTF-8");
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("get public key exception", e);
            }
        }*/

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> result = new HashMap<>();
            result = restTemplate.getForObject(tokenKeyUri, result.getClass());
            if (result != null && result.size() > 0) {
                publicKey = result.get("value");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("get public key exception", e);
            }
        }
        accessTokenConverter.setVerifierKey(publicKey);
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
