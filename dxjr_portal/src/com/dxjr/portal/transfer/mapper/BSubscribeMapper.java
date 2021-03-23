package com.dxjr.portal.transfer.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.transfer.entity.BSubscribe;
import com.dxjr.portal.transfer.vo.BsubscribeVo;
import com.dxjr.portal.transfer.vo.TransferCnd;

public interface BSubscribeMapper {

	int insert(BSubscribe record);

	BSubscribe selectById(Integer id);

	List<BSubscribe> querySubscribesByTransferId(@Param("transferId") Integer transferId, Page page);

	int queryCountSubscribesByTransferId(@Param("transferId") Integer transferId);

	List<BSubscribe> queryBorrowSubscribesByBorrowId(@Param("borrowId") Integer borrowId, Page page);

	int queryCountBorrowSubscribesByBorrowId(@Param("borrowId") Integer borrowId);

	int queryCountTransferinfosByBorrowId(@Param("borrowId") Integer borrowId, @Param("status") Integer status);

	List<BsubscribeVo> queryTransferinfosByBorrowId(@Param("borrowId") Integer borrowId, @Param("status") Integer status, Page page);

	String querySumAccountByUserId(TransferCnd transferCnd);

	public List<BsubscribeVo> querySubscribesListByTransferId(@Param("transferId") Integer transferId);

	public BigDecimal querySubscribesSumByTransferId(@Param("transferId") Integer transferId);

	/**
	 * 
	 * <p>
	 * Description:根据userId查询认购债权记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月25日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer querySubscribesCountByUserId(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询userId和transferId查询该用户所认购的最后一笔债权认购记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param transferId
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 *             BsubscribeVo
	 */
	public BsubscribeVo querySubscribesLastByUserIdAndTransferId(@Param("transferId") Integer transferId, @Param("userId") Integer userId,
			@Param("account") BigDecimal account) throws Exception;
}
