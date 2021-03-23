package com.dxjr.portal.curAccount.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.mapper.CurAccountMapper;
import com.dxjr.portal.curAccount.mapper.CurInMapper;
import com.dxjr.portal.curAccount.mapper.CurInterestDetailMapper;
import com.dxjr.portal.curAccount.mapper.CurOutMapper;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurAccountVo;

/****
 * 
 * 
 * <p>
 * Description: 活期宝账户 BLL <br />
 * </p>
 * 
 * @title CapitalAccountServiceImpl.java
 * @package com.dxjr.portal.curAccount.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Service
public class CurAccountServiceImpl implements CurAccountService {

	Logger logger = LoggerFactory.getLogger(CurAccountServiceImpl.class);

	@Autowired
	CurAccountMapper curAccountMapper;

	@Autowired
	CurInterestDetailMapper curInterestDetailMapper;

	@Autowired
	private CurOutMapper curOutMapper;

	@Autowired
	private CurInMapper curInMapper;

	/**
	 * 活期生息查询 list - map (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.curAccount.service.CurAccountService#queryCurAccountList(java.util.Map)
	 */
	@Override
	public List<Map<String ,Object>> queryCurAccountListMap(CurAccountCnd curAccountCnd) throws Exception {
		List<Map<String ,Object>> retMap = null;
		try {

			retMap = curAccountMapper.queryCurAccountListMap(curAccountCnd);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return retMap;
	}

	@Override
	public CurAccountVo selectByUserId(Integer userId) {
		return curAccountMapper.selectByUserId(userId);
	}

	@Override
	public BigDecimal getMaxUseMoneyByNow(Integer userId) throws Exception {
		// 获得活期宝账户
		CurAccount curAccount = curAccountMapper.selectByUserId(userId);
		if (curAccount != null) {
			return curAccount.getTotal();
		} else {
			return BigDecimal.ZERO;
		}
	}

	public CurAccountVo selectByUserIdForUpdate(Integer userId) {
		return curAccountMapper.selectByUserIdForUpdate(userId);
	}

	@Override
	public Integer queryCurAccountCount() {
		return curAccountMapper.queryCurAccountCount();
	}
}
