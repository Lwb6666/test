package com.dxjr.portal.fix.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.fix.mapper.FixRepaymentrecordMapper;
import com.dxjr.portal.fix.service.FixRepaymentrecordService;

/**
 * <p>
 * Description:定期宝待还接口实现类<br />
 * </p>
 * 
 * @title FixTenderDetailService.java
 * @package com.dxjr.portal.fix.service
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
@Service
public class FixRepaymentrecordServiceImpl implements FixRepaymentrecordService {

	@Autowired
	private FixRepaymentrecordMapper fixRepaymentrecordMapper;
	
	@Override
	public BigDecimal queryTotalInterest() throws Exception {
		return fixRepaymentrecordMapper.queryTotalInterest();
	}

	@Override
	public BigDecimal querySumCapital() throws Exception {
		return fixRepaymentrecordMapper.querySumCapital();
	}

}
