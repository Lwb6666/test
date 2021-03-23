package com.dxjr.portal.borrow.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.entity.Interest;
import com.dxjr.portal.borrow.service.CalculatorService;
import com.dxjr.portal.statics.CalculatorUtil;
import com.itextpdf.awt.geom.gl.Crossing.CubicCurve;

/**
 * 
 * <p>
 * Description:计算器<br />
 * </p>
 * 
 * @title CalculatorController.java
 * @package com.dxjr.portal.borrow.controller
 * @author yangshijin
 * @version 0.1 2014年8月15日
 */
@Controller
@RequestMapping(value = "/borrow/calculator")
public class CalculatorController extends BaseController{

	@Autowired
	private CalculatorService calculatorService;

	/**
	 * 
	 * <p>
	 * Description:进入计算器页面<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCounter")
	public ModelAndView toCounter() {
		String flag=currentRequest().getParameter("flag");
		ModelAndView mv = new ModelAndView("financing/calculator/counter");
		if(flag!=null){
			mv.addObject("flag", flag);
		}
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:理财计算器<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/interestCal")
	public ModelAndView interestCal(Interest interest) {
		ModelAndView mv = new ModelAndView("financing/calculator/counter");
		List<Map<String, BigDecimal>> datalist = new ArrayList<Map<String, BigDecimal>>();
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DEBXHK) {// 等额本息还款
			datalist = calculatorService.debxhkMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_AYFXDQHB) {// 按月付息到期还本
			datalist = calculatorService.ayfxdqhbMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DQHBFX) {// 到期还本付息
			calculatorService.dqhbfxMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {// 按天借款
			calculatorService.dqhbfxDay(interest);
		}
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {
			mv.addObject("rateP",
					calculatorService.getRatePercenageDay(interest));
		} else {
			mv.addObject("rateP",
					calculatorService.getRatePercenageMonth(interest));
		}
		mv.addObject("datalist", datalist);
		mv.addObject("interest", interest);
		mv.addObject("style", 1);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:成本计算器<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/costCal")
	public ModelAndView costCal(Interest interest) {
		ModelAndView mv = new ModelAndView("financing/calculator/counter");
		mv.addObject("money", interest.getMoney());
		List<Map<String, BigDecimal>> datalist = new ArrayList<Map<String, BigDecimal>>();
		BigDecimal manageFee = new BigDecimal("0");
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DEBXHK) {// 等额本息还款
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			datalist = calculatorService.debxhkMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_AYFXDQHB) {// 按月付息到期还本
			manageFee = CalculatorUtil.getManageFeeAyfxdqhbMonth(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			datalist = calculatorService.ayfxdqhbMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DQHBFX) {// 到期还本付息
			manageFee = CalculatorUtil.getManageFeeDqhbfxMonth(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			calculatorService.dqhbfxMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {// 按天借款
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			calculatorService.dqhbfxDay(interest);
		}
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {
			mv.addObject("rateP",
					calculatorService.getRatePercenageDay(interest));
		} else {
			mv.addObject("rateP",
					calculatorService.getRatePercenageMonth(interest));
		}
		mv.addObject("manageFee", manageFee.setScale(
				Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_EVEN));
		mv.addObject("datalist", datalist);
		mv.addObject("interest", interest);
		mv.addObject("style", 2);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:成本计算器（老版本）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月25日
	 * @param interest
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/costCalOld")
	public ModelAndView costCalOld(Interest interest) {
		ModelAndView mv = new ModelAndView("financing/calculator/counter");
		mv.addObject("money", interest.getMoney());
		List<Map<String, BigDecimal>> datalist = new ArrayList<Map<String, BigDecimal>>();
		BigDecimal manageFee = new BigDecimal("0");
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DEBXHK) {// 等额本息还款
			manageFee = CalculatorUtil.getManageFeeDebxhkMonth(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			datalist = calculatorService.debxhkMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_AYFXDQHB) {// 按月付息到期还本
			manageFee = CalculatorUtil.getManageFeeAyfxdqhbMonth(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			datalist = calculatorService.ayfxdqhbMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_MONTH
				&& interest.getStyle() == Constants.CALCULATOR_STYLE_DQHBFX) {// 到期还本付息
			manageFee = CalculatorUtil.getManageFeeDqhbfxMonth(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			calculatorService.dqhbfxMonth(interest);
		} else if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {// 按天借款
			manageFee = CalculatorUtil.getManageFeeDqhbfxDay(
					interest.getMoney(), interest.getPeriod());
			interest.setMoney(interest
					.getMoney()
					.subtract(manageFee)
					.setScale(Constants.CALCULATOR_MONEY_NUM,
							BigDecimal.ROUND_HALF_EVEN));
			calculatorService.dqhbfxDay(interest);
		}
		if (interest.getCategory() == Constants.CALCULATOR_CATEGORY_DAY) {
			mv.addObject("rateP",
					calculatorService.getRatePercenageDay(interest));
		} else {
			mv.addObject("rateP",
					calculatorService.getRatePercenageMonth(interest));
		}
		mv.addObject("manageFee", manageFee.setScale(
				Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_EVEN));
		mv.addObject("datalist", datalist);
		mv.addObject("interest", interest);
		mv.addObject("style", 2);
		return mv;
	}
}
