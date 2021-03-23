package com.dxjr.wx.favorite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.wx.favorite.mapper.TransferMapper;
import com.dxjr.wx.favorite.service.TransferService;
import com.dxjr.wx.favorite.vo.BidCountVo;

@Service(value = "wxTransferService")
public class TransferServiceImpl implements TransferService {

	@Autowired
	private TransferMapper transferMapper;

	public BidCountVo bidCount() throws Exception {
		return transferMapper.bidCount();
	}

	public int firstCount() throws Exception {
		return transferMapper.firstCount();
	}

}
