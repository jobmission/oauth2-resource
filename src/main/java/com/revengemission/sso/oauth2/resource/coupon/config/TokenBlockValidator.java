package com.revengemission.sso.oauth2.resource.coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * 验证是否在阻止名单等
 */
public class TokenBlockValidator implements OAuth2TokenValidator<Jwt> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is invalid", null);

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (checkTokenValid(jwt)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }

    /**
     * 检查token是在有效，如被加入阻止名单等
     *
     * @param jwt jwt token
     * @return
     */
    boolean checkTokenValid(Jwt jwt) {
        log.debug("OAuth2TokenValidator: checking token 【" + jwt.getTokenValue() + "】 is valid...");
        return true;
    }
}
