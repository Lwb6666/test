package com.dxjr.portal.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.CashRecordMapper;
import com.dxjr.portal.account.mapper.RechargeRecordMapper;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.report.service.CashReportService;

/**
 * <p>
 * Description:当前用户的提现记录导出excel Service<br />
 * </p>
 * 
 * @title CashReportService.java
 * @package com.dxjrweb.report.service
 * @author justin.xu
 * @version 0.1 2014年2月19日
 */
@Service
public class CashReportServiceImpl implements CashReportService {

	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;
	@Autowired
	private CashRecordMapper cashRecordMapper;

	@Override
	public List<RechargeRecordVo> queryRechargeRecordList(
			RechargeRecordCnd rechargeRecordCnd) throws Exception {
		return rechargeRecordMapper.queryRechargeRecordList(rechargeRecordCnd);
	}

	@Override
	public List<CashRecordVo> queryCashRecordList(CashRecordCnd cashRecordCnd)
			throws Exception {
		return cashRecordMapper.queryCashRecordList(cashRecordCnd);
	}

}
