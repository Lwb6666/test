package com.dxjr.portal.tzjinterface.service;

import java.util.List;

import com.dxjr.portal.tzjinterface.entity.BidInfo;
import com.dxjr.portal.tzjinterface.entity.CreateUserReq;
import com.dxjr.portal.tzjinterface.entity.InvestInfo;
import com.dxjr.portal.tzjinterface.entity.LoginReq;
import com.dxjr.portal.tzjinterface.entity.QueryReq;
import com.dxjr.portal.tzjinterface.entity.Redirect;
import com.dxjr.portal.tzjinterface.entity.RepayInfo;
import com.dxjr.portal.tzjinterface.entity.UserInfo;
import com.dxjr.portal.tzjinterface.exception.PlatformException;


public interface PlatformService {
	
	// 创建新用户
	public UserInfo createUser(CreateUserReq req) throws Exception;
	
	// 绑定老用户，需要跳转到用户授权界面
	public Redirect bindUser(CreateUserReq req) throws PlatformException;
	
	// 登录，设置登录态并跳转到平台
	public Redirect login(LoginReq req) throws PlatformException;
	
	// 查询用户信息
	public List<UserInfo> queryUser(QueryReq req) throws PlatformException;
	
	// 查询标的信息
	public List<BidInfo> queryBids(QueryReq req) throws PlatformException;
	
	// 查询投资记录
	public List<InvestInfo> queryInvests(QueryReq req) throws PlatformException;
	
	// 查询回款记录
	public List<RepayInfo> queryRepays(QueryReq req) throws PlatformException;

}
