package com.dxjr.portal.borrow.controller;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrow;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.NetValueBorrowService;
import com.dxjr.portal.borrow.util.BorrowUtil;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

@Controller
@RequestMapping(value = "/rongzi")
public class NetValueController extends BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private NetValueBorrowService netValueBorrowService;

	@Autowired
	private BorrowReportService borrowReportService;

	/**
	 * 净值贷申请初始化--来自产品页面
	 * <p>
	 * Description:这里写描述 value = "/initApplyPro"<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月22日
	 * @return ModelAndView
	 */
	@RequestMapping
	@RequiresAuthentication
	public ModelAndView initApplyPro() {
		ModelAndView mv = null;
		try {
			ShiroUser shiroUser = currentUser();
			mv = netValueBorrowService.initApply(shiroUser, "product");
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_PUBLIC_BORROW)) {
				mv = new ModelAndView("/borrow/borrowProduct");
				return mv;
			}
			BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(shiroUser.getUserId()).setScale(2, BigDecimal.ROUND_DOWN);
			mv.addObject("netMoneyLimit", netMoneyLimit);// 净值额度
		} catch (Exception e) {
			logger.error("净值贷申请初始化异常", e);
		}
		return mv;
	}

	/**
	 * 净值贷申请初始化
	 * <p>
	 * Description:净值贷申请初始化<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/initApply")
	@RequiresAuthentication
	public ModelAndView initApply(@RequestParam String viewType) {
		ModelAndView mv = null;
		try {
			ShiroUser shiroUser = currentUser();
			mv = netValueBorrowService.initApply(shiroUser, viewType);
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_PUBLIC_BORROW)) {
				mv = new ModelAndView("/borrow/borrowProduct");
				return mv;
			}
			BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(shiroUser.getUserId()).setScale(2, BigDecimal.ROUND_DOWN);
			mv.addObject("netMoneyLimit", netMoneyLimit);// 净值额度
		} catch (Exception e) {
			logger.error("净值贷申请初始化异常", e);
		}
		return mv;
	}

	/**
	 * 净值贷申请
	 * <p>
	 * Description:净值贷申请<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月20日
	 * @param borrow
	 * @return MessageBox
	 */
	@RequestMapping(value = "/applyNetValueBorrow")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox applyNetValueBorrow(@ModelAttribute Borrow borrow, @RequestParam String checkcode) {
		try {
			String msg = BorrowUtil.checkCode(currentSession(), checkcode);
			if (!"".equals(msg)) {
				return MessageBox.build("0", msg);
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_PUBLIC_BORROW)) {
				return MessageBox.build("-99", "");
			}
			ShiroUser shiroUser = currentUser();
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(shiroUser.getUserId()).setScale(2, BigDecimal.ROUND_DOWN);
			return netValueBorrowService.saveApplyNetValue(shiroUser, borrow, addip, netMoneyLimit);
		} catch (Exception e) {
			logger.error("净值贷申请异常", e);
			return MessageBox.build("0", "净值贷申请异常");
		}
	}

	/**
	 * 
	 * <p>
	 * Description:估算净值标满标后的净值额度和可提金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月21日
	 * @param borrow
	 * @param checkcode
	 * @return MessageBox
	 */
	@RequestMapping(value = "/getUserNetMoneyLimitForFullBorrow")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox getUserNetMoneyLimitForFullBorrow(@ModelAttribute Borrow borrow) {
		try {
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_PUBLIC_BORROW)) {
				return MessageBox.build("-99", "");
			}
			if (borrow.getStyle() == 4) {
				borrow.setTimeLimit(borrow.getTimeLimitDay());
			}
			return netValueBorrowService.getUserNetMoneyLimitForFullBorrow(shiroUser.getUserId(), borrow.getAccount(), borrow.getTimeLimit(), borrow.getStyle(), borrow.getApr());
		} catch (Exception e) {
			logger.error("估算净值标满标后的净值额度和可提金额时出现异常", e);
			return MessageBox.build("0", "估算净值标满标后的净值额度和可提金额时出现异常");
		}
	}
}