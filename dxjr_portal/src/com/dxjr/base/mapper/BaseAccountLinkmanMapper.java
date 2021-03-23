package com.dxjr.base.mapper;

import org.springframework.stereotype.Repository;

import com.dxjr.base.entity.AccountLinkman;

/**
 * 
 * <p>
 * Description:新闻公告类数据访问类<br />
 * </p>
 * @title BaseNewsNoticeMapper.java
 * @package com.dxjr.base.mapper 
 * @author yangshijin
 * @version 0.1 2014年4月23日
 */
public interface BaseAccountLinkmanMapper {
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
	public int insertEntity(AccountLinkman accountLinkman) throws Exception;

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
	public int updateEntity(AccountLinkman accountLinkman) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据userId查询关联人类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月4日
	 * @param userId
	 * @return
	 * @throws Exception
	 * AccountLinkman
	 */
	public AccountLinkman queryByUserId(Integer userId) throws Exception;

}
