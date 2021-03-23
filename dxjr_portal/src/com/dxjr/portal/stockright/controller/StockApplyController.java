package com.dxjr.portal.stockright.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.stockright.entity.ApplyInfo;
import com.dxjr.portal.stockright.entity.IsProtocol;
import com.dxjr.portal.stockright.entity.ShareholderRoster;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockApprove;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.StockApplyRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;


/**
 * 
 * <p>
 * Description:股权登陆controller<br />
 * </p>
 * @title StockSellerController.java
 * @package com.dxjr.portal.stockright.controller 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
@Controller
@RequestMapping(value="/stockApply")
public class StockApplyController extends BaseController {
	
	@Autowired
	private StockAccountService stockAccountService;
	
	@Autowired
	private StockApplyService stockApplyService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private StockEntrustService stockEntrustService;
	
	private static final Logger logger = Logger.getLogger(StockApplyController.class);

	
	/**
	 * 
	 * <p>
	 * Description:这进入用户注册股权登陆页面<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/inRegister")
	public ModelAndView inRegister(){
		ModelAndView mv = null;
		try{
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				mv = new ModelAndView("/member/login");
				return mv;
			}
			mv = new ModelAndView("/stockright/apply/stockloginapp");
			BigDecimal collect = stockAccountService.queryUserCollect(loginuser.getUserId());
			
			//劳婕怡和温征特殊不做任何限制
			if(loginuser.getUserId()!=173438 && loginuser.getUserId()!=66){
				//==============================验证黑名单==============================
				Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
				if(bl==false){
					mv = new ModelAndView("/common/404");
					return mv;
				}
				//=============================验证黑名单==============================
			}
			//获取用户信息
			StockUserInfoVo map = stockAccountService.queryUserInfoById(loginuser.getUserId());
			
			mv.addObject("userInfo", map);
			//查询待收信息
			mv.addObject("collect", collect);
			ShareholderRoster record  = new ShareholderRoster();
			record.setUserId(loginuser.getUserId());
			record.setVersion(1);
			record.setStatus(1);
			ShareholderRoster shareHolder = stockApplyService.selectShareholder(record);
			mv.addObject("shareHolder", shareHolder);
		}catch(Exception e){
			logger.error("获取用户账户信息失败"+e.getMessage());
			e.printStackTrace();
		}
		return mv;
		
	}
	
	/**
	 * 
	 * <p>
	 * Description:验证用户申请股权登陆权限<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-11
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value="/checkUserInfo")
	@ResponseBody
	public Map<String,Object> checkUserInfo(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				map.put("status", "-1");
				map.put("msg", "用户未登录！");
				return map;
			}
			
			//劳婕怡和温征特殊不做任何限制
			if(loginuser.getUserId()==173438 || loginuser.getUserId()==66){
				Integer count = stockApplyService.countApplyInfo(loginuser.getUserId());
				if(count>0){
					map.put("status", "3");
				}else{
					map.put("status", "2");
				}
				return map;
			}
			
			//==============================股权系统代码块开始==============================
			if(loginuser.getUserId()!=2){
				StockApplyRequest stockApp = new StockApplyRequest();
				stockApp.setUserId(loginuser.getUserId());
				//查询用户是否在黑名单
				Integer count = stockApplyService.checkBlackList(stockApp);
				//查询用户是否存在委托挂单记录
				StockEntrustCnd entrustCnd = new StockEntrustCnd();
				entrustCnd.setUserId(loginuser.getUserId());
				entrustCnd.setStatusArray(new Integer[] {1, 2});
				List<StockEntrust> entrustList = stockEntrustService.findEntrustListByUserId(entrustCnd);
				//查询股东账户
				StockAccount account = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
				if(entrustList.size() ==0 && account==null){
					if(count>0){
						map.put("status", "4");
						map.put("message", "系统异常，请稍后重试！");
						return map;
					}
				}
			 }
			
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(loginuser.getUserId());
			if (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1) {
				map.put("status", "1");
				map.put("message", "请先进行手机认证！");
				return map;
			}
			// 检查实名认证
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				map.put("status", "1");
				map.put("message", "请先进行实名认证！");
				return map;
			}
			
			
			Map<String,Object> checkMap = stockApplyService.checkUserInfo(loginuser.getUserId());
			map.putAll(checkMap);
		} catch (Exception e) {
			logger.error("查询转让方信息失败"+e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:申请股权登陆<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-13
	 * @param request
	 * @param req
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value="/registerStock")
	@ResponseBody
	public MessageBox saveApply(@ModelAttribute ApplyInfo request,HttpServletRequest req){
		String code = "0";
		try {
			ShiroUser loginuser=currentUser();
			if(loginuser==null){
				return new MessageBox(code,"您未登陆，请先登陆再来操作！");
			}
			if(request.getType()==1){
				if(request.getIsProtocol()!=IsProtocol.YES.getCode()){
					return new MessageBox(code,"非法参数提交!");
				}
			}
			StockApplyRequest st = new StockApplyRequest();
			st.setUserId(loginuser.getUserId());
			st.setStatusArray(new Integer[] {1,2});
			st.setType(request.getType());
			ApplyInfo app = stockApplyService.findApplyList(st);
			if(app!=null){
				if(app.getStatus()==1){
					return new MessageBox(code,"您已存待审核的记录，不能重复提交!");
				}
				if(app.getStatus()==2){
					return new MessageBox("1","存在审核通过记录,不能重复申请!");
				}
			}
			request.setUserId(loginuser.getUserId());
			request.setUserName(loginuser.getUserName());
			request.setAdduser(loginuser.getUserId());
			request.setAddip(HttpTookit.getRealIpAddr(req));
			request.setUpdateuser(loginuser.getUserId());
			request.setUpdateip(com.dxjr.utils.HttpTookit.getRealIpAddr(req));
			stockApplyService.saveApply(request);
			st.setStatusArray(new Integer[] {2});
			ApplyInfo apply = stockApplyService.findApplyList(st);
			if(apply!=null){
				code="1";
			}else{
				code="2";
			}
		} catch (Exception e) {
			logger.error("添加内转登陆申请记录"+e.getMessage());
			e.printStackTrace();
			return new MessageBox(code,"系统异常请刷新页面！");
		}
		return new MessageBox(code,"保存成功！");
	}
	
	
	/****
	 * 跳转提交申请信息列表页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-15
	 * @param type
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/queryApplyMain/{type}")
	public ModelAndView queryApplyMain(@PathVariable("type") Integer type) {
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/toLoginPage");
			return mv;
		}
			//查询股东账户
			StockAccount account = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
			//==============================验证黑名单==============================
			Boolean bl = stockApplyService.checkBlankUser(loginuser.getUserId());
			if(bl==false){
				mv = new ModelAndView("/common/404");
				return mv;
			}
			//=============================验证黑名单==============================
			
		if(type==1){
			mv = new ModelAndView("/stockright/apply/applyloginmain");
		}else if(type==2){
			mv = new ModelAndView("/stockright/apply/applyquitmain").addObject("account", account);
		}else{
			mv = new ModelAndView("/common/404");
		}
		
		return mv;
	}
	/****
	 * 查询审核列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-13
	 * @param pageNum
	 * @param seach
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/queryApplyList")
	public ModelAndView queryApplyList(@RequestParam Integer pageNum, @ModelAttribute StockApplyRequest seach) {
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/login");
			return mv;
		}
		seach.setUserId(loginuser.getUserId());
		Page p = new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE);
		try {
			p = stockApplyService.queryApplyInfoList(seach, p);
		} catch (Exception e) {
			logger.error("进入我的申请列表出错");
			e.printStackTrace();
		}
		
		if(seach.getType()==1){
			mv = new ModelAndView("/stockright/apply/applyloginlist");
		}else if(seach.getType()==2){
			mv = new ModelAndView("/stockright/apply/applyquitlist");
		}else{
			mv = new ModelAndView("/common/404");
		}
		mv.addObject("page", p);
		
		return mv;
	}
	
	/****
	 * 查询审核记录信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-15
	 * @param seach
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/queryApplyDetail")
	public ModelAndView queryApplyDetail(@ModelAttribute StockApplyRequest seach) {
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/login");
			return mv;
		}
		
		List<StockApprove> approveList = stockApplyService.findApplyApprove(seach);
		
		if(seach.getType()==1){
			mv = new ModelAndView("/stockright/apply/approveDetail");
		}else{
			mv = new ModelAndView("/common/404");
		}
		
		return mv.addObject("approveList", approveList);
	}
	
	
	/***
	 * 突出股东名称菜单
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-15
	 * @param seach
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/quitShareholder")
	public ModelAndView quitShareholder() {
		ModelAndView mv = null;
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			mv = new ModelAndView("/member/login");
			return mv;
		}
		Integer count = stockApplyService.countApplyInfo(loginuser.getUserId());
		if(count==0){
			mv = new ModelAndView("/common/404");
			return mv;
		}
		//获取用户信息
		mv = new ModelAndView("/stockright/apply/quitShareholder");
		StockUserInfoVo map = stockAccountService.queryUserInfoById(loginuser.getUserId());
		mv.addObject("userInfo", map);
		StockAccount stockAccount = stockAccountService.selectByPrimaryKey(loginuser.getUserId());
		mv.addObject("stockAccount", stockAccount);
		return mv;
	}
	
	
	/****
	 * 查询申请登录协议
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-23
	 * @param request
	 * @return
	 * ModelAndView
	 */
	@RequestMapping(value = "/toStockXiyi")
	public ModelAndView toStockXiyi(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/stockright/apply/stockxiyi");
		return mv;
	}
	
}
