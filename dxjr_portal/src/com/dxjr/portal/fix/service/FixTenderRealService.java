package com.dxjr.portal.fix.service;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderDetailCnd;
import com.dxjr.portal.fix.vo.FixTenderRealCnd;
import com.dxjr.portal.fix.vo.FixTenderRealVo;

/**
 * <p>
 * Description:定期宝认购明接口类<br />
 * </p>
 *
 * @title FixTenderDetailService.java
 * @package com.dxjr.portal.fix1.service
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixTenderRealService {

	/**
	 * 根据定期宝id分组查询用户总认购金额按第一次认购时间排序
	 * @param fixBorrowId
	 * @return
	 */
	public List<FixTenderRealVo> querySumAccountGroupByUser(Integer fixBorrowId)  throws Exception;


	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Page queryTotalSYInfoByPage(FixTenderRealCnd fixTenderRealCnd, Page page) throws Exception;

	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Page queryTotalTCInfoByPage(FixTenderRealCnd fixTenderRealCnd, Page page) throws Exception;

	/**
	 *
	 * <p>
	 * Description:统计定期宝退出中列表<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年7月6日
	 * @param fixTenderRealCnd
	 * @param page
	 * @return
	 * @throws Exception
	 * Page
	 */
	public Page queryTotalTCZInfoByPage(FixTenderRealCnd fixTenderRealCnd, Page page) throws Exception;


	public List<FixTenderRealVo> getFixTenderRealstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd) throws Exception;

	/**
	 *
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderRealCnd
	 * @return
	 * List<FixTenderRealVo>
	 */
	public List<FixTenderRealVo> getTcstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd) throws Exception;


	/**
	 * 定期宝收益中预期收益统计
	 */
	public BigDecimal  getYqsyBySzy(FixTenderRealCnd fixTenderRealCnd) throws Exception;


	public BigDecimal getRedMoneyByfixId(FixTenderRealCnd fixTenderRealCnd) throws Exception;

	/**
	 * 根据用户id查询投宝次数
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author wangbo
	 * @version 0.1 2015年5月23日
	 * @param userId
	 * @return int
	 */
	int getFixTenderSuccessCountByUserId(int userId);
}
