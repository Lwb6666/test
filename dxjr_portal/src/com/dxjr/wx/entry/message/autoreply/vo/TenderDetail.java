package com.dxjr.wx.entry.message.autoreply.vo;

import java.math.BigDecimal;

import com.dxjr.wx.utils.NumberUtil;

/**
 * 投标详情
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title TenderDetail.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class TenderDetail {
	/**
	 * 尊敬的xxx， 您的投标详情如下： 待收利息：94,883.29 待收总额：1,735,472.58 待付利息管理费：4,744.16
	 * 投标总额：11,060,387.89
	 */
	private String username;
	private BigDecimal collectedInter;
	private BigDecimal payInter;
	private BigDecimal totalTender;
	private BigDecimal totalCollected;
	private BigDecimal totalFixTender;
	private BigDecimal totalCurrTender;
	private BigDecimal unReceiveInterest;

	public String getUnReceiveInterest() {
		return NumberUtil.fmtMicrometer(unReceiveInterest);
	}

	public void setUnReceiveInterest(BigDecimal unReceiveInterest) {
		this.unReceiveInterest = unReceiveInterest;
	}

	public String getTotalCollected() {
		return NumberUtil.fmtMicrometer(totalCollected);
	}

	public void setTotalCollected(BigDecimal totalCollected) {
		this.totalCollected = totalCollected;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCollectedInter() {
		return NumberUtil.fmtMicrometer(collectedInter);
	}

	public void setCollectedInter(BigDecimal collectedInter) {
		this.collectedInter = collectedInter;
	}

	public String getPayInter() {
		return NumberUtil.fmtMicrometer(payInter);
	}

	public void setPayInter(BigDecimal payInter) {
		this.payInter = payInter;
	}

	public String getTotalTender() {
		return NumberUtil.fmtMicrometer(totalTender);
	}

	public void setTotalTender(BigDecimal totalTender) {

		this.totalTender = totalTender;
	}

	public void setTotalFixTender(BigDecimal totalFixTender) {
		this.totalFixTender = totalFixTender;
	}

	public void setTotalCurrTender(BigDecimal totalCurrTender) {
		this.totalCurrTender = totalCurrTender;
	}
	public String getTotalFixTender() {
		return NumberUtil.fmtMicrometer(totalFixTender);
	}
	public String getTotalCurrTender() {
		return NumberUtil.fmtMicrometer(totalCurrTender);
	}
	@Override
	public String toString() {
		return "尊敬的" + username + ",\n您的投标详情如下（元）:\n活期宝总额：" + getTotalCurrTender() + "\n定期宝总额：" + getTotalFixTender()+"\n待收利息：" + getCollectedInter()+"\n待收总额：" + getTotalCollected() +  "\n投标总额：" + getTotalTender();
	}

}
