package com.dxjr.wx.pay.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.ChinabankService;
import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.BankInfoCnd;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.wx.pay.service.OnlinePayWapService;
import com.dxjr.wx.pay.util.OnlineContext;
import com.dxjr.wx.pay.util.OnlineUtil;
import com.dxjr.wx.pay.vo.OnlineCardTrade;

/**
 * @author WangQianJin
 * @title 京东支付手机支付
 * @date 2016年6月1日
 */
@Controller
@RequestMapping(value = "/wx/online")
public class WXOnlinePayWapController extends BaseController {
	public Logger logger = Logger.getLogger(WXOnlinePayWapController.class);
	@Autowired
	private OnlinePayWapService onlinePayWapService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private ChinabankService chinabankService;
	@Autowired
	private MobileApproService mobileApproService;

	/**
	 * 京东支付签约
	 * @author WangQianJin
	 * @title @param money
	 * @title @param tradeCode
	 * @title @param request
	 * @title @return
	 * @date 2016年6月2日
	 */
	@RequestMapping(value = "/sign")
	@ResponseBody
	public Map<String, Object> sign(@RequestParam("money") BigDecimal money, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				map.put("url", "/login");
				map.put("certificationMsg", "请先登录");
				return map;
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
				// 跳手机充值首页
				map.put("url", "/recharge");
				map.put("certificationMsg", "非法用户");
				return map;
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				map.put("url", "/account/safeCenter/validateMobilePage");
				map.put("certificationMsg", "请先进行手机认证");
				return map;
			}
			// 判断是否通过了实名认证,手机支付必须进行实名认证，因风控参数需要
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				// 跳手机实名认证
				map.put("url", "/account/safeCenter/validateRealNamePage");
				map.put("certificationMsg", "请先进行实名认证");
				return map;
			}
			// 判断是否绑定了银行卡
			BankInfoCnd bankInfoCnd = new BankInfoCnd();
			bankInfoCnd.setUserId(shiroUser.getUserId());
			bankInfoCnd.setVerifyStatus(Constants.BANK_INFO_VERIFY_STATUS_YES);
			List<BankInfoVo> bankInfoList = bankInfoService.queryBankInfoList(bankInfoCnd);
			if (null == bankInfoList || bankInfoList.size() == 0 || bankInfoList.size() > 1) {
				// 跳转到银行卡手机绑定页面
				map.put("url", "/account/bankCard");
				map.put("certificationMsg", "请先绑定银行卡");
				return map;
			}
			// 不支持pc端充值
			if (null == shiroUser.getPlatform() || shiroUser.getPlatform().equals(1)) {
				map.put("url", "/recharge");
				map.put("certificationMsg", "不支持PC端充值");
				return map;
			}
			// 充值金额校验
			if(money==null || money.doubleValue()<=0){
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额不能小于等于0");
				return map;
	        }
			// 最多充值金额50元到100万
			if (money.compareTo(new BigDecimal("1000000")) == 1 || money.compareTo(new BigDecimal("50")) == -1) {				
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额必须小于100万元并且不小于50元");
				return map;
			}
			if(!validateMoney(money)){
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额最多只能保留两位小数");
				return map;
			}
			/*封装银行卡交易信息*/
			BankInfoVo bank=bankInfoList.get(0);
			//交易ID
			String tradeId=OnlineUtil.getTradeId(shiroUser.getUserId());
			//V表示签约，S表示消费
			OnlineCardTrade cardTrade=getOnlineCardTrade(bank,memberApproVo.getMobilenum(),OnlineContext.ONLINE_PAY_CHINABANK_TRADE_V,tradeId,money,null);	
			//调用京东支付签约接口获取手机验证码
			Map<String, String> result=onlinePayWapService.signTransaction(cardTrade);
			//封装接口返回信息
			ChinabankReceiveForm chinabankReceiveForm=getOnlineReturnResult(result);
			//保存签约返回信息
			chinabankService.saveAccountRechargeFeedback(chinabankReceiveForm);
			if (!OnlineContext.ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS.equals(chinabankReceiveForm.getRemark1())) {
				// 返回到手机充值失败页面
				map.put("url", "/recharge");
				map.put("certificationMsg", chinabankReceiveForm.getRemark2());
				return map;
			}
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			if (mobileAppro != null) {
				map.put("mobileNum", mobileAppro.getSecuritymobileNum());
			}
			map.put("certificationMsg", BusinessConstants.SUCCESS);
			map.put("tradeId", tradeId);
			return map;
		} catch (Exception e) {
			logger.error("使用京东支付手机支付签约报错", e);
			map.put("url", "/recharge");
			map.put("certificationMsg", "使用京东支付手机支付签约报错");
			return map;
		}
	}
	
	
	/**
	 * 京东支付支付
	 * @author WangQianJin
	 * @title @param money
	 * @title @param tradeCode
	 * @title @param request
	 * @title @return
	 * @date 2016年6月2日
	 */
	@RequestMapping(value = "/payment")
	@ResponseBody
	public Map<String, Object> payment(@RequestParam("money") BigDecimal money,@RequestParam("tradeCode") String tradeCode,@RequestParam("tradeId") String tradeId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				map.put("url", "/login");
				map.put("certificationMsg", "请先登录");
				return map;
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
				// 跳手机充值首页
				map.put("url", "/recharge");
				map.put("certificationMsg", "非法用户");
				return map;
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				map.put("url", "/account/safeCenter/validateMobilePage");
				map.put("certificationMsg", "请先进行手机认证");
				return map;
			}
			// 判断是否通过了实名认证,手机支付必须进行实名认证，因风控参数需要
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				// 跳手机实名认证
				map.put("url", "/account/safeCenter/validateRealNamePage");
				map.put("certificationMsg", "请先进行实名认证");
				return map;
			}
			// 判断是否绑定了银行卡
			BankInfoCnd bankInfoCnd = new BankInfoCnd();
			bankInfoCnd.setUserId(shiroUser.getUserId());
			bankInfoCnd.setVerifyStatus(Constants.BANK_INFO_VERIFY_STATUS_YES);
			List<BankInfoVo> bankInfoList = bankInfoService.queryBankInfoList(bankInfoCnd);
			if (null == bankInfoList || bankInfoList.size() == 0 || bankInfoList.size() > 1) {
				// 跳转到银行卡手机绑定页面
				map.put("url", "/account/bankCard");
				map.put("certificationMsg", "请先绑定银行卡");
				return map;
			}
			// 不支持pc端充值
			if (null == shiroUser.getPlatform() || shiroUser.getPlatform().equals(1)) {
				map.put("url", "/recharge");
				map.put("certificationMsg", "不支持PC端充值");
				return map;
			}
			// 充值金额校验
			if(money==null || money.doubleValue()<=0){
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额不能小于等于0");
				return map;
	        }
			// 最多充值金额50元到100万
			if (money.compareTo(new BigDecimal("1000000")) == 1 || money.compareTo(new BigDecimal("50")) == -1) {				
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额必须小于100万元并且不小于50元");
				return map;
			}
			if(!validateMoney(money)){
				map.put("url", "/recharge");
				map.put("certificationMsg", "充值金额最多只能保留两位小数");
				return map;
			}
			/*封装银行卡交易信息*/
			BankInfoVo bank=bankInfoList.get(0);
			/*保存充值记录*/ 
			TopupMoneyVo topupMoneyVo = new TopupMoneyVo();
			topupMoneyVo.setBankcode(bank.getBankCode());
			topupMoneyVo.setMoney(money);
			topupMoneyVo.setUserId(shiroUser.getUserId());
			topupMoneyVo.setAddIp(HttpTookit.getRealIpAddr(request));
			topupMoneyVo.setPlatform(shiroUser.getPlatform());	
			topupMoneyVo.setTrade_no(tradeId);
			topupMoneyVo.setCardNum(bank.getCardNum());
			topupMoneyVo.setBankUsername(bank.getRealName());
			Map<String, Object> resultMap = onlinePayWapService.savesend(shiroUser.getUserId(), topupMoneyVo, request);
			if (!resultMap.get("result").equals("success")) {
				// 返回到手机充值失败页面
				map.put("url", "/recharge");
				map.put("certificationMsg", String.valueOf(resultMap.get("result")));
				return map;
			}			
			//V表示签约，S表示消费
			OnlineCardTrade cardTrade=getOnlineCardTrade(bank,memberApproVo.getMobilenum(),OnlineContext.ONLINE_PAY_CHINABANK_TRADE_S,tradeId,money,tradeCode);			
			//调用京东支付签约接口获取手机验证码
			Map<String, String> result=onlinePayWapService.paymentTransaction(cardTrade);
			//封装接口返回信息
			ChinabankReceiveForm chinabankReceiveForm=getOnlineReturnResult(result);
			// 保存支付返回信息
			chinabankService.saveAccountRechargeFeedback(chinabankReceiveForm);
			
			// 判断银行返回的信息是否正确
			if (!OnlineContext.ONLINE_PAY_CHINABANK_STATUS_SUCCESS.equals(chinabankReceiveForm.getV_pstatus()) 
					|| !OnlineContext.ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS.equals(chinabankReceiveForm.getRemark1())) {
				// 返回到手机充值失败页面
				map.put("url", "/recharge");
				map.put("returnStatus", chinabankReceiveForm.getV_pstatus());
				map.put("returnCode", chinabankReceiveForm.getRemark1());
				map.put("certificationMsg", chinabankReceiveForm.getRemark2());
				return map;
			}
			
			// 接收支付信息，更新用户帐号和充值状态
			String msg = onlinePayWapService.saveAutoReceive(chinabankReceiveForm, request);
			if (!BusinessConstants.SUCCESS.equals(msg)) {
				// 返回到手机充值失败页面
				map.put("url", "/recharge");
				map.put("returnStatus", chinabankReceiveForm.getV_pstatus());
				map.put("returnCode", chinabankReceiveForm.getRemark1());
				map.put("certificationMsg", msg);
				return map;
			}
			map.put("certificationMsg", BusinessConstants.SUCCESS);
			return map;
		} catch (Exception e) {
			logger.error("使用京东支付手机支付充值报错", e);
			map.put("url", "/recharge");
			map.put("certificationMsg", "使用京东支付手机支付充值报错");
			return map;
		}
	}
	
	/**
	 * 京东支付异步回调
	 * @author WangQianJin
	 * @title @param money
	 * @title @param tradeCode
	 * @title @param request
	 * @title @return
	 * @date 2016年6月2日
	 */
	@RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ResponseBody
    public String receive(HttpServletRequest request) {
        logger.info("[京东支付]消费异步通知接口开始执行---------------------------");
        //处理交易结果
        //数据格式是resp=XML数据
        try {
            String resp = request.getParameter("resp");
            if(StringUtil.isEmpty(resp)){
                logger.info("异步通知报文为空!");
                return "failed";
            }
            Map<String, String> resultMap=onlinePayWapService.getAsynReturnResult(resp); 
            //封装接口返回信息
			ChinabankReceiveForm chinabankReceiveForm=getAsynOnlineReturnResult(resultMap);
			// 保存支付返回信息
			chinabankService.saveAccountRechargeFeedback(chinabankReceiveForm);
            //6. 调用[京东支付]消费接口进行充值,返回充值结果
            if (OnlineContext.ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS.equals(resultMap.get("data.return.code"))) {
                if(OnlineContext.ONLINE_PAY_CHINABANK_STATUS_SUCCESS.equals(resultMap.get("data.trade.status"))){                    
        			// 接收支付信息，更新用户帐号和充值状态
        			String msg = onlinePayWapService.saveAutoReceive(chinabankReceiveForm, request);
        			logger.info("[京东支付]消费异步通知接口结束执行---------------------------");
                    return BusinessConstants.SUCCESS;
                } else if (OnlineContext.ONLINE_PAY_CHINABANK_STATUS_DEALING.equals(resultMap.get("data.return.status"))){
                    //处理中,会继续通知
                    return "dealing";
                }else if(OnlineContext.ONLINE_PAY_CHINABANK_STATUS_MONEYBACK.equals(resultMap.get("data.return.status"))
                        || OnlineContext.ONLINE_PAY_CHINABANK_STATUS_PARTBACK.equals(resultMap.get("data.return.status"))){
                    //退款或部分退款,公司无退款业务
                    return "failed";
                }else {
                    //失败,需继续通知?
                    return "failed";
                }
            } else {
                //继续通知
                logger.info(resultMap.get("data.return.desc"));
                return "failed";
            }
        } catch (Exception e) {
            logger.error("网络异常!", e);
            return "failed";
        }        
	}
	
	/**
	 * 跳转到京东支付的补单页面
	 * @author WangQianJin
	 * @title @return
	 * @date 2016年6月16日
	 */
	@RequestMapping(value = "toLoseOrderIndex")
	public ModelAndView toLoseOrder() {
		ModelAndView mv = new ModelAndView("account/topup/jingdong/loseOrderIndex");
		return mv;
	}
	
	/**
	 * 京东支付提交补单
	 * @author WangQianJin
	 * @title @param v_oid
	 * @title @return
	 * @date 2016年6月16日
	 */
	@RequestMapping(value = "loseSend", method = RequestMethod.POST)
	@ResponseBody
	public String loseSend(HttpServletRequest request,String tradeId) {
		try {
	        if(StringUtil.isEmpty(tradeId)){
                return "订单号不能为空!";
            }
	        //封装请求信息
			OnlineCardTrade cardTrade= new OnlineCardTrade();
			cardTrade.setTradeType(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_Q);//Q表示查询	
			cardTrade.setTradeId(tradeId);			
			//调用京东支付查询接口
			Map<String, String> result=onlinePayWapService.queryTransaction(cardTrade);
			//封装接口返回信息
			ChinabankReceiveForm chinabankReceiveForm=getOnlineReturnResult(result);
			// 保存支付返回信息
			chinabankService.saveAccountRechargeFeedback(chinabankReceiveForm);
	        // 判断银行返回的信息是否正确
			if (!OnlineContext.ONLINE_PAY_CHINABANK_STATUS_SUCCESS.equals(chinabankReceiveForm.getV_pstatus()) 
					|| !OnlineContext.ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS.equals(chinabankReceiveForm.getRemark1())) {
				return "补单失败【INTERFACE:status-"+chinabankReceiveForm.getV_pstatus()+ "; code-"+chinabankReceiveForm.getRemark1()+"; desc-" + chinabankReceiveForm.getRemark2() + "】";
			}
			
			// 更新用户帐号和充值状态
			String msg = onlinePayWapService.saveAutoReceive(chinabankReceiveForm, request);
			if (!BusinessConstants.SUCCESS.equals(msg)) {			
				return "补单失败【PLATFORM:" + msg + "】";
			}
			
			return "补单成功";
		} catch (Exception e) {
			logger.error("京东支付补单异常", e);
			return "京东支付补单失败：系统异常";
		}
	}
	
	/**
	 * 校验金额
	 * @author WangQianJin
	 * @title @param money
	 * @title @return
	 * @date 2016年6月14日
	 */
	private boolean validateMoney(BigDecimal money){
		if(money==null){
			return false;
		}
		String strMoney=String.valueOf(money);
		int index=strMoney.indexOf(".");
		if(index > 0){
			//最多只能保留两位小数
			String temp=strMoney.substring(index);
			if(temp.length()>3){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 封装银行卡信息
	 * @author WangQianJin
	 * @title @param result
	 * @title @return
	 * @date 2016年6月1日
	 */
	private OnlineCardTrade getOnlineCardTrade(BankInfoVo bank,String mobile,String tradeType,String tradeId,BigDecimal money,String tradeCode){
		OnlineCardTrade cardTrade=new OnlineCardTrade();
		cardTrade.setCardBank(bank.getBankCode());
		cardTrade.setCardType(OnlineContext.ONLINE_PAY_CHINABANK_CARDTYPE_D);//信用卡：C / 借记卡：D
		cardTrade.setCardNo(bank.getCardNum());
		cardTrade.setCardName(bank.getRealName());
		cardTrade.setCardIdtype(OnlineContext.ONLINE_PAY_CHINABANK_IDTYPE_I);//I表示身份证
		cardTrade.setCardIdno(bank.getIdCardNo());
		cardTrade.setCardPhone(mobile);
		cardTrade.setTradeType(tradeType);//V表示签约，S表示消费		
		//京东支付是以分为单位，需要对充值的金额进行处理
		BigDecimal fenMoney = money.multiply(new BigDecimal(100));
		cardTrade.setTradeAmount(String.valueOf(fenMoney.intValue()));
		cardTrade.setTradeId(tradeId);		
		cardTrade.setTradeCurrency(OnlineContext.ONLINE_PAY_CHINABANK_CURRENCY);//CNY表示人民币
		cardTrade.setTradeDate(DateUtils.format(new Date(), DateUtils.YMD));
		cardTrade.setTradeTime(DateUtils.format(new Date(), "HHmmss"));
		//手机验证码
		cardTrade.setTradeCode(tradeCode);
		//调用消费接口才需要异步回调地址
		if(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_S.equals(tradeType)){
			cardTrade.setTradeNotice(OnlineContext.notice_url);
		}	
		return cardTrade;
	}
	
	/**
	 * 根据京东支付返回结果封装信息
	 * @author WangQianJin
	 * @title @param result
	 * @title @return
	 * @date 2016年6月1日
	 */
	private ChinabankReceiveForm getOnlineReturnResult(Map<String, String> result){
		ChinabankReceiveForm chinabankReceiveForm = new ChinabankReceiveForm();
		chinabankReceiveForm.setV_oid(result.get("data.trade.id")); // 订单号
		chinabankReceiveForm.setV_pmode(result.get("data.trade.type")); // 支付方式
		chinabankReceiveForm.setV_pstatus(result.get("data.trade.status")); // 支付结果，0支付完成
		chinabankReceiveForm.setV_pstring(result.get("data.trade.note")); // 对支付结果的说明
		BigDecimal yuanMoney=BigDecimal.ZERO;
		if(result.get("data.trade.amount")!=null && !"".equals(result.get("data.trade.amount"))){
			//京东支付返回的是分，精确到元
			yuanMoney= new BigDecimal(result.get("data.trade.amount")).divide(new BigDecimal(100));	        
		}	
		chinabankReceiveForm.setV_amount(String.valueOf(yuanMoney)); // 订单实际支付金额
		chinabankReceiveForm.setV_moneytype(result.get("data.trade.currency")); // 币种
		chinabankReceiveForm.setV_md5str(result.get("data.chinabank.sign")); // MD5校验码
		chinabankReceiveForm.setRemark1(result.get("data.return.code")); // 备注1
		chinabankReceiveForm.setRemark2(result.get("data.return.desc")); // 备注2
		return chinabankReceiveForm;
	}
	
	/**
	 * 根据京东支付异步返回结果封装信息
	 * @author WangQianJin
	 * @title @param result
	 * @title @return
	 * @date 2016年6月1日
	 */
	private ChinabankReceiveForm getAsynOnlineReturnResult(Map<String, String> result){
		ChinabankReceiveForm chinabankReceiveForm = new ChinabankReceiveForm();
		chinabankReceiveForm.setV_oid(result.get("data.trade.id")); // 订单号
		chinabankReceiveForm.setV_pmode(result.get("data.trade.type")); // 支付方式
		chinabankReceiveForm.setV_pstatus(result.get("data.trade.status")); // 支付结果，0支付完成
		chinabankReceiveForm.setV_pstring(result.get("data.trade.note")); // 对支付结果的说明
		BigDecimal yuanMoney=BigDecimal.ZERO;
		if(result.get("data.trade.amount")!=null && !"".equals(result.get("data.trade.amount"))){
			//京东支付返回的是分，精确到元
			yuanMoney= new BigDecimal(result.get("data.trade.amount")).divide(new BigDecimal(100));	        
		}	
		chinabankReceiveForm.setV_amount(String.valueOf(yuanMoney)); // 订单实际支付金额
		chinabankReceiveForm.setV_moneytype(result.get("data.trade.currency")); // 币种
		chinabankReceiveForm.setV_md5str(result.get("data.chinabank.sign")); // MD5校验码
		chinabankReceiveForm.setRemark1(result.get("data.return.code")); // 备注1
		chinabankReceiveForm.setRemark2("异步返回结果"); // 备注2
		return chinabankReceiveForm;
	}
}
