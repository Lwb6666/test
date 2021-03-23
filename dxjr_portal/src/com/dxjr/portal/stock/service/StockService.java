package com.dxjr.portal.stock.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.security.ShiroUser;

/**
 * 投资人期权信息
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockService.java
 * @package com.dxjr.portal.stock.service
 * @author huangpin
 * @version 0.1 2014年9月11日
 */
public interface StockService {

	/**
	 * 查询我的期权信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @param shiroUser
	 * @return ModelAndView
	 */
	public ModelAndView myStockInfo(ShiroUser shiroUser) throws Exception;

	/**
	 * 现金行权
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2016年9月27日
	 * @param payPwd
	 * @param addIp
	 * @param shiroUser
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox updateStockMoney(String payPwd, String addIp, ShiroUser shiroUser) throws Exception;
	
	/**
	 * 根据userId获取期权数据数量
	 * <p>
	 * Description:根据userId获取期权数据数量<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月25日
	 * @param userId
	 * @return
	 * int
	 */
	public int getCountByUserId(@Param(value = "userId") Integer userId);

}
