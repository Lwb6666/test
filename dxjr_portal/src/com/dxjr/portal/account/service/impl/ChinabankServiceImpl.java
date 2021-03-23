package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountRechargeFeedback;
import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.entity.SystemMessage;
import com.dxjr.base.entity.SystemMessageTemplate;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseAccountRechargeFeedbackMapper;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.base.mapper.BaseSystemMessageMapper;
import com.dxjr.base.mapper.BaseSystemMessageTemplateMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.ChinabankService;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.util.BankUtil;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.ChinabankPayForm;
import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.SystemMessageUtil;

/**
 * <p>
 * Description:网银在线支付业务实现类<br />
 * </p>
 * 
 * @title ChinabankServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年6月11日
 */
@Service
public class ChinabankServiceImpl implements ChinabankService {

	@Autowired
	private BaseRechargeRecordMapper baseRechargeRecordMapper;
	@Autowired
	private BaseAccountRechargeFeedbackMapper baseAccountRechargeFeedbackMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseSystemMessageTemplateMapper baseSystemMessageTemplateMapper;
	@Autowired
	private BaseSystemMessageMapper baseSystemMessageMapper;
	@Autowired
	private RechargeRecordService rechargeRecordService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLogService accountLogService;

	@Override
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 验证是否是支持的银行
		String bankName = BankUtil.getChinaBankNameByBankCode(topupMoneyVo.getBankcode());
		if (null == bankName || "".equals(bankName)) {
			resultMap.put("result", "不支持此银行！");
			return resultMap;
		}
		// 最低充值金额2分钱
		if (topupMoneyVo.getMoney().compareTo(new BigDecimal("0.02")) < 0) {
			resultMap.put("result", "充值金额必须大于或等于0.02元");
			return resultMap;
		}
		// 封装网银在线支付表单
		ChinabankPayForm payForm = packageChinabankPayForm(userId, topupMoneyVo, request);
		// 保存充值记录
		packageRechargeRecord(userId, topupMoneyVo, request, bankName, payForm);

		resultMap.put("chinabankPayForm", payForm);
		return resultMap;
	}

	@Override
	public String saveAutoReceive(ChinabankReceiveForm chinabankReceiveForm, HttpServletRequest request) throws Exception {
		String result = "success";

		// 加密key
		String text = chinabankReceiveForm.generateMd5String();
		String v_md5text = MD5.toMD5(text).toUpperCase();
		if (null == chinabankReceiveForm.getV_md5str() || !chinabankReceiveForm.getV_md5str().equals(v_md5text)) {
			return "SignMsgError";
		}
		if (null == chinabankReceiveForm || null == chinabankReceiveForm.getV_pstatus() || !"20".equals(chinabankReceiveForm.getV_pstatus())) {
			return "PstatusError";
		}
		// 找到充值记录并锁定
		RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
		rechargeRecordCnd.setTradeNo(chinabankReceiveForm.getV_oid());
		rechargeRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
		// 如果不是待付款且金额不相等
		if (null == rechargeRecordVo || null == rechargeRecordVo.getStatus() || rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT
				|| !String.valueOf(rechargeRecordVo.getMoney()).equals(chinabankReceiveForm.getV_amount())) {
			return "PayERROR";// 支付失败
		}
		// 支付成功，更新充值记录的状态为成功
		refreshRechargeStatus(rechargeRecordVo);
		// 手续费千分之二,最低1分钱
		BigDecimal fee = rechargeRecordVo.getFee();// new BigDecimal("0.01");
		// BigDecimal chargeFee = rechargeRecordVo.getMoney().divide(new
		// BigDecimal(1000)).setScale(4, RoundingMode.DOWN).multiply(new
		// BigDecimal(2));
		// if (chargeFee.compareTo(fee) == 1) {
		// fee = chargeFee;
		// }
		// fee = fee.setScale(2, RoundingMode.UP);
		AccountVo sourceAccount = accountService.queryAccountByUserIdForUpdate(rechargeRecordVo.getUserId());
		// 更新用户帐号的可用余额和总金额，得到扣除手续费后的总金额
		refreshAccountMoney(rechargeRecordVo, fee, sourceAccount);
		sourceAccount.setUseMoney(sourceAccount.getUseMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setNoDrawMoney(sourceAccount.getNoDrawMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setTotal(sourceAccount.getTotal().add(rechargeRecordVo.getMoney()));
		// 插入资金记录
		StringBuilder remarkbuilder = new StringBuilder("在线充值成功,本次使用的是".concat(Constants.ONLINE_TYPE_CHINABANK_NAME).concat("进行充值."));
		accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(), rechargeRecordVo.getMoney(), remarkbuilder.toString(), HttpTookit.getRealIpAddr(request), "online_recharge",
				null, null, null);
		// 插入手续费资金记录
		// String feeRemark = "网银在线充值:" + rechargeRecordVo.getPayment() +
		// ",扣除充值手续费:" + fee;
		// sourceAccount.setUseMoney(sourceAccount.getUseMoney().subtract(fee));
		// sourceAccount.setNoDrawMoney(sourceAccount.getNoDrawMoney().subtract(fee));
		// sourceAccount.setTotal(sourceAccount.getTotal().subtract(fee));
		// accountLogService.saveAccountLogByParams(sourceAccount,
		// sourceAccount.getUserId(), fee, feeRemark,
		// HttpTookit.getRealIpAddr(request), "chinabank_toup_fee", null, null,
		// null);
		// 发送站内信息 -线上充值成功
		// packageSystemMessage(chinabankReceiveForm, rechargeRecordVo);
		return result;
	}

	/**
	 * <p>
	 * Description:发送站内信息 -线上充值成功<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param shengReceiveForm
	 * @param rechargeRecordVo
	 * @throws Exception void
	 */
	private void packageSystemMessage(ChinabankReceiveForm chinabankReceiveForm, RechargeRecordVo rechargeRecordVo) throws Exception {
		SystemMessageTemplate systemMessageTemplate = baseSystemMessageTemplateMapper.queryByType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_ONLINE_RECHARGE_SUCCESS);
		if (null != systemMessageTemplate) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", rechargeRecordVo.getUsername());
			paramMap.put("money", rechargeRecordVo.getMoney());
			paramMap.put("orderNo", chinabankReceiveForm.getV_oid());
			SystemMessage systemMessage = new SystemMessage();
			systemMessage.setTitle(systemMessageTemplate.getTitle());
			systemMessage.setType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_ONLINE_RECHARGE_SUCCESS);
			systemMessage.setUserId(rechargeRecordVo.getUserId());
			systemMessage.setAddtime(new Date());
			systemMessage.setIsRead(Constants.SYSTEM_MESSAGE_NOT_READ);
			systemMessage.setContent(SystemMessageUtil.generateParamContent(systemMessageTemplate.getContent(), paramMap));
			baseSystemMessageMapper.insertEntity(systemMessage);
		}
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
		RechargeRecord updateRechargeRecord = new RechargeRecord();
		updateRechargeRecord.setId(rechargeRecordVo.getId());
		updateRechargeRecord.setStatus(Constants.RECHARGE_STATUS_SUCCESS);
		updateRechargeRecord.setVerifyTime(DateUtils.getTime());
		updateRechargeRecord.setVerifyRemark(Constants.ONLINE_TYPE_CHINABANK_NAME.concat("调用接口调用自动审核"));
		updateRechargeRecord.setVerifyTime2(DateUtils.getTime());
		updateRechargeRecord.setVerifyRemark2(Constants.ONLINE_TYPE_CHINABANK_NAME.concat("调用接口调用自动审核"));
		Integer updateCount = baseRechargeRecordMapper.updateEntity(updateRechargeRecord);
		return updateCount;
	}

	/**
	 * <p>
	 * Description:保存支付返回信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param chinabankReceiveForm
	 * @throws Exception void
	 */
	@Override
	public void saveAccountRechargeFeedback(ChinabankReceiveForm chinabankReceiveForm) throws Exception {
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(chinabankReceiveForm.getV_oid());
		rechargeFeedback.setPaymode(chinabankReceiveForm.getV_pmode());
		rechargeFeedback.setPaystatus(chinabankReceiveForm.getV_pstatus());
		rechargeFeedback.setPaystring(chinabankReceiveForm.getV_pstring());
		rechargeFeedback.setAmount(chinabankReceiveForm.getV_amount());
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(chinabankReceiveForm.getV_md5str());
		rechargeFeedback.setRemark1(chinabankReceiveForm.getRemark1());
		rechargeFeedback.setRemark2(chinabankReceiveForm.getRemark2());
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_CHINABANK);
		baseAccountRechargeFeedbackMapper.insertEntity(rechargeFeedback);
	}

	/**
	 * <p>
	 * Description:封装网银在线支付表单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @return ShengPayForm
	 */
	private ChinabankPayForm packageChinabankPayForm(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		ChinabankPayForm payForm = new ChinabankPayForm();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + BusinessConstants.ONLINE_PAY_CHINABANK_SHOPNO + "-HHmmssSSS", Locale.US);
		String v_oid = sf.format(new Date()) + "_" + userId;

		payForm.setV_mid(BusinessConstants.ONLINE_PAY_CHINABANK_SHOPNO);
		payForm.setV_url(BusinessConstants.CHINABANK_RECEVIE_URL);
		payForm.setV_oid(v_oid);
		// MD5值
		String v_amount = topupMoneyVo.getMoney().toString();// 充值金额
		String v_moneytype = BusinessConstants.CHINABANK_MONEY_TYPE; // 币种
		String text = v_amount + v_moneytype + v_oid + payForm.getV_mid() + payForm.getV_url() + BusinessConstants.ONLINE_PAY_CHINABANK_MD5KEY; // 拼凑加密串
		String v_md5info = MD5.toMD5(text).toUpperCase(); // 网银支付平台对MD5值只认大写字符串，所以小写的MD5值得转换为大写
		payForm.setV_md5info(v_md5info);
		payForm.setV_amount(v_amount);
		payForm.setV_moneytype(v_moneytype);
		payForm.setPmode_id(topupMoneyVo.getBankcode());
		// 自动回调地址：如果remark2符合[url:=]格式，就按照remark2的内容通知你们;如果remark2没有格式，就按照给你们设置的地址通知你们
		payForm.setRemark2("[url:=" + BusinessConstants.CHINABANK_AUTO_RECEVIE_URL + "]");
		return payForm;
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
	 * @param request
	 * @param bankName
	 * @param payForm
	 * @throws Exception void
	 */
	private void packageRechargeRecord(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request, String bankName, ChinabankPayForm payForm) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setTradeNo(payForm.getV_oid());
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
		// rechargeRecord.setFee(fee.setScale(2, RoundingMode.UP));// 手续费
		rechargeRecord.setFee(new BigDecimal("0"));// 在线支付没有手续费
		rechargeRecord.setAddip(request.getRemoteAddr());
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_CHINABANK);// 盛付通
		rechargeRecord.setVersion(1);
		rechargeRecord.setPlatform(topupMoneyVo.getPlatform());
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}

}
