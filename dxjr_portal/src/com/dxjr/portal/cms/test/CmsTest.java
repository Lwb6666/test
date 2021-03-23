package com.dxjr.portal.cms.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.cms.constant.CmsConstant;
import com.dxjr.portal.cms.service.CmsArticleService;
import com.dxjr.portal.cms.service.CmsChannelService;
import com.dxjr.portal.cms.service.CmsPediaEntryService;
import com.dxjr.portal.cms.service.CmsTagService;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsArticleVo;
import com.dxjr.portal.cms.vo.CmsChannel;
import com.dxjr.portal.cms.vo.CmsPediaEntry;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;
import com.dxjr.portal.statics.BusinessConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
public class CmsTest {

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

	// 首页热门文章 两个
	@Test
	public void testIndexTop2Article() {
		List<CmsArticle> cmsArticles = articleService.findHotArticlesByLimit(0, 2);
		System.out.println(cmsArticles.size());
	}

	// 首页 知识百科12个词条两个
	@Test
	public void testIndexTop12PediaEntry() {
		List<CmsPediaEntry> cmsArticles = cmsPediaEntryService.findPediaEntryByLimit(0, 12);
		System.out.println(cmsArticles.size());
	}

	/**
	 * <p>
	 * Description:首页查询5个置顶的文章 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年2月3日 void
	 */
	@Test
	public void testIndexQueryTopArticlesByChannels() {
		int channelTopArticles[] = new int[] { CmsConstant.TRADENEWS, CmsConstant.PLATFORMNOTICE, CmsConstant.MEDIAREPORT };
		List<CmsArticle> topsCmsArticles = articleService.queryTopArticlesByChannels(channelTopArticles, 0, 5);
		System.out.println(topsCmsArticles.size());
	}

	/**
	 * <p>
	 * Description:直通车7个 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testIndexTop7first() {
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		firstBorrowCnd.setType(String.valueOf(2));// 查询开通未满的
		Page page = new Page(0, 7);
		try {
			List<FirstBorrowVo> list = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <p>
	 * Description:查询栏目最新文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testIndexNewArticleByChannel() {
		int channels[] = new int[] { CmsConstant.INVESTMENTSTRATEGY, CmsConstant.PERSONALFINANCE, CmsConstant.LOANTOBUYCAR, CmsConstant.CONSUMPTIONLOAN, CmsConstant.KNOW };
		List<CmsArticleVo> cmsArticleVos = articleService.queryNewArticleByChannel(channels);
		System.out.println(cmsArticleVos.size());
	}

	/**
	 * <p>
	 * Description:顶玺最新公告<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testIndexNewNotice() {
		List<NewsNoticeVo> newsNoticeVos = newsNoticeService.queryNewNewsNoticeList(0, 0, 3);
		System.out.println(newsNoticeVos.size());
	}

	/**
	 * <p>
	 * Description:获取理财类、贷款类栏目的文章栏目标签 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testIndexChannel() {

		List<CmsChannel> cmsChannels = channelService.queryCmsChannelListByParentId(CmsConstant.MONEYMANAGEMENT);
		System.out.println("查询首页栏目" + cmsChannels.size() + "个");
		CmsChannel cmsChannel = cmsChannels.get(0);
		List<CmsArticle> cmsArticles = articleService.queryCmsArticleList(cmsChannel.getId(), 0, 3);
		System.out.println("查询第一个栏目下文章" + cmsArticles.size() + "个");
		// 查询理财大类下所有栏目下的标签
		List<CmsTag> cmsTags = cmsTagService.queryCmsTagList(CmsConstant.MONEYMANAGEMENT, 0, 27);
		System.out.println("查询第一个栏目下标签" + cmsTags.size() + "个");

		List<CmsArticle> cmsArticles1 = articleService.queryCmsArticleListByParentChannelId(CmsConstant.MONEYMANAGEMENT, 0, 3);
		System.out.println("查询第一个栏目下文章" + cmsArticles1.size() + "个");
		List<CmsTag> cmsTags1 = cmsTagService.queryCmsTagListByParentChannelId(CmsConstant.MONEYMANAGEMENT, 0, 27);
		System.out.println("查询父栏目下标签" + cmsTags1.size() + "个");
	}

	/**
	 * <p>
	 * Description:词条详情页面 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntryDetail() {
		CmsPediaEntry cmsPediaEntry = cmsPediaEntryService.getCmsPediaEntryById(1);
		System.out.println(cmsPediaEntry);
	}

	/**
	 * <p>
	 * Description:词条详情页面 ---投标专区<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntryDetailTender() {
		SearchInvestCnd seach = new SearchInvestCnd();
		seach.setLimitTime("isTendering");
		seach.setOrderType("asc");
		List<BorrowVo> list = investRecordService.queryInvestRecordList(seach, new Page(0, BusinessConstants.DEFAULT_PAGE_SIZE));
		System.out.println("词条详情页面 ---投标专区" + list.size());
	}

	/**
	 * <p>
	 * Description:词条详情页面 ---百科字母索引<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntryDetailZIndex() {
		List<String> initials = cmsPediaEntryService.queryAllInitials();
		System.out.println(initials.size());
	}

	/**
	 * <p>
	 * Description:词条详情页面 ---词条 相关标签<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntryDetailTag() {
		String name = "互联网金融";
		List<CmsTag> cmsTags = cmsTagService.queryTagsByName(name, 0, 21);
		System.out.println(cmsTags.size());
	}

	/**
	 * <p>
	 * Description:词条详情页面 ---词条 相关文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntryDetailArticle() {
		String name = "互联网金融";
		List<CmsArticle> cmsArticles = articleService.queryArticlesByTag(name, 0, 12);
		System.out.println(cmsArticles.size());
	}

	/**
	 * <p>
	 * Description:知识百科 ---<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日 void
	 */
	@Test
	public void testPediaEntrybyInitials() {
		String initials = "c";
		List<CmsPediaEntry> cmsPediaEntries = cmsPediaEntryService.queryPediaEntrysByInitials(initials,0, 160);
		System.out.println(cmsPediaEntries.size());
	}


}
