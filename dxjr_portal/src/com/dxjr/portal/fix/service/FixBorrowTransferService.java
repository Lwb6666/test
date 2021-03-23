package com.dxjr.portal.fix.service;

/**
 * 
 * @author WangQianJin
 * @title 定期宝转让服务类
 * @date 2015年9月15日
 */
public interface FixBorrowTransferService {

	/**
	 * 定期宝取消转让重构
	 * @author WangQianJin
	 * @title @param fixBorrowTransferId
	 * @title @param ip
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年9月14日
	 */
	public String saveTransferCancel(Integer fixBorrowTransferId, String ip) throws Exception;
}
