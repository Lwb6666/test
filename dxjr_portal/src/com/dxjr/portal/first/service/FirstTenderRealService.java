package com.dxjr.portal.first.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;

/**
 * <p>
 * Description:优先投标计划最终认购记录业务类<br />
 * </p>
 * 
 * @title FirstTenderRealService.java
 * @package com.dxjr.portal.first.service
 * @author justin.xu
 * @version 0.1 2014年7月24日
 */
public interface FirstTenderRealService {

	/**
	 * <p>
	 * Description:根据条件分页查询记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月24日
	 * @param firstTenderRealCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryPageListByCnd(FirstTenderRealCnd firstTenderRealCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据用户、直通车最终记录id执行解锁<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param firstTenderRealId
	 * @param memberId
	 * @return
	 * @throws Exception String
	 */
	public String saveUnLock(Integer firstTenderRealId, Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:解锁自动审核<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param firstTenderRealId
	 * @param memberId
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	public String saveUnLockApprove(Integer firstTenderRealId, Integer memberId, String ip, Integer approveUserId, String approveRemark) throws Exception;
}
