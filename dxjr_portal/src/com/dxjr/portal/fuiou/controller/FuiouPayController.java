package com.dxjr.portal.fuiou.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.Dictionary;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.fuiou.service.FuiouPayService;
import com.dxjr.portal.fuiou.util.FuiouUtil;
import com.dxjr.portal.fuiou.vo.FuiouPayBackVo;
import com.dxjr.portal.fuiou.vo.FuiouPayVo;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.httputil.HttpURLUtil;

/**
 * 充值-富友支付
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title FuiouPayController.java
 * @package com.dxjr.portal.fuiou.controller
 * @author huangpin
 * @version 0.1 2015年8月20日
 */
@Controller
@RequestMapping(value = "/account/topup/fuioupay")
public class FuiouPayController extends BaseController {

	private final static Logger logger = Logger.getLogger(FuiouPayController.class);

	@Autowired
	FuiouPayService fuiouPayService;

	/**
	 * 富友支付-初始化银行列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "bankList")
	public ModelAndView bankList() {
		ModelAndView mv = new ModelAndView("account/topup/fuioupay/bankList");
		return mv.addObject("banks", Dictionary.getValues(80706));
	}

	/**
	 * 发送支付请求
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param money 充值金额
	 * @param paybank 富友银行代码
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send")
	@RequiresAuthentication
	public ModelAndView sub(TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("account/topup/fuioupay/fuiouPage");
		try {
			Float money=null;
			String  paybank=null;
			if (null!=topupMoneyVo&&topupMoneyVo.getMoney().compareTo(new BigDecimal("0")) >0) {
				  money=(float) topupMoneyVo.getMoney().doubleValue();
			}
			if (null!=topupMoneyVo&&StringUtils.isNotEmpty(topupMoneyVo.getBankcode())) {
				  paybank=topupMoneyVo.getBankcode();
			}
			if (money == null || paybank == null || money <= 0 || "".equals(paybank)) {
				return new ModelAndView("account/topup/topupResult");
			}

			// 处理参数
			FuiouPayVo vo = new FuiouPayVo();
			money = money * 100;
			vo.setOrder_amt(money.intValue() + "");// 富友订单金额以分为单位，不能有小数点
			vo.setIss_ins_cd(paybank);// 富友银行代码
			vo.setOrder_id(DateUtils.getNow(DateUtils.YMDHMSSS) + "U" + currentUser().getUserId());// 订单号

			// 封装表单
			mv.addObject("fmStr", FuiouUtil.generateForm(vo, "fm", null));

			// 添加充值记录
			int n = fuiouPayService.saveRecharge(vo, HttpTookit.getRealIpAddr(currentRequest()), currentUser());
			if (n <= 0) {
				return new ModelAndView("account/topup/topupResult");
			}
		} catch (Exception e) {
			logger.error("跳转富友支付异常", e);
		}
		return mv;
	}

	/**
	 * 支付请求返回页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param vo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "send/backPage")
	public ModelAndView backPage(FuiouPayBackVo vo) {
		ModelAndView mv = new ModelAndView("account/topup/topupResult");
		// 支付失败
		String topupResult = "failure";
		try {
			if (null != vo && "0000".equals(vo.getOrder_pay_code()) && FuiouUtil.checkBackMd5(vo) && "11".equals(vo.getOrder_st())) {
				topupResult = "success";
			}
		} catch (Exception e) {
			logger.error("富友支付请求返回页面异常：" + vo.getOrder_pay_code() + vo.getOrder_pay_error(), e);
		}
		mv.addObject("topupResult", topupResult);
		return mv;
	}

	/**
	 * 支付请求后台通知处理
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param vo void
	 */
	@RequestMapping(value = "send/backNotify")
	@ResponseBody
	public void backNotify(FuiouPayBackVo vo) {
		try {
			if (null != vo && null != vo.getOrder_id()) {
				fuiouPayService.updateRecharge(vo, HttpTookit.getRealIpAddr(currentRequest()));
			}
		} catch (Exception e) {
			logger.error("富友支付请求后台通知处理异常：" + vo.getOrder_pay_code() + vo.getOrder_pay_error(), e);
		}
	}

	/**
	 * 进入补单页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toLoseOrderIndex")
	public ModelAndView lose() {
		ModelAndView mv = new ModelAndView("account/topup/fuioupay/loseOrderIndex");
		return mv;
	}

	/**
	 * 提交补单
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @param v_oid
	 * @return String
	 */
	@RequestMapping(value = "loseSend")
	@ResponseBody
	public String loseSend(String v_oid) {
		try {
			// 查询富友订单
			v_oid = v_oid.trim();
			String returnXmlStr = HttpURLUtil.doPost(FuiouUtil.query_pay_url + FuiouUtil.queryPayArguments(v_oid), "", "UTF-8");
			String code = FuiouUtil.getXmlCode(returnXmlStr, "order_pay_code");
			String order_st = FuiouUtil.getXmlCode(returnXmlStr, "order_st");// 订单状态：11 – 订单已支付
			if ("0000".equals(code) && "11".equals(order_st)) {
				// 补单
				return fuiouPayService.updateRecharge(v_oid, HttpTookit.getRealIpAddr(currentRequest()));
			}
			return "补单失败【返回码：" + code + ";订单状态：" + order_st + "】";
		} catch (Exception e) {
			logger.error("富友支付补单异常", e);
			return "补单失败：系统异常";
		}
	}

}
