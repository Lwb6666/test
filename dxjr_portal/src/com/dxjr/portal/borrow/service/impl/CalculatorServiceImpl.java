package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.borrow.entity.Interest;
import com.dxjr.portal.borrow.service.CalculatorService;
import com.dxjr.portal.statics.CalculatorUtil;

@Service
public class CalculatorServiceImpl implements CalculatorService {

	@Override
	public List<Map<String, BigDecimal>> debxhkMonth(Interest interest) {
		interest.setRateP(CalculatorUtil.getRatePercenageMonth(interest.getRate()));
		interest.setRateP(new BigDecimal(CalculatorUtil.subZeroAndDot(interest.getRateP().multiply(new BigDecimal("100")).toString())));

		interest.setMoneyInterestP(CalculatorUtil.getCostInterestPercenageMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		interest.setInterest(CalculatorUtil.getInterestDebxhkMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		List<Map<String, BigDecimal>> datalist = CalculatorUtil.getPeriodPercenageDebxhkListMonth(interest.getMoney(), interest.getRate(), interest.getPeriod());

		return datalist;
	}

	@Override
	public List<Map<String, BigDecimal>> ayfxdqhbMonth(Interest interest) {
		interest.setRateP(CalculatorUtil.getRatePercenageMonth(interest.getRate()));
		interest.setInterest(CalculatorUtil.getInterestTotaslAyfxdqhbMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		List<Map<String, BigDecimal>> datalist = CalculatorUtil.getPeriodPercenageAyfxdqhbListMonth(interest.getMoney(), interest.getRate(), interest.getPeriod());
		interest.setRateP(new BigDecimal(CalculatorUtil.subZeroAndDot(interest.getRateP().multiply(new BigDecimal("100")).toString())));
		return datalist;
	}

	@Override
	public void dqhbfxMonth(Interest interest) {
		interest.setRateP(CalculatorUtil.getRatePercenageMonth(interest.getRate()));
		interest.setInterest(CalculatorUtil.getInterestTotaslDqhbfxMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
	}

	@Override
	public void dqhbfxDay(Interest interest) {
		interest.setRateP(CalculatorUtil.getRatePercenageDay(interest.getRate()));
		interest.setInterest(CalculatorUtil.getInterestTotaslDqhbfxDay(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		interest.setCategory(Constants.CALCULATOR_CATEGORY_DAY);
		interest.setStyle(Constants.CALCULATOR_STYLE_DQHBFX);
	}

	@Override
	public BigDecimal getRatePercenageMonth(Interest interest) {
		return CalculatorUtil.getRatePercenageMonth(interest.getRate());
	}

	@Override
	public BigDecimal getRatePercenageDay(Interest interest) {
		return CalculatorUtil.getRatePercenageDay(interest.getRate());
	}

	@Override
	public BigDecimal getInterest(Interest interest) {
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_DEBXHK) {// 等额本息还款
			interest.setInterest(CalculatorUtil.getInterestDebxhkMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_AYFXDQHB) {// 按月付息到期还本
			interest.setInterest(CalculatorUtil.getInterestTotaslAyfxdqhbMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_DQHBFX) {// 到期还本付息
			interest.setInterest(CalculatorUtil.getInterestTotaslDqhbfxMonth(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {// 按天借款
			interest.setInterest(CalculatorUtil.getInterestTotaslDqhbfxDay(interest.getMoney(), interest.getRate(), interest.getPeriod()));
		}
		return interest.getInterest();
	}
}
