package com.dxjr.wx.entry.message.autoreply.vo;

import java.math.BigDecimal;

import com.dxjr.wx.utils.NumberUtil;

/**
 * 借款详情模板类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title BorrowDetail.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class BorrowDetail {
	/**
	 * 尊敬的xxx， 您的借款详情如下： 待还总额：205,537.50 待付借款利息：2,537.50 待付罚息：0 借款总额：2,970,592.37
	 */

	private String username;// 用户
	private BigDecimal totalNeedPay;// ： 待还总额
	private BigDecimal needPayInter;// 待付借款利息
	private BigDecimal needPayPena;// 待付罚息
	private BigDecimal totalborrow;// 借款总额

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTotalNeedPay() {
		return NumberUtil.fmtMicrometer(totalNeedPay);
	}

	public void setTotalNeedPay(BigDecimal totalNeedPay) {
		this.totalNeedPay = totalNeedPay;
	}

	public String getNeedPayInter() {
		return NumberUtil.fmtMicrometer(needPayInter);
	}

	public void setNeedPayInter(BigDecimal needPayInter) {
		this.needPayInter = needPayInter;
	}

	public String getNeedPayPena() {
		return NumberUtil.fmtMicrometer(needPayPena);
	}

	public void setNeedPayPena(BigDecimal needPayPena) {
		this.needPayPena = needPayPena;
	}

	public String getTotalborrow() {
		return NumberUtil.fmtMicrometer(totalborrow);
	}

	public void setTotalborrow(BigDecimal totalborrow) {
		this.totalborrow = totalborrow;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ",\n您的借款详情如下（元）：\n待还总额：" + getTotalNeedPay() + "\n待还利息：" + getNeedPayInter() + "\n待还罚息：" + getNeedPayPena() + "\n借款总额：" + getTotalborrow();
	}
}
