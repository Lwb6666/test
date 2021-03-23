package com.dxjr.portal.account.controller;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.*;
import com.dxjr.portal.member.vo.*;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

/**
 * <p>
 * Description:账户安全中心<br />
 * </p>
 *
 * @title AccountSafetyCentreController.java
 * @package com.dxjr.portal.account.controller
 * @author hujianpan
 * @version 0.1 2014年8月26日
 */
@Controller
@RequestMapping(value = "/AccountSafetyCentre")
public class AccountSafetyCentreController extends BaseController {

	public Logger logger = Logger.getLogger(AccountSafetyCentreController.class);

	@Autowired
	private MyAccountService myAccountServiceImpl;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private VIPApproService vipApproService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private OnlineCounterService onlineCounterService;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;

    /**
	 * <p>
	 * Description:进入账户安全中心主页面<br />
	 * </p>
	 *
	 * @author 胡建盼
	 * @version 0.1 2014年8月26日
	 * @param request
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/safetyIndex")
	public ModelAndView show(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("/account/safe/safetyCentre");
		ShiroUser shiroUser = currentUser();
		// 没有登入
		if (null == shiroUser) {
			forword(BusinessConstants.TOP_HOME_ADDRESS);
		}
		Integer memberId = shiroUser.getUserId();

		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(memberId);
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(memberId);
		EmailApproVo emailApproVo = emailApprService.queryEmailApproByUserId(memberId);

		MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(memberId);

        // 验证用户是否已经开通账户
        BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(memberId);
        if (baseEBankInfo != null && baseEBankInfo.getStatus() == 1) {
            mav.addObject("isCustody", true);
        } else {
            mav.addObject("isCustody", false);
        }

        // 两者其中一个是黑名单都无法开户
        if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE) ||
                super.judgeBlackByType(BusinessConstants.BLACK_TYPE_CASH)) {
            return redirect("/myaccount/toIndex.html");
        }

		BankInfoVo currentBankCardVo = bankInfoService.getUserCurrentCardAppro(shiroUser.getUserId());
		mav.addObject("realNameApproVo", realNameApproVo);
		mav.addObject("emailApproVo", emailApproVo);
		mav.addObject("mobileAppro", mobileAppro);
		mav.addObject("member", memberVo);
		mav.addObject("currentBankCardVo", currentBankCardVo);
		mav.addObject("safeLevel", safetyCertificatComm(shiroUser));
		OnlineCounterVo onLineVo= onlineCounterService.queryLastOnlineCounterByUserId(shiroUser.getUserId());
		mav.addObject("onLineVo",onLineVo);
		mav.addObject("logintime", DateUtils.timeStampToDate(memberVo.getAddtime(), DateUtils.YMD_DASH));
		return mav;
	}

	/**
	 *
	 * <p>
	 * Description:进入重置登入密码界面<br />
	 * </p>
	 *
	 * @author 胡建盼
	 * @version 0.1 2014年9月17日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/safetyCentre/enterResetLoginPwd")
	public ModelAndView enterResetLoginsPwd() {
		return new ModelAndView("/account/safe/modifyLoginPwdDiv");
	}

	/**
	 *
	 * <p>
	 * Description:进入找回登入密码界面<br />
	 * </p>
	 *
	 * @author 胡建盼
	 * @version 0.1 2014年9月17日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/safetyCentre/enterFindLoginPwd")
	public ModelAndView enterFindLoginsPwd() {

		return new ModelAndView("/account/safe/findLoginPwd");
	}


	@RequestMapping(value = "/safetyCentre/enterFindLoginPwdDiv")
	public ModelAndView enterFindLoginsPwdDiv() {
		return new ModelAndView("/account/safe/findLoginPwdDiv");
	}

	/**
	 *
	 * <p>
	 * Description:进入重置交易密码界面<br />
	 * </p>
	 *
	 * @author 胡建盼
	 * @version 0.1 2014年9月17日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/safetyCentre/enterResetTransactionPwd")
	public ModelAndView enterResetPwd() {

		return new ModelAndView("/account/safe/modifyPayPwdDiv");
	}

	/**
	 *
	 * <p>
	 * Description:进入找回交易密码界面<br />
	 * </p>
	 *
	 * @author 胡建盼
	 * @version 0.1 2014年9月17日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/safetyCentre/enterFindTransactionPwd")
	public ModelAndView enterfindTransactionPwd() {
		return new ModelAndView("/account/safe/findPayPwdDiv");
	}
	/**
	 *
	 * <p>
	 * Description:进入手机找回页面<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月24日
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/safetyCentre/findPhone")
	public ModelAndView enterFindLoginsPwdByPhone() {

		return new ModelAndView("/account/safe/findPhone");
	}


	/**
	 *
	 * <p>
	 * Description:判断安全级别<br />
	 * </p>
	 * @author Luobinbin
	 * @version 0.1 2016-5-16
	 * @param shiroUser
	 * @return
	 * String
	 */
	public String safetyCertificatComm(ShiroUser shiroUser) {

		HashMap<Object, Object> retMap = new HashMap<Object, Object>();
		MemberCnd memberCnd = new MemberCnd();
		Integer userId = shiroUser.getUserId();
		int i = 0;// 判断认证通过的个数
		String safeLevel="低";
		try {

			memberCnd.setId(userId);

			// 登录密码 , 交易密码 认证
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			// 登录密码
			if (null != memberVo && memberVo.getLogpassword() != null) {
				i++;
			}

			// 交易密码
			if (null != memberVo && memberVo.getPaypassword() != null) {
				i++;
			}

			// 实名认证
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(userId);
			if (null != realNameApproVo && realNameApproVo.getIsPassed() == 1) {
				i++;
			}

			// 邮箱认证
			EmailApproVo emailApproVo = emailApprService.queryEmailApproByUserId(userId);
			if (null != emailApproVo && emailApproVo.getChecked() == 1) {
				i++;
			}

			// 手机认证
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(userId);
			if (null != mobileAppro && mobileAppro.getPassed() == 1) {
				i++;
			}

			// 银行卡认证
			BankInfoVo currentBankCardVo = bankInfoService.getUserCurrentCard(userId);
			if (currentBankCardVo != null && currentBankCardVo.getStatus() == 0) {
				i++;
			}

			retMap.put("memberVo", memberVo);
			retMap.put("realNameApproVo", realNameApproVo);

			retMap.put("emailApproVo", emailApproVo);
			retMap.put("mobileAppro", mobileAppro);

			/* retMap.put("vipApproVo", vipApproVo); */
			retMap.put("currentBankCardVo", currentBankCardVo);

			// 安全等级
			int safeDegree = 0;
			if (i < 6) {
				safeDegree = (int) 100 * i / 6;
			}
			if (i == 6) {
				safeDegree = 100;
			}
			if(safeDegree<=80 && safeDegree<100){
				safeLevel="中";
			}else if(safeDegree == 100){
				safeLevel="高";
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return safeLevel;
	}


}
