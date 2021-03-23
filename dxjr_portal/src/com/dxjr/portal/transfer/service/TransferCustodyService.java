package com.dxjr.portal.transfer.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;
import com.dxjr.portal.transfer.vo.SubscribeTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.security.ShiroUser;

import java.util.List;

/**
 * 存管转让
 */
public interface TransferCustodyService {

	public String saveCustodyTransfer(SubscribeTransferVo stf, Integer userId, String realIpAddr,
									  Integer tenderTypeManual, Integer checkUserId,
									  String checkRemark, String addIp,ShiroUser shiroUser) throws Exception;

}
