package com.dxjr.wx.entry.message.autoreply.vo;

import java.math.BigDecimal;

import com.dxjr.wx.utils.NumberUtil;

/**
 * 收益详情模板类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title IncomeDetail.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class IncomeDetail {
	/**
	 * 尊敬的xxx， 您的收益详情如下： 净收益：372,722.64 收入总额：422,390.88 支出总额：49,668.24
	 */
	private String username;
	private BigDecimal netIncome;// 净收益
	private BigDecimal totalIncome;// 收入总额
	private BigDecimal totalOut;// 支出总额
	private BigDecimal hasNetMoney;// 已赚收益

	public String getHasNetMoney() {
		return NumberUtil.fmtMicrometer(hasNetMoney);
	}

	public void setHasNetMoney(BigDecimal hasNetMoney) {
		this.hasNetMoney = hasNetMoney;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNetIncome() {
		return NumberUtil.fmtMicrometer(netIncome);
	}

	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}

	public String getTotalIncome() {
		return NumberUtil.fmtMicrometer(totalIncome);
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public String getTotalOut() {
		return NumberUtil.fmtMicrometer(totalOut);
	}

	public void setTotalOut(BigDecimal totalOut) {
		this.totalOut = totalOut;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ",\n您的收益详情如下（元）:\n净收益：" + getNetIncome();
	}

}
