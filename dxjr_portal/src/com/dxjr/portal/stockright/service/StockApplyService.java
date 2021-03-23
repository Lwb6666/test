package com.dxjr.portal.stockright.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.stockright.entity.ApplyInfo;
import com.dxjr.portal.stockright.entity.ShareholderRoster;
import com.dxjr.portal.stockright.entity.StockApprove;
import com.dxjr.portal.stockright.vo.StockApplyRequest;


/****
 * 股权账户信息接口
 * <p>
 * Description:用户申请股权Service<br />
 * </p>
 * @title StockAccountService.java
 * @package com.dxjr.portal.stockright.service 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
public interface StockApplyService {
	/**
	 * 
	 * <p>
	 * Description:股权申请时，验证用户信息<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-16
	 * @param userId
	 * @return
	 * Map<String,Object>
	 */
	Map<String,Object> checkUserInfo(Integer userId);
	
	/**
	 * 
	 * <p>
	 * Description:保存用过股权登录申请<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-16
	 * @param request
	 * @return
	 * @throws Exception
	 * Integer
	 */
	Integer saveApply(ApplyInfo request)throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:查询股权申请转让权限<br />
	 * </p>
	 * @author xiaofei.li
	 * @version 0.1 2016-5-16
	 * @param userId
	 * @return
	 * Integer
	 */
	ApplyInfo querySignOutApply(int userId);
	
	/***
	 * 查询申请列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-13
	 * @param params
	 * @param page
	 * @return
	 * @throws Exception
	 * Page
	 */
	public Page queryApplyInfoList(StockApplyRequest seach, Page page);
	/***
	 * 查询审核记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-13
	 * @param record
	 * @return
	 * List<StockApprove>
	 */
	
	public List<StockApprove> findApplyApprove(StockApplyRequest record);
	
	/****
	 * 查询申请信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-15
	 * @param record
	 * @return
	 * int
	 */
	public ApplyInfo findApplyList(StockApplyRequest record);
	
	/***
	 * 统计审核信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-17
	 * @param record
	 * @return
	 * Integer
	 */
	public Integer countApplyInfo(Integer userid);
	
	public Integer checkBlackList(StockApplyRequest request) ;
	/****
	 * 查询股东花名册
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author xinwugang
	 * @version 0.1 2016-5-19
	 * @param record
	 * @return
	 * ShareholderRoster
	 */
	public ShareholderRoster selectShareholder(ShareholderRoster record);
	
	
	public Boolean checkBlankUser(int userId);
	
}
