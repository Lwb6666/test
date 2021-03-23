package com.dxjr.portal.configuration.service;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.dxjr.base.entity.Configuration;

public interface ConfigurationService {
	/**
	 * 
	 * <p>
	 * Description:查询所有记录<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年4月22日
	 * @param example
	 * @return List<Configuration>
	 */
	HashMap<Integer, LinkedHashMap<String, Configuration>> queryAllConfigurations();
}
