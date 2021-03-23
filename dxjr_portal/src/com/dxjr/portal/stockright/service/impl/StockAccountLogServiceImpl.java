package com.dxjr.portal.stockright.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockAccountlog;
import com.dxjr.portal.stockright.mapper.StockAccountlogMapper;
import com.dxjr.portal.stockright.service.StockAccountLogService;

/***
 * 股权账户接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockAccountServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xinwugang
 * @version 0.1 2016-5-11
 */
@Service
public class StockAccountLogServiceImpl implements StockAccountLogService {
	@Autowired
	private StockAccountlogMapper stockAccountLogMapper;

	@Override
	public void saveStockAccountlog(StockAccount account,
			StockAccountlog accountLog) {
		accountLog.setUserId(account.getUserId());//股东ID 
		accountLog.setAccountId(account.getId());//股权信息表id'
		accountLog.setTotal(account.getTotal());//股权总数量
		accountLog.setUseStock(account.getUseStock());//可用股权数量
		accountLog.setNoUseStock(account.getNoUseStock());//冻结股权数量
		stockAccountLogMapper.insertSelective(accountLog);
			
	}


}
