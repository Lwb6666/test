package com.dxjr.portal.stockright.service;

import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockAccountlog;

public interface StockAccountLogService {

	/**
	 * 
	 * <p>
	 * Description:根据股权账户信息，保存股权账户日志<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-15
	 * @param account
	 * @param accountLog
	 * void
	 */
	public void saveStockAccountlog(StockAccount account,
			StockAccountlog accountLog);
}
