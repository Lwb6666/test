package com.dxjr.base.memberaccumulatepoints.service;

import com.dxjr.base.entity.MemberAccumulatePoints;
import com.dxjr.common.page.Page;

/**
 * <p>
 * Description:用户积分记录<br />
 * </p>
 * @title MemberAccumulatePointsService.java
 * @package com.dxjr.base.memberaccumulatepoints.service
 * @author justin.xu
 * @version 0.1 2014年4月17日
 */
public interface MemberAccumulatePointsService {
	/**
	 * <p>
	 * Description:新增用户积分，用于所有的添加积分业务<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月17日
	 * @param memberAccumulatePoints
	 * @return
	 * @throws Exception Integer
	 */
	public Integer insertMemberAccumulatePoints(MemberAccumulatePoints memberAccumulatePoints) throws Exception;

	/**
	 * 获取用户昨日元宝
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月19日
	 * @param userId
	 * @return int
	 */
	public int getLastdaySycee(int userId);

	/**
	 * 我的元宝-不查交易金额为0的
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月19日
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page mySyceeList(int userId, int pageNo, int pageSize);

}
