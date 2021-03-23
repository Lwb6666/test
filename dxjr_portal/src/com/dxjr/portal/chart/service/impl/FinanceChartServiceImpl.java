package com.dxjr.portal.chart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.FinanceChart;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.chart.mapper.FinanceChartMapper;
import com.dxjr.portal.chart.service.FinanceChartService;
import com.dxjr.portal.chart.vo.BorrowDataInfoVo;
import com.dxjr.portal.chart.vo.DateMoneyVo;
import com.dxjr.portal.chart.vo.PublicChartsInfoVo;
import com.dxjr.portal.chart.vo.RepaymentChartCnd;
import com.dxjr.portal.chart.vo.RepaymentChartVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

@Service
public class FinanceChartServiceImpl implements FinanceChartService {
	@Autowired
	private FinanceChartMapper financeChartMapper;

	/**
	 * <p>
	 * Description:金额人数折线图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月26日
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMoneyPersonChart() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 取前30天的日期
		List<Date> dateList = new ArrayList<Date>();
		Date now = new Date();
		Date calcDate = null;
		for (int i = -29; i <= 0; i++) {
			calcDate = DateUtils.dayOffset(now, i);
			dateList.add(calcDate);
		}
		// 查询实时财务折线图前29天数据
		List<FinanceChart> financeCharts = financeChartMapper.queryFinanceCharts();
		// 查询今日借款总计
		BigDecimal nowBorrowMoney = financeChartMapper.queryNowTotalBorrowMoneyData();
		// 如果为空返回0
		nowBorrowMoney = this.returnZeroIfNull(nowBorrowMoney);
		// 查询今日投资者人数
		Integer nowInvestPersons = financeChartMapper.queryNowDayTotalPersonsData();
		// 查询今日待收本息总计
		BigDecimal nowDayCollection = financeChartMapper.queryNowCollectionData();
		// 如果为空返回0
		nowDayCollection = this.returnZeroIfNull(nowDayCollection);
		List<Double> borrowMoney = new ArrayList<Double>();
		List<Double> waitReceiveMoney = new ArrayList<Double>();
		List<Integer> investPersons = new ArrayList<Integer>();
		// 循环封装到数组里面
		for (FinanceChart financeChart : financeCharts) {
			borrowMoney.add(financeChart.getBorrowMoney());
			waitReceiveMoney.add(financeChart.getWaitReceiveMoney());
			investPersons.add(financeChart.getInvestPersons());
		}
		// 添加今天最新数据
		waitReceiveMoney.add(Double.parseDouble(nowDayCollection.divide(new BigDecimal(10000)).setScale(0, BigDecimal.ROUND_DOWN).toString()));
		borrowMoney.add(Double.parseDouble(nowBorrowMoney.toString()));
		investPersons.add(nowInvestPersons);

		resultMap.put("chartDate", dateList.toArray());
		resultMap.put("borrowMoney", borrowMoney.toArray());
		resultMap.put("waitReceiveMoney", waitReceiveMoney.toArray());
		resultMap.put("investPersons", investPersons.toArray());

		return resultMap;
	}

	@Override
	public PublicChartsInfoVo querySuccessBorrowChart() throws Exception {
		PublicChartsInfoVo publicChartsInfo = new PublicChartsInfoVo();
		publicChartsInfo.setChartHight(280);
		publicChartsInfo.setChartWidth(493);
		publicChartsInfo.setChartText("成交分布图 ");
		publicChartsInfo.setChartType(BusinessConstants.CHART_PIE);
		publicChartsInfo.setyText("元");
		// 成交的借款标list
		List<BorrowDataInfoVo> borrowDataList = financeChartMapper.querySuccessBorrowData();
		if (null != borrowDataList && borrowDataList.size() > 0) {
			String[] xCategories = new String[borrowDataList.size()];
			double[] chartData = new double[borrowDataList.size()];
			BorrowDataInfoVo borrowDataInfo = null;
			for (int i = 0; i < borrowDataList.size(); i++) {
				borrowDataInfo = borrowDataList.get(i);
				xCategories[i] = borrowDataInfo.getBorrowtypeName();
				chartData[i] = Double.parseDouble(borrowDataInfo.getAccount().setScale(2, BigDecimal.ROUND_CEILING).toString());
			}
			publicChartsInfo.setxCategories(xCategories);
			publicChartsInfo.setChartData(chartData);
		}
		return publicChartsInfo;
	}

	@Override
	public PublicChartsInfoVo queryWaitReceiveChart() throws Exception {
		PublicChartsInfoVo publicChartsInfo = new PublicChartsInfoVo();
		publicChartsInfo.setChartHight(280);
		publicChartsInfo.setChartWidth(493);
		publicChartsInfo.setChartText("待收分布图 ");
		publicChartsInfo.setChartType(BusinessConstants.CHART_PIE);
		publicChartsInfo.setyText("元");
		// 成交的借款标list
		List<BorrowDataInfoVo> borrowDataList = financeChartMapper.queryWaitReceiveData();
		if (null != borrowDataList && borrowDataList.size() > 0) {
			String[] xCategories = new String[borrowDataList.size()];
			double[] chartData = new double[borrowDataList.size()];
			BorrowDataInfoVo borrowDataInfo = null;
			for (int i = 0; i < borrowDataList.size(); i++) {
				borrowDataInfo = borrowDataList.get(i);
				xCategories[i] = borrowDataInfo.getBorrowtypeName();
				chartData[i] = Double.parseDouble(borrowDataInfo.getAccount().setScale(2, BigDecimal.ROUND_DOWN).toString());
			}
			publicChartsInfo.setxCategories(xCategories);
			publicChartsInfo.setChartData(chartData);
		}
		return publicChartsInfo;
	}

	@Override
	public Map<String, Object> queryMoneyPersonData() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询成交的借款标
		List<BorrowDataInfoVo> borrowDataList = financeChartMapper.querySuccessBorrowData();
		// 设置默认值
		BigDecimal recommendSuccessMoney = new BigDecimal(0);
		BigDecimal pledgeSuccessMoney = new BigDecimal(0);
		BigDecimal netSuccessMoney = new BigDecimal(0);
		BigDecimal secondSuccessMoney = new BigDecimal(0);
		BigDecimal guaranteeSuccessMoney = new BigDecimal(0);
		BigDecimal totalSuccessMoney = new BigDecimal(0);
		if (null != borrowDataList && borrowDataList.size() > 0) {
			BigDecimal account = new BigDecimal(0);
			for (BorrowDataInfoVo borrowDataInfo : borrowDataList) {
				account = borrowDataInfo.getAccount();
				// 1：信用标，2：抵押标，3：净值标，4：秒标
				if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_PLEDGE) {
					// 抵押标成交金额
					pledgeSuccessMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_NETVALUE) {
					// 净值标成交金额
					netSuccessMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_SEC) {
					// 秒还标
					secondSuccessMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_RECOMMEND) {
					// 信用标或推荐标
					recommendSuccessMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_GUARANTEED) {
					guaranteeSuccessMoney = account;
				}
				totalSuccessMoney = totalSuccessMoney.add(account);
			}
		}
		resultMap.put("pledgeSuccessMoney", pledgeSuccessMoney);
		resultMap.put("netSuccessMoney", netSuccessMoney);
		resultMap.put("secondSuccessMoney", secondSuccessMoney);
		resultMap.put("recommendSuccessMoney", recommendSuccessMoney);
		resultMap.put("guaranteeSuccessMoney", guaranteeSuccessMoney);
		// 总成交金额
		resultMap.put("totalSuccessMoney", totalSuccessMoney);

		// 正常还款总额
		BigDecimal normalRepaymentMoney = financeChartMapper.queryNormalRepaymentMoney();
		normalRepaymentMoney = this.returnZeroIfNull(normalRepaymentMoney);
		resultMap.put("normalRepaymentMoney", normalRepaymentMoney);
		// 逾期总额
		BigDecimal overdueMoney = financeChartMapper.queryOverdueMoney();
		overdueMoney = this.returnZeroIfNull(overdueMoney);
		resultMap.put("overdueMoney", overdueMoney);
		// 逾期已处理总额
		BigDecimal overdueYesProcessedMoney = financeChartMapper.queryOverdueYesProcessedMoney();
		overdueYesProcessedMoney = this.returnZeroIfNull(overdueYesProcessedMoney);
		resultMap.put("overdueYesProcessedMoney", overdueYesProcessedMoney);
		// 逾期未处理总额
		BigDecimal overdueNoProcessMoney = financeChartMapper.queryOverdueNoProcessMoney();
		overdueNoProcessMoney = this.returnZeroIfNull(overdueNoProcessMoney);
		resultMap.put("overdueNoProcessMoney", overdueNoProcessMoney);

		// 设置默认值
		BigDecimal pledgeCollectionMoney = new BigDecimal(0);
		BigDecimal netCollectionMoney = new BigDecimal(0);
		BigDecimal recommendCollectionMoney = new BigDecimal(0);
		BigDecimal guaranteeCollectionMoney = new BigDecimal(0);
		BigDecimal totalCollectionMoney = new BigDecimal(0);
		List<BorrowDataInfoVo> waitReceiveMoneyList = financeChartMapper.queryWaitReceiveData();
		if (null != waitReceiveMoneyList && waitReceiveMoneyList.size() > 0) {
			BigDecimal account = new BigDecimal(0);
			for (BorrowDataInfoVo borrowDataInfo : waitReceiveMoneyList) {
				account = borrowDataInfo.getAccount();
				// 1：信用标，2：抵押标，3：净值标，4：秒标
				if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_PLEDGE) {
					// 抵押标待收本息总额
					pledgeCollectionMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_NETVALUE) {
					// 净值标待收本息总额
					netCollectionMoney = account;
					// 信用标或推荐标的待收本息总额
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_RECOMMEND) {
					recommendCollectionMoney = account;
				} else if (borrowDataInfo.getBorrowtype().intValue() == Constants.BORROW_TYPE_GUARANTEED) {
					guaranteeCollectionMoney = account;
				}
				totalCollectionMoney = totalCollectionMoney.add(account);
			}
		}
		resultMap.put("pledgeCollectionMoney", pledgeCollectionMoney);
		resultMap.put("netCollectionMoney", netCollectionMoney);
		resultMap.put("recommendCollectionMoney", recommendCollectionMoney);
		resultMap.put("guaranteeCollectionMoney", guaranteeCollectionMoney);
		// 待收本息总额
		resultMap.put("totalCollectionMoney", totalCollectionMoney);
		// 投资者总收益
		BigDecimal investerNetMoney = financeChartMapper.queryInvesterNetMoney();
		investerNetMoney = this.returnZeroIfNull(investerNetMoney);
		resultMap.put("investerNetMoney", investerNetMoney);
		// 累计借款者人数
		Integer totalBorrowPersons = financeChartMapper.queryTotalBorrowPersons();
		resultMap.put("totalBorrowPersons", totalBorrowPersons);
		// 结清借款者人数
		Integer settleBorrowPersons = financeChartMapper.querySettleBorrowPersons();
		resultMap.put("settleBorrowPersons", settleBorrowPersons);
		resultMap.put("unclearedBorrowPersons", totalBorrowPersons - settleBorrowPersons);

		return resultMap;
	}

	@Override
	public Page findRepaymentChartForWeekPages(RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalcount = financeChartMapper.findRepaymentChartForWeekCount(repaymentChartCnd);
		page.setTotalCount(totalcount);
		List<RepaymentChartVo> list = financeChartMapper.findRepaymentChartForWeekPages(repaymentChartCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Page findRepaymentChartForOverduePages(RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalcount = financeChartMapper.findRepaymentChartForOverdueCount(repaymentChartCnd);
		page.setTotalCount(totalcount);
		List<RepaymentChartVo> list = financeChartMapper.findRepaymentChartForOverduePages(repaymentChartCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Page findRepaymentChartForFinishPages(RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalcount = financeChartMapper.findRepaymentChartForFinishCount(repaymentChartCnd);
		page.setTotalCount(totalcount);
		List<RepaymentChartVo> list = financeChartMapper.findRepaymentChartForFinishPages(repaymentChartCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Map<String, BigDecimal> queryRepaymentChartForWeek(RepaymentChartCnd repaymentChartCnd) throws Exception {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		List<Map<String, Object>> list = financeChartMapper.queryRepaymentChartForWeek(repaymentChartCnd);
		if (list.size() > 0 && list.get(0) != null) {
			if (list.get(0).get("noRepaymentAccount") != null) {
				map.put("noRepaymentAccount", new BigDecimal(list.get(0).get("noRepaymentAccount").toString()));
			}else{
				map.put("noRepaymentAccount", new BigDecimal(0));
			}
			if (list.get(0).get("yesRepaymentAccount") != null) {
				map.put("yesRepaymentAccount", new BigDecimal(list.get(0).get("yesRepaymentAccount").toString()));
			}else{
				map.put("yesRepaymentAccount", new BigDecimal(0));
			}
			if (list.get(0).get("repaymentAccount") != null) {
				map.put("repaymentAccount", new BigDecimal(list.get(0).get("repaymentAccount").toString()));
			}else{
				map.put("repaymentAccount", new BigDecimal(0));
			}
		} else {
			map.put("noRepaymentAccount", BigDecimal.ZERO);
			map.put("yesRepaymentAccount", BigDecimal.ZERO);
			map.put("repaymentAccount", BigDecimal.ZERO);
		}
		return map;
	}
	@Override
	public DateMoneyVo queryDateMoney() throws Exception {
		DateMoneyVo dateMoneyVo=financeChartMapper.queryDateMoney();
		dateMoneyVo.setYearTransactionAmount(financeChartMapper.queryYearDeal());
		dateMoneyVo.setYearTransactionCount(financeChartMapper.queryYearDealCount());
		dateMoneyVo.setCompensatoryCount(financeChartMapper.queryCompensatoryCount());
		return dateMoneyVo;
	}
	/**
	 * <p>
	 * Description:如果为空返回0<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月9日
	 * @param judgeNumber
	 * @return BigDecimal
	 */
	private BigDecimal returnZeroIfNull(BigDecimal judgeNumber) {
		if (null != judgeNumber) {
			return judgeNumber;
		}
		return new BigDecimal(0);
	}
}
