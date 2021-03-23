package com.dxjr.portal.first.mapper;

import com.dxjr.base.entity.FirstTransferApproved;

/**
 * <p>
 * Description:直通车转让审核数据库访问类<br />
 * </p>
 * 
 * @title FirstTransferApprovedMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2015年3月16日
 */
public interface FirstTransferApprovedMapper {
	/**
	 * <p>
	 * Description:保存直通车转让审核信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferApproved
	 * @return
	 * @throws Exception Integer
	 */
	public Integer saveFirstTransferApproved(FirstTransferApproved firstTransferApproved) throws Exception;
}