package com.dxjr.wx.entry.message.autoreply.vo;

import com.dxjr.wx.common.WxConstants;
import com.dxjr.wx.utils.NumberUtil;
import com.mysql.jdbc.StringUtils;

/**
 * 期权模板类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title LeastInvestment.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class LeastInvestment {
	/**
	 * 尊敬的xxx， 期权持有期，资产总额不能少于2200000元。
	 */
	private String username;
	private String leastInvestment;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLeastInvestment() {
		if (StringUtils.isNullOrEmpty(leastInvestment))
			return "0";
		return NumberUtil.fmtMicrometer2(leastInvestment);
	}

	public void setLeastInvestment(String leastInvestment) {
		this.leastInvestment = leastInvestment;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ", \n期权持有期间，资产总额不能少于" + getLeastInvestment() + "元" + "\n" + WxConstants.THREE;
	}

}
