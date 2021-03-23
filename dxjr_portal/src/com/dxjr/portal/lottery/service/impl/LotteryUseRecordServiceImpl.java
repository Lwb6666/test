package com.dxjr.portal.lottery.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Account;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.AccountLogMapper;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.lottery.controller.LotteryUseRecordController;
import com.dxjr.portal.lottery.mapper.LotteryGoodsSendInfoMapper;
import com.dxjr.portal.lottery.mapper.LotteryUseRecordMapper;
import com.dxjr.portal.lottery.service.LotteryUseRecordService;
import com.dxjr.portal.lottery.vo.LotteryGoodsSendInfoVo;
import com.dxjr.portal.lottery.vo.LotteryUseRecordCnd;
import com.dxjr.portal.lottery.vo.LotteryUseRecordVo;
import com.dxjr.portal.lottery.vo.Myreward;
import com.dxjr.utils.DateUtils;

/**
 * 
 * <p>
 * Description:抽奖使用记录业务处理方法<br />
 * </p>
 * 
 * @title LotteryUseRecordServiceImpl.java
 * @package com.dxjr.portal.lottery.service.impl
 * @author YangShiJin
 * @version 0.1 2015年4月11日
 */
@Service
public class LotteryUseRecordServiceImpl implements LotteryUseRecordService {
	
	private final static Logger logger = Logger.getLogger(LotteryUseRecordServiceImpl.class);
	
	

	@Autowired
	private LotteryUseRecordMapper lotteryUseRecordMapper;

	@Autowired
	private AccountLogMapper accountLogMapper;

	@Autowired
	private BaseAccountMapper baseAccountMapper;

	@Autowired
	private LotteryGoodsSendInfoMapper lotteryGoodsSendInfoMapper;

	@Autowired
	private AccountMapper accountMapper;

	/***
	 * 用户，可用余额 xj money , lottCount
	 */
	@Override
	public LotteryUseRecordVo queryMoneyS_xj(LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception {
		LotteryUseRecordVo lottVo = new LotteryUseRecordVo();
		try {

			AccountVo accountVo = accountMapper.queryByUserId(lotteryUseRecordCnd.getUserId());
			if (accountVo != null) {
				System.out.println("--------------- 1 ： " + accountVo.getUseMoney());

				System.out.println("--------------- 2： " + accountVo.getUseMoneyStr());

				lottVo.setUseMoney(accountVo.getUseMoney());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lottVo;
	}

	/***
	 * 现金， 实物 lottCont 几次抽奖机会
	 */
	@Override
	public LotteryUseRecordVo lottCount_sw(LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception {
		LotteryUseRecordVo retLott = new LotteryUseRecordVo();
		try {
			List<LotteryUseRecordVo> retList = lotteryUseRecordMapper.lottCount_sw(lotteryUseRecordCnd);
			if ((retList != null) && (retList.size() > 0)) {
				for (LotteryUseRecordVo lottV : retList) {
					if (lottV != null) {
						retLott.setUseNum(lottV.getUseNum());
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retLott;
	}

	/**
	 * 现金
	 */
	@Override
	public Page queryPageByLotteryUseRecordCnd_xj(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception {

		int totalCount = 0;
		try {
			totalCount = lotteryUseRecordMapper.queryLottcount_xj(lotteryUseRecordCnd);
			page.setTotalCount(totalCount);
			List<LotteryUseRecordVo> list = lotteryUseRecordMapper.queryLottList_xj(lotteryUseRecordCnd, page);
			page.setResult(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	/***
	 * 现金：使用
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public boolean up_xj_money(LotteryUseRecordCnd lotteryUseRecordCnd, String ipStr) throws Exception {

		boolean isF = false; // 判断金额 是否已经使用

		try {

			// -----------------------------------------------
			// 1.先根据id 查询该 使用记录 , 包括金额 ,userid
			LotteryUseRecordVo retLottVo = lotteryUseRecordMapper.queryXJById(lotteryUseRecordCnd.getId() + "");
			if (retLottVo.getStatus() == 0) {
				isF = true;
			}

			// status = 0 未使用
			if (isF) {
				BigDecimal bd_awardM = retLottVo.getAwardMoney(); // 10元
				// -----------------------------------------------

				// 2. 根据userid 查询 rocky_account 的记录
				Account account = baseAccountMapper.queryByUserIdForUpdate(lotteryUseRecordCnd.getUserId());

				account.setTotal(account.getTotal().add(bd_awardM));
				account.setUseMoney(account.getUseMoney().add(bd_awardM));
				account.setNoDrawMoney(account.getNoDrawMoney().add(bd_awardM));
				int acc_up = baseAccountMapper.updateEntity(account);

				// -----------------------------------------------

				// 3.账户 记录日志 , 使用：资金添加记录
				String tt = ""; // null

				AccountLogCnd accountLogCnd = new AccountLogCnd();
				accountLogCnd.setUserId(lotteryUseRecordCnd.getUserId());

				accountLogCnd.setType("web_award");

				accountLogCnd.setTotal(account.getTotal()); // 日志记录加1+10 = 11元

				accountLogCnd.setMoney(bd_awardM); // jia 操作金额 10元

				accountLogCnd.setUseMoney(account.getUseMoney()); // 日志记录加1+10 =
																	// 11元
				accountLogCnd.setNoUseMoney(account.getNoUseMoney());

				accountLogCnd.setCollection(account.getCollection());
				accountLogCnd.setToUser(lotteryUseRecordCnd.getUserId());

				accountLogCnd.setBorrowId(null);

				accountLogCnd.setBorrowName(tt);

				accountLogCnd.setIdType(null);

				accountLogCnd.setRemark("现金奖励使用");

				accountLogCnd.setAddtime(DateUtils.getDayTime(new Date(), 0));

				accountLogCnd.setAddip(ipStr);

				accountLogCnd.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());

				accountLogCnd.setDrawMoney(account.getDrawMoney());

				accountLogCnd.setNoDrawMoney(account.getNoDrawMoney()); // 日志记录加1+10
																		// = 11元

				int isT1 = accountLogMapper.lott_insert(accountLogCnd);
				// -----------------------------------------------
				// 4 .更新该记录
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sdate = sdf.format(new Date());
				lotteryUseRecordCnd.setDrawTime(sdate);
				int isT = lotteryUseRecordMapper.up_xj_money(lotteryUseRecordCnd);
			} else {
				// status != 0 已使用
				isF = false;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return isF;
	}

	/**
	 * 实物
	 */
	@Override
	public Page queryPageByLotteryUseRecordCnd_sw(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception {

		int totalCount = 0;
		try {

			// count
			totalCount = lotteryUseRecordMapper.queryLottcount_sw(lotteryUseRecordCnd);
			page.setTotalCount(totalCount);

			// list
			List<LotteryUseRecordVo> list = lotteryUseRecordMapper.queryLottList_sw(lotteryUseRecordCnd, page);
			page.setResult(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	// 实物，领取，提交
	// 领取后状态更新 status0-2
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public boolean lingqu_inst(LotteryGoodsSendInfoVo lotteryGoodsSendInfoVo, LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception {
		boolean isF = false; // 锁住 - 验证是否已经 领取
		try {

			// 实物领取前，验证是否可以领取-先锁住
			// 1.先根据id 查询该 领取 记录 , 包括status=0
			LotteryUseRecordVo retLottVo = lotteryUseRecordMapper.queryXJById(lotteryUseRecordCnd.getId() + "");
			if (retLottVo.getStatus() == 0) {
				// 执行领取操作
				isF = true;

				// 实物，领取，提交
				int isT = lotteryGoodsSendInfoMapper.lingqu_inst(lotteryGoodsSendInfoVo);

				// 领取后状态更新 status0-2
				int isT1 = lotteryUseRecordMapper.up_lingqu_status(lotteryUseRecordCnd);

			} else {
				// 已经领取 - 执行返回操作
				isF = false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return isF;
	}
	
	
	/**
	 * 确认收货， 更新status,remark
	 *  (non-Javadoc)
	 * @see com.dxjr.portal.lottery.service.LotteryUseRecordService#upStatusRemarkByQrsh(com.dxjr.portal.lottery.vo.LotteryUseRecordCnd)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public int upStatusRemarkByQrsh(LotteryUseRecordCnd  lotteryUseRecordCnd) throws Exception{
		int isSuc = 0 ;
		try {
			 isSuc = lotteryUseRecordMapper.upStatusRemarkByQrsh(lotteryUseRecordCnd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return isSuc;
	}

	// 实物 领取查看详情
	public List<LotteryUseRecordVo> sw_lingqu_detail(LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception {

		List<LotteryUseRecordVo> retLott = new ArrayList<LotteryUseRecordVo>();
		try {

			retLott = lotteryUseRecordMapper.sw_lingqu_detail(lotteryUseRecordCnd);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return retLott;

	}

	/**
	 * 奖励记录
	 */
	@Override
	public Page queryPageByLotteryUseRecordCnd_jljl(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception {

		int totalCount = 0;
		try {

			totalCount = lotteryUseRecordMapper.queryLottcount_jljl(lotteryUseRecordCnd);
			page.setTotalCount(totalCount);
			List<LotteryUseRecordVo> list = lotteryUseRecordMapper.queryLottList_jljl(lotteryUseRecordCnd, page);
			page.setResult(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	@Override
	public List<LotteryUseRecordVo> selectNewUseRecordByDate(Date date) throws Exception {
		return lotteryUseRecordMapper.selectNewUseRecordByDate(date);
	}

	@Override
	public List<LotteryUseRecordVo> selectNewUseRecordForMaxGoods() throws Exception {
		return lotteryUseRecordMapper.selectNewUseRecordForMaxGoods();
	}
	
	/**
	 * 奖励记录
	 */
	@Override
	public Page queryPageByLotteryUseRecordCnd_reward(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception {

		int totalCount = 0;
		try {
			totalCount = lotteryUseRecordMapper.queryLottCount_Reward(lotteryUseRecordCnd);
			page.setTotalCount(totalCount);
			List<Myreward> list = lotteryUseRecordMapper.queryLottList_Reward(lotteryUseRecordCnd, page);
			page.setResult(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}
}
