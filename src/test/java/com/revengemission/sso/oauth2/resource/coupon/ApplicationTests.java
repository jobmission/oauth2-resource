package com.revengemission.sso.oauth2.resource.coupon;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntityExample;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponTemplateEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.CouponEntityMapper;
import com.revengemission.sso.oauth2.resource.coupon.persistence.mapper.CouponTemplateEntityMapper;
import com.revengemission.sso.oauth2.resource.coupon.service.SequenceService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class ApplicationTests {

    @Autowired
    CouponTemplateEntityMapper couponTemplateEntityMapper;

    @Autowired
    CouponEntityMapper couponEntityMapper;

    @Autowired
    SequenceService sequenceService;


    @Disabled
    @Test
    public void sequenceTest() throws InterruptedException {

        for (int i = 0; i < 200; i++) {
            Runnable r = () -> System.out.println(sequenceService.nextValue("abc"));
            new Thread(r).start();
        }

        Thread.sleep(20000);

    }

    @Disabled
    @Test
    public void couponTest() {

        long template1Id = insertCouponTemplate(1);
        createCoupon(template1Id, "zhangsan");
        long template2Id = insertCouponTemplate(2);
        createCoupon(template2Id, "zhangsan");
        long total = couponEntityMapper.countByExample(new CouponEntityExample());

        System.out.println("total=" + total);

    }

    private long insertCouponTemplate(int couponType) {
        if (couponType == 1) {
            CouponTemplateEntity couponTemplateEntity1 = new CouponTemplateEntity();
            couponTemplateEntity1.setTemplateName(RandomStringUtils.randomAlphanumeric(10));
            couponTemplateEntity1.setCouponName("店铺满减券");
            couponTemplateEntity1.setCouponType(1);
            couponTemplateEntity1.setFullLimit(100);
            couponTemplateEntity1.setFaceValue(new BigDecimal("10"));
            couponTemplateEntity1.setScopeOfApplicationType(0);
            couponTemplateEntity1.setScopeOfApplication("");
            couponTemplateEntity1.setValidityPeriodType(2);
            couponTemplateEntity1.setValidPeriodHours(24);
            couponTemplateEntity1.setMaximumQuantity(1000);
            couponTemplateEntity1.setMaximumPerUser(1);
            couponTemplateEntity1.setQuantityIssued(0);
            couponTemplateEntity1.setDateCreated(LocalDateTime.now());
            couponTemplateEntityMapper.insert(couponTemplateEntity1);
            return couponTemplateEntity1.getId();
        } else {
            CouponTemplateEntity couponTemplateEntity2 = new CouponTemplateEntity();
            couponTemplateEntity2.setTemplateName(RandomStringUtils.randomAlphanumeric(10));
            couponTemplateEntity2.setCouponName("店铺折扣券");
            couponTemplateEntity2.setCouponType(1);
            couponTemplateEntity2.setFullLimit(100);
            couponTemplateEntity2.setFaceValue(new BigDecimal("0.9"));
            couponTemplateEntity2.setScopeOfApplicationType(0);
            couponTemplateEntity2.setScopeOfApplication("");
            couponTemplateEntity2.setValidityPeriodType(2);
            couponTemplateEntity2.setValidPeriodHours(24);
            couponTemplateEntity2.setMaximumQuantity(1000);
            couponTemplateEntity2.setMaximumPerUser(1);
            couponTemplateEntity2.setQuantityIssued(0);
            couponTemplateEntity2.setDateCreated(LocalDateTime.now());
            couponTemplateEntityMapper.insert(couponTemplateEntity2);
            return couponTemplateEntity2.getId();
        }
    }

    private long createCoupon(long templateId, String userId) {
        CouponTemplateEntity couponTemplateEntity = couponTemplateEntityMapper.selectByPrimaryKey(templateId);

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setTemplateId(templateId);
        couponEntity.setCouponName(couponTemplateEntity.getCouponName());
        couponEntity.setCoverImageUrl(couponTemplateEntity.getCoverImageUrl());
        couponEntity.setUserId(userId);
        couponEntity.setCouponType(couponTemplateEntity.getCouponType());
        couponEntity.setFullLimit(couponTemplateEntity.getFullLimit());
        couponEntity.setFaceValue(couponTemplateEntity.getFaceValue());
        couponEntity.setScopeOfApplicationType(couponTemplateEntity.getScopeOfApplicationType());
        couponEntity.setScopeOfApplication(couponTemplateEntity.getScopeOfApplication());
        couponEntity.setValidityPeriodType(couponTemplateEntity.getValidityPeriodType());
        if (couponTemplateEntity.getValidityPeriodType() == 1) {
            couponEntity.setBeginTime(couponTemplateEntity.getBeginTime());
            couponEntity.setEndTime(couponTemplateEntity.getEndTime());
        } else {
            couponEntity.setBeginTime(LocalDateTime.now());
            couponEntity.setEndTime(LocalDateTime.now().plusHours(couponTemplateEntity.getValidPeriodHours()));
        }
        couponEntity.setDateCreated(LocalDateTime.now());
        couponEntityMapper.insert(couponEntity);
        return couponEntity.getId();

    }

}
