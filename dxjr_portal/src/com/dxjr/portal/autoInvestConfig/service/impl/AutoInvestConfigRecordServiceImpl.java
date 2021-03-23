package com.dxjr.portal.autoInvestConfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.AutoInvestConfig;
import com.dxjr.base.entity.AutoInvestConfigRecord;
import com.dxjr.base.mapper.BaseAutoInvestConfigRecordMapper;
import com.dxjr.common.page.Page;
import com.dxjr.portal.autoInvestConfig.mapper.AutoInvestConfigRecordMapper;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestConfigRecordService;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordCnd;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordVo;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;

/**
 * 
 * <p>
 * Description:自动投标记录业务逻辑方法<br />
 * </p>
 * 
 * @title AutoInvestConfigRecord.java
 * @package com.dxjrweb.account.autoInvest.service
 * @author 杨仕金
 * @version 0.1 2013年12月13日
 */
@Service
public class AutoInvestConfigRecordServiceImpl implements AutoInvestConfigRecordService {

	@Autowired
	private BaseAutoInvestConfigRecordMapper baseAutoInvestConfigRecordMapper;

	@Autowired
	private AutoInvestConfigRecordMapper autoInvestConfigRecordMapper;

	@Override
	public int insertAutoInvestConfigRecord(AutoInvestConfigRecord autoInvestConfigRecord) throws Exception {
		return baseAutoInvestConfigRecordMapper.insertEntity(autoInvestConfigRecord);
	}

	@Override
	public AutoInvestConfigRecord queryById(Integer id) throws Exception {
		return baseAutoInvestConfigRecordMapper.queryById(id);
	}

	@Override
	public int updateAutoInvestConfigRecord(AutoInvestConfigRecord autoInvestConfigRecord) throws Exception {
		return baseAutoInvestConfigRecordMapper.updateEntity(autoInvestConfigRecord);
	}

	@Override
	public AutoInvestConfigRecord setAutoInvestConfigRecord(AutoInvestConfigVo autoInvestConfigVo) {
		AutoInvestConfigRecord autoInvestConfigRecord = new AutoInvestConfigRecord();
		if (autoInvestConfigVo.getId() != null) {
			autoInvestConfigRecord.setAuto_tender_id(autoInvestConfigVo.getId());
		}
		autoInvestConfigRecord.setUser_id(autoInvestConfigVo.getUser_id());
		autoInvestConfigRecord.setStatus(autoInvestConfigVo.getStatus());
		autoInvestConfigRecord.setTender_type(autoInvestConfigVo.getTender_type());
		autoInvestConfigRecord.setBorrow_type(autoInvestConfigVo.getBorrow_type());
		autoInvestConfigRecord.setTimelimit_status(autoInvestConfigVo.getTimelimit_status());
		autoInvestConfigRecord.setMin_time_limit(autoInvestConfigVo.getMin_time_limit());
		autoInvestConfigRecord.setMax_time_limit(autoInvestConfigVo.getMax_time_limit());
		autoInvestConfigRecord.setMin_day_limit(autoInvestConfigVo.getMin_day_limit());
		autoInvestConfigRecord.setMax_day_limit(autoInvestConfigVo.getMax_day_limit());
		autoInvestConfigRecord.setMin_apr(autoInvestConfigVo.getMin_apr());
		autoInvestConfigRecord.setMax_apr(autoInvestConfigVo.getMax_apr());
		autoInvestConfigRecord.setMin_tender_account(autoInvestConfigVo.getMin_tender_account());
		autoInvestConfigRecord.setTender_account_auto(autoInvestConfigVo.getTender_account_auto());
		autoInvestConfigRecord.setTender_scale(autoInvestConfigVo.getTender_scale());
		autoInvestConfigRecord.setAward_flag(autoInvestConfigVo.getAward_flag());
		autoInvestConfigRecord.setBalance_not_enough(autoInvestConfigVo.getBalance_not_enough());
		autoInvestConfigRecord.setSettime(autoInvestConfigVo.getSettime());
		autoInvestConfigRecord.setSetip(autoInvestConfigVo.getSetip());
		autoInvestConfigRecord.setBorrow_type1_status(autoInvestConfigVo.getBorrow_type1_status());
		autoInvestConfigRecord.setBorrow_type2_status(autoInvestConfigVo.getBorrow_type2_status());
		autoInvestConfigRecord.setBorrow_type3_status(autoInvestConfigVo.getBorrow_type3_status());
		autoInvestConfigRecord.setBorrow_type4_status(autoInvestConfigVo.getBorrow_type4_status());
		autoInvestConfigRecord.setUptime(autoInvestConfigVo.getUptime());
		autoInvestConfigRecord.setRemark(autoInvestConfigVo.getRemark());
		autoInvestConfigRecord.setBorrow_type5_status(autoInvestConfigVo.getBorrow_type5_status());
		autoInvestConfigRecord.setAutoType(autoInvestConfigVo.getAutoType());
		autoInvestConfigRecord.setVipLevel(autoInvestConfigVo.getVipLevel());
		autoInvestConfigRecord.setIsUseCur(autoInvestConfigVo.getIsUseCur());
		autoInvestConfigRecord.setCustodyFlag(autoInvestConfigVo.getCustodyFlag());
		return autoInvestConfigRecord;
	}

	@Override
	public AutoInvestConfigRecord setAutoInvestConfigRecord(AutoInvestConfig autoInvestConfig) {
		AutoInvestConfigRecord autoInvestConfigRecord = new AutoInvestConfigRecord();
		if (autoInvestConfig.getId() != null) {
			autoInvestConfigRecord.setAuto_tender_id(autoInvestConfig.getId());
		}
		autoInvestConfigRecord.setUser_id(autoInvestConfig.getUser_id());
		autoInvestConfigRecord.setStatus(autoInvestConfig.getStatus());
		autoInvestConfigRecord.setTender_type(autoInvestConfig.getTender_type());
		autoInvestConfigRecord.setBorrow_type(autoInvestConfig.getBorrow_type());
		autoInvestConfigRecord.setTimelimit_status(autoInvestConfig.getTimelimit_status());
		autoInvestConfigRecord.setMin_time_limit(autoInvestConfig.getMin_time_limit());
		autoInvestConfigRecord.setMax_time_limit(autoInvestConfig.getMax_time_limit());
		autoInvestConfigRecord.setMin_day_limit(autoInvestConfig.getMin_day_limit());
		autoInvestConfigRecord.setMax_day_limit(autoInvestConfig.getMax_day_limit());
		autoInvestConfigRecord.setMin_apr(autoInvestConfig.getMin_apr());
		autoInvestConfigRecord.setMax_apr(autoInvestConfig.getMax_apr());
		autoInvestConfigRecord.setMin_tender_account(autoInvestConfig.getMin_tender_account());
		autoInvestConfigRecord.setTender_account_auto(autoInvestConfig.getTender_account_auto());
		autoInvestConfigRecord.setTender_scale(autoInvestConfig.getTender_scale());
		autoInvestConfigRecord.setAward_flag(autoInvestConfig.getAward_flag());
		autoInvestConfigRecord.setBalance_not_enough(autoInvestConfig.getBalance_not_enough());
		autoInvestConfigRecord.setSettime(autoInvestConfig.getSettime());
		autoInvestConfigRecord.setSetip(autoInvestConfig.getSetip());
		autoInvestConfigRecord.setBorrow_type1_status(autoInvestConfig.getBorrow_type1_status());
		autoInvestConfigRecord.setBorrow_type2_status(autoInvestConfig.getBorrow_type2_status());
		autoInvestConfigRecord.setBorrow_type3_status(autoInvestConfig.getBorrow_type3_status());
		autoInvestConfigRecord.setBorrow_type4_status(autoInvestConfig.getBorrow_type4_status());
		autoInvestConfigRecord.setUptime(autoInvestConfig.getUptime());
		autoInvestConfigRecord.setRemark(autoInvestConfig.getRemark());
		autoInvestConfigRecord.setBorrow_type5_status(autoInvestConfig.getBorrow_type5_status());
		autoInvestConfigRecord.setAutoType(autoInvestConfig.getAutoType());
		autoInvestConfigRecord.setVipLevel(autoInvestConfig.getVipLevel());
		autoInvestConfigRecord.setIsUseCur(autoInvestConfig.getIsUseCur());
		autoInvestConfigRecord.setCustodyFlag(autoInvestConfig.getCustodyFlag());
		return autoInvestConfigRecord;
	}

	@Override
	public Page queryListByAutoInvestConfigRecordCnd(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd, int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalCount = queryAutoInvestConfigRecordCount(autoInvestConfigRecordCnd);
		page.setTotalCount(totalCount);
		List<AutoInvestConfigRecordVo> list = autoInvestConfigRecordMapper.queryAutoInvestConfigRecordListForPage(autoInvestConfigRecordCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public int queryAutoInvestConfigRecordCount(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd) throws Exception {
		return autoInvestConfigRecordMapper.queryAutoInvestConfigRecordCount(autoInvestConfigRecordCnd);
	}

	@Override
	public AutoInvestConfigRecordVo queryForVoById(Integer id) throws Exception {
		return autoInvestConfigRecordMapper.queryById(id);
	}

	@Override
	public AutoInvestConfigRecordVo queryLastRecordByBorrowId(int borrow_id) throws Exception {
		return autoInvestConfigRecordMapper.queryLastRecordByBorrowId(borrow_id);
	}

}
