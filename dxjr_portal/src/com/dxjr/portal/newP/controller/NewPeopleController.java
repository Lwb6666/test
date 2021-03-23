package com.dxjr.portal.newP.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.Dictionary;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.lottery.service.LotteryUseRecordService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.newP.service.NewPService;
import com.dxjr.portal.transfer.service.TransferService;

/***
 * 
 * <p>
 * Description: 新手专区 业务处理 <br />
 * </p>
 * 
 * @title NewPeopleController.java
 * @package com.dxjr.portal.newP.controller
 * @author HuangJun /newPeopleAreaUrl/newPeopleArea/newPeopleArea_xinshoubiao
 * @version 0.1 2015年4月15日
 */
@Controller
@RequestMapping(value = "/newPeopleArea")
public class NewPeopleController extends BaseController {

	@Autowired
	private NewPService newPService;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private LotteryUseRecordService lotteryUseRecordService;
	@Autowired
	MemberService memberService;

	@Autowired
	private TendRecordService tenderRecordService;

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private FixBorrowService fixBorrowService;

	/**
	 * 
	 * <p>
	 * Description:新手专区主页header.jsp首次跳转到这里<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月8日
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/newPeopleAreaBiao")
	public ModelAndView newPFirst(HttpServletRequest request) throws Exception {
		extendLinkSource();
		ModelAndView mv = new ModelAndView("/newp/newPeoplePage");
		mv.addObject("source", request.getParameter("source"));
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:处理外部链接推广来源注册<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月1日
	 * @param request
	 *            void
	 */
	private void extendLinkSource() {
		String extendLinkSourceName = (currentRequest().getParameter("source") == null) ? "39" : currentRequest().getParameter("source");// 链接来源
		String tid = currentRequest().getParameter("tid");// 如果来源于易瑞通，则为必有参数
		if (null != tid) {
			currentRequest().getSession().setAttribute("tid", tid);
		}
		if (!StringUtils.isEmpty(extendLinkSourceName) && StringUtils.isEmpty(currentRequest().getSession().getAttribute("linkSourceValue"))) {
			String linkSourceValue = Dictionary.getValue(1100, extendLinkSourceName.trim());
			currentRequest().getSession().setAttribute("linkSourceValue", linkSourceValue);
		}
	}

}
