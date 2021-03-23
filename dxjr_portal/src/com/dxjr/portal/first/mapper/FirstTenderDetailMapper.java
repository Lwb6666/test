package com.dxjr.portal.first.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.first.vo.FirstTenderDetailVo;

/**
 * <p>
 * Description:优先投标计划开通明细数据访问类<br />
 * </p>
 * 
 * @title FirstTenderDetailMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2014年7月15日
 */
public interface FirstTenderDetailMapper {
	/**
	 * <p>
	 * Description:根据条件分页查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param firstTenderDetailCnd
	 * @param page
	 * @return
	 * @throws Exception List<FirstTenderDetailVo>
	 */
	public List<FirstTenderDetailVo> queryFirstTenderDetailList(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param firstTenderDetailCnd
	 * @param page
	 * @return
	 * @throws Exception List<FirstTenderDetailVo>
	 */
	public List<FirstTenderDetailVo> queryFirstTenderDetailList(FirstTenderDetailCnd firstTenderDetailCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param firstTenderDetailCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryFirstTenderDetailCount(FirstTenderDetailCnd firstTenderDetailCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询优先计划开通明细信息,投标直通车列表——查看明细<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param firstTenderDetailCnd
	 * @return
	 * @throws Exception FirstTenderDetailVo
	 */
	public List<FirstTenderDetailVo> queryFirstTenderDetail(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计优先计划开通明细信息,投标直通车列表——查看明细总数<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param firstTenderDetailCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer countFirstTenderDetail(FirstTenderDetailCnd firstTenderDetailCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id找到投标明细中的用户帐号<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月17日
	 * @param firstBorrowId
	 * @return List<AccountVo>
	 */
	public List<AccountVo> queryAccountListForUpdateByFirstId(Integer firstBorrowId);

	/**
	 * <p>
	 * Description:根据直通车id更新指定状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月18日
	 * @param firstBorrowId
	 * @param status
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateStatusByFirstBorrowId(@Param("firstBorrowId") Integer firstBorrowId, @Param("status") Integer status) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新关联的最终记录id<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年12月4日
	 * @param firstBorrowId
	 * @param status
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateRealIdByFirstBorrowId(@Param("firstBorrowId") Integer firstBorrowId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据userId查询开通直通车的总额<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年4月8日
	 * @param userId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public Integer seletAccountTotalByUserId(Integer userId) throws Exception;
}
