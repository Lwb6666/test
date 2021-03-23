package com.dxjr.portal.member.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.member.mapper.StaffMapper;
import com.dxjr.portal.member.service.StaffService;
import com.dxjr.portal.member.vo.StaffCnd;
import com.dxjr.portal.member.vo.StaffVo;

@Service
public class StaffServiceImpl implements StaffService {
	public Logger logger = Logger.getLogger(StaffServiceImpl.class);

	@Autowired
	private StaffMapper staffMapper;

	@Override
	public List<StaffVo> queryStaffList(StaffCnd staffCnd) throws Exception {
		return staffMapper.queryStaffList(staffCnd);
	}

}
