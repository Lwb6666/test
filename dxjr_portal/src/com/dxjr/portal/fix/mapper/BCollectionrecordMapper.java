package com.dxjr.portal.fix.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.collection.vo.BCollectionRecordVo;

public interface BCollectionrecordMapper {
	/**
	 * 
	 * <p>
	 * Description:根据定期宝ID查询投标记录表中待收本金总和<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年6月26日
	 * @param fixBorrowId
	 * @return Integer
	 */
	public BigDecimal queryCollectionrecordSum(Integer fixBorrowId);
	
	/**
	 * 根据Map参数查询待收记录
	 * <p>
	 * Description:根据Map参数查询待收记录<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param params
	 * @return
	 * List<BCollectionRecordVo>
	 */
	public List<BCollectionRecordVo> queryCollectionrecord(Map<String, Object> params);
 
}
