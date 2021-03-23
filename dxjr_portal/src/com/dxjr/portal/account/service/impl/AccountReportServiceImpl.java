package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountReportMapper;
import com.dxjr.portal.account.service.AccountReportService;
import com.dxjr.portal.account.vo.RatioMoneyVo;
import com.dxjr.portal.account.vo.UnReceiveInterestCnd;
import com.dxjr.portal.account.vo.UnReceiveInterestVo;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.collection.vo.BCollectionRecordVo;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:帐号统计业务类<br />
 * </p>
 * 
 * @title AccountReportServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
@Service
public class AccountReportServiceImpl implements AccountReportService {

	@Autowired
	private AccountReportMapper accountReportMapper;
	@Autowired
	private VIPApproService vipApproService;
	@Autowired
	private RedAccountMapper redAccountMapper;
	
	/**
	 * 普通投标- 待收罚息-去掉直通车的 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.account.service.AccountReportService#queryUnReceiveLateInterestList(java.lang.Integer)
	 */
	@Override
	public BigDecimal queryUnReceiveLateInterestList(Integer memberId) throws Exception {
		return accountReportMapper.queryUnReceiveLateInterestList(memberId);
	}

	@Override
	public BigDecimal queryFirstTotalByUserId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFirstTotalByUserId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFirstUseMoneyByUserId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFirstUseMoneyByUserId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFirstTransferingUseMoney(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFirstTransferingUseMoney(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFirstFreezeAccountByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFirstFreezeAccountByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryLockAccountTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryLockAccountTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryLockCashTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryLockCashTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryYesInterstTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		// 得到用户的所有已赚利息
		BigDecimal money = accountReportMapper.queryYesInterstTotalByMemberId(memberId);
		if (null != money) {
			result = money;
		}
		// 扣除提前还款利息的总计
		BigDecimal subtractEarlyInterest = accountReportMapper.querySubtractEarlyInterestTotalByMemberId(memberId);
		if (null != subtractEarlyInterest) {
			result = result.subtract(subtractEarlyInterest);
		}
		// 减去非VIP阶段网站垫付不用付出的利息
		BigDecimal noVipNoInterestTotal = accountReportMapper.queryNoVipNoInterestTotalByMemberId(memberId);
		if (null != noVipNoInterestTotal) {
			result = result.subtract(noVipNoInterestTotal);
		}
		result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
		return result;
	}

	@Override
	public BigDecimal queryUnReceiveInterstTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryUnReceiveInterstTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryInterestBackTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryInterestBackTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryMoneyByType(Integer memberId, String[] types) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		if (null == types || types.length == 0 || null == memberId) {
			return result;
		}
	    //liutao 20151113 start
		List<String> ls = new ArrayList<String>(Arrays.asList(types));
		if(ls.contains("red_income")){
			BigDecimal redTotal = BigDecimal.ZERO;
			//已使用红包总金额
			RedAccountCnd redAccountCnd=new RedAccountCnd();
			redAccountCnd.setUserId(memberId);
			redTotal=redAccountMapper.queryRedMoneyTotal(redAccountCnd);
			//总收益加上红包收益
			if(null!=redTotal){
				result=result.add(redTotal);
			}
			ls.remove(5);
			types = (String[])ls.toArray(new String[ls.size()]);
		}
		if(null!=accountReportMapper.queryMoneyByType(memberId, types)){
			result = result.add(accountReportMapper.queryMoneyByType(memberId, types));
		}
		 //liutao 20151113 end
		if(null!=result){
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryReceiveInterestByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryReceiveInterestByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryUnPayManageInterstByMemberId(Integer memberId) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		List<RatioMoneyVo> ratioMoneyList = accountReportMapper.queryToPayInterstListByMemberId(memberId);
		if (null != ratioMoneyList && ratioMoneyList.size() > 0) {
			for (RatioMoneyVo ratioMoney : ratioMoneyList) {
				if (null != ratioMoney.getRatio()) {
					// 直接取费率
					result = result.add(ratioMoney.getInterest().multiply(new BigDecimal(ratioMoney.getRatio())));
				} else {
					// 如果为null,收取利息管理费的10%
					result = result.add(ratioMoney.getInterest().multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
				}
			}
		}
		return result;
	}

	@Override
	public BigDecimal queryPayLateInterestByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryPayLateInterestByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryUnPayLateInterestByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryUnPayLateInterestByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryCashCostByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryCashCostByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryHavaPayInterestByMemberId(Integer memberId) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal money = accountReportMapper.queryHavaPayInterestByMemberId(memberId);
		if (null != money) {
			result = money;
		}
		// 扣除 增加提前还款利息的总计
		BigDecimal addEarlyInterest = accountReportMapper.queryAddEarlyInterestTotalByMemberId(memberId);
		if (null != addEarlyInterest) {
			result = result.subtract(addEarlyInterest);
		}
		return result;
	}

	@Override
	public BigDecimal queryWaitPayInterestByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryWaitPayInterestByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryRechargeFeeTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryRechargeFeeTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	/**
	 * <p>
	 * Description:计算待收罚息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param unReceiveInterestVoList
	 * @param isVip
	 *            List<UnReceiveInterestVo>
	 */
	private List<UnReceiveInterestVo> calcUnReceiveInterest(List<UnReceiveInterestVo> unReceiveInterestVoList) {
		BigDecimal zeroLateInterest = BigDecimal.ZERO;
		BorrowVo borrowVo = null;
		BRepaymentRecordVo brepaymentRecordVo = null;
		for (UnReceiveInterestVo unReceiveInterestVo : unReceiveInterestVoList) {
			// 是否Vip，根据投标时是否vip来判断
			boolean isVip = false;
			if (unReceiveInterestVo != null && unReceiveInterestVo.getBcollectionRecordVo().getVipApp_passed() != null
					&& unReceiveInterestVo.getBcollectionRecordVo().getVipApp_passed() == 1) {
				isVip = true;
			}
			BCollectionRecordVo bCollectionRecordVo = unReceiveInterestVo.getBcollectionRecordVo();
			borrowVo = unReceiveInterestVo.getBorrowVo();
			brepaymentRecordVo = unReceiveInterestVo.getBrepaymentRecordVo();
			// 计算估计还款时间 （确定到天）与当前时间（天）相差的天数
			Date repayday = DateUtils.parse(DateUtils.timeStampToDate(bCollectionRecordVo.getRepayTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			Date now = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			int differDays = DateUtils.dayDiff(now, repayday);
			// 罚息一 = 预还金额*罚息计算比率一*相差的天数
			BigDecimal lateDayInterest = bCollectionRecordVo.getRepayAccount().multiply(BusinessConstants.LATE_INTEREST_RATE_ONE)
					.multiply(new BigDecimal(differDays));
			// 罚息二 = 预还金额*罚息计算比率二*相差的天数
			BigDecimal lateDayInterest2 = bCollectionRecordVo.getRepayAccount().multiply(BusinessConstants.LATE_INTEREST_RATE_TWO)
					.multiply(new BigDecimal(differDays));
			if (differDays <= 0) {
				bCollectionRecordVo.setLateInterest(zeroLateInterest);
				// 重新设置进去
				unReceiveInterestVo.setBcollectionRecordVo(bCollectionRecordVo);
				continue;
			}
			// 如果借款标状态为 还款中
			if (null != borrowVo.getStatus() && borrowVo.getStatus() == Constants.BORROW_STATUS_REPAY_CODE) {
				if (borrowVo.getBorrowtype() == Constants.BORROW_TYPE_PLEDGE) {
					// 计算抵押标待收罚息
					bCollectionRecordVo = calcPledgeLateInterest(isVip, zeroLateInterest, borrowVo, brepaymentRecordVo, bCollectionRecordVo,
							lateDayInterest);
				} else if (borrowVo.getBorrowtype() == Constants.BORROW_TYPE_NETVALUE) {
					// 计算净值标待收罚息
					bCollectionRecordVo = calcNetvalueLateInterest(zeroLateInterest, borrowVo, brepaymentRecordVo, bCollectionRecordVo,
							lateDayInterest2);
				}
			}
			// 重新设置进去,保留两位小数
			bCollectionRecordVo.setLateInterest(bCollectionRecordVo.getLateInterest().setScale(2, BigDecimal.ROUND_HALF_UP));
			unReceiveInterestVo.setBcollectionRecordVo(bCollectionRecordVo);
		}
		return unReceiveInterestVoList;
	}

	/**
	 * <p>
	 * Description:计算净值标待收罚息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param zeroLateInterest
	 * @param borrowVo
	 * @param brepaymentRecordVo
	 * @param bCollectionRecordVo
	 * @param lateDayInterest2
	 *            BCollectionRecordVo
	 */
	private BCollectionRecordVo calcNetvalueLateInterest(BigDecimal zeroLateInterest, BorrowVo borrowVo, BRepaymentRecordVo brepaymentRecordVo,
			BCollectionRecordVo bCollectionRecordVo, BigDecimal lateDayInterest2) {
		// 有分期
		if (borrowVo.getTimeLimit() > 1) {
			// 待还记录中的状态为未还
			if (brepaymentRecordVo.getStatus() == Constants.REPAYMENTRECORD_STATUS_NO_PAY) {
				// 网站已代还
				if (brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_HAVE_PAY) {
					bCollectionRecordVo.setLateInterest(zeroLateInterest);
					// 网站未代还，逾期 利息为千分之二
				} else if (brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_NO_PAY) {
					bCollectionRecordVo.setLateInterest(lateDayInterest2);
				}
				// 待还记录中的状态为已还
			} else if (brepaymentRecordVo.getStatus() == Constants.REPAYMENTRECORD_STATUS_HAVE_PAY) {
				// 网站已代还
				if (brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_HAVE_PAY) {
					bCollectionRecordVo.setLateInterest(zeroLateInterest);
				}
			}
		} else if (borrowVo.getTimeLimit() == 1) {
			if (brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_STATUS_HAVE_PAY) {
				bCollectionRecordVo.setLateInterest(zeroLateInterest);
				// 网站未代还，逾期 利息为千分之二
			} else if (brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_NO_PAY) {
				bCollectionRecordVo.setLateInterest(lateDayInterest2);
			}
		}
		return bCollectionRecordVo;
	}

	/**
	 * <p>
	 * Description:计算抵押标待收罚息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param isVip
	 * @param zeroLateInterest
	 * @param borrowVo
	 * @param brepaymentRecordVo
	 * @param bCollectionRecordVo
	 * @param lateDayInterest
	 *            BCollectionRecordVo
	 */
	private BCollectionRecordVo calcPledgeLateInterest(boolean isVip, BigDecimal zeroLateInterest, BorrowVo borrowVo,
			BRepaymentRecordVo brepaymentRecordVo, BCollectionRecordVo bCollectionRecordVo, BigDecimal lateDayInterest) {
		// 还款方式不是按天到期还款
		if (borrowVo.getStyle() != Constants.BORROW_STYLE_DAY_DUE_PAY) {
			// 如果用户非VIP，待还记录中的网站代还状态为已代还，逾期利息为0，否则为为千分之一的利息
			if (!isVip && brepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_HAVE_PAY) {
				bCollectionRecordVo.setLateInterest(zeroLateInterest);
			} else {
				bCollectionRecordVo.setLateInterest(lateDayInterest);
			}
			// 是按天到期还款，逾期利息为0
		} else {
			bCollectionRecordVo.setLateInterest(zeroLateInterest);
		}
		return bCollectionRecordVo;
	}

	@Override
	public BigDecimal queryTransferLockAccountTotalByMemberId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryTransferLockAccountTotalByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;

	}

	@Override
	public BigDecimal queryTransferDiffByMemberId(Integer memberId) {
		// 债权转让转让方债权金额
		BigDecimal transferDiff = accountReportMapper.queryTransferDiffByUserId(memberId);
		// 直通车转让转让方债权金额
		BigDecimal firstTransferDiff = accountReportMapper.queryFirstTransferDiffByUserId(memberId);

		if (null != transferDiff) {
			return transferDiff.add(firstTransferDiff);
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal querySubscribeTransferDiffByMemberId(Integer memberId) {

		// 债权转让认购方债权金额
		BigDecimal subscribeDiff = accountReportMapper.querySubscribeDiffByUserId(memberId);
		// 直通车转让认购方债权金额
		BigDecimal firstSubscribeDiff = accountReportMapper.queryFirstSubscribeDiffByUserId(memberId);

		if (null != subscribeDiff) {
			return (subscribeDiff.add(firstSubscribeDiff)).multiply(new BigDecimal(-1));
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 普通投标 - 代收本金 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.account.service.AccountReportService#queryDsbjByMemberId(java.lang.Integer)
	 */
	@Override
	public BigDecimal queryDsbjByMemberId(Integer memberId) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		result = accountReportMapper.queryDsbjByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFirstTransferDiffByMemberId(Integer memberId) {
		BigDecimal result = accountReportMapper.queryFirstTransferDiffByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFirstSubscribeDiffByMemberId(Integer memberId) {
		BigDecimal result = accountReportMapper.queryFirstSubscribeDiffByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal querySumManageFeeByMemberId(Integer memberId) {
		BigDecimal result = accountReportMapper.querySumManageFeeByMemberId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal queryFixTotalByUserId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFixTotalByUserId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}
 
	/**
	 * 按月统计 - 债权转让-债权金额 + 直通车转让 债权金额 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.account.service.AccountReportService#queryTransferDiffByMonth(java.lang.Integer)
	 */
	@Override
	public BigDecimal queryTransferDiffByMonth(UnReceiveInterestCnd unReceiveInterestCnd) throws Exception {
		BigDecimal retSum = new BigDecimal(0.00);
		try {
			// 债权转让-债权金额（按月统计）
			BigDecimal transferDiffMonth = accountReportMapper.queryTransferDiffByUserIdMonth(unReceiveInterestCnd);
			// 直通车转让 债权金额（按月统计）
			BigDecimal firstTransferDiffMonth = accountReportMapper.queryFirstTransferDiffByUserIdMonth(unReceiveInterestCnd);

			if (null != transferDiffMonth) {
				retSum = transferDiffMonth.add(firstTransferDiffMonth);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retSum;
	}
	
	
		@Override
	public BigDecimal queryFixInterestNoByUserId(Integer memberId) throws Exception {
		BigDecimal result = accountReportMapper.queryFixInterestNoByUserId(memberId);
		if (null != result) {
			return result;
		}
		return BigDecimal.ZERO;
	}
	/**
	 * 按月份 ： 债权转让认购 +  直通车转让认购
	 *  (non-Javadoc)
	 * @see com.dxjr.portal.account.service.AccountReportService#querySubscribeTransferDiffByMemberIdMonth(com.dxjr.portal.account.vo.UnReceiveInterestCnd)
	 */
	@Override
	public BigDecimal querySubscribeTransferDiffByMemberIdMonth(UnReceiveInterestCnd unReceiveInterestCnd) throws Exception {
		BigDecimal retSum = new BigDecimal(0.00);

		try {

			// 债权转让认购方债权金额（按月统计）
			BigDecimal subscribeDiffMonth = accountReportMapper.querySubscribeDiffByUserIdMonth(unReceiveInterestCnd);
			// 直通车转让认购方债权金额（按月统计）
			BigDecimal firstSubscribeDiffMonth = accountReportMapper.queryFirstSubscribeDiffByUserIdMonth(unReceiveInterestCnd);

			if (null != subscribeDiffMonth) {
				retSum = (subscribeDiffMonth.add(firstSubscribeDiffMonth)).multiply(new BigDecimal(-1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retSum;
	}

	@Override
	public BigDecimal queryFixInterestYesByUserId(Integer memberId) throws Exception {
		
		BigDecimal dingQibaomoney = accountReportMapper.queryFixInterestYesByUserId(memberId);
		if (null != dingQibaomoney) {
			return dingQibaomoney;
		}
		return BigDecimal.ZERO;
	}
 
}
