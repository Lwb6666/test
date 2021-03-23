package com.dxjr.portal.borrow.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrower;
import com.dxjr.base.entity.Mortgage;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.SalariatBorrowService;
import com.dxjr.portal.borrow.vo.SalariatBorrowVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

@Controller
@RequestMapping(value = "/commerceBorrow")
public class CommerceBorrowController extends BaseController {

	private static final Logger logger = Logger.getLogger(SalariatBorrowController.class);

	@Autowired
	private SalariatBorrowService salariatBorrowService;

	/**
	 * 诚商贷申请初始化
	 * <p>
	 * Description:我要融资-诚商贷申请-初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月16日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "initApply")
	public ModelAndView initApply(@RequestParam String viewType) {
		ModelAndView mv = null;
		try {
			ShiroUser shiroUser = currentUser();
			mv = salariatBorrowService.initApply(shiroUser, Constants.BORROW_PRODUCT_TYPE_COMMERCE, viewType);
		} catch (Exception e) {
			logger.error("诚商贷申请初始化异常", e);
		}
		return mv;
	}

	/**
	 * 诚商贷申请
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
	@RequestMapping(value = "applyCommerceBorrow")
	@ResponseBody
	public MessageBox applyCommerceBorrow(@ModelAttribute SalariatBorrowVo salariatBorrowVo, @ModelAttribute Borrower borrower, @ModelAttribute Mortgage mortgage, @RequestParam String checkcode, @RequestParam Integer businessUserId) {
		try {
			ShiroUser shiroUser = currentUser();
			String addip = HttpTookit.getRealIpAddr(currentRequest());
			String randomCode = (String) currentSession().getAttribute("randomCode");
			if (randomCode == null) {
				return MessageBox.build("0", "验证码不存在，请刷新。");
			} else if (!checkcode.equals(randomCode)) {
				return MessageBox.build("0", "验证码输入错误。");
			}
			if (businessUserId == null) {
		        return MessageBox.build("0", "请选择权证人员");
		    }		      
			return salariatBorrowService.saveApplySalariat(shiroUser, salariatBorrowVo, borrower, mortgage, addip, Constants.BORROW_PRODUCT_TYPE_COMMERCE, businessUserId);
		} catch (Exception e) {
			logger.error("诚商贷申请保存异常", e);
			return MessageBox.build("0", "诚商贷申请保存异常");
		}
	}

}
