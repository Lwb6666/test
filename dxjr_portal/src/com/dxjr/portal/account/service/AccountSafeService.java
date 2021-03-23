package com.dxjr.portal.account.service;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.security.ShiroUser;

public interface AccountSafeService {

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
	public MessageBox updatePwd(ShiroUser shiroUser, String oldPwd, String newPwd, String pwdType) throws Exception;

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
	public MessageBox addPayPwd(ShiroUser shiroUser, String newPwd) throws Exception;

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
	public ModelAndView getApproInfo(ShiroUser shiroUser) throws Exception;

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
	public MessageBox resetPwd(String username, String realname, String idCard, String findType, String addip, String pwdType) throws Exception;

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
	public String checkCode(HttpSession currentSession, String checkcode);
	/**
	 * 
	 * <p>
	 * Description:重置登录密码<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月25日
	 * @param username
	 * @param realname
	 * @param idCard
	 * @param findType
	 * @param addip
	 * @param pwdType
	 * @return
	 * @throws Exception
	 * String
	 */
	public String resetPassword( String realname, String idCard, String findType, String addip, String pwdType,String mobile) throws Exception;
    /**
     * 
     * <p>
     * Description:邮箱找回密码，需要身份和姓名验证<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年10月8日
     * @param realNameApproCnd
     * @return
     * @throws Exception
     * String
     */
	public String confirmRealName(RealNameApproCnd realNameApproCnd)throws Exception;
    /**
     * 
     * <p>
     * Description:邮箱找回密码验证通过，即发送邮件<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年10月8日
     * @param email
     * @param addip
     * @return
     * @throws Exception
     * String
     */
	public String resetPasswordByEmail(String email,String realname,String idCard) throws Exception;

}
