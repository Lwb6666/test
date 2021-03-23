package com.dxjr.portal.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.member.mapper.VipLevelMapper;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.VipLevelVo;
import com.dxjr.portal.statics.BusinessConstants;

/**
 * 
 * <p>
 * Description:VIP会员等级业务处理方法<br />
 * </p>
 * 
 * @title VipLevelServiceImpl.java
 * @package com.dxjr.portal.member.service.impl
 * @author yangshijin
 * @version 0.1 2015年1月14日
 */
@Service
public class VipLevelServiceImpl implements VipLevelService {

	@Autowired
	private VipLevelMapper vipLevelMapper;

	@Override
	public boolean getIsSvipByUserId(int userId) throws Exception {
		VipLevelVo vipLevelVo = queryByUserIdAndType(userId, BusinessConstants.VIP_LEVEL_TWO.intValue());
		// 判断该用户是否是终身顶级会员
		if (vipLevelVo != null && vipLevelVo.getStatus().intValue() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public VipLevelVo queryById(Integer id) throws Exception {
		return vipLevelMapper.queryById(id);
	}

	@Override
	public VipLevelVo queryByIdForUpdate(Integer id) throws Exception {
		return vipLevelMapper.queryByIdForUpdate(id);
	}

	@Override
	public VipLevelVo queryByUserIdAndType(Integer userId, Integer type) throws Exception {
		return vipLevelMapper.queryByUserIdAndType(userId, type);
	}

}
