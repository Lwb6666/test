package com.dxjr.base.mapper;

import com.dxjr.base.entity.BorrowAdvanceLog;

public interface BaseBorrowAdvanceLogMapper {

	/**
	 * 
	 * <p>
	 * Description:插入记录到帐号表,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param borrowAdvanceLog
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(BorrowAdvanceLog borrowAdvanceLog) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param id
	 * @return
	 * @throws Exception
	 *             BorrowAdvanceLog
	 */
	public BorrowAdvanceLog queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param borrowAdvanceLog
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(BorrowAdvanceLog borrowAdvanceLog) throws Exception;
}
