package com.dxjr.portal.autoInvestConfig.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvest;
import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvestRecord;
import com.dxjr.portal.autoInvestConfig.mapper.FixAutoInvestMapper;
import com.dxjr.portal.autoInvestConfig.mapper.FixAutoInvestRecordMapper;
import com.dxjr.portal.autoInvestConfig.service.FixAutoInvestService;
import com.dxjr.portal.curAccount.mapper.CurAccountMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.VipLevelMapper;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.VipLevelVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

/**
 * 
 * <p>
 * Description:自动投资-设置定期宝<br />
 * </p>
 * @title FixAutoInvestService.java
 * @package com.dxjr.portal.autoInvestConfig.service 
 * @author yubin
 * @version 0.1 2015年11月20日
 */
@Service
public class FixAutoInvestServiceImpl implements FixAutoInvestService {
    
	@Autowired
	private FixAutoInvestMapper fixAutoInvestMapper;
	@Autowired
	private FixAutoInvestRecordMapper fixAutoInvestRecordMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VipLevelMapper vipLevelMapper;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private CurAccountMapper curAccountMapper; 
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	public String initSetting(int userId,int autoId){
		if(autoId==0){
			if(fixAutoInvestMapper.checkExists(userId)>0){
				return "用户只能添加1条自动投宝规则";
			}
		}
		if(memberMapper.queryMobileIspassed(userId).intValue()<1){
			return "请先进行手机认证";
		}
		return "";
	}
	
	@Override
	public String insert(FixAutoInvest auto,ShiroUser shrioUser,String ip) throws Exception {
		//新增
		if(auto.getId()==null){
			auto.setUserId(shrioUser.getUserId());
			auto.setPlatform(shrioUser.getPlatform());
			auto.setAddip(ip);
			
			//设置uptime
			String uptime = null;
			if("1".equals(auto.getStatus())){
				uptime = this.getUptime(shrioUser.getUserId());
			}
			auto.setUptime(uptime);
			
			if("2".equals(auto.getAutoTenderType())){
				auto.setTenderMoney(null);
			}
			
			String checkResult = this.checkFixAutoData(auto,0);
			if(checkResult.equals(BusinessConstants.SUCCESS)){
				int flag1=fixAutoInvestMapper.insert(auto);
				
				//记日志
				auto = fixAutoInvestMapper.selectByUserId(shrioUser.getUserId());
				FixAutoInvestRecord record=new FixAutoInvestRecord();
				record.setRecordType(1);//记录类型【1：设置 2：修改 3：停用 4：启用 5：删除 6：投宝 7：流宝 8：撤宝 9：重新排队】
				record.setRownum(auto.getRownum());
				record.setAutoInvestId(auto.getId());
				record.setLimitMoney(auto.getLimitMoney());
				record.setAddip(ip);
				record.setPlatform(shrioUser.getPlatform());
				
				int flag2=fixAutoInvestRecordMapper.insert(record);
				if(flag1>0&&flag2>0){
					return BusinessConstants.SUCCESS;
				}
			}else{
				return checkResult;
			}
		}else{
			//修改
			FixAutoInvest oldAuto = fixAutoInvestMapper.selectByPrimaryKey(auto.getId());
			if(oldAuto.getStatus().equals("-1")){
				return "数据不存在";
			}
			if(!oldAuto.getUserId().equals(shrioUser.getUserId())){
				return "非法操作";
			}
			auto.setUserId(shrioUser.getUserId());
			if(auto.getStatus().equals("1") && StringUtils.isEmpty(auto.getUptime())){
				auto.setUptime(this.getUptime(shrioUser.getUserId()));
			}else if(auto.getStatus().equals("0")){
				auto.setUptime(null);
			}
			String checkResult = this.checkFixAutoData(auto,auto.getId());
			if(checkResult.equals(BusinessConstants.SUCCESS)){
				fixAutoInvestMapper.updateByPrimaryKeySelective(auto);
				
				//记日志
				auto = fixAutoInvestMapper.selectByUserId(shrioUser.getUserId());
				FixAutoInvestRecord record=new FixAutoInvestRecord();
				record.setRecordType(2);//记录类型【1：设置 2：修改 3：停用 4：启用 5：删除 6：投宝 7：流宝 8：撤宝 9：重新排队】
				record.setRownum(auto.getRownum());
				record.setAutoInvestId(auto.getId());
				record.setLimitMoney(auto.getLimitMoney());
				record.setAddip(ip);
				record.setPlatform(shrioUser.getPlatform());
				
				int flag2=fixAutoInvestRecordMapper.insert(record);
				if(flag2>0){
					return BusinessConstants.SUCCESS;
				}
			}else{
				return checkResult;
			}
			
		}
		return BusinessConstants.FAILURE;
	}

	@Override
	public String updateByPrimaryKeySelective(FixAutoInvest auto,int record_type,ShiroUser shrioUser,String ip)throws Exception {
		
		int flag1=fixAutoInvestMapper.updateByPrimaryKeySelective(auto);
		
		//记日志
		if(record_type!=5)auto = fixAutoInvestMapper.selectByUserId(auto.getUserId());
		
		FixAutoInvestRecord record=new FixAutoInvestRecord();
		record.setRownum(auto.getRownum());
		record.setRecordType(record_type);//记录类型【1：设置 2：修改 3：停用 4：启用 5：删除 6：投宝 7：流宝 8：撤宝 9：重新排队】
		record.setAutoInvestId(auto.getId());
		record.setLimitMoney(auto.getLimitMoney());
		record.setAddip(ip);
		record.setPlatform(shrioUser.getPlatform());
		
		int flag2=fixAutoInvestRecordMapper.insert(record);
		 
		
		if(flag1>0&&flag2>0){
			return BusinessConstants.SUCCESS;
		}
		return BusinessConstants.FAILURE;
	}

	@Override
	public FixAutoInvest selectByPrimaryKey(Integer id) throws Exception{
		// TODO Auto-generated method stub
		return fixAutoInvestMapper.selectByPrimaryKey(id);
	}
	

	@Override
	public FixAutoInvest selectByUserId(Integer userId) throws Exception {
		return fixAutoInvestMapper.selectByUserId(userId);
	}
	public String getUptime(Integer userId )throws Exception{
		 
		 VipLevelVo vo= vipLevelMapper.queryByUserIdAndType(userId,1);
		 
		 if(vo!=null&&vo.getOrder()>0){
			 return "0000"+vo.getOrder()+System.currentTimeMillis();
		 }else{
			 return System.currentTimeMillis()+"10000";
		 }
	}
	
	/**
	 * 数据验证
	 * <p>
	 * Description:验证是绑定手机号码<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年11月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String checkFixAutoData(FixAutoInvest a,int autoId)throws Exception{
		String s=initSetting(a.getUserId(),autoId);
		if("".equals(s)){
			//验证数据
			if(a.getAutoTenderType()==null||a.getStatus()==null||a.getFixLimit()==null||a.getIsUseCur()==null){
				return "非法操作";
			}
			
			if("1".equals(a.getAutoTenderType())){
				if(a.getTenderMoney()<100 || a.getTenderMoney()%100!=0){
					return "非法操作";
				}
			}
		}else{
			return s;
		}
		return BusinessConstants.SUCCESS;
	}

	@Override
	public Page queryListByAutoInvestConfigRecordCnd(Integer userId,
			int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalCount = fixAutoInvestRecordMapper.queryFixAutoInvestRecordCount(userId);
		page.setTotalCount(totalCount);
		List<FixAutoInvestRecord> list =  fixAutoInvestRecordMapper.queryFixAutoInvestRecordListForPage(userId, page);
		page.setResult(list);
		return page; 
		 
	}


	 
}
