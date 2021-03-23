package com.dxjr.base.mapper;

import com.dxjr.base.entity.VIPApply;

/**
 * 
 * <p>
 * Description:VIP申请数据访问类<br />
 * </p>
 * 
 * @title BaseVIPApplyMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2014年8月7日
 */
public interface BaseVIPApplyMapper {
	/**
	 * 
	 * <p>
	 * Description:插入记录到,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月7日
	 * @param vipApply
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(VIPApply vipApply) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月7日
	 * @param id
	 * @return
	 * @throws Exception
	 *             VIPApply
	 */
	public VIPApply queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月7日
	 * @param vipApply
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(VIPApply vipApply) throws Exception;

}
