package com.dxjr.portal.floatCoupon.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ProductConfig {
    private Integer id;

    private Integer type;

    private Integer targetType;

    private Integer status;

    private BigDecimal money;

    private Integer aprType;

    private BigDecimal aprYear;

    private BigDecimal aprMonth;

    private Byte lockLimit;

    private Date activeTime;

    private Date deactiveTime;

    private Integer countDays;

    private Integer validDays;

    private String remark;

    private Integer adduser;

    private String addip;

    private Date addtime;

    private Date lastModifyTime;

    private Integer lastModifyUser;

    private String lastModifyRemark;

    private Boolean isCustody;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public Byte getLockLimit() {
        return lockLimit;
    }

    public void setLockLimit(Byte lockLimit) {
        this.lockLimit = lockLimit;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getDeactiveTime() {
        return deactiveTime;
    }

    public void setDeactiveTime(Date deactiveTime) {
        this.deactiveTime = deactiveTime;
    }

    public Integer getCountDays() {
        return countDays;
    }

    public void setCountDays(Integer countDays) {
        this.countDays = countDays;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
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

    public Boolean getIsCustody() {
        return isCustody;
    }

    public void setIsCustody(Boolean isCustody) {
        this.isCustody = isCustody;
    }
}