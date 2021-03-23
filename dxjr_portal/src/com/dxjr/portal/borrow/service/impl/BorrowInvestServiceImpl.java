package com.dxjr.portal.borrow.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.service.BorrowInvestService;
import com.dxjr.portal.borrow.vo.BorrowCnd;

@Service
@WebService
public class BorrowInvestServiceImpl implements BorrowInvestService {
	@Autowired
	private BorrowMapper borrowMapper;

	@Override
	public Page findInverst(BorrowCnd borrowCnd, int curPage, int pageSize) throws Exception{
		Page page = new Page(curPage, pageSize);
		if("default".equals(borrowCnd.getSearchType()) || borrowCnd.getSearchType() ==null || "".equals(borrowCnd.getSearchType())){  //招标中的列表
			page.setTotalCount(borrowMapper.queryBorrowForDefaultCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForDefault(borrowCnd, page));
		}else if("advance".equals(borrowCnd.getSearchType())){  //预发标列表
			int i = borrowMapper.queryBorrowForAdvanceCount(borrowCnd);
			page.setTotalCount(i);	
			page.setResult(borrowMapper.queryBorrowForAdvance(borrowCnd, page));
		}else if("complete".equals(borrowCnd.getSearchType())){  //完成列表
			page.setTotalCount(borrowMapper.queryBorrowForCompleteCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForComplete(borrowCnd, page));
		}else if("completeDYB".equals(borrowCnd.getSearchType())){  //完成的抵押标
			page.setTotalCount(borrowMapper.queryBorrowForCompleteDYBCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForCompleteDYB(borrowCnd, page));
		}else if("completeJZB".equals(borrowCnd.getSearchType())){  //完成的净值标
			page.setTotalCount(borrowMapper.queryBorrowForCompleteJZBCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForCompleteJZB(borrowCnd, page));
		}else if("completeMB".equals(borrowCnd.getSearchType())){   //完成的秒标
			page.setTotalCount(borrowMapper.queryBorrowForCompleteMBCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForCompleteMB(borrowCnd, page));
		}else if("completeTJB".equals(borrowCnd.getSearchType())){  //完成的推荐标
			page.setTotalCount(borrowMapper.queryBorrowForCompleteTJBCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForCompleteTJB(borrowCnd, page));
		}else if("completeDBB".equals(borrowCnd.getSearchType())){  //担保标列表
			page.setTotalCount(borrowMapper.queryBorrowForCompleteDBBCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForCompleteDBB(borrowCnd, page));
		}else if("overdue".equals(borrowCnd.getSearchType())){  //逾期列表
			page.setTotalCount(borrowMapper.queryBorrowForOverdueCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForOverdue(borrowCnd, page));
		}else if("list".equals(borrowCnd.getSearchType())){  //借款列表
			page.setTotalCount(borrowMapper.queryBorrowForListCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForList(borrowCnd, page));
		}else {
			page.setTotalCount(borrowMapper.queryBorrowForListCount(borrowCnd));
			page.setResult(borrowMapper.queryBorrowForList(borrowCnd, page));
		}
		return page;
	}

}
