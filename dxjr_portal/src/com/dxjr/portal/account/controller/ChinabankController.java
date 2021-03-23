package com.dxjr.portal.account.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.ChinabankService;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;

/**
 * <p>
 * Description:网银在线支付控制类<br />
 * </p>
 * 
 * @title ChinabankController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年6月10日
 */
@Controller
@RequestMapping(value = "/account/topup/chinabank")
public class ChinabankController extends BaseController {
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private ChinabankService chinabankService;
	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * Description:跳转到网银在线的银行选择页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toIndex")
	public ModelAndView toChinaBank() {
		ModelAndView mv = new ModelAndView("account/topup/chinabank/chinabankIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:使用网银在线进行在线充值，进入银联充值页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月11日
	 * @param topupMoneyVo
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send")
	@RequiresAuthentication
	public ModelAndView send(TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("account/topup/chinabank/chinabankSend");
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
			Map<String, Object> resultMap = chinabankService.savesend(shiroUser.getUserId(), topupMoneyVo, request);
			if (!resultMap.get("result").equals("success")) {
				// 返回到充值失败页面
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}
			mav.addObject("chinabankPayForm", resultMap.get("chinabankPayForm"));
		} catch (Exception e) {
			e.printStackTrace();
			// 返回到充值失败页面
			mav.setViewName("account/topup/topupResult");
			mav.addObject("topupResult", "failure");
			return mav;
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:网银在线客户充值完调用查看返回商户页面执行的方法<br />
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
	public ModelAndView chinabankRecevie(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			ChinabankReceiveForm chinabankReceiveForm) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		String topupResult = "";
		String text = chinabankReceiveForm.generateMd5String();
		String v_md5text = MD5.toMD5(text).toUpperCase();
		if (null != chinabankReceiveForm.getV_md5str() && chinabankReceiveForm.getV_md5str().equals(v_md5text)) {
			if (null != chinabankReceiveForm.getV_pstatus()) {
				if ("30".equals(chinabankReceiveForm.getV_pstatus())) {// 支付失败
					topupResult = "failure";
				} else if ("20".equals(chinabankReceiveForm.getV_pstatus())) {// 支付成功
					topupResult = "success";
				}
			} else {// 支付失败
				topupResult = "failure";
			}
		} else {
			topupResult = "failure";
		}
		/**
		 * 测试使用 String result = "ok"; try { // 接收支付信息，更新用户帐号和充值状态 String msg =
		 * chinabankService.saveAutoReceive(chinabankReceiveForm, request); if
		 * (!"success".equals(msg)) { // return msg; } } catch (AppException ae)
		 * { // return "RepeatSubmit"; } catch (Exception e) {
		 * e.printStackTrace(); // return "ExceptionERROR"; }
		 */
		mv.addObject("topupResult", topupResult);
		return mv;
	}

	/**
	 * <p>
	 * Description:跳转到网银在线的补单页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toLoseOrderIndex")
	public ModelAndView toLoseOrder() {
		ModelAndView mv = new ModelAndView("account/topup/chinabank/loseOrderIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:进行网银在线补单操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "loseSend")
	public ModelAndView loseSend(String v_oid) {
		ModelAndView mv = new ModelAndView("account/topup/chinabank/loseSend");
		mv.addObject("v_oid", v_oid);
		mv.addObject("v_mid", BusinessConstants.ONLINE_PAY_CHINABANK_SHOPNO);
		mv.addObject("v_url", BusinessConstants.CHINABANK_AUTO_RECEVIE_URL);
		String key = BusinessConstants.ONLINE_PAY_CHINABANK_MD5KEY;
		String text = v_oid + key;
		String billNo_md5 = MD5.toMD5(text);
		mv.addObject("billNo_md5", billNo_md5);
		return mv;
	}
}
