package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class TokenBlackListValidator implements OAuth2TokenValidator<Jwt> {
    OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is invalid", null);

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
        System.out.println("checking token is valid...");
        return true;
    }
}
