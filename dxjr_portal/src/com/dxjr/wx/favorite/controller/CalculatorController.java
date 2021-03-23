package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.entity.Interest;
import com.dxjr.portal.borrow.service.CalculatorService;

@Controller(value = "wxCalculator")
@RequestMapping(value = "/wx/calculator")
public class CalculatorController extends BaseController {

	public Logger logger = Logger.getLogger(CalculatorController.class);

	@Autowired
	private CalculatorService calculatorService;

	/**
	 * 微信-计算器获取计算结果
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年12月11日
	 * @param interest
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Map<String, Object> calculator(Interest interest) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, BigDecimal>> datalist = new ArrayList<Map<String, BigDecimal>>();
			if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_DEBXHK) {// 等额本息还款
				datalist = calculatorService.debxhkMonth(interest);
			} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_AYFXDQHB) {// 按月付息到期还本
				datalist = calculatorService.ayfxdqhbMonth(interest);
			} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH && interest.getStyle() == Constants.CALCULATOR_STYLE_DQHBFX) {// 到期还本付息
				calculatorService.dqhbfxMonth(interest);
			} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {// 按天借款
				calculatorService.dqhbfxDay(interest);
			}
			if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {
				map.put("rateP", calculatorService.getRatePercenageDay(interest));
			} else {
				map.put("rateP", calculatorService.getRatePercenageMonth(interest));
			}
			map.put("datalist", datalist);
			map.put("interest", interest);
		} catch (Exception e) {
			logger.error("微信-计算器获取计算结果异常", e);
		}
		return map;
	}

}
