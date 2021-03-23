package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.CashRecord;
import com.dxjr.base.entity.CashRecordlog;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseCashRecordMapper;
import com.dxjr.base.vo.WebAwardVo;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.mapper.CashRecordMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountReportService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.service.CashRecordService;
import com.dxjr.portal.account.service.CashRecordlogService;
import com.dxjr.portal.account.service.InvestReportService;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.util.UserNetRepayMoneyTotal;
import com.dxjr.portal.account.util.UserNetValue;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;
import com.dxjr.portal.account.vo.TakeCashMoneyVo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:提现记录业务实现类<br />
 * </p>
 * 
 * @title CashRecordServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年6月18日
 */
@Service
public class CashRecordServiceImpl implements CashRecordService {

	@Autowired
	private BaseCashRecordMapper baseCashRecordMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private CashRecordMapper cashRecordMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private InvestReportService investReportService;
	@Autowired
	private AccountReportService accountReportService;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private AccountNetValueMapper netValueMapper;
	@Autowired
	private AccountNetValueMapper accountNetValueMapper;
	@Autowired
	private CashRecordlogService cashRecordlogService;
	@Autowired
	private VipLevelService vipLevelService;

	@Override
	public CashRecordVo queryCashRecordVoByCnd(CashRecordCnd cashRecordCnd) throws Exception {
		List<CashRecordVo> list = cashRecordMapper.queryCashRecordList(cashRecordCnd);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page queryPageListByCnd(CashRecordCnd cashRecordCnd, Page page) throws Exception {
		int totalCount = cashRecordMapper.queryCashRecordCount(cashRecordCnd);
		page.setTotalCount(totalCount);
		List<CashRecordVo> list = cashRecordMapper.queryCashRecordList(cashRecordCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public String saveCancelCash(CashRecordCnd cashRecordCnd, HttpServletRequest request) throws Exception {
		String result = "success";
		if (null == cashRecordCnd.getId() || null == cashRecordCnd.getUserId()) {
			return "取消提现参数错误";
		}
		// 锁定记录
		cashRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		CashRecordVo cashRecordVo = this.queryCashRecordVoByCnd(cashRecordCnd);
		if (null == cashRecordVo || cashRecordVo.getStatus() != Constants.CASH_RECORD_STATUS_APPLYING) {
			return "提现记录未找到或状态已变更,请刷新数据或稍后重试！";
		}
		// 更新提现记录为取消提现
		CashRecord cashRecord = this.updateCashStatusByCancel(cashRecordCnd, cashRecordVo);
		// 解冻账户资金
		AccountVo accountVo = uncoldAccount(cashRecordCnd, cashRecord);
		// 插入取消提现资金记录
		accountLogService.saveAccountLogByParams(accountVo, accountVo.getUserId(), cashRecord.getTotal(), "取消提现", HttpTookit.getRealIpAddr(request),
				"cancel_cash", null, null, null);
		// 判断用户的可提金额是否大于净值额度，如果大于，转入受限金额
		saveDrawToNoDraw(cashRecordCnd, request);

		// 插入取消提现操作日志
		packageCashrecordlogByCancel(request, cashRecordVo);
		return result;
	}

	@Override
	public BigDecimal queryCanTakeMoneyTotalByMemberId(Integer memberId) throws Exception {
		// 充值总额
		BigDecimal rechangeTotalMoney = investReportService.queryRechargeTotalByMemberId(memberId);
		// 提现总额
		BigDecimal cashTotalMoney = investReportService.queryCashTotalByMemberId(memberId);
		// 已赚利息
		BigDecimal yesInterstTotal = accountReportService.queryYesInterstTotalByMemberId(memberId);
		// 已赚奖励
		String[] awardype = { "web_recharge", "offline_recharge_award", "point_exchange_money", "activity_exchange_money" };
		BigDecimal awardTotal = accountReportService.queryMoneyByType(memberId, awardype);
		// 15天内充值总额
		String date = String.valueOf(DateUtils.parse(
				DateUtils.format(DateUtils.dayOffset(new Date(), -15), DateUtils.YMD_HMS).substring(0, 10) + " 00:00:00", DateUtils.YMD_HMS)
				.getTime());
		BigDecimal rechangeMoneyByDay = rechargeRecordService.queryRechargeTotalByMemberIdAndDay(memberId, date.substring(0, date.length() - 3));
		// 提现冻结
		BigDecimal cashLockTotalMoney = accountReportService.queryLockCashTotalByMemberId(memberId);
		return rechangeTotalMoney.add(yesInterstTotal).add(awardTotal).subtract(cashTotalMoney).subtract(cashLockTotalMoney)
				.subtract(rechangeMoneyByDay);
	}

	@Override
	public String saveTakeCash(TakeCashMoneyVo takeCashMoneyVo, MemberVo memberVo, HttpServletRequest request) throws Exception {
		String result = "success";
		// 验证提现的数据是否正确
		result = validateTakeCashData(takeCashMoneyVo, memberVo);
		if (!"success".equals(result)) {
			return result;
		}
		BankInfoVo bankInfoVo = bankInfoService.queryBankInfoByUserIdPrivateKey(memberVo.getId(), takeCashMoneyVo.getBankInfoId());
		if (null == bankInfoVo || bankInfoVo.getStatus().equals(-1)) {
			return "请您先添加银行卡！";
		}
		// 当前帐号信息并锁定
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(memberVo.getId());
		// 保留两位小数
		BigDecimal takeMoney = new BigDecimal(takeCashMoneyVo.getTakeMoney()).setScale(2, RoundingMode.DOWN);
		// 比较提现金额是否大于可用余额
		if (accountVo.getUseMoney().compareTo(BusinessConstants.TAKE_MONEY_MIN) == -1 || accountVo.getUseMoney().compareTo(takeMoney) == -1) {
			return "您的可用余额不足！";
		}
		// 获取最大可提金额
		BigDecimal minDrawMoney = getMaxDrawMoney(memberVo.getId());
		
		if (accountVo.getDrawMoney().compareTo(BusinessConstants.TAKE_MONEY_MIN) == -1 || accountVo.getDrawMoney().compareTo(takeMoney) == -1) {
			return "您的可提余额不足，最大可提金额为：" + minDrawMoney + "元！";
		}
		//获取渠道类型
		BigDecimal rewardMoney=BigDecimal.ZERO;
		int channelType=getChannelType(memberVo.getSource().intValue());
		if(channelType!=0){
			WebAwardVo webAwardVo=new WebAwardVo();
			webAwardVo.setUserId(memberVo.getId());
			webAwardVo.setType(channelType);			
			rewardMoney=baseAccountMapper.queryAwardMoney(webAwardVo);
		}		
		// 判断阿里摇61可是否提现金额30元
		if(judgeALiYao(memberVo,minDrawMoney,takeMoney,rewardMoney)){
			return "根据阿里摇活动规则，需要投资满10000以上，且标/宝还款后，才能提现活动奖励"+rewardMoney+"元，所以您的实际可提最大金额为：" + minDrawMoney.subtract(rewardMoney) + "元！";
		}
		// 判断各渠道奖励可提现金额
		if(judgeAllChannel(memberVo,minDrawMoney,takeMoney,rewardMoney)){
			return "您需要投资满1000以上，且标/宝还款后，才能提现活动奖励"+rewardMoney+"元，所以您的实际可提最大金额为：" + minDrawMoney.subtract(rewardMoney) + "元！";
		}
		// 取得用户的净值额度
		UserNetValue netValue = new UserNetValue();
		netValue.setUserid(memberVo.getId());
		netValueMapper.callGetUserNetMoneyLimit(netValue);
		if (netValue.getNetMoneyLimit().compareTo(BusinessConstants.TAKE_MONEY_MIN) == -1 || netValue.getNetMoneyLimit().compareTo(takeMoney) == -1) {
			return "您的净值额度不足，最大可提金额为：" + minDrawMoney + "元！";
		}
		// 待收利息
		BigDecimal unReceiveInterstTotal = accountReportService.queryUnReceiveInterstTotalByMemberId(memberVo.getId());
		// 提现冻结
		BigDecimal cashLockTotalMoney = accountReportService.queryLockCashTotalByMemberId(memberVo.getId());
		// 提现金额不能大于 (资产总额 - 待还总额 - 提现冻结 - 待收利息)
		BigDecimal remainTakeMoney = accountVo.getTotal().subtract(netValue.getRepaymentAccountTotal()).subtract(cashLockTotalMoney)
				.subtract(unReceiveInterstTotal);

		UserNetRepayMoneyTotal userNetRepayMoneyTotal = new UserNetRepayMoneyTotal();
		userNetRepayMoneyTotal.setUserid(memberVo.getId());
		netValueMapper.callGetUserNetRepayMoneyTotal(userNetRepayMoneyTotal);
		// 提现金额不能大于 (资产总额 - 待还总额 - 提现冻结 - 待收利息 - 预还净值标本息- 预扣借款管理费)
		BigDecimal remainMoney = remainTakeMoney.subtract(userNetRepayMoneyTotal.getNetRepayMoneyTotal()).subtract(
				userNetRepayMoneyTotal.getManagerFeeTotal());
		if (takeMoney.compareTo(remainMoney) == 1) {
			return "您的提现金额超过(资产总额 - 待还总额 - 提现冻结 - 待收利息 - 预还净值标本息- 预扣借款管理费)，最大可提金额为：" + minDrawMoney + "元！";
		}

		// 对申请金额以每5万元进行拆分为多笔提现
		BigDecimal[] moneyArray = takeMoney.divideAndRemainder(BusinessConstants.TAKE_MONEY_EVERY_MAX);
		if (moneyArray[1].compareTo(new BigDecimal("0")) == 1 && moneyArray[1].compareTo(BusinessConstants.TAKE_MONEY_FEE) != 1) {
			return "尾数提现金额必须大于2元,请确认!";
		}

		// 每笔都是5W
		BigDecimal everyTakeMoney = BusinessConstants.TAKE_MONEY_EVERY_MAX;
		// 5w的笔数
		int take5WCount = moneyArray[0].intValue();
		// 这里是小于等于，包括了最后一笔提现的尾数，索引从0开始
		for (int i = 0; i <= take5WCount; i++) {
			// 如果是最后一笔,为剩余金额
			if (i == take5WCount) {
				// 如果最后一笔金额大于0
				if (moneyArray[1].compareTo(new BigDecimal("0")) == 1) {
					everyTakeMoney = moneyArray[1];
				} else {
					// 忽略最后一笔
					break;
				}
			}
			// 冻结用户资金
			Account account = coldTakeCashAccount(accountVo, everyTakeMoney);
			// 插入提现申请资金记录
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), everyTakeMoney, "提现申请已经提交", HttpTookit.getRealIpAddr(request),
					"cash_cold", null, null, null);
			// 计算手续费
			BigDecimal fee = BusinessConstants.TAKE_MONEY_FEE;
			// 判断该用户是否是终身顶级会员
			if (vipLevelService.getIsSvipByUserId(memberVo.getId())) {
				fee = BigDecimal.ZERO;
			}
			// 当月内已免费的提现次数
			Integer getCashedCount = this.getCashedCount(memberVo.getId(), new Date());
			if (getCashedCount < 3) {
				fee = BigDecimal.ZERO;
			}
			// 插入提现记录
			this.addCashRecord(bankInfoVo, everyTakeMoney, fee, request);
		}
		return result;
	}
    /**
     *  
     * <p>
     * Description:判断顶玺金融阿里摇规则说明<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年2月16日
     * @param accountVo
     * @param memberVo
     * @param maxDrawMoney
     * @param takeMoney
     * @return
     * @throws Exception
     * boolean
     */
	private boolean judgeALiYao(MemberVo memberVo,BigDecimal maxDrawMoney,BigDecimal takeMoney,BigDecimal rewardMoney) throws Exception {
		
		if(memberVo.getSource().intValue()!=61){
			return false;//直接提现
		}
		BigDecimal repayment=baseAccountMapper.queryRepaymentTotal(memberVo.getId());
		if(memberVo.getSource().intValue()==61 && repayment.compareTo(new BigDecimal(10000))>=0){
			return false;//直接提现
		}
		if(memberVo.getSource().intValue()==61 && repayment.compareTo(new BigDecimal(10000))<0 
			&& maxDrawMoney.subtract(takeMoney).compareTo(rewardMoney)>=0){
			return false;//直接提现
		}
		return true;//不能提现
	}
	
	/**
	 * 判断各渠道可提现金额
	 * @author WangQianJin
	 * @title @param accountVo
	 * @title @param memberVo
	 * @title @param maxDrawMoney
	 * @title @param takeMoney
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年3月2日
	 */
	private boolean judgeAllChannel(MemberVo memberVo,BigDecimal maxDrawMoney,BigDecimal takeMoney,BigDecimal rewardMoney) throws Exception {
		//网站奖励各渠道
		if(memberVo.getSource().intValue()!=32 && memberVo.getSource().intValue()!=40 && memberVo.getSource().intValue()!=41 
				&& memberVo.getSource().intValue()!=42 && memberVo.getSource().intValue()!=43 && memberVo.getSource().intValue()!=44 
				&& memberVo.getSource().intValue()!=45 && memberVo.getSource().intValue()!=58){
			return false;//直接提现
		}
		BigDecimal repayment=baseAccountMapper.queryRepaymentTotal(memberVo.getId());
		if(memberVo.getSource().intValue()==32 || memberVo.getSource().intValue()==40 || memberVo.getSource().intValue()==41 
				|| memberVo.getSource().intValue()==42 || memberVo.getSource().intValue()==43 || memberVo.getSource().intValue()==44 
				|| memberVo.getSource().intValue()==45 || memberVo.getSource().intValue()==58){
			if(repayment.compareTo(new BigDecimal(1000))>=0){
				return false;//直接提现
			}	
			if(repayment.compareTo(new BigDecimal(1000))<0 && maxDrawMoney.subtract(takeMoney).compareTo(rewardMoney)>=0){
				return false;//直接提现
			}
		}		
		return true;//不能提现
	}
	
	/**
	 * 获取渠道类型
	 * @author WangQianJin
	 * @title @param source
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年3月3日
	 */
	private int getChannelType(int source) throws Exception {
		int type=0;
		if(source==32){
			type=101010;
		}else if(source==40){
			type=101011;
		}else if(source==41){
			type=101012;
		}else if(source==42){
			type=101013;
		}else if(source==43){
			type=101014;
		}else if(source==44){
			type=101015;
		}else if(source==45){
			type=101016;
		}else if(source==58){
			type=101017;
		}else if(source==61){
			type=101018;
		}		
		return type;
	}
    
	/**
	 * 
	 * <p>
	 * Description:获取可提金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月28日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal getMaxDrawMoney(Integer userId) throws Exception {
		BigDecimal minDrawMoney = new BigDecimal(0);
		AccountVo accountVo = accountService.queryAccountByUserId(userId);
		minDrawMoney = accountVo.getDrawMoney();
		// 取得用户的净值额度
		UserNetValue netValue = new UserNetValue();
		netValue.setUserid(userId);
		netValueMapper.callGetUserNetMoneyLimit(netValue);
		minDrawMoney = minDrawMoney.compareTo(netValue.getNetMoneyLimit()) == -1 ? minDrawMoney : netValue.getNetMoneyLimit();

		// 待收利息
		BigDecimal unReceiveInterstTotal = accountReportService.queryUnReceiveInterstTotalByMemberId(userId);
		// 提现冻结
		BigDecimal cashLockTotalMoney = accountReportService.queryLockCashTotalByMemberId(userId);
		// 提现金额不能大于 (资产总额 - 待还总额 - 提现冻结 - 待收利息)
		BigDecimal remainTakeMoney = accountVo.getTotal().subtract(netValue.getRepaymentAccountTotal()).subtract(cashLockTotalMoney)
				.subtract(unReceiveInterstTotal);
		UserNetRepayMoneyTotal userNetRepayMoneyTotal = new UserNetRepayMoneyTotal();
		userNetRepayMoneyTotal.setUserid(userId);
		netValueMapper.callGetUserNetRepayMoneyTotal(userNetRepayMoneyTotal);
		// 提现金额不能大于 (资产总额 - 待还总额 - 提现冻结 - 待收利息 - 预还净值标本息- 预扣借款管理费)
		BigDecimal remainMoney = remainTakeMoney.subtract(userNetRepayMoneyTotal.getNetRepayMoneyTotal()).subtract(
				userNetRepayMoneyTotal.getManagerFeeTotal());
		minDrawMoney = minDrawMoney.compareTo(remainMoney) == -1 ? minDrawMoney : remainMoney;
		return minDrawMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * <p>
	 * Description:增加提现记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param bankInfoVo
	 * @param money
	 * @param fee
	 * @param request
	 * @return CashRecord
	 */
	private CashRecord addCashRecord(BankInfoVo bankInfoVo, BigDecimal money, BigDecimal fee, HttpServletRequest request) throws Exception {
		CashRecord cashRecord = new CashRecord();
		cashRecord.setAccount(bankInfoVo.getCardNum());
		cashRecord.setAddtime(DateUtils.getTime());
		cashRecord.setAddip(HttpTookit.getRealIpAddr(request));
		cashRecord.setBank(bankInfoVo.getBankName());
		cashRecord.setBranch(bankInfoVo.getBranch());
		cashRecord.setCnapsCode(bankInfoVo.getCnapsCode());
		cashRecord.setUserId(bankInfoVo.getUserId());
		cashRecord.setStatus(Constants.CASH_RECORD_STATUS_APPLYING);
		cashRecord.setTotal(money);
		cashRecord.setFee(fee);
		cashRecord.setCredited(money.subtract(fee));
		cashRecord.setVersion(1);
		cashRecord.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		baseCashRecordMapper.insertEntity(cashRecord);
		return cashRecord;
	}

	/**
	 * <p>
	 * Description:冻结用户资金<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param accountVo
	 * @param takeMoney
	 * @return
	 * @throws Exception
	 * @throws AppException
	 *             Account
	 */
	private Account coldTakeCashAccount(AccountVo accountVo, BigDecimal takeMoney) throws Exception, AppException {
		accountVo.setUseMoney(accountVo.getUseMoney().subtract(takeMoney));
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().add(takeMoney));
		accountVo.setDrawMoney(accountVo.getDrawMoney().subtract(takeMoney));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		baseAccountMapper.updateEntity(account);
		return account;
	}

	/**
	 * <p>
	 * Description:验证提现的数据是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param takeCashMoneyVo
	 * @param memberVo
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String validateTakeCashData(TakeCashMoneyVo takeCashMoneyVo, MemberVo memberVo) throws Exception {
		String result = "success";
		if (memberVo.getIsFinancialUser() == Constants.MEMBER_BORROW_USER) {
			return "您是借款用户，不能申请提现！";
		}
		if (null == takeCashMoneyVo.getTakeMoney() || "".equals(takeCashMoneyVo.getTakeMoney().trim())) {
			return "请填写提现金额！";
		}
		if (!takeCashMoneyVo.getTakeMoney().matches("^-?\\d+\\.\\d+$") && !takeCashMoneyVo.getTakeMoney().matches("^\\d+$")) {
			return "金额输入不合法！";
		}
		if (null == takeCashMoneyVo.getPaypassword() || !MD5.toMD5(takeCashMoneyVo.getPaypassword()).equals(memberVo.getPaypassword())) {
			return "您的交易密码输入错误！【如果您登录后修改过交易密码，请重新登录后再试！】";
		}
		BigDecimal takeMoney = new BigDecimal(takeCashMoneyVo.getTakeMoney());
		if (takeMoney.compareTo(BusinessConstants.TAKE_MONEY_MAX) == 1 || takeMoney.compareTo(BusinessConstants.TAKE_MONEY_MIN) == -1) {
			return "单笔提现金额下限" + BusinessConstants.TAKE_MONEY_MIN + "，上限" + BusinessConstants.TAKE_MONEY_MAX + "！";
		}
		if (memberVo.getIsFinancialUser() == Constants.MEMBER_FINANCIAL_USER) {
			Integer laterCount = cashRecordMapper.queryRepaymentlaterCountForJZ(memberVo.getId());
			if (null != laterCount && laterCount > 0) { // 有逾期未还款的净值标
				return "您还有" + laterCount + "笔逾期未还款的记录，不能申请提现！";
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Description:解冻用户资金<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @param cashRecord
	 * @return
	 * @throws Exception
	 * @throws AppException
	 *             Account
	 */
	private AccountVo uncoldAccount(CashRecordCnd cashRecordCnd, CashRecord cashRecord) throws Exception, AppException {
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(cashRecordCnd.getUserId());
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().subtract(cashRecord.getTotal()));
		accountVo.setUseMoney(accountVo.getUseMoney().add(cashRecord.getTotal()));
		accountVo.setDrawMoney(accountVo.getDrawMoney().add(cashRecord.getTotal()));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		baseAccountMapper.updateEntity(account);
		return accountVo;
	}

	/**
	 * <p>
	 * Description:更新提现记录为取消提现<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @param cashRecordVo
	 * @return
	 * @throws Exception
	 * @throws AppException
	 *             CashRecord
	 */
	private CashRecord updateCashStatusByCancel(CashRecordCnd cashRecordCnd, CashRecordVo cashRecordVo) throws Exception, AppException {
		CashRecord cashRecord = new CashRecord();
		cashRecordVo.setStatus(Constants.CASH_RECORD_STATUS_CANCEL_CASH);
		cashRecordVo.setVerifyUserid(cashRecordCnd.getUserId());
		cashRecordVo.setVerifyTime(DateUtils.getTime());
		cashRecordVo.setVerifyRemark("取消提现");
		BeanUtils.copyProperties(cashRecordVo, cashRecord);
		baseCashRecordMapper.updateEntity(cashRecord);
		return cashRecord;
	}

	/**
	 * <p>
	 * Description:取消提现：判断用户的可提金额是否大于净值额度，如果大于，转入受限金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月25日
	 * @param cashRecordCnd
	 * @param request
	 * @throws Exception
	 *             void
	 */
	private void saveDrawToNoDraw(CashRecordCnd cashRecordCnd, HttpServletRequest request) throws Exception {
		DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd = new DrawMoneyToNoDrawCnd();
		drawMoneyToNoDrawCnd.setUserid(cashRecordCnd.getUserId());
		drawMoneyToNoDrawCnd.setNetmoneytype(BusinessConstants.NET_TYPE_CASH_CANCEL);
		drawMoneyToNoDrawCnd.setAddip(HttpTookit.getRealIpAddr(request));
		drawMoneyToNoDrawCnd.setAccountlogType("net_draw_to_nodraw_cash_cancel");
		drawMoneyToNoDrawCnd.setAccountlogRemark("取消提现之后,可提金额大于净值额度，可提金额转入受限金额。");
		accountNetValueMapper.dealDrawmoneyToNodraw(drawMoneyToNoDrawCnd);
	}

	/**
	 * <p>
	 * Description:取消提现时插入提现操作日志<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年11月10日
	 * @param request
	 * @param cashRecordVo
	 * @throws Exception
	 *             void
	 */
	private void packageCashrecordlogByCancel(HttpServletRequest request, CashRecordVo cashRecordVo) throws Exception {
		CashRecordlog cashRecordlog = new CashRecordlog();
		cashRecordlog.setAddip(HttpTookit.getRealIpAddr(request));
		cashRecordlog.setAddtime(new Date());
		cashRecordlog.setAddUserId(cashRecordVo.getUserId());
		cashRecordlog.setCashrecordId(cashRecordVo.getId());
		cashRecordlog.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		cashRecordlog.setType(Constants.CASHRECORD_LOG_TYPE_CANCEL);
		cashRecordlogService.saveCashRecordlog(cashRecordlog);
	}

	@Override
	public BigDecimal queryCashRecordTotalByCnd(CashRecordCnd cashRecordCnd) throws Exception {
		return cashRecordMapper.queryCashRecordTotalByCnd(cashRecordCnd);
	}

	@Override
	public Integer getCashedCount(Integer userId, Date date) throws Exception {
		if (date.compareTo(DateUtils.parse("2015-07-01", DateUtils.YMD_DASH)) >= 0) {
			return cashRecordMapper.getCashedCount(userId, DateUtils.firstDay(date), DateUtils.lastDay(date));
		} else {
			return 3;
		}
	}
}
