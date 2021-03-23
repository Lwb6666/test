package com.dxjr.portal.fix.mapper;


import com.dxjr.base.entity.FixExit;

import java.math.BigDecimal;


/**
 * <p>
 * Description:定期宝提前退出接口类<br />
 * </p>
 * 
 * @title FixExitMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author wangwanbin
 * @version 0.1 2016年6月21日
 */
public interface FixExitMapper {
	/**
	 * 统计当日申请退出总额
	 */
	BigDecimal countTodayApplyExitTotalForUpdate();

	int insertFixExit(FixExit fixExit);

}
