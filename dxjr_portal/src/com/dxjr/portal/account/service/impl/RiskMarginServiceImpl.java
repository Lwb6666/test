package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.RiskMarginMapper;
import com.dxjr.portal.account.service.RiskMarginService;
import com.dxjr.portal.account.vo.RiskMarginVo;

/**
 * @title RiskMarginServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author chenlu
 * @version 0.1 2014年8月6日
 */
@Service
public class RiskMarginServiceImpl implements RiskMarginService {

	@Autowired
	private RiskMarginMapper riskMarginMapper;

	@Override
	public BigDecimal queryRiskMargin() throws Exception {
		List<RiskMarginVo> list = riskMarginMapper.queryRiskMargin();
		if (null != list && list.size() == 1) {
			return list.get(0).getAccount();
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryYestDeal() {
		return riskMarginMapper.queryYestDeal();
	}

}
