package com.dxjr.wx.favorite.mapper;

import com.dxjr.wx.favorite.vo.BidCountVo;

public interface TransferMapper {

	/**
	 * 投标专区-统计
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月6日
	 * @return
	 * @throws Exception BidCountVo
	 */
	public BidCountVo bidCount() throws Exception;

	/**
	 * 转让中的直通车
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
