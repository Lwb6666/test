package com.dxjr.portal.fix.service.impl;

import com.dxjr.base.entity.*;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.configuration.mapper.ConfigurationMapper;
import com.dxjr.portal.fix.mapper.*;
import com.dxjr.portal.fix.service.FixExitService;
import com.dxjr.portal.fix.vo.FixAccountVo;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.fix.vo.FixTenderRealVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Description:定期宝认购明细接口实现类<br />
 * </p>
 * 
 * @title FixTenderDetailServiceImpl.java
 * @package com.dxjr.portal.fix1.service.impl
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
@Service
public class FixExitServiceImpl implements FixExitService {

	private static final String ERROR_CODE = "0";
	private static final String SUCCESS_CODE = "1";

	@Autowired
	private FixTenderRealMapper fixTenderRealMapper;
	@Autowired
	private FixExitMapper fixExitMapper;
	@Autowired
	private FixOperationLogMapper fixOperationLogMapper;
	@Autowired
	private FixBorrowMapper fixBorrowMapper;
	@Autowired
	private FixCollectionrecordMapper fixCollectionrecordMapper;
	@Autowired
	private ConfigurationMapper configurationMapper;
	@Autowired
	private FixAccountMapper fixAccountMapper;
	@Autowired
	private FixAccountLogMapper fixAccountLogMapper;
	@Autowired
	private fixRateMapper fixRateMapper;


	/**
	 * 检查是否符合提前退出条件
	 * @param fixTenderRealId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public MessageBox checkExit(Integer fixTenderRealId) throws Exception{
		return this.checkApply(fixTenderRealId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageBox applyExit(Integer fixTenderRealId,String ip,Integer platform) throws Exception{
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		Integer userId = shiroUser.getUserId();
		// 查询最终认购记录并锁定
		FixTenderRealVo fixTenderRealVo = fixTenderRealMapper.getFixTenderRealByIdForUpdate(fixTenderRealId);
		if (fixTenderRealVo.getStatus() != 0) {
			return MessageBox.build(ERROR_CODE,"定期宝状态已变更，请刷新页面！");
		}
		//1. 校验申请是否通过
		MessageBox checkResult = this.checkApply(fixTenderRealId);
		if(ERROR_CODE.equals(checkResult.getCode())){
			return checkResult;
		}
		//2. 处理申请
		//2.1 新增定期宝提前退出记录
		Map<String,String> resultMap = checkResult.getResult();
		// 查询定期宝账户并锁定
		FixAccountVo fixAccountVo = fixAccountMapper.queryFixAccountByFixBorrowId(Integer.parseInt(resultMap.get("fixId")));
		if (fixAccountVo == null) {
			return MessageBox.build(ERROR_CODE,"定期宝账户不存在");
		}
		FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowByIdForUpdate(Integer.parseInt(resultMap.get("fixId")));
		
		// 定期宝账户扣除申请退出本金
		fixAccountVo.setTotal(fixAccountVo.getTotal().subtract(new BigDecimal(resultMap.get("account"))));
		fixAccountVo.setUseMoney(fixAccountVo.getUseMoney().subtract(new BigDecimal(resultMap.get("account"))));
		FixAccount fixAccount = new FixAccount();
		BeanUtils.copyProperties(fixAccountVo, fixAccount);
		fixAccountMapper.updateFixAccountById(fixAccount);
		
		// 记录定期宝账户变动日志
		FixAccountLog fixAccountLog = new FixAccountLog();
		fixAccountLog.setFixBorrowId(fixAccountVo.getFixBorrowId());
		fixAccountLog.setType(21);  // 硬编码(21:定期宝提前退出申请，扣除本金)
		fixAccountLog.setMoney(new BigDecimal(resultMap.get("account")));
		fixAccountLog.setTotal(fixAccountVo.getTotal());
		fixAccountLog.setUseMoney(fixAccountVo.getUseMoney());
		fixAccountLog.setNoUseMoney(fixAccountVo.getNoUseMoney());
		fixAccountLog.setCollection(fixAccountVo.getCollection());
		fixAccountLog.setCapital(fixAccountVo.getCapital());
		fixAccountLog.setProfit(fixAccountVo.getProfit());
		fixAccountLog.setAdduser(-1);// 硬编码(-1:系统用户)
		fixAccountLog.setAddip("0.0.0.1");
		fixAccountLog.setRemark("定期宝提前退出申请，扣除本金");
		fixAccountLog.setBorrowId(fixAccountVo.getFixBorrowId());
		fixAccountLog.setBorrowName(fixBorrowVo.getName());
		fixAccountLog.setIdType(2);
		fixAccountLogMapper.insertFixAccountLog(fixAccountLog);
		
		//2.2 更新最终认购记录状态
		fixTenderRealMapper.updateStatus(fixTenderRealId,Constants.FIX_TENDER_REAL_STATUS_EXIT);
		
		FixExit fixExit = new FixExit();
		fixExit.setUserId(userId);
		fixExit.setFixTenderRealId(fixTenderRealId);
		fixExit.setAccount(new BigDecimal(resultMap.get("account")));
		fixExit.setUseMoney(new BigDecimal(resultMap.get("useMoney")));
		fixExit.setStatus(Constants.FIX_EXIT_STATUS_PASS);//审核通过

		fixExit.setCalcDays(Integer.valueOf(resultMap.get("calcDays")));
		fixExit.setFixBorrowId(Integer.parseInt(resultMap.get("fixId")));
		fixExit.setFixInterest(new BigDecimal(resultMap.get("fixInterest")));
		fixExit.setFixDays(Integer.parseInt(resultMap.get("fixDays")));
		fixExit.setServiceRate(new BigDecimal(resultMap.get("serviceRate")));
		fixExit.setFee(new BigDecimal(resultMap.get("serviceFee")));
		fixExit.setInterestReal(new BigDecimal(resultMap.get("interestReal")));
		fixExit.setAdduser(userId);
		fixExit.setAddip(ip);
		fixExit.setUpdateuser(userId);
		fixExit.setRemark("定期宝提前退出自动审核通过");
		fixExit.setPlatform(platform);
		fixExit.setFixSuccessTime(DateUtils.parse(resultMap.get("successTimeStr"), DateUtils.YMD_HMS));
		fixExit.setFixApr(new BigDecimal(resultMap.get("fixApr")));
		fixExitMapper.insertFixExit(fixExit);
		//2.3 	新增定期宝提前退出日志记录（t_fix_operation_log）
		FixOperationLog fixOperationLog = new FixOperationLog();
		fixOperationLog.setUserId(userId);
		fixOperationLog.setUserType(1);//前台操作
		fixOperationLog.setFixBorrowId(fixExit.getFixBorrowId());
		fixOperationLog.setFixTenderRealId(fixTenderRealId);
		fixOperationLog.setOperType(21);  //申请退出
		fixOperationLog.setPlatform(platform);
		fixOperationLog.setAddip(ip);
		fixOperationLog.setRemark("定期宝提前退出自动审核通过");
		fixOperationLogMapper.insertFixOperationLog(fixOperationLog);
		return MessageBox.build(SUCCESS_CODE,"申请成功!");
	}

	private MessageBox checkApply(Integer fixTenderRealId) throws Exception{
		ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		Integer userId = shiroUser.getUserId();
		FixTenderRealVo fixTenderRealVo = fixTenderRealMapper.getFixTenderRealById(fixTenderRealId);
		if(fixTenderRealVo == null){
			return MessageBox.build(ERROR_CODE,"记录不存在");
		}
		if(!userId.equals(fixTenderRealVo.getUserId())){
			return MessageBox.build(ERROR_CODE,"当前用户不是认购用户");
		}
		FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowByIdForUpdate(fixTenderRealVo.getFixBorrowId());
		if(fixTenderRealVo.getStatus()!=Constants.FIX_TENDER_REAL_STATUS_LOCK || fixBorrowVo.getStatus()!=Constants.FIX_BORROW_STATUS_SUCCESS_APPROVE_PASS ){
			return MessageBox.build(ERROR_CODE,"定期宝状态非收益中,不能提前退出");
		}
		if(fixBorrowVo.getLockLimit() == 1){
			return MessageBox.build(ERROR_CODE,"一月宝不能提前退出");
		}
		// 去掉时分秒
		Date successDate = DateUtils.formateDate(fixBorrowVo.getSuccessTime(), DateUtils.YMD_DASH);
		Date currentDate = DateUtils.formateDate(new Date(), DateUtils.YMD_DASH);
		int calcDays = DateUtils.dayDiff(currentDate, successDate);
		if(calcDays<=30){
			String dateStr = DateUtils.format(DateUtils.dayOffset(successDate, 31), DateUtils.YMD_DASH);
			return MessageBox.build(ERROR_CODE,"必须满30天才能申请退出，"+dateStr+"可申请退出");
		}
		if(DateUtils.dayDiff(fixBorrowVo.getLockEndtime(), currentDate)<=3){
			return MessageBox.build(ERROR_CODE,"定期宝还款前三天不能提前退出");
		}
		BigDecimal totalApply = fixExitMapper.countTodayApplyExitTotalForUpdate();
		if (configurationMapper.selectByTypeAndValue(20010, String.valueOf(fixTenderRealVo.getUserId())) == null) {
			if(totalApply.add(fixTenderRealVo.getAccount()).compareTo(new BigDecimal(300000)) == 1){
				return MessageBox.build(ERROR_CODE,"当日申请总额不能超过30万");
			}
		}
		int countBorrowById = fixTenderRealMapper.countBorrowById(fixTenderRealId);
		if(countBorrowById>0){
			return MessageBox.build(ERROR_CODE,"该定期宝正在投标中，不能申请退出");
		}
		/*String serviceRate =  Dictionary.getValue(2100, String.valueOf(fixBorrowVo.getLockLimit()));
		if(StringUtils.isBlank(serviceRate)){
			return MessageBox.build(ERROR_CODE,"服务费率异常");
		}*/
		int fixDays = DateUtils.dayDiff(fixBorrowVo.getLockEndtime(), successDate);//定期宝总天数
		// 定期宝类型
		int fixType = 0;
		if (fixBorrowVo.getLockLimit() == 3) { //　三月宝
			fixType = 1;  // 3月宝提前退出
		}else if(fixBorrowVo.getLockLimit() == 6){ // 六月宝
			fixType = 2;  // 6月宝提前退出
		}else if(fixBorrowVo.getLockLimit() == 12){ // 十二月宝
			fixType = 3;  // 12月宝提前退出
		}
		// 提前退出类型
		int signoutType = 0;
		if (DateUtils.monthOffset(successDate, 3).compareTo(currentDate) == 1) {
			signoutType = 1; // 未满3个月
		}else if(DateUtils.monthOffset(successDate, 3).compareTo(currentDate) < 1 && DateUtils.monthOffset(successDate, 6).compareTo(currentDate) == 1){
			signoutType = 2; // 未满6个月
		}else if(DateUtils.monthOffset(successDate, 6).compareTo(currentDate) < 1 && DateUtils.monthOffset(successDate, 12).compareTo(currentDate) == 1){
			signoutType = 3; // 未满12个月
		}
		FixRate fixRate = fixRateMapper.selectByFixTypeAndSignoutType(fixType, signoutType);
		if (fixRate == null || fixRate.getSignoutRate() == null || fixRate.getSignoutFeeRate() == null) {
			return MessageBox.build(ERROR_CODE,"定期宝提前退出费率异常，请联系客服");
		}
		//计算手续费
		BigDecimal serviceFee = fixTenderRealVo.getAccount().multiply(fixRate.getSignoutFeeRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
		//计算利息
		BigDecimal fixInterest = fixTenderRealVo.getAccount().multiply(fixRate.getSignoutRate()).multiply(new BigDecimal(fixBorrowVo.getLockLimit())).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		// 计算实际		
		BigDecimal interestReal = fixInterest.multiply(new BigDecimal(calcDays)).divide(new BigDecimal(fixDays), 2, BigDecimal.ROUND_HALF_UP);//实际利息
		Map<String,String> resultMap = new HashMap<>();
		resultMap.put("fixId",String.valueOf(fixBorrowVo.getId( )));
		resultMap.put("fixDays",String.valueOf(fixDays));
		resultMap.put("fixInterest",fixInterest.toString());
		resultMap.put("calcDays",String.valueOf(calcDays));
		resultMap.put("account",fixTenderRealVo.getAccount().toString());//退出本金
		resultMap.put("accountStr", MoneyUtil.fmtMoneyByDot(fixTenderRealVo.getAccount()));//退出本金
		resultMap.put("serviceRate",fixRate.getSignoutFeeRate().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN).toString());//服务费率
		resultMap.put("serviceFee",serviceFee.toString());//退出手续费
		resultMap.put("serviceFeeStr",MoneyUtil.fmtMoneyByDot(serviceFee));//退出手续费
		resultMap.put("interestReal",interestReal.toString());//退出利息
		resultMap.put("interestRealStr",MoneyUtil.fmtMoneyByDot(interestReal));//退出利息
		BigDecimal realMoney = fixTenderRealVo.getAccount().add(interestReal).subtract(serviceFee);
		resultMap.put("realMoney", realMoney.toString());//最终到账金额
		resultMap.put("realMoneyStr", MoneyUtil.fmtMoneyByDot(realMoney));//最终到账金额
		resultMap.put("successTimeStr", DateUtils.format(fixBorrowVo.getSuccessTime(), DateUtils.YMD_HMS));
		resultMap.put("useMoney",fixTenderRealVo.getUseMoney().toString());//可用余额
		resultMap.put("fixApr", fixRate.getSignoutRate().multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_DOWN).toString());
		return MessageBox.build(SUCCESS_CODE,"该申请可以通过",resultMap);
	}
}
