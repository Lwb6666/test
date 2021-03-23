package com.dxjr.portal.borrow.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.service.BorrowInvestService;
import com.dxjr.portal.borrow.vo.BorrowCnd;

@Controller
@RequestMapping(value = "/borrow/investment")
public class BorrowInvestController {

	@Autowired
	private BorrowInvestService borrowInvestService;

	@RequestMapping(value = "/forborrowlist/{pageSize}")
	public ModelAndView forborrowlist(HttpServletRequest request, @PathVariable int pageSize) {
		ModelAndView view = new ModelAndView("/borrow/borrowlist");
		/*String pageNo_str = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNo_str != null && !pageNo_str.equals("")) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		try {
			BorrowCnd borrowCnd = new BorrowCnd();
			Page page = borrowInvestService.findInverst(borrowCnd, pageNo, pageSize);
			view.addObject("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return view;
	}

	@RequestMapping(value = "/searchborrowlist/{pageSize}")
	public ModelAndView searchborrowlist(HttpServletRequest request, @PathVariable int pageSize) {
		ModelAndView view = new ModelAndView("/borrow/borrowlist-inner");
		String pageNo_str = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNo_str != null && !pageNo_str.equals("")) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		try {
			BorrowCnd borrowCnd = new BorrowCnd();
			String searchType = request.getParameter("searchType");
			borrowCnd.setSearchType(searchType);
			String searchOrderBy = request.getParameter("searchOrderBy");
			borrowCnd.setSearchOrderBy(searchOrderBy);
			String orderByType = request.getParameter("orderByType");
			borrowCnd.setOrderByType(orderByType);
			String name = request.getParameter("search_title");
			borrowCnd.setName(name);
			String username = request.getParameter("search_username");
			borrowCnd.setUserName(username);
			
			Page page = borrowInvestService.findInverst(borrowCnd, pageNo, pageSize);
			view.addObject("page", page);
			view.addObject("searchType", searchType);
			view.addObject("searchOrderBy", searchOrderBy);
			view.addObject("orderByType", orderByType);
			view.addObject("search_title", name);
			view.addObject("search_username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
}
