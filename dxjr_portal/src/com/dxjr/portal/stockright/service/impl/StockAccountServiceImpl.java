package com.dxjr.portal.stockright.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockAccountlog;
import com.dxjr.portal.stockright.mapper.CapitalAccountMapper;
import com.dxjr.portal.stockright.mapper.StockAccountMapper;
import com.dxjr.portal.stockright.service.StockAccountLogService;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.vo.StockAccountRequest;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;
import com.dxjr.portal.util.StringUtils;

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
public class StockAccountServiceImpl implements StockAccountService {
	@Autowired
	private StockAccountMapper stockAccountMapper;
	@Autowired
	private  CapitalAccountMapper capitalAccountMapper;

	@Autowired
	private StockAccountLogService stockAccountLogService;
	public StockAccount selectByPrimaryKey(Integer userId){
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(userId);
		return stockAccountMapper.selectByPrimaryKey(request);
	}
	
	
	public StockUserInfoVo queryUserInfoById(Integer userId){
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(userId);
		return stockAccountMapper.queryUserInfoById(request);
	}
	
	
	public BigDecimal queryUserCollect(Integer userId){
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(userId);
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal sanBiao = stockAccountMapper.querySanBiao(request);
		if(sanBiao != null){
			total= total.add(sanBiao);
		}
		BigDecimal dingQiBao = stockAccountMapper.queryDingQiBao(request);
		if(dingQiBao != null){
			total= total.add(dingQiBao);
		}
		//存管待收
		BigDecimal cunguan = stockAccountMapper.queryCunGuan(request);
		if(cunguan != null){
			total= total.add(cunguan);
		}
		return total;
	}
	
	
	public StockAccount selectAccountForUpdate(Integer userId, String forUpdate){
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(userId);
		if(StringUtils.isNotEmpty(forUpdate)){
			request.setIsForUpdate(1);
		}
		return stockAccountMapper.selectByPrimaryKey(request);
	}
	
	
	public Integer createStoceAccount(int userId,String stockCode,String stockName){
		//1:开通股权账户
		StockAccount account = new StockAccount();
		account.setUserId(userId);
		account.setStockCode(stockCode);
		account.setStockName(stockName);
		account.setTotal(0);
		account.setUseStock(0);
		account.setNoUseStock(0);
		account.setRemark("开通内转账户");
		account.setStatus(1);//1：正常；-1注销
		stockAccountMapper.insert(account);
		//2:添加开通账户日志
		StockAccountlog  accountLog = new StockAccountlog();
		accountLog.setOptStock(0);
		accountLog.setOptType(3);//1、认购；2、转让；3、开户；-1、撤销
		accountLog.setTargetId(account.getId());
		accountLog.setTargetName("gq_stock_account");
		accountLog.setTargetType(3);//1、主动认购；2、被动认购；3、开户；4、主动转让；5、被动转让；6、委托转让；7、委托认购；8、撤销认购；9、撤销转让
		accountLog.setToUser(-1);
		accountLog.setRemark("开通内转账户");
		accountLog.setAddip("0.0.0.1");
		stockAccountLogService.saveStockAccountlog(account, accountLog);
		return account.getId();
	}
	
	public CurAccount findCurAccount(int userId){
		CurAccount curAccount = capitalAccountMapper.querCurByUserId(userId);
		return curAccount;
	}
	
}
