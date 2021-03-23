package com.dxjr.portal.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.service.BlackListSevice;

/**
 * 
 * <p>
 * Description:黑名单业务处理方法<br />
 * </p>
 * 
 * @title BlackListServiceImpl.java
 * @package com.dxjr.console.member.service.impl
 * @author yangshijin
 * @version 0.1 2015年1月25日
 */
@Service
public class BlackListServiceImpl implements BlackListSevice {

	@Autowired
	private BlackListMapper blackListMapper;

	@Override
	public boolean judgeBlackByUserIdAndType(int userId, int type) throws Exception {
		if (blackListMapper.queryCountByUserIdAndType(userId, type) > 0) {
			return true;
		}
		return false;
	}

}
