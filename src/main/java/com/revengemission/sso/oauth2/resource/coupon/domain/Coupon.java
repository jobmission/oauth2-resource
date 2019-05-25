package com.revengemission.sso.oauth2.resource.coupon.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Date beginTime;
    private Date endTime;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getFullLimit() {
        return fullLimit;
    }

    public void setFullLimit(Integer fullLimit) {
        this.fullLimit = fullLimit;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public Integer getScopeOfApplicationType() {
        return scopeOfApplicationType;
    }

    public void setScopeOfApplicationType(Integer scopeOfApplicationType) {
        this.scopeOfApplicationType = scopeOfApplicationType;
    }

    public String getScopeOfApplication() {
        return scopeOfApplication;
    }

    public void setScopeOfApplication(String scopeOfApplication) {
        this.scopeOfApplication = scopeOfApplication;
    }

    public Integer getValidityPeriodType() {
        return validityPeriodType;
    }

    public void setValidityPeriodType(Integer validityPeriodType) {
        this.validityPeriodType = validityPeriodType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
