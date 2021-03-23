package com.dxjr.wx.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.service.CollectionStatisticService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

/**
 * 获取待收数据类（直通车待收和普通待收）
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title UnCollectController.java
 * @package com.dxjr.wx.favorite.controller
 * @author ZHUCHEN
 * @version 0.1 2014年10月29日
 */
@Controller
@RequestMapping(value = "/wx/favorite")
public class UnCollectController extends BaseController {
	private static final Logger logger = Logger.getLogger(UnCollectController.class);
	@Autowired
	private CollectionRecordService collectionRecordServiceImpl;
	@Autowired
	CollectionStatisticService collectionStatisticServiceImpl;

	/**
	 * 获取待收数据
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月30日
	 * @param session
	 * @param request
	 * @param type_collection
	 * @param status_type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/unCollection_record/{status_type}/{type_collection}/{pageNum}")
	@ResponseBody
	public Map<String, Object> unCollection_record(@PathVariable String type_collection, @PathVariable String status_type, @PathVariable int pageNum) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				return result;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user_id", shiroUser.getUserId());
			map.put("status_type", status_type);
			map.put("type_collection", type_collection);
			Page p = collectionRecordServiceImpl.queryMyCollections(map, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
			List<?> list = p.getResult();
			if (list != null && list.size() > 0) {
				if (p.getResult().size() == BusinessConstants.DEFAULT_PAGE_SIZE) {
					result.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				result.put("collectionRecordList", list);
			}
		} catch (Exception e) {
			logger.error("微信-获取待收数据异常", e);
		}
		return result;
	}
}