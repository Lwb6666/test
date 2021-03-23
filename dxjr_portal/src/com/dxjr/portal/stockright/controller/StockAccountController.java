package com.dxjr.portal.stockright.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockDealService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.StockApplyRequest;
import com.dxjr.portal.stockright.vo.StockDealRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.security.ShiroUser;


/***
 * 
 * <p>
 * Description:申请审核<br />
 * </p>
 * @title ApplyController.java
 * @package com.dxjr.console.stock.controller 
 * @author xinwugang
 * @version 0.1 2016-5-9
 */
@Controller
@RequestMapping(value="/stockAccount")
public class StockAccountController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(StockAccountController.class);
	@Autowired
	private AccountService accountServiceImp;
	@Autowired
	private StockEntrustService stockEntrustService;
	@Autowired
	private StockAccountService stockAccountService;
	@Autowired
	private StockDealService stockDealService;
	@Autowired
	private StockApplyService stockApplyService;
	@Autowired
	private MemberService memberService;
	
	/****
	 * 用户股权账户信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-11
	 * @return
	 * ModelAndView
	 */
	@RequestMapping("/accountIndex")
	public ModelAndView accountIndex(){
		ModelAndView mv = null;
		AccountVo accountVo = null;
		try {
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				mv = new ModelAndView("/member/login");
				return mv;
			}
			
			//==============================验证黑名单==============================
			Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
			if(bl==false){
				mv = new ModelAndView("/common/404");
				return mv;
			}
			//=============================验证黑名单==============================
			Integer count = stockApplyService.countApplyInfo(loginuser.getUserId());
			if(count==0){
				mv = new ModelAndView("/common/404");
				return mv;
			}
			
			mv = new ModelAndView("/stockright/account/accountIndex");
			//查询用户资金账户信息
			accountVo = accountServiceImp.queryAccountByUserId(loginuser.getUserId());
			//查询用户委托信息
			StockEntrustCnd entrustCnd = new StockEntrustCnd();
			entrustCnd.setUserId(loginuser.getUserId());
			entrustCnd.setSort(3);
			List<StockEntrust> entrustList = stockEntrustService.queryList(entrustCnd, 10);
			mv.addObject("accountVo", accountVo).addObject("entrustList", entrustList).addObject("accountVo", accountVo);
			
			StockAccount account = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
			mv.addObject("account", account);
			//统计当前用户转让股权总数
			StockDealRequest  request = new StockDealRequest();
			request.setSellerId(loginuser.getUserId());
			List<Map<String,Object>> sumSeller = stockDealService.summaryStockDeal(request);
			mv.addObject("sumSeller", sumSeller.get(0));
			
			//统计当前用户认购股权总数
			request.setBuyerId(loginuser.getUserId());
			request.setSellerId(null);
			List<Map<String,Object>> sumBuyer = stockDealService.summaryStockDeal(request);
			mv.addObject("sumBuyer", sumBuyer.get(0));
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(loginuser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			loginuser.setHeadImg(memberVo.getHeadimg());
			mv.addObject("memberVo", loginuser);
			return mv;
		} catch (Exception e) {
			logger.error("获取个人资产信息失败；"+e.getMessage());
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	/****
	 * 暂时用的进入股权账户页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-19
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/toStockIndex")
	public ModelAndView toStockIndex() {
		ModelAndView mv = null;
		mv = new ModelAndView("/stockright/common/index");
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
			return mv;
		}
		StockAccount account = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
		if(loginuser.getUserId()!=2){
			StockApplyRequest stockApp = new StockApplyRequest();
			stockApp.setUserId(loginuser.getUserId());
			Integer count = stockApplyService.checkBlackList(stockApp);
			if(count>0){
				if(account == null || account.getTotal()<=0){
					mv.addObject("isBlack", "false");
				}
			}
			
		}
		
		return mv;
	}
}
