package com.dxjr.portal.account.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.AccountSafeService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

@Controller
@RequestMapping(value = "/account/safe")
public class AccountSafeController extends BaseController {

	private static final Logger logger = Logger.getLogger(AccountSafeController.class);

	@Autowired
	private AccountSafeService accountSafeService;
    @Autowired
    private MobileApproService mobileApproService ;
    @Autowired
    private PhoneService phoneService;
	/**
	 * 跳转到设置交易密码页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月29日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toSetPayPwd")
	@RequiresAuthentication
	public ModelAndView toSetPayPwd() {
		return new ModelAndView("account/safe/setPayPwdDiv");
	}

	/**
	 * 修改登录密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月25日
	 * @param oldPwd
	 * @param newPwd
	 * @return MessageBox
	 */
	@RequestMapping(value = "modifyLoginPwd")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox modifyLoginPwd(@RequestParam String oldPwd, @RequestParam String newPwd) {
		try {
			ShiroUser shiroUser = currentUser();
			return accountSafeService.updatePwd(shiroUser, oldPwd, newPwd, "logpassword");
		} catch (Exception e) {
			logger.error("修改登录密码异常", e);
			return MessageBox.build("0", "修改登录密码异常");
		}
	}

	/**
	 * 修改交易密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param oldPwd
	 * @param newPwd
	 * @return MessageBox
	 */
	@RequestMapping(value = "modifyPayPwd")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox modifyPayPwd(@RequestParam String oldPwd, @RequestParam String newPwd) {
		try {
			ShiroUser shiroUser = currentUser();
			return accountSafeService.updatePwd(shiroUser, oldPwd, newPwd, "paypassword");
		} catch (Exception e) {
			logger.error("修改交易密码异常", e);
			return MessageBox.build("0", "修改交易密码异常");
		}
	}

	/**
	 * 设置交易密码
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月5日
	 * @param newPwd
	 * @return MessageBox
	 */
	@RequestMapping(value = "setPayPwd")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox setPayPwd(@RequestParam String newPwd) {
		try {
			ShiroUser shiroUser = currentUser();
			return accountSafeService.addPayPwd(shiroUser, newPwd);
		} catch (Exception e) {
			logger.error("设置交易密码异常", e);
			return MessageBox.build("0", "设置交易密码异常");
		}
	}

	/**
	 * 修改密码成功
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "modifyPwdResult")
	@ResponseBody
	@RequiresAuthentication
	public ModelAndView modifyPwdResult(@RequestParam Integer type) {
		ModelAndView mv = null;
		try {
			ShiroUser shiroUser = currentUser();
			mv = accountSafeService.getApproInfo(shiroUser);
			mv.addObject("type", type);
		} catch (Exception e) {
			logger.error("获取认证信息异常", e);
		}
		return mv;
	}

	/**
	 * 找回登录密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param username
	 * @param realname
	 * @param idCard
	 * @param findType
	 * @return MessageBox
	 */
	@RequestMapping(value = "findLoginPwd")
	@ResponseBody
	public MessageBox findLoginPwd(@RequestParam String username, @RequestParam String realname, @RequestParam String idCard, @RequestParam String findType, @RequestParam String checkcode) {
		try {
			String msg = accountSafeService.checkCode(currentSession(), checkcode);
			if (!"".equals(msg)) {
				return MessageBox.build("0", msg);
			}
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			return accountSafeService.resetPwd(username, realname, idCard, findType, addip, "logpassword");
		} catch (Exception e) {
			logger.error("找回登录密码异常", e);
			return MessageBox.build("0", "找回登录密码异常");
		}
	}

	/**
	 * 找回交易密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param username
	 * @param realname
	 * @param idCard
	 * @param findType
	 * @return MessageBox
	 */
	@RequestMapping(value = "findPayPwd")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox findPayPwd(@RequestParam String username, @RequestParam String realname, @RequestParam String idCard, @RequestParam String findType, @RequestParam String checkcode) {
		try {
			String msg = accountSafeService.checkCode(currentSession(), checkcode);
			if (!"".equals(msg)) {
				return MessageBox.build("0", msg);
			}
			if (currentUser() == null) {
				return new MessageBox("0", "未登录用户不能操作");
			}
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			return accountSafeService.resetPwd(username, realname, idCard, findType, addip, "paypassword");
		} catch (Exception e) {
			logger.error("找回交易密码异常", e);
			return MessageBox.build("0", "找回交易密码异常");
		}
	}

	/**
	 * 找回密码成功
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月27日
	 * @param findType
	 * @return ModelAndView
	 */
	@RequestMapping(value = "findPwdResult")
	@ResponseBody
	public ModelAndView findPwdResult(@RequestParam String findType, @RequestParam String pwdType) {
		ModelAndView mv = new ModelAndView("/account/safe/findPwdResultDiv");
		try {
			if ("email".equals(findType)) {
				mv.addObject("findType", "认证邮箱");
			} else if ("mobile".equals(findType)) {
				mv.addObject("findType", "认证手机");
			}
			if ("pay".equals(pwdType)) {
				mv.addObject("pwdType", "交易");
			} else {
				mv.addObject("pwdType", "登录");
			}
		} catch (Exception e) {
			logger.error("找回密码成功异常", e);
		}
		return mv;
	}
	/**
	 *  
	 * <p>
	 * Description:找回登录方式<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月25日
	 * @param username
	 * @param realname
	 * @param idCard
	 * @param findType
	 * @param checkcode
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "findLoginPasswd")
	@ResponseBody
	public MessageBox findLoginPasswd (String realname, String idCard, String findType,String mobileNum) {
		try {
			 
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			String result=accountSafeService.resetPassword( realname, idCard, findType, addip, "logpassword",mobileNum);
			if(!result.equals(BusinessConstants.SUCCESS)){
				return MessageBox.build("0", result);
			}
			return MessageBox.build("1", "修改成功");
		} catch (Exception e) {
			logger.error("找回登录密码异常", e);
			return MessageBox.build("0", "找回登录密码异常");
		}
	}
	/**
	 * 
	 * <p>
	 * Description:邮箱找回密码，需要身份证和姓名一致<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年10月8日
	 * @param realname
	 * @param idCard
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "confrimIdCard")
	@ResponseBody
	public MessageBox confrimIdCard(String realname, String idCard) {
		try {
			RealNameApproCnd realNameApproCnd=new RealNameApproCnd();
			realNameApproCnd.setIdCardNo(idCard);
			realNameApproCnd.setRealName(realname);
			String result=accountSafeService.confirmRealName(realNameApproCnd);
			if(!result.equals(BusinessConstants.SUCCESS)){
				return MessageBox.build("0", result);
			}
			return MessageBox.build("1", "");
		} catch (Exception e) {
			logger.error("找回登录密码异常", e);
			return MessageBox.build("0", "找回登录密码异常");
		}
	}
	@RequestMapping(value = "findLoginPasswdByEmail")
	@ResponseBody
	public MessageBox findLoginPasswdByEmail (String email,String realname,String idCard) {
		try {
			String result=accountSafeService.resetPasswordByEmail(email,realname,idCard);
			if(!result.equals(BusinessConstants.SUCCESS)){
				return MessageBox.build("0", result);
			}
			return MessageBox.build("1", "修改成功");
		} catch (Exception e) {
			logger.error("找回登录密码异常", e);
			return MessageBox.build("0", "找回登录密码异常");
		}
	}
	/**
	 * 
	 * <p>
	 * Description:微信找回密码<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年10月19日
	 * @param email
	 * @param realname
	 * @param idCard
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "findLoginPasswdByEmail2")
	@ResponseBody
	public MessageBox findLoginPasswdByEmail2 (String email,String realname,String checkcode,String idCard) {
		try {
			String randCode = (String) currentSession().getAttribute("randomCode");
			if (null == checkcode || null == randCode || !checkcode.equals(randCode)) {
				return new MessageBox("0", "验证码输入有误！");
			}
			String result=accountSafeService.resetPasswordByEmail(email,realname,idCard);
			if(!result.equals(BusinessConstants.SUCCESS)){
				return MessageBox.build("0", result);
			}
			return MessageBox.build("1", "修改成功");
		} catch (Exception e) {
			logger.error("找回登录密码异常", e);
			return MessageBox.build("0", "找回登录密码异常");
		}
	}
	/**
	 * 
	 * <p>
	 * Description:手机验证密码第一步<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年10月19日
	 * @param validateCode
	 * @param mobile
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "/findPwdOne")
	@ResponseBody
	public MessageBox findPwdOne(String checkcode,String mobileNum){
		 
		String randCode = (String) currentSession().getAttribute("randomCode");
		try {
		if (null == checkcode || null == randCode || !checkcode.equals(randCode)) {
			return new MessageBox("0", "验证码输入有误！");
		}
		mobileApproService.sendMobileApprValidate(mobileNum, currentRequest(), "", BusinessConstants.MOBILE_APPRO_FUNCTION,
					BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST);
		} catch (Exception e) {
			logger.error("微信手机找回密码", e);
		}
		return new MessageBox("1","");
	}
	/**
	 * 
	 * <p>
	 * Description:手机短信验证码第二步<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年10月19日
	 * @param validateCode
	 * @param mobileNum
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "/findPwdTwo")
	@ResponseBody
	public MessageBox findPwdTwo(String activeCode,String mobileNum){
		 
		// 判断输入的手机校验码是否正确
		String valiateResult;
		try {
			valiateResult = phoneService.compareSmsValidate(mobileNum, activeCode,BusinessConstants.MOBILE_APPRO_FUNCTION);
			if (valiateResult != "success") {
				return  new MessageBox("0",valiateResult);
			}
		} catch (Exception e) {
			logger.error("手机找回密码第二步:", e);
		}
		return new MessageBox("1","");
	}
}
