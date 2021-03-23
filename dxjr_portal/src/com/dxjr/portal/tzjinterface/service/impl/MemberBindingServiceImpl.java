package com.dxjr.portal.tzjinterface.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.tzjinterface.mapper.MemberBindingMapper;
import com.dxjr.portal.tzjinterface.service.MemberBindingService;
import com.dxjr.portal.tzjinterface.vo.MemberBindingVo;
import com.dxjr.portal.tzjinterface.vo.TzjRegisterVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.portal.tzjinterface.vo.TzjUserBindingVo;
import com.dxjr.utils.exception.AppException;

@Service
public class MemberBindingServiceImpl implements MemberBindingService {
	
	@Autowired
    private MemberBindingMapper memberBindingMapper;
	@Override
	public MemberBindingVo queryMemberBindingInfo(MemberBindingVo memberBindingVo)
			throws AppException {
		return memberBindingMapper.queryMemberBindingInfo(memberBindingVo);
	}

	@Override
	public Integer insertMemberBindingInfo(MemberBindingVo memberBindingVo)
			throws AppException {
		return memberBindingMapper.insertMemberBindingInfo(memberBindingVo);
	}

	@Override
	public Integer updateMemberBindingInfo(MemberBindingVo memberBindingVo)
			throws AppException {
		return memberBindingMapper.updateMemberBindingInfo(memberBindingVo);
	}

	@Override
	public Integer deleteMemberBindingInfo(Integer id) throws AppException {
		return memberBindingMapper.deleteMemberBindingInfo(id);
	}
	
	/**
	 * 根据ID获取绑定信息
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2015年11月25日
	 */
	@Override
	public TzjRegisterVo queryMemberBindingInfoById(Integer id){
		return memberBindingMapper.queryMemberBindingInfoById(id);
	}
	
	/**
	 * 根据查询条件获取用户绑定关系
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @date 2015年11月30日
	 */
	@Override
	public List<TzjUserBindingVo> queryUserBindingInfoByCnd(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException{
		return memberBindingMapper.queryUserBindingInfoByCnd(tzjTenderRecordCnd);
	}
	
	/**
	 * 根据投之家用户名获取平台用户名
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @date 2015年12月4日
	 */
	public String queryMemberNameByTzjName(String username){
		return memberBindingMapper.queryMemberNameByTzjName(username);
	}
	
	/**
	 * 根据用户ID获取绑定数量
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @date 2015年12月14日
	 */
	public Integer queryCountByUserId(Integer userId){
		return memberBindingMapper.queryCountByUserId(userId);
	}

}
