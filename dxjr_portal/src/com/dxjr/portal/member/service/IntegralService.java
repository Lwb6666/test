package com.dxjr.portal.member.service;

import com.dxjr.portal.member.vo.IntegralVo;

/**
 * <p>
 * Description:积分等级业务类<br />
 * </p>
 * 
 * @title IntegralService.java
 * @package com.dxjr.integral.service
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
public interface IntegralService {
	/**
	 * <p>
	 * Description:初始化积分等级，新增,返回 主键<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public Integer insertIntegral(Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return IntegralVo
	 */
	public IntegralVo queryIntegralByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:更新用户等级<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月28日
	 * @param integralVo
	 * @throws Exception
	 *             void
	 */
	public void updateCreditLevel(IntegralVo integralVo) throws Exception;

}
