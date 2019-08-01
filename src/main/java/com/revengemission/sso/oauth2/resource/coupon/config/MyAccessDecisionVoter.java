package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

/**
 * 实现自定义投票，处理 MyFilterInvocationSecurityMetadataSource 中的 permitAll，authenticated 等
 */
public class MyAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    public static final String PERMITALL = "permitAll";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecurityConfig && attribute.getAttribute() != null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                if (PERMITALL.equals(attribute.getAttribute())) {
                    return ACCESS_GRANTED;
                }
            }
        }
        return ACCESS_ABSTAIN;
    }
}
