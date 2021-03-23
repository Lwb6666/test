package com.dxjr.wx.entry.mainIn.mapper;

import com.dxjr.wx.entry.mainIn.vo.HistoryMessage;

public interface HistoryMessageMapper {
	/**
	 * 保存历史用户与微信记录
	 * <p>
	 * Description: 保存历史用户记录<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月27日
	 * @param message
	 * @return int
	 */
	int insert(HistoryMessage message);
}