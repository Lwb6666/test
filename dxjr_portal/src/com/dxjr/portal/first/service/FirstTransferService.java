package com.dxjr.portal.first.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTransferCancelVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferLaunchVo;
import com.dxjr.portal.first.vo.FirstTransferTypeCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;

/**
 * <p>
 * Description:直通车转让业务类<br />
 * </p>
 * 
 * @title FirstTransferService.java
 * @package com.dxjr.portal.first.service
 * @author justin.xu
 * @version 0.1 2015年3月11日
 */
public interface FirstTransferService {
	/**
	 * <p>
	 * Description:查询用户可转让的直通车认购<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @param firstTransferTypeCnd
	 * @param page
	 * @throws Exception void
	 */
	public Page queryPageCanTransferByCnd(FirstTransferTypeCnd firstTransferTypeCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询用户可转认直通车认购的的详细信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTenderRealId
	 * @param userId
	 * @return
	 * @throws Exception FirstTransferTypeVo
	 */
	public FirstTransferTypeVo queryFirstTransferTypeByCnd(Integer firstTenderRealId, Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:保存直通车转让<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param firstTransferLaunchVo
	 * @return String
	 */
	public String saveFirstTransfer(FirstTransferLaunchVo firstTransferLaunchVo) throws Exception;

	/**
	 * <p>
	 * Description:取消直通车转让<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月15日
	 * @param firstTransferCancelVo
	 * @return String
	 */
	public String saveCancelFirstTransfer(FirstTransferCancelVo firstTransferCancelVo) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询直通车转让<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param firstTransferCnd
	 * @param page
	 * @return Page
	 * @throws Exception
	 */
	public Page queryFirstTransferByCnd(FirstTransferCnd firstTransferCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据Id查询直通车转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月18日
	 * @param transferId
	 * @return
	 * @throws Exception FirstTransferVo
	 */
	public FirstTransferVo queryTransferById(Integer transferId) throws Exception;

	/**
	 * <p>
	 * Description:查询我的账号下直通车转让信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月20日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryMyFirstTransferList(FirstTransferCnd firstTransferCnd, Page page) throws Exception;
	
	/**
	 * <p>
	 * Description:查询我的账号下直通车认购信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月23日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryMyFirstTransferSubscribeList(FirstTransferCnd firstTransferCnd, Page page) throws Exception;
	
	/**
	 * <p>
	 * Description:查询直通车已转让信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 * Page
	 */
	public Page queryMyFirstTransferedList(FirstTransferCnd firstTransferCnd, Page page) throws Exception ;
}
