package com.dxjr.portal.fuiou.service;

import com.dxjr.portal.fuiou.vo.FuiouPayBackVo;
import com.dxjr.portal.fuiou.vo.FuiouPayVo;
import com.dxjr.security.ShiroUser;

public interface FuiouPayService {

	/**
	 * 生成待付款支付记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param vo
	 * @param ip
	 * @param user
	 * @return
	 * @throws Exception int
	 */
	public int saveRecharge(FuiouPayVo vo, String ip, ShiroUser user) throws Exception;

	/**
	 * 更新支付记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param vo
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	public String updateRecharge(FuiouPayBackVo vo, String ip) throws Exception;

	/**
	 * 更新支付记录-补单
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @param order_id
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	public String updateRecharge(String order_id, String ip) throws Exception;
}
