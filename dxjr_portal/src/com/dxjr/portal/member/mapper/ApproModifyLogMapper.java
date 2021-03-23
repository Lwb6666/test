package com.dxjr.portal.member.mapper;

import com.dxjr.portal.member.vo.ApproModifyLog;


/**
 * <p>
 * Description:认证修改记录日志数据访问类<br />
 * </p>
 * 
 * @title ApproModifyLogMapper.java
 * @package com.dxjr.console.member.mapper
 * @author justin.xu
 * @version 0.1 2014年10月18日
 */
public interface ApproModifyLogMapper {

	/**
	 * <p>
	 * Description:插入记录,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param approModifyLog
	 * @return
	 * @throws Exception int
	 */
	public Integer insertEntity(ApproModifyLog approModifyLog) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId查询更新手机号的记录数量<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年5月4日
	 * @param userId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public Integer queryCountForUpdateMObile(Integer userId) throws Exception;
}
