package com.dxjr.portal.curAccount.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurNoworkDayService;

/**
 * <p>
 * Description:节假日MB <br />
 * </p>
 * 
 * @title CurNoworkDayController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月28日
 */
@Controller
@RequestMapping(value = "/curNoworkDayController")
public class CurNoworkDayController extends BaseController {

	private static final Logger logger = Logger.getLogger(CurNoworkDayController.class);

	@Autowired
	private CurNoworkDayService curNoworkDayService;

	public CurNoworkDayController() {

	}

}
