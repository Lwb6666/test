package com.dxjr.portal.account.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.statics.BusinessConstants;

/**
 * <p>
 * Description:投标控制类,非https请求<br />
 * </p>
 * 
 * @title ToubiaoHttpController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2015年2月2日
 */
@Controller
@RequestMapping
public class ToubiaoHttpController extends BaseController {

	public Logger logger = Logger.getLogger(ToubiaoHttpController.class);

	@Autowired
	private InvestRecordService investRecordService;

	/**
	 * <p>
	 * Description:我要投标列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "queryInvestList")
	public ModelAndView queryInvestList(@RequestParam Integer pageNum, @ModelAttribute SearchInvestCnd seach) {
		ModelAndView mv = new ModelAndView("borrow/borrowGridByPage");

		System.out.println(seach.getRepayType());
		// String pageSize = "10";
		Page p = null;
		try {

			if (!"3".equals(seach.getBorrowType())) {
				seach.setExcludeJinzhi(1);// 排除净值
			}

			p = investRecordService.findInvest(seach, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));


			// }
		} catch (Exception e) {
			logger.error("进入我要投标出错");
			e.printStackTrace();
		}
		mv.addObject("page", p);
		mv.addObject("isDeault", seach.getIsdefult());
		return mv;
	}
	/**
	 * 
	 * <p>
	 * Description:我要投标列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年1月11日
	 * @param pageNum
	 * @param seach
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "queryInvestList1")
	public ModelAndView queryInvestList1(@RequestParam Integer pageNum, @ModelAttribute SearchInvestCnd seach) {
		ModelAndView mv = new ModelAndView("borrow/borrowGridByPage1");
		Page p = null;
		try {

			if (!"3".equals(seach.getBorrowType())) {
				seach.setExcludeJinzhi(1);// 排除净值
			}

			p = investRecordService.findInvest(seach, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));


			// }
		} catch (Exception e) {
			logger.error("进入我要投标出错");
			e.printStackTrace();
		}
		mv.addObject("page", p);
		mv.addObject("isDeault", seach.getIsdefult());
		return mv;
	}
	/**
	 * 
	 * <p>
	 * Description:我要投标列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年1月11日
	 * @param pageNum
	 * @param seach
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "queryInvestList2")
	public ModelAndView queryInvestList2(@RequestParam Integer pageNum, @ModelAttribute SearchInvestCnd seach) {
		ModelAndView mv = new ModelAndView("borrow/borrowGridByPage1");
		Page p = null;
		try {

			if (!"3".equals(seach.getBorrowType())) {
				seach.setExcludeJinzhi(1);// 排除净值
			}

			p = investRecordService.findInvest1(seach, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));


			// }
		} catch (Exception e) {
			logger.error("进入我要投标出错");
			e.printStackTrace();
		}
		mv.addObject("page", p);
		mv.addObject("isDeault", seach.getIsdefult());
		return mv;
	}
}
