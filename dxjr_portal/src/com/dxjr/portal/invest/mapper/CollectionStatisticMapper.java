package com.dxjr.portal.invest.mapper;

import java.util.Map;

import com.dxjr.portal.invest.vo.CollectionRepayInfoVo;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CollectionStatisticVo;

/**
 * 
 * @author hujianpan 待收记录信息统计汇总
 */
public interface CollectionStatisticMapper {
	/**
	 * <p>
	 * Description:统计记录待收金额数据<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年12月23日
	 * @param parameter
	 * @return CommonCollectionVo
	 */
	public CollectionStatisticVo countCollectionMoney(Map<String, Object> parameter);

	/**
	 * <p>
	 * Description:根据查询条件有效应收总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月22日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception CollectionRepayInfoVo
	 */
	public CollectionRepayInfoVo queryRepayTotalByCnd(CollectionStatisticCnd collectionStatisticCnd) throws Exception;

	
	
	/**
	 * <p>
	 * Description: 待收利息 总和 <br />
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

	
	
	/**
	 * <p>
	 * Description:查询用户是否投过标<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年4月23日
	 * @param dto
	 * @return Integer
	 */
	public Integer queryCollectionRecordByCnd(Map<String, Integer> dto);
}
