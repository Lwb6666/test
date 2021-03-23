package com.dxjr.portal.first.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTenderLogCnd;
import com.dxjr.portal.first.vo.FirstTenderLogVo;

/**
 * 
 * <p>
 * Description:直通车投标日志记录业务接口<br />
 * </p>
 * 
 * @title FirstTenderLogService.java
 * @package com.dxjr.portal.first.service
 * @author yangshijin
 * @version 0.1 2015年3月11日
 */
public interface FirstTenderLogService {

	/**
	 * 
	 * <p>
	 * Description:根据条件分页查询记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLogCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryPageListByCnd(FirstTenderLogCnd firstTenderLogCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据ID查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param id
	 * @return
	 * @throws Exception FirstTenderLogVo
	 */
	public FirstTenderLogVo queryById(int id) throws Exception;
}
