package com.dxjr.wx.account.service;

import java.util.Map;

import com.dxjr.security.ShiroUser;

public interface SafeCenterService {

	/**
	 * 当前登录用户邮箱，手机，实名认证信息检查
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月5日
	 * @param loginUser
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> certificationCheck(ShiroUser loginUser, String type, String bank) throws Exception;

}
