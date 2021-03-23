package com.dxjr.portal.fix.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.mapper.FixTenderRealMapper;
import com.dxjr.portal.fix.service.FixTenderRealService;
import com.dxjr.portal.fix.vo.FixTenderRealCnd;
import com.dxjr.portal.fix.vo.FixTenderRealVo;

/**
 * <p>
 * Description:定期宝认购明细接口实现类<br />
 * </p>
 * 
 * @title FixTenderDetailServiceImpl.java
 * @package com.dxjr.portal.fix1.service.impl
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
@Service
public class FixTenderRealServiceImpl implements FixTenderRealService {
	Logger logger = LoggerFactory.getLogger(FixTenderRealServiceImpl.class);
	@Autowired
	private FixTenderRealMapper fixTenderRealMapper;
	
	public List<FixTenderRealVo> querySumAccountGroupByUser(
			Integer fixBorrowId) {
		return null;
	}

	
	
	@Override
	public Page queryTotalSYInfoByPage(FixTenderRealCnd fixTendeRealCnd, Page page) {
		int totalCount = 0;
		List<FixTenderRealVo> retLst = null;
		try {
			totalCount = fixTenderRealMapper.queryTotalSYCounts(fixTendeRealCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderRealMapper.queryTotalSYByPage(fixTendeRealCnd, page);
			//判断列表对象不为空且包含的记录数大于0
			if(retLst!=null && retLst.size()>0)
			{
				page.setResult(retLst);
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return page;
	}
 
	

	@Override
	public Page queryTotalTCInfoByPage(FixTenderRealCnd fixTendeRealCnd, Page page) {
		int totalCount = 0;
		List<FixTenderRealVo> retLst = null;
		try {
			totalCount = fixTenderRealMapper.queryTotalTCCounts(fixTendeRealCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderRealMapper.queryTotalTCByPage(fixTendeRealCnd, page);
			//判断列表对象不为空且列表包含的记录数大于0
			if(retLst!=null && retLst.size()>0)
			{
				page.setResult(retLst);
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return page;
	}



	@Override
	public List<FixTenderRealVo> getFixTenderRealstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd) {
		return fixTenderRealMapper.getFixTenderRealstaticByBorrowId(fixTenderRealCnd);
	}

	@Override
	public List<FixTenderRealVo> getTcstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd) {
		return fixTenderRealMapper.getTcstaticByBorrowId(fixTenderRealCnd);
	}
	@Override
	public BigDecimal getYqsyBySzy(FixTenderRealCnd fixTenderRealCnd) throws Exception {
		BigDecimal result  =fixTenderRealMapper.getYqsyBySzy(fixTenderRealCnd);
		//判断对象不为空
		if(result!=null){
			return result;
		}
		return BigDecimal.ZERO;
	}



	@Override
	public Page queryTotalTCZInfoByPage(FixTenderRealCnd fixTenderRealCnd, Page page) throws Exception {
		int totalCount = 0;
		List<FixTenderRealVo> retLst = null;
		try {
			totalCount = fixTenderRealMapper.queryTotalTCZCounts(fixTenderRealCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderRealMapper.queryTotalTCZByPage(fixTenderRealCnd, page);
			//判断列表对象不为空且列表包含的记录数大于0
			if(retLst!=null && retLst.size()>0)
			{
				page.setResult(retLst);
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return page;
	}
	
	@Override
	public BigDecimal getRedMoneyByfixId(FixTenderRealCnd fixTenderRealCnd) throws Exception {
		return fixTenderRealMapper.getRedMoneyByfixId(fixTenderRealCnd);
	}

	@Override
	public int getFixTenderSuccessCountByUserId(int userId) {
		return fixTenderRealMapper.getFixTenderSuccessCountByUserId(userId);
	}
}
