package com.dxjr.portal.stockright.service;

import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.entity.CurAccountlog;

/**
 * 
 * <p>
 * Description::股权账户模块，活期宝变更业Service<br />
 * </p>
 * @author xiaofei.li
 * @version 0.1 2016-5-19
 * @param account
 * @param accountLog
 * void
 */
public interface CapitalAccountService {

	
	/**
	 * 
	 * <p>
	 * Description:保存活期宝变更日志<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-19
	 * @param cur
	 * void
	 */
	public void saveCurAccountlog(CurAccount cur, CurAccountlog curLog);
	
	
	/**
	 * 
	 * <p>
	 * Description:保存资金账户变更日志<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-20
	 * @param accountLogCnd
	 * void
	 */
	public void saveAccountlog(AccountVo account, AccountLogCnd accountLogCnd);
	
	
}
