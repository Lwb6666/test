package com.dxjr.common.cache;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存缓存Map
 * <p>
 * Description:数据存储在容器内存中<br />
 * </p>
 * 
 * @title CacheMap.java
 * @package com.dxjr.common.cache
 * @author zhaowei
 * @version 0.1 2016年1月12日
 */
public class CacheMap<K, V> extends AbstractMap<K, V> {

	private static final long DEFAULT_INTERVAL_TIME = 3000;
	private static CacheMap<Object, Object> defaultInstance;

	public static synchronized final CacheMap<Object, Object> getDefault() {
		if (defaultInstance == null) {
			defaultInstance = new CacheMap<Object, Object>(DEFAULT_INTERVAL_TIME);
		}
		return defaultInstance;
	}

	private class CacheEntry implements Entry<K, V> {
		long time;
		V value;
		K key;
		long expiredTime = 0L;

		CacheEntry(K key, V value) {
			this(key, value, 0);
		}

		CacheEntry(K key, V value, long expiredTime) {
			super();
			this.value = value;
			this.key = key;
			this.time = System.currentTimeMillis();
			this.expiredTime = expiredTime;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}
	}

	private class ClearThread extends Thread {
		
		ClearThread() {
			setName("clear cache thread");
		}

		public void run() {
			while (true) {
				try {
					long now = System.currentTimeMillis();
					Object[] keys = map.keySet().toArray();
					for (Object key : keys) {
						CacheEntry entry = map.get(key);
						if (entry.expiredTime > 0 && now - entry.time >= entry.expiredTime) {
							synchronized (map) {
								map.remove(key);
							}
						}
					}
					Thread.sleep(intervalTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private long intervalTime;
	private Map<K, CacheEntry> map = new ConcurrentHashMap<K, CacheEntry>();

	public CacheMap(long intervalTime) {
		this.intervalTime = intervalTime;
		new ClearThread().start();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new HashSet<Map.Entry<K, V>>();
		Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
		for (Entry<K, CacheEntry> entry : wrapEntrySet) {
			entrySet.add(entry.getValue());
		}
		return entrySet;
	}

	@Override
	public V get(Object key) {
		CacheEntry entry = map.get(key);
		return entry == null ? null : entry.value;
	}

	@Override
	public V put(K key, V value) {
		CacheEntry entry = new CacheEntry(key, value);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}

	public V put(K key, V value, long expiredTime) {
		CacheEntry entry = new CacheEntry(key, value, expiredTime);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}

}