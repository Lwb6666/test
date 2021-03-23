package com.dxjr.wx.pay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.dxjr.portal.statics.BusinessConstants;

/**
 * @author WangQianJin
 * @title 网银在线工具类
 * @date 2016年6月1日
 */
public class OnlineUtil {

	/**
	 * 获取交易ID
	 * @author WangQianJin
	 * @title @return
	 * @date 2016年6月1日
	 */
	public static String getTradeId(Integer userId){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-" + BusinessConstants.ONLINE_PAY_CHINABANK_SHOPNO + "-HHmmssSSS", Locale.US);
		String v_oid = "GCJR" + sf.format(new Date()) + "_" + userId;
        return v_oid;
	}
}
