package com.dxjr.wx.entry.message.autoreply.vo;

import com.dxjr.wx.common.WxConstants;
import com.mysql.jdbc.StringUtils;

/**
 * 期权数量
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title StockDetail.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class StockDetail {
	/**
	 * 尊敬的xxx， 您目前的期权数量为14600股。
	 */

	private String username;
	private String optionsNumber;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOptionsNumber() {
		if (StringUtils.isNullOrEmpty(optionsNumber))
			return "0";
		return optionsNumber;
	}

	public void setOptionsNumber(String optionsNumber) {
		this.optionsNumber = optionsNumber;
	}

	@Override
	public String toString() {
		return " 尊敬的" + username + ",\n 您目前的期权数量为" + getOptionsNumber() + "份" + "\n" + WxConstants.TWO;
	}

}
