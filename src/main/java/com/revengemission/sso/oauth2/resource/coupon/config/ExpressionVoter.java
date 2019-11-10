package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.Collection;

/**
 * 因 WebExpressionVoter 中的 WebExpressionConfigAttribute 只能在同package中使用，此voter 仿照WebExpressionVoter
 */
public class ExpressionVoter implements AccessDecisionVoter<FilterInvocation> {
    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

    @Override
    public int vote(Authentication authentication, FilterInvocation fi,
                    Collection<ConfigAttribute> attributes) {
        assert authentication != null;
        assert fi != null;
        assert attributes != null;

        ExpressionConfigAttribute eca = findConfigAttribute(attributes);

        if (eca == null) {
            return ACCESS_ABSTAIN;
        }

        EvaluationContext ctx = expressionHandler.createEvaluationContext(authentication, fi);

        int result = ExpressionUtils.evaluateAsBoolean(eca.getAuthorizeExpression(), ctx) ? ACCESS_GRANTED : ACCESS_DENIED;
        return result;
    }

    private ExpressionConfigAttribute findConfigAttribute(
        Collection<ConfigAttribute> attributes) {
        for (ConfigAttribute attribute : attributes) {
            if (attribute instanceof ExpressionConfigAttribute) {
                return (ExpressionConfigAttribute) attribute;
            }
        }
        return null;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof ExpressionConfigAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
