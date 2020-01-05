package com.revengemission.sso.oauth2.resource.coupon.controller;

import com.revengemission.sso.oauth2.resource.coupon.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Api(value = "测试接口", tags = {"测试接口"})
@RestController
public class TestController {

    @Autowired
    CouponService couponService;

    @ApiOperation(value = "测试接口-优惠券列表")
    @GetMapping("/coupon/list")
    public Map<String, Object> couponList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        result.put("data", couponService.list(authenticationToken.getToken().getSubject()));
        return result;
    }

    @GetMapping("/cat/list")
    public Map<String, Object> catList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        return result;
    }

    @GetMapping("/dog/list")
    public Map<String, Object> dogList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        return result;
    }

    @ApiOperation("测试接口-产品列表")
    @GetMapping("/product/list")
    public Map<String, Object> productList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        result.put("data", new ArrayList<>());
        return result;
    }

    @ApiOperation("测试接口-添加产品")
    @PostMapping("/product/add")
    public Map<String, Object> addProduct(@ApiParam(value = "产品名称") @RequestParam(value = "name") String name,
                                          @ApiParam(value = "库存") @RequestParam(value = "inventory", required = false, defaultValue = "0") int inventory) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        result.put("data", new ArrayList<>());
        return result;
    }

    @ApiOperation("测试接口-订单列表列表")
    @GetMapping("/order/list")
    public Map<String, Object> orderList(JwtAuthenticationToken authenticationToken) {
        Map<String, Object> result = new HashMap<>(16);
        result.put("status", 1);
        result.put("data", new ArrayList<>());
        return result;
    }
}
