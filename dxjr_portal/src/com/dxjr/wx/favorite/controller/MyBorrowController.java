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

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * 我的借款类
 * </p>
 * @title MyBorrowController.java
 * @package com.dxjr.wx.favorite.controller
 * @author ZHUCHEN
 * @version 0.1 2014年10月31日
 */
@Controller
@RequestMapping(value = "/wx/favorite")
public class MyBorrowController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyBorrowController.class);
	@Autowired
	BorrowService borrowService;
	@Autowired
	MemberMapper memberMapper;

	/**
	 * 获取我的借款相关数据
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月30日
	 * @param type 1-招标中借款，2-借款列表
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/myBorrow/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> unCollection_record(@PathVariable String type, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return result;
		}

		try {
			BorrowCnd borrowCnd = new BorrowCnd();
			borrowCnd.setUserId(shiroUser.getUserId());
			if ("1".equals(type)) {
				List<?> underwayBorrowList = borrowService.queryTendering(borrowCnd, 1, 10).getResult();
				if (underwayBorrowList != null && underwayBorrowList.size() > 0) {
					result.put("underwayBorrowList", underwayBorrowList);
				}
			} else if ("2".equals(type)) {
				List<?> borrowList = borrowService.queryAll(borrowCnd, pageNum, 10).getResult();
				if (borrowList != null && borrowList.size() > 0) {
					result.put("borrowList", borrowList);
					if (borrowList.size() == 10) {
						result.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
					}
				}
			}
		} catch (Exception e) {
			logger.error("微信-获取我的借款数据异常：", e);
		}

		return result;
	}

	/**
	 * <p>
	 * Description:撤标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/cancelBorrow")
	@ResponseBody
	public String cancelBorrow(String borrowid, String payPassword, String ip) {
		String result = "";
		ShiroUser shiroUser = currentUser();
		if (borrowid == null || borrowid.equals("")) {
			result = "该记录不存在，请刷新后重试！";
		} else {
			try {
				// 验证交易密码
				payPassword = MD5.toMD5(payPassword);
				MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
				if (!mem.getPaypassword().equals(payPassword)) {
					return "交易密码错误，请重新输入";
				}
				result = borrowService.cancelBorrow(Integer.parseInt(borrowid), shiroUser.getUserId(), ip);
			} catch (AppException e) {
				e.printStackTrace();
				result = "撤标失败！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "撤标失败！";
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Description:净值标提前满标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/advanceFullBorrow")
	@ResponseBody
	public String advanceFullBorrow(String borrowid, String payPassword, String ip) {
		String result = "";
		ShiroUser shiroUser = currentUser();
		if (borrowid == null || borrowid.equals("")) {
			result = "该记录不存在，请刷新后重试！";
		} else {
			try {
				// 验证交易密码
				payPassword = MD5.toMD5(payPassword);
				MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
				if (!mem.getPaypassword().equals(payPassword)) {
					return "交易密码错误，请重新输入";
				}
				result = borrowService.advanceFullBorrow(Integer.parseInt(borrowid), shiroUser.getUserId(), ip);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				result = "提前满标失败";
			} catch (Exception e) {
				e.printStackTrace();
				result = "提前满标失败";
			}
		}
		return result;
	}

}
