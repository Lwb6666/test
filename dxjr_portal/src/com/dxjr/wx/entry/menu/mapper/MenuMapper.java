package com.dxjr.wx.entry.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.wx.entry.menu.vo.Menu;

public interface MenuMapper {
	/**
	 * 查询菜单
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月27日
	 * @param pId
	 * @return List<Menu>
	 */
	List<Menu> selectMenu(@Param("pId") String pId);
}
