package com.revengemission.sso.oauth2.resource.coupon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Spring boot 事件监听，可以额外增加处理逻辑
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.info("User Oauth2 login success");
    }
}
