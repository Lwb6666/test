package com.dxjr.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CacheProxyFactory.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
@Component("cacheProxyFactory")
public final class CacheProxyFactory {
	
	private static CacheProxy cacheProxy;
	
	private CacheProxyFactory() {
	}
	
	public CacheProxy getCacheProxy() {
		return cacheProxy;
	}
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @return
	 * CacheProxy
	 */
	public static CacheProxy getInstance() {
		if (cacheProxy == null) {
			synchronized (CacheProxyFactory.class) {
				if (cacheProxy == null) {
					cacheProxy = new CacheProxy();
				}
			}
		}
		return cacheProxy;
	}

	@Autowired
	public void setCacheProxy(CacheProxy cacheProxy) {
		CacheProxyFactory.cacheProxy = cacheProxy;
	}
}
