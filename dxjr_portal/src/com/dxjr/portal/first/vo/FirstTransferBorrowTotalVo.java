package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:直通车转让关联借款的待收统计信息<br />
 * </p>
 * 
 * @title FirstTransferBorrowTotalVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月16日
 */
public class FirstTransferBorrowTotalVo extends FirstTenderRealVo implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;
	/**
	 * 待收本金总和
	 */
	private BigDecimal firstRepayCapital;
	/**
	 * 直通车所投标待收总利息
	 */
	private BigDecimal firstRepayInterest;
	/**
	 * 借款标个数
	 */
	private Integer borrowCount;

	public BigDecimal getFirstRepayCapital() {
		return firstRepayCapital;
	}

	public void setFirstRepayCapital(BigDecimal firstRepayCapital) {
		this.firstRepayCapital = firstRepayCapital;
	}

	public BigDecimal getFirstRepayInterest() {
		return firstRepayInterest;
	}

	public void setFirstRepayInterest(BigDecimal firstRepayInterest) {
		this.firstRepayInterest = firstRepayInterest;
	}

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}
}
