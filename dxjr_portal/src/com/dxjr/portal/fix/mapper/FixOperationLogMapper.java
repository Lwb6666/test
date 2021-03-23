package com.dxjr.portal.fix.mapper;

import com.dxjr.base.entity.FixOperationLog;

/**
 * <p>
 * Description:定期宝操作日志数据库接口类<br />
 * </p>
 * 
 * @title FixBorrowMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixOperationLogMapper {
	/**
	 * 添加定期宝操作日志
	 * @param fixOperationLog
	 */
	public void insertFixOperationLog (FixOperationLog fixOperationLog) throws Exception;
}
