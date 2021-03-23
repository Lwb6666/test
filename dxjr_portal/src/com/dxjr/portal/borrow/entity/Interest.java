package com.dxjr.portal.borrow.entity;

import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:利息计算器用到的实体类<br />
 * </p>
 * 
 * @title Interest.java
 * @package com.dxjr.portal.borrow.entity
 * @author yangshijin
 * @version 0.1 2014年8月15日
 */
public class Interest {

	private BigDecimal money;// 本金
	private BigDecimal rate;// 年利率
	private BigDecimal period;// 期限
	private int category;// 类型 0表示按月借款,1表示按天借款
	private int style;// 还款方式 0表示没有限制,1表示等额本息还款,2表示按月付息到期还本,3表示到期还本付息
	private BigDecimal rateP;// 每期利率
	private BigDecimal interest;// 总利息
	private BigDecimal moneyInterestP;// 每期应还本息

	public BigDecimal getMoneyInterestP() {
		return moneyInterestP;
	}

	public void setMoneyInterestP(BigDecimal moneyInterestP) {
		this.moneyInterestP = moneyInterestP;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getPeriod() {
		return period;
	}

	public void setPeriod(BigDecimal period) {
		this.period = period;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public BigDecimal getRateP() {
		return rateP;
	}

	public void setRateP(BigDecimal rateP) {
		this.rateP = rateP;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

}
