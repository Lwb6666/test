package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.OnlineCounter;
import com.dxjr.common.page.Page;
import com.dxjr.portal.member.vo.OnlineCounterCnd;
import com.dxjr.portal.member.vo.OnlineCounterVo;

/**
 * <p>
 * Description:在线人数记录业务类<br />
 * </p>
 * 
 * @title OnlineCounterService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public interface OnlineCounterService {
	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月19日
	 * @param onlineCounterCnd
	 * @return
	 * @throws Exception
	 *             List<OnlineCounterVo>
	 */
	public List<OnlineCounterVo> queryListByCnd(
			OnlineCounterCnd onlineCounterCnd) throws Exception;

	/**
	 * <p>
	 * Description:新增访问日志<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月8日
	 * @param member
	 * @param system
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public OnlineCounter insertOnlineCounter(Member member, String system,
			HttpServletRequest request, HttpSession session) throws Exception;

	/**
	 * <p>
	 * Description:根据用户id查询上一次登录信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月8日
	 * @param userid
	 * @return OnlineCounter
	 * @throws Exception
	 */
	public OnlineCounterVo queryLastOnlineCounterByUserId(Integer userId) throws Exception;

	/**
	 * <p>
	 * Description: <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月12日
	 * @param userId
	 * @param page
	 * @return Page
	 */
	public Page queryPageByCnd(Integer userId, Page page);
}
