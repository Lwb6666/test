package com.dxjr.portal.chart.vo;

import java.math.BigDecimal;

import com.dxjr.common.Dictionary;

/**
 * <p>
 * Description:成交标<br />
 * </p>
 * 
 * @title BorrowDataInfoVo.java
 * @package com.dxjr.portal.chart.vo
 * @author justin.xu
 * @version 0.1 2014年9月9日
 */
public class BorrowDataInfoVo {
	/**
	 * 标类型
	 */
	private Integer borrowtype;
	/**
	 * 标金额
	 */
	private BigDecimal account;
	/**
	 * 标名字
	 */
	private String borrowtypeName;

	public Integer getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(Integer borrowtype) {
		this.borrowtype = borrowtype;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public String getBorrowtypeName() {
		return Dictionary.getValue(300, String.valueOf(borrowtype));
	}
}