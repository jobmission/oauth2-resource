package com.revengemission.sso.oauth2.resource.coupon.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.revengemission.sso.oauth2.resource.coupon.utils.JsonUtil;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 从5.2.0 开始实现方式有所改变
 * 提取JWT中的claim作为权限依据，默认是scope，角色前会再次添加前缀ROLE_，详见JwtAuthenticationConverter
 */
public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    public GrantedAuthoritiesConverter() {
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        try {
            String claimJsonString = JsonUtil.objectToJsonString(jwt.getClaims());
            for (String authority : getRoles(claimJsonString)) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority));
            }
        } catch (JsonProcessingException e) {
            log.error("convert exception", e);
        }

        return grantedAuthorities;
    }

    Configuration conf = Configuration.builder().options(Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS).build();

    @SuppressWarnings("unchecked")
    Collection<String> getRoles(String claimJsonString) {
        if (claimJsonString == null) {
            return Collections.emptyList();
        }

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(claimJsonString);

        List<Object> authorities = JsonPath.using(conf).parse(document).read("$..roles");

        if (authorities == null || authorities.size() == 0) {
            authorities = JsonPath.using(conf).parse(document).read("$..authorities");
        }
        Collection<String> roles = new ArrayList<>();
        authorities.forEach(authorityItem -> {
            if (authorityItem instanceof String) {
                roles.add((String) authorityItem);
            } else if (authorityItem instanceof JSONArray) {
                roles.addAll((Collection<String>) authorityItem);
            } else if (authorityItem instanceof Collection) {
                roles.addAll((Collection<String>) authorityItem);
            }
        });

        return roles;
    }

}
