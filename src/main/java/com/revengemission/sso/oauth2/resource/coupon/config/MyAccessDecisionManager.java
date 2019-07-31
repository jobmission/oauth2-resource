package com.revengemission.sso.oauth2.resource.coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义 AccessDecisionManager，decide 中应该使用 AccessDecisionVoter
 * 默认 AccessDecisionManager 为 AffirmativeBased （一票通过）
 * web 类型项目中默认 AccessDecisionVoter 为 WebExpressionVoter
 * 其他类型项目中默认 AccessDecisionVoter 为 RoleVoter 和 AuthenticatedVoter
 */
@Deprecated
public class MyAccessDecisionManager implements AccessDecisionManager {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 方法是判定是否拥有权限的决策方法，
     * (1)authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * (2)object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * (3)configAttributes 为MyFilterInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
        throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null || configAttributes.size() == 0) {
            throw new AccessDeniedException("permission denied");
        }

        ConfigAttribute cfa;
        String needRole;
        //遍历基于URL获取的权限信息和用户自身的角色信息进行对比.
        for (Iterator<ConfigAttribute> it = configAttributes.iterator(); it.hasNext(); ) {
            cfa = it.next();
            needRole = cfa.getAttribute();
            log.info("decide:needRole:" + needRole + ",authentication=" + authentication.getAuthorities());
            //authentication 为CustomUserDetailService中添加的权限信息.
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (grantedAuthority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        //没有处理authenticated、permitAll、denyAll、fullyAuthenticated
        throw new AccessDeniedException("permission denied");
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
