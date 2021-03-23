package com.dxjr.portal.safe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;

@RequestMapping("/anquan")
@Controller
public class SafeController extends BaseController {

	@RequestMapping
	public ModelAndView index() {
		return forword("/safe/index");
	}

	@RequestMapping("/{jsp}")
	public ModelAndView toSafeJsp(@PathVariable String jsp) {
		String returnPage = "";
		if (null != jsp && !"".equals(jsp.trim())) {
			returnPage = SafeController.getJspPageByAction(jsp);
		}
		return forword("/safe/" + returnPage);
	}

	/**
	 * 路径对应的JSP页面地址
	 */
	private static Map<String, String> jspPageMap = new HashMap<String, String>();
	static {
		jspPageMap.put("yewu", "business");
		jspPageMap.put("benjin", "principal");
		jspPageMap.put("benyongjin", "backup");
		jspPageMap.put("huigou", "buyback");
		jspPageMap.put("jiandu", "external");
		jspPageMap.put("baozhang", "interior");
		jspPageMap.put("jishu", "technology");
		jspPageMap.put("fawu", "law");
		jspPageMap.put("jinrong", "organization");
		jspPageMap.put("fengkong", "youxiaofengkong");
	}

	/**
	 * <p>
	 * Description:根据请求路径不同返回页面地址<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月1日
	 * @param jsp
	 * @return String
	 */
	private static String getJspPageByAction(String jsp) {
		String returnPage = "";
		if (jspPageMap.containsKey(jsp)) {
			returnPage = jspPageMap.get(jsp);
		}
		return returnPage;
	}
}
