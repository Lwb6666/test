package com.dxjr.portal.member.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dxjr.portal.member.vo.VIPApproCnd;
import com.dxjr.portal.member.vo.VIPApproVo;

/**
 * <p>
 * Description:VIP认证数据访问类<br />
 * </p>
 * 
 * @title VIPApproMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface VIPApproMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param vipApproCnd
	 * @return
	 * @throws Exception
	 *             List<VIPApproVo>
	 */
	public List<VIPApproVo> queryVIPApproList(VIPApproCnd vipApproCnd)
			throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param VIPApproCnd
	 *            vipApproCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryVIPApproCount(VIPApproCnd vipApproCnd) throws Exception;
}
