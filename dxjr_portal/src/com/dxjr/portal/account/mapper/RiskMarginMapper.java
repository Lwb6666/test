package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.portal.account.vo.RiskMarginVo;

/**
 * @title RiskMarginMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author chenlu
 * @version 0.1 2014年8月6日
 */
public interface RiskMarginMapper {

	/**
	 * 查询风险保证金
	 * 
	 * @return
	 */
	public List<RiskMarginVo> queryRiskMargin() throws Exception;

	/**
	 * 昨日成交：散标，定期宝，标转，车转，以满标&满宝时间为准
	 * 
	 * @return
	 */
	@Select("SELECT SUM(yest) from "
			+ "(SELECT IFNULL(sum(ACCOUNT),0)yest from rocky_borrow where `STATUS`in(4,5,6,42) and DATEDIFF(CURDATE(),FROM_UNIXTIME(SUCCESS_TIME,'%Y-%m-%d'))=1 "
			+ "UNION SELECT IFNULL(sum(ACCOUNT),0)yest from t_fix_tender_real where DATEDIFF(CURDATE(),ADDTIME)=1 "
			+ "UNION SELECT IFNULL(sum(ACCOUNT),0)yest from rocky_b_transfer where `STATUS`=4 and DATEDIFF(CURDATE(),SUCCESS_TIME)=1 "
			+ "UNION SELECT IFNULL(sum(ACCOUNT),0)yest from t_first_transfer where `STATUS`=4 and DATEDIFF(CURDATE(),SUCCESS_TIME)=1)t")
	@ResultType(BigDecimal.class)
	public BigDecimal queryYestDeal();

}
