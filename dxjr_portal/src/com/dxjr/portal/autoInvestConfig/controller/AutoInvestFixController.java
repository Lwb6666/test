package com.dxjr.portal.autoInvestConfig.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvest;
import com.dxjr.portal.autoInvestConfig.service.FixAutoInvestService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

/**
 * 我的账户-投资管理-自动投资-自动投定期宝
 * <p>
 * Description:<br />
 * </p>
 * 
 * @title AutoInvestFixController.java
 * @package com.dxjr.portal.autoInvestConfig.controller
 * @author yubin
 * @version 0.1 2015年11月19日
 */
@Controller
@RequestMapping(value = "/myaccount/autoInvestFix")
public class AutoInvestFixController extends BaseController {

	private static final Logger logger = Logger.getLogger(AutoInvestFixController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private FixAutoInvestService fixAutoInvestService;

	@Autowired
	private VipLevelService vipLevelService;

	@Autowired
	private CurAccountService curAccountService;

	/**
	 * 我的设置
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/autoInvestFixMain")
	public ModelAndView autoInvestFixMain() {
		ModelAndView mv = new ModelAndView("account/autoInvest/autoInvest-fix-main");
		try {
			mv.addObject("auto", fixAutoInvestService.selectByUserId(currentUser().getUserId()));
		} catch (Exception e) {
			logger.error("自动投定期宝页面异常", e);
		}
		return mv;
	}

	/**
	 * 设置&修改初始化
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/investFixSeting")
	public ModelAndView investFixSeting(@RequestParam(value = "id", required = false) Integer id) {
		ModelAndView mv = new ModelAndView("account/autoInvest/autoInvest-fix-seting");
		try {
			// 账户可用余额
			int userId = currentUser().getUserId();
			AccountVo accountVo = accountService.queryAccountByUserId(userId);
			mv.addObject("useMoney", accountVo.getUseMoney());

			// 修改
			if (id != null) {
				FixAutoInvest fixAutoInvest = fixAutoInvestService.selectByPrimaryKey(id);
				if (fixAutoInvest.getUserId() != userId) {
					return new ModelAndView(BusinessConstants.ERROR);
				}
				if ("-1".equals(fixAutoInvest.getStatus())) {
					mv.addObject("msg", "数据已变更，请刷新");
					return mv;
				}
				mv.addObject("fixAutoInvest", fixAutoInvest);
			} else {
				mv.addObject("msg", fixAutoInvestService.initSetting(userId, 0));
			}
		} catch (Exception e) {
			logger.error("自动设置投定期宝页面异常", e);
		}
		return mv;
	}

	/**
	 * 设置&修改-提交
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/addAutoInvestFix")
	@ResponseBody
	public MessageBox addAutoInvestFix(@ModelAttribute FixAutoInvest fixAutoInvest) {
		ShiroUser shiroUser = currentUser();
		String result = "";
		try {
			if (null != fixAutoInvest && fixAutoInvest.getFixLimits() != null) {
				result = fixAutoInvestService.insert(fixAutoInvest, shiroUser, HttpTookit.getRealIpAddr(currentRequest()));
			} else {
				return new MessageBox("0", "至少选中一期定期宝");
			}
			if (BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("1", "设置成功");
			} else {
				return new MessageBox("0", result);
			}
		} catch (Exception e) {
			logger.error("添加自动投定期宝记录异常:", e);
			return new MessageBox("0", "设置异常");
		}
	}

	/**
	 * 启用或者停用功能
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/enabledFixBorrow")
	@ResponseBody
	public MessageBox enabledFixBorrow(@RequestParam(value = "id", required = true) Integer id, @RequestParam(value = "status") int status) {
		ShiroUser shiroUser = currentUser();
		String msg = "非法操作";
		FixAutoInvest fixAutoInvest = null;
		if (id != null && id > 0) {
			try {
				fixAutoInvest = fixAutoInvestService.selectByUserId(shiroUser.getUserId());
				if (null != fixAutoInvest && (fixAutoInvest.getUserId().equals(shiroUser.getUserId()))) {
					// 停用时，状态应为1
					if (status == 0 && !fixAutoInvest.getStatus().equals("1")) {
						return new MessageBox("0", "数据已变更，请刷新");
					}
					// 启用时，状态应为0
					if (status == 1 && !fixAutoInvest.getStatus().equals("0")) {
						return new MessageBox("0", "数据已变更，请刷新");
					}
					if (fixAutoInvest.getStatus().equals("0")) {
						// 未启用状态 ，现在执行启用
						fixAutoInvest.setStatus("1");
						fixAutoInvest.setUptime(fixAutoInvestService.getUptime(shiroUser.getUserId()));
						msg = fixAutoInvestService.updateByPrimaryKeySelective(fixAutoInvest, 4, shiroUser, HttpTookit.getRealIpAddr(currentRequest()));
					} else {
						// 启用状态，现在执行停用
						fixAutoInvest.setStatus("0");
						fixAutoInvest.setUptime(null);
						msg = fixAutoInvestService.updateByPrimaryKeySelective(fixAutoInvest, 3, shiroUser, HttpTookit.getRealIpAddr(currentRequest()));
					}
				} else {
					return new MessageBox("0", "记录不存在");
				}
			} catch (Exception e) {
				return new MessageBox("0", "网络异常...");
			}
		}
		if (BusinessConstants.SUCCESS.equals(msg)) {
			return new MessageBox("1", fixAutoInvest.getStatus().equals("0") ? "【停用】成功" : "【启用】成功");
		}
		return new MessageBox("0", msg);
	}

	/**
	 * 删除自动投定期宝功能
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/deleteFixBorrow")
	@ResponseBody
	public MessageBox deleteFixBorrow(@RequestParam(value = "id", required = true) Integer id) {
		ShiroUser shiroUser = currentUser();
		String msg = "非法操作";
		if (id != null && id > 0 && shiroUser != null) {
			try {
				FixAutoInvest fixAutoInvest = fixAutoInvestService.selectByPrimaryKey(id);
				if (fixAutoInvest == null || "-1".equals(fixAutoInvest.getStatus())) {
					return new MessageBox("0", "记录不存在");
				}

				if (!fixAutoInvest.getUserId().equals(shiroUser.getUserId())) {
					return new MessageBox("0", "非法操作");
				}

				fixAutoInvest = fixAutoInvestService.selectByUserId(shiroUser.getUserId());
				fixAutoInvest.setStatus("-1");
				msg = fixAutoInvestService.updateByPrimaryKeySelective(fixAutoInvest, 5, shiroUser, HttpTookit.getRealIpAddr(currentRequest()));
			} catch (Exception e) {
				logger.error("删除自动投定期宝功能", e);
				return new MessageBox("0", "网络异常...");
			}
		}
		if (BusinessConstants.SUCCESS.equals(msg)) {
			return new MessageBox("1", "删除成功");
		}
		return new MessageBox("0", msg);
	}

	/**
	 * 查询自动投宝记录功能
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月19日
	 * @param pageSize
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/queryAutoFixTenterLog/{pageNo}")
	public ModelAndView queryAutoFixTenterLog(@PathVariable int pageNo) {
		Page page = null;
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return new ModelAndView(BusinessConstants.ERROR);
		}
		ModelAndView modelAndView = new ModelAndView("account/autoInvest/autoInvestLog-fix-list");
		try {
			page = fixAutoInvestService.queryListByAutoInvestConfigRecordCnd(currentUser().getUserId(), pageNo, 10);
			modelAndView.addObject("page", page);
		} catch (Exception e) {
			logger.error("查询自动投宝记录功能异常", e);
		}
		return modelAndView;
	}

}