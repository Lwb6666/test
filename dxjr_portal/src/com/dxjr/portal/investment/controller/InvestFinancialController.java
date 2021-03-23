package com.dxjr.portal.investment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.dxjr.portal.investment.mapper.ChannelBindingInvestMapper;
import com.dxjr.portal.investment.service.InvestMentService;
import com.dxjr.portal.investment.util.Tripledes;
import com.dxjr.portal.investment.vo.ChannelBindingInvestVo;
import com.dxjr.portal.investment.vo.UserInfo;
import com.dxjr.portal.investment.vo.WdtxRtInfo;
import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BlackListVo;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberLoginCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.tzjinterface.service.MemberBindingService;
import com.dxjr.portal.tzjinterface.service.RequesturlLogService;
import com.dxjr.portal.tzjinterface.vo.RequesturlLogVo;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.portal.util.StringUtils;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

@Controller
@RequestMapping(value = "/ttxProduct")
public class InvestFinancialController extends BaseController{

	public Logger logger = Logger.getLogger(InvestFinancialController.class);
	
	public static final String COOKIE_LOGIN_USERID = new String("LOGIN_USERID");
	
	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;
	
	@Autowired
	private BlackListMapper blackListMapper;
	
	@Autowired
	private MemberBindingService memberBindingService;
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@Autowired
	private InvestMentService investMentService;
	
	@Autowired
	private MobileApproMapper mobileApproMapper;
	
	@Autowired
	private ChannelBindingInvestMapper channelBindingInvestMapper;
	
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	
	@Autowired
	private RequesturlLogService requesturlLogService;
	
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * 跳转到投天下来源的用户登录页面
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @return
	 * @date 2016年3月25日
	 */
	@RequestMapping(value = "/forTtxLoginNew")
	public ModelAndView forTZJLoginNew(HttpServletRequest request, HttpSession session,HttpServletResponse httpResp) {	
		
		String ip = HttpTookit.getRealIpAddr(request);
		String message = null;
		try {
			if(outerInterfaceMapper.judgeIPForInterface(ip,11) <= 0){
				      logger.debug("register 平台IP未加入白名单 ip================"+ip);
				         session.setAttribute("message", "平台IP未加入白名单");
				         message ="平台IP未加入白名单";
				         return forword("member/ttxloginNew").addObject("message", message);		
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		// 校验请求信息
		    String rep = request.getParameter("params");
		    session.setAttribute("params", rep);
		    if(rep==null){
		    	   message="请求数据为空！";
		           session.setAttribute("message", "请求数据为空！");
		       }else{
		    	   message ="PC端登录";
		       }
		    Map<String,Object>  params = Tripledes.getInstance().decrypt(rep, Map.class);
		    String callback = (String) params.get("redirectUrl");
		return forword("member/ttxloginNew").addObject("message", message).addObject("callback",callback);
	}	
	/**
	 * 
	 * @author JingBinbin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年6月30日
	 */
	@RequestMapping(value = "/ttxRegister")
	@ResponseBody
	public String register(HttpServletRequest request, HttpServletResponse response){
		logger.debug("========================投资天下理财平台 register begin==========================");
		String resultJson="";
		String ip = HttpTookit.getRealIpAddr(request);
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		Map<String,Object> mapRq = new HashMap<String,Object>();
		try {
			if(outerInterfaceMapper.judgeIPForInterface(ip,11) <= 0){
			  resultMap.put("success", false);
			  resultMap.put("code", "107");
		      resultMap.put("msg", "平台IP未加入白名单");			          
		      logger.debug("register 平台IP未加入白名单 ip================"+ip);
		      String streslt = Tripledes.getInstance().encrypt(resultMap);
              return streslt;			
		      }
			response.setHeader("Content-type", "text/plain;charset=UTF-8");
			String params = request.getParameter("params");
			if(StringUtils.isNotEmpty(params)){
			mapRq = Tripledes.getInstance().decrypt(params, Map.class);
			String str = dataProcess(mapRq);//字段判空
			if(StringUtils.isNotEmpty(str) && !str.equals("1")){
			  return Tripledes.getInstance().encrypt(str);
			 }
			 // 是否进行手机认证
		      MobileApproCnd mobileApproCnd = new MobileApproCnd();
		      mobileApproCnd.setMobileNum(String.valueOf(mapRq.get("phone")));
		      Integer passedCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		      // 是否进行了手机绑定
		      ChannelBindingInvestVo uinfo=new ChannelBindingInvestVo();
		      uinfo.setStatus(1);
		      uinfo.setPhone(String.valueOf(mapRq.get("phone")));
		      Integer bindCount=channelBindingInvestMapper.queryChannelByUserPhone(uinfo);
			//验证改用户是否已经存在
			if(passedCount.intValue() == 0 && bindCount.intValue()==0){
				Member member = new Member();
		    	  String autoGenerateUserName = null;
		    	  String autoGeneratePassword = null;			
		    	  autoGenerateUserName = investMentService.generateUserName(String.valueOf(mapRq.get("phone")), null);
		    	  if (autoGenerateUserName != null) {
					 member.setUsername(autoGenerateUserName);
		    	  }
				  // 生成6位随机密码
				  autoGeneratePassword =investMentService.generatePassword();
				  if (autoGeneratePassword != null) {
						member.setLogpassword(autoGeneratePassword);
				  }
				  member.setIsFinancialUser(1);// 默认都生成为理财用户					
				  member.setSource(Integer.valueOf(81));// 投天下
				  member.settId("wdtx");
				  member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
				  member.setIp(HttpTookit.getRealIpAddr(request));
	              String resultStr = memberRegisterService.insertAutoGenerateMemberForWdty(member, String.valueOf(mapRq.get("phone")), request, request.getSession());

	              if ("success".equals(resultStr)) {	
	            	  
	            	// 用于sso
				   cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
	            	// 注册信息 
				     MemberCnd memberCnd = new MemberCnd();
					 memberCnd.setId(member.getId());
					 MemberVo memberVo= memberService.queryMemberByCnd(memberCnd);
	                 ChannelBindingInvestVo invo = new ChannelBindingInvestVo();
	                 invo.setBankCardNo(String.valueOf(mapRq.get("bankCardNo")));
	                 invo.setBankName(String.valueOf(mapRq.get("bankName")));
	                 invo.setBankCode(String.valueOf(mapRq.get("bankCode")));
	                 invo.setIdCardNo(String.valueOf(mapRq.get("idCardNo")));
	                 invo.setChannelUid(String.valueOf(mapRq.get("wdtxUserId")));
	                 invo.setPhone(String.valueOf(mapRq.get("phone")));
	                 invo.setRealName(String.valueOf(mapRq.get("realName")));
	                 invo.setUsername(memberVo.getUsername());
	                 invo.setSource(Integer.valueOf(81));
	                 invo.setStatus(Integer.valueOf(1));
	                 invo.setUserId(currentUser().getUserId());
	                 invo.setFromly("ttx");
	                 invo.setUserStatus("0");
	                 String token = StringUtil.getUuid();
	                 invo.setToken(token);
	                 
	                 channelBindingInvestMapper.insertEntityInfo(invo);
	            	  
	                 resultMap.put("success", true);
	                 resultMap.put("code", 1);
	                 resultMap.put("msg", "注册成功");
	                 resultMap.put("username", autoGenerateUserName);
	                 resultMap.put("pfUserId", currentUser().getUserId());
	                 resultMap.put("token", token);
	                 String streslt = Tripledes.getInstance().encrypt(resultMap);
	                 return streslt;
	              }
	              
			}else{
				resultMap.put("success", false);
				resultMap.put("code", "1003");//用户已经存在
				resultMap.put("msg", "手机号已经存在");
				String streslt = Tripledes.getInstance().encrypt(resultMap);
                return streslt;
			}
			}else{
				resultMap.put("success", false);
				resultMap.put("code", "1004");//无任何请求数据
				resultMap.put("msg", "无任何请求数据");
				String streslt = Tripledes.getInstance().encrypt(resultMap);
                return streslt;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("用户注册失败："+e.getMessage());
		}
		
		resultMap.put("success", false);
		resultMap.put("code", "10000");//注册失败
		resultMap.put("msg", "注册失败");
		String streslt = Tripledes.getInstance().encrypt(resultMap);
        return streslt;
	}
	
	/**
	 * 
	 * @author JingBinbin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年6月30日
	 */
	@RequestMapping(value = "/loginBinding")
	@ResponseBody
	public MessageBox loginBinding(HttpServletRequest request, HttpSession session, HttpServletResponse response, MemberLoginCnd memberLoginCnd){
		logger.debug("========================投资天下理财平台 register begin==========================");
		ChannelBindingInvestVo pcount = new ChannelBindingInvestVo();
		
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");
        
		if ("1".equals(logRequestURLMsb.getCode())) {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return logRequestURLMsb;
		}
		// 解析请求参数Map
		String params = (String) session.getAttribute("params");
		Map<String, Object> result = Tripledes.getInstance().decrypt(params, Map.class);
		String username=String.valueOf(result.get("wdtxUserId")) ;
		if(username!=null && !username.equals("")){
			//根据投之家用户名获取平台用户名			
			String telephone=String.valueOf(result.get("phone")) ;
		     pcount =  channelBindingInvestMapper.queryChannelByUserId(username);
			if(pcount!=null){
				String mobile=mobileApproMapper.getMobileByUsername(String.valueOf(pcount.getUserId()));	
				if(StringUtils.isNotEmpty(mobile)&&StringUtils.isNotEmpty(telephone) && !telephone.equals(mobile)){				
					return MessageBox.build("406", "您在投天下的手机号与顶玺金融绑定的手机号不一致！");
				}	
				String loginMobile=mobileApproMapper.getMobileByUsername(memberLoginCnd.getUsername());
				if(StringUtils.isNotEmpty(loginMobile)&&StringUtils.isNotEmpty(telephone) && !telephone.equals(loginMobile)){				
					return MessageBox.build("406", "您在投天下的手机号与顶玺金融绑定的手机号不一致！");
				}
			}
			
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
			/*封装回调投天下URL的参数*/			
			if (null != result) {
				WdtxRtInfo wdinfo=new WdtxRtInfo();
				wdinfo.setWdtxUserId(username);
				wdinfo.setUsername(String.valueOf(result.get("phone")));
				wdinfo.setResultParams(params);
				wdinfo.setSecret(String.valueOf(result.get("secret")));
				if(pcount!=null){
					wdinfo.setToken(pcount.getToken());
				}
				WdtxRtInfo tzjReqParam=bingingOUser(memberVo, wdinfo, "2");
				if(tzjReqParam!=null){
//					d6c9c8
					String redirectUrl = String.valueOf(result.get("redirectUrl"));
//					String redirectUrl = String.valueOf("http://121.41.82.107/api/bindUser");
					return MessageBox.build("params", redirectUrl);
				}else{
					return MessageBox.build("0", "登录失败");
				}
				
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
	 * 平台老用户账号绑定
	 * @author JingBinbin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年6月30日
	 */
	public WdtxRtInfo bingingOUser(MemberVo memberVo,WdtxRtInfo wdinfo,String str){
		ChannelBindingInvestVo cinv = new ChannelBindingInvestVo();
		//手机号验证
		 cinv.setUserId(currentUser().getUserId());
	  try {
			 Integer pcount = channelBindingInvestMapper.queryChannelByUserPhone(cinv);
		 if(pcount==0){
			 ChannelBindingInvestVo  invo = new ChannelBindingInvestVo();
			 invo.setChannelUid(wdinfo.getWdtxUserId());
             invo.setPhone(wdinfo.getUsername());
             invo.setStatus(Integer.valueOf(1));
             invo.setUsername(memberVo.getUsername());
             invo.setSource(Integer.valueOf(81));
             invo.setFromly("ttx");
             invo.setUserId(currentUser().getUserId());
             invo.setUserStatus("1");
             String token = StringUtil.getUuid();
             invo.setToken(token);
			 channelBindingInvestMapper.insertEntityInfo(invo);
			 wdinfo.setSuccess(true);
			 wdinfo.setCode(1);
			 wdinfo.setMsg("绑定成功");
			 String userid = String.valueOf(currentUser().getUserId());
			 wdinfo.setPfUserId(userid);
		     return wdinfo;
		    }else{
		    	ChannelBindingInvestVo  invo = new ChannelBindingInvestVo();
				 invo.setChannelUid(wdinfo.getWdtxUserId());
	             invo.setPhone(wdinfo.getUsername());
	             invo.setStatus(Integer.valueOf(1));
	             invo.setUsername(memberVo.getUsername());
	             invo.setSource(Integer.valueOf(81));
	             invo.setUserId(currentUser().getUserId());
	             invo.setUserStatus("1");
	             channelBindingInvestMapper.updateEntityDl(invo);
	             wdinfo.setSuccess(true);
				 wdinfo.setCode(1);
				 wdinfo.setMsg("绑定成功");
				 String userid = String.valueOf(currentUser().getUserId());
				 wdinfo.setPfUserId(userid);
				 return wdinfo;
		    }
		 } catch (Exception e) {
				// TODO Auto-generated catch block
             wdinfo.setSuccess(false);
			 wdinfo.setCode(1006);
			 wdinfo.setMsg("绑定失败");
			 String userid = String.valueOf(currentUser().getUserId());
			 wdinfo.setPfUserId(userid);
			 return wdinfo;
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
	
	
	
	
	public static String dataProcess(Map<String,Object> map){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String resultJson="";
		if(!StringUtils.isNotEmpty(String.valueOf(map.get("from")))){
			resultMap.put("success", false);
			resultMap.put("code", "1002");//参数数字段为空
			resultMap.put("msg", "请求参数缺少");
			resultJson=JsonUtils.bean2Json(resultMap);
			return resultJson;
		}else if(!StringUtils.isNotEmpty(String.valueOf(map.get("wdtxUserId")))){
			resultMap.put("success", false);
			resultMap.put("code", "1002");//参数数字段为空
			resultMap.put("msg", "请求参数缺少");
			resultJson=JsonUtils.bean2Json(resultMap);
			return resultJson;
		}else if(!StringUtils.isNotEmpty(String.valueOf(map.get("username")))){
			resultMap.put("success", false);
			resultMap.put("code", "1002");//参数数字段为空
			resultMap.put("msg", "请求参数缺少");
			resultJson=JsonUtils.bean2Json(resultMap);
			return resultJson;
		}else if(!StringUtils.isNotEmpty(String.valueOf(map.get("phone")))){
			resultMap.put("success", false);
			resultMap.put("code", "1002");//参数数字段为空
			resultMap.put("msg", "请求参数缺少");
			resultJson=JsonUtils.bean2Json(resultMap);
			return resultJson;
		}else{
			return "1";
		}
	}
}
