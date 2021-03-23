package com.dxjr.portal.member.mapper;

import com.dxjr.base.entity.BankinfoLog;

public interface BankinfoLogMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(BankinfoLog record);

	int insertOld(BankinfoLog record);

	BankinfoLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(BankinfoLog record);

	/**
	 * <p>
	 * Description:查询银行卡信息日志表-用户锁定的记录<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月24日
	 * @param userId
	 * @return BankinfoLog
	 */
	BankinfoLog queryBankCardLogLockByUserId(Integer userId);

	/**
	 * <p>
	 * Description:查询银行卡操作日志中的锁定记录(type=0的记录)<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月25日
	 * @param userId
	 * @return Integer
	 */
	int querytBankCardLock(Integer userId);

}