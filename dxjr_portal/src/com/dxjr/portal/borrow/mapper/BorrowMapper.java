package com.dxjr.portal.borrow.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.Borrow;
import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.InvestBorrow;

public interface BorrowMapper {
	/**
	 * <p>
	 * Description:新增借款标，不同标种借入功能完成后期可删除<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param record
	 * @return int
	 */
	Integer insert(Borrow record) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询借款标<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param id
	 * @return BorrowVo
	 */
	BorrowVo selectByPrimaryKey(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询借款标并锁定<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param id
	 * @return BorrowVo
	 */
	public BorrowVo selectByPrimaryKeyForUpdate(Integer id);

	/**
	 * <p>
	 * Description:根据id更新借款标<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param record
	 * @return Integer
	 */
	Integer updateByPrimaryKey(Borrow record) throws Exception;

	/**
	 * <p>
	 * Description:查询投标中的借款标数量<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param startPage
	 * @param pageSize
	 * @param userId
	 * @return List<RepaymentBorrow>
	 */
	Integer countTenderBorrow();

	/**
	 * <p>
	 * Description:分页查询投标中的借款标集合<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param page
	 * @return List<Borrow>
	 */
	List<BorrowVo> selectTenderBorrow(Page page);

	/**
	 * <p>
	 * Description:调用手动投标存储过程<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param map void
	 */
	public void saveManualTender(Map<String, Object> params) throws Exception;

	/**
	 * <p>
	 * Description:调用复审存储过程<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月3日 void
	 */
	public void approveBorrowReCheck(Map<String, Object> params) throws Exception;

	/**
	 * <p>
	 * Description:调用还款存储过程<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月4日
	 * @param map void
	 */
	public void repayBorrow(Map map);

	/**
	 * <p>
	 * Description: 提前还款<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月3日
	 * @param map void
	 */
	public void repayEarlyBorrow(Map map);

	/**
	 * <p>
	 * Description:操作罚息<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年7月22日
	 * @param map void
	 */
	public void operatingPenalty(Map map);

	/**
	 * <p>
	 * Description:调用垫付后还款存储过程<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月18日
	 * @param map void
	 */
	public void afterWebpayBorrow(Map map);

	/**
	 * <p>
	 * Description:调用撤标存储过程<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param map void
	 */
	public void cancelBorrow(Map map);

	/**
	 * <p>
	 * Description:调用流标存储过程<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param map void
	 */
	public void flowBorrow(Map map);

	/**
	 * <p>
	 * Description:取得最后一条借款<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月5日
	 * @return Borrow
	 */
	public Borrow queryLastBorrow();

	/**
	 * 查询最近一条净值标
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月5日
	 * @return Borrow
	 */
	public Borrow queryLastNetValueBorrow();

	/**
	 * 根据合同编号查询
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月5日
	 * @param contractNo
	 * @return Borrow
	 */
	public Borrow queryBorrowByContractNo(String contractNo);

	/**
	 * <p>
	 * Description:我要理财-招标中的列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForDefault(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-招标中的列表记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForDefaultCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-预发标列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForAdvance(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-预发标列表记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForAdvanceCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForComplete(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成列表记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForCompleteCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的抵押标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForCompleteDYB(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的抵押标记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForCompleteDYBCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的净值标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForCompleteJZB(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的净值标记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForCompleteJZBCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的秒标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForCompleteMB(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的秒标记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForCompleteMBCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的推荐标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForCompleteTJB(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的推荐标记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForCompleteTJBCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-逾期列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForOverdue(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-逾期列表记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForOverdueCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-借款列表<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryBorrowForList(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-借款列表记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月13日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBorrowForListCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:我要理财-完成的担保标数量统计<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月4日
	 * @param borrowCnd
	 * @param page
	 * @return List<?>
	 */
	List<?> queryBorrowForCompleteDBB(BorrowCnd borrowCnd, Page page);

	/**
	 * <p>
	 * Description:我要理财-完成的担保标列表<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月4日
	 * @param borrowCnd
	 * @return int
	 */
	int queryBorrowForCompleteDBBCount(BorrowCnd borrowCnd);

	/**
	 * <p>
	 * 查询成交总金额
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月6日
	 * @return
	 * @throws Exception List<RiskMarginVo>
	 */
	public Map<String, ?> queryTotalMoney() throws Exception;

	/**
	 * <p>
	 * 未满抵押标
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getLatestNotFull(Map map) throws Exception;

	/**
	 * <p>
	 * 已经满的抵押标
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getLatestFull(Map map) throws Exception;

	/**
	 * <p>
	 * 预发标
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getAdvanced(Map map) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:查询新手标<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年4月18日
	 * @return
	 * @throws Exception
	 * BorrowVo
	 */
	public BorrowVo getAdvancedNew() throws Exception;

	/**
	 * <p>
	 * Description:查询正在投标中记录<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年9月9日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryTendering(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * 查询用户所有标
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> queryAll(BorrowCnd borrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询正在投标中记录数<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年9月9日
	 * @param borrowCnd
	 * @return
	 * @throws Exception int
	 */
	public int queryTenderingCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * 查询用户所有标(计数)
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowCnd
	 * @return
	 * @throws Exception int
	 */
	public int queryAllCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * 正在投标中记录数-融资
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月10日
	 * @param borrowCnd
	 * @return
	 * @throws Exception int
	 */
	public int getTenderingCount(BorrowCnd borrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:成功借款次数<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月22日
	 * @param map
	 * @return
	 * @throws Exception int
	 */
	public int borrowSuccessCount(Map map) throws Exception;

	/**
	 * <p>
	 * Description:查询未满标<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月25日
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> getBorrowList(Map map, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询已满标<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月25日
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> getBorrowListSuccess(Map map, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据id更新借款标状态和审核状态<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月30日
	 * @param borrowVo
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateBorrowStatusById(Borrow borrow) throws Exception;

	/**
	 * <p>
	 * Description:投资管理===正在投标中列表==即当前用户投资其他正在招标中的借款标信息 <br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param map
	 * @return int
	 */
	int queryTenderingForOtherBorrowCount(Map<String, Object> map);
	
	/**
	 * 散标列表数量
	 * <p>
	 * Description:重写方法<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月27日
	 * @param map
	 * @return
	 * int
	 */
	int queryTenderingForOtherBorrowCountNew(Map<String, Object> map);
	
	/**
	 * 根据id查询投标记录
	 * <p>
	 * Description:根据id查询投标记录<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param id
	 * @return
	 * BorrowVo
	 */
	BorrowVo queryTenderingForId(Map<String, Object> map);
	/**
	 * 根据认购记录Map查询投标信息
	 * <p>
	 * Description:根据认购记录Map查询投标信息<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param map
	 * @return
	 * BorrowVo
	 */
	BorrowVo queryTenderingForMap(Map<String, Object> map); 
	
	/**
	 * 散标列表
	 * <p>
	 * Description:重写方法<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月27日
	 * @param map
	 * @param page
	 * @return
	 * List<BorrowVo>
	 */
	List<BorrowVo> queryTenderingForOtherBorrowNew(Map<String, Object> map, Page page);

	/**
	 * <p>
	 * Description:投资管理===正在投标中列表==即当前用户投资其他正在招标中的借款标信息 数量<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param map
	 * @param page
	 * @return List<BorrowVo>
	 */
	List<BorrowVo> queryTenderingForOtherBorrow(Map<String, Object> map, Page page);

	/**
	 * <p>
	 * Description:获取某标种的最后一笔标信息<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年9月15日
	 * @param borrowType
	 * @return Borrow
	 */
	public Borrow queryLastBorrowByBorrowType(int borrowType);

	/**
	 * <p>
	 * Description:根据借款标id查询投标总额<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年9月15日
	 * @param borrowId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryTenderTotalByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:还款引发债权转让撤销<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2014年12月22日
	 * @param map
	 * @return
	 */
	public void transferCancelByRepay(Map<String, Object> mapTrans);

	/**
	 * <p>
	 * Description:包含债转 满标的记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年3月4日
	 * @param map
	 * @return List<BorrowVo>
	 */
	List<BorrowVo> getLatestFullConstainTransfer(Map map);

	/**
	 * <p>
	 * Description:包含债转 没有满标的记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年3月4日
	 * @param map
	 * @return List<BorrowVo>
	 */
	List<BorrowVo> getLatestNotFullConstainTransfer(Map map);

	/**
	 * <p>
	 * Description:直通车转让撤销<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月1日
	 * @param params
	 * void
	 */
	public void cancelFirstTransfer(Map<String, Object> params)throws Exception;
	
	
	public Integer updateBorrow(BorrowVo borrowVo);
	
	public Integer updateCGBorrowZS(BorrowVo borrowVo);
	
	public Integer updateBorrowStutus(BorrowVo borrowVo);
	
	public Integer updateBorrowStutusDay(BorrowVo borrowVo);
	
	
	/**
	 * 
	 * <p>
	 * Description:查询投标记录详情<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowId
	 * @return
	 * InvestBorrow
	 */
	public List<InvestBorrow> findTenderrecordInfo(Integer borrowId,Page page);
	
	public int findTenderrecordInfoCount(Integer borrowId);
	
	public int updateBorrowAdvance(BorrowVo borrowVo);

}