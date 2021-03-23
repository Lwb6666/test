package com.dxjr.wx.favorite.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.wx.favorite.vo.BidCountVo;

public interface TenderService {

	/**
	 * 投标专区-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月3日
	 * @param p
	 * @return
	 * @throws Exception List<BorrowVo>
	 */
	public List<BorrowVo> bidList(Page p) throws Exception;

	/**
	 * 普通标统计
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
	 * 投标列表总数
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月19日
	 * @return
	 * @throws Exception Integer
	 */
	public Integer tenderBidCount() throws Exception;

}
