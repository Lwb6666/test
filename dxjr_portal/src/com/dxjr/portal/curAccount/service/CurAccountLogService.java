package com.dxjr.portal.curAccount.service;

import java.math.BigDecimal;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.vo.CurAccountLogCnd;

/****
 * 
 * 
 * <p>
 * Description:活期宝账户操作日志记录 BLL 接口 <br />
 * </p>
 * 
 * @title CurAccountLogService.java
 * @package com.dxjr.portal.curAccount.service
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
public interface CurAccountLogService {

	/****
	 * 
	 * <p>
	 * Description: 资金信息 - 分页 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月5日
	 * @param mapParam
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryCurAccountLogByPage(CurAccountLogCnd curAccountLogCnd, Page page) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:根据参数集合插入活期宝资金日志<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param curAccount 活期宝账户
	 * @param userId 用户ID
	 * @param money  交易金额
	 * @param remark  备注
	 * @param addIp 操作IP
	 * @param type  操作类型
	 * @param adduser 操作人ID
	 * @throws Exception
	 * void
	 */
	public void saveCurAccountLogByParams(CurAccount curAccount, Integer userId, BigDecimal money, String remark, String addIp, Integer type, Integer adduser)
			throws Exception;

}
