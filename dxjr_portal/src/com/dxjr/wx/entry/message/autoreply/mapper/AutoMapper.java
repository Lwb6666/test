package com.dxjr.wx.entry.message.autoreply.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.wx.entry.message.autoreply.vo.Auto;

public interface AutoMapper {
	/**
	 * <p>
	 * Description:根据关键字自动回复<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param paramString1
	 * @param paramString2
	 * @return Auto
	 */
	Auto queryByKeyWord(@Param("key") String paramString1);

	/**
	 * <p>
	 * Description:根据click的key查询自动回复<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param paramString1
	 * @param paramString2
	 * @return Auto
	 */
	Auto queryTextByEvent(@Param("key") String paramString1);
}