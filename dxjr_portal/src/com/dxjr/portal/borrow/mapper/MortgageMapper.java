package com.dxjr.portal.borrow.mapper;

import com.dxjr.base.mapper.BaseMortgageMapper;
import com.dxjr.portal.borrow.vo.MortgageVo;

public interface MortgageMapper extends BaseMortgageMapper {
	/**
	 * <p>
	 * Description:根据借款标id查询抵押信息<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月26日
	 * @param map
	 * @param p
	 * @return
	 * @throws Exception Page
	 */
	public MortgageVo getMortgageByBorrowId(Integer borrowId) throws Exception;
}
