package com.dxjr.portal.account.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.RealNameAppro;
import com.dxjr.base.mapper.BaseMobileApproMapper;
import com.dxjr.base.mapper.BaseRealNameApproMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountSafeService;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.EmailSendForUserService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.SendEmailTemplate;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;

@Service
public class AccountSafeServiceImpl implements AccountSafeService {

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	BaseRealNameApproMapper realNameApproMapper;

	@Autowired
	BaseMobileApproMapper mobileApproMapper;

	@Autowired
	private EmailSendForUserService emailSendForUserService;

	@Autowired
	private SmsSendService smsSendService;

	@Autowired
	private MemberService memberService;

	/**
	 * 设置交易密码
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月5日
	 * @param shiroUser
	 * @param newPwd
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox addPayPwd(ShiroUser shiroUser, String newPwd) throws Exception {
		if (shiroUser == null) {
			return new MessageBox("0", "未登录用户不能操作");
		}
		MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
		if (mem.getPaypassword() != null && !"".equals(mem.getPaypassword())) {
			return new MessageBox("0", "您已设置交易密码");
		}
		String logPwd = mem.getLogpassword();
		newPwd = MD5.toMD5(newPwd);
		if (newPwd.equals(logPwd)) {
			return new MessageBox("0", "交易密码与登录密码不能一样，请重新输入。");
		}
		memberMapper.updatePwd("paypassword", newPwd, shiroUser.getUserId());
		return new MessageBox("1", "设置成功");
	}

	/**
	 * 修改登录/交易密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月25日
	 * @param shiroUser 当前登录用户
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param pwdType 密码类型，以表字段为参数
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox updatePwd(ShiroUser shiroUser, String oldPwd, String newPwd, String pwdType) throws Exception {
		if (shiroUser == null) {
			return new MessageBox("0", "未登录用户不能操作");
		}

		MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
		String pwd = "";
		String pwd2 = "";
		if ("logpassword".equals(pwdType)) {
			pwd = mem.getLogpassword();
			pwd2 = mem.getPaypassword() == null ? "" : mem.getPaypassword();
		} else {
			pwd = mem.getPaypassword();
			if (pwd == null || "".equals(pwd)) {
				return new MessageBox("0", "请先设置交易密码");
			}
			pwd2 = mem.getLogpassword();
		}

		oldPwd = MD5.toMD5(oldPwd);
		newPwd = MD5.toMD5(newPwd);

		if (!pwd.equals(oldPwd)) {
			return new MessageBox("0", "当前密码输入错误");
		} else if (oldPwd.equals(newPwd)) {
			return new MessageBox("0", "新密码与当前密码不能一样，请重新输入。");
		} else if (pwd2.equals(newPwd)) {
			return new MessageBox("0", "登录密码与交易密码不能一样，请重新输入。");
		} else {
			memberMapper.updatePwd(pwdType, newPwd, shiroUser.getUserId());
		}

		return new MessageBox("1", "修改成功");
	}

	/**
	 * 获取认证信息
	 * <p>
	 * Description:密码修改成功后，跳转至修改密码成功的页面，显示认证信息<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param shiroUser
	 * @return ModelAndView
	 */
	public ModelAndView getApproInfo(ShiroUser shiroUser) throws Exception {
		ModelAndView mv = new ModelAndView("/account/safe/modifyResult");
		if (shiroUser == null) {
			return new ModelAndView("/member/login");
		}
		int userId = shiroUser.getUserId();
		boolean mobileAppro = memberMapper.queryMobileIspassed(userId) > 0 ? true : false;
		boolean emailAppro = memberMapper.queryEmailIspassed(userId) > 0 ? true : false;
		boolean vipAppro = memberMapper.queryVIPIspassed(userId) > 0 ? true : false;

		mv.addObject("mobileAppro", mobileAppro);
		mv.addObject("emailAppro", emailAppro);
		mv.addObject("vipAppro", vipAppro);
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
	 * @param addip
	 * @param pwdType
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox resetPwd(String username, String realname, String idCard, String findType, String addip, String pwdType) throws Exception {
		MemberVo memberVo = memberMapper.queryMemberByloginname(username);
		if (memberVo == null) {
			return new MessageBox("0", "您输入的用户名不存在");
		}

		int userId = memberVo.getId();
		if (memberMapper.queryRealNameIspassed(userId) == 0) {
			return new MessageBox("0", "您还未进行实名认证，请联系客服修改密码。");
		}

		RealNameAppro approMem = realNameApproMapper.queryByUserId(userId);
		String approRealName = approMem.getRealName();
		if (!realname.equals(approRealName)) {
			return new MessageBox("0", "真实姓名输入错误");
		}
		String approIdCard = approMem.getIdCardNo();
		if (!idCard.equals(approIdCard)) {
			return new MessageBox("0", "身份证输入错误");
		}

		if ("email".equals(findType) && memberMapper.queryEmailIspassed(userId) == 0)
			return new MessageBox("0", "您的邮箱还未进行认证，请使用其它方式找回密码。");
		if ("phone".equals(findType) && memberMapper.queryMobileIspassed(userId) == 0)
			return new MessageBox("0", "您的手机还未进行认证，请使用其它方式找回密码。");

		// 修改密码
		String randPwd = String.valueOf(Math.round(Math.random() * 899999 + 100000));
		memberMapper.updatePwd(pwdType, MD5.toMD5(randPwd), userId);

		String type = "登录";
		if ("paypassword".equals(pwdType)) {
			type = "交易";
		}

		// 发邮件
		if ("email".equals(findType)) {
			String email = memberVo.getEmail();
			String content = SendEmailTemplate.findPwdContent(username, type, randPwd);
			emailSendForUserService.sendEmail(content, email);
		}

		// 发送短信
		if ("mobile".equals(findType)) {
			String mobile = mobileApproMapper.queryByUserId(userId).getMobileNum();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("type", type);
			map.put("time", DateUtils.format(new Date(), DateUtils.YMD_HMS));
			map.put("password", randPwd);
			smsSendService.saveSms(addip, BusinessConstants.SMS_TEMPLATE_TYPE_FIND_PASSWORD, map, mobile);
		}

		return new MessageBox("1", findType);
	}

	/**
	 * 校验验证码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月30日
	 * @param currentSession
	 * @param checkcode
	 * @return String
	 */
	public String checkCode(HttpSession currentSession, String checkcode) {
		String randomCode = (String) currentSession.getAttribute("randomCode");
		String msg = "";
		if (randomCode == null) {
			msg = "验证码不存在，请刷新。";
		} else if (!checkcode.equals(randomCode)) {
			msg = "验证码输入错误。";
		}
		currentSession.removeAttribute("randomCode");
		return msg;
	}

	public String resetPassword(String realname, String idCard, String findType, String addip, String pwdType, String mobileNum) throws Exception {
		String result = BusinessConstants.SUCCESS;

		MemberVo memberVo = memberMapper.queryMemberByloginname(mobileNum);
		if (memberVo == null) {
			return "实名验证不通过，请重新输入";
		}

		int userId = memberVo.getId();
		if (memberMapper.queryRealNameIspassed(userId) == 0) {
			return "您还未进行实名认证，请联系客服修改密码。";
		}

		RealNameAppro approMem = realNameApproMapper.queryByUserId(userId);
		String approRealName = approMem.getRealName();
		if (!realname.equals(approRealName)) {
			return "真实姓名输入错误";
		}
		String approIdCard = approMem.getIdCardNo();
		if (!idCard.equals(approIdCard)) {
			return "身份证输入错误";
		}

		if ("email".equals(findType) && memberMapper.queryEmailIspassed(userId) == 0)
			return "您的邮箱还未进行认证，请使用其它方式找回密码。";
		if ("phone".equals(findType) && memberMapper.queryMobileIspassed(userId) == 0)
			return "您的手机还未进行认证，请使用其它方式找回密码。";

		// 修改密码
		String randPwd = String.valueOf(Math.round(Math.random() * 899999 + 100000));
		memberMapper.updatePwd(pwdType, MD5.toMD5(randPwd), userId);

		String type = "登录";
		if ("paypassword".equals(pwdType)) {
			type = "交易";
		}

		// 发邮件
		if ("email".equals(findType)) {
			String email = memberVo.getEmail();
			String content = SendEmailTemplate.findPwdContent(memberVo.getUsername(), type, randPwd);
			emailSendForUserService.sendEmail(content, email);
		}

		// 发送短信
		if ("mobile".equals(findType)) {
			String mobile = mobileApproMapper.queryByUserId(userId).getMobileNum();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", memberVo.getUsername());
			map.put("type", type);
			map.put("time", DateUtils.format(new Date(), DateUtils.YMD_HMS));
			map.put("password", randPwd);
			smsSendService.saveSms(addip, BusinessConstants.SMS_TEMPLATE_TYPE_FIND_PASSWORD, map, mobile);
		}

		return result;
	}

	@Override
	public String confirmRealName(RealNameApproCnd realNameApproCnd) throws Exception {

		RealNameAppro approMem = realNameApproMapper.confirmRealName(realNameApproCnd);
		if (approMem == null) {
			return "身份证和姓名不存在！";
		}
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(approMem.getUserId());
		// 判断是否通过了实名认证
		if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return "-1";
		}
		// 您还没有进行邮箱认证，请先进行邮箱认证
		if ((null == memberApproVo.getEmailChecked() || memberApproVo.getEmailChecked() != Constants.YES)) {
			return "您还没有进行邮箱认证，请先进行邮箱认证！";
		}
		// 您还没有进行手机认证，请先进行手机认证
		if (null == memberApproVo.getMobilePassed() || memberApproVo.getMobilePassed() != Constants.YES) {
			// return "您还没有进行手机认证，请先进行手机认证！";
		}

		return BusinessConstants.SUCCESS;
	}

	@Override
	public String resetPasswordByEmail(String email, String realname, String idCard) throws Exception {
		MemberVo memberVo = memberMapper.queryMemberInfoByEmail(email);

		if (memberVo == null) {
			return "邮箱号码未被绑定";
		}
		int userId = memberVo.getId();
		RealNameAppro approMem = realNameApproMapper.queryByUserId(userId);
		if (approMem == null) {
			return "此邮箱未与您的身份证绑定";
		}
		String approRealName = approMem.getRealName();
		if (!realname.equals(approRealName)) {
			return "此邮箱未与您的身份证绑定";
		}
		String approIdCard = approMem.getIdCardNo();
		if (!idCard.equals(approIdCard)) {
			return "此邮箱未与您的身份证绑定";
		}
		// 修改密码
		String randPwd = String.valueOf(Math.round(Math.random() * 899999 + 100000));
		memberMapper.updatePwd("logpassword", MD5.toMD5(randPwd), userId);
		String type = "登录";
		String content = SendEmailTemplate.findPwdContent(memberVo.getUsername(), type, randPwd);
		emailSendForUserService.sendEmail(content, email);
		return BusinessConstants.SUCCESS;
	}

}
