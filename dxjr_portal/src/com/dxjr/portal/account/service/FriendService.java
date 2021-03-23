package com.dxjr.portal.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.FriendCnd;
import com.dxjr.portal.account.vo.InviteRankVo;

/**
 * 
 * <p>
 * Description:我要推广接口类<br />
 * </p>
 * 
 * @title FriendService.java
 * @package com.dxjr.portal.account.service
 * @author yangshijin
 * @version 0.1 2014年6月5日
 */
public interface FriendService {

	public Page queryInviteDetailsByUserId(int userId, int pageNo, int pageSize);

	/**
	 * <p>
	 * Description:根据用户ID查询推广的人数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryAllFriendCountByUserId(int userId) throws Exception;

	/**
	 * <p>
	 * Description:根据userId查询推广的用户记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @param userId
	 * @return List<MemberVo>
	 */
	public Page queryAllFriendByUserId(int userId, int pageNo, int pageSize) throws Exception;

	/**
	 * 查询某人的推广用户记录获得的奖励
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月25日
	 * @param userId
	 * @return
	 * @throws Exception Double
	 */
	public Double queryAllFriendSumMoneyByUserId(int userId) throws Exception;

	/**
	 * <p>
	 * Description:推广成功奖励10元<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月6日
	 * @throws Exception void
	 */
	public void awardInviter();

	/**
	 * <p>
	 * Description:某用户(当月,当季,年度)推荐人数排名<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月18日
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return InviteRankVo
	 */
	public InviteRankVo queryInviteNumRank(Integer userId, Date startDate, Date endDate);

	/**
	 * <p>
	 * Description:用户(当月,当季,年度)推荐人数排名<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月18日
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @return InviteRankVo
	 */
	public Page queryInviteNumRank(Date startDate, Date endDate, Page page);

	/**
	 * <p>
	 * Description:某用户(当月,当季,年度)共享奖排名<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月18日
	 * @param userId
	 * @param month
	 * @param interestType
	 * @return InviteRankVo
	 */
	public InviteRankVo queryInviteInterestRank(Integer userId, Integer month, String interestType);

	/**
	 * <p>
	 * Description:用户(当月,当季,年度)共享奖排名<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月18日
	 * @param month
	 * @param interestType
	 * @param page
	 * @return Page
	 */
	public Page queryInviteInterestRank(Integer month, String interestType, Page page);


	/**
	 * <p>
	 * Description:某用户(当月,当季,年度)推荐人员明细列表<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月18日
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @return Page
	 */
	public Page queryInviteNumDetail(Integer userId, Date startDate, Date endDate, Page page);

	/**
	 * <p>
	 * Description:某用户的历史推荐共享奖明细<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月11日
	 * @param userId
	 * @return List<InviteRankVo>
	 */
	public List<InviteRankVo> queryInviteInterestDetailList(Integer userId);

	/**
	 * <p>
	 * Description:查询某用户[月度,季度,年度]获取的某项奖励<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年12月15日
	 * @param userId
	 * @param type
	 * @param month
	 * @return BigDecimal
	 */
	public BigDecimal queryInviteIssuedReward(Integer userId, Integer type, Integer month);
	
	
	public Page queryInviteDetailsByUserIdNew(FriendCnd friendCnd, int pageNo, int pageSize);
}
