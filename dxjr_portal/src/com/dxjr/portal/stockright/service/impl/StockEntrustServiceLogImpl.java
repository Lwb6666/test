package com.dxjr.portal.stockright.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.entity.StockEntrustlog;
import com.dxjr.portal.stockright.mapper.StockEntrustlogMapper;
import com.dxjr.portal.stockright.service.StockEntrustLogService;

/***
 * 委托信息接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
@Service
public class StockEntrustServiceLogImpl implements StockEntrustLogService {

	@Autowired
	private StockEntrustlogMapper stockEntrustlogMapper;

	@Override
	public StockEntrust saveStockEntrustLog(StockEntrust record) {
		StockEntrustlog log = new StockEntrustlog();
		log.setEntrustId(record.getId());
		log.setUserId(record.getUserId());
		log.setUserName(record.getUserName());
		log.setUserRealName(record.getUserRealName());
		log.setEntrustCode(record.getEntrustCode());
		log.setStockCode(record.getStockCode());
		log.setStockName(record.getStockName());
		log.setEntrustType(record.getEntrustType());
		log.setPrice(record.getPrice());
		log.setAmount(record.getAmount());
		log.setTotalPrice(record.getTotalPrice());
		log.setExpectFee(record.getExpectFee());
		log.setEntrustTotalPrice(record.getEntrustTotalPrice());
		log.setStatus(record.getStatus());
		log.setResidueAmount(record.getResidueAmount());
		log.setDrawMoney(record.getDrawMoney());
		log.setNoDrawMoney(record.getNoDrawMoney());
		log.setExpiryDate(record.getExpiryDate());
		log.setCurUseMoney(record.getCurUseMoney());
		log.setCurNoUseMoney(record.getCurNoUseMoney());
		if(record.getStatus() == 1){
			log.setOptUserName(record.getUserName());
			log.setOptUserRealName(record.getUserRealName());
			log.setDealAmount(0);
			log.setDealTotalPrice(BigDecimal.ZERO);
			log.setDealRate(BigDecimal.ZERO);
			log.setDealFee(BigDecimal.ZERO);
		}
		if(record.getStatus() == -1){
			log.setOptUserName(record.getUserName());
			log.setOptUserRealName(record.getUserRealName());
			log.setDealAmount(record.getDealAmount());
			log.setDealTotalPrice(record.getDealTotalPrice());
			log.setDealRate(record.getDealRate());
			log.setDealFee(record.getDealFee());
			
		}
		if(record.getStatus() == 2 || record.getStatus() == 3){
			log.setOptUserName("系统");
			log.setOptUserRealName("系统");
			log.setDealAmount(record.getDealAmount());
			log.setDealTotalPrice(record.getDealTotalPrice());
			log.setDealRate(record.getDealRate());
			log.setDealFee(record.getDealFee());
		}
		log.setPlatform(record.getPlatform());
		log.setRemark(record.getRemark());
		log.setAdduser(record.getUserId());
		log.setAddip(record.getAddip());
		stockEntrustlogMapper.insertSelective(log);
		return record;
	}
	
}
