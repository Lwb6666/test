package com.dxjr.portal.lianlian.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.lianlian.service.LianlianpayWapService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LlWapBankcardInfo;
import com.dxjr.portal.lianlian.vo.LlWapBankcardRequest;
import com.dxjr.portal.lianlian.vo.LlWapBankcardResponse;
import com.dxjr.portal.lianlian.vo.LlWapBankcardUnbindRequest;
import com.dxjr.portal.lianlian.vo.LlWapBankcardUnbindResponse;
import com.dxjr.portal.lianlian.vo.LlWapPaymentRequest;
import com.dxjr.portal.lianlian.vo.LlWapPaymentRiskItem;
import com.dxjr.portal.member.mapper.BankInfoMapper;
import com.dxjr.portal.member.vo.BankInfoCnd;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.sinapay.sign.SignAndVerify;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.file.FileUtil;
import com.dxjr.utils.httputil.HttpURLUtil;

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
public class LianlianpayWapServiceImpl implements LianlianpayWapService {
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
	public String querybankcard(String card_no) throws Exception {
		Map<String, Object> resultMap = this.queryLianlianBankcard(card_no);
		String result = String.valueOf(resultMap.get("result"));
		return result;
	}

	@Override
	public LlWapBankcardInfo queryBankcardInfo(String card_no) throws Exception {
		Map<String, Object> resultMap = this.queryLianlianBankcard(card_no);
		String result = String.valueOf(resultMap.get("result"));
		LlWapBankcardInfo llWapBankcardInfo = new LlWapBankcardInfo();
		llWapBankcardInfo.setResultMsg(result);
		if (result.equals(BusinessConstants.SUCCESS)) {
			LlWapBankcardResponse llWapBankcardResponse = (LlWapBankcardResponse) resultMap.get("llWapBankcardResponse");
			llWapBankcardInfo.setBank_code(BankUtil.getLianlianpayWapBankCodeByCode(llWapBankcardResponse.getBank_code()));
			llWapBankcardInfo.setBank_name(BankUtil.getLianlianpayWapBankNameByCode(llWapBankcardResponse.getBank_code()));
		}
		return llWapBankcardInfo;
	}

	@Override
	public String saveBankcardUnbind(LlWapBankcardUnbindRequest llWapBankcardUnbindRequest) throws Exception {
		if (null == llWapBankcardUnbindRequest || null == llWapBankcardUnbindRequest.getUser_id() || "".equals(llWapBankcardUnbindRequest.getUser_id().trim())) {
			return "未找到此用户";
		}
		BankInfoCnd bankInfoCnd = new BankInfoCnd();
		bankInfoCnd.setUserId(Integer.parseInt(llWapBankcardUnbindRequest.getUser_id()));
		bankInfoCnd.setVerifyStatus(Constants.BANK_INFO_VERIFY_STATUS_YES);
		List<BankInfoVo> bankInfoList = bankInfoMapper.queryBankInfoList(bankInfoCnd);
		if (null == bankInfoList || bankInfoList.size() == 0 || bankInfoList.size() > 1) {
			return "未找到用户绑定的银行卡";
		}
		BankInfoVo bankInfoVo = bankInfoList.get(0);
		if (null == bankInfoVo.getNoAgree() || "".equals(bankInfoVo.getNoAgree().trim())) {
			return "用户未签约银行卡协议号,请确认";
		}

		llWapBankcardUnbindRequest.setOid_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_OID_PARTNER);
		llWapBankcardUnbindRequest.setPay_type("D");// 手机认证支付
		llWapBankcardUnbindRequest.setSign_type(BusinessConstants.ONLINE_PAY_LIANLIANPAY_SIGNTYPE);
		llWapBankcardUnbindRequest.setNo_agree(bankInfoVo.getNoAgree());

		// 设置签名密钥
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(FileUtil.readFile(
				LianlianpayWapServiceImpl.class.getResource("/").getPath() + BusinessConstants.ONLINE_PAY_LIANLIANPAY_RPIVATE_KEY).toString()));
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = fact.generatePrivate(spec);
		// 待签名串
		String signContent = LLPayUtil.generateToSignContent(llWapBankcardUnbindRequest);
		String signMsg = SignAndVerify.sign("MD5withRSA", signContent, privateKey);
		llWapBankcardUnbindRequest.setSign(signMsg);

		// 得到请求后的json字符串
		String arguments = JsonUtils.bean2Json(llWapBankcardUnbindRequest);
		String resultJson = HttpURLUtil.doPost(BusinessConstants.ONLINE_PAY_LLWAP_CARD_UNBIND_URL, arguments, "UTF-8");
		LlWapBankcardUnbindResponse llWapBankcardUnbindResponse = JsonUtils.json2Bean(resultJson, LlWapBankcardUnbindResponse.class);
		if (null == llWapBankcardUnbindResponse || null == llWapBankcardUnbindResponse.getRet_code() || !"0000".equals(llWapBankcardUnbindResponse.getRet_code())) {
			return "银行卡信息有误,请确认！";
		}
		// 验签是否成功
		if (!LLPayUtil.validateSignMsg(llWapBankcardUnbindResponse)) {
			return "解绑银行卡安全验证出错，请联系客服！";
		}
		// 去除用户绑定的协议号
		bankInfoMapper.updateNoAgreeByUserId(Integer.parseInt(llWapBankcardUnbindRequest.getUser_id()), null);
		return BusinessConstants.SUCCESS;
	}

	@Override
	public Map<String, Object> savesend(TopupMoneyVo topupMoneyVo, BankInfoVo bankInfoVo, LlWapPaymentRiskItem llWapPaymentRiskItem, String returnURL) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 保留两位小数
		topupMoneyVo.setMoney(topupMoneyVo.getMoney().setScale(2, RoundingMode.DOWN));
		// 最多充值金额100万
		if (topupMoneyVo.getMoney().compareTo(new BigDecimal("1000000")) == 1 || topupMoneyVo.getMoney().compareTo(new BigDecimal("50")) == -1) {
			resultMap.put("result", "充值金额必须小于100万元并且不小于50元");
			return resultMap;
		}
		// 封装支付表单
		LlWapPaymentRequest paymentInfo = this.packageLianlianPayForm(topupMoneyVo, bankInfoVo, returnURL);
		// 得到请求后的json字符串
		String riskItemJson = JsonUtils.bean2Json(llWapPaymentRiskItem);
		paymentInfo.setRisk_item(riskItemJson);
		String lianlianPayRequestString = LLPayUtil.generateWapFormString(paymentInfo, BusinessConstants.ONLINE_PAY_LLWAP_ACTIONURL);
		// 保存充值记录
		this.packageRechargeRecord(topupMoneyVo, bankInfoVo, paymentInfo);

		resultMap.put("lianlianPayRequestString", lianlianPayRequestString);
		return resultMap;
	}

	/**
	 * <p>
	 * Description:封装连连手机支付表单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月28日
	 * @param topupMoneyVo
	 * @param bankInfoVo
	 * @return PaymentInfo
	 * @throws Exception
	 */
	private LlWapPaymentRequest packageLianlianPayForm(TopupMoneyVo topupMoneyVo, BankInfoVo bankInfoVo, String returnURL) throws Exception {
		LlWapPaymentRequest paymentInfo = new LlWapPaymentRequest();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd" + BusinessConstants.ONLINE_PAY_LIANLIANPAY_FORMAT_USER_ID + "HHmmssSSS", Locale.US);
		String merOrderNum = sf.format(new Date()) + "" + topupMoneyVo.getUserId();

		paymentInfo.setVersion(BusinessConstants.ONLINE_PAY_LLWAP_VERSION);
		paymentInfo.setOid_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_OID_PARTNER);
		paymentInfo.setUser_id(String.valueOf(topupMoneyVo.getUserId()));
		paymentInfo.setApp_request(BusinessConstants.ONLINE_PAY_LLWAP_APP_REQUEST);
		paymentInfo.setSign_type(BusinessConstants.ONLINE_PAY_LIANLIANPAY_SIGNTYPE);
		paymentInfo.setBusi_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_BUSI_PARTNER);
		paymentInfo.setNo_order(merOrderNum);
		paymentInfo.setDt_order(DateUtils.format(new Date(), DateUtils.YMDHMS));
		paymentInfo.setName_goods("账户充值");
		paymentInfo.setMoney_order(String.valueOf(topupMoneyVo.getMoney()));
		paymentInfo.setNotify_url(BusinessConstants.ONLINE_PAY_LLWAP_BGURL);

		paymentInfo.setUrl_return(returnURL);
		paymentInfo.setNo_agree(bankInfoVo.getNoAgree());
		paymentInfo.setId_type("0");// 默认为0 身份证
		paymentInfo.setId_no(bankInfoVo.getIdCardNo());
		paymentInfo.setAcct_name(bankInfoVo.getRealName());
		paymentInfo.setCard_no(bankInfoVo.getCardNum());

		return paymentInfo;
	}

	/**
	 * <p>
	 * Description:保存充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param topupMoneyVo
	 * @param bankInfoVo
	 * @param paymentInfo
	 * @throws Exception void
	 */
	private void packageRechargeRecord(TopupMoneyVo topupMoneyVo, BankInfoVo bankInfoVo, LlWapPaymentRequest paymentInfo) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setTradeNo(paymentInfo.getNo_order());
		rechargeRecord.setUserId(topupMoneyVo.getUserId());
		rechargeRecord.setType(Constants.RECHARGE_TYPE_ONLINE);
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
		rechargeRecord.setMoney(topupMoneyVo.getMoney());
		rechargeRecord.setPayment(bankInfoVo.getBankName());
		rechargeRecord.setCardNum(bankInfoVo.getCardNum());
		rechargeRecord.setBankUsername(bankInfoVo.getRealName());
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
		rechargeRecord.setAddip(topupMoneyVo.getAddIp());
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_LIANLIANAPAY);
		rechargeRecord.setVersion(1);
		rechargeRecord.setPlatform(topupMoneyVo.getPlatform());
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}

	/**
	 * <p>
	 * Description:调用连连支付接口查询银行卡信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月8日
	 * @param card_no
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	private Map<String, Object> queryLianlianBankcard(String card_no) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", BusinessConstants.SUCCESS);
		if (null == card_no || "".equals(card_no.trim())) {
			resultMap.put("result", "请输入银行卡号");
			return resultMap;
		}
		LlWapBankcardRequest llWapBankcardRequest = new LlWapBankcardRequest();
		llWapBankcardRequest.setCard_no(card_no.trim());
		llWapBankcardRequest.setOid_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_OID_PARTNER);
		llWapBankcardRequest.setSign_type(BusinessConstants.ONLINE_PAY_LIANLIANPAY_SIGNTYPE);

		// 设置签名密钥
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(FileUtil.readFile(
				LianlianpayWapServiceImpl.class.getResource("/").getPath() + BusinessConstants.ONLINE_PAY_LIANLIANPAY_RPIVATE_KEY).toString()));
		KeyFactory fact = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = fact.generatePrivate(spec);
		String signContent = LLPayUtil.generateToSignContent(llWapBankcardRequest);
		String signMsg = SignAndVerify.sign("MD5withRSA", signContent, privateKey);
		llWapBankcardRequest.setSign(signMsg);

		// 得到请求后的json字符串
		String arguments = JsonUtils.bean2Json(llWapBankcardRequest);
		String resultJson = HttpURLUtil.doPost(BusinessConstants.ONLINE_PAY_LLWAP_CARD_QUERY_URL, arguments, "UTF-8");
		LlWapBankcardResponse llWapBankcardResponse = JsonUtils.json2Bean(resultJson, LlWapBankcardResponse.class);
		// 处理结果 0000：支付成功
		if (null == llWapBankcardResponse) {
			resultMap.put("result", "未找到此银行卡！");
			return resultMap;
		}
		if (null == llWapBankcardResponse.getRet_code() || !"0000".equals(llWapBankcardResponse.getRet_code())) {
			resultMap.put("result", "银行卡信息有误,请确认！");
			return resultMap;
		}
		// 验签是否成功
		if (!LLPayUtil.validateSignMsg(llWapBankcardResponse)) {
			resultMap.put("result", "银行卡安全验证出错，请联系客服！");
			return resultMap;
		}
		String bankName = BankUtil.getLianlianpayWapBankNameByCode(llWapBankcardResponse.getBank_code());
		if (null == bankName || "".equals(bankName)) {
			resultMap.put("result", "不支持此银行！");
			return resultMap;
		}
		// 如果是信用卡
		if (llWapBankcardResponse.getCard_type().equals("3")) {
			resultMap.put("result", "目前暂不支付信用卡！");
			return resultMap;
		}
		resultMap.put("llWapBankcardResponse", llWapBankcardResponse);
		return resultMap;
	}
}
