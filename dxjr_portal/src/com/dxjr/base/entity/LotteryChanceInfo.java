package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description:抽奖机会信息<br />
 * </p>
 * @title LotteryChanceInfo.java
 * @package com.dxjr.base.entity 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public class LotteryChanceInfo implements Serializable{
	private static final long serialVersionUID = -1347113401073599989L;

	/**
	 * 主键ID
	 */
    private Integer id;

    /**
     * 抽奖机会来源类型ID
     */
    private Integer lotteryChanceRuleInfoId;

    /**
     * 抽奖次数
     */
    private Integer lotteryNum;

    /**
     * 有效可用次数
     */
    private Integer useNum;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加IP
     */
    private String addIp;

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

    public Integer getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(Integer lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }
}