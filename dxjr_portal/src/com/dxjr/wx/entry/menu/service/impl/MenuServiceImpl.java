package com.dxjr.wx.entry.menu.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.utils.JsonUtils;
import com.dxjr.wx.entry.menu.mapper.MenuMapper;
import com.dxjr.wx.entry.menu.service.MenuService;
import com.dxjr.wx.entry.menu.vo.ClickButton;
import com.dxjr.wx.entry.menu.vo.CommonButton;
import com.dxjr.wx.entry.menu.vo.ComplexButton;
import com.dxjr.wx.entry.menu.vo.MainButton;
import com.dxjr.wx.entry.menu.vo.Menu;
import com.dxjr.wx.entry.menu.vo.ViewButton;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper mapper;

	@Override
	public String createButton(String pId) {
		Map<ComplexButton, List<CommonButton>> menu = getMenu(null);
		Iterator<ComplexButton> iterator = menu.keySet().iterator();
		List<CommonButton> list = new ArrayList<CommonButton>();
		while (iterator.hasNext()) {
			ComplexButton complexButton = (ComplexButton) iterator.next();
			if (complexButton == null) {
				list.add(menu.get(complexButton).get(0));// 一级菜单
			} else {
				List<CommonButton> list2 = (List<CommonButton>) menu.get(complexButton);
				CommonButton[] btu = new CommonButton[list2.size()];
				CommonButton[] butt = (CommonButton[]) list2.toArray(btu);
				complexButton.setSub_button(butt);
				list.add(complexButton);
			}
		}
		MainButton mainButton = new MainButton();
		CommonButton[] btus = new CommonButton[list.size()];
		mainButton.setButton((CommonButton[]) list.toArray(btus));
		return JsonUtils.toJson(mainButton);
	}

	@Override
	public String deleteButton() {
		return null;
	}

	/**
	 * 获取菜单
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author Wang Bo
	 * @version 0.1 2014年11月1日
	 * @param pId
	 * @return Map<ComplexButton,List<CommonButton>>
	 */
	private Map<ComplexButton, List<CommonButton>> getMenu(String pId) {
		Map<ComplexButton, List<CommonButton>> map = new LinkedHashMap<ComplexButton, List<CommonButton>>();
		List<Menu> list = mapper.selectMenu(pId);
		for (Menu m : list) {
			List<Menu> menu2 = mapper.selectMenu(String.valueOf(m.getId()));
			List<CommonButton> li = new ArrayList<CommonButton>();
			ComplexButton btu = new ComplexButton();
			btu.setName(m.getName());
			if (menu2 == null || menu2.size() == 0) {
				// 只有一级菜单
				if (m.getType() != null) {
					if ("click".equalsIgnoreCase(m.getType())) {
						ClickButton clickButton = new ClickButton();
						clickButton.setKey(m.getEventKey());
						clickButton.setName(m.getName());
						clickButton.setType("click");
						li.add(clickButton);
					} else if ("view".equalsIgnoreCase(m.getType())) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(m.getName());
						viewButton.setType("view");
						viewButton.setUrl(m.getUrl());
						li.add(viewButton);
					}
					btu = null;
				}
			} else {
				// 有二级菜单
				for (Menu m1 : menu2) {
					if ("click".equalsIgnoreCase(m1.getType())) {
						ClickButton clickButton = new ClickButton();
						clickButton.setKey(m1.getEventKey());
						clickButton.setName(m1.getName());
						clickButton.setType("click");
						li.add(clickButton);
					} else if ("view".equalsIgnoreCase(m1.getType())) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(m1.getName());
						viewButton.setType("view");
						viewButton.setUrl(m1.getUrl());
						li.add(viewButton);
					}
				}
			}
			map.put(btu, li);
		}
		return map;
	}
}
