package com.dxjr.portal.curAccount.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurAccountVo;

/***
 * 
 * 
 * <p>
 * Description: 活期宝账户 BLL 接口 <br />
 * </p>
 * 
 * @title CapitalAccountService.java
 * @package com.dxjr.portal.curAccount.service
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
public interface CurAccountService {

	/**
	 * <p>
	 * Description: 根据userId 查询 活期宝 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param curAccountCnd
	 * @return
	 * @throws Exception
	 *             List<Map>
	 */
	public List<Map<String ,Object>> queryCurAccountListMap(CurAccountCnd curAccountCnd) throws Exception;
	
	
	/**
	 * <p>
	 * Description:根据用户ID查询活期宝账户信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @return CurAccount
	 */
	public CurAccountVo selectByUserId(Integer userId);

	/**
	 * 
	 * <p>
	 * Description:根据用户ID查询活期宝账户信息，并锁定<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @return CurAccount
	 */
	public CurAccountVo selectByUserIdForUpdate(Integer userId);

	/**
	 * 
	 * <p>
	 * Description:根据userId获取当前最大可使用金额（用于投标、认购债权转让、开通直通车、认购直通车转让等）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal getMaxUseMoneyByNow(Integer userId) throws Exception;
	
	/**
	 * <p>
	 * Description:查询加入活期宝的人数<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月21日
	 * @return
	 * Integer
	 */
	public Integer queryCurAccountCount();
}
