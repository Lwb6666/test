package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.InviterMapper;
import com.dxjr.portal.account.service.FriendService;
import com.dxjr.portal.account.vo.FriendCnd;
import com.dxjr.portal.account.vo.InviteRankVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * 
 * <p>
 * Description:我要推广业务实现类<br />
 * </p>
 * 
 * @title FriendServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author yangshijin
 * @version 0.1 2014年6月5日
 */
@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private InviterMapper inviterMapper;
	@Autowired
	private CacheManager cacheManager;

	@Override
	public Page queryAllFriendByUserId(int userId, int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalCount = queryAllFriendCountByUserId(userId);
		page.setTotalCount(totalCount);
		FriendCnd friendCnd = new FriendCnd();
		friendCnd.setUserId(userId);
		List<MemberVo> list = memberMapper.queryAllFriendForPageByUserId(friendCnd, page);
		page.setResult(list);
		return page;
	}

	public Page queryInviteDetailsByUserId(int userId, int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setTotalCount(inviterMapper.countInviteDetailsByUserId(userId));
		page.setResult(inviterMapper.queryInviteDetailsByUserId(userId, page));
		return page;
	}

	public InviteRankVo queryInviteNumRank(Integer userId, Date startDate, Date endDate) {
		List<InviteRankVo> list = this.queryInviteNumRankList(startDate, endDate);
		if (!list.isEmpty()) {
			for (InviteRankVo inviteRankVo : list) {
				if (inviteRankVo.getUserId().equals(userId)) {
					return inviteRankVo;
				}
			}
		}
		return InviteRankVo.EMPTY;
	}

	public Page queryInviteNumRank(Date startDate, Date endDate, Page page) {
		page.setTotalCount(this.countInviteNumRankList(startDate, endDate));
		if (page.getTotalPage() >= page.getPageNo()) {
			List<InviteRankVo> result = this.queryInviteNumRankList(startDate, endDate);
			int endIdx = page.getPageNo() * page.getPageSize() > result.size() ? result.size() : page.getPageNo() * page.getPageSize();
			page.setResult(result.subList((page.getPageNo() - 1) * page.getPageSize(), endIdx));
		}
		return page;
	}
	
	public InviteRankVo queryInviteInterestRank(Integer userId, Integer month, String interestType) {
		List<InviteRankVo> list = this.queryInviteInterestRankList(month, interestType);
		if (!list.isEmpty()) {
			for (InviteRankVo inviteRankVo : list) {
				if (inviteRankVo.getUserId().equals(userId)) {
					return inviteRankVo;
				}
			}
		}
		return InviteRankVo.EMPTY;
	}

	public Page queryInviteInterestRank(Integer month, String interestType, Page page) {
		page.setTotalCount(this.countInviteInterestRankList(month, interestType));
		if (page.getTotalPage() >= page.getPageNo()) {
			List<InviteRankVo> result = this.queryInviteInterestRankList(month, interestType);
			int endIdx = page.getPageNo() * page.getPageSize() > result.size() ? result.size() : page.getPageNo() * page.getPageSize();
			page.setResult(result.subList((page.getPageNo() - 1) * page.getPageSize(), endIdx));
		}
		return page;
	}

	private int countInviteNumRankList(Date startDate, Date endDate) {
		String key1 = (startDate == null ? "null" : startDate.getTime()) + "-" + (endDate == null ? "null" : endDate.getTime()) + "-num-count-timer";
		String key2 = (startDate == null ? "null" : startDate.getTime()) + "-" + (endDate == null ? "null" : endDate.getTime()) + "-num-count";
		boolean isSameDay = false;

		Cache cache = cacheManager.getCache("inviteCache");
		ValueWrapper vw1 = cache.get(key1);
		ValueWrapper vw2 = cache.get(key2);

		if (vw1 != null) {
			long time = (long) vw1.get();
			if (!(isSameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(time), new Date()))) {
				cache.evict(key1);
			}
		}
		if (!isSameDay) {
			cache.put(key1, System.currentTimeMillis());
		}

		if (vw2 != null) {
			if (isSameDay) {
				return (int) vw2.get();
			} else {
				cache.evict(key2);
			}
		}

		int count = inviterMapper.countInviteNumRankList(startDate, endDate);
		cache.put(key2, count);
		return count;
	}

	@SuppressWarnings("unchecked")
	private List<InviteRankVo> queryInviteNumRankList(Date startDate, Date endDate) {
		String key1 = (startDate == null ? "null" : startDate.getTime()) + "-" + (endDate == null ? "null" : endDate.getTime()) + "-num-list-timer";
		String key2 = (startDate == null ? "null" : startDate.getTime()) + "-" + (endDate == null ? "null" : endDate.getTime()) + "-num-list";
		boolean isSameDay = false;

		Cache cache = cacheManager.getCache("inviteCache");
		ValueWrapper vw1 = cache.get(key1);
		ValueWrapper vw2 = cache.get(key2);

		if (vw1 != null) {
			long time = (long) vw1.get();
			if (!(isSameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(time), new Date()))) {
				cache.evict(key1);
			}
		}
		if (!isSameDay) {
			cache.put(key1, System.currentTimeMillis());
		}
		
		if (vw2 != null) {
			if (isSameDay) {
				return (List<InviteRankVo>) vw2.get();
			} else {
				cache.evict(key2);
			}
		}

		List<InviteRankVo> ret = inviterMapper.queryInviteNumRankList(startDate, endDate);
		cache.put(key2, ret);
		return ret;
		
	}

	private int countInviteInterestRankList(Integer month, String interestType) {
		String key1 = (month == null ? "null" : month) + "-" + (interestType == null ? "null" : interestType) + "-interest-count-timer";
		String key2 = (month == null ? "null" : month) + "-" + (interestType == null ? "null" : interestType) + "-interest-count";
		boolean isSameDay = false;

		Cache cache = cacheManager.getCache("inviteCache");
		ValueWrapper vw1 = cache.get(key1);
		ValueWrapper vw2 = cache.get(key2);

		if (vw1 != null) {
			long time = (long) vw1.get();
			if (!(isSameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(time), new Date()))) {
				cache.evict(key1);
			}
		}
		if (!isSameDay) {
			cache.put(key1, System.currentTimeMillis());
		}

		if (vw2 != null) {
			if (isSameDay) {
				return (int) vw2.get();
			} else {
				cache.evict(key2);
			}
		}

		int count = inviterMapper.countInviteInterestRankList(month, interestType);
		cache.put(key2, count);
		return count;
	}

	@SuppressWarnings("unchecked")
	private List<InviteRankVo> queryInviteInterestRankList(Integer month, String interestType) {
		String key1 = (month == null ? "null" : month) + "-" + (interestType == null ? "null" : interestType) + "-interest-list-timer";
		String key2 = (month == null ? "null" : month) + "-" + (interestType == null ? "null" : interestType) + "-interest-list";
		boolean isSameDay = false;

		Cache cache = cacheManager.getCache("inviteCache");
		ValueWrapper vw1 = cache.get(key1);
		ValueWrapper vw2 = cache.get(key2);

		if (vw1 != null) {
			long time = (long) vw1.get();
			if (!(isSameDay = org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(time), new Date()))) {
				cache.evict(key1);
			}
		}
		if (!isSameDay) {
			cache.put(key1, System.currentTimeMillis());
		}

		if (vw2 != null) {
			if (isSameDay) {
				return (List<InviteRankVo>) vw2.get();
			} else {
				cache.evict(key2);
			}
		}

		List<InviteRankVo> ret = inviterMapper.queryInviteInterestRankList(month, interestType);
		cache.put(key2, ret);
		return ret;
	}

	public Page queryInviteNumDetail(Integer userId, Date startDate, Date endDate, Page page) {
		page.setTotalCount(inviterMapper.countInviteNumDetailList(userId, startDate, endDate));
		if (page.getTotalPage() >= page.getPageNo()) {
			page.setResult(inviterMapper.queryInviteNumDetailList(userId, startDate, endDate, page));
		}
		return page;
	}

	public List<InviteRankVo> queryInviteInterestDetailList(Integer userId) {
		return inviterMapper.queryInviteInterestDetailList(userId);
	}

	public BigDecimal queryInviteIssuedReward(Integer userId, Integer type, Integer month) {
		return inviterMapper.queryInviteIssuedReward(userId, type, month);
	}

	@Override
	public Integer queryAllFriendCountByUserId(int userId) throws Exception {
		return memberMapper.queryAllFriendCountByUserId(userId);
	}

	public Double queryAllFriendSumMoneyByUserId(int userId) throws Exception {
		return memberMapper.queryAllFriendSumMoneyByUserId(userId);
	}

	@Override
	public void awardInviter() {
		Map<String, Object> map = new HashMap<String, Object>();
		inviterMapper.awardInviter(map);
	}
	@Override
	public Page queryInviteDetailsByUserIdNew(FriendCnd friendCnd,int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setTotalCount(inviterMapper.countInviteDetailsByUserIdNew(friendCnd));
		page.setResult(inviterMapper.queryInviteDetailsByUserIdNew(friendCnd,page));
		return page;
	}
}
