package com.dxjr.wx.pay.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.lianlian.controller.LianlianpayWapController;
import com.dxjr.portal.lianlian.service.LianlianpayWapService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LlWapPaymentRiskItem;
import com.dxjr.portal.lianlian.vo.LlWapReceiveFormResponse;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoCnd;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;

/**
 * <p>
 * Description:连连手机支付控制类<br />
 * </p>
 * 
 * @title LianlianWapController.java
 * @package com.dxjr.wx.pay.controller
 * @author justin.xu
 * @version 0.1 2015年4月8日
 */
@Controller
@RequestMapping(value = "/wx/pay")
public class WXLianlianpayWapController extends BaseController {
	public Logger logger = Logger.getLogger(LianlianpayWapController.class);
	@Autowired
	private LianlianpayWapService lianlianpayWapService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BankInfoService bankInfoService;

	/**
	 * <p>
	 * Description:发送支付请求前验证是否合法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月8日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/send")
	@ResponseBody
	public Map<String, Object> send(@RequestParam("money") BigDecimal money, HttpServletRequest request) {
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
			TopupMoneyVo topupMoneyVo = new TopupMoneyVo();
			topupMoneyVo.setMoney(money);
			topupMoneyVo.setUserId(shiroUser.getUserId());
			topupMoneyVo.setAddIp(HttpTookit.getRealIpAddr(request));
			topupMoneyVo.setPlatform(shiroUser.getPlatform());
			// 保存充值记录
			LlWapPaymentRiskItem llWapPaymentRiskItem = new LlWapPaymentRiskItem();
			llWapPaymentRiskItem.setFrms_ware_category("2009");// 代表p2p小额贷款
			llWapPaymentRiskItem.setUser_info_mercht_userno(String.valueOf(shiroUser.getUserId()));
			if (null != memberApproVo.getMobilenum()) {
				llWapPaymentRiskItem.setUser_info_bind_phone(memberApproVo.getMobilenum());
			}
			llWapPaymentRiskItem.setUser_info_dt_register(DateUtils.format(memberApproVo.getUserAddtimeDate(), DateUtils.YMDHMS));
			llWapPaymentRiskItem.setUser_info_full_name(memberApproVo.getRealname());
			llWapPaymentRiskItem.setUser_info_id_type(memberApproVo.getCardTypeForPhonePay());
			llWapPaymentRiskItem.setUser_info_id_no(memberApproVo.getIdcardno());
			llWapPaymentRiskItem.setUser_info_identify_state("1");
			llWapPaymentRiskItem.setUser_info_identify_type("3");
			
			String returnURL = BusinessConstants.ONLINE_PAY_LLWAP_FRONTURL_PREFIX_M;
			if (Integer.valueOf(3).equals(shiroUser.getPlatform())) {
				returnURL = BusinessConstants.ONLINE_PAY_LLWAP_FRONTURL_PREFIX_ANDROID;
			} else if (Integer.valueOf(4).equals(shiroUser.getPlatform())) {
				returnURL = BusinessConstants.ONLINE_PAY_LLWAP_FRONTURL_PREFIX_IOS;
			}
			String rechargetype = request.getParameter("rechargetype");
			if ("1".equals(rechargetype)) {
				returnURL += BusinessConstants.ONLINE_PAY_LLWAP_FRONTURL_ADVERTISED;
			} else {
				returnURL += BusinessConstants.ONLINE_PAY_LLWAP_FRONTURL;
			}
			Map<String, Object> resultMap = lianlianpayWapService.savesend(topupMoneyVo, bankInfoList.get(0), llWapPaymentRiskItem, returnURL);
			if (!resultMap.get("result").equals("success")) {
				// 返回到手机充值失败页面
				map.put("url", "/recharge");
				map.put("certificationMsg", String.valueOf(resultMap.get("result")));
				return map;
			}
			map.put("lianlianPayRequestString", resultMap.get("lianlianPayRequestString"));
			map.put("certificationMsg", BusinessConstants.SUCCESS);
			return map;
		} catch (Exception e) {
			logger.error("使用连连手机支付进行在线充值，进入银联充值页面报错", e);
			map.put("url", "/recharge");
			map.put("certificationMsg", "使用连连手机支付进行在线充值，进入银联充值页面报错");
			return map;
		}
	}

	/**
	 * <p>
	 * Description:连连手机支付客户充值完调用查看返回商户页面执行的方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param response
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "recevie")
	@ResponseBody
	public Map<String, Object> recevie(HttpServletRequest request, LlWapReceiveFormResponse llWapReceiveFormResponse) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("certificationMsg", "failure");
		// 支付失败
		try {
			LianlianPayDataResponse lianlianPayDataResponse = JsonUtils.json2Bean(StringEscapeUtils.unescapeHtml4(llWapReceiveFormResponse.getRes_data()), LianlianPayDataResponse.class);
			if (null != lianlianPayDataResponse && null != lianlianPayDataResponse.getResult_pay()) {
				if ("SUCCESS".equals(lianlianPayDataResponse.getResult_pay())) {// 支付成功
					// 验签是否成功
					if (!LLPayUtil.validateSignMsg(lianlianPayDataResponse)) {
						map.put("certificationMsg", "success");
						return map;
					}
				}
			}
		} catch (Exception e) {
			logger.error("返回到支付页面出错", e);
			map.put("certificationMsg", "返回到支付页面出错");
		}
		return map;
	}
}
