package com.dxjr.portal.member.mapper;

import java.util.List;

import com.dxjr.portal.member.vo.StaffCnd;
import com.dxjr.portal.member.vo.StaffVo;

/**
 * <p>
 * Description:员工数据访问类<br />
 * </p>
 * 
 * @title StaffMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface StaffMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param staffCnd
	 * @return
	 * @throws Exception
	 *             List<StaffVo>
	 */
	public List<StaffVo> queryStaffList(StaffCnd staffCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param StaffCnd
	 *            staffCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryStaffCount(StaffCnd staffCnd) throws Exception;
}
