package com.dxjr.portal.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.AccountUploadDocMapper;
import com.dxjr.portal.account.service.AccountUploadDocService;
import com.dxjr.portal.account.vo.AccountUploadDocCnd;
import com.dxjr.portal.account.vo.AccountUploadDocVo;

/**
 * <p>
 * Description:上传资料业务实现类<br />
 * </p>
 * 
 * @title AccountUploadDocServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
@Service
public class AccountUploadDocServiceImpl implements AccountUploadDocService {

	@Autowired
	private AccountUploadDocMapper accountUploadDocMapper;

	@Override
	public Integer queryUploadDocCountByUserId(Integer memberId) throws Exception {
		AccountUploadDocCnd accountUploadDocCnd = new AccountUploadDocCnd();
		accountUploadDocCnd.setUserId(memberId);
		return accountUploadDocMapper.queryAccountUploadDocCount(accountUploadDocCnd);
	}

	@Override
	public List<AccountUploadDocVo> queryUniqueStylesByBorrowId(Integer borrowId) throws Exception {
		return accountUploadDocMapper.queryUniqueStylesByBorrowId(borrowId);
	}

	@Override
	public Page queryByBorrowId(Map<String, Object> map, Page p) throws Exception {
		AccountUploadDocCnd accountUploadDocCnd = new AccountUploadDocCnd();
		accountUploadDocCnd.setBorrowId(Integer.parseInt(map.get("borrowId").toString()));

		List<AccountUploadDocVo> list = accountUploadDocMapper.queryAccountUploadDocList(accountUploadDocCnd);

		p.setResult(list);
		return p;
	}

	@Override
	public List<AccountUploadDocVo> queryAccountUploadDocList(AccountUploadDocCnd accountUploadDocCnd) throws Exception {
		return accountUploadDocMapper.queryAccountUploadDocList(accountUploadDocCnd);
	}
}
