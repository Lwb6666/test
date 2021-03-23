package com.dxjr.common.cache;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title AbstractKeyValueCache.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public abstract class KeyValueCache implements IKeyValueCache {
	
	private IKeyValueCache delegate = null;
	
	public KeyValueCache() {
	}
	
	public KeyValueCache(IKeyValueCache delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void put(String key, String value) {
		this.put(key, value, 0);
	}
	
	@Override
	public void put(String key, String value, long expiredTime) {
		if (delegate != null) {
			delegate.put(key, value, expiredTime);
		} else {
			this.selfPut(key, value, expiredTime);
		}
	}

	@Override
	public String get(String key) {
		if (delegate != null) {
			return delegate.get(key);
		}
		return this.selfGet(key);
	}

	@Override
	public boolean isExists(String key) {
		if (delegate != null && delegate.isExists(key)) {
			return true;
		}
		return this.isSelfExists(key);
	}
	
	@Override
	public Long remove(String key) {
		if (delegate != null) {
			return delegate.remove(key);
		}
		return this.selfRemove(key);
	}
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param key
	 * @param value
	 * void
	 */
	protected abstract void selfPut(String key, String value);
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author Administrator
	 * @version 0.1 2016年1月12日
	 * @param key
	 * @param value
	 * @param expiredTime
	 * void
	 */
	protected abstract void selfPut(String key, String value, long expiredTime);
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param key
	 * @return
	 * boolean
	 */
	protected abstract boolean isSelfExists(String key);
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月20日
	 * @param key
	 * @return
	 * String
	 */
	protected abstract String selfGet(String key); 
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月20日
	 * @param key
	 * @return
	 * String
	 */
	protected abstract Long selfRemove(String key); 

}
