package com.dxjr.portal.webservice.first.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>
 * Description:直通车信息业务类实现WebService<br />
 * </p>
 * 
 * @title WsFirstBorrowService.java
 * @package com.dxjr.portal.webservice.first.service
 * @author justin.xu
 * @version 0.1 2014年7月22日
 */
@WebService
public interface WsFirstBorrowService {
	/**
	 * <p>
	 * Description:直通车满标手动复审通过<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月17日
	 * @param firstBorrowId
	 * @return
	 * @throws Exception String
	 */
	@WebMethod
	public String saveTenderFirstManualApprove(@WebParam(name = "firstBorrowId") Integer firstBorrowId, @WebParam(name = "ip") String ip, @WebParam(name = "serviceKey") String serviceKey)
			throws Exception;

	/**
	 * <p>
	 * Description:直通车解锁手动审核通过<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月29日
	 * @param firstBorrowId
	 * @param memberId
	 * @param ip
	 * @param approveUserId
	 * @param approveRemark
	 * @return
	 * @throws Exception String
	 */
	@WebMethod
	public String saveUnlockManualApprove(@WebParam(name = "firstTenderRealId") Integer firstBorrowId, @WebParam(name = "memberId") Integer memberId, @WebParam(name = "ip") String ip,
			@WebParam(name = "approveUserId") Integer approveUserId, @WebParam(name = "approveRemark") String approveRemark, @WebParam(name = "serviceKey") String serviceKey) throws Exception;

	/**
	 * <p>
	 * Description:后台根据用户、直通车id执行解锁<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月25日
	 * @param firstBorrowId
	 * @param memberId
	 * @return
	 * @throws Exception String
	 */
	@WebMethod
	public String saveUnLockManual(@WebParam(name = "firstTenderRealId") Integer firstBorrowId, @WebParam(name = "memberId") Integer memberId, @WebParam(name = "serviceKey") String serviceKey)
			throws Exception;
}
