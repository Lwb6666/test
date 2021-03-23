package com.dxjr.portal.curAccount.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.service.CurInService;
import com.dxjr.portal.curAccount.vo.CurInLaunchVo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;

/****
 * 
 * <p>
 * Description: 活期宝转入(投标)记录 <br />
 * </p>
 * 
 * @title CurInController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Controller
@RequestMapping(value = "/curInController")
public class CurInController extends BaseController {

	private static final Logger logger = Logger.getLogger(CurInController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BankInfoService bankInfoService;

	@Autowired
	private CurInService curInService;

	/**
	 * <p>
	 * Description:跳转到活期宝画面<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView curAccountInvest(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("curAccount/curAccountInvest");
		try {
			// 判断是否登录
			if (isLogin()) {
				// 获得用户信息
				ShiroUser shiroUser = currentUser();
				// 帐号资金
				AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
				mv.addObject("account", account);
			}
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("进入活期宝画面出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:活期宝加入<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param curInLaunchVo
	 * @param request
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@RequestMapping(value = "/doCurrentIn")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox doCurrentIn(CurInLaunchVo curInLaunchVo, HttpServletRequest request) throws Exception {
		MessageBox MessageBox = validateCurrentData(curInLaunchVo, request, 0);
		try {
			// 验证数据是否正确
			if (!"1".equals(MessageBox.getCode())) {
				return MessageBox;
			}
			ShiroUser shiroUser = currentUser();
			// 用户ID
			curInLaunchVo.setUserId(shiroUser.getUserId());
			// 判断IP是否为空
			if (StringUtils.isEmpty(curInLaunchVo.getAddIp())) {
				curInLaunchVo.setAddIp(HttpTookit.getRealIpAddr(request));
			}
			// 平台来源设定
			curInLaunchVo.setPlatform(shiroUser.getPlatform());
			// 保存活期宝转入信息
			String result = curInService.saveCurrentIn(curInLaunchVo);
			if ("00001".equals(result)) {
				return new MessageBox("-6", "加入金额不能小于1元!");
			} else if ("00002".equals(result)) {
				return new MessageBox("-6", "加入金额不能大于账户可用余额!");
			}
		} catch (Exception e) {
			logger.error("保存活期宝转入出错", e);
			return new MessageBox("-6", "网络连接异常,请刷新页面或稍后重试！");
		}

		return MessageBox;
	}

	/**
	 * <p>
	 * Description:我的账户活期宝转入<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月13日
	 * @param curInLaunchVo
	 * @param request
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@RequestMapping(value = "/doMyCurrentIn")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox doMyCurrentIn(CurInLaunchVo curInLaunchVo, HttpServletRequest request) throws Exception {
		MessageBox MessageBox = validateCurrentData(curInLaunchVo, request, 1);
		try {
			// 验证数据是否正确
			if (!"1".equals(MessageBox.getCode())) {
				return MessageBox;
			}
			ShiroUser shiroUser = currentUser();
			// 用户ID
			curInLaunchVo.setUserId(shiroUser.getUserId());
			// 判断IP是否为空
			if (StringUtils.isEmpty(curInLaunchVo.getAddIp())) {
				curInLaunchVo.setAddIp(HttpTookit.getRealIpAddr(request));
			}
			// 平台来源设定
			curInLaunchVo.setPlatform(shiroUser.getPlatform());
			// 保存活期宝转入信息
			String result = curInService.saveCurrentIn(curInLaunchVo);
			if ("00001".equals(result)) {
				return new MessageBox("-6", "加入金额不能小于1元!");
			} else if ("00002".equals(result)) {
				return new MessageBox("-6", "加入金额不能大于账户可用余额!");
			}
		} catch (Exception e) {
			logger.error("保存活期宝转入出错", e);
			return new MessageBox("-6", "网络连接异常,请刷新页面或稍后重试！");
		}

		return MessageBox;
	}

	/**
	 * <p>
	 * Description:活期宝介绍<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月7日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toAccountIntroduce")
	public ModelAndView toAccountProblem() {
		return forword("curAccount/curAccountIntroduce");
	}

	/**
	 * <p>
	 * Description:活期宝常见问题<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月7日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toAccountProblem")
	public ModelAndView toInvestFAQ() {
		return forword("curAccount/curAccountProblem");
	}

	/**
	 * <p>
	 * Description:判断可否转入到活期宝<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月13日
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping(value = "/judgeIsCanIn")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox judgeIsCanIn(HttpServletRequest request) {
		try {
			if (!isLogin()) {
				return new MessageBox("0", "请先登录");
			}

			if (!hasRole("invest")) {
				return new MessageBox("0", "您是借款用户,不能进行此操作");
			}
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_CURRENT)) {
				return new MessageBox("-99", "");
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
			// 交易密码设置
			if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
				return new MessageBox("-4", "您还没有设置交易密码，请先设置");
			}

			// 检查是否绑定了银行卡
			BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
			if (null == bankInfoVo) {
				return new MessageBox("-5", "请先绑定银行卡!");
			}
			int hourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
			if (hourNum >= 0 && hourNum < 4) {
				return MessageBox.build("-6", "耐心等待一会，系统在努力结算");
			}

		} catch (Exception e) {
			logger.error("活期宝加入出错", e);
			return new MessageBox("99", "网络连接异常,请刷新页面或稍后重试！");
		}
		return new MessageBox("1", BusinessConstants.SUCCESS);
	}

	/**
	 * <p>
	 * Description:验证活期宝数据<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param curInLaunchVo
	 * @param request
	 * @param flag
	 *            0:活期宝加入;1:我的账户活期宝加入
	 * @return MessageBox
	 */
	private MessageBox validateCurrentData(CurInLaunchVo curInLaunchVo, HttpServletRequest request, int flag) {
		try {
			if (!isLogin()) {
				return new MessageBox("0", "请先登录");
			}

			if (!hasRole("invest")) {
				return new MessageBox("-6", "您是借款用户,不能进行此操作");
			}
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_CURRENT)) {
				return new MessageBox("-99", "");
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
		/*	// 交易密码设置
			if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
				return new MessageBox("-4", "您还没有设置交易密码，请先设置");
			}*/

			// 检查是否绑定了银行卡
			BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
			if (null == bankInfoVo) {
				return new MessageBox("-5", "请先绑定银行卡!");
			}
			// 验证加入金额不能为空
			if (flag == 0 && curInLaunchVo.getTenderMoney() == null) {
				return new MessageBox("-6", "加入金额不能为空!");
			}
			// 验证加入金额必须大于1
			if (BigDecimal.ONE.compareTo(curInLaunchVo.getTenderMoney()) > 0) {
				return new MessageBox("-6", "加入金额不能小于1元!");
			}
			// 验证加入金额必须小于账户的可用余额
			// 帐号资金
			AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
			if (curInLaunchVo.getTenderMoney().compareTo(account.getUseMoney()) > 0) {
				return new MessageBox("-6", "加入金额不能大于账户可用余额!");
			}
			/*// 交易密码
			if (!memberVo.getPaypassword().equals(MD5.toMD5(curInLaunchVo.getPayPassword()))) {
				return new MessageBox("-6", "交易密码有误");
			}*/
			/*if (flag == 0) {
				// 验证验证码
				String randCode = (String) request.getSession().getAttribute("randomCode");
				if (null == curInLaunchVo.getCheckCode() || null == randCode || !curInLaunchVo.getCheckCode().equals(randCode)) {
					return new MessageBox("-6", "验证码输入有误！");
				} else {
					// 验证码输入正确,删除验证码
					request.getSession().removeAttribute("randomCode");
				}
			}*/

			// int hourNum = Integer.parseInt(DateUtils.format(new Date(),
			// DateUtils.HH_ONLY));
			// if (hourNum >= 0 && hourNum <= 4) {
			// return MessageBox.build("-6", "每天0点至4点无法进行转出操作！");
			// }

		} catch (Exception e) {
			logger.error("活期宝加入出错", e);
			return new MessageBox("99", "网络连接异常,请刷新页面或稍后重试！");
		}
		return new MessageBox("1", BusinessConstants.SUCCESS);
	}

	/**
	 * <p>
	 * Description:我的账户活期宝转入<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月23日
	 * @param request
	 * @param targetFrame
	 *            1:表示从活期生息画面进入；2：表示从我的账户画面进入
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/enterCurIn/{targetFrame}")
	@RequiresAuthentication
	// 判断是否登录 
	public ModelAndView forPledgebid(HttpServletRequest request, @PathVariable("targetFrame") Integer targetFrame) {
		ModelAndView view = new ModelAndView("/curAccount/cur_in");
		ShiroUser shiroUser = currentUser();
		try {
			// 获取服务器时间
			int currentHourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
			view.addObject("currentHourNum", currentHourNum);
			// 帐号资金
			AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
			view.addObject("maxAccount", account.getUseMoney());
			view.addObject("targetFrame", targetFrame);
		} catch (Exception e) {
			logger.error("进入活期宝页面出错！");
		}
		return view;
	}

}
