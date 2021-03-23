package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.AccountDayLogMapper;
import com.dxjr.portal.account.service.AccountDayLogService;
import com.dxjr.portal.account.vo.AccountDayLogCnd;
import com.dxjr.portal.account.vo.AccountDayLogVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:资金操作日志业务实现类<br />
 * </p>
 * 
 * @title AccountLogServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
@Service
public class AccountDayLogServiceImpl implements AccountDayLogService {

	@Autowired
	private AccountDayLogMapper accountDayLogMapper;

	@Override
	public BigDecimal queryDayAverageCollectionTotal(Integer userId)
			throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		// 日均待收计算天数
		int count = BusinessConstants.DAY_INTERST_LENGTH;
		// 获取截止到昨天的待收总和
		AccountDayLogCnd accountDayLogCnd = new AccountDayLogCnd();
		accountDayLogCnd.setUserId(userId);
		// 取得昨天的日期
		Date yesterday = DateUtils.dayOffset(new Date(), -1);
		String endAddDate = DateUtils.format(yesterday, DateUtils.YMD_DASH);
		endAddDate = endAddDate + " 23:59:59";
		accountDayLogCnd.setEndAddDate(endAddDate);
		BigDecimal collectionTotal = accountDayLogMapper
				.queryAccountDayLogCollectionTotal(accountDayLogCnd);
		if (null != collectionTotal) {
			result = collectionTotal.divide(new BigDecimal(count), 2,
					BigDecimal.ROUND_DOWN);
		}
		return result;
	}

	@Override
	public List<AccountDayLogVo> queryAccountDayLogCollection(Integer memberId) {
		return accountDayLogMapper.queryAccountDayLogCollection(memberId);
	}

}
