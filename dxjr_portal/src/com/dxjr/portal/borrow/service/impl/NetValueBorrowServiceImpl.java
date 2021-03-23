package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrow;
import com.dxjr.base.entity.Configuration;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.util.UserNetFullBorrowValue;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.SalariatBorrowMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.NetValueBorrowService;
import com.dxjr.portal.borrow.util.BorrowUtil;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;
import com.dxjr.utils.RandomGUIDUtil;

@Service
public class NetValueBorrowServiceImpl implements NetValueBorrowService {

	@Autowired
	private SalariatBorrowMapper salariatBorrowMapper;

	@Autowired
	private BorrowMapper borrowMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private AccountNetValueMapper netValueMapper;

	/*
	 * @Autowired private BaseMemberMapper memberMapper;
	 */

	/**
	 * 验证借款资格
	 * <p>
	 * Description:验证借款资格<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param userId
	 * @return
	 * @throws Exception String
	 */
	@Override
	public String checkBorrowCertification(ShiroUser loginMem) throws Exception {
		String msg = "";

		if (loginMem == null) {
			return "未登录用户不能操作。";
		}
		int userId = loginMem.getUserId();

		MemberVo memberVo = memberMapper.querySimpleInfoById(userId);
		String addtimeStr = memberVo.getAddtime();
		String canResult = BorrowUtil.canLaunchEquity(addtimeStr);

		if (!"".equals(canResult)) {
			return canResult;
		}

		if (1 != loginMem.getIsFinancialUser()) {// 1 理财用户 0 借款用户
			return "抱歉，您是借款用户，不能申请净值贷。";
		}

		int rst = memberMapper.queryEmailIspassed(userId);
		if (rst == 0) {
			return "还未进行邮箱认证！";
		}

		rst = memberMapper.queryMobileIspassed(userId);
		if (rst == 0) {
			return "还未进行手机认证！";
		}

		rst = memberMapper.queryRealNameIspassed(userId);
		if (rst == 0) {
			return "还未进行实名认证！";
		}

		BorrowCnd borrowCnd = new BorrowCnd();
		borrowCnd.setUserId(userId);
		int count = borrowMapper.getTenderingCount(borrowCnd);
		if (count > 0) {
			return "抱歉，您已经有一个借款标正在投标中，必须在满标后才能发新的借款标！";
		}

		return msg;
	}

	/**
	 * 净值贷申请初始化
	 * <p>
	 * Description:净值贷申请初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @return
	 * @throws Exception ModelAndView
	 */
	@Override
	public ModelAndView initApply(ShiroUser loginMem, String viewType) throws Exception {
		ModelAndView mv = new ModelAndView("/borrow/applyNetValueBorrow");

		String s = checkBorrowCertification(loginMem);
		if (!"".equals(s)) {
			if ("help".equals(viewType)) {
				mv = new ModelAndView("/helpcenter/aboutBorrow/index");
				mv.addObject("scrollToHeight", 0);
			} else {
				mv = new ModelAndView("/borrow/borrowProduct");
			}

			mv.addObject("msg", s);
			return mv;
		}

		Collection<Configuration> netValueOptions = Dictionary.getValues(818);// 净值标-还款方式
		mv.addObject("netValueOptions", netValueOptions);

		return mv;
	}

	/**
	 * 净值贷申请
	 * <p>
	 * Description:保存净值贷申请信息<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param borrow 借款信息
	 * @param addip
	 * @param netMoneyLimit 净值额度
	 * @return
	 * @throws Exception MessageBox
	 */
	@Override
	public MessageBox saveApplyNetValue(ShiroUser loginMem, Borrow borrow, String addip, BigDecimal netMoneyLimit) throws Exception {

		// 验证借款资格
		String s = checkBorrowCertification(loginMem);
		if (!"".equals(s)) {
			return MessageBox.build("0", s);
		}

		// 验证金额
		BigDecimal borrowNetMoneyLimit = borrow.getAccount();
		if (borrowNetMoneyLimit.compareTo(netMoneyLimit) == 1) {
			return MessageBox.build("0", "借款金额不能超过您的净值额度");
		}

		borrow.setBorrowtype(Constants.BORROW_TYPE_NETVALUE);// 3 净值标

		if (borrow.getStyle() != 4 && borrow.getStyle() != 1) {
			return MessageBox.build("0", "信息输入有误！");
		}

		// 验证字段
		/*
		 * if (BorrowUtil.checkBorrowData(borrow) == false) { return MessageBox.build("0", "信息输入有误！"); }
		 */

		if (BorrowUtil.checkBorrowDataNew(borrow) == false) {
			return MessageBox.build("0", "信息输入有误！");
		}

		// 保存净值贷基本信息
		if (borrow.getMostAccount() == null) {
			borrow.setMostAccount(BigDecimal.ZERO);
		}
		borrow.setUserId(loginMem.getUserId());
		borrow.setAddtime(new Date());
		borrow.setAddip(addip);
		borrow.setUuid(RandomGUIDUtil.newGuid());
		borrow.setStatus(Constants.BORROW_STATUS_NEW_CODE);// 1：新标
		borrow.setApprstatus(Constants.BORROW_APPSTATUS_WAIT_CODE);// 0：待审核

		borrow.setProductType(Constants.BORROW_PRODUCT_TYPE_NETVALUE);// 借贷产品类型：1-诚薪贷
																		// 2-诚商贷
																		// 3-净值贷
		if (borrow.getStyle() == Constants.BORROW_STYLE_MONTH_INSTALMENTS) {// 等额本息
			borrow.setOrder(borrow.getTimeLimit());
		} else {
			borrow.setOrder(1);// 按天
			borrow.setTimeLimit(borrow.getTimeLimitDay());
		}
		String newNo = BorrowUtil.getContractNo(borrowMapper.queryLastBorrowByBorrowType(borrow.getBorrowtype()), Constants.BORROW_TYPE_NETVALUE);// 获取合同编号
		/*
		 * Borrow b = borrowMapper.queryBorrowByContractNo(newNo); if (b != null) { newNo = BorrowUtil.getContractNo(b, 3); }
		 */
		borrow.setContractNo(newNo);
		borrow.setBorrowUse(borrow.getName());

		// 密码加密
		if (borrow.getBidPassword() != null && !"".equals(borrow.getBidPassword())) {
			borrow.setBidPassword(MD5.toMD5(borrow.getBidPassword()));
		}
		borrow.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		salariatBorrowMapper.insertEntity(borrow);
		Integer borrowId = borrow.getId();

		// 保存审批信息
		salariatBorrowMapper.insertBorrowApproved(borrowId);

		// 自动审核
		borrowService.saveApproveBorrowFirst(borrowId, Constants.APPROVE_BORROW_PASS, Constants.AUTO_CHECK_USERID, "系统初审", "A");
		borrowService.saveApproveBorrowAntiFraud(borrowId, Constants.APPROVE_BORROW_PASS, Constants.AUTO_CHECK_USERID, "系统反欺诈");
		borrowService.saveApproveBorrowFinal(borrowId, Constants.APPROVE_BORROW_PASS, Constants.AUTO_CHECK_USERID, "系统终审", addip);

		return MessageBox.build("1" + borrowId, "申请成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public MessageBox getUserNetMoneyLimitForFullBorrow(int userId, BigDecimal account, Integer timeLimit, Integer style, BigDecimal apr) throws Exception {
		// 取得用户的净值额度
		UserNetFullBorrowValue userNetFullBorrowValue = new UserNetFullBorrowValue();
		userNetFullBorrowValue.setUserid(userId);
		userNetFullBorrowValue.setBorrow_account(account);
		userNetFullBorrowValue.setBorrow_timelimit(timeLimit);
		userNetFullBorrowValue.setBorrow_style(style);
		userNetFullBorrowValue.setBorrow_apr(apr);
		netValueMapper.getUserNetMoneyLimitForFullBorrow(userNetFullBorrowValue);
		BigDecimal netMoneyLimit = BigDecimal.ZERO;
		BigDecimal netDrawMoney = BigDecimal.ZERO;
		if (userNetFullBorrowValue != null && userNetFullBorrowValue.getNetMoneyLimit() != null) {
			netMoneyLimit = userNetFullBorrowValue.getNetMoneyLimit().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		if (userNetFullBorrowValue != null && userNetFullBorrowValue.getNetDrawMoney() != null) {
			netDrawMoney = userNetFullBorrowValue.getNetDrawMoney().setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return MessageBox.build("1", "净值贷申请满标之后，预计您的净值额度是：" + netMoneyLimit + "元，可提金额是：" + netDrawMoney + "元，是否继续？");
	}
}
