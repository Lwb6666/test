package com.dxjr.common;

import com.dxjr.utils.RandomGUIDUtil;

public class GlobleConstant {
	/**
	 * 
	 * <p>
	 * Description:生成token<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年2月18日
	 * @return
	 * String
	 * @throws Exception 
	 */
	public static String getToken(){
		String token = null;
		try {
			token = RandomGUIDUtil.newGuid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}
}
