package com.dxjr.portal.curAccount.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurInService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.curAccount.vo.CurAccountVo;
import com.dxjr.portal.curAccount.vo.CurOutVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/****
 * 
 * <p>
 * Description:活期宝转出记录 <br />
 * </p>
 * 
 * @title CurOutController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Controller
@RequestMapping(value = "/curOut")
public class CurOutController extends BaseController {
	private static final Logger logger = Logger.getLogger(CurOutController.class);

	@Autowired
	private CurOutService curOutService;
	@Autowired
	private CurInService curInService;
	@Autowired
	private CurAccountService curAccountService;

	/**
	 * 
	 * <p>
	 * Description:判断是否可转出到可用余额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param request
	 * @param curOutVo
	 * @return MessageBox
	 */
	@RequestMapping(value = "/judgeIsCanOut")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox judgeIsCanOut(HttpServletRequest request, CurOutVo curOutVo) {
		ShiroUser shiroUser = currentUser();
		String result = "success";
		try {
			CurAccountVo curAccountVo = curAccountService.selectByUserId(shiroUser.getUserId());
			if (curAccountVo == null) {
				return MessageBox.build("0", "未开通活期宝，无法操作！");
			}
			int hourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
			if (hourNum >= 0 && hourNum < 4) {
				return MessageBox.build("0", "耐心等待一会，系统正在努力结算！");
			}
			if (curOutService.queryCountByUserIdAndDate(shiroUser.getUserId(), new Date()) >= 5) {
				return MessageBox.build("0", "当天转出笔数已达到5笔，无法再进行转出！");
			}
		} catch (Exception e) {
			logger.error("活期宝转出操作出错！", e);
			return new MessageBox("0", "网络连接异常，请刷新页面或稍后重试!");
		}
		// 判断该用户是否可转出
		return MessageBox.build("1", result);
	}

	/**
	 * 
	 * <p>
	 * Description:进入活期宝转出页面<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param request
	 * @param targetFrame
	 *            1:表示从活期生息画面进入；2：表示从我的账户画面进入
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/enterCurOut/{targetFrame}")
	@RequiresAuthentication
	// 判断是否登录 
	public ModelAndView forPledgebid(HttpServletRequest request, @PathVariable("targetFrame") Integer targetFrame) {
		ModelAndView view = new ModelAndView("/curAccount/cur_out");
		ShiroUser shiroUser = currentUser();
		// 是否可转出
		boolean isCanOut = true;
		try {
			// 查询当天转出次数
			Integer curOutCount = curOutService.queryCountByUserIdAndDate(shiroUser.getUserId(), new Date());
			view.addObject("curOutCount", curOutCount);
			int currentHourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
			view.addObject("currentHourNum", currentHourNum);
			// 获取当前账户
			CurAccount curAccount = curAccountService.selectByUserId(shiroUser.getUserId());
			// 查询当天转入受限总额
			BigDecimal noDrawMoneyTotal = curInService.queryNoDrawMoneyTotalByUserIdAndDate(shiroUser.getUserId(), new Date());
			// 最大可转出金额
			BigDecimal maxAccount = BigDecimal.ZERO;
			if (curAccount != null) {
				// 最大可转出金额
				maxAccount = curAccount.getTotal().subtract(noDrawMoneyTotal).setScale(2, BigDecimal.ROUND_DOWN);
				if (maxAccount.compareTo(BigDecimal.ZERO) == -1) {
					maxAccount = BigDecimal.ZERO;
				}
			}
			view.addObject("maxAccount", maxAccount);
			view.addObject("targetFrame", targetFrame);
		} catch (Exception e) {
			logger.error("进入活期宝页面出错！");
			isCanOut = false;
		}
		view.addObject("isCanOut", isCanOut);
		return view;
	}

	/**
	 * 
	 * <p>
	 * Description:转出到可用余额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping(value = "/saveCurOut")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox saveCurOut(HttpServletRequest request, CurOutVo curOutVo) {
		ShiroUser shiroUser = currentUser();
		String result = "success";
		try {
			// 较验转出参数及是否可进行转出
			result = curOutService.validateCurOutParam(shiroUser.getUserId(), curOutVo);
			if (!result.equals("success")) {
				return MessageBox.build("0", result);
			}
			// 开始转出，处理业务逻辑
			result = curOutService.saveCurOut(shiroUser.getUserId(), curOutVo.getAccount(), HttpTookit.getRealIpAddr(request));
			if (!result.equals("success")) {
				return MessageBox.build("0", result);
			}
		} catch (AppException ae) {
			return new MessageBox("0", ae.getMessage());
		} catch (Exception e) {
			logger.error("活期宝转出操作出错！", e);
			return new MessageBox("0", "网络连接异常，请刷新页面或稍后重试!");
		}
		// 判断该用户是否可转出
		return MessageBox.build("1", result);
	}

}
