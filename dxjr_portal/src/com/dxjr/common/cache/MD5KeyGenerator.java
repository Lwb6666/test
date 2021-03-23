package com.dxjr.common.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title MD5KeyGenerator.java
 * @package com.dxjr.common.cache 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
@Component("md5KeyGenerator")
public class MD5KeyGenerator implements IKeyGenerator {

	@Override
	public String generate(String rawStr) {
		if (rawStr != null) {
			return DigestUtils.md5Hex(rawStr);
		}
		return null;
	}
	
}
