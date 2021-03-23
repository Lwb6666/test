package com.dxjr.wx.pay.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
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
import com.dxjr.wx.pay.service.MotoPayService;
import com.dxjr.wx.pay.service.OnlinePayWapService;
import com.dxjr.wx.pay.util.OnlineContext;
import com.dxjr.wx.pay.vo.OnlineCardTrade;

/**
 * @author WangQianJin
 * @title 网银在线手机支付服务接口
 * @date 2016年6月1日
 */
@Service
public class OnlinePayWapServiceImpl implements OnlinePayWapService {
	
	/**
	 * 网银在线服务
	 */
	MotoPayService service = new MotoPayService();
	
	@Autowired
	private RechargeRecordService rechargeRecordService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountLogService accountLogService;
	
	@Autowired
	private BaseRechargeRecordMapper baseRechargeRecordMapper;
	
	@Autowired
	private BaseAccountMapper baseAccountMapper;

	/**
	 * 签约交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> signTransaction(OnlineCardTrade cardTrade){
		String respStr = service.trade(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_V,cardTrade);
		return service.operate(respStr);
	}
	
	/**
	 * 支付交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> paymentTransaction(OnlineCardTrade cardTrade){
		String respStr = service.trade(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_S,cardTrade);
		return service.operate(respStr);
	}
	
	/**
	 * 查询交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> queryTransaction(OnlineCardTrade cardTrade){
		String respStr = service.trade(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_Q,cardTrade);
		return service.operate(respStr);
	}
	
	/**
	 * 根据异步返回结果进行解析
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> getAsynReturnResult(String respStr){
		return service.operateAsyn(respStr);
	}
	
	@Override
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		// 验证是否是支持的银行
		String bankName = BankUtil.getOnlineBankNameByBankCode(topupMoneyVo.getBankcode());
		if (null == bankName || "".equals(bankName)) {
			resultMap.put("result", "不支持此银行！");
			return resultMap;
		}		
		// 封装网银在线支付表单
		ChinabankPayForm payForm = packageChinabankPayForm(userId, topupMoneyVo, request);
		// 保存充值记录
		packageRechargeRecord(userId, topupMoneyVo, request, bankName, payForm);

		resultMap.put("chinabankPayForm", payForm);
		return resultMap;
	}
	
	/**
	 * 接收支付信息，更新用户帐号和充值状态
	 * @author WangQianJin
	 * @title @param chinabankReceiveForm
	 * @title @param request
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月1日
	 */
	@Override
	public String saveAutoReceive(ChinabankReceiveForm chinabankReceiveForm, HttpServletRequest request) throws Exception {
		String result = "success";
		// 判断返回的状态和返回码是否正确
		if (null == chinabankReceiveForm || null == chinabankReceiveForm.getV_pstatus() 
				|| !OnlineContext.ONLINE_PAY_CHINABANK_STATUS_SUCCESS.equals(chinabankReceiveForm.getV_pstatus()) 
				|| !OnlineContext.ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS.equals(chinabankReceiveForm.getRemark1())) {
			return "PstatusOrPcodeError";
		}
		// 找到充值记录并锁定
		RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
		rechargeRecordCnd.setTradeNo(chinabankReceiveForm.getV_oid());
		rechargeRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
		// 如果不是待付款且金额不相等
		if (null == rechargeRecordVo || null == rechargeRecordVo.getStatus() || rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT
				|| rechargeRecordVo.getMoney().doubleValue()!=Double.parseDouble(chinabankReceiveForm.getV_amount())) {
			return "PayERROR";// 支付失败
		}
		// 支付成功，更新充值记录的状态为成功
		refreshRechargeStatus(rechargeRecordVo);
		// 手续费千分之二,最低1分钱
		BigDecimal fee = rechargeRecordVo.getFee();
		AccountVo sourceAccount = accountService.queryAccountByUserIdForUpdate(rechargeRecordVo.getUserId());
		// 更新用户帐号的可用余额和总金额，得到扣除手续费后的总金额
		refreshAccountMoney(rechargeRecordVo, fee, sourceAccount);
		sourceAccount.setUseMoney(sourceAccount.getUseMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setNoDrawMoney(sourceAccount.getNoDrawMoney().add(rechargeRecordVo.getMoney()));
		sourceAccount.setTotal(sourceAccount.getTotal().add(rechargeRecordVo.getMoney()));
		// 插入资金记录
		StringBuilder remarkbuilder = new StringBuilder("在线充值成功,本次使用的是".concat("京东支付").concat("进行充值."));
		accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(), rechargeRecordVo.getMoney(), remarkbuilder.toString(), HttpTookit.getRealIpAddr(request), "online_recharge",
				null, null, null);
		return result;
	}
	
	/**
	 * 更新充值记录的状态为成功
	 * @author WangQianJin
	 * @title @param rechargeRecordVo
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月1日
	 */
	private Integer refreshRechargeStatus(RechargeRecordVo rechargeRecordVo) throws Exception {
		RechargeRecord updateRechargeRecord = new RechargeRecord();
		updateRechargeRecord.setId(rechargeRecordVo.getId());
		updateRechargeRecord.setStatus(Constants.RECHARGE_STATUS_SUCCESS);
		updateRechargeRecord.setVerifyTime(DateUtils.getTime());
		updateRechargeRecord.setVerifyRemark("京东支付".concat("调用接口调用自动审核"));
		updateRechargeRecord.setVerifyTime2(DateUtils.getTime());
		updateRechargeRecord.setVerifyRemark2("京东支付".concat("调用接口调用自动审核"));
		Integer updateCount = baseRechargeRecordMapper.updateEntity(updateRechargeRecord);
		return updateCount;
	}
	
	/**
	 * 更新用户帐号的可用余额和总金额
	 * @author WangQianJin
	 * @title @param rechargeRecordVo
	 * @title @param fee
	 * @title @param sourceAccount
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月1日
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
		String v_oid = topupMoneyVo.getTrade_no();

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
		rechargeRecord.setCardNum(topupMoneyVo.getCardNum());
		rechargeRecord.setBankUsername(topupMoneyVo.getBankUsername());
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}
}
