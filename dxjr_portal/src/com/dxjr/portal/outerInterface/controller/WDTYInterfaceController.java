package com.dxjr.portal.outerInterface.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BlackListVo;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.outerInterface.entity.WdtyMemberBinding;
import com.dxjr.portal.outerInterface.service.WDTYInterfaceService;
import com.dxjr.portal.outerInterface.util.Constants;
import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.outerInterface.util.TripleDesUtil;
import com.dxjr.portal.outerInterface.vo.WdtyCnd;
import com.dxjr.portal.outerInterface.vo.WdtyParamCnd;
import com.dxjr.portal.outerInterface.vo.WdtyReturnVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.tzjinterface.util.HttpclientPost;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * 
 * <p>
 * Description:网贷天眼接口Controller<br />
 * </p>
 * 
 * @title WDTYInterfaceController.java
 * @package com.dxjr.portal.outerInterface.controller
 * @author yangshijin
 * @version 0.1 2014年8月19日
 */
@Controller
@RequestMapping(value = "/wdty/api/")
public class WDTYInterfaceController extends BaseController {
	
	public Logger logger = Logger.getLogger(WDTYInterfaceController.class);
	
	public static final String COOKIE_LOGIN_USERID = new String("LOGIN_USERID");

	@Autowired
	private WDTYInterfaceService wdtyInterfaceService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private BlackListMapper blackListMapper;
	/**
	 * 
	 * <p>
	 * Description:外部访问接口测试页面<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "to_interface_testing")
	public ModelAndView toInterfaceTesting(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("test");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:每日成交量数据(仅统计净值标和抵押标)（网贷天眼调用接口）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "interface/transaction_info")
	public @ResponseBody
	String transaction_info(HttpServletRequest request) {
		String result = "-2";
		try {
			String date = request.getParameter("date"); // 格式为:yyyy-MM-dd
			result = wdtyInterfaceService.transactionInfo(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 平台贷款数据(仅统计净值标和抵押标)（网贷天眼调用接口）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "interface/loan_info")
	public @ResponseBody
	String loan_info(HttpServletRequest request) {
		String result = "-2";
		try {
			String date = request.getParameter("date"); // 格式为:yyyy-MM-dd
			result = wdtyInterfaceService.loanInfo(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 账户绑定
	 * @author WangQianJin
	 * @title @param request
	 * @title @return
	 * @date 2015年12月15日
	 */
	@RequestMapping(value = "/accountBinding")
	public @ResponseBody
	String accountBinding(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		String data="";
		logger.info("begin accountBinding mothed =====");
		WdtyParamCnd parameteVo = null;
		try {
			// 接受请求过来的加密数据
			String datas = request.getParameter("data");
			String signkey = request.getParameter("signkey");
			//String endatas=URLEncoder.encode(datas);	
			//String dedatas=URLDecoder.decode(datas);			
			//String appid = request.getParameter("appid");
			if(datas==null || signkey==null){				
				return returnToJson("4001","Missing necessary parameters",data);
			}
			// 解密
			String trData = TripleDesUtil.decrypt(Constants.KEY,datas);
			System.out.println("解密后的数据:" + trData);			
			// 平台需要拿到这些数据做校验是否登录成功
			Map<String, String> mpa = StringUtil.paramMap(trData); // 得到用户信息
			String localSign=TripleDesUtil.getToMD5(TripleDesUtil.getSignkeyByMap(mpa));
			System.out.println("localSign================:" + localSign);
			if(!signkey.equals(localSign)){
				return returnToJson("4003","Signkey check failed",data);
			}
			parameteVo=getWdtyParamByMap(mpa);
			if(parameteVo.getMobile()==null || "".equals(parameteVo.getMobile())){
				return returnToJson("4006","Invalid parameters",data);
			}
			// 验证手机号是否存在
			MobileApproCnd mobileApproCnd = new MobileApproCnd();
			mobileApproCnd.setMobileNum(parameteVo.getMobile());
			Integer usernameCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
			// 判断手机号是否已存在
			if (usernameCount.intValue() == 0) {
				/*Case1:天眼用户手机号在平台未注册*/
				Member member = new Member();
				String autoGenerateUserName = null;
				String autoGeneratePassword = null;			
				autoGenerateUserName = generateUserName(parameteVo.getUsername(), null);
				if (autoGenerateUserName != null) {
					member.setUsername(autoGenerateUserName);
				}
				// 生成6位随机密码
				autoGeneratePassword = generatePassword();
				if (autoGeneratePassword != null) {
					member.setLogpassword(autoGeneratePassword);
				}
				member.setIsFinancialUser(1);// 默认都生成为理财用户					
				member.setSource(Integer.valueOf(13));// 网贷天眼
				member.settId("wdty");
				member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
				member.setIp(HttpTookit.getRealIpAddr(request));
				String resultStr = memberRegisterService.insertAutoGenerateMemberForWdty(member, parameteVo.getMobile(), request, session);
				if ("success".equals(resultStr)) {	
					// 用于sso
					cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
					// 绑定用户
					parameteVo.setUser_id(currentUser().getUserId());
					parameteVo.setIs_reg(0);
					parameteVo.setStatus(1);
					parameteVo.setLoggingType(0);
					WdtyMemberBinding binding=bindingMemberInfo(parameteVo);
					data=getDataByNewBinding(binding);
					//加密后data
					String enData=TripleDesUtil.encryptData(data, Constants.KEY);					
					logger.info("网贷天眼一键注册绑定成功！");	
					return returnToJson(Constants.CODE,Constants.MESSAGE,enData);												
				}				
			}else{
				/*Case2:天眼用户手机号在平台已注册*/
				parameteVo.setIs_reg(1);
				parameteVo.setLogin_key(StringUtil.getUuid());
				data=getDataByOldBinding(parameteVo);
				//加密后data
				String enData=TripleDesUtil.encryptData(data, Constants.KEY);					
				logger.info("网贷天眼老用户已注册！");	
				return returnToJson(Constants.CODE,Constants.MESSAGE,enData);
			}
			return returnToJson(Constants.CODE,Constants.MESSAGE,data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("accountBinding 参数解析异常 =====" + e.getMessage());
			return returnToJson("4010","Data decrypt failed",data);
		}
	}
	

	
	/**
	 * 跳转到投之家来源的用户登录页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/forWdtyLogin")
	public ModelAndView forTZJLogin(HttpServletRequest request, HttpSession session) {		
		// 接受请求过来的加密数据
		String datas = request.getParameter("data");
		String signkey = request.getParameter("signkey");
		return forword("member/wdtyLogin").addObject("signkey", signkey).addObject("datas",datas);
	}
	
	/**
	 * 网贷天眼登录
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param response
	 * @title @param memberLoginCnd
	 * @title @return
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/wdtyLogin")
	@ResponseBody
	public MessageBox wdtyLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response, WdtyParamCnd wdtyParamCnd) {

		try {
			// 解析请求参数Map		
			boolean flagSucc=false;
			if(wdtyParamCnd.getUsernamep()!=null && !wdtyParamCnd.getUsernamep().equals("")){
				flagSucc = isBindingSucessByUsername(wdtyParamCnd.getUsernamep(),2);
				if(flagSucc){					
					return MessageBox.build("406", "平台账户已绑定！");
				}
			}
			if(wdtyParamCnd.getUsername()!=null && !wdtyParamCnd.getUsername().equals("")){
				flagSucc = isBindingSucessByUsername(wdtyParamCnd.getUsername(),1);
				if(flagSucc){					
					return MessageBox.build("406", "网贷天眼账户已绑定！");
				}
			}
			
			if(wdtyParamCnd.getSignkey()==null || wdtyParamCnd.getSignkey()==null){		
				return MessageBox.build("4001", "缺少必要的数据！");
			}
			if(wdtyParamCnd.getDatas()==null || wdtyParamCnd.getDatas()==null){		
				return MessageBox.build("4001", "缺少必要的数据！");
			}
			System.out.println("signkey==========================="+wdtyParamCnd.getSignkey());
			System.out.println("datas==========================="+wdtyParamCnd.getDatas());
			String trData=null;
			// 解密
			trData = TripleDesUtil.decrypt(Constants.KEY,wdtyParamCnd.getDatas());
			System.out.println("解密后的数据:" + trData);
			
			// 平台需要拿到这些数据做校验是否登录成功
			Map<String, String> mpa = StringUtil.paramMap(trData); // 得到用户信息
			String localSign=TripleDesUtil.getToMD5(TripleDesUtil.getSignkeyByMap(mpa));
			System.out.println("localSign================:" + localSign);
			if(!wdtyParamCnd.getSignkey().equals(localSign)){
				return MessageBox.build("4003", "签名串校验失败！");
			}
			try {
				if(wdtyParamCnd.getUsernamep()!=null && !wdtyParamCnd.getUsernamep().equals("")){
					List<BlackListVo>  blackListVos = blackListMapper.queryByUserName(wdtyParamCnd.getUsernamep().trim());
					if(null!=blackListVos&&blackListVos.size()>0){
						for(BlackListVo blackListVo:blackListVos){
							 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
								 return MessageBox.build("4001", "您的账户有异常，请联系客服！");
							 }
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			getWdtyParamByMap(mpa,wdtyParamCnd);			
			
			MemberVo memberVo=null;
			// shiro登录
			UsernamePasswordToken token = new UsernamePasswordToken(wdtyParamCnd.getUsernamep(), MD5.toMD5(wdtyParamCnd.getPasswd()), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
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
			if (wdtyParamCnd.getSaveid() != null && "" != wdtyParamCnd.getSaveid().trim()) {
				cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
				cookieGenerator.addCookie(response, wdtyParamCnd.getCookieusername());
			}
			
			if (currentUser().getUserId() == null) {
				return MessageBox.build("4101", "用户不存在");
			}
			
			//用户有可能用手机号登录，重新设置用户名
			if(currentUser().getUserName()!=null && !"".equals(currentUser().getUserName())){
				wdtyParamCnd.setUsernamep(currentUser().getUserName());
			}
			if(currentUser().getUserId()!=null && currentUser().getUserId()>0){
				flagSucc = isBindingSucessByUsername(currentUser().getUserId().toString(),3);
				if(flagSucc){					
					return MessageBox.build("406", "网贷天眼账户已绑定！");
				}
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(currentUser().getUserId());
			memberVo = memberService.queryMemberByCnd(memberCnd);
			if(memberVo==null){
				return MessageBox.build("4101", "用户不存在");
			}
			
			/*老用户绑定*/
			wdtyParamCnd.setUser_id(currentUser().getUserId());
			WdtyMemberBinding binding=bindingUserMemberInfo(wdtyParamCnd,memberVo);
			/*封装绑定信息*/
			wdtyParamCnd.setUserkey(binding.getUserkey());			
			String data=getDataBySendBinding(wdtyParamCnd);
			System.out.println("网贷天眼参数data======================"+data);
			//加密后data
			String enData=TripleDesUtil.encryptData(data, Constants.KEY);
			System.out.println("网贷天眼参数enData======================"+enData);
			//调用网贷天眼发送绑定信息
			String returnResult=TripleDesUtil.sendPostRequest(Constants.WDTY_MEMBER_BIND_URL, TripleDesUtil.getToMD5(data), Constants.APPID, enData);
			System.out.println("网贷天眼返回结果======================"+returnResult);
			Map mapResult = JsonUtils.json2Map(returnResult);
			String code=mapResult.get("code").toString();
			if(!Constants.CODE.equals(code)){
				logger.info("网贷天眼绑定异常");
			}
			//返回登录页面跳转到投资页面
			if(binding.getLoan_id()!=null){
				if(binding.getLoan_type()!=null){
					if(binding.getLoan_type().intValue()==2){
						return MessageBox.build("2", binding.getLoan_id());
					}else if(binding.getLoan_type().intValue()==3){
						return MessageBox.build("3", binding.getLoan_id());
					}
				}
				return MessageBox.build("1", binding.getLoan_id());		
			}				
						
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("登录失败", e);
			return MessageBox.build("0", "用户名或密码错误");
		}
		
		return MessageBox.build("4", "绑定成功");
		
	}
	
	/**
	 * 账户解绑
	 * @author WangQianJin
	 * @title @param request
	 * @title @return
	 * @date 2015年12月15日
	 */
	@RequestMapping(value = "accountUnbinding")
	public @ResponseBody
	String accountUnbinding(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		String data="";
		logger.info("begin accountUnbinding mothed =====");
		try {
			// 接受请求过来的加密数据
			String datas = request.getParameter("data");
			String signkey = request.getParameter("signkey");
			if(datas==null || signkey==null){				
				return returnToJson("4001","Missing necessary parameters",data);
			}
			String localSign=TripleDesUtil.getToMD5(datas);
			System.out.println("localSign================:" + localSign);
			if(!signkey.equals(localSign)){
				return returnToJson("4003","Signkey check failed",data);
			}
			// 解密
			String trData = TripleDesUtil.decrypt(signkey,datas);
			System.out.println("解密后的数据:" + trData);			
			NameValuePair[] postData = {
				new NameValuePair("signkey", Constants.KEY),																		
				new NameValuePair("appid", Constants.APPID), 
				new NameValuePair("data", datas)
			};		
			HttpclientPost.getHttpUrlPost(Constants.WDTY_MEMBER_UNBIND_URL,postData);
			return returnToJson(Constants.CODE,Constants.MESSAGE,trData);
		} catch (Exception e) {
			logger.info("accountUnbinding 参数解析异常 =====" + e.getMessage());
			return returnToJson("4010","Data decrypt failed",data);
		}	
		
	}	
	
	/**
	 * 自动登录获取口令
	 * @author WangQianJin
	 * @title @param request
	 * @title @return
	 * @date 2015年2月3日
	 */
	@RequestMapping(value = "/autoLoginPage")
	public @ResponseBody
	String autoLoginPage(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		String data="";
		logger.info("begin autoLoginPage mothed =====");
		WdtyParamCnd parameteVo = null;
		try {
			// 接受请求过来的加密数据
			String datas = request.getParameter("data");
			String signkey = request.getParameter("signkey");
			if(datas==null || signkey==null){				
				return returnToJson("4001","Missing necessary parameters",data);
			}
			// 解密
			String trData = TripleDesUtil.decrypt(signkey,datas);
			System.out.println("解密后的数据:" + trData);			
			// 平台需要拿到这些数据做校验是否登录成功
			Map<String, String> mpa = StringUtil.paramMap(trData); // 得到用户信息
			String localSign=TripleDesUtil.getToMD5(TripleDesUtil.getSignkeyByMap(mpa));
			System.out.println("localSign================:" + localSign);
			if(!signkey.equals(localSign)){
				return returnToJson("4003","Signkey check failed",data);
			}
			parameteVo=getWdtyParamByMap(mpa);
			if(parameteVo.getMobile()==null || "".equals(parameteVo.getMobile())){
				return returnToJson("4006","Invalid parameters",data);
			}
			parameteVo.setStatus(1);
			WdtyMemberBinding wdtyMemberBinding = wdtyInterfaceService.queryMemberBindingInfo(parameteVo);
			if(wdtyMemberBinding!=null){
				//loginKey只能使用一次，每次获取都重新生成
				String loginKey=StringUtil.getUuid();
				if(loginKey==null || "".equals(loginKey)){
					loginKey=UUID.randomUUID().toString();
				}
				System.out.println("loginKey======================================"+loginKey);
				wdtyMemberBinding.setLogin_key(loginKey);
				wdtyInterfaceService.updateMemberBindingInfo(wdtyMemberBinding);
				data="login_key="+loginKey;
			}			
			//加密后data
			String enData=TripleDesUtil.encryptData(data, Constants.KEY);					
			logger.info("网贷天眼获取自动登陆login_key成功！");	
			System.out.println("enData======================================"+enData);
			return returnToJson(Constants.CODE,Constants.MESSAGE,enData);
		} catch (Exception e) {
			logger.info("autoLoginPage 参数解析异常 =====" + e.getMessage());
			return returnToJson("4010","Data decrypt failed",data);
		}
	}
	
	/**
	 * 校验绑定关系
	 * @author WangQianJin
	 * @title @param request
	 * @title @return
	 * @date 2015年2月3日
	 */
	@RequestMapping(value = "/checkBinding")	
	public ModelAndView checkBinding(HttpServletRequest request,HttpSession session, HttpServletResponse response) {
//		String data="";
		logger.info("begin checkBinding mothed =====");
		WdtyParamCnd parameteVo = null;
		try {
			// 接受请求过来的加密数据
			String datas = request.getParameter("data");
			String signkey = request.getParameter("signkey");
			if(datas==null || signkey==null){				
				return redirect("/myaccount/toIndex.html");
			}
			// 解密
			String trData = TripleDesUtil.decrypt(signkey,datas);
			System.out.println("解密后的数据:" + trData);			
			// 平台需要拿到这些数据做校验是否登录成功
			Map<String, String> mpa = StringUtil.paramMap(trData); // 得到用户信息
			String localSign=TripleDesUtil.getToMD5(TripleDesUtil.getSignkeyByMap(mpa));
			System.out.println("localSign================:" + localSign);
			if(!signkey.equals(localSign)){
				return redirect("/myaccount/toIndex.html");
			}
			parameteVo=getWdtyParamByMap(mpa);
			if(parameteVo.getMobile()==null || "".equals(parameteVo.getMobile())){
				return redirect("/myaccount/toIndex.html");
			}
			if(parameteVo.getLogin_key()==null || "".equals(parameteVo.getLogin_key())){
				return redirect("/myaccount/toIndex.html");
			}
			parameteVo.setStatus(1);
			WdtyMemberBinding wdtyMemberBinding = wdtyInterfaceService.queryMemberBindingInfoByKey(parameteVo);
			if(wdtyMemberBinding!=null){				
				MemberCnd memberCnd = new MemberCnd();
				memberCnd.setId(wdtyMemberBinding.getUser_id());
				MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
				if(memberVo==null){
					return redirect("/myaccount/toIndex.html");
				}
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

				// 保存cookie
				CookieGenerator cookieGenerator = new CookieGenerator();
				cookieGenerator.setCookieMaxAge(2147483647);
				if (parameteVo.getSaveid() != null && "" != parameteVo.getSaveid().trim()) {
					cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
					cookieGenerator.addCookie(response, parameteVo.getCookieusername());
				}
				
				//登录以后清空loginKey
				wdtyInterfaceService.updateLoginKeyById(wdtyMemberBinding);
				
				//返回登录页面跳转到投资页面				
				if(parameteVo.getLoan_type()!=null){
					if(parameteVo.getLoan_type().intValue()==1){
						redirect("/toubiao/" + parameteVo.getLoan_id() + ".html");
					}else if(parameteVo.getLoan_type().intValue()==2){
						return redirect("/zhaiquan/" + parameteVo.getLoan_id() + ".html");
					}else if(parameteVo.getLoan_type().intValue()==3){
						return redirect("/dingqibao/" + parameteVo.getLoan_id() + ".html");
					}else{
						return redirect("/myaccount/toIndex.html");
					}
				}else{
					return redirect("/myaccount/toIndex.html");
				}	
			}else{
				return redirect("/myaccount/toIndex.html");
			}								
			logger.info("checkBinding校验绑定关系成功！");
			return redirect("/myaccount/toIndex.html");
		} catch (Exception e) {
			logger.info("checkBinding 参数解析异常 =====" + e.getMessage());
			return redirect("/myaccount/toIndex.html");
		}
	}
	
	/**
	 * 新老用户绑定
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding bindingUserMemberInfo(WdtyParamCnd parameteVo,MemberVo memberVo) throws AppException {
		WdtyMemberBinding binding = new WdtyMemberBinding();	
		try {						
			binding.setMobile(parameteVo.getMobile());
			binding.setUsername(parameteVo.getUsername());
			binding.setUsernamep(parameteVo.getUsernamep());
			binding.setSend_time(parameteVo.getSend_time());
			binding.setSid(parameteVo.getSid());
			if(parameteVo.getLoan_id()!=null){
				WdtyCnd wdtyCnd=StringUtil.getLoadIdAndType(parameteVo.getLoan_id().toString());
				binding.setLoan_id(Integer.parseInt(wdtyCnd.getBorrowId()));
				binding.setLoan_type(wdtyCnd.getBorrowType());
			}
			binding.setIs_reg(1);
			binding.setUser_id(parameteVo.getUser_id());
			binding.setStatus(1);
			//判断新旧用户(通过网贷天眼过来的用户都是新用户，否则不是)
			if(memberVo.getSource()!=null && memberVo.getSource().intValue()==13){
				binding.setLoggingType(0);
			}else{
				binding.setLoggingType(1);
			}			
			if(parameteVo.getUserkey()==null || "".equals(parameteVo.getUserkey())){
				binding.setUserkey(StringUtil.getUuid());
			}				
			Boolean falg=isBindingSucess(parameteVo);
			if(falg){						
				wdtyInterfaceService.updateMemberBindingInfo(binding);
			}else{						
				wdtyInterfaceService.insertWdtyMemberBinding(binding);
			}			
			logger.info("账号绑定成功！");
		} catch (Exception e) {
			logger.error("封装网贷天眼接口参数异常："+e.getMessage());
			e.printStackTrace();
		}				
		return binding;
	}
	
	/**
	 * 校验用户是否已绑定
	 * @author WangQianJin
	 * @title @param username
	 * @title @param type
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	private Boolean isBindingSucessByUsername(String username,int type) throws AppException {
		WdtyParamCnd parameteVo=null;
		if(type==1){
			parameteVo=new WdtyParamCnd();
			parameteVo.setUsername(username);
			parameteVo.setStatus(1);
		}else if(type==2){
			parameteVo=new WdtyParamCnd();
			parameteVo.setUsernamep(username);
			parameteVo.setStatus(1);
		}else if(type==3){
			parameteVo=new WdtyParamCnd();
			parameteVo.setUser_id(Integer.valueOf(username));
			parameteVo.setStatus(1);
		}
		return isBindingSucess(parameteVo);
	}
	
	/**
	 * 封装为JSON返回
	 * @author WangQianJin
	 * @title @param code
	 * @title @param message
	 * @title @param data
	 * @title @return
	 * @date 2015年12月16日
	 */
	private String returnToJson(String code,String message,String data){
		WdtyReturnVo returnVo=new WdtyReturnVo();		
		returnVo.setCode(code);
		returnVo.setMessage(message);
		returnVo.setData(data);
		return JsonUtils.bean2Json(returnVo);
	}
	
	/**
	 * 获取一键注册返回数据
	 * @author WangQianJin
	 * @title @param binding
	 * @title @return
	 * @date 2015年12月16日
	 */
	private String getDataByNewBinding(WdtyMemberBinding binding){
		StringBuilder sb = new StringBuilder();
		sb.append("is_reg=").append(binding.getIs_reg());
		sb.append("&mobile=").append(binding.getMobile());
		sb.append("&username=").append(binding.getUsernamep());
		sb.append("&sid=").append(binding.getSid());
		sb.append("&userkey=").append(binding.getUserkey());		
		return sb.toString();
	}
	
	/**
	 * 获取老用户绑定返回数据
	 * @author WangQianJin
	 * @title @param binding
	 * @title @return
	 * @date 2015年12月16日
	 */
	private String getDataByOldBinding(WdtyParamCnd parameteVo){
		StringBuilder sb = new StringBuilder();
		sb.append("is_reg=").append(parameteVo.getIs_reg());
		sb.append("&mobile=").append(parameteVo.getMobile());
		sb.append("&login_key=").append(parameteVo.getLogin_key());
		sb.append("&sid=").append(parameteVo.getSid());	
		return sb.toString();
	}
	
	/**
	 * 获取发送绑定信息给天眼
	 * @author WangQianJin
	 * @title @param binding
	 * @title @return
	 * @date 2015年12月16日
	 */
	private String getDataBySendBinding(WdtyParamCnd parameteVo){
		StringBuilder sb = new StringBuilder();
		sb.append("mobile=").append(parameteVo.getMobile());		
		sb.append("&send_time=").append(parameteVo.getSend_time());
		sb.append("&sid=").append(parameteVo.getSid());
		sb.append("&userkey=").append(parameteVo.getUserkey());	
		sb.append("&username=").append(parameteVo.getUsernamep());
		return sb.toString();
	}
	
	/**
	 * 将平台用户信息与网贷之家用户信息进行绑定
	 * @author WangQianJin
	 * @title @param userId
	 * @title @param loggingType
	 * @title @param result
	 * @title @param askMode
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding bindingMemberInfo(WdtyParamCnd parameteVo) throws AppException {
		MemberVo memberVo;
		if (parameteVo.getUser_id() == null) {
			return null;
		} else {
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(parameteVo.getUser_id());
			memberVo = memberService.queryMemberByCnd(memberCnd);
		}
		parameteVo.setStatus(1);
		// 已绑定成功直接返回
		if (isBindingSucess(parameteVo)) {			
			logger.error("用户ID为   " + parameteVo.getUser_id() + "的账户已经绑定成功！");
			return null;
		}
		/*封装网贷天眼绑定信息*/
		WdtyMemberBinding binding = new WdtyMemberBinding();		
		binding.setMobile(parameteVo.getMobile());
		binding.setUsername(parameteVo.getUsername());
		binding.setUsernamep(memberVo.getUsername());
		binding.setSend_time(parameteVo.getSend_time());
		binding.setSid(parameteVo.getSid());
		if(parameteVo.getLoan_id()!=null){
			WdtyCnd wdtyCnd=StringUtil.getLoadIdAndType(parameteVo.getLoan_id().toString());
			binding.setLoan_id(Integer.parseInt(wdtyCnd.getBorrowId()));
			binding.setLoan_type(wdtyCnd.getBorrowType());
		}		
		binding.setIs_reg(parameteVo.getIs_reg());
		binding.setUserkey(StringUtil.getUuid());
		binding.setUser_id(parameteVo.getUser_id());
		binding.setStatus(parameteVo.getStatus());
		binding.setLoggingType(parameteVo.getLoggingType());		
		wdtyInterfaceService.insertWdtyMemberBinding(binding);	
		return binding;
	}
	
	/**
	 * 查询是否绑定
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @param memberVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	private Boolean isBindingSucess(WdtyParamCnd parameteVo) throws AppException {		
		WdtyMemberBinding wdtyMemberBinding = wdtyInterfaceService.queryMemberBindingInfo(parameteVo);
		if (wdtyMemberBinding != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 自动生成用户名
	 * @author WangQianJin
	 * @title @param userName
	 * @title @param autoIndex
	 * @title @return
	 * @date 2015年12月16日
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
	 * 生成随机密码
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月16日
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
	 * 封装请求参数
	 * @author WangQianJin
	 * @title @param mpa
	 * @title @return
	 * @date 2015年12月15日
	 */
	private WdtyParamCnd getWdtyParamByMap(Map<String, String> mpa){
		WdtyParamCnd parameteVo = new WdtyParamCnd();		
		parameteVo.setMobile(mpa.get("mobile"));
		parameteVo.setUsername(mpa.get("username"));
		parameteVo.setSend_time(mpa.get("send_time"));
		parameteVo.setSid(mpa.get("sid"));
		if(mpa.get("loan_id")!=null){
			String bid=mpa.get("loan_id");
			// 债权转让项目
			if (bid.contains("_")) {
				final String[] string = bid.split("_");
				if (null != string && string.length == 2) {
					parameteVo.setLoan_id(Integer.parseInt(string[1]));
					parameteVo.setLoan_type(2);				
				}
			}else if(bid.length()>=9){
				parameteVo.setLoan_id(Integer.parseInt(bid));
				parameteVo.setLoan_type(3);
			}else{
				parameteVo.setLoan_id(Integer.parseInt(bid));
				parameteVo.setLoan_type(1);
			}
		}
		parameteVo.setLogin_key(mpa.get("login_key"));
		parameteVo.setUserkey(mpa.get("userkey"));
		return parameteVo;
	}
	
	/**
	 * 封装请求参数
	 * @author WangQianJin
	 * @title @param mpa
	 * @title @return
	 * @date 2015年12月15日
	 */
	private WdtyParamCnd getWdtyParamByMap(Map<String, String> mpa,WdtyParamCnd parameteVo){		
		parameteVo.setMobile(mpa.get("mobile"));
		parameteVo.setUsername(mpa.get("username"));
		parameteVo.setSend_time(mpa.get("send_time"));
		parameteVo.setSid(mpa.get("sid"));
		if(mpa.get("loan_id")!=null){
			String bid=mpa.get("loan_id");
			// 债权转让项目
			if (bid.contains("_")) {
				final String[] string = bid.split("_");
				if (null != string && string.length == 2) {
					parameteVo.setLoan_id(Integer.parseInt(string[1]));
					parameteVo.setLoan_type(2);				
				}
			}else if(bid.length()>=9){
				parameteVo.setLoan_id(Integer.parseInt(bid));
				parameteVo.setLoan_type(3);
			}else{
				parameteVo.setLoan_id(Integer.parseInt(bid));
				parameteVo.setLoan_type(1);
			}
		}
		parameteVo.setLogin_key(mpa.get("login_key"));
		parameteVo.setUserkey(mpa.get("userkey"));
		return parameteVo;
	}
}
