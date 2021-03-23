package com.dxjr.portal.stock.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.stock.service.StockService;
import com.dxjr.utils.HttpTookit;

@Controller
@RequestMapping(value = "/stock")
public class StockController extends BaseController {

	private static final Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	/**
	 * 获取我的期权信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "myStock")
	@RequiresAuthentication
	public ModelAndView myStock() {
		ModelAndView mv = null;
		try {
			mv = stockService.myStockInfo(currentUser());
		} catch (Exception e) {
			mv = new ModelAndView("/account/stock/myStock");
			logger.error("获取期权信息异常", e);
		}
		return mv;
	}

	/**
	 * 现金行权
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping(value = "exerciseStock")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox exerciseStock(HttpServletRequest request, @RequestParam String payPwd) {
		try {
			return stockService.updateStockMoney(payPwd, HttpTookit.getRealIpAddr(request), currentUser());
		} catch (Exception e) {
			logger.error("现金行权异常", e);
			return MessageBox.build("0", "现金行权异常");
		}
	}
}
