package com.dxjr.portal.scwInterface.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.scwInterface.mapper.ScwMapper;
import com.dxjr.portal.scwInterface.service.ScwService;
import com.dxjr.portal.scwInterface.vo.ScwBorrowVo;
import com.dxjr.portal.scwInterface.vo.ScwCnd;
import com.dxjr.portal.scwInterface.vo.ScwMemberBinding;
import com.dxjr.portal.scwInterface.vo.ScwTenderRecord;
import com.dxjr.utils.exception.AppException;

/**
 * @author WangQianJin
 * @title 生菜网服务实现类
 * @date 2016年4月23日
 */
@Service
public class ScwServiceImpl implements ScwService{
	
	@Autowired
	private ScwMapper wcwMapper;

	/**
	 * 添加绑定信息
	 * @author WangQianJin
	 * @title @param scwMemberBinding
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer insertScwMemberBinding(ScwMemberBinding scwMemberBinding) throws AppException{
		return wcwMapper.insertScwMemberBinding(scwMemberBinding);
	}
	
	/**
	 * 查询生菜网用户数量
	 * @author WangQianJin
	 * @title @param scwCnd
	 * @title @return
	 * @date 2016年4月23日
	 */
	public Integer queryScwMemberCount(ScwCnd scwCnd){
		return wcwMapper.queryScwMemberCount(scwCnd);
	}
	
	/**
	 * 修改生菜网用户信息
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer updateScwMemberBinding(ScwMemberBinding scwMemberBinding) throws AppException{
		return wcwMapper.updateScwMemberBinding(scwMemberBinding);
	}
	
	/**
	 * 查询生菜网投资记录
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月25日
	 */
	public List<ScwTenderRecord> queryTenderRecord(ScwCnd scwCnd) throws AppException{
		return wcwMapper.queryTenderRecord(scwCnd);
	}
	
	/**
	 * 查询生菜网用户信息
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2016年5月6日
	 */
	public ScwMemberBinding queryScwMemberInfo(String phone){
		return wcwMapper.queryScwMemberInfo(phone);
	}
	
	/**
	 * 查询生菜网可投标列表
	 * @author WangQianJin
	 * @title @param scwCnd
	 * @title @return
	 * @date 2016年5月6日
	 */
	public List<ScwBorrowVo> queryBorrowVoList(ScwCnd scwCnd){
		return wcwMapper.queryBorrowVoList(scwCnd);
	}
}
