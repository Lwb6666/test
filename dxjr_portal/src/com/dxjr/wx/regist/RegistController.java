package com.dxjr.wx.regist;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.lottery.service.LotteryChanceRuleInfoService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.util.VerifyContainSpecialChar;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.utils.RegResource;
import com.dxjr.wx.entry.bind.service.BindService;

@Controller
@RequestMapping(value = "/wx/regist")
public class RegistController extends BaseController {
	public Logger logger = Logger.getLogger(RegistController.class);
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private MobileApproService mobileApproService;
    @Autowired
	private RedAccountMapper redAccountMapper;
	@Autowired
	private RedAccountService redAccountService;
	@Autowired
	private RedAccountLogMapper redAccountLogMapper;
	@Autowired
	private LotteryChanceRuleInfoService lotteryChanceRuleInfoService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private BindService bindService;

	/**
	 * 提交注册
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月3日
	 * @param member
	 * @return Object
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Object wxRegist(Member member, HttpServletResponse response,
			String ip) {
		String result = BusinessConstants.SUCCESS;
		try {
			// 微信只支持理财用户的注册
			member.setIsFinancialUser(1);
			// 微信的平台来源
			if (member.getPlatform() == null) {
				return new MessageBox("0", "平台来源不能为空");
			}
			// 微信的source来源
			if (member.getSource() == null) {
				return new MessageBox("0", "用户来源不能为空");
			}
			// 微信的真实ip
			member.setAddip(ip);
			// 微信的真实ip
			member.setLastloginip(ip);
			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~20位之间");
			}
			if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			if (null == member.getLogpassword()|| "".equals(member.getLogpassword().trim())) {
				return new MessageBox("0", "密码输入有误！");
			}
			if (null == member.getActiveCode()&& !"".equals(member.getActiveCode().trim().trim())) {
				return new MessageBox("0", "请输入手机验证码");
			}
			// 验证手机验证码是否输入正确
			// 验证验证码是否正确
			String valiateResult = phoneService.compareSmsValidate(member.getMobileNum(), member.getActiveCode(), BusinessConstants.MOBILE_APPRO_FUNCTION);
			if (valiateResult != "success") {
				return new MessageBox("0", valiateResult);
			}
			String inviterName = currentRequest().getParameter("inviterName");
			result = memberRegisterService.insertMemberWithoutEmail(member,inviterName, currentRequest(), currentSession());
			// 注册借款用户需后台审核，审核通过才能登录系统
			if ("success".equals(result)) {
				
				cookieRetrievingCookieGenerator.addCookie(currentRequest(),response,
						TicketCryptor.encrypt(currentUser().getSsoTicket()));
				return new MessageBox("1", result);
			}

		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			logger.error("register", e);
		}
		return new MessageBox("0", result);
	}

	/**
	 * 
	 * <p>
	 * Description:验证信息<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月22日
	 * @param member
	 * @param response
	 * @param ip
	 * @return Object
	 */
	@RequestMapping(value = "confirm")
	@ResponseBody
	public Object wxConfrim(Member member) {
		String result = BusinessConstants.SUCCESS;
		try {

			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~20位之间");
			}
			if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			if (null == member.getLogpassword()|| "".equals(member.getLogpassword().trim())) {
				return new MessageBox("0", "密码输入有误！");
			}
			if (null == member.getMobileNum()|| "".equals(member.getMobileNum().trim())) {
				return new MessageBox("0", "手机号码不能为空");
			}
			if (!RegResource.checkMobileNumber(member.getMobileNum())) {
				return new MessageBox("0", "手机号码输入有误");
			}
			// 验证验证码
			String validatecode = currentRequest().getParameter("validatecode");
			String randCode = (String) currentSession().getAttribute("randomCode");
			if (null == validatecode || null == randCode|| !validatecode.equals(randCode)) {
				return new MessageBox("0", "验证码输入有误！");
			}
			String inviterName = currentRequest().getParameter("inviterName");
			result = memberRegisterService.confrim(member, inviterName,currentRequest(), currentSession());

			if ("success".equals(result)) {
				// 发送短信验证码
				mobileApproService.sendMobileApprValidate(
						member.getMobileNum(), currentRequest(), "",
						BusinessConstants.MOBILE_APPRO_FUNCTION,
						BusinessConstants.SMS_TEMPLATE_TYPE_REGIST_MOBILE_CODE);
				return new MessageBox("1", result);
			}

		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			logger.error("register", e);
		}
		return new MessageBox("0", result);
	}

	/**
	 * 
	 * <p>
	 * Description:校验用户名长度是否符合要求<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月11日
	 * @param member
	 * @return boolean
	 */
	private boolean verifyUserNameLength(Member member) {
		return !(member != null && null != member.getUsername() && (member
				.getUsername().trim().length() <= 20 && member.getUsername()
				.trim().length() >= 2));
	}

	/**
	 * 
	 * <p>
	 * Description:验证手机短信码是否正确<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月23日
	 * @param mobile
	 * @param activeCode
	 * @return String
	 */
	@RequestMapping(value = "/checkCode")
	@ResponseBody
	public MessageBox checkRegPhoneCode(String mobileNum, String activeCode) {
		String result = BusinessConstants.SUCCESS;
		// 判断输入的手机校验码是否正确
		String valiateResult;
		try {
			valiateResult = phoneService.compareSmsValidate(mobileNum,
					activeCode, BusinessConstants.MOBILE_APPRO_FUNCTION);
			if (!result.equals(valiateResult)) {
				return new MessageBox("0", valiateResult);
			}
		} catch (Exception e) {
			logger.error("手机号码验证异常", e);
		}

		return new MessageBox("1", BusinessConstants.SUCCESS);
	}

	/**
	 * 
	 * <p>
	 * Description:手机发送短信<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月18日
	 * @return MessageBox
	 */
	@RequestMapping(value = "/sendMobileCode")
	public @ResponseBody MessageBox sendMobileCode(String mobileNum) {
		String result = "success";
		try {
			if (null != mobileNum && !"".equals(mobileNum.trim())&&RegResource.checkMobileNumber(mobileNum)) {
				// 验证码发送
				result = mobileApproService.sendMobileApprValidate(mobileNum,currentRequest(),"",BusinessConstants.MOBILE_APPRO_FUNCTION,BusinessConstants.SMS_TEMPLATE_TYPE_WX_SAFECENTER_MOBILEVALIDATE);
			}
		} catch (Exception e) {
			result = "failer";
			logger.error("网络异常");
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}
		return new MessageBox("1", result);
	}

}