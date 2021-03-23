package com.dxjr.portal.outerInterface.mapper;

import com.dxjr.portal.outerInterface.entity.WdtyMemberBinding;
import com.dxjr.portal.outerInterface.vo.WdtyParamCnd;
import com.dxjr.utils.exception.AppException;

/**
 * @author WangQianJin
 * @title 网贷之家Mapper接口
 * @date 2015年12月15日
 */
public interface WDTYInterfaceMapper {

	/**
	 * 添加网贷信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int insertWdtyMemberBinding(WdtyMemberBinding wdtyMemberBinding) throws AppException;
	
	/**
	 * 根据条件查询网贷信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding queryMemberBindingInfo(WdtyParamCnd parameteVo) throws AppException;
	
	/**
	 * 修改网贷天眼信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateMemberBindingInfo(WdtyMemberBinding wdtyMemberBinding) throws AppException;
	
	/**
	 * 根据Key查询网贷信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding queryMemberBindingInfoByKey(WdtyParamCnd parameteVo) throws AppException;	
	
	/**
	 * 根据ID修改loginKey
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateLoginKeyById(WdtyMemberBinding wdtyMemberBinding) throws AppException;
}
