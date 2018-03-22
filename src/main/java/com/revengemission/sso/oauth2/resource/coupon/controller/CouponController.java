package com.revengemission.sso.oauth2.resource.coupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class CouponController {

    @ResponseBody
    @RequestMapping("/coupon/list")
    public String couponList(Principal principal) {
        System.out.println(principal);
        return "many to many";
    }
}
