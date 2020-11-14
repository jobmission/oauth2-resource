package com.revengemission.sso.oauth2.resource.coupon.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revengemission.sso.oauth2.resource.coupon.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 从5.2.0 开始实现方式有所改变
 * 提取JWT中的claim作为权限依据，默认是scope，角色前会再次添加前缀ROLE_，详见JwtAuthenticationConverter
 */
public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    ObjectMapper objectMapper;

    public GrantedAuthoritiesConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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


    @SuppressWarnings("unchecked")
    Collection<String> getRoles(String claimJsonString) {
        if (claimJsonString == null) {
            return Collections.emptyList();
        }
        Collection<String> roles = new HashSet<>();
        try {
            JsonNode treeNode = objectMapper.readTree(claimJsonString);
            List<JsonNode> jsonNodes = treeNode.findValues("roles");
            jsonNodes.forEach(jsonNode -> {
                if (jsonNode.isArray()) {
                    jsonNode.elements().forEachRemaining(e -> {
                        roles.add(e.asText());
                    });
                } else {
                    roles.add(jsonNode.asText());
                }
            });

            jsonNodes = treeNode.findValues("authorities");
            jsonNodes.forEach(jsonNode -> {
                if (jsonNode.isArray()) {
                    jsonNode.elements().forEachRemaining(e -> {
                        roles.add(e.asText());
                    });
                } else {
                    roles.add(jsonNode.asText());
                }
            });


        } catch (JsonProcessingException e) {
            log.error("读取role exception", e);
        }

        return roles;
    }

}
