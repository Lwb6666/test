package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:投资人待收类<br />
 * </p>
 * 
 * @title FixCollectionrecord.java
 * @package com.dxjr.base.entity
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public class FixCollectionrecord {
	
	/**
	 * 主键id
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
	 * 最终认购记录表Id
	 */
	private Integer fixTenderRealId; 
	
	/**
	 * 状态
	 */
	private Integer status; 
	
	/**
	 * 预计收款日期
	 */
	private Date repayTime; 
	
	/**
	 * 实际收款时间
	 */
	private Date repayYestime; 
	
	/**
	 * 待收金额
	 */
	private BigDecimal repayAccount; 
	
	/**
	 * 实收金额
	 */
	private BigDecimal repayYesaccount; 
	
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
	 * 实际垫付金额
	 */
	private BigDecimal advanceYesaccount; 
	
	/**
	 * 垫付时间
	 */
	private Date advancetime; 
	
	/**
	 * 添加时间
	 */
	private Date addtime; 
	
	/**
	 * 添加ip
	 */
	private String addip; 
	

	
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
	
	public Integer getFixTenderRealId () {
		return fixTenderRealId;
	}
	
	
	public void setFixTenderRealId (Integer fixTenderRealId) {
		this.fixTenderRealId = fixTenderRealId;
	}
	
	public Integer getStatus () {
		return status;
	}
	
	
	public void setStatus (Integer status) {
		this.status = status;
	}
	
	public Date getRepayTime () {
		return repayTime;
	}
	
	
	public void setRepayTime (Date repayTime) {
		this.repayTime = repayTime;
	}
	
	public Date getRepayYestime () {
		return repayYestime;
	}
	
	
	public void setRepayYestime (Date repayYestime) {
		this.repayYestime = repayYestime;
	}
	
	public BigDecimal getRepayAccount () {
		return repayAccount;
	}
	
	
	public void setRepayAccount (BigDecimal repayAccount) {
		this.repayAccount = repayAccount;
	}
	
	public BigDecimal getRepayYesaccount () {
		return repayYesaccount;
	}
	
	
	public void setRepayYesaccount (BigDecimal repayYesaccount) {
		this.repayYesaccount = repayYesaccount;
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
	
	public BigDecimal getAdvanceYesaccount () {
		return advanceYesaccount;
	}
	
	
	public void setAdvanceYesaccount (BigDecimal advanceYesaccount) {
		this.advanceYesaccount = advanceYesaccount;
	}
	
	public Date getAdvancetime () {
		return advancetime;
	}
	
	
	public void setAdvancetime (Date advancetime) {
		this.advancetime = advancetime;
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


}
