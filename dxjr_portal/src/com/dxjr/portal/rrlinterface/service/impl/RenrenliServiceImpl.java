package com.dxjr.portal.rrlinterface.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.rrlinterface.mapper.RenrenliMapper;
import com.dxjr.portal.rrlinterface.service.RenrenliService;
import com.dxjr.portal.rrlinterface.vo.RrlCnd;
import com.dxjr.portal.rrlinterface.vo.RrlMemberBinding;
import com.dxjr.portal.rrlinterface.vo.RrlTenderRecord;
import com.dxjr.utils.exception.AppException;

/**
 * @author WangQianJin
 * @title 人人利服务实现类
 * @date 2016年4月23日
 */
@Service
public class RenrenliServiceImpl implements RenrenliService{
	
	@Autowired
	private RenrenliMapper renrenliMapper;

	/**
	 * 添加绑定信息
	 * @author WangQianJin
	 * @title @param rrlMemberBinding
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer insertRrlMemberBinding(RrlMemberBinding rrlMemberBinding) throws AppException{
		return renrenliMapper.insertRrlMemberBinding(rrlMemberBinding);
	}
	
	/**
	 * 查询人人利用户数量
	 * @author WangQianJin
	 * @title @param rrlCnd
	 * @title @return
	 * @date 2016年4月23日
	 */
	public Integer queryRrlMemberCount(RrlCnd rrlCnd){
		return renrenliMapper.queryRrlMemberCount(rrlCnd);
	}
	
	/**
	 * 修改人人利用户信息
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer updateRrlMemberBinding(RrlMemberBinding rrlMemberBinding) throws AppException{
		return renrenliMapper.updateRrlMemberBinding(rrlMemberBinding);
	}
	
	/**
	 * 查询人人利投资记录
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月25日
	 */
	public List<RrlTenderRecord> queryTenderRecord(RrlCnd rrlCnd) throws AppException{
		return renrenliMapper.queryTenderRecord(rrlCnd);
	}
}
