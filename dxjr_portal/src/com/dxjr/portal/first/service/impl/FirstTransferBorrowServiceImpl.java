package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.service.FirstTransferBorrowService;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferBorrowVo;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细业务实现类<br />
 * </p>
 * 
 * @title FirstTransferBorrowServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author justin.xu
 * @version 0.1 2015年3月18日
 */
@Service
public class FirstTransferBorrowServiceImpl implements FirstTransferBorrowService {

	// 日志类取得
	Logger logger = LoggerFactory.getLogger(FirstTransferBorrowServiceImpl.class);

	@Autowired
	private FirstTransferBorrowMapper firstTransferBorrowMapper;

	@Override
	public Page queryCanTransferBorrowList(FirstTransferBorrowCnd firstTransferBorrowCnd, Page page) throws Exception {
		// 查询可转让直通车总件数
		Integer count = firstTransferBorrowMapper.queryCanTransferBorrowCount(firstTransferBorrowCnd);
		// 分页查询可转让直通车信息
		List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper.queryCanTransferBorrowList(firstTransferBorrowCnd, page);
		page.setTotalCount(count);
		page.setResult(firstTransferBorrowList);
		return page;
	}

	@Override
	public Page queryTransferBorrowListByTransferId(Integer transferId, Page page) throws Exception {
		// 获得转让资金明细信息件数
		Integer totalCount = firstTransferBorrowMapper.queryTransferBorrowListCount(transferId);
		page.setTotalCount(totalCount);
		// 获得转让资金明细信息
		List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper.queryTransferBorrowListByTransferId(transferId, page);
		page.setResult(firstTransferBorrowList);
		return page;
	}

	@Override
	public BigDecimal queryBorrowCollectionAccountSum(Integer transferId) throws Exception {
		return firstTransferBorrowMapper.queryBorrowCollectionAccountSum(transferId);
	}

	@Override
	public BigDecimal queryCanTransferBorrowAccountSum(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception {
		return firstTransferBorrowMapper.queryCanTransferBorrowAccountSum(firstTransferBorrowCnd);
	}

}
