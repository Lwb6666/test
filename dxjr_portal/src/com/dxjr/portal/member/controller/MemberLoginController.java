package com.dxjr.portal.member.controller;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.chart.mapper.FinanceChartMapper;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.newP.service.NewPService;
/**
 * 
 * <p>
 * Description:pc端推广、直接、推荐注册页面。<br />
 * </p>
 * @title MemberLoginController.java
 * @package com.dxjr.portal.member.controller 
 * @author yubin
 * @version 0.1 2015年10月14日
 */
@Controller
@RequestMapping(value = "/user")
public class MemberLoginController extends BaseController {
	
    private static final Logger logger=Logger.getLogger(MemberLoginController.class);
	@Autowired
	private NewPService newPService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private FinanceChartMapper financeChartMapper;
	@Autowired
	private FirstBorrowService firstBorrowService;
	@Autowired
	private FixBorrowService fixBorrowService;
	@Autowired
	private MemberMapper memberMapper;
	
	@RequestMapping(value = "/userRegiste")
	public ModelAndView userRegistration(){
		return new  ModelAndView("member/userRegiste");
	}
	@RequiresAuthentication
	@RequestMapping(value="/registeSuccessPage")
	public ModelAndView validationSuccess(String username)throws Exception{
		ModelAndView mv=new ModelAndView("member/userValidationSuccess");
		//倒计时图标是否显示
		String flag=(String) currentSession().getAttribute("flag");
		if(flag==null){
			mv.addObject("flag", "0");
			currentSession().setAttribute("flag", "1");
		}else{
			mv.addObject("flag", "1");
		}
		// 新增新手标记录
		BorrowVo borrowNew;
		try {
			borrowNew = newPService.getAdvancedNew();
			if (borrowNew != null) {
				mv.addObject("borrowNew", borrowNew);
			}
		} catch (Exception e) {
			logger.error("首页取新手标报错", e);
		} 
		int signAwardNum=memberMapper.getSignAwardNum();
		return mv.addObject("username",URLDecoder.decode(username,"utf-8")).addObject("signAwardNum",signAwardNum);
	}
	@RequestMapping(value = "/memberRegiste")
	public ModelAndView memberRegistration(){
		 
		Map<String, String> map = new HashMap<String, String>();
		try {
			BigDecimal TotalMoney=borrowService.getTotalMoney();//获取成交总金额
			BigDecimal investerNetMoney = financeChartMapper.queryInvesterNetMoney();// 投资者总收益
			Map<String, Object> firstData = firstBorrowService.queryFirstData();// 投标直通车总额
			map.put("firstTotalAccountMoney",String.valueOf(new BigDecimal(firstData.get("firstTotalAccount").toString()).divide(new BigDecimal(10000)).intValue()));
            map.put("TotalMoney", String.valueOf(TotalMoney.divide(new BigDecimal(10000)).intValue()));
            map.put("investerNetMoney", String.valueOf(investerNetMoney.divide(new BigDecimal(10000)).intValue()));
		} catch (Exception e) {
			logger.error("推广页面", e);
		}
		return new  ModelAndView("member/memberRegiste").addObject("map", map);
	}
	@RequestMapping(value="/newTender")
	@ResponseBody
    public MessageBox newTender(){
    	// 新增新手标记录
		BorrowVo borrowNew;
		try {
			borrowNew = newPService.getAdvancedNew();
			if (borrowNew != null) {
				return MessageBox.build("1", String.valueOf(borrowNew.getId()));
			}
		} catch (Exception e) {
			MessageBox.build("0", "0");
			logger.error("首页取新手标报错", e);
		} 
    	return MessageBox.build("0", "0");
    }
	@RequiresAuthentication
	@RequestMapping(value="/memberSuccess")
	public ModelAndView memberValidation(String username)throws Exception{
		ModelAndView mv=new ModelAndView("member/memberValidationSuccess");
		//倒计时图标是否显示
		String flag=(String) currentSession().getAttribute("flag");
		if(flag==null){
			mv.addObject("flag", "0");
			currentSession().setAttribute("flag", "1");
		}else{
			mv.addObject("flag", "1");
		}
		
		// 新增新手宝记录
		FixBorrowVo fixBorrowNew=null;
		try {
			fixBorrowNew = fixBorrowService.getNewFixBorrow();
			if (fixBorrowNew != null) {
				mv.addObject("fixBorrowNew", fixBorrowNew);
			}
		} catch (Exception e) {
			logger.error("首页取新手宝报错", e);
		}
		return mv.addObject("username",URLDecoder.decode(username,"utf-8"));
	}
 
}
