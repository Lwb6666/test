package com.dxjr.wx.favorite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.invest.mapper.InvestRecordMapper;
import com.dxjr.wx.favorite.mapper.TenderMapper;
import com.dxjr.wx.favorite.service.TenderService;
import com.dxjr.wx.favorite.vo.BidCountVo;

@Service
public class TenderServiceImpl implements TenderService {

	@Autowired
	private TenderMapper tenderMapper;
	@Autowired
	private InvestRecordMapper investRecordMapper;
	public List<BorrowVo> bidList(Page p) throws Exception {
		SearchInvestCnd searchInvestCnd=new SearchInvestCnd();
		searchInvestCnd.setOrderType("asc");
		searchInvestCnd.setLimitTime("isTendering");
		searchInvestCnd.setExcludeJinzhi(1);
		searchInvestCnd.setIsShowNewBorrow(1);
		List<BorrowVo> list = investRecordMapper.queryInvestRecord(searchInvestCnd, p);
		list = BorrowVo.handleBorrow(list);
		return list;
	}

	public BidCountVo bidCount() throws Exception {
		return tenderMapper.bidCount();
	}

	public Integer tenderBidCount() throws Exception {
		return tenderMapper.tenderBidCount();
	}
}
