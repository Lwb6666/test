package com.dxjr.portal.tzjinterface.service;

import java.util.List;

import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.portal.tzjinterface.vo.TransferTenderRecordLogVo;
import com.dxjr.utils.exception.AppException;

public interface TransferTenderRecordService {
	/**
	 * 
	 * <p>
	 * Description:查询当天未传送给投之家的投标记录信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @return TenderRecordAggVo
	 * @throws AppException
	 * Integer
	 */
	public List<TenderRecordAggVo> queryTenderRecordAggList(String OId) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:修改指定的投标记录流水<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param TransferTenderRecordLogVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer updateTransferTenderRecordLog(TransferTenderRecordLogVo requesturlLogVo) throws AppException;

}
