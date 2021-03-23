package com.dxjr.portal.investment.service;

public interface InvestMentService {

	/**
	 * @author JingBinbin
	 * @title 生成随机密码
	 * @date 2016年7月5日
	 */
	public String generatePassword();
	
	/**
	 * @author JingBinbin
	 * @title 生成用户名
	 * @date 2016年7月5日
	 */
	public String generateUserName(String userName, Integer autoIndex);
}
