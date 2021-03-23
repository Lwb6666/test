package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:待还统计数据VO<br />
 * </p>
 * 
 * @title BorrowDetailRepayVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年9月12日
 */
public class BorrowDetailRepayVo implements Serializable {

	private static final long serialVersionUID = 8151875417287128480L;
	/** 待还本息 */
	private BigDecimal waitToPayMoney;
	/** 剩余期数 */
	private Integer remainPeriods;
	/** 下一期还款日 */
	private String repaymentTime;
	private Date repaymentTimeDate;

	private String status;

	public Date getRepaymentTimeDate() {
		if (null != repaymentTime) {
			return DateUtils.timeStampToDate(repaymentTime);
		}
		return repaymentTimeDate;
	}

	public BigDecimal getWaitToPayMoney() {
		return waitToPayMoney;
	}

	public void setWaitToPayMoney(BigDecimal waitToPayMoney) {
		this.waitToPayMoney = waitToPayMoney;
	}

	public Integer getRemainPeriods() {
		return remainPeriods;
	}

	public void setRemainPeriods(Integer remainPeriods) {
		this.remainPeriods = remainPeriods;
	}

	public String getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}