package com.dxjr.portal.fix.mapper;

import java.util.List;

import com.dxjr.portal.fix.vo.FixTenderSubscribeVo;
import com.dxjr.portal.fix.vo.FixTenderTransferCnd;

public interface FixTenderSubscribeMapper {
    
	/**
	 * 根据转让ID和状态获取认购记录
	 * @author WangQianJin
	 * @title @param fixTenderTransferCnd
	 * @title @return
	 * @date 2015年9月15日
	 */
	public List<FixTenderSubscribeVo> queryTenderSubscribeListByTransferId(FixTenderTransferCnd fixTenderTransferCnd);
	
	/**
	 * 根据ID修改认购状态为认购失败
	 * @author WangQianJin
	 * @title @param id
	 * @date 2015年9月15日
	 */
	public void updateStatusForSubFailureById(FixTenderSubscribeVo fixTenderSubscribeVo);
}