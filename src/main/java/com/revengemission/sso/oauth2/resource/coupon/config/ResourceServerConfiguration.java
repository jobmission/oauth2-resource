package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

/*
 * Resource Server (default) will attempt to coerce these scopes into a list of granted authorities,
 * prefixing each scope with the string "SCOPE_".
 *
 * To use own custom attribute, may need to adapt the attribute or a composition of attributes into internalized authorities.
 * implements Converter<Jwt, AbstractAuthenticationToken>,or extends JwtAuthenticationConverter;
 *
 * 默认验证JWT中scope,如果使用JWT中其他claim字段, 需要覆盖jwtAuthenticationConverter，参照注释
 *
 * https://docs.spring.io/spring-security/site/docs/5.1.4.RELEASE/reference/htmlsingle/#oauth2resourceserver
 *
 * */
@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .mvcMatchers("/coupon/**").hasAnyAuthority("ROLE_SUPER")
                .mvcMatchers("/product/**").hasAnyAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .accessDeniedHandler(customAccessDeniedHandler)
                .bearerTokenResolver(new CustomTokenResolver())
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter()).decoder(jwtDecoder());
    }

    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new GrantedAuthoritiesExtractor();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoderJwkSupport jwtDecoder = new NimbusJwtDecoderJwkSupport(oAuth2ResourceServerProperties.getJwt().getJwkSetUri());
        OAuth2TokenValidator<Jwt> tokenBlackListValidator = new TokenBlackListValidator();
        OAuth2TokenValidator<Jwt> delegatingOAuth2TokenValidator = new DelegatingOAuth2TokenValidator<>(tokenBlackListValidator);
        jwtDecoder.setJwtValidator(delegatingOAuth2TokenValidator);
        return jwtDecoder;
    }

}
