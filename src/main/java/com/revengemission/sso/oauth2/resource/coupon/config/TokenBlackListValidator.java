package com.revengemission.sso.oauth2.resource.coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * 严重是否在黑名单等
 */
public class TokenBlackListValidator implements OAuth2TokenValidator<Jwt> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is invalid", null);

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (checkTokenValid(jwt.getTokenValue())) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }

    /**
     * 检查token是在有效，如被加入黑名单等
     *
     * @param jwtToken jwt token
     * @return
     */
    boolean checkTokenValid(String jwtToken) {
        log.debug("OAuth2TokenValidator: checking token 【" + jwtToken + "】 is valid...");
        return true;
    }
}
