package com.dxjr.portal.transfer.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowDetailService;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.constant.TransferConstant;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;
import com.dxjr.portal.transfer.vo.SubscribeTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

@Controller
@RequestMapping(value = "/zhaiquan")
public class TransferController extends BaseController {

	Logger logger = LoggerFactory.getLogger(TransferController.class);

	@Autowired
	private TransferService transferService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	RealNameApproService realNameApproService;

	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;

	@Autowired
	private BorrowDetailService borrowDetailService;

	private static DecimalFormat df = new DecimalFormat("#,##0.##");

	@Autowired
	private VipLevelService vipLevelService;

	@Autowired
	private BankInfoService bankInfoService;

	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private BaseEBankInfoMapper baseEBankInfoMapper;

	/**
	 * <p>
	 * Description:债权转让-转入列表页面value = "toTransferList"<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月21日
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView toTransferList(String type) {
		ModelAndView mv = new ModelAndView("transfer/transferList1");
		if ("first".equals(type)) {
			mv.addObject("type", type);
		}
		return mv;
	}
	@RequiresAuthentication
	@RequestMapping("/totransfercontainer")
	public ModelAndView transfercontainer() {
		ModelAndView transferPage = new ModelAndView("transfer/transfercontainer");
		return transferPage;
	}

	/**
	 * <p>
	 * Description:查询我可转让的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月28日
	 * @param transferCnd
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping("/mytransferlist/1/{pageNo}")
	public ModelAndView myTransferList(@ModelAttribute TransferCnd transferCnd, @PathVariable("pageNo") int pageNo) {
		ModelAndView transferPage = new ModelAndView();

		Integer type = transferCnd.getType();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			transferPage = null;
			return transferPage;
		}

		Integer userId = shiroUser.getUserId();
		transferCnd.setUserId(userId);

		try {
			Page page = new Page(pageNo, 10);
			// 可以转让的债权
			transferPage.setViewName("transfer/myeabletransferlist");
			transferService.queryTransferClaim(transferCnd, page);
			transferPage.addObject("page", page);
		} catch (Exception e) {
			logger.error("myTransferList出错userid:" + userId + ",type:" + type);
			transferPage = null;
		}
		return transferPage;
	}

	/**
	 * <p>
	 * Description:查询我正在转让中的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月28日
	 * @param transferCnd
	 * @return ModelAndView
	 */
	@RequestMapping("/mytransferlist/2/{pageNo}")
	public ModelAndView myTransferingList(@ModelAttribute TransferCnd transferCnd, @PathVariable("pageNo") int pageNo) {
		ModelAndView transferPage = new ModelAndView();

		Integer type = transferCnd.getType();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			transferPage = null;
			return transferPage;
		}
		Integer userId = shiroUser.getUserId();
		try {
			Page page = new Page(pageNo, 10);
			transferCnd.setUserId(userId);
			// 转让中债权
			transferPage.setViewName("transfer/mytransferinglist");
			transferCnd.setStatus(Constants.TRANSFER_STATU_ING);
			transferService.queryMyTransfer(transferCnd, page);
			transferPage.addObject("page", page);

		} catch (Exception e) {
			logger.error("myTransferingList出错userid:" + userId + ",type:" + type);
			transferPage = null;
		}
		return transferPage;
	}

	/**
	 * <p>
	 * Description:查询我转出的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月28日
	 * @param transferCnd
	 * @return ModelAndView
	 */
	@RequestMapping("/mytransferlist/3/{pageNo}")
	public ModelAndView myTransferoutList(@ModelAttribute TransferCnd transferCnd, @PathVariable("pageNo") int pageNo) {
		ModelAndView transferPage = new ModelAndView();

		Integer type = transferCnd.getType();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			transferPage = null;
			return transferPage;
		}

		Integer userId = shiroUser.getUserId();
		transferCnd.setUserId(userId);
		try {
			Page page = new Page(pageNo, 10);
			transferPage.setViewName("transfer/mytransferoutlist");
			transferCnd.setStatus(Constants.TRANSFER_SUCCESS);
			transferService.queryMyTransfer(transferCnd, page);
			transferPage.addObject("page", page);
		} catch (Exception e) {
			logger.error("myTransferoutList出错userid:" + userId + ",type:" + type);
			transferPage = null;
		}
		return transferPage;
	}

	/**
	 * <p>
	 * Description:查询我转入中的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月28日
	 * @param transferCnd
	 * @return ModelAndView
	 */
	@RequestMapping("/mytransferlist/5/{pageNo}")
	public ModelAndView myTransferiningList(@ModelAttribute TransferCnd transferCnd, @PathVariable("pageNo") int pageNo) {
		ModelAndView transferPage = new ModelAndView();

		Integer type = transferCnd.getType();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			transferPage = null;
			return transferPage;
		}

		Integer userId = shiroUser.getUserId();
		transferCnd.setUserId(userId);
		try {
			Page page = new Page(pageNo, 10);
			transferPage.setViewName("transfer/mytransferininglist");
			transferCnd.setStatus(Constants.TRANSFER_STATU_ING);
			transferService.queryMyTransfer(transferCnd, page);
			transferPage.addObject("page", page);
		} catch (Exception e) {
			logger.error("myTransferinList出错userid:" + userId + ",type:" + type);
			transferPage = null;
		}
		return transferPage;
	}

	/**
	 * <p>
	 * Description:查询我转入的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月28日
	 * @param transferCnd
	 * @return ModelAndView
	 */
	@RequestMapping("/mytransferlist/4/{pageNo}")
	public ModelAndView myTransferinList(@ModelAttribute TransferCnd transferCnd, @PathVariable("pageNo") int pageNo) {
		ModelAndView transferPage = new ModelAndView();

		Integer type = transferCnd.getType();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			transferPage = null;
			return transferPage;
		}

		Integer userId = shiroUser.getUserId();
		transferCnd.setUserId(userId);
		try {
			Page page = new Page(pageNo, 10);
			transferPage.setViewName("transfer/mytransferinlist");
			transferCnd.setStatus(Constants.TRANSFER_SUCCESS);
			transferService.queryMyTransfer(transferCnd, page);
			transferPage.addObject("page", page);
		} catch (Exception e) {
			logger.error("myTransferinList出错userid:" + userId + ",type:" + type);
			transferPage = null;
		}
		return transferPage;
	}
	
	/**
	 * 
	 * <p>
	 * Description:去债权转让判断<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年7月27日
	 * @param tenderId
	 * @return
	 * @throws Exception
	 * MessageBox
	 */
	@RequestMapping("/toTransferJudge/{tenderId}")
	@ResponseBody
	public MessageBox toTransferJudge(@PathVariable("tenderId") Integer tenderId) throws Exception {
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return new MessageBox("1", "未登录，请先登录");
		}
		return transferService.toTransferJudge(shiroUser.getUserId(), tenderId);
	}

	/**
	 * <p>
	 * Description:跳转到转让页面<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月30日
	 * @param tenderId
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping("/toTransfer/{tenderId}")
	public ModelAndView toTransfer(@PathVariable("tenderId") Integer tenderId) throws Exception {
		ModelAndView transferPage = new ModelAndView();
		ShiroUser shiroUser = currentUser();

		if (shiroUser == null) {
			transferPage = new ModelAndView("page/common/404");
			return transferPage;
		}

		transferPage.setViewName("transfer/transfer");
		Integer userId = shiroUser.getUserId();
		BTransfer transfer = transferService.queryTransferByTenderIdAndUserId(tenderId, userId);

		/*
		 * if (((BTransferVo) transfer).getParentId() != null) { transferPage = new ModelAndView("page/common/404"); }
		 */

		// 设置默认
		transfer.setTenderId(tenderId);
		transfer.setCoef(new BigDecimal(1));
		if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-07-01 00:00:00").before(new Date())){
			transfer.setManageFee(transfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATANEW)).setScale(2, RoundingMode.HALF_UP));
		}else{
			transfer.setManageFee(transfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATA)).setScale(2, RoundingMode.HALF_UP));
		}
		// 判断该用户是否是终身顶级会员
		try {
			if (vipLevelService.getIsSvipByUserId(shiroUser.getUserId())) {
				transfer.setManageFee(BigDecimal.ZERO);
			}
		} catch (Exception e) {

		}


		BorrowVo borrow = borrowService.selectByPrimaryKey(transfer.getBorrowId());
		// 非存管用户不能进行发起存管转让操作
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
		if ( (baseEBankInfo == null || baseEBankInfo.getStatus() == null || baseEBankInfo.getStatus() != 1)
				&& (borrow.getIsCustody() !=null && borrow.getIsCustody() == 1)) {
			transferPage.addObject("isCustody", 0);
		}else{
			transferPage.addObject("isCustody", 0);
		}
		transfer.setAccount(transfer.getAccount().setScale(2, RoundingMode.HALF_UP));
		transfer.setAccountReal(transfer.getAccount());
		transferPage.addObject("transfer", transfer);
		return transferPage;
	}

	/**
	 * <p>
	 * Description:执行转让操作<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月30日
	 * @param pageBtransferVo
	 * @param request
	 * @return MessageBox
	 */
	@RequestMapping("/dotransfer")
	@ResponseBody
	public MessageBox dotransfer(@ModelAttribute BTransferVo pageBtransferVo, HttpServletRequest request) {
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return new MessageBox("0", "请登录");
			}

			Integer userId = shiroUser.getUserId();



			// 验证前端数据 正确性
			validateParam(pageBtransferVo, request);
			// 验证数据的正确性
			BTransferVo btransfer = validatData(pageBtransferVo, userId);
			// 设置属性
			setServiceParam(btransfer, pageBtransferVo, request, userId);
			transferService.addTransfer(pageBtransferVo);
			return new MessageBox("1" + pageBtransferVo.getId() + "/" + pageBtransferVo.getBorrowId(), "转让成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.getStackTrace();
			return new MessageBox("0", e.getMessage());
		}

	}

	private void setServiceParam(BTransfer btransfer, BTransfer pageBtransfer, HttpServletRequest request, Integer userId) throws Exception {

		BigDecimal account = pageBtransfer.getAccount();
		BigDecimal coef = pageBtransfer.getCoef();
		String ip = HttpTookit.getRealIpAddr(request);
		pageBtransfer.setAccountReal(account.multiply(coef).setScale(2, RoundingMode.HALF_UP));
		pageBtransfer.setUserId(userId);

		BeanUtils.copyProperties(btransfer, pageBtransfer, new String[] { "account", "coef", "accountReal", "manageFee", "transferName", "transferContent",
				"bidPassword", "userId", "mostAccount", "lowestAccount", "validTime", "tenderId", "borrowId", "transferCreditRating", "validMinute" });
			if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-07-01 00:00:00").before(new Date())){
				pageBtransfer.setManageFee(pageBtransfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATANEW)).setScale(2, RoundingMode.HALF_UP));
			}else{
				pageBtransfer.setManageFee(pageBtransfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATA)).setScale(2, RoundingMode.HALF_UP));
			}
		ShiroUser shiroUser = currentUser();
		// 判断该用户是否是终身顶级会员
		if (vipLevelService.getIsSvipByUserId(shiroUser.getUserId())) {
			pageBtransfer.setManageFee(BigDecimal.ZERO);
			// pageBtransfer.setInterestManageFee(BigDecimal.ZERO);
		}

		pageBtransfer.setStatus(Constants.TRANSFER_STATU_NEW_WAIT);// 待审核
		pageBtransfer.setSendmessage(0);
		pageBtransfer.setAddIp(ip);
		Date date = new Date();
		pageBtransfer.setPlatForm(currentUser().getPlatform());
		pageBtransfer.setAddTime(date);
		// 设置结束时间
		if (!StringUtils.isEmpty(pageBtransfer.getBidPassword())) {
			// 当天流债转的时间
			Date calCurDate = com.dxjr.utils.DateUtils.getDateByTime(new Date(), 22, 29, 0);
			if (date.getTime() > calCurDate.getTime()) {
				// 如果当前时间大于10点半，就是明天的10点半
				Date tomDate = DateUtils.addDays(date, 1);
				calCurDate = com.dxjr.utils.DateUtils.getDateByTime(tomDate, 22, 29, 0);
			}
			pageBtransfer.setEndTime(calCurDate);
			// 当前时间与流债转时间相差分钟数
			int minute = com.dxjr.utils.DateUtils.minuteDiff(calCurDate, date);
			pageBtransfer.setValidMinute(minute);
		} else {
			pageBtransfer.setEndTime(DateUtils.addDays(date, pageBtransfer.getValidTime()));
		}
		pageBtransfer.setIsAutotransfer(0);
		pageBtransfer.setAccountYes(new BigDecimal(0));
		pageBtransfer.setContractNo("");
		pageBtransfer.setTransferCreditRating("A");
		if (pageBtransfer.getGainLoss() == null) {
			pageBtransfer.setGainLoss(BigDecimal.ZERO);
		}
		if (pageBtransfer.getInterestManageFee() == null) {
			pageBtransfer.setInterestManageFee(BigDecimal.ZERO);
		}
		pageBtransfer.setInterestDiff(pageBtransfer.getGainLoss().subtract(pageBtransfer.getInterestManageFee()));
		pageBtransfer.setAddMac("");
		pageBtransfer.setTenderTimes(0);
		pageBtransfer.setTransferName(btransfer.getBorrowName());// 转让名字默认为借款标题

		BigDecimal currApr = new BigDecimal(0);
		currApr = getNowApr(btransfer.getBorrowApr(), pageBtransfer.getCoef(), btransfer.getBorrowStyle(), btransfer.getTimeLimit(),
				btransfer.getTimeLimitReal(), btransfer.getAccountSurplus());
		pageBtransfer.setCurrApr(currApr);
		if (!StringUtils.isEmpty(pageBtransfer.getBidPassword())) {
			pageBtransfer.setBidPassword(MD5.toMD5(pageBtransfer.getBidPassword()));
		}

	}

	private BTransferVo validatData(BTransfer pageTransfer, Integer userId) throws Exception {

		Integer tenderId = pageTransfer.getTenderId();

		BTransferVo transfer = transferService.queryTransferByTenderIdAndUserId(tenderId, userId);

		/*
		 * if (transfer.getParentId() != null) { throw new RuntimeException("暂不支持债权二次转让，敬请谅解！"); }
		 */

		Integer borrowId = transfer.getBorrowId();
		if (borrowId.intValue() != pageTransfer.getBorrowId().intValue()) {
			throw new RuntimeException("借款标的参数不正确");
		}
		transfer.setAccount(transfer.getAccount().setScale(2, RoundingMode.HALF_UP));
		BigDecimal account = transfer.getAccount();

		if (account.doubleValue() != pageTransfer.getAccount().doubleValue()) {
			throw new RuntimeException("债权价格金额不正确");
		}

		BigDecimal interest = transfer.getInterest();
		if (interest.doubleValue() < 0) {
			logger.error("利息计算有误tenderId" + tenderId + ",userId" + userId + ",interest" + interest);
			throw new RuntimeException("转让失败");
		}

		BigDecimal mostAccount = pageTransfer.getMostAccount();

		if (mostAccount.doubleValue() > account.multiply(pageTransfer.getCoef()).setScale(2, RoundingMode.HALF_UP).doubleValue()) {
			throw new RuntimeException("最大认购金额不能超过转让价格");
		}


		// 非存管用户不能进行发起存管转让操作
	/*	BorrowVo borrow = borrowService.selectByPrimaryKey(transfer.getBorrowId());
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
		if ( (baseEBankInfo == null || baseEBankInfo.getStatus() == null || baseEBankInfo.getStatus() != 1)
				&& (borrow.getIsCustody() !=null && borrow.getIsCustody() == 1)) {
			throw new RuntimeException("非存管用户不能进行发起存管转让操作");
		}*/

		return transfer;

	}

	private void validateParam(BTransfer btransfer, HttpServletRequest request) {

		if (btransfer == null) {
			throw new RuntimeException("参数错误");
		}

		try {
			BorrowVo borrow = borrowService.selectByPrimaryKey(btransfer.getBorrowId());
			if(borrow.getIsCustody() !=null && borrow.getIsCustody().intValue() ==1 ){
				throw new RuntimeException("存管标目前暂不支持债权转让");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("借款标信息查询异常");
		}

		String validatecode = request.getParameter("checkcode");
		String randCode = (String) request.getSession().getAttribute("randomCode");
		if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
			throw new RuntimeException("验证码不正确");
		}

		if (btransfer.getTenderId() == null) {
			throw new RuntimeException("投标记录不能为空");
		}
		if (btransfer.getBorrowId() == null) {
			throw new RuntimeException("借款标不能为空");
		}




		/*
		 * if (StringUtils.isEmpty(btransfer.getTransferName())) { throw new RuntimeException("转让标题不能为空"); } if (btransfer.getTransferName().length() > TransferConstant.TRANSFERNAMELENGTH) { throw new
		 * RuntimeException("转让标题长度不能超过30字符"); } if (!StringUtils.isEmpty(btransfer.getTransferContent())) { if (btransfer.getTransferContent().length() > TransferConstant.TRANSFERCONTENTLENGTH) {
		 * throw new RuntimeException("转让内容长度不能超过100字符"); } }
		 */

		BigDecimal account = btransfer.getAccount();
		if (account == null) {
			throw new RuntimeException("债权价格不能为空");
		}

		BigDecimal coef = btransfer.getCoef();
		if (coef == null) {
			throw new RuntimeException("转让系数不能为空");
		}

		double coefVal = coef.doubleValue();

		if (String.valueOf(coefVal).length() > 5) {
			throw new RuntimeException("转让系数格式不正确");
		}

		if (!(TransferConstant.COEFMIN <= coefVal && coefVal <= TransferConstant.COEFMAX)) {
			throw new RuntimeException(String.format("转让系数必须在%s-%s之间", String.valueOf(TransferConstant.COEFMIN), String.valueOf(TransferConstant.COEFMAX)));
		}

		BigDecimal mostAccount = btransfer.getMostAccount();
		if (mostAccount == null) {
			throw new RuntimeException("最大认购金额不能为空");
		}

		if (mostAccount.doubleValue() < 50) {
			throw new RuntimeException("最大认购金额必须大于50元");
		}

		matchMoney(mostAccount, "最大认购金额");

		BigDecimal lowestAccount = btransfer.getLowestAccount();
		if (lowestAccount == null) {
			throw new RuntimeException("最小认购金额不能为空");
		}

		if (lowestAccount.doubleValue() < 50) {
			throw new RuntimeException("最小认购金额不得低于50");
		}

		if (lowestAccount.doubleValue() > mostAccount.doubleValue()) {
			throw new RuntimeException("最小认购金额不能大于最大认购金额");
		}

		matchMoney(lowestAccount, "最小认购金额");

		Integer validTime = btransfer.getValidTime();
		if (validTime == null) {
			throw new RuntimeException("失效时间不能够为空");
		}

		if (!(1 <= validTime.intValue() && validTime.intValue() <= 3)) {
			throw new RuntimeException("失效时间必须在1~3之间");
		}

		String bidPassword = btransfer.getBidPassword();
		if (!StringUtils.isEmpty(bidPassword)) {
			if (!bidPassword.matches("[0-9a-zA-Z]{6,12}")) {
				throw new RuntimeException("认购密码必须为字母或数字并且密码长度必须在6~12位之间");
			}
		}

	}

	private static void matchMoney(BigDecimal paymoney, String msg) {

		String paymoneyStr = String.valueOf(paymoney.doubleValue());

		if (paymoney.doubleValue() == 0) {
			throw new RuntimeException(msg + "不能为0！");
		}

		if (paymoneyStr.indexOf('.') != -1) {
			String paymoneyStrEnd = paymoneyStr.substring(paymoneyStr.indexOf('.') + 1);

			if (paymoneyStrEnd.length() > 2) {
				throw new RuntimeException(msg + "输入金额有误！");
			}

		}

	}

	/**
	 * <p>
	 * Description:债权转让-列表查询<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月21日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "queryTransferList")
	public ModelAndView queryTransferList(@RequestParam Integer pageNum, @ModelAttribute SearchTransferVo seach) {
		ModelAndView mv = new ModelAndView("transfer/transferGridByPage");

		Page p = null;
		try {

			p = transferService.findtransferList(seach, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));

		} catch (Exception e) {
			logger.error("进入债权转让出错");
			e.printStackTrace();
		}
		mv.addObject("page", p);
		return mv;
	}

	/**
	 * <p>
	 * Description:债权转让-旧数据处理<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月21日
	 */
	@RequestMapping(value = "updateTransferOld")
	public void updateTransferOld() {

		transferService.updateTransferOld();

	}

	/**
	 * <p>
	 * Description:债权转让-进入认购页面<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月21日
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "toTransferSubscribe/{id}")
	public ModelAndView toTransferSubscribe(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("transfer/transferSubscribe");

		AccountVo accountVo = new AccountVo();
		BTransferVo bTransferVo = new BTransferVo();

		ShiroUser shiroUser = currentUser();

		// 自动购买金额
		BigDecimal effectiveTenderMoney = new BigDecimal(0);

		BigDecimal alsoNeedMoney = new BigDecimal(0);

		try {
			// 获取个人账户余额
			accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 债权转让信息
			bTransferVo = transferService.selectByPrimaryKey(id);
			// 剩余可认购金额
			alsoNeedMoney = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
			mv.addObject("alsoNeed", alsoNeedMoney);

			// 个人已认购金额
			TransferCnd transferCnd = new TransferCnd();
			transferCnd.setTransferId(id);
			transferCnd.setUserId(shiroUser.getUserId());
			String sumAccount = transferService.querySumAccountByUserId(transferCnd);
			mv.addObject("sumAccount", sumAccount);

			/*
			 * // 自动购买金额 = 用户余额 | 当：用户余额大于最小认购额度，并小于债权余额时； if (accountVo.getUseMoney ().compareTo(bTransferVo.getLowestAccount()) == 1 && accountVo.getUseMoney().compareTo(alsoNeedMoney) == -1) {
			 * effectiveTenderMoney = accountVo.getUseMoney(); effectiveTenderMoney = effectiveTenderMoney.subtract(new BigDecimal(sumAccount)); } // 自动购买金额 = 剩余可认购金额 | 当：用户金额大于债转余额，或等于债转余额时； if
			 * (accountVo.getUseMoney().compareTo(alsoNeedMoney) == 1 || accountVo.getUseMoney().compareTo(alsoNeedMoney) == 0) { effectiveTenderMoney = alsoNeedMoney; effectiveTenderMoney =
			 * effectiveTenderMoney.subtract(new BigDecimal(sumAccount)); }
			 */

			// 自动购买金额赋值：
			// 默认赋值=用户金额
			effectiveTenderMoney = accountVo.getUseMoney();
			// 如果自动购买金额大于最大认购额度；=最大认购额度
			if (effectiveTenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
				effectiveTenderMoney = bTransferVo.getMostAccount();
			}
			// 如果自动购买金额大于债权余额；=债权余额
			if (effectiveTenderMoney.compareTo(alsoNeedMoney) == 1) {
				effectiveTenderMoney = alsoNeedMoney;
			}
			// 如果用户余额<最小额度&&债权余额>最小额度；=债权余额
			if (accountVo.getUseMoney().compareTo(bTransferVo.getLowestAccount()) == -1 && alsoNeedMoney.compareTo(bTransferVo.getLowestAccount()) == 1) {
				effectiveTenderMoney = new BigDecimal(0);
			}
			mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
			mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
		} catch (Exception e) {
			logger.error("进入认购页面出错", e);
		}

		mv.addObject("account", accountVo);
		mv.addObject("transfer", bTransferVo);
		mv.addObject("effectiveTenderMoney", effectiveTenderMoney);
		return mv;
	}

	/**
	 * <p>
	 * Description:根据债权id获取当前用户的有效投标金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月10日
	 * @param request
	 * @param id
	 * @return MessageBox
	 */
	@RequestMapping(value = "/findEffectiveTenderMoney/{id}")
	@ResponseBody
	public BigDecimal findEffectiveTenderMoney(HttpServletRequest request, @PathVariable Integer id) {
		ShiroUser shiroUser = currentUser();
		BigDecimal result = BigDecimal.ZERO;
		try {
			// 自动购买金额
			BigDecimal effectiveTenderMoney = BigDecimal.ZERO;
			// 获取个人账户余额
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 债权转让信息
			BTransferVo bTransferVo = transferService.selectByPrimaryKey(id);
			// 剩余可认购金额
			BigDecimal alsoNeedMoney = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);

			BigDecimal useMoney = accountVo.getUseMoney();

			// 如果可使用活期宝金额
			String isUseCurMoney = request.getParameter("isUseCurMoney");
			if (isUseCurMoney != null && isUseCurMoney.equals("1")) {
				BigDecimal maxUseMoney = curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId());
				useMoney = useMoney.add(maxUseMoney);
			}
			effectiveTenderMoney = useMoney;

			// 如果自动购买金额大于最大认购额度；=最大认购额度
			if (effectiveTenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
				effectiveTenderMoney = bTransferVo.getMostAccount();
			}
			// 如果自动购买金额大于债权余额；=债权余额
			if (effectiveTenderMoney.compareTo(alsoNeedMoney) == 1) {
				effectiveTenderMoney = alsoNeedMoney;
			}
			// 如果用户余额<最小额度&&债权余额>最小额度；=债权余额
			if (useMoney.compareTo(bTransferVo.getLowestAccount()) == -1 && alsoNeedMoney.compareTo(bTransferVo.getLowestAccount()) == 1) {
				effectiveTenderMoney = new BigDecimal(0);
			}
			result = effectiveTenderMoney;
		} catch (Exception e) {
			result = new BigDecimal(0);
		}
		return result;
	}

	/**
	 * <p>
	 * Description:债权转让-认购<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月27日
	 * @param request
	 *            、SubscribeTransferBean
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "subscribeTransfer")
	@ResponseBody
	public MessageBox subscribeTransfer(HttpServletRequest request, SubscribeTransferVo stf, String ip) {

		ShiroUser shiroUser = currentUser();
		try {
			if (StringUtils.isEmpty(ip)) {
				ip = HttpTookit.getRealIpAddr(request);
			}
			stf.setPlatform(shiroUser.getPlatform());
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_TRANSFER)) {
				return new MessageBox("-99", "");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new MessageBox("-99", "");
		}
		try {
			// 验证认购数据；
			String result = this.validateSubscribeData(shiroUser, stf, request);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("0", result);
			}

			// 手动认购；
			result = transferService.saveManualSubscribe(stf, shiroUser.getUserId(), ip, Constants.TENDER_TYPE_MANUAL);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("0", result);
			}

			// 自动复审
			transferService.saveApproveTransferRecheck(stf.getTransferid(), Constants.AUTO_CHECK_USERID, Constants.AUTO_CHECK_REMARK,
					HttpTookit.getRealIpAddr(request), shiroUser.getPlatform());
			// 发放“首次投资奖或理财里程碑奖”抽奖机会
			/*
			 * lotteryChanceInfoService.investLevelAwardLotteryChance(shiroUser. getUserId(), stf.getTenderMoney());
			 */
		} catch (AppException ae) {
			logger.error("手动认购失败", ae.getMessage());
			return new MessageBox("0", ae.getMessage());
		} catch (Exception e) {
			logger.error("手动认购失败", e);
			return new MessageBox("0", "认购操作失败，请联系管理人员！");
		}

		return new MessageBox("1", "认购成功");
	}

	private String validateSubscribeData(ShiroUser shiroUser, SubscribeTransferVo stf, HttpServletRequest request) throws Exception {

		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
		if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
				&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
			return "请先进行邮箱或手机认证！";
		}

		// 检查实名认证
		if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return "请先进行实名认证";
		}

		String result = BusinessConstants.SUCCESS;
		// 购买金额
		BigDecimal tenderMoney = stf.getTenderMoney();
		BigDecimal sumAccountMoney = new BigDecimal(0);

		if (shiroUser.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
			return "借款用户不允许认购！";
		}
		if (null == tenderMoney) {
			return "购买金额不能为空！";
		}

		/*if (null == stf.getPayPassword()) {
			return "交易密码不能为空";
		}*/

		// 检查是否绑定了银行卡
		BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
		if (null == bankInfoVo) {
			return "请先绑定银行卡!";
		}

		// 购买金额 | 不能小于最小认购金额，大于可认购金额
		BTransferVo bTransferVo = transferService.selectByPrimaryKey(stf.getTransferid());

		if (shiroUser.getUserId().equals(bTransferVo.getUserId())) {
			return "不能认购本人的债权转让";
		}

		BigDecimal alsoNeed = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
		if (tenderMoney.compareTo(bTransferVo.getLowestAccount()) == -1) {
			if (alsoNeed.compareTo(bTransferVo.getLowestAccount()) == -1) { // 剩余可认购额度<最小认购额度
				if (tenderMoney.compareTo(alsoNeed) == 0) { // 最大可认购金额小于最小认购金额时，认购金额必须等于最大可认购金额；

				} else {
					return "当剩余资金很小时，购买金额必须等于剩余金额！";
				}
			} else {
				return "购买金额小于最小认购额度，无法认购！";
			}

		}
		if (tenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
			return "购买金额大于最大认购额度，无法认购！";
		}
		if (stf.getSumAccount() != null) {
			sumAccountMoney = stf.getSumAccount();
		}
		if (sumAccountMoney.add(tenderMoney).compareTo(bTransferVo.getMostAccount()) == 1) {
			return "累计购买金额大于最大认购额度，无法认购！";
		}

		if (tenderMoney.compareTo(alsoNeed) == 1) {
			return "购买金额大于剩余可认购额度，无法认购！";
		}

		// 账户余额| 不能小于最小认购额度，小于购买金额；
		AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
		BigDecimal useMoney = accountVo.getUseMoney();
		// 如果可使用活期宝金额
		if (stf.getIsUseCurMoney() != null && stf.getIsUseCurMoney().equals("1")) {
			BigDecimal maxUseMoney = curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId());
			useMoney = useMoney.add(maxUseMoney);
		}
		if (useMoney.compareTo(bTransferVo.getLowestAccount()) == -1 && useMoney.compareTo(stf.getTenderMoney()) == -1) {
			return "账户余额小于可购买金额，无法认购！";
		}

		// 认购密码
		if (bTransferVo.getBidPassword() != null && !bTransferVo.getBidPassword().equals("")) {
			if (null == stf.getBidPassword() || !bTransferVo.getBidPassword().equals(MD5.toMD5(stf.getBidPassword()))) {
				return "认购密码有误";
			}
		}

		// 交易密码
		/*MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		if (null == memberVo.getPaypassword() || !memberVo.getPaypassword().equals(MD5.toMD5(stf.getPayPassword()))) {
			return "交易密码有误";
		}*/
		/*// 验证验证码
		String validatecode = stf.getCheckCode();
		String randCode = (String) request.getSession().getAttribute("randomCode");
		if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
			return "验证码输入有误！";
		} else {
			// 验证码输入正确 立马删除验证码
			request.getSession().removeAttribute("randomCode");
		}*/
		// 非转让中债权不可认购
		if (bTransferVo.getStatus() != 2) {
			return "非转让中债权不可认购！";
		}

		return result;
	}

	/**
	 * <p>
	 * Description:取消转让<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月22日
	 * @param bTransfer
	 * @param request
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/cancelTransfer")
	@ResponseBody
	public MessageBox cancelTransfer(@ModelAttribute BTransfer bTransfer, HttpServletRequest request) {

		ShiroUser shiroUser = currentUser();
		if (bTransfer == null || bTransfer.getId() == null) {
			return MessageBox.build("0", "参数不正确");
		}

		if (StringUtils.isEmpty(bTransfer.getCancelRemark())) {
			// return MessageBox.build("0", "取消备注不能为空");
		}

		try {
			String ip = HttpTookit.getRealIpAddr(request);
			bTransfer.setCancelUser(shiroUser.getUserId());
			bTransfer.setUserId(shiroUser.getUserId());
			bTransfer.setCancelIp(ip);

			transferService.updateMyTransferForCancel(bTransfer);

			if ("0001".equals(bTransfer.getRemark())) {
				return MessageBox.build("1", "取消转让成功");
			} else {
				return MessageBox.build("0", "取消转让失败");
			}

		} catch (Exception e) {
			logger.error("取消转让失败", e);
			return MessageBox.build("0", "取消转让失败，请联系管理人员！");

		}
	}

	/**
	 * <p>
	 * Description:转让详情<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月22日
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/{id}")
	public ModelAndView totransferdetail(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("transfer/transferdetail");
		// 自动购买金额
		BigDecimal effectiveTenderMoney = new BigDecimal(0);

		BigDecimal alsoNeedMoney = new BigDecimal(0);
		AccountVo accountVo = new AccountVo();
		BTransferVo bTransferVo = new BTransferVo();
		try {
			// 查询 转让信息
			BTransfer bTransfer = transferService.getTransferDetailById(id);
			if (bTransfer == null) {
				mv = new ModelAndView("page/common/404");
				return mv;
			}
			mv.addObject("bTransfer", bTransfer);

			// 查询 原始标信息
			Integer borrowId = bTransfer.getBorrowId();
			// 获取借款标详情 copy myaccountcontroller 类 toInvest
			Map<String, ?> resultMap = borrowDetailService.queryBorrowDetailInfo(borrowId);
			if (null == resultMap || null == resultMap.get("borrowDetailVo")) {
				mv = new ModelAndView("page/common/404");
				return mv;
			}


			BorrowVo borrowVo =  borrowService.selectByPrimaryKey(borrowId);
			if(borrowVo != null && borrowVo.getIsCustody() !=null && borrowVo.getIsCustody().intValue() ==1){
				return redirect("/custody/zhaiquan/"+id+".html");
			}

			ShiroUser shiroUser = currentUser();
			// 投资人账户余额
			if (shiroUser != null) {
				accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
				BigDecimal useMoney = accountVo.getUseMoney();
				mv.addObject("useMoney", useMoney);
				mv.addObject("account", accountVo);
				// 获取个人账户余额
				// 债权转让信息
				bTransferVo = transferService.selectByPrimaryKey(id);
				// 剩余可认购金额
				alsoNeedMoney = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
				mv.addObject("alsoNeed", alsoNeedMoney);

				// 个人已认购金额
				TransferCnd transferCnd = new TransferCnd();
				transferCnd.setTransferId(id);
				transferCnd.setUserId(shiroUser.getUserId());
				String sumAccount = transferService.querySumAccountByUserId(transferCnd);
				mv.addObject("sumAccount", sumAccount);

				// 自动购买金额赋值：
				// 默认赋值=用户金额
				effectiveTenderMoney = accountVo.getUseMoney();
				// 如果自动购买金额大于最大认购额度；=最大认购额度
				if (effectiveTenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
					effectiveTenderMoney = bTransferVo.getMostAccount();
				}
				// 如果自动购买金额大于债权余额；=债权余额
				if (effectiveTenderMoney.compareTo(alsoNeedMoney) == 1) {
					effectiveTenderMoney = alsoNeedMoney;
				}
				// 如果用户余额<最小额度&&债权余额>最小额度；=债权余额
				if (accountVo.getUseMoney().compareTo(bTransferVo.getLowestAccount()) == -1 && alsoNeedMoney.compareTo(bTransferVo.getLowestAccount()) == 1) {
					effectiveTenderMoney = new BigDecimal(0);
				}
				mv.addObject("effectiveTenderMoney", effectiveTenderMoney);
				mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
			}
			mv.addObject("nowTime", new Date());
			mv.addObject("loginMember", shiroUser);
			mv.addObject("borrow", resultMap.get("borrowDetailVo"));
			// 待还本息统计数据
			mv.addObject("borrowDetailRepayVo", resultMap.get("borrowDetailRepayVo"));
			// 已还本息总额
			mv.addObject("repaymentYesAccountTotal", resultMap.get("repaymentYesAccountTotal"));
			// 垫付统计数据
			mv.addObject("borrowDetailWebPayVo", resultMap.get("borrowDetailWebPayVo"));
			// 借款者信用档案
			mv.addObject("borrowDetailCreditVo", resultMap.get("borrowDetailCreditVo"));
			// 借款者信息
			mv.addObject("borrower", resultMap.get("borrowerVo"));
			// 借款者抵押信息
			mv.addObject("mortgageVo", resultMap.get("mortgageVo"));
			// 借款者认证信息
			mv.addObject("accountUploadDocs", resultMap.get("accountUploadDocs"));
		} catch (Exception e) {
			logger.error("转让详情", e);
			mv = new ModelAndView("page/common/500");
			return mv;
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:转让详情--购买记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param transferId
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchSubscribes/{transferId}/{pageNo}")
	public ModelAndView searchSubscribes(@PathVariable("transferId") Integer transferId, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("transfer/transferdetail_subscribelist");

		if (transferId == null) {
			mv = new ModelAndView("page/common/404");
			return mv;
		}

		try {
			Page page = new Page(pageNo, 10);
			transferService.querySubscribesByTransferId(transferId, page);
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("转让详情--购买记录", e);
			mv = new ModelAndView("page/common/500");
			return mv;
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:借款详情--转让记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchBorrowSubscribes/{borrowId}/{pageNo}")
	public ModelAndView searchBorrowSubscribes(@PathVariable("borrowId") Integer borrowId, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("investment/toInvest_transferrecord");

		if (borrowId == null) {
			mv = new ModelAndView("page/common/404");
			return mv;
		}

		try {
			Page page = new Page(pageNo, 10);
			transferService.queryBorrowSubscribesByBorrowId(borrowId, page);
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("借款详情--转让记录", e);
			mv = new ModelAndView("page/common/500");
			return mv;
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:借款详情--债权信息<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param borrowId
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/transferinfo/{borrowId}/{pageNo}")
	public ModelAndView transferinfo(@RequestParam("status") Integer status, @PathVariable("borrowId") Integer borrowId, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("investment/toInvest_transferinfo");

		if (borrowId == null) {
			mv = new ModelAndView("page/common/404");
			return mv;
		}

		try {
			Page page = new Page(pageNo, 10);
			transferService.queryTransferinfosByBorrowId(borrowId, status, page);
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("借款详情--债权信息", e);
			mv = new ModelAndView("page/common/500");
			return mv;
		}
		return mv;
	}

	public BigDecimal getNowApr(BigDecimal borrowApr, BigDecimal coef, int borrowStyle, int timeLimit, int timeLimitReal, BigDecimal accountSurplus) {
		// 按日还款：转让后收益率=原标利率+（1-转让系数）*360/剩余天数
		// 按月还款：转让后收益率=原标利率+24*（1-转让系数）/(剩余月数+1)
		// 月还息到期还本：综合利率=（原标利率*剩余月数+12*（1-转让系数））/剩余月数
		String nowApr = "";
		if (null != accountSurplus) {
			// nowApr =
			// df.format(super.getAccountSurplus().subtract(super.getAccountReal()).multiply(new
			// BigDecimal(36000)).divide(super.getAccountReal(),8,
			// BigDecimal.ROUND_HALF_UP).divide(new
			// BigDecimal(timeLimit),2, BigDecimal.ROUND_HALF_UP));

			Integer styleNum = borrowStyle;
			if (styleNum == 4) { // 按天 |
									// (1-getCoef())*360/timeLimit+borrowApr
				nowApr = df.format((new BigDecimal(1).subtract(coef)).multiply(new BigDecimal(36000))
						.divide(new BigDecimal(timeLimit), 2, BigDecimal.ROUND_HALF_UP).add(borrowApr));
			}
			if (styleNum == 3) { // 按月
				Integer monthNum = (int) ((timeLimitReal - 0.5) / 30 + 1);
				nowApr = df.format((new BigDecimal(2400)).multiply(new BigDecimal(1).subtract(coef))
						.divide((new BigDecimal(monthNum).add(new BigDecimal(1))), 2, BigDecimal.ROUND_HALF_UP).add(borrowApr));
			}
			if (styleNum == 2) { // 月还息到期还本
				nowApr = df.format((new BigDecimal(1200)).multiply(new BigDecimal(1).subtract(coef)).add(borrowApr.multiply(new BigDecimal(timeLimitReal)))
						.divide(new BigDecimal(timeLimitReal), 2, BigDecimal.ROUND_HALF_UP));
			}
			if (styleNum == 1) { // 等额本息
				String str1 = borrowApr.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).toString();
				String str2 = coef.toString();
				double yearactive = Double.parseDouble(str1); // 年利率
				double monthactive = yearactive / 12; // 月利率
				int totalmonth = timeLimitReal; // 剩余期数
				double price = 1 - Double.parseDouble(str2); // 1-转让系数
				nowApr = getRealRate(monthactive, totalmonth, price);

			}

		} else {
			nowApr = "0";
		}

		return new BigDecimal(nowApr);
	}

	public double getMonthmoney(double monthactive, int totalmonth, double price) {
		double totalmoney = 1;
		double monthmoney = (totalmoney - totalmoney * price) * monthactive * (Math.pow((1 + monthactive), totalmonth))
				/ (Math.pow((1 + monthactive), totalmonth) - 1);
		return monthmoney;
	}

	public String getRealRate(double monthactive, int totalmonth, double price) {
		double protoMonthmoney = getMonthmoney(monthactive, totalmonth, 0);
		double starttmp, endtmp;
		if (price >= 0) {
			starttmp = 0;
			endtmp = 1;
		} else {
			starttmp = -1;
			endtmp = 0;
		}
		while (true) {
			double tmpmonthactive = 0;
			tmpmonthactive = monthactive + endtmp;
			double monthmoney = getMonthmoney(tmpmonthactive, totalmonth, price);
			if (Math.abs(protoMonthmoney - monthmoney) >= 0.0000001) {
				if (protoMonthmoney < monthmoney) {
					endtmp = (starttmp + endtmp) / 2;
				} else {
					double tmp = starttmp;
					starttmp = endtmp;
					endtmp = endtmp + (endtmp - tmp) / 2;
				}
				continue;
			} else {
				return String.format("%.4f", tmpmonthactive * 1200);
			}
		}

	}

	/**
	 * <p>
	 * Description:官网改版--债权转让<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2016年1月11日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/transferList1")
	public ModelAndView toTransferList1(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("transfer/transferList1");

		return mv;
	}

}
