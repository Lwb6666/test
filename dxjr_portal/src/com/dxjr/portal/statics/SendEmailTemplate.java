package com.dxjr.portal.statics;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.base.entity.EmailAppro;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.utils.HttpTookit;

/**
 * <p>
 * Description:发送邮件模板<br />
 * </p>
 * 
 * @title SendEmailTemplate.java
 * @package com.dxjr.portal.statics
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
public class SendEmailTemplate {
	/**
	 * <p>
	 * Description:注册邮件内容.<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月18日
	 * @param request
	 * @param ea
	 * @param member
	 * @return String
	 */
	public static String packageVerifyContent(HttpServletRequest request,
			EmailAppro ea, MemberVo memberVo) {
		String basePath = HttpTookit.getPathNotPort(request);
		String linkage = basePath + "appro/registEmailAppro/" + memberVo.getId()
				+ "/" + ea.getRandUUID() + ".html?email=" + memberVo.getEmail();
		StringBuilder sb = new StringBuilder();
		sb.append("<font color='red'>您好：</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您加入顶玺金融大家庭，我们将<font color='red'>用心呵护您的财富，和您一起分享成长的快乐！</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您在顶玺金融平台上的用户名是：<font color='red'>"
				+ memberVo.getUsername() + "</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请点击下面链接，完成您的邮箱验证：<br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='"
				+ linkage + "'>" + linkage + "</a><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果链接无法点击，请将链接复制到浏览器地址栏中进行访问。<br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此信是由顶玺金融系统发出，系统不接受回信，请勿直接回复。如有任何疑问，请联系我们：400-000-0000，我们将竭诚为您服务！");
		return sb.toString();
	}
	/**
	 * <p>
	 * Description:注册邮件内容.<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月18日
	 * @param request
	 * @param ea
	 * @param member
	 * @return String
	 */
	public static String packageRegisterContent(HttpServletRequest request,
			EmailAppro ea, MemberVo memberVo) {
		String basePath = HttpTookit.getPathNotPort(request);
		String linkage = basePath + "appro/email_appro/" + memberVo.getId()
				+ "/" + ea.getRandUUID() + ".html?email=" + memberVo.getEmail();
		StringBuilder sb = new StringBuilder();
		sb.append("<font color='red'>您好：</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您加入顶玺金融大家庭，我们将<font color='red'>用心呵护您的财富，和您一起分享成长的快乐！</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您在顶玺金融平台上的用户名是：<font color='red'>"
				+ memberVo.getUsername() + "</font><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请点击下面链接，完成您的邮箱验证：<br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='"
				+ linkage + "'>" + linkage + "</a><br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果链接无法点击，请将链接复制到浏览器地址栏中进行访问。<br/>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此信是由顶玺金融系统发出，系统不接受回信，请勿直接回复。如有任何疑问，请联系我们：400-000-0000，我们将竭诚为您服务！");
		return sb.toString();
	}
	
	/**
	 * 找回密码邮件模板
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param request
	 * @param member
	 * @param type
	 * @param randCode
	 * @return
	 * String
	 */
	public static String findPwdContent(String username, String type, String randCode) {
		StringBuilder sb = new StringBuilder();
		sb.append("<font color='red'>您好：</font><br/>");
		sb.append("<div style='margin-left:20px;'>欢迎您加入顶玺金融大家庭，我们将<font color='red'>用心呵护您的财富，和您一起分享成长的快乐！</font></div>");
		sb.append("<div style='margin-left:20px;'>您在顶玺金融平台上的用户名是：<font color='red'>"
				+ username + "</font></div>");
		sb.append("<div style='margin-left:20px;'>系统已将您的<font color='red'>"
				+ type + "密码</font>重置为：<font color='red'>" + randCode
				+ "</font></div>");
		sb.append("<div style='margin-left:20px;'>本次密码为临时密码，请您尽快登录系统修改<font color='red'>"
				+ type + "密码</font></div>");
		sb.append("<div style='margin-left:20px;'>此信是由顶玺金融发出，系统不接受回信，请勿直接回复。如有任何疑问，请联系我们：400-000-0000，我们将竭诚为您服务！</div>");
		return sb.toString();
	}
}
