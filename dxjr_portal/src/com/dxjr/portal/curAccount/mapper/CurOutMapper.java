package com.dxjr.portal.curAccount.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.curAccount.entity.CurOut;

public interface CurOutMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CurOut record);

	int insertSelective(CurOut record);

	CurOut selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CurOut record);

	int updateByPrimaryKey(CurOut record);

	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询转出到普通可用余额的记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryCountByUserIdAndDate(@Param("userId") Integer userId, @Param("date") Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId和type查询活期宝最新一条转出记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param type
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 *             CurOut
	 */
	public CurOut queryCurOutLastByUserIdAndType(@Param("type") Integer type, @Param("userId") Integer userId, @Param("account") BigDecimal account)
			throws Exception;
}