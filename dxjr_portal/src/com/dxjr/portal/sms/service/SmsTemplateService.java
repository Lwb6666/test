package com.dxjr.portal.sms.service;

import com.dxjr.portal.sms.vo.SmsTemplateVo;

/**
 * <p>
 * Description:短信模板业务类<br />
 * </p>
 * 
 * @title SmsTemplateService.java
 * @package com.dxjr.portal.sms.service
 * @author justin.xu
 * @version 0.1 2014年4月29日
 */
public interface SmsTemplateService {
	/**
	 * <p>
	 * Description:根据类型查询有效模板<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月29日
	 * @param type
	 * @return
	 * @throws Exception
	 *             SmsTemplate
	 */
	public SmsTemplateVo querySmsTemplateByType(Integer type) throws Exception;
}
