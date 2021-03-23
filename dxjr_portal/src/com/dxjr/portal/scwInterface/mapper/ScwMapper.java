package com.dxjr.portal.scwInterface.mapper;

import java.util.List;

import com.dxjr.portal.scwInterface.vo.ScwBorrowVo;
import com.dxjr.portal.scwInterface.vo.ScwCnd;
import com.dxjr.portal.scwInterface.vo.ScwMemberBinding;
import com.dxjr.portal.scwInterface.vo.ScwTenderRecord;
import com.dxjr.utils.exception.AppException;

public interface ScwMapper {

	/**
	 * 添加绑定信息
	 * @author WangQianJin
	 * @title @param scwMemberBinding
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年5月5日
	 */
	public Integer insertScwMemberBinding(ScwMemberBinding scwMemberBinding) throws AppException;
	
	/**
	 * 查询生菜网用户数量
	 * @author WangQianJin
	 * @title @param scwCnd
	 * @title @return
	 * @date 2016年5月5日
	 */
	public Integer queryScwMemberCount(ScwCnd scwCnd);
	
	/**
	 * 修改生菜网用户信息
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年5月5日
	 */
	public Integer updateScwMemberBinding(ScwMemberBinding scwMemberBinding) throws AppException;
	
	/**
	 * 查询生菜网投资记录
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年5月5日
	 */
	public List<ScwTenderRecord> queryTenderRecord(ScwCnd scwCnd) throws AppException;
	
	/**
	 * 查询生菜网用户信息
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2016年5月6日
	 */
	public ScwMemberBinding queryScwMemberInfo(String phone);
	
	/**
	 * 查询生菜网可投标列表
	 * @author WangQianJin
	 * @title @param scwCnd
	 * @title @return
	 * @date 2016年5月6日
	 */
	public List<ScwBorrowVo> queryBorrowVoList(ScwCnd scwCnd);
}
