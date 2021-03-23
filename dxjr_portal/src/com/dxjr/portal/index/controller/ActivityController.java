package com.dxjr.portal.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.DoubleElevenVo;
import com.dxjr.portal.fix.vo.FixActivityCnd;
import com.dxjr.portal.fix.vo.FixActivityVo;
import com.dxjr.security.ShiroUser;

/**
 * @author WangQianJin
 * @title 活动Controller
 * @date 2015年11月10日
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController {

	private final static Logger logger = Logger.getLogger(ActivityController.class);	

	@Autowired
	private FixBorrowService fixBorrowService;

	
	/**
	 * 双十一活动
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2015年11月9日
	 */
	@RequestMapping(value = "/doubleEleven")
	public ModelAndView doubleEleven(HttpServletRequest request,HttpServletResponse response) {		
		ModelAndView mv = new ModelAndView("/index/doubleEleven");
		List<DoubleElevenVo> threeList=null;
		List<DoubleElevenVo> sixList=null;		
		try {
			//获取3月宝投资排名前10名
			threeList=fixBorrowService.queryFixForDoubleElevenList(3);
			//获取6月宝投资排名前10名
			sixList=fixBorrowService.queryFixForDoubleElevenList(6);		
		} catch (Exception e) {
			logger.error("双十一活动查询失败.", e);
			e.printStackTrace();
		}
		return mv.addObject("threeList", threeList).addObject("sixList", sixList);
	}
	/**
	 * 红包活动
	 * @author liutao
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2015年12月02日
	 */
	@RequestMapping(value = "/redActivity")
	public ModelAndView redHuoDong(HttpServletRequest request,HttpServletResponse response) {		
		return new ModelAndView("/index/redActivity");
   }
	/**
	 * 双旦活动
	 * @author liutao
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/doubleDan")
	public ModelAndView doubleDan(HttpServletRequest request,HttpServletResponse response) {		
		return new ModelAndView("/index/doubleDanActivity");
    }
	
	/**
	 * 贺新春迎元宵活动
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年1月27日
	 */
	@RequestMapping(value = "/newYear")
	public ModelAndView newYear(HttpServletRequest request,HttpServletResponse response) {		
		ModelAndView mv = new ModelAndView("/activity/newYear");
		List<DoubleElevenVo> newYearList=null;		
		try {		
			FixActivityCnd cnd=new FixActivityCnd();
			cnd.setLimitStr("3,6,12");
			//获取3月宝、6月宝和12月宝投资排名前10名
			newYearList=fixBorrowService.queryFixForNewYearList(cnd);		
		} catch (Exception e) {
			logger.error("贺新春迎元宵活动查询失败.", e);
			e.printStackTrace();
		}
		return mv.addObject("newYearList", newYearList);
	}
	
	/**
	 * 新春巨献活动
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年1月27日
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/invited")
	public ModelAndView invited(HttpServletRequest request,HttpServletResponse response) {		
		ModelAndView mv = new ModelAndView("/activity/invited");
		List<FixActivityVo> invitedList=null;
		int invitedNum=0;
		int invitedMoney=0;
		try {	
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return redirect("/member/toLoginPage.html");
			}
			//查询2月1号以后当月注册并投资的金额大于等于1W的被邀请人信息
			invitedList=fixBorrowService.queryFixForInvitedList(shiroUser.getUserId());	
			if(invitedList!=null){
				invitedNum=invitedList.size();
			}
			if(invitedNum==1){
				invitedMoney=20;
			}else if(invitedNum==2){
				invitedMoney=80;
			}else if(invitedNum>=3){
				invitedMoney=150;
			}
			// 查询推荐链接
			String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5();
			mv.addObject("recommendPath", recommendPath);
		} catch (Exception e) {
			logger.error("新春巨献活动查询失败.", e);
			e.printStackTrace();
		}
		return mv.addObject("invitedList", invitedList).addObject("invitedNum",invitedNum).addObject("invitedMoney",invitedMoney);
	}
	
	/**
	 * 5月50亿活动
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年5月10日
	 */
	@RequestMapping(value = "/fiftyBillion")
	public ModelAndView fiftyBillion(HttpServletRequest request,HttpServletResponse response) {		
		return new ModelAndView("/activity/fiftyBillion");
    }
	/**
	 * 资金存管活动
	 * @author 刘涛
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年6月22日
	 */
	@RequestMapping(value = "/zjcg")
	public ModelAndView zjcg(HttpServletRequest request,HttpServletResponse response) {		
		return new ModelAndView("/activity/autoTenderActivity");
    }
	/**
	 * 8月份活动
	 * @author 刘涛
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2016年07月26日
	 */
	@RequestMapping(value = "/bayue")
	public ModelAndView bayue(HttpServletRequest request,HttpServletResponse response) {		
		return new ModelAndView("/activity/bayueActivity");
    }
}
