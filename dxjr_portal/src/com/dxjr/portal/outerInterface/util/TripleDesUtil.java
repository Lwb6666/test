package com.dxjr.portal.outerInterface.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;

import com.dxjr.portal.tzjinterface.util.EncryptMD5;
import com.dxjr.portal.tzjinterface.util.HttpclientPost;

public class TripleDesUtil {

	public static String DESEDE = "DESede";
	
	public static String keyStr = Constants.KEY;
	
	/**
	 * 加密 
	 * @param input 加密数据
	 * @param key 秘钥
	 * @return
	 */
	public static String encrypt(String input, String key){
		
		byte[] crypted = null;
		try{
//			String strValue = new String(input.getBytes("ISO-8859-1"),"UTF-8");
			SecretKeySpec skey = new SecretKeySpec(keyStr.getBytes(), DESEDE);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes("UTF-8"));
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return new String(Base64.encodeBase64(crypted));		
	}
	
	/**
	 * 解密
	 * @param input 解密数据
	 * @param key 秘钥
	 * @return
	 */
	public static String decrypt(String key,String input){
		byte[] output = null;
		try{
			SecretKeySpec skey = new SecretKeySpec(keyStr.getBytes("UTF-8"), DESEDE);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(Base64.decodeBase64(input));
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return new String(output);
	}
	
	
	/**
	 * 数据解密
	 * @param key 秘钥
	 * @param input 待解密数据
	 * @return 解密后的结果
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptData(String key,String input) throws UnsupportedEncodingException{
		return TripleDesUtil.decrypt(keyStr,URLDecoder.decode(input,"UTF-8"));
	}
	
	/**
	 * 数据加密
	 * @param key 秘钥
	 * @param input 待解密数据
	 * @return 解密后的结果
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptData(String key,String input) throws UnsupportedEncodingException{
		return URLEncoder.encode(TripleDesUtil.encrypt(key,input));
	}
	
	/** 参数加密
	 * post 参数请求封装 
	 * @param key 秘钥
	 * @param input 加密内容
	 * @param appid 商户id
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String,String> getPostData(String key,String input,String appid) throws NoSuchAlgorithmException{
		// post请求参数的封装
		Map<String, String> form = new HashMap<String, String>();
		
		// 加密数据的调用 Base64 + TripleDes 
		// URLEncoder 我这在post发送的时候做了一次 所以这里就不加
		String tripleStr = TripleDesUtil.encrypt(input, key); // 得到加密数据密文
		System.out.println("加密数据:"+tripleStr);
		
		// 签名:1、参数先做排序以英文字母小写升序排。2、MD5加密得到签名
		String sort = StringUtil.paramSort(input); // 排序
		System.out.println("排序后:"+sort);
		
		String md5Signkey = null;
		try {
			md5Signkey = MD5.getMd5(sort.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 得到签名
		System.out.println("MD5加密后:"+md5Signkey);
		
		// 把参数封装好 post请求天眼接口
		form.put("appid",appid); // 测试商户id:javatest
		form.put("signkey",md5Signkey); // md5加密签名
		form.put("data",tripleStr);  // 测试的加密url密文
		
		return form;
	}
	
	/**
	 * 发送HTTP POST请求
	 * @author WangQianJin
	 * @title @param url
	 * @title @param signkey
	 * @title @param appid
	 * @title @param data
	 * @title @return
	 * @date 2015年12月17日
	 */
	public static String sendPostRequest(String url,String signkey,String appid,String data){
		NameValuePair[] postData = { 
			new NameValuePair("signkey", signkey),
			new NameValuePair("appid", appid),
			new NameValuePair("data", data)
		};
		String result=HttpclientPost.getHttpUrlPost(url,postData);
		return result;
	}
	
	/**
	 * 获取MD5值
	 * @author WangQianJin
	 * @title @param param
	 * @title @return
	 * @date 2016年2月1日
	 */
	public static String getToMD5(String param){
		try {
			return EncryptMD5.toMD5(param);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据结果获取拼接字符串
	 * @author WangQianJin
	 * @title @param list
	 * @title @return
	 * @date 2016年1月29日
	 */
	public static String getSignkeyByMap(Map<String,String> params){
		StringBuilder result = new StringBuilder();
		String resultStr="";
		if(params!=null){
			try {	            
	            //升序后追加字符串
	            for (Map.Entry<String, String> entry : params.entrySet()) {
	    			String key = entry.getKey();
	    			String value = entry.getValue();
	    			result.append(key + "=" + value + "&");       
	    		}	           
	            //去除最后一个&
				resultStr=result.toString();
				if(resultStr.length()>0){
					resultStr=resultStr.substring(0,resultStr.length()-1);
				}				
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
		return resultStr;		
	}

}
