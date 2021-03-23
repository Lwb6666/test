/**
 * 
 */
package com.dxjr.portal.account.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.ModifyMemberMapper;
import com.dxjr.portal.account.service.AccountBBSPostListService;
import com.dxjr.portal.account.service.AccountDetailService;
import com.dxjr.portal.account.vo.ModifyMember;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowInvestService;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.CharacterEncoder;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:账户详情<br />
 * </p>
 * 
 * @title AccountDetailController.java
 * @package com.dxjr.portal.account.controller
 * @author hujianpan
 * @version 0.1 2014年8月13日
 */
@Controller
@RequestMapping(value = "/accountdetail")
public class AccountDetailController extends BaseController {
	@Autowired
	private MemberService memberServiceImpl;
	@Autowired
	private BorrowInvestService borrowInvestServiceImpl;
	@Autowired
	private AccountDetailService accountDetailServiceImpl;
	@Autowired
	private AccountBBSPostListService accountBBSPostListService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
    @Autowired
    private ModifyMemberMapper modifyMemberMapper;
	/**
	 * <p>
	 * Description:进入用户信息页面<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param request
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "/show")
	public ModelAndView show(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView("/accountdetail/show");
		String userIdStr = request.getParameter("userId");
		String userIdMD5 = request.getParameter("userIdMD5");
		String userName = CharacterEncoder.decodeURL(request.getParameter("userName"), "UTF-8");
		if (userName != null) {
			userName = StrinUtils.stringEncryptDe(userName); // 解密用户名
		}
		String flagTemp =request.getParameter("flagTemp");
		mav.addObject("flagTemp",flagTemp);
         
		MemberCnd memberCnd = new MemberCnd();

		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)&& StringUtils.isEmpty(userIdMD5)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));
		memberCnd.setUserIdMD5(StringUtils.isEmpty(userIdMD5) ? null : userIdMD5);

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在

			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		ModifyMember modifyMember=modifyMemberMapper.findMemberById(member.getId());
		ShiroUser user=currentUser();//登录用户
		if(user==null){
			mav.addObject("isView",0);
		}else{
			if(user.getUserName().equals(member.getUsername()) && member.getIsFinancialUser().intValue()==1){
				mav.addObject("isView",1);
			}else{
				mav.addObject("isView",0);
			}
		} 
		if(modifyMember!=null){
			mav.addObject("flag",1);
		}else{
			mav.addObject("flag",0);
		}
		mav.addObject("userId", member.getId());
		mav.addObject("userName", member.getUserNameEncrypt());
		mav.addObject("member", member);
		mav.addObject("userlevel", memberServiceImpl.queryUserLevelByMemberId(member.getId()));
         
		return mav;
	}

	/**
	 * <p>
	 * Description:查看用户基本信息<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月14日
	 * @param request
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "memberinfo")
	public ModelAndView memberinfo(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("/accountdetail/memberinfo");
		String userIdStr = request.getParameter("userId");
		String userName = request.getParameter("userName");
		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}

		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在

			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		mav.addObject("member", member);
		mav.addObject("userlevel", memberServiceImpl.queryUserLevelByMemberId(memberServiceImpl.queryMemberByCnd(memberCnd).getId()));
		return mav;
	}

	/**
	 * <p>
	 * Description:查看用户投资列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月14日
	 * @param request
	 * @param username
	 * @param pages 接收第几页，每页条数
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "investlist")
	public ModelAndView investlist(HttpServletRequest request, String username, @ModelAttribute Page pages) throws Exception {

		ModelAndView mav = new ModelAndView("/accountdetail/investlist");

		String userIdStr = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String pageNum = request.getParameter("pageNum");

		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		if (userName != null) {
			userName = StrinUtils.stringEncryptDe(userName); // 解密用户名
		}
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在

			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", member.getId());
		Page resultPages = accountDetailServiceImpl.queryInvestList(params, new Page(conver(pageNum), BusinessConstants.DEFAULT_PAGE_SIZE));
		mav.addObject("resultPages", resultPages);

		return mav;
	}

	private int conver(String srcStr) {
		int des = 1;
		try {
			des = Integer.valueOf(srcStr);
		} catch (Exception e) {
			return des;
		}

		return des;
	}

	/**
	 * <p>
	 * Description:查看用户借款列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月14日
	 * @param request
	 * @param username
	 * @param pages 接收第几页，每页条数
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "borrowlist")
	public ModelAndView borrowlist(HttpServletRequest request, String username, @ModelAttribute Page pages) throws Exception {
		ModelAndView mav = new ModelAndView("/accountdetail/borrowlist");
		String userIdStr = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String pageNum = request.getParameter("pageNum");

		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		if (userName != null) {
			userName = StrinUtils.stringEncryptDe(userName); // 解密用户名
		}
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在

			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		BorrowCnd borrowCnd = new BorrowCnd();
		borrowCnd.setSearchType("list");// 借款列表
		borrowCnd.setSearchOrderBy("schedule");
		borrowCnd.setOrderByType("DESC");
		borrowCnd.setUserId(member.getId());
		Page resultPages = borrowInvestServiceImpl.findInverst(borrowCnd, conver(pageNum), BusinessConstants.DEFAULT_PAGE_SIZE);
		mav.addObject("resultPages", resultPages);

		return mav;
	}

	/**
	 * 查询某人的发帖列表
	 * 
	 * @author 胡建盼
	 * @param request
	 * @param username
	 * @param pages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bbsItemslist")
	public ModelAndView bbsItemslist(HttpServletRequest request, String username, @ModelAttribute Page pages) throws Exception {
		ModelAndView mav = new ModelAndView("/accountdetail/bbsItemslist");
		/** 当前登入用户信息 */
		ShiroUser currentMember = currentUser();

		String userIdStr = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String pageNum = request.getParameter("pageNum");

		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		if (userName != null) {
			userName = StrinUtils.stringEncryptDe(userName); // 解密用户名
		}
		// 将要查看的用户
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在

			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Boolean isSearchAll = null;

		// 如果当前用户是管理员或者自己那么将查询所有，如果是普通用户那么查询 未删除帖子
		if (currentMember != null) {
			if (currentMember.getUserName().equals(member.getUsername())) {
				isSearchAll = true;
			}
			if (accountBBSPostListService.isCurrentLookerhasPower(member.getId())) {
				isSearchAll = true;
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", member.getId());
		param.put("isSearchAll", isSearchAll);
		// 查询发帖列表
		Page resultPages = accountBBSPostListService.queryBbsNotesPage(param, new Page(conver(pageNum), BusinessConstants.DEFAULT_PAGE_SIZE));
		mav.addObject("resultPages", resultPages);
		return mav;
	}
	/**
	 * 
	 * <p>
	 * Description:修改用户名字<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月24日
	 * @param username
	 * @param oldname
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "/modifyUsername")
	@ResponseBody
	public MessageBox modifyUsername(String username,String oldname){
		ShiroUser loginuser=currentUser();
		if(loginuser==null){
			return new MessageBox("0","请登录再修改用户名");
		}
		String realName=StrinUtils.stringEncryptDe(oldname);//解密
		if(!(loginuser.getUserName().equals(realName))){
			return new MessageBox("0","非法操作");
		}
		String result=memberServiceImpl.modifyUsername(loginuser,username,HttpTookit.getRealIpAddr(currentRequest()));
		if(!result.equals(BusinessConstants.SUCCESS)){ 
			return new MessageBox("0",result);
		}
		return new MessageBox("1","用户名修改成功");
	}
	/**
	 * <p>
	 * Description:查看用户投资列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月14日
	 * @param request
	 * @param username
	 * @param pages 接收第几页，每页条数
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "fixInvestlist")
	public ModelAndView fixInvestlist(HttpServletRequest request, String username, @ModelAttribute Page pages) throws Exception {
		ModelAndView mav = new ModelAndView("/accountdetail/fixInvestlist");
		String userIdStr = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String pageNum = request.getParameter("pageNum");
		if (StringUtils.isEmpty(userName) && StringUtils.isEmpty(userIdStr)) {
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		if (userName != null) {
			userName = StrinUtils.stringEncryptDe(userName); // 解密用户名
		}
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(StringUtils.isEmpty(userName) ? null : userName);
		memberCnd.setId(StringUtils.isEmpty(userIdStr) ? null : Integer.valueOf(userIdStr));

		MemberVo member = memberServiceImpl.queryMemberByCnd(memberCnd);
		if (null == member) {// 用户不存在
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", member.getId());
		Page resultFixPages = accountDetailServiceImpl.queryFixList(params, new Page(conver(pageNum),BusinessConstants.DEFAULT_PAGE_SIZE));
		mav.addObject("resultPages", resultFixPages);
		return mav;
	}
}
