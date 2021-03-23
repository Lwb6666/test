package com.dxjr.portal.tzjinterface.mapper;

import com.dxjr.portal.tzjinterface.vo.TransferTenderRecordLogVo;
import com.dxjr.utils.exception.AppException;

public interface TenderRecordTransferLogMapper {
	
	/**
	 * 
	 * <p>
	 * Description:新增一批投标记录流水<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer insertTransferTenderRecordLog(TransferTenderRecordLogVo requesturlLogVo) throws AppException;
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
	public void updateTransferTenderRecordStatus();
}
