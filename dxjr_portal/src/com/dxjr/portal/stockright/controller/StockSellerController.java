package com.dxjr.portal.stockright.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.Dictionary;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.stockright.entity.EntrustType;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockDeal;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockDealService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.StockDealRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;


/**
 * 
 * <p>
 * Description:股权转让方controller<br />
 * </p>
 * @title StockSellerController.java
 * @package com.dxjr.portal.stockright.controller 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
@Controller
@RequestMapping(value="/stockSeller")
public class StockSellerController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(StockSellerController.class);
	@Autowired
	private StockAccountService stockAccountService;
	
	@Autowired
	private StockDealService stockDealService;
	
	@Autowired
	private StockEntrustService stockEntrustService;
	
	@Autowired
	private AccountService accountServiceImp;
	@Autowired
	private StockApplyService stockApplyService;
	/**
	 * 
	 * <p>
	 * Description:进入股权转让页面<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/sellerIndex")
	public ModelAndView sellerIndex(){
		ModelAndView mv = null;
		try {
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				mv = new ModelAndView("/member/toLoginPage");
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
			mv = new ModelAndView("/stockright/entrust/sellermain");
			//获取当前用户资金账户信息
			AccountVo accountVo = accountServiceImp.queryAccountByUserId(loginuser.getUserId());
			mv.addObject("accountVo", accountVo);
			
			//检索用户股权账户信息
			StockAccount account = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
			mv.addObject("account", account);
			
			//获取最近一条成交记录
			List<StockDeal> dealRecord =stockDealService.queryFistStockDeal();
			mv.addObject("dealRecord", dealRecord);
			
			StockEntrustCnd entrustCnd = new StockEntrustCnd();
			//获取5条转让数据  金额从大到小
			entrustCnd.setEntrustType(EntrustType.SELL.getCode());
			entrustCnd.setIsResidue("yes");
			List<StockEntrust> sellerList = stockEntrustService.queryList(entrustCnd, 5);
			mv.addObject("sellerList", sellerList);
			
			//获取5条认购数据  金额从大到小
			entrustCnd.setEntrustType(EntrustType.BUY.getCode());
			List<StockEntrust> buyerList = stockEntrustService.queryList(entrustCnd, 5);
			mv.addObject("buyerList", buyerList);
			
		} catch (Exception e) {
			logger.error("查询转让方内转信息失败"+e.getMessage());
			e.printStackTrace();
		}
		return mv.addObject("minPrice", Dictionary.getValue(1901,"price_min")).addObject("maxPrice", Dictionary.getValue(1901,"price_max")).addObject("stockIncrease", Dictionary.getValue(1903,"stock_increase")).addObject("sellerRate", Dictionary.getValue(1902,"seller_rate"));
	}
	
	
	/***
	 * 跳转到委托列表页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-13
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/toEntrustMain")
	public ModelAndView toEntrustMain() {
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
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
		mv = new ModelAndView("/stockright/entrust/entrustmain");
		
		return mv;
	}
	
	/**
	 * 
	 * <p>
	 * Description:分页获取委托记录列表<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param entrustCnd
	 * @param pageSize
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/queryPageList")
	public ModelAndView queryList(@ModelAttribute StockEntrustCnd entrustCnd, @RequestParam Integer pageNum){
		ModelAndView mv = null;
	try{	
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
			return mv;
		}
		Integer count = stockApplyService.countApplyInfo(loginuser.getUserId());
		if(count==0){
			mv = new ModelAndView("/common/404");
			return mv;
		}
		mv = new ModelAndView("/stockright/entrust/entrustlist");
		entrustCnd.setUserId(loginuser.getUserId());
		Page pag = new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE);
		pag = stockEntrustService.queryPageList(entrustCnd,pag);
		mv.addObject("page", pag);
	} catch (Exception e) {
		logger.error("查询用户委托列表"+e.getMessage());
		e.printStackTrace();
	}
	return mv;
	}
	
	/****
	 * 根据委托id查询委托详情
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/findEntrustDetail/{entrustId}")
	public ModelAndView findEntrustDetail(@PathVariable("entrustId") Integer entrustId){
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
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
		StockEntrustCnd entrustCnd = new StockEntrustCnd();
		entrustCnd.setId(entrustId);
		entrustCnd.setUserId(loginuser.getUserId());
		StockEntrust entrust = stockEntrustService.queryEntrustForUpdate(entrustCnd);
		mv = new ModelAndView("/stockright/entrust/entrustDetailMain");
		return mv.addObject("entrust", entrust);
	}
	
	/***
	 * 查询委托的交易详细信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/findEntrustDetailList")
	public ModelAndView findEntrustDetailList(@ModelAttribute StockEntrustCnd entrustCnd, @RequestParam Integer pageNum){
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
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
		mv = new ModelAndView("/stockright/entrust/entrustDetailList");
		Page pag = new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE);
		StockDealRequest deal = new StockDealRequest();
		//认购
		if(entrustCnd.getEntrustType()==1){
			deal.setBuyerEntrustId(entrustCnd.getId());
			deal.setBuyerId(loginuser.getUserId());
		}
		//转让
		if(entrustCnd.getEntrustType()==2){
			deal.setSellerEntrustId(entrustCnd.getId());
			deal.setSellerId(loginuser.getUserId());
		}
		pag = stockDealService.queryPageList(deal,pag);
		mv.addObject("page", pag).addObject("entrustId", entrustCnd.getId()).addObject("userId", loginuser.getUserId());
		return mv;
	}
	
	 /**
	  * 
	  * <p>
	  * Description:转让方撤单<br />
	  * </p>
	  * @author xiaofei.li
	  * @version 0.1 2016-5-18
	  * @param entrustCnd
	  * @param req
	  * @return
	  * MessageBox
	  */
		@RequestMapping(value="/revokeEntrust")
		@ResponseBody
		public MessageBox revokeEntrust(StockEntrustCnd entrustCnd, HttpServletRequest req){
			MessageBox result = null;
			try{
				ShiroUser loginuser=currentUser();
				if(loginuser==null){
					return new MessageBox("20000", "用户未登陆！");
				}
				//参数校验
				if(entrustCnd.getId() == null){
					return  new MessageBox("30000", "非法参数提交！");
				}
				//==============================验证黑名单==============================
				Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
				if(bl==false){
					return  new MessageBox("30000", "撤单失败，请刷新重试！");
				}
				//=============================验证黑名单==============================
				entrustCnd.setUpdateIp(HttpTookit.getRealIpAddr(req));
				entrustCnd.setUserId(loginuser.getUserId());
				result = stockEntrustService.saveRevokeEntrust(entrustCnd);
			}catch(Exception e){
				logger.error("转让方撤单失败"+e.getMessage());
				result = new MessageBox("40000", "撤单失败！"+e.getMessage());
				e.printStackTrace();
			}
			return result;
			
		}
}
