package com.revengemission.sso.oauth2.resource.coupon.persistence.mapper;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.ResourceEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.ResourceEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ResourceEntityMapper {
    long countByExample(ResourceEntityExample example);

    int deleteByExample(ResourceEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ResourceEntity record);

    int insertSelective(ResourceEntity record);

    List<ResourceEntity> selectByExample(ResourceEntityExample example);

    ResourceEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ResourceEntity record, @Param("example") ResourceEntityExample example);

    int updateByExample(@Param("record") ResourceEntity record, @Param("example") ResourceEntityExample example);

    int updateByPrimaryKeySelective(ResourceEntity record);

    int updateByPrimaryKey(ResourceEntity record);
}