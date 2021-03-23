package com.dxjr.portal.first.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.first.service.FirstTenderRealService;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:优先投标最终开通控制类<br />
 * </p>
 * 
 * @title FirstTenderRealController.java
 * @package com.dxjr.portal.first.controller
 * @author justin.xu
 * @version 0.1 2014年7月24日
 */
@Controller
@RequestMapping(value = "/first/tenderreal/")
public class FirstTenderRealController extends BaseController {

	private final static Logger logger = Logger.getLogger(FirstTenderRealController.class);

	@Autowired
	private FirstTenderRealService firstTenderRealService;

	/**
	 * 我的帐号左导航-投标直通车列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toIndex")
	public String toFirstBorrowIndex(HttpServletRequest request) {
		return "first/real/firstTenderRealIndex";
	}

	/**
	 * <p>
	 * Description:查询用户的优先计划最终开通信息列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年3月3日
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryList/{pageNo}")
	public ModelAndView queryList(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer pageNo, FirstTenderRealCnd firstTenderRealCnd) throws Exception {
		ShiroUser shiroUser = currentUser();
		// 设置为当前用户
		firstTenderRealCnd.setUserId(shiroUser.getUserId());
		firstTenderRealCnd.setOrderSql("ORDER BY tr.FIRST_BORROW_ID asc");
		ModelAndView mv = new ModelAndView("first/real/firstTenderRealInner");
		Page page = firstTenderRealService.queryPageListByCnd(firstTenderRealCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
		mv.addObject("firstTenderRealPage", page);
		return mv;
	}

	/**
	 * <p>
	 * Description:解锁<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年3月6日
	 * @param request
	 * @param session
	 * @param firstBorrowId
	 * @return String
	 */
	@RequestMapping(value = "/unlock/{firstTenderRealId}")
	public @ResponseBody
	String unlockFirstTenderReal(HttpServletRequest request, HttpSession session, @PathVariable Integer firstTenderRealId) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			// 根据用户、直通车id执行解锁
			result = firstTenderRealService.saveUnLock(firstTenderRealId, shiroUser.getUserId());
			if (!"success".equals(result)) {
				return result;
			}
			try {
				// 解锁自动审核
				firstTenderRealService.saveUnLockApprove(firstTenderRealId, shiroUser.getUserId(), HttpTookit.getRealIpAddr(request), null, null);
			} catch (Exception e) {
				logger.error("解锁自动审核出错", e);
			}
		} catch (AppException ae) {
			return ae.getMessage();
		} catch (Exception e) {
			logger.error("解锁出错", e);
			result = "网络连接异常,请刷新页面或稍后重试！";
		}

		return result;
	}
}
