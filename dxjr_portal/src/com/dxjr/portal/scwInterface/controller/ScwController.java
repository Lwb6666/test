package com.dxjr.portal.scwInterface.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.scwInterface.service.ScwService;
import com.dxjr.portal.scwInterface.vo.ScwBorrowVo;
import com.dxjr.portal.scwInterface.vo.ScwCnd;
import com.dxjr.portal.scwInterface.vo.ScwMemberBinding;
import com.dxjr.portal.scwInterface.vo.ScwTenderRecord;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.RegResource;
import com.dxjr.utils.exception.AppException;


@Controller
@RequestMapping(value = "/api/scw")
public class ScwController extends BaseController {

	public Logger logger = Logger.getLogger(ScwController.class);
	
	@Autowired
	private ScwService scwService;
	
	@Autowired
	private MobileApproMapper mobileApproMapper;
	
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;
	
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	
	/**
	 * 一键注册
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年4月23日
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(HttpServletRequest request, HttpServletResponse response) {		
		
		logger.debug("========================生菜网 register begin==========================");
		String resultJson="";
		try {			  
		      Map resultMap = new HashMap();
		      String ip=HttpTookit.getRealIpAddr(request);
			  if (outerInterfaceMapper.judgeIPForInterface(ip,10) <= 0) {
				  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "请求IP未加入白名单");
		    	  resultMap.put("from", "顶玺金融");		          
		          resultJson=JsonUtils.bean2Json(resultMap);
		          System.out.println(resultJson);
		          logger.debug("register 请求IP未加入白名单 ip================"+ip);
		          return resultJson;				   
			  }
		      String fr = request.getParameter("fr");
		      String username = request.getParameter("username");
//		      String userpwd = request.getParameter("userpwd");
		      String userpwd = null;
		      //用户名即用户手机号
		      String phone=username;
		      /*校验参数*/
		      if (StringUtil.isEmpty(fr)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "来源标识不能为空");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (StringUtil.isEmpty(username)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "用户名不能为空");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (!RegResource.checkMobileNumber(phone)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "手机号码输入有误");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (!"shengcai18".equals(fr)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "来源标识有误");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      MemberRegisterCnd usernameCnd = new MemberRegisterCnd();
			  usernameCnd.setUsername(username.trim());
		      Integer usernameCount = memberRegisterMapper.queryRepeatMemberCount(usernameCnd);
		      if (null != usernameCount && usernameCount > 0) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "用户名已经存在");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      // 是否进行手机认证
		      MobileApproCnd mobileApproCnd = new MobileApproCnd();
		      mobileApproCnd.setMobileNum(phone);
		      Integer passedCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		      // 是否进行了手机绑定
		      ScwCnd scwCnd=new ScwCnd();
		      scwCnd.setStatus(1);
		      scwCnd.setPhone(phone);
		      Integer bindCount=scwService.queryScwMemberCount(scwCnd);
		      // 判断手机号是否已存在
		      if (passedCount.intValue() == 0 && bindCount.intValue() == 0) {
		    	  Member member = new Member();
		    	  member.setUsername(username);
		    	  userpwd=generatePassword();
		    	  member.setLogpassword(userpwd);		    	  
				  member.setIsFinancialUser(1);// 默认都生成为理财用户					
				  member.setSource(Integer.valueOf(77));// 生菜网
				  member.settId("scw");
				  member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
				  member.setIp(HttpTookit.getRealIpAddr(request));
				  String resultStr = memberRegisterService.insertAutoGenerateMemberForWdty(member, phone, request, request.getSession());
				  if ("success".equals(resultStr)) {	
					  // 用于sso
					  cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
					  //添加绑定关系	
				      ScwMemberBinding scwMemberBinding=new ScwMemberBinding();
				      scwMemberBinding.setPhone(phone);
				      scwMemberBinding.setUsername(username);
				      scwMemberBinding.setUsernamep(username);
				      scwMemberBinding.setFr(fr);
				      scwMemberBinding.setUserpwd(member.getLogpassword());				      
				      scwMemberBinding.setUser_id(currentUser().getUserId());
				      scwMemberBinding.setStatus(1);
				      scwMemberBinding.setLoggingType(0);
				      
				      //根据手机号查询生菜网信息
				      ScwCnd scwExistCnd=new ScwCnd();			     
				      scwExistCnd.setPhone(phone);
				      Integer existCount=scwService.queryScwMemberCount(scwExistCnd);
				      if (existCount.intValue() == 0) {
				    	  scwService.insertScwMemberBinding(scwMemberBinding);
				      }else{
				    	  scwService.updateScwMemberBinding(scwMemberBinding);
				      }		
				      //获取绑定信息
				      String createtime="";
				      ScwMemberBinding info=scwService.queryScwMemberInfo(phone);
				      if(info!=null){
				    	  createtime=DateUtils.format(info.getAddtime(),DateUtils.YMD_HMS);				    			  
				      }
				      resultMap.put("status", "1");
				      resultMap.put("uid", currentUser().getUserId());
				      resultMap.put("createtime", createtime);
				      resultMap.put("from", "顶玺金融");				     
				      resultJson=JsonUtils.bean2Json(resultMap);
				      System.out.println(resultJson);
				      logger.info("生菜网注册绑定成功！");	
				      return resultJson;					
				  }
		      }else{
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "手机号已经存在");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		logger.debug("========================生菜网 register end==========================");
		return null;
	}
	
	/**
	 * 投资记录
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年4月23日
	 */
	@RequestMapping(value = "/tenderRecord")
	@ResponseBody
	public String tenderRecord(HttpServletRequest request, HttpServletResponse response) {		
		logger.debug("========================生菜网 tenderRecord begin==========================");
		String resultJson="";
		try {
		      Map resultMap = new HashMap();
		      String ip=HttpTookit.getRealIpAddr(request);
			  if (outerInterfaceMapper.judgeIPForInterface(ip,10) <= 0) {
				  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "请求IP未加入白名单");
		    	  resultMap.put("from", "顶玺金融");
		          resultJson=JsonUtils.bean2Json(resultMap);
		          System.out.println(resultJson);	
		          logger.debug("tenderRecord 请求IP未加入白名单 ip================"+ip);
		          return resultJson;				   
			  }
		      /*校验参数*/
		      String fr = request.getParameter("fr");
		      String stime = request.getParameter("stime");
		      String etime = request.getParameter("etime");
		      if (StringUtil.isEmpty(fr)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "来源标识不能为空");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (StringUtil.isEmpty(stime)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "开始时间不能为空");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (StringUtil.isEmpty(etime)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "结束时间不能为空");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }
		      if (!"shengcai18".equals(fr)) {
		    	  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "来源标识有误");
		    	  resultMap.put("from", "顶玺金融");
		    	  resultJson=JsonUtils.bean2Json(resultMap);
		    	  System.out.println(resultJson);		        
		    	  return resultJson;
		      }		      
		      ScwCnd scwCnd = new ScwCnd();
		      scwCnd.setStime(stime);
		      scwCnd.setEtime(etime);
		      /*获取投资记录并返回*/
		      List<ScwTenderRecord> list=scwService.queryTenderRecord(scwCnd);
		      resultMap.put("status", "1");
		      resultMap.put("from", "顶玺金融");		     
		      resultMap.put("data", list);
		      resultJson=JsonUtils.bean2Json(resultMap);
		      System.out.println(resultJson);
		      logger.info("生菜网获取投资记录成功！");	
		      return resultJson;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		logger.debug("========================生菜网 tenderRecord end==========================");
		return null;
	}
	
	/**
	 * 取标信息
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年4月23日
	 */
	@RequestMapping(value = "/queryborrow")
	@ResponseBody
	public String queryborrow(HttpServletRequest request, HttpServletResponse response) {		
		logger.debug("========================生菜网 queryborrow begin==========================");
		String resultJson="";
		try {
		      Map resultMap = new HashMap();
		      String ip=HttpTookit.getRealIpAddr(request);
			  if (outerInterfaceMapper.judgeIPForInterface(ip,10) <= 0) {
				  resultMap.put("status", "0");
		    	  resultMap.put("errinfo", "请求IP未加入白名单");
		    	  resultMap.put("from", "顶玺金融");
		          resultJson=JsonUtils.bean2Json(resultMap);
		          System.out.println(resultJson);	
		          logger.debug("tenderRecord 请求IP未加入白名单 ip================"+ip);
		          return resultJson;				   
			  }
		      /*校验参数*/
		      String type = request.getParameter("type");
		      Integer limitType=null;
		      if(type!=null && !"".equals(type)){
		    	  limitType=Integer.parseInt(type);
		      }
		      ScwCnd scwCnd = new ScwCnd();
		      scwCnd.setLimitType(limitType);
		      /*获取可投标列表并返回*/
		      List<ScwBorrowVo> list=scwService.queryBorrowVoList(scwCnd);
		      if(list!=null && list.size()>0){
		    	  resultMap.put("status", "1");		     
			      resultMap.put("data", list);			      
		      }else{
		    	  resultMap.put("status", "0");		     
			      resultMap.put("error", "暂无可投标的");			      
		      }	
		      resultJson=JsonUtils.bean2Json(resultMap);
		      System.out.println(resultJson);
		      logger.info("生菜网获取可投标列表！");
		      return resultJson;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		logger.debug("========================生菜网 queryborrow end==========================");
		return null;
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
}
