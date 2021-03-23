package com.dxjr.base.mapper;

import com.dxjr.base.entity.SystemMessage;

/**
 * <p>
 * Description:系统站内信数据访问类<br />
 * </p>
 * 
 * @title BaseSystemMessageMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public interface BaseSystemMessageMapper {
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
	public int insertEntity(SystemMessage systemMessage) throws Exception;

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
	 *             OnlineCounter
	 */
	public SystemMessage queryById(Integer id) throws Exception;

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
	public int updateEntity(SystemMessage systemMessage) throws Exception;

}
