package com.dxjr.common.cache;

import java.util.Map;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title InMemoryCache.java
 * @package com.dxjr.common.cache
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public class InMemoryCache extends KeyValueCache {
	
	private final static CacheMap<String, String> memoryMap = new CacheMap<String, String>(3000);
	
	public InMemoryCache() {
	}

	public InMemoryCache(IKeyValueCache delegate) {
		super(delegate);
	}

	@Override
	protected void selfPut(String key, String value) {
		this.put(key, value, 0);

	}
	
	@Override
	protected void selfPut(String key, String value, long expiredTime) {
		memoryMap.put(key, value, expiredTime);
	}

	@Override
	protected boolean isSelfExists(String key) {
		return memoryMap.containsKey(key);
	}

	public static Map<String, String> getMemoryMap() {
		return memoryMap;
	}

	@Override
	protected String selfGet(String key) {
		return memoryMap.get(key);
	}

	@Override
	protected Long selfRemove(String key) {
		return memoryMap.remove(key) != null ? 1L : 0L;
	}

}
