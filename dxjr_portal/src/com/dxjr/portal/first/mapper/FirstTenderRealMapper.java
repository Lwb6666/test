package com.dxjr.portal.first.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.portal.first.vo.FirstTenderRealVo;

/**
 * 
 * <p>
 * Description:优先投标计划最终开通记录访问类<br />
 * </p>
 * 
 * @title FirstTenderRealMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author yangshijin
 * @version 0.1 2014年7月24日
 */
public interface FirstTenderRealMapper {

	/**
	 * 
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月24日
	 * @param firstTenderRealCnd
	 * @return
	 * @throws Exception List<FirstTenderRealVo>
	 */
	public List<FirstTenderRealVo> queryFirstTenderRealList(FirstTenderRealCnd firstTenderRealCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月24日
	 * @param firstTenderRealCnd
	 * @return
	 * @throws Exception List<FirstTenderRealVo>
	 */
	public List<FirstTenderRealVo> queryFirstTenderRealList(FirstTenderRealCnd firstTenderRealCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月24日
	 * @param firstTenderRealCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryFirstTenderRealCount(FirstTenderRealCnd firstTenderRealCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据优先计划ID统计该资金池剩余总金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月24日
	 * @param firstBorrowId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryRemainAmountForTenderReal(int firstBorrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id,用户id更新待收表中的优先投标计划为失效<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月21日
	 * @param firstBorrowId
	 * @param memberId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateCollectionFirstToInvalid(@Param("firstTenderRealId") Integer firstTenderRealId, @Param("memberId") Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某期某人有效认购总额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年12月10日
	 * @param firstBorrowId
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer findAccountTotalByUserIdAndFirstBorrowId(@Param("firstBorrowId") Integer firstBorrowId, @Param("userId") Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取当前最大的排队序列号<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月10日
	 * @return
	 * @throws Exception Integer
	 */
	public Integer getMaxOrderNumByFirstTenderReal() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据已解锁的最终记录id查询已获利息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月18日
	 * @param firstTenderRealId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal getIncomeForLockedByRealId(Integer firstTenderRealId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据未解锁的最终记录id查询已获利息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月18日
	 * @param firstTenderRealId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal getIncomeForUnlockedByRealId(Integer firstTenderRealId) throws Exception;
}
