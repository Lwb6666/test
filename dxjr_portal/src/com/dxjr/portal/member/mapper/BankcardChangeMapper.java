package com.dxjr.portal.member.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.member.vo.BankcardChange;

public interface BankcardChangeMapper {

	int getLastFailApprove(int userId) throws Exception;

	int addChange(BankcardChange change) throws Exception;

	int updateBankinfo(BankcardChange change) throws Exception;

	String kefuEmailList() throws Exception;

	void updateBankinfoStatus(@Param("userId") int userId, @Param("status") int status) throws Exception;

	Date getLastApplyTime(int userId) throws Exception;
}
