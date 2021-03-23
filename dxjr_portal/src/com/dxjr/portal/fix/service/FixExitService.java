package com.dxjr.portal.fix.service;

import com.dxjr.portal.base.MessageBox;

/**
 * <p>
 * Description:定期宝认购明接口类<br />
 * </p>
 * 
 * @title FixTenderDetailService.java
 * @package com.dxjr.portal.fix1.service
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixExitService {

	MessageBox checkExit(Integer fixTenderRealId) throws Exception;

	MessageBox applyExit(Integer fixTenderRealId,String ip,Integer platform) throws Exception;
}
