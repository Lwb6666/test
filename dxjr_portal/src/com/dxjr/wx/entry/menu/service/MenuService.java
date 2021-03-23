package com.dxjr.wx.entry.menu.service;

public interface MenuService {
	/**
	 * 
	 * <p>
	 * Description:创建菜单<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param pId
	 * @return String
	 */
	String createButton(String pId);

	/**
	 * 
	 * <p>
	 * Description:删除菜单<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @return String
	 */
	String deleteButton();
}
