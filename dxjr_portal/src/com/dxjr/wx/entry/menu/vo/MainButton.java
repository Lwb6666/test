package com.dxjr.wx.entry.menu.vo;

import java.util.Arrays;

/**
 * 
 * <p>
 * Description:顶级菜单<br />
 * </p>
 * 
 * @title MainButton.java
 * @package com.wx.menu.entity
 * @author wangbo
 * @version 0.1 2014年5月6日
 */
public class MainButton {

	private CommonButton[] button;

	public CommonButton[] getButton() {
		return button;
	}

	public void setButton(CommonButton[] button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "MainButton [button=" + Arrays.toString(button) + "]";
	}

}
