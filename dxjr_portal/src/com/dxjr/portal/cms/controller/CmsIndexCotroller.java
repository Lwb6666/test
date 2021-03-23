package com.dxjr.portal.cms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.CacheService;
import com.dxjr.common.SerializeUtil;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.cms.constant.CmsConstant;
import com.dxjr.portal.cms.service.CmsArticleService;
import com.dxjr.portal.cms.service.CmsChannelService;
import com.dxjr.portal.cms.service.CmsPediaEntryService;
import com.dxjr.portal.cms.service.CmsTagService;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsChannel;
import com.dxjr.portal.cms.vo.CmsPediaEntry;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;

@Controller
public class CmsIndexCotroller {

	@Autowired
	CmsArticleService articleService;

	@Autowired
	CmsPediaEntryService cmsPediaEntryService;

	@Autowired
	private FirstBorrowService firstBorrowService;

	@Autowired
	NewsNoticeService newsNoticeService;

	@Autowired
	CmsChannelService channelService;

	@Autowired
	CmsTagService cmsTagService;

	@Autowired
	InvestRecordService investRecordService;



	@Autowired
	TransferService transferService;

	@RequestMapping(value = "/zixun")
	public ModelAndView index() throws Exception {

		ModelAndView mav = new ModelAndView("/cms/informationindex");

		CmsChannel cmsChannel = null;
		CmsChannel loanCmsChannel = null;
		List<CmsTag> cmsTags = null;
		List<CmsTag> loanCmsTags = null;
		List<CmsArticle> moneyManagementCmsArticles = null;
		List<CmsArticle> loanCmsArticles = null;
		List<CmsArticle> InvestmentStrategyCmsArticles=null;
		List<CmsArticle> regulationsCmsArticles=null;
		List<CmsArticle> faqCmsArticles=null;

		// 查询头部动态显示的栏目
		List<CmsChannel> showChannels = channelService.queryShowChannels();

		// 查询首页 平台公告，行业新闻，媒体报道， 置顶的文章
		int channelTopArticles[] = new int[] { CmsConstant.TRADENEWS, CmsConstant.PLATFORMNOTICE, CmsConstant.MEDIAREPORT };
		List<CmsArticle> topsCmsArticles = articleService.queryTopArticlesByChannels(channelTopArticles, 0, 5);

		// 知识百科
		List<CmsPediaEntry> cmsPediaEntrys = cmsPediaEntryService.findPediaEntryByLimit(0, 25);
		// 直通车专区
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		// firstBorrowCnd.setType(String.valueOf(2));// 查询开通未满的
		Page page = new Page(0, 7);
		List<FirstBorrowVo> firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
		// 根据板块查询最新第一个文章
		/*
		 * int channels[] = new int[] { CmsConstant.INVESTMENTSTRATEGY,
		 * CmsConstant.PERSONALFINANCE, CmsConstant.LOANTOBUYCAR,
		 * CmsConstant.CONSUMPTIONLOAN, CmsConstant.KNOW }; List<CmsArticleVo>
		 * cmsArticleVos = articleService.queryNewArticleByChannel(channels);
		 */

		String enable =CacheService.getInstance().getEnable();
		String cachehotsKey="zx_hotsCmsArticles";

		// 两篇热门文章
		List<CmsArticle> hotsCmsArticles=null;
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cachehotsKey.getBytes());
			if (key != null) {
				hotsCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				hotsCmsArticles = articleService.findHotArticlesByLimit(0, 11);
				CacheService.getInstance().put(cachehotsKey.getBytes(), SerializeUtil.serialize(hotsCmsArticles));
				CacheService.getInstance().expire(cachehotsKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			hotsCmsArticles = articleService.findHotArticlesByLimit(0, 11);
		}

		// 顶玺最新公告
		List<NewsNoticeVo> newsNoticeVos = null;
		String cachenewKey="zx_newsNoticeVos";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cachenewKey.getBytes());
			if (key != null) {
				newsNoticeVos = (List<NewsNoticeVo>) SerializeUtil.unserialize(key);
			} else {
				newsNoticeService.queryNewNewsNoticeList(0, 0, 3);
				CacheService.getInstance().put(cachenewKey.getBytes(), SerializeUtil.serialize(newsNoticeVos));
				CacheService.getInstance().expire(cachenewKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			newsNoticeService.queryNewNewsNoticeList(0, 0, 3);
		}

		// 查询理财 文章 以及标签
		List<CmsChannel> cmsChannels = channelService.queryCmsChannelListByParentId(CmsConstant.MONEYMANAGEMENT);

		String cachemoneyManagerKey="zx_moneyManager";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cachemoneyManagerKey.getBytes());
			if (key != null) {
				moneyManagementCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				moneyManagementCmsArticles = articleService.queryCmsArticleListByParentChannelId(CmsConstant.MONEYMANAGEMENT, 0, 12);
				CacheService.getInstance().put(cachemoneyManagerKey.getBytes(), SerializeUtil.serialize(moneyManagementCmsArticles));
				CacheService.getInstance().expire(cachemoneyManagerKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			moneyManagementCmsArticles = articleService.queryCmsArticleListByParentChannelId(CmsConstant.MONEYMANAGEMENT, 0, 12);
		}

		cmsTags = cmsTagService.queryCmsTagListByParentChannelId(CmsConstant.MONEYMANAGEMENT, 0, 21);

		// 查询借款类文章 以及标签
		List<CmsChannel> loanCmsChannels = channelService.queryCmsChannelListByParentId(CmsConstant.LOAN);

		String cacheloanKey="zx_loan";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cacheloanKey.getBytes());
			if (key != null) {
				loanCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				loanCmsArticles = articleService.queryCmsArticleListByParentChannelId(CmsConstant.LOAN, 0, 12);
				CacheService.getInstance().put(cacheloanKey.getBytes(), SerializeUtil.serialize(loanCmsArticles));
				CacheService.getInstance().expire(cacheloanKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			loanCmsArticles = articleService.queryCmsArticleListByParentChannelId(CmsConstant.LOAN, 0, 12);
		}


		loanCmsTags = cmsTagService.queryCmsTagListByParentChannelId(CmsConstant.LOAN, 0, 21);

		// 投资攻略
		String cacheInvestmentKey="zx_investment";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cacheInvestmentKey.getBytes());
			if (key != null) {
				InvestmentStrategyCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				InvestmentStrategyCmsArticles = articleService.queryCmsArticleList(CmsConstant.INVESTMENTSTRATEGY, 0, 10);
				CacheService.getInstance().put(cacheInvestmentKey.getBytes(), SerializeUtil.serialize(InvestmentStrategyCmsArticles));
				CacheService.getInstance().expire(cacheInvestmentKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			InvestmentStrategyCmsArticles = articleService.queryCmsArticleList(CmsConstant.INVESTMENTSTRATEGY, 0, 10);
		}


		// 政策法规
		String cacheregulationsKey="zx_regulations";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cacheregulationsKey.getBytes());
			if (key != null) {
				regulationsCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				regulationsCmsArticles = articleService.queryCmsArticleList(CmsConstant.POLICIESREGULATIONS, 0, 10);
				CacheService.getInstance().put(cacheregulationsKey.getBytes(), SerializeUtil.serialize(regulationsCmsArticles));
				CacheService.getInstance().expire(cacheregulationsKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			regulationsCmsArticles = articleService.queryCmsArticleList(CmsConstant.POLICIESREGULATIONS, 0, 10);
		}

		// 常见问题
		String cachefaqKey="zx_faq";
		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cachefaqKey.getBytes());
			if (key != null) {
				faqCmsArticles = (List<CmsArticle>) SerializeUtil.unserialize(key);
			} else {
				faqCmsArticles = articleService.queryCmsArticleList(CmsConstant.FAQ, 0, 14);
				CacheService.getInstance().put(cachefaqKey.getBytes(), SerializeUtil.serialize(faqCmsArticles));
				CacheService.getInstance().expire(cachefaqKey.getBytes(), 3600);// 1小时候失效
			}
		} else {
			faqCmsArticles = articleService.queryCmsArticleList(CmsConstant.FAQ, 0, 14);

		}

		Date now = new Date();
		mav.addObject("indexCmsChannels", cmsChannels);
		mav.addObject("cmsChannel", cmsChannel);
		mav.addObject("loanCmsChannel", loanCmsChannel);
		mav.addObject("topsCmsArticles", topsCmsArticles);
		mav.addObject("showChannels", showChannels);
		return mav.addObject("hotsCmsArticles", hotsCmsArticles).addObject("cmsPediaEntrys", cmsPediaEntrys).addObject("firstBorrowVos", firstBorrowVos)
				.addObject("newsNoticeVos", newsNoticeVos).addObject("moneyManagementCmsArticles", moneyManagementCmsArticles).addObject("loanCmsChannels", loanCmsChannels)
				.addObject("cmsTags", cmsTags).addObject("loanCmsChannel", loanCmsChannel).addObject("loanCmsArticles", loanCmsArticles).addObject("loanCmsTags", loanCmsTags)
				.addObject("InvestmentStrategyCmsArticles", InvestmentStrategyCmsArticles).addObject("regulationsCmsArticles", regulationsCmsArticles).addObject("faqCmsArticles", faqCmsArticles)
				.addObject("nowTime", now).addObject("isHome", 1);
	}

	@RequestMapping(value = "/searchArticle/{type}/{channelId}")
	public ModelAndView index(@PathVariable("type") Integer type, @PathVariable("channelId") Integer channelId) throws Exception {
		ModelAndView mav = new ModelAndView("/cms/content");
		CmsChannel cmsChannel = null;
		List<CmsTag> cmsTags = null;
		List<CmsArticle> cmsArticles = null;
		List<CmsChannel> cmsChannels = channelService.queryCmsChannelListByParentId(type);
		if (!cmsChannels.isEmpty()) {
			cmsArticles = articleService.queryCmsArticleList(channelId, 0, 12);
			cmsChannel = channelService.getCmsChannelById(channelId);
			cmsTags = cmsTagService.queryCmsTagList(channelId, 0, 27);
		}
		mav.addObject("cmsChannels", cmsChannels);
		mav.addObject("channel", cmsChannel);
		mav.addObject("type", type);
		return mav.addObject("cmsArticles", cmsArticles).addObject("cmsTags", cmsTags);
	}

	@RequestMapping(value = "/searchTab/{type}")
	public ModelAndView index(@PathVariable("type") Integer type) throws Exception {
		ModelAndView mav = new ModelAndView("/cms/content");
		if (type.intValue() == 1) {
			// 直通车专区
			FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
			Page page = new Page(0, 7);
			List<FirstBorrowVo> firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
			mav.setViewName("/cms/zhitongchecontent");
			mav.addObject("firstBorrowVos", firstBorrowVos).addObject("nowTime", new Date());
		} else if (type.intValue() == 2) {
			// 投标
			SearchInvestCnd seach = new SearchInvestCnd();
			seach.setLimitTime("isTendering");
			seach.setOrderType("asc");
			List<BorrowVo> investList = investRecordService.queryInvestRecordList(seach, new Page(0, 7));
			mav.setViewName("/cms/invertcontent");
			mav.addObject("investList", investList);
		} else if (type.intValue() == 3) {
			// 转让
			SearchTransferVo seach = new SearchTransferVo();
			seach.setOrderBy("asc");
			seach.setTransferStatus("2");
			List<BTransferVo> transferlist = transferService.querytransferRecordList(seach, new Page(0, 7));
			mav.setViewName("/cms/transfercontent");
			mav.addObject("transferlist", transferlist);
		}

		return mav;
	}


}
