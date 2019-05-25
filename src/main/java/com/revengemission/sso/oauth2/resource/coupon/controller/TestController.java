package com.revengemission.sso.oauth2.resource.coupon.controller;

import com.revengemission.sso.oauth2.resource.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    CouponService couponService;

    @GetMapping("/coupon/list")
    public Map<String, Object> couponList(JwtAuthenticationToken authenticationToken) {
        System.out.println(authenticationToken);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("data", couponService.list(authenticationToken.getToken().getSubject()));
        return result;
    }

    @GetMapping("/product/list")
    public Map<String, Object> productList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("data", new ArrayList<>());
        return result;
    }
}
