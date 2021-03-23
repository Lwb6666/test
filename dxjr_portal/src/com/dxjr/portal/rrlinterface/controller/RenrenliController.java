package com.dxjr.portal.rrlinterface.controller;

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
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.rrlinterface.service.RenrenliService;
import com.dxjr.portal.rrlinterface.util.RrlCommonUtil;
import com.dxjr.portal.rrlinterface.util.RrlConstants;
import com.dxjr.portal.rrlinterface.util.RrlMD5;
import com.dxjr.portal.rrlinterface.vo.RrlCnd;
import com.dxjr.portal.rrlinterface.vo.RrlMemberBinding;
import com.dxjr.portal.rrlinterface.vo.RrlTenderRecord;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;


@Controller
@RequestMapping(value = "/api/renrenli")
public class RenrenliController extends BaseController {

	public Logger logger = Logger.getLogger(RenrenliController.class);
	
	@Autowired
	private RenrenliService renrenliService;
	
	@Autowired
	private MobileApproMapper mobileApproMapper;
	
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;
	
	/**
	 * 
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年4月23日
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(HttpServletRequest request, HttpServletResponse response) {		
		
		logger.debug("========================人人利 register begin==========================");
		String resultJson="";
		try {			  
		      Map resultMap = new HashMap();
		      String ip=HttpTookit.getRealIpAddr(request);
			  if (outerInterfaceMapper.judgeIPForInterface(ip,9) <= 0) {
				  resultMap.put("Code", "107");
		          resultMap.put("Tip", "平台IP未加入白名单");		          
		          resultJson=JsonUtils.bean2Json(resultMap);
		          System.out.println(resultJson);
		          logger.debug("register 平台IP未加入白名单 ip================"+ip);
		          return resultJson;				   
			  }
		      response.setHeader("Content-type", "text/plain;charset=UTF-8");
		      String phone = request.getParameter("Phone");
		      String custid = request.getParameter("Cust_id");
		      String sign_type = request.getParameter("Sign_type");
		      String sign = request.getParameter("Sign");
		      String cfrom = request.getParameter("cfrom");
		      /*校验参数*/
		      if (StringUtil.isEmpty(custid)) {
		        resultMap.put("Code", "003");
		        resultMap.put("Tip", "Cust_id为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);		        
		        return resultJson;
		      }
		      //平台在接收到Cust_id(商务编号)时要进行验证，验证方法如下：
		      String res = test_cust_id(custid, phone);
		      Map rmap = (Map)JsonUtils.json2Map(res);
		      String code = rmap.get("Code").toString();	
		      if (!code.equals("101")) {
		        resultMap.put("Code", code);
		        resultMap.put("Tip", "Cust_id验证失败");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      if (StringUtil.isEmpty(phone)) {
		        resultMap.put("Code", "102");
		        resultMap.put("Tip", "电话号码不能为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }	      
		      if (StringUtil.isEmpty(sign_type)) {
		        resultMap.put("Code", "008");
		        resultMap.put("Tip", "sign_type为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }		      
		      if (StringUtil.isEmpty(sign)) {
		        resultMap.put("Code", "009");
		        resultMap.put("Tip", "sign值为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      if (!sign_type.equals(RrlConstants.rrl_type)) {
		        resultMap.put("Code", "103");
		        resultMap.put("Tip", "签名类型出错");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      /*MD5校验*/
		      String value = RrlMD5.MD5Encode(RrlConstants.rrl_cust_id + phone + RrlConstants.rrl_key).toLowerCase();
		      if (!value.equals(sign.toLowerCase())) {
		        resultMap.put("Code", "104");
		        resultMap.put("Tip", "签名出错");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }		      
		      if (StringUtil.isEmpty(cfrom)) {
		        resultMap.put("Code", "010");
		        resultMap.put("Tip", "cfrom值为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      // 是否进行手机认证
		      MobileApproCnd mobileApproCnd = new MobileApproCnd();
		      mobileApproCnd.setMobileNum(phone);
		      Integer passedCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		      // 是否进行了手机绑定
		      RrlCnd rrlCnd=new RrlCnd();
		      rrlCnd.setStatus(1);
		      rrlCnd.setPhone(phone);
		      Integer bindCount=renrenliService.queryRrlMemberCount(rrlCnd);
		      // 判断手机号是否已存在
		      if (passedCount.intValue() == 0 && bindCount.intValue() == 0) {
		    	  Member member = new Member();
		    	  String autoGenerateUserName = null;
		    	  String autoGeneratePassword = null;			
		    	  autoGenerateUserName = generateUserName(phone, null);
		    	  if (autoGenerateUserName != null) {
					 member.setUsername(autoGenerateUserName);
		    	  }
				  // 生成6位随机密码
				  autoGeneratePassword = generatePassword();
				  if (autoGeneratePassword != null) {
						member.setLogpassword(autoGeneratePassword);
				  }
				  member.setIsFinancialUser(1);// 默认都生成为理财用户					
				  member.setSource(Integer.valueOf(76));// 人人利
				  member.settId("rrl");
				  member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
				  member.setIp(HttpTookit.getRealIpAddr(request));
				  String resultStr = memberRegisterService.insertAutoGenerateMemberForWdty(member, phone, request, request.getSession());
				  if ("success".equals(resultStr)) {	
					  // 用于sso
					  cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
					  //添加绑定关系	
				      RrlMemberBinding rrlMemberBinding=new RrlMemberBinding();
				      rrlMemberBinding.setPhone(phone);
				      rrlMemberBinding.setUsernamep(autoGenerateUserName);
				      rrlMemberBinding.setCust_id(custid);
				      rrlMemberBinding.setSign_type(sign_type);
				      rrlMemberBinding.setSign(sign);
				      rrlMemberBinding.setCfrom(cfrom);
				      rrlMemberBinding.setUser_id(currentUser().getUserId());
				      rrlMemberBinding.setStatus(1);
				      rrlMemberBinding.setLoggingType(0);
				      String custkey = StringUtil.getUuid();
				      rrlMemberBinding.setCust_key(custkey);
				      
				      //根据手机号查询人人利信息
				      RrlCnd rrlExistCnd=new RrlCnd();			     
				      rrlExistCnd.setPhone(phone);
				      Integer existCount=renrenliService.queryRrlMemberCount(rrlExistCnd);
				      if (existCount.intValue() == 0) {
				    	  renrenliService.insertRrlMemberBinding(rrlMemberBinding);
				      }else{
				    	  renrenliService.updateRrlMemberBinding(rrlMemberBinding);
				      }				     
				      resultMap.put("Code", "101");
				      resultMap.put("Tip", "操作成功");
				      Map dataMap = new HashMap();
				      dataMap.put("Cust_key", rrlMemberBinding.getCust_key());
				      resultMap.put("Data", dataMap);
				      resultJson=JsonUtils.bean2Json(resultMap);
				      System.out.println(resultJson);
				      logger.info("人人利注册绑定成功！");	
				      return resultJson;					
				  }
		      }else{
		    	  resultMap.put("Code", "100");
			      resultMap.put("Tip", "手机号已存在");
			      resultJson=JsonUtils.bean2Json(resultMap);
			      System.out.println(resultJson);
			      return resultJson;
		      }
		      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		logger.debug("========================人人利 register end==========================");
		return null;
	}
	
	/**
	 * 
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年4月23日
	 */
	@RequestMapping(value = "/tenderRecord")
	@ResponseBody
	public String tenderRecord(HttpServletRequest request, HttpServletResponse response) {		
		logger.debug("========================人人利 tenderRecord begin==========================");
		String resultJson="";
		try {
		      response.setHeader("Content-type", "text/plain;charset=UTF-8");
		      Map resultMap = new HashMap();
		      String ip=HttpTookit.getRealIpAddr(request);
			  if (outerInterfaceMapper.judgeIPForInterface(ip,9) <= 0) {
				  resultMap.put("Code", "107");
		          resultMap.put("Tip", "平台IP未加入白名单");
		          resultJson=JsonUtils.bean2Json(resultMap);
		          System.out.println(resultJson);	
		          logger.debug("tenderRecord 平台IP未加入白名单 ip================"+ip);
		          return resultJson;				   
			  }
		      /*校验参数*/
		      String custid = request.getParameter("Cust_id");
		      String order_no = request.getParameter("Order_no");
		      String cust_key = request.getParameter("Cust_key");
		      String start_date = request.getParameter("Start_date");
		      String end_date = request.getParameter("End_date");
		      String sign_type = request.getParameter("Sign_type");
		      String sign = request.getParameter("Sign");
		      String cfrom = request.getParameter("cfrom");
		      if (StringUtil.isEmpty(custid)) {
		        resultMap.put("Code", "004");
		        resultMap.put("Tip", "Cust_id为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      String res = test_cust_id(custid, "");
		      Map rmap = (Map)JsonUtils.json2Map(res);
		      String code = rmap.get("Code").toString();
		      if (!code.equals("101")) {
		        resultMap.put("Code", code);
		        resultMap.put("Tip", "Cust_id验证失败");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }		      
		      if (StringUtil.isEmpty(sign_type)) {
		        resultMap.put("Code", "005");
		        resultMap.put("Tip", "sign_type为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      if (!sign_type.equals(RrlConstants.rrl_type)) {
		        resultMap.put("Code", "103");
		        resultMap.put("Tip", "签名类型出错");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }		     
		      if (StringUtil.isEmpty(sign)) {
		        resultMap.put("Code", "006");
		        resultMap.put("Tip", "sign为空");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      /*MD5安全校验*/
		      String value = RrlMD5.MD5Encode(RrlConstants.rrl_cust_id + order_no + cust_key + RrlConstants.rrl_key).toLowerCase();
		      if (!value.equals(sign.toLowerCase())) {
		        resultMap.put("Code", "105");
		        resultMap.put("Tip", "签名出错");
		        resultJson=JsonUtils.bean2Json(resultMap);
		        System.out.println(resultJson);
		        return resultJson;
		      }
		      /*参数处理*/
		      RrlCnd rrlCnd=new RrlCnd();
		      if(!StringUtil.isEmpty(order_no)){
		    	  if(order_no.contains("Z_")){
		    		  rrlCnd.setOrder_zno(order_no.substring(2));
		    		  rrlCnd.setOrder_dno("0");
		    		  rrlCnd.setOrder_bno("0");
		    	  }else if(order_no.contains("D_")){
		    		  rrlCnd.setOrder_dno(order_no.substring(2));
		    		  rrlCnd.setOrder_zno("0");
		    		  rrlCnd.setOrder_bno("0");
		    	  }else{
		    		  rrlCnd.setOrder_bno(order_no);
		    		  rrlCnd.setOrder_dno("0");
		    		  rrlCnd.setOrder_zno("0");
		    	  }
		      }
		      if(start_date!=null && start_date.length()==13){
		    	  rrlCnd.setStart_date(start_date.substring(0,start_date.length()-3));
		      }else{
		    	  rrlCnd.setStart_date(start_date);
		      }
		      if(end_date!=null && end_date.length()==13){
		    	  rrlCnd.setEnd_date(end_date.substring(0,end_date.length()-3));
		      }else{
		    	  rrlCnd.setEnd_date(end_date);
		      }		      
		      rrlCnd.setCust_key(cust_key);
		      /*获取投资记录并返回*/
		      List<RrlTenderRecord> list=renrenliService.queryTenderRecord(rrlCnd);
		      resultMap.put("Code", "101");
		      resultMap.put("Tip", "操作成功");		     
		      resultMap.put("Data", list);
		      resultJson=JsonUtils.bean2Json(resultMap);
		      System.out.println(resultJson);
		      logger.info("人人利获取投资记录成功！");	
		      return resultJson;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		logger.debug("========================人人利 tenderRecord end==========================");
		return null;
	}
	
	/**
	 * 验证商务编号及手机号
	 * @author WangQianJin
	 * @title @param cust_id
	 * @title @param phone
	 * @title @return
	 * @date 2016年4月23日
	 */
	public String test_cust_id(String cust_id, String phone){
	    Map paramMap = new HashMap();
	    paramMap.put("url", RrlConstants.rrl_cust_id_url);
	    Map dataMap = new HashMap();
	    dataMap.put("Cust_id", cust_id);
	    if (!StringUtil.isEmpty(phone)) {
	      dataMap.put("Phone", phone);
	    }
	    paramMap.put("params", dataMap);
	    return RrlCommonUtil.dopost(paramMap);
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
