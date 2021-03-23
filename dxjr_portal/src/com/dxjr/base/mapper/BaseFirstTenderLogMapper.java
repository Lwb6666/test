package com.dxjr.base.mapper;

import com.dxjr.base.entity.FirstTenderLog;

/**
 * 
 * <p>
 * Description:直通车投标日志记录数据访问类<br />
 * </p>
 * 
 * @title BaseFirstTenderLogMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2015年3月11日
 */
public interface BaseFirstTenderLogMapper {
	/**
	 * 
	 * <p>
	 * Description:插入记录,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLog
	 * @return
	 * @throws Exception Integer
	 */
	public Integer insertEntity(FirstTenderLog firstTenderLog) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param id
	 * @return
	 * @throws Exception FirstTenderLog
	 */
	public FirstTenderLog queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLog
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateEntity(FirstTenderLog firstTenderLog) throws Exception;

}
