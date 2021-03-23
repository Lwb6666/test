package com.dxjr.portal.autoInvestConfig.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordCnd;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordVo;

/**
 * 
 * <p>
 * Description:自动投标规则日志类数据访问类<br />
 * </p>
 * @title BaseAutoInvestConfigRecordMapper.java
 * @package com.dxjr.base.mapper 
 * @author yangshijin
 * @version 0.1 2014年5月19日
 */
public interface AutoInvestConfigRecordMapper {
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询自动投标规则日志数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfigRecordCnd
	 * @return
	 * @throws Exception
	 * int
	 */
	public int queryAutoInvestConfigRecordCount(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询自动投标规则日志记录集合（分页）<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfigRecordCnd
	 * @param page
	 * @return
	 * @throws Exception
	 * List<AutoInvestConfigRecordVo>
	 */
	public List<AutoInvestConfigRecordVo> queryAutoInvestConfigRecordListForPage(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd, Page page) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据ID查询日志信息<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月28日
	 * @param id
	 * @return
	 * @throws Exception
	 * AutoInvestConfigRecordVo
	 */
	public AutoInvestConfigRecordVo queryById(Integer id) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:查询最后一笔自动投标的投标规则日志信息<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月18日
	 * @param borrow_id
	 * @return
	 * @throws Exception
	 * AutoInvestConfigRecordVo
	 */
	public AutoInvestConfigRecordVo queryLastRecordByBorrowId(int borrow_id) throws Exception;
}
