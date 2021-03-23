package com.dxjr.portal.globle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

@Controller
public class GlobleController extends BaseController {

	/**
	 * <p>
	 * Description:返回主页<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月9日
	 * @return ModelAndView
	 */
	@RequestMapping("/global/forindex")
	public ModelAndView forindex() {
		return new ModelAndView("/islogin");
	}

	@RequestMapping("/zifei")
	public ModelAndView forInvestTariffInfo() {
		return new ModelAndView("/investment/investTariffInfo");
	}

	@RequestMapping("/global/investment/borrowTariff")
	public ModelAndView forBorrowTariff() {
		return new ModelAndView("/borrow/borrowTariff");
	}

	@RequestMapping(value = "/global/borrow/bidProduct")
	public ModelAndView bidProduct() {
		return forword("/borrow/bidProduct");
	}

	@RequestMapping(value = "/global/borrow/borrowProduct")
	public ModelAndView borrowProduct() {
		return forword("borrow/borrowProduct");
	}

	@RequestMapping(value = "/jianjie")
	public ModelAndView company() {
		return forword("index/company");
	}

	@RequestMapping(value = "/jianjie/course")
	public ModelAndView course() {
		return forword("index/developCourse");
	}

	@RequestMapping(value = "/jianjie/tuandui")
	public ModelAndView team() {
		return forword("index/team");
	}

	@RequestMapping(value = "/jianjie/lianxi")
	public ModelAndView contact() {
		return forword("index/contact");
	}

	@RequestMapping(value = "/jianjie/zhaopin")
	public ModelAndView recruitment() {
		return forword("index/recruitment");
	}

	@RequestMapping(value = "/jianjie/huodong")
	public ModelAndView teamactivity() {
		return forword("index/teamactivity");
	}

	@RequestMapping(value = "/indexto/{pageName}")
	public ModelAndView toIndexPages(@PathVariable String pageName) {
		return forword("index/" + pageName);
	}

	@RequestMapping(value = "/baidu_verify_{code}", produces = "text/html")
	@ResponseBody
	public String baidu_verify(@PathVariable("code") String code) {
		return code;
	}
}
