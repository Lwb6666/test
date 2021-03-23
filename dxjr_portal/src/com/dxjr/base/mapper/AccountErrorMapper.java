package com.dxjr.base.mapper;

import java.util.List;

import com.dxjr.base.entity.AccountError;

public interface AccountErrorMapper {
   
	public int insert(AccountError accountError);
	
	public List<AccountError> findAccountErrorByUserId(Integer userId);
	
	public int findAccountErrorByUserIdCount(Integer userId);
	
}