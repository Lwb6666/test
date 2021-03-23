package com.dxjr.portal.stockright.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.Dictionary;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.entity.CurAccountlog;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.freemarker.service.BaseFreemarkerService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.EnumerationUtil;
import com.dxjr.portal.stockright.entity.ApplyInfo;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockAccountlog;
import com.dxjr.portal.stockright.entity.StockDeal;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.mapper.CapitalAccountMapper;
import com.dxjr.portal.stockright.mapper.StockAccountMapper;
import com.dxjr.portal.stockright.mapper.StockDealMapper;
import com.dxjr.portal.stockright.mapper.StockEntrustMapper;
import com.dxjr.portal.stockright.service.CapitalAccountService;
import com.dxjr.portal.stockright.service.StockAccountLogService;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockDealService;
import com.dxjr.portal.stockright.service.StockEntrustLogService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.CapitalAccountCnd;
import com.dxjr.portal.stockright.vo.StockAccountRequest;
import com.dxjr.portal.stockright.vo.StockDealRequest;
import com.dxjr.portal.stockright.vo.StockEntrustApplyCnd;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;
import com.dxjr.portal.util.PdfLowManager;
import com.dxjr.portal.util.PdfManager;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.file.FileUtil;
import com.itextpdf.text.pdf.PdfReader;

/***
 * 交易接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
@Service
public class StockDealServiceImpl implements StockDealService {
	private static final Logger logger = Logger.getLogger(StockDealServiceImpl.class);

	@Autowired
	private StockDealMapper stockDealMapper;
	
	@Autowired
	private StockEntrustMapper stockEntrustMapper;
	
	@Autowired
	private StockAccountService stockAccountService;
	
	@Autowired
	private StockApplyService stockApplyInfoService;
	
	@Autowired
	private StockAccountMapper stockAccountMapper;
	
	@Autowired
	private StockAccountLogService stockAccountLogService;
	
	@Autowired
	private StockEntrustService stockEntrustService;
	
	@Autowired
	private StockEntrustLogService stockEntrustLogService;
	
	@Autowired
	private  CapitalAccountMapper capitalAccountMapper;
	
	@Autowired
	private  CapitalAccountService capitalAccountService;
	
	@Autowired
	private BaseFreemarkerService baseFreemarkerService;
	
	@Autowired
	private CurOutService curOutService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 
	 * <p>
	 * Description:获取最新成交记录<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @return
	 * List<StockDeal>
	 */
	public List<StockDeal> queryFistStockDeal(){
		List<StockDeal> entrustList = stockDealMapper.queryFistStockDeal();
		return entrustList;
	}
	
	public List<Map<String,Object>> summaryStockDeal(StockDealRequest request){
		return stockDealMapper.summaryStockDeal(request);
	}
	

	@Override
	public MessageBox saveSellerApplyEntrust(StockEntrustApplyCnd entrustCnd) throws Exception {
		// 1:校验申请参数
		//判断A轮股东股权登录申请转让时间？？？？？？？？？？
		/*String tadayTime = DateUtils.format(new Date(), DateUtils.YMD);
		if(Integer.parseInt(tadayTime) < 20160801){
			return new MessageBox("50000", "当前持有份额时间不足6个月,不允许转让");
		}*/
		
		//查查询认购单数量 
		StockEntrustCnd cnd = new StockEntrustCnd();
		cnd.setEntrustType(1);
		cnd.setUserId(entrustCnd.getUserId());
		int entrustCount = stockEntrustMapper.queryUserEntrust(cnd);
		if(entrustCount > 0){
			return new MessageBox("50000", "您有认购挂单未成交，当前不允许转让！");
		}
		MessageBox  result = this.checkApplyEntrust(entrustCnd);
		if("00000".equals(result.getCode())){
			//2:保存申请委托申请记录
			int sellerEntrustId = this.saveApplyEntrust(entrustCnd);
			
			//3:获取锁定转让委托记录
			StockEntrustCnd stockEntrustCnd = new StockEntrustCnd();
			stockEntrustCnd.setId(sellerEntrustId);
			stockEntrustCnd.setIsLocked(1);
			StockEntrust seller = stockEntrustService.queryEntrustForUpdate(stockEntrustCnd);
			if(seller==null || ( seller.getStatus() != 1 &&  seller.getStatus() != 2) || seller.getResidueAmount() <= 0){
				return result;
			}
				
			//4：获取锁定 认购记录
			StockEntrustCnd buyerRequest = new StockEntrustCnd();
			buyerRequest.setEntrustType(1);
			buyerRequest.setPrice(seller.getPrice());
			buyerRequest.setUserId(entrustCnd.getUserId());
			List<StockEntrust> buyerList= stockEntrustMapper.queryListForUpdate(buyerRequest);
			if(buyerList.size() > 0){
			//5: 匹配撮合
				for(StockEntrust buyer : buyerList){
					if(seller.getResidueAmount() <= 0)
						break;
					
			//撮合	1：转让方转让量 > 认购方认购量
					if(seller.getResidueAmount() > buyer.getResidueAmount()){
						//认购方           转让方             成交数量          成交金额         成交类型（主动转让）
						this.updateMatch(buyer, seller, buyer.getResidueAmount(),buyer.getPrice(),1);
					}else{
			//撮合	2：转让方转让量  <=  认购方认购量
						//认购方           转让方             成交数量          成交金额          成交类型（主动转让）
						this.updateMatch(buyer, seller, seller.getResidueAmount(),buyer.getPrice(),1);
					}
			
				}
				
			}
		}
		return result;
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:验证申请参数<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * @return
	 * boolean
	 */
	private MessageBox checkApplyEntrust(StockEntrustApplyCnd entrustCnd){
		
		int appNum = entrustCnd.getAmount();
		StockAccount sellerStockAccount = stockAccountService.selectByPrimaryKey(entrustCnd.getUserId());
		int userStock = sellerStockAccount.getUseStock();
		
		//1: 校验单价 //2: 校对数量
		MessageBox box= this.checkApplyParameter(entrustCnd);
		if(!"00000".equals(box.getCode())){
			return box;
		}
		
		if(userStock <  appNum){
			return new MessageBox("50000", "转让内转份额不能大于可卖出内转份额！");
		}
		
		//3: 校对账户可操作股权数量与申请转让股权数量
		String checkUserStock =  Dictionary.getValue(1905, "stock_control_num");
		int stockNum = Integer.parseInt(checkUserStock);
		//转让前大于5W  转让后 小于5W
		if(userStock >= stockNum && userStock - appNum < stockNum){
			//查询是否有退出股东名称申请记录
			ApplyInfo apply = stockApplyInfoService.querySignOutApply(entrustCnd.getUserId());
			if(apply == null || apply.getStatus()== -1){
				return new MessageBox("60000", "您当前内转份额"+userStock+",转让"+appNum+"份需要做退出内转花名册申请！");
			}
			if(apply.getStatus() == 2){
				return new MessageBox("00000", "");
			}else if(apply.getStatus()==1){
				return new MessageBox("50000", "退出有限合伙审核中，请耐心等待！");
			}else if(apply.getStatus()==3){
				return new MessageBox("60000", "退出有限合伙审核未通过，请重新申请！");
			}else{
				return new MessageBox("50000", "申请退出内转异常！");
			}
		}
		return box;
	}
	
	/**
	 * 
	 * <p>
	 * Description:保存委托转让申请<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * void
	 */
	private int saveApplyEntrust(StockEntrustApplyCnd entrustCnd){
		//转让方服务费
		BigDecimal sellerRate = new BigDecimal(Dictionary.getValue(1902, "seller_rate"));
		//本次委托单价
		BigDecimal price = entrustCnd.getPrice();
		//本次委托数量
		Integer amount = entrustCnd.getAmount();
		//委托总价
		BigDecimal totalPrice = price.multiply(new BigDecimal(amount));
		//委托手续费
		BigDecimal fee = totalPrice.multiply(sellerRate);
		//1：冻结转让股权数量
			StockAccount stockAccount = stockAccountService.selectAccountForUpdate(entrustCnd.getUserId(),"forUpdate");
			//可用股权数量 = 原可用股权数量  - 股权转让数量
			stockAccount.setUseStock(stockAccount.getUseStock()-amount);
			//冻结总数 = 原冻结股权数量 + 股权转让数量
			stockAccount.setNoUseStock(stockAccount.getNoUseStock()+amount);
			stockAccountMapper.updateByPrimaryKeySelective(stockAccount);
		//3：生成转让委托单
			StockEntrust entrust = new StockEntrust();
			entrust.setEntrustType(2);//1、认购；2、转让
			entrust.setAmount(amount);//本次委托量
			entrust.setTotalPrice(totalPrice);
			entrust.setExpectFee(fee);
			entrust.setPrice(price);
			entrust.setRemark("申请委托转让");
			entrust.setAddip(entrustCnd.getAddip());
			entrust.setUserId(entrustCnd.getUserId());
			StockEntrust record = stockEntrustService.saveStockEntrust(stockAccount, entrust);
			
		//2：保存股权变更日志
			StockAccountlog stockAccountLog = new StockAccountlog();
			stockAccountLog.setOptStock(amount);//操作股权数量
			stockAccountLog.setOptType(2);//1、认购；2、转让；3、开户；-1、撤销
			stockAccountLog.setTargetId(record.getId());
			stockAccountLog.setTargetName("gq_stock_entrust");
			stockAccountLog.setTargetType(6);//1、主动认购；2、被动认购；3、开户；4、主动转让；5、被动转让；6、委托转让；7、委托认购；8、撤销认购；9、撤销转让
			stockAccountLog.setToUser(-1);
			stockAccountLog.setRemark("内转委托转让");
			stockAccountLog.setAddip(entrustCnd.getAddip());
			stockAccountLogService.saveStockAccountlog(stockAccount, stockAccountLog);
		
		//4：生成委托日志
			stockEntrustLogService.saveStockEntrustLog(record);
		return record.getId();
	}
	
	private void updateMatch(StockEntrust buyer, StockEntrust seller, int appNum, BigDecimal price,int dealType) throws Exception{
		//转让方服务费率
		BigDecimal sellerRate = new BigDecimal(Dictionary.getValue(1902, "seller_rate"));
		//认购方服务费率
		BigDecimal buyerRate = new BigDecimal(Dictionary.getValue(1902, "buyer_rate"));
		//总金额
		BigDecimal totalMoney = price.multiply(new BigDecimal(appNum));
		//转让方服务费
		BigDecimal sellerFee = totalMoney.multiply(sellerRate);
		//认购方服务费
		BigDecimal buyerFee = totalMoney.multiply(buyerRate);
		
		//1：判断认购方是否开户 ,未开户的先开户
		StockAccount isStockAccount = stockAccountService.selectAccountForUpdate(buyer.getUserId(),"");
		if(isStockAccount == null){
			stockAccountService.createStoceAccount(buyer.getUserId(),seller.getStockCode(),seller.getStockName());
			buyer.setStockCode(seller.getStockCode());
			buyer.setStockName(seller.getStockName());
		}
		
		//2:变更转让方委托单
		seller.setDealAmount(seller.getDealAmount()+appNum);
		seller.setDealTotalPrice(seller.getDealTotalPrice().add(totalMoney));

		seller.setDealFee(seller.getDealFee().add(sellerFee));
		seller.setDealRate(sellerRate);
		int sellerResidueAmount = seller.getResidueAmount()- appNum;
		seller.setStatus(sellerResidueAmount > 0 ? 2 : 3);
		seller.setResidueAmount(sellerResidueAmount);
		seller.setUpdateuser(-1);
		stockEntrustService.updateEntrust(seller);
		
		//3:生成转让方委托单日志
		stockEntrustLogService.saveStockEntrustLog(seller);
		
		//4:变更认购方方委托单

		buyer.setDealAmount(buyer.getDealAmount()+appNum);
		buyer.setDealTotalPrice(buyer.getDealTotalPrice().add(totalMoney));
		buyer.setDealFee(buyer.getDealFee().add(buyerFee));
		buyer.setDealRate(buyerRate);
		int  buyerResidueAmount = buyer.getResidueAmount()- appNum;
		buyer.setStatus(buyerResidueAmount > 0 ? 2 : 3);
		buyer.setResidueAmount(buyerResidueAmount);
		buyer.setUpdateuser(-1);
		stockEntrustService.updateEntrust(buyer);
		
		//5:生成认购方委托单日志
		stockEntrustLogService.saveStockEntrustLog(buyer);
		
		//6：添加成交记录
		StockDeal record = new StockDeal();
		record.setTurnoverAmount(appNum);
		record.setDealType(dealType);
		record.setTurnoverPrice(price);
		record.setTurnoverTotalPrice(totalMoney);
		record.setServiceCharge(sellerFee.add(buyerFee));
		record.setSellerFee(sellerFee);
		record.setSellerRate(sellerRate);
		record.setBuyerFee(buyerFee);
		record.setBuyerRate(buyerRate);
		this.saveDealRecord(buyer,seller,record);
		
		if(buyerResidueAmount == 0){
			BigDecimal entrustTotalPrice = buyer.getEntrustTotalPrice();
			//扯返金额 = 委托总价（包含服务费） - 已经成交金额 - 成交服务费
			BigDecimal withdrawMoney = entrustTotalPrice.subtract(buyer.getDealTotalPrice()).subtract(buyer.getDealFee());
			//认购方交易完成，剩余资金大于0 返回到可用
			if(withdrawMoney.compareTo(BigDecimal.ZERO) > 0){
				this.buyerAccountWithdraw(buyer,withdrawMoney);
			}
		}
	}
	
	private void saveDealRecord(StockEntrust buyer, StockEntrust seller,StockDeal record){
		record.setSellerEntrustId(seller.getId());
		record.setSellerId(seller.getUserId());
		record.setSellerName(seller.getUserName());
		record.setSellerRealName(seller.getUserRealName());
		record.setSellerStockCode(seller.getStockCode());
		record.setSellerStockName(seller.getStockName());
		record.setEntrustPrice(seller.getPrice());
		record.setSurplusSellerAmount(seller.getResidueAmount());
		record.setBuyerEntrustId(buyer.getId());
		record.setBuyerPrice(buyer.getPrice());
		record.setSurplusBuyerAmount(buyer.getResidueAmount());
		record.setBuyerId(buyer.getUserId());
		record.setBuyerName(buyer.getUserName());
		record.setBuyerRealName(buyer.getUserRealName());
		record.setBuyerStockCode(buyer.getStockCode());
		record.setBuyerStockName(buyer.getStockName());
		record.setRemark("系统撮合成功");
		record.setStatus(1);//待定时器处理
		record.setAdduser(-1);
		stockDealMapper.insertSelective(record);
	}
	
	public Page queryPageList(StockDealRequest dealCnd,Page page){
		Integer count = stockDealMapper.countDetailList(dealCnd);
		page.setTotalCount(count);
		List<StockDeal> dealList = stockDealMapper.fundDetailList(dealCnd, page);
		page.setResult(dealList);
		return page;
	}
	
	public StockDeal selectByPrimaryKey(Integer id){
		StockDeal deal =  stockDealMapper.selectByPrimaryKey(id);
		return deal;
	 }
	
	
	//申请认购时校验申请数量与申请单价
	private MessageBox checkApplyParameter(StockEntrustApplyCnd entrustCnd){
		BigDecimal appPrice = entrustCnd.getPrice();
		int appNum = entrustCnd.getAmount();
		
		//1: 校验单价
		String priceMin = Dictionary.getValue(1901, "price_min");
		String priceMax = Dictionary.getValue(1901, "price_max");
		BigDecimal maxPrice = new BigDecimal(priceMax);
		BigDecimal minPrice = new BigDecimal(priceMin);
		if(appPrice.compareTo(minPrice) < 0  || appPrice.compareTo(maxPrice) > 0){
			return new MessageBox("50000", "内转单价不合法！");
		}
		
		//2: 校对数量
		String stockApplyNum = Dictionary.getValue(1903, "stock_increase");
		int checkNum = Integer.parseInt(stockApplyNum);
		if(appNum % checkNum != 0 && appNum > 0){
			return new MessageBox("50000", "数量必须以"+checkNum+"倍数增加！");
		}
		
		return new MessageBox("00000", "");
	}
/*********************************************转让方认购*******************************************************************/	
	
	public MessageBox saveBuyerApplyEntrust(StockEntrustApplyCnd entrustCnd) throws Exception{
		StockEntrustCnd cnd = new StockEntrustCnd();
		cnd.setEntrustType(2);
		cnd.setUserId(entrustCnd.getUserId());
		int entrustCount = stockEntrustMapper.queryUserEntrust(cnd);
		if(entrustCount > 0){
			return new MessageBox("50000", "您有转让挂单未成交，当前不允许认购！");
		}
		
		//1:校验资金账户与认购金额验证
		MessageBox box = this.checkApplyParameter(entrustCnd);
		if(!"00000".equals(box.getCode())){
			return box;
		}
		//认购方服务费率
		BigDecimal buyerRate = new BigDecimal(Dictionary.getValue(1902, "buyer_rate"));
		//股权总价
		BigDecimal totalStockPrice = entrustCnd.getPrice().multiply(new BigDecimal(entrustCnd.getAmount()));
		//申请认购服务费
		BigDecimal applyFee = totalStockPrice.multiply(buyerRate); 
		//认购总价
		BigDecimal applyTotalPrice = totalStockPrice.add(applyFee);
		AccountVo account = null;
		account = accountService.queryAccountByUserIdForUpdate(entrustCnd.getUserId());
		if(account == null){
			return new MessageBox("20000", "资金账户信息错误！");
		}
		//2:验证余额与申请冻结金额
		BigDecimal differenceAmount = BigDecimal.ZERO;
			if(entrustCnd.getIsCur() == 1 && account.getUseMoney().compareTo(applyTotalPrice) < 0){
				differenceAmount = applyTotalPrice.subtract(account.getUseMoney());
				//调用  活期宝金额转出用于股权
				String result = curOutService.turnCurOutForInvest(entrustCnd.getUserId(), differenceAmount, "0.0.0.1", BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK);
				if (!BusinessConstants.SUCCESS.equals(result)) {
					throw new Exception("活期宝账户操作异常");
				}
				account = accountService.queryAccountByUserIdForUpdate(entrustCnd.getUserId());
			}
		if(account.getUseMoney().compareTo(applyTotalPrice) < 0){
			return new MessageBox("20000", "您的账户余额不足，请充值！");
		}
		//3：冻结资金账户  产生委托单
		int entrustId = this.updateBuyerAccount(applyTotalPrice,account,entrustCnd,differenceAmount);
		
		
		//调用 变更活期宝转入资金targetID
		CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK, entrustCnd.getUserId(), differenceAmount);
		if (curOut != null) {
			curOut.setTargetId(entrustId);
			curOutService.updateByPrimaryKeySelective(curOut);
		}

		//4:获取锁定认购委托记录
		StockEntrustCnd stockEntrustCnd = new StockEntrustCnd();
		stockEntrustCnd.setId(entrustId);
		stockEntrustCnd.setIsLocked(2);
		StockEntrust buyer = stockEntrustService.queryEntrustForUpdate(stockEntrustCnd);
		if(buyer==null || ( buyer.getStatus() != 1 &&  buyer.getStatus() != 2) || buyer.getResidueAmount() <= 0){
			return box;
		}
			
		//5：获取锁定 转让记录列表
		StockEntrustCnd buyerRequest = new StockEntrustCnd();
		buyerRequest.setPrice(buyer.getPrice());
		buyerRequest.setEntrustType(2);
		buyerRequest.setUserId(entrustCnd.getUserId());
		List<StockEntrust> sellerList= stockEntrustMapper.queryListForUpdate(buyerRequest);
		if(sellerList.size() > 0){
		//6: 匹配撮合
			for(StockEntrust seller : sellerList){
				if(buyer.getResidueAmount() <= 0)
					break;
		//撮合	1：认购方转让量 < 转让方认购量
				if(buyer.getResidueAmount() < seller.getResidueAmount()){
					//认购方           转让方             成交数量          成交金额          成交类型（主动认购）
					this.updateMatch(buyer, seller, buyer.getResidueAmount(), seller.getPrice(),2);
				}else{
		//撮合	2：认购方转让量  >=  转让方认购量
					//认购方           转让方             成交数量          成交金额          成交类型（主动认购）
					this.updateMatch(buyer, seller, seller.getResidueAmount(),seller.getPrice(),2);
				}
			}
			
		}
		return box;
	}

	
	/**
	 * 
	 * <p>
	 * Description:活期宝资金变更到可用余额<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-19
	 * @param difference
	 * @param account
	 * @return
	 * BigDecimal
	 */
	private Map<String,BigDecimal> updateCurToAccount(BigDecimal difference, AccountVo account,String ip){
		CurAccount cur = capitalAccountMapper.querCurByUserId(account.getUserId());
		Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();
		BigDecimal drawMoney = account.getDrawMoney();
		map.put("freezeUseMoney", BigDecimal.ZERO);
		map.put("freezeNoUseMoney", BigDecimal.ZERO);
		if(cur != null){
			
			if(cur.getTotal().compareTo(difference) < 0){
				return map;
			}
			
			BigDecimal freezeUseMoney = BigDecimal.ZERO;
			BigDecimal freezeNoUseMoney = BigDecimal.ZERO;
			if(cur.getUseMoney().compareTo(difference) < 0){
				freezeUseMoney = cur.getUseMoney();
				freezeNoUseMoney = difference.subtract(cur.getUseMoney());
				map.put("freezeUseMoney", freezeUseMoney);
				map.put("freezeNoUseMoney", freezeNoUseMoney);
			}else{
				freezeUseMoney = difference;
				map.put("freezeUseMoney", freezeUseMoney);
			}
			//1:变更活期宝账户余额
			cur.setTotal(cur.getTotal().subtract(difference));
			cur.setUseMoney(cur.getUseMoney().subtract(freezeUseMoney));
			cur.setNoUseMoney(cur.getNoUseMoney().subtract(freezeNoUseMoney));
			cur.setLastUpUser(account.getUserId());
			capitalAccountMapper.updateCurByUserId(cur);
			//2：产生活期宝变更日志
			CurAccountlog curLog =  new CurAccountlog();
			curLog.setMoney(difference);
			curLog.setType(201);//转出到可用余额 
			curLog.setAddip(ip);
			curLog.setRemark("活期宝转可用余额");
			curLog.setAdduser(account.getUserId());
			capitalAccountService.saveCurAccountlog(cur, curLog);
			//3:变更资金账户信息
			account.setTotal(account.getTotal().add(difference));
			account.setUseMoney(account.getUseMoney().add(difference));
			account.setDrawMoney(account.getDrawMoney().add(difference));
			capitalAccountMapper.updateAccount(account);
			//4:产生资金账户变更日志
			AccountLogCnd accountLogCnd = new AccountLogCnd();
			accountLogCnd.setType("101"); //活期宝转入可用余额	 
			accountLogCnd.setMoney(difference);
			accountLogCnd.setToUser(-1);
			accountLogCnd.setBorrowId(cur.getId());
			accountLogCnd.setBorrowName("t_cur_account");
			accountLogCnd.setIdType(5);//活期宝转入
			accountLogCnd.setRemark("活期宝转入可提");
			accountLogCnd.setAddip(ip);
			capitalAccountService.saveAccountlog(account, accountLogCnd);
			map.put("drawMoney", drawMoney.add(difference));
		}
		return map;
			
	}
	
	
	
	/**
	 * 
	 * <p>
	 * Description:股权认购申请，冻结资金 产生委托单<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-20
	 * @param applyFreezeMoney
	 * @param account
	 * @param entrustCnd
	 * void
	 */
	private int updateBuyerAccount(BigDecimal applyFreezeMoney, AccountVo account, 
			StockEntrustApplyCnd entrustCnd,BigDecimal differenceAmount){
		BigDecimal freezeNoDrawMoney = BigDecimal.ZERO;
		BigDecimal freezeDrawMoney = BigDecimal.ZERO;
		if(applyFreezeMoney.compareTo(account.getNoDrawMoney()) <= 0){
			//受限金额部分扣除
			freezeNoDrawMoney = applyFreezeMoney;
		}else{
			//受限金额全部扣除
			freezeNoDrawMoney = account.getNoDrawMoney();
			freezeDrawMoney = applyFreezeMoney.subtract(account.getNoDrawMoney());
		}
		
			//认购方服务费率
		BigDecimal buyerRate = new BigDecimal(Dictionary.getValue(1902, "buyer_rate"));
			//股权总价
		BigDecimal totalStockPrice = entrustCnd.getPrice().multiply(new BigDecimal(entrustCnd.getAmount()));
			//申请认购服务费
		BigDecimal applyFee = totalStockPrice.multiply(buyerRate); 
		
		
		//1：产生认购委托单
		StockEntrust entrust = new StockEntrust();
		entrust.setEntrustType(1);//1、认购；2、转让
		entrust.setAmount(entrustCnd.getAmount());//本次委托量
		entrust.setTotalPrice(totalStockPrice);
		entrust.setExpectFee(applyFee);
		entrust.setPrice(entrustCnd.getPrice());
		entrust.setRemark("申请委托认购");
		entrust.setAddip(entrustCnd.getAddip());
		entrust.setUserId(entrustCnd.getUserId());
		entrust.setDrawMoney(freezeDrawMoney);
		entrust.setNoDrawMoney(freezeNoDrawMoney);
		//活期宝产生收益扣除金额
		entrust.setCurUseMoney(BigDecimal.ZERO);
		//活期宝未产生收益扣除金额
		entrust.setCurNoUseMoney(differenceAmount);
		StockAccount stockAccount = stockAccountService.selectAccountForUpdate(entrustCnd.getUserId(),"");
		StockEntrust record = stockEntrustService.saveStockEntrust(stockAccount, entrust);
	
		//2：产生委托单日志
		stockEntrustLogService.saveStockEntrustLog(record);
		
		//3:冻结认购方资金账户
		account.setNoUseMoney(account.getNoUseMoney().add(applyFreezeMoney));
		account.setUseMoney(account.getUseMoney().subtract(applyFreezeMoney));
		account.setNoDrawMoney(account.getNoDrawMoney().subtract(freezeNoDrawMoney));
		account.setDrawMoney(account.getDrawMoney().subtract(freezeDrawMoney));
		capitalAccountMapper.updateAccount(account);
		
		//4：添加资金账户变更日志
		AccountLogCnd accountLogCnd = new AccountLogCnd();
		accountLogCnd.setType("gq_deal_freeze"); //新增类型    股权交易冻结
		accountLogCnd.setMoney(applyFreezeMoney);
		accountLogCnd.setToUser(-1);
		accountLogCnd.setBorrowId(record.getId());
		accountLogCnd.setBorrowName("gq_stock_entrust");
		accountLogCnd.setIdType(10);//新增类型   股权认购委托冻结
		accountLogCnd.setRemark("内转认购冻结");
		accountLogCnd.setAddip(entrustCnd.getAddip());
		capitalAccountService.saveAccountlog(account, accountLogCnd);
		
		return record.getId();
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:认购方完全成交时，撤回剩余冻结金额到可提<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-20
	 * @param buyer
	 * @throws Exception
	 * void
	 */
	private void buyerAccountWithdraw(StockEntrust buyer,BigDecimal withdrawMoney) throws Exception{		
		CapitalAccountCnd accountCnd = new CapitalAccountCnd();
		accountCnd.setUserId(buyer.getUserId());
		accountCnd.setYear("yes");
		AccountVo account = capitalAccountMapper.queryCapitalAccount(accountCnd);
		account.setUseMoney(account.getUseMoney().add(withdrawMoney));
		account.setDrawMoney(account.getDrawMoney().add(withdrawMoney));
		account.setNoUseMoney(account.getNoUseMoney().subtract(withdrawMoney));
		capitalAccountMapper.updateAccount(account);
		
		//4:产生资金账户变更日志
		AccountLogCnd accountLogCnd = new AccountLogCnd();
		accountLogCnd.setType("gq_deal_withdraw"); //认购方全部成交  结余资金解冻
		accountLogCnd.setMoney(withdrawMoney);
		accountLogCnd.setToUser(-1);
		accountLogCnd.setBorrowId(buyer.getId());
		accountLogCnd.setBorrowName("gq_stock_entrust");
		accountLogCnd.setIdType(12);//活期宝转入
		accountLogCnd.setRemark("内转认购交易完成，剩余资金解冻");
		accountLogCnd.setAddip("0.0.0.1");
		capitalAccountService.saveAccountlog(account, accountLogCnd);
	}
	
	@Override
	public String querysSockDeailDload(Integer dealId, String contextPath,Integer userId,Map<String ,Object> reqMap) throws Exception{
		
		StockDeal dealDetail =  stockDealMapper.selectByPrimaryKey(dealId);
		Map<String, Object> templateMap = new HashMap<String, Object>();
		
		StockEntrust sellerEntrust = stockEntrustMapper.findEntrustById(dealDetail.getSellerEntrustId());
		StockEntrust buyerEntrust = stockEntrustMapper.findEntrustById(dealDetail.getBuyerEntrustId());
		StockAccountRequest request = new StockAccountRequest();
		request.setUserId(dealDetail.getSellerId());
		StockUserInfoVo sellerInfo =  stockAccountMapper.queryUserInfoById(request);
		request.setUserId(dealDetail.getBuyerId());
		StockUserInfoVo buyerInfo = stockAccountMapper.queryUserInfoById(request);
		
		templateMap.put("sellerEntrust", sellerEntrust);
		templateMap.put("buyerEntrust", buyerEntrust);
		templateMap.put("deal", dealDetail);
		templateMap.put("sellerInfo", sellerInfo);
		templateMap.put("buyerInfo", buyerInfo);
		String templateName = EnumerationUtil.getProtocolPathByBorrowType("11");
		/********************** download s **************************************/
		commDownLoad(templateName, templateMap, reqMap, "内转交易协议");
		/********************** download e **************************************/
		return "success";
	}
	
	public void commDownLoad(String templateName, Map<String, Object> templateMap, Map<String, Object> reqMap, String titleName) throws Exception {
		logger.info("下载 协议- 公共方法 start ");
		try {
			// 临时html文件
			String htmlPathName = "tmpFile/stockDownLoad" + System.currentTimeMillis() + ".html";
			// 读取协议文件pledge.ftl-生成临时html文件
			baseFreemarkerService.createHtml(templateName, templateMap, reqMap.get("realPathNew") + htmlPathName);
			// 临时pdf文件
			String pdfPathName = "tmpFile/stockDownLoad" + System.currentTimeMillis() + ".pdf";
			// 页眉图片
			String headerImagePath = reqMap.get("realPathNew") + "images/header.png";
			try {
				// 生成临时pdf文件
				PdfManager.createPdf(reqMap.get("realPathNew") + htmlPathName, reqMap.get("realPathNew") + pdfPathName,headerImagePath);
			} catch (Exception e) {
				logger.error("生成临时pdf文件错误", e);
			}
			PdfReader reader = new PdfReader(reqMap.get("realPathNew") + pdfPathName);
			int pagecount = reader.getNumberOfPages();
			String finishPdfName = "tmpFile/borrowInfoDownLoad_new" + System.currentTimeMillis() + ".pdf";
			// 图章path
			String markImagePath = reqMap.get("realPathNew") + "images/tuzhang.png";
			// 加水印前pdf path
			String InPdfFile = reqMap.get("realPathNew") + pdfPathName;
			// 加水印后pdf path
			String outPdfFile = reqMap.get("realPathNew") + finishPdfName;

			logger.error("InPdfFile================================"+InPdfFile);
			logger.error("outPdfFile================================"+outPdfFile);
			logger.error("markImagePath================================"+markImagePath);
			logger.error("pagecount================================"+pagecount);
			// 给临时pdf文件加水印图片
			PdfLowManager.addPdfMarkLow(InPdfFile, outPdfFile, markImagePath, pagecount);

			// 下载pdf文件
			FileUtil.streamDownload((HttpServletRequest) reqMap.get("request"), finishPdfName, "内转交易协议"
					+ DateUtils.format(new Date(), DateUtils.YMD) + ".pdf", (HttpServletResponse) reqMap.get("response"));

			// 删除临时html,pdf,pdf(加水印)文件
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), htmlPathName);
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), pdfPathName);
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), finishPdfName);
		} catch (Exception e) {
			logger.error("下载 协议- 公共方法异常", e);
		}
		logger.info("下载 协议- 公共方法 end ");
	}

	
}
