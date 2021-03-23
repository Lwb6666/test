package com.dxjr.portal.account.service;

import java.util.Map;

import com.dxjr.common.page.Page;

/**
 * <p>
 * Description:账户详情服务接口<br />
 * </p>
 * 
 * @title AccountDetailService.java
 * @package com.dxjr.portal.account.service
 * @author 胡建盼
 * @version 0.1 2014年8月14日
 */
public interface AccountDetailService {
	public Page queryInvestList(Map<String, Object> params,Page page) throws Exception;
	public Page queryFixList(Map<String, Object> params,Page page) throws Exception;
}
