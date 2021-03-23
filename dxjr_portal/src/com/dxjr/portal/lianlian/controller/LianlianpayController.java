package com.dxjr.portal.lianlian.controller;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.lianlian.service.LianlianpayService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianLosePaymentResponse;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LianlianPaymentRequest;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.sinapay.sign.SignAndVerify;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.file.FileUtil;
import com.dxjr.utils.httputil.HttpURLUtil;

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
@RequestMapping(value = "/account/topup/lianlianpay")
public class LianlianpayController extends BaseController {
	private final static Logger logger = Logger.getLogger(LianlianpayController.class);

	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private LianlianpayService lianlianpayService;
	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * Description:跳转到连连支付页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toIndex")
	public ModelAndView toSinapay() {
		ModelAndView mv = new ModelAndView("account/topup/lianlianpay/llpayIndex");
		return mv;
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
		ModelAndView mav = new ModelAndView("account/topup/lianlianpay/llpaySend");
		try {
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
				mav.setViewName("redirect:/account/topup/toTopupIndex.html");
				return mav;
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				return redirect("/AccountSafetyCentre/safetyIndex.html");
			}
			// 判断是否通过了实名认证
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				return redirect("/account/approve/realname/toRealNameAppro.html");
			}
			// 保存充值记录
			topupMoneyVo.setPlatform(shiroUser.getPlatform());
			Map<String, Object> resultMap = lianlianpayService.savesend(shiroUser.getUserId(), topupMoneyVo, HttpTookit.getRealIpAddr(request), request.getRealPath(""));
			if (!resultMap.get("result").equals("success")) {
				// 返回到充值失败页面
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}
			mav.addObject("lianlianPayRequestString", resultMap.get("lianlianPayRequestString"));
		} catch (Exception e) {
			logger.error("使用连连支付进行在线充值，进入银联充值页面报错", e);
			// 返回到充值失败页面
			mav.setViewName("account/topup/topupResult");
			mav.addObject("topupResult", "failure");
			return mav;
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:连连支付客户充值完调用查看返回商户页面执行的方法<br />
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
	public ModelAndView recevie(HttpServletRequest request, LianlianPayDataResponse lianlianPayDataResponse) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		// 支付失败
		String topupResult = "failure";
		try {
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

	/**
	 * <p>
	 * Description:跳转到在线的补单页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toLoseOrderIndex")
	public ModelAndView toLoseOrder() {
		ModelAndView mv = new ModelAndView("account/topup/lianlianpay/loseOrderIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:进行在线补单操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "loseSend")
	@ResponseBody
	public String loseSend(HttpServletRequest request, String v_oid) {
		String result = "success";
		try {
			// 封装请求
			LianlianPaymentRequest paymentInfo = new LianlianPaymentRequest();
			paymentInfo.setOid_partner(BusinessConstants.ONLINE_PAY_LIANLIANPAY_OID_PARTNER);
			paymentInfo.setSign_type(BusinessConstants.ONLINE_PAY_LIANLIANPAY_SIGNTYPE);
			paymentInfo.setNo_order(v_oid);

			// 设置签名密钥
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(FileUtil.readFile(LianlianpayController.class.getResource("/").getPath()
					+ BusinessConstants.ONLINE_PAY_LIANLIANPAY_RPIVATE_KEY)));
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = fact.generatePrivate(spec);
			String signContent = LLPayUtil.generateToSignContent(paymentInfo);
			String signMsg = SignAndVerify.sign("MD5withRSA", signContent, privateKey);
			paymentInfo.setSign(signMsg);

			// 得到请求后的json字符串
			String arguments = JsonUtils.bean2Json(paymentInfo);
			result = HttpURLUtil.doPost(BusinessConstants.ONLINE_PAY_LIANLIANPAY_LOSEACTION_URL, arguments, "UTF-8");
			LianlianLosePaymentResponse lianlianLosePaymentResponse = JsonUtils.json2Bean(result, LianlianLosePaymentResponse.class);
			LianlianPayDataResponse lianlianPayDataResponse = new LianlianPayDataResponse();
			BeanUtils.copyProperties(lianlianLosePaymentResponse, lianlianPayDataResponse);
			// 保存支付返回信息
			lianlianpayService.savePackageAccountRechargeFeedback(lianlianPayDataResponse);
			// 处理结果 SUCCESS：支付成功
			if (null == lianlianPayDataResponse || null == lianlianPayDataResponse.getResult_pay() || !"SUCCESS".equals(lianlianPayDataResponse.getResult_pay())) {
				return "TransStatusError";
			}
			// 验签是否成功
			if (!LLPayUtil.validateSignMsg(lianlianLosePaymentResponse)) {
				return "signMsgVerifyError";
			}
			// 接收支付信息，更新用户帐号和充值状态
			String msg = lianlianpayService.saveAutoReceive(lianlianPayDataResponse, HttpTookit.getRealIpAddr(request));
			if (!"success".equals(msg)) {
				return "error:" + msg;
			}
		} catch (Exception e) {
			result = "error";
			logger.error("连连支付补单出错", e);
			return result;
		}
		return "OK";
	}
}
