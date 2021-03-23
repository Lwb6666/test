package com.dxjr.portal.webservice.borrowreport.service;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * <p>
 * Description:借款统计信息业务类实现WebService<br />
 * </p>
 * 
 * @title WsBorrowReportService.java
 * @package com.dxjr.portal.webservice.borrowreport.service
 * @author justin.xu
 * @version 0.1 2014年6月26日
 */
@WebService
public interface WsBorrowReportService {
	/**
	 * <p>
	 * Description:统计某个用户下的净值额度<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月26日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	@WebMethod
	public BigDecimal queryNetMoneyLimit(
			@WebParam(name = "memberId") Integer memberId) throws Exception;

}
