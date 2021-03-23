package com.dxjr.portal.borrow.service;

import com.dxjr.portal.borrow.vo.BorrowApprovedVo;

/**
 * <p>
 * Description:借款标审核业务类<br />
 * </p>
 * 
 * @title BorrowApprovedService.java
 * @package com.dxjr.portal.borrow.service
 * @author justin.xu
 * @version 0.1 2014年8月30日
 */
public interface BorrowApprovedService {
	/**
	 * <p>
	 * Description:根据借款标id找到审核记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月30日
	 * @param borrowId
	 * @return BorrowApprovedVo
	 */
	public BorrowApprovedVo selectByBorrowIdForUpdate(Integer borrowId)
			throws Exception;
}
