package com.dxjr.portal.lianlian.service;

import java.util.Map;

import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.lianlian.vo.LlWapBankcardInfo;
import com.dxjr.portal.lianlian.vo.LlWapBankcardUnbindRequest;
import com.dxjr.portal.lianlian.vo.LlWapPaymentRiskItem;
import com.dxjr.portal.member.vo.BankInfoVo;

/**
 * <p>
 * Description:手机连连支付service<br />
 * </p>
 * 
 * @title LianlianpayWapService.java
 * @package com.dxjr.portal.lianlian.service
 * @author justin.xu
 * @version 0.1 2015年3月26日
 */
public interface LianlianpayWapService {
	/**
	 * <p>
	 * Description:银行卡卡bin信息查询<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月26日
	 * @param card_no
	 * @return
	 * @throws Exception String
	 */
	public String querybankcard(String card_no) throws Exception;

	/**
	 * <p>
	 * Description:银行卡卡bin信息查询,返回银行卡信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月26日
	 * @param card_no
	 * @return
	 * @throws Exception LlWapBankcardInfo
	 */
	public LlWapBankcardInfo queryBankcardInfo(String card_no) throws Exception;

	/**
	 * <p>
	 * Description:执行银行卡解绑操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月7日
	 * @param llWapBankcardUnbindRequest
	 * @return
	 * @throws Exception String
	 */
	public String saveBankcardUnbind(LlWapBankcardUnbindRequest llWapBankcardUnbindRequest) throws Exception;

	/**
	 * <p>
	 * Description:保存连连支付充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月26日
	 * @param topupMoneyVo
	 * @param bankInfoVo
	 * @param llWapPaymentRiskItem
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> savesend(TopupMoneyVo topupMoneyVo, BankInfoVo bankInfoVo, LlWapPaymentRiskItem llWapPaymentRiskItem, String returnURL) throws Exception;
}
