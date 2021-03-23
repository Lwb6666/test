package com.dxjr.wx.entry.menu.vo;

/**
 * 
 * <p>
 * Description:view菜单实体<br />
 * </p>
 * 
 * @title ViewButton.java
 * @package com.wx.menu.entity
 * @author wangbo
 * @version 0.1 2014年5月6日
 */
public class ViewButton extends CommonButton {
	private String type;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
