package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.portal.account.mapper.AccountLogMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:资金操作日志业务实现类<br />
 * </p>
 * 
 * @title AccountLogServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
@Service
public class AccountLogServiceImpl implements AccountLogService {

	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;
	@Autowired
	private AccountLogMapper accountLogMapper;

	@Override
	public void insertAccountLog(AccountLog accountLog) throws Exception {
		accountLog.setAddtime(DateUtils.getTime());
		baseAccountLogMapper.insertEntity(accountLog);
	}

	@Override
	public Integer queryAccountLogCount(AccountLogCnd accountLogCnd) throws Exception {
		return accountLogMapper.queryAccountLogCount(accountLogCnd);
	}

	@Override
	public void saveAccountLogByParams(AccountVo accountVo, Integer userId, BigDecimal money,
                                       String remark, String addIp, String type, Integer idType,
                                       Integer borrowId, String borrowName, Integer isCustody)
			throws Exception {
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip(addIp);
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(accountVo.getCollection());
		accountLog.setUseMoney(accountVo.getUseMoney());
		accountLog.setNoUseMoney(accountVo.getNoUseMoney());
		accountLog.setTotal(accountVo.getTotal());
		accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountLog.setDrawMoney(accountVo.getDrawMoney());
		accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
		accountLog.setMoney(money);// 交易金额
		accountLog.setRemark(remark);
		accountLog.setToUser(userId);// 设置为本人
		accountLog.setUserId(accountVo.getUserId());
		accountLog.setType(type);
        accountLog.seteUseMoney(accountVo.geteUseMoney());
        accountLog.seteFreezeMoney(accountVo.geteFreezeMoney());
        accountLog.seteCollection(accountVo.geteCollection());
        accountLog.setIsCustody(isCustody);
		if (null != idType) {
			accountLog.setIdType(idType);
		}
		if (null != borrowId) {
			accountLog.setBorrowId(borrowId);
		}
		if (null != borrowName) {
			accountLog.setBorrowName(borrowName);
		}
        baseAccountLogMapper.insertAccountLog(accountLog);
	}

    @Override
    public void saveAccountLogByParams(AccountVo accountVo, Integer userId, BigDecimal money,
                                       String remark, String addIp, String type, Integer idType,
                                       Integer borrowId, String borrowName) throws Exception {
        AccountLog accountLog = new AccountLog();
		accountLog.setAddip(addIp);
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(accountVo.getCollection());
		accountLog.setUseMoney(accountVo.getUseMoney());
		accountLog.setNoUseMoney(accountVo.getNoUseMoney());
		accountLog.setTotal(accountVo.getTotal());
		accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountLog.setDrawMoney(accountVo.getDrawMoney());
		accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
		accountLog.setMoney(money);// 交易金额
		accountLog.setRemark(remark);
		accountLog.setToUser(userId);// 设置为本人
		accountLog.setUserId(accountVo.getUserId());
		accountLog.setType(type);
		if (null != idType) {
			accountLog.setIdType(idType);
		}
		if (null != borrowId) {
			accountLog.setBorrowId(borrowId);
		}
		if (null != borrowName) {
			accountLog.setBorrowName(borrowName);
		}
		this.insertAccountLog(accountLog);
    }

}
