package com.dxjr.portal.fix.mapper;

import com.dxjr.base.entity.FixTenderTransfer;

/**
 * @author WangQianJin
 * @title 定期宝投标转让Mapper
 * @date 2015年9月15日
 */
public interface FixTenderTransferMapper {
	
	/**
	 * 根据转让ID修改转让记录
	 * @author WangQianJin
	 * @title @param record
	 * @title @return
	 * @date 2015年9月15日
	 */
	int updateByTransferId(FixTenderTransfer record);
	
	/**
	 * 添加定期宝转让记录
	 * @author WangQianJin
	 * @title @param record
	 * @title @return
	 * @date 2015年9月15日
	 */
	int insert(FixTenderTransfer record);
}