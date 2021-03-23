package com.dxjr.portal.fix.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.mapper.FixTenderDetailMapper;
import com.dxjr.portal.fix.service.FixTenderDetailService;
import com.dxjr.portal.fix.vo.FixTenderDetailCnd;
import com.dxjr.portal.fix.vo.FixTenderDetailVo;

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
public class FixTenderDetailServiceImpl implements FixTenderDetailService {
	Logger logger = LoggerFactory.getLogger(FixTenderDetailServiceImpl.class);
	@Autowired
	private FixTenderDetailMapper fixTenderDetailMapper;
	
	@Override
	public List<FixTenderDetailVo> querySumAccountGroupByUser (
			Integer fixBorrowId) throws Exception {
		return fixTenderDetailMapper.querySumAccountGroupByUser(fixBorrowId);
	}

	
	@Override
	public Integer queryTotalJoinCounts() throws Exception {
	 
		return fixTenderDetailMapper.queryTotalJoinCounts();
	}

 
 

	public Page queryTotalJoinInfoByPage(FixTenderDetailCnd fixTenderDetailCnd, Page page) throws Exception {

		int totalCount = 0;
		List<FixTenderDetailVo> retLst = null;
		try {
			totalCount = fixTenderDetailMapper.queryTotalJoinInfoByUserCounts(fixTenderDetailCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderDetailMapper.queryTotalJoinInfoByUser(fixTenderDetailCnd, page);
			//判断结果对象不为空且记录数大于0
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
	public Page queryFixTenderDetailVoList(Integer fixBorrowId, Page page) throws Exception {
		Integer totalCounts =fixTenderDetailMapper.queryFixTenderDetailVoCounts(fixBorrowId); 
		page.setTotalCount(totalCounts);
		List<FixTenderDetailVo> list = fixTenderDetailMapper.queryFixTenderDetailVoList(fixBorrowId, page);
		page.setResult(list);
		return page;
	}


	@Override
	public void insertFixTenderDetail(FixTenderDetail fixTenderDetail) throws Exception {
		fixTenderDetailMapper.insertFixTenderDetail(fixTenderDetail);
	}

 
	
	
	public Page getFixTenderDetailByBorrowIdByPage(FixTenderDetailCnd fixTenderDetailCnd, Page page) throws Exception{

		int totalCount = 0;
		List<FixTenderDetailVo> retLst = null;
		try {
			totalCount = fixTenderDetailMapper.queryTotalJoinInfoByUserCounts(fixTenderDetailCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderDetailMapper.queryTotalJoinInfoByUser(fixTenderDetailCnd, page);
			//判断结果对象不为空且包含记录数大于0
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
	public List<FixTenderDetailVo> getFixTenderDetailByBorrowId(FixTenderDetailCnd fixTenderDetailCnd) throws Exception {
		return fixTenderDetailMapper.getFixTenderDetailByBorrowId(fixTenderDetailCnd);
	}


	@Override
	public FixTenderDetailVo getFixTenderDetailStaticByBorrowId(FixTenderDetailCnd fixTenderDetailCnd)  throws Exception{
		return fixTenderDetailMapper.getFixTenderDetailStaticByBorrowId(fixTenderDetailCnd);
	}


	@Override
	public Page queryJoinInfoByPage(FixTenderDetailCnd fixTenderDetailCnd, Page page) throws Exception {

		int totalCount = 0;
		List<FixTenderDetailVo> retLst = null;
		try {
			totalCount = fixTenderDetailMapper.queryJoinInfoByUserCounts(fixTenderDetailCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderDetailMapper.queryJoinInfoByUser(fixTenderDetailCnd, page);
			//判断结果对象不为空且包含记录数大于0
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
	public BigDecimal getYqsyByJrz(FixTenderDetailCnd fixTenderDetailCnd) throws Exception {
		BigDecimal result  =fixTenderDetailMapper.getYqsyByJrz(fixTenderDetailCnd);
		//判断定期宝加入中的预期收益不为空
		if(result!=null){
			return result;
		}
		return BigDecimal.ZERO;
	}
	
	
	@Override
	public Integer getUserAccount(Map map) throws Exception {
		Integer result  =fixTenderDetailMapper.getUserAccount(map);
		if(result!=null){
			return result;
		}
		return Integer.valueOf(0);
	}

 

}
