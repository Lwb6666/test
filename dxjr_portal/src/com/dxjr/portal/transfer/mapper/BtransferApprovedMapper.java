package com.dxjr.portal.transfer.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.transfer.entity.TransferApproved;

public interface BtransferApprovedMapper {

	int insert(TransferApproved approved);

	void updateStute(@Param("id") Integer transferApprovedId, @Param("status") Integer transferFirstStatuPass);

}