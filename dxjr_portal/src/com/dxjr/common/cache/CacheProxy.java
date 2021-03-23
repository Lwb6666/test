package com.dxjr.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CacheProxy.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
@Component
public class CacheProxy implements IKeyValueCache {
	
	public CacheProxy cacheProxy;

	@Autowired
	@Qualifier("md5KeyGenerator")
	private IKeyGenerator generator;
	
	@Autowired
	@Qualifier("redisCache")
	private IKeyValueCache cache;
	
	public CacheProxy() {
	}
	
	public CacheProxy(IKeyValueCache cache) {
		this.cache = cache;
	}

	@Override
	public void put(String key, String value) {
		put(key, value, 0);
	}
	
	@Override
	public void put(String key, String value, long expiredTime) {
		if (generator != null) {
			key = generator.generate(key);
		}
		if (cache != null)  {
			cache.put(key, value, expiredTime);
		}
	}

	@Override
	public String get(String key) {
		if (generator != null) {
			key = generator.generate(key);
		}
		if (cache != null)  {
			return cache.get(key);
		}
		return null;
	}

	@Override
	public boolean isExists(String key) {
		if (generator != null) {
			key = generator.generate(key);
		}
		if (cache != null)  {
			return cache.isExists(key);
		}
		return false;
	}

	@Override
	public Long remove(String key) {
		if (generator != null) {
			key = generator.generate(key);
		}
		if (cache != null)  {
			return cache.remove(key);
		}
		return 0L;
	}

}
