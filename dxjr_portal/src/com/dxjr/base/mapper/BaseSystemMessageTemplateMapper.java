package com.dxjr.base.mapper;

import com.dxjr.base.entity.SystemMessageTemplate;

/**
 * <p>
 * Description:系统站内信模板数据访问类<br />
 * </p>
 * 
 * @title BaseSystemMessageTemplateMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public interface BaseSystemMessageTemplateMapper {
	/**
	 * <p>
	 * Description:插入记录到,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param account
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(SystemMessageTemplate systemMessageTemplate)
			throws Exception;

	/**
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param id
	 * @return
	 * @throws Exception
	 *             SystemMessageTemplate
	 */
	public SystemMessageTemplate queryById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据type查询有效的系统站内信模板数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param id
	 * @return
	 * @throws Exception
	 *             SystemMessageTemplate
	 */
	public SystemMessageTemplate queryByType(Integer type) throws Exception;

	/**
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param account
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(SystemMessageTemplate systemMessageTemplate)
			throws Exception;

}
