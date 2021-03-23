package com.dxjr.portal.sinapay.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.sinapay.protocol.SinaPayLoserSupport;
import com.dxjr.portal.sinapay.service.SinapayService;
import com.dxjr.portal.sinapay.tool.CallServiceUtil;
import com.dxjr.portal.sinapay.tool.Tools;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.file.FileUtil;

/**
 * 
 * <p>
 * Description:用户充值Controller<br />
 * </p>
 * 
 * @title TopupController.java
 * @package com.dxjrweb.account.account.controller
 * @author gang.li
 * @version 0.1 2013年9月9日
 */
@Controller
@RequestMapping(value = "/account/topup/sinapay")
public class SinapayController extends BaseController {
	private final static Logger logger = Logger.getLogger(SinapayController.class);

	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private SinapayService sinapayService;
	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * Description:跳转到新浪支付页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toIndex")
	public ModelAndView toSinapay() {
		ModelAndView mv = new ModelAndView("account/topup/sinapay/sinapayIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:使用新浪进行在线充值，进入银行充值页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @author 胡建盼
	 * @version 0.1 2014年5月22日
	 * @param topupMoneyVo
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send")
	@RequiresAuthentication
	public ModelAndView send(TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("account/topup/sinapay/sinapaySend");
		try {
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
				mav.setViewName("redirect:/account/topup/toTopupIndex.html");
				return mav;
			}
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				return redirect("/AccountSafetyCentre/safetyIndex.html");
			}

			// 判断是否通过了实名认证
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				return redirect("/account/approve/realname/toRealNameAppro.html");
			}

			// 保存充值记录
			topupMoneyVo.setPlatform(shiroUser.getPlatform());
			Map<String, Object> resultMap = sinapayService.savesend(shiroUser.getUserId(), topupMoneyVo, request, response);
			if (!resultMap.get("result").equals("success")) {
				// 返回到充值失败页面
				mav.setViewName("account/topup/topupResult");
				mav.addObject("topupResult", "failure");
				return mav;
			}
			mav.addObject("sinapayRequestString", resultMap.get("sinapayRequestString"));
		} catch (Exception e) {
			e.printStackTrace();
			// 返回到充值失败页面
			mav.setViewName("account/topup/topupResult");
			mav.addObject("topupResult", "failure");
			return mav;
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:新浪支付客户充值完调用查看返回商户页面执行的方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "recevie")
	public ModelAndView shengpayRecevie(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		String topupResult = "";
		topupResult = "success";// 支付成功
		mv.addObject("topupResult", topupResult);
		return mv;
	}

	/**
	 * <p>
	 * Description:跳转到新浪在线的补单页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toLoseOrderIndex")
	@ResponseBody
	public ModelAndView toLoseOrder() {
		ModelAndView mv = new ModelAndView("account/topup/sinapay/loseOrderIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:进行新浪充值补单操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "loseSend")
	@ResponseBody
	public String loseSend(String v_oid, String startTime, String endTime, HttpServletRequest request) {

		String queryUrl = BusinessConstants.ONLINE_PAY_SINAPAY_SERVICEURL;
		String _input_charset = BusinessConstants.ONLINE_PAY_SINAPAY_INPUT_CHARSET;// 字符编码
		final String MD5Key = BusinessConstants.ONLINE_PAY_SINAPAY_MD5KEY;
		java.util.Date currentTime = new java.util.Date();// 得到当前系统时间
		String str_date1 = DateUtils.format(currentTime, DateUtils.YMDHMS); // 将日期时间格式化

		String RSASignKey;
		try {
			RSASignKey = FileUtil.readFile(request.getRealPath("") + "/WEB-INF/classes/sinapay/rsa_sign_private.pem");
		} catch (IOException e1) {
			return "补单失败，未找到密钥！";
		}

		// 校验输入参数是否为空
		final String verifiyParameterIsNull = SinaPayLoserSupport.verifiyParameterHasValueForLoser(v_oid, startTime, endTime);
		// 有错误提示则返回
		if (!StringUtils.isEmpty(verifiyParameterIsNull)) {
			return verifiyParameterIsNull;
		}

		String start_time = null;
		String end_time = null;
		try {
			start_time = DateUtils.format(DateUtils.parse(startTime, DateUtils.YMD_SLAHMS), DateUtils.YMDHMS);
			end_time = DateUtils.format(DateUtils.parse(endTime, DateUtils.YMD_SLAHMS), DateUtils.YMDHMS);
		} catch (Exception e) {
			return "时间格式有误！";
		}

		Map<String, String> requestParamterMap = SinaPayLoserSupport.initQueryB2COrderParameter(v_oid, start_time, end_time, str_date1);

		SinaPayLoserSupport.generateSignForQuery(_input_charset, BusinessConstants.ONLINE_PAY_SINAPAY_SIGN_TYPE, MD5Key, RSASignKey, requestParamterMap);

		try {
			String param = Tools.createLinkString(requestParamterMap, true);
			// 发起 交易查询(对账查询)
			String queryResult = URLDecoder.decode(CallServiceUtil.sendPost(queryUrl, param), _input_charset);
			Map<String, String> queryContent = (Map<String, String>) com.dxjr.portal.util.JsonUtils.json2Map(queryResult);
			String signKey = "";
			if (BusinessConstants.ONLINE_PAY_SINAPAY_MD5.equalsIgnoreCase(BusinessConstants.ONLINE_PAY_SINAPAY_SIGN_TYPE)) {
				signKey = MD5Key;
			} else {
				signKey = FileUtil.readFile(request.getRealPath("") + "/WEB-INF/classes/sinapay/rsa_sign_public.pem");
			}
			final boolean check_sign = SinaPayLoserSupport.checkSignForQueryOrder(queryContent, signKey);
			if (check_sign) {
				if (SinaPayLoserSupport.isQueryResult(queryContent)) {

					return SinaPayLoserSupport.processLoseOrder(v_oid, request, queryContent, sinapayService);

				}
			} else {
				return "数据签名验证失败";
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}

		return "没有找到订单！";
	}

}
