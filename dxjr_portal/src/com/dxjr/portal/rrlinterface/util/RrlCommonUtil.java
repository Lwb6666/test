package com.dxjr.portal.rrlinterface.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dxjr.portal.util.JsonUtils;

public class RrlCommonUtil {

	public static String dopost(Map<String, Object> map) {
	    try{
		      String url = map.get("url").toString();
		      URL surl = new URL(url);
		      Map paramMap = (Map)map.get("params");
		      String param = prepareParam(paramMap);
		      HttpURLConnection connection = (HttpURLConnection)surl.openConnection();
		      connection.setRequestProperty("accept", "*/*");
		      connection.setRequestProperty("connection", "Keep-Alive");
		      connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		      connection.setConnectTimeout(10000);
		      connection.setDoOutput(true);
		      connection.setDoInput(true);
		      PrintWriter out = new PrintWriter(connection.getOutputStream());
		      out.print(param);
		      out.flush();
		      String result = "";
		      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		      String line="";
		      while ((line = in.readLine()) != null){
		    	  result = result + line;
		      }
		      return result;
	    }catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	}

	private static String prepareParam(Map<String, Object> paramMap) {
	    StringBuffer sb = new StringBuffer();
	    if ((paramMap == null) || (paramMap.isEmpty())) {
	      return "";
	    }
	    Map<String, Object> dataMap = (Map)paramMap.get("Data");
	    String value="";
	    if ((dataMap == null) || (dataMap.isEmpty())) {
	      for (String key : paramMap.keySet()) {
	        value = paramMap.get(key) == null ? "" : paramMap.get(key).toString();
	        if (sb.length() < 1)
	          sb.append(key).append("=").append(value);
	        else {
	          sb.append("&").append(key).append("=").append(value);
	        }
	      }
	      return sb.toString();
	    }
	    List mapKeyList = new ArrayList();
	    for (String key : dataMap.keySet()) {
	    	Map valueData = (Map)dataMap.get(key);
	    	String sonvalue = JsonUtils.bean2Json(valueData);
	    	mapKeyList.add(sonvalue);
	    }
	    paramMap.put("Data", mapKeyList);
	    for (String key : paramMap.keySet()) {
		     String valueParam = paramMap.get(key) == null ? "" : paramMap.get(key).toString();
		     if (sb.length() < 1)
		        sb.append(key).append("=").append(valueParam);
		     else {
		        sb.append("&").append(key).append("=").append(valueParam);
		     }
	    }
	    return sb.toString();
	}
}
