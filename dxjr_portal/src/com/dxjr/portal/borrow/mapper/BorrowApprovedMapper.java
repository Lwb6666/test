package com.dxjr.portal.borrow.mapper;

import com.dxjr.portal.borrow.vo.BorrowApprovedVo;

/**
 * 
 * <p>
 * Description:借款标审核数据访问类<br />
 * </p>
 * 
 * @title BorrowApprovedMapper.java
 * @package com.dxjr.portal.borrow.mapper
 * @author justin.xu
 * @version 0.1 2014年8月30日
 */
public interface BorrowApprovedMapper {
	/**
	 * <p>
	 * Description:根据借款标id找到审核记录并锁定<br />
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