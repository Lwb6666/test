package com.dxjr.portal.floatCoupon.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Configuration;
import com.dxjr.portal.configuration.mapper.ConfigurationMapper;
import com.dxjr.portal.fix.mapper.FixBorrowMapper;
import com.dxjr.portal.fix.mapper.FixTenderRealMapper;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.fix.vo.FixTenderRealCnd;
import com.dxjr.portal.fix.vo.FixTenderRealVo;
import com.dxjr.portal.floatCoupon.entity.FloatCoupon;
import com.dxjr.portal.floatCoupon.entity.FloatCouponlog;
import com.dxjr.portal.floatCoupon.entity.ProductConfig;
import com.dxjr.portal.floatCoupon.mapper.FloatCouponMapper;
import com.dxjr.portal.floatCoupon.mapper.FloatCouponlogMapper;
import com.dxjr.portal.floatCoupon.mapper.ProductConfigMapper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FloatCouponService {
	@Autowired
	private FixBorrowMapper fixBorrowMapper;
	@Autowired
	private ProductConfigMapper productConfigMapper;
	@Autowired
	private FixTenderRealMapper fixTenderRealMapper;
	@Autowired
	private ConfigurationMapper configurationMapper;
	@Autowired
	private FloatCouponMapper floatCouponMapper;
	@Autowired
	private FloatCouponlogMapper floatCouponlogMapper;
	
	/**
	 * 
	 * <p>
	 * Description:9月份活动发放定期宝加息券(定期宝满宝时调用此方法)<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @throws Exception
	 * void
	 */
	public void grantFixFloatCoupon(Integer fixBorrowId) throws Exception{
		// 查询该定期宝在加息活动期间的加入记录数量
		if(fixTenderRealMapper.selectCountForFloatByFixBorrowId(fixBorrowId) > 0){
			// 查询定期宝
			FixBorrowVo fixBorrowVo = fixBorrowMapper.getFixBorrowByIdForUpdate(fixBorrowId);
			// 查询加息券
			ProductConfig productConfig = productConfigMapper.selectProductConfigForFix(fixBorrowVo.getLockLimit());
			
			// 查询该定期宝在加息活动期间的加入记录（按人）
			FixTenderRealCnd fixTenderRealCnd = new FixTenderRealCnd();
			fixTenderRealCnd.setFixBorrowId(fixBorrowVo.getId());  // 定期宝id
			fixTenderRealCnd.setBeginTime(productConfig.getActiveTime()); //活动开始时间
			fixTenderRealCnd.setEndTime(productConfig.getDeactiveTime()); //活动截止时间
			List<FixTenderRealVo> list = fixTenderRealMapper.getFixTenderRealByCnd(fixTenderRealCnd);
			if (list.size() > 0) {
				for (FixTenderRealVo fixTenderRealVo : list) {
					fixTenderRealCnd = new FixTenderRealCnd();
					fixTenderRealCnd.setUserId(fixTenderRealVo.getUserId());  // 投资人
					fixTenderRealCnd.setLockLimit(fixBorrowVo.getLockLimit());  // 锁定期限
					fixTenderRealCnd.setBeginTime(productConfig.getActiveTime()); //活动开始时间
					fixTenderRealCnd.setEndTime(productConfig.getDeactiveTime()); //活动截止时间
					//　统计在活动期间内认购总额
					BigDecimal accountTotal = fixTenderRealMapper.getTenderAccountTotalByCnd(fixTenderRealCnd);
					// 查询该类型宝的起始金额
					Configuration configuration = configurationMapper.selectByTypeAndValue(2210, fixBorrowVo.getLockLimit().toString());
					if (configuration == null) {
						throw new Exception("定期宝加息满额金额为空");
					}
					BigDecimal minAccount = new BigDecimal(configuration.getName());
					if (accountTotal.compareTo(minAccount) == -1) { // 认购总额小于起始满额金额，不发加息券
						continue; 
					}
					// 查询该类型宝的封顶金额
					Configuration configuration2 = configurationMapper.selectByTypeAndValue(2220, fixBorrowVo.getLockLimit().toString());
					if (configuration2 == null) {
						throw new Exception("定期宝加息封顶金额为空");
					}
					BigDecimal maxAccount = new BigDecimal(configuration2.getName());
					
					// 查询该用户该类型宝待发放加息的记录
					fixTenderRealCnd = new FixTenderRealCnd();
					fixTenderRealCnd.setUserId(fixTenderRealVo.getUserId());  // 投资人
					fixTenderRealCnd.setLockLimit(fixBorrowVo.getLockLimit());  // 锁定期限
					fixTenderRealCnd.setBeginTime(productConfig.getActiveTime()); //活动开始时间
					fixTenderRealCnd.setEndTime(productConfig.getDeactiveTime()); //活动截止时间
					List<FixTenderRealVo> fixTenderRealList = fixTenderRealMapper.selectFixTenderRealForFloatCouponByCnd(fixTenderRealCnd);
					// 当前笔之前的累计加入金额
					BigDecimal lastSumMoney = BigDecimal.ZERO;
					for (FixTenderRealVo userfixTenderReal : fixTenderRealList) {
						if (userfixTenderReal.getIsFloatCoupon() == null || 
								(userfixTenderReal.getIsFloatCoupon().intValue() != 0 && userfixTenderReal.getIsFloatCoupon().intValue() != 1)) {
							throw new Exception("加息标记异常");
						}
						if (userfixTenderReal.getIsFloatCoupon().intValue() == 0) {  // 未发加息券
							if (userfixTenderReal.getStatus().intValue() == 0) {  // 未失效
								// 加息金额
								BigDecimal money = BigDecimal.ZERO;
								// 如果当前笔累计加入金额小于定期宝加息封顶金额
								if (userfixTenderReal.getSumMoney().compareTo(maxAccount) <= 0) {
									money = userfixTenderReal.getAccount();
								}else {
									if (lastSumMoney.compareTo(maxAccount) <= 0) {
										money = maxAccount.subtract(lastSumMoney);
									}else {
										break;
									}
								}
								// 加息金额大于加入金额，抛出异常
								if (money.compareTo(userfixTenderReal.getAccount()) == 1) {
									throw new Exception("加息金额异常");
								}
								// 加息金额大于0
								if (money.compareTo(BigDecimal.ZERO) == 1) {
									// 发放定期宝加息券
									insertFloatCouponForFix(userfixTenderReal, money, productConfig);
								}
							}
							lastSumMoney = userfixTenderReal.getSumMoney();
							if (lastSumMoney.compareTo(maxAccount) == 1) {
								break;
							}
						}else {
							lastSumMoney = userfixTenderReal.getSumMoney();
							if (lastSumMoney.compareTo(maxAccount) == 1) {
								break;
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>
	 * Description:新增定期宝加息券<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月26日
	 * @param fixTenderRealVo
	 * @param money
	 * @param productConfig
	 * @return
	 * @throws Exception
	 * void
	 */
	public void insertFloatCouponForFix(FixTenderRealVo fixTenderRealVo, BigDecimal money, ProductConfig productConfig) throws Exception{
		FloatCoupon floatCoupon = new FloatCoupon();
		floatCoupon.setUserId(fixTenderRealVo.getUserId());
		floatCoupon.setProductConfigId(productConfig.getId());
		floatCoupon.setType(1);  // 加息券
		floatCoupon.setTargetType(productConfig.getTargetType());
		floatCoupon.setTargetId(fixTenderRealVo.getId());
		floatCoupon.setTargetName("t_fix_tender_real");
		floatCoupon.setBizId(fixTenderRealVo.getFixBorrowId());
		floatCoupon.setStatus(1);  // 加息中
		floatCoupon.setAprType(productConfig.getAprType());
		floatCoupon.setAprYear(productConfig.getAprYear());
		floatCoupon.setAprMonth(productConfig.getAprMonth());
		floatCoupon.setInterest(BigDecimal.ZERO);
		floatCoupon.setCapital(money);  //加息本金
		floatCoupon.setActiveTime(new Date());
		floatCoupon.setActiveUser(-1);
		floatCoupon.setCountDays(0);
		floatCoupon.setExitDate(null);
		floatCoupon.setValidDays(null);
		floatCoupon.setEndDate(null);
		floatCoupon.setRemark("认购定期宝发放加息券");
		floatCoupon.setAdduser(-1);
		floatCoupon.setAddtime(new Date());
		floatCoupon.setAddip("0.0.0.1");
		floatCoupon.setPlatform(3);  // 后台
		floatCoupon.setIsCustody(0);
		floatCoupon.setLastModifyTime(new Date());
		floatCoupon.setLastModifyUser(-1);
		floatCoupon.setLastModifyRemark("启用定期宝加息券");
		floatCouponMapper.insert(floatCoupon);
		if (floatCoupon.getId() == null || floatCoupon.getId().intValue() <= 0) {
			throw new Exception("发放定期宝加息券，新增加息券失败！");
		}
		// 新增日志记录
		FloatCouponlog floatCouponlog = new FloatCouponlog();
		floatCouponlog.setUserId(fixTenderRealVo.getUserId());
		floatCouponlog.setFloatCouponId(floatCoupon.getId());
		floatCouponlog.setType(1); // 发放加息券
		floatCouponlog.setApr(productConfig.getAprYear());
		floatCouponlog.setInterest(BigDecimal.ZERO);
		floatCouponlog.setCapital(money);
		floatCouponlog.setInterestDate(null);
		floatCouponlog.setRemark("发放定期宝加息券");
		floatCouponlog.setAdduser(-1);
		floatCouponlog.setAddtime(new Date());
		floatCouponlog.setAddip("0.0.0.1");
		floatCouponlog.setPlatform(3);
		floatCouponlog.setIsCustody(0);
		floatCouponlogMapper.insert(floatCouponlog);
		
		// 新增日志记录(立即启动)
		floatCouponlog.setId(null);
		floatCouponlog.setType(2); // 启用加息券
		floatCouponlog.setRemark("启用定期宝加息券");
		floatCouponlogMapper.insert(floatCouponlog);
	}
}
