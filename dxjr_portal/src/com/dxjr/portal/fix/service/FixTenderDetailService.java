package com.dxjr.portal.fix.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderDetailCnd;
import com.dxjr.portal.fix.vo.FixTenderDetailVo;

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
public interface FixTenderDetailService {
	
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
	public Integer queryTotalJoinCounts() throws Exception;
	
	/**
	 * 分页查询某个定期宝的认购记录
	 * @param fixBorrowId
	 * @param page
	 * @return
	 */
	public Page queryFixTenderDetailVoList(Integer fixBorrowId,Page page) throws Exception;
	
	
	/**
	 * 新增认购明细
	 */
	public void insertFixTenderDetail(FixTenderDetail fixTenderDetail) throws Exception;
	
	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Page queryTotalJoinInfoByPage(FixTenderDetailCnd fixTenderDetailCnd,Page page) throws Exception;
	
	
	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Page queryJoinInfoByPage(FixTenderDetailCnd fixTenderDetailCnd,Page page) throws Exception;
/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @author 陈建国
 * @version 0.1 2015年5月23日
 * @param fixTenderDetailCnd
 * @param page
 * @return
 * Page
 */
	public List<FixTenderDetailVo> getFixTenderDetailByBorrowId(FixTenderDetailCnd fixTenderDetailCnd) throws Exception;
	
	
	/**
	 * 定期宝统计信息
	 */
	public FixTenderDetailVo  getFixTenderDetailStaticByBorrowId(FixTenderDetailCnd fixTenderDetailCnd) throws Exception;
	
	/**
	 * 定期宝加入中预期收益统计
	 */
	public BigDecimal  getYqsyByJrz(FixTenderDetailCnd fixTenderDetailCnd) throws Exception;
	
	
	/**
	 * 根据锁定期限和用户ID查看是否存在定期宝投资记录
	 */
	public Integer getUserAccount(Map map) throws Exception;
}
