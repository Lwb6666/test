package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.portal.curAccount.entity.CurInterestDetail;

public class CurInterestDetailVo extends CurInterestDetail implements Serializable {

	private static final long serialVersionUID = -6903226903535940857L;
	
	//电子账单 - 活期当月累计收益
	private BigDecimal sumMonthMoney;

	/**
	 * @return sumMonthMoney : return the property sumMonthMoney.
	 */
	public BigDecimal getSumMonthMoney() {
		return sumMonthMoney;
	}

	/**
	 * @param sumMonthMoney : set the property sumMonthMoney.
	 */
	public void setSumMonthMoney(BigDecimal sumMonthMoney) {
		this.sumMonthMoney = sumMonthMoney;
	}
	
	

}