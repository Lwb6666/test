package com.dxjr.portal.helpcenter.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:我要融资帮助Controller<br />
 * </p>
 * 
 * @title RongziHelpController.java
 * @package com.dxjr.portal.helpcenter.controller
 * @author justin.xu
 * @version 0.1 2015年2月1日
 */
@Controller
@RequestMapping
public class RongziHelpController extends BaseController {

	public Logger logger = Logger.getLogger(RongziHelpController.class);

	/**
	 * <p>
	 * Description:诚薪贷<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月1日
	 * @return ModelAndView
	 */
	@RequestMapping("/chengxindai")
	public ModelAndView toChengxindai() {
		return forword("helpcenter/aboutBorrow/chengxindai");
	}

	/**
	 * 
	 * <p>
	 * Description:诚商贷<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月1日
	 * @return ModelAndView
	 */
	@RequestMapping("/chengshangdai")
	public ModelAndView toChengshangdai() {
		return forword("helpcenter/aboutBorrow/chengshangdai");
	}

	/**
	 * 
	 * <p>
	 * Description:净值贷<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月1日
	 * @return ModelAndView
	 */
	@RequestMapping("/jingzhidai")
	public ModelAndView toJingzhidai() {
		return forword("helpcenter/aboutBorrow/jingzhidai");
	}
}
