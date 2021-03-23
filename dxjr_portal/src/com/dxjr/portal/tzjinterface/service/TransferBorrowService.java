package com.dxjr.portal.tzjinterface.service;

import java.util.List;

import com.dxjr.portal.tzjinterface.vo.BorrowStateChangedVo;
import com.dxjr.portal.tzjinterface.vo.BorrowStateVo;
import com.dxjr.portal.tzjinterface.vo.BorrowTransferLogVo;
import com.dxjr.portal.tzjinterface.vo.InvitationBorrowVo;
import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.utils.exception.AppException;

public interface TransferBorrowService {
	/**
	 * 
	 * <p>
	 * Description:查询可投标记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<borrowTransferLogMapper>
	 */
	public List<BorrowTransferLogVo> queryBorrowTransferLogs() throws AppException;
	/**
	 * 
	 * <p>
	 * Description:投标数据传输facade<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<BorrowTransferLogVo>
	 */
	public List<InvitationBorrowVo> TransferBorrow() throws AppException;
	/**
	 * 
	 * <p>
	 * Description:查询可投标信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<BorrowTransferLogVo>
	 */
	public List<InvitationBorrowVo> queryBorrowList() throws AppException;
	/**
	 * 
	 * <p>
	 * Description:查询可投标信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<BorrowStateVo>
	 */
	public List<BorrowStateVo> queryStateChangeBorrowList() throws AppException;
	
	/**
	 * 根据查询条件获取投资记录
	 * @author WangQianJin
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<TenderRecordAggVo> queryTenderRecord(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException;
	
	/**
	 * 根据查询条件获取标的状态
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<BorrowStateChangedVo> queryStatusChangedBorrow(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException;
}
