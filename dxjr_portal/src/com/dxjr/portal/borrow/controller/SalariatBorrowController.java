package com.dxjr.portal.borrow.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrower;
import com.dxjr.base.entity.Mortgage;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.SalariatBorrowService;
import com.dxjr.portal.borrow.util.BorrowUtil;
import com.dxjr.portal.borrow.vo.SalariatBorrowVo;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

@Controller
@RequestMapping(value = "/salariatBorrow")
public class SalariatBorrowController extends BaseController {

	private static final Logger logger = Logger.getLogger(SalariatBorrowController.class);

	@Autowired
	private SalariatBorrowService salariatBorrowService;

	/**
	 * 诚薪贷申请初始化
	 * <p>
	 * Description:我要融资-诚薪贷申请-初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月16日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "initApply")
	@RequiresAuthentication
	public ModelAndView initApply(@RequestParam String viewType) {
		ModelAndView mv = null;
		try {
			ShiroUser shiroUser = currentUser();
			mv = salariatBorrowService.initApply(shiroUser, Constants.BORROW_PRODUCT_TYPE_SALARIAT, viewType);
		} catch (Exception e) {
			logger.error("诚薪贷申请初始化异常", e);
		}
		return mv;
	}

	/**
	 * 诚薪贷申请
	 * <p>
	 * Description:我要融资-诚薪贷申请-保存申请信息<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月16日
	 * @param salariatBorrowVo
	 * @param borrower
	 * @param mortgage
	 * @return MessageBox
	 */
	@RequestMapping(value = "applySalariatBorrow")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox applySalariatBorrow(@ModelAttribute SalariatBorrowVo salariatBorrowVo, @ModelAttribute Borrower borrower, @ModelAttribute Mortgage mortgage, @RequestParam String checkcode, @RequestParam Integer businessUserId) {
		try {
			String msg = BorrowUtil.checkCode(currentSession(), checkcode);
			if (!"".equals(msg)) {
				return MessageBox.build("0", msg);
			}
			if (businessUserId == null) {
				return MessageBox.build("0", "请选择权证人员");
		    }
			ShiroUser shiroUser = currentUser();
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			return salariatBorrowService.saveApplySalariat(shiroUser, salariatBorrowVo, borrower, mortgage, addip, Constants.BORROW_PRODUCT_TYPE_SALARIAT,businessUserId);
		} catch (Exception e) {
			logger.error("诚薪贷申请保存异常", e);
			return MessageBox.build("0", "诚薪贷申请保存异常");
		}
	}

	/**
	 * 资料上传初始化
	 * <p>
	 * Description:资料上传初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月17日
	 * @param borrowId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "initUpload")
	@RequiresAuthentication
	public ModelAndView initUpload(@RequestParam Integer borrowId) {
		ModelAndView mv = new ModelAndView("/borrow/borrowInfoUpload");
		try {
			ShiroUser shiroUser = currentUser();
			mv = salariatBorrowService.initUpload(shiroUser, borrowId);
		} catch (Exception e) {
			logger.error("诚薪贷资料上传初始化异常", e);
		}
		return mv;

	}

	/**
	 * 提交上传资料
	 * <p>
	 * Description:上传图片<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param files
	 * @param borrowId
	 * @param style
	 * @return MessageBox
	 */
	@RequestMapping(value = "uploadBorrowInfos", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	@RequiresAuthentication
	public String uploadBorrowInfos(@RequestParam("files") MultipartFile[] files, @RequestParam Integer borrowId, @RequestParam Integer style) {
		MessageBox msg = null;
		try {
			ShiroUser shiroUser = currentUser();
			msg = salariatBorrowService.saveBorrowInfos(shiroUser, files, borrowId, style, currentRequest());
		} catch (Exception e) {
			logger.error("诚薪贷资料上传异常", e);
			msg = MessageBox.build("0", "诚薪贷资料上传异常");
		}
		return JsonUtils.bean2Json(msg);
	}

	/**
	 * 上传历史标图片
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowId
	 * @param oldBorrowId
	 * @return String
	 */
	@RequestMapping(value = "uploadOldBorrowInfos")
	@ResponseBody
	@RequiresAuthentication
	@RequiresRoles("borrow")
	public String uploadOldBorrowInfos(@RequestParam Integer borrowId, @RequestParam Integer oldBorrowId) {
		MessageBox msg = null;
		try {
			ShiroUser shiroUser = currentUser();
			msg = salariatBorrowService.saveBorrowInfosFromOldBorrow(shiroUser, HttpTookit.getRealIpAddr(currentRequest()), borrowId, oldBorrowId);
		} catch (Exception e) {
			logger.error("上传历史标图片异常", e);
			msg = MessageBox.build("0", "上传历史标图片异常");
		}
		return JsonUtils.bean2Json(msg);
	}

	/**
	 * 删除上传资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param docId
	 * @param borrowId
	 * @return MessageBox
	 */
	@RequestMapping(value = "deleteDoc")
	@ResponseBody
	@RequiresAuthentication
	public MessageBox deleteDoc(@RequestParam Integer docId, @RequestParam Integer borrowId) {
		MessageBox msg = new MessageBox();
		try {
			ShiroUser shiroUser = currentUser();
			msg = salariatBorrowService.deleteDoc(shiroUser, docId, borrowId);
		} catch (Exception e) {
			logger.error("资料删除异常", e);
			msg = MessageBox.build("0", "资料删除异常");
		}
		return msg;
	}

	/**
	 * 初始化编辑，限Borrow用户
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "initEditBorrow/{borrowId}")
	@RequiresAuthentication
	@RequiresRoles("borrow")
	public ModelAndView initEditBorrow(HttpServletResponse response, @PathVariable Integer borrowId) {
		try {
			return salariatBorrowService.initEditBorrow(borrowId, currentUser());
		} catch (Exception e) {
			logger.error("借款标修改初始化异常", e);
			return new ModelAndView("/common/404");
		}
	}

	/**
	 * 编辑借款标，限Borrow用户
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param salariatBorrowVo
	 * @param borrower
	 * @param mortgage
	 * @param checkcode
	 * @return MessageBox
	 */
	@RequestMapping(value = "editBorrow")
	@RequiresAuthentication
	@RequiresRoles("borrow")
	@ResponseBody
	public MessageBox editBorrow(@ModelAttribute SalariatBorrowVo salariatBorrowVo, @ModelAttribute Borrower borrower, @ModelAttribute Mortgage mortgage, @RequestParam String checkcode, @RequestParam Integer businessUserId) {
		try {
			String msg = BorrowUtil.checkCode(currentSession(), checkcode);
			if (!"".equals(msg)) {
				return MessageBox.build("0", msg);
			}
			if (businessUserId == null) {
		        return MessageBox.build("0", "请选择权证人员");
		    }
			ShiroUser shiroUser = currentUser();

			return salariatBorrowService.updateBorrow(shiroUser, salariatBorrowVo, borrower, mortgage, salariatBorrowVo.getId(), businessUserId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("借款标修改保存异常", e);
			return MessageBox.build("0", "借款标修改保存异常");
		}
	}

	/**
	 * 删除借款标全部资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月17日
	 * @param borrowId
	 * @return MessageBox
	 */
	@RequestMapping(value = "deleteAllDoc/{borrowId}")
	@ResponseBody
	@RequiresAuthentication
	@RequiresRoles("borrow")
	public MessageBox deleteAllDoc(@PathVariable Integer borrowId) {
		MessageBox msg = new MessageBox();
		try {
			ShiroUser shiroUser = currentUser();
			msg = salariatBorrowService.deleteAllDoc(shiroUser, borrowId);
		} catch (Exception e) {
			logger.error("资料删除异常", e);
			msg = MessageBox.build("0", "资料删除异常");
		}
		return msg;
	}

}
