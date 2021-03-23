package com.dxjr.base.memberaccumulatepoints.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.MemberAccumulatePoints;
import com.dxjr.base.mapper.BaseMemberAccumulatePointsMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.base.memberaccumulatepoints.service.MemberAccumulatePointsService;
import com.dxjr.common.page.Page;

/**
 * <p>
 * Description:用户积分记录实现类<br />
 * </p>
 * @title MemberAccumulatePointsServiceImpl.java
 * @package com.dxjr.base.memberaccumulatepoints.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月17日
 */
@Service
public class MemberAccumulatePointsServiceImpl implements MemberAccumulatePointsService {

	@Autowired
	private BaseMemberAccumulatePointsMapper memberAccumulatePointsMapper;
	@Autowired
	private BaseMemberMapper memberMapper;

	@Override
	public Integer insertMemberAccumulatePoints(MemberAccumulatePoints memberAccumulatePoints) throws Exception {
		Integer insertKey = 0;
		// 更新用户积分
		Member member = memberMapper.queryById(memberAccumulatePoints.getMemberId());
		int gainaccumulatepoints = member.getGainaccumulatepoints() + memberAccumulatePoints.getGainAccumulatePoints();
		int accumulatepoints = member.getAccumulatepoints() + memberAccumulatePoints.getGainAccumulatePoints();
		member.setGainaccumulatepoints(gainaccumulatepoints);
		member.setAccumulatepoints(accumulatepoints);
		memberMapper.updateEntity(member);
		memberAccumulatePointsMapper.insertEntity(memberAccumulatePoints);
		return insertKey;
	}

	public int getLastdaySycee(int userId) {
		return memberAccumulatePointsMapper.getLastdaySycee(userId);
	}

	public Page mySyceeList(int userId, int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setTotalCount(memberAccumulatePointsMapper.mySyceeListCount(userId));
		page.setResult(memberAccumulatePointsMapper.mySyceeList(userId, page));
		return page;
	}
}
