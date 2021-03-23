package com.dxjr.portal.borrow.mapper;

import com.dxjr.portal.borrow.entity.BorrowBusiness;
import com.dxjr.portal.borrow.vo.BorrowBusinessVo;

public interface BorrowBusinessMapper {

	/**
	 * 
	 * <p>
	 * Description:新增<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月26日
	 * @param business
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer insertBorrowBusiness(BorrowBusiness business) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月26日
	 * @param business
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer updateBorrowBusiness(BorrowBusiness business) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月26日
	 * @param id
	 * @return
	 * @throws Exception
	 *             BorrowBusinessVo
	 */
	public BorrowBusinessVo selectBorrowBusinessById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据borrowId查询<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月26日
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             BorrowBusinessVo
	 */
	public BorrowBusinessVo selectBorBusByBorrowIdForUpdate(Integer borrowId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据borrowId查询<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月26日
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             BorrowBusinessVo
	 */
	public BorrowBusinessVo selectBorBusByBorrowId(Integer borrowId) throws Exception;
}