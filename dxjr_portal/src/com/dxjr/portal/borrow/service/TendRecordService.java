package com.dxjr.portal.borrow.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.entity.TenderRecord;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.tender.vo.BTenderRecordCnd;
import com.dxjr.portal.tender.vo.BTenderRecordVo;

public interface TendRecordService {
	List<TenderRecord> selectByUserIdAndBorrowId(Integer userId, Integer borrowId);

	BigDecimal getMaxeffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account);

	BigDecimal isEffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account);

	/**
	 * 
	 * <p>
	 * Description:根据标ID、用户ID、自动投标排名查询自动投标记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月27日
	 * @param borrowId
	 *            标ID
	 * @param userId
	 *            用户ID
	 * @param autotenderOrder
	 *            自动投标排名
	 * @return TenderRecord
	 */
	public TenderRecord queryTenderRecordForAutoTender(int borrowId, int userId, Integer autotenderOrder);

	public int updateByPrimaryKeySelective(TenderRecord record);

	/**
	 * 
	 * <p>
	 * Description:根据borrowId查询投标记录 <br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月22日
	 * @param map
	 * @return Page
	 * @throws Exception
	 */
	public Page queryTenderRecordByBorrowId(Integer borrowId, Page p) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据borrowId查询投标记录数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月24日
	 * @param map
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryTenderRecordCountByBorrowId(Map<String, Object> map) throws Exception;

	/**
	 * <p>
	 * Description:获取用户的投标有效金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月3日
	 * @param borrowId
	 * @param userId
	 * @param money
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal getEffectMoney(Integer borrowId, Integer userId, BigDecimal money) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据borrowId查询投标记录已投总金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年12月19日
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal getTenderAccountYesByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询投标记录集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月23日
	 * @param bTenderRecordCnd
	 * @return
	 * @throws Exception
	 *             List<BTenderRecordVo>
	 */
	public List<BTenderRecordVo> queryTenderListByCnd(BTenderRecordCnd bTenderRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId查询投官方标的投标记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月4日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer getTenderRecordCountByUserId(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId查询投标成功的数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月23日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer getTenderSuccessCountByUserId(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId查询投过散标的数量（即仅手动投标和自动投标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月23日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer getTenderPowderCountByUserId(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询userId和account查询该用户所投最后一笔投标记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param borrowId
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 *             TenderRecordVo
	 */
	public TenderRecordVo queryTenderRecordLastByUserIdAndAccount(Integer borrowId, Integer userId, BigDecimal account) throws Exception;
	
	/**
	 * 根据直通车认购记录ID获取投标信息
	 * <p>
	 * Description:根据直通车认购记录ID获取投标信息<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param realId
	 * @return
	 * List<TenderRecord>
	 */
	List<TenderRecord> queryTenderBorrow(Integer realId);

	
	public BigDecimal getCGMaxeffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account);
}
