package com.dxjr.portal.account.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dxjr.portal.account.vo.ShareholderRankCnd;
import com.dxjr.portal.account.vo.ShareholderRankVo;

/**
 * <p>
 * Description:股东加权数据访问类<br />
 * </p>
 * 
 * @title ShareholderRankMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public interface ShareholderRankMapper {
	/**
	 * 
	 * <p>
	 * Description:查询某人当天的综合排名信息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param shareholderRankCnd
	 * @return
	 * @throws Exception
	 *             ShareholderRankVo
	 */
	public ShareholderRankVo queryShareholderRankByUserId(
			ShareholderRankCnd shareholderRankCnd) throws Exception;

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
	public List<ShareholderRankVo> queryShareholderRankByType(
			ShareholderRankCnd shareholderRankCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:初始化股东加权排名表数据<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @throws Exception
	 *             void
	 */
	public void initShareholderRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的加权待收排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateDayInterstRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的累计总积分排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateAccumulatepointsRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的优先投标总额排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateFirstTenderTotalRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的推广有效人数排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateExtensionNumberRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的加权待收排名得分<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateShareholderRankForDayInterstScore(String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的累计总积分排名得分<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateShareholderRankForAccumulatepointsScore(String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的优先投标总额排名得分<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateShareholderRankForFirstTenderTotalScore(String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的推广有效人数排名得分<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月4日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateShareholderRankForExtensionNumberScore(String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动更新当天的综合得分<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateShareholderRankForTotalScore(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新当天综合排名<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @param date
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateTotalRank(String date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取当天最新的插入日期<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年7月11日
	 * @return String
	 */
	public String findMaxAddtime() throws Exception;

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
