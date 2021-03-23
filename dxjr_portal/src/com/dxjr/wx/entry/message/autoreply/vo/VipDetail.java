package com.dxjr.wx.entry.message.autoreply.vo;


/**
 * vip详情模板
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title VipDetail.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class VipDetail {
	/**
	 * 尊敬的xxx，您的vip有效期至2015-9-25。
	 */
	private String username;
	private String date2;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ", \n 您的vip有效期至" + date2 + "\n";
	}

}
