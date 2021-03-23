package com.dxjr.portal.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AlipayService;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:支付宝充值Controller<br />
 * </p>
 * 
 * @title AlipayController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年6月12日
 */
@Controller
@RequestMapping(value = "/account/topup/alipay")
public class AlipayController extends BaseController {
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * Description:支付宝进行充值<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月12日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/alipayRecharge")
	@ResponseBody
	public String alipayRecharge(TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpSession session) {
		String msg = "success";
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser.getIsFinancialUser() == 1) {
				return "您是理财用户，无法使用支付宝充值！";
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			// 判断是否通过了实名认证
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				return "-1";
			}
			// 您还没有进行邮箱认证，请先进行邮箱认证
			if ((null == memberApproVo.getEmailChecked() || memberApproVo.getEmailChecked() != Constants.YES)) {
				return "-2";
			}
			// 您还没有进行手机认证，请先进行手机认证
			if (null == memberApproVo.getMobilePassed() || memberApproVo.getMobilePassed() != Constants.YES) {
				return "-3";
			}
			String result = alipayService.savepay(shiroUser.getUserId(), topupMoneyVo, request);
			if (!"success".equals(result)) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "充值申请提交失败！请重新提交！";
		}
		return msg;
	}
}
