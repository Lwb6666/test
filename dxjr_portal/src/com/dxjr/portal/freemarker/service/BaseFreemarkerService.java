package com.dxjr.portal.freemarker.service;

import java.util.Map;

/**
 * <p>
 * Description: 模板文件 操作 <br />
 * </p>
 * 
 * @title BaseFreemarkerService.java
 * @package com.dxjr.portal.freemarker.service
 * @author HuangJun
 * @version 0.1 2015年6月9日
 */
public interface BaseFreemarkerService {

	/**
	 * <p>
	 * Description:通过模板产生邮件正文<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年11月19日
	 * @param templateName
	 *            邮件模板名称
	 * @param map
	 *            模板中要填充的对象
	 * @return String
	 */
	public String generateEmailContentByFreeMarker(String templateName, Map<String, Object> map) throws Exception;

	/**
	 * <p>
	 * Description: 通过模版生成html文件 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年6月3日
	 * @param templateName
	 *            模版路径名称
	 * @param map
	 *            参数
	 * @param targetPath
	 *            完整的目标路径名称
	 * @throws Exception
	 *             void void
	 */
	public void createHtml(String templateName, Map<String, Object> map, String targetPath) throws Exception;

}
