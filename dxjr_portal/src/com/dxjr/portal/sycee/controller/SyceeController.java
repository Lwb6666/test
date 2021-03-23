package com.dxjr.portal.sycee.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.aop.annotation.GenerateToken;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.sycee.entity.SyceeAddress;
import com.dxjr.portal.sycee.entity.SyceeGoods;
import com.dxjr.portal.sycee.service.SyceeService;
import com.dxjr.utils.CSRFTokenManager;
import com.dxjr.utils.HttpTookit;

/**
 * 元宝商城
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title SyceeController.java
 * @package com.dxjr.portal.sycee.controller
 * @author huangpin
 * @version 0.1 2015年10月23日
 */
@Controller
@RequestMapping(value = "/sycee")
public class SyceeController extends BaseController {

	private static final Logger logger = Logger.getLogger(SyceeController.class);

	@Autowired
	private SyceeService syceeService;

	@Autowired
	private MemberService memberService;

	// @RequestMapping(value = "")
	// public ModelAndView waiting() {
	// return new ModelAndView("/sycee/waiting");
	// }

	/**
	 * 元宝商城-首页
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月23日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "")
	@GenerateToken
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("/sycee/index");
		try {
			// 登录用户
			if (currentUser() != null) {
				MemberCnd memberCnd = new MemberCnd();
				memberCnd.setId(currentUser().getUserId());
				MemberVo m = memberService.queryMemberByCnd(memberCnd);
				mv.addObject("userSycee", m.getAccumulatepoints());// 总元宝
				mv.addObject("userHeadImg", currentUser().getHeadImg());// 用户头像
				mv.addObject("signItem", syceeService.getSignItem());// 签到帖
				mv.addObject("addr", syceeService.getById(currentUser().getUserId()));// 收货地址
			}
			// 线上商品
			mv.addObject("onlineGoods", syceeService.syceeClassList(1));
			// 线下商品
			mv.addObject("offlineGoods", syceeService.syceeClassList(2));
			// 折扣标记，1代表打折
			mv.addObject("discountFlag", syceeService.getSyceeDiscountFlag());
			mv.addObject("csrf", CSRFTokenManager.getTokenForSession());
		} catch (Exception e) {
			logger.error("元宝商城首页加载异常", e);
		}
		return mv;
	}

	/**
	 * 商品详情，@RequiresAuthentication登录才可访问
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月24日
	 * @param goodsId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/goods/{goodsId}")
	@RequiresAuthentication
	public ModelAndView syceeGoodsInfo(@PathVariable("goodsId") int goodsId) {
		ModelAndView mv = new ModelAndView("/sycee/goodsDiv");
		SyceeGoods goods = syceeService.getGoodsInfo(goodsId);
		if (goods.getFirstClass().intValue() == 2 && goods.getSecondClass() == 3) {
			mv.addObject("exchangeTimes", syceeService.getExchangeTimes(currentUser().getUserId()));
		}
		int discountFlag = syceeService.getSyceeDiscountFlag();// 折扣标记，1代表打折
		int surplusSycee = 0;// 兑换后剩余元宝
		if (discountFlag == 1) {
			surplusSycee = syceeService.getUserSycee(currentUser().getUserId()) - goods.getSycee() * goods.getNum();// 打折
		} else {
			surplusSycee = syceeService.getUserSycee(currentUser().getUserId()) - goods.getOldSycee() * goods.getNum();// 不打折
		}
		mv.addObject("discountFlag", discountFlag);
		mv.addObject("goods", goods);
		mv.addObject("surplusSycee", surplusSycee);

		return mv;
	}

	/**
	 * 兑换商品提交
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月26日
	 * @param goodsId
	 * @return MessageBox
	 */
	@RequestMapping(value = "/exchange/goods/{goodsId}")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox subExchange(@PathVariable("goodsId") int goodsId, int num) {
		try {
			String ip = HttpTookit.getRealIpAddr(currentRequest());
			return syceeService.addExchange(currentUser().getUserId(), goodsId, ip, num);
		} catch (Exception e) {
			logger.error("【用户" + currentUser().getUserId() + "】元宝兑换商品异常", e);
			return new MessageBox("0", "兑换失败");
		}
	}

	/**
	 * 添加联系地址
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月6日
	 * @param zip_code
	 * @param phone
	 * @param name
	 * @param address
	 * @return MessageBox
	 */
	@RequestMapping(value = "/address/add")
	@RequiresAuthentication
	@ResponseBody
	public MessageBox addAddress(@ModelAttribute SyceeAddress syceeAddress) {
		SyceeAddress address = syceeService.getById(currentUser().getUserId());
		if (address != null) {
			try {
				return syceeService.updateAddr(syceeAddress, currentUser().getUserId());
			} catch (Exception e) {
				logger.error("【用户" + currentUser().getUserId() + "】修改联系地址失败", e);
				return new MessageBox("0", "修改失败！");
			}
		} else {
			syceeAddress.setUserId(currentUser().getUserId());
			syceeAddress.setAddtime(new Date());
			try {
				return syceeService.addAddress(syceeAddress);
			} catch (Exception e) {
				logger.error("【用户" + currentUser().getUserId() + "】添加联系地址失败", e);
				return new MessageBox("0", "添加失败！");
			}
		}

	}
	/**
	 * 回显地址
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author 刘涛
	 * @version 0.1 2016年04月13日
	 * @param goodsId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/updateAddress")
	@RequiresAuthentication
	@ResponseBody
	public SyceeAddress updateAddress() {
		SyceeAddress syceeAddress =syceeService.getById(currentUser().getUserId());
		return syceeAddress;
	}
}
