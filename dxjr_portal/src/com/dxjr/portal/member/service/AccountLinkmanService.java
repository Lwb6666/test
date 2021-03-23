package com.dxjr.portal.member.service;

import java.util.Map;

import com.dxjr.base.entity.AccountLinkman;
import com.dxjr.portal.member.vo.AccountLinkmanVo;

/**
 * <p>
 * Description:用户关联人业务<br />
 * </p>
 * 
 * @title AccountLinkmanService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface AccountLinkmanService {

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return AccountLinkmanVo
	 */
	public AccountLinkmanVo queryAccountLinkmanByUserId(Integer memberId)
			throws Exception;

	
	/**
	 * 
	 * <p>
	 * Description:插入记录到关联人表,返回新增的主键ID<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param accountLinkman
	 * @return
	 * @throws Exception
	 * int
	 */
	public int insertAccountLinkman(AccountLinkman accountLinkman) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询关联人类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param id
	 * @return
	 * @throws Exception
	 * AccountLinkman
	 */
	public AccountLinkman queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新关联人类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param accountLinkman
	 * @return
	 * @throws Exception
	 * int
	 */
	public int updateAccountLinkman(AccountLinkman accountLinkman) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据id查询关联人类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param id
	 * @return
	 * @throws Exception
	 * AccountLinkman
	 */
	public AccountLinkman queryByUserId(Integer userId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:新增或更新关联人<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param userId
	 * @param map
	 * @return
	 * @throws Exception
	 * int
	 */
	public int saveOrUpdateLinkman(Integer userId, String name, String mobile, String email, String relationship, String ip) throws Exception; 
}
