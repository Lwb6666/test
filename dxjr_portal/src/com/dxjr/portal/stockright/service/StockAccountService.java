package com.dxjr.portal.stockright.service;

import java.math.BigDecimal;

import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;


/****
 * 股权账户 接口
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockAccountService.java
 * @package com.dxjr.portal.stockright.service 
 * @author xinwugang
 * @version 0.1 2016-5-11
 */
public interface StockAccountService {
	
	/**
	 * 
	 * <p>
	 * Description:根据id 或 userId 检索用户账户信息<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param userId
	 * @return
	 * StockAccount
	 */
	StockAccount selectByPrimaryKey(Integer userId);
	
	
	/**
	 * 
	 * <p>
	 * Description:根据用户ID查询用户基本信息<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param request
	 * @return
	 * Map<String,Object>
	 */
	StockUserInfoVo queryUserInfoById(Integer userId);

	
	/**
	 * 
	 * <p>
	 * Description:获取用户待收信息<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param userId
	 * @return
	 * Map<String,Object>
	 */
	BigDecimal queryUserCollect(Integer userId);
	
	/**
	 * 
	 * <p>
	 * Description:根据userId 查询锁定股权账户<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param userId
	 * @return
	 * StockAccount
	 */
	StockAccount selectAccountForUpdate(Integer userId,String forUpdate);
	
	
	/**
	 * 
	 * <p>
	 * Description:股权账户开户<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-16
	 * @param userId
	 * @param stockCode
	 * @param stockName
	 * @return
	 * Integer
	 */
	public Integer createStoceAccount(int userId,String stockCode,String stockName);
	
	/***
	 * 查询活期宝账户
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-20
	 * @param userId
	 * @return
	 * CurAccount
	 */
	public CurAccount findCurAccount(int userId);
}
