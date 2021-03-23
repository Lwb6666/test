package com.dxjr.portal.webservice.borrow.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:借款标WebService<br />
 * </p>
 * 
 * @title WsBorrowService.java
 * @package com.dxjr.portal.webservice.borrow
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
@WebService
public interface WsBorrowService {
	/**
	 * <p>
	 * Description:操作罚息<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月27日
	 * @param borrowid
	 * @throws Exception
	 * 
	 */
	@WebMethod
	public String saveOperatingPenalty(@WebParam(name = "repaymentid") Integer repaymentid, @WebParam(name = "addip") String addip, @WebParam(name = "serviceKey") String serviceKey) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:初审借款标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月9日
	 * @param borrowId
	 * @param flag
	 * @param userId
	 * @param remark
	 * @param creditRating
	 * @return
	 * @throws Exception String
	 */
	@WebMethod
	public String saveApproveBorrowFirst(@WebParam(name = "borrowId") int borrowId, @WebParam(name = "flag") int flag, @WebParam(name = "userId") int userId, @WebParam(name = "remark") String remark,
			@WebParam(name = "creditRating") String creditRating, @WebParam(name = "serviceKey") String serviceKey) throws Exception;

	/**
	 * <p>
	 * Description:反欺诈审核<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年4月24日
	 * @param borrow
	 * @return
	 * @throws Exception Borrow
	 */
	@WebMethod
	public String saveApproveBorrowAntiFraud(@WebParam(name = "borrowId") int borrowId, @WebParam(name = "flag") int flag, @WebParam(name = "userId") int userId,
			@WebParam(name = "remark") String remark, @WebParam(name = "serviceKey") String serviceKey) throws Exception;

	/**
	 * <p>
	 * Description:借款标终审<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param borrowId
	 * @param flag
	 * @param userId
	 * @param remark
	 * @return String
	 */
	@WebMethod
	public String saveApproveBorrowFinal(@WebParam(name = "borrowId") Integer borrowId, @WebParam(name = "flag") Integer flag, @WebParam(name = "userId") Integer userId,
			@WebParam(name = "remark") String remark, @WebParam(name = "addip") String addip, @WebParam(name = "serviceKey") String serviceKey) throws AppException, Exception;

	/**
	 * 
	 * <p>
	 * Description:借款标复审<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月14日
	 * @param borrowid
	 * @return
	 * @throws Exception String
	 */
	@WebMethod
	public String saveApproveBorrowReCheck(@WebParam(name = "borrowid") Integer borrowid, @WebParam(name = "checkUserId") Integer checkUserId, @WebParam(name = "checkRemark") String checkRemark,
			@WebParam(name = "addip") String addip, @WebParam(name = "serviceKey") String serviceKey) throws Exception;

}
