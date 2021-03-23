package com.dxjr.base.mapper;

import com.dxjr.base.entity.MailSendControl;

/**
 * 
 * <p>
 * Description:邮件发送控制数据访问类<br />
 * </p>
 * 
 * @title BaseMailSendControlMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2014年9月15日
 */
public interface BaseMailSendControlMapper {
	/**
	 * 
	 * <p>
	 * Description:插入记录到关联人表,返回新增的主键ID<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月15日
	 * @param mailSendControl
	 * @return
	 * @throws Exception int
	 */
	public int insertEntity(MailSendControl mailSendControl) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月15日
	 * @param id
	 * @return
	 * @throws Exception MailSendControl
	 */
	public MailSendControl queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月15日
	 * @param mailSendControl
	 * @return
	 * @throws Exception int
	 */
	public int updateEntity(MailSendControl mailSendControl) throws Exception;

}
