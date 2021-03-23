package com.dxjr.portal.stockright.service;

import com.dxjr.portal.stockright.entity.StockEntrust;


/****
 * 委托信息接口
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustService.java
 * @package com.dxjr.portal.stockright.service 
 * @author xinwugang
 * @version 0.1 2016-5-11
 */
public interface StockEntrustLogService {
	
	/**
	 * 
	 * <p>
	 * Description:委托申请公用Service <br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-15
	 * @param stockAccount
	 * @param entrust
	 * @return
	 * int
	 */
	public StockEntrust saveStockEntrustLog(StockEntrust record);

}
