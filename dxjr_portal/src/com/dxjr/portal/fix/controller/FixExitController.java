package com.dxjr.portal.fix.controller;


import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.fix.service.*;
import com.dxjr.utils.HttpTookit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Description:定期宝提前退出Controller<br />
 * </p>
 * 
 * @title FixBorrowController.java
 * @package com.dxjr.portal.fix1.controller
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
@Controller
@RequestMapping(value = "/dingqibao/exit")
public class FixExitController extends BaseController {
	private final static Logger logger = Logger.getLogger(FixExitController.class);

	@Autowired
	private FixExitService fixExitService;

	/**
	 *
	 * <p>
	 * Description:查询是否满足提前退出条件并计算手续费及利息<br />
	 * </p>
	 * 
	 * @author wangwanbin
	 * @version 0.1 2016年6月21日
	 * @param fixTenderRealId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/checkExit")
	@ResponseBody
	public MessageBox checkExit(@RequestParam Integer fixTenderRealId) {
		MessageBox box;
		try {
			box = fixExitService.checkExit(fixTenderRealId);
		} catch (Exception e) {
			box = MessageBox.build("0","系统异常");
			logger.error("查询是否满足提前退出条件并计算手续费及利息异常", e);
		}
		return box;
	}

	/**
	 *
	 * <p>
	 * Description:处理申请<br />
	 * </p>
	 *
	 * @author wangwanbin
	 * @version 0.1 2016年6月21日
	 * @param fixTenderRealId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/applyExit")
	@ResponseBody
	public MessageBox applyExit(@RequestParam Integer fixTenderRealId, @RequestParam Integer platform, HttpServletRequest request) {
		MessageBox box;
		try {
			box = fixExitService.applyExit(fixTenderRealId, HttpTookit.getRealIpAddr(request) ,platform);
		} catch (Exception e) {
			box = MessageBox.build("0","系统异常");
			logger.error("查询是否满足提前退出条件并计算手续费及利息异常", e);
		}
		return box;
	}

}
