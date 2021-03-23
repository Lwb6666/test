package com.dxjr.portal.chart.vo;

import java.math.BigDecimal;

/**
 * <p>
 * Description:日期对应金额和人数<br />
 * </p>
 * 
 * @title DateMoneyInfoVo.java
 * @package com.dxjr.portal.chart.vo
 * @author justin.xu
 * @version 0.1 2014年9月7日
 */
public class DateMoneyInfoVo {
	/**
	 * 统计日期
	 */
	private String calcDate;
	/**
	 * 统计人数/每日投资者id
	 */
	private Integer persons;
	/**
	 * 金额
	 */
	private BigDecimal account;

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public String getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(String calcDate) {
		this.calcDate = calcDate;
	}

	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

}