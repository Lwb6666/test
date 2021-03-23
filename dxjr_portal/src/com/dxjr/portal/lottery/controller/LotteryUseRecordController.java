package com.dxjr.portal.lottery.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.memberaccumulatepoints.service.MemberAccumulatePointsService;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.InviterMapper;
import com.dxjr.portal.account.service.FriendService;
import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.account.vo.FriendCnd;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.lottery.mapper.LotteryUseRecordMapper;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.lottery.service.LotteryUseRecordService;
import com.dxjr.portal.lottery.vo.LotteryGoodsSendInfoVo;
import com.dxjr.portal.lottery.vo.LotteryUseRecordCnd;
import com.dxjr.portal.lottery.vo.LotteryUseRecordVo;
import com.dxjr.portal.lottery.vo.Myreward;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.sycee.service.SyceeService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

/**
 * 
 * <p>
 * Description:抽奖使用记录<br />
 * </p>
 * 
 * @title LotteryUseRecordController.java
 * @package com.dxjr.portal.lottery.controller
 * @author YangShiJin
 * @version 0.1 2015年4月11日
 */
@Controller
@RequestMapping(value = "/lottery_use_record")
public class LotteryUseRecordController extends BaseController {
	private final static Logger logger = Logger.getLogger(LotteryUseRecordController.class);

	@Autowired
	private LotteryUseRecordService lotteryUseRecordService;

	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	
	@Autowired RedAccountService redAccountService;
	
	@Autowired
	private LotteryUseRecordMapper lotteryUseRecordMapper;
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberAccumulatePointsService syceeService;
	@Autowired
	private SyceeService syceeService1;
	@Autowired
	private FriendService friendService;
	@Autowired
	private InviterMapper inviterMapper;
	@Autowired
	private RedAccountMapper redAccountMapper;
	/****
	 * 
	 * <p>
	 * Description:我的奖励-首次进入<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月15日
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/lott_use_record")
	@RequiresAuthentication
	public ModelAndView newPFirst(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("lott/lott_tab");
		mv.addObject("tabType", request.getParameter("tabType"));
		if(null!=request.getParameter("redId")){
			mv.addObject("redId", request.getParameter("redId"));
		}else{
			mv.addObject("redId",0);
		}
		return mv;
	}

	// 现金奖励
	@RequestMapping(value = "/xj_lott/{pageNum}")
	public ModelAndView lott_xj_query(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_xj");
		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();
		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}

			lotteryUseRecordCnd.setUserId(shiroUser.getUserId());

			// 可用余额，
			LotteryUseRecordVo lottVo = lotteryUseRecordService.queryMoneyS_xj(lotteryUseRecordCnd);
			if (lottVo != null) {
				mv.addObject("lottVo", lottVo);
			} else {
				mv.addObject("lottVo_ling", "0");
			}

			// 剩余几次抽奖机会
			Integer chanceTotalNum = lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());

			if ((chanceTotalNum != null) && (chanceTotalNum != 0)) {
				mv.addObject("chanceTotalNum", chanceTotalNum);
			} else {
				int ling = 0;
				mv.addObject("ling", ling);
			}

			Page p = lotteryUseRecordService.queryPageByLotteryUseRecordCnd_xj(lotteryUseRecordCnd, new Page(pageNum,
					BusinessConstants.DEFAULT_PAGE_SIZE));
			List<LotteryUseRecordVo> lottLst = null;

			if (p.getResult() != null) {
				lottLst = (List<LotteryUseRecordVo>) p.getResult();
			}

			mv.addObject("lottLst", lottLst);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p);

		} catch (Exception e) {
			logger.info("异常信息：" + e.getMessage());
		}
		return mv;
	}

	/***
	 * 
	 * <p>
	 * Description:现金：使用 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月13日
	 * @param request
	 * @param session
	 * @param response
	 * @param lott_cnd
	 * @return MessageBox
	 */
	@RequestMapping(value = "lott_xj_sh")
	@RequiresAuthentication
	public @ResponseBody
	MessageBox cancelCash(HttpServletRequest request, HttpSession session, HttpServletResponse response, LotteryUseRecordCnd lott_cnd) {
		try {
			ShiroUser shiroUser = currentUser();
			LotteryUseRecordVo retLottVo = lotteryUseRecordMapper.queryXJById(lott_cnd.getId() + "");
			//判断中奖用户与当前用户是否同一人
			if(retLottVo.getUserId().intValue()!=shiroUser.getUserId().intValue()){
				return new MessageBox("0", "当前用户与中奖用户不匹配，领取失败！");
			}
			
			lott_cnd.setUserId(shiroUser.getUserId());
			String ipStr = HttpTookit.getRealIpAddr(request);

			// 使用 金额
			boolean isFalse = lotteryUseRecordService.up_xj_money(lott_cnd, ipStr);
			// true - status 0 - 可以使用
			if (!isFalse) {
				return new MessageBox("0", "领取成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageBox("0", "网络连接异常，请刷新页面或稍后重试!");
		}
		return new MessageBox("1", "状态已变更，请刷新后重试！");
	}

	// 实物奖励 /lottery_use_record/sw_lott/1
	@RequestMapping(value = "/sw_lott/{pageNum}")
	public ModelAndView lott_sw_query(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw");

		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();

		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			lotteryUseRecordCnd.setUserId(shiroUser.getUserId());

			// 剩余几次抽奖机会
			Integer chanceTotalNum = lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());

			if ((chanceTotalNum != null) && (chanceTotalNum != 0)) {
				mv.addObject("chanceTotalNum", chanceTotalNum);
			} else {
				int ling = 0;
				mv.addObject("ling", ling);
			}

			// list
			Page p = lotteryUseRecordService.queryPageByLotteryUseRecordCnd_sw(lotteryUseRecordCnd, new Page(pageNum,
					BusinessConstants.DEFAULT_PAGE_SIZE));
			List<LotteryUseRecordVo> lottLst = null;

			if (p.getResult() != null) {
				lottLst = (List<LotteryUseRecordVo>) p.getResult();
			}

			mv.addObject("lottLst", lottLst);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p);

		} catch (Exception e) {
			logger.info("查询异常信息:" + e.getMessage());
		}

		return mv;

	}
	//我的红包
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lott_hongbao/{pageNum}")
	public ModelAndView lott_hongbao_query(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_hongbao");

		RedAccountCnd redAccountCnd=new RedAccountCnd();

		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			redAccountCnd.setUserId(shiroUser.getUserId());
            //隐藏小红点状态
			Object redDot=currentSession().getAttribute("redDot");
			if(redDot!=null){
				redAccountService.updateRedDotState(shiroUser.getUserId());
				currentSession().removeAttribute("redDot");
			}
			//查询未开启 未使用的红包
			List<RedAccount>openRedAccounts=redAccountService.queryPageByOpen_hb(shiroUser.getUserId());
			
			//查询已过期，已使用，冻结的红包
			Page p = redAccountService.queryPageByExpiredRed_hb(redAccountCnd,new Page(pageNum,8));
			List<RedAccount> redList = null;
            
			if (p.getResult() != null) {
				redList = (List<RedAccount>) p.getResult();
			}
            mv.addObject("openRedAccounts",openRedAccounts);
			mv.addObject("redList", redList);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p);

		} catch (Exception e) {
			logger.info("查询异常信息:" + e.getMessage());
		}

		return mv;

	}

	// 实物领取
	@RequiresAuthentication
	@RequestMapping(value = "/sw_lingqu")
	public ModelAndView toLinkman(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw_lingqu");
		ShiroUser shiroUser = currentUser();

		try {
			String lott_id = request.getParameter("lott_id");// 领取实物 id

			mv.addObject("lott_id", lott_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * <p>
	 * Description:确认收货<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param request
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/swLingquQrsh")
	public ModelAndView swLingquQrsh(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw_qrsh");
		try {
			String lott_id = request.getParameter("lott_id");// 领取实物 id

			mv.addObject("lott_id", lott_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	

	// 领取完成， 关闭
	@RequiresAuthentication
	@RequestMapping(value = "/afterLQ_close")
	public ModelAndView afterLingQu(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw");

		return mv;

	}

	// 领取提交
	@RequiresAuthentication
	@RequestMapping(value = "/lingqu_submit")
	public @ResponseBody
	String lingqu_submit(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		LotteryGoodsSendInfoVo lotteryGoodsSendInfoVo = new LotteryGoodsSendInfoVo();
		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();

		try {
			String name = request.getParameter("name"); // 姓名
			String mobile = request.getParameter("mobile"); // 手机
			String address = request.getParameter("sw_address"); // 地址
			String postcode = request.getParameter("sw_code"); // 邮编
			String lott_id = request.getParameter("lott_id"); // 抽奖机会使用记录ID,
																// 实物id
			ShiroUser shiroUser = currentUser();

			lotteryUseRecordCnd.setUserId(shiroUser.getUserId()); // userid
																	// 放lott_use_record
			lotteryUseRecordCnd.setId(Integer.valueOf(lott_id)); // 奖励记录 id

			lotteryGoodsSendInfoVo.setUserId(shiroUser.getUserId()); // userid
																		// 放goods_send
			lotteryGoodsSendInfoVo.setLotteryUseRecordId(Integer.valueOf(lott_id)); // 抽奖id

			lotteryGoodsSendInfoVo.setContact(name);
			lotteryGoodsSendInfoVo.setMobile(mobile);
			lotteryGoodsSendInfoVo.setAddress(address);
			lotteryGoodsSendInfoVo.setPostcode(postcode);

			String temp1 = null;
			String ipStr = HttpTookit.getRealIpAddr(request);
			Integer platForm = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();

			lotteryGoodsSendInfoVo.setExpressCompany(temp1); // '快递公司',
			lotteryGoodsSendInfoVo.setExpressCode(temp1); // '快递单号',
			lotteryGoodsSendInfoVo.setAddTime(new Date()); // '添加时间',
			lotteryGoodsSendInfoVo.setAddIp(ipStr); // 添加IP',
			lotteryGoodsSendInfoVo.setRemark("我的奖励实物奖励领取记录"); // '备注',
			lotteryGoodsSendInfoVo.setPlatform(platForm + ""); // '平台来源(1：网页
																// 2、微信)',

			boolean isFalse = lotteryUseRecordService.lingqu_inst(lotteryGoodsSendInfoVo, lotteryUseRecordCnd);
			// true - status 0 -可以领取
			if (isFalse) {
				sb.append("suc_hj");
			} else {
				// 领取失败
				sb.append("fl_hj");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	
	/**
	 * <p>
	 * Description:确认收货, 更新status ,remark <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param request
	 * @return
	 * String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/lingquQrsh")
	public @ResponseBody
	String lingquQrsh(HttpServletRequest request,@ModelAttribute LotteryUseRecordCnd lotteryUseRecordCnd) {

		String qrshTag = "";
		try {
			String remark = request.getParameter("remark"); // 备注
			logger.info("------------"+ lotteryUseRecordCnd.getRemark());
			logger.info("------------"+ lotteryUseRecordCnd.getLott_id());
			ShiroUser shiroUser = currentUser();
			if(shiroUser!=null)
			{
				lotteryUseRecordCnd.setUserId(shiroUser.getUserId());
			}
			int isTag = lotteryUseRecordService.upStatusRemarkByQrsh(lotteryUseRecordCnd);
			if(isTag>0)
			{
				qrshTag = "suc_hj";
			}else {
				qrshTag = "fl_hj";
			}
			
		} catch (Exception e) {
			logger.equals(e.getMessage());
		}
		return qrshTag;
	}

	/****
	 * 
	 * <p>
	 * Description: 实物领取前，验证是否可以查看 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月17日
	 * @param request
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@RequestMapping(value = "//sw_lingqu_detail_pd")
	@ResponseBody
	public MessageBox judgTender(HttpServletRequest request) throws Exception {
		if (!isLogin()) {
			return new MessageBox("0", "请先登录");
		}
		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();

		int lingqu_id = Integer.valueOf(request.getParameter("id"));
		ShiroUser shiroUser = currentUser();

		if (shiroUser != null) {
			lotteryUseRecordCnd.setUserId(shiroUser.getUserId());
			lotteryUseRecordCnd.setId(lingqu_id);
		}

		// 根据实物 id ,查询 快递信息,实物 领取查看详情
		List<LotteryUseRecordVo> retLott = lotteryUseRecordService.sw_lingqu_detail(lotteryUseRecordCnd);
		if (retLott != null && retLott.size() > 0) {
			// 有记录--可以查看 - 继续弹出窗
		} else {
			// 无记录 - 不可查看- 提示:“无查看信息” - 返回
			return new MessageBox("0", "无查看信息!");
		}

		return new MessageBox("1", "success");
	}

	/***
	 * 
	 * <p>
	 * Description:实物领取 详情 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月14日
	 * @param request
	 * @param lingqu_id
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/sw_lingqu_detail/{lingqu_id}")
	public ModelAndView sw_lingqu_detail(HttpServletRequest request, @PathVariable int lingqu_id) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw_lqDetail");
		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();
		LotteryUseRecordVo lottVo = new LotteryUseRecordVo();
		int isTag = 0; // 0 有查看信息 1无查看信息

		try {

			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			if (shiroUser != null) {
				lotteryUseRecordCnd.setUserId(shiroUser.getUserId());
				lotteryUseRecordCnd.setId(lingqu_id);
			}
			// 根据实物 id ,查询 快递信息,实物 领取查看详情
			List<LotteryUseRecordVo> retLott = lotteryUseRecordService.sw_lingqu_detail(lotteryUseRecordCnd);

			if (retLott != null && retLott.size() > 0) {
				isTag = 0;
				// 把查看详情信息返回到页面
				for (LotteryUseRecordVo vo : retLott) {
					// 6个字段 (必须)
					lottVo.setContact(vo.getContact());
					lottVo.setMobile(vo.getMobile());

					lottVo.setAddress(vo.getAddress());
					lottVo.setPostcode(vo.getPostcode());

					lottVo.setExpress_company(vo.getExpress_company());
					lottVo.setExpress_code(vo.getExpress_code());

					// 奖励名称
					lottVo.setLotteryGoodsName(vo.getLotteryGoodsName());
					// 获奖时间
					lottVo.setAddTime(vo.getAddTime());
					// 过期时间
					lottVo.setOverdueTime(vo.getOverdueTime());
					// 状态
					lottVo.setStatus_z(vo.getStatus_z());
					// 备注
					lottVo.setRemark(vo.getRemark());

				}
			} else {
				// 查看详情是空-- 界面提示:无查看信息,返回
				isTag = 1;

			}

			mv.addObject("lottVo", lottVo);
			mv.addObject("isTag", isTag);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("实物领取详情错误:", e);
		}
		return mv;
	}

	// 奖励记录
	@RequestMapping(value = "/jljl_lott/{pageNum}")
	public ModelAndView lott_jljl_query(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_jljl");

		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();

		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}

			lotteryUseRecordCnd.setUserId(shiroUser.getUserId());

			// 剩余几次抽奖机会
			Integer chanceTotalNum = lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());

			if ((chanceTotalNum != null) && (chanceTotalNum != 0)) {
				mv.addObject("chanceTotalNum", chanceTotalNum);
			} else {
				int ling = 0;
				mv.addObject("ling", ling);
			}

			// 奖励list
			Page p = lotteryUseRecordService.queryPageByLotteryUseRecordCnd_jljl(lotteryUseRecordCnd, new Page(pageNum,
					BusinessConstants.DEFAULT_PAGE_SIZE));
			List<LotteryUseRecordVo> lottLst = null;

			if (p.getResult() != null) {
				lottLst = (List<LotteryUseRecordVo>) p.getResult();
			}

			mv.addObject("lottLst", lottLst);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p);

		} catch (Exception e) {
			logger.info("查询错误信息:" + e.getMessage());
		}

		return mv;
		
	}
//==========================官网我的账户改版start================================//
	//我的奖励
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lott_hongbaonew/{pageNum}/{awardType}")
	public ModelAndView lott_hongbaonew_query(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int awardType) {
		ModelAndView mv = new ModelAndView("/lott/lott_hongbao");
		LotteryUseRecordCnd lotteryUseRecordCnd = new LotteryUseRecordCnd();
		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			lotteryUseRecordCnd.setUserId(shiroUser.getUserId());
			lotteryUseRecordCnd.setAwardType(awardType);
			// 剩余几次抽奖机会
			int chanceTotalNum=lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());
			mv.addObject("chanceTotalNum", chanceTotalNum);
			// 奖励list
			Page p = lotteryUseRecordService.queryPageByLotteryUseRecordCnd_reward(lotteryUseRecordCnd, new Page(pageNum,BusinessConstants.DEFAULT_PAGE_SIZE));
			List<Myreward> lottLst = null;
			if (p.getResult() != null) {
				lottLst = (List<Myreward>) p.getResult();
			}
			mv.addObject("lottLst", lottLst);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p).addObject("awardType", awardType);

		} catch (Exception e) {
			logger.info("查询错误信息:" + e.getMessage());
		}
		return mv;

	}
	
	/**
	 * 我的元宝
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年05月19日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/xj_lottnew/{pageNum}")
	public ModelAndView lott_xj_newquery(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_xj");
		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			int userId = shiroUser.getUserId();
			UserLevelRatio ul = memberService.queryUserLevel(userId);
			mv.addObject("ul", ul);// 等级信息

			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(userId);
			MemberVo m = memberService.queryMemberByCnd(memberCnd);
			mv.addObject("sycee", m.getAccumulatepoints());// 总元宝
			mv.addObject("honor", m.getHonor());// 荣誉值

			mv.addObject("lastdaySycee", syceeService.getLastdaySycee(userId));// 昨日元宝
			mv.addObject("page", syceeService.mySyceeList(currentUser().getUserId(), pageNum, 20));
			mv.addObject("signItem", syceeService1.getSignItem());// 签到帖
		} catch (Exception e) {
			logger.error("我的元宝加载异常", e);
		}
		return mv;
	}
	//我的红包
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sw_lottnew/{pageNum}")
	public ModelAndView querysw_lottnew(HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/lott/lott_sw");
		RedAccountCnd redAccountCnd=new RedAccountCnd();
		try {
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			redAccountCnd.setUserId(shiroUser.getUserId());
			//查询未开启 未使用的红包
			List<RedAccount>openRedAccounts=redAccountService.queryPageByOpen_hb(shiroUser.getUserId());
			
			//查询已过期，已使用，冻结的红包
			Page p = redAccountService.queryPageByExpiredRed_hb(redAccountCnd,new Page(pageNum,4));
			List<RedAccount> redList = null;
            
			if (p.getResult() != null) {
				redList = (List<RedAccount>) p.getResult();
			}
            mv.addObject("openRedAccounts",openRedAccounts);
            if(null!=openRedAccounts&&openRedAccounts.size()>0){
            	   mv.addObject("openRedSize",openRedAccounts.size());
            }else{
            	   mv.addObject("openRedSize",0);
            }
			mv.addObject("redList", redList);
			mv.addObject("pageNum", p.getPageNo());
			mv.addObject("pageSize", p.getPageSize());
			mv.addObject("totalCount", p.getTotalCount());
			mv.addObject("totalPage", p.getTotalPage());
			mv.addObject("page", p);

		} catch (Exception e) {
			logger.info("查询异常信息:" + e.getMessage());
		}

		return mv;

	}
	//推荐奖励
	@RequestMapping(value = "/jljl_lottnew/{pageNum}/{isRecommendSuccess}/{redId}")
	public ModelAndView queryjljl_lottnew(HttpServletRequest request, @PathVariable int pageNum, @PathVariable int isRecommendSuccess, @PathVariable int redId) {
		ModelAndView mv = new ModelAndView("/lott/lott_jljl");
		ShiroUser shiroUser = currentUser();
		if (null == shiroUser) {
			return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
		}
		try {
			if(redId>0){
				String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5()+"&redId="+redId;
				mv.addObject("recommendPath", recommendPath);
			}else{
				String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5();
				mv.addObject("recommendPath", recommendPath);
			}
			FriendCnd friendCnd=new  FriendCnd();
			friendCnd.setUserId(shiroUser.getUserId());
			friendCnd.setIsRecommendSuccess(isRecommendSuccess);
			Page page = friendService.queryInviteDetailsByUserIdNew(friendCnd, pageNum, 10);
			mv.addObject("page", page);
			mv.addObject("sumMoney", friendService.queryAllFriendSumMoneyByUserId(shiroUser.getUserId())).addObject("isRecommendSuccess",isRecommendSuccess).addObject("redId",redId);
			
			//查询推荐总人数
			mv.addObject("inviterMember",inviterMapper.countInviteDetailsByUserId(shiroUser.getUserId()));
			//查询推荐成功人数
			FriendCnd friendCnd1=new  FriendCnd();
			friendCnd1.setUserId(shiroUser.getUserId());
			friendCnd1.setIsRecommendSuccess(1);
			mv.addObject("inviterSuccess",inviterMapper.countInviteDetailsByUserIdNew(friendCnd1));
			//查询推荐人奖励总额
			mv.addObject("inviterReward",inviterMapper.queryRewardTotal(shiroUser.getUserId()));
			//查询红包奖励总额
			mv.addObject("inviterRedReward",redAccountMapper.queryRedRewardTotal(shiroUser.getUserId(),1980));
			//查询推荐共享奖
			mv.addObject("inviterMoney",inviterMapper.queryReward(shiroUser.getUserId(),4));
		} catch (Exception e) {
			logger.error("查询异常信息", e);
		}
		return mv;
	}

//==========================官网我的账户改版end================================//
}
