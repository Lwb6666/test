package com.dxjr.portal.rrlinterface.util;

import com.dxjr.utils.PropertiesUtil;

/**
 * @author WangQianJin
 * @title 人人利常量类
 * @date 2016年4月23日
 */
public interface RrlConstants {
	
	//人人利商务编号校验地址
	public static final String rrl_cust_id_url = PropertiesUtil.getValue("rrl_cust_id_url");
	//人人利加密类型
	public static final String rrl_type = "MD5";
	//人人利商务编号
	public static final String rrl_cust_id = PropertiesUtil.getValue("rrl_cust_id");
	//人人利秘钥
	public static final String rrl_key = PropertiesUtil.getValue("rrl_key");
	
}
