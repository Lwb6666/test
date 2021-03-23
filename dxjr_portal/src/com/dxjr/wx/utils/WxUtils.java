package com.dxjr.wx.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.dxjr.wx.common.WxConstants;

public class WxUtils {
	private static final Logger logger = Logger.getLogger(WxUtils.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String encodeURL(String input) {
		return encodeURL(input, WxConstants.default_enc);
	}

	public static String encodeURL(String input, String enc) {
		if (input == null) {
			return null;
		}
		try {
			return URLEncoder.encode(input, enc);
		} catch (UnsupportedEncodingException e) {
			logger.error("encode failed", e);
		}
		return input;
	}

	public static String decodeURL(String input) {
		return decodeURL(input, WxConstants.default_enc);
	}

	public static String decodeURL(String input, String enc) {
		if (input == null) {
			return null;
		}
		try {
			return URLDecoder.decode(input, enc);
		} catch (UnsupportedEncodingException e) {
			logger.error("decode failed", e);
		}
		return input;
	}

	public static <T> T json2Bean(String json, Class<T> clazz) {
		if (json == null) {

			return null;

		}
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			logger.error("unconverted json[" + json + "]", e);
		}
		return null;
	}

	public static String bean2Json(Object bean) {
		if (bean == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(bean);
		} catch (Exception e) {
			logger.error("unconverted bean[" + bean + "]", e);
		}
		return null;
	}

	public static Map<?, ?> json2Map(String json) {
		if (json == null) {
			return null;
		}
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			logger.error("unconverted json[" + json + "]", e);
		}
		return null;
	}

	public static List<?> json2List(String json) {
		if (json == null) {
			return null;
		}
		try {
			return objectMapper.readValue(json, List.class);
		} catch (Exception e) {
			logger.error("unconverted json[" + json + "]", e);
		}
		return null;
	}

	public static String getResponseFromServer(String url, String encoding) {
		try {
			return getResponseFromServer(new URL(url), encoding);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String getResponseFromServer(URL constructedUrl, String encoding) {
		return getResponseFromServer(constructedUrl, HttpsURLConnection.getDefaultHostnameVerifier(), encoding);
	}

	public static String getResponseFromServer(URL constructedUrl, HostnameVerifier hostnameVerifier, String encoding) {
		URLConnection conn = null;
		try {
			conn = constructedUrl.openConnection();
			if ((conn instanceof HttpsURLConnection)) {
				((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
			}
			BufferedReader in;
			if ((encoding == null) || (encoding.length() == 0)) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
			}

			StringBuilder stringBuffer = new StringBuilder(255);
			String line;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if ((conn != null) && ((conn instanceof HttpURLConnection))) {
				((HttpURLConnection) conn).disconnect();
			}
		}
	}

	private static String token = "dxjr_wx";

	private static String bdToken = "gcjrToken";

	public static boolean checkToken(String signature, String timestamp, String nonce) {
		String[] array = new String[] { token, timestamp, nonce };
		String resultToken = null;
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			// 加密
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bs = digest.digest(sb.toString().getBytes());
			resultToken = byteToStr(bs);

		} catch (Exception e) {
		}
		sb = null;
		return resultToken != null ? resultToken.equals(signature.toUpperCase()) : false;

	}

	public static String makeSign(String timestamp, String nonce) {
		String[] array = new String[] { bdToken, timestamp, nonce };
		String resultToken = null;
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			// 加密
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bs = digest.digest(sb.toString().getBytes());
			resultToken = byteToStr(bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb = null;
		return resultToken;
	}

	public static boolean checkbdToken(String signature, String timestamp, String nonce) {
		String[] array = new String[] { bdToken, timestamp, nonce };
		String resultToken = null;
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			// 加密
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] bs = digest.digest(sb.toString().getBytes());
			resultToken = byteToStr(bs);

		} catch (Exception e) {
		}
		sb = null;
		return resultToken != null ? resultToken.equals(signature.toUpperCase()) : false;
	}

	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest = strDigest + byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
		tempArr[1] = Digit[(mByte & 0xF)];

		String s = new String(tempArr);
		return s;
	}

	//
	// /**
	// * 图片消息对象转换成xml
	// *
	// * @param imageMessage 图片消息对象
	// * @return xml
	// */
	// public static String messageToXml(ImageMessage imageMessage) {
	// xstream.alias("xml", imageMessage.getClass());
	// return xstream.toXML(imageMessage);
	// }
	//
	// /**
	// * 语音消息对象转换成xml
	// *
	// * @param voiceMessage 语音消息对象
	// * @return xml
	// */
	// public static String messageToXml(VoiceMessage voiceMessage) {
	// xstream.alias("xml", voiceMessage.getClass());
	// return xstream.toXML(voiceMessage);
	// }
	//
	// /**
	// * 视频消息对象转换成xml
	// *
	// * @param videoMessage 视频消息对象
	// * @return xml
	// */
	// public static String messageToXml(VideoMessage videoMessage) {
	// xstream.alias("xml", videoMessage.getClass());
	// return xstream.toXML(videoMessage);
	// }
	//
	// /**
	// * 音乐消息对象转换成xml
	// *
	// * @param musicMessage 音乐消息对象
	// * @return xml
	// */
	// public static String messageToXml(MusicMessage musicMessage) {
	// xstream.alias("xml", musicMessage.getClass());
	// return xstream.toXML(musicMessage);
	// }
	//
	// public static String messageToXml(NewsMessage newsMessage) {
	// xstream.alias("xml", newsMessage.getClass());
	// xstream.alias("item", new Article().getClass());
	// return xstream.toXML(newsMessage);
	// }
	public static String getBindUrl() {
		String url;
		url = WxConstants.OAUTO2_URL.replace("APPID", WxConstants.AppId);
		url = url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=f", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID);
		return url;
	}

	// 生成菜单地址
	public static void main(String[] args) {
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1762bbc01ca4f159&redirect_uri=http%3A%2F%2F116.226.19.246%2Fentry%2Ftransferred%3Ft%3Da&response_type=code&scope=snsapi_base&state=0#wechat_redirect
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1762bbc01ca4f159&redirect_uri=http%3A%2F%2Flocalhost%3A8082%2Fdxjr_wx%2Fentry%2Ftransferred.html%3Ft%3Da&response_type=code&scope=snsapi_base&state=0#wechat_redirect
		// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1762bbc01ca4f159&redirect_uri=http%3A%2F%2Ftest.dxjr.com%2Fentry%2Ftransferred.html%3Ft%3Da&response_type=code&scope=snsapi_base&state=0#wechat_redirect

		String url = WxConstants.OAUTO2_URL.replace("APPID", "wx1762bbc01ca4f159");
		System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=a", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));

		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=a", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=b", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=c", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=d", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=e", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?t=f", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL, "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));//活动一键登录跳首页
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL(WxConstants.TRANSFERRED_MENU_URL + "?f=activity/recommend&pWxId=&w=", "utf-8")).replace("SCOPE",
		// WxConstants.ONLY_OPENID));// 分享
		// System.out.println(url.replace("REDIRECT_URI", WxUtils.encodeURL("", "utf-8")).replace("SCOPE", WxConstants.ONLY_OPENID));
	}
}
