package com.dxjr.wx.entry.menu.vo;

/**
 * 
 * <p>
 * Description:一级菜单实体<br />
 * </p>
 * 
 * @title ComplexButton.java
 * @package com.wx.menu.entity
 * @author wangbo
 * @version 0.1 2014年5月6日
 */
public class ComplexButton extends CommonButton {
	private CommonButton[] sub_button;

	public CommonButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(CommonButton[] sub_button) {
		this.sub_button = sub_button;
	}

}
