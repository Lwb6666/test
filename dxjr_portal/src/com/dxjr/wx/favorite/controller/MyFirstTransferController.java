package com.dxjr.wx.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.first.service.FirstTransferBorrowService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferCancelVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;

/**
 * 我的债转-直通车
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title MyTransferController.java
 * @package com.dxjr.wx.favorite.controller
 * @author huangpin
 * @version 0.1 2015年2月10日
 */
@Controller
@RequestMapping(value = "/wx/favorite/first")
public class MyFirstTransferController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyFirstTransferController.class);
	@Autowired
	private TransferService transferService;
	@Autowired
	private FirstTransferService firstTransferService;
	@Autowired
	private FirstTransferBorrowService firstTransferBorrowService;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	private VipLevelService vipLevelService;

	/**
	 * 微信-我的直通车转让-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年2月10日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> list(@PathVariable int type, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return result;
		}

		List<?> list;
		try {
			Page page = new Page(pageNum, 10);
			if (type == 1) {
				FirstTransferTypeCnd cnd = new FirstTransferTypeCnd();
				cnd.setUserId(shiroUser.getUserId());
				cnd.setType(type);
				firstTransferService.queryPageCanTransferByCnd(cnd, page);
			}
			if (type == 2) {
				FirstTransferCnd c = new FirstTransferCnd();
				c.setUserId(shiroUser.getUserId());
				c.setTransferStatus(Constants.FIRST_TRANSFER_STATU_ING);
				firstTransferService.queryMyFirstTransferList(c, page);
			}
			if (type == 3) {
				FirstTransferCnd c = new FirstTransferCnd();
				c.setUserId(shiroUser.getUserId());
				c.setTransferStatus(Constants.FIRST_TRANSFER_SUCCESS);
				firstTransferService.queryMyFirstTransferList(c, page);
			}
			if (type == 4) {
				FirstTransferCnd c = new FirstTransferCnd();
				c.setUserId(shiroUser.getUserId());
				c.setTransferStatus(Constants.FIRST_TRANSFER_SUCCESS);
				firstTransferService.queryMyFirstTransferedList(c, page);
			}
			list = page.getResult();
			if (list != null && list.size() > 0) {
				if (list.size() == 10) {
					result.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				result.put("transferList", list);
			}
		} catch (Exception e) {
			logger.error("微信-我的直通车转让-列表异常：", e);
		}

		return result;
	}

	/**
	 * 微信-我的直通车转让-取消转让
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月20日
	 * @param transferId
	 * @param payPassword
	 * @param ip
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/cancelTransfer")
	@ResponseBody
	public MessageBox cancelTransfer(Integer transferId, String payPassword, String ip) {

		ShiroUser shiroUser = currentUser();
		if (transferId == null || StringUtils.isEmpty(payPassword)) {
			return MessageBox.build("0", "参数不正确");
		}
		try {
			// 验证交易密码
			payPassword = MD5.toMD5(payPassword);
			MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
			if (!mem.getPaypassword().equals(payPassword)) {
				return MessageBox.build("0", "交易密码错误，请重新输入");
			}
			FirstTransferCancelVo vo = new FirstTransferCancelVo();
			vo.setUserId(shiroUser.getUserId());
			vo.setPlatform(shiroUser.getPlatform());
			vo.setUserName(shiroUser.getUserName());
			vo.setAddIp(ip);
			vo.setFirstTransferId(transferId);
			String result = firstTransferService.saveCancelFirstTransfer(vo);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return new MessageBox("0", result);
			}
		} catch (Exception e) {
			logger.error("微信-我的直通车转让-取消转让", e);
			return MessageBox.build("0", "取消直通车转让异常,请刷新页面或稍后重试！");
		}
		return new MessageBox("1", "取消直通车转让成功");
	}

	/**
	 * 微信-我的直通车转让-填写转让信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月20日
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/initSaleTransfer/{id}")
	@ResponseBody
	public Map<String, Object> initSale(@PathVariable Integer id) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			Integer userId = currentUser().getUserId();
			FirstTransferTypeVo firstTransferTypeVo = firstTransferService.queryFirstTransferTypeByCnd(id, userId);

			if (firstTransferTypeVo.getParentId() == null) {
				map.put("t", firstTransferTypeVo);
			}

			FirstTransferBorrowCnd cnd = new FirstTransferBorrowCnd();
			cnd.setUserId(userId);
			cnd.setFirstTenderRealId(id);
			map.put("accountSum", firstTransferBorrowService.queryCanTransferBorrowAccountSum(cnd));// 直通车所投标债权价格总额

			return map;
		} catch (Exception e) {
			logger.error("微信-我的直通车转让-填写转让信息异常", e);
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 微信-我的直通车转让-转让信息-债权价格详情
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月20日
	 * @param transferId
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/initSaleTransfer/bidList/{transferId}/{pageNum}")
	@ResponseBody
	public Map<String, Object> firstBidList(@PathVariable("transferId") Integer transferId, @PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FirstTransferBorrowCnd cnd = new FirstTransferBorrowCnd();
			cnd.setUserId(currentUser().getUserId());
			cnd.setFirstTenderRealId(transferId);
			List<?> list = firstTransferBorrowService.queryCanTransferBorrowList(cnd, (new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE))).getResult();
			if (list != null) {
				map.put("bidList", list);
				if (list.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
		} catch (Exception e) {
			logger.error("微信-我的直通车转让-转让信息-债权价格详情获取异常", e);
		}
		return map;
	}
}
