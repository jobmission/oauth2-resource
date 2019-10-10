package com.revengemission.sso.oauth2.resource.coupon.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class Coupon extends BaseDomain {
    private Long templateId;
    private String couponName;
    private String coverImageUrl;
    private String userId;
    private Integer couponType;
    private Integer fullLimit;
    private BigDecimal faceValue;
    private Integer scopeOfApplicationType;
    private String scopeOfApplication;
    private Integer validityPeriodType;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;

}
