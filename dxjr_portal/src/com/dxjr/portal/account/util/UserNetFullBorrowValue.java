package com.dxjr.portal.account.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:估算净值标满标后的净值额度和可提金额<br />
 * </p>
 * 
 * @title UserNetFullBorrowValue.java
 * @package com.dxjr.portal.account.util
 * @author yangshijin
 * @version 0.1 2015年1月21日
 */
public class UserNetFullBorrowValue implements Serializable {
	private static final long serialVersionUID = 2983336490619187126L;
	/** 借款人id */
	private Integer userid;
	/** 借款金额 */
	private BigDecimal borrow_account;
	/** 借款期限 */
	private Integer borrow_timelimit;
	/** 还款方式 */
	private Integer borrow_style;
	/** */
	private BigDecimal borrow_apr;

	/** 净值额度 */
	private BigDecimal netMoneyLimit;
	/** 可提金额 */
	private BigDecimal netDrawMoney;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public BigDecimal getBorrow_account() {
		return borrow_account;
	}

	public void setBorrow_account(BigDecimal borrow_account) {
		this.borrow_account = borrow_account;
	}

	public Integer getBorrow_timelimit() {
		return borrow_timelimit;
	}

	public void setBorrow_timelimit(Integer borrow_timelimit) {
		this.borrow_timelimit = borrow_timelimit;
	}

	public Integer getBorrow_style() {
		return borrow_style;
	}

	public void setBorrow_style(Integer borrow_style) {
		this.borrow_style = borrow_style;
	}

	public BigDecimal getNetMoneyLimit() {
		return netMoneyLimit;
	}

	public void setNetMoneyLimit(BigDecimal netMoneyLimit) {
		this.netMoneyLimit = netMoneyLimit;
	}

	public BigDecimal getNetDrawMoney() {
		return netDrawMoney;
	}

	public void setNetDrawMoney(BigDecimal netDrawMoney) {
		this.netDrawMoney = netDrawMoney;
	}

	public BigDecimal getBorrow_apr() {
		return borrow_apr;
	}

	public void setBorrow_apr(BigDecimal borrow_apr) {
		this.borrow_apr = borrow_apr;
	}
}
