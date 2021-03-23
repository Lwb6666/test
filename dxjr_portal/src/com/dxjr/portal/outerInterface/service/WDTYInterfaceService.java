package com.dxjr.portal.outerInterface.service;

import com.dxjr.portal.outerInterface.entity.WdtyMemberBinding;
import com.dxjr.portal.outerInterface.vo.WdtyParamCnd;
import com.dxjr.utils.exception.AppException;

/**
 * 
 * <p>
 * Description:网贷天眼调用接口<br />
 * </p>
 * 
 * @title WDTYInterfaceService.java
 * @package com.dxjr.portal.outerInterface.service
 * @author yangshijin
 * @version 0.1 2014年8月21日
 */
public interface WDTYInterfaceService {
	/**
	 * 
	 * <p>
	 * Description:每日成交量数据(仅统计净值标和抵押标)（网贷天眼调用接口）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String transactionInfo(String ip, String url, String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:平台贷款数据(仅统计净值标和抵押标)（网贷天眼调用接口）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String loanInfo(String ip, String url, String date) throws Exception;
	
	/**
	 * 根据查询条件获取信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding queryMemberBindingInfo(WdtyParamCnd parameteVo) throws AppException;
	
	/**
	 * 添加网贷信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int insertWdtyMemberBinding(WdtyMemberBinding wdtyMemberBinding) throws AppException;
	
	/**
	 * 修改网贷天眼信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateMemberBindingInfo(WdtyMemberBinding wdtyMemberBinding) throws AppException;
	
	/**
	 * 根据Key查询网贷信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年2月4日
	 */
	public WdtyMemberBinding queryMemberBindingInfoByKey(WdtyParamCnd parameteVo) throws AppException;
	
	/**
	 * 根据ID修改loginKey
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateLoginKeyById(WdtyMemberBinding wdtyMemberBinding) throws AppException;

}
