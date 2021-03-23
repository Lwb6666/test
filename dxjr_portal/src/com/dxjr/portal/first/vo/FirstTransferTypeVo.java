package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:我的账户-直通车转让菜单中列表<br />
 * </p>
 * 
 * @title FirstTransferTypeVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月12日
 */
public class FirstTransferTypeVo extends FirstTenderRealVo implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;
	/**
	 * 债权价格
	 */
	private BigDecimal bondsAccount;
	/**
	 * 资金占用的利息[E]
	 */
	private BigDecimal interest;

	/**
	 * 利息的利息[G]
	 */
	private BigDecimal interestDiff;
	/**
	 * 转让管理费=投资本金*比率
	 */
	private BigDecimal manageFee;
	/**
	 * 转让管理费的月数=直通车开通时间到转让时间的月数差额
	 */
	private Integer manageFeeMonth;
	/**
	 * 转让管理费比率=3月及3月以内1%，3月以上0.5%
	 */
	private BigDecimal manageFeeRatio;

	public BigDecimal getBondsAccount() {
		return bondsAccount;
	}

	public void setBondsAccount(BigDecimal bondsAccount) {
		this.bondsAccount = bondsAccount;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getInterestDiff() {
		return interestDiff;
	}

	public void setInterestDiff(BigDecimal interestDiff) {
		this.interestDiff = interestDiff;
	}

	public BigDecimal getManageFee() {
		// 2015-06-01之前转让管理费免费
		if (new Date().compareTo(DateUtils.parse("2015-06-01", DateUtils.YMD_DASH)) < 0) {
			return new BigDecimal(0);
		}
		// 转让管理费=投资本金*比率
		manageFee = new BigDecimal(super.getAccount()).multiply(this.getManageFeeRatio()).setScale(2, RoundingMode.HALF_UP);
		return manageFee;
	}

	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	public Integer getManageFeeMonth() {
		// 转让管理费的月数=直通车开通时间到转让时间的月数差额
		manageFeeMonth = DateUtils.monthAfterDiff(DateUtils.convert2StartDate(super.getAddtime()), new Date());
		return manageFeeMonth;
	}

	public void setManageFeeMonth(Integer manageFeeMonth) {
		this.manageFeeMonth = manageFeeMonth;
	}

	public BigDecimal getManageFeeRatio() {
		// 3月及3月以内1%，3月以上0.5%
		if (this.getManageFeeMonth() <= 3) {
			manageFeeRatio = new BigDecimal("0.01");
		} 
		// 3-12个月0.5%
		else if(this.getManageFeeMonth() > 3 && this.getManageFeeMonth() <= 12) {
			manageFeeRatio = new BigDecimal("0.005");
		}else{
			manageFeeRatio = BigDecimal.ZERO;
		}
		return manageFeeRatio;
	}

	public void setManageFeeRatio(BigDecimal manageFeeRatio) {
		this.manageFeeRatio = manageFeeRatio;
	}

}
