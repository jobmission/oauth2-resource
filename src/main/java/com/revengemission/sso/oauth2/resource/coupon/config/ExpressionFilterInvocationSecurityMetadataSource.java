package com.revengemission.sso.oauth2.resource.coupon.config;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.ResourceEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.ResourceEntityExample;
import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.ResourceEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 因无法使用ConfigAttribute的WebExpressionConfigAttribute实现，因此无法使用WebExpressionVoter投票
 * 此处简单实现，资源加载是可增加缓存，定期重新加载
 */
public class ExpressionFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

    private FilterInvocationSecurityMetadataSource hardCodedSecurityMetadataSource;
    private ResourceEntityMapper resourceEntityMapper;
    private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME = "mvcHandlerMappingIntrospector";

    public ExpressionFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource, ResourceEntityMapper resourceEntityMapper) {
        this.hardCodedSecurityMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
        this.resourceEntityMapper = resourceEntityMapper;
    }

    private Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = new HashMap<>(32);

    /**
     * 加载资源-权限关系
     */
    private void loadResource(HttpServletRequest request) {
        try {
            List<ResourceEntity> resourceEntityList = resourceEntityMapper.selectByExample(new ResourceEntityExample());
            if (resourceEntityList == null || resourceEntityList.size() == 0) {
                log.warn("DB中没有查到资源权限列表，请先配置resource_entity！");
            } else {
                resourceMap.clear();
                Collection<ConfigAttribute> array;
                ConfigAttribute cfg;
                ServletContext sc = request.getServletContext();
                ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);

                HandlerMappingIntrospector introspector = ac.getBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME, HandlerMappingIntrospector.class);

                for (ResourceEntity resourceEntity : resourceEntityList) {
                    array = new ArrayList<>();
                    cfg = new ExpressionConfigAttribute(expressionHandler.getExpressionParser().parseExpression(resourceEntity.getPermission()));
                    array.add(cfg);
                    resourceMap.put(new MvcRequestMatcher(introspector, resourceEntity.getUrl()), array);
                }
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("加载权限列表异常", e);
            }
        }

    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法。
     * object-->FilterInvocation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        FilterInvocation filterInvocation = (FilterInvocation) object;

        HttpServletRequest request = filterInvocation.getHttpRequest();

        if (resourceMap == null || resourceMap.size() == 0) {
            loadResource(request);
        }

        String requestUrl = filterInvocation.getRequestUrl();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap
            .entrySet()) {
            if (entry.getKey().matches(request)) {
                log.info("【" + requestUrl + "】匹配到DB权限列表");
                return entry.getValue();
            }
        }

        log.info("【" + requestUrl + "】不在DB权限列表当中,尝试匹配代码中的权限配置...");

///        return null; //默认白名单通过

        //  返回代码定义的默认配置(authenticated、permitAll等)
        Collection<ConfigAttribute> configAttributes = hardCodedSecurityMetadataSource.getAttributes(object);
        if (configAttributes == null || configAttributes.size() == 0) {
            log.info("【" + requestUrl + "】不在代码中的权限配置");
        } else {
            log.info("【" + requestUrl + "】匹配到代码中硬编码的配置或默认配置");
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap
            .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
