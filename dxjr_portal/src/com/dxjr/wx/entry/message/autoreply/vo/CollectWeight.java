package com.dxjr.wx.entry.message.autoreply.vo;

import com.dxjr.wx.common.WxConstants;
import com.dxjr.wx.utils.NumberUtil;
import com.mysql.jdbc.StringUtils;

/**
 * 加权待收模板类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title CollectWeight.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class CollectWeight {
	/**
	 * 尊敬的xxx， 您目前的加权待收为人民币2000000.00元。
	 */
	private String username;
	private String weightMoney;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWeightMoney() {
		if (StringUtils.isNullOrEmpty(weightMoney))
			return "0";
		return NumberUtil.fmtMicrometer(weightMoney);
	}

	public void setWeightMoney(String weightMoney) {

		this.weightMoney = weightMoney;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ",\n您目前的加权待收为人民币" + getWeightMoney() + "元。" + "\n" + WxConstants.ONE;
	}
}
