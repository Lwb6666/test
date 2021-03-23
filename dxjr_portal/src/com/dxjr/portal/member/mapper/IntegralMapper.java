package com.dxjr.portal.member.mapper;

import java.util.List;

import com.dxjr.portal.member.vo.IntegralCnd;
import com.dxjr.portal.member.vo.IntegralVo;

/**
 * <p>
 * Description:积分等级数据访问类<br />
 * </p>
 * 
 * @title IntegralMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月28日
 */
public interface IntegralMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param mobileApproCnd
	 * @return
	 * @throws Exception
	 *             List<IntegralVo>
	 */
	public List<IntegralVo> queryIntegralList(IntegralCnd integralCnd)
			throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param IntegralCnd
	 *            integralCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryIntegralCount(IntegralCnd integralCnd) throws Exception;
}
