package com.dxjr.common.tag;

import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.SecurityUtils;

import com.dxjr.common.Dictionary;
import com.dxjr.portal.util.CharacterEncoder;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:自定义el表达式<br />
 * </p>
 * 
 * @title ELTag.java
 * @package com.dxjr.bbs.tag
 * @author qiongbiao.zhang
 * @version 0.1 2014年6月17日
 */
public class ELTag {
	
	public static String encode(String input) {
		return CharacterEncoder.encodeURL(input, "UTF-8");
	}

	public static String decode(String input) {
		return CharacterEncoder.decodeURL(input, "UTF-8");
	}
	
	public static ShiroUser currentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}
	 
	public static boolean hasRole(String role) {
		return SecurityUtils.getSubject().hasRole(role);
	}
	
	public static boolean isPermitted(String permission) {
		return SecurityUtils.getSubject().isPermitted(permission);
	}

	public static String desc(String type, String name) {
		return Dictionary.getValue(type, name);
	}
	
	public static Date currDate() {
		return new Date();
	}

	public static int year(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int month(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int day(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int lastday(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

}
