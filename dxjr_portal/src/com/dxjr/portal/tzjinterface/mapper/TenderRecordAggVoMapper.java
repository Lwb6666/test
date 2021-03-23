package com.dxjr.portal.tzjinterface.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.utils.exception.AppException;

public interface TenderRecordAggVoMapper {
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
	public List<TenderRecordAggVo> queryTenderRecordAggList(@Param(value="OId")String OId) throws AppException;
	
}
