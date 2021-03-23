package com.dxjr.portal.lottery.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.lottery.service.LotteryUseRecordService;
import com.dxjr.portal.lottery.vo.LotteryChanceInfoVo;
import com.dxjr.portal.lottery.vo.LotteryDraw;
import com.dxjr.portal.lottery.vo.LotteryUseRecordVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;
import com.swetake.util.Qrcode;

@Controller
@RequestMapping(value = "/lottery_chance")
public class LotteryChanceInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(LotteryChanceInfoController.class);

	@Autowired
	private RedAccountService redService;

	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private LotteryUseRecordService lotteryUseRecordService;
	@Autowired
	MemberService memberService;
	@Autowired
	private TendRecordService tenderRecordService;
	@Autowired
	private TransferService transferService;

	@Autowired
	private FixBorrowService fixBorrowService;
	@Autowired
	MemberMapper memberMapper;

	/**
	 * <p>
	 * Description:进入抽奖页面<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/info")
	public ModelAndView newPFirst(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/lottery/chance_index");
		ShiroUser shiroUser = currentUser();
		if (shiroUser != null) {
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			mv.addObject("memberApproVo", memberApproVo);
			mv.addObject(
					"mobileChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER,
							shiroUser.getUserId()));
			mv.addObject(
					"realNameChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_REAL_NAME_APPRO,
							shiroUser.getUserId()));
			mv.addObject(
					"firstInvestChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_FIRST_INVEST,
							shiroUser.getUserId()));
			mv.addObject(
					"investGradeChanceCount",
					lotteryChanceInfoService.queryLotteryNumTotalByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_INVEST_GRADE,
							shiroUser.getUserId()));
			// 查询该用户的投资总金额
			BigDecimal totalInvestAccount = lotteryChanceInfoService.queryInvestTotalByUserId(shiroUser.getUserId());
			if (totalInvestAccount.compareTo(BigDecimal.ZERO) == 0) {
				mv.addObject("totalInvestAccount", BigDecimal.ZERO);
			} else {
				mv.addObject("totalInvestAccount", totalInvestAccount);
			}
			// 统计抽奖总次数
			Integer chanceTotalNum = lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());
			mv.addObject("chanceTotalNum", chanceTotalNum);
		}
		return mv.addObject("signAwardNum", memberMapper.getSignAwardNum());
	}

	/**
	 * <p>
	 * Description:查询24小时内获取抽奖机会记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月17日
	 * @param session
	 * @param request
	 * @return Integer
	 */
	@RequestMapping(value = "/queryChanceInfoRecord")
	@ResponseBody
	public List<LotteryChanceInfoVo> queryChanceInfoRecord(HttpSession session, HttpServletRequest request) {
		// 24小时前时间
		Date date = DateUtils.hoursOffset(new Date(), -24);
		// 查询24小时内的抽奖机会信息
		List<LotteryChanceInfoVo> lotteryChanceInfoVoList = null;
		try {
			lotteryChanceInfoVoList = lotteryChanceInfoService.selectNewChanceInfoByDate(date);
			String username = "";

			for (LotteryChanceInfoVo vrvo : lotteryChanceInfoVoList) {
				username = vrvo.getUsername();
				vrvo.setUsername(StrinUtils.stringSecretTo(username));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取抽奖机会记录出错", e);
		}
		return lotteryChanceInfoVoList;
	}

	/**
	 * <p>
	 * Description:查询24小时内中奖记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月17日
	 * @param session
	 * @param request
	 * @return Integer
	 */
	@RequestMapping(value = "/lotteryUseRecord")
	@ResponseBody
	public List<LotteryUseRecordVo> lotteryUseRecord(HttpSession session, HttpServletRequest request) {
		// 24小时前时间
		Date date = DateUtils.hoursOffset(new Date(), -24);
		// 查询24小时内的获奖信息
		List<LotteryUseRecordVo> lotteryUseRecordVoList = null;
		try {
			String username = "";
			lotteryUseRecordVoList = lotteryUseRecordService.selectNewUseRecordByDate(date);

			for (LotteryUseRecordVo vrvo : lotteryUseRecordVoList) {
				username = vrvo.getUsername();
				vrvo.setUsername(StrinUtils.stringSecretTo(username));
			}
		} catch (Exception e) {
			logger.error("获取中奖记录出错", e);
		}
		return lotteryUseRecordVoList;
	}

	@RequestMapping(value = "/lotteryMaxGoods")
	@ResponseBody
	public List<LotteryUseRecordVo> lotteryMaxGoods(HttpSession session, HttpServletRequest request) {
		// 查询最新10条大奖记录（包含IPhone6、1888元）
		List<LotteryUseRecordVo> lotteryUseRecordVoList = null;
		try {
			lotteryUseRecordVoList = lotteryUseRecordService.selectNewUseRecordForMaxGoods();
			String username = "";
			for (LotteryUseRecordVo vrvo : lotteryUseRecordVoList) {
				username = vrvo.getUsername();
				vrvo.setUsername(StrinUtils.stringSecretTo(username));
			}
		} catch (Exception e) {
			logger.error("获取大奖记录出错", e);
		}
		return lotteryUseRecordVoList;
	}

	/**
	 * <p>
	 * Description:统计抽奖机会有效总次数<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月22日
	 * @param session
	 * @param request
	 * @return Integer
	 */
	@RequestMapping(value = "/queryLotteryChanceUseNumTotal")
	@ResponseBody
	public Integer queryLotteryChanceUseNumTotal(String flag) {
		int total = 0;
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser != null && shiroUser.getUserId() != null) {
				total = lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());

				if ("red".equals(flag)) {
					total += redService.getUnuseCount(currentUser().getUserId());
				}
			}
		} catch (Exception e) {
			logger.error("统计抽奖机会有效总次数", e);
		}
		return total;
	}

	/**
	 * <p>
	 * Description:抽奖<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param session
	 * @param request
	 * @param lottvo
	 * @return LottVo
	 */
	@RequestMapping(value = "/lotteryDraw")
	@ResponseBody
	public LotteryDraw lotteryDraw(HttpSession session, HttpServletRequest request) {
		LotteryDraw lotteryDraw = new LotteryDraw();
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null || shiroUser.getUserId() == null) {
				lotteryDraw.setsFlag("fail");
				lotteryDraw.setsAngel(0);
				lotteryDraw.seteAngel(0);
				lotteryDraw.setMessage("请先登录！");
				lotteryDraw.setDtnTime(0);
				lotteryDraw.setPosition(0);
			} else {
				lotteryDraw = lotteryChanceInfoService.lotteryDraw(shiroUser.getUserId());
				lotteryDraw.setAwardNum(lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId()));
			}
		} catch (Exception e) {
			logger.error("抽奖出错", e);
			lotteryDraw.setsFlag("fail");
			lotteryDraw.setsAngel(0);
			lotteryDraw.seteAngel(0);
			lotteryDraw.setMessage("抽奖出错，请联系客服！");
			lotteryDraw.setDtnTime(0);
			lotteryDraw.setPosition(0);
			lotteryDraw.setAwardNum(0);
		}
		return lotteryDraw;
	}

	public List<Map<String, Object>> zhuan() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();

		int[] shuzu = { 0, 1, 2, 3, 4, 4, 4, 4 };
		// int[] shuzu = {1,1,1,1,2,2,2,2};
		// 数组长度8
		int m = 0; // 返回数据 开始idx
		int n = 3; // 返回数据 结束idx
		// 数字随机,向下获取随机数整数Math.floor(), 随机数 * 最基本基数(arr.length数组长度)，得到0-7之间的随机idx
		double sz = Math.random() * (n - m + 1) + m;
		System.err.println(sz);
		int sz_idx = (int) Math.floor(sz);
		System.err.println(sz_idx);
		int zhizheng = shuzu[sz_idx]; // 0 -7
		System.err.println(zhizheng);
		int sAngel = 0; // 起始角度
		int eAngel = 0; // 起始角度 + 默认旋转的圈数
		int dtnTime = 3000; // /转动时间
		String message = "";
		int zhuanQ = 360 * 50; // 旋转 圈数

		switch (zhizheng) {
		case 1:
			eAngel = 160 + zhuanQ;
			message = "一等奖";
			break;
		case 2:
			eAngel = 250 + zhuanQ;
			message = "二等奖";
			break;
		case 3:
			eAngel = 35 + zhuanQ;
			message = "三等奖";
			break;
		default:

			int[] buzhong_sz = { 50, 100, 190, 350, 300 };
			int buzhong_r = (int) Math.floor(Math.random() * buzhong_sz.length);
			eAngel = buzhong_sz[buzhong_r] + zhuanQ;
			message = "没有中奖";
			break;
		}
		map1.put("sAngel", sAngel);
		map1.put("eAngel", eAngel);
		map1.put("dtnTime", dtnTime);
		map1.put("message", message);
		/*
		 * System.out.println(zhizheng + "," + sAngel + ", " + eAngel + ", " + dtnTime + " -  " + message);
		 */
		maps.add(map1);
		return maps;
	}

	/**
	 * <p>
	 * Description:跳到推荐页面<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param session
	 * @param request
	 * @param lottvo
	 * @return LottVo
	 */
	@RequestMapping(value = "/toRecommendaward")
	@RequiresAuthentication
	public ModelAndView toRecommendaward(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/lottery/recommend_award");
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			mv.addObject("recommendPath", "");
		} else {
			// 查询推荐链接
			String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5();
			mv.addObject("recommendPath", recommendPath);
		}
		return mv;
	}

	@RequestMapping(value = "/toNewAward")
	public ModelAndView toNewAward(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/newp/newPeoplePage").addObject("user", currentUser());
		// ModelAndView mv = new ModelAndView("/lottery/newAward").addObject("user", currentUser());
		ShiroUser shiroUser = super.currentUser();
		if (shiroUser != null) {
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			mv.addObject("memberApproVo", memberApproVo);
			mv.addObject(
					"mobileChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER,
							shiroUser.getUserId()));
			mv.addObject(
					"realNameChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_REAL_NAME_APPRO,
							shiroUser.getUserId()));
			mv.addObject(
					"firstInvestChanceCount",
					lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_FIRST_INVEST,
							shiroUser.getUserId()));
			// 查询该用户的投资总金额
			BigDecimal totalInvestAccount = lotteryChanceInfoService.queryInvestTotalByUserId(shiroUser.getUserId());
			if (totalInvestAccount.compareTo(BigDecimal.ZERO) == 0) {
				mv.addObject("totalInvestAccount", BigDecimal.ZERO);
			} else {
				mv.addObject("totalInvestAccount", totalInvestAccount);
			}

			// 是否新手
			boolean isNewShou = true;
			if (tenderRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0) {
				isNewShou = false;
			}
			if (transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
				isNewShou = false;
			}
			mv.addObject("isNewShou", isNewShou);
		}
		// ------------------------------------

		// 新手专享，新手标
//		BorrowVo borrowVo = newPService.getAdvancedNew();
//		if (borrowVo != null) {
//			mv.addObject("borrowVo", borrowVo);
//			mv.addObject("scheduleStr", borrowVo.getScheduleStrNoDecimal());
//		}
		// 新增新手宝记录
		FixBorrowVo fixBorrowNew = null;
		try {
			fixBorrowNew = fixBorrowService.getNewFixBorrow();
			if (fixBorrowNew != null) {
				mv.addObject("fixBorrowNew", fixBorrowNew);
				mv.addObject("scheduleStr", fixBorrowNew.getScheduleStrNoDecimal());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("source", request.getParameter("source"));
		return mv;
	}

	@RequestMapping(value = "/toEncoderQRCoder")
	public void toEncoderQRCoder(@RequestParam(value = "url", required = true) String url, HttpServletResponse response) {
		encoderQRCoder(url, response);
		return;
	}

	public void encoderQRCoder(String content, HttpServletResponse response) {
		try {
			Qrcode handler = new Qrcode();
			handler.setQrcodeErrorCorrect('M');
			handler.setQrcodeEncodeMode('B');
			handler.setQrcodeVersion(7);

			System.out.println(content);
			byte[] contentBytes = content.getBytes("UTF-8");

			BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);

			// 设定图像颜色：BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置肯能导致解析出错
			int pixoff = 2;
			// 输出内容：二维码
			if (contentBytes.length > 0 && contentBytes.length < 124) {
				boolean[][] codeOut = handler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
			}

			gs.dispose();
			bufImg.flush();

			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "jpg", response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			Qrcode handler = new Qrcode();
			handler.setQrcodeErrorCorrect('M');
			handler.setQrcodeEncodeMode('B');
			handler.setQrcodeVersion(7);

			String content = "http://www.dxjr.com/member/toRegisterPage.html?code=DB09D320F63119E7558A48D1B4FF55AE";
			byte[] contentBytes = content.getBytes("UTF-8");

			BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);

			// 设定图像颜色：BLACK
			gs.setColor(Color.BLACK);

			// 设置偏移量 不设置肯能导致解析出错
			int pixoff = 2;
			// 输出内容：二维码
			if (contentBytes.length > 0 && contentBytes.length < 124) {
				boolean[][] codeOut = handler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
			}

			gs.dispose();
			bufImg.flush();

			File file = new File("D://test.jpg");

			if (!file.exists()) {
				file.createNewFile();
			}

			ImageIO.write(bufImg, "jpg", new FileOutputStream(file));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Description:抽奖提醒<br />
	 * </p>
	 * 
	 * @author LiuTao
	 * @version 0.1 2016年1月21日
	 * @param session
	 * @param request
	 * @return Integer
	 */
	@RequestMapping(value = "/LotteryTip")
	@ResponseBody
	public Integer queryLotteryChanceNumTotal(HttpSession session, HttpServletRequest request) {
		int lotteryChanceCount = 0;
		Object lotteryChance = currentSession().getAttribute("lotteryChanceCount");
		if (lotteryChance != null) {
			lotteryChanceCount = (int) lotteryChance;
			lotteryChanceInfoService.updatelotteryChanceState(currentUser().getUserId());
			session.removeAttribute("lotteryChanceCount");
		}
		return lotteryChanceCount;
	}

}
