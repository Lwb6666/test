package com.dxjr.portal.sinapay.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
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
import com.dxjr.portal.sinapay.model.FormGenerator;
import com.dxjr.portal.sinapay.protocol.SinaPayRechargeSupport;
import com.dxjr.portal.sinapay.protocol.SinaPayReponse;
import com.dxjr.portal.sinapay.protocol.SinaPayRequest;
import com.dxjr.portal.sinapay.protocol.SinaQueryRecordListReponse;
import com.dxjr.portal.sinapay.service.SinapayService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.file.FileUtil;

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
public class SinapayServiceImpl implements SinapayService {
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

	@Override
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 保留两位小数
		System.out.println("**************" + topupMoneyVo + "*****" + topupMoneyVo.getMoney() + ":''''");
		topupMoneyVo.setMoney(topupMoneyVo.getMoney().setScale(2, RoundingMode.DOWN));
		// 验证银行信息是否合法
		String result = validateBankname(topupMoneyVo, resultMap);
		if (!"success".equals(result)) {
			resultMap.put("result", result);
			return resultMap;
		}
		java.util.Date currentTime = new java.util.Date();// 得到当前系统时间
		Map<String, String> params = new HashMap<String, String>();
		params = SinaPayRechargeSupport.initCreateInstantOrderParameter(userId, topupMoneyVo, currentTime);
		if (null == params || params.size() == 0) {
			return null;
		}

		String sinapayRequestString = SinaPayRechargeSupport.generateFormForRecharge(params, request);

		// 保存充值记录
		String bankName = BankUtil.getSinapayBankNameByCode(topupMoneyVo.getBankcode());

		this.packageRechargeRecord(userId, topupMoneyVo, request, bankName, params.get("out_trade_no"));

		resultMap.put("sinapayRequestString", sinapayRequestString);
		return resultMap;
	}

	@Override
	public String saveAutoReceive(SinaPayReponse sinaPayReponse, HttpServletRequest request) throws Exception {
		String result = "success";
		String orderId = sinaPayReponse.getOrderId(); // 订单号
		String payAmount = sinaPayReponse.getPayAmount();// 订单金额
		String ip = HttpTookit.getRealIpAddr(request);// ip
		// 保存充值金额更新用户账号和记录资金明细
		result = saveRechargeAccountAndLog(orderId, payAmount, ip);
		if (!"success".equals(result)) {
			return result;
		}
		return result;
	}

	@Override
	public String saveLoseOrder(SinaQueryRecordListReponse sinaQueryRecordListReponse, String ip) throws Exception {
		String result = "success";
		// 当前订单状态，目前有四种状态：Ready，Processing，Closed，Success
		if (null == sinaQueryRecordListReponse || null == sinaQueryRecordListReponse.getCurrentStatus()
				|| (!"PAY_FINISHED".equals(sinaQueryRecordListReponse.getCurrentStatus()) && !"TRADE_FINISHED".equals(sinaQueryRecordListReponse.getCurrentStatus()))) {
			return "订单状态异常，请确认";
		}
		String payAmount = sinaQueryRecordListReponse.getOrderAmount();// 订单金额
		// 保存充值金额更新用户账号和记录资金明细
		result = saveRechargeAccountAndLog(sinaQueryRecordListReponse.getOrderId(), payAmount, ip);
		if (!"success".equals(result)) {
			return result;
		}
		return result;
	}

	/**
	 * <p>
	 * Description:保存支付返回信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @throws Exception void
	 */
	@Override
	public void saveAccountRechargeFeedback(SinaPayReponse sinaPayReponse) throws Exception {
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(sinaPayReponse.getOrderId());
		rechargeFeedback.setPaymode(BankUtil.getSinapayBankNameByCode(sinaPayReponse.getBankId()));
		rechargeFeedback.setPaystatus(sinaPayReponse.getPayResult());
		rechargeFeedback.setPaystring(sinaPayReponse.getPayResult());

		// 如果不是待付款 或金额不一致,新浪支付返回的钱是以分为单位
		rechargeFeedback.setAmount("" + Double.parseDouble(sinaPayReponse.getPayAmount()));
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(null);
		rechargeFeedback.setRemark1(null);
		rechargeFeedback.setRemark2(null);
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_SINAPAY);
		baseAccountRechargeFeedbackMapper.insertEntity(rechargeFeedback);
	}

	@Override
	public void savePackageLoseFeedBack(SinaQueryRecordListReponse sinaQueryRecordListReponse) throws Exception {
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(sinaQueryRecordListReponse.getOrderId());
		rechargeFeedback.setPaymode(BankUtil.getSinapayBankNameByCode(sinaQueryRecordListReponse.getBankId()));
		rechargeFeedback.setPaystatus(sinaQueryRecordListReponse.getCurrentStatus());
		rechargeFeedback.setPaystring(sinaQueryRecordListReponse.getCurrentStatus());

		// 如果不是待付款 或金额不一致,新浪支付返回的钱是以分为单位
		rechargeFeedback.setAmount("" + Double.parseDouble(sinaQueryRecordListReponse.getPayAmount()));
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(null);
		rechargeFeedback.setRemark1(null);
		rechargeFeedback.setRemark2(null);
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_SINAPAY);
		baseAccountRechargeFeedbackMapper.insertEntity(rechargeFeedback);
	}

	/**
	 * <p>
	 * Description:验证密钥是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月29日
	 * @param sinaPayReponse
	 * @return String
	 * @throws Exception
	 */
	private boolean validateSignMsg(SinaPayReponse sinaPayReponse, HttpServletRequest request) throws Exception {
		InputStream is = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/classes/sinapay/weihuimas.crt");
		X509Certificate certificate = X509Certificate.getInstance(is);
		Signature verify = Signature.getInstance(certificate.getSigAlgName());
		verify.initVerify(certificate.getPublicKey());
		// System.out.println(RSACore.getByteLength((RSAPublicKey)certificate.getPublicKey()));
		// System.out.println(Base64.decodeBase64(sinaPayReponse.getSignMsg()).length);

		verify.update(sinaPayReponse.generateSignContent().getBytes(Charsets.UTF_8));
		return verify.verify(Base64.decodeBase64(sinaPayReponse.getSignMsg()));
	}

	/**
	 * <p>
	 * Description:生成表单字符串<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月28日
	 * @param sinaPayRequest
	 * @return String
	 */
	private String generateFormString(SinaPayRequest sinaPayRequest, HttpServletRequest request) throws Exception {
		FormGenerator formGenerator = new FormGenerator();
		formGenerator.setActionUrl(BusinessConstants.ONLINE_PAY_SINAPAY_ACTIONURL);
		formGenerator.setFormElementId("sinapaySendForm");
		formGenerator.setSignType(BusinessConstants.ONLINE_PAY_SINAPAY_SIGNTYPE);
		formGenerator.setKey(FileUtil.readFile(request.getRealPath("") + "/WEB-INF/classes/sinapay/sinaprivate_key.txt"));
		// formGenerator.setKey(FileUtil.readFile(request.getRealPath("") +
		// "/WEB-INF/classes/sinapay/nodes-testmerch.key"));
		formGenerator.init();
		String result = formGenerator.generateForm(sinaPayRequest);
		return result;
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
			bankName = BankUtil.getSinapayBankNameByCode(topupMoneyVo.getBankcode());
		} else if (topupMoneyVo.getPayChannel().equals("credit")) {
			bankName = BankUtil.getSinapayCreditBankNameByCode(topupMoneyVo.getBankcode());
		}
		if (null == bankName || "".equals(bankName)) {
			return "不支持此银行！";
		}
		return result;
	}

	/**
	 * <p>
	 * Description:封装新良支付表单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月28日
	 * @param topupMoneyVo
	 * @param request
	 * @return
	 * @throws Exception SinaPayRequest
	 */
	private SinaPayRequest packageSinapayForm(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		SinaPayRequest sinaPayRequest = new SinaPayRequest();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + BusinessConstants.ONLINE_PAY_SINAPAY_MEMBER_ID + "-HHmmssSSS", Locale.US);
		String merOrderNum = sf.format(new Date()) + "_" + userId;
		// 支付方式 10：银行卡网银支付综合入口 15：信用卡支付 20：借记卡支付 30：信用支付
		String payType = "20";
		if (topupMoneyVo.getPayChannel().equals("credit")) {
			payType = "15";
		}

		sinaPayRequest.setInputCharset(BusinessConstants.ONLINE_PAY_SINAPAY_CHARSET);
		sinaPayRequest.setBgUrl(BusinessConstants.ONLINE_PAY_SINAPAY_BGURL);
		sinaPayRequest.setVersion(BusinessConstants.ONLINE_PAY_SINAPAY_VERSION);
		sinaPayRequest.setLanguage(BusinessConstants.ONLINE_PAY_SINAPAY_LANGUAGE);
		sinaPayRequest.setMerchantAcctId(BusinessConstants.ONLINE_PAY_SINAPAY_MERCHANT_ACCT_ID);
		sinaPayRequest.setOrderId(merOrderNum);
		// 以分为单位
		BigDecimal resultMoney = topupMoneyVo.getMoney().multiply(new BigDecimal("100"));
		sinaPayRequest.setOrderAmount(String.valueOf(resultMoney.intValue()));
		sinaPayRequest.setOrderTime(DateUtils.format(new Date(), DateUtils.YMDHMS));
		sinaPayRequest.setPayType(payType);
		sinaPayRequest.setBankId(topupMoneyVo.getBankcode());
		// sinaPayRequest.setBankId("TESTBANK");
		sinaPayRequest.setPid(BusinessConstants.ONLINE_PAY_SINAPAY_MEMBER_ID);
		sinaPayRequest.setIp(HttpTookit.getRealIpAddr(request));

		return sinaPayRequest;
	}

	/**
	 * <p>
	 * Description:保存充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param topupMoneyVo
	 * @param request
	 * @param bankName
	 * @throws Exception void
	 */
	private void packageRechargeRecord(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request, String bankName, String outTradeNo) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setTradeNo(outTradeNo);
		rechargeRecord.setUserId(userId);
		rechargeRecord.setType(Constants.RECHARGE_TYPE_ONLINE);
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
		rechargeRecord.setMoney(topupMoneyVo.getMoney());
		rechargeRecord.setPayment(bankName);
		// 手续费千分之二,最低1分钱
		BigDecimal fee = new BigDecimal("0.01");
		BigDecimal chargeFee = topupMoneyVo.getMoney().divide(new BigDecimal(1000)).setScale(4, RoundingMode.DOWN).multiply(new BigDecimal(2));
		if (chargeFee.compareTo(fee) == 1) {
			fee = chargeFee;
		}
		rechargeRecord.setFee(fee.setScale(2, RoundingMode.UP));// 手续费
		// rechargeRecord.setFee(BigDecimal.ZERO);// 在线支付没有手续费
		rechargeRecord.setAddip(request.getRemoteAddr());
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_SINAPAY);
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
		rechargeRecord.setVerifyRemark(Constants.ONLINE_TYPE_SINAPAY_NAME.concat("调用接口调用自动审核"));
		rechargeRecord.setVerifyTime2(DateUtils.getTime());
		rechargeRecord.setVerifyRemark2(Constants.ONLINE_TYPE_SINAPAY_NAME.concat("调用接口调用自动审核"));
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

	/**
	 * <p>
	 * Description:保存充值金额更新用户账号和记录资金明细<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月30日
	 * @param orderId
	 * @param payAmount
	 * @param ip
	 * @throws Exception void
	 */
	private String saveRechargeAccountAndLog(String orderId, String payAmount, String ip) throws Exception {
		String result = "success";
		// 找到充值记录并锁定
		RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
		rechargeRecordCnd.setTradeNo(orderId);
		rechargeRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
		// 如果不是待付款 或金额不一致,新浪支付返回的钱是以分为单位
		if (null == rechargeRecordVo) {
			return "PayERROR";
		}
		BigDecimal resultMoney = rechargeRecordVo.getMoney();

		if (null == rechargeRecordVo || rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT || !String.valueOf(resultMoney).equals(payAmount)) {
			return "PayERROR";// 支付失败
		}

		BigDecimal fee = rechargeRecordVo.getFee();
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
		StringBuilder remarkbuilder = new StringBuilder("在线充值成功,本次使用的是".concat(Constants.ONLINE_TYPE_SINAPAY_NAME).concat("进行充值."));
		accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(), rechargeRecordVo.getMoney(), remarkbuilder.toString(), ip, "online_recharge", null, null, null);
		// 插入手续费资金记录
		String feeRemark = "新浪在线充值:" + rechargeRecordVo.getPayment() + ",扣除充值手续费:" + fee;
		sourceAccount.setUseMoney(sourceAccount.getUseMoney().subtract(fee));
		sourceAccount.setNoDrawMoney(sourceAccount.getNoDrawMoney().subtract(fee));
		sourceAccount.setTotal(sourceAccount.getTotal().subtract(fee));
		accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(), fee, feeRemark, ip, "TOPUP_FEE", null, null, null);
		// 发送站内信息 -线上充值成功
		// packageSystemMessage(sinaPayReponse, rechargeRecordVo);
		return result;
	}
}
