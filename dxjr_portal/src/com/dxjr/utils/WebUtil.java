package com.dxjr.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	/**
	 * @Title: toUTF8
	 * @Description: "ISO-8859-1"格式字符转换成"UTF-8"
	 * @param String str "ISO-8859-1"格式字符
	 * @return "UTF-8"格式字符
	 * @throws
	 */
	public static String toUTF8(String str) {
		try {
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return str;
	}

	/**
	 * @Title: firstWordToUpperCase
	 * @Description: 将字符串首字母转换成大写字母的函数
	 * @param String str 带转换字符串
	 * @return String 新字符串
	 * @throws
	 */
	public static String firstWordToUpperCase(String str) {
		Pattern expression = Pattern.compile("\\w+");
		Matcher matcher = expression.matcher(str);
		String result = "";
		while (matcher.find()) {
			result = matcher.group();
			String x = String.valueOf((char) (result.charAt(0) - 32));
			result = result.replaceFirst("[a-zA-Z]", x);
		}
		return result;
	}

	/**
	 * @Title: getClassPath
	 * @Description: 返回系统的classes根目录全路径
	 * @return String 返回系统的classes根目录全路径
	 * @throws
	 */
	public static String getClassPath() {
		String path = "";
		Properties prop = System.getProperties();
		// 获取操作系统类型
		String os = prop.getProperty("os.name");
		// 代表是windows系统
		if (os.startsWith("win") || os.startsWith("Win")) {
			path = WebUtil.class.getResource("/").getPath().toString()
					.substring(1);
		} else {
			path = WebUtil.class.getResource("/").getPath().toString();
		}
		prop = null;
		return path;
	}

	/**
	 * @Title: getFileExtName
	 * @Description: 获取文件后缀名
	 * @param String fileName 文件名称
	 * @return String 获取文件后缀名
	 * @throws
	 */
	public static String getFileExtName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") >= 0) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		return extName;
	}

	private static long[] ls = new long[3000];
	private static int li = 0;

	/**
	 * @Title: getPK
	 * @Description: 主键生成方法，生成Long型的数字主键
	 * @return long 返回long 主键内容
	 * @throws
	 */
	public synchronized static long getPK() {
		/*
		 * 修改主键生成算法，使主键在软件生命周期内具有时间连续性同时适应JS
		 * 前端应用，根据当前算法，至少17年内不会发生2^53溢出问题
		 * - 关于性能问题：
		 * 新主键方案每10毫秒内有十万分之一的可能性会发生冲突主键问题，因此
		 * 当系统每秒数据生成量达到100条时生成器实际性能大约下降0.003%，此
		 * 后呈线性下降，每秒数据生成量150000条时，主键生成性能大约下降一倍，
		 * 每秒生成数据超过300000条后，该主键生成算法将不再安全
		 * - 关于并发问题：
		 * 该算法并发运行时（例如分布式服务器系统）存在每秒千万分之一的冲突
		 * 可能性，因此建议不用于并发式系统，即便投入应用，也应当保证每秒并
		 * 发插入数据不超过1000条。
		 */
		String a = String
				.valueOf((System.currentTimeMillis() / 10L) % 100000000000L);
		String d = (String.valueOf((1 + Math.random()) * 100000)).substring(1,
				6);
		long lo = Long.parseLong(a + d);
		for (int i = 0; i < 3000; i++) {
			long lt = ls[i];
			if (lt == lo) {
				lo = getPK();
				break;
			}
		}
		ls[li] = lo;
		li++;
		if (li == 3000) {
			li = 0;
		}
		return lo;
	}

	/**
	 * @Title: getRanName
	 * @Description: 根据时间生成一个随机数
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public synchronized static String getRanName() {
		return String.valueOf(getPK());
	}

	/*
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 */
	public static String encodeChineseDownloadFileName(
			HttpServletRequest request, String pFileName) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pFileName;
	}

}
