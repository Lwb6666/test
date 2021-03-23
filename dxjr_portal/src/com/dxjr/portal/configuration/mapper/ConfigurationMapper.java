package com.dxjr.portal.configuration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.Configuration;

public interface ConfigurationMapper {
	
	List<Configuration> selectAll();
	
	public Configuration selectByTypeAndValue(@Param("type") Integer type, @Param("value") String value);
}