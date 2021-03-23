package com.dxjr.portal.first.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;

/**
 * <p>
 * Description:直通车数据访问类<br />
 * </p>
 * 
 * @title FirstBorrowMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2014年7月14日
 */
public interface FirstBorrowMapper {
	/**
	 * <p>
	 * Description:根据条件分页查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<FirstBorrowVo>
	 */
	public List<FirstBorrowVo> queryFirstBorrowList(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception List<FirstBorrowVo>
	 */
	public List<FirstBorrowVo> queryFirstBorrowList(FirstBorrowCnd firstBorrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryFirstBorrowCount(FirstBorrowCnd firstBorrowCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标直通车-加入总人次<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return Integer
	 */
	public Integer queryFirstPersonsTotalCount() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标直通车-累计总金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	public BigDecimal queryFirstTotalAccount() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标直通车-为用户累计赚取(包含待收利息和已赚利息)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	public BigDecimal queryFirstTotalInterst() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标直通车-资金利用率<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return BigDecimal
	 */
	public BigDecimal queryFirstAccountRate() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标直通车-为用户自动投标次数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return Integer
	 */
	public Integer queryFirstTenderCount() throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id，在解锁时更新直通车实际总金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月28日
	 * @param unlockaccount 要解锁的金额
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateRealAccountByUnlock(@Param("unlockaccount") Integer unlockaccount, @Param("id") Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id，在解锁时更新直通车更新直通车记录的状态为失效<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月28日
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateFirstBorrowStatusByUnlock(@Param("id") Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询符合投标的直通车期数记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月5日
	 * @return
	 * @throws Exception List<FirstBorrowVo>
	 */
	public List<FirstBorrowVo> queryFirstBorrowForTenderBorrow() throws Exception;

	/**
	 * 
	 * <p>
	 * 获取直通车总余额
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月6日
	 * @return BigDecimal
	 * @throws Exception
	 * 
	 */
	public BigDecimal queryFirstUseMoney() throws Exception;

	/**
	 * 
	 * <p>
	 * 获取最新直通车信息
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @return
	 * @throws Exception FirstBorrowVo
	 */
	public FirstBorrowVo getLatest();

	/**
	 * 
	 * <p>
	 * Description:调用直通车投标存储过程<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param map void
	 */
	public void firstTender(Map map);

	/**
	 * <p>
	 * Description:根据id查询有效直通车<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月22日
	 * @param id
	 * @return
	 * @throws Exception FirstBorrowVo
	 */
	public FirstBorrowVo queryAvailableFirstBorrowById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取首页及直通车专区页面显示的直通车记录（分页）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月19日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception List<FirstBorrowVo>
	 */
	public List<FirstBorrowVo> queryFirstBorrowByFirstBorrowCnd(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取首页及直通车专区页面显示的直通车记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception int
	 */
	public Integer queryFirstBorrowCountByFirstBorrowCnd(FirstBorrowCnd firstBorrowCnd) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:获取首页显示的直通车<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年3月31日
	 * @param now
	 * @return
	 * @throws Exception
	 * FirstBorrowVo
	 */
	public FirstBorrowVo getFirstBorrowForIndex(Date now) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:获取首页显示直通车的下一期预发直通车<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年3月31日
	 * @param firstBorrowId
	 * @return
	 * @throws Exception
	 * FirstBorrowVo
	 */
	public FirstBorrowVo getAdvanceFirstBorrowForIndex(int firstBorrowId) throws Exception;
}
