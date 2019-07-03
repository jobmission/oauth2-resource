package com.revengemission.sso.oauth2.resource.coupon.persistence.mapper;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.CouponEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CouponEntityMapper {
    long countByExample(CouponEntityExample example);

    int deleteByExample(CouponEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CouponEntity record);

    int insertSelective(CouponEntity record);

    List<CouponEntity> selectByExample(CouponEntityExample example);

    CouponEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CouponEntity record, @Param("example") CouponEntityExample example);

    int updateByExample(@Param("record") CouponEntity record, @Param("example") CouponEntityExample example);

    int updateByPrimaryKeySelective(CouponEntity record);

    int updateByPrimaryKey(CouponEntity record);
}