package com.dxjr.portal.account.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.service.ShengpayService;
import com.dxjr.portal.account.vo.MyAccountApproMsgVo;
import com.dxjr.portal.account.vo.ShengReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:盛付通支付控制类<br />
 * </p>
 * 
 * @title ShengpayController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年5月21日
 */
@Controller
@RequestMapping(value = "/account/topup/shengpay")
public class ShengpayController extends BaseController {
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private ShengpayService shengpayService;
	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * Description:跳转到盛付通支付页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toIndex")
	public ModelAndView toShengpay() {
		ModelAndView mv = new ModelAndView(
				"account/topup/shengpay/shengpayIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:使用盛付通进行在线充值，进入银联充值页面<br />
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
	public ModelAndView send(TopupMoneyVo topupMoneyVo,
			HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView(
				"account/topup/shengpay/shengpaySend");
		try {
			ShiroUser shiroUser = currentUser();
			// 验证用户的认证信息是否通过（包含邮箱、实名、手机认证）
			MyAccountApproMsgVo myAccountApproMsgVo = myAccountService
					.validateAccountAppro(shiroUser.getUserId());
			if (!myAccountApproMsgVo.getResult().equals("success")) {
				// 保存错误信息返回到前台
				mav.addObject("myAccountApproMsgVo", myAccountApproMsgVo);
				mav.setViewName("account/myAccountBackmsg");
				return mav;
			}
			
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			
			// 保存充值记录
			Map<String, Object> resultMap = shengpayService.savesend(memberVo,
					topupMoneyVo, request);
			if (!resultMap.get("result").equals("success")) {
				// 返回到充值失败页面
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}
			mav.addObject("shengPayForm", resultMap.get("shengPayForm"));
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
	 * Description:盛付通客户充值完调用查看返回商户页面执行的方法<br />
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
	public ModelAndView shengpayRecevie(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		String topupResult = "";
		/*
		 * 等待付款中 00 付款成功 01 付款失败 02 过期 03 撤销成功 04 退款中 05 退款成功 06 退款失败 07 部分退款成功
		 * 08
		 */
		String transStatus = request.getParameter("TransStatus");
		if (null != transStatus && "01".equals(transStatus)) {// 支付成功
			topupResult = "success";// 支付成功
		} else {
			topupResult = "failure";// 支付失败
		}
		mv.addObject("topupResult", topupResult);
		return mv;
	}

	/**
	 * <p>
	 * Description:盛付通充值结果会自动调用本方法,本接口的地址每次提交支付申请时提交给盛付通<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param response
	 *            void
	 */
	@RequestMapping(value = "autoRecevie")
	public @ResponseBody
	String shengpayAutoRecevie(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, ShengReceiveForm shengReceiveForm) {
		String result = "OK";
		try {
			// 接收支付信息，更新用户帐号和充值状态
			String msg = shengpayService.saveAutoReceive(shengReceiveForm,
					request);
			if (!"success".equals(msg)) {
				return msg;
			}
		} catch (AppException ae) {
			return ae.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "ExceptionERROR";
		}
		return result;
	}
}
