package com.revengemission.sso.oauth2.resource.coupon.persistence.mapper;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponTemplateEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponTemplateEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponTemplateEntityMapper {
    long countByExample(CouponTemplateEntityExample example);

    int deleteByExample(CouponTemplateEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CouponTemplateEntity record);

    int insertSelective(CouponTemplateEntity record);

    List<CouponTemplateEntity> selectByExample(CouponTemplateEntityExample example);

    CouponTemplateEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CouponTemplateEntity record, @Param("example") CouponTemplateEntityExample example);

    int updateByExample(@Param("record") CouponTemplateEntity record, @Param("example") CouponTemplateEntityExample example);

    int updateByPrimaryKeySelective(CouponTemplateEntity record);

    int updateByPrimaryKey(CouponTemplateEntity record);
}