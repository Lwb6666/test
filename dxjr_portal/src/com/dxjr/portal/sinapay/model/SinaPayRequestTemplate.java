package com.dxjr.portal.sinapay.model;

import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;

import com.dxjr.portal.sinapay.protocol.SinaPayRequest;

public class SinaPayRequestTemplate extends Properties {
	private static final long serialVersionUID = 1L;

	public SinaPayRequest createDefaultRequest() {
		SinaPayRequest request = new SinaPayRequest();

		try {
			BeanUtils.copyProperties(request, this);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		return request;
	}

}
