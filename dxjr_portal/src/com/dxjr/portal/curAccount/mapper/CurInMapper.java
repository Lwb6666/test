package com.dxjr.portal.curAccount.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.curAccount.entity.CurIn;

public interface CurInMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CurIn record);

	int insertSelective(CurIn record);

	CurIn selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CurIn record);

	int updateByPrimaryKey(CurIn record);

	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询某天内的转入受限总额 <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryNoDrawMoneyTotalByUserIdAndDate(@Param("userId") Integer userId, @Param("date") Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询未开始产生收益的转入记录，并锁定<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             List<CurIn>
	 */
	public List<CurIn> queryCurInListByUserIdAndDateForUpdate(@Param("userId") Integer userId, @Param("date") Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询未开始产生收益的转入记录中有效可扣除金额(用于转出到可用余额) <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryEffectiveMoneyByUserIdAndDateForUseMoney(@Param("userId") Integer userId, @Param("date") Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询未开始产生收益的转入记录中有效可扣除金额(用于投资转出（包含投标、开通直通车、认购债转、
	 * 认购直通车转让、认购定期宝等）) <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryEffectiveMoneyByUserIdAndDateForInvest(@Param("userId") Integer userId, @Param("date") Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:保存活期宝转入信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月6日
	 * @param params
	 *            void
	 */
	public void saveCurrentIn(Map<String, Object> params);
}