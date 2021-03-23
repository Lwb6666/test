package com.dxjr.portal.helpcenter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:帮助中心控制器<br />
 * </p>
 * @title GuaranteedController.java
 * @package com.dxjr.portal.account.controller
 * @author hujianpan
 * @version 0.1 2014年8月13日
 */
@Controller
@RequestMapping(value = "/bangzhu")
public class HelpCenterController extends BaseController {

	public Logger logger = Logger.getLogger(HelpCenterController.class);

	@RequestMapping
	public ModelAndView toHelpCenterIndex() {
		return forword("/helpcenter/helpindex");
	}

	@RequestMapping("/{jsp}")
	public ModelAndView toHelpJsp(@PathVariable String jsp) {
		String returnPage = "";
		if (null != jsp && !"".equals(jsp.trim())) {
			returnPage = HelpCenterController.getJspPageByAction(jsp);
		}
		return forword("/helpcenter/" + returnPage);
	}

	/**
	 * <p>
	 * Description:帮助中心页面跳转controller<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月28日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/show")
	public ModelAndView showHelpInfo(HttpServletRequest request) {
		/** 要前往的页面 */
		String pageName = request.getParameter("pageName");
		/** 帮助中心左侧导航条当前选中的一级和二级导航菜单Id */
		String newparentId = (null == request.getParameter("newparentId") ? "" : request.getParameter("newparentId"));
		String newId = (null == request.getParameter("newId") ? "" : request.getParameter("newId"));
		/** 帮助中心左侧导航条上一次选中的一级和二级导航菜单Id */
		String oldparentId = (null == request.getParameter("oldparentId") ? "" : request.getParameter("oldparentId"));
		String oldId = (null == request.getParameter("oldId") ? "" : request.getParameter("oldId"));

		ModelAndView mav = forword("helpcenter/" + pageName);
		return mav.addObject("newId", newId).addObject("oldId", oldId).addObject("newparentId", newparentId).addObject("oldparentId", oldparentId);
	}

	/**
	 * 路径对应的JSP页面地址
	 */
	private static Map<String, String> jspPageMap = new HashMap<String, String>();
	static {
		jspPageMap.put("daohang", "newcomer/index");
		jspPageMap.put("1", "newcomer/knowgc");
		jspPageMap.put("2", "newcomer/reglogin");
		jspPageMap.put("3", "newcomer/recharge");
		jspPageMap.put("4", "newcomer/bid");
		jspPageMap.put("5", "newcomer/fee");
		jspPageMap.put("touzi", "bid/index");
		jspPageMap.put("6", "bid/new");
		jspPageMap.put("7", "bid/product");
		jspPageMap.put("8", "bid/fee");
		jspPageMap.put("9", "bid/direct");
		jspPageMap.put("10", "bid/official");
		jspPageMap.put("11", "bid/transfer");
		jspPageMap.put("jiekuan", "borrow/index");
		jspPageMap.put("12", "borrow/product");
		jspPageMap.put("13", "borrow/apply");
		jspPageMap.put("14", "borrow/doc");
		jspPageMap.put("15", "borrow/approve");
		jspPageMap.put("16", "borrow/level");
		jspPageMap.put("17", "borrow/collection");
		jspPageMap.put("18", "borrow/repay");
		jspPageMap.put("zhanghu", "account/index");
		jspPageMap.put("19", "account/pwd");
		jspPageMap.put("20", "account/withdraw");
		jspPageMap.put("21", "account/safe");
		jspPageMap.put("22", "bid/firstborrow");
		jspPageMap.put("jieshi", "words/index");
		jspPageMap.put("qita", "others/index");
		// 活期宝
		jspPageMap.put("23", "curAccount/curAccountHelper");
		jspPageMap.put("24", "fix/fixAccountHelper");
		// 我要融资
		jspPageMap.put("25", "borrow/financing");
		jspPageMap.put("26", "account/sycee");
		//关于资金存管
		jspPageMap.put("27", "account/zjcg");
	}

	/**
	 * <p>
	 * Description:根据请求路径不同返回页面地址<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2015年2月1日
	 * @param jsp
	 * @return String
	 */
	private static String getJspPageByAction(String jsp) {
		String returnPage = "";
		if (jspPageMap.containsKey(jsp)) {
			returnPage = jspPageMap.get(jsp);
		}
		return returnPage;
	}

}
