package com.dxjr.portal.account.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.TurnDrawLogVO;
/**
 * 账户转可提历史记录
 * @author 胡建盼
 * 
 */
public interface DrawLogRecordService {

	/**
	 * 
	 * <p>
	 * Description:分页查询账户转可提历史记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月6日
	 * @param userId
	 * @param page
	 * @return
	 * Page
	 */
	Page queryDrawPageListByCnd(double userId,String startTime,String endTime, Page page);

	List<TurnDrawLogVO> queryDrawListByCnd(double userId, String startTime, String endTime);
}
