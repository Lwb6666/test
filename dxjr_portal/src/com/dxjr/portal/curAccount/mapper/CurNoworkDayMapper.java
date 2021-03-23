package com.dxjr.portal.curAccount.mapper;

import com.dxjr.portal.curAccount.entity.CurNoworkDay;

public interface CurNoworkDayMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CurNoworkDay record);

	int insertSelective(CurNoworkDay record);

	CurNoworkDay selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CurNoworkDay record);

	int updateByPrimaryKey(CurNoworkDay record);

}