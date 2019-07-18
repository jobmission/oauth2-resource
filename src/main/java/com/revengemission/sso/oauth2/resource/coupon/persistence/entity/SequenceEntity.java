package com.revengemission.sso.oauth2.resource.coupon.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class SequenceEntity implements Serializable {
    private Long id;

    private String sequenceName;

    private Long currentValue;

    private int version;

    private int recordStatus;

    private int sortPriority;

    private String remark;

    private Date dateCreated;

    private Date lastModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName == null ? null : sequenceName.trim();
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
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
        SequenceEntity other = (SequenceEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSequenceName() == null ? other.getSequenceName() == null : this.getSequenceName().equals(other.getSequenceName()))
            && (this.getCurrentValue() == null ? other.getCurrentValue() == null : this.getCurrentValue().equals(other.getCurrentValue()))
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
        result = prime * result + ((getSequenceName() == null) ? 0 : getSequenceName().hashCode());
        result = prime * result + ((getCurrentValue() == null) ? 0 : getCurrentValue().hashCode());
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
        sb.append(", sequenceName=").append(sequenceName);
        sb.append(", currentValue=").append(currentValue);
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