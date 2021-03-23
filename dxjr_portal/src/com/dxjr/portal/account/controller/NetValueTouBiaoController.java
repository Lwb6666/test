package com.dxjr.portal.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:净值标类<br />
 * 
 * @package com.dxjr.portal.account.controller
 * @author 邹理享
 * @version 0.1 2015年4月13日
 */
@Controller
@RequestMapping(value = "/jingzhibiao")
public class NetValueTouBiaoController extends BaseController {

	public Logger logger = Logger.getLogger(NetValueTouBiaoController.class);


	@RequestMapping
	public ModelAndView myInvest(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/jingzhilist");
		request.setAttribute("borrowT", 3);
		return mv;
	}



}
