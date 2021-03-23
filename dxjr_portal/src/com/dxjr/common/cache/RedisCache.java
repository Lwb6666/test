package com.dxjr.common.cache;

import org.springframework.stereotype.Component;

import com.dxjr.common.CacheService;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title RedisCache.java
 * @package com.dxjr.common.cache
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
@Component("redisCache")
public class RedisCache extends KeyValueCache {

	private CacheService cacheService = CacheService.getInstance();

	public RedisCache() {
	}

	public RedisCache(IKeyValueCache delegate) {
		super(delegate);
	}

	@Override
	public Long selfRemove(String key) {
		if (key != null && key.length() > 0) {
			return cacheService.remove(key);
		}
		return 0L;
	}

	@Override
	protected void selfPut(String key, String value) {
		if (key != null && key.length() > 0) {
			cacheService.put(key, value);
		}
	}

	@Override
	protected void selfPut(String key, String value, long expiredTime) {
		if (key != null && key.length() > 0) {
			cacheService.put(key, value);
			cacheService.expire(key, new Long(expiredTime).intValue() / 1000);

		}
	}

	@Override
	protected boolean isSelfExists(String key) {
		return cacheService.existKey(key);
	}

	@Override
	protected String selfGet(String key) {
		return cacheService.getString(key);
	}

}
