package com.dxjr.portal.stockright.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.stockright.entity.StockDeal;
import com.dxjr.portal.stockright.vo.StockDealRequest;
import com.dxjr.portal.stockright.vo.StockEntrustApplyCnd;


/****
 * 交易信息接口
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustService.java
 * @package com.dxjr.portal.stockright.service 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
public interface StockDealService {
	
	/****
	 * 根据传入条件查询委托挂单信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param entrustCnd
	 * @return
	 * List<StockEntrust>
	 */
	public List<StockDeal> queryFistStockDeal();

	/**
	 * 
	 * <p>
	 * Description:统计股权买卖信息<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param request
	 * @return
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> summaryStockDeal(StockDealRequest request);
	
	
	/**
	 * 
	 * <p>
	 * Description:转让方股权转让申请、撮合<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * @return
	 * boolean
	 */
	public MessageBox saveSellerApplyEntrust(StockEntrustApplyCnd entrustCnd)throws Exception ;
	
	/***
	 * 查询交易列表分页
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @param dealCnd
	 * @param page
	 * @return
	 * Page
	 */
	public Page queryPageList(StockDealRequest dealCnd,Page page);
	
	/****
	 * 通过id查询交易详情
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @param id
	 * @return
	 * StockDeal
	 */
	public StockDeal selectByPrimaryKey(Integer id);
	
	/**
	 * 
	 * <p>
	 * Description:认购方股权转让申请、撮合<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * @return
	 * boolean
	 */
	public MessageBox saveBuyerApplyEntrust(StockEntrustApplyCnd entrustCnd )throws Exception;
	
	
	public String querysSockDeailDload(Integer dealId, String contextPath,Integer userId,Map<String ,Object> reqMap) throws Exception;
	
}
