package com.dxjr.portal.account.service;

import java.util.Map;

import com.dxjr.common.page.Page;

public interface AccountBBSPostListService {
	/**
	 * 
	 * <p>
	 * Description:查询发帖列表<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月15日
	 * @param param
	 * @param page
	 * @return
	 * Page
	 */
	public Page queryBbsNotesPage(Map<String,Object> param,Page page);
	/**
	 * 
	 * <p>
	 * Description:判断当前查看用户信息的user是否有权限查看已经删除的帖子<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月15日
	 * @param userId
	 * @return
	 * Boolean
	 */
	public Boolean isCurrentLookerhasPower(Integer userId);
}
