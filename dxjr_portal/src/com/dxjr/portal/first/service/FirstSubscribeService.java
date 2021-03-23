package com.dxjr.portal.first.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstSubscribeCnd;

/**
 * <p>
 * Description:直通车转让认购业务类<br />
 * </p>
 * 
 * @title FirstSubscribeService.java
 * @package com.dxjr.portal.first.service
 * @author 朱泳霖
 * @version 0.1 2015年3月19日
 */
public interface FirstSubscribeService {

	/**
	 * <p>
	 * Description:根据直通车转让Id查询购买记录<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月20日
	 * @param firstSubscribeCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page querySubscribeListByTransferId(FirstSubscribeCnd firstSubscribeCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:手动认购<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月27日
	 * @param firstSubscribeCnd
	 * @param userId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveManualSubscribe(FirstSubscribeCnd firstSubscribeCnd, Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:自动复审<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月27日
	 * @param firstSubscribeCnd
	 * @param userId
	 * @param userName
	 * @param checkRemark
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveTransferRecheck(FirstSubscribeCnd firstSubscribeCnd, Integer userId,String userName, String checkRemark) throws Exception;
}
