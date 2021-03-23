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
public class FixTenderUser {
	
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
	 * 状态
	 */
	private Integer status; 
	
	/**
	 * 添加时间
	 */
	private Date addTime; 
	
	 


	
	/**
	 * 备注
	 */
	private String remark; 
	

	/**
	 * 定期宝最终认购记录id
	 */
	private String fixTenderRealId; 
	
	/**
	 * 借款标ID
	 */
	private String borrowId; 
	
	/**
	 * 投标ID
	 */
	private String tenderId; 
	/**
	 * 标的名称
	 */
	private String borrowName; 
	
	/**
	 * 合同编号-借款标表里的合同编号
	 */
	private String contractNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFixBorrowId() {
		return fixBorrowId;
	}

	public void setFixBorrowId(Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFixTenderRealId() {
		return fixTenderRealId;
	}

	public void setFixTenderRealId(String fixTenderRealId) {
		this.fixTenderRealId = fixTenderRealId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	} 
	
 
}
