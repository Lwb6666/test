package com.dxjr.wx.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.swing.JOptionPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.dxjr.wx.common.InteractiveConstant;
import com.mysql.jdbc.StringUtils;

public class HttpClientUtils {
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	/**
	 * 传入参数对象直接发送请求,获取远程返回的String
	 * <p>
	 * 传入的参数需要与属性个数一致，并附上对应的get方法，返回的是集合
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月20日
	 * @param httpclient 从内存中取出来的http客户端
	 * @param url 要访问的路径
	 * @param params 访问url时要携带的参数对象
	 * @return 返回字符串
	 * @throws Exception Object
	 */
	public static String getRomoteString(HttpClient httpclient, String url, Object params) throws Exception {
		httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
		httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
		PostMethod postMethod = new PostMethod(url);
		System.out.println("url:::" + url);
		int statusCode = 0;
		List<NameValuePair> paramsList = getParamsList(params);
		NameValuePair[] nvps = new NameValuePair[paramsList.size()];
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		postMethod.setRequestBody(paramsList.toArray(nvps));
		statusCode = httpclient.executeMethod(postMethod);
		if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new Exception("请求接口失败，失败码[ " + statusCode + " ]");
		}
		return inputStream2String(postMethod.getResponseBodyAsStream());
	}

	/**
	 * 传入参数对象直接发送请求
	 * <p>
	 * 传入的参数需要与属性个数一致，并附上对应的get方法，返回的是集合
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月20日
	 * @param httpclient 从内存中取出来的http客户端
	 * @param url 要访问的路径
	 * @param params 访问url时要携带的参数对象
	 * @param clazz 返回对象的名称
	 * @return
	 * @throws Exception Object
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, ?> getRomoteObject(HttpClient httpclient, String url, Object params) throws Exception {
		httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
		httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
		System.out.println("访问地址:" + url);
		PostMethod postMethod = new PostMethod(url);
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		int statusCode = 0;
		List<NameValuePair> paramsList = getParamsList(params);
		NameValuePair[] nvps = new NameValuePair[paramsList.size()];
		postMethod.setRequestBody(paramsList.toArray(nvps));
		statusCode = httpclient.executeMethod(postMethod);
		if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new Exception("请求接口失败，失败码[ " + statusCode + " ]");
		}
		Object obj = WxUtils.json2Bean(inputStream2String(postMethod.getResponseBodyAsStream()), Object.class);
		if (obj == null) {
			return null;
		}
		return (LinkedHashMap<String, ?>) obj;
	}

	// /**
	// * 传入参数对象直接发送请求
	// * <p>
	// * 传入的参数需要与属性个数一致，并附上对应的get方法，返回的是集合
	// * </p>
	// *
	// * @author ZHUCHEN
	// * @version 0.1 2014年10月20日
	// * @param httpclient
	// * 从内存中取出来的http客户端
	// * @param url
	// * 要访问的路径
	// * @param params
	// * 访问url时要携带的参数对象
	// * @param clazz
	// * 返回对象的名称
	// * @return
	// * @throws Exception
	// * Object
	// */
	// public static Object getRomoteObject(HttpClient httpclient, String url,
	// Object params, Class<?> clazz) throws Exception {
	// System.out.println("访问地址:" + url);
	// PostMethod postMethod = new PostMethod(url);
	// postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
	// "utf-8");
	// int statusCode = 0;
	// List<NameValuePair> paramsList = getParamsList(params);
	// NameValuePair[] nvps = new NameValuePair[paramsList.size()];
	// postMethod.setRequestBody(paramsList.toArray(nvps));
	// statusCode = httpclient.executeMethod(postMethod);
	// if (statusCode < HttpURLConnection.HTTP_OK || statusCode >=
	// HttpURLConnection.HTTP_MULT_CHOICE) {
	// throw new Exception("请求接口失败，失败码[ " + statusCode + " ]");
	// }
	// Object obj =
	// WxUtils.json2Bean(inputStream2String(postMethod.getResponseBodyAsStream()),
	// clazz);
	// if (obj == null) {
	// return null;
	// }
	// return obj;
	// }

	/**
	 * 传入参数对象直接发送请求
	 * <p>
	 * 传入的参数需要与属性个数一致，并附上对应的get方法，返回的是集合
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月20日
	 * @param httpclient 从内存中取出来的http客户端
	 * @param url 要访问的路径
	 * @param params 访问url时要携带的参数对象
	 * @param classname 返回对象的名称
	 * @return
	 * @throws Exception Object
	 */
	public static List<?> getRomoteList(HttpClient httpclient, String url, Object params) throws Exception {
		httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
		httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
		System.out.println("::::::::::::getromoteList:::::::url:" + url);
		PostMethod postMethod = new PostMethod(url);
		int statusCode = 0;
		if (params != null) {
			List<NameValuePair> paramsList = getParamsList(params);
			NameValuePair[] nvps = new NameValuePair[paramsList.size()];
			postMethod.setRequestBody(paramsList.toArray(nvps));
		}
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		statusCode = httpclient.executeMethod(postMethod);
		if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
			throw new Exception("请求接口失败，失败码[ " + statusCode + " ]");
		}
		System.out.println("::::::::::::over:::::::url:" + url);
		return WxUtils.json2List(inputStream2String(postMethod.getResponseBodyAsStream()));
	}

	/**
	 * 获取图片的字节数组
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月20日
	 * @param httpclient
	 * @param url
	 * @return
	 * @throws Exception byte[]
	 */
	public static byte[] getImage(HttpClient httpclient, String url) throws Exception {
		try {
			httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
			httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
			PostMethod postMethod = new PostMethod(url);
			postMethod.setRequestBody(new NameValuePair[0]);
			httpclient.executeMethod(postMethod);
			BufferedInputStream in = new BufferedInputStream(postMethod.getResponseBodyAsStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[2500];
			int size;
			while ((size = in.read(buf)) != -1) {
				out.write(buf, 0, size);
			}
			in.close();
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "获取验证码错误" + e.getMessage());
		}
		return null;
	}

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月18日
	 * @param params
	 * @param list void
	 */
	private static List<NameValuePair> getParamsList(Object obj) {
		Object o;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (obj == null)
			return list;
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			String name;
			String value;
			for (Field field : fields) {
				name = field.getName();
				o = obj.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1)).invoke(obj, null);
				if (o == null)
					continue;
				value = String.valueOf(o);

				list.add(new NameValuePair(name, value));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("参数获取异常：" + e);
		}
		return list;
	}

	/**
	 * 读返回值
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月16日
	 * @param is
	 * @return
	 * @throws IOException String
	 */
	private static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	/**
	 * 获取单个返回对象
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月18日
	 * @param jsonString
	 * @param obj
	 * @return
	 * @throws Exception void
	 */
	public static Object setRetrunObject(String jsonString, String classname) throws Exception {
		System.out.println("返回的json:" + jsonString);
		Object obj = Class.forName(classname).newInstance();
		jsonString = jsonString.substring(1, jsonString.length() - 1);
		if (StringUtils.isNullOrEmpty(jsonString))
			return obj;
		String[] strs = jsonString.split(",");
		Field[] fields = obj.getClass().getDeclaredFields();
		List<String> nameList = new ArrayList<String>();
		for (Field f : fields) {
			nameList.add(f.getName());
		}
		int index;
		String fieldName;
		Field field;
		String[] tempstrs;
		String tempValue;
		for (int i = 0; i < strs.length; i++) {
			tempstrs = strs[i].split(":");
			fieldName = tempstrs[0];
			fieldName = fieldName.substring(1, fieldName.length() - 1);
			index = nameList.indexOf(fieldName);
			tempValue = tempstrs[1];
			if (tempValue.startsWith(InteractiveConstant.BEGINSTR)) {
				while (getCount(tempValue, InteractiveConstant.BEGINSTR) != getCount(tempValue, InteractiveConstant.ENDSTR)) {
					tempValue = tempValue + "," + strs[++i];
				}
				tempValue = tempValue.substring(InteractiveConstant.BEGINSTR.length(), tempValue.length() - InteractiveConstant.ENDSTR.length());
			} else if (tempValue.startsWith("\"")) {
				tempValue = tempValue.substring(1, tempValue.length() - 1);
			}
			if (index > -1) {
				field = fields[index];
				obj.getClass().getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[] { field.getType() }).invoke(obj, getRealValue(tempValue, field.getType()));
			}
		}
		return obj;
	}

	/**
	 * 获取列表对象
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月18日
	 * @param jsonString
	 * @param obj
	 * @return
	 * @throws Exception void
	 */
	public static List<Object> setRetrunList(String jsonString, String classname) throws Exception {
		List<Object> result = new ArrayList<Object>();
		if (StringUtils.isNullOrEmpty(jsonString))
			return result;
		Object tempObj = Class.forName(classname).newInstance();
		Field[] fields = tempObj.getClass().getDeclaredFields();
		List<String> nameList = new ArrayList<String>();
		for (Field f : fields) {
			nameList.add(f.getName());
		}
		String[] objects = jsonString.substring(1, jsonString.length() - 1).split("}");
		for (int i = 0; i < objects.length; i++) {
			String objectStr = objects[i];
			if (objectStr.contains(InteractiveConstant.BEGINSTR)) {
				while (getCount(objectStr, InteractiveConstant.BEGINSTR) != getCount(objectStr, InteractiveConstant.ENDSTR)) {
					objectStr = objectStr + "}" + objects[++i];
				}
			}
			if (objectStr.startsWith(",")) {
				objectStr = objectStr.substring(1);
			}
			objectStr = objectStr + "}";
			result.add(setRetrunObject(objectStr, classname));
		}

		return result;
	}

	/**
	 * 各类型赋值
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月20日
	 * @param tempValue
	 * @param type
	 * @return Object
	 */
	private static Object getRealValue(String tempValue, Class<?> type) {
		if (type == byte.class) {
			return Byte.parseByte(tempValue);
		} else if (type == Byte.class) {
			return Byte.parseByte(tempValue);
		} else if (type == short.class) {
			return Short.parseShort(tempValue);
		} else if (type == Short.class) {
			return Short.parseShort(tempValue);
		} else if (type == char.class) {
			return tempValue.charAt(0);
		} else if (type == int.class) {
			return Integer.parseInt(tempValue);
		} else if (type == Integer.class) {
			return Integer.parseInt(tempValue);
		} else if (type == long.class) {
			return Long.parseLong(tempValue);
		} else if (type == Long.class) {
			return Long.parseLong(tempValue);
		} else if (type == float.class) {
			return Float.parseFloat(tempValue);
		} else if (type == Float.class) {
			return Float.parseFloat(tempValue);
		} else if (type == double.class) {
			return Double.parseDouble(tempValue);
		} else if (type == Double.class) {
			return Double.parseDouble(tempValue);
		} else if (type == BigDecimal.class) {
			return new BigDecimal(tempValue);
		} else if (type == java.util.Date.class) {
			return new Date(Long.parseLong(tempValue));
		} else {
			return tempValue;
		}
	}

	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		String json = null;
		StringBuffer buffer = new StringBuffer();
		try {
			TrustManager[] manager = { new DefaultTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, manager, new SecureRandom());
			SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(requestMethod);
			conn.setSSLSocketFactory(sslSocketFactory);
			conn.setUseCaches(false);
			conn.setHostnameVerifier((HostnameVerifier) new TrustAnyHostnameVerifier());
			if (outputStr != null) {
				OutputStream outputStream = conn.getOutputStream();

				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			conn.disconnect();
			json = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}

	/**
	 * <p>
	 * Description:HTTP请求<br />
	 * </p>
	 * 
	 * @author Wang bo
	 * @version 0.1 2014年4月30日
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return JSONObject
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {

		String json = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			// if ("GET".equalsIgnoreCase(requestMethod)) {
			// conn.connect();
			// }
			conn.connect();
			if (outputStr != null) {
				OutputStream outputStream = conn.getOutputStream();

				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;
			conn.disconnect();
			json = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;

	}

	/**
	 * <p>
	 * Description:URL转码<br />
	 * </p>
	 * 
	 * @author wang bo
	 * @version 0.1 2014年4月30日
	 * @param source
	 * @return String
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月21日 void
	 */
	private static int getCount(String str, String partten) {
		// e表示需要匹配的数据，使用Pattern建立匹配模式
		Pattern p = Pattern.compile(partten);
		// 使用Matcher进行各种查找替换操作
		Matcher m = p.matcher(str);
		int i = 0;
		while (m.find()) {
			i++;
		}
		return i;
	}
}
