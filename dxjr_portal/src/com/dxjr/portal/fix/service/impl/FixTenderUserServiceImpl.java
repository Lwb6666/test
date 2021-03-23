package com.dxjr.portal.fix.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.mapper.FixTenderUserMapper;
import com.dxjr.portal.fix.service.FixTenderUserService;
import com.dxjr.portal.fix.vo.FixTenderUserCnd;
import com.dxjr.portal.fix.vo.FixTenderUserVo;

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
public class FixTenderUserServiceImpl implements FixTenderUserService {
	
	
	Logger logger = LoggerFactory.getLogger(FixTenderUserServiceImpl.class);
	@Autowired
	private FixTenderUserMapper fixTenderUserMapper;
	@Override
	public FixTenderUserVo getFixTenderDetailStaticByBorrowId(FixTenderUserCnd fixTenderUserCnd) {
		return fixTenderUserMapper.getFixTenderDetailStaticByBorrowId(fixTenderUserCnd);
	}

	@Override
	public Integer queryTotalTBCounts(FixTenderUserCnd fixTenderUserCnd) {
		return fixTenderUserMapper.queryTotalTBCounts(fixTenderUserCnd);
	}

	@Override
	public Page queryTotalTBByPage(FixTenderUserCnd fixTenderUserCnd, Page page) {
		int totalCount = 0;
		List<FixTenderUserVo> retLst = null;
		try {
			totalCount = fixTenderUserMapper.queryTotalTBCounts(fixTenderUserCnd);
		    //判断总记录数大于0
			if(totalCount>0)
		    {
		    	page.setTotalCount(totalCount);
		    }
			
			retLst = fixTenderUserMapper.queryTotalTBByPage(fixTenderUserCnd, page);
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
	
 

}
