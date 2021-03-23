package com.dxjr.portal.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.account.mapper.ShareholderRankMapper;
import com.dxjr.portal.account.service.ShareholderRankService;
import com.dxjr.portal.account.vo.ShareholderRankCnd;
import com.dxjr.portal.account.vo.ShareholderRankVo;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:股东加权业务类实现<br />
 * </p>
 * 
 * @title ShareholderRankServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月21日
 */
@Service
public class ShareholderRankServiceImpl implements ShareholderRankService {

	@Autowired
	private ShareholderRankMapper shareholderRankMapper;

	@Override
	public ShareholderRankVo queryShareholderRankByUserId(Integer memberId)
			throws Exception {
		String date = shareholderRankMapper.findMaxAddtime();
		ShareholderRankCnd shareholderRankCnd = new ShareholderRankCnd();
		shareholderRankCnd.setUserId(memberId);
		shareholderRankCnd.setAddtime(date);
		return shareholderRankMapper
				.queryShareholderRankByUserId(shareholderRankCnd);
	}

	@Override
	public List<ShareholderRankVo> queryShareholderRankByType(String type)
			throws Exception {

		if (null == type || "".equals(type.trim())) {
			return null;
		}
		String date = shareholderRankMapper.findMaxAddtime();
		ShareholderRankCnd shareholderRankCnd = new ShareholderRankCnd();
		shareholderRankCnd.setAddtime(date);
		shareholderRankCnd.setType(type);
		return shareholderRankMapper
				.queryShareholderRankByType(shareholderRankCnd);
	}

	@Override
	public void shareholderRankTimer() throws Exception {
		// 格式：yyyy-MM-dd
		// 当前时间戳
		String date = DateUtils.getCurrentTimeStamp();
		// 初始化股东加权排名表数据
		shareholderRankMapper.initShareholderRank(date);

		// 自动更新当天的加权待收排名
		shareholderRankMapper.updateDayInterstRank(date);
		// 自动更新当天的累计总积分排名
		shareholderRankMapper.updateAccumulatepointsRank(date);
		// 自动更新当天的优先投标总额排名
		shareholderRankMapper.updateFirstTenderTotalRank(date);
		// 自动更新当天的推广有效人数排名
		shareholderRankMapper.updateExtensionNumberRank(date);

		// 自动更新当天的加权待收排名得分
		shareholderRankMapper.updateShareholderRankForDayInterstScore(date);
		// 更新当天的累计总积分排名得分
		shareholderRankMapper
				.updateShareholderRankForAccumulatepointsScore(date);
		// 更新当天的优先投标总额排名得分
		shareholderRankMapper
				.updateShareholderRankForFirstTenderTotalScore(date);
		// 更新当天的推广有效人数排名得分
		shareholderRankMapper
				.updateShareholderRankForExtensionNumberScore(date);
		// 更新当天的综合得分
		shareholderRankMapper.updateShareholderRankForTotalScore(date);

		// 更新当天综合排名
		shareholderRankMapper.updateTotalRank(date);
	}

	@Override
	public Map<String, String> queryNoRanksByUserId(int userId)
			throws Exception {
		return shareholderRankMapper.queryNoRanksByUserId(userId);
	}
}
