package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
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
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.service.ShengpayService;
import com.dxjr.portal.account.util.BankUtil;
import com.dxjr.portal.account.util.ShengpayUtils;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.account.vo.ShengPayForm;
import com.dxjr.portal.account.vo.ShengReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.SystemMessageUtil;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:盛付通支付业务实现类<br />
 * </p>
 * 
 * @title ShengpayServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月22日
 */
@Service
public class ShengpayServiceImpl implements ShengpayService {

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
	public Map<String, Object> savesend(MemberVo memberVo, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 验证是否是支持的银行
		String bankName = BankUtil.getShengBankNameByCode(topupMoneyVo.getBankcode());
		if (null == bankName || "".equals(bankName)) {
			resultMap.put("result", "不支持此银行！");
			return resultMap;
		}
		// 封装盛付通支付表单
		ShengPayForm payForm = packageShengpayForm(memberVo, topupMoneyVo, request);
		// 保存充值记录
		packageRechargeRecord(memberVo, topupMoneyVo, request, bankName, payForm);

		resultMap.put("shengPayForm", payForm);
		return resultMap;
	}

	@Override
	public String saveAutoReceive(ShengReceiveForm shengReceiveForm, HttpServletRequest request) throws Exception {
		String result = "success";
		// 保存支付返回信息
		packageAccountRechargeFeedback(shengReceiveForm);

		// 加密key
		String signMsg = ShengpayUtils.signCallback(shengReceiveForm.toSignString() + BusinessConstants.ONLINE_PAY_SHENGPAY_KEY, shengReceiveForm.getCharset());
		// 转换为大写
		if (!shengReceiveForm.getSignMsg().equals(signMsg.toUpperCase())) {
			return "SignMsgError";
		}

		// 等待付款中 00 付款成功 01 付款失败 02 过期 03 撤销成功 04 退款中 05 退款成功 06 退款失败 07部分退款成功08
		if (null == shengReceiveForm || null == shengReceiveForm.getTransStatus() || !"01".equals(shengReceiveForm.getTransStatus())) {
			return "TransStatusError";
		}
		RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
		rechargeRecordCnd.setTradeNo(shengReceiveForm.getOrderNo());
		RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
		// 如果不是待付款
		if (null == rechargeRecordVo || null == rechargeRecordVo.getStatus() || rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT
				|| !String.valueOf(rechargeRecordVo.getMoney()).equals(shengReceiveForm.getOrderAmount())) {
			return "PayERROR";// 支付失败
		}
		// 支付成功，更新充值记录的状态为成功
		Integer updateCount = refreshRechargeStatus(rechargeRecordVo);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("重复提交");
		}
		// 更新用户帐号的可用余额和总金额
		AccountVo accountVo = refreshAccountMoney(rechargeRecordVo);
		// 插入资金记录
		StringBuilder remarkbuilder = new StringBuilder("在线充值成功,本次使用的是".concat(Constants.ONLINE_TYPE_SHENGPAY_NAME).concat("进行充值."));
		accountLogService.saveAccountLogByParams(accountVo, accountVo.getUserId(), rechargeRecordVo.getMoney(), remarkbuilder.toString(), HttpTookit.getRealIpAddr(request), "online_recharge", null,
				null, null);
		// 发送站内信息 -线上充值成功
		// packageSystemMessage(shengReceiveForm, rechargeRecordVo);
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
	private void packageSystemMessage(ShengReceiveForm shengReceiveForm, RechargeRecordVo rechargeRecordVo) throws Exception {
		SystemMessageTemplate systemMessageTemplate = baseSystemMessageTemplateMapper.queryByType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_ONLINE_RECHARGE_SUCCESS);
		if (null != systemMessageTemplate) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", rechargeRecordVo.getUsername());
			paramMap.put("money", rechargeRecordVo.getMoney());
			paramMap.put("orderNo", shengReceiveForm.getOrderNo());
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
		rechargeRecordVo.setStatus(Constants.RECHARGE_STATUS_SUCCESS);
		rechargeRecordVo.setVerifyTime(DateUtils.getTime());
		rechargeRecordVo.setVerifyRemark(Constants.ONLINE_TYPE_SHENGPAY_NAME.concat("调用接口调用自动审核"));
		RechargeRecord updateRechargeRecord = new RechargeRecord();
		BeanUtils.copyProperties(rechargeRecordVo, updateRechargeRecord);
		updateRechargeRecord.setSelfVersion(rechargeRecordVo.getVersion());
		updateRechargeRecord.setVersion(rechargeRecordVo.getVersion() + 1);
		Integer updateCount = baseRechargeRecordMapper.updateEntity(updateRechargeRecord);
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
	private AccountVo refreshAccountMoney(RechargeRecordVo rechargeRecordVo) throws Exception {
		AccountVo accountVo = accountService.queryAccountByUserId(rechargeRecordVo.getUserId());

		accountVo.setUseMoney(accountVo.getUseMoney().add(rechargeRecordVo.getMoney()));
		accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().add(rechargeRecordVo.getMoney()));
		accountVo.setTotal(accountVo.getTotal().add(rechargeRecordVo.getMoney()));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		return accountVo;
	}

	/**
	 * <p>
	 * Description:保存支付返回信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param shengReceiveForm
	 * @throws Exception void
	 */
	private void packageAccountRechargeFeedback(ShengReceiveForm shengReceiveForm) throws Exception {
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(shengReceiveForm.getOrderNo());
		rechargeFeedback.setPaymode(BankUtil.getShengBankNameByCode(shengReceiveForm.getInstCode()));
		rechargeFeedback.setPaystatus(shengReceiveForm.getTransStatus());
		rechargeFeedback.setPaystring(shengReceiveForm.getTransStatus());
		rechargeFeedback.setAmount(shengReceiveForm.getTransAmount());
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(null);
		rechargeFeedback.setRemark1(null);
		rechargeFeedback.setRemark2(null);
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_SHENGPAY);
		baseAccountRechargeFeedbackMapper.insertEntity(rechargeFeedback);
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
	private void packageRechargeRecord(MemberVo memberVo, TopupMoneyVo topupMoneyVo, HttpServletRequest request, String bankName, ShengPayForm payForm) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setTradeNo(payForm.getOrderNo());
		rechargeRecord.setUserId(memberVo.getId());
		rechargeRecord.setType(Constants.RECHARGE_TYPE_ONLINE);
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
		rechargeRecord.setMoney(topupMoneyVo.getMoney());
		rechargeRecord.setPayment(bankName);
		rechargeRecord.setFee(new BigDecimal("0"));// 在线支付没有手续费
		rechargeRecord.setAddip(request.getRemoteAddr());
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_SHENGPAY);// 盛付通
		rechargeRecord.setVersion(1);
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}

	/**
	 * <p>
	 * Description:封装盛付通支付表单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @return ShengPayForm
	 */
	private ShengPayForm packageShengpayForm(MemberVo memberVo, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		ShengPayForm payForm = new ShengPayForm();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + BusinessConstants.ONLINE_PAY_SHENGPAY_MSGSENDER + "-HHmmssSSS", Locale.US);
		String merOrderNum = sf.format(new Date()) + "_" + memberVo.getId();

		payForm.setName(BusinessConstants.ONLINE_PAY_SHENGPAY_NAME);
		payForm.setVersion(BusinessConstants.ONLINE_PAY_SHENGPAY_VERSION);
		payForm.setCharset(BusinessConstants.ONLINE_PAY_SHENGPAY_CHARSET);
		payForm.setMsgSender(BusinessConstants.ONLINE_PAY_SHENGPAY_MSGSENDER);
		payForm.setPageUrl(BusinessConstants.ONLINE_PAY_SHENGPAY_FRONTMERURL);
		payForm.setNotifyUrl(BusinessConstants.ONLINE_PAY_SHENGPAY_BACKGROUNDMERURL);
		payForm.setSignType(BusinessConstants.ONLINE_PAY_SHENGPAY_SIGNTYPE);
		payForm.setSignMsg(BusinessConstants.ONLINE_PAY_SHENGPAY_KEY);
		payForm.setSendTime(ShengpayUtils.getShengpayServerTime());
		payForm.setOrderNo(merOrderNum);
		payForm.setOrderAmount(topupMoneyVo.getMoney().toString());
		payForm.setOrderTime(DateUtils.format(new Date(), DateUtils.YMDHMS));
		payForm.setPayType("PT001");// 网银支付
		payForm.setInstCode(topupMoneyVo.getBankcode());
		payForm.setPayChannel(BusinessConstants.ONLINE_PAY_SHENGPAY_PAYCHANNEL_DEBIT_CARD);// 借记卡渠道
		payForm.setBuyerContact(memberVo.getEmail());
		payForm.setBuyerIp(HttpTookit.getRealIpAddr(request));
		// 加密key
		payForm = ShengpayUtils.signForm(payForm, payForm.getSignMsg());
		// 转换为大写
		payForm.setSignMsg(payForm.getSignMsg().toUpperCase());
		return payForm;
	}

}
