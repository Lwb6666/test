package com.dxjr.portal.fix.mapper;

import java.math.BigDecimal;

import com.dxjr.base.entity.FixRepaymentrecord;

/**
 * <p>
 * Description:定期宝待还记录数据库接口类<br />
 * </p>
 * 
 * @title FixRepaymentrecordMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public interface FixRepaymentrecordMapper {
	/**
	 * 添加定期宝待还记录
	 * @return
	 */
	public Integer insertFixRepaymentrecord(FixRepaymentrecord fixRepaymentrecord) throws Exception;
	
	/**
	 * 统计查询已经付给用户利息总金额
	 * @return
	 */
	public BigDecimal queryTotalInterest() throws Exception;
	
	/**
	 * 统计查询累计加入(收益中和已退出的)总金额
	 * @return
	 * @throws Exception
	 */
	public BigDecimal querySumCapital() throws Exception;
}
