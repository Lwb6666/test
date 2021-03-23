package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.transfer.constant.TransferConstant;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;

/**
 * 我的债转
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title MyTransferController.java
 * @package com.dxjr.wx.favorite.controller
 * @author huangpin
 * @version 0.1 2015年2月10日
 */
@Controller
@RequestMapping(value = "/wx/favorite")
public class MyTransferController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyTransferController.class);
	@Autowired
	private TransferService transferService;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	private VipLevelService vipLevelService;

	/**
	 * 我的债转-可转让
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年2月10日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/myTransfer/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> unCollection_record(@PathVariable int type, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return result;
		}

		TransferCnd transferCnd = new TransferCnd();
		transferCnd.setUserId(shiroUser.getUserId());
		transferCnd.setType(type);
		List<?> list;
		try {
			Page page = new Page(pageNum, 10);
			if (type == 1) {
				transferService.queryTransferClaim(transferCnd, page);
			}
			if (type == 2 || type == 5) {
				transferCnd.setStatus(Constants.TRANSFER_STATU_ING);
				transferService.queryMyTransfer(transferCnd, page);
			}
			if (type == 3 || type == 4) {
				transferCnd.setStatus(Constants.TRANSFER_SUCCESS);
				transferService.queryMyTransfer(transferCnd, page);
			}
			list = page.getResult();
			if (list != null && list.size() > 0) {
				if (list.size() == 10) {
					result.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				result.put("transferList", list);
			}
		} catch (Exception e) {
			logger.error("微信-获取我的债转-可转让数据异常：", e);
		}

		return result;
	}

	/**
	 * 取消债转
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2015年2月11日
	 * @param ip
	 * @return MessageBox
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/cancelTransfer")
	@ResponseBody
	public MessageBox cancelTransfer(Integer id, String payPassword, String ip) {

		ShiroUser shiroUser = currentUser();
		if (id == null || StringUtils.isEmpty(payPassword) || StringUtils.isEmpty(ip)) {
			return MessageBox.build("0", "参数不正确");
		}
		try {
			// 验证交易密码
			payPassword = MD5.toMD5(payPassword);
			MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
			if (!mem.getPaypassword().equals(payPassword)) {
				return MessageBox.build("0", "交易密码错误，请重新输入");
			}
			BTransfer bTransfer = new BTransfer();
			bTransfer.setId(id);
			bTransfer.setCancelUser(shiroUser.getUserId());
			bTransfer.setUserId(shiroUser.getUserId());
			bTransfer.setCancelIp(ip);

			transferService.updateMyTransferForCancel(bTransfer);

			if ("0001".equals(bTransfer.getRemark())) {
				return MessageBox.build("1", "取消转让成功");
			} else {
				return MessageBox.build("0", "取消转让失败");
			}

		} catch (Exception e) {
			logger.error("微信取消转让异常", e);
			return MessageBox.build("0", "取消转让失败，请联系管理人员！");

		}
	}

	@RequestMapping(value = "/initSaleTransfer/{tenderId}")
	@ResponseBody
	public Map<String, Object> initSale(@PathVariable Integer tenderId) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			Integer userId = currentUser().getUserId();
			BTransfer transfer = transferService.queryTransferByTenderIdAndUserId(tenderId, userId);
			transfer.setTenderId(tenderId);
			transfer.setCoef(new BigDecimal(1));

			if (vipLevelService.getIsSvipByUserId(userId)) {
				transfer.setManageFee(BigDecimal.ZERO);
			} else {
				if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-07-01 00:00:00").before(new Date())){
					transfer.setManageFee(transfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATANEW)).setScale(2, RoundingMode.HALF_UP));
				}else{
					transfer.setManageFee(transfer.getCapital().multiply(new BigDecimal(TransferConstant.MANAGERFREERATA)).setScale(2, RoundingMode.HALF_UP));
				}
			}

			transfer.setAccount(transfer.getAccount().setScale(2, RoundingMode.HALF_UP));
			transfer.setAccountReal(transfer.getAccount());

			map.put("t", transfer);

			return map;
		} catch (Exception e) {
			logger.error("微信-债转专区-基本信息异常", e);
		}
		return new HashMap<String, Object>();
	}
}
