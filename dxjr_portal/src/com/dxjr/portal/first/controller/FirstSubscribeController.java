package com.dxjr.portal.first.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstSubscribeService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstSubscribeCnd;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:直通车转让认购控制类<br />
 * </p>
 * @title FirstSubscribeController.java
 * @package com.dxjr.portal.first.controller
 * @author 朱泳霖
 * @version 0.1 2015年3月19日
 */
@Controller
@RequestMapping(value = "/zhitongche/subscribe")
public class FirstSubscribeController extends BaseController {

	Logger logger = LoggerFactory.getLogger(FirstTransferBorrowController.class);

	@Autowired
	private FirstSubscribeService firstSubscribeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private FirstTransferService firstTransferService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private CurAccountService curAccountService;

	/**
	 * <p>
	 * Description:查询直通车转让认购信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月19日
	 * @param transferId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/querySubscribeList/{transferId}/{pageNo}")
	public ModelAndView querySubscribeListById(@PathVariable("transferId") Integer transferId, @PathVariable("pageNo") Integer pageNo) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/subscribeDetailInner");
		try {
			// 直通车转让认购查询
			FirstSubscribeCnd firstSubscribeCnd = new FirstSubscribeCnd();
			firstSubscribeCnd.setTransferId(transferId);
			Page page = firstSubscribeService.querySubscribeListByTransferId(firstSubscribeCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("firstSubscribePage", page);
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("查询直通车转让认购记录出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:确认认购<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月19日
	 * @param request
	 * @param session
	 * @param firstBorrowOpenCnd
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping
	@ResponseBody
	public MessageBox transferSubscribe(HttpServletRequest request, HttpSession session, FirstSubscribeCnd firstSubscribeCnd) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			// 判断IP是否为空
			if (StringUtils.isEmpty(firstSubscribeCnd.getAddIp())) {
				firstSubscribeCnd.setAddIp(HttpTookit.getRealIpAddr(request));
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_FIRST)) {
				return MessageBox.build("0", "");
			}
			// 平台来源设定
			firstSubscribeCnd.setPlatform(shiroUser.getPlatform());
			// 判断当前用户能否购买直通车
			result = this.validateSubscribeData(shiroUser, firstSubscribeCnd, request);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}

			// 手动认购
			result = firstSubscribeService.saveManualSubscribe(firstSubscribeCnd, shiroUser.getUserId());
			if (!"success".equals(result)) {
				return new MessageBox("0", result);
			}
			// 自动复审
			result = firstSubscribeService.saveTransferRecheck(firstSubscribeCnd, shiroUser.getUserId(), shiroUser.getUserName(), Constants.AUTO_CHECK_REMARK);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("0", result);
			}
		} catch (AppException ae) {
			return new MessageBox("0", ae.getMessage());
		} catch (Exception e) {
			logger.error("直通车认购出错", e);
			return new MessageBox("0", "数据异常,请联系客服！");
		}
		return new MessageBox("1", "操作成功");
	}

	/**
	 * <p>
	 * Description:判断当前用户能否购买直通车<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月27日
	 * @param shiroUser
	 * @return
	 * @throws Exception String
	 */
	private String validateSubscribeData(ShiroUser shiroUser, FirstSubscribeCnd firstSubscribeCnd, HttpServletRequest request) throws Exception {
		String result = BusinessConstants.SUCCESS;
		if (!isLogin()) {
			return ("请先登录");
		}

		if (!hasRole("invest")) {
			return ("您是借款用户,不能进行此操作");
		}
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
		// 查询用户信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 判断是否通过了认证
		if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
				&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
			return ("请先进行邮箱或手机认证！");
		}

		// 检查实名认证
		if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return ("请先进行实名认证");
		}

	/*	// 交易密码是否为空
		if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
			return ("您还没有设置交易密码，请先设置");
		}*/
	/*	// 交易密码
		if (!memberVo.getPaypassword().equals(MD5.toMD5(firstSubscribeCnd.getPayPassword()))) {
			return "交易密码有误";
		}*/

		// 检查是否绑定了银行卡
		BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
		if (null == bankInfoVo) {
			return "请先绑定银行卡!";
		}

		FirstTransferVo firstTransferVo = firstTransferService.queryTransferById(firstSubscribeCnd.getTransferId());
		// 认购密码
		if (!StringUtils.isEmpty(firstTransferVo.getBidPassword())) {
			if (StringUtils.isEmpty(firstSubscribeCnd.getBidPassword()) || !firstTransferVo.getBidPassword().equals(MD5.toMD5(firstSubscribeCnd.getBidPassword()))) {
				return "认购密码有误";
			}
		}

		// 判斷转让人和购买人是否是相同
		if (shiroUser.getUserId().equals(firstTransferVo.getUserId())) {
			return ("不能认购本人的债权转让");
		}

		// 判断认购画面的转让价格是否与数据库的转让价格一致
		// if (!firstTransferVo.getAccountReal().equals(firstSubscribeCnd.getAccountReal())) {
		if (firstTransferVo.getAccountReal().subtract(firstSubscribeCnd.getAccountReal()).compareTo(BigDecimal.ZERO) != 0) {
			return ("转让价格已变更，请<a href='javascript:parent.window.location.reload();' style='color:#3A5FCD;text-decoration: underline;'>刷新</a>页面");
		}

		// 账户余额| 不能小于最小认购额度，小于购买金额；
		AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
		BigDecimal useMoney = accountVo.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstSubscribeCnd.getIsUseCurMoney() != null && firstSubscribeCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId());
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 判断账户余额是否大于转让价格
		if (useMoney.compareTo(firstTransferVo.getAccountReal()) == -1) {
			return "账户余额小于可购买金额，无法认购！";
		}

		// 验证验证码
		/*String randCode = (String) request.getSession().getAttribute("randomCode");
		if (null == firstSubscribeCnd.getCheckCode() || null == randCode || !firstSubscribeCnd.getCheckCode().equals(randCode)) {
			return "验证码输入有误！";
		} else {
			// 验证码输入正确,删除验证码
			request.getSession().removeAttribute("randomCode");
		}*/

		// 非转让中直通车不可认购
		if (firstTransferVo.getStatus() != 2) {
			return "非转让中直通车不可认购！";
		}

		return result;
	}
}
