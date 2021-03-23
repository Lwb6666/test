package com.dxjr.wx.account.mapper;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.wx.account.vo.WxRedAccountVo;

/**
 * 红包账户表
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title RedAccountMapper.java
 * @package com.dxjr.portal.red.entity
 * @author huangpin
 * @version 0.1 2015年11月3日
 */
public interface WxRedAccountMapper {
	/**
	 * <p>
	 * Description:显示未开启和未使用的红包。<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月4日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             List<RedAccount>
	 */
	List<WxRedAccountVo> queryOpenRed(Integer userId) throws Exception;
	
	/**
	 * 显示已过期或不能使用的红包
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<WxRedAccountVo> queryByExpiredRed(Integer userId) throws Exception;
	
	/**
	 * 获取可使用的红包数量
	 * @param redAccountCnd
	 * @return
	 * @throws Exception
	 */
	int queryOpenRedCount(Integer userId) throws Exception;
	
	/**
	 * 获取当前用户可用红包
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<WxRedAccountVo> getMyRuleReds(Map map) throws Exception;

	/**
	 * 统计可用红包总数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryTotalRed(WxRedAccountVo wxRedAccountVo);
	
	/**
	 * 统计可用红包总数
	 * 
	 * @param id
	 * @return
	 */
	public List<WxRedAccountVo> queryRedByPage(WxRedAccountVo wxRedAccountVo, Page page);
	
	/**
	 * 统计不可用红包总数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryTotalCloseRed(WxRedAccountVo wxRedAccountVo);
	
	/**
	 * 统计不可用红包总数
	 * 
	 * @param id
	 * @return
	 */
	public List<WxRedAccountVo> queryCloseRedByPage(WxRedAccountVo wxRedAccountVo, Page page);
	
}
