package com.dxjr.base.mapper;

import com.dxjr.base.entity.Staff;

/**
 * <p>
 * Description:员工数据访问类<br />
 * </p>
 * 
 * @title BaseStaffMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface BaseStaffMapper {
	/**
	 * <p>
	 * Description:插入记录到,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param staff
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(Staff staff) throws Exception;

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
	 *             Staff
	 */
	public Staff queryById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param staff
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(Staff staff) throws Exception;

}
