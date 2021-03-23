package com.dxjr.portal.account.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.FixTenderReal;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.service.TodrawLogService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.fix.service.FixTenderRealService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/**
 * 
 * <p>
 * Description:转可提现记录Controller<br />
 * </p>
 * 
 * @title TodrawLogController.java
 * @package com.dxjr.portal.account.controller
 * @author yangshijin
 * @version 0.1 2014年8月12日
 */
@Controller
@RequestMapping(value = "/myaccount/todrawLog")
public class TodrawLogController extends BaseController {

	@Autowired
	private TodrawLogService todrawLogService;
	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TendRecordService tendRecordService;
	@Autowired
	private FixTenderRealService fixTenderRealService;

	/**
	 * 
	 * <p>
	 * Description:判断是否可转可提<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月23日
	 * @param request
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/judgeToDraw")
	@ResponseBody
	public MessageBox judgeToDraw(HttpServletRequest request) throws Exception {
		try {
			ShiroUser shiroUser = currentUser();
			if (tendRecordService.getTenderSuccessCountByUserId(shiroUser.getUserId()).intValue() > 0 ||fixTenderRealService.getFixTenderSuccessCountByUserId(shiroUser.getUserId().intValue())>0) {
				return new MessageBox("1", "success");
			} else {
				return new MessageBox("-1", "必须完成一次投标或投宝后才能转可提！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageBox("-1", "网络连接异常，请稍候重试");
		}
	}

	/**
	 * 
	 * <p>
	 * Description:进入转可提现页面<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toGetDraw")
	public ModelAndView getcash() throws Exception {
		ModelAndView mav = new ModelAndView("account/draw/getdraw");
		ShiroUser shiroUser = currentUser();
		if (shiroUser != null) {
			// 当前帐号信息
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 计算当前的净值额度
			BigDecimal maxAccount = borrowReportService.queryNetMoneyLimit(shiroUser.getUserId());
			// 查询冻结中的转可提金额
			BigDecimal noMoneyToal = todrawLogService.queryTodrawNoMoney(shiroUser.getUserId());
			mav.addObject("maxAccount", maxAccount);
			/*
			 * if (noMoneyToal != null) { mav.addObject( "maxDrawMoney",
			 * maxAccount.subtract(accountVo.getDrawMoney())
			 * .subtract(noMoneyToal) .setScale(2, BigDecimal.ROUND_DOWN)); }
			 * else { mav.addObject( "maxDrawMoney",
			 * maxAccount.subtract(accountVo.getDrawMoney()).setScale( 2,
			 * BigDecimal.ROUND_DOWN)); }
			 */
			// 实名认证信息
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			mav.addObject("realNameApproVo", realNameApproVo);
			mav.addObject("accountVo", accountVo);
		} else {
			mav = new ModelAndView("member/login");
		}
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Description:保存转可提现申请<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param request
	 * @param takeMoney 转可提金额
	 * @param paypassword 支付密码
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/saveTodrawLog")
	public @ResponseBody
	String saveTakeCash(HttpServletRequest request, String takeMoney, String paypassword) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			if (tendRecordService.getTenderSuccessCountByUserId(shiroUser.getUserId()).intValue() <= 0 &&fixTenderRealService.getFixTenderSuccessCountByUserId(shiroUser.getUserId().intValue())<=0) {
				return "必须完成一次投标或投宝后才能转可提！";
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			if (memberVo != null) {
				result = todrawLogService.saveTodrawLog(memberVo, takeMoney, paypassword, HttpTookit.getRealIpAddr(request));
				if (!"success".equals(result)) {
					return result;
				}
			} else {
				return "当前用户不存在，请重新登录后操作！";
			}
		} catch (AppException ae) {
			return ae.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "网络连接异常，请刷新页面或稍后重试!";
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:取消转可提<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param request
	 * @param draw_id
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/cancelDraw")
	public @ResponseBody
	String cancelCash(HttpServletRequest request, int draw_id) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser != null) {
				result = todrawLogService.cancelCash(draw_id, shiroUser.getUserId(), HttpTookit.getRealIpAddr(request));
				if (!"success".equals(result)) {
					return result;
				}
			} else {
				return "当前用户不存在，请重新登录后操作！";
			}
		} catch (AppException ae) {
			return ae.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "网络连接异常，请刷新页面或稍后重试!";
		}
		return result;
	}
}
