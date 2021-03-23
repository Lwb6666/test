package com.dxjr.portal.account.service;

import java.util.List;
import java.util.Map;

import com.dxjr.portal.account.vo.ShareholderRankVo;

/**
 * <p>
 * Description:股东加权业务类<br />
 * </p>
 * 
 * @title ShareholderRankService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月21日
 */
public interface ShareholderRankService {
	/**
	 * <p>
	 * Description:查询某人当天的综合排名信息.排序规则是 1,2,2,4(3被并列的2占用了)<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             ShareholderRankVo
	 */
	public ShareholderRankVo queryShareholderRankByUserId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:股东加权统计，查询某类型排名200名的记录。<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @param type
	 * @return
	 * @throws Exception
	 *             List<ShareholderRankVo>
	 */
	public List<ShareholderRankVo> queryShareholderRankByType(String type)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:每日更新股东加权排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @throws Exception
	 *             void
	 */
	public void shareholderRankTimer() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据用户Id查询是否在未排名名单中<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 */
	public Map<String, String> queryNoRanksByUserId(int userId)
			throws Exception;
}
