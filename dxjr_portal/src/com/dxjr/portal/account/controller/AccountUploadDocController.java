package com.dxjr.portal.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.AccountUploadDocService;
import com.dxjr.portal.account.vo.AccountUploadDocCnd;
import com.dxjr.portal.account.vo.AccountUploadDocVo;
import com.dxjr.portal.base.BaseController;

/**
 * <p>
 * Description:借款标上传资料控制类<br />
 * </p>
 * 
 * @title AccountUploadDocController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年9月14日
 */
@Controller
@RequestMapping(value = "/borrowdoc")
public class AccountUploadDocController extends BaseController {

	private final static Logger logger = Logger.getLogger(AccountUploadDocController.class);

	@Autowired
	private AccountUploadDocService accountUploadDocService;

	/**
	 * <p>
	 * Description:根据类型和借款标id显示认证图片集合，（用于投标详情）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月14日
	 * @param request
	 * @param borrowId
	 * @param style
	 * @return ModelAndView
	 */
	@RequestMapping(value = "showInner/{borrowId}/{style}")
	public ModelAndView toShowDocs(HttpServletRequest request, @PathVariable Integer borrowId, @PathVariable Integer style) {
		ModelAndView mv = new ModelAndView("investment/toInvest_borrowDocs");
		try {
			AccountUploadDocCnd accountUploadDocCnd = new AccountUploadDocCnd();
			accountUploadDocCnd.setBorrowId(borrowId);
			accountUploadDocCnd.setStyle(style);
			List<AccountUploadDocVo> accountUploadDocVos = accountUploadDocService.queryAccountUploadDocList(accountUploadDocCnd);
			mv.addObject("accountUploadDocVos", accountUploadDocVos);
		} catch (Exception e) {
			logger.error("根据借款标ID和类型显示认证资料出错", e);
		}
		return mv;
	}
}
