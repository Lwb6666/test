package com.dxjr.portal.giode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description：新手指南<br />
 * </p>
 * 
 * @title GiodeController.java
 * @package com.dxjr.portal.giode
 * @author 邹理享
 * @version 0.1 2015年1月23日
 */
@Controller
@RequestMapping(value = "/zhinan")
public class GiodeController extends BaseController {

	@RequestMapping
	public ModelAndView forindex() {
		return new ModelAndView("/giode/newnavi");
	}

}
