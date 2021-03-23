package com.dxjr.portal.outerInterface.vo;

import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:逾期借款标Vo<br />
 * </p>
 * 
 * @title OverdueBorrowVo.java
 * @package com.dxjrweb.outer_interface.model
 * @author yangshijin
 * @version 0.1 2014年8月21日
 */
public class OverdueBorrowVo {
	/** 主键id */
	private int id;
	/** 用户ID */
	private int userId;
	/** 用户名 */
	private String username;
	/** 证件号 **/
	private String idcard;
	/** 逾期笔数 **/
	private Integer overdueCount;
	/** 逾期期数 **/
	private Integer overduePeriod;
	/** 逾期总额 **/
	private BigDecimal overdueTotal;
	/** 逾期本金 **/
	private BigDecimal overduePrincipal;
	/** 垫付总额 **/
	private BigDecimal paymentTotal;
	/** 垫付次数 **/
	private Integer paymentCount;
	/** 垫付期数 **/
	private Integer paymentPeriod;
	/** 已还金额 **/
	private BigDecimal repayAmount;
	/** 待还金额 **/
	private BigDecimal waitAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(Integer overdueCount) {
		this.overdueCount = overdueCount;
	}

	public Integer getOverduePeriod() {
		return overduePeriod;
	}

	public void setOverduePeriod(Integer overduePeriod) {
		this.overduePeriod = overduePeriod;
	}

	public BigDecimal getOverdueTotal() {
		return overdueTotal;
	}

	public void setOverdueTotal(BigDecimal overdueTotal) {
		this.overdueTotal = overdueTotal;
	}

	public BigDecimal getOverduePrincipal() {
		return overduePrincipal;
	}

	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}

	public BigDecimal getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(BigDecimal paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public Integer getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	public Integer getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(Integer paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getWaitAmount() {
		return waitAmount;
	}

	public void setWaitAmount(BigDecimal waitAmount) {
		this.waitAmount = waitAmount;
	}
}
