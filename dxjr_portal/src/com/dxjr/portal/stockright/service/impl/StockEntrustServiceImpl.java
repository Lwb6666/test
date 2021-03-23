package com.dxjr.portal.stockright.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockAccountlog;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.mapper.CapitalAccountMapper;
import com.dxjr.portal.stockright.mapper.StockAccountMapper;
import com.dxjr.portal.stockright.mapper.StockEntrustMapper;
import com.dxjr.portal.stockright.service.CapitalAccountService;
import com.dxjr.portal.stockright.service.StockAccountLogService;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockEntrustLogService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.CapitalAccountCnd;
import com.dxjr.portal.stockright.vo.StockAccountRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;
import com.dxjr.utils.DateUtils;

/***
 * 委托信息接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xinwugang
 * @version 0.1 2016-5-11
 */
@Service
public class StockEntrustServiceImpl implements StockEntrustService {

	@Autowired
	private StockEntrustMapper stockEntrustMapper;
	@Autowired
	private StockAccountMapper stockAccountMapper;
	
	@Autowired
	private StockEntrustLogService stockEntrustLogService;
	
	@Autowired
	private StockAccountService stockAccountService;
	
	@Autowired
	private StockAccountLogService stockAccountLogService;
	
	@Autowired
	private CapitalAccountMapper capitalAccountMapper;
	
	@Autowired
	private  CapitalAccountService capitalAccountService;
	
	
	public List<StockEntrust> findEntrustListByUserId(StockEntrustCnd entrustCnd){
		List<StockEntrust> entrustList = stockEntrustMapper.findEntrustListByUserId(entrustCnd);
		return entrustList;
	}
	
	/**
	 * 
	 * <p>
	 * Description:这获取委托记录<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param entrustCnd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * List<StockEntrust>
	 */
	public List<StockEntrust> queryList(StockEntrustCnd entrustCnd,int pageSize){
		entrustCnd.setSort(1);//排序
		Page page = new Page(1, pageSize);
		Integer count = stockEntrustMapper.queryListCount(entrustCnd);
		page.setTotalCount(count);
		List<StockEntrust> entrustList = stockEntrustMapper.queryList(entrustCnd, page);
		return entrustList;
	}

	
	@Override
	public Page queryPageList(StockEntrustCnd entrustCnd, Page page) {
		entrustCnd.setSort(3);//排序 时间倒叙
		Integer count = stockEntrustMapper.queryListCount(entrustCnd);
		page.setTotalCount(count);
		List<StockEntrust> entrustList = stockEntrustMapper.queryList(entrustCnd, page);
		page.setResult(entrustList);
		return page;
	}
	
	
	public StockEntrust saveStockEntrust(StockAccount stockAccount,StockEntrust entrust){
		//1：获取用户基本信息
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(entrust.getUserId());
		StockUserInfoVo userInfo = stockAccountMapper.queryUserInfoById(request);
		//2：保存用户委托单
		entrust.setUserId(entrust.getUserId());
		entrust.setUserName(userInfo.getUserName());
		entrust.setUserRealName(userInfo.getRealName());
		entrust.setEntrustCode(createCode());//委托单编号
		entrust.setExpiryDate(createExpiryDate());
		if(stockAccount != null){
			entrust.setStockCode(stockAccount.getStockCode());
			entrust.setStockName(stockAccount.getStockName());
		}
		if(entrust.getEntrustType() == 2){//股权转让
			//转让委托总价 = 股权总价
			entrust.setEntrustTotalPrice(entrust.getTotalPrice());
		}else{//股权认购
			//认购委托总价 = 股权总价+ 服务费
			entrust.setEntrustTotalPrice(entrust.getTotalPrice().add(entrust.getExpectFee()));
		}
		entrust.setStatus(1);//1、已挂单；2、部分成交；3、全部成交；-1、已撤销；
		entrust.setResidueAmount(entrust.getAmount());
		entrust.setPlatform(1);//平台来源(1：网页 2：微信 3：安卓端 4： IOS端)',
		 stockEntrustMapper.insertSelective(entrust);
		 return entrust;
	}
	
	private String createCode(){
		int randomNum;//定义两变量
        Random ne=new Random();//实例化一个random的对象ne
        randomNum=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
		String dateStr = DateUtils.getNow("yyMMddHHmmss");
		return dateStr+randomNum;
	}
	
	
	public StockEntrust queryEntrustForUpdate(StockEntrustCnd entrustCnd){
		return stockEntrustMapper.selectByPrimaryKey(entrustCnd);
	}
	
	
	public int updateEntrust(StockEntrust request){
		return stockEntrustMapper.updateByPrimaryKeySelective(request);
	}
	
	public StockEntrust findEntrustById(Integer id){
		StockEntrust entrust = stockEntrustMapper.findEntrustById(id);
		return entrust;
	}
	
	
	
	public MessageBox saveRevokeEntrust(StockEntrustCnd entrustCnd) throws Exception{
		//1:获取锁定委托单
		entrustCnd.setIsLocked(1);//是否锁定
		StockEntrust entrust = stockEntrustMapper.selectByPrimaryKey(entrustCnd);
		//2:验证委托单信息
		if(entrust.getStatus() != 1 && entrust.getStatus() != 2){
			return new MessageBox("30000", "委托单状态错误！");
		}
		if(entrust.getResidueAmount() <= 0){
			return new MessageBox("30000", "剩余认购量错误！");
		}
		if(entrust.getEntrustType() == 1){
			//认购撤单
			return this.updateBuyerEntrust(entrust,entrustCnd.getUpdateIp(),entrustCnd.getUserId());
		}else{
			//转让撤单
			return this.updateSellerEntrust(entrust,entrustCnd.getUpdateIp(),entrustCnd.getUserId());
		}
	}
	
	private MessageBox updateSellerEntrust(StockEntrust entrust, String ip,int userId ){
		//3:修改委托单
		StockEntrust updateEntrust = new StockEntrust();
			updateEntrust.setStatus(-1);//撤销
			updateEntrust.setUpdateip(ip);
			updateEntrust.setUserId(userId);
			updateEntrust.setId(entrust.getId());
			updateEntrust.setRemark("已撤单");
			stockEntrustMapper.updateByPrimaryKeySelective(updateEntrust);
		//4：产生委托单变更日志
			entrust.setStatus(-1);//撤单
			entrust.setUpdateip(ip);
			entrust.setRemark("已经撤单");
			stockEntrustLogService.saveStockEntrustLog(entrust);
		//5:修改股权账户信息
			StockAccount stockAccount = stockAccountService.selectAccountForUpdate(userId,"forUpdate");
			//未成交量
			int residueAmount = entrust.getResidueAmount();
			stockAccount.setUseStock(stockAccount.getUseStock()+residueAmount);
			stockAccount.setNoUseStock(stockAccount.getNoUseStock()-residueAmount);
			stockAccountMapper.updateByPrimaryKeySelective(stockAccount);
		//6：产生股权账户日志
			StockAccountlog stockAccountLog = new StockAccountlog();
			stockAccountLog.setOptStock(residueAmount);//操作股权数量
			stockAccountLog.setOptType(-1);//1、认购；2、转让；3、开户；-1、撤销
			stockAccountLog.setTargetId(stockAccount.getId());
			stockAccountLog.setTargetName("gq_stock_account");
			stockAccountLog.setTargetType(9);//1、主动认购；2、被动认购；3、开户；4、主动转让；5、被动转让；6、委托转让；7、委托认购；8、撤销认购；9、撤销转让
			stockAccountLog.setRemark("内转委托撤销");
			stockAccountLog.setAddip(ip);
			stockAccountLog.setToUser(userId);
			stockAccountLogService.saveStockAccountlog(stockAccount, stockAccountLog);
			
			return new MessageBox("00000","");
	}
	

	public Integer countSellerEntrust(){
		return stockEntrustMapper.countSellerEntrust();
	}
	
	
	private MessageBox updateBuyerEntrust(StockEntrust entrust, String ip,int userId ) throws Exception{
		//3:修改委托单
		StockEntrust updateEntrust = new StockEntrust();
			updateEntrust.setStatus(-1);//撤销
			updateEntrust.setUpdateip(ip);
			updateEntrust.setUserId(userId);
			updateEntrust.setId(entrust.getId());
			updateEntrust.setRemark("已撤单");
			stockEntrustMapper.updateByPrimaryKeySelective(updateEntrust);
		//4：产生委托单变更日志
			entrust.setStatus(-1);//撤单
			entrust.setUpdateip(ip);
			entrust.setRemark("已撤单");
			stockEntrustLogService.saveStockEntrustLog(entrust);
		//5:修改资金账户
		//未成交金额 =委托总价（包含服务费） - 已成交总价 - 实际成交服务费 
			BigDecimal noDealMoney  = entrust.getEntrustTotalPrice().subtract(entrust.getDealTotalPrice()).subtract(entrust.getDealFee()); 
			//当前委托单申请时冻结可提金额
			BigDecimal freezeDreawMoney = entrust.getDrawMoney();
			//当前委托单申请时冻结不可提金额
			BigDecimal freezeNoDreawMoney = entrust.getNoDrawMoney();
			if(noDealMoney.compareTo(freezeDreawMoney) <= 0){
				//未成交金额 <= 可提冻结金额  未成交金额全部解冻到可提
				freezeDreawMoney= noDealMoney;
				freezeNoDreawMoney = BigDecimal.ZERO;
			}else{
				//未成交金额 > 可提冻结金额  冻结可提全部解冻
				freezeNoDreawMoney = noDealMoney.subtract(freezeDreawMoney);
			}
			CapitalAccountCnd accountCnd = new CapitalAccountCnd();
			accountCnd.setUserId(userId);
			accountCnd.setYear("yes");
			AccountVo account = capitalAccountMapper.queryCapitalAccount(accountCnd);
			account.setDrawMoney(account.getDrawMoney().add(freezeDreawMoney));
			account.setNoDrawMoney(account.getNoDrawMoney().add(freezeNoDreawMoney));
			account.setUseMoney(account.getUseMoney().add(noDealMoney));
			account.setNoUseMoney(account.getNoUseMoney().subtract(noDealMoney));
			capitalAccountMapper.updateAccount(account);
			
		//6：资金变更日志
			AccountLogCnd accountLogCnd = new AccountLogCnd();
			accountLogCnd.setType("gq_deal_revoke"); //新增类型    股权交易冻结
			accountLogCnd.setMoney(noDealMoney);
			accountLogCnd.setToUser(-1);
			accountLogCnd.setBorrowId(entrust.getId());
			accountLogCnd.setBorrowName("gq_stock_entrust");
			accountLogCnd.setIdType(11);//新增类型   股权认购委托撤单
			accountLogCnd.setRemark("内转认购委托撤单");
			accountLogCnd.setAddip(ip);
			accountLogCnd.setToUser(userId);
			capitalAccountService.saveAccountlog(account, accountLogCnd);
			
		/*	//活期宝使用金额撤回
			BigDecimal curUseMoney = entrust.getCurUseMoney() == null ? BigDecimal.ZERO : entrust.getCurUseMoney();
			BigDecimal curNoUseMoney = entrust.getCurNoUseMoney() == null ? BigDecimal.ZERO : entrust.getCurNoUseMoney();
			BigDecimal curToatlPrice = curUseMoney.add(curNoUseMoney);//活期宝划出金额
			// 活期宝划出金额 > 0  &&  撤单金额  > 账户可用划出金额
			if(curToatlPrice.compareTo(BigDecimal.ZERO) > 0){
				if(curToatlPrice.compareTo(curUseMoney) <= 0){
					//撤单金额  >= 产生收益部分 全部撤回到产生收益中
					curUseMoney = curToatlPrice;
					curNoUseMoney = BigDecimal.ZERO;
				}else{
					//撤单金额  < 产生收益部分 产生收益全部撤回
					curNoUseMoney = curToatlPrice.subtract(curUseMoney);
				}
				//需要划转到活期宝的金额
				BigDecimal toCurMoney = curNoUseMoney.add(curUseMoney);
				// 3: 活期宝资金增加
					CurAccount cur = capitalAccountMapper.querCurByUserId(account.getUserId());
					cur.setTotal(cur.getTotal().add(toCurMoney));
					cur.setUseMoney(cur.getUseMoney().add(curUseMoney));
					cur.setNoUseMoney(cur.getNoUseMoney().add(curNoUseMoney));
					cur.setLastUpUser(-1);
					capitalAccountMapper.updateCurByUserId(cur);
				// 4: 产生活期宝日志
					CurAccountlog curLog =  new CurAccountlog();
					curLog.setMoney(toCurMoney);
					curLog.setType(201);//转出到可用余额 
					curLog.setAddip(ip);
					curLog.setRemark("股权交易撤单，可用余额转入活期宝");
					curLog.setAdduser(account.getUserId());
					capitalAccountService.saveCurAccountlog(cur, curLog);
				// 1: 资金账户扣除 中划转到活期宝的金额
					account.setTotal(account.getTotal().subtract(toCurMoney));
					account.setUseMoney(account.getUseMoney().subtract(toCurMoney));
					account.setDrawMoney(account.getDrawMoney().subtract(toCurMoney));
					capitalAccountMapper.updateAccount(account);
				// 2: 产生资金变更日志
					AccountLogCnd accountLog = new AccountLogCnd();
					accountLog.setType("102"); //新增类型    可提金额划转到活期宝
					accountLog.setMoney(toCurMoney);
					accountLog.setToUser(-1);
					accountLog.setBorrowId(cur.getId());
					accountLog.setBorrowName("gq_account");
					accountLog.setIdType(4);//活期宝转出
					accountLog.setRemark("股权交易撤单，可提金额转入到活期宝");
					accountLog.setAddip(ip);
					capitalAccountService.saveAccountlog(account, accountLog);
				
			}*/
			return new MessageBox("00000","");
	}
	
	private Date createExpiryDate(){
		Calendar cal = Calendar.getInstance();
	     cal.setTime(new Date());
	     cal.add(Calendar.YEAR, 100);
	   return cal.getTime();
	}
}
