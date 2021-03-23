package com.dxjr.portal.fix.mapper;

import com.dxjr.base.entity.FixAccount;
import com.dxjr.portal.fix.vo.FixAccountVo;


/**
 * <p>
 * Description:定期宝账户数据库接口类<br />
 * </p>
 * 
 * @title FixAccountMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public interface FixAccountMapper {
	/**
	 * 修改定期宝账户
	 * @param fixAccount
	 */
	public void updateFixAccount(FixAccount fixAccount) throws Exception;
	
	/**
	 * 根据定期宝ID获取账户信息
	 * @author WangQianJin
	 * @title @param fixBorrowId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public FixAccountVo queryFixAccountByFixBorrowId(Integer fixBorrowId) throws Exception;
	
	/**
	 * 根据ID修改定期宝账户
	 * @author WangQianJin
	 * @title @param fixAccount
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public void updateFixAccountById(FixAccount fixAccount) throws Exception;
	
}
