package com.dxjr.portal.liumi.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dxjr.portal.liumi.service.LmChannelServices;
import com.dxjr.portal.liumi.utils.LmDatUtils;
import com.dxjr.portal.liumi.utils.MD5Util;
import com.dxjr.portal.liumi.utils.SHA1;
import com.dxjr.portal.liumi.vo.PlaceOrderRequest;
import com.dxjr.portal.liumi.vo.TokenRequest;

@Service
public class LmChannelServicesImpl implements LmChannelServices {

	public Logger logger = Logger.getLogger(LmChannelServicesImpl.class);
	
	@Override
	public String getToken() throws Exception {
		// TODO Auto-generated method stub
		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setAppkey(LmDatUtils.appkey);
		String md5AppSecret = MD5Util.string2MD5(LmDatUtils.appsecret);
		tokenRequest.setAppsecret(md5AppSecret);
		String sign = SHA1.getSHA1(tokenRequest.toSign());
		tokenRequest.setSign(sign);
		byte[] data = ("{\"appkey\":\"" + LmDatUtils.appkey
				+ "\",\"appsecret\":\"" + md5AppSecret
				+ "\",\"sign\":\"" + sign + "\"}")
				.getBytes();
		//String data = JSON.toJSONString(tokenRequest);
		logger.info("请求地址："+LmDatUtils.lmhost);
		String result = httpPost("/server/getToken", data);
		System.out.println("getTokenResult:" + result);
		Pattern p = Pattern.compile("\"token\":\"(.*)\"");
		Matcher m = p.matcher(result);
		m.find();
		String token = m.group(1);
		System.out.println("token:" + token);
		PlaceOrderRequest por = new PlaceOrderRequest();
		por.setToken(token);
		por.setMobile("15618810429");
		por.setPostpackage("LT500");
		placeOeder(por);
		return token;
	}

	@Override
	public String placeOeder(PlaceOrderRequest por) throws Exception {
		// TODO Auto-generated method stub
		PlaceOrderRequest orderRequest = new PlaceOrderRequest();
		orderRequest.setAppkey(LmDatUtils.appkey);
		String appver = "Http";
		orderRequest.setAppver(appver);
		String extno = "unittest";
		orderRequest.setExtno(extno);
		orderRequest.setFixtime("");
		orderRequest.setMobile(por.getMobile());
		orderRequest.setPostpackage(por.getPostpackage());
		orderRequest.setApiver("2.0");
		orderRequest.setDes("0");
		orderRequest.setToken(por.getToken());
		String sign = SHA1.getSHA1(orderRequest.toSign());
		byte[] data = ("{\"appkey\":\"" + LmDatUtils.appkey + "\","
				+ "\"appver\":\"" + appver + "\","
				+ "\"apiver\":\"2.0\",\"des\":\"0\","
				+ "\"extno\":\"" + extno + "\","
				+ "\"fixtime\":\"\","
				+ "\"mobile\":\"" + por.getMobile() + "\","
				+ "\"postpackage\":\"" + por.getPostpackage() + "\","
				+ "\"token\":\"" + por.getToken() + "\","
				+ "\"sign\":\"" + sign + "\"}").getBytes();
		String result = httpPost("/server/placeOrder", data);
		System.out.println("result:" + result);
		return result;
	}

	@Override
	public String checkLTPhone(PlaceOrderRequest por) throws Exception {
		// TODO Auto-generated method stub
		PlaceOrderRequest orderRequest = new PlaceOrderRequest();
		orderRequest.setAppkey(LmDatUtils.appkey);
		String appver = "Http";
		orderRequest.setAppver(appver);
		orderRequest.setMobile(por.getMobile());
		orderRequest.setPostpackage(por.getPostpackage());
		orderRequest.setToken(por.getToken());
		String sign = SHA1.getSHA1(orderRequest.toCheckSign());
		byte[] data = ("{\"appkey\":\"" + LmDatUtils.appkey + "\","
				+ "\"appsecret\":\"" + LmDatUtils.appsecret + "\","
				+ "\"token\":\"" + por.getToken() + "\","
				+ "\"mobile\":\"" + por.getMobile() + "\","
				+ "\"postpackage\":\"" + por.getPostpackage() + "\","
				+ "\"sign\":\"" + sign + "\"}").getBytes();
		String result = httpPost(LmDatUtils.lmhost, data);
		System.out.println("result:" + result);
		return result;
	}

	@Override
	public String phoneInfo(PlaceOrderRequest req) throws Exception {
		// TODO Auto-generated method stub
		PlaceOrderRequest orderRequest = new PlaceOrderRequest();
		orderRequest.setAppkey(LmDatUtils.appkey);
		orderRequest.setMobile(req.getMobile());
		orderRequest.setToken(req.getToken());
		String sign = SHA1.getSHA1(orderRequest.tophoneSign());
		byte[] data = ("{\"appkey\":\"" + LmDatUtils.appkey + "\","
				+ "\"token\":\"" + req.getToken() + "\","
				+ "\"mobile\":\"" + req.getMobile() + "\","
				+ "\"sign\":\"" + sign + "\"}").getBytes();
		String result = httpPost(LmDatUtils.lmhost, data);
		System.out.println("result:" + result);
		return result;
	}
	
	private String httpPost(String url, byte[] data)
			throws IOException {
		URL httpUrl = new URL(LmDatUtils.lmhost+url);
		logger.info("封装请求地址："+httpUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl
				.openConnection();
		httpURLConnection.setConnectTimeout(3000);
		httpURLConnection.setDoInput(true);// 从服务器获取数据
		httpURLConnection.setDoOutput(true);// 向服务器写入数据
		// 设置请求体的类型
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Lenth",
				String.valueOf(data.length));
		// 获得输出流，向服务器输出数据
		OutputStream outputStream = (OutputStream) httpURLConnection
				.getOutputStream();
		outputStream.write(data);

		String result = null;
		// 获得服务器响应的结果和状态码
		int responseCode = httpURLConnection.getResponseCode();
		InputStream inputStream;
		if (responseCode == 200) {
			// 获得输入流，从服务器端获得数据
			inputStream = (InputStream) httpURLConnection.getInputStream();
			// } else {
			// inputStream = (InputStream) httpURLConnection.getErrorStream();
			// }
			// 把从输入流InputStream按指定编码格式encode变成字符串String
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			if (inputStream != null) {
				while ((len = inputStream.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
				result = new String(byteArrayOutputStream.toByteArray(),
						"UTF-8");
			}
		}
		return result;
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl
    				.openConnection();
    		httpURLConnection.setConnectTimeout(3000);
    		httpURLConnection.setDoInput(true);// 从服务器获取数据
    		httpURLConnection.setDoOutput(true);// 向服务器写入数据
    		// 设置请求体的类型
    		httpURLConnection.setRequestProperty("Content-Type",
    				"application/x-www-form-urlencoded");
    		httpURLConnection.setRequestProperty("Content-Lenth",
    				String.valueOf(param.length()));
            
            
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

}
