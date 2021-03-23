package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.FirstBorrow;
import com.dxjr.base.entity.FirstBorrowAppr;
import com.dxjr.base.entity.FirstTenderDetail;
import com.dxjr.base.entity.FirstTenderReal;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseFirstBorrowApprMapper;
import com.dxjr.base.mapper.BaseFirstBorrowMapper;
import com.dxjr.base.mapper.BaseFirstTenderDetailMapper;
import com.dxjr.base.mapper.BaseFirstTenderRealMapper;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.first.mapper.FirstBorrowMapper;
import com.dxjr.portal.first.mapper.FirstTenderRealMapper;
import com.dxjr.portal.first.service.FirstBorrowNewService;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowOpenCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.first.vo.FirstTenderDetailVo;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:直通车业务实现类<br />
 * </p>
 * @title FirstBorrowServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author justin.xu
 * @version 0.1 2014年7月14日
 */
@Service
public class FirstBorrowNewServiceImpl implements FirstBorrowNewService {

	@Autowired
	private FirstBorrowMapper firstBorrowMapper;
	@Autowired
	private BaseFirstBorrowMapper baseFirstBorrowMapper;
	@Autowired
	private BaseFirstTenderDetailMapper baseFirstTenderDetailMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseFirstBorrowApprMapper baseFirstBorrowApprMapper;
	@Autowired
	private BaseFirstTenderRealMapper baseFirstTenderRealMapper;
	@Autowired
	private FirstTenderRealMapper firstTenderRealMapper;
	@Autowired
	private TenderRecordMapper tenderRecordMapper;
	@Autowired
	private BorrowMapper borrowMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FirstTenderDetailService firstTenderDetailService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private CurOutService curOutService;
	@Autowired
	private RedAccountMapper redAccountMapper;
	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;
	@Autowired
	private RedAccountLogMapper redLogMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private RedAccountService redAccountService;
	@Autowired
	private RedAccountLogMapper redAccountLogMapper;

	@Override
	public Page queryPageListByCnd(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception {
		firstBorrowCnd.setOrderSql(" ORDER BY f.ID DESC");
		Integer totalCount = firstBorrowMapper.queryFirstBorrowCount(firstBorrowCnd);
		page.setTotalCount(totalCount);
		List<FirstBorrowVo> list = firstBorrowMapper.queryFirstBorrowList(firstBorrowCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public FirstBorrowVo queryFirstBorrowByCnd(FirstBorrowCnd firstBorrowCnd) throws Exception {
		List<FirstBorrowVo> list = firstBorrowMapper.queryFirstBorrowList(firstBorrowCnd);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public FirstBorrowVo queryAvailableFirstBorrowById(Integer id) throws Exception {
		return firstBorrowMapper.queryAvailableFirstBorrowById(id);
	}

	@Override
	public Map<String, Object> queryFirstData() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 投标直通车-加入总人次
		map.put("firstTotalCount", this.queryFirstPersonsTotalCount());
		// 投标直通车-累计总金额
		map.put("firstTotalAccount", this.queryFirstTotalAccount());
		// 投标直通车-为用户累计赚取
		map.put("firstTotalInterst", this.queryFirstTotalInterst());
		// 投标直通车-资金利用率
		map.put("firstAccountRate", this.queryFirstAccountRate());
		// 投标直通车-为用户自动投标
		map.put("firstTenderCount", this.queryFirstTenderCount());

		// Added by Chen Lu on 2014-8-6
		// 投标直通车-总余额
		map.put("firstUseMoney", this.queryFirstUseMoney().divide(new BigDecimal(10000)));
		return map;
	}

	@Override
	public String saveTenderFirstBorrowForTenderReal(Integer userId, FirstBorrowOpenCnd firstBorrowOpenCnd) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String result = "success";
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(userId);
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		if (memberVo.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
			return "借款用户不允许开通！";
		}
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(userId);
		if ((null == memberApproVo.getEmailChecked() || memberApproVo.getEmailChecked() != Constants.YES)
				&& (null == memberApproVo.getMobilePassed() || memberApproVo.getMobilePassed() != Constants.YES)) {
			return "您还没有进行邮箱或手机认证，请先进行邮箱或手机认证";
		}

		// 获取优先投标信息
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		firstBorrowCnd.setId(firstBorrowOpenCnd.getFirstBorrowId());
		// 锁定投标记录
		firstBorrowCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		FirstBorrowVo firstBorrowVo = this.queryFirstBorrowByCnd(firstBorrowCnd);
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrowOpenCnd.getIsUseCurMoney() != null && firstBorrowOpenCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			firstBorrowVo.setIsUseCurMoney(firstBorrowOpenCnd.getIsUseCurMoney());
		}
		// 获取帐号信息并锁定帐号
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(userId);
		BigDecimal useMoney = accountVo.getUseMoney();

		// 计算开通的最大有效金额
		BigDecimal effectiveMoney = firstTenderDetailService.isEffectiveMoneyForTenderReal(firstBorrowVo, userId, firstBorrowOpenCnd.getTenderMoney(), accountVo);
		// 判断红包
		Integer redId = firstBorrowOpenCnd.getRedId();
		redId = null;//不能使用红包12.7
		RedAccount red = null;
		if (redId != null && redId > 0) {
			red = redAccountMapper.getById(redId);
			if (red != null) {
				if (red.getStatus() != 2 || !red.getUserId().equals(userId)) {
					return "该红包不可使用";
				}
				if (effectiveMoney.compareTo(new BigDecimal(red.getMoney().floatValue() * 100)) < 0) {
					return "投资金额不满足红包使用条件";
				}
				if(red.getRedType() == 1970 ){
					return "活动期间所获红包只能用于购买定期宝";
				}
			} else {
				return "该红包不存在";
			}
		}
		// 验证投标直通车数据是否正确
		result = validateTenderFirst(memberVo, firstBorrowOpenCnd, request, firstBorrowVo, accountVo, effectiveMoney);
		if (!"success".equals(result)) {
			return result;
		}

		RedAccountLog redLog = null;
		if (red != null) {

			// 红包表和红包日志表更新
			redLog = redAccountService.updateRedByZhitongche(redId, HttpTookit.getRealIpAddr(request), firstBorrowVo.getId());

			// 更新账户表
			accountVo = accountService.queryAccountByUserIdForUpdate(userId);
			BigDecimal redMoney = red.getMoney();
			accountMapper.updateAccountForRed(redMoney, userId);
			// 账户日志
			accountVo = accountService.queryAccountByUserId(userId);
			AccountLog accountLog = new AccountLog();
			accountLog.setUserId(userId);
			accountLog.setAddip(HttpTookit.getRealIpAddr(request));
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
			accountLog.setBorrowId(redLog.getId());// 红包操作日志ID
			accountLog.setRemark("红包直通车转入，红包金额转入账户");
			baseAccountLogMapper.insertEntity(accountLog);
			useMoney = accountVo.getUseMoney();

		}

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrowOpenCnd.getIsUseCurMoney() != null && firstBorrowOpenCnd.getIsUseCurMoney().equals("1")) {
			// 当开通金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (firstBorrowOpenCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = firstBorrowOpenCnd.getTenderMoney().subtract(useMoney);
				// 活期宝金额转出用于开通直通车
				result = curOutService.turnCurOutForInvest(userId, remainTenderMoney, "0.0.0.1", BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW);
				if (!BusinessConstants.SUCCESS.equals(result)) {
					return result;
				}
			}
		}

		// 重新获取帐号信息并锁定帐号
		accountVo = accountService.queryAccountByUserIdForUpdate(userId);

		// 用户投直通车时修改直通车记录，增加投标资金信息
		FirstBorrow firstBorrow = this.modifyFirstBorrowWhenTender(firstBorrowVo, effectiveMoney.intValue());
		// 保存开通记录
		FirstTenderDetail firstTenderDetail = packageFirstTenderDetail(userId, request, accountVo, effectiveMoney, firstBorrow);
		// 购买完成后，更新红包日志表，添加BIZ_ID值
		if (redLog != null) {
			redAccountLogMapper.updateBizId(firstTenderDetail.getId(), redLog.getId());
		}
		// 修改帐号信息，可用减少，直通车总可用余额增加
		packageAccount(accountVo, effectiveMoney);
		// 保存直通车资金使用记录
		packageFirstAccountLog(userId, request, accountVo, effectiveMoney, firstBorrow);
		// 保存最终开通记录
		FirstTenderReal firstTenderReal = packageFirstTenderReal(userId, request, accountVo, effectiveMoney, firstBorrow, firstTenderDetail.getAddtime());

		// 更新开通记录状态
		packageFirstTenderDetailRecord(firstTenderDetail, firstTenderReal);
		// 满车复审
		if (firstBorrow.getPlanAccount().intValue() == firstBorrow.getRealAccount().intValue()) {
			// 更新优先投标为满标审核通过
			firstBorrow.setStatus(Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS);
			firstBorrow.setSuccessTime(new Date());
			firstBorrow.setSelfVersion(firstBorrow.getVersion());
			firstBorrow.setVersion(UUID.randomUUID().toString().replace("-", ""));
			Integer updateCount = baseFirstBorrowMapper.updateEntity(firstBorrow);
			if (updateCount != null && updateCount.intValue() > 0) {
				// 插入满车审核记录
				FirstBorrowAppr firstBorrowAppr = new FirstBorrowAppr();
				firstBorrowAppr.setApprTime(new Date()); // 满车时间
				firstBorrowAppr.setRemark("满标审核!");
				firstBorrowAppr.setStyle(Constants.APPROVE_STYLE_SUCCESS_APPROVE);
				firstBorrowAppr.setFirstBorrowId(firstBorrowVo.getId());
				firstBorrowAppr.setStatus(Constants.APPROVE_PASS);
				firstBorrowAppr.setUserId(-1); // 系统自动审核
				if (firstTenderReal.getId() != null) {
					firstBorrowAppr.setFirstTenderRealId(firstTenderReal.getId());
				}
				baseFirstBorrowApprMapper.insertEntity(firstBorrowAppr);
			}
		}

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrowOpenCnd.getIsUseCurMoney() != null && firstBorrowOpenCnd.getIsUseCurMoney().equals("1")) {
			// 当开通金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (firstBorrowOpenCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = firstBorrowOpenCnd.getTenderMoney().subtract(useMoney);
				// 获取转出记录
				CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW, userId, remainTenderMoney);
				if (firstTenderDetail == null || firstTenderDetail.getId() == null || curOut == null) {
					throw new AppException("活期宝开通直通车失败");
				} else {
					curOut.setTargetId(firstTenderDetail.getId());
					Integer updateCurOutCount = curOutService.updateByPrimaryKeySelective(curOut);
					if (updateCurOutCount == null || updateCurOutCount == 0) {
						throw new AppException("活期宝开通直通车失败");
					}
				}
			}
		}

		// 发放“直通车投资奖”抽奖机会
		//lotteryChanceInfoService.firstBorrowAwardLotteryChance(userId, Integer.parseInt(String.valueOf(effectiveMoney)), firstTenderReal.getId());

		// 发放“首次投资奖或理财里程碑奖”抽奖机会
		// lotteryChanceInfoService.investLevelAwardLotteryChance(userId,
		// effectiveMoney);

		return result;
	}

	@Override
	public String saveTenderFirstAutoApprove(Integer firstBorrowId, String ip) throws Exception {
		String result = "success";
		// 获取优先投标信息
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		firstBorrowCnd.setId(firstBorrowId);
		// 锁定投标记录
		firstBorrowCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		FirstBorrowVo firstBorrowVo = this.queryFirstBorrowByCnd(firstBorrowCnd);
		// 如果满标复审中,执行满标操作
		if (firstBorrowVo.getStatus() != Constants.FIRST_BORROW_STATUS_TO_SUCCESS_APPROVE) {
			return "直通车状态非满标复审中,请刷新数据!";
		}
		// 插入满标审核记录
		FirstBorrowAppr firstBorrowAppr = new FirstBorrowAppr();
		firstBorrowAppr.setApprTime(new Date());
		firstBorrowAppr.setStatus(Constants.APPROVE_PASS);
		firstBorrowAppr.setRemark("满标审核，系统自动通过!");
		firstBorrowAppr.setStyle(Constants.APPROVE_STYLE_SUCCESS_APPROVE);
		firstBorrowAppr.setFirstBorrowId(firstBorrowVo.getId());
		baseFirstBorrowApprMapper.insertEntity(firstBorrowAppr);

		// 更新优先投标为满标审核通过
		firstBorrowVo.setStatus(Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS);
		firstBorrowVo.setSuccessTime(new Date());
		// 获取锁定日期
		Date lockEndTime = DateUtils.monthOffset(firstBorrowVo.getSuccessTime(), firstBorrowVo.getLockLimit());
		firstBorrowVo.setLockEndtime(lockEndTime);
		FirstBorrow firstBorrow = new FirstBorrow();
		BeanUtils.copyProperties(firstBorrowVo, firstBorrow);
		BeanUtils.copyProperties(firstBorrowVo, firstBorrow);
		firstBorrow.setSelfVersion(firstBorrow.getVersion());
		firstBorrow.setVersion(UUID.randomUUID().toString().replace("-", ""));
		Integer updateCount = baseFirstBorrowMapper.updateEntity(firstBorrow);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("直通车数据已变更,请刷新页面或稍后重试！");
		}

		// 获取开通记录，
		FirstTenderDetailCnd firstTenderDetailCnd = new FirstTenderDetailCnd();
		firstTenderDetailCnd.setFirstBorrowId(firstBorrow.getId());
		List<FirstTenderDetailVo> tenders = firstTenderDetailService.queryListByCnd(firstTenderDetailCnd);
		// 复审通过，更新用户资金账户、，生成开通成功日志、更新开通状态
		reviewPassed(firstBorrow, tenders, ip);
		// 生成最终的开通记录
		this.generateTenderReal(firstBorrow, tenders);
		// 更新关联的最终记录id
		this.updateFirstTenderDetail(firstBorrow);
		return result;
	}

	@Override
	public Integer updateRealAccountByUnlock(Integer unlockaccount, Integer firstBorrowId) throws Exception {
		return firstBorrowMapper.updateRealAccountByUnlock(unlockaccount, firstBorrowId);
	}

	@Override
	public Integer updateFirstBorrowStatusByUnlock(Integer firstBorrowId) throws Exception {
		return firstBorrowMapper.updateFirstBorrowStatusByUnlock(firstBorrowId);
	}

	/**
	 * <p>
	 * Description:生成最终的开通记录<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年3月6日
	 * @param firstBorrowId void
	 */
	private void generateTenderReal(FirstBorrow firstBorrow, List<FirstTenderDetailVo> firstTenderDetailList) throws Exception {
		// 获取用户的投资总额
		Map<String, Integer> userMoneyMap = new HashMap<String, Integer>();
		for (FirstTenderDetailVo firstTenderDetailVo : firstTenderDetailList) {
			// 如果用户已加入，金融累加
			if (userMoneyMap.containsKey(String.valueOf(firstTenderDetailVo.getUserId()))) {
				userMoneyMap.put(String.valueOf(firstTenderDetailVo.getUserId()), userMoneyMap.get(String.valueOf(firstTenderDetailVo.getUserId())) + firstTenderDetailVo.getAccount());
				// 包含用户
			} else {
				userMoneyMap.put(String.valueOf(firstTenderDetailVo.getUserId()), firstTenderDetailVo.getAccount());
			}
		}
		for (String userId : userMoneyMap.keySet()) {
			FirstTenderReal firstTenderReal = new FirstTenderReal();
			firstTenderReal.setFirstBorrowId(firstBorrow.getId());
			firstTenderReal.setAccount(userMoneyMap.get(userId));
			firstTenderReal.setRate(new BigDecimal(firstTenderReal.getAccount()).divide(new BigDecimal(firstBorrow.getPlanAccount()), 8, BigDecimal.ROUND_DOWN));
			firstTenderReal.setUserId(Integer.parseInt(userId));
			firstTenderReal.setUseMoney(new BigDecimal(userMoneyMap.get(userId)));
			firstTenderReal.setPlanAccount(firstBorrow.getPlanAccount());
			firstTenderReal.setStatus(Constants.TENDER_REAL_STATUS_AVAILABLE);
			firstTenderReal.setAddtime(new Date());
			firstTenderReal.setVersion(UUID.randomUUID().toString().replace("-", ""));
			firstTenderReal.setFirstTenderType(Constants.FIRST_BORROW_TENDER_TYPE_ONE); // 首次开通
			baseFirstTenderRealMapper.insertEntity(firstTenderReal);
		}
	}

	/**
	 * <p>
	 * Description:复审通过，资金账户、日志、开通状态<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrow void
	 */
	private void reviewPassed(FirstBorrow firstBorrow, List<FirstTenderDetailVo> tenders, String ip) throws Exception {
		// 获取开通记录用户的帐号信息并锁定记录
		List<AccountVo> accountList = firstTenderDetailService.queryAccountListForUpdateByFirstId(firstBorrow.getId());
		// 更新投标明细为开通成功
		firstTenderDetailService.updateStatusByFirstBorrowId(firstBorrow.getId(), Constants.TENDER_DETAIL_STATUS_SUCCESS);
		// 将帐号记录封装到map中
		Map<Integer, AccountVo> accountVoMap = new HashMap<Integer, AccountVo>();
		for (AccountVo accountVo : accountList) {
			accountVoMap.put(accountVo.getUserId(), accountVo);
		}

		for (FirstTenderDetailVo firstTenderDetailVo : tenders) {
			AccountVo accountVo = accountVoMap.get(firstTenderDetailVo.getUserId());

			accountVo.setNoUseMoney(accountVo.getNoUseMoney().subtract(new BigDecimal(firstTenderDetailVo.getAccount())));
			// 更新用户优先投标余额
			if (null != accountVo.getFirstBorrowUseMoney()) {
				accountVo.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney().add(new BigDecimal(firstTenderDetailVo.getAccount())));
			} else {
				accountVo.setFirstBorrowUseMoney(new BigDecimal(firstTenderDetailVo.getAccount()));
			}

			// 更新帐号
			Account account = new Account();
			BeanUtils.copyProperties(accountVo, account);
			baseAccountMapper.updateEntity(account);

			// 重新设置到map中
			accountVoMap.put(firstTenderDetailVo.getUserId(), accountVo);

			// 开通账户日志-开通成功
			accountLogService.saveAccountLogByParams(accountVo, firstTenderDetailVo.getUserId(), new BigDecimal(firstTenderDetailVo.getAccount()), "投标直通车：(第" + firstBorrow.getPeriods() + "期)"
					+ "审核通过，扣除账户冻结金额。", ip, "first_subscribe_success", Constants.ACCOUNT_LOG_ID_TYPE_FIRST, firstBorrow.getId(), firstBorrow.getName());
		}
	}

	/**
	 * <p>
	 * Description:保存直通车资金使用记录<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param memberVo
	 * @param request
	 * @param accountVo
	 * @param effectiveMoney
	 * @param firstBorrow
	 * @throws Exception void
	 */
	private void packageFirstAccountLog(Integer userId, HttpServletRequest request, AccountVo accountVo, BigDecimal effectiveMoney, FirstBorrow firstBorrow) throws Exception {
		StringBuffer firstLogRemark = new StringBuffer();
		firstLogRemark.append("开通投标直通车成功，可用减少，直通车余额增加。");
		accountLogService.saveAccountLogByParams(accountVo, userId, effectiveMoney, firstLogRemark.toString(), HttpTookit.getRealIpAddr(request), "first_subscribe_direct_success",
				Constants.ACCOUNT_LOG_ID_TYPE_FIRST, firstBorrow.getId(), firstBorrow.getName());
	}

	/**
	 * <p>
	 * Description:用户投直通车时修改帐号信息（用于直接生成最终认购记录）<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param accountVo
	 * @param effectiveMoney
	 * @return
	 * @throws Exception
	 * @throws AppException Account
	 */
	private Account packageAccount(AccountVo accountVo, BigDecimal effectiveMoney) throws Exception {
		// 可用减少
		accountVo.setUseMoney(accountVo.getUseMoney().subtract(effectiveMoney));
		// 如果投标直通车金额大于等于不可用金额,不可提金额为0，可提金额为：原有可提金额-（投标直通车金额-不可提金额）
		if (effectiveMoney.compareTo(accountVo.getNoDrawMoney()) >= 0) {
			accountVo.setDrawMoney(accountVo.getDrawMoney().subtract(effectiveMoney.subtract(accountVo.getNoDrawMoney())));
			accountVo.setNoDrawMoney(BigDecimal.ZERO);
		} else {
			accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().subtract(effectiveMoney));
		}
		// 直通车总可用余额增加
		accountVo.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney().add(effectiveMoney));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateAccountCount = baseAccountMapper.updateEntity(account);
		if (null == updateAccountCount || updateAccountCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		return account;
	}

	/**
	 * <p>
	 * Description:保存开通记录<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param memberVo
	 * @param request
	 * @param accountVo
	 * @param effectiveMoney
	 * @param firstBorrow
	 * @throws Exception FirstTenderDetail
	 */
	private FirstTenderDetail packageFirstTenderDetail(Integer userId, HttpServletRequest request, AccountVo accountVo, BigDecimal effectiveMoney, FirstBorrow firstBorrow) throws Exception {
		FirstTenderDetail firstTenderDetail = new FirstTenderDetail();
		firstTenderDetail.setUserId(userId);
		firstTenderDetail.setFirstBorrowId(firstBorrow.getId());
		firstTenderDetail.setAccount(effectiveMoney.intValue());
		// 如果投标直通车金额大于等于不可用金额
		if (effectiveMoney.compareTo(accountVo.getNoDrawMoney()) >= 0) {
			firstTenderDetail.setNoDrawMoney(accountVo.getNoDrawMoney());
			firstTenderDetail.setDrawMoney(effectiveMoney.subtract(accountVo.getNoDrawMoney()));
		} else {
			firstTenderDetail.setNoDrawMoney(effectiveMoney);
			firstTenderDetail.setDrawMoney(BigDecimal.ZERO);
		}
		firstTenderDetail.setAddIp(HttpTookit.getRealIpAddr(request));
		firstTenderDetail.setStatus(Constants.TENDER_DETAIL_STATUS_DOING);
		firstTenderDetail.setAddtime(new Date());
		firstTenderDetail.setVersion(UUID.randomUUID().toString().replace("-", ""));
		firstTenderDetail.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		baseFirstTenderDetailMapper.insertEntity(firstTenderDetail);
		return firstTenderDetail;
	}

	/**
	 * <p>
	 * Description: 用户投直通车时修改直通车记录，增加投标资金信息<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrow
	 * @param money
	 * @return
	 * @throws Exception Integer
	 */
	private FirstBorrow modifyFirstBorrowWhenTender(FirstBorrowVo firstBorrowVo, Integer money) throws Exception {
		// 计算当前投标的有效金额
		Integer remaind = firstBorrowVo.getPlanAccount() - firstBorrowVo.getRealAccount();
		money = remaind.compareTo(money) == 1 ? money : remaind;
		// 开通中
		firstBorrowVo.setAccountYes(firstBorrowVo.getAccountYes() + money);
		firstBorrowVo.setRealAccount(firstBorrowVo.getRealAccount() + money);
		firstBorrowVo.setTenderTimes(firstBorrowVo.getTenderTimes() + 1);
		FirstBorrow firstBorrow = new FirstBorrow();
		BeanUtils.copyProperties(firstBorrowVo, firstBorrow);
		firstBorrow.setSelfVersion(firstBorrow.getVersion());
		firstBorrow.setVersion(UUID.randomUUID().toString().replace("-", ""));
		Integer updateCount = baseFirstBorrowMapper.updateEntity(firstBorrow);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("直通车数据已变更,请刷新页面或稍后重试！");
		}
		return firstBorrow;
	}

	/**
	 * <p>
	 * Description:验证投标直通车数据是否正确<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param memberVo
	 * @param firstBorrowOpenCnd
	 * @param request
	 * @param firstBorrow
	 * @param account
	 * @return
	 * @throws Exception String
	 */
	private String validateTenderFirst(MemberVo memberVo, FirstBorrowOpenCnd firstBorrowOpenCnd, HttpServletRequest request, FirstBorrowVo firstBorrow, AccountVo account, BigDecimal effectiveMoney)
			throws Exception {
		String result = "success";
		// 验证直通车页面传参是否正确
		result = validateTenderFirstParamData(memberVo, firstBorrowOpenCnd, request);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证直通车投标业务数据是否正确
		result = validateTenderFirstLogic(firstBorrowOpenCnd, firstBorrow);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证直通车投标金额是否正确
		result = validateTenderFirstMoney(memberVo, firstBorrowOpenCnd, firstBorrow, account, effectiveMoney);
		if (!"success".equals(result)) {
			return result;
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证直通车投标金额是否正确<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param memberVo
	 * @param firstBorrowOpenCnd
	 * @param firstBorrow
	 * @param account
	 * @return
	 * @throws Exception String
	 */
	private String validateTenderFirstMoney(MemberVo memberVo, FirstBorrowOpenCnd firstBorrowOpenCnd, FirstBorrowVo firstBorrow, AccountVo account, BigDecimal effectiveMoney) throws Exception {
		String result = "success";
		StringBuffer moneyMsg = new StringBuffer();
		// 如果金额小于1000，并且不是1000的倍数提示开通金融有误,并且必须是整数，即不能包含小数位数
		if (effectiveMoney.compareTo(BigDecimal.ZERO) == 0 || effectiveMoney.compareTo(new BigDecimal(1000)) == -1 || effectiveMoney.scale() > 0 || (effectiveMoney.intValue() % 1000 != 0)) {
			moneyMsg.append("开通金额错误！\n");
		}
		BigDecimal useMoney = account.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstBorrowOpenCnd.getIsUseCurMoney() != null && firstBorrowOpenCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(account.getUserId());
				useMoney = useMoney.add(maxCurUseMoney);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (useMoney.compareTo(effectiveMoney) == -1) {
			moneyMsg.append("-账户余额不足，无法开通！\n");
		}
		if (moneyMsg.length() > 0) {
			return moneyMsg.toString();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证直通车投标业务数据是否正确 <br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrowOpenCnd
	 * @param msg
	 * @param firstBorrow void
	 */
	private String validateTenderFirstLogic(FirstBorrowOpenCnd firstBorrowOpenCnd, FirstBorrowVo firstBorrow) {
		String result = "success";
		StringBuffer msg = new StringBuffer();
		if (null == firstBorrow) {
			msg.append("-直通车数据未找到！\n");
		}
		if (null != firstBorrow.getBidPassword() && !"".equals(firstBorrow.getBidPassword())) {
			if (!firstBorrow.getBidPassword().equals(MD5.toMD5(firstBorrowOpenCnd.getBidPassword()))) {
				msg.append("-开通密码输入有误！\n");
			}
		}
		// 如果状态不是审核通过投标中且不是满标审核通过时
		if (firstBorrow.getStatus() != Constants.FIRST_BORROW_STATUS_APPROVE_PASS && firstBorrow.getStatus() != Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS) {
			msg.append("-该开通计划不允许继续开通！\n");
		} else {
			// 当已投金额大于等与计划金额时
			if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS || firstBorrow.getRealAccount() >= firstBorrow.getPlanAccount()) {
				msg.append("-直通车已满，无法继续开通！\n");
			}
		}
		// 验证当前时间是否超过了发布时间
		int havePassTime = new Date().compareTo(firstBorrow.getPublishTime());
		if (havePassTime < 0) {
			msg.append("-未到发布时间,请稍候！\n");
		}
		// 验证当前时间是否已超过认购截止时间
		havePassTime = new Date().compareTo(firstBorrow.getEndTime());
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
	 * Description:验证直通车页面传参是否正确<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param memberVo
	 * @param firstBorrowOpenCnd
	 * @param request
	 * @param msg void
	 */
	private String validateTenderFirstParamData(MemberVo memberVo, FirstBorrowOpenCnd firstBorrowOpenCnd, HttpServletRequest request) {
		String result = "success";
		StringBuffer msg = new StringBuffer();
		String randCode = (String) request.getSession().getAttribute("randomCode");
		if (null == firstBorrowOpenCnd.getCheckCode() || null == randCode || !firstBorrowOpenCnd.getCheckCode().equals(randCode)) {
			msg.append("-验证码输入有误！\n");
		}
		request.getSession().removeAttribute("randomCode");
		// 验证传进来的数据是否正确
		String payPassword = MD5.toMD5(firstBorrowOpenCnd.getPayPassword());
		if (null == memberVo.getPaypassword() || !memberVo.getPaypassword().equals(payPassword)) {
			msg.append("-交易密码输入有误！\n");
		}
		if (msg.length() > 0) {
			return msg.toString();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:投标直通车-加入总人次<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return Integer
	 */
	private Integer queryFirstPersonsTotalCount() throws Exception {
		Integer count = firstBorrowMapper.queryFirstPersonsTotalCount();
		if (null != count) {
			return count;
		}
		return 0;
	}

	/**
	 * <p>
	 * Description:投标直通车-累计总金额<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	private BigDecimal queryFirstTotalAccount() throws Exception {
		BigDecimal result = firstBorrowMapper.queryFirstTotalAccount();
		if (null != result) {
			return result.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * <p>
	 * Description:投标直通车-为用户累计赚取(包含待收利息和已赚利息)<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	private BigDecimal queryFirstTotalInterst() throws Exception {
		BigDecimal result = firstBorrowMapper.queryFirstTotalInterst();
		if (null != result) {
			return result.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * <p>
	 * Description:投标直通车-资金利用率<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	private BigDecimal queryFirstAccountRate() throws Exception {
		BigDecimal result = firstBorrowMapper.queryFirstAccountRate();
		if (null != result) {
			return result.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * <p>
	 * Description:投标直通车-为用户自动投标次数<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return Integer
	 */
	private Integer queryFirstTenderCount() throws Exception {
		Integer count = firstBorrowMapper.queryFirstTenderCount();
		if (null != count) {
			return count;
		}
		return 0;
	}

	@Override
	public String saveFirstBorrow(int borrow_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowid", borrow_id);
		firstBorrowMapper.firstTender(map);
		String msg = map.get("msg").toString();
		if (!"0001".equals(msg)) {
			throw new AppException("直通车投标出错");
		}
		return BusinessConstants.SUCCESS;
	}

	/**
	 * <p>
	 * 获取直通车总余额
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @return
	 * @throws Exception BigDecimal
	 */
	private BigDecimal queryFirstUseMoney() throws Exception {
		BigDecimal result = firstBorrowMapper.queryFirstUseMoney();
		if (null != result) {
			return result.setScale(2, BigDecimal.ROUND_DOWN);
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取最新直通车信息
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @return
	 * @throws Exception
	 */
	@Override
	public FirstBorrowVo getLatest() {
		return firstBorrowMapper.getLatest();
	}

	@Override
	public FirstBorrowVo queryFirstBorrowForIndex() throws Exception {
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		firstBorrowCnd.setNow(new Date());
		Page page = new Page(1, 1);
		List<FirstBorrowVo> list = firstBorrowMapper.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page queryFirstListByCnd(FirstBorrowCnd firstBorrowCnd, int pageNo, int pageSize) throws Exception {
		firstBorrowCnd.setNow(new Date());
		Page page = new Page(pageNo, pageSize);
		Integer totalCount = firstBorrowMapper.queryFirstBorrowCountByFirstBorrowCnd(firstBorrowCnd);
		page.setTotalCount(totalCount);
		List<FirstBorrowVo> list = firstBorrowMapper.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
		page.setResult(list);
		return page;
	}

	/**
	 * <p>
	 * Description:帐号冻结扣除，直通车总额增加<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param accountVo
	 * @param effectiveMoney
	 * @return
	 * @throws Exception Account
	 */
	private Account packageAccountFirst(AccountVo accountVo, BigDecimal effectiveMoney) throws Exception {
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().subtract(effectiveMoney));
		accountVo.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney().add(effectiveMoney));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateAccountCount = baseAccountMapper.updateEntity(account);
		if (null == updateAccountCount || updateAccountCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		return account;
	}

	/**
	 * <p>
	 * Description:保存直通车最终认购资金使用记录<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param userId
	 * @param request
	 * @param accountVo
	 * @param effectiveMoney
	 * @param firstBorrow
	 * @throws Exception void
	 */
	private void packageFirstTenderRealAccountLog(Integer userId, HttpServletRequest request, AccountVo accountVo, BigDecimal effectiveMoney, FirstBorrow firstBorrow) throws Exception {
		// 开通账户日志-开通成功
		accountLogService.saveAccountLogByParams(accountVo, userId, effectiveMoney, "开通投标直通车成功，审核通过，扣除账户冻结金额。", HttpTookit.getRealIpAddr(request), "first_subscribe_success",
				Constants.ACCOUNT_LOG_ID_TYPE_FIRST, firstBorrow.getId(), firstBorrow.getName());
	}

	/**
	 * <p>
	 * Description:更新开通记录状态<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param firstTenderDetail void
	 */
	private void packageFirstTenderDetailRecord(FirstTenderDetail firstTenderDetail, FirstTenderReal firstTenderReal) throws Exception {
		if (firstTenderReal == null || firstTenderReal.getId() == null) {
			throw new AppException("直通车最终记录为空,请刷新页面或稍后重试！");
		}
		firstTenderDetail.setStatus(Constants.TENDER_DETAIL_STATUS_SUCCESS);
		firstTenderDetail.setSelfVersion(firstTenderDetail.getVersion());
		firstTenderDetail.setVersion(firstTenderDetail.getVersion() + 1);
		firstTenderDetail.setFirstTenderRealId(firstTenderReal.getId());
		Integer updateFirstTenderDetailCount = baseFirstTenderDetailMapper.updateEntity(firstTenderDetail);
		if (null == updateFirstTenderDetailCount || updateFirstTenderDetailCount == 0) {
			throw new AppException("直通车开通记录数据已变更,请刷新页面或稍后重试！");
		}
	}

	/**
	 * <p>
	 * Description:保存最终开通记录(用于开通时直接生成最终记录)<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param userId
	 * @param request
	 * @param accountVo
	 * @param effectiveMoney
	 * @param firstBorrow
	 * @throws Exception void
	 */
	private FirstTenderReal packageFirstTenderReal(Integer userId, HttpServletRequest request, AccountVo accountVo, BigDecimal effectiveMoney, FirstBorrow firstBorrow, Date onBusTime)
			throws Exception {
		FirstTenderReal firstTenderReal = new FirstTenderReal();
		firstTenderReal.setUserId(userId);
		firstTenderReal.setFirstBorrowId(firstBorrow.getId());
		firstTenderReal.setAccount(effectiveMoney.intValue());
		firstTenderReal.setUseMoney(effectiveMoney);
		firstTenderReal.setPlanAccount(firstBorrow.getPlanAccount());
		firstTenderReal.setRate(new BigDecimal(firstTenderReal.getAccount()).divide(new BigDecimal(firstBorrow.getPlanAccount()), 8, BigDecimal.ROUND_DOWN));
		firstTenderReal.setPlanAccount(firstBorrow.getPlanAccount());
		firstTenderReal.setStatus(Constants.TENDER_REAL_STATUS_AVAILABLE);
		firstTenderReal.setAddtime(new Date());
		firstTenderReal.setVersion(UUID.randomUUID().toString().replace("-", ""));
		firstTenderReal.setOnBusTime(onBusTime);
		Integer maxNum = firstTenderRealMapper.getMaxOrderNumByFirstTenderReal();
		if (maxNum == null) {
			maxNum = 1;
		} else {
			maxNum = maxNum + 1;
		}
		firstTenderReal.setOrderNum(maxNum.intValue());
		firstTenderReal.setFirstTenderType(Constants.FIRST_BORROW_TENDER_TYPE_ONE);
		baseFirstTenderRealMapper.insertEntity(firstTenderReal);
		return firstTenderReal;
	}

	/**
	 * <p>
	 * Description:更新关联的最终记录id<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年12月4日
	 * @param firstBorrow
	 * @throws Exception void
	 */
	private void updateFirstTenderDetail(FirstBorrow firstBorrow) throws Exception {
		// 更新关联的最终记录id
		firstTenderDetailService.updateRealIdByFirstBorrowId(firstBorrow.getId());
	}

	@Override
	public List<FirstBorrowVo> queryFirstBorrowByFirstBorrowCnd(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception {
		return firstBorrowMapper.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
	}

}
