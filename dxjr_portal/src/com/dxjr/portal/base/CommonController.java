package com.dxjr.portal.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController extends BaseController {
	@RequestMapping(value = "/error/{code}")
	public ModelAndView error(String code) {
		return forword("/common/" + code);
	}
	
	@RequestMapping(value = "/message", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageBox message(@RequestParam("code") String code, @RequestParam("message") String message) {
		return MessageBox.build(code, message);
	}
}
