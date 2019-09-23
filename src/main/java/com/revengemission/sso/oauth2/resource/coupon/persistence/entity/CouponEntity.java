package com.revengemission.sso.oauth2.resource.coupon.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponEntity implements Serializable {
    private Long id;

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

    private int version;

    private int recordStatus;

    private int sortPriority;

    private String remark;

    private LocalDateTime dateCreated;

    private LocalDateTime lastModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl == null ? null : coverImageUrl.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
        this.scopeOfApplication = scopeOfApplication == null ? null : scopeOfApplication.trim();
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(int sortPriority) {
        this.sortPriority = sortPriority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CouponEntity other = (CouponEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getCouponName() == null ? other.getCouponName() == null : this.getCouponName().equals(other.getCouponName()))
            && (this.getCoverImageUrl() == null ? other.getCoverImageUrl() == null : this.getCoverImageUrl().equals(other.getCoverImageUrl()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCouponType() == null ? other.getCouponType() == null : this.getCouponType().equals(other.getCouponType()))
            && (this.getFullLimit() == null ? other.getFullLimit() == null : this.getFullLimit().equals(other.getFullLimit()))
            && (this.getFaceValue() == null ? other.getFaceValue() == null : this.getFaceValue().equals(other.getFaceValue()))
            && (this.getScopeOfApplicationType() == null ? other.getScopeOfApplicationType() == null : this.getScopeOfApplicationType().equals(other.getScopeOfApplicationType()))
            && (this.getScopeOfApplication() == null ? other.getScopeOfApplication() == null : this.getScopeOfApplication().equals(other.getScopeOfApplication()))
            && (this.getValidityPeriodType() == null ? other.getValidityPeriodType() == null : this.getValidityPeriodType().equals(other.getValidityPeriodType()))
            && (this.getBeginTime() == null ? other.getBeginTime() == null : this.getBeginTime().equals(other.getBeginTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getVersion() == other.getVersion())
            && (this.getRecordStatus() == other.getRecordStatus())
            && (this.getSortPriority() == other.getSortPriority())
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDateCreated() == null ? other.getDateCreated() == null : this.getDateCreated().equals(other.getDateCreated()))
            && (this.getLastModified() == null ? other.getLastModified() == null : this.getLastModified().equals(other.getLastModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getCouponName() == null) ? 0 : getCouponName().hashCode());
        result = prime * result + ((getCoverImageUrl() == null) ? 0 : getCoverImageUrl().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCouponType() == null) ? 0 : getCouponType().hashCode());
        result = prime * result + ((getFullLimit() == null) ? 0 : getFullLimit().hashCode());
        result = prime * result + ((getFaceValue() == null) ? 0 : getFaceValue().hashCode());
        result = prime * result + ((getScopeOfApplicationType() == null) ? 0 : getScopeOfApplicationType().hashCode());
        result = prime * result + ((getScopeOfApplication() == null) ? 0 : getScopeOfApplication().hashCode());
        result = prime * result + ((getValidityPeriodType() == null) ? 0 : getValidityPeriodType().hashCode());
        result = prime * result + ((getBeginTime() == null) ? 0 : getBeginTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + getVersion();
        result = prime * result + getRecordStatus();
        result = prime * result + getSortPriority();
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDateCreated() == null) ? 0 : getDateCreated().hashCode());
        result = prime * result + ((getLastModified() == null) ? 0 : getLastModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateId=").append(templateId);
        sb.append(", couponName=").append(couponName);
        sb.append(", coverImageUrl=").append(coverImageUrl);
        sb.append(", userId=").append(userId);
        sb.append(", couponType=").append(couponType);
        sb.append(", fullLimit=").append(fullLimit);
        sb.append(", faceValue=").append(faceValue);
        sb.append(", scopeOfApplicationType=").append(scopeOfApplicationType);
        sb.append(", scopeOfApplication=").append(scopeOfApplication);
        sb.append(", validityPeriodType=").append(validityPeriodType);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", version=").append(version);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", sortPriority=").append(sortPriority);
        sb.append(", remark=").append(remark);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastModified=").append(lastModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}