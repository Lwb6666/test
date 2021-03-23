package com.dxjr.portal.tzjinterface.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.tzjinterface.exception.BusinessException;
import com.dxjr.portal.tzjinterface.mapper.EncryptionKeyMapper;
import com.dxjr.portal.tzjinterface.service.EncryptionKeyService;
import com.dxjr.portal.tzjinterface.vo.EncryptionKeyVo;

@Service
public class EncryptionKeyServiceImpl implements EncryptionKeyService{

	@Autowired
	EncryptionKeyMapper encryptionKeyMapper;
	@Override
	public EncryptionKeyVo queryNewestEncryptionKey(Integer keyId)
			throws BusinessException {
		return encryptionKeyMapper.queryNewestEncryptionKey(keyId);
	}

	

}
