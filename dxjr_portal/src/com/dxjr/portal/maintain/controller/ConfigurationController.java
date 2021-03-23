package com.dxjr.portal.maintain.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.Dictionary;
import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:数据配置控制类<br />
 * </p>
 * 
 * @title ConfigurationController.java
 * @package com.dxjr.portal.maintain.controller
 * @author justin.xu
 * @version 0.1 2014年11月12日
 */
@Controller
@RequestMapping(value = "/maintain/conf/")
public class ConfigurationController extends BaseController {

	private final static Logger logger = Logger.getLogger(ConfigurationController.class);

	/**
	 * <p>
	 * Description:清空数据字典，重新加载<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年11月12日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/cleanUp")
	@ResponseBody
	public String cleanUp(HttpServletRequest request) {
		Dictionary.cleanUp();
		return "SUCCESS";
	}
}
