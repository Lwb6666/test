package com.dxjr.portal.member.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dxjr.portal.member.vo.AccountLinkmanCnd;
import com.dxjr.portal.member.vo.AccountLinkmanVo;

/**
 * <p>
 * Description:用户关联人数据访问类<br />
 * </p>
 * 
 * @title AccountLinkmanMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface AccountLinkmanMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param accountLinkmanCnd
	 * @return
	 * @throws Exception
	 *             List<AccountLinkmanVo>
	 */
	public List<AccountLinkmanVo> queryAccountLinkmanList(
			AccountLinkmanCnd accountLinkmanCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param AccountLinkmanCnd
	 *            accountLinkman
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryAccountLinkmanCount(AccountLinkmanCnd accountLinkman)
			throws Exception;
}
