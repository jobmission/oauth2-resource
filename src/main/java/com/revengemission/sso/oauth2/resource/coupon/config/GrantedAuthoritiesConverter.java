package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 从5.2.0 开始实现方式有所改变
 * 提取JWT中的claim作为权限依据，默认是scope,详见JwtAuthenticationConverter, 参考JwtGrantedAuthoritiesConverter
 */
@Deprecated
public class GrantedAuthoritiesConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public final AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Collection<String> authorities = (Collection<String>) jwt.getClaims().get("authorities");

        return authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

}
