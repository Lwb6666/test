package com.dxjr.portal.member.service;

import com.dxjr.portal.member.vo.ApproModifyLog;




/**
 * <p>
 * Description:认证修改记录日志数据业务类<br />
 * </p>
 * 
 * @title ApproModifyLogService.java
 * @package com.dxjr.console.member.service
 * @author justin.xu
 * @version 0.1 2014年10月18日
 */
public interface ApproModifyLogService {

	/**
	 * <p>
	 * Description:新增认证修改记录日志数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月18日
	 * @param approModifyLog
	 * @throws Exception Integer
	 */
	public Integer saveApproModifyLog(ApproModifyLog approModifyLog) throws Exception;

}
