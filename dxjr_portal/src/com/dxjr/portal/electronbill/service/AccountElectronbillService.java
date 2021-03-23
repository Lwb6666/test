package com.dxjr.portal.electronbill.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.electronbill.entity.ElectronBill;

/**
 * 
 * <p>
 * Description:我的账户电子账单<br />
 * </p>
 * @title AccountElectronbillService.java
 * @package com.dxjr.portal.electronbill.service 
 * @author jianxin.chen
 * @version 0.1 2016年8月10日
 */
public interface AccountElectronbillService {
	ElectronBill AccountElectronbill(int userId,String yearMonth);
}
