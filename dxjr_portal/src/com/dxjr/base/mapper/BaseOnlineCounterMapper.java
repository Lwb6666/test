package com.dxjr.base.mapper;

import com.dxjr.base.entity.OnlineCounter;

/**
 * <p>
 * Description:在线人数记录数据访问类<br />
 * </p>
 * 
 * @title OnlineCounterMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public interface BaseOnlineCounterMapper {
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
	public int insertEntity(OnlineCounter onlineCounter) throws Exception;

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
	public OnlineCounter queryById(Integer id) throws Exception;

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
	public int updateEntity(OnlineCounter onlineCounter) throws Exception;

}
