package com.dxjr.portal.tzjinterface.service;

import java.util.List;

import com.dxjr.portal.tzjinterface.vo.MemberBindingVo;
import com.dxjr.portal.tzjinterface.vo.TzjRegisterVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.portal.tzjinterface.vo.TzjUserBindingVo;
import com.dxjr.utils.exception.AppException;

public interface MemberBindingService {
	/**
	 * 
	 * <p>
	 * Description:根据主键查询绑定信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param MemberBindingVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public MemberBindingVo queryMemberBindingInfo(MemberBindingVo memberBindingVo) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:新增一条绑定信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param MemberBindingVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer insertMemberBindingInfo(MemberBindingVo memberBindingVo) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:修改指定的账号绑定信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param MemberBindingVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer updateMemberBindingInfo(MemberBindingVo memberBindingVo) throws AppException;
	/**
	 * 
	 * <p>
	 * Description:删除指定的账户绑定日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param MemberBindingVo
	 * @return
	 * @throws AppException
	 * Integer
	 */
	public Integer deleteMemberBindingInfo(Integer id) throws AppException;
	
	/**
	 * 根据ID获取绑定信息
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2015年11月25日
	 */
	public TzjRegisterVo queryMemberBindingInfoById(Integer id);
	
	/**
	 * 根据查询条件获取用户绑定关系
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @date 2015年11月30日
	 */
	public List<TzjUserBindingVo> queryUserBindingInfoByCnd(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException;
	
	/**
	 * 根据投之家用户名获取平台用户名
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @date 2015年12月4日
	 */
	public String queryMemberNameByTzjName(String username);
	
	/**
	 * 根据用户ID获取绑定数量
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @date 2015年12月14日
	 */
	public Integer queryCountByUserId(Integer userId);

}
