package com.dxjr.portal.stockright.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;


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
public interface StockEntrustService {
	
	/****
	 * 根据传入条件查询委托挂单信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-11
	 * @param entrustCnd
	 * @return
	 * List<StockEntrust>
	 */
	public List<StockEntrust> findEntrustListByUserId(StockEntrustCnd entrustCnd);
	
	/**
	 * 
	 * <p>
	 * Description:获取委托记录列表<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @param entrustCnd
	 * @param pageSize
	 * @return
	 * List<StockEntrust>
	 */
	public List<StockEntrust> queryList(StockEntrustCnd entrustCnd,int pageSize);
	
	/**
	 * 
	 * <p>
	 * Description:用户挂单列表<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * @param pageSize
	 * @return
	 * List<StockEntrust>
	 */
	public Page queryPageList(StockEntrustCnd entrustCnd,Page page);
	
	
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
	public StockEntrust saveStockEntrust(StockAccount stockAccount,StockEntrust entrust);

	/**
	 * 
	 * <p>
	 * Description:查询委托单实体<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-15
	 * @param entrustCnd
	 * @return
	 * StockEntrust
	 */
	public StockEntrust queryEntrustForUpdate(StockEntrustCnd entrustCnd);
	
	/**
	 * 
	 * <p>
	 * Description:变更委托转让单<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-15
	 * @param request
	 * @return
	 * StockEntrust
	 */
	public int updateEntrust(StockEntrust request);
	/****
	 * 根据id查询委托记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @param id
	 * @return
	 * StockEntrust
	 */
	
	public StockEntrust findEntrustById(Integer id);
	
	
	/**
	 * 
	 * <p>
	 * Description:转让方委托单撤销<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-18
	 * @param entrustCnd
	 * @return
	 * MessageBox
	 */
	public MessageBox saveRevokeEntrust(StockEntrustCnd entrustCnd) throws Exception;
	
	/****
	 * 统计转让委托剩余量
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-20
	 * @return
	 * Integer
	 */
	public Integer countSellerEntrust();
}
