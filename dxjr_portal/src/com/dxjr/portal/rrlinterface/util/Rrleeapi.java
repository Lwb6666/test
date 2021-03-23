package com.dxjr.portal.rrlinterface.util;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dxjr.portal.outerInterface.util.StringUtil;
import com.dxjr.portal.util.JsonUtils;

public class Rrleeapi {

	  //DEMO PRODUCT
//	  public String cust_id_url = "http://openapi.renrenlee.com/zlo/Getp2pinfo/custid/";
//	  public String rrl_cust_id = "bb9f00dc762f1e4f1cc57cade53ab7";
//	  public String rrl_key = "tiantianrrl";
//	  public String rrl_type = "MD5";
//	  public String push_url = "http://openapi.renrenlee.com/zlo/getp2pinfo/getsubscribe/";
//	  public String rrl_username = "zlo_uXEkn";
//	  public String rrl_password = "zlopwd_VKGVV9Tj";
	  
	  public String cust_id_url = "";
	  public String rrl_cust_id = "";
	  public String rrl_key = "";
	  public String rrl_type = "";
	  public String push_url = "";
	  public String rrl_username = "";
	  public String rrl_password = "";

	  public void setUserinfo(String cust_id_url, String rrl_cust_id, String rrl_key, String rrl_type, String push_url, String rrl_username, String rrl_password){
	    this.cust_id_url = cust_id_url;
	    this.rrl_cust_id = rrl_cust_id;
	    this.rrl_key = rrl_key;
	    this.rrl_type = rrl_type;
	    this.push_url = push_url;
	    this.rrl_username = rrl_username;
	    this.rrl_password = rrl_password;
	  }

	  public void data_capture(HttpServletRequest request, HttpServletResponse response, String packageName, String classname, String method){
	    try {
	      response.setHeader("Content-type", "text/plain;charset=UTF-8");
	      Map resultMap = new HashMap();
	      String custid = request.getParameter("Cust_id");
	      if (StringUtil.isEmpty(custid)) {
	        resultMap.put("Code", "004");
	        resultMap.put("Tip", "Cust_id为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }

	      String res = test_cust_id(custid, "");
	      Map rmap = (Map)JsonUtils.json2Map(res);
	      String code = rmap.get("Code").toString();

	      if (!code.equals("101")) {
	        resultMap.put("Code", code);
	        resultMap.put("Tip", "Cust_id验证失败");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String order_no = request.getParameter("Order_no");
	      String cust_key = request.getParameter("Cust_key");
	      String start_date = request.getParameter("Start_date");
	      String end_date = request.getParameter("End_date");
	      String sign_type = request.getParameter("Sign_type");
	      if (StringUtil.isEmpty(sign_type)) {
	        resultMap.put("Code", "005");
	        resultMap.put("Tip", "sign_type为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      if (!sign_type.equals(this.rrl_type)) {
	        resultMap.put("Code", "103");
	        resultMap.put("Tip", "签名类型出错");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String sign = request.getParameter("Sign");
	      if (StringUtil.isEmpty(sign)) {
	        resultMap.put("Code", "006");
	        resultMap.put("Tip", "sign为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String value = RrlMD5.MD5Encode(this.rrl_cust_id + order_no + cust_key + this.rrl_key).toLowerCase();
	      if (!value.equals(sign.toLowerCase())) {
	        resultMap.put("Code", "105");
	        resultMap.put("Tip", "签名出错");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      Map map = new HashMap();
	      map.put("order_no", order_no);
	      map.put("cust_key", cust_key);
	      map.put("start_date", start_date);
	      map.put("end_date", end_date);
	      map.put("sign_type", sign_type);
	      map.put("sign", sign);
	      map.put("response", response);
	      getMethod(packageName, classname, method, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  public void register(HttpServletRequest request, HttpServletResponse response, String packageName, String classname, String method){
	    try
	    {
	      Map resultMap = new HashMap();
	      response.setHeader("Content-type", "text/plain;charset=UTF-8");
	      String phone = request.getParameter("Phone");
	      String custid = request.getParameter("Cust_id");
	      if (StringUtil.isEmpty(custid)) {
	        resultMap.put("Code", "003");
	        resultMap.put("Tip", "Cust_id为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }

	      String res = test_cust_id(custid, phone);
	      Map rmap = (Map)JsonUtils.json2Map(res);
	      String code = rmap.get("Code").toString();

	      if (!code.equals("101")) {
	        resultMap.put("Code", code);
	        resultMap.put("Tip", "Cust_id验证失败");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      if (StringUtil.isEmpty(phone)) {
	        resultMap.put("Code", "102");
	        resultMap.put("Tip", "电话号码不能为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String custkey = request.getParameter("Cust_key");
	      String sign_type = request.getParameter("Sign_type");
	      if (StringUtil.isEmpty(sign_type)) {
	        resultMap.put("Code", "008");
	        resultMap.put("Tip", "sign_type为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String sign = request.getParameter("Sign");
	      if (StringUtil.isEmpty(sign)) {
	        resultMap.put("Code", "009");
	        resultMap.put("Tip", "sign值为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      if (!sign_type.equals(this.rrl_type)) {
	        resultMap.put("Code", "103");
	        resultMap.put("Tip", "签名类型出错");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String value = RrlMD5.MD5Encode(this.rrl_cust_id + phone + this.rrl_key).toLowerCase();
	      if (!value.equals(sign.toLowerCase())) {
	        resultMap.put("Code", "104");
	        resultMap.put("Tip", "签名出错");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      String cfrom = request.getParameter("cfrom");
	      if (StringUtil.isEmpty(cfrom)) {
	        resultMap.put("Code", "010");
	        resultMap.put("Tip", "cfrom值为空");
	        response.getWriter().write(JsonUtils.bean2Json(resultMap));
	        return;
	      }
	      Map map = new HashMap();
	      map.put("phone", phone);
	      map.put("custkey", custkey);
	      map.put("sign_type", sign_type);
	      map.put("sign", sign);
	      map.put("cfrom", cfrom);
	      map.put("response", response);
	      getMethod(packageName, classname, method, map);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  public String data_push(Map<String, Object> map) {
	    try {
	      Map paramMap = new HashMap();
	      paramMap.put("Cust_id", this.rrl_cust_id);
	      paramMap.put("Data", map);
	      paramMap.put("Sign_type", this.rrl_type);
	      paramMap.put("Sign", RrlMD5.MD5Encode(this.rrl_username + this.rrl_password + this.rrl_cust_id + this.rrl_key));
	      Map dataMap = new HashMap();
	      dataMap.put("url", this.push_url);
	      dataMap.put("params", paramMap);
	      return RrlCommonUtil.dopost(dataMap);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return "";
	  }

	  public String test_cust_id(String cust_id, String phone){
	    Map paramMap = new HashMap();
	    paramMap.put("url", this.cust_id_url);
	    Map dataMap = new HashMap();
	    dataMap.put("Cust_id", cust_id);
	    if (!StringUtil.isEmpty(phone)) {
	      dataMap.put("Phone", phone);
	    }
	    paramMap.put("params", dataMap);
	    return RrlCommonUtil.dopost(paramMap);
	  }

	  private static void getMethod(String packageName, String classname, String method, Map<String, Object> map) {
	    try {
	      if ((!StringUtil.isEmpty(packageName)) && (!StringUtil.isEmpty(classname)) && (!StringUtil.isEmpty(method))) {
	        String path = packageName + classname;
	        Class c = Class.forName(path);
	        Method m = c.getDeclaredMethod(method, new Class[] { Map.class });
	        m.invoke(c.newInstance(), new Object[] { map });
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
