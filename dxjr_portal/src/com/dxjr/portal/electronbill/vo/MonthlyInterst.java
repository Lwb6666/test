package com.dxjr.portal.electronbill.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthlyInterst implements Serializable {

	private String month;
	private BigDecimal interst;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getInterst() {
		return interst;
	}

	public void setInterst(BigDecimal interst) {
		this.interst = interst;
	}
}
