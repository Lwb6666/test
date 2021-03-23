package com.dxjr.portal.first.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;

/**
 * <p>
 * Description:优先投标前台Controller,非httts请求<br />
 * </p>
 * 
 * @title FirstBorrowHttpController.java
 * @package com.dxjr.portal.first.controller
 * @author justin.xu
 * @version 0.1 2015年2月2日
 */
@Controller
@RequestMapping
public class FirstBorrowHttpController extends BaseController {

	private final static Logger logger = Logger.getLogger(FirstBorrowHttpController.class);
	@Autowired
	private FirstBorrowService firstBorrowService;

	/**
	 * 
	 * <p>
	 * Description:查询直通车专区列表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月19日
	 * @param request
	 * @param response
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchFirstList")
	public ModelAndView searchFirstList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("first/firstInvestList-inner");
		Date now = new Date();
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		String type = request.getParameter("type");
		firstBorrowCnd.setType(type);
		String orderParam = request.getParameter("orderParam");
		String orderType = request.getParameter("orderType");

		StringBuffer orderSql = new StringBuffer("");
		if (orderParam != null && orderType != null) {
			if (orderParam.equals("periods")) { // 按期数排序
				orderSql.append(" ORDER BY cast(f.periods as signed) ");
			} else if (orderParam.equals("progress")) { // 按进度排序
				orderSql.append(" ORDER BY CASE WHEN f.status = 3 then f.account_yes/f.PLAN_ACCOUNT when f.status = 5 then f.REAL_ACCOUNT /f.PLAN_ACCOUNT end ");
			}
			if (!orderParam.equals("") && orderType.equals("desc")) { // 按降序排序
				orderSql.append(orderType);
			} else if (!orderParam.equals("") && orderType.equals("asc")) { // 按升序排序
				orderSql.append(orderType);
			}
		}
		firstBorrowCnd.setOrderSql(orderSql.toString());
		int pageNo = 1;
		int pageSize = 10;
		String pageNoStr = request.getParameter("pageNo");
		String pageSizeStr = request.getParameter("pageSize");
		if (pageNoStr != null && !pageNoStr.equals("")) {
			pageNo = Integer.parseInt(pageNoStr);
		}
		if (pageSizeStr != null && !pageSizeStr.equals("")) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		mv.addObject("type", type);
		mv.addObject("orderParam", orderParam);
		mv.addObject("orderType", orderType);
		mv.addObject("pageNo", pageNo);
		mv.addObject("pageSize", pageSize);
		mv.addObject("nowTime", now);
		try {
			Page page = firstBorrowService.queryFirstListByCnd(firstBorrowCnd, pageNo, pageSize);
			mv.addObject("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

}
