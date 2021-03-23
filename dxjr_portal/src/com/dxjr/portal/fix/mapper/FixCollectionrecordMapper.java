package com.dxjr.portal.fix.mapper;

import com.dxjr.base.entity.FixCollectionrecord;

import java.math.BigDecimal;

/**
 * <p>
 * Description:投资人待收记录数据库接口类<br />
 * </p>
 * 
 * @title FixCollectionrecordMapper.java
 * @package com.dxjr.portal.fix.mapper
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public interface FixCollectionrecordMapper {
	/**
	 * 添加用户待收记录
	 * @param fixCollectionrecord
	 * @return
	 */
	public Integer insertFixCollectionrecord(FixCollectionrecord fixCollectionrecord) throws Exception;

	/**
	 * 根据最终认购记录获取投资人代收记录
	 * @return
	 */
	BigDecimal getFixCollectionrecordByTenderRealId(int fixTenderRealId);
}
