package com.dxjr.portal.electronbill.mapper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 * <p>
 * Description:我的账户电子账单mapper<br />
 * </p>
 * @title AccountElectronbillMapper.java
 * @package com.dxjr.portal.electronbill.mapper 
 * @author jianxin.chen
 * @version 0.1 2016年8月10日
 */
public interface AccountElectronbillMapper { 
	//当月定期宝收益 
	BigDecimal sumTheMonthFixIncome(Map<String, Object> map);
	//当月活期宝收益 
	BigDecimal sumTheMonthCurIncome(Map<String, Object> map);
	//当月散标收益
	BigDecimal sumTheMonthBidIncome(Map<String, Object> map);
	//当月定期宝投资金额 
	BigDecimal sumTheMonthFixInvest(Map<String, Object> map);
	//当月活期宝投资金额 
	BigDecimal sumTheMonthCurInvest(Map<String, Object> map);
	//当月存管投资金额
	BigDecimal sumTheMonthCustodyInvest(Map<String, Object> map);
	// 当月非存管投资金额
	BigDecimal sumTheMonthNotCustodyInvest(Map<String, Object> map);
	//定期宝累计赚取金额  按定期宝类型区分
	BigDecimal sumFixIncomeByLimit(Map<String, Object> map);
	//定期宝当月赚取金额和回收本金  按定期宝类型区分 
	Map<String, Object> sumTheMonthFixIncomeAndCapitalByLimit(Map<String, Object> map);
	//定期宝当月新增投资金额  按定期宝类型区分
	BigDecimal sumTheMonthFixInvestByLimit(Map<String, Object> map);
	//存管标累计已赚取金额
	BigDecimal sumCustodyIncome(Map<String, Object> map);
	//存管标当月已赚取金额和回收本金
	Map<String, Object> sumTheMonthCustodyIncomeAndCapital(Map<String, Object> map);
	//非存管标累计已赚取金额
	BigDecimal sumNotCustodyIncome(Map<String, Object> map);
	//非存管标当月已赚取金额和回收本金
	Map<String, Object> sumTheMonthNotCustodyIncomeAndCapital(Map<String, Object> map);
	//活期宝累计赚取金额
	BigDecimal sumCurIncome(Map<String, Object> map);
	//活期宝当月回收本金
	BigDecimal sumTheMonthCurCapital(Map<String, Object> map);
	//当月获得的元宝
	Integer sumTheMonthAccmulatepointIncome(Map<String, Object> map);
	//累计获得的元宝
	Integer sumAccmulatepointIncome(Map<String, Object> map);
	//已经使用的元宝
	Integer sumAccmulatepointIsUse(Map<String, Object> map);
	//当前持有的元宝
	Integer myAccmulatepoint(Map<String, Object> map);
	//当月获得的红包
	BigDecimal sumTheMonthRedAccIncome(Map<String, Object> map);
	//累计获得的红包
	BigDecimal sumRedAccIncome(Map<String, Object> map);
	//已经使用的红包
	BigDecimal sumRedAccIsUse(Map<String, Object> map);
	//当前持有的红包
	BigDecimal myRedAcc(Map<String, Object> map);
	//当月获得的其他奖励
	BigDecimal sumTheMonthOtherIncome(Map<String, Object> map);
	//累计获得的其他奖励
	BigDecimal sumOtherIncome(Map<String, Object> map);
	//当月获得抽奖机会
	Integer sumTheMonthLottChance(Map<String, Object> map);
	//当月充值金额
	BigDecimal sumTheMonthRecharge(Map<String, Object> map);
	//当月提现金额
	BigDecimal sumTheMonthCash(Map<String, Object> map);
	//当月提现服务费
	BigDecimal sumTheMonthCashFee(Map<String, Object> map);
	//当月债权转让服务费
	BigDecimal sumTheMonthTransFee(Map<String, Object> map);
	//当月定期宝退出服务费
	BigDecimal sumTheMonthFixExitFee(Map<String, Object> map);
}