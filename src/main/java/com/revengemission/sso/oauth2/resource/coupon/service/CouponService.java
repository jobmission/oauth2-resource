package com.revengemission.sso.oauth2.resource.coupon.service;

import com.revengemission.sso.oauth2.resource.coupon.domain.Coupon;

import java.util.List;

public interface CouponService extends CommonServiceInterface<Coupon> {
    List<Coupon> list(String userId);

    long count(String userId);
}
