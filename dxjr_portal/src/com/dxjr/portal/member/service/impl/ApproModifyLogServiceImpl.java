package com.dxjr.portal.member.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.service.ApproModifyLogService;
import com.dxjr.portal.member.vo.ApproModifyLog;


@Service
public class ApproModifyLogServiceImpl implements ApproModifyLogService {
	public Logger logger = Logger.getLogger(ApproModifyLogServiceImpl.class);

	@Autowired
	private ApproModifyLogMapper approModifyLogMapper;

	@Override
	public Integer saveApproModifyLog(ApproModifyLog approModifyLog) throws Exception {
		return approModifyLogMapper.insertEntity(approModifyLog);
	}

}
