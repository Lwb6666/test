package com.dxjr.portal.borrow.mapper;


import com.dxjr.portal.borrow.entity.BorrowerDealError;

public interface BorrowerDealErrorMapper {
   
	public Integer insert(BorrowerDealError borrowerDealError);
	
	public BorrowerDealError selectByPrimaryKey(Integer id);
	
	public Integer findBorrowErrorCount(Integer borrowId);
	
}