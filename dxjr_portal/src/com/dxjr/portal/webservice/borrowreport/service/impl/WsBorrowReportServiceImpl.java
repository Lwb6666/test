package com.dxjr.portal.webservice.borrowreport.service.impl;

import java.math.BigDecimal;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.webservice.borrowreport.service.WsBorrowReportService;

/**
 * <p>
 * Description:借款统计信息业务类实现WebService<br />
 * </p>
 * 
 * @title WsBorrowReportServiceImpl.java
 * @package com.dxjr.portal.webservice.borrowreport.service.impl
 * @author justin.xu
 * @version 0.1 2014年6月26 日
 */
@Service
@WebService
public class WsBorrowReportServiceImpl implements WsBorrowReportService {

	@Autowired
	private BorrowReportService borrowReportService;

	@Override
	public BigDecimal queryNetMoneyLimit(Integer memberId) throws Exception {
		return borrowReportService.queryNetMoneyLimit(memberId);
	}
}
