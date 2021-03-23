package com.dxjr.portal.index.vo;

import java.io.Serializable;

public class CountVo  implements Serializable {
	
	private static final long serialVersionUID = -5683124331346329445L;
	
	private String totalMoney;
	private String riskMargin;
	private String income;
	private String rate;
	
	public CountVo() {
	}
	
	public CountVo(String totalMoney, String riskMargin, String income, String rate) {
		this.totalMoney = totalMoney;
		this.riskMargin = riskMargin;
		this.income = income;
		this.rate = rate;
	}
	
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getRiskMargin() {
		return riskMargin;
	}
	public void setRiskMargin(String riskMargin) {
		this.riskMargin = riskMargin;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
	
}
