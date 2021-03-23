package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.mapper.AccountReportMapper;
import com.dxjr.portal.account.mapper.BorrowReportMapper;
import com.dxjr.portal.account.service.AccountReportService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.util.UserNetValue;

/**
 * <p>
 * Description:我的借款统计信息业务类实现<br />
 * </p>
 * 
 * @title BorrowReportServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
@Service
public class BorrowReportServiceImpl implements BorrowReportService {

	@Autowired
	private BorrowReportMapper borrowReportMapper;
	@Autowired
	private AccountReportMapper accountReportMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountReportService accountReportService;
	@Autowired
	private AccountNetValueMapper netValueMapper;

	@Override
	public BigDecimal queryBorrowTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = borrowReportMapper.queryBorrowTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryUnpayCapitalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = borrowReportMapper.queryUnpayCapitalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryHavePayTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		// 查询某个用户ID下单纯 的已还总额的总计，（未去除提前还款）
		BigDecimal money = borrowReportMapper.queryHavePayTotalByMemberId(memberId);
		if (null != money) {
			result = money;
		}
		// 扣除 增加提前还款利息的总计
		BigDecimal addEarlyInterest = accountReportMapper.queryAddEarlyInterestTotalByMemberId(memberId);
		if (null != addEarlyInterest) {
			result = result.subtract(addEarlyInterest);
		}
		return result;
	}

	@Override
	public BigDecimal queryCashCostByMemberId(Integer memberId) throws Exception {
		BigDecimal result = borrowReportMapper.queryCashCostByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public BigDecimal queryNetMoneyLimit(Integer memberId) throws Exception {
		// 取得用户的净值额度
		UserNetValue netValue = new UserNetValue();
		netValue.setUserid(memberId);
		netValueMapper.callGetUserNetMoneyLimit(netValue);
		return netValue.getNetMoneyLimit();
	}

	@Override
	public BigDecimal queryWaitReceiveCapitalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = borrowReportMapper.queryWaitReceiveCapitalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryRepaymentAccountTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = borrowReportMapper.queryRepaymentAccountTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public int queryLoanTimes(Integer memberId) throws Exception {
		return borrowReportMapper.queryLoanCount(memberId, null);
	}

	@Override
	public int queryLoanNowRepaying(Integer memberId) throws Exception {
		return borrowReportMapper.queryLoanCount(memberId, 0);
	}
}
