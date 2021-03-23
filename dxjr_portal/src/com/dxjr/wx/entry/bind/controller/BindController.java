package com.dxjr.wx.entry.bind.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.sms.vo.SendMobileCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.wx.common.WxConstants;
import com.dxjr.wx.entry.bind.service.BindService;
import com.dxjr.wx.entry.bind.vo.BindVo;
import com.dxjr.wx.entry.mainIn.vo.WxContent;
import com.dxjr.wx.entry.message.pushmessage.utils.PushMessageUtils;
import com.dxjr.wx.entry.message.pushmessage.vo.AutoMessage;
import com.dxjr.wx.utils.HttpClientUtils;
import com.dxjr.wx.utils.WxUtils;

/**
 * 绑定controller类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title BindController.java
 * @package com.dxjr.wx.entry.bind.controller
 * @author Wang Bo
 * @version 0.1 2014年11月1日
 */
@Controller
@RequestMapping("/wx/bind")
public class BindController extends BaseController {
	private static final Logger logger = Logger.getLogger(BindController.class);
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private BindService bindService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SmsSendService sendService;

	/**
	 * 绑定
	 * <p>
	 * Description:绑定<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param request
	 * @param bindVo
	 * @return MessageBox
	 */
	@RequestMapping("/bind")
	@ResponseBody
	public Map<String, Object> bind(BindVo bindVo, String ip) {
		MessageBox mb = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Integer hadBind = bindService.queryHadBind(bindVo.getWxId());
		try {
			mb = bindService.saveBind(bindVo, BusinessConstants.MOBILE_APPRO_FUNCTION, BusinessConstants.SMS_TEMPLATE_TYPE_BIND_WX_SUCCESS, currentRequest());
			if ("1".equals(mb.getCode())) {
				MemberVo memberVo = bindService.queryMemberByLoginname(bindVo.getTelphone());
				String url = WxConstants.OAUTO2_URL.replace("APPID", WxConstants.AppId);
				url = url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=a", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID);
				AutoMessage result = new AutoMessage();
				if (memberVo.getSource() != null && memberVo.getSource().intValue() == 21) {
					if (hadBind != null && hadBind != 0) {
						result = PushMessageUtils.pushBindMessage(memberVo.getId(), bindVo.getWxId(), memberVo.getUsername(), new Date(), url, "您好,您的账户绑定成功", "您现在可以通过微信查询账户信息,\n\n\n点击\"详情\"立即登录");
					} else
						result = PushMessageUtils.pushBindMessage(memberVo.getId(), bindVo.getWxId(), memberVo.getUsername(), new Date(), url, "您好,您的账户绑定成功", "10元红包已发放到您的顶玺金融账户，登陆顶玺金融www.dxjr.com，点击我的账户即可领取，首次投资可再获得20元红包。投资专享16.8%收益，招商银行特约合作单位。\n\n\n点击\"详情\"立即登录");
				} else
					result = PushMessageUtils.pushBindMessage(memberVo.getId(), bindVo.getWxId(), bindVo.getUsername(), new Date(), url, "您好,您的账户绑定成功", "您现在可以通过微信查询账户信息,\n\n\n点击\"详情\"立即登录");
				SendMobileCnd sendMobileCnd = new SendMobileCnd();
				sendMobileCnd.setIp(ip);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("username", memberVo.getUsername());
				sendMobileCnd.setMobile(bindVo.getTelphone());
				sendMobileCnd.setModelName(BusinessConstants.MOBILE_APPRO_FUNCTION);
				sendMobileCnd.setParamMap(paramMap);
				sendMobileCnd.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_WX);
				sendMobileCnd.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_BIND_WX_SUCCESS);
				sendService.saveTemplateMessage(sendMobileCnd);
				map.put("autoMessage", WxUtils.bean2Json(result));
			}
			map.put("code", mb.getCode());
			map.put("message", mb.getMessage());
		} catch (Exception e) {
			logger.error("微信账号绑定异常", e);
			map.put("code", "0");
			map.put("message", "微信账号绑定失败");
		}
		return map;
	}

	/**
	 * 取消绑定
	 * <p>
	 * Description:取消绑定<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param request
	 * @param bindVo
	 * @return MessageBox
	 */
	@RequestMapping("/unbind")
	@ResponseBody
	public MessageBox unbind(HttpServletRequest request, BindVo bindVo, HttpServletResponse response) {
		try {
			MessageBox mb = bindService.updateUnBind(bindVo);
			if (mb.getCode().equals("1")) {
				SecurityUtils.getSubject().logout();
				cookieRetrievingCookieGenerator.removeCookie(response);
			}
			return mb;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("微信账号解绑异常", e);
			return MessageBox.build("0", "官网链接失败");
		}
	}

	/**
	 * 发送绑定验证码
	 * <p>
	 * Description:发送绑定验证码<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param request
	 * @param bindVo
	 * @return MessageBox
	 */
	@RequestMapping("/sendBindMsg")
	@ResponseBody
	public MessageBox sendBindMsg(HttpServletRequest request, BindVo bindVo, HttpServletResponse response, String ip) {
		String result = BusinessConstants.SUCCESS;
		try {
			MessageBox info = bindService.verfiBindInfo(bindVo);
			if (!"success".equals(info.getMessage())) {
				return info;
			}
			SendMobileCnd sendMobileCnd = new SendMobileCnd();
			sendMobileCnd.setMobile(bindVo.getTelphone());
			sendMobileCnd.setIp(ip);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", bindVo.getUsername());
			sendMobileCnd.setModelName(BusinessConstants.MOBILE_APPRO_FUNCTION);
			sendMobileCnd.setNeedRandcode(true);
			sendMobileCnd.setParamMap(map);
			sendMobileCnd.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_WX);
			sendMobileCnd.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_BIND_WX_CODE);
			result = sendService.saveTemplateMessage(sendMobileCnd);
			if (BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("1", "验证码发送成功，请查收手机短信。");
			}
			return MessageBox.build("0", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("微信绑定发送手机验证码异常", e);
			return new MessageBox("0", "网络连接异常，请刷新页面或稍后重试!");
		}

	}

	/**
	 * 取消关注自动解绑
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2015年3月12日
	 * @param BindVo
	 * @return MessageBox
	 */
	@RequestMapping("/updateBindUnsubscribe")
	@ResponseBody
	public MessageBox unsubscribeUnbind(BindVo BindVo) {
		try {
			return bindService.updateUnBindUnsubscribe(BindVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("微信取消关注自动解绑异常", e);
			return MessageBox.build("0", "官网链接失败");
		}
	}

	/**
	 * 项目启动自动解绑
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2015年3月12日 void
	 */
	public void autoUnbind() {
		List<?> list = null;
		try {
			list = HttpClientUtils.getRomoteList(new HttpClient(), WxConstants.CLEAR_WITH_START_UP_URL, null);
			if (list != null && list.size() != 0) {
				String ids = list.toString();
				ids = ids.replace("[", "(").replace("]", ")");
				bindService.updateBindStartUp(ids);
			}
		} catch (Exception e) {
			// 连接微信失败不进行操作
			logger.error("微信开机自动解绑异常", e);
		}
	}

	@RequestMapping("/autoUnbindtoPortal")
	@ResponseBody
	public MessageBox autoUnbindtoPortal(WxContent wxContent) {
		String params = wxContent.getContent();
		try {
			if (params != null && params != "") {
				params = params.replace("[", "(").replace("]", ")");
				bindService.updateBindStartUp(params);
				return MessageBox.build("1", "success");
			}
		} catch (Exception e) {
			// 连接微信失败不进行操作
			logger.error("微信端提醒官网自动解绑异常", e);
			return MessageBox.build("0", "执行失败");
		}
		return MessageBox.build("0", "执行失败");
	}
}
