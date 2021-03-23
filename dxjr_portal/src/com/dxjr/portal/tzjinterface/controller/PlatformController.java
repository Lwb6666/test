package com.dxjr.portal.tzjinterface.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.portal.tzjinterface.entity.InvestInfo;
import com.dxjr.portal.tzjinterface.entity.Message;
import com.dxjr.portal.tzjinterface.entity.Redirect;
import com.dxjr.portal.tzjinterface.entity.ServiceData;
import com.dxjr.portal.tzjinterface.exception.PlatformException;
import com.dxjr.portal.tzjinterface.service.PlatformService;
import com.dxjr.portal.tzjinterface.util.MessageCrypt;
import com.dxjr.portal.tzjinterface.util.SHA1;
import com.dxjr.portal.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * @author WangQianJin
 * @title 投之家新接口控制器
 * @date 2016年3月23日
 */
@Controller
@RequestMapping(value = "/tzj/interface")
public class PlatformController extends BaseController {
	
	public Logger logger = Logger.getLogger(PlatformController.class);

	@Autowired
	private PlatformService tzjNewInterfaceService;

	@Autowired
    private MessageCrypt messageCrypt;
	
	private GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 投之家新接口公用访问方法
	 * @author WangQianJin
	 * @title @param msg
	 * @title @param httpResp
	 * @title @return
	 * @title @throws Throwable
	 * @date 2016年3月24日
	 */
    @RequestMapping(value = "/command", method = RequestMethod.POST, consumes = "application/json" )
    @ResponseBody
    public Message command(@RequestBody Message msg, HttpServletRequest request, HttpServletResponse httpResp) throws Throwable {	
    	logger.info("-------------------------------- command begin --------------------------------");
    	logger.info("System.currentTimeMillis() ====================="+System.currentTimeMillis() / 1000);
    	logger.info("msg.getTimestamp() ====================="+msg.getTimestamp());
    	logger.info("TZJConstants.expireTime ====================="+TZJConstants.expireTime);
    	if ((System.currentTimeMillis() / 1000) - msg.getTimestamp() > TZJConstants.expireTime) {
    		throw new PlatformException(100, "时间戳过期");
    	}
    	ServiceData req = messageCrypt.decryptMsg(msg);
    	
    	Class<? extends PlatformService> claz = tzjNewInterfaceService.getClass();
    	Method[] methods = claz.getMethods();
    	Method method = null;
    	for (Method m : methods) {
    		if (m.getName().equals(req.getService())) {
    			method = m;
    			break;
    		}
    	}
    	if (method == null) {
        	throw new PlatformException(101, "错误的业务类型:" + req.getService());
    	}
    	request.setAttribute("timestamp", msg.getTimestamp().toString());
    	request.setAttribute("signature", msg.getSignature());
    	request.setAttribute("nonce", msg.getNonce());    	
    	Class<?>[] paramClasses = method.getParameterTypes();
    	if (paramClasses.length != 1) {
    		throw new PlatformException(101, req.getService() + "方法参数错误");
    	}
    	Gson gson = gsonBuilder.create();
    	//Object param = gson.fromJson(req.getBody(), paramClasses[0]);
    	Object param = JsonUtils.json2BeanForDate(req.getBody().toString(),paramClasses[0]);
    	Object ret = null;
    	try {
    		ret = method.invoke(tzjNewInterfaceService, param);
    	} catch (InvocationTargetException e) {
    		System.out.println("投之家接口command异常："+e.getMessage());
    		logger.error("投之家接口command异常："+e.getMessage());
    		throw e.getTargetException();
    	}    	
    	if (ret instanceof Redirect) {  
    		Redirect r = (Redirect)ret;
    		httpResp.sendRedirect(r.getUri());    		
    	}
    	ServiceData resp = new ServiceData(req.getService(), ret);
    	logger.info("-------------------------------- command end --------------------------------");
    	return messageCrypt.encryptMsg(resp);
    }
    
    /**
	 * 投之家新接口接收表单方式提交
	 * @author WangQianJin
	 * @title @param msg
	 * @title @param httpResp
	 * @title @return
	 * @title @throws Throwable
	 * @date 2016年3月24日
	 */
    @RequestMapping(value = "/commandForm", method = RequestMethod.POST)
    @ResponseBody
    public Message commandForm(HttpServletRequest request, HttpServletResponse httpResp) throws Throwable {	
    	logger.info("-------------------------------- commandFrom begin --------------------------------");
    	Message msg = new Message();
		msg.setData(request.getParameter("data"));
		msg.setNonce(request.getParameter("nonce"));
		msg.setSignature(request.getParameter("signature"));
		msg.setTimestamp(Long.parseLong(request.getParameter("timestamp")));
    	logger.debug("System.currentTimeMillis() ====================="+System.currentTimeMillis() / 1000);
    	logger.debug("msg.getTimestamp() ====================="+msg.getTimestamp());
    	logger.debug("TZJConstants.expireTime ====================="+TZJConstants.expireTime);
    	if ((System.currentTimeMillis() / 1000) - msg.getTimestamp() > TZJConstants.expireTime) {
    		throw new PlatformException(100, "时间戳过期");
    	}
    	ServiceData req = messageCrypt.decryptMsg(msg);
    	
    	Class<? extends PlatformService> claz = tzjNewInterfaceService.getClass();
    	Method[] methods = claz.getMethods();
    	Method method = null;
    	for (Method m : methods) {
    		if (m.getName().equals(req.getService())) {
    			method = m;
    			break;
    		}
    	}
    	if (method == null) {
        	throw new PlatformException(101, "错误的业务类型:" + req.getService());
    	}
    	request.setAttribute("timestamp", msg.getTimestamp().toString());
    	request.setAttribute("signature", msg.getSignature());
    	request.setAttribute("nonce", msg.getNonce()); 
    	request.setAttribute("reqMessage", msg);
    	Class<?>[] paramClasses = method.getParameterTypes();
    	if (paramClasses.length != 1) {
    		throw new PlatformException(101, req.getService() + "方法参数错误");
    	}
    	Gson gson = gsonBuilder.create();
    	//Object param = gson.fromJson(req.getBody(), paramClasses[0]);
    	Object param = JsonUtils.json2BeanForDate(req.getBody().toString(),paramClasses[0]);
    	Object ret = null;
    	try {
    		ret = method.invoke(tzjNewInterfaceService, param);
    	} catch (InvocationTargetException e) {
    		System.out.println("投之家接口commandForm异常："+e.getMessage());
    		logger.error("投之家接口commandForm异常："+e.getMessage());
    		throw e.getTargetException();
    	}    	
    	if (ret instanceof Redirect) {  
    		Redirect r = (Redirect)ret;
    		httpResp.sendRedirect(r.getUri());    		
    	}
    	ServiceData resp = new ServiceData(req.getService(), ret);
    	logger.info("-------------------------------- commandFrom end --------------------------------");
    	return messageCrypt.encryptMsg(resp);
    }
    
    /**
	 * 跳转到投之家来源的用户登录页面
	 */
	@RequestMapping(value = "/forTestSignature")
	public ModelAndView forTZJLogin(HttpServletRequest request, HttpSession session) {
		Message msg = new Message();
		msg.setTimestamp(1460545399L);
		msg.setNonce("lDD5458v6F42Eafx");
		String data = "mj2w8e++VINuKqcXnnafVXSKlurXza1GlkhuqqJNAaHCTztlyS0yO5/cfaIJy1hmyH1WbZSPZ0rS\n"+
"Wc4HrA8s/9RudhR16AMlkZhcSdNbUFqpBUmyQhoh+9H+yd8QLxK7c/GGWm/nhQk2GHqd5yRjytDC\n"+
"uFj5L7MDvLY37w4He5d/V143mnPrZEpbR+6p7hZUz7WoTNhLEnMEyLlMz84s1Ll0iR8Xp0c2sSGc\n"+
"mRFoLFSAAAm3puJUG/fqGo/84RsoTLeKYf12KnJimi1HVAT6d2qO4FBVr/rhJLN5JvG2aHKe+eZU\n"+
"Ii+I8/FEn4kSenkxGs8QfkGkh+79ooPnx4fKMbGtcpe7SnfXo+okkxbFTxCFcdD7xih/leRXH/Vg\n"+
"k5ZaM0oxdM9yoau5xhJ7eCY0kb9uQo5zTI4N8ROTfXDg1GOUoN8QPXoILAWoc5ziD1EP5OlG13ZR\n"+
"snn6mflFWYDLwtA2Fyu/7d6k5ZxQe5Lzi9EIYl1Dn6hYJUuDWtRr668yi9jhkqqxAwmIj1Td/m1m\n"+
"m1J8nNC+lF2z/2gGV95NV4yzW9TVi35IN96MQsouNeD/uaNrQlh7e47VjUfF09BM6wtgVA3MC6cw\n"+
"w7udxgtf5JjbpLrs++Wqs01PqnLp/5jGPA78DVNCll0gxxsMJfONEGnt44jcwYka7qhJ4/dOVp4y\n"+
"aw74Imn42KLSRkgjJ2xk259Ef5rG8cqXBSDaJQms4HQvS1g4EdyR0onczDGqHMrrt2FYtHSGrE07\n"+
"QUSKe+uyTgAjqWF+An2T4UK4BSNQoOyAPJR23Gtl/VeVPhBdBh7BazYUZkqylyvmRGhe2vLcqY2y\n"+
"eiCaPkk1aYLGX1HAOqyMRvJwk/HXFjr9LNj64fJETv3xKkBF/Mip2f7Q4WPSglfiVn5dynUAaF/T\n"+
"VldjBHfErS6niSIcaDzbYnKI0ZP88nGChBZd49tRxfUcO8Jub8svJ24ED+Q11TQ1MCAEsQ==";
		msg.setData(data);
		String token="shitoujinrong";
		String sign="";
		try{
			sign=SHA1.getSHA1(token, msg);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("getSignature======================="+sign);
		return forword("member/tzjTest").addObject("signature", sign);
	}

	public static void main(String args[]){
	    //insert code here
//		InvestInfo investInfo=new InvestInfo();
//		investInfo.setTags("1,2");
//		String s = JsonUtils.bean2Json(investInfo);
//		System.out.println(s);
	}
}
