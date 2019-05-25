package com.revengemission.sso.oauth2.resource.coupon.service.impl;

import com.github.dozermapper.core.Mapper;
import com.revengemission.sso.oauth2.resource.coupon.domain.Coupon;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntityExample;
import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.CouponEntityMapper;
import com.revengemission.sso.oauth2.resource.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    Mapper dozerMapper;

    @Autowired
    CouponEntityMapper couponEntityMapper;

    @Override
    public List<Coupon> list(String userId) {
        CouponEntityExample example = new CouponEntityExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CouponEntity> entityList = couponEntityMapper.selectByExample(example);
        if (entityList != null && entityList.size() > 0) {
            List<Coupon> couponList = new ArrayList<>();
            entityList.forEach(e -> {
                couponList.add(dozerMapper.map(e, Coupon.class));
            });
            return couponList;
        } else {
            return null;
        }
    }

    @Override
    public long count(String userId) {
        CouponEntityExample example = new CouponEntityExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return couponEntityMapper.countByExample(example);
    }
}
