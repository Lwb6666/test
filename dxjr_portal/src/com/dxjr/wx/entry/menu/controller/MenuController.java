package com.dxjr.wx.entry.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.wx.entry.menu.service.MenuService;

@Controller
@RequestMapping(value = "/wx/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * 
	 * <p>
	 * Description:创建菜单<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param httpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "/createMenu")
	@ResponseBody
	public String createMenu() {
		String buttons = menuService.createButton(null);
		return buttons;
	}
}
