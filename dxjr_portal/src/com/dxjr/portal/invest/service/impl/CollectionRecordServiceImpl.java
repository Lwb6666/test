package com.dxjr.portal.invest.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.collection.vo.BCollectionRecordVo;
import com.dxjr.portal.fix.mapper.BCollectionrecordMapper;
import com.dxjr.portal.invest.mapper.AccCommonCollectionMapper;
import com.dxjr.portal.invest.mapper.CollectionStatisticMapper;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.vo.CollectionRepayInfoVo;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CommonCollectionVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

/**
 * @desc 待收记录查询服务
 * @author hujianpan
 * @package 
 *          com.dxjr.portal.invest.service.impl.CollectionRecordServiceImpl.java
 * 
 */
@Service
public class CollectionRecordServiceImpl implements CollectionRecordService {
	public Logger logger = Logger.getLogger(CollectionRecordServiceImpl.class);
	@Autowired
	private AccCommonCollectionMapper accCommonCollectionMapper;
	@Autowired
	private CollectionStatisticMapper collectionStatisticMapper;
	@Autowired
	private BCollectionrecordMapper bcollectionrecordMapper;

	@Override
	public Page queryMyCollections(Map<String, Object> parameter, Page pages) {
		convertParameter(parameter);
		pages.setTotalCount(accCommonCollectionMapper.countAccCommonCollections(parameter));
		List<CommonCollectionVo> list = accCommonCollectionMapper.queryAccCommonCollectionsWithPage(parameter, pages);
		List<CommonCollectionVo> commonCollectionVoList = new ArrayList<CommonCollectionVo>();
		for (CommonCollectionVo commonCollectionVo : list) {
			if (commonCollectionVo.getStatus() != null) {
				if (commonCollectionVo.getFirstBorrowId() != null) {
					if (commonCollectionVo.getStatus() == 2 || commonCollectionVo.getStatus() == 3) {
						if (commonCollectionVo.getAdvancetime() != null) {
							if (Long.parseLong(DateUtils.date2TimeStamp(DateUtils.format(commonCollectionVo.getAdvancetime(), DateUtils.YMD_DASH))) >= Long
									.parseLong(DateUtils.date2TimeStamp("2015-03-10"))) {
								commonCollectionVo.setInverestFee(BigDecimal.ZERO);
								commonCollectionVo.setInverestFeeStr(MoneyUtil.roundMoney(commonCollectionVo.getInverestFee()));
								commonCollectionVo.setGetYesAccount(commonCollectionVo.getRepayYesaccount());
								commonCollectionVo.setGetYesAccountStr(MoneyUtil.roundMoney(commonCollectionVo.getGetYesAccount()));
							}
						}
					}
					if (commonCollectionVo.getStatus() == 1) {
						if (commonCollectionVo.getRepayYestime() != null && !commonCollectionVo.getRepayYestime().equals("")) {
							if (Long.parseLong(DateUtils.date2TimeStamp(DateUtils.TimeStamp2Date(commonCollectionVo.getRepayYestime())).toString()) >= Long
									.parseLong(DateUtils.date2TimeStamp("2015-03-10").toString())) {
								commonCollectionVo.setInverestFee(BigDecimal.ZERO);
								commonCollectionVo.setInverestFeeStr(MoneyUtil.roundMoney(commonCollectionVo.getInverestFee()));
								commonCollectionVo.setGetYesAccount(commonCollectionVo.getRepayYesaccount());
								commonCollectionVo.setGetYesAccountStr(MoneyUtil.roundMoney(commonCollectionVo.getGetYesAccount()));
							}
						}
					}
				} else {
					if (commonCollectionVo.getStatus() == 2 || commonCollectionVo.getStatus() == 3) {
						if (commonCollectionVo.getAdvancetime() != null) {
							if (Long.parseLong(DateUtils.dateTime2TimeStamp(DateUtils.format(commonCollectionVo.getAdvancetime(), DateUtils.YMD_HMS))) >= Long
									.parseLong(DateUtils.dateTime2TimeStamp("2015-07-20 18:00:00"))) {
								commonCollectionVo.setInverestFee(BigDecimal.ZERO);
								commonCollectionVo.setInverestFeeStr(MoneyUtil.roundMoney(commonCollectionVo.getInverestFee()));
								commonCollectionVo.setGetYesAccount(commonCollectionVo.getRepayYesaccount());
								commonCollectionVo.setGetYesAccountStr(MoneyUtil.roundMoney(commonCollectionVo.getGetYesAccount()));
							}
						}
					}
					if (commonCollectionVo.getStatus() == 1) {
						if (commonCollectionVo.getRepayYestime() != null && !commonCollectionVo.getRepayYestime().equals("")) {
							if (Long.parseLong(commonCollectionVo.getRepayYestime()) >= Long.parseLong(DateUtils.dateTime2TimeStamp(
									"2015-07-20 18:00:00").toString())) {
								commonCollectionVo.setInverestFee(BigDecimal.ZERO);
								commonCollectionVo.setInverestFeeStr(MoneyUtil.roundMoney(commonCollectionVo.getInverestFee()));
								commonCollectionVo.setGetYesAccount(commonCollectionVo.getRepayYesaccount());
								commonCollectionVo.setGetYesAccountStr(MoneyUtil.roundMoney(commonCollectionVo.getGetYesAccount()));
							}
						}
					}
				}
			}
			commonCollectionVoList.add(commonCollectionVo);
		}
		pages.setResult(commonCollectionVoList);
		return pages;
	}

	@Override
	public List<CommonCollectionVo> queryMyCollections(Boolean statistic, Map<String, Object> parameter) {
		List<CommonCollectionVo> returnList;
		if (!statistic) {
			convertParameter(parameter);
		}

		returnList = accCommonCollectionMapper.queryAccCommonCollections(parameter);
		returnList = calculatedLateInterest(null, returnList);
		return returnList;
	}

	/**
	 * <p>
	 * Description:参数转换处理<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年12月23日
	 * @param parameter
	 *            void
	 */
	private void convertParameter(Map<String, Object> parameter) {
		String beginTime = (String) parameter.get("beginTime");
		String endTime = (String) parameter.get("endTime");
		// 时间参数格式转换
		if (beginTime != null && !"".equals(beginTime)) {
			beginTime = com.dxjr.utils.DateUtils.date2TimeStamp(beginTime);
			parameter.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			endTime = com.dxjr.utils.DateUtils.dayOffset(DateUtils.parse(endTime, DateUtils.YMD_DASH), 1).getTime() / 1000L + "";
			parameter.put("endTime", endTime);
		}
		// end transTime
	}

	@Override
	public Page queryAccFirstCollections(Map<String, Object> parameter, Page pages) {
		pages.setTotalCount(accCommonCollectionMapper.countAccFirstCommonCollections(parameter));
		pages.setResult(accCommonCollectionMapper.queryAccFirstCommonCollectionsWithPage(parameter, pages));
		return pages;
	}

	@Override
	public List<CommonCollectionVo> queryAccFirstCollections(Map<String, Object> parameter) {
		return accCommonCollectionMapper.queryAccFirstCommonCollections(parameter);
	}

	@Override
	public List<CommonCollectionVo> calculatedLateInterest(Page p, List<CommonCollectionVo> listReturn) {
		if (null != p && null != p.getResult()) {
			for (int i = 0; i < p.getResult().size(); i++) {
				CommonCollectionVo collectionAggVo = (CommonCollectionVo) p.getResult().get(i);
				listReturn.add(calculatedOneCollectionLaterInterest(collectionAggVo));
			}
			return listReturn;
		} else if (null == p) {
			List<CommonCollectionVo> exportList = new ArrayList<>();
			if (null != listReturn && listReturn.size() > 0) {
				for (int i = 0; i < listReturn.size(); i++) {
					CommonCollectionVo collectionAggVo = listReturn.get(i);
					exportList.add(calculatedOneCollectionLaterInterest(collectionAggVo));
				}
			}
			return exportList;
		}
		return null;

	}
	
	@Override
	public List<CommonCollectionVo> calculatedLateInterestInfo(List<CommonCollectionVo> firstCollectionsList, List<CommonCollectionVo> listReturn) {
		if(firstCollectionsList != null && firstCollectionsList.size() > 0){
			for(int i =0; i<firstCollectionsList.size(); i++){
				CommonCollectionVo collectionAggVo = (CommonCollectionVo) firstCollectionsList.get(i);
				listReturn.add(calculatedOneCollectionLaterInterest(collectionAggVo));
			}
			
			return listReturn;
		}
		
		//		if (null != p && null != p.getResult()) {
//			for (int i = 0; i < p.getResult().size(); i++) {
//				CommonCollectionVo collectionAggVo = (CommonCollectionVo) p.getResult().get(i);
//				listReturn.add(calculatedOneCollectionLaterInterest(co	llectionAggVo));
//			}
//			return listReturn;
//		} else if (null == p) {
//			List<CommonCollectionVo> exportList = new ArrayList<>();
//			if (null != listReturn && listReturn.size() > 0) {
//				for (int i = 0; i < listReturn.size(); i++) {
//					CommonCollectionVo collectionAggVo = listReturn.get(i);
//					exportList.add(calculatedOneCollectionLaterInterest(collectionAggVo));
//				}
//			}
//			return exportList;
//		}
//		return null;
		return null;

	}

	/**
	 * 
	 * <p>
	 * Description:按照还款方式、标种、还款状态计算逾期罚息<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月19日
	 * @param collectionAggVo
	 * @return CommonCollectionVo
	 */
	private CommonCollectionVo calculatedOneCollectionLaterInterest(CommonCollectionVo collectionAggVo) {

		long repayTime = Long.parseLong(collectionAggVo.getRepayTime()) * 1000;// 估计还款时间
		Date repayday = DateUtils.fromSecToDate(repayTime);
		Date curday = DateUtils.fromSecToDate(System.currentTimeMillis());

		int lateDays = DateUtils.dayDiff(curday, repayday);

		BigDecimal account = collectionAggVo.getRepayAccount();
		BigDecimal lateDayInterest = account.multiply(BusinessConstants.LATE_INTEREST_RATE_ONE).multiply(new BigDecimal(lateDays));
		BigDecimal lateDayInterest2 = account.multiply(BusinessConstants.LATE_INTEREST_RATE_TWO).multiply(new BigDecimal(lateDays));

		boolean isVip = false;
		if (collectionAggVo != null && collectionAggVo.getVipApp_passed() != null && collectionAggVo.getVipApp_passed() == 1) {
			isVip = true;
		}

		BorrowVo borrow = collectionAggVo.getBorrowVo();

		if (collectionAggVo == null || collectionAggVo.getStatus() == null) {
			/** 没有记录或状态异常 */
			return null;
		}
		// 如果未还款
		if (collectionAggVo.getStatus() == 0) {
			/** 未还款 */
			if (lateDays > 0) {// 逾期天数大于0
				// 是vip 或者非vip未垫付给罚息
				if (isVip
						|| (null != collectionAggVo.getBrepaymentRecordVo() && null != collectionAggVo.getBrepaymentRecordVo().getWebstatus() && collectionAggVo
								.getBrepaymentRecordVo().getWebstatus().equals(0))) {
					if (borrow.getBorrowtype() == 1 || borrow.getBorrowtype() == 2 || borrow.getBorrowtype() == 5) {
						collectionAggVo.setLateDays(lateDays);
						collectionAggVo.setLateInterest(lateDayInterest);
					} else if (borrow.getBorrowtype() == 3) { // 净值标 3
						collectionAggVo.setLateDays(lateDays);
						collectionAggVo.setLateInterest(lateDayInterest2);
					}
				}
			} else {
				/** 没有逾期 */
				collectionAggVo.setLateDays(0);
				collectionAggVo.setLateInterest(BigDecimal.ZERO);
			}
		}

		collectionAggVo.setRepayAccount(collectionAggVo.getRepayAccount().setScale(2, BigDecimal.ROUND_HALF_UP));
		collectionAggVo.setLateInterest(collectionAggVo.getLateInterest().setScale(2, BigDecimal.ROUND_HALF_UP));
		return collectionAggVo;
	}

	@Override
	public CollectionRepayInfoVo queryRepayTotalByCnd(CollectionStatisticCnd collectionStatisticCnd) throws Exception {
		return collectionStatisticMapper.queryRepayTotalByCnd(collectionStatisticCnd);
	}

	@Override
	public Boolean existsCollectionRecordByCnd(Map<String, Integer> dto) {
		Integer result = collectionStatisticMapper.queryCollectionRecordByCnd(dto);
		if (result == 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<BCollectionRecordVo> queryCollectionrecord(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return bcollectionrecordMapper.queryCollectionrecord(params);
	}

	@Override
	public List<CommonCollectionVo> queryAccFirstCommonCollectionsList(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return accCommonCollectionMapper.queryAccFirstCommonCollectionsList(parameter);
	}

	/*
	 * @Override public void updateCollectionRecordByIds(String[] collectionIds)
	 * { accCommonCollectionMapper.updateCollectionRecordByIds(collectionIds); }
	 */

}
