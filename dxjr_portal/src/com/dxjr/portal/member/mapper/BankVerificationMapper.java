package com.dxjr.portal.member.mapper;

import com.dxjr.portal.member.vo.BankcardVerification;



/**
 * <p>
 * Description:银行信息数据访问类<br />
 * </p>
 * @title BankInfoMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface BankVerificationMapper {
	int insert(BankcardVerification record);
	
	/**
	 * 根据用户ID查询今天请求次数
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @date 2016年5月19日
	 */
	public Integer queryTodayRequestNumByUid(Integer userId);
	
	/**
	 * 根据用户ID查询所有请求次数
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @date 2016年5月19日
	 */
	public Integer queryAllRequestNumByUid(Integer userId);;
}
