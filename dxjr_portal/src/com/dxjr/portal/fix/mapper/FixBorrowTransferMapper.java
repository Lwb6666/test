package com.dxjr.portal.fix.mapper;

import java.util.List;

import com.dxjr.base.entity.FixBorrowTransfer;
import com.dxjr.portal.fix.vo.FixBorrowTransferVo;

public interface FixBorrowTransferMapper {
	
	/**
	 * 根据借款标ID查询定期宝转让信息
	 * @author WangQianJin
	 * @title @param borrowId
	 * @title @return
	 * @date 2015年9月15日
	 */
	public List<FixBorrowTransferVo> queryFixBorrowTransferByBorrowId(Integer borrowId);
	
	/**
	 * 根据ID修改定期宝转让信息
	 * @author WangQianJin
	 * @title @param record
	 * @title @return
	 * @date 2015年9月15日
	 */
	public int updateByPrimaryKeySelective(FixBorrowTransfer record);
	
	/**
	 * 根据定期宝ID获取定期宝信息
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2015年9月15日
	 */
	public FixBorrowTransferVo queryFixBorrowTransferById(Integer id);
	
	/**
	 * 新增定期宝转让信息
	 * @author WangQianJin
	 * @title @param record
	 * @title @return
	 * @date 2015年9月15日
	 */
	int insert(FixBorrowTransfer record);

}