package com.dxjr.portal.curAccount.service;

import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.portal.curAccount.vo.CurInLaunchVo;

/****
 * 
 * 
 * <p>
 * Description:活期宝转入(投标)记录 BLL 接口 <br />
 * </p>
 * 
 * @title CurInService.java
 * @package com.dxjr.portal.curAccount.service
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
public interface CurInService {

	/**
     * 
     * <p>
     * Description:根据userId和date查询某天内的转入受限总额 <br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年5月5日
     * @param userId
     * @param date
     * @return
     * @throws Exception
     * BigDecimal
     */
    public BigDecimal queryNoDrawMoneyTotalByUserIdAndDate(Integer userId, Date date) throws Exception;
    
    /**
	 * <p>
	 * Description:保存活期宝转入信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param curInLaunchVo
	 * @return String
	 */
	public String saveCurrentIn(CurInLaunchVo curInLaunchVo) throws Exception;
}
