package com.revengemission.sso.oauth2.resource.coupon.persistence.mapper;

import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.SequenceEntity;
import com.revengemission.sso.oauth2.resource.coupon.persistence.entity.SequenceEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SequenceEntityMapper {
    long countByExample(SequenceEntityExample example);

    int deleteByExample(SequenceEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SequenceEntity record);

    int insertSelective(SequenceEntity record);

    List<SequenceEntity> selectByExample(SequenceEntityExample example);

    SequenceEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SequenceEntity record, @Param("example") SequenceEntityExample example);

    int updateByExample(@Param("record") SequenceEntity record, @Param("example") SequenceEntityExample example);

    int updateByPrimaryKeySelective(SequenceEntity record);

    int updateByPrimaryKey(SequenceEntity record);
}