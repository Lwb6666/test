package com.dxjr.portal.account.mapper;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.BbsItemsVo;
import com.dxjr.portal.account.vo.InvestVo;

/**
 * <p>
 * Description:账户详情<br />
 * </p>
 * 
 * @title AccountDetailMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author 胡建盼
 * @version 0.1 2014年8月5日
 */
public interface AccountDetailMapper {
	public Integer countInvestVo(Map<String, Object> params);
	public List<InvestVo> queryInvestVoList(Map<String, Object> params,Page page);
	public List<InvestVo> queryInvestVoList(Map<String, Object> params);
	public List<BbsItemsVo> queryBbsItemsList(Map<String,Object> params,Page page);
	public Integer countBbsItemsList(Map<String,Object> params);
	public Integer isCurrentLookerhasPower(Integer userId);
	public Integer queryFixVoCount(Map<String, Object> params);
	public List<InvestVo> queryFixVoList(Map<String, Object> params,Page page);
}
