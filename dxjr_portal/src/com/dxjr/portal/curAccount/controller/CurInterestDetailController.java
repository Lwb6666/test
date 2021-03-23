package com.dxjr.portal.curAccount.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurInterestDetailService;

/****
 * 
 * <p>
 * Description:收益发放日志 <br />
 * </p>
 * 
 * @title CurInterestDetailController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Controller
@RequestMapping(value = "/curInterestDetailController")
public class CurInterestDetailController extends BaseController {

	private static final Logger logger = Logger.getLogger(CurInterestDetailController.class);

	@Autowired
	private CurInterestDetailService curInterestDetailService;

	public CurInterestDetailController() {

	}

}
