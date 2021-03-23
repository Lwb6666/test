package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LotterySourceTypeChance {
    private Integer id;

    private Integer lotteryChanceRuleInfoId;

    private Integer lotteryGoodsId;

    private BigDecimal chance;

    private Integer status;

    private Integer addUserId;

    private Date addTime;

    private String addIp;

    private Integer lastUpdateUserId;

    private Date lastUpdateTime;

    private String lastUpdateIp;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLotteryChanceRuleInfoId() {
        return lotteryChanceRuleInfoId;
    }

    public void setLotteryChanceRuleInfoId(Integer lotteryChanceRuleInfoId) {
        this.lotteryChanceRuleInfoId = lotteryChanceRuleInfoId;
    }

    public Integer getLotteryGoodsId() {
        return lotteryGoodsId;
    }

    public void setLotteryGoodsId(Integer lotteryGoodsId) {
        this.lotteryGoodsId = lotteryGoodsId;
    }

    public BigDecimal getChance() {
        return chance;
    }

    public void setChance(BigDecimal chance) {
        this.chance = chance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
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
        this.addIp = addIp;
    }

    public Integer getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Integer lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateIp() {
        return lastUpdateIp;
    }

    public void setLastUpdateIp(String lastUpdateIp) {
        this.lastUpdateIp = lastUpdateIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}