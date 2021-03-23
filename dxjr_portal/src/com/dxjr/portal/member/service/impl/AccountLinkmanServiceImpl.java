package com.dxjr.portal.member.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.AccountLinkman;
import com.dxjr.base.mapper.BaseAccountLinkmanMapper;
import com.dxjr.portal.member.mapper.AccountLinkmanMapper;
import com.dxjr.portal.member.service.AccountLinkmanService;
import com.dxjr.portal.member.vo.AccountLinkmanCnd;
import com.dxjr.portal.member.vo.AccountLinkmanVo;

/**
 * <p>
 * Description:用户关联人接口的实现类<br />
 * </p>
 * 
 * @title AccountLinkmanServiceImpl.java
 * @package com.dxjr.portal.member.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
@Service
public class AccountLinkmanServiceImpl implements AccountLinkmanService {

	@Autowired
	private AccountLinkmanMapper accountLinkmanMapper;
	@Autowired
	private BaseAccountLinkmanMapper baseAccountLinkmanMapper;

	@Override
	public AccountLinkmanVo queryAccountLinkmanByUserId(Integer memberId)
			throws Exception {
		AccountLinkmanCnd accountLinkmanCnd = new AccountLinkmanCnd();
		accountLinkmanCnd.setUserId(memberId);
		List<AccountLinkmanVo> list = accountLinkmanMapper
				.queryAccountLinkmanList(accountLinkmanCnd);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insertAccountLinkman(AccountLinkman accountLinkman)
			throws Exception {
		return baseAccountLinkmanMapper.insertEntity(accountLinkman);
	}

	@Override
	public AccountLinkman queryById(Integer id) throws Exception {
		return baseAccountLinkmanMapper.queryById(id);
	}

	@Override
	public int updateAccountLinkman(AccountLinkman accountLinkman)
			throws Exception {
		return baseAccountLinkmanMapper.updateEntity(accountLinkman);
	}

	@Override
	public AccountLinkman queryByUserId(Integer userId) throws Exception {
		return baseAccountLinkmanMapper.queryByUserId(userId);
	}

	@Override
	public int saveOrUpdateLinkman(Integer userId, String name, String mobile, String email, String relationship, String ip)
			throws Exception {
		AccountLinkman accountLinkman = queryByUserId(userId);
		if(accountLinkman == null){
			accountLinkman = new AccountLinkman();
			accountLinkman.setName(name);
			accountLinkman.setMobile(mobile);
			accountLinkman.setEmail(email);
			accountLinkman.setRelationship(relationship);
			accountLinkman.setUserId(userId);
			accountLinkman.setAddtime(new Date());
			accountLinkman.setAddip(ip);
			//新增
			return insertAccountLinkman(accountLinkman);
		}else {
			accountLinkman.setName(name);
			accountLinkman.setMobile(mobile);
			accountLinkman.setEmail(email);
			accountLinkman.setRelationship(relationship);
			accountLinkman.setModifytime(new Date());
			//修改
			return updateAccountLinkman(accountLinkman);
		}
	}
}
