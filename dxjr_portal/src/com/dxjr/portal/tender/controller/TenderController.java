package com.dxjr.portal.tender.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.controller.MyAccountController;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.VIPApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * 
 * <p>
 * Description:投标控制类<br />
 * </p>
 * 
 * @title TenderController.java
 * @package com.dxjr.portal.tender.vo
 * @author chenlu
 * @version 0.1 2014年8月26日
 */
@Controller
@RequestMapping(value = "/tender")
public class TenderController extends BaseController {
	public Logger logger = Logger.getLogger(MyAccountController.class);

	@Autowired
	private TendRecordService tenderRecordService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BorrowService borrowService;

	@Autowired
	private VIPApproService vipApproService;

	@Autowired
	RealNameApproService realNameApproService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private TransferService transferService;

	@Autowired
	private FirstTransferService firstTransferService;

	/**
	 * 
	 * <p>
	 * Description:获取投标列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月26日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "searchTenderRecordList/{borrowId}/{pageNo}")
	public ModelAndView searchTenderRecordList(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer borrowId,
			@PathVariable Integer pageNo) {
		ModelAndView mv = new ModelAndView("investment/toInvest_tenderRecord");
		try {
			if (null == borrowId || null == pageNo) {
				/** 报500错误 */
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			ShiroUser shiroUser = currentUser();
			Page pgTender = tenderRecordService.queryTenderRecordByBorrowId(borrowId, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", pgTender);
			mv.addObject("loginMember", shiroUser);
		} catch (Exception e) {
			logger.error("我要投标详细页面_获取投标列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:判断当前用户是否能投标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月2日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/judgTender")
	@ResponseBody
	public MessageBox judgTender(HttpServletRequest request) throws Exception {
		if (!isLogin()) {
			return new MessageBox("0", "请先登录");
		}

		if (!hasRole("invest")) {
			return new MessageBox("0", "您是借款用户,不能进行此操作");
		}
		ShiroUser shiroUser = currentUser();
		Integer type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_BORROW)) {
				return new MessageBox("-99", "");
			}
		} else if (type == 2) {
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_FIRST)) {
				return new MessageBox("-99", "");
			}
		} else if (type == 3) {
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_TRANSFER)) {
				return new MessageBox("-99", "");
			}
		}
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
		// 查询用户信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
				&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
			return new MessageBox("-1", "请先进行邮箱或手机认证！");
		}

		// 检查实名认证
		if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return new MessageBox("-2", "请先进行实名认证");
		}

		if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
			return new MessageBox("-4", "您还没有设置交易密码，请先设置");
		}

		// 检查是否绑定了银行卡
		BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
		if (null == bankInfoVo) {
			return new MessageBox("-5", "请先绑定银行卡!");
		}

		// vip验证，
		/*
		 * VIPApproVo vipApproVo =
		 * vipApproService.queryVIPApproByUserId(shiroUser.getUserId());
		 * 
		 * if (vipApproVo == null) { return new MessageBox("10",
		 * "您是非VIP用户，一旦发生逾期垫付将不保障利息，请先认证为vip用户！"); } if (vipApproVo != null) {
		 * if (vipApproVo.getPassed() == -1 || DateUtils.dayDiff(new Date(),
		 * vipApproVo.getIndate()) > 1) { return new MessageBox("11",
		 * "您的VIP已过期，一旦发生逾期垫付将不保障利息，请重新认证为vip用户！"); } }
		 */
		// 是否是新手投标及新手标
		String borrowIdStr = request.getParameter("borrowId");
		if (borrowIdStr != null && !borrowIdStr.equals("")) {
			int borrowId = Integer.parseInt(borrowIdStr);
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(borrowId);
			if (borrowVo.getAreaType() != null && borrowVo.getAreaType() == 1) { // 新手标
				if (tenderRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0) {
					return new MessageBox("-6", "您不是新手，无法投新手标！");
				}
				if (transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
					return new MessageBox("-6", "您不是新手，无法投新手标！");
				}
			}
		}

		// 验证是否购买本人直通车
		if (!StringUtils.isEmpty(request.getParameter("transferId"))) {
			FirstTransferVo firstTransferVo = firstTransferService.queryTransferById(Integer.valueOf(request.getParameter("transferId")));
			// 判斷转让人和购买人是否是相同
			if (shiroUser.getUserId().equals(firstTransferVo.getUserId())) {
				return new MessageBox("-7", "不能购买本人直通车");
			}
		}
		return new MessageBox("1", "success");
	}

	/**
	 * <p>
	 * Description:判断当前用户是否vip<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年12月29日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/vipAppro")
	@ResponseBody
	public MessageBox vipAppro(HttpServletRequest request) throws Exception {
		ShiroUser shiroUser = currentUser();
		Integer memberId = shiroUser.getUserId();
		VIPApproVo vipApproVo = vipApproService.queryVIPApproByUserId(memberId);

		if (vipApproVo == null) {
			return new MessageBox("0", "您是非VIP用户，一旦发生逾期垫付将不保障利息，请先认证为vip用户！");
		}
		if (vipApproVo != null) {
			if (vipApproVo.getPassed() == -1 || DateUtils.dayDiff(new Date(), vipApproVo.getIndate()) > 1) {
				return new MessageBox("-1", "您的VIP已过期，一旦发生逾期垫付将不保障利息，请重新认证为vip用户！");
			}
		}

		String borrowIdStr = request.getParameter("borrowId");
		int borrowId = 0;
		if (borrowIdStr != null && !borrowIdStr.equals("")) {
			borrowId = Integer.parseInt(borrowIdStr);
		}
		BorrowVo borrowVo = borrowService.selectByPrimaryKey(borrowId);
		if (borrowVo != null && borrowVo.getBorrowtype() != 3 && borrowVo.getBorrowtype() != 4) {
			if (vipApproVo == null) {
				// 根据userId查询投官方标的投标记录数量
				Integer count = tenderRecordService.getTenderRecordCountByUserId(memberId);
				if (count == null || count.intValue() == 0) {
					return new MessageBox("0", "您是非VIP用户，一旦发生逾期垫付将不保障利息，请先认证为vip用户！");
				}
			}
			if (vipApproVo != null) {
				if (vipApproVo.getPassed() == -1 || DateUtils.dayDiff(new Date(), vipApproVo.getIndate()) > 1) {
					// 根据userId查询投官方标的投标记录数量
					Integer count = tenderRecordService.getTenderRecordCountByUserId(memberId);
					if (count == null || count.intValue() == 0) {
						return new MessageBox("-1", "您的VIP已过期，一旦发生逾期垫付将不保障利息，请重新认证为vip用户！");
					}
				}
			}
		}
		return new MessageBox("1", "success");
	}
}
