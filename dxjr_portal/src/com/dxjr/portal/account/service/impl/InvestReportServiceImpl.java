package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.InvestReportMapper;
import com.dxjr.portal.account.service.InvestReportService;

/**
 * <p>
 * Description:我的投资统计信息业务类实现<br />
 * </p>
 * 
 * @title InvestReportServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
@Service
public class InvestReportServiceImpl implements InvestReportService {

	@Autowired
	private InvestReportMapper investReportMapper;

	@Override
	public BigDecimal queryTenderTotalByMemberId(Integer memberId)
			throws Exception {
		BigDecimal result = investReportMapper
				.queryTenderTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public Integer queryUncollectedRankingByUserId(Integer memberId)
			throws Exception {
		Integer result = investReportMapper
				.queryUncollectedRankingByUserId(memberId);
		if (null != result) {
			return result;
		}
		return 0;
	}

	@Override
	public BigDecimal queryRechargeTotalByMemberId(Integer memberId)
			throws Exception {
		BigDecimal result = investReportMapper
				.queryRechargeTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryCashTotalByMemberId(Integer memberId)
			throws Exception {
		BigDecimal result = investReportMapper
				.queryCashTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public int queryInvestTimes(Integer memberId) throws Exception {
		/*
		 * 累计投标：
		 *	borrow status :2：投标中，3：满标复审中，4：还款中，5：还款结束，-1：流标，-2：被撤销 ，-3：审核失败，42：已垫付
		 *	terden status :0：正在投标 ，1：所投标还款中 ，2：所投标完成结束
		 */
		int status[] = { 0, 1, 2 };
		int borrowStatus[] ={2,3,4,5,-1,-2,-3,42};
		int result = investReportMapper.queryInvestCount(memberId, status,borrowStatus);
		return result;
	}

	@Override
	public int queryInvestCountNowRunning(Integer memberId) throws Exception {
		/*  
		 *  正在进行中的投标：
		 *	borrow status :2：投标中
		 *  terden status :0：正在投标
		 */
		int status[] = { 0 };
		int borrowStatus[] ={2};
		int result = investReportMapper.queryInvestCount(memberId, status,borrowStatus);
		return result;
	}

}
