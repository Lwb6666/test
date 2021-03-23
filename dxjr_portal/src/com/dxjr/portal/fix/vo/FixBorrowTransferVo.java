package com.dxjr.portal.fix.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.base.entity.FixBorrowTransfer;


/**
 * @author WangQianJin
 * @title 定期宝转让
 * @date 2015年9月15日
 */
public class FixBorrowTransferVo extends FixBorrowTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 
	 * 待转出金额
	 */ 
	private BigDecimal dzcMoney;
	private String dzcMoneyWan; //万元为单位


	/**
	 * @return dzcMoney : return the property dzcMoney.
	 */
	public BigDecimal getDzcMoney() {
		return dzcMoney;
	}

	/**
	 * @param dzcMoney
	 *            : set the property dzcMoney.
	 */
	public void setDzcMoney(BigDecimal dzcMoney) {
		this.dzcMoney = dzcMoney;
	}

	/**
	 * @return dzcMoneyWan : return the property dzcMoneyWan.
	 */
	public String getDzcMoneyWan() {
		if(dzcMoney!=null)
		{
			return String.valueOf(dzcMoney.divide(new BigDecimal(10000)));
		}
		return dzcMoneyWan;
	}

	/**
	 * @param dzcMoneyWan : set the property dzcMoneyWan.
	 */
	public void setDzcMoneyWan(String dzcMoneyWan) {
		this.dzcMoneyWan = dzcMoneyWan;
	}

}