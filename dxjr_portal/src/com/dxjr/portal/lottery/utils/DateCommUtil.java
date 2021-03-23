package com.dxjr.portal.lottery.utils;

import java.util.Date;

public class DateCommUtil {

	public static String sysDate() throws Exception {
		String curSysdate = new Date().toLocaleString();
		return curSysdate;
	}

}
