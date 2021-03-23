package com.dxjr.wx.entry.message.autoreply.vo;

import com.mysql.jdbc.StringUtils;

/**
 * 推广人数模板类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title PromotNumber.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class PromotNumber {
	/**
	 * 尊敬的xxx， 您的总推广人数为17人，其中2014年11月及以后的推广人数为10人。
	 */

	private String username;
	private String allPromot;
	private String promotInMonth;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAllPromot() {
		if (StringUtils.isNullOrEmpty(allPromot))
			return "0";
		return allPromot;
	}

	public void setAllPromot(String allPromot) {
		this.allPromot = allPromot;
	}

	public String getPromotInMonth() {
		if (StringUtils.isNullOrEmpty(promotInMonth))
			return "0";
		return promotInMonth;
	}

	public void setPromotInMonth(String promotInMonth) {
		this.promotInMonth = promotInMonth;
	}

	//
	// @Override
	// public String toString() {
	// return "尊敬的" + username + ",\n 您的总推广人数为" + getAllPromot() + "人，其中" +
	// getDate() + "及以后的推广人数为" + getPromotInMonth() + "人" + "\n" +
	// WxConstants.FOUR;
	// }
	//
	@Override
	public String toString() {
		return "尊敬的" + username + ",\n 您的总推广人数为" + getAllPromot() + "人\n";
	}
}
