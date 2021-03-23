package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:定期宝待还记录类<br />
 * </p>
 * 
 * @title FixRepaymentrecord.java
 * @package com.dxjr.base.entity
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public class FixRepaymentrecord {
	
	/**
	 * 主键id
	 */
	private Integer id; 
	
	/**
	 * 状态
	 */
	private Integer status; 
	
	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId; 
	
	/**
	 * 预计还款日期
	 */
	private Date repaymentTime; 
	
	/**
	 * 实际还款时间
	 */
	private Date repaymentYestime; 
	
	/**
	 * 待还金额
	 */
	private BigDecimal repaymentAccount; 
	
	/**
	 * 实还金额
	 */
	private BigDecimal repaymentYesaccount; 
	
	/**
	 * 利息
	 */
	private BigDecimal interest; 
	
	/**
	 * 本金
	 */
	private BigDecimal capital; 
	
	/**
	 * 逾期天数
	 */
	private Integer lateDays; 
	
	/**
	 * 逾期利息
	 */
	private BigDecimal lateInterest; 
	
	/**
	 * 垫付时间
	 */
	private Date advancetime; 
	
	/**
	 * 实际垫付金额
	 */
	private BigDecimal advanceYesaccount; 
	
	/**
	 * 垫付后逾期天数
	 */
	private Integer afterwebpayLateDay; 
	
	/**
	 * 是否补罚息
	 */
	private Integer isRepairInterest; 
	
	/**
	 * 补罚息时间
	 */
	private Date repairInterestTime; 
	
	/**
	 * 添加时间
	 */
	private Date addtime; 
	
	/**
	 * 添加ip
	 */
	private String addip; 
	
	/**
	 * 平台来源
	 */
	private Integer platform; 
	

	
	public Integer getId () {
		return id;
	}
	
	
	public void setId (Integer id) {
		this.id = id;
	}
	
	public Integer getStatus () {
		return status;
	}
	
	
	public void setStatus (Integer status) {
		this.status = status;
	}
	
	public Integer getFixBorrowId () {
		return fixBorrowId;
	}
	
	
	public void setFixBorrowId (Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}
	
	public Date getRepaymentTime () {
		return repaymentTime;
	}
	
	
	public void setRepaymentTime (Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	
	public Date getRepaymentYestime () {
		return repaymentYestime;
	}
	
	
	public void setRepaymentYestime (Date repaymentYestime) {
		this.repaymentYestime = repaymentYestime;
	}
	
	public BigDecimal getRepaymentAccount () {
		return repaymentAccount;
	}
	
	
	public void setRepaymentAccount (BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}
	
	public BigDecimal getRepaymentYesaccount () {
		return repaymentYesaccount;
	}
	
	
	public void setRepaymentYesaccount (BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}
	
	public BigDecimal getInterest () {
		return interest;
	}
	
	
	public void setInterest (BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getCapital () {
		return capital;
	}
	
	
	public void setCapital (BigDecimal capital) {
		this.capital = capital;
	}
	
	public Integer getLateDays () {
		return lateDays;
	}
	
	
	public void setLateDays (Integer lateDays) {
		this.lateDays = lateDays;
	}
	
	public BigDecimal getLateInterest () {
		return lateInterest;
	}
	
	
	public void setLateInterest (BigDecimal lateInterest) {
		this.lateInterest = lateInterest;
	}
	
	public Date getAdvancetime () {
		return advancetime;
	}
	
	
	public void setAdvancetime (Date advancetime) {
		this.advancetime = advancetime;
	}
	
	public BigDecimal getAdvanceYesaccount () {
		return advanceYesaccount;
	}
	
	
	public void setAdvanceYesaccount (BigDecimal advanceYesaccount) {
		this.advanceYesaccount = advanceYesaccount;
	}
	
	public Integer getAfterwebpayLateDay () {
		return afterwebpayLateDay;
	}
	
	
	public void setAfterwebpayLateDay (Integer afterwebpayLateDay) {
		this.afterwebpayLateDay = afterwebpayLateDay;
	}
	
	public Integer getIsRepairInterest () {
		return isRepairInterest;
	}
	
	
	public void setIsRepairInterest (Integer isRepairInterest) {
		this.isRepairInterest = isRepairInterest;
	}
	
	public Date getRepairInterestTime () {
		return repairInterestTime;
	}
	
	
	public void setRepairInterestTime (Date repairInterestTime) {
		this.repairInterestTime = repairInterestTime;
	}
	
	public Date getAddtime () {
		return addtime;
	}
	
	
	public void setAddtime (Date addtime) {
		this.addtime = addtime;
	}
	
	public String getAddip () {
		return addip;
	}
	
	
	public void setAddip (String addip) {
		this.addip = addip;
	}
	
	public Integer getPlatform () {
		return platform;
	}
	
	
	public void setPlatform (Integer platform) {
		this.platform = platform;
	}


}
