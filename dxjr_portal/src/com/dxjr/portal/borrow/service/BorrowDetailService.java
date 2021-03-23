package com.dxjr.portal.borrow.service;

import java.util.Map;

/**
 * <p>
 * Description:借款标详情业务接口<br />
 * </p>
 * 
 * @title BorrowDetailService.java
 * @package com.dxjr.portal.borrow.service
 * @author justin.xu
 * @version 0.1 2014年9月12日
 */
public interface BorrowDetailService {
	/**
	 * <p>
	 * Description:查询借款标详情数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月13日
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryBorrowDetailInfo(Integer borrowId) throws Exception;
	/**
	 * <p>
	 * Description:查询借款标详情数据,共后台审核使用<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月19日
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	Map<String, ?> showBorrowDetailInfo(Integer borrowId) throws Exception;

}
