package com.dxjr.portal.repayment.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.repayment.mapper.BRepaymentRecordMapper;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.repayment.vo.RepaymentBorrowVo;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:待还记录业务实现类<br />
 * </p>
 * 
 * @title BRepaymentRecordServiceImpl.java
 * @package com.dxjr.portal.repayment.service.impl
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
@Service
public class BRepaymentRecordServiceImpl implements BRepaymentRecordService {

	@Autowired
	private BRepaymentRecordMapper bRepaymentRecordMapper;

	@Override
	public Page queryRepaymentsByUserId(Integer userId, Page page) throws Exception {
		Integer totalCount = bRepaymentRecordMapper.selectRepaymentsCount(userId);
		page.setTotalCount(totalCount);
		List<RepaymentBorrowVo> list = bRepaymentRecordMapper.selectRepayments(userId, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Page queryRepaymentList(RepaymentRecordCnd repaymentRecordCnd, int pageNo, int pageSize) throws Exception {
		if (repaymentRecordCnd.getBeginTime() != null && !repaymentRecordCnd.getBeginTime().equals("")) {
			repaymentRecordCnd.setBeginTime(DateUtils.dateTime2TimeStamp(repaymentRecordCnd.getBeginTime() + " 00:00:00"));
		}
		if (repaymentRecordCnd.getEndTime() != null && !repaymentRecordCnd.getEndTime().equals("")) {
			repaymentRecordCnd.setEndTime(DateUtils.dateTime2TimeStamp(repaymentRecordCnd.getEndTime() + " 23:59:59"));
		}
		Page page = new Page(pageNo, pageSize);
		int totalCount = this.queryRepaymentCount(repaymentRecordCnd);
		page.setTotalCount(totalCount);
		List<BRepaymentRecordVo> list = bRepaymentRecordMapper.queryRepaymentList(repaymentRecordCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public int queryRepaymentCount(RepaymentRecordCnd repaymentRecordCnd) throws Exception {
		int count = bRepaymentRecordMapper.queryRepaymentCount(repaymentRecordCnd);
		return count;
	}

	@Override
	public BigDecimal queryRepaymentAccountTotalByBorrowId(Map<String, Object> map) throws Exception {

		BigDecimal repaymentAmount = bRepaymentRecordMapper.queryRepaymentAccountTotal(map);
		if (repaymentAmount == null) {
			repaymentAmount = BigDecimal.ZERO;
		}
		return repaymentAmount;
	}

	public Page querySums(Map<String, Object> map, Page page) throws Exception {

		List<BRepaymentRecordVo> list = bRepaymentRecordMapper.querySums(map, page);

		page.setResult(list);

		return page;
	}

	@Override
	public Map<String, Object> repaymentRecordTongji(RepaymentRecordCnd repaymentRecordCnd) throws Exception {
		Map<String, Object> map = bRepaymentRecordMapper.repaymentRecordTongji(repaymentRecordCnd);
		if (map == null) {
			map = new HashMap<String, Object>();
			map.put("repaymentAccountTotal", "0");
			map.put("capitalTotal", "0");
			map.put("interestTotal", "0");
			map.put("lateInterestTotal", "0");
		} else {
			if (map.get("repaymentAccountTotal") == null || map.get("repaymentAccountTotal").equals("")) {
				map.put("repaymentAccountTotal", "0");
			}
			if (map.get("capitalTotal") == null || map.get("capitalTotal").equals("")) {
				map.put("capitalTotal", "0");
			}
			if (map.get("interestTotal") == null || map.get("interestTotal").equals("")) {
				map.put("interestTotal", "0");
			}
			if (map.get("lateInterestTotal") == null || map.get("lateInterestTotal").equals("")) {
				map.put("lateInterestTotal", "0");
			}
		}
		return map;
	}
	
	/**
	 * 根据借款标ID查询待还记录
	 * @author WangQianJin
	 * @title @param borrowId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月19日
	 */
	public List<BRepaymentRecordVo> queryRepaymentByBorrowId(Integer borrowId) throws Exception {
		return bRepaymentRecordMapper.queryBRepaymentRecordByBorrowId(borrowId);
	}
}
