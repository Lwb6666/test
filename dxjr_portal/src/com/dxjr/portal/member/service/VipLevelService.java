package com.dxjr.portal.member.service;

import com.dxjr.portal.member.vo.VipLevelVo;

/**
 * 
 * <p>
 * Description:VIP会员等级业务接口<br />
 * </p>
 * 
 * @title VipLevelService.java
 * @package com.dxjr.portal.member.service
 * @author yangshijin
 * @version 0.1 2015年1月14日
 */
public interface VipLevelService {

	/**
	 * 
	 * <p>
	 * Description:根据id查询对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param id
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询对象，并锁定<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param id
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryByIdForUpdate(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据用户id及type查询vip会员等级信息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param userId
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryByUserIdAndType(Integer userId, Integer type) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId获取用户是否是终身顶级会员<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月14日
	 * @param userId
	 * @return
	 * @throws Exception boolean
	 */
	public boolean getIsSvipByUserId(int userId) throws Exception;
}
