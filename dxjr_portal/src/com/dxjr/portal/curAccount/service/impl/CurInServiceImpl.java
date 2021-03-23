package com.dxjr.portal.curAccount.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.mapper.CurAccountMapper;
import com.dxjr.portal.curAccount.mapper.CurInMapper;
import com.dxjr.portal.curAccount.service.CurInService;
import com.dxjr.portal.curAccount.vo.CurInLaunchVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/****
 * 
 * 
 * <p>
 * Description: 活期宝转入(投标)记录 BLL <br />
 * </p>
 * 
 * @title CurInServiceImpl.java
 * @package com.dxjr.portal.curAccount.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Service
public class CurInServiceImpl implements CurInService {
	
	Logger logger = LoggerFactory.getLogger(CurInServiceImpl.class);
	@Autowired
	private CurInMapper curInMapper;
	
	@Autowired
	private CurAccountMapper curAccountMapper;
	
	@Override
	public BigDecimal queryNoDrawMoneyTotalByUserIdAndDate(Integer userId, Date date) throws Exception {
		return curInMapper.queryNoDrawMoneyTotalByUserIdAndDate(userId, date);
	}
	
	@Override
	public String saveCurrentIn(CurInLaunchVo curInLaunchVo) throws Exception {
		String result = BusinessConstants.SUCCESS;
		try {
			// 验证是否第一次开通活期宝
			CurAccount curAccount = curAccountMapper.selectByUserId(curInLaunchVo.getUserId());
			// 新增活期宝账户
			if (curAccount == null) {
				// 新增活期宝账户
				CurAccount curAccountInsert = new CurAccount();
				// 用户ID
				curAccountInsert.setUserId(curInLaunchVo.getUserId());
				// 活期宝账户金额
				curAccountInsert.setTotal(BigDecimal.ZERO);
				// 可产生收益的金额
				curAccountInsert.setUseMoney(BigDecimal.ZERO);
				// 未产生收益的金额
				curAccountInsert.setNoUseMoney(BigDecimal.ZERO);
				// 累计收益金额
				curAccountInsert.setInterestTotal(BigDecimal.ZERO);
				// 昨日收益金额
				curAccountInsert.setInterestYesterday(BigDecimal.ZERO);
				// 开户人
				curAccountInsert.setAdduser(curInLaunchVo.getUserId());
				// 失效时间
				curAccountInsert.setEndtime(DateUtils.parse("2099-12-31 23:59:59", DateUtils.YMD_HMS));
				// 最后更新人
				curAccountInsert.setLastUpUser(curInLaunchVo.getUserId());
				// 状态
				curAccountInsert.setStatus(0);
				// 新增账户信息
				curAccountMapper.insert(curAccountInsert);
			}

			// 保存活期宝转入信息
			Map<String, Object> params = new HashMap<String, Object>();

			// 用户ID
			params.put("userId", curInLaunchVo.getUserId());
			// 加入金额
			params.put("account", curInLaunchVo.getTenderMoney());
			// 转入方式(1:可用余额转入,2:投标资金退回活期宝, 3:购买债权资金退回活期宝)
			params.put("tenderType", 1);
			// 操作类型 (101 未产生收益转可产生收益  102 可用余额转入  103 投标资金退回  104 购买债权资金退回  105  活期生息 201 转出到可用余额   202 投标转出  203 开通直通车转出  204 购买债权转出   205 购买直通车转让转出)
			params.put("curLogType", 102);
			// 日志类型 (201:可用余额转出到活期宝)
			params.put("accountLogType", 201);
			// 转出ID
			params.put("outId", null);
			// IP地址
			params.put("addip", curInLaunchVo.getAddIp());
			// 平台来源
			params.put("platform", curInLaunchVo.getPlatform());
			curInMapper.saveCurrentIn(params);
			// 存储过程返回参数
			String msg = params.get("msg").toString();
			if (!"00000".equals(msg)) {
				return msg;
			}
		} catch (Exception e) {
			throw new Exception("保存活期宝转入信息出错！");
		}
		return result;
	}

}
