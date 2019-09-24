package com.revengemission.sso.oauth2.resource.coupon.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponTemplate extends BaseDomain {
    private String templateName;
    private String couponName;
    private String coverImageUrl;
    private Integer couponType;
    private Integer fullLimit;
    private BigDecimal faceValue;
    private Integer scopeOfApplicationType;
    private String scopeOfApplication;
    private Integer validityPeriodType;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer validPeriodHours;
    private Integer maximumQuantity;
    private Integer maximumPerUser;
    private Integer quantityIssued;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getValidPeriodHours() {
        return validPeriodHours;
    }

    public void setValidPeriodHours(Integer validPeriodHours) {
        this.validPeriodHours = validPeriodHours;
    }

    public Integer getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(Integer maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public Integer getMaximumPerUser() {
        return maximumPerUser;
    }

    public void setMaximumPerUser(Integer maximumPerUser) {
        this.maximumPerUser = maximumPerUser;
    }

    public Integer getQuantityIssued() {
        return quantityIssued;
    }

    public void setQuantityIssued(Integer quantityIssued) {
        this.quantityIssued = quantityIssued;
    }
}
