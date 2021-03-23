package com.dxjr.common.cache;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title IKeyValueCache.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public interface IKeyValueCache extends ICacheable {
	
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
	public void put(String key, String value);
	
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
	public void put(String key, String value, long expiredTime);
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param key
	 * @return
	 * String
	 */
	public String get(String key);
	
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
	public boolean isExists(String key);
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param key
	 * void
	 */
	public Long remove(String key);
	
}
