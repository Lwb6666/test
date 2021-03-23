package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.FirstTenderReal;
import com.dxjr.base.entity.FirstUnlockAppr;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseFirstTenderRealMapper;
import com.dxjr.base.mapper.BaseFirstUnlockApprMapper;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;
import com.dxjr.portal.first.mapper.FirstTenderRealMapper;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.service.FirstTenderRealService;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.portal.first.vo.FirstTenderRealVo;
import com.dxjr.portal.member.service.BlackListSevice;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:优先投标计划最终认购记录业务实现类<br />
 * </p>
 * 
 * @title FirstTenderRealServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author justin.xu
 * @version 0.1 2014年7月24日
 */
@Service
public class FirstTenderRealServiceImpl implements FirstTenderRealService {

	@Autowired
	private FirstTenderRealMapper firstTenderRealMapper;
	@Autowired
	private BaseFirstTenderRealMapper baseFirstTenderRealMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseFirstUnlockApprMapper baseFirstUnlockApprMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private FirstBorrowService firstBorrowService;
	@Autowired
	private AccountNetValueMapper accountNetValueMapper;
	@Autowired
	private FirstTenderDetailService firstTenderDetailService;
	@Autowired
	private BlackListSevice blackListSevice;

	@Override
	public Page queryPageListByCnd(FirstTenderRealCnd firstTenderRealCnd, Page page) throws Exception {
		firstTenderRealCnd.setOrderSql(" order by tr.addtime desc ");
		Integer totalCount = firstTenderRealMapper.queryFirstTenderRealCount(firstTenderRealCnd);
		page.setTotalCount(totalCount);
		List<FirstTenderRealVo> list = firstTenderRealMapper.queryFirstTenderRealList(firstTenderRealCnd, page);
		List<FirstTenderRealVo> firstTenderRealVoList = new ArrayList<FirstTenderRealVo>();
		for (FirstTenderRealVo firstTenderRealVo : list) {
			if (firstTenderRealVo.getStatus().intValue() == 0) {
				firstTenderRealVo.setFirstEarnedIncome(firstTenderRealMapper.getIncomeForUnlockedByRealId(firstTenderRealVo.getId()));
			}
			if (firstTenderRealVo.getStatus().intValue() == 1) {
				firstTenderRealVo.setFirstEarnedIncome(firstTenderRealMapper.getIncomeForLockedByRealId(firstTenderRealVo.getId()));
			}
			firstTenderRealVoList.add(firstTenderRealVo);
		}
		page.setResult(firstTenderRealVoList);
		return page;
	}

	@Override
	public String saveUnLock(Integer firstTenderRealId, Integer memberId) throws Exception {
		String result = "success";
		if (null == firstTenderRealId) {
			return "参数错误";
		}
		FirstTenderRealCnd firstTenderRealCnd = new FirstTenderRealCnd();
		firstTenderRealCnd.setId(firstTenderRealId);
		firstTenderRealCnd.setUserId(memberId);
		firstTenderRealCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		List<FirstTenderRealVo> realList = firstTenderRealMapper.queryFirstTenderRealList(firstTenderRealCnd);
		if (null == realList || realList.size() != 1) {
			return "未找到此直通车数据!";
		}
		FirstTenderRealVo firstTenderRealVo = realList.get(0);
		if (firstTenderRealVo.getStatus() == 2) {
			return "该直通车解锁中，请确认数据！";
		}
		if (firstTenderRealVo.getStatus() == 1) {
			return "该直通车已解锁，请勿重复操作！";
		}
		// 如果不存在直通车下车黑名单记录，则需判断是否过最低锁定期
		if (!blackListSevice.judgeBlackByUserIdAndType(memberId, BusinessConstants.BLACK_TYPE_FIRST_DEBUS)) {
			if (null == firstTenderRealVo.getUnLockYn() || !"Y".equals(firstTenderRealVo.getUnLockYn())) {
				return "未过最低锁定期，请确认数据！";
			}
		}
		// 更新此期数此用户的状态为 解锁中
		firstTenderRealVo.setStatus(Constants.TENDER_REAL_STATUS_UNLOCKING);
		FirstTenderReal firstTenderReal = new FirstTenderReal();
		BeanUtils.copyProperties(firstTenderRealVo, firstTenderReal);
		baseFirstTenderRealMapper.updateEntity(firstTenderReal);
		return result;
	}

	@Override
	public String saveUnLockApprove(Integer firstTenderRealId, Integer memberId, String ip, Integer approveUserId, String approveRemark) throws Exception {
		String result = "success";
		if (null == firstTenderRealId) {
			return "参数错误";
		}
		FirstTenderRealCnd firstTenderRealCnd = new FirstTenderRealCnd();
		firstTenderRealCnd.setId(firstTenderRealId);
		firstTenderRealCnd.setUserId(memberId);
		// 锁定记录
		firstTenderRealCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		List<FirstTenderRealVo> realList = firstTenderRealMapper.queryFirstTenderRealList(firstTenderRealCnd);
		if (null == realList || realList.size() != 1) {
			return "未找到此直通车数据!";
		}
		FirstTenderRealVo firstTenderRealVo = realList.get(0);
		if (firstTenderRealVo.getStatus() != Constants.TENDER_REAL_STATUS_UNLOCKING) {
			return "认购记录非锁定中,请刷新页面或稍后重试";
		}
		// 直通车可用余额
		BigDecimal realUseMoney = firstTenderRealVo.getUseMoney();
		// 更新此期数此用户的最终开通可用余额 = 0，状态为已失效
		updateFirstRealWhenUnLockApprove(firstTenderRealVo);
		if (realUseMoney.compareTo(new BigDecimal("0")) > 0) {
			// 更新用户帐号,将直通车可用余额返回到帐号
			AccountVo accountVo = updateAccountWhenUnLockApprove(firstTenderRealVo, realUseMoney);
			// 插入一条用户解锁资金明细记录
			packageAccountlogWhenUnLockApprove(ip, firstTenderRealVo, realUseMoney, accountVo);
		}
		// 判断用户的可提金额是否大于净值额度，如果大于，转入受限金额
		DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd = new DrawMoneyToNoDrawCnd();
		drawMoneyToNoDrawCnd.setUserid(memberId);
		drawMoneyToNoDrawCnd.setNetmoneytype(BusinessConstants.NET_TYPE_FIRST_UNLOCK_SUCCESS);
		drawMoneyToNoDrawCnd.setAddip(ip);
		drawMoneyToNoDrawCnd.setAccountlogType("net_draw_to_nodraw_first_unlock_success");
		drawMoneyToNoDrawCnd.setAccountlogRemark("直通车解锁之后,可提金额大于净值额度，可提金额转入受限金额。");
		accountNetValueMapper.dealDrawmoneyToNodraw(drawMoneyToNoDrawCnd);

		// 更新待收中的直通车状态 为已失效
		firstTenderRealMapper.updateCollectionFirstToInvalid(firstTenderRealId, memberId);
		// 根据解锁变动情况更新 实际直通车总额字段
		firstBorrowService.updateRealAccountByUnlock(firstTenderRealVo.getAccount(), firstTenderRealVo.getFirstBorrowId());
		// 如果实际金额为0，更新直通车记录的状态为失效
		// firstBorrowService.updateFirstBorrowStatusByUnlock(firstBorrowId);
		// 向直通车解锁审核表中插入一条记录
		insertFirstUnlockAppr(firstTenderRealVo, approveUserId, approveRemark);
		return result;
	}

	/**
	 * <p>
	 * Description:向直通车解锁审核表中插入一条记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月28日
	 * @param firstTenderRealVo
	 * @throws Exception void
	 */
	private void insertFirstUnlockAppr(FirstTenderRealVo firstTenderRealVo, Integer approveUserId, String approveRemark) throws Exception {
		FirstUnlockAppr firstUnlockAppr = new FirstUnlockAppr();
		firstUnlockAppr.setApprTime(new Date());
		if (null != approveUserId) {
			firstUnlockAppr.setUserId(approveUserId);
		}
		if (null != approveRemark && !"".equals(approveRemark.trim())) {
			firstUnlockAppr.setRemark(approveRemark);
		} else {
			firstUnlockAppr.setRemark("解锁审核，系统自动通过!");
		}
		firstUnlockAppr.setStatus(Constants.APPROVE_PASS);
		firstUnlockAppr.setFirstTenderRealId(firstTenderRealVo.getId());
		baseFirstUnlockApprMapper.insertEntity(firstUnlockAppr);
	}

	/**
	 * <p>
	 * Description:解锁审核时：插入一条用户解锁资金明细记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param ip
	 * @param firstTenderRealVo
	 * @param realUseMoney
	 * @param accountVo
	 * @throws Exception void
	 */
	private void packageAccountlogWhenUnLockApprove(String ip, FirstTenderRealVo firstTenderRealVo, BigDecimal realUseMoney, AccountVo accountVo) throws Exception {
		StringBuffer firstLogRemark = new StringBuffer();
		firstLogRemark.append("投标直通车解锁成功，可用增加,直通车余额减少成功。");
		accountLogService.saveAccountLogByParams(accountVo, firstTenderRealVo.getUserId(), realUseMoney, firstLogRemark.toString(), ip, "first_unlock_success", Constants.ACCOUNT_LOG_ID_TYPE_FIRST,
				firstTenderRealVo.getFirstBorrowId(), firstTenderRealVo.getFirstBorrowName());
	}

	/**
	 * <p>
	 * Description:解锁审核时：更新用户帐号,将直通车可用余额返回到帐号<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param firstTenderRealVo
	 * @param realUseMoney
	 * @throws Exception void
	 */
	private AccountVo updateAccountWhenUnLockApprove(FirstTenderRealVo firstTenderRealVo, BigDecimal realUseMoney) throws Exception {
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(firstTenderRealVo.getUserId());
		accountVo.setUseMoney(accountVo.getUseMoney().add(realUseMoney));
		accountVo.setDrawMoney(accountVo.getDrawMoney().add(realUseMoney));
		accountVo.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney().subtract(realUseMoney));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		baseAccountMapper.updateEntity(account);
		return accountVo;
	}

	/**
	 * <p>
	 * Description:解锁审核时：更新此期数此用户的最终开通可用余额 = 0，状态为已失效<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param firstTenderRealVo
	 * @throws Exception
	 * @throws AppException void
	 */
	private void updateFirstRealWhenUnLockApprove(FirstTenderRealVo firstTenderRealVo) throws Exception, AppException {
		firstTenderRealVo.setUseMoney(BigDecimal.ZERO);
		firstTenderRealVo.setStatus(Constants.TENDER_REAL_STATUS_INVALID);
		FirstTenderReal firstTenderReal = new FirstTenderReal();
		BeanUtils.copyProperties(firstTenderRealVo, firstTenderReal);
		baseFirstTenderRealMapper.updateEntity(firstTenderReal);
	}
}
