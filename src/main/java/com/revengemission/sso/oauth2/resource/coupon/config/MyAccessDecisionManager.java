package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;

/**
 * 自定义 AccessDecisionManager，decide 中应该使用 AccessDecisionVoter，可参照AffirmativeBased的实现方式
 * 默认 AccessDecisionManager 为 AffirmativeBased （一票通过）
 * web 类型项目中默认 AccessDecisionVoter 为 WebExpressionVoter
 * 其他类型项目中默认 AccessDecisionVoter 为 RoleVoter 和 AuthenticatedVoter
 */
@Deprecated
public class MyAccessDecisionManager extends AbstractAccessDecisionManager {

    protected MyAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
        super(decisionVoters);
    }

    /**
     * 方法是判定是否拥有权限的决策方法，
     * (1)authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * (2)object 包含客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * (3)configAttributes 为FilterInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {
        int deny = 0;
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);

            if (logger.isDebugEnabled()) {
                logger.debug("Voter: " + voter + ", returned: " + result);
            }
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    return;
                case AccessDecisionVoter.ACCESS_DENIED:
                    deny++;
                    break;
                default:
                    break;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException(messages.getMessage(
                "AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        }

        // To get this far, every AccessDecisionVoter abstained
        checkAllowIfAllAbstainDecisions();
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
