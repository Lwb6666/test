package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Stock;
import com.dxjr.portal.account.mapper.AccountReportMapper;
import com.dxjr.portal.account.service.AccountDayLogService;
import com.dxjr.portal.account.service.AccountReportService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.service.InvestReportService;
import com.dxjr.portal.account.service.MyAccountReportService;
import com.dxjr.portal.account.util.PercentageUtil;
import com.dxjr.portal.account.vo.AccountInfo;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.Percentage;
import com.dxjr.portal.account.vo.UnReceiveInterestCnd;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.vo.CurAccountVo;
import com.dxjr.portal.stock.mapper.StockMapper;

/**
 * <p>
 * Description:我的帐号业务类<br />
 * </p>
 * 
 * @title MyAccountReportServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
@Service
public class MyAccountReportServiceImpl implements MyAccountReportService {

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountReportService accountReportService;
	@Autowired
	private AccountDayLogService accountDayLogService;
	@Autowired
	private InvestReportService investReportService;
	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private StockMapper stockMapper;
	@Autowired
	private AccountReportMapper accountReportMapper;
	@Autowired
	private CurAccountService curAccountService;

	@Override
	public Map<String, BigDecimal> queryUserAccountDetail(Integer memberId) throws Exception {
		Map<String, BigDecimal> userDetailMap = new HashMap<String, BigDecimal>();
		AccountVo accountVo = accountService.queryAccountByUserId(memberId);
		// 净值额度
		BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(memberId).setScale(2, BigDecimal.ROUND_DOWN);
		userDetailMap.put("netMoneyLimit", netMoneyLimit);
		// 待收总额
		BigDecimal collection = accountVo.getCollection();
		userDetailMap.put("collection", collection);
		// 可用资金
		userDetailMap.put("use_money", accountVo.getUseMoney());
		// 加权待收、日均待收
		BigDecimal dayInterst = accountDayLogService.queryDayAverageCollectionTotal(memberId);
		userDetailMap.put("dayInterst", dayInterst);

		// 定期宝本金总额
		BigDecimal fixCapitalTotal = accountReportService.queryFixTotalByUserId(memberId);
		userDetailMap.put("fixCapitalTotal", fixCapitalTotal);

		// 定期宝已赚总额
		BigDecimal fixInvestYesTotal = accountReportService.queryFixInterestYesByUserId(memberId);
		userDetailMap.put("fixInvestYesTotal", fixInvestYesTotal);

		// 定期宝预期利息
		BigDecimal fixInvestNoTotal = accountReportService.queryFixInterestNoByUserId(memberId);
		userDetailMap.put("fixInvestNoTotal", fixInvestNoTotal);
		// 投标直通车总额
		BigDecimal firstTotal = accountReportService.queryFirstTotalByUserId(memberId);
		userDetailMap.put("firstTotal", firstTotal);
		// 投标直通车可用余额
		BigDecimal firstUseMoney = accountReportService.queryFirstUseMoneyByUserId(memberId);
		userDetailMap.put("firstUseMoney", firstUseMoney);
		// 投标直通车冻结金额
		BigDecimal firstFreezeAccount = accountReportService.queryFirstFreezeAccountByMemberId(memberId);
		userDetailMap.put("firstFreezeAccount", firstFreezeAccount);
		// 投标冻结
		BigDecimal tenderLockAccountTotal = accountReportService.queryLockAccountTotalByMemberId(memberId);
		userDetailMap.put("tenderLockAccountTotal", tenderLockAccountTotal);

		// 债转冻结
		BigDecimal transferLockAccountTotal = accountReportService.queryTransferLockAccountTotalByMemberId(memberId);
		userDetailMap.put("transferLockAccountTotal", transferLockAccountTotal);

		// 提现冻结
		BigDecimal cashLockTotalMoney = accountReportService.queryLockCashTotalByMemberId(memberId);
		userDetailMap.put("cashLockTotalMoney", cashLockTotalMoney);
		/******************* 收益总额开始 ******************************/
		// 已赚利息(利息收入)
		BigDecimal yesInterstTotal = accountReportService.queryYesInterstTotalByMemberId(memberId);
		// 待收利息
		BigDecimal unReceiveInterstTotal = accountReportService.queryUnReceiveInterstTotalByMemberId(memberId);
		// 普通投标 - 待收罚息
		BigDecimal unReceiveInterest = accountReportService.queryUnReceiveLateInterestList(memberId);
		// 已赚奖励
		// 加入红包收入类型 liutao
		String[] awardype = { "web_recharge", "offline_recharge_award", "point_exchange_money", "activity_exchange_money", "web_award", "red_income" };
		BigDecimal awardTotal = accountReportService.queryMoneyByType(memberId, awardype);
		// 已收罚息(罚息收入)
		BigDecimal receiveInterest = accountReportService.queryReceiveInterestByMemberId(memberId);

		// 非vip收取利息
		BigDecimal interestBack = accountReportService.queryInterestBackTotalByMemberId(memberId);

		// 债权转出收益
		BigDecimal transferDiff = accountReportService.queryTransferDiffByMemberId(memberId);

		// 现金行权金额
		BigDecimal stockMoney = new BigDecimal(0);
		List<Stock> list = stockMapper.getByProperty("user_id", memberId + "");
		if (list != null && list.size() > 0 && list.get(0).getStockMoney() != null) {
			stockMoney = list.get(0).getStockMoney();
			// netMoney = netMoney.add(stockMoney);
		}
		// 活期宝累计收益
		BigDecimal curInterestTotal = BigDecimal.ZERO;
		// 活期宝昨日收益
		BigDecimal curInterestYesterday = BigDecimal.ZERO;
		// 活期宝收益总额
		BigDecimal curTotal = BigDecimal.ZERO;
		// 活期宝信息
		CurAccountVo curAccountVo = curAccountService.selectByUserId(memberId);
		if (curAccountVo != null) {
			curInterestTotal = curAccountVo.getInterestTotal();
			curInterestYesterday = curAccountVo.getInterestYesterday();
			curTotal = curAccountVo.getTotal();
		}
		// 定期宝信息

		// 收益总额 = 利息收入 + 罚息收入 + 奖励收入 + 行权金额+ 债权金额(new)+活期宝累计收益+定期宝收益总额
		BigDecimal netMoney = yesInterstTotal.add(receiveInterest).add(awardTotal).add(stockMoney).add(transferDiff).add(curInterestTotal)
				.add(fixInvestYesTotal);

		netMoney = netMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		userDetailMap.put("netMoney", netMoney);

		userDetailMap.put("netMoney_yesInterstTotal", yesInterstTotal.add(interestBack));// 已赚利息
		userDetailMap.put("netMoney_unReceiveInterstTotal", unReceiveInterstTotal);// 待收利息
		userDetailMap.put("netMoney_awardTotal", awardTotal);// 已赚奖励
		userDetailMap.put("netMoney_receiveInterest", receiveInterest);// 已收罚息
		userDetailMap.put("netMoney_unReceiveInterest", unReceiveInterest);// 待收罚息
		userDetailMap.put("netMoney_interestBack", interestBack);
		userDetailMap.put("netMoney_stockMoney", stockMoney);// 行权金额
		userDetailMap.put("transfer_diff", transferDiff);// 债权转出收益
		userDetailMap.put("cur_interest_total", curInterestTotal);// 活期宝累计收益
		userDetailMap.put("cur_interest_yesterday", curInterestYesterday);// 活期宝昨日收益
		userDetailMap.put("cur_total", curTotal);// 活期宝收益总额
		userDetailMap.put("fixInvest_yes_total", fixInvestYesTotal);// 定期宝收益总额

		/******************* 收益总额结束 ******************************/

		/******************* 支出总额开始 *****************************/
		// VIP支出+已付利息管理费+借款管理费+转可提费用
		String[] typeArray = { "vip_cost", "inverest_fee", "borrow_manage_fee", "draw_deduct_fee" };
		BigDecimal totalDetailMoney = accountReportService.queryMoneyByType(memberId, typeArray);
		// 待付利息管理费
		BigDecimal unpayInterest = accountReportService.queryUnPayManageInterstByMemberId(memberId);
		// 已付罚息
		BigDecimal payLateInterest = accountReportService.queryPayLateInterestByMemberId(memberId);
		// 待付罚息
		BigDecimal unPayLateInterest = accountReportService.queryUnPayLateInterestByMemberId(memberId);
		// 提现费用
		BigDecimal cashCost = accountReportService.queryCashCostByMemberId(memberId);
		// 已付利息
		BigDecimal havaPayInterest = accountReportService.queryHavaPayInterestByMemberId(memberId);
		// 待还利息（待付利息）
		BigDecimal waitPayInterest = accountReportService.queryWaitPayInterestByMemberId(memberId);
		// 充值手续费
		BigDecimal totalFeeMoney = accountReportService.queryRechargeFeeTotalByMemberId(memberId);
		// 查询转让管理费
		BigDecimal transferManageFee = accountReportService.queryMoneyByType(memberId, new String[] { "transfer_manage_fee" });
		// 查询转让管理费
		BigDecimal firstTransferManageFee = accountReportService.querySumManageFeeByMemberId(memberId);

		// 债权认购支出
		BigDecimal subscribeTransferDiff = accountReportService.querySubscribeTransferDiffByMemberId(memberId);

		/*
		 * 总的费用支出(新) = 借款利息支出 + 充值费用支出 + 借款管理费支出 + 利息管理费支出 +提现费用支出 + 转可提费用支出 +罚息支出 + VIP费用支出 + 转让管理费支出+债权认购支出
		 */
		BigDecimal payTotal = totalDetailMoney.add(payLateInterest).add(cashCost).add(havaPayInterest).add(totalFeeMoney).add(transferManageFee)
				.add(firstTransferManageFee).add(subscribeTransferDiff);
		userDetailMap.put("payTotal", payTotal);

		// userDetailMap.put("payTotal", payTotal);

		// 支出总额=VIP支出+已付利息管理费+待付利息管理费+借款管理费+已付罚息+待付罚息+提现费用+转可提费用+已付利息+待付利息 +
		// 充值手续费 + 转让管理费
		userDetailMap.put("payTotal_vip_cost", accountReportService.queryMoneyByType(memberId, new String[] { "vip_cost" }));// VIP支出
		userDetailMap.put("payTotal_inverest_fee", accountReportService.queryMoneyByType(memberId, new String[] { "inverest_fee" }));// 已付利息管理费
		userDetailMap.put("payTotal_unpayInterest", unpayInterest);// 待付利息管理费
		userDetailMap.put("payTotal_borrow_manage_fee", accountReportService.queryMoneyByType(memberId, new String[] { "borrow_manage_fee" }));// 借款管理费
		userDetailMap.put("payTotal_payLateInterest", payLateInterest);// 已付罚息
		userDetailMap.put("payTotal_unPayLateInterest", unPayLateInterest);// 待付罚息
		userDetailMap.put("payTotal_cashCost", cashCost);// 提现费用
		userDetailMap.put("payTotal_draw_deduct_fee", accountReportService.queryMoneyByType(memberId, new String[] { "draw_deduct_fee" }));// 转可提费用
		userDetailMap.put("payTotal_havaPayInterest", havaPayInterest);// 已付利息
		userDetailMap.put("payTotal_waitPayInterest", waitPayInterest);// 待付利息
		userDetailMap.put("payTotal_totalFeeMoney", totalFeeMoney);// 充值手续费
		userDetailMap.put("payTotal_transfer_manage_fee", transferManageFee.add(firstTransferManageFee)); // 转让管理费
		userDetailMap.put("payTotal_subscribeTransferDiff", subscribeTransferDiff); // 债权认购支出

		/******************* 支出总额结束 *****************************/

		// 净收益
		BigDecimal netEaring = netMoney.subtract(payTotal);
		userDetailMap.put("netEaring", netEaring);

		// 可提余额
		BigDecimal drawMoney = accountVo.getDrawMoney();
		userDetailMap.put("drawMoney", drawMoney);

		// 不可提金额
		BigDecimal noDrawMoney = accountVo.getNoDrawMoney();
		userDetailMap.put("noDrawMoney", noDrawMoney);

		// 冻结总额
		BigDecimal noUseMoney = accountVo.getNoUseMoney();
		userDetailMap.put("noUseMoney", noUseMoney);

		return userDetailMap;
	}

	@Override
	public Map<String, Object> queryUserInvestDetail(Integer memberId) throws Exception {
		Map<String, Object> userDetailMap = new HashMap<String, Object>();
		// 投资总额
		BigDecimal investTotal = investReportService.queryTenderTotalByMemberId(memberId);
		userDetailMap.put("investTotal", investTotal);
		// 已赚利息
		BigDecimal yesInterstTotal = accountReportService.queryYesInterstTotalByMemberId(memberId);
		userDetailMap.put("yesInterstTotal", yesInterstTotal);
		// 待收利息
		BigDecimal interstTotal = accountReportService.queryUnReceiveInterstTotalByMemberId(memberId);
		userDetailMap.put("interstTotal", interstTotal);
		// 待收排名
		int uncollectedRanking = investReportService.queryUncollectedRankingByUserId(memberId);
		userDetailMap.put("uncollectedRanking", uncollectedRanking);
		// 已付利息管理费
		String[] inverestfeeType = { "inverest_fee" };
		BigDecimal inverestfee = accountReportService.queryMoneyByType(memberId, inverestfeeType);
		userDetailMap.put("inverestfee", inverestfee);
		// 待付利息管理费
		BigDecimal unpayInterest = accountReportService.queryUnPayManageInterstByMemberId(memberId);
		userDetailMap.put("unpayInterest", unpayInterest);
		// 已赚奖励
		String[] awardype = { "web_recharge", "offline_recharge_award", "point_exchange_money", "activity_exchange_money" };
		BigDecimal awardTotal = accountReportService.queryMoneyByType(memberId, awardype);
		userDetailMap.put("awardTotal", awardTotal);
		// 已收罚息
		BigDecimal receiveInterest = accountReportService.queryReceiveInterestByMemberId(memberId);
		userDetailMap.put("receiveInterest", receiveInterest);
		// 普通投标- 待收罚息-去掉直通车的
		BigDecimal unReceiveInterest = accountReportService.queryUnReceiveLateInterestList(memberId);
		userDetailMap.put("unReceiveInterest", unReceiveInterest);
		// 充值总额
		BigDecimal rechangeTotalMoney = investReportService.queryRechargeTotalByMemberId(memberId);
		userDetailMap.put("rechangeTotalMoney", rechangeTotalMoney);
		// 提现总额
		BigDecimal cashTotalMoney = investReportService.queryCashTotalByMemberId(memberId);
		userDetailMap.put("cashTotalMoney", cashTotalMoney);
		// 净充值
		BigDecimal netRechangeTotal = rechangeTotalMoney.subtract(cashTotalMoney);
		userDetailMap.put("netRechangeTotal", netRechangeTotal);

		// 普通标+购买债权）- 代收本金
		BigDecimal pttbDsbj = accountReportService.queryDsbjByMemberId(memberId);
		userDetailMap.put("pttbDsbj", pttbDsbj);
		// System.out.println("------------- pttbDsbj =  " + pttbDsbj);

		// 累计投标次数
		int investTimes = investReportService.queryInvestTimes(memberId);
		// 正在进行的投标数
		int investNowRunning = investReportService.queryInvestCountNowRunning(memberId);

		userDetailMap.put("investTimes", investTimes);
		userDetailMap.put("investNowRunning", investNowRunning);

		return userDetailMap;
	}

	@Override
	public Map<String, Object> queryUserBorrowDetail(Integer memberId) throws Exception {
		Map<String, Object> userDetailMap = new HashMap<String, Object>();
		// 借款总额
		BigDecimal borrowTotal = borrowReportService.queryBorrowTotalByMemberId(memberId);
		userDetailMap.put("borrowTotal", borrowTotal);
		// 待还本金
		BigDecimal unpayCapital = borrowReportService.queryUnpayCapitalByMemberId(memberId);
		userDetailMap.put("unpayCapital", unpayCapital);
		// 待还利息
		BigDecimal waitPayInterest = accountReportService.queryWaitPayInterestByMemberId(memberId);
		userDetailMap.put("waitPayInterest", waitPayInterest);
		// 待还总额
		BigDecimal repaymentAccountTotal = unpayCapital.add(waitPayInterest);
		userDetailMap.put("repaymentAccountTotal", repaymentAccountTotal);
		// 借款管理费
		String[] borrowManageFeeType = { "borrow_manage_fee" };
		BigDecimal borrowManageFee = accountReportService.queryMoneyByType(memberId, borrowManageFeeType);
		userDetailMap.put("borrowManageFee", borrowManageFee);
		// 已还总额
		BigDecimal paidTotal = borrowReportService.queryHavePayTotalByMemberId(memberId);
		userDetailMap.put("paidTotal", paidTotal);
		// 已付利息
		BigDecimal havaPayInterest = accountReportService.queryHavaPayInterestByMemberId(memberId);
		userDetailMap.put("havaPayInterest", havaPayInterest);
		// 已付罚息
		BigDecimal payLateInterest = accountReportService.queryPayLateInterestByMemberId(memberId);
		userDetailMap.put("payLateInterest", payLateInterest);
		// 待付罚息
		BigDecimal unPayLateInterest = accountReportService.queryUnPayLateInterestByMemberId(memberId);
		userDetailMap.put("unPayLateInterest", unPayLateInterest);
		// 提现手续费
		BigDecimal takeCashFee = borrowReportService.queryCashCostByMemberId(memberId);
		userDetailMap.put("takeCashFee", takeCashFee);
		// VIP费用
		String[] vipType = { "vip_cost" };
		BigDecimal vipCost = accountReportService.queryMoneyByType(memberId, vipType);
		userDetailMap.put("vipCost", vipCost);
		// 净值额度
		BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(memberId).setScale(8, BigDecimal.ROUND_DOWN);
		userDetailMap.put("netMoneyLimit", netMoneyLimit);

		// 累计借款次数
		int loanTimes = borrowReportService.queryLoanTimes(memberId);
		// 正在还款
		int loanNowRepaying = borrowReportService.queryLoanNowRepaying(memberId);

		userDetailMap.put("loanTimes", loanTimes);
		userDetailMap.put("loanNowRepaying", loanNowRepaying);

		return userDetailMap;
	}

	@Override
	public AccountInfo queryAccountInfo(UnReceiveInterestCnd unReceiveInterestCnd) throws Exception {
		// 查询rocky_account账户信息
		AccountInfo accountInfo = accountReportMapper.queryRaAccountInfo(unReceiveInterestCnd);
		if (null != accountInfo) {
			// 查询活期宝信息
			AccountInfo curInfo = accountReportMapper.queryCurInfo(unReceiveInterestCnd);
			if (curInfo == null) {
				accountInfo.setCurTotal(BigDecimal.ZERO);
			} else {
				accountInfo.setCurTotal(curInfo.getCurTotal());
			}
			// 查询定期宝信息
			AccountInfo fixInfo = accountReportMapper.queryFixInfo(unReceiveInterestCnd);
			if (fixInfo == null) {
				accountInfo.setFixCapitalTotal(BigDecimal.ZERO);
				accountInfo.setFixInterstTotal(BigDecimal.ZERO);
			} else {
				accountInfo.setFixCapitalTotal(fixInfo.getFixCapitalTotal());
				accountInfo.setFixInterstTotal(fixInfo.getFixInterstTotal());
			}
			// 查询标信息
			AccountInfo borrowInfo = accountReportMapper.queryBorrowInfo(unReceiveInterestCnd);
			if (borrowInfo == null) {
				accountInfo.setBCapitalTotal(BigDecimal.ZERO);
				accountInfo.setBInterstTotal(BigDecimal.ZERO);
			} else {
				accountInfo.setBCapitalTotal(borrowInfo.getBCapitalTotal());
				accountInfo.setBInterstTotal(borrowInfo.getBInterstTotal());
			}
			// 账户总额=基础账户总额+活期账户总额+定期宝总额
			accountInfo.setAccountTotal(accountInfo.getRaTotal().add(accountInfo.getCurTotal())
					.add(accountInfo.getFixCapitalTotal().add(accountInfo.getFixInterstTotal())));
			// 定期宝总额=宝本金+宝利息
			accountInfo.setFixTotal(accountInfo.getFixCapitalTotal().add(accountInfo.getFixInterstTotal()));
			// 待收总额=待收本金+待收利息
			accountInfo.setCollectionTotal(accountInfo.getRaCollectionTotal().add(accountInfo.getFixCapitalTotal()).add(accountInfo.getFixInterstTotal()));
			// 待收本金=标本金+宝本金
			accountInfo.setCollectionCapitalTotal(accountInfo.getFixCapitalTotal().add(accountInfo.getBCapitalTotal()));
			// 待收利息=标利息+宝利息
			accountInfo.setCollectionInterstTotal(accountInfo.getFixInterstTotal().add(accountInfo.getBInterstTotal()));
			// 充值总额
			BigDecimal rechargeTotal = accountReportMapper.queryRechargerecord(unReceiveInterestCnd.getUserId());
			// 提现总额
			BigDecimal cashTotal = accountReportMapper.queryCashrecord(unReceiveInterestCnd.getUserId());
			// 认购债权转让支付利息
			BigDecimal subscribeTransferPayMoney = accountReportMapper.querySubsribeTransferPayMoney(unReceiveInterestCnd.getUserId());
			// 2016.8.8，因债转生成的待收利息不准，导致净收益出现负数，暂时不减待收利息
//			accountInfo.setNetEaring(accountInfo.getAccountTotal().add(cashTotal).subtract(rechargeTotal));

			// 净收益 =账户总额+提现总额-充值总额-待收利息
			accountInfo.setNetEaring(accountInfo.getAccountTotal().add(cashTotal).add(subscribeTransferPayMoney).subtract(rechargeTotal).subtract(accountInfo.getCollectionInterstTotal()));
			if (null != accountInfo.getAccountTotal() && accountInfo.getAccountTotal().compareTo(BigDecimal.ZERO) > 0) {
				// 计算百分比
				Percentage percentage = PercentageUtil.getPercentage(accountInfo.getAccountTotal(), accountInfo.getFixTotal(), accountInfo.getTenderTotal(),
						accountInfo.getCurTotal(), accountInfo.getUseMoneyTotal());
				accountInfo.setPercentage(percentage);
			}
		}
		return accountInfo;
	}
}
