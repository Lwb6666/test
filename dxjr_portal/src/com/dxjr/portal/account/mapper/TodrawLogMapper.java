package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.TodrawLogCnd;
import com.dxjr.portal.account.vo.TodrawLogVo;

/**
 * 
 * <p>
 * Description:转可提现记录数据访问类<br />
 * </p>
 * 
 * @title TodrawLogMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author yangshijin
 * @version 0.1 2014年8月12日
 */
public interface TodrawLogMapper {

	/**
	 * 
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLogCnd
	 * @return
	 * @throws Exception
	 *             List<TodrawLogVo>
	 */
	public List<TodrawLogVo> queryTodrawLogList(TodrawLogCnd todrawLogCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件分页查询对象集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLogCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<TodrawLogVo>
	 */
	public List<TodrawLogVo> queryTodrawLogList(TodrawLogCnd todrawLogCnd,
			Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLogCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryTodrawLogCount(TodrawLogCnd todrawLogCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userid查询冻结中的转可提金额总额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param userid
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryTodrawNoMoney(int userid) throws Exception;
}
