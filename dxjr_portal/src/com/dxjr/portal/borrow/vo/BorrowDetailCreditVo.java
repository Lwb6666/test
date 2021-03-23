package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:借款者信用档案VO<br />
 * </p>
 * 
 * @title BorrowDetailCreditVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年9月13日
 */
public class BorrowDetailCreditVo implements Serializable {

	private static final long serialVersionUID = 8151875417287128480L;
	/** 成功借款（笔）数 */
	private Integer borrowCount;
	/** 借款总额 **/
	private BigDecimal borrowAccount;
	/** 待还本息（元） */
	private BigDecimal waitToPay;
	/** 信用额度 **/
	private BigDecimal creditMoney;

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}

	public BigDecimal getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(BigDecimal borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public BigDecimal getWaitToPay() {
		return waitToPay;
	}

	public void setWaitToPay(BigDecimal waitToPay) {
		this.waitToPay = waitToPay;
	}

	public BigDecimal getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(BigDecimal creditMoney) {
		this.creditMoney = creditMoney;
	}

}