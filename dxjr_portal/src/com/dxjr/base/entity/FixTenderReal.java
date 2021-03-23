package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:定期宝最终认购记录类<br />
 * </p>
 * 
 * @title FixTenderReal.java
 * @package com.dxjr.base.entity
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public class FixTenderReal {
	
	/**
	 * 主键Id
	 */
	private Integer id; 
	
	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId; 
	
	/**
	 * 用户id
	 */
	private Integer userId; 
	
	/**
	 * 用户认购金额
	 */
	private BigDecimal account; 
	
	/**
	 * 可用余额
	 */
	private BigDecimal useMoney; 
	
	/**
	 * 状态
	 */
	private Integer status; 
	
	/**
	 * 添加时间
	 */
	private Date addTime; 
	
	/**
	 * 排序号
	 */
	private Integer orderNum; 
	
	/**
	 * 开通类型
	 */
	private Integer fixTenderType; 
	
	/**
	 * 定期宝原始认购记录ID
	 */
	private Integer parentId; 
	
	/**
	 * 解锁人id
	 */
	private Integer unlockUserid; 
	
	/**
	 * 解锁时间
	 */
	private Date unlockTime; 
	
	/**
	 * 解锁ip
	 */
	private String unlockIp; 
	
	/**
	 * 备注
	 */
	private String remark; 
	

	
	public Integer getId () {
		return id;
	}
	
	
	public void setId (Integer id) {
		this.id = id;
	}
	
	public Integer getFixBorrowId () {
		return fixBorrowId;
	}
	
	
	public void setFixBorrowId (Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}
	
	public Integer getUserId () {
		return userId;
	}
	
	
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	
	public java.math.BigDecimal getAccount () {
		return account;
	}
	
	
	public void setAccount (java.math.BigDecimal account) {
		this.account = account;
	}
	
	public java.math.BigDecimal getUseMoney () {
		return useMoney;
	}
	
	
	public void setUseMoney (java.math.BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	
	public Integer getStatus () {
		return status;
	}
	
	
	public void setStatus (Integer status) {
		this.status = status;
	}
	
	 
	
	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

 
	
	public Integer getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}


	public Integer getFixTenderType () {
		return fixTenderType;
	}
	
	
	public void setFixTenderType (Integer fixTenderType) {
		this.fixTenderType = fixTenderType;
	}
	
	public Integer getParentId () {
		return parentId;
	}
	
	
	public void setParentId (Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getUnlockUserid () {
		return unlockUserid;
	}
	
	
	public void setUnlockUserid (Integer unlockUserid) {
		this.unlockUserid = unlockUserid;
	}
	
	public java.util.Date getUnlockTime () {
		return unlockTime;
	}
	
	
	public void setUnlockTime (java.util.Date unlockTime) {
		this.unlockTime = unlockTime;
	}
	
	public String getUnlockIp () {
		return unlockIp;
	}
	
	
	public void setUnlockIp (String unlockIp) {
		this.unlockIp = unlockIp;
	}
	
	public String getRemark () {
		return remark;
	}
	
	
	public void setRemark (String remark) {
		this.remark = remark;
	}


}
