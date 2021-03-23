package com.dxjr.portal.fix.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.FixAccount;
import com.dxjr.base.entity.FixAccountLog;
import com.dxjr.base.entity.FixBorrow;
import com.dxjr.base.entity.FixBorrowTransfer;
import com.dxjr.base.entity.FixCollectionrecord;
import com.dxjr.base.entity.FixOperationLog;
import com.dxjr.base.entity.FixRepaymentrecord;
import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.base.entity.FixTenderReal;
import com.dxjr.base.entity.FixTenderTransfer;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.util.PercentageUtil;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.Percentage;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.fix.mapper.BCollectionrecordMapper;
import com.dxjr.portal.fix.mapper.FixAccountLogMapper;
import com.dxjr.portal.fix.mapper.FixAccountMapper;
import com.dxjr.portal.fix.mapper.FixBorrowMapper;
import com.dxjr.portal.fix.mapper.FixBorrowTransferMapper;
import com.dxjr.portal.fix.mapper.FixCollectionrecordMapper;
import com.dxjr.portal.fix.mapper.FixOperationLogMapper;
import com.dxjr.portal.fix.mapper.FixRepaymentrecordMapper;
import com.dxjr.portal.fix.mapper.FixTenderDetailMapper;
import com.dxjr.portal.fix.mapper.FixTenderRealMapper;
import com.dxjr.portal.fix.mapper.FixTenderTransferMapper;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.DoubleElevenVo;
import com.dxjr.portal.fix.vo.FixActivityCnd;
import com.dxjr.portal.fix.vo.FixActivityVo;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.fix.vo.FixBorrowOpenCnd;
import com.dxjr.portal.fix.vo.FixBorrowStaticVo;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.fix.vo.FixTenderDetailVo;
import com.dxjr.portal.floatCoupon.service.FloatCouponService;
import com.dxjr.portal.freemarker.service.BaseFreemarkerService;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.entity.RedRule;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.EnumerationUtil;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:定期宝信息接口实现类<br />
 * </p>
 * 
 * @title FixBorrowServiceImpl.java
 * @package com.dxjr.portal.fix1.service.impl
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
@Service
public class FixBorrowServiceImpl implements FixBorrowService {
	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private RedAccountLogMapper redLogMapper;

	@Autowired
	private RedAccountMapper redMapper;

	@Autowired
	private FixBorrowMapper fixBorrowMapper;

	@Autowired
	private FixTenderDetailMapper fixTenderDetailMapper;

	@Autowired
	private FixOperationLogMapper fixOperationLogMapper;

	@Autowired
	private FixTenderRealMapper fixTenderRealMapper;

	@Autowired
	private FixCollectionrecordMapper fixCollectionrecordMapper;

	@Autowired
	private FixRepaymentrecordMapper fixRepaymentrecordMapper;

	@Autowired
	private FixAccountMapper fixAccountMapper;

	@Autowired
	private FixAccountLogMapper fixAccountLogMapper;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BaseFreemarkerService baseFreemarkerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountLogService accountLogService;

	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;

	@Autowired
	private CurAccountService curAccountService;

	@Autowired
	private CurOutService curOutService;

	@Autowired
	private BCollectionrecordMapper bCollectionrecordMapper;

	@Autowired
	private FixBorrowTransferMapper fixBorrowTransferMapper;

	@Autowired
	private TenderRecordMapper tenderRecordMapper;

	@Autowired
	private FixTenderTransferMapper fixTenderTransferMapper;
	
	@Autowired
	private FloatCouponService floatCouponService;

	@Override
	public BigDecimal queryTotalAccountYes() throws Exception {
		return fixBorrowMapper.queryTotalAccountYes();
	}

	@Override
	public String queryTotalAccountInUseRate() throws Exception {

		BigDecimal totalAccountInUse = fixBorrowMapper.queryTotalAccountInUse();
		BigDecimal totalAccountIsLocked = fixBorrowMapper.queryTotalAccountIsLocked();
		String rate = null;
		// 如果投标中的总金额为空或者锁定中的总金额为空或者锁定中的总金额为0
		if (null == totalAccountInUse || null == totalAccountIsLocked || totalAccountIsLocked.doubleValue() == 0) {
			return "0.00%";
		} else {
			DecimalFormat df = new DecimalFormat("0.00%");
			rate = df.format(new BigDecimal(totalAccountInUse.doubleValue() / totalAccountIsLocked.doubleValue()));
		}
		return rate;
	}

	@Override
	public FixBorrowVo getFixBorrowById(Integer id) throws Exception {
		return fixBorrowMapper.getFixBorrowById(id);
	}

	@Override
	public Page queryFixBorrowVoList(FixBorrowCnd fixBorrowCnd, Page page) throws Exception {
		Integer totalCount = fixBorrowMapper.queryFixBorrowCount(fixBorrowCnd);
		page.setTotalCount(totalCount);
		List<FixBorrowVo> list = fixBorrowMapper.queryFixBorrowVoList(fixBorrowCnd, page);
		// 如果查询到的list不为空或者list的size大于0，执行循环操作
		if (null != list && list.size() > 0) {
			for (FixBorrowVo fixBorrowVo : list) {
				// 判断发布时间不为空且当前时间是否早于发布时间，如果早于设置加入状态为0（敬请期待）,否则加入状态为1(加入中)
				if (null != fixBorrowVo.getPublishTime() && new Date().before(fixBorrowVo.getPublishTime())) {
					fixBorrowVo.setJoinStatus(0);
				} else {
					fixBorrowVo.setJoinStatus(1);
				}
			}
		}
		page.setResult(list);
		return page;
	}

	@Override
	public String saveTenderFixtBorrow(Integer userId, FixBorrowOpenCnd fixBorrowOpenCnd, Integer platForm, String ip) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String result = "success";
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(userId);
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 判断是否为借款用户
		if (memberVo.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
			return "借款用户不允许开通！";
		}
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(userId);
		if ((null == memberApproVo.getEmailChecked() || memberApproVo.getEmailChecked() != Constants.YES)
				&& (null == memberApproVo.getMobilePassed() || memberApproVo.getMobilePassed() != Constants.YES)) {
			return "您还没有进行邮箱或手机认证，请先进行邮箱或手机认证";
		}

		// 1.查询定期宝信息
		FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowByIdForUpdate(fixBorrowOpenCnd.getFixBorrowId());

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowOpenCnd.getIsUseCurMoney() != null && fixBorrowOpenCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			fixBorrowVo.setIsUseCurMoney(fixBorrowOpenCnd.getIsUseCurMoney());
		}

		// 2.获取帐号信息并锁定帐号
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(userId);
		BigDecimal useMoney = accountVo.getUseMoney();

		// 计算开通的最大有效金额
		BigDecimal maxEffectiveMoney = isEffectiveMoneyForTenderReal(fixBorrowVo, userId, fixBorrowOpenCnd.getTenderMoney(), accountVo);
		fixBorrowVo.setMaxEffectiveMoney(maxEffectiveMoney);

		// 获取认购金额
		BigDecimal effectiveMoney = fixBorrowOpenCnd.getTenderMoney();
		// 验证是否可以使用红包
		Integer redId = fixBorrowOpenCnd.getRedId();
		RedAccount red = null;
		if (redId != null && redId > 0) {
			red = redMapper.getById(redId);
			if (red != null) {
				if (red.getStatus() != 2 || !red.getUserId().equals(userId)) {
					return "该红包不可使用";
				}
				if (effectiveMoney.compareTo(new BigDecimal(red.getMoney().floatValue() * 100)) < 0) {
					return "投资金额不满足红包使用条件";
				}
				RedAccountCnd redAccountCnd = new RedAccountCnd();
				redAccountCnd.setRedType(red.getRedType().toString());
				redAccountCnd.setRedMoney(String.valueOf(red.getMoney().intValue()));
				redAccountCnd.setRedMonth(fixBorrowVo.getLockLimit().toString());
				RedRule redRule = redMapper.queryRedRule(redAccountCnd);
				if (null != redRule) {
					if (redRule.getUseType() != 1) {
						return "红包不能投定期宝";
					}
				} else {
					return "该红包不符合使用规则";
				}
			} else {
				return "该红包不存在";
			}
		}

		// 3.验证定期宝数据是否正确
		result = validateTenderFix(memberVo, fixBorrowOpenCnd, request, fixBorrowVo, accountVo, effectiveMoney, red);
		if (!"success".equals(result)) {
			return result;
		}
		// 使用红包
		int redLogId = 0;
		if (red != null) {
			red = redMapper.getByIdForUpdate(redId);
			int n = redMapper.freezeRedpPlatform(redId, fixBorrowVo.getId(), 1, platForm);
			if (n == 1) {
				// 红包日志
				RedAccountLog redLog = new RedAccountLog();
				redLog.setRedId(redId);
				redLog.setAddip(ip);
				redLog.setBizType(1);// 1定期宝
				redLog.setRemark("用户使用红包投定期宝【宝ID：" + fixBorrowVo.getId() + "】，红包冻结");
				redLogMapper.addByRed(redLog);
				redLogId = redLog.getId();

				// 更新账户表
				accountVo = accountService.queryAccountByUserIdForUpdate(userId);
				BigDecimal redMoney = red.getMoney();
				accountMapper.updateAccountForRed(redMoney, userId);
				// 账户日志
				accountVo = accountService.queryAccountByUserId(userId);
				AccountLog accountLog = new AccountLog();
				accountLog.setUserId(userId);
				accountLog.setAddip(ip);
				accountLog.setAddtime(new Date().getTime() / 1000 + "");
				accountLog.setMoney(redMoney);
				accountLog.setTotal(accountVo.getTotal());
				accountLog.setDrawMoney(accountVo.getDrawMoney());
				accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
				accountLog.setUseMoney(accountVo.getUseMoney());
				accountLog.setNoUseMoney(accountVo.getNoUseMoney());
				accountLog.setCollection(accountVo.getCollection());
				accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
				accountLog.setToUser(userId);
				accountLog.setType("red_envelop_in");// 字典表定义（红包转入）
				accountLog.setIdType(7);// 7：红包转入
				accountLog.setBorrowId(redLogId);// 红包操作日志ID
				accountLog.setRemark("认购定期宝使用红包，红包金额转入账户");
				baseAccountLogMapper.insertEntity(accountLog);

				useMoney = accountVo.getUseMoney();
			}
		}

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowOpenCnd.getIsUseCurMoney() != null && fixBorrowOpenCnd.getIsUseCurMoney().equals("1")) {
			// 当开通金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (fixBorrowOpenCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = fixBorrowOpenCnd.getTenderMoney().subtract(useMoney);
				// 活期宝金额转出用于购买定期宝
				result = curOutService.turnCurOutForInvest(userId, remainTenderMoney, "0.0.0.1", BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO);
				if (!BusinessConstants.SUCCESS.equals(result)) {
				 throw new AppException("活期宝转入个人账户失败");
				 //return result;
				}
			}
		}

		// 重新获取帐号信息并锁定帐号
		accountVo = accountService.queryAccountByUserIdForUpdate(userId);

		// 4.保存认购明细
		FixTenderDetail fixTenderDetail = this.packageFixTenderDetail(userId, request, accountVo, effectiveMoney, fixBorrowVo, platForm);

		// 更新红包日志bizId
		if (redLogId != 0) {
			redLogMapper.updateBizId(fixTenderDetail.getId(), redLogId);// 定期宝认购记录的ID
		}

		// 5.更新用户账户表rocky_account的total(总金额)、use_money(可用金额)、draw_money(可提现金额)、no_draw_money(受限金额)
		this.packegeUpdateAccount(fixTenderDetail);
		// 记录资金明细
		this.pacakgeInsertAccountLog(accountVo, request, fixTenderDetail, fixBorrowVo);

		// 6.更新定期宝信息表实际认购总额和认购次数
		this.packageUpdateFixBorrow(fixTenderDetail);

		/*----判断是否满标,如果满标执行满标操作----*/

		// 7.从认购明细表获取定期宝认购总额
		BigDecimal detailSumAccount = fixTenderDetailMapper.getSumAccountByFixBorrowId(fixBorrowVo.getId());

		// 8.判断条件1：实际认购金额不为;2:实际认购金额+本次认购金额=计划金额3:认购记录明细总额=计划金额
		if (null != fixBorrowVo.getAccountYes()
				&& fixBorrowVo.getAccountYes().add(fixBorrowOpenCnd.getTenderMoney()).compareTo(fixBorrowVo.getPlanAccount()) == 0
				&& fixBorrowVo.getPlanAccount().compareTo(detailSumAccount) == 0) {
			// 9.更新定期宝信息表，修改状态为满标复审中
			FixBorrowVo fiBorrowVoForUpdate = new FixBorrowVo();
			fiBorrowVoForUpdate.setId(fixBorrowVo.getId());
			fiBorrowVoForUpdate.setStatus(Constants.FIX_BORROW_STATUS_TO_SUCCESS_APPROVE);// 置状态为满标复审中
			fixBorrowMapper.updateFixBorrowStatus(fiBorrowVoForUpdate);

			// 10.记录满宝表操作日志(宝状态变为满宝复审中)
			FixOperationLog fixOperationLog = new FixOperationLog();
			fixOperationLog = new FixOperationLog();
			fixOperationLog.setUserId(-1);// 系统用户
			fixOperationLog.setUserType(2);// 硬编码用户类型(2:后台)
			fixOperationLog.setFixBorrowId(fixBorrowVo.getId());
			fixOperationLog.setOperType(4);// 硬编码操作类型(满宝复审中)
			fixOperationLog.setRemark("满宝复审中");
			fixOperationLog.setAddip("0.0.0.1");// 系统地址
			fixOperationLog.setPlatform(platForm);// 硬编码平台来源(1:网页)
			fixOperationLogMapper.insertFixOperationLog(fixOperationLog);
			result = "isFull";
		}

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowOpenCnd.getIsUseCurMoney() != null && fixBorrowOpenCnd.getIsUseCurMoney().equals("1")) {
			// 当开通金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (fixBorrowOpenCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = fixBorrowOpenCnd.getTenderMoney().subtract(useMoney);
				// 获取转出记录
				CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO, userId, remainTenderMoney);
				if (fixTenderDetail == null || fixTenderDetail.getId() == null || curOut == null) {
					throw new AppException("活期宝加入定期宝失败");
				} else {
					curOut.setTargetId(fixTenderDetail.getId());
					Integer updateCurOutCount = curOutService.updateByPrimaryKeySelective(curOut);
					if (updateCurOutCount == null || updateCurOutCount == 0) {
						throw new AppException("活期宝加入定期宝失败");
					}
				}
			}
		}

		// 投资3月以上定期宝发放抽奖机会
		if (fixBorrowVo.getLockLimit() >= 3) {
			int lotteryNum = 1;
			int lockLimit = fixBorrowVo.getLockLimit().intValue();
			if (lockLimit == 6)
				lotteryNum = 2;
			if (lockLimit == 12)
				lotteryNum = 3;

			Map map = new HashMap();
			map.put("userId", userId);
			map.put("lock_limit", lockLimit);

			Integer userAccount = fixTenderDetailMapper.getUserAccount(map);
			// 等于0说明为首笔投资
			if (userAccount != null && userAccount == 1) {
				lotteryChanceInfoService.insertLotteryChanceInfoByCode(userId, BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_FIX_AWARD, lotteryNum);
				return result += "[您的投资是对我们最大的鼓励！<br/>恭喜您成功首次投资" + fixBorrowVo.getLockLimit() + "月宝，国小诚为您奉上抽奖机会" + lotteryNum + "次<br/>快去顶玺官网“抽大奖”试试手气吧！]";
			}
		}

		return result;
	}

	/**
	 * <p>
	 * Description:验证定期宝数据是否正确<br />
	 * </p>
	 * 
	 * @author caodefeng
	 * @version 0.1 2015年5月14日
	 * @param memberVo
	 * @param fixBorrowOpenCnd
	 * @param request
	 * @param fixtBorrow
	 * @param account
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String validateTenderFix(MemberVo memberVo, FixBorrowOpenCnd fixBorrowOpenCnd, HttpServletRequest request, FixBorrowVo fixBorrow,
			AccountVo account, BigDecimal effectiveMoney, RedAccount red) throws Exception {
		String result = "success";
		// 验证定期宝页面传参是否正确
		result = validateTenderFixParamData(memberVo, fixBorrowOpenCnd, request);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证定期宝业务数据是否正确
		result = validateTenderFixLogic(fixBorrowOpenCnd, fixBorrow);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证定期宝认购金额是否正确
		result = validateTenderFixMoney(memberVo, fixBorrowOpenCnd, fixBorrow, account, effectiveMoney, red);
		if (!"success".equals(result)) {
			return result;
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证定期宝业务数据是否正确 <br />
	 * </p>
	 * 
	 * @author caodefeng
	 * @version 0.1 2015年5月14日
	 * @param fixBorrowOpenCnd
	 * @param msg
	 * @param fixBorrow
	 *            void
	 */
	private String validateTenderFixLogic(FixBorrowOpenCnd fixBorrowOpenCnd, FixBorrowVo fixBorrow) {
		String result = "success";
		StringBuffer msg = new StringBuffer();
		// 定期宝对象为空
		if (null == fixBorrow) {
			msg.append("-定期宝数据未找到！\n");
		}

		// 如果状态不是审核通过投标中且不是满宝审核通过时
		if (fixBorrow.getStatus() != Constants.FIX_BORROW_STATUS_APPROVE_PASS && fixBorrow.getStatus() != Constants.FIX_BORROW_STATUS_SUCCESS_APPROVE_PASS) {
			msg.append("-该定期宝计划不允许继续开通！\n");
		} else {
			// 当已投金额大于等与计划金额时
			if (null != fixBorrow.getAccountYes()) {
				if (fixBorrow.getStatus() == Constants.FIX_BORROW_STATUS_SUCCESS_APPROVE_PASS
						|| fixBorrow.getPlanAccount().compareTo(fixBorrow.getAccountYes()) <= 0) {
					msg.append("-定期宝已满，无法继续开通！\n");
				}
			}

		}
		// 验证当前时间是否超过了发布时间
		int havePassTime = new Date().compareTo(fixBorrow.getPublishTime());
		if (havePassTime < 0) {
			msg.append("-未到发布时间,请稍候！\n");
		}
		// 验证当前时间是否已超过认购截止时间
		havePassTime = new Date().compareTo(fixBorrow.getEndTime());
		if (havePassTime > 0) {
			msg.append("-已过开通截止时间,无法继续开通！\n");
		}
		if (msg.length() > 0) {
			return msg.toString();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证定期宝页面传参是否正确<br />
	 * </p>
	 * 
	 * @author caodefeng
	 * @version 0.1 2015年5月14日
	 * @param memberVo
	 * @param fixBorrowOpenCnd
	 * @param request
	 * @param msg
	 *            void
	 */
	private String validateTenderFixParamData(MemberVo memberVo, FixBorrowOpenCnd fixBorrowOpenCnd, HttpServletRequest request) {
		String result = "success";
		StringBuffer msg = new StringBuffer();
		/*String randCode = (String) request.getSession().getAttribute("randomCode");
		if (null == fixBorrowOpenCnd.getCheckCode() || null == randCode || !fixBorrowOpenCnd.getCheckCode().equals(randCode)) {
			msg.append("-验证码输入有误！\n");
		}
		request.getSession().removeAttribute("randomCode");*/
		// 验证传进来的数据是否正确
	/*	String payPassword = MD5.toMD5(fixBorrowOpenCnd.getPayPassword());
		if (null == memberVo.getPaypassword() || !memberVo.getPaypassword().equals(payPassword)) {
			msg.append("-交易密码输入有误！\n");
		}*/
		if (msg.length() > 0) {
			return msg.toString();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证定期宝投标金额是否正确<br />
	 * </p>
	 * 
	 * @author caodefeng
	 * @version 0.1 2015年5月14日
	 * @param memberVo
	 * @param fixBorrowOpenCnd
	 * @param fixBorrow
	 * @param account
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String validateTenderFixMoney(MemberVo memberVo, FixBorrowOpenCnd fixBorrowOpenCnd, FixBorrowVo fixBorrow, AccountVo account,
			BigDecimal effectiveMoney, RedAccount red) throws Exception {
		String result = "success";
		StringBuffer moneyMsg = new StringBuffer();
		BigDecimal useMoney = account.getUseMoney();
		if (effectiveMoney.compareTo(BigDecimal.ZERO) == 0 || effectiveMoney.compareTo(new BigDecimal(100)) == -1 || effectiveMoney.scale() > 0
				|| ((effectiveMoney.intValue()) % 100 != 0)) {
			return "开通金额错误";
		}
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowOpenCnd.getIsUseCurMoney() != null && fixBorrowOpenCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(account.getUserId());
				useMoney = useMoney.add(maxCurUseMoney);
				if (null != red && red.getMoney().compareTo(BigDecimal.ZERO) > 0) {
					if (fixBorrow.getMaxEffectiveMoney().subtract(red.getMoney()) != null
							&& useMoney.compareTo(fixBorrow.getMaxEffectiveMoney().subtract(red.getMoney())) == -1) {
						moneyMsg.append("-账户余额不足，无法开通！\n");
					}
				} else {
					if (fixBorrow.getMaxEffectiveMoney() != null && useMoney.compareTo(fixBorrow.getMaxEffectiveMoney()) == -1) {
						moneyMsg.append("-账户余额不足，无法开通！\n");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (null != red && red.getMoney().compareTo(BigDecimal.ZERO) > 0) {
				if (useMoney.compareTo(effectiveMoney.subtract(red.getMoney())) == -1) {
					moneyMsg.append("-账户余额不足，无法开通！\n");
				}
			} else {
				if (useMoney.compareTo(effectiveMoney) == -1) {
					moneyMsg.append("-账户余额不足，无法开通！\n");
				}
			}
		}
		if (fixBorrowOpenCnd.getTenderMoney().compareTo(fixBorrow.getPlanAccount().subtract(fixBorrow.getAccountYes())) > 0) {
			moneyMsg.append("-开通金额不得大于可投剩余金额！\n");
		}
		FixBorrowCnd fixBorrowCnd = new FixBorrowCnd();
		fixBorrowCnd.setFixBorrowId(fixBorrowOpenCnd.getFixBorrowId());
		fixBorrowCnd.setUserId(memberVo.getId());
		BigDecimal userRemainMoney = fixBorrowMapper.queryRemainAccountByUserId(fixBorrowCnd);
		if (fixBorrowOpenCnd.getTenderMoney().compareTo(userRemainMoney) > 0) {
			moneyMsg.append("开通金额超过最大剩余可投开通额度" + userRemainMoney + "元！\n");
		}
		if (moneyMsg.length() > 0) {
			return moneyMsg.toString();
		}
		return result;
	}

	/**
	 * 保存定期宝加入明细
	 * 
	 * @param userId
	 * @param request
	 * @param accountVo
	 * @param effectiveMoney
	 * @param fixBorrow
	 * @return
	 * @throws Exception
	 */
	private FixTenderDetail packageFixTenderDetail(Integer userId, HttpServletRequest request, AccountVo accountVo, BigDecimal effectiveMoney,
			FixBorrow fixBorrow, Integer platForm) throws Exception {
		FixTenderDetail fixTenderDetail = new FixTenderDetail();
		fixTenderDetail.setUserId(userId);
		fixTenderDetail.setFixBorrowId(fixBorrow.getId());
		fixTenderDetail.setAccount(effectiveMoney);

		// 规则：用户认购金额=可提金额+受限金额
		// 如果账户受限金额大于等于认购金额,无需从账户可提金额中扣除
		if (accountVo.getNoDrawMoney().compareTo(effectiveMoney) >= 0) {
			fixTenderDetail.setDrawMoney(BigDecimal.ZERO);// 用户认购金额中包含的可提金额为0
			fixTenderDetail.setNoDrawMoney(effectiveMoney);// 用户认购金额中包含的受限金额等于认购金额
		} else {
			fixTenderDetail.setNoDrawMoney(accountVo.getNoDrawMoney());// 用户认购金额中包含的受限金额等于用户账户中的受限金额
			fixTenderDetail.setDrawMoney(effectiveMoney.subtract(accountVo.getNoDrawMoney()));// 用户认购金额中包含的可提金额等于用户认购金额减去受限金额
		}
		fixTenderDetail.setAddIp(HttpTookit.getRealIpAddr(request));
		fixTenderDetail.setStatus(Constants.TENDER_DETAIL_STATUS_DOING);
		// 平台来源
		fixTenderDetail.setPlatForm(platForm);
		fixTenderDetailMapper.insertFixTenderDetail(fixTenderDetail);
		return fixTenderDetail;
	}

	@Override
	public void packegeUpdateAccount(FixTenderDetail fixTenderDetail) throws Exception {
		fixBorrowMapper.updateAccount(fixTenderDetail);
	}

	@Override
	public void packageUpdateFixBorrow(FixTenderDetail fixTenderDetail) throws Exception {
		fixBorrowMapper.updateFixBorrow(fixTenderDetail);
	}

	@Override
	public List<FixBorrowStaticVo> queryFixAccountInfoMap(FixBorrowCnd fixBorrowCnd) {
		List<FixBorrowStaticVo> retMap = null;
		try {
			retMap = fixBorrowMapper.queryFixAccountInfoMap(fixBorrowCnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	@Override
	public List<FixBorrowVo> queryFixBorrowData(FixBorrowCnd fixBorrowCnd) {
		List<FixBorrowVo> list = new ArrayList<FixBorrowVo>();
		try {
			list = fixBorrowMapper.getFixBorrowByCnd(fixBorrowCnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 记录资金明细
	 * 
	 * @param accountVo
	 * @param request
	 * @param fixTenderDetail
	 * @throws Exception
	 */
	private void pacakgeInsertAccountLog(AccountVo accountVo, HttpServletRequest request, FixTenderDetail fixTenderDetail, FixBorrowVo fixBorrowVo)
			throws Exception {
		AccountLog accountLog = new AccountLog();
		accountLog.setTotal(accountVo.getTotal().subtract(fixTenderDetail.getAccount()));
		accountLog.setCollection(accountVo.getCollection());
		accountLog.setDrawMoney(accountVo.getDrawMoney().subtract(fixTenderDetail.getDrawMoney()));
		accountLog.setNoDrawMoney(accountVo.getNoDrawMoney().subtract(fixTenderDetail.getNoDrawMoney()));
		accountLog.setUserId(fixTenderDetail.getUserId());
		accountLog.setToUser(-1);
		accountLog.setMoney(fixTenderDetail.getAccount());
		accountLog.setUseMoney(accountVo.getUseMoney().subtract(fixTenderDetail.getAccount()));
		accountLog.setNoUseMoney(accountVo.getNoUseMoney());
		accountLog.setType("fix_join");
		accountLog.setIdType(6);// 定期宝id
		accountLog.setBorrowId(fixTenderDetail.getFixBorrowId());
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setAddip(fixTenderDetail.getAddIp());
		accountLog.setRemark("认购定期宝(" + fixBorrowVo.getLockLimit() + "月宝)");
		accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountLogService.insertAccountLog(accountLog);
	}

	@Override
	public BigDecimal querySumAccountYes() throws Exception {
		return fixBorrowMapper.querySumAccountYes();
	}

	/**
	 * 复审操作
	 * 
	 * @param fixBorrowVo
	 * @param fixBorrowOpenCnd
	 */
	public String updateForReCheck(FixBorrowOpenCnd fixBorrowOpenCnd, HttpServletRequest request, Integer platForm) throws Exception {
		String result = "success";
		FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowByIdForUpdate(fixBorrowOpenCnd.getFixBorrowId());

		// 7.从认购明细表获取定期宝认购总额
		BigDecimal detailSumAccount = fixTenderDetailMapper.getSumAccountByFixBorrowId(fixBorrowVo.getId());

		// 8.判断条件1：实际认购金额不为null;2:实际认购金额=计划金额3:认购记录明细总额=计划金额
		if (null != fixBorrowVo.getAccountYes() && fixBorrowVo.getAccountYes().compareTo(fixBorrowVo.getPlanAccount()) == 0
				&& fixBorrowVo.getPlanAccount().compareTo(detailSumAccount) == 0) {

			// 11.更新定期宝信息表，修改状态我为满标复审通过
			FixBorrowVo fiBorrowVoForUpdate = new FixBorrowVo();
			fiBorrowVoForUpdate.setId(fixBorrowVo.getId());
			fiBorrowVoForUpdate.setStatus(Constants.FIX_BORROW_STATUS_SUCCESS_APPROVE_PASS);// 置状态为满标复审通过
			fiBorrowVoForUpdate.setSuccessTime(new Date());// 该字段只作为满宝标志参数，请勿删除(sql实际采用now())
			fixBorrowMapper.updateFixBorrowStatus(fiBorrowVoForUpdate);
			// 取出值主要为了取出lockEndDate
			FixBorrowVo fixBorrowVo1 = fixBorrowMapper.getFixBorrowByIdForUpdate(fixBorrowOpenCnd.getFixBorrowId());

			// 12.记录满宝表操作日志(宝状态变为满宝通过)
			FixOperationLog fixOperationLog = new FixOperationLog();
			fixOperationLog.setUserId(-1);// 系统用户
			fixOperationLog.setUserType(2);// 硬编码用户类型(2:后台用户)
			fixOperationLog.setFixBorrowId(fixBorrowVo.getId());
			fixOperationLog.setOperType(5);// 硬编码操作类型(满宝复审通过)
			fixOperationLog.setRemark("满宝复审通过");
			fixOperationLog.setAddip("0.0.0.1");// 系统地址
			fixOperationLog.setPlatform(platForm);// 硬编平台来源(1:网页)
			fixOperationLogMapper.insertFixOperationLog(fixOperationLog);

			// 13.最终认购记录合并生成，待收记录生成，更新认购明细，累加本金和利息

			List<FixTenderDetailVo> list = fixTenderDetailMapper.querySumAccountGroupByUser(fixBorrowVo.getId());
			// 获取最终认购记录表中的最大排序号
			Integer ordernum = fixTenderRealMapper.queryMaxOrderNum();
			BigDecimal sumAccount = new BigDecimal(0);
			BigDecimal sumInterest = new BigDecimal(0);
			// 判断列表对象不为null且不为空
			if (null != list && list.size() > 0) {
				for (FixTenderDetailVo fixTenderDetailVo : list) {
					// 合并最终认购记录
					FixTenderReal fixTenderReal = new FixTenderReal();
					fixTenderReal.setFixBorrowId(fixBorrowVo.getId());
					fixTenderReal.setUserId(fixTenderDetailVo.getUserId());
					fixTenderReal.setAccount(fixTenderDetailVo.getSumAccount());
					fixTenderReal.setUseMoney(fixTenderDetailVo.getSumAccount());
					fixTenderReal.setStatus(0);// 硬编码(0:锁定中)
					fixTenderReal.setOrderNum(++ordernum);// 排序号累加
					fixTenderReal.setFixTenderType(1);// 硬编码(1:开通定期宝)
					fixTenderRealMapper.insertFixTenderReal(fixTenderReal);

					// 待收表记录生成
					FixCollectionrecord fixCollectionrecord = new FixCollectionrecord();
					fixCollectionrecord.setFixBorrowId(fixBorrowVo.getId());
					fixCollectionrecord.setUserId(fixTenderDetailVo.getUserId());
					fixCollectionrecord.setFixTenderRealId(fixTenderReal.getId());
					fixCollectionrecord.setStatus(0);// 硬编码(0:还款中)
					fixCollectionrecord.setCapital(fixTenderDetailVo.getSumAccount());
					// 计算每个用户利息
					BigDecimal interest = fixTenderDetailVo.getSumAccount().multiply(fixBorrowVo.getApr())
							.divide(new BigDecimal(1200), 8, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(fixBorrowVo.getLockLimit()))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					fixCollectionrecord.setInterest(interest);
					fixCollectionrecord.setRepayTime(fixBorrowVo1.getLockEndtime());
					fixCollectionrecord.setAddip(HttpTookit.getRealIpAddr(request));
					fixCollectionrecord.setRepayAccount(fixTenderDetailVo.getSumAccount().add(interest));// 待金额等于待收本金+待收利息
					fixCollectionrecordMapper.insertFixCollectionrecord(fixCollectionrecord);

					// 更新每个用户的明细记录
					fixTenderDetailVo.setFixTenderRealId(fixTenderReal.getId());
					fixTenderDetailVo.setStatus(1);// 硬编码(1:投宝成功)
					fixTenderDetailVo.setFixBorrowId(fixBorrowVo.getId());
					fixTenderDetailMapper.updateFixTenderDetail(fixTenderDetailVo);

					// 累加认购本金和利息
					sumAccount = sumAccount.add(fixTenderDetailVo.getSumAccount());
					sumInterest = sumInterest.add(interest);
				}
			}

			// 14.判读利息误差值是否在允许范围内

			// 按计划金额计算利息
			BigDecimal fixBorrowInterest = fixBorrowVo.getPlanAccount().multiply(fixBorrowVo.getApr())
					.divide(new BigDecimal(1200), 8, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(fixBorrowVo.getLockLimit()))
					.setScale(2, BigDecimal.ROUND_HALF_UP);

			// 平均一个人相差一分钱,n个人等于1分钱*n个人，计算允许的误差值
			BigDecimal discrepancy = new BigDecimal("0.01").multiply(new BigDecimal(list.size()));

			// 判断所有用户利息总和与计划金额利息差的绝对值是否小于等于允许的误差值
			if (new BigDecimal(Math.abs(sumInterest.subtract(fixBorrowInterest).doubleValue())).compareTo(discrepancy) <= 0) {
				// 15.待还记录表生成
				FixRepaymentrecord fixRepaymentrecord = new FixRepaymentrecord();
				fixRepaymentrecord.setStatus(0);// 硬编码(0:未还)
				fixRepaymentrecord.setFixBorrowId(fixBorrowVo.getId());

				fixRepaymentrecord.setRepaymentAccount(sumAccount.add(sumInterest));
				fixRepaymentrecord.setRepaymentTime(fixBorrowVo1.getLockEndtime());
				fixRepaymentrecord.setInterest(sumInterest);
				fixRepaymentrecord.setCapital(sumAccount);
				fixRepaymentrecord.setPlatform(platForm);// 平台来源(1：网页)
				fixRepaymentrecord.setAddip("0.0.0.1");
				fixRepaymentrecordMapper.insertFixRepaymentrecord(fixRepaymentrecord);
			} else {
				result = "fail";
				throw new AppException("待收记录数据异常!");
			}

			// 16.更新定期宝账户总额和可用余额
			FixAccount fixAccount = new FixAccount();
			fixAccount.setFixBorrowId(fixBorrowVo.getId());
			fixAccount.setTotal(sumAccount);
			fixAccount.setUseMoney(sumAccount);
			fixAccount.setCapital(sumAccount);
			fixAccountMapper.updateFixAccount(fixAccount);

			// 17.记录定期宝账户变动日志
			FixAccountLog fixAccountLog = new FixAccountLog();
			fixAccountLog.setFixBorrowId(fixBorrowVo.getId());
			fixAccountLog.setType(2);// 硬编码(2:满宝)
			fixAccountLog.setMoney(sumAccount);
			fixAccountLog.setTotal(sumAccount);
			fixAccountLog.setUseMoney(sumAccount);
			fixAccountLog.setNoUseMoney(new BigDecimal(0));
			fixAccountLog.setCollection(new BigDecimal(0));
			fixAccountLog.setCapital(sumAccount);
			fixAccountLog.setProfit(new BigDecimal(0));
			fixAccountLog.setAdduser(-1);// 硬编码(-1:系统用户)
			fixAccountLog.setAddip("0.0.0.1");
			fixAccountLog.setRemark("满宝");
			fixAccountLogMapper.insertFixAccountLog(fixAccountLog);

			// 18.添加定期宝满宝待发邮件记录
			fixBorrowMapper.insertMailRecord(fixBorrowVo.getId());

			// 查询该定期宝使用的红包，标记为已使用
			List<RedAccount> reds = redMapper.getBizReds(fixBorrowVo.getId(), 1, 3);
			for (RedAccount r : reds) {
				r = redMapper.getByIdForUpdate(r.getId());
				int n = redMapper.useRed(r.getId());
				if (n == 1) {
					RedAccountLog redLog = new RedAccountLog();
					redLog.setAddip(HttpTookit.getRealIpAddr(request));
					redLog.setRedId(r.getId());
					redLog.setBizType(1);// 1定期宝
					redLog.setBizId(fixBorrowVo.getId());// 定期宝ID
					redLog.setRemark("满宝更新红包状态：已使用");
					redLogMapper.addByRed(redLog);
				}
			}

			// 发放定期宝加息券
			floatCouponService.grantFixFloatCoupon(fixBorrowVo.getId());;
		}
		return result;
	}

	@Override
	public String queryBorrowProtocol(Integer fix_borrow_id, String contextPath, Integer userId, String money) throws Exception {
		// 查询借款标
		FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowById(fix_borrow_id);

		String templateName = EnumerationUtil.getProtocolPathByBorrowType("10");
		// 发标人
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(userId);
		FixBorrowCnd fixBorrowCnd = new FixBorrowCnd();
		fixBorrowCnd.setFixBorrowId(fix_borrow_id);
		fixBorrowCnd.setUserId(userId);
		MemberVo borrowMember = memberService.queryMemberByCnd(memberCnd);
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(userId);
		BigDecimal total = fixBorrowMapper.querySumAccountByUserId(fixBorrowCnd);
		// 判断金额不为空
		if (money != null && !"".equals(money)) {
			total = total.add(new BigDecimal(money));
		}
		Map<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("fixBorrowVo", fixBorrowVo);
		templateMap.put("apr", BusinessConstants.aprDf.format(fixBorrowVo.getApr()) + "%");
		templateMap.put("lockLimit", BusinessConstants.numberDf.format(fixBorrowVo.getLockLimit()));
		templateMap.put("username", borrowMember.getUsername());
		if (StringUtils.isNotEmpty(memberApproVo.getIdcardno())) {
			templateMap.put("idCardNo", memberApproVo.getIdcardno());
		} else {
			templateMap.put("idCardNo", "");
		}
		templateMap.put("capital", BusinessConstants.numberDf.format(total));
		templateMap.put("contextPath", contextPath);
		templateMap.put("userId", userId);

		// 得到协议内容
		String content = baseFreemarkerService.generateEmailContentByFreeMarker(templateName, templateMap);
		return content;
	}

	@Override
	public FixBorrowStaticVo queryFixStatusCount(FixBorrowCnd fixBorrowCnd) throws Exception {
		return fixBorrowMapper.queryFixStatusCount(fixBorrowCnd);
	}

	@Override
	public BigDecimal queryRemainAccountByUserId(FixBorrowCnd fixBorrowCnd) throws Exception {

		return fixBorrowMapper.queryRemainAccountByUserId(fixBorrowCnd);
	}

	/**
	 * 查询用户的最大可投定期宝有效金额
	 * 
	 * @author WangQianJin
	 * @title @param firstBorrow
	 * @title @param userId
	 * @title @param money
	 * @title @param account
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月24日
	 */
	@Override
	public BigDecimal getMaxeffectiveMoneyForTenderReal(FixBorrowVo fixBorrowVo, Integer userId, BigDecimal money, AccountVo account) throws Exception {
		// 获取此用户在此定期宝已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		Integer accountTotal = fixTenderRealMapper.findAccountTotalByUserIdAndFixBorrowId(fixBorrowVo.getId(), userId);
		if (accountTotal != null) {
			tendered = tendered.add(new BigDecimal(accountTotal));
		}
		// 定期宝目前所剩余额
		BigDecimal syje = fixBorrowVo.getPlanAccount().subtract(fixBorrowVo.getAccountYes());
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowVo.getIsUseCurMoney() != null && fixBorrowVo.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal max = fixBorrowVo.getMostAccount();
		// 用户可再投金额
		max = max.subtract(tendered);
		BigDecimal min = fixBorrowVo.getLowestAccount();
		BigDecimal result1 = min;
		BigDecimal result2 = max;
		// 是否还有可以投定期宝
		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}
		// 如果剩余可投金额大于0，则继续
		if (!isNoMoreMoney) {
			// 定期宝剩余可投金额小于最小投宝金额
			boolean isfinal = false;
			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}
			// 最大可投的金额减去剩余金额小于0.01
			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}
			// 最大金额小于0
			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				// 最大可投金额等于0
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}
				// 可用余额大于最大可投金额
				if (useMoney.compareTo(max) >= 0) {
					// 最大可投金额大约剩余金额
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
						// 最大可投金额小于剩余金额
					} else if (max.compareTo(syje) < 0) {
						// 最大可投金额小于最小投宝金额
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
					// 可用余额小于最大可投金额
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					// 可用余额大于最小投宝金额
					if (useMoney.compareTo(min) >= 0) {
						// 可用余额大于剩余金额
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
							// 可用余额小于剩余金额
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
						// 可用余额小于最小投宝金额
					} else if (useMoney.compareTo(min) < 0) {
						// 剩余可投金额小于最小投宝金额
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
							// 剩余投宝金额小于最小投宝金额
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							// 可用余额大于剩余投宝金额
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
								// 可用余额小于剩余投宝金额
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}

			}
			// 剩余可投金额小于最小投宝金额
			if (isfinal) {
				// 最小投宝金额大于最大投宝金额
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				// 最小投宝金额大于最大投宝金额
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}
			// 四舍五入保留2位小数
			result1 = result1.setScale(2, BigDecimal.ROUND_DOWN);
			result2 = result2.setScale(2, BigDecimal.ROUND_DOWN);
		}
		if (fixBorrowVo.getAreaType() != null && fixBorrowVo.getAreaType().intValue() == 1) {
			// 新手宝最高投1W
			if (result2.doubleValue() > 10000) {
				result2 = new BigDecimal(10000);
			}
		}
		return result2;
	}

	/**
	 * 计算开通的最大有效金额
	 * 
	 * @author WangQianJin
	 * @title @param fixBorrowVo
	 * @title @param userId
	 * @title @param money
	 * @title @param account
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月26日
	 */
	@Override
	public BigDecimal isEffectiveMoneyForTenderReal(FixBorrowVo fixBorrowVo, Integer userId, BigDecimal money, AccountVo account) throws Exception {
		// 获取已投金额
		BigDecimal tendered = BigDecimal.ZERO;
		Integer accountTotal = fixTenderRealMapper.findAccountTotalByUserIdAndFixBorrowId(fixBorrowVo.getId(), userId);
		if (accountTotal != null) {
			tendered = tendered.add(new BigDecimal(accountTotal));
		}
		// 标目前所剩余额
		BigDecimal syje = fixBorrowVo.getPlanAccount().subtract(fixBorrowVo.getAccountYes());
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (fixBorrowVo.getIsUseCurMoney() != null && fixBorrowVo.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BigDecimal max = fixBorrowVo.getMostAccount();
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}
		BigDecimal min = fixBorrowVo.getLowestAccount();
		BigDecimal inputMoney = money;
		BigDecimal returnValue = BigDecimal.ZERO;
		BigDecimal result1 = min;
		BigDecimal result2 = max;

		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {
			boolean isfinal = false;
			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}
			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}
			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}
				if (useMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (useMoney.compareTo(max) < 0) {
					result2 = useMoney;
					if (useMoney.compareTo(min) >= 0) {
						if (useMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (useMoney.compareTo(syje) < 0) {
							result2 = useMoney;
						}
					} else if (useMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (useMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (useMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}
			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			if (isfinal) {
				result1 = result1.setScale(2, BigDecimal.ROUND_UP);
				result2 = result2.setScale(2, BigDecimal.ROUND_UP);
			}
			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}

		}
		return returnValue;
	}

	/**
	 * 定期宝自动发起转让
	 * 
	 * @author WangQianJin
	 * @title @param id
	 * @title @param ip
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public String saveTransfer(Integer id, String ip) throws Exception {
		// 根据系统时间查询到期的定期宝
		FixBorrowVo fixBorrowVo = fixBorrowMapper.queryMatureFixBorrowById(id);
		if (fixBorrowVo != null) {
			// 判断定期宝是否投标
			if (fixBorrowVo.getFixAccountUserMoney().compareTo(fixBorrowVo.getCapital()) == 0) {
				return "定期宝没有投标，无需转让。定期宝ID为：" + fixBorrowVo.getId();
			}
			// 获取中待收本金总和
			BigDecimal collectionCapital = bCollectionrecordMapper.queryCollectionrecordSum(fixBorrowVo.getId());
			// 待收本金总和 + 定期宝信息.可用余额 != 定期宝信息.本金 的情况
			if (collectionCapital.add(fixBorrowVo.getFixAccountUserMoney()).compareTo(fixBorrowVo.getCapital()) != 0) {
				return "定期宝金额出错，无法转让。定期宝ID为：" + fixBorrowVo.getId();
			}
			// 新增定期宝转让信息表（t_fix_borrow_transfer）
			Integer fixBorrowTransferId = addFixBorrowTransfer(fixBorrowVo, collectionCapital);

			// 修改定期宝信息，把状态修改为转让中
			FixBorrow fixBorrow = new FixBorrow();
			fixBorrow.setId(fixBorrowVo.getId());
			fixBorrow.setStatus(8);
			fixBorrow.setLastModifylUser(-1);
			fixBorrowMapper.updateFixBorrowStatusById(fixBorrow);

			// 保存定期宝操作日志
			addFixOperationLog(fixBorrow.getId(), 8, ip);

			// 根据定期宝ID查询投标记录表信息
			List<TenderRecordVo> tenderList = tenderRecordMapper.queryFixTenderRecordList(fixBorrowVo.getId());
			// 循环投标记录
			for (TenderRecordVo tenderRecordVo : tenderList) {
				// 保存定期宝投标转让表
				addFixTenderTransfer(fixBorrow.getId(), fixBorrowTransferId, tenderRecordVo, ip);
			}
		}
		return "success";
	}

	/**
	 * 添加定期宝转让信息
	 * 
	 * @author WangQianJin
	 * @title @param fixBorrowVo
	 * @title @param collectionCapital
	 * @title @return
	 * @date 2015年9月15日
	 */
	private Integer addFixBorrowTransfer(FixBorrowVo fixBorrowVo, BigDecimal collectionCapital) {
		// 新增定期宝转让信息表（t_fix_borrow_transfer）
		FixBorrowTransfer fixBorrowTransfer = new FixBorrowTransfer();
		// 定期宝ID
		fixBorrowTransfer.setFixBorrowId(fixBorrowVo.getId());
		// 债权本金
		fixBorrowTransfer.setAccount(collectionCapital);
		// 可用金额
		fixBorrowTransfer.setUseMoney(fixBorrowVo.getFixAccountUserMoney());
		// 奖励
		fixBorrowTransfer.setAwards(BigDecimal.ZERO);
		// 转让价格
		fixBorrowTransfer.setAccountReal(collectionCapital);
		// 已经借到的金额
		fixBorrowTransfer.setAccountYes(BigDecimal.ZERO);
		// 锁定期限
		fixBorrowTransfer.setFixLockLimit(fixBorrowVo.getLockLimit());
		// 锁定方式
		fixBorrowTransfer.setFixLockStyle(fixBorrowVo.getLockStyle());
		// 锁定结束日期，精确到天
		fixBorrowTransfer.setFixLockEndtime(fixBorrowVo.getLockEndtime());
		// 年利率
		fixBorrowTransfer.setFixApr(fixBorrowVo.getApr());
		// 定期宝转让标题
		fixBorrowTransfer.setFixBorrowName(fixBorrowVo.getName());
		// 合同编号
		fixBorrowTransfer.setFixContractNo(fixBorrowVo.getContractNo());
		// 添加人
		fixBorrowTransfer.setAdduser(-1);
		// 最后修改人
		fixBorrowTransfer.setLastUpdateUser(-1);
		// 状态
		fixBorrowTransfer.setStatus(1);
		// 备注
		fixBorrowTransfer.setRemark("定期宝发起转让");
		// 发起的平台来源
		fixBorrowTransfer.setPlatform(1);
		fixBorrowTransferMapper.insert(fixBorrowTransfer);
		return fixBorrowTransfer.getId();
	}

	/**
	 * 添加定期宝操作日志表
	 * 
	 * @author WangQianJin
	 * @throws Exception
	 * @title @param fixBorrowId
	 * @title @param operType
	 * @title @param ip
	 * @date 2015年9月15日
	 */
	private void addFixOperationLog(Integer fixBorrowId, Integer operType, String ip) throws Exception {
		// 保存定期宝操作日志
		FixOperationLog fixOperationLog = new FixOperationLog();
		// 操作人ID
		fixOperationLog.setUserId(-1);
		// 用户类型
		fixOperationLog.setUserType(-1);
		// 定期宝ID
		fixOperationLog.setFixBorrowId(fixBorrowId);
		// 操作类型
		fixOperationLog.setOperType(operType);
		// 备注
		if (operType == 8) {
			fixOperationLog.setRemark("定期宝自动发起转让");
		} else if (operType == 9) {
			fixOperationLog.setRemark("定期宝转让复审中");
		} else if (operType == 10) {
			fixOperationLog.setRemark("定期宝转让成功");
		} else if (operType == 11) {
			fixOperationLog.setRemark("定期宝取消转让");
		}
		// IP
		fixOperationLog.setAddip(ip);
		// 平台来源
		fixOperationLog.setPlatform(1);
		fixOperationLogMapper.insertFixOperationLog(fixOperationLog);
	}

	/**
	 * 添加定期宝投标转让表数据
	 * 
	 * @author WangQianJin
	 * @title @param fixBorrowId
	 * @title @param fixBorrowTransferId
	 * @title @param tenderRecordVo
	 * @title @param ip
	 * @date 2015年9月15日
	 */
	private void addFixTenderTransfer(Integer fixBorrowId, Integer fixBorrowTransferId, TenderRecordVo tenderRecordVo, String ip) {
		FixTenderTransfer fixTenderTransfer = new FixTenderTransfer();
		// 定期宝转让ID
		fixTenderTransfer.setFixBorrowTransferId(fixBorrowTransferId);
		// 定期宝ID
		fixTenderTransfer.setFixBorrowId(fixBorrowId);
		// 借款标Id
		fixTenderTransfer.setBorrowId(tenderRecordVo.getBorrowId());
		// 投标ID
		fixTenderTransfer.setTenderId(tenderRecordVo.getId().intValue());
		// 债权债权本金
		fixTenderTransfer.setAccount(tenderRecordVo.getTenderCapital());
		// 转让价格
		fixTenderTransfer.setAccountReal(tenderRecordVo.getTenderCapital());
		// 利息
		fixTenderTransfer.setInterest(tenderRecordVo.getTenderInterest());
		// 待收金额
		fixTenderTransfer.setRepaymentAccount(tenderRecordVo.getTenderRepayAccount());
		// 标的合同编号
		fixTenderTransfer.setContractNo(tenderRecordVo.getBorrowContractNo());
		// 添加人
		fixTenderTransfer.setAdduser(-1);
		// 添加IP
		fixTenderTransfer.setAddIp(ip);
		// 最后修改人
		fixTenderTransfer.setLastUpdateUser(-1);
		// 已经借到的金额
		fixTenderTransfer.setAccountYes(BigDecimal.ZERO);
		// 状态(1：转让中，2：转让复审中，3：转让完成，4：复审失败)
		fixTenderTransfer.setStatus(1);
		// 备注
		fixTenderTransfer.setRemark("定期宝中标发起转让");
		// 平台来源
		fixTenderTransfer.setPlatform(1);
		fixTenderTransferMapper.insert(fixTenderTransfer);
	}

	/**
	 * 根据锁定期限获取双十一活动列表
	 * 
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年11月9日
	 */
	public List<DoubleElevenVo> queryFixForDoubleElevenList(Integer lockLimit) throws Exception {
		return fixBorrowMapper.queryFixForDoubleElevenList(lockLimit);
	}

	/**
	 * 根据用户ID获取投资数量
	 * 
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月2日
	 */
	public Integer getTenderCountByUserId(Integer userId) {
		return fixBorrowMapper.getTenderCountByUserId(userId);
	}

	/**
	 * 根据用户ID获取投资新手宝的数量
	 * 
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月2日
	 */
	public Integer getNewTenderCountByUserId(Integer userId) {
		return fixBorrowMapper.getNewTenderCountByUserId(userId);
	}

	/**
	 * 获取新手宝定期宝
	 * 
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月2日
	 */
	public FixBorrowVo getNewFixBorrow() {
		return fixBorrowMapper.getNewFixBorrow();
	}

	public FixBorrowVo getLimitFixBorrow(int limit) {
		return fixBorrowMapper.getLimitFixBorrow(limit);
	}

	/**
	 * 根据多个期限获取定期宝投资排名
	 * 
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年1月27日
	 */
	public List<DoubleElevenVo> queryFixForNewYearList(FixActivityCnd cnd) throws Exception {
		return fixBorrowMapper.queryFixForNewYearList(cnd);
	}

	/**
	 * 查询2月1号以后当月注册并投资的金额大于等于1W的被邀请人信息
	 * 
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年1月27日
	 */
	public List<FixActivityVo> queryFixForInvitedList(Integer userId) throws Exception {
		return fixBorrowMapper.queryFixForInvitedList(userId);
	}

	@Override
	public Percentage querySumAccountByLimit(FixBorrowCnd fixBorrowCnd) throws Exception {
		    Percentage percentage=null;
			String checkStyle ="";
		    List<Map<String, Object>> map=fixBorrowMapper.querySumAccountByLimit(fixBorrowCnd);
			BigDecimal fixAccountOne = new BigDecimal(0);   //1月宝
			BigDecimal fixAccountThree = new BigDecimal(0);  //3月宝
			BigDecimal fixAccountSix = new BigDecimal(0);   //6月宝
			BigDecimal fixAccountTwel = new BigDecimal(0);  //12月宝
			if(null!=map&&map.size()>0){
		        for(int i = 0; i<map.size(); i++){
					Map<String ,Object> map1 = map.get(i);
					String limit = map1.get("lock_limit").toString();
					if(i==0) checkStyle = limit;
					if("1".equals(limit)){
						 fixAccountOne = (BigDecimal) map1.get("account");
					} else if("3".equals(limit)){
						 fixAccountThree = (BigDecimal) map1.get("account");
					} else if("6".equals(limit)){
						 fixAccountSix = (BigDecimal) map1.get("account");
					} else if("12".equals(limit)){
						 fixAccountTwel = (BigDecimal) map1.get("account");
					}
				}
			     if(fixAccountOne.add(fixAccountThree).add(fixAccountSix).add(fixAccountTwel).compareTo(BigDecimal.ZERO)>0){
						percentage=PercentageUtil.getPercentage(fixAccountOne.add(fixAccountThree).add(fixAccountSix).add(fixAccountTwel),fixAccountOne,fixAccountThree,fixAccountSix,fixAccountTwel);
						percentage.setFixAccountOneRatio(percentage.getFixreax());
						percentage.setFixAccountThreeRatio(percentage.getTenderTotal());
						percentage.setFixAccountSixRatio(percentage.getCurrTotal());
						percentage.setFixAccountTwelRatio(percentage.getUseTotal());
						percentage.setCheckStyle(checkStyle);
					    percentage.setFixTotal(fixAccountOne.add(fixAccountThree).add(fixAccountSix).add(fixAccountTwel));
					    percentage.setFixAccountOne(fixAccountOne);
					    percentage.setFixAccountThree(fixAccountThree);
					    percentage.setFixAccountSix(fixAccountSix);
					    percentage.setFixAccountTwel(fixAccountTwel);
			     }
			}
	     return percentage;
	}
	
}
