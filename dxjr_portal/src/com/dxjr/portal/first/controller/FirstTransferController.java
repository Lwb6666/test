package com.dxjr.portal.first.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstTransferCancelVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferLaunchVo;
import com.dxjr.portal.first.vo.FirstTransferTypeCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;

/**
 * <p>
 * Description:直通车转让控制类<br />
 * </p>
 * 
 * @title FirstTransferController.java
 * @package com.dxjr.portal.first.controller
 * @author justin.xu
 * @version 0.1 2015年3月11日
 */
@Controller
@RequestMapping(value = "/zhitongche/zhuanrang")
public class FirstTransferController extends BaseController {

	Logger logger = LoggerFactory.getLogger(FirstTransferController.class);

	@Autowired
	private FirstTransferService firstTransferService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CurAccountService curAccountService;

	/**
	 * <p>
	 * Description:进入直通车转让列表页面br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView toTransferList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("first/transfer/transferList");

		return mv;
	}

	/**
	 * <p>
	 * Description:进入我的账号、导航直通车转让-直通车转出页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @return ModelAndView
	 */
	@RequestMapping("/tocontainer")
	public ModelAndView tocontainer() {
		ModelAndView transferPage = new ModelAndView("first/transfer/containerIndex");
		return transferPage;
	}

	/**
	 * <p>
	 * Description:进入直通车转出不同类型子页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @return ModelAndView
	 */
	@RequestMapping("/queryTypePage")
	public ModelAndView queryTypePage(FirstTransferTypeCnd firstTransferTypeCnd) {
		ModelAndView mav = new ModelAndView();
		// 可转让
		if (firstTransferTypeCnd.getType().equals(1)) {
			mav.setViewName("first/transfer/myCanTransferInner");
			// 转让中
		} else if (firstTransferTypeCnd.getType().equals(2)) {
			mav.setViewName("first/transfer/myTransferingInner");
		}
		// 已转让
		else if (firstTransferTypeCnd.getType().equals(3)) {
			mav.setViewName("first/transfer/myTransferedInner");
		}
		// 已转入
		else if (firstTransferTypeCnd.getType().equals(4)) {
			mav.setViewName("first/transfer/myTransferSubscribeInner");
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:查询可转让的直通车认购列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @return ModelAndView
	 */
	@RequestMapping("/queryCanTransferList/{pageNo}")
	@RequiresAuthentication
	public ModelAndView queryCanTransferList(HttpServletRequest request,FirstTransferTypeCnd firstTransferTypeCnd, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/transfer/myCanTransferList");
		
		try {
			ShiroUser shiroUser = currentUser();
			firstTransferTypeCnd.setUserId(shiroUser.getUserId());
			firstTransferTypeCnd.setType(BusinessConstants.FIRST_TRANSFER_TYPE_ENABLED);
			
			String timeScope = request.getParameter("timeScope"); //时间范围  
			
			if("month".equals(timeScope)){  //一个月
				firstTransferTypeCnd.setEndTime(DateUtils.getSysdate());
				firstTransferTypeCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH));
			} else if("threemonth".equals(timeScope)){  //三个月
				firstTransferTypeCnd.setEndTime(DateUtils.getSysdate());
				firstTransferTypeCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH));
			} else if("sixmonth".equals(timeScope)){ //六个月以前
				firstTransferTypeCnd.setEndTime(DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH));
			} else if("all".equals(timeScope)){
				firstTransferTypeCnd.setEndTime(null);
				firstTransferTypeCnd.setBeginTime(null);
			}
			Page page = firstTransferService.queryPageCanTransferByCnd(firstTransferTypeCnd, (new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE)));
			
			
			mv.addObject("page", page).addObject("beginTime",firstTransferTypeCnd.getBeginTime()).addObject("endTime",firstTransferTypeCnd.getEndTime());;
		} catch (Exception e) {
			logger.error("查询可转让直通车列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:进入直通车转让信息页面和债权价格详情页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @param tenderRealId
	 * @return ModelAndView
	 */
	@RequestMapping("/toTransferIndex/{tenderRealId}")
	public ModelAndView toTransferIndex(@PathVariable("tenderRealId") Integer tenderRealId) {
		ModelAndView mav = new ModelAndView("first/transfer/transferIndex");
		mav.addObject("tenderRealId", tenderRealId);
		return mav;
	}

	/**
	 * <p>
	 * Description:跳转到转让页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param tenderRealId
	 * @return ModelAndView
	 */
	@RequestMapping("/toTransfer/{tenderRealId}")
	public ModelAndView toTransfer(@PathVariable("tenderRealId") Integer tenderRealId) {
		ModelAndView mav = new ModelAndView("common/404");
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return mav;
			}
			FirstTransferTypeVo firstTransferTypeVo = firstTransferService.queryFirstTransferTypeByCnd(tenderRealId, shiroUser.getUserId());
			if (firstTransferTypeVo != null) {
				// 二次债转
				if (firstTransferTypeVo.getParentId() != null) {
					return mav;
				}
				mav.setViewName("first/transfer/transfer");
				mav.addObject("firstTransferTypeVo", firstTransferTypeVo);
			}
		} catch (Exception e) {
			mav.setViewName("common/404");
			logger.error("进入发起直通车转让详情页面出错", e);
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:判断数据是否异常<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年4月17日
	 * @param tenderRealId
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@RequestMapping(value = "/toTransferJudgTender/{tenderRealId}")
	@ResponseBody
	public MessageBox toTransferJudgTender(@PathVariable("tenderRealId") Integer tenderRealId) throws Exception {
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return new MessageBox("0", "请先登录");
		}
		FirstTransferTypeVo firstTransferTypeVo = firstTransferService.queryFirstTransferTypeByCnd(tenderRealId, shiroUser.getUserId());
		if (firstTransferTypeVo == null) {
			return new MessageBox("0", "数据异常,请刷新画面");
		}
		return new MessageBox("1", "success");
	}

	/**
	 * <p>
	 * Description:执行转让操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param firstTransferLaunchVo
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping("/dotransfer")
	@ResponseBody
	public MessageBox dotransfer(FirstTransferLaunchVo firstTransferLaunchVo, HttpServletRequest request) {
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return new MessageBox("0", "请登录");
			}
			firstTransferLaunchVo.setUserId(shiroUser.getUserId());
			firstTransferLaunchVo.setUserName(shiroUser.getUserName());
			firstTransferLaunchVo.setSessionCheckCode((String) request.getSession().getAttribute("randomCode"));
			firstTransferLaunchVo.setPlatform(shiroUser.getPlatform());
			firstTransferLaunchVo.setAddip(HttpTookit.getRealIpAddr(request));
			String result = firstTransferService.saveFirstTransfer(firstTransferLaunchVo);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return new MessageBox("0", result);
			}
		} catch (Exception e) {
			logger.error("保存直通车转让出错", e);
			return new MessageBox("0", "网络连接异常,请刷新页面或稍后重试！");
		}
		return new MessageBox("1", "操作成功");
	}

	/**
	 * <p>
	 * Description:查询我的账号中-转让中的直通车列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @return ModelAndView
	 */
	@RequestMapping("/queryTransferingList/{pageNo}")
	@RequiresAuthentication
	public ModelAndView queryTransferingList(HttpServletRequest request,FirstTransferTypeCnd firstTransferTypeCnd, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/transfer/myTransferingList");
		try {
			ShiroUser shiroUser = currentUser();
			FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
			firstTransferCnd.setUserId(shiroUser.getUserId());
			firstTransferCnd.setTransferStatus(Constants.FIRST_TRANSFER_STATU_ING);
			firstTransferCnd.setBeginTime(firstTransferTypeCnd.getBeginTime());
			firstTransferCnd.setEndTime(firstTransferTypeCnd.getEndTime());
			
			String timeScope = request.getParameter("timeScope"); //时间范围  
			
			if("month".equals(timeScope)){  //一个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH));
			} else if("threemonth".equals(timeScope)){  //三个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH));
			} else if("sixmonth".equals(timeScope)){ //六个月以前
				firstTransferCnd.setEndTime(DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH));
			} else if("all".equals(timeScope)){
				firstTransferCnd.setEndTime(null);
				firstTransferCnd.setBeginTime(null);
			}
			Page page = firstTransferService.queryMyFirstTransferList(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", page).addObject("beginTime",firstTransferCnd.getBeginTime()).addObject("endTime",firstTransferCnd.getEndTime());;
		} catch (Exception e) {
			logger.error("查询我的转让中直通车列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:查询我的账号中-已转让的直通车列表<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月23日
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping("/queryTransferedList/{pageNo}")
	@RequiresAuthentication
	public ModelAndView queryTransferedList(HttpServletRequest request,FirstTransferTypeCnd firstTransferTypeCnd, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/transfer/myTransferedList");
		try {
			ShiroUser shiroUser = currentUser();
			FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
			firstTransferCnd.setUserId(shiroUser.getUserId());
			firstTransferCnd.setTransferStatus(Constants.FIRST_TRANSFER_SUCCESS);
			firstTransferCnd.setBeginTime(firstTransferTypeCnd.getBeginTime());
			firstTransferCnd.setEndTime(firstTransferTypeCnd.getEndTime());
			String timeScope = request.getParameter("timeScope"); //时间范围  
			
			if("month".equals(timeScope)){  //一个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH));
			} else if("threemonth".equals(timeScope)){  //三个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH));
			} else if("sixmonth".equals(timeScope)){ //六个月以前
				firstTransferCnd.setEndTime(DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH));
			} else if("all".equals(timeScope)){
				firstTransferCnd.setEndTime(null);
				firstTransferCnd.setBeginTime(null);
			}
			
			Page page = firstTransferService.queryMyFirstTransferList(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			
			mv.addObject("page", page).addObject("beginTime",firstTransferCnd.getBeginTime()).addObject("endTime",firstTransferCnd.getEndTime());
		} catch (Exception e) {
			logger.error("查询我的已转让直通车列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:查询我的账号中-认购的直通车列表<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月23日
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping("/queryTransferSubscribeList/{pageNo}")
	@RequiresAuthentication
	public ModelAndView queryTransferSubscribeList(HttpServletRequest request,FirstTransferTypeCnd firstTransferTypeCnd, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/transfer/myTransferSubscribeList");
		try {
			ShiroUser shiroUser = currentUser();
			FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
			firstTransferCnd.setUserId(shiroUser.getUserId());
			firstTransferCnd.setTransferStatus(Constants.FIRST_TRANSFER_SUCCESS);
			firstTransferCnd.setBeginTime(firstTransferTypeCnd.getBeginTime());
			firstTransferCnd.setEndTime(firstTransferTypeCnd.getEndTime());
			
			String timeScope = request.getParameter("timeScope"); //时间范围  
			
			if("month".equals(timeScope)){  //一个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH));
			} else if("threemonth".equals(timeScope)){  //三个月
				firstTransferCnd.setEndTime(DateUtils.getSysdate());
				firstTransferCnd.setBeginTime(DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH));
			} else if("sixmonth".equals(timeScope)){ //六个月以前
				firstTransferCnd.setEndTime(DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH));
			} else if("all".equals(timeScope)){
				firstTransferCnd.setEndTime(null);
				firstTransferCnd.setBeginTime(null);
			}
			Page page = firstTransferService.queryMyFirstTransferedList(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			
			mv.addObject("page", page).addObject("beginTime",firstTransferCnd.getBeginTime()).addObject("endTime",firstTransferCnd.getEndTime());
		} catch (Exception e) {
			logger.error("查询我的转让中直通车认购列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:取消转让操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param transferId
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping("/cancelTransfer/{transferId}")
	@ResponseBody
	public MessageBox cancelTransfer(@PathVariable("transferId") Integer transferId, HttpServletRequest request) {
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return new MessageBox("0", "请登录");
			}
			FirstTransferCancelVo firstTransferCancelVo = new FirstTransferCancelVo();
			firstTransferCancelVo.setUserId(shiroUser.getUserId());
			firstTransferCancelVo.setPlatform(shiroUser.getPlatform());
			firstTransferCancelVo.setUserName(shiroUser.getUserName());
			firstTransferCancelVo.setAddIp(HttpTookit.getRealIpAddr(request));
			firstTransferCancelVo.setFirstTransferId(transferId);
			String result = firstTransferService.saveCancelFirstTransfer(firstTransferCancelVo);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return new MessageBox("0", result);
			}
		} catch (Exception e) {
			logger.error("取消直通车转让出错", e);
			return new MessageBox("0", "网络连接异常,请刷新页面或稍后重试！");
		}
		return new MessageBox("1", "取消直通车转让成功");
	}

	/**
	 * 
	 * <p>
	 * Description:直通车转让查询<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param pageNum
	 * @param seach
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryTransferList/{pageNo}")
	public ModelAndView queryTransferList(FirstTransferCnd firstTransferCnd, @PathVariable("pageNo") Integer pageNo) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/transferGridByPage");

		// 分页查询结果
		Page page = null;
		try {
			// 直通车转让信息分页查询
			page = firstTransferService.queryFirstTransferByCnd(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", page);
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("进入直通车转让出错", e);
		}
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:根据Id查询直通车转让信息,跳转到债转详情画面<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月18日
	 * @param transferId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryTransferById/{transferId}")
	public ModelAndView queryTransferById(@PathVariable("transferId") Integer transferId) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/transferDetail");

		try {
			// 获取直通车转让信息
			FirstTransferVo firstTransferVo = firstTransferService.queryTransferById(transferId);
			// 判断是否登录
			if (isLogin()) {
				// 获得用户信息
				ShiroUser shiroUser = currentUser();
				// 帐号资金
				AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
				mv.addObject("account", account);
				// 可投金额设定（=转让价格）
				mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
				
			}
			mv.addObject("effectiveAccount", firstTransferVo.getAccountReal());
			// 直通车转让信息设定
			mv.addObject("firstTransfer", firstTransferVo);
			// 取当前时间
			mv.addObject("nowTime", DateUtils.format(new Date(), DateUtils.YMD_SLAHMS));
			// 剩余时间
			mv.addObject("endTime", getFirstTransferEndTime(firstTransferVo.getEndTime()));
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("进入直通车转让出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:直通车转让剩余时间获取<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年6月4日
	 * @param endTime
	 * @return String
	 */
	private String getFirstTransferEndTime(Date endTime) {
		Date TransferEndTime = DateUtils.parse(DateUtils.format(endTime, DateUtils.YMD_SLASH) + " 22:30:00", DateUtils.YMD_SLAHMS);
		if (endTime.getTime() >= TransferEndTime.getTime()) {
			return DateUtils.format(DateUtils.dayOffset(endTime, 1), DateUtils.YMD_SLASH) + " 22:30:00";
		} else {
			return DateUtils.format(endTime, DateUtils.YMD_SLASH) + " 22:30:00";
		}
	}

	/**
	 * 
	 * <p>
	 * Description:根据Id查询直通车转让信息,跳转到确认认购pop画面<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月18日
	 * @param transferId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryTransferPopById/{transferId}")
	public ModelAndView queryTransferPopById(@PathVariable("transferId") Integer transferId) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/transferSubscribePop");

		try {
			// 获取直通车转让信息
			FirstTransferVo firstTransferVo = firstTransferService.queryTransferById(transferId);
			// 判断是否登录
			if (isLogin()) {
				ShiroUser shiroUser = currentUser();
				// 帐号资金
				AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
				mv.addObject("account", account);
				mv.addObject("effectiveAccount", firstTransferVo.getAccountReal());
				mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
			}
			// 直通车转让信息设定
			mv.addObject("firstTransfer", firstTransferVo);
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("进入直通车转让出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:查询已转让的直通车记录<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年4月14日
	 * @param firstTransferTypeCnd
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping("/queryAllTransferSubscribeList/{pageNo}")
	public ModelAndView queryAllTransferSubscribeList(FirstTransferTypeCnd firstTransferTypeCnd, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/firstTransferedList");
		try {
			FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
			firstTransferCnd.setTransferStatus(Constants.FIRST_TRANSFER_SUCCESS);
			Page page = firstTransferService.queryMyFirstTransferSubscribeList(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("查询直通车转让记录列表出错", e);
		}
		return mv;
	}
	@RequestMapping(value = "/queryTransferList1/{pageNo}")
	public ModelAndView queryTransferList1(FirstTransferCnd firstTransferCnd, @PathVariable("pageNo") Integer pageNo) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/transferGridByPage1");

		// 分页查询结果
		Page page = null;
		try {
			// 直通车转让信息分页查询
			page = firstTransferService.queryFirstTransferByCnd(firstTransferCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", page);
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("进入直通车转让出错", e);
		}
		return mv;
	}

}
