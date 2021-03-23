package com.dxjr.portal.index.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.FeedbackInfo;
import com.dxjr.base.entity.Member;
import com.dxjr.base.mapper.BaseFeedbackInfoMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.portal.index.service.IndexService;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;

@Service
public class IndexServiceImpl implements IndexService {
	public Logger logger = Logger.getLogger(IndexServiceImpl.class);

	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BaseFeedbackInfoMapper baseFeedbackInfoMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private RealNameApproService realNameApproService;

	@Override
	public String savefeedback(String mobileNum) throws Exception {
		String result = "success";
		mobileNum = mobileNum.trim();
		FeedbackInfo feedbackInfo = new FeedbackInfo();
		feedbackInfo.setStatus(0);
		feedbackInfo.setMobileNum(mobileNum);
		feedbackInfo.setAddTime(new Date());
		feedbackInfo.setVersion(1);

		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setMobileNum(mobileNum);
		int count = mobileApproMapper.queryMobileApproCount(mobileApproCnd);
		if (count == 1) {
			List<MobileApproVo> mobileApproVoList = mobileApproMapper.queryMobileApproList(mobileApproCnd);
			if (mobileApproVoList.size() > 0) {
				MobileApproVo mobileApproVo = mobileApproVoList.get(0);
				if (mobileApproVo != null) {
					Member member = baseMemberMapper.queryById(mobileApproVo.getUserId());
					if (member != null) {
						feedbackInfo.setUserName(member.getUsername());
					}
					RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(mobileApproVo.getUserId());
					if (realNameApproVo != null) {
						feedbackInfo.setRealName(realNameApproVo.getRealName());
					}
				}
			}
		}
		baseFeedbackInfoMapper.insertEntity(feedbackInfo);
		return result;
	}

}
