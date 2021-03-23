package com.dxjr.portal.outerInterface.service;

import com.dxjr.portal.outerInterface.vo.OuterParamCnd;

/**
 * 
 * <p>
 * Description:海树网接口<br />
 * </p>
 * 
 * @title HSWInterfaceService.java
 * @package com.dxjr.portal.outerInterface.service
 * @author yangshijin
 * @version 0.1 2014年8月21日
 */
public interface HSWInterfaceService {

	/**
	 * 
	 * <p>
	 * Description:获取借款标(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getBorrows(String ip, String url,
			OuterParamCnd outerParamCnd, int pageNum, int pageSize)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标ID获取投标记录(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getTenderRecord(String ip, String url, String id,
			int pageNum, int pageSize) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取逾期的借款标(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getOverdueBorrows(String ip, String url, int pageNum,
			int pageSize) throws Exception;
}
