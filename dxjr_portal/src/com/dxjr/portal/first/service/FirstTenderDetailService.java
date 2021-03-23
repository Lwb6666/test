package com.dxjr.portal.first.service;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.first.vo.FirstTenderDetailVo;

/**
 * <p>
 * Description:优先投标计划开通明细业务类<br />
 * </p>
 * 
 * @title FirstTenderDetailService.java
 * @package com.dxjr.portal.first.service
 * @author justin.xu
 * @version 0.1 2014年7月15日
 */
public interface FirstTenderDetailService {

	/**
	 * <p>
	 * Description:根据条件分页查询记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryPageListByCnd(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception List<FirstTenderDetailVo>
	 */
	public List<FirstTenderDetailVo> queryListByCnd(FirstTenderDetailCnd firstTenderDetailCnd) throws Exception;

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
	public Page queryFirstTenderDetail(FirstTenderDetailCnd firstTenderDetailCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询用户的最大可投直通车有效金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param firstBorrow
	 * @param userId
	 * @param money
	 * @param account
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal getMaxeffectiveMoney(FirstBorrowVo firsorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception;

	/**
	 * <p>
	 * Description:判断输入金额是否有效金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrow
	 * @param userId
	 * @param money
	 * @param account
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal isEffectiveMoney(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception;

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
	public Integer updateStatusByFirstBorrowId(Integer firstBorrowId, Integer status) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新关联的最终记录id<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年12月4日
	 * @param firstBorrowId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateRealIdByFirstBorrowId(Integer firstBorrowId) throws Exception;

	/**
	 * <p>
	 * Description:判断输入金额是否有效金额（用于直接生成最终认购记录）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrow
	 * @param userId
	 * @param money
	 * @param account
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal isEffectiveMoneyForTenderReal(FirstBorrowVo firstBorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception;

	/**
	 * <p>
	 * Description:查询用户的最大可投直通车有效金额（用于直接生成最终认购记录）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param firstBorrow
	 * @param userId
	 * @param money
	 * @param account
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal getMaxeffectiveMoneyForTenderReal(FirstBorrowVo firsorrow, Integer userId, BigDecimal money, AccountVo account) throws Exception;
	
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
