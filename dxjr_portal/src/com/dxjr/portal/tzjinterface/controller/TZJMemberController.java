package com.dxjr.portal.tzjinterface.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BlackListVo;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberLoginCnd;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.portal.tzjinterface.entity.CallBackReq;
import com.dxjr.portal.tzjinterface.entity.Message;
import com.dxjr.portal.tzjinterface.entity.ServiceData;
import com.dxjr.portal.tzjinterface.service.MemberBindingService;
import com.dxjr.portal.tzjinterface.service.RequesturlLogService;
import com.dxjr.portal.tzjinterface.util.CryptException;
import com.dxjr.portal.tzjinterface.util.DES3;
import com.dxjr.portal.tzjinterface.util.EncapUrlParameter;
import com.dxjr.portal.tzjinterface.util.MessageCrypt;
import com.dxjr.portal.tzjinterface.util.ParseURLTool;
import com.dxjr.portal.tzjinterface.vo.MemberBindingVo;
import com.dxjr.portal.tzjinterface.vo.RequesturlLogVo;
import com.dxjr.portal.tzjinterface.vo.TzjRegisterVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.portal.tzjinterface.vo.TzjUserBindingVo;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:TZJ用户action<br />
 * </p>
 * 
 * @title TZJMemberController.java
 * @package com.dxjr.portal.tzjinterface.controller
 * @author hujianpan
 * @version 0.1 2014年10月15日
 */
@Controller
@RequestMapping(value = "/api/tzj")
public class TZJMemberController extends BaseController {

	public Logger logger = Logger.getLogger(TZJMemberController.class);
	/** 记住用户名 的缓存名字 */
	public static final String COOKIE_LOGIN_USERID = new String("LOGIN_USERID");
	private static final String TO_TZJLogin = "/api/tzj/forTZJLogin.html";
	private static final String TO_TZJLoginNew = "/api/tzj/forTZJLoginNew.html";
	

	@Autowired
	private MemberService memberService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private RequesturlLogService requesturlLogService;
	@Autowired
	private MemberBindingService memberBindingService;
	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	
	@Autowired
    private MessageCrypt messageCrypt;
	@Autowired
	private BlackListMapper blackListMapper;
	/**
	 * 跳转到投之家来源的用户登录页面
	 */
	@RequestMapping(value = "/forTZJLogin")
	public ModelAndView forTZJLogin(HttpServletRequest request, HttpSession session) {

		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		Map<String, String> result = null;
		if (null != params && params.size() > 1) {
			String timestamp=params.get("timestamp");
			// 构造解密后的参数Map
			result = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);			
		}
		Boolean isErrorRedirect = (Boolean) session.getAttribute("error");
		if (isErrorRedirect != null && isErrorRedirect && result == null) {
			result = (Map<String, String>) session.getAttribute("priorResultMap");
			session.removeAttribute("priorResultMap");
			if (result == null || result.size() == 0) {
				return redirect("/");
			}
		}		
		session.setAttribute("result", result);
		final Object hasAlertMessage = session.getAttribute("message");
		String message = null;
		if (StringUtils.isEmpty(hasAlertMessage)) {
			message = null;
		} else {
			message = String.valueOf(hasAlertMessage);
			session.removeAttribute("message");
		}		

		return forword("member/tzjlogin").addObject("message", message);
	}

	/**
	 * <p>
	 * Description：供登录方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param request
	 * @param session
	 * @param response
	 * @param memberLoginCnd
	 * @return String
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public MessageBox TZJLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response, MemberLoginCnd memberLoginCnd) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return logRequestURLMsb;
		}
		// 解析请求参数Map
		@SuppressWarnings("unchecked")
		Map<String, String> result = (Map<String, String>) session.getAttribute("result");
		if(result==null){
			return MessageBox.build("406", "请求已失效，请重新进入此页面！");
		}
		String username=result.get("username");
		if(username!=null && !username.equals("")){
			//根据投之家用户名获取平台用户名			
			String telephone=result.get("telephone");
			String usernamep=memberBindingService.queryMemberNameByTzjName(username);
			String mobile=mobileApproMapper.getMobileByUsername(usernamep);			
			if(mobile!=null && telephone!=null && !telephone.equals(mobile)){				
				return MessageBox.build("406", "您输入的手机号与顶玺金融绑定的手机号不一致！");
			}	
			String loginMobile=mobileApproMapper.getMobileByUsername(memberLoginCnd.getUsername());
			if(loginMobile!=null && telephone!=null && !telephone.equals(loginMobile)){				
				return MessageBox.build("406", "您输入的手机号与顶玺金融绑定的手机号不一致！");
			}	
			boolean flagSucc=false;
			try {
				if(usernamep!=null && !usernamep.equals("")){
					flagSucc = isBindingSucessByUsernamep(usernamep);
					if(flagSucc){					
						return MessageBox.build("406", "您的账户与顶玺金融已绑定！");
					}
				}
				if(memberLoginCnd.getUsername()!=null && !memberLoginCnd.getUsername().equals("")){
					flagSucc = isBindingSucessByUsernamep(memberLoginCnd.getUsername());
					if(flagSucc){					
						return MessageBox.build("406", "您的账户与顶玺金融已绑定！");
					}
				}
				flagSucc = isBindingSucessByUsername(username);
				if(flagSucc){					
					return MessageBox.build("406", "此账户已绑定！");
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}			
		}
		session.removeAttribute("result");

		// 判断MD5值是否正确
		if (result != null && result.get("sign") == null) {
			return MessageBox.build("4", "MD5校验失败,请注意网络安全！");
		}
		//判断连接时间是否在5分钟之内，防止黑客侵入
		long diffTimestampAndNow=DateUtils.diffTimestampAndNow(result.get("timestamp"));
		if(diffTimestampAndNow>300){
			return MessageBox.build("406", "时间失效");
		}
		MemberVo memberVo=null;
		try {
			if(memberLoginCnd.getUsername()!=null && !memberLoginCnd.getUsername().equals("")){
				List<BlackListVo> blackListVos= blackListMapper.queryByUserName(memberLoginCnd.getUsername().trim());
				if(null!=blackListVos&&blackListVos.size()>0){
					for(BlackListVo blackListVo:blackListVos){
						 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
							 return MessageBox.build("406", "您的账户有异常，请联系客服");
						 }
					}
				}
			}
			// shiro登录
			UsernamePasswordToken token = new UsernamePasswordToken(memberLoginCnd.getUsername(), MD5.toMD5(memberLoginCnd.getPasswd()), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setIp(HttpTookit.getRealIpAddr(request));
			loginCnd.setSessionId(session.getId());
			loginCnd.setPlatform(currentUser().getPlatform());
			// 调用登录逻辑
			String msg = memberService.saveLogin(loginCnd);
			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));

			// 保存cookie
			CookieGenerator cookieGenerator = new CookieGenerator();
			cookieGenerator.setCookieMaxAge(2147483647);
			if (memberLoginCnd.getSaveid() != null && "" != memberLoginCnd.getSaveid().trim()) {
				cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
				cookieGenerator.addCookie(response, memberLoginCnd.getCookieusername());
			}
			
			if (currentUser().getUserId() == null) {
				return MessageBox.build("5", "您未登录");
			}
			
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(currentUser().getUserId());
			memberVo = memberService.queryMemberByCnd(memberCnd);
			if(memberVo==null){
				return MessageBox.build("6", "当前用户信息异常");
			}
			
			/*封装回调投之家URL的参数*/			
			if (null != result) {				
				Map<String, String> tzjReqParam=bindingUserMemberInfo(memberVo, String.valueOf(1), result,"1");
				return MessageBox.build("1", "success",tzjReqParam);
			}			
						
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("登录失败", e);
			return MessageBox.build("0", "用户名或密码错误");
		}
		
		return MessageBox.build("1", "success");
		
	}

	/**
	 * 
	 * <p>
	 * Description:将平台用户信息与投之家用户信息进行绑定<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日 void
	 * @throws AppException
	 */
	public Integer bindingMemberInfo(Integer userId, String loggingType, Map<String, String> result, String askMode) throws AppException {
		MemberVo memberVo;
		if (userId == null) {
			return null;
		} else {
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(userId);
			memberVo = memberService.queryMemberByCnd(memberCnd);
		}
		// 已绑定成功直接返回
		if (!isBindingSucess(result, memberVo)) {
			logger.error("用户ID为   " + userId + "的账户已经绑定成功！");
			return null;
		}
		MemberBindingVo memberBindingVo = new MemberBindingVo();		
		//memberBindingVo.setService(String.valueOf("bindreport"));
		//memberBindingVo.setRequestFrom(String.valueOf("gcjr"));// 需与投之家协商	
		/**投之家传递参数*/
		memberBindingVo.setRequestFrom(result.get("from"));
		memberBindingVo.setService(result.get("service"));
		memberBindingVo.setUserName(result.get("username"));
		memberBindingVo.setEmail(result.get("email"));
		memberBindingVo.setTelephone(result.get("telephone"));
		memberBindingVo.setRealName(result.get("realName"));
		memberBindingVo.setCardNo(result.get("cardNo"));
		memberBindingVo.setTs(result.get("timestamp"));
		memberBindingVo.setSign(result.get("sign"));
		/**内部信息*/
		memberBindingVo.setDr(String.valueOf(1));
		memberBindingVo.setRegTime(memberVo.getAddtime());// 注册时间
		memberBindingVo.setUserId(memberVo.getId());	
		memberBindingVo.setUserNamep(memberVo.getUsername());
		memberBindingVo.setIsSuccess(Integer.valueOf(1));
		memberBindingVo.setLoggingType(loggingType);
		memberBindingVo.setMessage("投之家一键注册绑定成功");		
		try {
			memberBindingService.insertMemberBindingInfo(memberBindingVo);
		} catch (AppException e) {
			logger.error(e.getStackTrace());
			return null;
		}		
		return memberBindingVo.getId();
	}
	
	/**
	 * 平台老账户绑定接口
	 * @author WangQianJin
	 * @title @param userId
	 * @title @param loggingType
	 * @title @param result
	 * @title @param askMode
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月26日
	 */
	public Map<String, String> bindingUserMemberInfo(MemberVo memberVo, String loggingType, Map<String, String> result,String newType) throws AppException {
		Map<String, String> ruselt=null;		
		try {
			Map<String, String> params = constructionMemberBindingRequestParams(loggingType, result, memberVo);						
			MemberBindingVo memberBindingVo=new MemberBindingVo();
			Integer num=memberBindingService.queryCountByUserId(currentUser().getUserId());
			if(num.intValue()>0){
				//更新绑定状态				
				memberBindingVo.setUserId(currentUser().getUserId());
				memberBindingVo.setUserName(params.get("username"));
				memberBindingVo.setUserNamep(params.get("usernamep"));
				memberBindingVo.setLoggingType(loggingType);
				memberBindingVo.setIsSuccess(Integer.valueOf(1));
				memberBindingVo.setEmail(params.get("email"));
				memberBindingVo.setTelephone(params.get("telephone"));
				memberBindingVo.setRealName(params.get("realName"));
				memberBindingVo.setCardNo(params.get("cardNo"));
				memberBindingVo.setMessage("账号绑定成功！");			
				memberBindingService.updateMemberBindingInfo(memberBindingVo);	
			}else{
				memberBindingVo.setRequestFrom(params.get("from"));
				memberBindingVo.setService(params.get("service"));
				memberBindingVo.setUserName(params.get("username"));
				memberBindingVo.setEmail(params.get("email"));
				memberBindingVo.setTelephone(params.get("telephone"));
				memberBindingVo.setRealName(params.get("realName"));
				memberBindingVo.setCardNo(params.get("cardNo"));
				memberBindingVo.setTs(params.get("timestamp"));
				memberBindingVo.setSign(params.get("sign"));
				/**内部信息*/
				memberBindingVo.setDr(String.valueOf(1));
				memberBindingVo.setRegTime(memberVo.getAddtime());// 注册时间
				memberBindingVo.setUserId(currentUser().getUserId());	
				memberBindingVo.setUserNamep(params.get("usernamep"));
				memberBindingVo.setIsSuccess(Integer.valueOf(1));
				memberBindingVo.setLoggingType(loggingType);
				memberBindingVo.setMessage("投之家老用户绑定成功");		
				try {
					memberBindingService.insertMemberBindingInfo(memberBindingVo);
				} catch (AppException e) {
					logger.error(e.getStackTrace());
					return null;
				}
			}
			String timestamp=params.get("timestamp");
			if("1".equals(newType)){
				ruselt=EncapUrlParameter.buildEncryptParameter(params, timestamp);
			}else if("2".equals(newType)){
				ruselt=getNewResultByMember(memberBindingVo,params);	
			}	
			logger.info("账号绑定成功！");
		} catch (Exception e) {
			logger.error("封装投之家接口参数异常：");
			logger.error(e.getStackTrace());
		}				
		return ruselt;
	}
	
	/**
	 * 投之家新接口信息回调参数
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @param params
	 * @title @return
	 * @date 2016年3月31日
	 */
	private Map<String, String> getNewResultByMember(MemberBindingVo memberBindingVo,Map<String, String> params){
		Map<String, String> ruselt = new TreeMap<String, String>();
		try {		
			/*查询绑定时间*/
			MemberBindingVo cnd=new MemberBindingVo();
			cnd.setUserId(currentUser().getUserId());
			MemberBindingVo memRes=memberBindingService.queryMemberBindingInfo(cnd);
			/*封装请求内容*/
			CallBackReq req=new CallBackReq();
			req.setUsername(params.get("username"));
			req.setUsernamep(params.get("usernamep"));
			req.setRegisterAt(DateUtils.timeStampToDate(params.get("registerAt")));				
			req.setBindAt(DateUtils.timeStampToDate(memRes.getAddTime()));
			req.setBindType(Integer.parseInt(params.get("bindType")));
			System.out.println("username========================:"+req.getUsername());
			System.out.println("usernamep========================:"+req.getUsernamep());
			System.out.println("RegisterAt========================:"+req.getRegisterAt());
			System.out.println("BindAt========================:"+req.getBindAt());
			System.out.println("bindType========================:"+req.getBindType());
			
			logger.debug("appId=====================("+TZJConstants.appId+")");
			logger.debug("key=====================("+TZJConstants.aesKey+")");
			logger.debug("token=====================("+TZJConstants.token+")");
			logger.debug("username=====================("+req.getUsername()+")");
			logger.debug("usernamep=====================("+req.getUsernamep()+")");
			logger.debug("RegisterAt=====================("+req.getRegisterAt()+")");
			logger.debug("BindAt=====================("+req.getBindAt()+")");
			logger.debug("bindType=====================("+req.getBindType()+")");
			/*请求数据*/
			ServiceData sd = new ServiceData("bindUser", req);			
			Message msg = messageCrypt.encryptMsg(sd);
//			Gson gson = new Gson();
//			String data = gson.toJson(msg);
			ruselt.put("data", msg.getData());
			ruselt.put("timestamp", msg.getTimestamp().toString());
			ruselt.put("nonce", msg.getNonce());
			ruselt.put("signature", msg.getSignature());
			ruselt.put("platID", TZJConstants.appId);			
			System.out.println("data========================:"+ruselt.get("data"));
			System.out.println("timestamp========================:"+ruselt.get("timestamp"));
			System.out.println("nonce========================:"+ruselt.get("nonce"));
			System.out.println("signature========================:"+ruselt.get("signature"));
			System.out.println("platID========================:"+ruselt.get("platID"));
			
			logger.debug("data=====================("+ruselt.get("data")+")");
			logger.debug("timestamp=====================("+ruselt.get("timestamp")+")");
			logger.debug("nonce=====================("+ruselt.get("nonce")+")");
			logger.debug("signature=====================("+ruselt.get("signature")+")");
			logger.debug("appId=====================("+ruselt.get("platID")+")");
			
		} catch (Exception e) {
			logger.error("封装投之家接口参数异常：");
			logger.error(e.getStackTrace());
		}
		return ruselt;
	}

	private Boolean isBindingSucess(Map<String, String> result, MemberVo memberVo) throws AppException {
		MemberBindingVo isBindinVoCnd = new MemberBindingVo();
		isBindinVoCnd.setDr(String.valueOf(1));
		isBindinVoCnd.setUserId(memberVo.getId());
		isBindinVoCnd.setUserNamep(memberVo.getUsername());
		isBindinVoCnd.setIsSuccess(Integer.valueOf(1));
		MemberBindingVo isBindinVo = memberBindingService.queryMemberBindingInfo(isBindinVoCnd);
		if (isBindinVo == null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private Boolean isBindingSucessByUsername(String username) throws AppException {
		MemberBindingVo isBindinVoCnd = new MemberBindingVo();
		isBindinVoCnd.setDr(String.valueOf(1));
		isBindinVoCnd.setUserName(username);
		isBindinVoCnd.setIsSuccess(Integer.valueOf(1));
		MemberBindingVo isBindinVo = memberBindingService.queryMemberBindingInfo(isBindinVoCnd);
		if (isBindinVo != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	private Boolean isBindingSucessByUsernamep(String usernamep) throws AppException {
		MemberBindingVo isBindinVoCnd = new MemberBindingVo();
		isBindinVoCnd.setDr(String.valueOf(1));
		isBindinVoCnd.setUserNamep(usernamep);
		isBindinVoCnd.setIsSuccess(Integer.valueOf(1));
		MemberBindingVo isBindinVo = memberBindingService.queryMemberBindingInfo(isBindinVoCnd);
		if (isBindinVo != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 
	 * <p>
	 * Description:构造账号绑定请求参数<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月20日
	 * @param loggingType
	 * @param result
	 * @param memberVo
	 * @return Map<String,String>
	 */
	private Map<String, String> constructionMemberBindingRequestParams(String loggingType, Map<String, String> result, MemberVo memberVo) {
		Map<String, String> params = new TreeMap<String, String>();
		if (null == result) {
			return params;
		}
		params.put("from", "touzhijia");
		params.put("service", "bindUser");		
		params.put("username", result.get("username"));
		params.put("usernamep", memberVo.getUsername());
		params.put("registerAt", memberVo.getAddtime());
		params.put("type", loggingType);
		params.put("timestamp", DateUtils.getCurrentTimeStamp());
		params.put("sign", result.get("sign"));
		params.put("email", result.get("email"));
		params.put("telephone", result.get("telephone"));
		params.put("realName", result.get("realName"));
		params.put("cardNo", result.get("cardNo"));
		params.put("bindType", loggingType);
		params.put("nonce", result.get("nonce"));		
		return params;
	}

	/**
	 * <p>
	 * Description:单点登录方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param request
	 * @param session
	 * @param response
	 * @param memberLoginCnd
	 * @return String
	 */
	@RequestMapping(value = "/ssologin")
	@ResponseBody
	public ModelAndView ssoLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			logger.error("新增访问日志失败！");
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);		
		// 构造解密后的参数Map
		if (params == null || params.size() <= 0) {
			session.setAttribute("message", "投之家传递过来的请求参数解析失败！");
			return redirect(TO_TZJLogin);
		}
		String timestamp=params.get("timestamp");
		Map<String, String> resultMap = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		session.setAttribute("priorResultMap", resultMap);
		session.setAttribute("error", true);
		// 判断MD5值是否正确
		if (resultMap != null) {
			if (resultMap.get("sign") == null) {
				logger.error("MD5校验失败");
				session.setAttribute("message", "MD5校验失败");
				return redirect(TO_TZJLogin);
			}
		} else {
			// 解析参数异常
			logger.error("解析参数异常。。。。");
			session.setAttribute("message", "参数解析异常");
			return redirect(TO_TZJLogin);
		}
		//判断连接时间是否在5分钟之内，防止黑客侵入
		long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
		if(diffTimestampAndNow>300){
			logger.error("时间失效");
			session.setAttribute("message", "时间失效");
			return redirect(TO_TZJLogin);
		}
		// 判断投之家和平台账户是否绑定成功
		MemberBindingVo memberBindinResult = new MemberBindingVo();
		try {
			MemberBindingVo memberBindingVo = new MemberBindingVo();
			memberBindingVo.setIsSuccess(Integer.valueOf(1));
			memberBindingVo.setUserName(resultMap.get("username"));
			memberBindingVo.setUserNamep(resultMap.get("usernamep"));
			memberBindinResult = memberBindingService.queryMemberBindingInfo(memberBindingVo);
		} catch (AppException e1) {
			logger.error("网络异常，请稍后再试！");
			session.setAttribute("message", "网络异常，请重试！");
			return redirect(TO_TZJLogin);
		}
		if (memberBindinResult == null) {
			logger.equals("投之家与平台账户未绑定");
			session.setAttribute("message", "投之家与平台账号未绑定，请登入！");
			return redirect(TO_TZJLogin);
		}
		try {
			 List<BlackListVo>  blackListVos = blackListMapper.queryByUserName(memberBindinResult.getUserNamep().trim());
			if(null!=blackListVos&&blackListVos.size()>0){
				for(BlackListVo blackListVo:blackListVos){
					 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
					    logger.equals("您的账户有异常，请联系客服");
						session.setAttribute("message", "您的账户有异常，请联系客服！");
						return redirect(TO_TZJLogin);
					 }
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(resultMap.get("usernamep"));
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);

		try {
			// shiro登录
			UsernamePasswordToken token = new UsernamePasswordToken(memberVo.getUsername(), memberVo.getLogpassword(), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setIp(HttpTookit.getRealIpAddr(request));
			loginCnd.setSessionId(session.getId());
			loginCnd.setPlatform(currentUser().getPlatform());
			// 调用登录逻辑
			String msg = memberService.saveLogin(loginCnd);

			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));

			session.removeAttribute("priorResultMap");
			session.removeAttribute("error");
			if (BusinessConstants.VISITOR_UNAUTHERIZED.equals(msg)) {
				logger.error("请先前往认证");
				return redirect("/member/toCheckMemberInfo.html");
			}
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("登录失败", e);
			session.setAttribute("message", "登入失败，请稍后。。。");
			return redirect(TO_TZJLogin);
		}

		final String bid = resultMap.get("bid");
		// 借款标 id 为0 时直接跳转到个人中心
		if (StringUtils.isEmpty(bid) || "0".equals(bid.trim())) {
			return redirect("/myaccount/toIndex.html");
		}
		// 债权转让项目
		if (bid.contains("_")) {
			final String[] string = bid.split("_");
			if (null != string && string.length == 2) {
				return redirect("/zhaiquan/" + string[1] + ".html");
			}
		}
		if(bid.length()>=9){
			return redirect("/dingqibao/" + bid + ".html");
		}else{
			return redirect("/toubiao/" + bid + ".html");
		}		
	}

	private MessageBox logRequestURL(String requestPath, String direction) {
		// 访问路径
		String urlStr = requestPath;
		RequesturlLogVo newRequesturlLogVo = new RequesturlLogVo();
		newRequesturlLogVo.setUrlString(urlStr);
		newRequesturlLogVo.setDirection(direction);
		newRequesturlLogVo.setDr(String.valueOf(1));
		newRequesturlLogVo.setIsSuccess(Integer.valueOf(1));
		try {
			requesturlLogService.insertRequestUrlLog(newRequesturlLogVo);
		} catch (AppException e1) {
			return MessageBox.build("5", "网络异常，请稍后重试！");
		}
		return MessageBox.build("1", String.valueOf(newRequesturlLogVo.getId()));
	}

	/**
	 * <p>
	 * Description:投之家平台第一次登入，自动生成用户<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/autogeneratemember")
	@ResponseBody
	public String autogeneratemember(HttpServletRequest request, HttpSession session, HttpServletResponse response) {		
		logger.debug("进入autogeneratemember （自动生成平台账号方法），使用投之家账号登录！ ");
		String result = BusinessConstants.SUCCESS;
		Member member = new Member();
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			logger.error(logRequestURLMsb.getMessage());
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		String timestamp=params.get("timestamp");
		// 构造解密后的参数Map
		Map<String, String> resultMap = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		session.setAttribute("priorResultMap", resultMap);
		session.setAttribute("error", true);
		
		try {	
			// 判断MD5值是否正确
			if (null != resultMap) {
				if (resultMap.get("sign") == null) {
					logger.error("MD5校验失败");
					session.setAttribute("message", "MD5校验失败");
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("MD5校验失败","utf-8")));
				}
			} else {
				logger.error("参数解析异常。。。");
				session.setAttribute("message", "参数解析异常！");
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("参数解析异常","utf-8")));
			}
			//判断连接时间是否在5分钟之内，防止黑客侵入
			long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
			if(diffTimestampAndNow>300){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("时间失效","utf-8")));
			}
			
			String mobaile=resultMap.get("telephone");
			if (null != mobaile && !"".equals(mobaile.trim())) {
				// 验证手机号是否存在
				MobileApproCnd mobileApproCnd = new MobileApproCnd();
				mobileApproCnd.setMobileNum(mobaile);
				Integer usernameCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
				if (null != usernameCount && usernameCount > 0) {
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					return JsonUtils.bean2Json(MessageBox.build("-1001", URLEncoder.encode("手机号码已存在","utf-8")));
				}
			}
			String userName=memberBindingService.queryMemberNameByTzjName(resultMap.get("username"));
			if (null != userName && !"".equals(userName.trim())) {
				// 验证用户名
				MemberRegisterCnd usernameCnd = new MemberRegisterCnd();
				usernameCnd.setUsername(userName.trim());
				Integer usernameCount = memberRegisterMapper.queryRepeatMemberCount(usernameCnd);
				if (null != usernameCount && usernameCount > 0) {
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					return JsonUtils.bean2Json(MessageBox.build("-1002", URLEncoder.encode("该用户已绑定","utf-8")));
				}
			}			
		} catch (Exception e1) {
			logger.error((e1.getMessage()));
			session.setAttribute("message", "系统校验异常!");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			try {
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("系统校验异常!","utf-8")));
			} catch (UnsupportedEncodingException e) {
				logger.error((e.getMessage()));
			}
		}

		String autoGenerateUserName = null;
		String autoGeneratePassword = null;
		Integer bmid=null;
		try {
			autoGenerateUserName = generateUserName(resultMap.get("username"), null);
			if (autoGenerateUserName != null) {
				member.setUsername(autoGenerateUserName);
			}
			// 生成6位随机密码
			autoGeneratePassword = generatePassword();
			if (autoGeneratePassword != null) {
				member.setLogpassword(autoGeneratePassword);
			}
			member.setIsFinancialUser(1);// 默认都生成为理财用户
			member.setEmail(resultMap.get("email"));
			member.setSource(Integer.valueOf(17));// 投之家
			member.settId("tzj");
			member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			member.setIp(HttpTookit.getRealIpAddr(request));
			result = memberRegisterService.insertAutoGenerateMember(member, resultMap, request, session);
			if ("success".equals(result)) {
				// 用于sso
				cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
				// 绑定用户
				bmid=bindingMemberInfo(currentUser().getUserId(), String.valueOf(0), resultMap, TZJConstants.POST_URL);
				session.removeAttribute("priorResultMap");
				session.removeAttribute("error");
				logger.info("自动生成用户成功，并登入成功！");
				logger.info("离开autogeneratemember （自动生成平台账号方法），使用投之家账号登录！ ");				
			}
		} catch (Exception e) {
			logger.error("register=====", e);
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("网络连接异常，请稍候重试！");
			session.setAttribute("message", "网络连接异常，请稍后再试！");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			try {
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("网络连接异常，请稍后再试！","utf-8")));
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage());
			}
		}
		logger.error(result);
		session.setAttribute("message", result);	
		if(bmid!=null){			
			TzjRegisterVo tzjVo=memberBindingService.queryMemberBindingInfoById(bmid);
			Map<String, String> mapTzj=getDESForTzjRegisterVo(tzjVo);
			String desStr=JsonUtils.bean2Json(mapTzj);			
			logger.info("一键注册传输数据为：   =============" + desStr);
			//处理成功，设置状态码为201
			response.setStatus(HttpServletResponse.SC_CREATED);
			return desStr;
		}		
		return null;
	}
	
	//获取加密后的对象
	private Map<String, String> getDESForTzjRegisterVo(TzjRegisterVo tzjVo){
		Map<String, String> result=null;
		try {
			if(tzjVo!=null){
				Map<String, String> params=new TreeMap<String, String>();
				params.put("from", tzjVo.getFrom());
				params.put("registerAt", tzjVo.getRegisterAt());
				params.put("type", tzjVo.getType());
				params.put("username", tzjVo.getUsername());
				params.put("usernamep", tzjVo.getUsernamep());
				params.put("sign", tzjVo.getSign());
				params.put("timestamp", tzjVo.getTimestamp());
				result=ParseURLTool.buildEncryptionParameter(params,tzjVo.getTimestamp());				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:生成随机密码<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @return String
	 */
	private String generatePassword() {
		StringBuffer buffer = new StringBuffer(UUID.randomUUID().toString().replace("-", ""));
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < 6; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:生成用户名规则
	 * 
	 * 
	 * <br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @return String
	 */
	private String generateUserName(String userName, Integer autoIndex) {
		String name = userName + (autoIndex == null ? "" : ++autoIndex);
		try {
			memberRegisterService.queryMemberRepeat(null, null, name);
		} catch (AppException app) {
			autoIndex = autoIndex == null ? 0 : autoIndex;
			name = generateUserName(userName, autoIndex);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
		return name;
	}
	
	/**
	 * 获取用户绑定关系
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param response
	 * @title @return
	 * @date 2015年11月30日
	 */
	@RequestMapping(value = "/userBinding")
	@ResponseBody
	public String userBinding(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if (logRequestURLMsb.getCode() == "1") {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return JsonUtils.bean2Json(logRequestURLMsb);
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		String timestamp=params.get("timestamp");
		// //构造解密后的参数Map
		Map<String, String> result = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		try{
			// //判断MD5值是否正确
			if (result.get("sign") == null) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("MD5校验失败","utf-8")));
			}
			//判断连接时间是否在5分钟之内，防止黑客侵入
			long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
			if(diffTimestampAndNow>300){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("时间失效","utf-8")));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		// 查询投资记录信息
		List<TzjUserBindingVo> userBindingList = null;
		TzjTenderRecordCnd tzjTenderRecordCnd=new TzjTenderRecordCnd();
		tzjTenderRecordCnd.setStartTime(result.get("startTime"));
		tzjTenderRecordCnd.setEndTime(result.get("endTime"));
		tzjTenderRecordCnd.setUsernameStr(JsonUtils.getSqlStrByJson(result.get("username")));
		tzjTenderRecordCnd.setType(result.get("type"));
		try {
			userBindingList = memberBindingService.queryUserBindingInfoByCnd(tzjTenderRecordCnd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//如果无记录，则返回NONE
		if(userBindingList==null || userBindingList.size()==0){
			String none=DES3.encryptModeTzj(TZJConstants.NONE,timestamp);
			return JsonUtils.returnTzjForJson(none,timestamp);
		}
		String desStr=DES3.encryptModeTzj(JsonUtils.bean2Json(userBindingList),timestamp);
		String tzjStr=JsonUtils.returnTzjForJson(desStr,timestamp);
		logger.info("传输数据为：   =============" + tzjStr);
		return tzjStr;
		
	}
	
	/**
	 * 投之家新绑定登录接口
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @return
	 * @date 2016年3月25日
	 */
	@RequestMapping(value = "/forTZJLoginNew")
	public ModelAndView forTZJLoginNew(HttpServletRequest request, HttpSession session,HttpServletResponse httpResp) {	
		
		// 校验请求信息
		Message bindUserMessage = (Message)request.getServletContext().getAttribute("bindUserMessage");
		if(bindUserMessage==null){
			session.setAttribute("message", "请求数据为空！");
			return redirect(TO_TZJLoginNew);
		}
		//删除对象
		request.getServletContext().removeAttribute("bindUserMessage");
		
		Map<String,String> resultMap = (Map<String, String>)request.getServletContext().getAttribute("bindUserResult");
		if(resultMap==null){
			session.setAttribute("message", "获取信息为空！");
			return redirect(TO_TZJLoginNew);
		}
		//获取登录类型，0:PC，1:WAP
		String type=resultMap.get("type");		
		if("0".equals(type)){
			final Object hasAlertMessage = session.getAttribute("message");
			String message = null;
			if (StringUtils.isEmpty(hasAlertMessage)) {
				message = null;
			} else {
				message = String.valueOf(hasAlertMessage);
				session.removeAttribute("message");
			}
			return forword("member/tzjloginNew").addObject("message", message).addObject("callback",TZJConstants.callback);
		}else{	
			resultMap.put("data", bindUserMessage.getData());
			resultMap.put("timestamp", bindUserMessage.getTimestamp().toString());
			resultMap.put("nonce", bindUserMessage.getNonce());
			resultMap.put("signature", bindUserMessage.getSignature());
			return redirect(TZJConstants.WX_OLDUSER_URL).addAllObjects(resultMap);
		}
	}

	/**
	 * <p>
	 * Description：供登录方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param request
	 * @param session
	 * @param response
	 * @param memberLoginCnd
	 * @return String
	 */
	@RequestMapping(value = "/loginNew")
	@ResponseBody
	public MessageBox loginNew(HttpServletRequest request, HttpSession session, HttpServletResponse response, MemberLoginCnd memberLoginCnd) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return logRequestURLMsb;
		}
		// 解析请求参数Map
		@SuppressWarnings("unchecked")
		Map<String, String> result = (Map<String, String>) request.getServletContext().getAttribute("bindUserResult");
		if(result==null){
			return MessageBox.build("406", "请求已失效，请重新进入此页面！");
		}
		//删除对象
		request.getServletContext().removeAttribute("bindUserResult");
		
		String username=result.get("username");
		if(username!=null && !username.equals("")){
			//根据投之家用户名获取平台用户名			
			String telephone=result.get("telephone");
			String usernamep=memberBindingService.queryMemberNameByTzjName(username);
			String mobile=mobileApproMapper.getMobileByUsername(usernamep);			
			if(mobile!=null && telephone!=null && !telephone.equals(mobile)){				
				return MessageBox.build("406", "您在投之家的手机号与顶玺金融绑定的手机号不一致！");
			}	
			String loginMobile=mobileApproMapper.getMobileByUsername(memberLoginCnd.getUsername());
			if(loginMobile!=null && telephone!=null && !telephone.equals(loginMobile)){				
				return MessageBox.build("406", "您在投之家的手机号与顶玺金融绑定的手机号不一致！");
			}	
//			boolean flagSucc=false;
//			try {
//				if(usernamep!=null && !usernamep.equals("")){
//					flagSucc = isBindingSucessByUsernamep(usernamep);
//					if(flagSucc){					
//						return MessageBox.build("406", "您的账户与顶玺金融已绑定！");
//					}
//				}
//				if(memberLoginCnd.getUsername()!=null && !memberLoginCnd.getUsername().equals("")){
//					flagSucc = isBindingSucessByUsernamep(memberLoginCnd.getUsername());
//					if(flagSucc){					
//						return MessageBox.build("406", "您的账户与顶玺金融已绑定！");
//					}
//				}
//				flagSucc = isBindingSucessByUsername(username);
//				if(flagSucc){					
//					return MessageBox.build("406", "此账户已绑定！");
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//			}			
		}

		MemberVo memberVo=null;
		try {
			if(memberLoginCnd.getUsername()!=null && !memberLoginCnd.getUsername().equals("")){
				List<BlackListVo> blackListVos= blackListMapper.queryByUserName(memberLoginCnd.getUsername().trim());
				if(null!=blackListVos&&blackListVos.size()>0){
					for(BlackListVo blackListVo:blackListVos){
						 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
							 return MessageBox.build("406", "您的账户有异常，请联系客服");
						 }
					}
				}
			}
			// shiro登录
			UsernamePasswordToken token = new UsernamePasswordToken(memberLoginCnd.getUsername(), MD5.toMD5(memberLoginCnd.getPasswd()), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setIp(HttpTookit.getRealIpAddr(request));
			loginCnd.setSessionId(session.getId());
			loginCnd.setPlatform(currentUser().getPlatform());
			// 调用登录逻辑
			String msg = memberService.saveLogin(loginCnd);
			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));

			// 保存cookie
			CookieGenerator cookieGenerator = new CookieGenerator();
			cookieGenerator.setCookieMaxAge(2147483647);
			if (memberLoginCnd.getSaveid() != null && "" != memberLoginCnd.getSaveid().trim()) {
				cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
				cookieGenerator.addCookie(response, memberLoginCnd.getCookieusername());
			}
			
			if (currentUser().getUserId() == null) {
				return MessageBox.build("5", "您未登录");
			}
			
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(currentUser().getUserId());
			memberVo = memberService.queryMemberByCnd(memberCnd);
			if(memberVo==null){
				return MessageBox.build("6", "当前用户信息异常");
			}
			
			/*封装回调投之家URL的参数*/			
			if (null != result) {
				Map<String, String> tzjReqParam=bindingUserMemberInfo(memberVo, String.valueOf(1), result,"2");
				return MessageBox.build("1", "success",tzjReqParam);
			}			
						
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("登录失败", e);
			return MessageBox.build("0", "用户名或密码错误");
		}
		
		return MessageBox.build("1", "success");
		
	}
	
	/**
	 * 新SSO单点登录接口
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param response
	 * @title @return
	 * @date 2016年3月25日
	 */
	@RequestMapping(value = "/ssologinNew")
	@ResponseBody
	public ModelAndView ssologinNew(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		// 新增访问日志主键
		Integer newRequestLogId = null;
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");
		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			logger.error("新增访问日志失败！");
		}	
		// 校验请求信息
		Message reqMessage = (Message)request.getServletContext().getAttribute("reqMessage");
		if(reqMessage==null){
			session.setAttribute("message", "请求数据为空！");
			return redirect(TO_TZJLoginNew);
		}
		//删除对象
		request.getServletContext().removeAttribute("reqMessage");
		
		if ((System.currentTimeMillis() / 1000) - reqMessage.getTimestamp() > TZJConstants.expireTime) {
			session.setAttribute("message", "时间戳过期！");
			return redirect(TO_TZJLoginNew);
    	}
		try {
			ServiceData req = messageCrypt.decryptMsg(reqMessage);
		} catch (CryptException e2) {
			session.setAttribute("message", "解密异常！");
			return redirect(TO_TZJLoginNew);
		}		
		Map<String,String> resultMap = (Map<String, String>)request.getServletContext().getAttribute("ssoResult");
		if(resultMap==null){
			session.setAttribute("message", "获取信息为空！");
			return redirect(TO_TZJLoginNew);
		}
		//删除对象
		request.getServletContext().removeAttribute("ssoResult");
		
		//获取登录类型，0:PC，1:WAP
		String type=resultMap.get("type");		
		if("0".equals(type)){
			// 判断投之家和平台账户是否绑定成功
			MemberBindingVo memberBindinResult = new MemberBindingVo();
			try {
				MemberBindingVo memberBindingVo = new MemberBindingVo();
				memberBindingVo.setIsSuccess(Integer.valueOf(1));
				memberBindingVo.setUserName(resultMap.get("username"));
				memberBindingVo.setUserNamep(resultMap.get("usernamep"));
				memberBindinResult = memberBindingService.queryMemberBindingInfo(memberBindingVo);
			} catch (AppException e1) {
				logger.error("网络异常，请稍后再试！");
				session.setAttribute("message", "网络异常，请重试！");
				return redirect(TO_TZJLoginNew);
			}
			if (memberBindinResult == null) {
				logger.equals("投之家与平台账户未绑定");
				session.setAttribute("message", "投之家与平台账号未绑定，请登入！");
				return redirect(TO_TZJLoginNew);
			}
			try {
				 List<BlackListVo>  blackListVos = blackListMapper.queryByUserName(memberBindinResult.getUserNamep().trim());
				if(null!=blackListVos&&blackListVos.size()>0){
					for(BlackListVo blackListVo:blackListVos){
						 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
						    logger.equals("您的账户有异常，请联系客服！");
							session.setAttribute("message", "您的账户有异常，请联系客服！");
							return redirect(TO_TZJLoginNew);
						 }
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setUsername(resultMap.get("usernamep"));
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			try {
				// shiro登录
				UsernamePasswordToken token = new UsernamePasswordToken(memberVo.getUsername(), memberVo.getLogpassword(), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
				Subject subject = SecurityUtils.getSubject();
				subject.login(token);
				LoginCnd loginCnd = new LoginCnd();
				loginCnd.setUserId(currentUser().getUserId());
				loginCnd.setUserName(currentUser().getUserName());
				loginCnd.setIp(HttpTookit.getRealIpAddr(request));
				loginCnd.setSessionId(session.getId());
				loginCnd.setPlatform(currentUser().getPlatform());
				// 调用登录逻辑
				String msg = memberService.saveLogin(loginCnd);
				// 用于sso
				cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
				if (BusinessConstants.VISITOR_UNAUTHERIZED.equals(msg)) {
					logger.error("请先前往认证");
					return redirect("/member/toCheckMemberInfo.html");
				}
			} catch (Exception e) {
				if (isLogin()) {
					SecurityUtils.getSubject().logout();
				}
				logger.error("登录失败", e);
				session.setAttribute("message", "登入失败，请稍后。。。");
				return redirect(TO_TZJLoginNew);
			}
			final String bid = resultMap.get("bid");
			/*home为首页，account为个人中心*/ 
			if (StringUtils.isEmpty(bid) || "home".equals(bid.trim())) {
				return redirect("/");
			}
			if ("account".equals(bid.trim())) {
				return redirect("/myaccount/toIndex.html");
			}
			// 债权转让项目
			if (bid.contains("_")) {
				final String[] string = bid.split("_");
				if (null != string && string.length == 2) {
					return redirect("/zhaiquan/" + string[1] + ".html");
				}
			}
			if(bid.length()>=9){
				return redirect("/dingqibao/" + bid + ".html");
			}else{
				return redirect("/toubiao/" + bid + ".html");
			}
		}else{	
			resultMap.put("data", reqMessage.getData());
			resultMap.put("timestamp", reqMessage.getTimestamp().toString());
			resultMap.put("nonce", reqMessage.getNonce());
			resultMap.put("signature", reqMessage.getSignature());
			return redirect(TZJConstants.WX_SSO_URL).addAllObjects(resultMap);
		}		
				
	}
}
