package com.dxjr.base.mapper;

import com.dxjr.base.entity.TodrawLog;

/**
 * 
 * <p>
 * Description:转可提现记录数据访问类<br />
 * </p>
 * 
 * @title BaseTodrawLogMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2014年8月8日
 */
public interface BaseTodrawLogMapper {
	/**
	 * 
	 * <p>
	 * Description:插入记录,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月8日
	 * @param todrawLog
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(TodrawLog todrawLog) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月8日
	 * @param id
	 * @return
	 * @throws Exception
	 *             TodrawLog
	 */
	public TodrawLog queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月8日
	 * @param todrawLog
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(TodrawLog todrawLog) throws Exception;

}
