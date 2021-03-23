package com.dxjr.portal.member.mapper;

import java.util.List;

import com.dxjr.portal.member.vo.VIPApplyCnd;
import com.dxjr.portal.member.vo.VIPApplyVo;

/**
 * 
 * <p>
 * Description:VIP申请数据访问类<br />
 * </p>
 * 
 * @title VIPApplyMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author yangshijin
 * @version 0.1 2014年8月7日
 */
public interface VIPApplyMapper {
	/**
	 * 
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月7日
	 * @param vipApplyCnd
	 * @return
	 * @throws Exception
	 *             List<VIPApplyVo>
	 */
	public List<VIPApplyVo> queryVIPApplyList(VIPApplyCnd vipApplyCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月7日
	 * @param vipApplyCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryVIPApplyCount(VIPApplyCnd vipApplyCnd) throws Exception;
}
