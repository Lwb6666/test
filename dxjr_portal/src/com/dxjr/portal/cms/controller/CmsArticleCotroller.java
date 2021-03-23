package com.dxjr.portal.cms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.cms.service.CmsArticleService;
import com.dxjr.portal.cms.service.CmsChannelService;
import com.dxjr.portal.cms.service.CmsPediaEntryService;
import com.dxjr.portal.cms.service.CmsTagService;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsArticleCnd;
import com.dxjr.portal.cms.vo.CmsChannel;
import com.dxjr.portal.cms.vo.CmsComment;
import com.dxjr.portal.cms.vo.CmsCommentVo;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.cms.vo.SearchPageVo;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;
import com.dxjr.portal.util.CharacterEncoder;

@Controller
public class CmsArticleCotroller extends BaseController {
	Logger logger = LoggerFactory.getLogger(CmsArticleCotroller.class);

	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private CmsChannelService cmsChannelService;
	@Autowired
	private CmsTagService cmsTagService;

	@Autowired
	NewsNoticeService newsNoticeService;
	@Autowired
	InvestRecordService investRecordService;

	@Autowired
	CmsPediaEntryService cmsPediaEntryService;

	@Autowired
	private FirstBorrowService firstBorrowService;

	/**
	 * <p>
	 * Description:栏目列表<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @return jsp
	 * @throws Exception
	 */
	public ModelAndView tochannel(@PathVariable Integer id, @RequestParam Integer pageNum) throws Exception {
		ModelAndView mv = new ModelAndView("cms/informationChannel");

		// 栏目信息
		CmsChannel cmsChannel = new CmsChannel();
		cmsChannel = cmsChannelService.getCmsChannelById(id);

		// 两篇置顶文章---本栏目； | 不够的，用热门文章补足；
		int lave = 0;
		if (pageNum == 1) {
			List<CmsArticle> hotsCmsArticles = cmsArticleService.findTopArticlesByChannelLimit(id, 0, 2);

			if (hotsCmsArticles.size() < 2) {
				lave = 2 - hotsCmsArticles.size();
			}
			if (lave > 0) {
				List<CmsArticle> hotsCmsArticles2 = cmsArticleService.findHotArticlesByChannelLimit(id, 0, lave);
				for (int i = 0; i < hotsCmsArticles2.size(); i++) {
					hotsCmsArticles.add(hotsCmsArticles2.get(i));
				}
			}
			mv.addObject("hotsCmsArticles", hotsCmsArticles);
		}

		// 文章列表分页---本栏目；
		CmsArticleCnd cmsArticleCnd = new CmsArticleCnd();
		cmsArticleCnd.setChannelId(id);
		cmsArticleCnd.setLave(lave);
		Page p = cmsArticleService.findPageByCnd(cmsArticleCnd, new Page(pageNum, 10));

		// 热门文章：---本栏目；
		List<CmsArticle> hotsCmsArticlesAll = cmsArticleService.findHotArticlesByChannelLimit(id, 0, 9);
		CmsArticle hotsCmsArticles1 = new CmsArticle();
		List<CmsArticle> hotsCmsArticles8 = new ArrayList<CmsArticle>();
		if (hotsCmsArticlesAll.size() > 0) {
			hotsCmsArticles1 = hotsCmsArticlesAll.get(0);
		}
		if (hotsCmsArticlesAll.size() > 1) {
			hotsCmsArticlesAll.remove(0);
			hotsCmsArticles8 = hotsCmsArticlesAll;
		}

		// 热门标签：------本栏目；
		int num1 = 21;
		List<CmsTag> cmsTagList21 = cmsTagService.queryCmsTagListByTypeAndNum(id, num1);

		// 投标专区
		int type = 0;
		List<BorrowVo> borrowVos = investRecordService.queryBorrowByLimit(type, 0, 6);

		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		Page page = new Page(0, 7);
		List<FirstBorrowVo> firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
		mv.addObject("firstBorrowVos", firstBorrowVos).addObject("nowTime", new Date());

		// 顶玺最新公告
		List<NewsNoticeVo> newsNoticeVos = newsNoticeService.queryNewNewsNoticeList(0, 0, 3);
		// 查询头部动态显示的栏目
		List<CmsChannel> showChannels = cmsChannelService.queryShowChannels();
		mv.addObject("showChannels", showChannels);
		mv.addObject("cmsChannel", cmsChannel);

		mv.addObject("page", p);
		mv.addObject("hotsCmsArticles1", hotsCmsArticles1);
		mv.addObject("hotsCmsArticles8", hotsCmsArticles8);
		mv.addObject("cmsTagList21", cmsTagList21);
		mv.addObject("borrowVos", borrowVos);
		mv.addObject("newsNoticeVos", newsNoticeVos);

		return mv;
	}

	/**
	 * <p>
	 * Description:资讯详情<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @return jsp
	 */
	@RequestMapping(value = "zixun/{id}")
	public ModelAndView toinformationDetail(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("cms/informationDetail");
		CmsArticle cmsArticle = new CmsArticle();

		// 文章详情
		cmsArticle = cmsArticleService.getCmsArticleById(id);

		int channelId = cmsArticle.getChannelId();
		// 热门文章：---本栏目；
		List<CmsArticle> hotsCmsArticlesAll = cmsArticleService.findHotArticlesByChannelLimit(channelId, 0, 9);
		CmsArticle hotsCmsArticles1 = new CmsArticle();
		List<CmsArticle> hotsCmsArticles8 = new ArrayList<CmsArticle>();
		if (hotsCmsArticlesAll.size() > 0) {
			hotsCmsArticles1 = hotsCmsArticlesAll.get(0);
		}
		if (hotsCmsArticlesAll.size() > 1) {
			hotsCmsArticlesAll.remove(0);
			hotsCmsArticles8 = hotsCmsArticlesAll;
		}

		// 热门标签：------本栏目；
		int num1 = 21;
		List<CmsTag> cmsTagList21 = cmsTagService.queryCmsTagListByTypeAndNum(channelId, num1);

		// 投标专区
		int type = 0;
		List<BorrowVo> borrowVos = investRecordService.queryBorrowByLimit(type, 0, 6);

		// 顶玺最新公告
		List<NewsNoticeVo> newsNoticeVos = newsNoticeService.queryNewNewsNoticeList(0, 0, 3);

		// 相关文章： id查询出对应的标签，标签查询相关的文章-排除本文章； | 如果无，查询最新的几篇文章；
		List<CmsArticle> aboutArticles = new ArrayList<CmsArticle>();
		aboutArticles = cmsArticleService.findAboutArticlesByTag(id);
		if (aboutArticles.size() == 0) {
			aboutArticles = cmsArticleService.findNewArticlesByChannelLimit(channelId, 0, 6);
		}

		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		Page page = new Page(0, 7);
		List<FirstBorrowVo> firstBorrowVos;
		try {
			firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
			mv.addObject("firstBorrowVos", firstBorrowVos).addObject("nowTime", new Date());
		} catch (Exception e) {

		}

		// 常见问题：
		int channelId2 = 38;
		List<CmsArticle> questionArticles = cmsArticleService.findHotArticlesByChannelLimit(channelId2, 0, 6);

		// 上一篇，下一篇：
		CmsArticle cmsArticleLast = new CmsArticle();
		CmsArticle cmsArticleNext = new CmsArticle();
		cmsArticleLast = cmsArticleService.findArticlesLast(id);
		cmsArticleNext = cmsArticleService.findArticlesNext(id);

		// 查询该文章的评论总数，总人数；
		CmsCommentVo cmsCommentVo = cmsArticleService.queryCommentSum(id);

		// 文章点击数+1
		CmsArticle cmsArticle2 = new CmsArticle();
		cmsArticle2.setId(cmsArticle.getId());
		cmsArticle2.setHits(cmsArticle.getHits() + 1);
		cmsArticleService.updateCmsArticleHits(cmsArticle2);

		// 评论内容：
		SearchPageVo searchPageVo = new SearchPageVo();
		searchPageVo.setId(id);
		List<CmsCommentVo> commentList = cmsArticleService.queryCommentListByCnd(searchPageVo);
		// 查询头部动态显示的栏目
		List<CmsChannel> showChannels = cmsChannelService.queryShowChannels();
		mv.addObject("showChannels", showChannels);
		mv.addObject("cmsArticle", cmsArticle);
		mv.addObject("hotsCmsArticles1", hotsCmsArticles1);
		mv.addObject("hotsCmsArticles8", hotsCmsArticles8);
		mv.addObject("cmsTagList21", cmsTagList21);
		mv.addObject("borrowVos", borrowVos);
		mv.addObject("newsNoticeVos", newsNoticeVos);
		mv.addObject("aboutArticles", aboutArticles);
		mv.addObject("questionArticles", questionArticles);
		mv.addObject("cmsArticleLast", cmsArticleLast);
		mv.addObject("cmsArticleNext", cmsArticleNext);
		mv.addObject("cmsCommentVo", cmsCommentVo);
		mv.addObject("commentList", commentList);

		return mv;
	}

	/**
	 * <p>
	 * Description:标签集合<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @return jsp
	 */
	@RequestMapping(value = "tag")
	public ModelAndView totagSet() {
		ModelAndView mv = new ModelAndView("cms/tagSet");

		// 热门标签---全部
		int type1 = 0;
		int num1 = 50;
		List<CmsTag> cmsTagList = cmsTagService.queryCmsTagListByTypeAndNum(type1, num1);

		// 理财标签
		int type2 = 1;
		int num2 = 50;
		List<CmsTag> cmsTagList2 = cmsTagService.queryCmsTagListByTypeAndNum(type2, num2);

		// 贷款标签
		int type3 = 2;
		int num3 = 50;
		List<CmsTag> cmsTagList3 = cmsTagService.queryCmsTagListByTypeAndNum(type3, num3);

		// 热门文章：---针对全部文章，前9篇；
		List<CmsArticle> hotsCmsArticlesAll = cmsArticleService.findHotArticlesByChannelLimit(0, 0, 9);
		CmsArticle hotsCmsArticles1 = new CmsArticle();
		List<CmsArticle> hotsCmsArticles8 = new ArrayList<CmsArticle>();
		if (hotsCmsArticlesAll.size() > 0) {
			hotsCmsArticles1 = hotsCmsArticlesAll.get(0);
		}
		if (hotsCmsArticlesAll.size() > 1) {
			hotsCmsArticlesAll.remove(0);
			hotsCmsArticles8 = hotsCmsArticlesAll;
		}

		// 投标专区
		int type = 0;
		List<BorrowVo> borrowVos = investRecordService.queryBorrowByLimit(type, 0, 6);

		// 顶玺最新公告
		List<NewsNoticeVo> newsNoticeVos = newsNoticeService.queryNewNewsNoticeList(0, 0, 3);

		// 直通车
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		Page page = new Page(0, 7);
		List<FirstBorrowVo> firstBorrowVos;
		try {
			firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
			mv.addObject("firstBorrowVos", firstBorrowVos).addObject("nowTime", new Date());
		} catch (Exception e) {

		}
		// 查询头部动态显示的栏目
		List<CmsChannel> showChannels = cmsChannelService.queryShowChannels();
		mv.addObject("showChannels", showChannels);

		mv.addObject("cmsTagList", cmsTagList);
		mv.addObject("cmsTagList2", cmsTagList2);
		mv.addObject("cmsTagList3", cmsTagList3);
		mv.addObject("hotsCmsArticles1", hotsCmsArticles1);
		mv.addObject("hotsCmsArticles8", hotsCmsArticles8);
		mv.addObject("borrowVos", borrowVos);
		mv.addObject("newsNoticeVos", newsNoticeVos);
		return mv;
	}

	/**
	 * <p>
	 * Description:标签列表<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @return jsp
	 */
	@RequestMapping(value = "tag/{id}")
	public ModelAndView totag(@PathVariable("id") String idstr) {
		ModelAndView mv = new ModelAndView("cms/tagList");

		// 从超链中解析出id和页码，
		Integer pageNum = 1;
		String url1 = CharacterEncoder.decodeURL(idstr, "UTF-8");
		Integer id = 0;
		if (url1.indexOf("-") != -1) {
			String[] c = url1.split("-");
			id = Integer.parseInt(c[0]);
			pageNum = Integer.parseInt(c[1]);
		} else {
			// String regStr = "^[+-]?[1-9][0-9]*$|^0$";
			// if(url1.matches(regStr)){
			id = Integer.parseInt(url1);
			// }else{
			// 参数查询
			// CmsTag cmstag2 = cmsTagService.getCmsTagByParam(url1);
			// id = cmstag2.getId();
			// }
		}

		// 标签对象
		CmsTag cmstag = cmsTagService.getCmsTagById(id);

		// 两篇热门文章---标签对应的；| 删除，不要了！
		// List<CmsArticle> hotsCmsArticles2 =
		// cmsTagService.findHotArticlesByTagLimit(id, 0, 2);

		// 文章
		SearchPageVo searchPageVo = new SearchPageVo();
		searchPageVo.setId(id);
		Page p = cmsTagService.queryArticlePageByCnd(searchPageVo, new Page(pageNum, 10));

		// 热门文章：---全部；
		List<CmsArticle> hotsCmsArticlesAll = cmsArticleService.findHotArticlesByChannelLimit(0, 0, 9);
		CmsArticle hotsCmsArticles1 = new CmsArticle();
		List<CmsArticle> hotsCmsArticles8 = new ArrayList<CmsArticle>();
		if (hotsCmsArticlesAll.size() > 0) {
			hotsCmsArticles1 = hotsCmsArticlesAll.get(0);
		}
		if (hotsCmsArticlesAll.size() > 1) {
			hotsCmsArticlesAll.remove(0);
			hotsCmsArticles8 = hotsCmsArticlesAll;
		}

		// 热门标签---全部
		int num1 = 21;
		List<CmsTag> cmsTagList = cmsTagService.queryCmsTagListByTypeAndNum(0, num1);

		// 投标专区
		int type = 0;
		List<BorrowVo> borrowVos = investRecordService.queryBorrowByLimit(type, 0, 6);

		// 顶玺最新公告
		List<NewsNoticeVo> newsNoticeVos = newsNoticeService.queryNewNewsNoticeList(0, 0, 3);

		// 直通车
		FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
		Page page = new Page(0, 7);
		List<FirstBorrowVo> firstBorrowVos;
		try {
			firstBorrowVos = firstBorrowService.queryFirstBorrowByFirstBorrowCnd(firstBorrowCnd, page);
			mv.addObject("firstBorrowVos", firstBorrowVos).addObject("nowTime", new Date());
		} catch (Exception e) {

		}

		// 查询头部动态显示的栏目
		List<CmsChannel> showChannels = cmsChannelService.queryShowChannels();
		mv.addObject("showChannels", showChannels);

		mv.addObject("cmstag", cmstag);
		// mv.addObject("hotsCmsArticles2", hotsCmsArticles2);
		mv.addObject("tagId", id);
		mv.addObject("page", p);
		mv.addObject("hotsCmsArticles1", hotsCmsArticles1);
		mv.addObject("hotsCmsArticles8", hotsCmsArticles8);
		mv.addObject("cmsTagList", cmsTagList);
		mv.addObject("borrowVos", borrowVos);
		mv.addObject("newsNoticeVos", newsNoticeVos);

		return mv;
	}

	/**
	 * <p>
	 * Description:保存评论<br />
	 * 请求、返回、权限；
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月5日
	 * @param
	 * @return ajax
	 */
	@RequestMapping(value = "saveComment")
	@ResponseBody
	public MessageBox saveComment(HttpServletRequest request) {

		if (!isLogin()) {
			return new MessageBox("0", "请先登录");
		}

		String msg = null;
		Integer articleId = Integer.parseInt(request.getParameter("id"));
		String commentCon = request.getParameter("commentCon");

		if (commentCon.length() < 6) {
			return new MessageBox("0", "评论内容不能为空或长度小于6!");
		}
		if (commentCon.length() > 1000) {
			return new MessageBox("0", "评论内容长度不能大于1000!");
		}

		CmsComment cmsComment = new CmsComment();
		cmsComment.setArticleId(articleId);
		cmsComment.setContent(commentCon);
		cmsComment.setCreateBy(currentUser().getUserId());

		try {
			// 保存
			cmsArticleService.saveComment(cmsComment);
			return MessageBox.build("1", "保存成功.");

		} catch (Exception e) {
			logger.error("保存评论失败.", e);
			msg = "保存失败.";
		}

		return MessageBox.build("0", msg);
	}

	/**
	 * <p>
	 * Description:评论分页查询<br />
	 * ---废弃；
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月5日
	 * @param
	 * @return ajax
	 */
	@RequestMapping(value = "queryCommentList")
	public ModelAndView queryCommentList(@RequestParam Integer pageNum, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("cms/commentGridByPage");

		Integer articleId = Integer.parseInt(request.getParameter("articleId"));

		Page p = null;
		try {
			SearchPageVo searchPageVo = new SearchPageVo();
			searchPageVo.setId(articleId);
			p = cmsArticleService.queryCommentPageByCnd(searchPageVo, new Page(pageNum, 1));

		} catch (Exception e) {
			logger.error("查询评论出错");
			e.printStackTrace();
		}
		mv.addObject("page", p);

		return mv;
	}

	// 栏目，行业新闻---跳转到公共处理方法中；
	/*
	 * @RequestMapping(value = "hangye") public ModelAndView channelTo() {
	 * 
	 * CmsChannel channel = cmsChannelService.getChannelByPinyin("hangye");
	 * Integer id = channel.getId(); return tochannel(id, 1); }
	 */

	// 栏目跳转---跳转到公共处理方法中；
	@RequestMapping(value = "/{pinyin}")
	public ModelAndView channelTo3(@PathVariable("pinyin") String pinyin, HttpServletRequest request) throws Exception {
		Integer pageNum = 1;
		String url1 = pinyin;

		if (url1.indexOf("-") != -1) {
			String[] c = url1.split("-");
			pinyin = c[0];
			pageNum = Integer.parseInt(c[1]);
		}

		CmsChannel channel = cmsChannelService.getChannelByPinyin(pinyin);
		Integer id = channel.getId();

		/*
		 * if(request.getParameter("pageNum")!=null){ pageNum =
		 * Integer.parseInt(request.getParameter("pageNum")); }
		 */
		return tochannel(id, pageNum);
	}

}
