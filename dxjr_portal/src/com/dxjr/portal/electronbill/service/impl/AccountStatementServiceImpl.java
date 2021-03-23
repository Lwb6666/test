package com.dxjr.portal.electronbill.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.AccountReportMapper;
import com.dxjr.portal.electronbill.mapper.AccountStatementMapper;
import com.dxjr.portal.electronbill.service.AccountStatementService;
import com.dxjr.portal.electronbill.vo.AccountStatement;
import com.dxjr.portal.electronbill.vo.MonthlyInterst;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.mapper.RedAccountMapper;

@Service
public class AccountStatementServiceImpl implements AccountStatementService {
	
	@Autowired
	private AccountStatementMapper accountStatementMapper;
	@Autowired
	private AccountReportMapper accountReportMapper;
	@Autowired
	private RedAccountMapper redAccountMapper;
	public void insert(int userId, int year, int month) throws Exception {
		String msg = accountStatementMapper.insert(userId, year, month);
		if ("00001".equals(msg)) {
			throw new Exception("生成(" + year + "-" + month + ")电子对账单数据失败.");
		}
	}

	public AccountStatement selectByUserId(int userId, int year, int month) {
		AccountStatement accountStatement=accountStatementMapper.selectByUserId(userId, year, month);
		try {
			BigDecimal result = BigDecimal.ZERO;
			BigDecimal redTotal = BigDecimal.ZERO;
			//liutao 20151113  已使用红包总金额
			RedAccountCnd redAccountCnd=new RedAccountCnd();
			redAccountCnd.setUserId(userId);
			redAccountCnd.setMonth(Integer.valueOf(month).toString());
			redAccountCnd.setYear(Integer.valueOf(year).toString());
			redTotal=redAccountMapper.queryRedMoneyTotal(redAccountCnd);
			if(null==accountStatement||null==accountStatement.getAwardMoney()){
				accountStatement.setAwardMoney(BigDecimal.ZERO);
			}
			//总收益加上红包收益
			result=accountStatement.getAwardMoney().add(redTotal);
			accountStatement.setAwardMoney(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return accountStatement;
	}

	public List<MonthlyInterst> selectMonthlyInterstList(int userId, int year, int month) {
		return accountStatementMapper.selectMonthlyInterstList(userId, year, month);
	}
}

