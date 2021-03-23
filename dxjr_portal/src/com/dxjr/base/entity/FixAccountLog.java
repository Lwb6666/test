package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:定期宝账户日志类<br />
 * </p>
 * 
 * @title FixAccountLog.java
 * @package com.dxjr.base.entity
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public class FixAccountLog {
	
	/**
	 * 主键id
	 */
	private Integer id; 
	
	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId; 
	
	/**
	 * 账户变动类型
	 */
	private Integer type; 
	
	/**
	 * 借款标id
	 */
	private Integer borrowId; 
	
	/**
	 * 借款标名称
	 */
	private String borrowName; 
	
	/**
	 * 业务类型：1：借款标
	 */
	private Integer idType; 
	
	/**
	 * 操作金额
	 */
	private BigDecimal money; 
	
	/**
	 * 总额
	 */
	private BigDecimal total; 
	
	/**
	 * 可用余额
	 */
	private BigDecimal useMoney; 
	
	/**
	 * 冻结金额
	 */
	private BigDecimal noUseMoney; 
	
	/**
	 * 待收总额
	 */
	private BigDecimal collection; 
	
	/**
	 * 本金
	 */
	private BigDecimal capital;
	
	/**
	 * 实际收益
	 */
	private BigDecimal profit; 
	
	/**
	 * 操作人
	 */
	private Integer adduser; 
	
	/**
	 * 操作时间
	 */
	private Date addtime; 
	
	/**
	 * 操作ip
	 */
	private String addip; 
	
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
	
	public Integer getType () {
		return type;
	}
	
	
	public void setType (Integer type) {
		this.type = type;
	}
	
	public Integer getBorrowId () {
		return borrowId;
	}
	
	
	public void setBorrowId (Integer borrowId) {
		this.borrowId = borrowId;
	}
	
	public String getBorrowName () {
		return borrowName;
	}
	
	
	public void setBorrowName (String borrowName) {
		this.borrowName = borrowName;
	}
	
	public Integer getIdType () {
		return idType;
	}
	
	
	public void setIdType (Integer idType) {
		this.idType = idType;
	}
	
	public BigDecimal getMoney () {
		return money;
	}
	
	
	public void setMoney (BigDecimal money) {
		this.money = money;
	}
	
	public BigDecimal getTotal () {
		return total;
	}
	
	
	public void setTotal (BigDecimal total) {
		this.total = total;
	}
	
	public BigDecimal getUseMoney () {
		return useMoney;
	}
	
	
	public void setUseMoney (BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	
	public BigDecimal getNoUseMoney () {
		return noUseMoney;
	}
	
	
	public void setNoUseMoney (BigDecimal noUseMoney) {
		this.noUseMoney = noUseMoney;
	}
	
	
	public BigDecimal getCapital() {
		return capital;
	}


	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}


	public BigDecimal getProfit() {
		return profit;
	}


	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}


	public BigDecimal getCollection () {
		return collection;
	}
	
	
	public void setCollection (BigDecimal collection) {
		this.collection = collection;
	}
	
	public Integer getAdduser () {
		return adduser;
	}
	
	
	public void setAdduser (Integer adduser) {
		this.adduser = adduser;
	}
	
	public java.util.Date getAddtime () {
		return addtime;
	}
	
	
	public void setAddtime (java.util.Date addtime) {
		this.addtime = addtime;
	}
	
	public String getAddip () {
		return addip;
	}
	
	
	public void setAddip (String addip) {
		this.addip = addip;
	}
	
	public String getRemark () {
		return remark;
	}
	
	
	public void setRemark (String remark) {
		this.remark = remark;
	}


}
