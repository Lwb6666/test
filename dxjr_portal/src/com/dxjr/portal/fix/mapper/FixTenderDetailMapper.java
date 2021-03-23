package com.dxjr.portal.fix.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderDetailCnd;
import com.dxjr.portal.fix.vo.FixTenderDetailVo;

/**
 * <p>
 * Description:定期宝认购明细数据库接口类<br />
 * </p>
 * 
 * @title FixTenderDetailMapper.java
 * @package com.dxjr.base.account
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixTenderDetailMapper {
	/**
	 * 根据定期宝id分组查询用户总认购金额按第一次认购时间排序
	 * @param fixBorrowId
	 * @return
	 */
	public List<FixTenderDetailVo> querySumAccountGroupByUser(Integer fixBorrowId) throws Exception;
	
		/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public int queryTotalJoinCounts() throws Exception;
	
	/**
	 * 分页查询某个定期宝的认购记录
	 * @param fixBorrowId
	 * @param page
	 * @return
	 */
	public List<FixTenderDetailVo> queryFixTenderDetailVoList(Integer fixBorrowId,Page page) throws Exception;
	
	/**
	 * 查询某个定期宝的认购记录数
	 * @param fixBorrowId
	 * @return
	 */
	public Integer queryFixTenderDetailVoCounts(Integer fixBorrowId) throws Exception;

	/**
	 * 新增认购明细
	 */
	public void insertFixTenderDetail(FixTenderDetail fixTenderDetail) throws Exception;
	
	

	/**
	 * 统计加入次数
	 * @param id
	 * @return
	 */
	public  Integer queryTotalJoinInfoByUserCounts(FixTenderDetailCnd fixTendeRetailCnd);
	
	/**
	 * 统计加入记录
	 * @param id
	 * @return
	 */
	public List<FixTenderDetailVo> queryTotalJoinInfoByUser(FixTenderDetailCnd fixTendeRetailCnd,Page page);
	
	
	/**
	 * 统计加入次数
	 * @param id
	 * @return
	 */
	public  Integer queryJoinInfoByUserCounts(FixTenderDetailCnd fixTendeRetailCnd);
	
	/**
	 * 统计加入记录
	 * @param id
	 * @return
	 */
	public List<FixTenderDetailVo> queryJoinInfoByUser(FixTenderDetailCnd fixTendeRetailCnd,Page page);
	
	
	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderDetailCnd
	 * @return
	 * List<FixTenderDetailVo>
	 */
	public List<FixTenderDetailVo> getFixTenderDetailByBorrowId(FixTenderDetailCnd fixTenderDetailCnd);
	/**
	 * 定期宝统计记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderDetailCnd
	 * @return
	 * List<FixTenderDetailVo>
	 */
	public FixTenderDetailVo  getFixTenderDetailStaticByBorrowId(FixTenderDetailCnd fixTenderDetailCnd);

	/**
	 * 根据定期宝id获取定期宝所有认购总额
	 * @param fixBorrowId
	 * @return
	 */
	public BigDecimal  getSumAccountByFixBorrowId(Integer fixBorrowId) throws Exception;
	
	/**
	 * 更新认购明细
	 * @param fixTenderDetailVo
	 * @return
	 */
	public Integer updateFixTenderDetail(FixTenderDetailVo fixTenderDetailVo);
	
	/**
	 * 根据定期宝加入中的预期收益
	 * @param  
	 * @return
	 */
	public BigDecimal  getYqsyByJrz(FixTenderDetailCnd fixTenderDetailCnd) throws Exception;
	
	
	/**
	 * 根据账户id获得定期宝投资明细总额
	 * @param map
	 * @return
	 */
	public Integer getUserAccount(Map map) throws Exception;
	
	
}
