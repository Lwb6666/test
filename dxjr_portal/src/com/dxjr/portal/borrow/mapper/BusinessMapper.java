package com.dxjr.portal.borrow.mapper;

import java.util.List;

import com.dxjr.portal.borrow.vo.BusinessCnd;
import com.dxjr.portal.borrow.vo.BusinessVo;


/**
 * @author WangQianJin
 * @title 业务员Mapper
 * @date 2015年8月17日
 */
public interface BusinessMapper {
	
	/**
	 * 根据查询条件获取业务员列表
	 * @author WangQianJin
	 * @title @param businessCnd
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月17日
	 */
	public List<BusinessVo> searchBusinessListByCnd(BusinessCnd businessCnd) throws Exception;
}
