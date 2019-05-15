package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 提取JWT中的claim作为权限依据，默认是scope,详见JwtAuthenticationConverter
 */
public class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> authorities = (Collection<String>) jwt.getClaims().get("authorities");

        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
