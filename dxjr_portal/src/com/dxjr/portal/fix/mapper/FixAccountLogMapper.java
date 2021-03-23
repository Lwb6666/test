package com.dxjr.portal.fix.mapper;

import com.dxjr.base.entity.FixAccountLog;
import com.dxjr.portal.fix.vo.FixAccountLogVo;

/**
 * <p>
 * Description:定期宝账户日志数据库接口类<br />
 * </p>
 * 
 * @title FixAccountLogMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public interface FixAccountLogMapper {
	/**
	 * 添加账户日志
	 * @param fixAccountLog
	 */
	public void insertFixAccountLog(FixAccountLog fixAccountLog) throws Exception;
	
	/**
	 * 添加账户Vo日志
	 * @author WangQianJin
	 * @title @param fixAccountLogVo
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public void insertFixAccountVoLog(FixAccountLogVo fixAccountLogVo) throws Exception;
	
}
