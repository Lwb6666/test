package com.dxjr.portal.invest.service;

import java.util.Map;

import com.dxjr.portal.invest.vo.CollectionRepayInfoVo;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CollectionStatisticVo;

public interface CollectionStatisticService {
	/**
	 * 
	 * <p>
	 * Description:待收信息统计汇总<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月23日
	 * @param parameter
	 * @return
	 * CommonCollectionVo
	 */
	public CollectionStatisticVo countCollectionMoney(Map<String,Object> parameter);
	
	
	/**
	 * <p>
	 * Description:待收利息 总和  <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月7日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception
	 * CollectionRepayInfoVo
	 */
	public CollectionStatisticVo myAccountCollectionMoneyDS(CollectionStatisticCnd collectionStatisticCnd) throws Exception;

	
	/**
	 * <p>
	 * Description: 已收利息 总和 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月7日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception
	 * CollectionRepayInfoVo
	 */
	public CollectionStatisticVo myAccountCollectionMoneyYS(CollectionStatisticCnd collectionStatisticCnd) throws Exception;

	

}
