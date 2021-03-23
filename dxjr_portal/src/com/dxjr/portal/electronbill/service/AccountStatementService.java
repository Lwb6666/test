package com.dxjr.portal.electronbill.service;

import java.util.List;

import com.dxjr.portal.electronbill.vo.AccountStatement;
import com.dxjr.portal.electronbill.vo.MonthlyInterst;

public interface AccountStatementService {

	public void insert(int userId, int year, int month) throws Exception;

	public AccountStatement selectByUserId(int userId, int year, int month);

	public List<MonthlyInterst> selectMonthlyInterstList(int userId, int year, int month);
}
