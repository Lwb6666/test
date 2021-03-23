package com.dxjr.portal.fix.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 定期宝活动查询条件
 * @date 2016年1月27日
 */
public class FixActivityCnd implements Serializable {

	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7827009911268447771L;
	
	
	/**
	 * 期限字符串
	 */
	private String limitStr;


	public String getLimitStr() {
		return limitStr;
	}


	public void setLimitStr(String limitStr) {
		this.limitStr = limitStr;
	}
	
	
	

}
