package com.revengemission.sso.oauth2.resource.coupon.service;


public interface SequenceService {
    default long nextValue(String sequenceName) {
        throw new RuntimeException("未实现");
    }

    default long[] nextValue(String sequenceName, int count) {
        throw new RuntimeException("未实现");
    }

    default long currentValue(String sequenceName) {
        throw new RuntimeException("未实现");
    }

}
