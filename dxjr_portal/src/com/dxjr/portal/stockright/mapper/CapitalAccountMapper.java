package com.dxjr.portal.stockright.mapper;

import java.util.List;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.portal.account.vo.AccountCnd;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.entity.CurAccountlog;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.stockright.vo.CapitalAccountCnd;


public interface CapitalAccountMapper {

	Integer saveCurAccountlog(CurAccountlog curLog);
	
	Integer saveAccountlog(AccountLog accountlog);
	
	
    /**
     * 
     * <p>
     * Description:根据用户ID获取锁定活期宝账户信息<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-19
     * @param userId
     * @return
     * CurAccount
     */
    CurAccount querCurByUserId(int userId);
    
    
    /**
     * 
     * <p>
     * Description:变更活期宝资金<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-19
     * @param cur
     * @return
     * Integer
     */
    Integer updateCurByUserId(CurAccount cur);
    
    
    /**
     * 
     * <p>
     * Description:变更资金账户信息<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-19
     * @param account
     * @return
     * Integer
     */
    Integer updateAccount(AccountVo account);
    
    
    /**
     * 
     * <p>
     * Description:添加资金账户变更日志<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-20
     * @param accountLogCnd
     * @return
     * Integer
     */
    Integer saveAccountlog(AccountLogCnd accountLogCnd);
    
    /**
     * 
     * <p>
     * Description:获取资金账户<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-23
     * @param accountCnd
     * @return
     * @throws Exception
     * AccountVo
     */
    AccountVo queryCapitalAccount(CapitalAccountCnd accountCnd) throws Exception;

    
    /**
     * 
     * <p>
     * Description:活期宝转出日志<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-24
     * @param record
     * @return
     * int
     */
 //  int insertCurOut(CurOut record);
}