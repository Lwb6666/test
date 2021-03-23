package com.dxjr.portal.account.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.dxjr.portal.account.vo.LoginRemindLogVo;
import com.dxjr.portal.account.vo.MyAccountApproMsgVo;
import com.dxjr.portal.account.vo.YearCollect;

/**
 * <p>
 * Description:我的帐号业务类<br />
 * </p>
 * 
 * @title AccountService.java
 * @package com.dxjr.account.service
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
public interface MyAccountService {
	/**
	 * <p>
	 * Description:验证帐号是否通过了认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param memberId
	 * @return
	 * @throws Exception MyAccountApproMsgVo
	 */
	public MyAccountApproMsgVo validateAccountAppro(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:上传头像<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年9月14日
	 * @param files
	 * @return
	 * @throws Exception String
	 */
	public String saveHeadImg(MultipartFile file, int userId, HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:添加vip提醒日志<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年12月3日
	 * @param LoginRemindLogVo
	 * @return
	 * @throws Exception String
	 */
	public void saveLoginRemindLog(LoginRemindLogVo lv) throws Exception;
	public YearCollect queryDayCollect(String dateTime, Integer userId) throws Exception;

	public List<YearCollect> queryYearCollect(String year, Integer userId) throws Exception;
}
