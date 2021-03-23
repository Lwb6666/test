package com.dxjr.portal.sycee.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.LotteryChanceInfo;
import com.dxjr.base.entity.MemberAccumulatePoints;
import com.dxjr.base.mapper.BaseLotteryChanceInfoMapper;
import com.dxjr.base.mapper.BaseMemberAccumulatePointsMapper;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.fix.mapper.FixTenderRealMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.sms.service.SmsTemplateService;
import com.dxjr.portal.sycee.entity.SyceeAddress;
import com.dxjr.portal.sycee.entity.SyceeExchange;
import com.dxjr.portal.sycee.entity.SyceeGoods;
import com.dxjr.portal.sycee.mapper.SyceeAddressMapper;
import com.dxjr.portal.sycee.mapper.SyceeExchangeMapper;
import com.dxjr.portal.sycee.mapper.SyceeGoodsMapper;
import com.dxjr.portal.sycee.service.SyceeService;

@Service
public class SyceeServiceImpl implements SyceeService {
	@Autowired
	SyceeAddressMapper addressMapper;

	@Autowired
	SyceeGoodsMapper goodsMapper;

	@Autowired
	SyceeExchangeMapper exchangeMapper;

	@Autowired
	BaseMemberAccumulatePointsMapper userSyceeMapper;

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	BaseLotteryChanceInfoMapper baseLotteryChanceInfoMapper;

	@Autowired
	RedAccountMapper redMapper;

	@Autowired
	RedAccountLogMapper redLogMapper;

	@Autowired
	FixTenderRealMapper fixTenderRealMapper;

	@Autowired
	MobileApproMapper mobileApproMapper;

	@Autowired
	SmsTemplateService smsTemplateService;

	@Autowired
	SmsSendService smsSendService;

	@Override
	public List<SyceeGoods> syceeClassList(int firstClass) {
		return goodsMapper.getByProperty("first_class", firstClass);
	}

	@Override
	public int getSignItem() {
		Integer id = goodsMapper.getSignItem();
		return id == null ? 0 : id;
	}

	@Override
	public SyceeGoods getGoodsInfo(int goodsId) {
		return goodsMapper.getById(goodsId);
	}

	@Override
	public int getUserSycee(int userId) {
		return userSyceeMapper.getUserSycee(userId);
	}

	@Override
	public int getSyceeDiscountFlag() {
		return goodsMapper.getDiscountFlag();
	}

	@Override
	public int getExchangeTimes(int userId) {
		return goodsMapper.getMyExchangeNum(2, 3, userId);
	}

	@Override
	public MessageBox addExchange(int userId, int goodsId, String ip, int num) throws Exception {
		if (num <= 0 || num > 1000) {
			return new MessageBox("0", "兑换数量错误");
		}
		int discountFlag = goodsMapper.getDiscountFlag();
		SyceeGoods g = goodsMapper.getById(goodsId);
		int userSycee = userSyceeMapper.getUserSycee(userId);// 用户当前元宝
		int price = g.getSycee();// 兑换所需元宝(折扣价)
		if (discountFlag != 1) {
			price = g.getOldSycee();// 兑换所需元宝(原价)
		}
		int needSycee = price * num;// 兑换所需元宝=单价*数量

		if (userSycee < needSycee) {
			return new MessageBox("0", "您的元宝不足");
		}
		if (g.getShowFlag().intValue() != 1 || g.getApproveStatus().intValue() != 1) {
			return new MessageBox("0", "该商品不能兑换");// 商品处于上架&审核通过状态才可兑换
		}

		// 浦菲优澜健身券兑换条件验证
		if (g.getFirstClass().intValue() == 2 && g.getSecondClass().intValue() == 3) {
			if (num > 1) {
				return new MessageBox("0", "浦菲优澜健身券每人限兑一件。");
			}

			// 1.当月投宝且满宝达到1000元(含)
			int fixMoney = fixTenderRealMapper.getMonthTender(userId);
			if (fixMoney < 1000) {
				return new MessageBox("0", "该商品需本月投宝且满宝金额达1000元才可兑换，您本月当前投宝且满宝金额为" + fixMoney + "元,请再接再励。");
			}

			// 2.只能兑换1次
			int myExchangeNum = goodsMapper.getMyExchangeNum(2, 3, userId);
			if (myExchangeNum >= 1) {
				return new MessageBox("0", "您已经兑换过浦菲优澜健身券，每人限兑一件。");
			}

			// 3.总兑换量为100
			int exchangeNum = goodsMapper.getExchangeNum(goodsId);
			if (exchangeNum >= 100) {
				return new MessageBox("0", "您来晚了，该商品限量兑换100件已全部兑换完毕。");
			}
		}

		// 添加兑换记录
		SyceeExchange e = new SyceeExchange();
		e.setSyceeGoodsId(goodsId);
		e.setNum(num);
		e.setPrice(price);
		e.setSycee(needSycee);
		e.setUserId(userId);
		if (g.getFirstClass().intValue() == 1) {// 线上商品
			e.setDealStatus(1);// 已处理
			e.setDealUsername("系统");
			e.setDealRemark("线上商品系统自动兑换");
		}
		if (g.getFirstClass().intValue() == 2) {// 线下商品
			e.setDealStatus(0);// 未处理
		}
		int n = exchangeMapper.add(e);
		if (n == 1) {
			// 扣除会员元宝
			memberMapper.queryMemberByIdForUpdate(userId);
			memberMapper.updateAccumulatePoints(userId, -needSycee);
			MemberVo m = memberMapper.queryMemberById(userId);
			if (m.getAccumulatepoints() < 0) {
				throw new Exception();
			}
			if (g.getFirstClass().intValue() == 2 && g.getSecondClass().intValue() == 3) {
				if (goodsMapper.getMyExchangeNum(2, 3, userId) > 1 || goodsMapper.getExchangeNum(goodsId) > 100) {
					throw new Exception();
				}
			}

			// 记录元宝明细
			MemberAccumulatePoints memberPoint = new MemberAccumulatePoints();
			memberPoint.setAccumulatePoints(-needSycee);// 交易元宝
			memberPoint.setDetail("兑换" + g.getName() + "，数量：" + num);// 交易明细
			memberPoint.setType(102);// 元宝兑换商品
			memberPoint.setTargetId(g.getId());
			memberPoint.setPointSmagnification(1);
			memberPoint.setMemberId(userId);
			memberPoint.setHonor(m.getHonor());
			memberPoint.setSycee(m.getAccumulatepoints());
			userSyceeMapper.insertEntity(memberPoint);

			// 发放-抽奖机会
			if (g.getFirstClass() == 1 && g.getSecondClass() == 2) {
				LotteryChanceInfo lc = new LotteryChanceInfo();
				lc.setAddIp(ip);
				lc.setLotteryChanceRuleInfoId(10);// 元宝商城兑换
				lc.setLotteryNum(num);
				lc.setRemark("元宝商城兑换商品【" + g.getName() + "】，数量【" + num + "】");
				lc.setUseNum(num);
				lc.setUserId(userId);
				lc.setUsername(m.getUsername());
				baseLotteryChanceInfoMapper.insert(lc);
				return new MessageBox("1", "choujiang");// 抽奖提示框特殊写在页面
			}

			// 发放-红包
			if (g.getFirstClass() == 1 && g.getSecondClass() == 1) {
				for (int i = 0; i < num; i++) {
					RedAccount red = new RedAccount();
					red.setMoney(new BigDecimal(g.getMoney()));
					red.setStatus(2);// 未使用
					red.setUserId(userId);
					red.setRedType(1950);// 1950元宝兑换红包
					red.setRemark("用户使用" + needSycee + "元宝兑换红包");
					redMapper.add(red);
					int redId = red.getId();

					RedAccountLog redLog = new RedAccountLog();
					redLog.setMoney(red.getMoney());
					redLog.setRedId(redId);
					redLog.setAddip(ip);
					redLog.setStatus(2);// 未使用
					redLog.setBizType(0);// 0创建
					redLog.setRemark(red.getRemark());
					redLog.setUserId(userId);
					redLogMapper.add(redLog);
				}

			}

			try {
				// 发送短信,并标记为已处理
				if (g.getFirstClass().intValue() == 2 && g.getSecondClass().intValue() == 3) {
					Map<String, Object> smsMap = new HashMap<String, Object>();
					smsMap.put("goodsName", g.getName());
					smsMap.put("goodsRemark", g.getRemark());
					String mobile = mobileApproMapper.getMobile(userId);
					String s = smsSendService.saveSms(ip, 602, smsMap, mobile);
					if (s != null && s.indexOf("success") != -1) {
						exchangeMapper.updateDeal("系统已发送短信至用户手机" + mobile, goodsId, userId);
					}
				}
			} catch (Exception e2) {
			}
			if (g.getFirstClass().intValue() == 2 && null == addressMapper.getById(userId)) {
				return new MessageBox("1", "恭喜兑换" + g.getName() + "成功", g.getFirstClass().toString());
			} else {
				return new MessageBox("1", "恭喜兑换" + g.getName() + "成功");
			}

		} else {
			return new MessageBox("0", "兑换失败");
		}
	}

	@Override
	public SyceeAddress getById(int userId) {
		// TODO Auto-generated method stub
		return addressMapper.getById(userId);
	}

	@Override
	public MessageBox addAddress(SyceeAddress address) throws Exception {

		int n = addressMapper.add(address);
		if (n == 1) {
			return new MessageBox("1", "操作成功！");
		} else {
			return new MessageBox("0", "操作失败！");
		}
	}

	@Override
	public MessageBox updateAddr(SyceeAddress address, int userId) {
		String addressAddr;
		String zipCode;
		if (address.getAddress() == null) {
			addressAddr = "";
		} else {
			addressAddr = address.getAddress();
		}
		if (address.getZipCode() == null) {
			zipCode = "";
		} else {
			zipCode = address.getZipCode();
		}
		int n = addressMapper.updateAddr(address.getName(), addressAddr, address.getPhone(), zipCode, userId);
		if (n == 1) {
			return new MessageBox("1", "操作成功！");
		} else {
			return new MessageBox("0", "操作失败！");
		}

	}

}
