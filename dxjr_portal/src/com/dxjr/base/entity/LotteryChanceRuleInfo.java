package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description:抽奖机会来源类型规则信息<br />
 * </p>
 * @title LotteryChanceRuleInfo.java
 * @package com.dxjr.base.entity 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public class LotteryChanceRuleInfo implements Serializable{
	private static final long serialVersionUID = -1257559796835527897L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 抽奖机会来源类型值
	 */
    private String code;

    /**
     * 抽奖机会来源类型名
     */
    private String name;

    /**
     * 抽奖次数
     */
    private Integer lotteryNum;

    /**
     * 状态【0：生效，-1：失效】
     */
    private Integer status;

    /**
     * 添加时间（精确到天）
     */
    private Date addTime;

    /**
     * 添加人ID
     */
    private Integer addUserId;

    /**
     * 添加人名称
     */
    private String addUserName;

    /**
     * 添加IP
     */
    private String addIp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 截止时间（精确到天）
     */
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(Integer lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}