package com.dxjr.wx.entry.menu.vo;

/**
 * click菜单
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title ClickButton.java
 * @package com.dxjr.wx.entry.menu.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class ClickButton extends CommonButton {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
