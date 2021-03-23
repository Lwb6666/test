package com.dxjr.portal.stockright.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.stockright.entity.StockDeal;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockDealService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.StockEntrustApplyCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.lowagie.text.DocumentException;

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
@RequestMapping(value="/stockDeal")
public class StockDealController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(StockDealController.class);
	
	@Autowired
	private StockDealService stockDealService;
	@Autowired
	private StockEntrustService stockEntrustService;
	@Autowired
	private StockApplyService stockApplyService;

	/**
	 * 
	 * <p>
	 * Description:转让方委托单申请<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/sellerApplyEntrust")
	@ResponseBody
	public MessageBox sellerApplyEntrust(StockEntrustApplyCnd entrustCnd, HttpServletRequest req){
		MessageBox result = null;
		try{
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				return new MessageBox("20000", "用户未登陆！");
			}
			//参数协议参数
			if(entrustCnd.getPrice() == null || entrustCnd.getAmount() == null){
				return  new MessageBox("30000", "非法参数提交！");
			}
			//==============================验证黑名单==============================
			Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
			if(bl==false){
				return  new MessageBox("30000", "委托失败，请重新刷新页面！");
			}
			//=============================验证黑名单==============================
				entrustCnd.setUserId(loginuser.getUserId());
				entrustCnd.setAddip(HttpTookit.getRealIpAddr(req));
				result = stockDealService.saveSellerApplyEntrust(entrustCnd);
		}catch(Exception e){
			logger.error("转让方委托挂单失败"+e.getMessage());
			result = new MessageBox("40000", "委托失败！"+e.getMessage());
			e.printStackTrace();
		}
		return result;
		
	}
	
	/****
	 * 查询交易明细
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-16
	 * @param dealId
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/findDealDetail/{dealId}/{entrustId}")
	public ModelAndView findDealDetail(@PathVariable("dealId") Integer dealId,@PathVariable("entrustId") Integer entrustId){
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
		StockDeal deal = stockDealService.selectByPrimaryKey(dealId);
		StockEntrust sellerEntrust = stockEntrustService.findEntrustById(deal.getSellerEntrustId());
		StockEntrust buyerEntrust = stockEntrustService.findEntrustById(deal.getBuyerEntrustId());
		mv = new ModelAndView("/stockright/entrust/dealDetail");
		return mv.addObject("deal", deal).addObject("sellerEntrust", sellerEntrust).addObject("buyerEntrust", buyerEntrust).addObject("entrustId", entrustId);
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:认购方委托单申请<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/buyerApplyEntrust")
	@ResponseBody
	public MessageBox buyerApplyEntrust(StockEntrustApplyCnd entrustCnd, HttpServletRequest req){
		MessageBox result = null;
		try{
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				return new MessageBox("20000", "用户未登陆！");
			}
			//参数协议参数
			if(entrustCnd.getPrice() == null || entrustCnd.getAmount() == null || entrustCnd.getIsCur() == null){
				return  new MessageBox("30000", "非法的参数提交！");
			}
			//==============================验证黑名单==============================
			Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
			if(bl==false){
				return  new MessageBox("30000", "委托失败，请重新刷新页面！");
			}
			//=============================验证黑名单==============================
				entrustCnd.setUserId(loginuser.getUserId());
				entrustCnd.setAddip(HttpTookit.getRealIpAddr(req));
				result = stockDealService.saveBuyerApplyEntrust(entrustCnd);
		}catch(Exception e){
			logger.error("委托认购挂单失败"+e.getMessage());
			result = new MessageBox("40000", "委托失败！"+e.getMessage());
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	/****
	 * 交易协议下载
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-24
	 * @return
	 * MessageBox
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/stockDownloadPDF/{dealId}")
	public void stockDownloadPDF(HttpServletRequest request,HttpServletResponse response, @PathVariable int dealId) {
		logger.info("下载协议-内转成交  start ");
		try {
			ShiroUser shiroUser = currentUser();
			Integer userId = shiroUser.getUserId();
			Map<String ,Object> reqMap = new HashMap<String ,Object>();
			String path = request.getContextPath();
			String port = request.getServerPort()+"";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			//转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal","ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);
			stockDealService.querysSockDeailDload(dealId, request.getContextPath(), userId,reqMap);
			
		} catch (Exception e) {
			logger.error("下载内转交易协议出错", e);
		}
		logger.info("下载内转交易协议-借款  end ");
	}
}
