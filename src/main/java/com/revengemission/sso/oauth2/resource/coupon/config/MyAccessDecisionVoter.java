package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 仅仅只是AccessDecisionVoter的实现样例，没有实际功能
 */
@Deprecated
public class MyAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        for (ConfigAttribute attribute : attributes) {
            System.out.println("MyAccessDecisionVoter support:" + this.supports(attribute));
            if (this.supports(attribute)) {
                HttpServletRequest request = object.getRequest();
                System.out.println("MyAccessDecisionVoter getRequestUrl:" + request.getRequestURI());
                System.out.println("MyAccessDecisionVoter getMethod:" + request.getMethod());
                System.out.println("MyAccessDecisionVoter attribute:" + attribute.getAttribute());
                System.out.println("MyAccessDecisionVoter attribute:" + attribute.toString());
            }
        }
        return ACCESS_ABSTAIN;
    }
}
