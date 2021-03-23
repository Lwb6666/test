package com.dxjr.portal.stock.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.Stock;
import com.dxjr.base.mapper.BaseStockMapper;

/**
 * 期权排名
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title BaseStockMapper.java
 * @package com.dxjr.base.mapper
 * @author huangpin
 * @version 0.1 2014年9月11日
 */
public interface StockMapper extends BaseStockMapper {

	/**
	 * 更新期权信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2016年9月27日
	 * @param stock
	 * @return int
	 */
	public int updateStockMoney(Stock stock);

	/**
	 * 
	 * <p>
	 * Description:根据Id查询期权记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月26日
	 * @param userId
	 * @return Stock
	 */
	public Stock queryByUserIdForUpdate(@Param("userId") int userId);

}
