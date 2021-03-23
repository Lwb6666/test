package com.dxjr.portal.lottery.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.LotteryChanceInfo;
import com.dxjr.base.entity.LotteryGoodsLimit;
import com.dxjr.base.entity.LotteryUseRecord;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.MemberAccumulatePoints;
import com.dxjr.base.mapper.BaseLotteryChanceInfoMapper;
import com.dxjr.base.mapper.BaseLotteryGoodsLimitMapper;
import com.dxjr.base.mapper.BaseLotteryUseRecordMapper;
import com.dxjr.base.mapper.BaseMemberAccumulatePointsMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.portal.lottery.mapper.LotteryChanceInfoMapper;
import com.dxjr.portal.lottery.mapper.LotteryChanceRuleInfoMapper;
import com.dxjr.portal.lottery.mapper.LotteryGoodsLimitMapper;
import com.dxjr.portal.lottery.mapper.LotteryGoodsMapper;
import com.dxjr.portal.lottery.mapper.LotterySourceTypeChanceMapper;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.lottery.vo.LotteryChanceInfoVo;
import com.dxjr.portal.lottery.vo.LotteryChanceRuleInfoVo;
import com.dxjr.portal.lottery.vo.LotteryDraw;
import com.dxjr.portal.lottery.vo.LotteryGoodsLimitVo;
import com.dxjr.portal.lottery.vo.LotteryGoodsVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.RedistributeUtils;

/**
 * 
 * <p>
 * Description:抽奖机会信息业务逻辑处理类<br />
 * </p>
 * 
 * @title LotteryChanceInfoServiceImpl.java
 * @package com.dxjr.portal.lottery.service.impl
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
@Service
public class LotteryChanceInfoServiceImpl implements LotteryChanceInfoService {

	@Autowired
	private BaseLotteryChanceInfoMapper baseLotteryChanceInfoMapper;
	@Autowired
	private LotteryChanceInfoMapper lotteryChanceInfoMapper;
	@Autowired
	private LotteryChanceRuleInfoMapper lotteryChanceRuleInfoMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private LotteryGoodsMapper lotteryGoodsMapper;
	@Autowired
	private LotterySourceTypeChanceMapper lotterySourceTypeChanceMapper;
	@Autowired
	private LotteryGoodsLimitMapper lotteryGoodsLimitMapper;
	@Autowired
	private BaseLotteryUseRecordMapper baseLotteryUseRecordMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BaseLotteryGoodsLimitMapper baseLotteryGoodsLimitMapper;
	@Autowired
	private RedAccountMapper redAccountMapper;
	@Autowired
	private RedAccountLogMapper redAccountLogMapper;
	@Autowired
	private BaseMemberAccumulatePointsMapper baseMemberAccumulatePointsMapper;

	@Override
	public Integer selectCountByCodeAndUserId(String code, Integer userId) {
		return lotteryChanceInfoMapper.selectCountByCodeAndUserId(code, userId);
	}
	
	@Override
	public String insertLotteryChanceInfoByCode(Integer userId, String code) throws Exception {
		LotteryChanceRuleInfoVo lotteryChanceRuleInfoVo = lotteryChanceRuleInfoMapper.selectByCode(code);
		if (lotteryChanceRuleInfoVo != null && lotteryChanceRuleInfoVo.getLotteryNum() != null) {
			Member member = baseMemberMapper.queryById(userId);
			if (member != null && member.getIsFinancialUser() != null && member.getIsFinancialUser().intValue() == 1) {
				LotteryChanceInfo lotteryChanceInfo = new LotteryChanceInfo();
				lotteryChanceInfo.setLotteryChanceRuleInfoId(lotteryChanceRuleInfoVo.getId());
				lotteryChanceInfo.setLotteryNum(lotteryChanceRuleInfoVo.getLotteryNum());
				lotteryChanceInfo.setUseNum(lotteryChanceRuleInfoVo.getLotteryNum());
				lotteryChanceInfo.setUserId(userId);
				lotteryChanceInfo.setAddTime(new Date());
				lotteryChanceInfo.setAddIp("0.0.0.1");
				lotteryChanceInfo.setUsername(member.getUsername());
				if (baseLotteryChanceInfoMapper.insert(lotteryChanceInfo) > 0) {
					return "success";
				}
			}
		}
		return "fail";
	}

	@Override
	public String insertLotteryChanceInfoByCode(Integer userId, String code, Integer lotteryNum) throws Exception {
		LotteryChanceRuleInfoVo lotteryChanceRuleInfoVo = lotteryChanceRuleInfoMapper.selectByCode(code);
		if (lotteryChanceRuleInfoVo != null && lotteryChanceRuleInfoVo.getLotteryNum() != null) {
			Member member = baseMemberMapper.queryById(userId);
			if (member != null && member.getIsFinancialUser() != null && member.getIsFinancialUser().intValue() == 1) {
				LotteryChanceInfo lotteryChanceInfo = new LotteryChanceInfo();
				lotteryChanceInfo.setLotteryChanceRuleInfoId(lotteryChanceRuleInfoVo.getId());
				lotteryChanceInfo.setLotteryNum(lotteryNum);
				lotteryChanceInfo.setUseNum(lotteryNum);
				lotteryChanceInfo.setUserId(userId);
				lotteryChanceInfo.setAddTime(new Date());
				lotteryChanceInfo.setAddIp("0.0.0.1");
				lotteryChanceInfo.setUsername(member.getUsername());
				if (baseLotteryChanceInfoMapper.insert(lotteryChanceInfo) > 0) {
					return "success";
				}
			}
		}
		return "fail";
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String investLevelAwardLotteryChance(Integer userId, BigDecimal account) throws Exception {
		MemberVo memberVo = memberMapper.querySimpleInfoById(userId);
		if (memberVo != null && memberVo.getIsFinancialUser() != null && memberVo.getIsFinancialUser().intValue() == 1) {
			// 发放“首次投资奖或理财里程碑奖”抽奖机会
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("account", account);
			lotteryChanceInfoMapper.investLevelAwardLotteryChance(map);
			if (map.get("msg") != null && map.get("msg").toString().equals("0001")) {
				return "success";
			}
		}
		return "failure";
	}

	@Override
	public String firstBorrowAwardLotteryChance(Integer userId, Integer account, Integer firstTenderRealId) throws Exception {
		MemberVo memberVo = memberMapper.querySimpleInfoById(userId);
		if (memberVo != null && memberVo.getIsFinancialUser() != null && memberVo.getIsFinancialUser().intValue() == 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("account", account);
			map.put("firstTenderRealId", firstTenderRealId);
			lotteryChanceInfoMapper.firstBorrowAwardLotteryChance(map);
			System.err.println("userId:" + map.get("userId") + ",account:" + map.get("account") + ",firstTenderRealId:"
					+ map.get("firstTenderRealId") + ",msg:" + map.get("msg"));
			if (map.get("msg") != null && map.get("msg").toString().equals("0001")) {
				return "success";
			}
		}
		return "failure";
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public LotteryDraw lotteryDraw(Integer userId) throws Exception {
		MemberVo memberVo = memberMapper.querySimpleInfoById(userId);
		LotteryDraw lotteryDraw = new LotteryDraw();
		// 理财用户才能抽奖
		if (memberVo != null && memberVo.getIsFinancialUser() != null && memberVo.getIsFinancialUser().intValue() == 1) {
			// 抽奖
			Map<String, Object> resultMap = handleParentLotteryDraw(userId);
			if (resultMap.get("parentLotteryGoodsVo") != null && resultMap.get("finalLotteryGoodsVo") != null) {
				// 抽中的一级奖品
				LotteryGoodsVo parentLotteryGoodsVo = (LotteryGoodsVo) resultMap.get("parentLotteryGoodsVo");
				// 抽中的最终奖品
				LotteryGoodsVo finalLotteryGoodsVo = (LotteryGoodsVo) resultMap.get("finalLotteryGoodsVo");
				if (finalLotteryGoodsVo != null) {
					lotteryDraw.setAwardType(finalLotteryGoodsVo.getAwardType());
					if (finalLotteryGoodsVo.getAwardType() == 4 || finalLotteryGoodsVo.getAwardNum() != null) {
						lotteryDraw.setAwardNum(finalLotteryGoodsVo.getAwardNum());
					} else {
						lotteryDraw.setAwardNum(0);
					}
				} else {
					lotteryDraw.setAwardNum(0);
					lotteryDraw.setAwardType(0);
				}
				if (resultMap.get("chanceRuleInfoName") != null) {
					lotteryDraw.setSourceTypeName(resultMap.get("chanceRuleInfoName").toString());
				}
				lotteryDraw = handleLotteryDraw(lotteryDraw, parentLotteryGoodsVo.getTurntablePosition(), finalLotteryGoodsVo.getName());
			} else {
				lotteryDraw.setsFlag("fail");
				lotteryDraw.setsAngel(0);
				lotteryDraw.seteAngel(0);
				lotteryDraw.setMessage("您没有抽奖机会，无法抽奖！");
				lotteryDraw.setDtnTime(0);
				lotteryDraw.setAwardNum(0);
				lotteryDraw.setAwardType(0);
			}
		} else {
			lotteryDraw.setsFlag("fail");
			lotteryDraw.setsAngel(0);
			lotteryDraw.seteAngel(0);
			lotteryDraw.setMessage("您不是理财用户，无法抽奖！");
			lotteryDraw.setDtnTime(0);
			lotteryDraw.setAwardNum(0);
			lotteryDraw.setAwardType(0);
		}
		return lotteryDraw;
	}

	/**
	 * 
	 * <p>
	 * Description:随机抽取一级奖品<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param userId
	 * @throws Exception
	 *             void
	 */
	public Map<String, Object> handleParentLotteryDraw(Integer userId) throws Exception {
		// 获取该用户一条有效抽奖机会，并锁住
		LotteryChanceInfoVo lotteryChanceInfoVo = lotteryChanceInfoMapper.selectFirstChanceInfoByUserId(userId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (lotteryChanceInfoVo != null) {
			// 获取奖品ID及中奖概率
			Map<Integer, BigDecimal> goodsMap = new HashMap<Integer, BigDecimal>();
			// 获取8种奖品及与该抽奖机会类型匹配的中奖概率
			List<LotteryGoodsVo> lotteryGoodsVoList = lotteryGoodsMapper.selectLotteryGoodsByLotteryChanceRuleInfoId(lotteryChanceInfoVo
					.getLotteryChanceRuleInfoId());
			BigDecimal totalChance = lotteryGoodsMapper.queryTotalChanceByLotteryChanceRuleInfoId(lotteryChanceInfoVo.getLotteryChanceRuleInfoId());
			int thankYouKey = 0;
			int thankYouPostion = 0;
			for (LotteryGoodsVo lotteryGoodsVo : lotteryGoodsVoList) {
				if (lotteryGoodsVo.getAwardType() == 3) {
					thankYouKey = lotteryGoodsVo.getId();
					thankYouPostion = lotteryGoodsVo.getTurntablePosition();
				}
				goodsMap.put(lotteryGoodsVo.getId(), lotteryGoodsVo.getChance());
			}
			// 设置奖品数组池
			int[] goodsArray = new int[100 * BusinessConstants.LOTTERY_int_PROGRESSIVE_RATIO];

			if (totalChance.compareTo(new BigDecimal(100)) == 1 || thankYouKey == 0) {
				// 比例根据倍数放大
				BigDecimal value = totalChance.multiply(new BigDecimal(BusinessConstants.LOTTERY_int_PROGRESSIVE_RATIO)).setScale(0,
						BigDecimal.ROUND_DOWN);
				int maxLength = Integer.parseInt(String.valueOf(value));
				goodsArray = new int[maxLength];
			}

			// 填充中奖池
			Iterator<Integer> iter = goodsMap.keySet().iterator();
			int index = 0;
			while (iter.hasNext()) {
				int key = iter.next();
				// 比例根据倍数放大
				BigDecimal value = goodsMap.get(key).multiply(new BigDecimal(BusinessConstants.LOTTERY_int_PROGRESSIVE_RATIO))
						.setScale(0, BigDecimal.ROUND_DOWN);
				int length = Integer.parseInt(String.valueOf(value));
				for (int i = 0; i < length; i++) {
					goodsArray[index] = key;
					index++;
				}
			}
			// 如果不满100%，则剩余部分填充谢谢参与的中奖ID。
			for (; index < goodsArray.length; index++) {
				if (thankYouKey > 0) {
					goodsArray[index] = thankYouKey;
				}
			}
			// 打乱后的最终中奖池
			int[] resultArray = randomUpsetArray(goodsArray);
			// 随机获取中奖下标
			int xb_idx = this.getWiningIdx(resultArray.length);
			// 读取中奖ID
			int goods_id = resultArray[xb_idx];
			// 抽取二级奖品中奖ID
			Integer subsetGoodsId = handleSubsetLotteryDraw(goods_id);

			// 如果二级奖品中奖ID不为空，判断该二级奖品
			if (subsetGoodsId != null) {
				LotteryGoodsLimitVo lotteryGoodsLimitVo = lotteryGoodsLimitMapper.selectByGoodsIdAndNow(subsetGoodsId, new Date());
				// 如果限制记录存在，且剩余数量为0时，则抽中的该奖品作废。
				if (lotteryGoodsLimitVo != null && lotteryGoodsLimitVo.getSumNum().intValue() - lotteryGoodsLimitVo.getUsedNum() <= 0) {
					if (thankYouKey > 0) { // 如果谢谢参与存在，则中奖ID为谢谢参与ID
						subsetGoodsId = null;
						goods_id = thankYouKey;
					} else {
						// 否则，从新抽奖
						handleParentLotteryDraw(userId);
					}
				}
			} else {
				LotteryGoodsLimitVo lotteryGoodsLimitVo = lotteryGoodsLimitMapper.selectByGoodsIdAndNow(goods_id, new Date());
				// 如果限制记录存在，且剩余数量为0时，则抽中的该奖品作废。
				if (lotteryGoodsLimitVo != null && lotteryGoodsLimitVo.getSumNum().intValue() - lotteryGoodsLimitVo.getUsedNum() <= 0) {
					if (thankYouKey > 0) { // 如果谢谢参与存在，则中奖ID为谢谢参与ID
						goods_id = thankYouKey;
					} else {
						// 否则，从新抽奖
						handleParentLotteryDraw(userId);
					}
				}
			}

			// 查询一级中奖奖品信息
			LotteryGoodsVo parentLotteryGoodsVo = lotteryGoodsMapper.selectByPrimaryKey(goods_id);
			// 最终奖品
			LotteryGoodsVo finalLotteryGoodsVo = null;
			if (subsetGoodsId != null) {
				// 查询二级中奖奖品信息
		 		finalLotteryGoodsVo = lotteryGoodsMapper.selectByPrimaryKey(subsetGoodsId);
			} else {
				// 查询一级中奖奖品信息
				finalLotteryGoodsVo = parentLotteryGoodsVo;
			}

			// 扣除抽奖机会，并更新记录
			lotteryChanceInfoVo.setUseNum(lotteryChanceInfoVo.getUseNum() - 1);
			LotteryChanceInfo lotteryChanceInfo = new LotteryChanceInfo();
			BeanUtils.copyProperties(lotteryChanceInfoVo, lotteryChanceInfo);
			baseLotteryChanceInfoMapper.updateByPrimaryKeySelective(lotteryChanceInfo);

			// 生成抽奖机会使用记录
			hanldeInsertLotteryUseRecord(userId, lotteryChanceInfoVo.getId(), finalLotteryGoodsVo);

			resultMap.put("parentLotteryGoodsVo", parentLotteryGoodsVo); // 一级奖品
			resultMap.put("finalLotteryGoodsVo", finalLotteryGoodsVo); // 最终奖品
			resultMap.put("thankYouPostion", thankYouPostion);
			resultMap.put("chanceRuleInfoName", lotteryChanceInfoVo.getChanceRuleInfoName());
		}
		return resultMap;
	}

	/**
	 * 
	 * <p>
	 * Description:随机抽取二级奖品<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param userId
	 * @throws Exception
	 *             Integer
	 */
	public Integer handleSubsetLotteryDraw(int goodsId) throws Exception {
		// 查询一级中奖奖品的二级奖品信息
		List<LotteryGoodsVo> lotteryGoodsVoList = lotteryGoodsMapper.selectLotteryGoodsByParentId(goodsId);
		BigDecimal totalChance = lotteryGoodsMapper.queryTotalChanceByParentId(goodsId);
		// 当二级奖品存在时
		if (lotteryGoodsVoList.size() > 0) {
			// 设置二级奖品ID及中奖概率
			Map<Integer, BigDecimal> goodsMap = new HashMap<Integer, BigDecimal>();
			for (LotteryGoodsVo lotteryGoodsVo : lotteryGoodsVoList) {
				goodsMap.put(lotteryGoodsVo.getId(), lotteryGoodsVo.getChance());
			}
			// 比例根据倍数放大
			BigDecimal totalChanceValue = totalChance.multiply(new BigDecimal(BusinessConstants.LOTTERY_int_PROGRESSIVE_RATIO)).setScale(0,
					BigDecimal.ROUND_DOWN);
			int maxLength = Integer.parseInt(String.valueOf(totalChanceValue));
			// 设置奖品数组池
			int[] goodsArray = new int[maxLength];

			// 填充中奖池
			Iterator<Integer> iter = goodsMap.keySet().iterator();
			int index = 0;
			while (iter.hasNext()) {
				int key = iter.next();
				// 比例根据倍数放大
				BigDecimal value = goodsMap.get(key).multiply(new BigDecimal(BusinessConstants.LOTTERY_int_PROGRESSIVE_RATIO))
						.setScale(0, BigDecimal.ROUND_DOWN);
				int length = Integer.parseInt(String.valueOf(value));
				for (int i = 0; i < length; i++) {
					goodsArray[index] = key;
					index++;
				}
			}
			// 打乱后的最终中奖池
			int[] resultArray = randomUpsetArray(goodsArray);
			// 随机获取中奖下标
			int xb_idx = this.getWiningIdx(resultArray.length);
			// 读取中奖ID
			int good_id = resultArray[xb_idx];
			if (good_id > 0) {
				return good_id;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Description:生成抽奖使用记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param userId
	 * @param finalLotteryGoodsVo
	 * @throws Exception
	 *             void
	 */
	public void hanldeInsertLotteryUseRecord(int userId, int lotteryChanceInfoId, LotteryGoodsVo finalLotteryGoodsVo) throws Exception {
		// 新增抽奖机会使用记录
		LotteryUseRecord lotteryUseRecord = new LotteryUseRecord();
		System.err.println("中奖ID：" + finalLotteryGoodsVo.getId());
		if (finalLotteryGoodsVo.getAwardType().intValue() == 3 || finalLotteryGoodsVo.getAwardType().intValue() == 4) { // 　谢谢参与或抽奖机会
			lotteryUseRecord.setStatus(1);
			lotteryUseRecord.setDrawTime(new Date());
		} else {
			lotteryUseRecord.setStatus(0);
		}
		//红包抽奖 更新已使用状态 20151103 liuao
		if (null!=finalLotteryGoodsVo.getAwardType()&&finalLotteryGoodsVo.getAwardType().intValue() == 5) {
			lotteryUseRecord.setStatus(1);
			lotteryUseRecord.setDrawTime(new Date());
		}
		
		
		//新规则奖励50元宝
		if (null!=finalLotteryGoodsVo.getAwardType()&&finalLotteryGoodsVo.getAwardType().intValue() == 6) {
			int award = finalLotteryGoodsVo.getAwardMoney().intValue();
			memberMapper.updateAccumulatePointsById(userId, award);
			
			MemberVo member = memberMapper.queryMemberById(userId);
			
			MemberAccumulatePoints syceeDetail =new MemberAccumulatePoints();
			syceeDetail.setMemberId(userId);
			syceeDetail.setAccumulatePoints(award);
			syceeDetail.setGainAccumulatePoints(0);
			syceeDetail.setGainDate(new Date());
			syceeDetail.setType(103);
			syceeDetail.setPointSmagnification(1);
			syceeDetail.setHonor(member.getHonor());
			syceeDetail.setSycee(member.getAccumulatepoints());
			syceeDetail.setDetail("抽奖获得"+ award +"元宝");
			syceeDetail.setRemark("抽奖获得"+ award +"元宝");
			baseMemberAccumulatePointsMapper.insertEntity(syceeDetail);
			
			//更新获奖记录状态
			lotteryUseRecord.setStatus(1);
			lotteryUseRecord.setDrawTime(new Date());
		}
		
		
		lotteryUseRecord.setUserId(userId);
		lotteryUseRecord.setLotteryId(lotteryChanceInfoId);
		lotteryUseRecord.setLotteryGoodsId(finalLotteryGoodsVo.getId());
		lotteryUseRecord.setAddTime(new Date());
		lotteryUseRecord.setAwardType(finalLotteryGoodsVo.getAwardType());
		lotteryUseRecord.setLotteryGoodsName(finalLotteryGoodsVo.getName());
		lotteryUseRecord.setAwardNum(finalLotteryGoodsVo.getAwardNum());
		lotteryUseRecord.setAwardMoney(finalLotteryGoodsVo.getAwardMoney());
		lotteryUseRecord.setPlatform("1"); // 官网抽奖
		if (finalLotteryGoodsVo.getValidDay() != null && finalLotteryGoodsVo.getValidDay().intValue() != 0) {
			Date curdate = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			lotteryUseRecord.setOverdueTime(DateUtils.dayOffset(curdate, finalLotteryGoodsVo.getValidDay() + 1));
		}
		baseLotteryUseRecordMapper.insert(lotteryUseRecord);
		//红包抽奖 已使用红包添加红包记录表及日志表  20151103 liuao
		if (null!=finalLotteryGoodsVo.getAwardType()&&finalLotteryGoodsVo.getAwardType().intValue() == 5) {
			insertredAccountAndredAccountLog(userId,finalLotteryGoodsVo);
		}
		if (finalLotteryGoodsVo.getAwardType().intValue() == 4) { // 如果是抽奖机会
			LotteryChanceInfo lotteryChanceInfo = new LotteryChanceInfo();
			lotteryChanceInfo.setUserId(userId);
			lotteryChanceInfo.setLotteryNum(finalLotteryGoodsVo.getAwardNum());
			lotteryChanceInfo.setUseNum(finalLotteryGoodsVo.getAwardNum());
			lotteryChanceInfo.setAddTime(new Date());
			lotteryChanceInfo.setAddIp("0.0.0.1");
			LotteryChanceRuleInfoVo lotteryChanceRuleInfoVo = lotteryChanceRuleInfoMapper
					.selectByCode(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_LOTTERY_DRAW);
			if (lotteryChanceRuleInfoVo != null) {
				lotteryChanceInfo.setLotteryChanceRuleInfoId(lotteryChanceRuleInfoVo.getId());
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(userId);
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			if (memberVo != null) {
				lotteryChanceInfo.setUsername(memberVo.getUsername());
			}
			baseLotteryChanceInfoMapper.insert(lotteryChanceInfo);
		}
		
		LotteryGoodsLimitVo lotteryGoodsLimitVo = lotteryGoodsLimitMapper.selectByGoodsIdAndNow(finalLotteryGoodsVo.getId(), new Date());
		if (lotteryGoodsLimitVo != null && lotteryGoodsLimitVo.getUsedNum() != null) {
			lotteryGoodsLimitVo.setUsedNum(lotteryGoodsLimitVo.getUsedNum() + 1);
			LotteryGoodsLimit lotteryGoodsLimit = new LotteryGoodsLimit();
			BeanUtils.copyProperties(lotteryGoodsLimitVo, lotteryGoodsLimit);
			baseLotteryGoodsLimitMapper.updateByPrimaryKey(lotteryGoodsLimit);
		}
	}

	/**
	 * 
	 * <p>
	 * Description:处理返回信息<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param lotteryDraw
	 * @param turntablePosition
	 *            获奖位置
	 * @param name
	 *            获奖名称
	 * @return
	 * @throws Exception
	 *             LotteryDraw
	 */
	public LotteryDraw handleLotteryDraw(LotteryDraw lotteryDraw, int turntablePosition, String name) throws Exception {
		int sAngel = 0; // 起始角度
		int eAngel = 0; // 起始角度 + 默认旋转的圈数
		int dtnTime = 8000; // 转动时间
		String message = name;

		int zhuanQ = 360 * 10; // 默认旋 圈数
		int cellDegrees = 45; // 每格度数
		// 随机生成初始位置
		int initPosition = cellDegrees * (turntablePosition - 1) + 22 + zhuanQ;
		eAngel = initPosition;
		if (turntablePosition > 0 && turntablePosition < 9) {
			lotteryDraw.setsFlag("success");
		} else {
			lotteryDraw.setsFlag("fail");
			message = "没有中奖";
		}
		lotteryDraw.setsAngel(sAngel);
		lotteryDraw.seteAngel(eAngel);
		lotteryDraw.setMessage(message);
		lotteryDraw.setDtnTime(dtnTime);
		lotteryDraw.setPosition(turntablePosition);
		return lotteryDraw;
	}

	@Override
	public Integer selectChanceUseNumTotalByUserId(int userId) throws Exception {
		return lotteryChanceInfoMapper.selectChanceUseNumTotalByUserId(userId);
	}

	@Override
	public List<LotteryChanceInfoVo> selectNewChanceInfoByDate(Date date) throws Exception {
		return lotteryChanceInfoMapper.selectNewChanceInfoByDate(date);
	}

	@Override
	public BigDecimal queryInvestTotalByUserId(int userId) throws Exception {
		return lotteryChanceInfoMapper.queryInvestTotalByUserId(userId);
	}

	/**
	 * 
	 * <p>
	 * Description:随机打乱数组<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月12日
	 * @param goodsArray
	 * @return
	 * @throws Exception
	 *             int[]
	 */
	public int[] randomUpsetArray(int[] goodsArray) throws Exception {
		int[] resultArray = goodsArray;
		// 循环5-10之间的随机次数，打乱数组
		for (int i = 0; i < Math.random() * 5 + 5; i++) {
			// 利用洗牌程序原理随机打乱数组
			RedistributeUtils.shuffle(resultArray, new Random());
			// 利用生成随机索引交换原理打乱数组
			// resultArray = RedistributeUtils.redistribute(resultArray);
		}
		return resultArray;
	}

	/**
	 * 
	 * <p>
	 * Description:随机获取中奖下标<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月10日
	 * @param resultArray
	 * @return int
	 */
	public int getWiningIdx(int resultArray) {
		// 设置中奖下标组
		int[] xb_num = new int[10];
		xb_num[0] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[1] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[2] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[3] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[4] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[5] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[6] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[7] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[8] = (int) Math.floor((double) Math.random() * resultArray);
		xb_num[9] = (int) Math.floor((double) Math.random() * resultArray);
		int xb_idx = (int) Math.floor(xb_num[(int) Math.floor((double) Math.random() * 10)]);
		return xb_idx;
	}
	
	@Override
	public Integer queryLotteryNumTotalByCodeAndUserId(String code, Integer userId) {
		return lotteryChanceInfoMapper.queryLotteryNumTotalByCodeAndUserId(code, userId);
	}
	/**
	 * 
	 * <p>
	 * Description:操作红包表和日志表<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2015年11月03日
	 */
	@Transactional(rollbackFor = Throwable.class)
	private void insertredAccountAndredAccountLog(int userId,LotteryGoodsVo finalLotteryGoodsVo){
		try {
			RedAccount redAccount =new RedAccount();
			redAccount.setUserId(userId);
			redAccount.setRedType(1960);
			if(null!=finalLotteryGoodsVo&&null!=finalLotteryGoodsVo.getAwardMoney()){
			  redAccount.setMoney(finalLotteryGoodsVo.getAwardMoney());
			}
			redAccount.setAddTime(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 30);
			Date endTime = cal.getTime();
			redAccount.setEndTime(endTime);
			redAccount.setStatus(2);
			redAccount.setFlag(0);
			redAccount.setRemark("自动创建抽奖红包");
			redAccount.setLastUpdateTime(new Date());
			redAccountMapper.add(redAccount);
			RedAccountLog redAccountLog=new RedAccountLog();
			if(null!=finalLotteryGoodsVo&&null!=finalLotteryGoodsVo.getAwardMoney()){
				redAccountLog.setMoney(finalLotteryGoodsVo.getAwardMoney());
			}else{
				redAccountLog.setMoney(BigDecimal.ZERO);	
			}
			redAccountLog.setUserId(userId);
			redAccountLog.setRedId(redAccount.getId());
			redAccountLog.setBizId(0);
			redAccountLog.setOpttime(new Date());
			redAccountLog.setStatus(2);
			redAccountLog.setRemark("自动创建抽奖红包");
			redAccountLog.setAddip("127.0.0.1");
			redAccountLog.setOptuser(-1);
			redAccountLogMapper.add(redAccountLog);
			//微信号推送红包发送成功到个人账户
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <p>
	 * Description:查询未提醒抽奖次数<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	@Override
	public Integer queryLotteryNumTotal(String code, Integer userId) {
		return lotteryChanceInfoMapper.queryLotteryNumTotal(code, userId);
	}
	/**
	 * 
	 * <p>
	 * Description:更新提醒状态为未提醒<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	@Override
	public void updatelotteryChanceState(Integer userId) {
		lotteryChanceInfoMapper.updatelotteryChanceState(userId);
	}
}
