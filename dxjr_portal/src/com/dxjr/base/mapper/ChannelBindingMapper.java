package com.dxjr.base.mapper;

import com.dxjr.base.entity.ChannelBindingVo;

/**
 * @author WangQianJin
 * @title 渠道绑定映射
 * @date 2016年5月27日
 */
public interface ChannelBindingMapper {
	
	/**
	 * 新增渠道绑定信息
	 * @author WangQianJin
	 * @title @param channelBinding
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年5月27日
	 */
	public int insertEntity(ChannelBindingVo channelBinding) throws Exception;
	
	/**
	 * 根据用户ID查询渠道信息
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @date 2016年5月27日
	 */
	public ChannelBindingVo queryChannelByUserId(String hxyid);
	
	/**
	 * 修改渠道信息
	 * @author WangQianJin
	 * @title @param channelBinding
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年5月27日
	 */
	public int updateEntity(ChannelBindingVo channelBinding) throws Exception;
}
