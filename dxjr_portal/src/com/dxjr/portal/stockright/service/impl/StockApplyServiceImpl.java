package com.dxjr.portal.stockright.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.Dictionary;
import com.dxjr.common.page.Page;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.mapper.RealNameApproMapper;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.stockright.entity.ApplyInfo;
import com.dxjr.portal.stockright.entity.ShareholderRoster;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockApprove;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.mapper.ApplyInfoMapper;
import com.dxjr.portal.stockright.mapper.ShareholderRosterMapper;
import com.dxjr.portal.stockright.mapper.StockAccountMapper;
import com.dxjr.portal.stockright.mapper.StockApproveMapper;
import com.dxjr.portal.stockright.mapper.StockEntrustMapper;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.vo.StockAccountRequest;
import com.dxjr.portal.stockright.vo.StockApplyRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;

/***
 * 交易接口实现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title StockEntrustServiceImpl.java
 * @package com.dxjr.portal.stockright.service.impl 
 * @author xiaofei.li
 * @version 0.1 2016-5-11
 */
@Service

public class StockApplyServiceImpl implements StockApplyService {

	
	@Autowired
	private StockAccountService stockAccountService;
	
	@Autowired
	private ApplyInfoMapper applyInfoMapper;
	
	@Autowired
	private RealNameApproMapper realNameApproMapper;
	
	@Autowired
	private MobileApproMapper mobileApproMapper;
	
	@Autowired
	private  StockApproveMapper stockApproveMapper;
	
	@Autowired
	private  ShareholderRosterMapper shareholderRosterMapper;
	@Autowired
	private StockEntrustMapper stockEntrustMapper;
	@Autowired
	private StockAccountMapper stockAccountMapper;

	@Override
	public Map<String, Object> checkUserInfo(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "2");
		
		
		String sources = Dictionary.getValue(1904,"collection_val");
		//查询改用户是否存在审核通过数据
		StockApplyRequest st = new StockApplyRequest();
		st.setUserId(userId);
		st.setType(1);
		st.setStatusArray(new Integer[] {1,2});
		ApplyInfo app = applyInfoMapper.findApplyList(st);
		BigDecimal collect = new BigDecimal(sources);
		BigDecimal userCollect = stockAccountService.queryUserCollect(userId);
		
		
		ShareholderRoster record = new ShareholderRoster();
		// record.setVersion(1);
		record.setUserId(userId);
		record.setStatus(1);
		ShareholderRoster roster = shareholderRosterMapper.selectShareholder(record);
		
		if(app!=null ){
			//待审核 验证试试待收
			if(app.getStatus()==1){
				//待收大于20W  提示正在审核中
				if(userCollect.compareTo(collect) >= 0){
					map.put("status", "2");
					return map;
				}else{
				//待收小于20W  提示待收不足
					map.put("status", "4");
					map.put("message", "您的待收"+userCollect+",不足20万不能申请内转系统登录！");
					return map;
				}
			}
			//审核通过 直接进入
			if(app.getStatus()==2){
				map.put("status", "3");
				return map;
			}
		}
		
		//待收 >= 20W
		if(userCollect.compareTo(collect) >= 0){
			map.put("status", "2");
			return map;
		}
		//待收 < 20W
		if(userCollect.compareTo(collect)<0){
			if(roster!=null){
				map.put("status", "2");
				return map;
			}else{
				map.put("status", "4");
				map.put("message", "您的待收"+userCollect+",不足20万不能申请内转系统登录！");
				return map;
			}
		}
		//验证黑名单
		StockApplyRequest request = new StockApplyRequest();
		request.setUserId(userId);
		Integer count = applyInfoMapper.checkBlackList(request);
		if(count > 0){
			map.put("status", "4");
			map.put("message", "您属于黑名单用户,不能申请内转系统登录！");
			return map;
		}		
			

		return map;
	}
	
	
	/**
	 * 提供用户申请数据来源
	 */
	public Integer saveApply(ApplyInfo request) throws Exception{
		RealNameApproCnd cnd = new RealNameApproCnd();
		cnd.setUserId(request.getUserId());
		cnd.setIsPassed(1);
		//1:获取用户实名认证信息
		RealNameApproVo  userInfo = realNameApproMapper.queryRealNameApproList(cnd).get(0);
		MobileApproCnd mobCnd  = new MobileApproCnd();
		mobCnd.setUserId(request.getUserId());
		mobCnd.setPassed(1);
		//2:获取用户手机实名认证信息
		MobileApproVo mobilInfo = mobileApproMapper.queryMobileApproList(mobCnd).get(0);
		request.setUserRealName(userInfo.getRealName());
		request.setIdCard(userInfo.getIdCardNo());
		request.setMobile(mobilInfo.getMobileNum());
		request.setSex(Integer.parseInt(userInfo.getSex()));
		request.setStatus(1);//待审核
		request.setRemark("提交审核");
		//3:保存用户申请信息
		request.setCollection(stockAccountService.queryUserCollect(request.getUserId()));
		applyInfoMapper.insertSelective(request);
		StockApprove  approve = new StockApprove();
		approve.setTargetId(request.getId());
		approve.setTargetName("gq_apply_info");
		approve.setTargetType(request.getType());
		approve.setRemark("提交审核");
		approve.setStatus(1);
		approve.setAdduser(request.getAdduser());
		approve.setUserName(request.getUserName());
		approve.setUserRealName(request.getUserRealName());
		approve.setAddip(request.getAddip());
		//4:保存用户申请审核信息
		int code = stockApproveMapper.insertSelective(approve);
		ShareholderRoster record = new ShareholderRoster();
		record.setUserId(request.getUserId());
		record.setStatus(1);
		ShareholderRoster shareholder = shareholderRosterMapper.selectShareholder(record);
		
		//第一批股东申请登录系统自动审核通过
		
		if(shareholder!=null && shareholder.getVersion()==1 && request.getType()==1){
			//修改审核通过状态
			request.setRemark("A轮内转用户自动审核通过");
			request.setStatus(2);
			request.setUpdateuser(-1);
			request.setStockTotal(shareholder.getStockTotal());
			request.setUpdateip("0.0.0.1");
			applyInfoMapper.updateByPrimaryKeySelective(request);
			//添加自动审核通过记录
			StockApprove  app = new StockApprove();
			app.setTargetId(request.getId());
			app.setTargetName("gq_apply_info");
			app.setTargetType(request.getType());
			app.setRemark("A轮内转用户自动审核通过");
			app.setStatus(2);
			app.setAdduser(-1);
			app.setUserName("系统");
			app.setUserRealName("系统");
			app.setAddip("0.0.0.1");
			stockApproveMapper.insertSelective(app);
		}
		//劳婕怡和温征特殊不做任何限制
		if(request.getUserId()==173438 || request.getUserId()==66){
			//修改审核通过状态
			request.setRemark(request.getUserName()+"用户自动审核通过");
			request.setStatus(2);
			request.setUpdateuser(-1);
			request.setStockTotal(0);
			request.setUpdateip("0.0.0.1");
			applyInfoMapper.updateByPrimaryKeySelective(request);
			//添加自动审核通过记录
			StockApprove  app = new StockApprove();
			app.setTargetId(request.getId());
			app.setTargetName("gq_apply_info");
			app.setTargetType(request.getType());
			app.setRemark(request.getUserName()+"用户自动审核通过");
			app.setStatus(2);
			app.setAdduser(-1);
			app.setUserName("系统");
			app.setUserRealName("系统");
			app.setAddip("0.0.0.1");
			stockApproveMapper.insertSelective(app);
		}
		return code;
	}
	
	
	@Override
	public ApplyInfo querySignOutApply(int userId) {
		return applyInfoMapper.querySignOutApply(userId);
	}
	
	
	public Page queryApplyInfoList(StockApplyRequest seach, Page page){
		page.setTotalCount(applyInfoMapper.countApplyInfo(seach));
		page.setResult(applyInfoMapper.queryApplyInfoList(seach, page));
		return page;
	}
	
	public List<StockApprove> findApplyApprove(StockApplyRequest record){
		List<StockApprove> approveList = stockApproveMapper.findApplyApprove(record);
		return approveList;
	}


	@Override
	public ApplyInfo findApplyList(StockApplyRequest record) {
		return applyInfoMapper.findApplyList(record);
	}
	public Integer countApplyInfo(Integer userid){
		StockApplyRequest record = new StockApplyRequest();
		record.setUserId(userid);
		record.setStatus(2);
		record.setType(1);
		return applyInfoMapper.countApplyInfo(record);
	}
	
	public Integer checkBlackList(StockApplyRequest request){
		Integer count = applyInfoMapper.checkBlackList(request);
		return count;
	}
	
	
	public ShareholderRoster selectShareholder(ShareholderRoster record){
		ShareholderRoster roster = shareholderRosterMapper.selectShareholder(record);
		return roster;
	}
	
	public Boolean checkBlankUser(int userId){
			if(userId == 2 || userId == 173438 || userId == 66){
				return true;
			}
			StockApplyRequest stockApp = new StockApplyRequest();
			stockApp.setUserId(userId);
			//查询用户是否在黑名单
			Integer count = applyInfoMapper.checkBlackList(stockApp);
			
			StockEntrustCnd entrustCnd = new StockEntrustCnd();
			entrustCnd.setUserId(userId);
			entrustCnd.setStatusArray(new Integer[] {1, 2});
			List<StockEntrust> entrustList = stockEntrustMapper.findEntrustListByUserId(entrustCnd);
			//查询股东账户
			StockAccountRequest request = new StockAccountRequest();
			request.setUserId(userId);
			StockAccount account = stockAccountMapper.selectByPrimaryKey(request);
			//待收
			
			BigDecimal total = BigDecimal.ZERO;
			//散标待收
			BigDecimal sanBiao = stockAccountMapper.querySanBiao(request);
			if(sanBiao != null){
				total= total.add(sanBiao);
			}
			//定期宝待收
			BigDecimal dingQiBao = stockAccountMapper.queryDingQiBao(request);
			if(dingQiBao != null){
				total= total.add(dingQiBao);
			}
			//存管待收
			BigDecimal cunguan = stockAccountMapper.queryCunGuan(request);
			if(cunguan != null){
				total= total.add(cunguan);
			}
			BigDecimal collection = new  BigDecimal(Dictionary.getValue(1904,"collection_val"));
			if(entrustList.size() ==0 && account==null){
				if(count>0){
					return false;
				}
				if(total.compareTo(collection)==-1){
					return false;
				}
			}
		
		
		return true;
	}
	
}
