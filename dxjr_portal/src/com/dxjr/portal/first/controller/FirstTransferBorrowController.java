package com.dxjr.portal.first.controller;

import java.math.BigDecimal;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.first.service.FirstTransferBorrowService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细控制类<br />
 * </p>
 * 
 * @title FirstTransferBorrowController.java
 * @package com.dxjr.portal.first.controller
 * @author justin.xu
 * @version 0.1 2015年3月18日
 */
@Controller
@RequestMapping(value = "/zhitongche/transferborrow")
public class FirstTransferBorrowController extends BaseController {

	Logger logger = LoggerFactory.getLogger(FirstTransferBorrowController.class);

	@Autowired
	private FirstTransferBorrowService firstTransferBorrowService;

	@Autowired
	private FirstTransferService firstTransferService;

	/**
	 * <p>
	 * Description:查询我的账号中-直通车转让对应标统计列表信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月18日
	 * @param tenderRealId
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping("/queryCanList/{tenderRealId}/{pageNo}")
	@RequiresAuthentication
	public ModelAndView queryCanTransferList(@PathVariable("tenderRealId") Integer tenderRealId, @PathVariable("pageNo") Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/transfer/myCanBorrowList");
		try {
			ShiroUser shiroUser = currentUser();
			FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
			firstTransferBorrowCnd.setUserId(shiroUser.getUserId());
			firstTransferBorrowCnd.setFirstTenderRealId(tenderRealId);
			Page page = firstTransferBorrowService.queryCanTransferBorrowList(firstTransferBorrowCnd, (new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE)));
			// 债权剩余价值合计
			BigDecimal accountSum = firstTransferBorrowService.queryCanTransferBorrowAccountSum(firstTransferBorrowCnd);
			// 直通车转让信息
			FirstTransferTypeVo firstTransferTypeVo = firstTransferService.queryFirstTransferTypeByCnd(tenderRealId, shiroUser.getUserId());
			
			mv.addObject("page", page);
			mv.addObject("tenderRealId", tenderRealId);
			mv.addObject("accountSum", accountSum);
			mv.addObject("bondsAccount", firstTransferTypeVo.getBondsAccount());
			mv.addObject("useMoney", firstTransferTypeVo.getUseMoney());
		} catch (Exception e) {
			logger.error("查询我的账号中-直通车转让对应标统计列表信息出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:直通车转让资金明细表查询<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月19日
	 * @param transferId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryTransferBorrowList/{transferId}/{pageNo}")
	public ModelAndView queryTransferBorrowListById(@PathVariable("transferId") Integer transferId, @PathVariable("pageNo") Integer pageNo) {
		// 跳转页面设置
		ModelAndView mv = new ModelAndView("first/transfer/transferBorrowDetailInner");
		try {
			// 直通车转让资金明细表查询
			Page page = firstTransferBorrowService.queryTransferBorrowListByTransferId(transferId, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			// 债权剩余价值合计
			BigDecimal borrowCollectionAccountSum = firstTransferBorrowService.queryBorrowCollectionAccountSum(transferId);
			// 获取直通车转让信息
			FirstTransferVo firstTransferVo = firstTransferService.queryTransferById(transferId);
			mv.addObject("page", page);
			mv.addObject("borrowCollectionAccountSum", borrowCollectionAccountSum);
			mv.addObject("useMoney", firstTransferVo.getUseMoney());
		} catch (Exception e) {
			// 异常页面设置
			mv.setViewName("common/404");
			logger.error("查询债权价格详情出错", e);
		}
		return mv;
	}
}
