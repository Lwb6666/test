package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.statics.BusinessConstants;

/**
 * <p>
 * Description:账号接口的实现类<br />
 * </p>
 * 
 * @title AccountServiceImpl.java
 * @package com.dxjr.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public Integer insertAccount(Integer userId) throws Exception {
		Account account = new Account();
		BigDecimal zero = BigDecimal.ZERO;
		account.setUserId(userId);
		// 初始化帐号的金额为0
		account.setTotal(zero);
		account.setUseMoney(zero);
		account.setNoUseMoney(zero);
		account.setCollection(zero);
		account.setFirstBorrowUseMoney(zero);
		account.setNetvalue(zero);
		account.setVersion(1);
		account.setECollection(BigDecimal.ZERO);
		account.setEFreezeMoney(BigDecimal.ZERO);
		account.setEUseMoney(BigDecimal.ZERO);
		baseAccountMapper.insertEntity(account);
		return account.getId();
	}

	@Override
	public AccountVo queryAccountByUserId(Integer memberId) throws Exception {
		AccountCnd accountCnd = new AccountCnd();
		accountCnd.setUserId(memberId);
		List<AccountVo> accountList = accountMapper
				.queryAccountList(accountCnd);
		if (null != accountList && accountList.size() == 1) {
			return accountList.get(0);
		}
		return null;
	}
	@Override
	public BigDecimal queryCollectionCapital(Integer memberId) throws Exception {
		AccountCnd accountCnd = new AccountCnd();
		accountCnd.setUserId(memberId);
		BigDecimal collectionCapital = accountMapper.queryCollectionCapital(accountCnd);
		return collectionCapital;
	}
	@Override
	public AccountVo queryAccountByUserIdForUpdate(Integer memberId)
			throws Exception {
		AccountCnd accountCnd = new AccountCnd();
		accountCnd.setUserId(memberId);
		// 锁定记录
		accountCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		List<AccountVo> accountList = accountMapper
				.queryAccountList(accountCnd);
		if (null != accountList && accountList.size() == 1) {
			return accountList.get(0);
		}
		return null;
	}

}
