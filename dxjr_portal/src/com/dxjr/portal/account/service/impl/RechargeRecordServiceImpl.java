package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.RechargeRecordMapper;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;

/**
 * <p>
 * Description:充值记录业务实现类<br />
 * </p>
 * 
 * @title RechargeRecordServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月22日
 */
@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;

	@Override
	public RechargeRecordVo queryRechargeRecordVoByCnd(RechargeRecordCnd rechargeRecordCnd) throws Exception {
		List<RechargeRecordVo> list = rechargeRecordMapper.queryRechargeRecordList(rechargeRecordCnd);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Page queryPageListByCnd(RechargeRecordCnd rechargeRecordCnd, Page page) throws Exception {
		int totalCount = rechargeRecordMapper.queryRechargeRecordCount(rechargeRecordCnd);
		page.setTotalCount(totalCount);
		List<RechargeRecordVo> list = rechargeRecordMapper.queryRechargeRecordList(rechargeRecordCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public BigDecimal queryRechargeTotalByMemberIdAndDay(Integer memberId, String datetime) throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		// 线上充值总和
		BigDecimal onlineMoneyTotal = rechargeRecordMapper.queryOnlineMoneyTotalByDatetime(memberId, datetime);
		if (null != onlineMoneyTotal) {
			result = result.add(onlineMoneyTotal);
		}
		// 线下充值总和
		BigDecimal offlineMoneyTotal = rechargeRecordMapper.queryOfflineMoneyTotalByDatetime(memberId, datetime);
		if (null != offlineMoneyTotal) {
			result = result.add(offlineMoneyTotal);
		}
		return result;
	}

	@Override
	public BigDecimal queryRechargeTotalByCnd(RechargeRecordCnd rechargeRecordCnd) throws Exception {
		return rechargeRecordMapper.queryRechargeTotalByCnd(rechargeRecordCnd);
	}

}
