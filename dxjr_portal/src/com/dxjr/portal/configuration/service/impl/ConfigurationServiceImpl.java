package com.dxjr.portal.configuration.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Configuration;
import com.dxjr.portal.configuration.mapper.ConfigurationMapper;
import com.dxjr.portal.configuration.service.ConfigurationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

	@Autowired
	private ConfigurationMapper configurationMapper;

	@Cacheable(value = "queryAllConfigurations")
	public HashMap<Integer, LinkedHashMap<String, Configuration>> queryAllConfigurations() {
		List<Configuration> configurations = configurationMapper.selectAll();

		HashMap<Integer, LinkedHashMap<String, Configuration>> ret = new HashMap<Integer, LinkedHashMap<String, Configuration>>();
		Integer type = null;
		for (Configuration configuration : configurations) {
			if (!ret.containsKey(type = configuration.getType())) {
				ret.put(type, new LinkedHashMap<String, Configuration>());
			}
			ret.get(type).put(configuration.getName(), configuration);
		}

		return ret;
	}
}
