package com.dxjr.portal.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.ApproService;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.OnlineCounterService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.OnlineCounterVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:认证Controller<br />
 * </p>
 * 
 * @title EmailApproController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
@Controller
@RequestMapping(value = "/appro")
public class ApproController extends BaseController {

	public Logger logger = Logger.getLogger(ApproController.class);

	@Autowired
	private ApproService approService;
	@Autowired
	MemberService memberService;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MobileApproService mobileApproService;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;
	@Autowired
	private VIPApproService vipApproService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private OnlineCounterService onlineCounterService;
	/**
	 * 
	 * <p>
	 * Description:获取各项认证<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月14日
	 * @param request
	 * @param session
	 * @param email
	 * @return MemberApproVo
	 */
	@RequestMapping(value = "/findAppro")
	@ResponseBody
	public MemberApproVo updateAndSendMail(HttpServletRequest request, HttpSession session, String email) {
		MemberApproVo memberApproVo = null;
		try {
			ShiroUser shiroUser = currentUser();
			memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberApproVo;
	}

	/**
	 * <p>
	 * Description:邮箱认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param request
	 * @param user_id
	 * @param uuid
	 * @param email
	 * @return String
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/email_appro/{userId}/{uuid}")
	public ModelAndView emailAppro(HttpServletRequest request, @PathVariable Integer userId, @PathVariable String uuid, String email) throws Exception {
		ModelAndView mv = new ModelAndView("/account/safe/safetyCentre");
		String result = "success";
		try {
			if(userId.compareTo(currentUser().getUserId())!=0){
				result = "请本人认证";
			}
			result = approService.updateEmailAppr(currentUser().getUserId(), uuid, email, request);
			if (result.equals("success")) {
				result = "恭喜您通过了邮箱认证！";
			}else{
				email=null;
			}
		} catch (Exception e) {
			result = "网络异常，请稍候重试！";
			e.printStackTrace();
		}
		MemberApproVo memberApproVo = null;
		if (null != currentUser() && currentUser().getUserId() != null) {
			memberApproVo = memberService.queryMemberApproByUserId(currentUser().getUserId());
		}

		mv.addObject("memberApproVo", memberApproVo);
		mv.addObject("email", email).addObject("result", result);
		
		//官网改版-我的账户
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
        	mv.addObject("isCustody", true);
        } else {
        	mv.addObject("isCustody", false);
        }

		BankInfoVo currentBankCardVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
		mv.addObject("realNameApproVo", realNameApproVo);
		mv.addObject("emailApproVo", emailApproVo);
		mv.addObject("mobileAppro", mobileAppro);
		mv.addObject("member", memberVo);
		mv.addObject("currentBankCardVo", currentBankCardVo);
		mv.addObject("safeLevel", safetyCertificatComm(shiroUser));
		OnlineCounterVo onLineVo= onlineCounterService.queryLastOnlineCounterByUserId(shiroUser.getUserId());
		mv.addObject("onLineVo",onLineVo);
		mv.addObject("logintime", DateUtils.timeStampToDate(memberVo.getAddtime(), DateUtils.YMD_DASH));
		return mv;
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

	/**
	 * <p>
	 * Description:邮箱认证<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月3日
	 * @param request
	 * @param user_id
	 * @param uuid
	 * @param email
	 * @return String
	 */
	@RequestMapping(value = "/registEmailAppro/{userId}/{uuid}")
	public ModelAndView registEmailAppro(HttpServletRequest request, @PathVariable Integer userId, @PathVariable String uuid, String email) {
		ModelAndView mv = new ModelAndView("member/registerSucess");
		String result = memberService.activateRegistEmail(request, userId, uuid, email);
		mv.addObject("userName", result);

		return mv;
	}
}
