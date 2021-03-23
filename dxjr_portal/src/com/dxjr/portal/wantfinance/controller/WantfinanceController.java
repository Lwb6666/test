package com.dxjr.portal.wantfinance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/newdxjr/financing/wantfinance")
public class WantfinanceController {

	@RequestMapping("toWantfinance")
	public ModelAndView toWantfinance() {
		ModelAndView mv = new ModelAndView(
				"/financing/wantfinance/wantfinance");

		return mv;
	}
}
