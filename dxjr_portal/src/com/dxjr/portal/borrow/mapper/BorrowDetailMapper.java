package com.dxjr.portal.borrow.mapper;

import java.math.BigDecimal;

import com.dxjr.portal.borrow.vo.BorrowDetailCreditVo;
import com.dxjr.portal.borrow.vo.BorrowDetailRepayVo;
import com.dxjr.portal.borrow.vo.BorrowDetailVo;
import com.dxjr.portal.borrow.vo.BorrowDetailWebPayVo;

/**
 * <p>
 * Description:借款标详情数据访问接口<br />
 * </p>
 * 
 * @title BorrowDetailMapper.java
 * @package com.dxjr.portal.borrow.mapper
 * @author justin.xu
 * @version 0.1 2014年9月12日
 */
public interface BorrowDetailMapper {

	/**
	 * <p>
	 * Description:根据借款标id查询借款标详情（用于投标）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月12日
	 * @param id 借款标id
	 * @return
	 * @throws Exception BorrowDetailVo
	 */
	public BorrowDetailVo selectBorrowDetailByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询借款标详情（用于后台审核时查看借款标详情）<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月19日
	 * @param id 借款标id
	 * @return
	 * @throws Exception BorrowDetailVo
	 */
	public BorrowDetailVo selectBorrowDetail(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询待还统计数据（用于投标）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月12日
	 * @param id
	 * @return
	 * @throws Exception BorrowDetailRepayVo
	 */
	public BorrowDetailRepayVo selectBorrowDetailRepayByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询待还本息总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月12日
	 * @param borrowId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal selectRepayTotal(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询已还本息总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月13日
	 * @param borrowId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryHavePayTotal(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询已垫付数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月13日
	 * @param borrowId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BorrowDetailWebPayVo queryBorrowDetailWebPayById(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款者id统计借款总额和借款笔数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月22日
	 * @param map
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BorrowDetailCreditVo queryBorrowDetailCrediByMemberId(Integer memberId) throws Exception;

}