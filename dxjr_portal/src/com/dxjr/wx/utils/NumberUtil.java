package com.dxjr.wx.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.mysql.jdbc.StringUtils;

public class NumberUtil {
	/**
	 * 格式化并四舍五入
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月17日
	 * @param text
	 * @return String
	 */
	public static String fmtMicrometer(String text) {
		if (!StringUtils.isNullOrEmpty(text)) {
			BigDecimal bg = new BigDecimal(text).setScale(2, BigDecimal.ROUND_HALF_UP);
			DecimalFormat d = new DecimalFormat(",##0.00");
			return d.format(bg);
		}
		return text;
	}

	public static void main(String[] args) {
		System.out.println(fmtMicrometer("664.69480000"));
	}

	/**
	 * 格式化并四舍五入
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月17日
	 * @param bigDecimal
	 * @return String
	 */
	public static String fmtMicrometer(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			BigDecimal bg = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			DecimalFormat d = new DecimalFormat(",##0.00");
			return d.format(bg);
		}
		return null;
	}

	/**
	 * 格式化不四舍五入
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月17日
	 * @param bigDecimal
	 * @return String
	 */
	public static String fmtMicrometer2(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			BigDecimal bg = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			DecimalFormat d = new DecimalFormat(",##0.00");
			return d.format(bg);
		}
		return null;
	}

	/**
	 * 格式化不四舍五入
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月17日
	 * @param bigDecimal
	 * @return String
	 */
	public static String fmtMicrometer2(String text) {
		if (!StringUtils.isNullOrEmpty(text)) {
			BigDecimal bg = new BigDecimal(text).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			DecimalFormat d = new DecimalFormat(",##0.00");
			return d.format(bg);
		}
		return text;
	}

}
