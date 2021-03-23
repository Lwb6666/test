package com.dxjr.common.cache;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title IKeyGenerator.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public interface IKeyGenerator {
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhaowei
	 * @version 0.1 2015年11月19日
	 * @param rawStr
	 * @return
	 * String
	 */
	public String generate(String rawStr);
}
