package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:借款详情垫付统计数据VO<br />
 * </p>
 * 
 * @title BorrowDetailWebPayVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年9月13日
 */
public class BorrowDetailWebPayVo implements Serializable {

	private static final long serialVersionUID = 8151875417287128480L;
	/** 垫付期数 */
	private Integer webPayOrder;
	/** 垫付时间 */
	private Date advanceTime;
	/** 垫付金额 */
	private BigDecimal advanceYesAccount;



	public Integer webStatus;

	public Integer getWebPayOrder() {
		return webPayOrder;
	}

	public void setWebPayOrder(Integer webPayOrder) {
		this.webPayOrder = webPayOrder;
	}

	public Date getAdvanceTime() {
		return advanceTime;
	}

	public void setAdvanceTime(Date advanceTime) {
		this.advanceTime = advanceTime;
	}

	public BigDecimal getAdvanceYesAccount() {
		return advanceYesAccount;
	}

	public void setAdvanceYesAccount(BigDecimal advanceYesAccount) {
		this.advanceYesAccount = advanceYesAccount;
	}

	public Integer getWebStatus() {
		return webStatus;
	}

	public void setWebStatus(Integer webStatus) {
		this.webStatus = webStatus;
	}

}