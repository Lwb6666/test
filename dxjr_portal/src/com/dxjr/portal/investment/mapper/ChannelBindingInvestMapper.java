package com.dxjr.portal.investment.mapper;

import com.dxjr.portal.investment.vo.ChannelBindingInvestVo;


public interface ChannelBindingInvestMapper {

	/**
	 * 新增渠道绑定信息
	 * @author JingBinbin
	 * @title @param channelBindingInvest
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年7月1日
	 */
	public int insertEntityInfo(ChannelBindingInvestVo channelBindingInvest);
	
	/**
	 * 根据用户ID查询渠道信息
	 * @author JingBinbin
	 * @title @param userId
	 * @title @return
	 * @date 2016年7月1日
	 */
	public ChannelBindingInvestVo queryChannelByUserId(String hxyid);  
	
	
	/**
	 * 根据用户ID查询渠道信息
	 * @author JingBinbin
	 * @title @param userId
	 * @title @return
	 * @date 2016年7月1日
	 */
	public ChannelBindingInvestVo queryChannelBypinId(String hxyid);
	
	/**
	 * 根据用户名查询渠道信息
	 * @author JingBinbin
	 * @title @param userId
	 * @title @return
	 * @date 2016年7月1日
	 */
	public ChannelBindingInvestVo queryBindingInfo(ChannelBindingInvestVo cbiv);
	
	/**
	 * 根据用户手机号查询渠道信息
	 * @author JingBinbin
	 * @title @param userId
	 * @title @return
	 * @date 2016年7月1日
	 */
	public Integer queryChannelByUserPhone(ChannelBindingInvestVo investVo);
	
	/**
	 * 修改渠道信息
	 * @author JingBinbin
	 * @title @param channelBindingInvest
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年7月1日  updateBinding
	 */
	public int updateEntity(ChannelBindingInvestVo channelBindingInvest) throws Exception;
	
	/**
	 * 修改绑定状态
	 * @author JingBinbin
	 * @title @param channelBindingInvest
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年7月1日      
	 */
	public int updateBinding(ChannelBindingInvestVo channelBindingInvest) throws Exception;
	
	/**
	 * 修改老用户信息
	 * @author JingBinbin
	 * @title @param channelBindingInvest
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年7月1日      updateEntityDl
	 */
	public int updateEntityDl(ChannelBindingInvestVo channelBindingInvest) throws Exception;
	
}
