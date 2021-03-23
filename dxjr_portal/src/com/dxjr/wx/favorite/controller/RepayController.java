package com.dxjr.wx.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/**
 * 获取待收数据类（直通车待收和普通待收）
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title RepayController.java
 * @package com.dxjr.wx.favorite.controller
 * @author ZHUCHEN
 * @version 0.1 2014年10月29日
 */
@Controller
@RequestMapping(value = "/wx/favorite")
public class RepayController extends BaseController {
	private static final Logger logger = Logger.getLogger(RepayController.class);
	@Autowired
	private BRepaymentRecordService bRepaymentRecordService;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 最近待还列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月4日
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/queryRepaymentDetails")
	@ResponseBody
	public Map<String, Object> repayment_record(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser shiroUser = currentUser();

		String pageNum_str = request.getParameter("pageNum");
		String status = request.getParameter("status");
		if (shiroUser == null) {
			return map;
		}
		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}
		int pageSize = 10;
		RepaymentRecordCnd repaymentRecordCnd = new RepaymentRecordCnd();
		repaymentRecordCnd.setUserId(shiroUser.getUserId());
		repaymentRecordCnd.setStatus(Integer.parseInt(status));

		Page p = null;
		try {
			p = bRepaymentRecordService.queryRepaymentList(repaymentRecordCnd, pageNum, pageSize);
		} catch (Exception e) {
			logger.error("微信-获取待还列表数据 异常：", e);
			return map;
		}
		List<?> list = p.getResult();
		if (list != null && list.size() > 0) {
			if (p.getResult().size() == BusinessConstants.DEFAULT_PAGE_SIZE) {
				map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
			map.put("result", list);
		}
		return map;
	}

	/**
	 * <p>
	 * Description:还款<br />
	 * </p>
	 * @author zhuchen
	 * @version 0.1 2014年5月29日
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/repayBorrow")
	@ResponseBody
	public MessageBox repayBorrow(Integer repaymentid, String payPwd, String ip) {
		try {
			ShiroUser shiroUser = currentUser();
			// 验证交易密码
			payPwd = MD5.toMD5(payPwd);
			MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
			if (!mem.getPaypassword().equals(payPwd)) {
				return MessageBox.build("0", "交易密码错误，请重新输入。");
			}
			String result = borrowService.saveRepayBorrow(repaymentid, ip, shiroUser.getUserId());
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return new MessageBox("0", result);
			}
		} catch (AppException e) {
			logger.error("还款失败", e);
			return new MessageBox("0", "还款失败");
		} catch (Exception e) {
			logger.error("还款失败", e);
			return new MessageBox("0", "还款失败");
		}
		return new MessageBox("1", "还款成功");
	}
}