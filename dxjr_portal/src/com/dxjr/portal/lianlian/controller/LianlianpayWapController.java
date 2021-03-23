package com.dxjr.portal.lianlian.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.lianlian.service.LianlianpayWapService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LlWapBankcardInfo;
import com.dxjr.portal.lianlian.vo.LlWapBankcardUnbindRequest;
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
 * Description:连连支付充值Controller<br />
 * </p>
 * 
 * @title LianlianpayController.java
 * @package com.dxjr.portal.lianlian.controller
 * @author justin.xu
 * @version 0.1 2014年10月24日
 */
@Controller
@RequestMapping(value = "/account/topup/llwap")
public class LianlianpayWapController extends BaseController {
	private final static Logger logger = Logger.getLogger(LianlianpayWapController.class);

	@Autowired
	private LianlianpayWapService lianlianpayWapService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BankInfoService bankInfoService;

	/**
	 * <p>
	 * Description:更新银行卡确认状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月1日
	 * @return String
	 */
	@RequestMapping(value = "updateBankcardnoVerifyStatus")
	@ResponseBody
	public String updateBankcardnoVerifyStatus() {
		StringBuffer resultSb = new StringBuffer("未更新的id:");
		Integer totalSuccessCount = 0;
		List<BankInfoVo> list = new ArrayList<BankInfoVo>();
		try {
			BankInfoCnd bankInfoCnd = new BankInfoCnd();
			bankInfoCnd.setVerifyStatus(Constants.BANK_INFO_VERIFY_STATUS_NO);
			list = bankInfoService.queryBankInfoList(bankInfoCnd);
			if (null != list && list.size() > 0) {
				for (BankInfoVo bankInfoVo : list) {
					String result = lianlianpayWapService.querybankcard(bankInfoVo.getCardNum());
					if (result.equals(BusinessConstants.SUCCESS)) {
						// 更新验证状态【为 1：验证通过】
						Integer updateCount = bankInfoService.updateBankInfoStatus(bankInfoVo.getId(), Constants.BANK_INFO_VERIFY_STATUS_YES);
						Thread.sleep(200);
						totalSuccessCount = totalSuccessCount + updateCount;
					} else {
						resultSb.append("" + bankInfoVo.getId() + "***原因:" + result + "***\n");
					}
				}
			}
		} catch (Exception e) {
			resultSb.append(e.getMessage());
			e.printStackTrace();
		}
		return "总条数:" + list.size() + ",成功条数:" + totalSuccessCount + "," + resultSb.toString();
	}

	/**
	 * <p>
	 * Description:跳转到连连手机支付页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toIndex")
	public ModelAndView toSinapay() {
		ModelAndView mv = new ModelAndView("account/topup/llwap/llpayIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:银行卡卡bin信息查询<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月25日
	 * @param llWapBankcardRequest
	 * @return String
	 */
	@RequestMapping(value = "querybankcard")
	@ResponseBody
	public String querybankcard(String card_no, HttpServletRequest request) {
		try {
			String result = lianlianpayWapService.querybankcard(card_no);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return result;
			}
		} catch (Exception e) {
			logger.error("验证银行卡出错", e);
			return "验证银行卡出错";
		}
		return "OK";
	}

	/**
	 * <p>
	 * Description:银行卡卡bin信息查询，返回银行卡业务信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月25日
	 * @param llWapBankcardRequest
	 * @return String
	 */
	@RequestMapping(value = "querybankcardInfo")
	@ResponseBody
	public LlWapBankcardInfo querybankcardInfo(String card_no, HttpServletRequest request) {
		LlWapBankcardInfo llWapBankcardInfo = null;
		try {
			llWapBankcardInfo = lianlianpayWapService.queryBankcardInfo(card_no);
		} catch (Exception e) {
			logger.error("查询银行卡出错", e);
		}
		return llWapBankcardInfo;
	}

	/**
	 * <p>
	 * Description:用户银行卡解绑<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月25日
	 * @param platform 平台来源
	 * @return String
	 */
	@RequestMapping(value = "bankcardUnbind")
	@ResponseBody
	// @RequiresAuthentication
	public String bankcardUnbind(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId) {
		try {
			ShiroUser shiroUser = currentUser();
			LlWapBankcardUnbindRequest llWapBankcardUnbindRequest = new LlWapBankcardUnbindRequest();
			llWapBankcardUnbindRequest.setUser_id(userId != null ? userId : String.valueOf(shiroUser.getUserId()));
			String result = lianlianpayWapService.saveBankcardUnbind(llWapBankcardUnbindRequest);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return result;
			}
		} catch (Exception e) {
			logger.error("用户银行卡解绑出错", e);
			return "用户银行卡解绑出错";
		}
		return "OK";
	}

	/**
	 * <p>
	 * Description:使用连连支付进行在线充值，进入银联充值页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param topupMoneyVo
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send")
	@RequiresAuthentication
	public ModelAndView send(TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("account/topup/llwap/llpaySend");
		try {
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
				// 跳手机充值首页
				mav.setViewName("redirect:/account/topup/toTopupIndex.html");
				return mav;
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				// 跳手机安全中心
				return redirect("/AccountSafetyCentre/safetyIndex.html");
			}

			// 判断是否通过了实名认证,手机支付必须进行实名认证，因风控参数需要
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				// 跳手机实名认证
				return redirect("/account/approve/realname/toRealNameAppro.html");
			}

			// 判断是否绑定了银行卡
			BankInfoCnd bankInfoCnd = new BankInfoCnd();
			bankInfoCnd.setUserId(shiroUser.getUserId());
			bankInfoCnd.setVerifyStatus(Constants.BANK_INFO_VERIFY_STATUS_YES);
			List<BankInfoVo> bankInfoList = bankInfoService.queryBankInfoList(bankInfoCnd);
			if (null == bankInfoList || bankInfoList.size() == 0 || bankInfoList.size() > 1) {
				// To do 跳转到银行卡手机绑定页面
				return redirect("/account/approve/realname/toRealNameAppro.html");
			}

			// 不支持pc端充值
			if (null == shiroUser.getPlatform() || shiroUser.getPlatform().equals(1)) {
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}

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
			Map<String, Object> resultMap = lianlianpayWapService.savesend(topupMoneyVo, bankInfoList.get(0), llWapPaymentRiskItem, BusinessConstants.ONLINE_PAY_LIANLIANPAY_FRONTURL);
			if (!resultMap.get("result").equals("success")) {
				// 返回到手机充值失败页面
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}
			mav.addObject("lianlianPayRequestString", resultMap.get("lianlianPayRequestString"));
		} catch (Exception e) {
			logger.error("使用连连手机支付进行在线充值，进入银联充值页面报错", e);
			// 返回到手机充值失败页面
			mav.setViewName("account/topup/topupResult");
			mav.addObject("topupResult", "failure");
			return mav;
		}
		return mav;
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
	 * @return ModelAndView
	 */
	@RequestMapping(value = "recevie")
	public ModelAndView recevie(HttpServletRequest request, LlWapReceiveFormResponse llWapReceiveFormResponse) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		// 支付失败
		String topupResult = "failure";
		try {
			LianlianPayDataResponse lianlianPayDataResponse = JsonUtils.json2Bean(StringEscapeUtils.unescapeHtml4(llWapReceiveFormResponse.getRes_data()), LianlianPayDataResponse.class);
			if (null != lianlianPayDataResponse && null != lianlianPayDataResponse.getResult_pay()) {
				if ("SUCCESS".equals(lianlianPayDataResponse.getResult_pay())) {// 支付成功
					// 验签是否成功
					if (!LLPayUtil.validateSignMsg(lianlianPayDataResponse)) {
						mv.addObject("topupResult", topupResult);
						return mv;
					}
					topupResult = "success";
				}
			}
		} catch (Exception e) {
			logger.error("返回到支付页面出错", e);
		}
		mv.addObject("topupResult", topupResult);
		return mv;
	}

}
