package com.dxjr.portal.account.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.memberaccumulatepoints.service.MemberAccumulatePointsService;
import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * 我的账户-有奖活动-我的元宝
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title MySyceeController.java
 * @package com.dxjr.portal.account.controller
 * @author huangpin
 * @version 0.1 2015年10月13日
 */
@Controller
@RequestMapping(value = "/account/sycee")
public class MySyceeController extends BaseController {

	private static final Logger logger = Logger.getLogger(MySyceeController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberAccumulatePointsService syceeService;

	/**
	 * 我的元宝
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月19日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "")
	@RequiresAuthentication
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("account/sycee/syceeMain");
		try {
			int userId = currentUser().getUserId();
			UserLevelRatio ul = memberService.queryUserLevel(userId);
			mv.addObject("ul", ul);// 等级信息

			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(userId);
			MemberVo m = memberService.queryMemberByCnd(memberCnd);
			mv.addObject("sycee", m.getAccumulatepoints());// 总元宝
			mv.addObject("honor", m.getHonor());// 荣誉值

			mv.addObject("lastdaySycee", syceeService.getLastdaySycee(userId));// 昨日元宝

		} catch (Exception e) {
			logger.error("我的元宝加载异常", e);
		}
		return mv;
	}

	/**
	 * 我的元宝-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月19日
	 * @param pageNo
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "/list/{pageNo}")
	@RequiresAuthentication
	public ModelAndView list(@PathVariable Integer pageNo) throws Exception {
		ModelAndView mv = new ModelAndView("account/sycee/syceeList");
		mv.addObject("page", syceeService.mySyceeList(currentUser().getUserId(), pageNo, 20));
		return mv;
	}

}
