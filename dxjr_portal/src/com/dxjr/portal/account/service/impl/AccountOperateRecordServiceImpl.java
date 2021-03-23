package com.dxjr.portal.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.AccountOperateRecordMapper;
import com.dxjr.portal.account.service.AccountOperateRecordService;
import com.dxjr.portal.account.vo.AccountOperateVO;
import com.dxjr.utils.DateUtils;

@Service
public class AccountOperateRecordServiceImpl implements AccountOperateRecordService{

	@Autowired
	private AccountOperateRecordMapper accountOperateRecordMapper;
	
	@Override
	public Page searchPageUserAccountList(String username, String startTime,
			String endTime, String type, Integer curPage, Integer pageSize)
			{
		return searchPageUserAccountList(username, startTime, endTime,
                type, null, curPage, pageSize);
	}

	@Override
	public Integer countUserAccount(String username, String startTime,
			String endTime, String type) {
		return null;
	}

	@Override
	public List<AccountOperateVO> searchOneMonthUserAccountList(String username,
                                                                String startTime, String endTime, String type, String accountType) {
		
		if(startTime != null && !"".equals(startTime)){
			startTime = com.dxjr.utils.DateUtils.date2TimeStamp(startTime);
		}
		if(endTime != null && !"".equals(endTime)){
			endTime = com.dxjr.utils.DateUtils.dayOffset(DateUtils.parse(endTime, DateUtils.YMD_DASH),1).getTime()+"";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("type", type);
		params.put("username", username);
		params.put("isCustody", accountType);
		//
		List<AccountOperateVO> list = accountOperateRecordMapper.queryOneMonthAccOpertRecordList(
				params);
		return list;
	}

	@Override
	public AccountOperateVO searchPaymentDetail(Integer accountId) {
		return accountOperateRecordMapper.searchPaymentDetail(accountId);
	}

    @Override
    public Page searchPageUserAccountList(String username, String startTime, String endTime,
                                          String type, String accountType, Integer curPage, Integer pageSize) {
        //时间参数格式转换
		if(startTime != null && !"".equals(startTime)){
			startTime = com.dxjr.utils.DateUtils.date2TimeStamp(startTime);
		}
		if(endTime != null && !"".equals(endTime)){
			endTime = com.dxjr.utils.DateUtils.dayOffset(DateUtils.parse(endTime, DateUtils.YMD_DASH),1).getTime()+"";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("type", type);
		params.put("username", username);
        if (accountType != null && (("0").equals(accountType) || ("1").equals(accountType))) {
            params.put("isCustody", accountType);
        }
		Page page = new Page(curPage, pageSize);
		int totalCount = accountOperateRecordMapper
				.countAccOpertRecord(params);
		page.setTotalCount(totalCount);
		List<AccountOperateVO> list = accountOperateRecordMapper.queryAccOpertRecordList(
				params, page);
		page.setResult(list);
        return page;
    }

}
