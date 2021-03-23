package com.dxjr.wx.favorite.service;

import com.dxjr.wx.favorite.vo.BidCountVo;

public interface TransferService {

	/**
	 * 首页-债转统计
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月7日
	 * @return
	 * @throws Exception BidCountVo
	 */
	public BidCountVo bidCount() throws Exception;

	/**
	 * 首页-债转统计-直通车
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月15日
	 * @return
	 * @throws Exception int
	 */
	public int firstCount() throws Exception;

}
