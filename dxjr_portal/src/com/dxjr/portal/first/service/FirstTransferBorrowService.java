package com.dxjr.portal.first.service;

import java.math.BigDecimal;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细业务类<br />
 * </p>
 * 
 * @title FirstTransferBorrowService.java
 * @package com.dxjr.portal.first.service
 * @author justin.xu
 * @version 0.1 2015年3月18日
 */
public interface FirstTransferBorrowService {
	/**
	 * <p>
	 * Description:查询我的账号中-直通车转让对应标统计列表信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月18日
	 * @param firstTransferBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryCanTransferBorrowList(FirstTransferBorrowCnd firstTransferBorrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车转让ID查询转让资金明细信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月20日
	 * @param transferId
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryTransferBorrowListByTransferId(Integer transferId, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车转让ID查询债权剩余价值合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月30日
	 * @param transferId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public BigDecimal queryBorrowCollectionAccountSum(Integer transferId) throws Exception ;
	
	/**
	 * <p>
	 * Description:根据最终认购记录查询债权剩余价值合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月2日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryCanTransferBorrowAccountSum(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception ;
}
