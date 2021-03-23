package com.dxjr.base.mapper;

import org.springframework.stereotype.Repository;

import com.dxjr.base.entity.AccountRechargeFeedback;

/**
 * 
 * <p>
 * Description:第三方支付平台反馈数据访问类<br />
 * </p>
 * 
 * @title AccountRechargeFeedbackMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年5月23日
 */
public interface BaseAccountRechargeFeedbackMapper {
	/**
	 * <p>
	 * Description:插入记录,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param account
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertEntity(AccountRechargeFeedback accountRechargeFeedback)
			throws Exception;

	/**
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param id
	 * @return
	 * @throws Exception
	 *             Account
	 */
	public AccountRechargeFeedback queryById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:更新对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param account
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateEntity(AccountRechargeFeedback accountRechargeFeedback)
			throws Exception;

}
