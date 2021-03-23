package com.dxjr.portal.outerInterface.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.outerInterface.entity.ExternalAccessLog;
import com.dxjr.portal.outerInterface.vo.ExternalAccessLogCnd;
import com.dxjr.portal.outerInterface.vo.OuterParamCnd;
import com.dxjr.portal.outerInterface.vo.OverdueBorrowVo;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;

/**
 * 
 * <p>
 * Description:外部支持接口数据访问类<br />
 * </p>
 * 
 * @title OuterInterfaceMapper.java
 * @package com.dxjr.portal.outerInterface.mapper
 * @author yangshijin
 * @version 0.1 2014年8月19日
 */
public interface OuterInterfaceMapper {

	/**
	 * 
	 * <p>
	 * Description:插入外部访问日志<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param externalAccessLog
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertExternalAccessLog(ExternalAccessLog externalAccessLog)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询访问IP是否有访问网贷天眼接口的权限<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int judgeIPForWDTY(String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询访问IP是否有访问网贷天眼接口的权限<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int judgeIPForHSW(String ip) throws Exception;
	
	/**
	 * 根据IP和类型判断是否有权限
	 * @author WangQianJin
	 * @title @param ip
	 * @title @param accessType
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月15日
	 */
	public int judgeIPForInterface(@Param("ip") String ip,@Param("accessType") Integer accessType) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取每日成交量数据接口某个IP在某个时间段内的访问次数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param externalAccessLogCnd
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int findAccessCountForTransactionInfo(
			ExternalAccessLogCnd externalAccessLogCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某日的净值标总额(满标)、抵押标总额(满标)、天标总额(满标)、月标总额(满标)、贷款总额 (满标)、平均年利率<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param externalAccessLogCnd
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 */
	public Map<String, Object> findTodayBorrowInfo(
			@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某日的贷款周期(平均值)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal findTodayPeriod(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计某时间段投资人数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public int findInvestesCount(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计某时间段借款人人数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public int findLoanerCount(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询今天累计待收本息总计<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryNowCollectionData() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某IP在段之内频繁访问次数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param externalAccessLogCnd
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int judgeIPByAccessByDate(ExternalAccessLogCnd externalAccessLogCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个时间段内满标的借款标（包含抵押标和净值标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             List<BorrowVo>
	 */
	public List<BorrowVo> findBorrowInfo(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取正在投标中的标（净值标和抵押标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @return
	 * @throws Exception
	 *             List<BorrowVo>
	 */
	public List<BorrowVo> findBorrowByNow() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某天内满标的借款标信息（净值标和抵押标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             List<BorrowVo>
	 */
	public List<BorrowVo> findBorrowByDate(
			@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某天还款的标的信息（净值标和抵押标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 *             List<BorrowVo>
	 */
	public List<BorrowVo> getPaiedLoanInfo(
			@Param("beginTime") String beginTime,
			@Param("endTime") String endTime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标id查询待还记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param borrowid
	 * @return
	 * @throws Exception
	 *             List<BRepaymentRecordVo>
	 */
	public List<BRepaymentRecordVo> queryRepaymentRecordByBorrow(int borrowid)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:验证用户是否是平台用户<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param userName
	 * @param realName
	 * @param cardId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer judgeUser(@Param("userName") String userName,
			@Param("realName") String realName, @Param("cardId") String cardId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标id查询投标记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param borrowid
	 * @return
	 * @throws Exception
	 *             List<TenderRecordVo>
	 */
	public List<TenderRecordVo> queryTenderRecordByBorrow(int borrowid)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标id查询投标记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param outerParamCnd
	 * @return
	 * @throws Exception
	 *             List<TenderRecordVo>
	 */
	public List<TenderRecordVo> queryTenderRecordListByBorrow(
			OuterParamCnd outerParamCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标id查询投标记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param outerParamCnd
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryTenderRecordCountByBorrow(OuterParamCnd outerParamCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询借款标（仅查询抵押标和净值标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月20日
	 * @param outerParamCnd
	 * @param page
	 * @return List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowListByOuterParam(
			OuterParamCnd outerParamCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询借款标数量（仅查询抵押标和净值标）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param outerParamCnd
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryBorrowCountByOuterParam(OuterParamCnd outerParamCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取逾期借款标列表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<OverdueBorrowVo>
	 */
	public List<OverdueBorrowVo> findOverdueBorrowList(Page page)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取逾期借款标数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int findOverdueBorrowCout() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询发生过逾期的还款记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             List<BRepaymentRecordVo>
	 */
	public List<BRepaymentRecordVo> findOverdueRepaymentRecordList(int userId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待还本金的总计<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryUnpayCapitalByMemberId(int userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待还利息的总计<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryWaitPayInterestByMemberId(int userId)
			throws Exception;
}
