package com.dxjr.portal.curAccount.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.curAccount.mapper.CurNoworkDayMapper;
import com.dxjr.portal.curAccount.service.CurNoworkDayService;

/****
 * 
 * 
 * <p>
 * Description: 节假日BLL <br />
 * </p>
 * 
 * @title CurNoworkDayServiceImpl.java
 * @package com.dxjr.portal.curAccount.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月28日
 */
@Service
public class CurNoworkDayServiceImpl implements CurNoworkDayService {

	Logger logger = LoggerFactory.getLogger(CurNoworkDayServiceImpl.class);

	@Autowired
	CurNoworkDayMapper curNoworkDayMapper;

}
