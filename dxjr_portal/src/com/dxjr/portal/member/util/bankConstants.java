package com.dxjr.portal.member.util;

import com.dxjr.utils.PropertiesUtil;

/**
 * @author WangQianJin
 * @title 人人利常量类
 * @date 2016年4月23日
 */
public interface bankConstants {
	
	public static final String bank_id_url = PropertiesUtil.getValue("bank_id_url");
	public static final String bank_user_id = PropertiesUtil.getValue("bank_user_id");
	public static final String bank_aesKey = PropertiesUtil.getValue("bank_aesKey");
	public static final String bank_mKey = PropertiesUtil.getValue("bank_mKey");
	
}
