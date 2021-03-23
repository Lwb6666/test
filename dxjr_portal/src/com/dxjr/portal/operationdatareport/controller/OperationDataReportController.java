package com.dxjr.portal.operationdatareport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/operationdatareport")
public class OperationDataReportController {

	@RequestMapping(value = "/report")
	public ModelAndView toOperationDataReport() {
		ModelAndView mav = new ModelAndView("/operationdatareport/october");
		return mav;
	}

}
