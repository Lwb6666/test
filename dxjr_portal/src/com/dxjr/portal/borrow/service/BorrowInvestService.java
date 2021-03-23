package com.dxjr.portal.borrow.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowCnd;

public interface BorrowInvestService {
	/**
	 * 
	 * <p>
	 * Description:我要理财-查询借款标列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * Page
	 */
	public Page findInverst(BorrowCnd borrowCnd, int curPage, int pageSize) throws Exception;
}
