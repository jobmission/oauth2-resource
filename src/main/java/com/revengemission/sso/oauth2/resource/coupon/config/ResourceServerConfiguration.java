package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;


@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

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
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .mvcMatchers("/coupon/**").hasAnyAuthority("SCOPE_user_info")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .bearerTokenResolver(new CustomTokenResolver())
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    //验证JWT中的其他claim
    /*static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
        protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
            Collection<String> authorities = (Collection<String>) jwt.getClaims().get("mycustomclaim");

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }*/

}
