package com.dxjr.wx.advertised.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Bank;
import com.dxjr.base.entity.BankInfo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.lianlian.service.LianlianpayWapService;
import com.dxjr.portal.lianlian.vo.LlWapBankcardInfo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;
import com.dxjr.wx.advertised.service.AdvertisedService;
import com.dxjr.wx.advertised.vo.AdvertisedVo;

/**
 * <p>
 * Description:广告推广Controller<br />
 * </p>
 * 
 * @title AdvertisementController.java
 * @package com.dxjr.portal.member.controller
 * @author hujianpan
 * @version 0.1 2015年1月13日
 */
@Controller
@RequestMapping(value = "/advertised")
public class AdvertisedController extends BaseController {

	private final static Logger logger = Logger.getLogger(AdvertisedController.class);
	@Autowired
	private AdvertisedService advertisedService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	MemberService memberService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private LianlianpayWapService lianlianpayWapService;
	@Autowired
	private MemberRegisterService memberRegisterService;

	/**
	 * <p>
	 * Description:广告活动注册入口<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年12月31日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "/regist")
	@ResponseBody
	public MessageBox regist(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdvertisedVo vo) {
		String result = "success";
		try {
			if (StringUtils.isEmpty(vo.getMobile())) {
				return new MessageBox("0", "手机号不能为空！");
			}
			if (StringUtils.isEmpty(vo.getActiveCode())) {
				return new MessageBox("0", "手机验证码不能为空！");
			}
			try {
				memberRegisterService.queryMemberRepeat(null, null, vo.getUsername());
			} catch (AppException app) {
				return new MessageBox("0", "用户名已存在");
			}
			result = advertisedService.regist(vo, request, BusinessConstants.MOBILE_APPRO_FUNCTION, session);
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			e.printStackTrace();
			logger.error(e.getStackTrace());
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
		} finally {

		}
		if (BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("1", "注册成功！");
		}
		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:无手机验证码的广告活动注册入口<br />
	 * </p>
	 * 
	 * @author tongjuxing
	 * @version 0.1 2015年8月25日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @return MessageBox
	 */
	@RequestMapping(value = "/registNoActiveCode")
	@ResponseBody
	public MessageBox registNoActiveCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdvertisedVo vo) {
		String result = "success";
		try {
			
			if (StringUtils.isEmpty(vo.getMobile())) {
				return new MessageBox("0", "手机号不能为空！");
			}
			if (!verifyMobail(vo.getMobile())) {
				return new MessageBox("0", "请输入正确的手机号！");
			}
			try {
				memberRegisterService.queryMemberRepeat(null, vo.getMobile(), vo.getUsername());
			} catch (AppException app) {
				return new MessageBox("0", app.getMessage());
			}
			vo.setNewPwd(vo.getMobile().substring(5));
			
			result = advertisedService.registNoActiveCode(vo, request, session);
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			logger.error(e);
			e.printStackTrace();
		}
		
		if (BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("1", "注册成功！");
		}
		return new MessageBox("0", result);
	}
	
	/**
	 * <p>
	 * Description:注册时获取手机验证码<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月11日
	 * @param request
	 * @param vo
	 * @return Object
	 */
	@RequestMapping(value = "/sendMobailCode")
	@ResponseBody
	public Object sendMobailActiveWithWXAdvertisement(HttpServletRequest request, AdvertisedVo vo) {
		String result = BusinessConstants.SUCCESS;
		try {
			if (!StringUtils.isEmpty(vo.getMobile())) {

				if (!verifyMobail(vo.getMobile())) {
					return new MessageBox("0", "请输入正确的手机号！");
				}
				if (!mobileApproService.isMobileNumUsed(vo.getMobile())) {
					result = mobileApproService.sendMobileApprValidate(vo.getMobile(), request, "", BusinessConstants.MOBILE_APPRO_FUNCTION,
							BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST);

				} else {
					return new MessageBox("0", "手机号已被使用！");
				}
			} else {
				return new MessageBox("0", "手机号不能为空！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}
		return new MessageBox("1", "发送成功，请注意查收");
	}

	/**
	 * <p>
	 * Description:充值前进行业务逻辑处理：1、实名认证处理；2、银行卡绑定处理；<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月11日
	 * @param request
	 * @param response
	 * @param session
	 * @param vo
	 * @return Object
	 */
	@RequestMapping(value = "/beforerecharge")
	@ResponseBody
	public Object beforeRecharge(HttpServletRequest request, HttpServletResponse response, HttpSession session, AdvertisedVo vo) {

		String result = "success";
		/************************************ begin 实名认证 ***************************/
		try {
			if (null == currentUser()) {
				return new MessageBox("1", "请先登入");
			}
			ShiroUser shiroUser = currentUser();
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			// 没有进行过实名认证
			if (realNameApproVo == null || realNameApproVo.getIsPassed() != 1) {
				MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
				if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
						&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
					return "请先进行邮箱或手机认证！";
				}
				result = realNameApproService.realnameAppro(shiroUser.getUserId(), vo.getRealname(), vo.getIdcard(), HttpTookit.getRealIpAddr(request));
				if (!result.equals("success")) {
					return new MessageBox("1", result);
				}
			}
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			logger.error(e);
			return new MessageBox("1", result);
		}
		/************************************ end 实名认证 ***************************/

		/************************************ begin 添加银行卡 *************************/
		String msg = null;
		try {
			List<BankInfoVo> list = bankInfoService.queryBankInfosByUserId(currentUser().getUserId());
			// 没有银行卡时添加银行卡
			if (list.size() == 0) {

				try {
					LlWapBankcardInfo llWapBankcardInfo = lianlianpayWapService.queryBankcardInfo(vo.getCardNum());
					if (!llWapBankcardInfo.getResultMsg().equals(BusinessConstants.SUCCESS)) {
						return MessageBox.build("1", llWapBankcardInfo.getResultMsg());
					}
					if (llWapBankcardInfo.getBank_code() == null) {
						return MessageBox.build("1", "开户行不存在！");
					}

					// _cnapsCode = Long.valueOf(cnapsCode);

					BankInfo bankInfo = new BankInfo();
					bankInfo.setUserId(currentUser().getUserId());
					bankInfo.setCardNum(vo.getCardNum());
					bankInfo.setBankCode(llWapBankcardInfo.getBank_code());
					bankInfo.setBankName(llWapBankcardInfo.getBank_name());
					msg = advertisedService.saveBankcard(bankInfo);
					if (!"success".equals(msg)) {
						return MessageBox.build("1", msg);
					}
				} catch (Exception e) {
					logger.error("保存银行卡信息失败.", e);
					msg = "保存失败.";
					return MessageBox.build("1", msg);
				}

			}
		} catch (Exception e1) {
			logger.error("保存银行卡信息失败.", e1);
			return MessageBox.build("1", msg);
		}

		/************************************ end 添加银行卡 *************************/

		return MessageBox.build("0", "success");
	}

	/**
	 * <p>
	 * Description:查询银行卡信息<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月11日
	 * @param cardNum
	 * @return LlWapBankcardInfo
	 */
	@RequestMapping(value = "bankcardInfo/{cardNum}")
	@ResponseBody
	public LlWapBankcardInfo bankCardInfo(@PathVariable("cardNum") String cardNum) {
		LlWapBankcardInfo llWapBankcardInfo = null;
		try {
			llWapBankcardInfo = lianlianpayWapService.queryBankcardInfo(cardNum);
		} catch (Exception e) {
			logger.error("校验银行卡号信息异常", e);
			llWapBankcardInfo.setResultMsg("校验银行卡号信息异常");
		}
		if (null == llWapBankcardInfo || StringUtils.isEmpty(llWapBankcardInfo.getBank_code())) {
			List<Bank> bankList = bankInfoService.queryBankList(null);
			StringBuffer support = new StringBuffer("很抱歉，充值失败！<br/>").append("请更换您的银行卡！<br/>").append("移动充值仅支持以下14家银行：<br/>");
			for (Bank vo : bankList) {
				support.append("◆ ").append(vo.getBankName()).append("<br/>");
			}
			llWapBankcardInfo.setResultMsg(support.toString());
		}
		return llWapBankcardInfo;
	}

	/**
	 * <p>
	 * Description:获取连连支付支持的银行信息<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月11日
	 * @param district
	 * @return List<Bank>
	 */
	@RequestMapping(value = "/queryBankList")
	@ResponseBody
	public List<Bank> queryBankList(@RequestParam("district") String district) {
		return bankInfoService.queryBankList(district);
	}

	/**************************************************** begin private method *****************************/
	private Boolean verifyMobail(String mobile) {
		if (mobile.trim().length() != 11) {
			return false;
		}
		try {
			Long.parseLong(mobile.trim());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/**************************************************** end private method *****************************/
	/**
	 * 
	 * <p>
	 * Description:校验验证码是否正确<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月22日
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "/isValiteCode")
	@ResponseBody
	public MessageBox isValiteCode(String validateCode,String mobile){
		 
		String randCode = (String) currentSession().getAttribute("randomCode");
		try {
		if (null == validateCode || null == randCode || !validateCode.equals(randCode)) {
			return new MessageBox("0", "验证码输入有误！");
		}
		mobileApproService.sendMobileApprValidate(mobile, currentRequest(), "", BusinessConstants.MOBILE_APPRO_FUNCTION,
					BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST);
		} catch (Exception e) {
			logger.error("微信推广", e);
		}
		return new MessageBox("1","");
	}
}
