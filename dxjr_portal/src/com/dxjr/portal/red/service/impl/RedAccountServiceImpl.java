package com.dxjr.portal.red.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Member;
import com.dxjr.common.page.Page;
import com.dxjr.portal.lottery.mapper.LotteryChanceRuleInfoMapper;
import com.dxjr.portal.lottery.vo.LotteryChanceRuleInfoVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.sms.service.SmsSendService;

@Service
public class RedAccountServiceImpl implements RedAccountService {

	@Autowired
	RedAccountMapper redMapper;
	@Autowired
	RedAccountLogMapper redAccountLogMapper;
	@Autowired
	LotteryChanceRuleInfoMapper lotteryChanceRuleInfoMapper;
	@Autowired
	SmsSendService smsSendService;

	public int getUnuseCount(int userId) {
		return redMapper.getUnuseCount(userId);
	}

	public List<RedAccount> getMyReds(int userId) {
		return redMapper.getMyReds(userId);
	}

	/**
	 * <p>
	 * Description:显示已过期、已冻结、已使用红包。<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月4日
	 * @param redAccountCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryPageByExpiredRed_hb(RedAccountCnd redAccountCnd, Page page) throws Exception {

		int totalCount = 0;
		try {
			totalCount = redMapper.queryExpiredRedCount(redAccountCnd);
			page.setTotalCount(totalCount);
			List<RedAccount> list = redMapper.queryExpiredRed(redAccountCnd, page);
			page.setResult(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * <p>
	 * Description:显示未开启和未使用的红包。<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月4日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             List<RedAccount>
	 */
	public List<RedAccount> queryPageByOpen_hb(int userId) throws Exception {

		return redMapper.queryOpenRed(userId);
	}

	/**
	 * <p>
	 * Description:更新红包表的状态为1<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月4日
	 * @param redAccountCnd
	 * @throws Exception
	 *             void
	 */
	public void updateRedDotState(Integer userId) throws Exception {
		redMapper.updateRedDotState(userId);
	}

	/**
	 * <p>
	 * Description:查询是否有红包入账户<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年11月4日
	 * @param redAccountCnd
	 * @return
	 * @throws Exception
	 *             RedAccount
	 */
	public RedAccount queryRedDotState(Integer userId) throws Exception {
		return redMapper.queryRedDotState(userId);
	}

	public RedAccountLog updateRedByZhitongche(int redId, String ip, int USEBIZ_ID) throws Exception {

		RedAccount redAccount = redMapper.getById(redId);

		RedAccountLog redAccountLog = new RedAccountLog();

		if (redAccount != null) {
			// 更新红包账户表
			redAccount.setStatus(4);
			redAccount.setUseTime(new Date());
			redAccount.setLastUpdateTime(new Date());
			redAccount.setUsebizType(2);// 直通车
			redAccount.setUsebizId(USEBIZ_ID);
			redAccount.setRemark("用户使用红包投直通车【车ID：" + USEBIZ_ID + "】，红包使用");
			redMapper.updateByPrimaryKeySelective(redAccount);
			// 更新红包账户日志表

			redAccountLog.setAddip(ip);
			redAccountLog.setUserId(redAccount.getUserId());
			redAccountLog.setUsebizId(USEBIZ_ID);
			redAccountLog.setOpttime(new Date());
			redAccountLog.setUseTime(new Date());
			redAccountLog.setRedId(redId);
			redAccountLog.setMoney(redAccount.getMoney());
			redAccountLog.setBizType(2);
			redAccountLog.setStatus(4);
			redAccountLog.setRemark("用户使用红包投直通车【车ID：" + USEBIZ_ID + "】，红包使用");
			redAccountLog.setOptuser(redAccount.getUserId());
			redAccountLogMapper.add(redAccountLog);
		}
		return redAccountLog;
	}

	public List<RedAccount> getMyRuleReds(Map map) {
		return redMapper.getMyRuleReds(map);
	}

	@Override
	public void insertRedByRegister(Member member, String ip, String mobileNum) throws Exception {
		LotteryChanceRuleInfoVo lc = lotteryChanceRuleInfoMapper.selectByCode("11");
		if (lc != null) {

			// 发放20元新手红包
			Date addtime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 30);
			Date endTime = cal.getTime();

			RedAccount redAccount = new RedAccount();
			redAccount.setUserId(member.getId());
			redAccount.setMoney(new BigDecimal(20));
			redAccount.setRedType(1970);
			redAccount.setAddTime(addtime);
			redAccount.setEndTime(endTime);
			redAccount.setStatus(2);
			redAccount.setFlag(0);
			redAccount.setRemark("自动创建活动新手红包：[ " + member.getPlatform() + "]" + member.getUsername());
			redAccount.setLastUpdateTime(addtime);
			redMapper.add(redAccount);

			// 更新红包日志
			RedAccountLog redAccountLog = new RedAccountLog();
			redAccountLog.setUserId(member.getId());
			redAccountLog.setRedId(redAccount.getId());
			redAccountLog.setBizId(0);
			redAccountLog.setMoney(new BigDecimal(20));
			redAccountLog.setOpttime(new Date());
			redAccountLog.setStatus(2);
			redAccountLog.setRemark("自动创建活动新手红包：[ " + getPlatform(member.getPlatform()) + "]" + member.getUsername());
			redAccountLog.setAddip(ip);
			redAccountLog.setOptuser(-1);
			redAccountLogMapper.add(redAccountLog);

			// 发送短信
			Map<String, Object> smsMap = new HashMap<String, Object>();
			smsMap.put("content", "恭喜您完成注册");
			smsMap.put("money", "20");
			smsSendService.saveSms(ip, 509, smsMap, mobileNum);
		}

	}
	@Override
	public void insertRedByRegisterActivity(Member member, String ip, String mobileNum,BigDecimal redMoney,int redType) throws Exception {
		LotteryChanceRuleInfoVo lc = lotteryChanceRuleInfoMapper.selectByCode("11");
		if (lc != null) {

			Date addtime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 30);
			Date endTime = cal.getTime();

			RedAccount redAccount = new RedAccount();
			redAccount.setUserId(member.getId());
			redAccount.setMoney(redMoney);
			redAccount.setRedType(redType);
			redAccount.setAddTime(addtime);
			redAccount.setEndTime(endTime);
			redAccount.setStatus(2);
			redAccount.setFlag(0);
			redAccount.setRemark("自动创建奥运活动新人注册红包：[ " + member.getPlatform() + "]" + member.getUsername());
			redAccount.setLastUpdateTime(addtime);
			redMapper.add(redAccount);

			// 更新红包日志
			RedAccountLog redAccountLog = new RedAccountLog();
			redAccountLog.setUserId(member.getId());
			redAccountLog.setRedId(redAccount.getId());
			redAccountLog.setBizId(0);
			redAccountLog.setMoney(redMoney);
			redAccountLog.setOpttime(new Date());
			redAccountLog.setStatus(2);
			redAccountLog.setRemark("自动创建奥运活动新人注册红包：[ " + getPlatform(member.getPlatform()) + "]" + member.getUsername());
			redAccountLog.setAddip(ip);
			redAccountLog.setOptuser(-1);
			redAccountLogMapper.add(redAccountLog);

			// 发送短信
//			Map<String, Object> smsMap = new HashMap<String, Object>();
//			smsMap.put("content", "恭喜您完成注册");
//			smsMap.put("money", "20");
//			smsSendService.saveSms(ip, 509, smsMap, mobileNum);
		}

	}
	public String getPlatform(Integer platform) {
		String name = "";
		int flag = 0;
		if (platform != null) {
			flag = platform.intValue();
		}
		switch (flag) {
		case 2:
			name = "微信平台";
			break;
		case 3:
			name = "安卓平台";
			break;
		case 4:
			name = "ios平台";
			break;
		default:
			name = "pc平台";
		}
		return name;
	}
}
