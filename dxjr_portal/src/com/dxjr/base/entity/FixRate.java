package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FixRate {
    private Integer id;

    private Integer fixType;

    private Integer signoutType;

    private BigDecimal signoutRate;

    private BigDecimal signoutFeeRate;

    private Integer addUser;

    private Date addTime;

    private String addIp;

    private String remark;

    private Integer updateUser;

    private Date updateTime;

    private String updateIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFixType() {
        return fixType;
    }

    public void setFixType(Integer fixType) {
        this.fixType = fixType;
    }

    public Integer getSignoutType() {
        return signoutType;
    }

    public void setSignoutType(Integer signoutType) {
        this.signoutType = signoutType;
    }

    public BigDecimal getSignoutRate() {
        return signoutRate;
    }

    public void setSignoutRate(BigDecimal signoutRate) {
        this.signoutRate = signoutRate;
    }

    public BigDecimal getSignoutFeeRate() {
        return signoutFeeRate;
    }

    public void setSignoutFeeRate(BigDecimal signoutFeeRate) {
        this.signoutFeeRate = signoutFeeRate;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }
}