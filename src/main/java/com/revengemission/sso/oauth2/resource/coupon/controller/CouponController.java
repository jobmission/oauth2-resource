package com.revengemission.sso.oauth2.resource.coupon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CouponController {

    @GetMapping("/coupon/list")
    public Map<String, Object> couponList(Principal principal) {
        System.out.println(principal);
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        result.put("data", new ArrayList<>());
        return result;
    }
}
