package com.dxjr.portal.electronbill.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.AccountReportService;
import com.dxjr.portal.account.service.MyAccountReportService;
import com.dxjr.portal.account.vo.UnReceiveInterestCnd;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.chart.vo.PublicChartsInfoVo;
import com.dxjr.portal.curAccount.service.CurInterestDetailService;
import com.dxjr.portal.curAccount.vo.CurInterestDetailCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailVo;
import com.dxjr.portal.electronbill.service.AccountStatementService;
import com.dxjr.portal.electronbill.vo.AccountStatement;
import com.dxjr.portal.electronbill.vo.MonthlyInterst;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description: 电子账单 MB <br />
 * </p>
 * 
 * @title AccountStatementController.java
 * @package com.dxjr.portal.electronbill.controller
 * @author HuangJun
 * @version 0.1 2015年5月26日
 */
@Controller
@RequestMapping("/bill")
public class AccountStatementController extends BaseController {
	public Logger logger = Logger.getLogger(AccountStatementController.class);

	private static BigDecimal HUNDRED = BigDecimal.valueOf(100);

	@Autowired
	private AccountStatementService accountStatementService;

	// 电子账单-按月统计 - 活期累计收益
	@Autowired
	private CurInterestDetailService curInterestDetailService;
	// 电子账单-按月统计-债权金额
	@Autowired
	private AccountReportService accountReportService;

	@Autowired
	private MyAccountReportService myAccountReportService;

	@RequiresAuthentication
	@RequestMapping("/{year}/{month}")
	public ModelAndView index(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {

		Calendar cal = Calendar.getInstance();
		if (year < 2015 || year > cal.get(Calendar.YEAR) || (year == cal.get(Calendar.YEAR) && month >= (cal.get(Calendar.MONTH) + 1))) {
			return redirect("/error/404.html");
		}

		Integer userId = currentUser().getUserId();
		AccountStatement accountStatement = accountStatementService.selectByUserId(userId, year, month);
		if (accountStatement == null) {
			try {
				accountStatementService.insert(userId, year, month);
				accountStatement = accountStatementService.selectByUserId(userId, year, month);
			} catch (Exception e) {
				stackTraceError(logger, e);
				return redirect("/myaccount/toIndex.html");
			}
		}

		// 电子账单hj -add ：当月活期收益
		Map<String, BigDecimal> curAccountMap = new HashMap<String, BigDecimal>();
		try {

			// 按月 -活期收益
			int y = year;
			int m = month;
			int lastDay = DateUtils.getMonthLastDay(y, m);
			String sDateStr = (y + "").concat("-").concat(m + "").concat("-").concat("01").concat(" 0:0:0"); // 2015-5-01 0:0:0
			String eDateStr = (y + "").concat("-").concat(m + "").concat("-").concat(lastDay + "").concat(" 23:59:59"); // 2015-5-31 23:59:59
			Date sDate = DateUtils.parse(sDateStr, DateUtils.YMD_HMS);
			Date eDate = DateUtils.parse(eDateStr, DateUtils.YMD_HMS);
			CurInterestDetailCnd curInterestDetailCnd = new CurInterestDetailCnd();
			curInterestDetailCnd.setUserId(userId);
			curInterestDetailCnd.setsDate(sDate);
			curInterestDetailCnd.seteDate(eDate);
			CurInterestDetailVo retCurInterestDetailVo = curInterestDetailService.querySumMonthMoneyByConn(curInterestDetailCnd);
			BigDecimal bdCurMonthInterest = new BigDecimal(0.00);
			if (retCurInterestDetailVo != null) {
				bdCurMonthInterest = retCurInterestDetailVo.getSumMonthMoney();
				curAccountMap.put("cur_interest_total", bdCurMonthInterest);
			}

			// 按月 -债权转出收益
			BigDecimal bdTransfer = new BigDecimal(0.00);
			UnReceiveInterestCnd unReceiveInterestCnd = new UnReceiveInterestCnd();
			unReceiveInterestCnd.setUserId(userId);
			unReceiveInterestCnd.setsDate(sDate);
			unReceiveInterestCnd.seteDate(eDate);
			bdTransfer = accountReportService.queryTransferDiffByMonth(unReceiveInterestCnd);

			// 按月 - 债权认购支出
			BigDecimal bdSubscribe = new BigDecimal(0.00);
			bdSubscribe = accountReportService.querySubscribeTransferDiffByMemberIdMonth(unReceiveInterestCnd);
			curAccountMap.put("payTotal_subscribeTransferDiff", bdSubscribe);

			// 净收益 = 利息收入 + 其他收入合计 - 支出合计
			BigDecimal netProfitSum = new BigDecimal(0.00);
			BigDecimal interestSum = new BigDecimal(0.00);
			BigDecimal incomeSum = new BigDecimal(0.00);
			BigDecimal expendSum = new BigDecimal(0.00);
			if (accountStatement != null) {
				// 收入合计 = 原来的金额- 债权金额(删了) +债权转出收益 + 活期收益
				accountStatement.setOtherTotalIncome(accountStatement.getOtherTotalIncome().subtract(accountStatement.getTransferDiff())
						.add(bdTransfer).add(bdCurMonthInterest));
				// 债权转出收益
				accountStatement.setTransferDiff(bdTransfer);
				// 支出合计 = 原来的金额 + 债权认购支出
				accountStatement.setTotalExpenditure(accountStatement.getTotalExpenditure().add(bdSubscribe));
				// 利息收入
				interestSum = accountStatement.getInterst();
				// 其他收入合计
				incomeSum = accountStatement.getOtherTotalIncome();
				// 支出合计
				expendSum = accountStatement.getTotalExpenditure();
				// 净收益
				netProfitSum = interestSum.add(incomeSum).subtract(expendSum);
				accountStatement.setNetIncome(netProfitSum);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return forword("account/bill/billlist").addObject("year", year).addObject("month", month).addObject("accountStatement", accountStatement)
				.addObject("curAccountMap", curAccountMap);
	}

	@RequiresAuthentication
	@RequestMapping(value = "/showDebitItemPercent/{year}/{month}")
	@ResponseBody
	public PublicChartsInfoVo debitItemPercent(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		Integer userId = currentUser().getUserId();
		AccountStatement accountStatement = accountStatementService.selectByUserId(userId, year, month);
		// 按月 - 债权认购支出
		BigDecimal bdSubscribe = new BigDecimal(0.00);
		try {
			// 按月 -活期收益
			int y = year;
			int m = month;
			int lastDay = DateUtils.getMonthLastDay(y, m);
			String sDateStr = (y + "").concat("-").concat(m + "").concat("-").concat("01").concat(" 0:0:0"); // 2015-5-01 0:0:0
			String eDateStr = (y + "").concat("-").concat(m + "").concat("-").concat(lastDay + "").concat(" 23:59:59"); // 2015-5-31 23:59:59
			Date sDate = DateUtils.parse(sDateStr, DateUtils.YMD_HMS);
			Date eDate = DateUtils.parse(eDateStr, DateUtils.YMD_HMS);
			UnReceiveInterestCnd unReceiveInterestCnd = new UnReceiveInterestCnd();
			unReceiveInterestCnd.setUserId(userId);
			unReceiveInterestCnd.setsDate(sDate);
			unReceiveInterestCnd.seteDate(eDate);
			
			bdSubscribe = accountReportService.querySubscribeTransferDiffByMemberIdMonth(unReceiveInterestCnd);
			if(accountStatement!=null)
			{
				// 支出合计 = 原来的金额 + 债权认购支出
				accountStatement.setTotalExpenditure(accountStatement.getTotalExpenditure().add(bdSubscribe));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* 生成图表 开始 */
		PublicChartsInfoVo debitItemsCharts = new PublicChartsInfoVo();
		debitItemsCharts.setChartHight(359);
		debitItemsCharts.setChartWidth(464);
		debitItemsCharts.setChartText("支出各项占比(%）");
		debitItemsCharts.setChartType(BusinessConstants.CHART_PIE);
		debitItemsCharts.setyText("%");
		// publicChartsInfo.setxText("月份");
		// 成交的借款标list
		String[] xCategories = null;
		double[] chartData = null;
		if (accountStatement!=null && accountStatement.getTotalExpenditure() != null && accountStatement.getTotalExpenditure().compareTo(BigDecimal.ZERO) > 0) {
			xCategories = new String[10];
			chartData = new double[10];
			xCategories[0] = "借款管理费";
			chartData[0] = accountStatement.getBorrowManageFee().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[1] = "提现费用支出";
			chartData[1] = accountStatement.getCashCost().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4).multiply(HUNDRED)
					.doubleValue();
			xCategories[2] = "转可提费用支出";
			chartData[2] = accountStatement.getDrawDeductFee().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[3] = "借款利息支出";
			chartData[3] = accountStatement.getInterestRepay().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[4] = "利息管理费支出";
			chartData[4] = accountStatement.getInterestCost().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[5] = "罚息支出";
			chartData[5] = accountStatement.getLateInterestRepay().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[6] = "充值费用支出";
			chartData[6] = accountStatement.getRechargeFee().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[7] = "转让管理费用支出";
			chartData[7] = accountStatement.getTransferManageFee().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4)
					.multiply(HUNDRED).doubleValue();
			xCategories[8] = "VIP费用支出";
			chartData[8] = accountStatement.getVipCost().divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4).multiply(HUNDRED)
					.doubleValue();
			xCategories[9] = "债权认购支出";
			chartData[9] = bdSubscribe.divide(accountStatement.getTotalExpenditure(), BigDecimal.ROUND_DOWN, 4).multiply(HUNDRED)
					.doubleValue();
		} else {
			xCategories = new String[1];
			chartData = new double[1];
			xCategories[0] = "本月无支出项";
			chartData[0] = 100D;
		}
		debitItemsCharts.setxCategories(xCategories);
		debitItemsCharts.setChartData(chartData);
		debitItemsCharts.setSeriesName("");
		/* 生成图表 结束 */
		return debitItemsCharts;
	}

	/**
	 * <p>
	 * Description:生成收入所占比图表<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年2月13日
	 * @param accountStatement
	 * @return PublicChartsInfoVo
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/showCreditItemPercent/{year}/{month}")
	@ResponseBody
	public PublicChartsInfoVo createCreditItemCharts(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		Integer userId = currentUser().getUserId();
		AccountStatement accountStatement = accountStatementService.selectByUserId(userId, year, month);
		// 按月 -债权转出收益
		BigDecimal bdTransfer = new BigDecimal(0.00);
		BigDecimal bdCurMonthInterest = new BigDecimal(0.00);
		try {
	
			// 按月 -活期收益
			int y = year;
			int m = month;
			int lastDay = DateUtils.getMonthLastDay(y, m);
			String sDateStr = (y + "").concat("-").concat(m + "").concat("-").concat("01").concat(" 0:0:0"); // 2015-5-01 0:0:0
			String eDateStr = (y + "").concat("-").concat(m + "").concat("-").concat(lastDay + "").concat(" 23:59:59"); // 2015-5-31 23:59:59
			Date sDate = DateUtils.parse(sDateStr, DateUtils.YMD_HMS);
			Date eDate = DateUtils.parse(eDateStr, DateUtils.YMD_HMS);
			CurInterestDetailCnd curInterestDetailCnd = new CurInterestDetailCnd();
			curInterestDetailCnd.setUserId(userId);
			curInterestDetailCnd.setsDate(sDate);
			curInterestDetailCnd.seteDate(eDate);
			CurInterestDetailVo retCurInterestDetailVo = curInterestDetailService.querySumMonthMoneyByConn(curInterestDetailCnd);
			if (retCurInterestDetailVo != null) {
				bdCurMonthInterest = retCurInterestDetailVo.getSumMonthMoney();
			}
			UnReceiveInterestCnd unReceiveInterestCnd = new UnReceiveInterestCnd();
			unReceiveInterestCnd.setUserId(userId);
			unReceiveInterestCnd.setsDate(sDate);
			unReceiveInterestCnd.seteDate(eDate);
			bdTransfer = accountReportService.queryTransferDiffByMonth(unReceiveInterestCnd);
			
			if (accountStatement != null) {
				// 收入合计 = 原来的金额- 债权金额(删了) +债权转出收益 + 活期收益
				accountStatement.setOtherTotalIncome(accountStatement.getOtherTotalIncome().subtract(accountStatement.getTransferDiff())
						.add(bdTransfer).add(bdCurMonthInterest));
				accountStatement.setTransferDiff(bdTransfer);
				// 收入合计 = 利息收入+其他收入合计
				accountStatement.setTotalIncome(accountStatement.getInterst().add(accountStatement.getOtherTotalIncome()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PublicChartsInfoVo creditItemCharts = new PublicChartsInfoVo();
		creditItemCharts.setChartHight(359);
		creditItemCharts.setChartWidth(464);
		creditItemCharts.setChartText("收入各项占比(%）");
		creditItemCharts.setChartType(BusinessConstants.CHART_PIE);
		creditItemCharts.setyText("%");
		// 成交的借款标list
		if (accountStatement != null && accountStatement.getTotalIncome() != null && accountStatement.getTotalIncome().compareTo(BigDecimal.ZERO) > 0) {
			String[] xCategories = new String[7];
			double[] chartData = new double[7];
			xCategories[0] = "利息收入";
			chartData[0] = accountStatement.getInterst().divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[1] = "罚息收入";
			chartData[1] = accountStatement.getLateInterest().divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[2] = "奖励收入";
			chartData[2] = accountStatement.getAwardMoney().divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[3] = "行权金额";
			chartData[3] = accountStatement.getStockMoney().divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[4] = "债权转出收益";
			chartData[4] = bdTransfer.divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[5] = "活期收益";
			chartData[5] = bdCurMonthInterest.divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			xCategories[6] = "定期宝收益";
			chartData[6] = accountStatement.getFixInterest().divide(accountStatement.getTotalIncome(), BigDecimal.ROUND_DOWN, 6).multiply(HUNDRED)
					.doubleValue();
			creditItemCharts.setxCategories(xCategories);
			creditItemCharts.setChartData(chartData);
		} else {
			String[] xCategories = new String[1];
			double[] chartData = new double[1];
			xCategories[0] = "本月无收入";
			chartData[0] = 100D;

			creditItemCharts.setxCategories(xCategories);
			creditItemCharts.setChartData(chartData);
		}
		return creditItemCharts;
	}

	/**
	 * <p>
	 * Description:生成近一年利息收入图表<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年2月13日
	 * @param year
	 * @param month
	 * @param userId
	 * @return PublicChartsInfoVo
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/showInterst/{year}/{month}")
	@ResponseBody
	public PublicChartsInfoVo createMonthlyInterstCharts(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
		Integer userId = currentUser().getUserId();
		List<MonthlyInterst> monthlyInterstList = accountStatementService.selectMonthlyInterstList(userId, year, month);

		/* 近一年利息收入图表生成 开始 */
		PublicChartsInfoVo monthlyInterstCharts = new PublicChartsInfoVo();
		monthlyInterstCharts.setChartHight(589);
		monthlyInterstCharts.setChartWidth(231);
		monthlyInterstCharts.setChartText("近一年利息收入(单位：元 ）");
		monthlyInterstCharts.setChartType(BusinessConstants.CHART_COLUMN);
		// 成交的借款标list
		if (null != monthlyInterstList && monthlyInterstList.size() > 0) {
			String[] xCategories = new String[monthlyInterstList.size()];
			double[] chartData = new double[monthlyInterstList.size()];
			MonthlyInterst monthlyInterstVo = null;
			for (int i = 0; i < monthlyInterstList.size(); i++) {
				monthlyInterstVo = monthlyInterstList.get(i);
				xCategories[i] = monthlyInterstVo.getMonth().split("-")[1] + "月";
				chartData[i] = Double.parseDouble(monthlyInterstVo.getInterst().setScale(2, BigDecimal.ROUND_CEILING).toString());
			}
			monthlyInterstCharts.setxCategories(xCategories);
			monthlyInterstCharts.setChartData(chartData);
			monthlyInterstCharts.setSeriesName("");
		}
		/* 近一年利息收入图表生成 结束 */
		return monthlyInterstCharts;
	}
}
