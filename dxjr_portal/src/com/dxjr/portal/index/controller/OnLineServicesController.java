package com.dxjr.portal.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

@Controller
@RequestMapping(value = "/onLineServices")
public class OnLineServicesController  extends BaseController{

	private final static Logger logger = Logger.getLogger(OnLineServicesController.class);
	@RequestMapping(value = "/webQQ")
	public ModelAndView queryIsLogin(HttpServletRequest request) {

		return  new ModelAndView("/biz_qq/biz_qq_wpa");
	}
	
}
