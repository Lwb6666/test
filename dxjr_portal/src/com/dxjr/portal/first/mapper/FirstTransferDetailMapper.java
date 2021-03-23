package com.dxjr.portal.first.mapper;

import com.dxjr.base.entity.FirstTransferDetail;

/**
 * <p>
 * Description:直通车转让明细数据库访问类<br />
 * </p>
 * 
 * @title FirstTransferDetailMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2015年3月16日
 */
public interface FirstTransferDetailMapper {
	/**
	 * <p>
	 * Description:保存直通车转让明细信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferDetail
	 * @return
	 * @throws Exception Integer
	 */
	public Integer saveFirstTransferDetail(FirstTransferDetail firstTransferDetail) throws Exception;
}