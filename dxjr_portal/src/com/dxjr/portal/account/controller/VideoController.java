package com.dxjr.portal.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:我的帐号控制类<br />
 * </p>
 * 
 * @title MyAccountController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
@Controller
@RequestMapping(value = "/video")
public class VideoController extends BaseController {

	public Logger logger = Logger.getLogger(VideoController.class);

	/**
	 * 
	 * <p>
	 * Description:转向视频广告界面<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年9月16日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showVideoAD")
	public ModelAndView toShowVideoAD(HttpServletRequest request) {

		return forword("/index/videoAD");
	}

}
