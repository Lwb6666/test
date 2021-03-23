package com.dxjr.portal.stockright.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.entity.CurAccountlog;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.stockright.mapper.CapitalAccountMapper;
import com.dxjr.portal.stockright.service.CapitalAccountService;

/***
 * 股权账户接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockAccountServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xinwugang
 * @version 0.1 2016-5-11
 */
@Service
public class CapitalAccountServiceImpl implements CapitalAccountService {
	@Autowired
	private CapitalAccountMapper capitalAccountMapper;

	@Override
	public void saveCurAccountlog(CurAccount cur, CurAccountlog curLog) {
		
		curLog.setUserId(cur.getUserId());
		curLog.setTotal(cur.getTotal());
		curLog.setUseMoney(cur.getUseMoney());
		curLog.setNoUseMoney(cur.getNoUseMoney());
		curLog.setInterestTotal(cur.getInterestTotal());
		curLog.setInterestYesterday(cur.getInterestYesterday());
		capitalAccountMapper.saveCurAccountlog(curLog);
	}
	
	public void saveAccountlog(AccountVo account, AccountLogCnd accountLog){
		accountLog.setUserId(account.getUserId());
		accountLog.setTotal(account.getTotal());
		accountLog.setUseMoney(account.getUseMoney());
		accountLog.setNoUseMoney(account.getNoUseMoney());
		accountLog.setCollection(account.getCollection());
		accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
		accountLog.setDrawMoney(account.getDrawMoney());
		accountLog.setNoDrawMoney(account.getNoDrawMoney());
		accountLog.seteUseMoney(account.geteUseMoney());
		accountLog.seteFreezeMoney(account.geteFreezeMoney());
		accountLog.seteCollection(account.geteCollection());
		capitalAccountMapper.saveAccountlog(accountLog);
	}

}
