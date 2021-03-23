package com.dxjr.portal.account.service;

import com.dxjr.base.entity.CashRecordlog;

/**
 * <p>
 * Description:提现操作日志业务类<br />
 * </p>
 * 
 * @title CashRecordService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年6月18日
 */
public interface CashRecordlogService {
	/**
	 * <p>
	 * Description:保存提现操作日志<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年11月10日
	 * @param cashRecordlog
	 * @return Integer
	 * @throws Exception
	 */
	public Integer saveCashRecordlog(CashRecordlog cashRecordlog) throws Exception;
}
