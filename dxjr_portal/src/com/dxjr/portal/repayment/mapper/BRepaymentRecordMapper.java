package com.dxjr.portal.repayment.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.BRepaymentRecord;
import com.dxjr.common.page.Page;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.repayment.vo.RepaymentBorrowVo;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;

/**
 * <p>
 * Description:待还记录访问类<br />
 * </p>
 * 
 * @title BRepaymentRecordMapper.java
 * @package com.dxjr.portal.repayment.mapper
 * @author justin.xu
 * @version 0.1 2014年8月3日
 */
public interface BRepaymentRecordMapper {
	/**
	 * <p>
	 * Description:根据id获得待还记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月3日
	 * @param id
	 * @return
	 * @throws Exception BRepaymentRecordVo
	 */
	public BRepaymentRecordVo queryBRepaymentRecordById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据id获得待还记录并锁定<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月3日
	 * @param id
	 * @return
	 * @throws Exception BRepaymentRecordVo
	 */
	public BRepaymentRecordVo queryBRepaymentRecordByIdForupdate(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id获得待还记录集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月3日
	 * @param borrowId
	 * @return
	 * @throws Exception List<BRepaymentRecordVo>
	 */
	public List<BRepaymentRecordVo> queryBRepaymentRecordByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:分页查询当前用户的待还记录集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param page
	 * @param userId
	 * @return List<RepaymentBorrowVo>
	 */
	List<RepaymentBorrowVo> selectRepayments(@Param("userId") Integer userId, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询当前用户的待还记录数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param userId
	 * @return Integer
	 */
	Integer selectRepaymentsCount(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取待还列表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @param p
	 * @return
	 * @throws Exception List<BRepaymentRecordVo>
	 */
	public List<BRepaymentRecordVo> queryRepaymentList(RepaymentRecordCnd repaymentRecordCnd, Page p) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:待还列表记录数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @return
	 * @throws Exception int
	 */
	public int queryRepaymentCount(RepaymentRecordCnd repaymentRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计还款总额、本金总额、利息总额、罚息总额等数据项<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> repaymentRecordTongji(RepaymentRecordCnd repaymentRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:用户待还总额<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月22日
	 * @param map
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryRepaymentAccountTotal(Map map) throws Exception;

	/**
	 * <p>
	 * Description: 查询本期还款期数之前未还记录数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月27日
	 * @param borrowId
	 * @param order
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBeforeOrderUnPayCount(@Param("borrowId") Integer borrowId, @Param("order") Integer order) throws Exception;

	public List<BRepaymentRecordVo> querySums(Map<String, Object> map, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新还款记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月12日
	 * @param bRepaymentRecord
	 * @return int
	 */
	public int updateBReaymentRecordById(BRepaymentRecord bRepaymentRecord) throws Exception;
}
