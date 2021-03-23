package com.dxjr.portal.tzjinterface.mapper;

import java.util.List;

import com.dxjr.portal.tzjinterface.vo.BorrowTransferLogVo;
import com.dxjr.utils.exception.AppException;

public interface BorrowTransferLogMapper {
	/**
	 * 
	 * <p>
	 * Description:查询可投标数据传输日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<BorrowTransferLogVo>
	 */
	public List<BorrowTransferLogVo> queryBorrowTransferLogs() throws AppException;
	/**
	 * 
	 * <p>
	 * Description:生成借款标数据传输流水记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer insertBorrowTransferLogVo() throws AppException;
}
