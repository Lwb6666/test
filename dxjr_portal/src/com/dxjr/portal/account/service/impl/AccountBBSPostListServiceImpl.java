package com.dxjr.portal.account.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.AccountDetailMapper;
import com.dxjr.portal.account.service.AccountBBSPostListService;
@Service
public class AccountBBSPostListServiceImpl implements AccountBBSPostListService {

	@Autowired
	AccountDetailMapper accountDetailMapper;

	@Override
	public Page queryBbsNotesPage(Map<String,Object> params,Page page) {
		page.setTotalCount(accountDetailMapper.countBbsItemsList(params));
		page.setResult(accountDetailMapper.queryBbsItemsList(params,page));
		return page;
	}

	@Override
	public Boolean isCurrentLookerhasPower(Integer userId) {
		//是否有权限 true :有权限  false : 没有权限
		Integer lookers =accountDetailMapper.isCurrentLookerhasPower(userId);
		return ( lookers != null && lookers > 0) ? true : false;
	}

}
