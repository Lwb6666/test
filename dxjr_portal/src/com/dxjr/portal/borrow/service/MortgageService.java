package com.dxjr.portal.borrow.service;

import com.dxjr.portal.borrow.vo.MortgageVo;

public interface MortgageService {
	/**
	 * 
	 * <p>
	 * Description:根据借款标id查询抵押信息<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月26日
	 * @param map
	 * @param p
	 * @return
	 * @throws Exception Page
	 */
	public MortgageVo getMortgageByBorrowId(Integer borrowId) throws Exception;
}
