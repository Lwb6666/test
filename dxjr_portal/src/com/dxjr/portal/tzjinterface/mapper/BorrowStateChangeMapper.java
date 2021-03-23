package com.dxjr.portal.tzjinterface.mapper;

import java.util.List;

import com.dxjr.portal.tzjinterface.vo.BorrowStateVo;
import com.dxjr.utils.exception.AppException;

public interface BorrowStateChangeMapper {
	/**
	 * 
	 * <p>
	 * Description:查询TJZ用户所投标状态变化记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @return
	 * @throws AppException
	 * List<BorrowTransferLogVo>
	 */
	public List<BorrowStateVo> queryStateChangeBorrowList() throws AppException;
	

}
