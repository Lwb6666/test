package com.dxjr.portal.borrow.entity;

import java.math.BigDecimal;

public class TenderRecord {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.USER_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer userId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer borrowId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.STATUS
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer status;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.ACCOUNT
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private BigDecimal account;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.INTEREST
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private BigDecimal interest;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.REPAYMENT_ACCOUNT
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private BigDecimal repaymentAccount;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.REPAYMENT_YESACCOUNT
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private BigDecimal repaymentYesaccount;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.REPAYMENT_YESINTEREST
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private BigDecimal repaymentYesinterest;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.ADDTIME
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String addtime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.ADDIP
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String addip;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.TENDER_TYPE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer tenderType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.AUTOTENDER_ORDER
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer autotenderOrder;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.AUTOTENDER_ORDER_REMARK
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String autotenderOrderRemark;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.FIRST_BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private Integer firstBorrowId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.FIRST_BORROW_SCALE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String firstBorrowScale;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.USER_LEVEL
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String userLevel;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column rocky_b_tenderrecord.RATIO
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	private String ratio;

	/** 自动类型（1：按抵押标、担保标投标，2：按净值标、信用标投标 */
	private Integer autoType;
	
	private Integer platform;
	
	private Integer isVip;
	
	private Integer eBankInfoId;// 存管银行卡主键
	
	private String eFreezeNo;//银行冻结流水号
	
	private String eFreezeDate;// 冻结时间
	
	private Integer isCustody;//存管方式 【0：非存管，1：浙商存管】
	
	private String  bizInvestNo;//平台投资流水号
	
	private String eInvestDate;//投资时间
	
	private String eInvestNo; //银行投资流水号
	
	private Integer eInvestStatus;//银行投资状态(0：未上报,20：成功30：失败)
	
	private String bizFreezeNo;//平台冻结业务流水号
	
	

	public String getBizFreezeNo() {
		return bizFreezeNo;
	}

	public void setBizFreezeNo(String bizFreezeNo) {
		this.bizFreezeNo = bizFreezeNo;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public Integer geteBankInfoId() {
		return eBankInfoId;
	}

	public void seteBankInfoId(Integer eBankInfoId) {
		this.eBankInfoId = eBankInfoId;
	}

	public String geteFreezeNo() {
		return eFreezeNo;
	}

	public void seteFreezeNo(String eFreezeNo) {
		this.eFreezeNo = eFreezeNo;
	}

	public String geteFreezeDate() {
		return eFreezeDate;
	}

	public void seteFreezeDate(String eFreezeDate) {
		this.eFreezeDate = eFreezeDate;
	}

	public Integer getIsCustody() {
		return isCustody;
	}

	public void setIsCustody(Integer isCustody) {
		this.isCustody = isCustody;
	}

	public String getBizInvestNo() {
		return bizInvestNo;
	}

	public void setBizInvestNo(String bizInvestNo) {
		this.bizInvestNo = bizInvestNo;
	}

	public String geteInvestDate() {
		return eInvestDate;
	}

	public void seteInvestDate(String eInvestDate) {
		this.eInvestDate = eInvestDate;
	}

	public String geteInvestNo() {
		return eInvestNo;
	}

	public void seteInvestNo(String eInvestNo) {
		this.eInvestNo = eInvestNo;
	}

	public Integer geteInvestStatus() {
		return eInvestStatus;
	}

	public void seteInvestStatus(Integer eInvestStatus) {
		this.eInvestStatus = eInvestStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.ID
	 * 
	 * @return the value of rocky_b_tenderrecord.ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.ID
	 * 
	 * @param id the value for rocky_b_tenderrecord.ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.USER_ID
	 * 
	 * @return the value of rocky_b_tenderrecord.USER_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.USER_ID
	 * 
	 * @param userId the value for rocky_b_tenderrecord.USER_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.BORROW_ID
	 * 
	 * @return the value of rocky_b_tenderrecord.BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getBorrowId() {
		return borrowId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.BORROW_ID
	 * 
	 * @param borrowId the value for rocky_b_tenderrecord.BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.STATUS
	 * 
	 * @return the value of rocky_b_tenderrecord.STATUS
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.STATUS
	 * 
	 * @param status the value for rocky_b_tenderrecord.STATUS
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public BigDecimal getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public BigDecimal getRepaymentYesinterest() {
		return repaymentYesinterest;
	}

	public void setRepaymentYesinterest(BigDecimal repaymentYesinterest) {
		this.repaymentYesinterest = repaymentYesinterest;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.ADDTIME
	 * 
	 * @return the value of rocky_b_tenderrecord.ADDTIME
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getAddtime() {
		return addtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.ADDTIME
	 * 
	 * @param addtime the value for rocky_b_tenderrecord.ADDTIME
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.ADDIP
	 * 
	 * @return the value of rocky_b_tenderrecord.ADDIP
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getAddip() {
		return addip;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.ADDIP
	 * 
	 * @param addip the value for rocky_b_tenderrecord.ADDIP
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setAddip(String addip) {
		this.addip = addip;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.TENDER_TYPE
	 * 
	 * @return the value of rocky_b_tenderrecord.TENDER_TYPE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getTenderType() {
		return tenderType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.TENDER_TYPE
	 * 
	 * @param tenderType the value for rocky_b_tenderrecord.TENDER_TYPE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setTenderType(Integer tenderType) {
		this.tenderType = tenderType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.AUTOTENDER_ORDER
	 * 
	 * @return the value of rocky_b_tenderrecord.AUTOTENDER_ORDER
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getAutotenderOrder() {
		return autotenderOrder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.AUTOTENDER_ORDER
	 * 
	 * @param autotenderOrder the value for
	 *            rocky_b_tenderrecord.AUTOTENDER_ORDER
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setAutotenderOrder(Integer autotenderOrder) {
		this.autotenderOrder = autotenderOrder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.AUTOTENDER_ORDER_REMARK
	 * 
	 * @return the value of rocky_b_tenderrecord.AUTOTENDER_ORDER_REMARK
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getAutotenderOrderRemark() {
		return autotenderOrderRemark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.AUTOTENDER_ORDER_REMARK
	 * 
	 * @param autotenderOrderRemark the value for
	 *            rocky_b_tenderrecord.AUTOTENDER_ORDER_REMARK
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setAutotenderOrderRemark(String autotenderOrderRemark) {
		this.autotenderOrderRemark = autotenderOrderRemark;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.FIRST_BORROW_ID
	 * 
	 * @return the value of rocky_b_tenderrecord.FIRST_BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.FIRST_BORROW_ID
	 * 
	 * @param firstBorrowId the value for rocky_b_tenderrecord.FIRST_BORROW_ID
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.FIRST_BORROW_SCALE
	 * 
	 * @return the value of rocky_b_tenderrecord.FIRST_BORROW_SCALE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getFirstBorrowScale() {
		return firstBorrowScale;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.FIRST_BORROW_SCALE
	 * 
	 * @param firstBorrowScale the value for
	 *            rocky_b_tenderrecord.FIRST_BORROW_SCALE
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setFirstBorrowScale(String firstBorrowScale) {
		this.firstBorrowScale = firstBorrowScale;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.USER_LEVEL
	 * 
	 * @return the value of rocky_b_tenderrecord.USER_LEVEL
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getUserLevel() {
		return userLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.USER_LEVEL
	 * 
	 * @param userLevel the value for rocky_b_tenderrecord.USER_LEVEL
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column rocky_b_tenderrecord.RATIO
	 * 
	 * @return the value of rocky_b_tenderrecord.RATIO
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public String getRatio() {
		return ratio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column rocky_b_tenderrecord.RATIO
	 * 
	 * @param ratio the value for rocky_b_tenderrecord.RATIO
	 * 
	 * @mbggenerated Mon May 19 14:13:32 CST 2014
	 */
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Integer getAutoType() {
		return autoType;
	}

	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}

}