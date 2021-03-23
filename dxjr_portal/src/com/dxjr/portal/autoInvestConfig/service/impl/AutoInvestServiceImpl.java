package com.dxjr.portal.autoInvestConfig.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.AutoInvestConfig;
import com.dxjr.base.entity.AutoInvestConfigRecord;
import com.dxjr.base.mapper.BaseAutoInvestConfigMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.autoInvestConfig.mapper.AutoInvestConfigMapper;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestConfigRecordService;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestService;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.VipLevelVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.exception.AppException;

/**
 * 
 * <p>
 * Description:自动投标业务处理方法<br />
 * </p>
 * 
 * @title AutoInvestServiceImpl.java
 * @package com.dxjr.portal.autoInvestConfig.service.impl
 * @author yangshijin
 * @version 0.1 2014年5月19日
 */
@Service
public class AutoInvestServiceImpl implements AutoInvestService {
	public Logger logger = Logger.getLogger(AutoInvestServiceImpl.class);

	@Autowired
	private BaseAutoInvestConfigMapper baseAutoInvestConfigMapper;
	@Autowired
	private AutoInvestConfigMapper autoInvestConfigMapper;

	@Autowired
	private TenderRecordMapper tenderRecordMapper;

	@Autowired
	private BorrowMapper borrowMapper;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private TendRecordService tendRecordService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AutoInvestConfigRecordService autoInvestConfigRecordService;
	@Autowired
	private VipLevelService vipLevelService;

	@Override
	public int insertAutoInvestConfig(AutoInvestConfig autoInvestConfig) throws Exception {
		return baseAutoInvestConfigMapper.insertEntity(autoInvestConfig);
	}

	@Override
	public int updateAutoInvestConfig(AutoInvestConfig autoInvestConfig) throws Exception {
		return baseAutoInvestConfigMapper.updateEntity(autoInvestConfig);
	}

	@Override
	public AutoInvestConfig queryById(int id) throws Exception {
		return baseAutoInvestConfigMapper.queryById(id);
	}

	@Override
	public List<AutoInvestConfigVo> queryListByUserId(Integer user_id) throws Exception {
		return autoInvestConfigMapper.queryListByUserId(user_id);
	}

	@Override
	public int queryCountByUserId(int user_id) throws Exception {
		return autoInvestConfigMapper.queryCountByUserId(user_id);
	}

	@Override
	public List<AutoInvestConfigVo> queryAutoInvestConfigAllByUser_id(int user_id) throws Exception {
		return autoInvestConfigMapper.queryAllByUserId(user_id);
	}

	@Override
	public AutoInvestConfigVo queryAutoInvestConfigById(int id) throws Exception {
		return autoInvestConfigMapper.queryById(id);
	}

	@Override
	public String saveAutoTenderBorrow(int borrow_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowid", borrow_id);
		map.put("uptime", 1);
		autoInvestConfigMapper.autoTender(map);
		String msg = map.get("msg").toString();
		if (!"0001".equals(msg)) {
			throw new AppException("自动投标出错");
		}
		return BusinessConstants.SUCCESS;
	}

	@Override
	public String insertOrUpdate(int userId, AutoInvestConfig autoInvestConfig, String ip) throws Exception {
		
		
		//判断是否添加自动类型
		if (autoInvestConfig.getId() == null) {
			int count = autoInvestConfigMapper.findCountByUserIdAndAutoType(userId, autoInvestConfig.getAutoType());
			if (count > 0) {
				return "您已设置了一条该自动类型的规则，不允许再次新增！";
			}
		}
		//判断投标方式
		if (autoInvestConfig.getTender_type() == 1) { // 按金额投标
			autoInvestConfig.setTender_scale((double) 0);
		}else if (autoInvestConfig.getTender_type() == 3) { // 按余额投标
			autoInvestConfig.setTender_account_auto(BigDecimal.ZERO);
			autoInvestConfig.setTender_scale((double) 0);
		} else {
			return "投标方式必须选择一项";
		}
		//判断借款期限
		if(autoInvestConfig.getTimelimit_status()==1){
			autoInvestConfig.setMin_time_limit(0);
			autoInvestConfig.setMax_time_limit(0);
		}
		if (autoInvestConfig.getMin_time_limit() > autoInvestConfig.getMax_time_limit()) {
			return "最大借款月数不能小于最短借款月数";
		}
		if (autoInvestConfig.getMax_time_limit() > 12) {
			return "最大借款月数不能超过12个月";
		}
		if (autoInvestConfig.getMax_time_limit() < 0) {
			return "最短借款月数不能为负";
		}
		//设置自动类型和标类型
		autoInvestConfig.setBorrow_type1_status(1);
		autoInvestConfig.setBorrow_type2_status(1);
		autoInvestConfig.setBorrow_type5_status(1);
		autoInvestConfig.setAutoType(1);
		//不按比例投标 当余额不足的情况下如何处理 1、余额全部投 2、不参与本次投标'
		if (autoInvestConfig.getTender_type() != 3) { // 不按比例投标
			autoInvestConfig.setBalance_not_enough(1);
		}
		if (autoInvestConfig.getStatus() == 1) { // 启用
			// 处理已启用的历史规则
			handleHistoryRecord(userId, autoInvestConfig.getId(), autoInvestConfig.getAutoType(), autoInvestConfig.getPlatform(), autoInvestConfig.getIsUseCur());
		}
		if (vipLevelService.getIsSvipByUserId(userId)) {
			autoInvestConfig.setVipLevel(1);
		} else {
			autoInvestConfig.setVipLevel(0);
		}
		if (autoInvestConfig.getId() == null) { // 新增
			
			autoInvestConfig.setUser_id(userId);
			autoInvestConfig.setSettime(DateUtils.getTime());
			autoInvestConfig.setUptime(getUptime(userId));
			autoInvestConfig.setSetip(ip);
			// 新增规则
			insertAutoInvestConfig(autoInvestConfig);
			return "ADD_SUCCESS";
		} else { // 修改
			// 保存修改的前的旧规则
			AutoInvestConfigVo autoInvestConfigVo = queryAutoInvestConfigById(autoInvestConfig.getId());
			// 初始化日志信息.
			AutoInvestConfigRecord autoInvestConfigRecord = autoInvestConfigRecordService.setAutoInvestConfigRecord(autoInvestConfigVo);
			autoInvestConfigRecord.setAddtime(new Date()); // 添加时间
			if (autoInvestConfigVo.getStatus() == 1) { // 启用
				autoInvestConfigRecord.setAuto_tender_id(autoInvestConfigVo.getId());
				autoInvestConfigRecord.setRownum(autoInvestConfigVo.getRownum());
			}
			autoInvestConfigRecord.setRecord_type(1); // 修改
			autoInvestConfigRecord.setPlatform(autoInvestConfigVo.getPlatform()); // 来源平台
			// 保存日志信息.
			autoInvestConfigRecordService.insertAutoInvestConfigRecord(autoInvestConfigRecord);
			autoInvestConfig.setAutoType(autoInvestConfigVo.getAutoType());
			autoInvestConfig.setSettime(autoInvestConfigVo.getSettime());
			autoInvestConfig.setUser_id(autoInvestConfigVo.getUser_id());
			autoInvestConfig.setSetip(autoInvestConfigVo.getSetip());

			autoInvestConfig.setUptime(autoInvestConfigVo.getUptime());
			if (autoInvestConfig.getStatus() == 1) { // 启用状态
				if (autoInvestConfigVo.getStatus() == 0) { // 未启用状态
					autoInvestConfig.setUptime(getUptime(userId));
				}
			}
			// 更新规则
			autoInvestConfig.setAutoType(1);
			updateAutoInvestConfig(autoInvestConfig);
			return "UPDATE_SUCCESS";
		}
	}

	/**
	 * 
	 * <p>
	 * Description:处理历史启用的记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月25日
	 * @param userId
	 * @param id
	 *            待修改记录Id
	 * @param autoType
	 *            待修改记录的自动类型
	 * @throws Exception
	 *             void
	 */
	public void handleHistoryRecord(int userId, Integer id, Integer autoType, Integer platform, Integer isUseCur) throws Exception {
		// 查询用户已启用的自动投标规则并获取排队号
		List<AutoInvestConfigVo> list = queryAutoInvestConfigAllByUser_id(userId);
		if (id != null) { // 修改时
			if (list.size() > 0) {
				for (AutoInvestConfigVo autoInvestConfigVo : list) {
					if (autoInvestConfigVo.getId().intValue() != id.intValue()) {
						if (autoType == autoInvestConfigVo.getAutoType()) {
							// 保存停用的其他规则
							AutoInvestConfigRecord autoInvestConfigRecord = autoInvestConfigRecordService
									.setAutoInvestConfigRecord(autoInvestConfigVo);
							autoInvestConfigRecord.setAddtime(new Date()); // 添加时间
							autoInvestConfigRecord.setRecord_type(1); // 修改
							autoInvestConfigRecord.setRownum(autoInvestConfigVo.getRownum()); // 排队号
							autoInvestConfigRecord.setPlatform(autoInvestConfigVo.getPlatform());
							// 保存日志信息.
							autoInvestConfigRecordService.insertAutoInvestConfigRecord(autoInvestConfigRecord);

							autoInvestConfigVo.setStatus(0); // 停用
							autoInvestConfigVo.setPlatform(platform);
							autoInvestConfigVo.setIsUseCur(isUseCur);
							// 将autoInvestConfigVo转换为autoInvestConfig类
							AutoInvestConfig autoInvestConfig = new AutoInvestConfig();
							BeanUtils.copyProperties(autoInvestConfigVo, autoInvestConfig);
							updateAutoInvestConfig(autoInvestConfig);
						}
					}
				}
			}
		} else { // 新增时
			if (list.size() > 0) {
				for (AutoInvestConfigVo autoInvestConfigVo : list) {
					if (autoType == autoInvestConfigVo.getAutoType()) {
						// 保存停用的其他规则
						AutoInvestConfigRecord autoInvestConfigRecord = autoInvestConfigRecordService.setAutoInvestConfigRecord(autoInvestConfigVo);
						autoInvestConfigRecord.setAddtime(new Date()); // 添加时间
						autoInvestConfigRecord.setRecord_type(1); // 修改
						autoInvestConfigRecord.setRownum(autoInvestConfigVo.getRownum()); // 排队号
						autoInvestConfigRecord.setPlatform(autoInvestConfigVo.getPlatform());
						// 保存日志信息.
						autoInvestConfigRecordService.insertAutoInvestConfigRecord(autoInvestConfigRecord);

						autoInvestConfigVo.setStatus(0); // 停用
						autoInvestConfigVo.setPlatform(platform);
						autoInvestConfigVo.setIsUseCur(isUseCur);
						// 将autoInvestConfigVo转换为autoInvestConfig类
						AutoInvestConfig autoInvestConfig = new AutoInvestConfig();
						BeanUtils.copyProperties(autoInvestConfigVo, autoInvestConfig);
						updateAutoInvestConfig(autoInvestConfig);
					}
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String EnableOrDisableRecord(int id, int status, ShiroUser shiroUser) throws Exception {
		// 修改前的旧规则
		AutoInvestConfigVo autoInvestConfigVo = queryAutoInvestConfigById(id);
		if(autoInvestConfigVo.getUser_id()!=shiroUser.getUserId()){
			return "非法行为";
		}
		if(autoInvestConfigVo.getTender_type()==2){
			return "您当前的投标方式是【按比例投标】，该投标方式已失效，禁止启用，请重新设置";
		}
		if(autoInvestConfigVo.getBorrow_type()==4){
			return "您当前的还款方式是【按天还款】，该还款方式已失效，禁止启用，请重新设置";
		}
		if (status == 0 && autoInvestConfigVo.getStatus() != 1) {
			return "数据已变更，请刷新后重试！";
		}
		if (status == 1 && autoInvestConfigVo.getStatus() != 0) {
			return "数据已变更，请刷新后重试！";
		}
		if(autoInvestConfigVo.getMin_day_limit()>0&&autoInvestConfigVo.getMax_time_limit()==0
				&&autoInvestConfigVo.getMin_time_limit()==0){
			return "您当前的借款期限是【按天范围 】，该借款期限已失效，禁止启用，请重新设置";
		}
		if (status == 1 && autoInvestConfigVo.getStatus() == 0) {
			if (autoInvestConfigVo.getAutoType() == 1) {
				if (autoInvestConfigVo.getBorrow_type1_status() == 0 && autoInvestConfigVo.getBorrow_type2_status() == 0
						&& autoInvestConfigVo.getBorrow_type5_status() == 0) {
					return "必须选择一项借款标！";
				}
			}
		}
		if (status == 1) {
			// 处理已启用的历史规则
			handleHistoryRecord(shiroUser.getUserId(), id, autoInvestConfigVo.getAutoType(), shiroUser.getPlatform(),autoInvestConfigVo.getIsUseCur());
		}

		// 保存修改前的旧规则日志信息.
		AutoInvestConfigRecord autoInvestConfigRecord = autoInvestConfigRecordService.setAutoInvestConfigRecord(autoInvestConfigVo);
		autoInvestConfigRecord.setAddtime(new Date()); // 添加时间
		autoInvestConfigRecord.setRecord_type(1); // 修改
		if (autoInvestConfigVo.getStatus() == 1) { // 启用
			autoInvestConfigRecord.setAuto_tender_id(autoInvestConfigVo.getId());
			autoInvestConfigRecord.setRownum(autoInvestConfigVo.getRownum());
		}
		autoInvestConfigRecord.setPlatform(autoInvestConfigVo.getPlatform());
		autoInvestConfigRecordService.insertAutoInvestConfigRecord(autoInvestConfigRecord);

		autoInvestConfigVo.setStatus(status);
		autoInvestConfigVo.setUptime(getUptime(shiroUser.getUserId()));
		autoInvestConfigVo.setPlatform(shiroUser.getPlatform());
		// 将autoInvestConfigVo转换为autoInvestConfig类
		AutoInvestConfig autoInvestConfig = new AutoInvestConfig();
		BeanUtils.copyProperties(autoInvestConfigVo, autoInvestConfig);
		updateAutoInvestConfig(autoInvestConfig);
		if (status == 1) {
			return BusinessConstants.ENABLE;
		} else {
			return BusinessConstants.DISABLE;
		}
	}

	@Override
	public String delAutoInvest(int id, ShiroUser shiroUser) throws Exception {
		AutoInvestConfigVo autoInvestConfigVo = queryAutoInvestConfigById(id);
		if (autoInvestConfigVo.getStatus() == 2) {
			return "该记录已被删除，请勿重复操作！";
		}
		// 保存该要删除的记录
		AutoInvestConfigRecord autoInvestConfigRecord = autoInvestConfigRecordService.setAutoInvestConfigRecord(autoInvestConfigVo);
		if (autoInvestConfigVo.getStatus() == 1) { // 启用
			autoInvestConfigRecord.setAuto_tender_id(autoInvestConfigVo.getId());
			autoInvestConfigRecord.setRownum(autoInvestConfigVo.getRownum());
		}
		autoInvestConfigRecord.setAddtime(new Date()); // 添加时间
		autoInvestConfigRecord.setRecord_type(3); // 删除
		autoInvestConfigRecord.setPlatform(autoInvestConfigVo.getPlatform());
		autoInvestConfigRecordService.insertAutoInvestConfigRecord(autoInvestConfigRecord);

		// 删除规则
		autoInvestConfigVo.setStatus(2);
		autoInvestConfigVo.setPlatform(shiroUser.getPlatform());
		// 将autoInvestConfigVo转换为autoInvestConfig类
		AutoInvestConfig autoInvestConfig = new AutoInvestConfig();
		BeanUtils.copyProperties(autoInvestConfigVo, autoInvestConfig);
		if (updateAutoInvestConfig(autoInvestConfig) > 0) {
			return BusinessConstants.SUCCESS;
		} else {
			return "删除失败";
		}
	}

	@Override
	public Integer findCountByUserIdAndAutoType(Integer user_id, Integer autoType) throws Exception {
		return autoInvestConfigMapper.findCountByUserIdAndAutoType(user_id, autoType);
	}

	public String getUptime(int userId) throws Exception {
		// 判断该用户是否是终身顶级会员
		VipLevelVo vipLevelVo = vipLevelService.queryByUserIdAndType(userId, BusinessConstants.VIP_LEVEL_TWO);
		String initStr = "";
		if (vipLevelVo != null && vipLevelVo.getStatus().intValue() == BusinessConstants.VIP_LEVEL_STATUS_ENABLE) {
			int index = 17 - vipLevelVo.getOrder().toString().length();
			for (int i = 0; i < index; i++) {
				initStr = initStr + "0";
			}
			return initStr + vipLevelVo.getOrder();
		} else {
			// 获取排队时间
			return String.valueOf(new Date().getTime()) + "0001";
		}
	}

	@Override
	public Integer countTotalsByUserId(Integer userId) throws Exception {
		
		return autoInvestConfigMapper.countTotalsByUserId(userId);	 
	}
}
