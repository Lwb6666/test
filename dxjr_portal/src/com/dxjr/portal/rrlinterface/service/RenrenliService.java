package com.dxjr.portal.rrlinterface.service;

import com.dxjr.portal.rrlinterface.vo.RrlCnd;
import com.dxjr.portal.rrlinterface.vo.RrlMemberBinding;
import com.dxjr.portal.rrlinterface.vo.RrlTenderRecord;
import com.dxjr.utils.exception.AppException;

import java.util.List;

/**
 * @author WangQianJin
 * @title 人人利服务类
 * @date 2016年4月23日
 */
public interface RenrenliService {

	/**
	 * 添加绑定信息
	 * @author WangQianJin
	 * @title @param rrlMemberBinding
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer insertRrlMemberBinding(RrlMemberBinding rrlMemberBinding) throws AppException;
	
	/**
	 * 查询人人利用户数量
	 * @author WangQianJin
	 * @title @param rrlCnd
	 * @title @return
	 * @date 2016年4月23日
	 */
	public Integer queryRrlMemberCount(RrlCnd rrlCnd);
	
	/**
	 * 修改人人利用户信息
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月23日
	 */
	public Integer updateRrlMemberBinding(RrlMemberBinding rrlMemberBinding) throws AppException;
	
	/**
	 * 查询人人利投资记录
	 * @author WangQianJin
	 * @title @param memberBindingVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年4月25日
	 */
	public List<RrlTenderRecord> queryTenderRecord(RrlCnd rrlCnd) throws AppException;
}
