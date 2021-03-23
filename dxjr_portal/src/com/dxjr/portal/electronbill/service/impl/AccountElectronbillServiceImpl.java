package com.dxjr.portal.electronbill.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.electronbill.entity.ElectronBill;
import com.dxjr.portal.electronbill.mapper.AccountElectronbillMapper;
import com.dxjr.portal.electronbill.service.AccountElectronbillService;

@Service
public class AccountElectronbillServiceImpl implements AccountElectronbillService {
	
	@Autowired
	private AccountElectronbillMapper accountElectronbillMapper;

	@Override
	public ElectronBill AccountElectronbill(int userId,String yearMonth) {
		ElectronBill electronBill=new ElectronBill();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		//添加当前年月查询条件
		map.put("yearMonth", yearMonth);
		//当前持有的元宝
		Integer map2=accountElectronbillMapper.myAccmulatepoint(map);
		electronBill.setAccumulatepoints(map2);
		//当前持有的红包
		BigDecimal map3=accountElectronbillMapper.myRedAcc(map);
		electronBill.setRedaccount(map3);
		//累计获得的元宝
		Integer map4=accountElectronbillMapper.sumAccmulatepointIncome(map);
		electronBill.setSumAccumulatepoints(map4);
		//已经使用的元宝
		Integer map5=accountElectronbillMapper.sumAccmulatepointIsUse(map);
		electronBill.setAccumulatepointsIsuse(map5);
		//活期宝累计赚取金额
		BigDecimal map7=accountElectronbillMapper.sumCurIncome(map);
		electronBill.setSumCurIncome(map7);
		//存管标累计已赚取金额
		BigDecimal map8=accountElectronbillMapper.sumCustodyIncome(map);
		electronBill.setSumCustodyIncome(map8);
		//一月宝累计赚取金额 
		map.put("fixLimit", 1);
		BigDecimal map91=accountElectronbillMapper.sumFixIncomeByLimit(map);
		electronBill.setSumFixIncomeOne(map91);
		//三月宝累计赚取金额 
		map.put("fixLimit", 3);
		BigDecimal map93=accountElectronbillMapper.sumFixIncomeByLimit(map);
		electronBill.setSumFixIncomeThree(map93);
		//六月宝累计赚取金额 
		map.put("fixLimit", 6);
		BigDecimal map96=accountElectronbillMapper.sumFixIncomeByLimit(map);
		electronBill.setSumFixIncomeSix(map96);
		//十二月宝累计赚取金额 
		map.put("fixLimit", 12);
		BigDecimal map912=accountElectronbillMapper.sumFixIncomeByLimit(map);
		electronBill.setSumFixIncomeTwelve(map912);
		//非存管标累计已赚取金额
		BigDecimal map10=accountElectronbillMapper.sumNotCustodyIncome(map);
		electronBill.setSumNotCustodyIncomeOne(map10);
		//累计获得的其他奖励
		BigDecimal map11=accountElectronbillMapper.sumOtherIncome(map);
		electronBill.setSumOtherIncome(map11);
		//累计获得的红包
		BigDecimal map12=accountElectronbillMapper.sumRedAccIncome(map);
		electronBill.setSumRedaccount(map12);
		//已经使用的红包
		BigDecimal map13=accountElectronbillMapper.sumRedAccIsUse(map);
		electronBill.setRedaccountIsuse(map13);
		//活期宝当月回收本金
		BigDecimal map6=accountElectronbillMapper.sumTheMonthCurCapital(map);
		electronBill.setTheMonthCurCapital(map6);
		//当月获得的元宝
		Integer map14=accountElectronbillMapper.sumTheMonthAccmulatepointIncome(map);
		electronBill.setTheMonthAccumulatepoints(map14);
		//当月散标收益
		BigDecimal map15=accountElectronbillMapper.sumTheMonthBidIncome(map);
		electronBill.setTheMonthBidIncome(map15);
		//当月提现金额
		BigDecimal map16=accountElectronbillMapper.sumTheMonthCash(map);
		electronBill.setTheMonthCash(map16);
		//当月提现服务费
		BigDecimal map17=accountElectronbillMapper.sumTheMonthCashFee(map);
		electronBill.setTheMonthCashFee(map17);
		//当月活期宝收益 
		BigDecimal map18=accountElectronbillMapper.sumTheMonthCurIncome(map);
		electronBill.setTheMonthCurIncome(map18);
		//当月活期宝投资金额 
		BigDecimal map19=accountElectronbillMapper.sumTheMonthCurInvest(map);
		electronBill.setTheMonthCurInvest(map19);
		//存管标当月已赚取金额和回收本金
		Map<String, Object> map20=accountElectronbillMapper.sumTheMonthCustodyIncomeAndCapital(map);
		electronBill.setTheMonthCustodyIncome(new BigDecimal(map20.get("sumTheMonthCustodyIncome").toString()));
		electronBill.setTheMonthCustodyCapital(new BigDecimal(map20.get("sumTheMonthCustodyCapital").toString()));
		//当月存管投资金额
		BigDecimal map21=accountElectronbillMapper.sumTheMonthCustodyInvest(map);
		electronBill.setTheMonthCustodyInvest(map21);
		//当月定期宝退出服务费
		BigDecimal map22=accountElectronbillMapper.sumTheMonthFixExitFee(map);
		electronBill.setTheMonthFixexitFee(map22);
		//当月定期宝收益 
		BigDecimal map23=accountElectronbillMapper.sumTheMonthFixIncome(map);
		electronBill.setTheMonthFixIncome(map23);
		//一月宝当月赚取金额和回收本金
		map.put("fixLimit", 1);
		Map<String, Object> map241=accountElectronbillMapper.sumTheMonthFixIncomeAndCapitalByLimit(map);
		electronBill.setTheMonthFixIncomeOne(new BigDecimal(map241.get("sumTheMonthFixIncomeByLimit").toString()));
		electronBill.setTheMonthFixCapitalOne(new BigDecimal(map241.get("sumTheMonthFixCapitalByLimit").toString()));
		//三月宝当月赚取金额和回收本金
		map.put("fixLimit", 3);
		Map<String, Object> map243=accountElectronbillMapper.sumTheMonthFixIncomeAndCapitalByLimit(map);
		electronBill.setTheMonthFixIncomeThree(new BigDecimal(map243.get("sumTheMonthFixIncomeByLimit").toString()));
		electronBill.setTheMonthFixCapitalThree(new BigDecimal(map243.get("sumTheMonthFixCapitalByLimit").toString()));
		//六月宝当月赚取金额和回收本金
		map.put("fixLimit", 6);
		Map<String, Object> map246=accountElectronbillMapper.sumTheMonthFixIncomeAndCapitalByLimit(map);
		electronBill.setTheMonthFixIncomeSix(new BigDecimal(map246.get("sumTheMonthFixIncomeByLimit").toString()));
		electronBill.setTheMonthFixCapitalSix(new BigDecimal(map246.get("sumTheMonthFixCapitalByLimit").toString()));
		//十二月宝当月赚取金额和回收本金
		map.put("fixLimit", 12);
		Map<String, Object> map2412=accountElectronbillMapper.sumTheMonthFixIncomeAndCapitalByLimit(map);
		electronBill.setTheMonthFixIncomeTwelve(new BigDecimal(map2412.get("sumTheMonthFixIncomeByLimit").toString()));
		electronBill.setTheMonthFixCapitalTwelve(new BigDecimal(map2412.get("sumTheMonthFixCapitalByLimit").toString()));
		//当月定期宝投资金额 
		BigDecimal map25=accountElectronbillMapper.sumTheMonthFixInvest(map);
		electronBill.setTheMonthFixInvest(map25);
		//一月宝当月新增投资金额
		map.put("fixLimit", 1);
		BigDecimal map261=accountElectronbillMapper.sumTheMonthFixInvestByLimit(map); 
		electronBill.setTheMonthFixInvestOne(map261);
		//三月宝当月新增投资金额 
		map.put("fixLimit", 3);
		BigDecimal map263=accountElectronbillMapper.sumTheMonthFixInvestByLimit(map);
		electronBill.setTheMonthFixInvestThree(map263);
		//六月宝当月新增投资金额 
		map.put("fixLimit", 6);
		BigDecimal map266=accountElectronbillMapper.sumTheMonthFixInvestByLimit(map);
		electronBill.setTheMonthFixInvestSix(map266);
		//十二月宝当月新增投资金额 
		map.put("fixLimit", 12);
		BigDecimal map2612=accountElectronbillMapper.sumTheMonthFixInvestByLimit(map);
		electronBill.setTheMonthFixInvestTwelve(map2612);
		//当月获得抽奖机会
		Integer map27=accountElectronbillMapper.sumTheMonthLottChance(map);
		electronBill.setTheMonthLottchance(map27);
		//非存管标当月已赚取金额和回收本金
		Map<String, Object> map28=accountElectronbillMapper.sumTheMonthNotCustodyIncomeAndCapital(map);
		electronBill.setTheMonthNotCustodyIncome(new BigDecimal(map28.get("sumTheMonthNotCustodyIncome").toString()));
		electronBill.setTheMonthNotCustody(new BigDecimal(map28.get("sumTheMonthNotCustodyCapital").toString()));
		//当月非存管投资金额
		BigDecimal map29=accountElectronbillMapper.sumTheMonthNotCustodyInvest(map);
		electronBill.setTheMonthNotCustodyInvest(map29);
		//当月获得的其他奖励
		BigDecimal map30=accountElectronbillMapper.sumTheMonthOtherIncome(map);
		electronBill.setTheMonthOtherIncome(map30);
		//当月充值金额
		BigDecimal map31=accountElectronbillMapper.sumTheMonthRecharge(map);
		electronBill.setTheMonthRecharge(map31);
		//当月获得的红包
		BigDecimal map32=accountElectronbillMapper.sumTheMonthRedAccIncome(map);
		electronBill.setTheMonthRedaccount(map32);
		//当月债权转让服务费
		BigDecimal map33=accountElectronbillMapper.sumTheMonthTransFee(map);
		electronBill.setTheMonthTransFee(map33);
		//年
		electronBill.setYear(yearMonth.substring(0,4));
		//月
		electronBill.setMonth(yearMonth.substring(4,6));
		//用户id
		electronBill.setUserId(userId);
		//谁创建的
		electronBill.setAddBy(userId);
		//添加时间
		electronBill.setAddTime(new Date());
		electronBill.setRemark(yearMonth+"的电子账单");
		return electronBill;
	}
}

