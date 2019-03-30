package com.revengemission.sso.oauth2.resource.coupon.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomTokenResolver implements BearerTokenResolver {
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$");

    public String resolve(HttpServletRequest request) {
        String access_token = resolveFromAuthorizationHeader(request);
        if (StringUtils.isEmpty(access_token)) {
            access_token = resolveFromRequestParameters(request);
        }
        if (StringUtils.isEmpty(access_token)) {
            access_token = resolveFromCookie(request);
        }
        //todo check whether token in redis <tokenBlacklist>
        return access_token;
    }

    private static String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")) {
            Matcher matcher = authorizationPattern.matcher(authorization);
            if (!matcher.matches()) {
                BearerTokenError error = new BearerTokenError("invalid_token", HttpStatus.UNAUTHORIZED, "Bearer token is malformed", "https://tools.ietf.org/html/rfc6750#section-3.1");
                throw new OAuth2AuthenticationException(error);
            } else {
                return matcher.group("token");
            }
        } else {
            return null;
        }
    }

    private static String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues("access_token");
        if (values != null && values.length != 0) {
            if (values.length == 1) {
                return values[0];
            } else {
                BearerTokenError error = new BearerTokenError("invalid_request", HttpStatus.BAD_REQUEST, "Found multiple tokens in the request", "https://tools.ietf.org/html/rfc6750#section-3.1");
                throw new OAuth2AuthenticationException(error);
            }
        } else {
            return null;
        }
    }

    protected String resolveFromCookie(HttpServletRequest request) {

        String cookieToken = null;
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组

        if (null != cookies && cookies.length > 0) {
            int foundTimes = 0;
            for (Cookie cookie : cookies) {
                if (null != cookie.getName() && cookie.getName().trim().equalsIgnoreCase("access_token")) {
                    cookieToken = cookie.getValue().trim();
                    foundTimes++;
                }
            }
            if (foundTimes > 1) {
                BearerTokenError error = new BearerTokenError("invalid_request", HttpStatus.BAD_REQUEST, "Found multiple tokens in the request", "https://tools.ietf.org/html/rfc6750#section-3.1");
                throw new OAuth2AuthenticationException(error);
            }
        }
        return cookieToken;
    }
}
