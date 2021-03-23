package com.dxjr.portal.tzjinterface.mapper;

import java.util.List;

import com.dxjr.portal.tzjinterface.entity.BidInfo;
import com.dxjr.portal.tzjinterface.entity.InvestInfo;
import com.dxjr.portal.tzjinterface.entity.RepayInfo;
import com.dxjr.portal.tzjinterface.vo.BorrowStateChangedVo;
import com.dxjr.portal.tzjinterface.vo.InvitationBorrowVo;
import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.portal.tzjinterface.vo.TzjQueryCnd;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.utils.exception.AppException;

public interface InvitationBorrowMapper {
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
	 * 根据查询条件获取投资记录
	 * @author WangQianJin
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<TenderRecordAggVo>  queryTenderRecord(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException;	
	
	/**
	 * 根据查询条件获取标的状态
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<BorrowStateChangedVo> queryStatusChangedBorrow(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException;
	
	/**
	 * 根据查询条件获取标的信息
	 * @author WangQianJin
	 * @title @param cnd
	 * @title @return
	 * @date 2016年3月28日
	 */
	public List<BidInfo> queryBorrowListByCnd(TzjQueryCnd cnd);

	/**
	 * 根据查询条件获取投资信息
	 * @author WangQianJin
	 * @title @param cnd
	 * @title @return
	 * @date 2016年3月29日
	 */
	public List<InvestInfo> queryTenderRecordListByCnd(TzjQueryCnd cnd);
	
	/**
	 * 根据查询条件获取回款信息
	 * @author WangQianJin
	 * @title @param cnd
	 * @title @return
	 * @date 2016年3月29日
	 */
	public List<RepayInfo> queryRepayInfoListByCnd(TzjQueryCnd cnd);
	
	
}
