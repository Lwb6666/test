package com.dxjr.portal.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.member.vo.BankcardTimes;

public interface BankcardTimesMapper {

	BankcardTimes getByUserId(int userId) throws Exception;

	void addTimes(@Param("userId") int userId, @Param("changeTimes") int changeTimes, @Param("applyTimes") int applyTimes, @Param("clickTimes") int clickTimes) throws Exception;

	void insert(int userId);
}
