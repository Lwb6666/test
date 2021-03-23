package com.dxjr.portal.lianlian.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountRechargeFeedback;
import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseAccountRechargeFeedbackMapper;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.base.mapper.BaseSystemMessageMapper;
import com.dxjr.base.mapper.BaseSystemMessageTemplateMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.util.BankUtil;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.lianlian.service.LianlianpayService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LianlianPaymentRequest;
import com.dxjr.portal.member.mapper.BankInfoMapper;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:新浪支付service<br />
 * </p>
 * 
 * @title SinapayServiceImpl.java
 * @package com.dxjr.portal.sinapay.service.impl
 * @author justin.xu
 * @version 0.1 2014年6月10日
 */
@Service
public class LianlianpayServiceImpl implements LianlianpayService {
	@Autowired
	private BaseSystemMessageTemplateMapper baseSystemMessageTemplateMapper;
	@Autowired
	private BaseSystemMessageMapper baseSystemMessageMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseRechargeRecordMapper baseRechargeRecordMapper;
	@Autowired
	private BaseAccountRechargeFeedbackMapper baseAccountRechargeFeedbackMapper;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BankInfoMapper bankInfoMapper;

	@Override
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, String ip, String realPath) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 保留两位小数
		topupMoneyVo.setMoney(topupMoneyVo.getMoney().setScale(2, RoundingMode.DOWN));
		// 验证银行信息是否合法
		String result = validateBankname(topupMoneyVo, resultMap);
		if (!"success".equals(result)) {
			resultMap.put("result", result);
			return resultMap;
		}
		// 最多充值金额100万
		if (topupMoneyVo.getMoney().compareTo(new BigDecimal("1000000")) == 1 || topupMoneyVo.getMoney().compareTo(new BigDecimal("2")) == -1) {
			resultMap.put("result", "充值金额必须小于100万元并且不小于2元");
			return resultMap;
		}

		// 封装支付表单
		LianlianPaymentRequest paymentInfo = this.packageLianlianPayForm(userId, topupMoneyVo, ip);
		String lianlianPayRequestString = LLPayUtil.generateFormString(paymentInfo, BusinessConstants.ONLINE_PAY_LIANLIANPAY_ACTIONURL);
		// 保存充值记录
		String bankName = BankUtil.getLianlianpayBankNameByCode(topupMoneyVo.getBankcode());
		this.packageRechargeRecord(userId, topupMoneyVo, ip, bankName, paymentInfo);

		resultMap.put("lianlianPayRequestString", lianlianPayRequestString);
		return resultMap;
	}

	@Override
	public String saveAutoReceive(LianlianPayDataResponse lianlianPayDataResponse, String ip) throws Exception {
		String result = "success";
		// 找到充值记录并锁定
		RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
		rechargeRecordCnd.setTradeNo(lianlianPayDataResponse.getNo_order());
		rechargeRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
		// 如果不是待付款 或金额不一致,新浪支付返回的钱是以分为单位
		if (null == rechargeRecordVo || rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT
				|| rechargeRecordVo.getMoney().compareTo(new BigDecimal(lianlianPayDataResponse.getMoney_order())) != 0) {
			return "PayERROR";// 支付失败
		}

		BigDecimal fee = new BigDecimal("0");
		// 手续费千分之二,最低1分钱
		// BigDecimal fee = new BigDecimal("0.01");
		// BigDecimal chargeFee = rechargeRecordVo.getMoney()
		// .divide(new BigDecimal(1000)).setScale(4, RoundingMode.DOWN)
		// .multiply(new BigDecimal(2));
		// if (chargeFee.compareTo(fee) == 1) {
		// fee = chargeFee;
		// }
		// fee = fee.setScale(2, RoundingMode.DOWN);
		// 支付成功，更新充值记录的状态为成功
		refreshRechargeStatus(rechargeRecordVo);
		AccountVo sourceAccount = accountService.queryAccountByUserIdForUpdate(rechargeRecordVo.getUserId());
		// 更新用户帐号的可用余额和总金额，得到扣除手续费后的总金额
		refreshAccountMoney(rechargeRecordVo, fee, sourceAccount);
		// 未扣除手续前的总金额
		sourceAccount.setUseMoney(sourceAccount.getUseMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setNoDrawMoney(sourceAccount.getNoDrawMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setTotal(sourceAccount.getTotal().add(rechargeRecordVo.getMoney()));
		// 插入资金记录
		StringBuilder remarkbuilder = new StringBuilder("在线充值成功,本次使用的是".concat(Constants.ONLINE_TYPE_LIANLIANPAY_NAME).concat("进行充值."));
		accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(), rechargeRecordVo.getMoney(), remarkbuilder.toString(), ip, "online_recharge", null, null, null);
		// 插入手续费资金记录
		// packageAccountlog(request, rechargeRecordVo, updateAccount, fee);
		// 手机支付更新协议号
		if (null != lianlianPayDataResponse.getPay_type() && lianlianPayDataResponse.getPay_type().equals("D")) {
			if (null != lianlianPayDataResponse.getNo_agree() && !"".equals(lianlianPayDataResponse.getNo_agree().trim())) {
				bankInfoMapper.updateNoAgreeByUserId(rechargeRecordVo.getUserId(), lianlianPayDataResponse.getNo_agree());
			}
		}

		return result;
	}

	@Override
	public void savePackageAccountRechargeFeedback(LianlianPayDataResponse lianlianPayDataResponse) throws Exception {
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(lianlianPayDataResponse.getNo_order());
		rechargeFeedback.setPaymode(BankUtil.getLianlianpayBankNameByCode(lianlianPayDataResponse.getBank_code()));
		rechargeFeedback.setPaystatus(lianlianPayDataResponse.getResult_pay());
		rechargeFeedback.setPaystring(lianlianPayDataResponse.getResult_pay());

		// 如果不是待付款 或金额不一致,新浪支付返回的钱是以分为单位
		rechargeFeedback.setAmount(lianlianPayDataResponse.getMoney_order());
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(null);
		rechargeFeedback.setRemark1(null);
		rechargeFeedback.setRemark2(null);
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_LIANLIANAPAY);
		baseAccountRechargeFeedbackMapper.insertEntity(rechargeFeedback);
	}

	/**
	 * <p>
	 * Description:验证银行信息是否合法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月28日
	 * @param topupMoneyVo
	 * @param resultMap
	 * @return String
	 */
	private String validateBankname(TopupMoneyVo topupMoneyVo, Map<String, Object> resultMap) throws Exception {
		String result = "success";
		// 验证是否是支持的银行
		if (null == topupMoneyVo.getPayChannel() || !(topupMoneyVo.getPayChannel().equals("debit") || topupMoneyVo.getPayChannel().equals("credit"))) {
			return "充值渠道不能为空！";
		}
		String bankName = null;
		if (topupMoneyVo.getPayChannel().equals("debit")) {
			bankName = BankUtil.getLianlianpayBankNameByCode(topupMoneyVo.getBankcode());
		} else if (topupMoneyVo.getPayChannel().equals("credit")) {
			bankName = BankUtil.getLianlianpayCreditBankNameByCode(topupMoneyVo.getBankcode());
		}
		if (null == bankName || "".equals(bankName)) {
			return "不支持此银行！";
		}
		return result;
	}

	/**
	 * <p>
	 * Description:封装连连支付表单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月28日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param ip
	 * @return PaymentInfo
	 * @throws Exception
	 */
	private LianlianPaymentRequest packageLianlianPayForm(Integer userId, TopupMoneyVo topupMoneyVo, String ip) throws Exception {
		LianlianPaymentRequest paymentInfo = new LianlianPaymentRequest();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd" + BusinessConstants.ONLINE_PAY_LIANLIANPAY_FORMAT_USER_ID + "HHmmssSSS", Locale.US);
		String merOrderNum = sf.format(new Date()) + "" + userId;
		// 1：网银支付（借记卡） 8：网银支付（信用卡） 9：B2B 企业网银支付 不传则1 和8 都支持
		String payType = "1";
		if (topupMoneyVo.getPayChannel().equals("credit")) {
			payType = "8";
		}
		paymentInfo.setVersion(BusinessConstants.ONLINE_PAY_LIANLIANPAY_VERSION);
		paymentInfo.setOid_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_OID_PARTNER);
		paymentInfo.setUser_id(String.valueOf(userId));
		paymentInfo.setTimestamp(DateUtils.format(new Date(), DateUtils.YMDHMS));
		paymentInfo.setSign_type(BusinessConstants.ONLINE_PAY_LIANLIANPAY_SIGNTYPE);
		paymentInfo.setBusi_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_BUSI_PARTNER);
		paymentInfo.setNo_order(merOrderNum);
		paymentInfo.setDt_order(DateUtils.format(new Date(), DateUtils.YMDHMS));
		paymentInfo.setName_goods("外部账户充值");
		paymentInfo.setMoney_order(String.valueOf(topupMoneyVo.getMoney()));
		paymentInfo.setNotify_url(BusinessConstants.ONLINE_PAY_LIANLIANPAY_BGURL);
		paymentInfo.setUrl_return(BusinessConstants.ONLINE_PAY_LIANLIANPAY_FRONTURL);
		paymentInfo.setUserreq_ip(ip.replace(".", "_"));
		paymentInfo.setUrl_order("");
		paymentInfo.setValid_order("10080");// 单位分钟，可以为空，默认7天
		paymentInfo.setBank_code(topupMoneyVo.getBankcode());
		paymentInfo.setPay_type(payType);
		return paymentInfo;
	}

	/**
	 * <p>
	 * Description:保存充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param ip
	 * @param bankName
	 * @param paymentInfo
	 * @throws Exception void
	 */
	private void packageRechargeRecord(Integer userId, TopupMoneyVo topupMoneyVo, String ip, String bankName, LianlianPaymentRequest paymentInfo) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setTradeNo(paymentInfo.getNo_order());
		rechargeRecord.setUserId(userId);
		rechargeRecord.setType(Constants.RECHARGE_TYPE_ONLINE);
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
		rechargeRecord.setMoney(topupMoneyVo.getMoney());
		rechargeRecord.setPayment(bankName);
		// 手续费千分之二,最低1分钱
		// BigDecimal fee = new BigDecimal("0.01");
		// BigDecimal chargeFee = topupMoneyVo.getMoney()
		// .divide(new BigDecimal(1000)).setScale(4, RoundingMode.DOWN)
		// .multiply(new BigDecimal(2));
		// if (chargeFee.compareTo(fee) == 1) {
		// fee = chargeFee;
		// }
		// rechargeRecord.setFee(fee.setScale(2, RoundingMode.DOWN));// 手续费
		rechargeRecord.setFee(BigDecimal.ZERO);// 在线支付没有手续费
		rechargeRecord.setAddip(ip);
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_LIANLIANAPAY);
		rechargeRecord.setVersion(1);
		rechargeRecord.setPlatform(topupMoneyVo.getPlatform());
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}

	/**
	 * <p>
	 * Description:更新充值记录的状态为成功<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordVo
	 * @return
	 * @throws Exception Integer
	 */
	private Integer refreshRechargeStatus(RechargeRecordVo rechargeRecordVo) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setId(rechargeRecordVo.getId());
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_SUCCESS);
		rechargeRecord.setVerifyTime(DateUtils.getTime());
		rechargeRecord.setVerifyRemark(Constants.ONLINE_TYPE_LIANLIANPAY_NAME.concat("调用接口调用自动审核"));
		rechargeRecord.setVerifyTime2(DateUtils.getTime());
		rechargeRecord.setVerifyRemark2(Constants.ONLINE_TYPE_LIANLIANPAY_NAME.concat("调用接口调用自动审核"));
		int updateCount = baseRechargeRecordMapper.updateEntity(rechargeRecord);
		return updateCount;
	}

	/**
	 * <p>
	 * Description:更新用户帐号的可用余额和总金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordVo
	 * @return
	 * @throws Exception Account
	 */
	private Account refreshAccountMoney(RechargeRecordVo rechargeRecordVo, BigDecimal fee, AccountVo sourceAccount) throws Exception {
		Account account = new Account();
		BeanUtils.copyProperties(sourceAccount, account);
		account.setUseMoney(account.getUseMoney().add(rechargeRecordVo.getMoney()).subtract(fee));
		account.setNoDrawMoney(account.getNoDrawMoney().add(rechargeRecordVo.getMoney()).subtract(fee));
		account.setTotal(account.getTotal().add(rechargeRecordVo.getMoney()).subtract(fee));
		baseAccountMapper.updateEntity(account);
		return account;
	}
}
