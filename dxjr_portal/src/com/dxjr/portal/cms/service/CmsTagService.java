package com.dxjr.portal.cms.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.cms.vo.SearchPageVo;

public interface CmsTagService {
 
	Page searchCmsTagPage(int pageNo, int pageSize);

	void save(CmsTag cmsTag);

	List<CmsTag> queryCmsTagList(Integer parentChannelId, int i, int j);

	List<CmsTag> queryTagsByName(String name, int i, int j);

	/**
	 * <p>
	 * Description:标签列表查询<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月2日
	 * @param 
	 * @return 
	 */
	List<CmsTag> queryCmsTagListByTypeAndNum(Integer type, Integer num);
	/**
	 * <p>
	 * Description:文章列表查询-by标签<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月2日
	 * @param 
	 * @return 
	 */
	Page queryArticlePageByCnd(SearchPageVo searchPageVo, Page page);

	/**
	 * <p>
	 * Description:热门的2篇文章-by标签<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return 
	 */
	List<CmsArticle> findHotArticlesByTagLimit(Integer id, int i, int j);
	
	/**
	 * <p>
	 * Description:标签-id查询<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return 
	 */
	CmsTag getCmsTagById(Integer id);

	List<CmsTag> queryCmsTagListByParentChannelId(int moneymanagement, int i, int j);

	/**
	 * <p>
	 * Description:标签-参数查询<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return 
	 */
	CmsTag getCmsTagByParam(String idstr);
	

}
