package com.dxjr.portal.fix.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderUserCnd;
import com.dxjr.portal.fix.vo.FixTenderUserVo;

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
public interface FixTenderUserService {
	
	/**
	 * 通过条件查询记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderDetailCnd
	 * @return
	 * FixTenderDetailVo
	 */
	public FixTenderUserVo  getFixTenderDetailStaticByBorrowId(FixTenderUserCnd fixTenderUserCnd) throws Exception;
	
	
	
	

	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Integer queryTotalTBCounts(FixTenderUserCnd fixTenderUserCnd) throws Exception;
	
	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Page queryTotalTBByPage(FixTenderUserCnd fixTenderUserCnd,Page page) throws Exception;
	
	
}
