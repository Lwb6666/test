package com.dxjr.wx.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.AutoInvestConfig;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestConfigRecordService;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestService;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordCnd;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

@Controller
@RequestMapping(value = "/wx/autoTender")
public class AutoTenderController extends BaseController {

	public Logger logger = Logger.getLogger(AutoTenderController.class);

	@Autowired
	private AutoInvestConfigRecordService autoService;
	@Autowired
	private AutoInvestService autoInvestService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VipLevelService vipLevelService;

	/**
	 * 微信-自动投标设置列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年12月8日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/setList")
	@ResponseBody
	public Map<String, Object> auto() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currentUser() != null) {
				List<AutoInvestConfigVo> setList = autoInvestService.queryListByUserId(currentUser().getUserId());
				map.put("setList", setList);
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
				for (AutoInvestConfigVo autoInvestConfigVo : setList) {
					if (autoInvestConfigVo.getStatus() == 1) {
						if (autoInvestConfigVo.getAutoType() == BusinessConstants.AUTO_TYPE_ONE) {
							String uptime_str = DateUtils.timeStampToDate(autoInvestConfigVo.getUptime().substring(0, 10), DateUtils.YMD_DASH);
							String reload_uptime = DateUtils.format(DateUtils.dayOffset(DateUtils.parse(uptime_str, DateUtils.YMD_DASH), 36), DateUtils.YMD_DASH);

							map.put("pledgeTime", reload_uptime);
						}

						if (autoInvestConfigVo.getAutoType() == BusinessConstants.AUTO_TYPE_TWO) {
							String uptime_str = DateUtils.timeStampToDate(autoInvestConfigVo.getUptime().substring(0, 10), DateUtils.YMD_DASH);
							String reload_uptime = DateUtils.format(DateUtils.dayOffset(DateUtils.parse(uptime_str, DateUtils.YMD_DASH), 36), DateUtils.YMD_DASH);
							map.put("netTime", reload_uptime);
						}
					}
				}
				if (vipLevelService.getIsSvipByUserId(currentUser().getUserId())) {
					map.put("isSvip", "yes");
				}
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-自动投标设置列表获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-自动投标记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年12月8日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/autoList/{pageNum}")
	@ResponseBody
	public Map<String, Object> autoList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currentUser() != null) {
				AutoInvestConfigRecordCnd cnd = new AutoInvestConfigRecordCnd();
				cnd.setUser_id(currentUser().getUserId());
				List<?> autoList = autoService.queryListByAutoInvestConfigRecordCnd(cnd, pageNum, BusinessConstants.DEFAULT_PAGE_SIZE).getResult();
				if (autoList != null) {
					map.put("autoList", autoList);
					if (autoList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
						map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-自动投标记录获取异常", e);
		}
		return map;
	}

	/**
	 * <p>
	 * Description:校验是否可以再次添加投标<br />
	 * </p>
	 * @author zhangpeng
	 * @version 0.1 2015年6月18日
	 * @param request
	 * @param
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkAutoCount")
	@ResponseBody
	public Map<String, Object> checkAutoCount() {
		ShiroUser shiroUser = currentUser();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		String isCheck = null;
		try {
			count = autoInvestService.queryCountByUserId(shiroUser.getUserId());
		} catch (Exception e) {
			logger.error("查询是否自动投标异常", e);
		}
		if (count < 1) {
			isCheck = "true";
		} else {
			isCheck = "false";
		}
		map.put("isCheck", isCheck);
		return map;
	}

	/**
	 * <p>
	 * Description:新增或修改自动投标信息<br />
	 * </p>
	 * @author zhangpeng
	 * @version 0.1 2015年6月18日
	 * @param request
	 * @param autoInvestConfig
	 * @return map
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/saveAutoInvest")
	@ResponseBody
	public Map<String, Object> saveAutoInvest(AutoInvestConfig autoInvestConfig) {
		Map<String, Object> map = null;
		try {
			ShiroUser shiroUser = currentUser();
			// 新增或修改规则
			String msg = autoInvestService.insertOrUpdate(shiroUser.getUserId(), autoInvestConfig, autoInvestConfig.getSetip());
			map = new HashMap<String, Object>();
			map.put("msg", msg);
			return map;
		} catch (Exception e) {
			logger.error("增加自动投标异常", e);
			return map;
		}
	}
}
