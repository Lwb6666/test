package com.dxjr.portal.member.service;

/**
 * 
 * <p>
 * Description:黑名单业务处理接口<br />
 * </p>
 * 
 * @title BlackListSevice.java
 * @package com.dxjr.console.member.service
 * @author yangshijin
 * @version 0.1 2015年1月25日
 */
public interface BlackListSevice {

	/**
	 * 
	 * <p>
	 * Description:根据userId及type判断某人某操作类型是否被禁止<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月27日
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception boolean
	 */
	public boolean judgeBlackByUserIdAndType(int userId, int type) throws Exception;
}
