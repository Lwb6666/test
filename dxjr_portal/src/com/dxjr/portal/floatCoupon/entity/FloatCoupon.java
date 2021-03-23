package com.dxjr.portal.floatCoupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FloatCoupon {
    private Integer id;

    private Integer userId;

    private Integer productConfigId;

    private Integer type;

    private Integer targetType;

    private Integer targetId;

    private String targetName;

    private Integer bizId;

    private Integer status;

    private Integer aprType;

    private BigDecimal aprYear;

    private BigDecimal aprMonth;

    private BigDecimal interest;

    private BigDecimal capital;

    private Integer activeUser;

    private Date activeTime;

    private Integer countDays;

    private Date exitDate;

    private Integer validDays;

    private Date endDate;

    private String remark;

    private Date addtime;

    private Integer adduser;

    private String addip;

    private Date lastModifyTime;

    private Integer lastModifyUser;

    private String lastModifyRemark;

    private Integer platform;

    private Integer isCustody;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductConfigId() {
        return productConfigId;
    }

    public void setProductConfigId(Integer productConfigId) {
        this.productConfigId = productConfigId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAprType() {
        return aprType;
    }

    public void setAprType(Integer aprType) {
        this.aprType = aprType;
    }

    public BigDecimal getAprYear() {
        return aprYear;
    }

    public void setAprYear(BigDecimal aprYear) {
        this.aprYear = aprYear;
    }

    public BigDecimal getAprMonth() {
        return aprMonth;
    }

    public void setAprMonth(BigDecimal aprMonth) {
        this.aprMonth = aprMonth;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public Integer getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Integer activeUser) {
        this.activeUser = activeUser;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getCountDays() {
        return countDays;
    }

    public void setCountDays(Integer countDays) {
        this.countDays = countDays;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getAdduser() {
        return adduser;
    }

    public void setAdduser(Integer adduser) {
        this.adduser = adduser;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(Integer lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public String getLastModifyRemark() {
        return lastModifyRemark;
    }

    public void setLastModifyRemark(String lastModifyRemark) {
        this.lastModifyRemark = lastModifyRemark == null ? null : lastModifyRemark.trim();
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getIsCustody() {
        return isCustody;
    }

    public void setIsCustody(Integer isCustody) {
        this.isCustody = isCustody;
    }
}