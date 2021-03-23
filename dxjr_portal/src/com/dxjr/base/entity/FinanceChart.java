package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:实时财务拆线图数据<br />
 * </p>
 * 
 * @title FinanceChart.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年9月9日
 */
public class FinanceChart implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	private Integer id;
	/** 统计日期 */
	private Date calcDate;
	/** 借款总计 */
	private Double borrowMoney;
	/** 待收本息总计 */
	private Double waitReceiveMoney;
	/** 投资者人数 */
	private Integer investPersons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCalcDate() {
		return calcDate;
	}

	public void setCalcDate(Date calcDate) {
		this.calcDate = calcDate;
	}

	public Double getBorrowMoney() {
		return borrowMoney;
	}

	public void setBorrowMoney(Double borrowMoney) {
		this.borrowMoney = borrowMoney;
	}

	public Double getWaitReceiveMoney() {
		return waitReceiveMoney;
	}

	public void setWaitReceiveMoney(Double waitReceiveMoney) {
		this.waitReceiveMoney = waitReceiveMoney;
	}

	public Integer getInvestPersons() {
		return investPersons;
	}

	public void setInvestPersons(Integer investPersons) {
		this.investPersons = investPersons;
	}

}
