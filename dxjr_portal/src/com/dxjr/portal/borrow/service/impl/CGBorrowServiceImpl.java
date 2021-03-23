/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGBorrowServiceImpl.java
 * @package com.dxjr.portal.borrow.service.impl 
 * @author tanghaitao
 * @version 0.1 2016年5月23日
 */
package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.entity.MailSendRecord;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.base.mapper.BaseMailSendRecordMapper;
import com.dxjr.base.mapper.BaseVIPApproMapper;
import com.dxjr.common.custody.xml.FBRes;
import com.dxjr.common.custody.xml.Finance;
import com.dxjr.common.custody.xml.Message;
import com.dxjr.common.custody.xml.PIReq;
import com.dxjr.common.custody.xml.PTRReq;
import com.dxjr.common.custody.xml.PTRRes;
import com.dxjr.common.custody.xml.PTRResList;
import com.dxjr.common.custody.xml.Record;
import com.dxjr.common.custody.xml.RecordList;
import com.dxjr.common.custody.xml.UFBReq;
import com.dxjr.common.custody.xml.XmlUtil;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.entity.TenderRecord;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.CGBorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.InvestBorrow;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.UUIDGenerator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**   
 * <p>
 * Description:存管标service<br />
 * </p>
 * @title CGBorrowServiceImpl.java
 * @package com.dxjr.portal.borrow.service.impl 
 * @author tanghaitao
 * @version 0.1 2016年5月23日
 */

@Service
@Transactional
public class CGBorrowServiceImpl implements CGBorrowService{

	public Logger logger = Logger.getLogger(CGBorrowServiceImpl.class);
	
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TenderRecordMapper tenderRecordMapper;
	@Autowired
	private BaseVIPApproMapper baseVIPApproMapper;
	@Autowired
	private BaseEBankInfoMapper baseEBankInfoMapper;
	@Autowired
	private BorrowMapper borrowMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;
	@Autowired
	private CGUtilService cGUtilService;
	@Autowired
	private BaseMailSendRecordMapper baseMailSendRecordMapper;
	
	
	public String tenderCGBorrow(FBRes fBRes,TenderBorrowCnd tenderBorrowCnd,ShiroUser shiroUser,String addip,String p2pDJ) throws Exception{
		//查询借款标信息，并锁定
		BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
		if(fBRes.getBlockStatus().equals("20")){
			//查询用户账户并锁定
			AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(shiroUser.getUserId());
			BaseEBankInfo baseEBankInfo=baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
			BigDecimal 	interest = null;    
			if(borrowVo.getStyle()==3){
				/**到期还本付息**/
				interest=tenderBorrowCnd.getTenderMoney().multiply(borrowVo.getApr()).divide(new BigDecimal(100*12),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(borrowVo.getTimeLimit())).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else if(borrowVo.getStyle()==4){
				 /**按天还款**/
				interest=tenderBorrowCnd.getTenderMoney().multiply(borrowVo.getApr()).divide(new BigDecimal(100*360),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(borrowVo.getTimeLimit())).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else if(borrowVo.getStyle()==1){
				/**等额本息**/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("money", tenderBorrowCnd.getTenderMoney());
				map.put("apr", borrowVo.getApr());
				map.put("limit", borrowVo.getTimeLimit());
				interest=tenderRecordMapper.getFqInsTotal(map);
				if (interest.compareTo(BigDecimal.ZERO) == -1) {
					interest = BigDecimal.ZERO;
				}
			}else if(borrowVo.getStyle()==2){
				 /**按月付息到期还本**/
				interest=tenderBorrowCnd.getTenderMoney().multiply(borrowVo.getApr()).divide(new BigDecimal(100*12),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(borrowVo.getTimeLimit())).setScale(2, BigDecimal.ROUND_HALF_UP);
			}	    
			
			Integer passed= baseVIPApproMapper.findPassed(shiroUser.getUserId());
			if(passed==null || passed==-1){
				passed=0;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", shiroUser.getUserId());
			tenderRecordMapper.getUserLevelRatio(map);
			TenderRecord tenderRecord=new TenderRecord();
			tenderRecord.setUserId(shiroUser.getUserId());
			tenderRecord.setBorrowId(tenderBorrowCnd.getBorrowid());
			tenderRecord.setAccount(tenderBorrowCnd.getTenderMoney());
			tenderRecord.setInterest(interest);
			tenderRecord.setRepaymentAccount(interest.add(tenderBorrowCnd.getTenderMoney()));
			tenderRecord.setIsVip(passed);
			tenderRecord.setRatio((String)map.get("ratio"));
			tenderRecord.setUserLevel((String)map.get("userLevel"));
			tenderRecord.setPlatform(shiroUser.getPlatform());
			tenderRecord.setIsCustody(1);//浙商存管
			tenderRecord.seteFreezeNo(fBRes.getSerialNo());
			tenderRecord.seteBankInfoId(baseEBankInfo.getId());
			tenderRecord.seteInvestStatus(0);//未上报
			tenderRecord.seteFreezeDate(fBRes.getInstSettleDate());
			tenderRecord.setBizInvestNo(UUIDGenerator.generate(CGBusinessConstants.BORROW_PIReq_ID));
			tenderRecord.setBizFreezeNo(p2pDJ);
			tenderRecord.setAddip(addip);
			tenderRecordMapper.insertTenderrecord(tenderRecord);
			
			//更新借款标
			BorrowVo borrow=new BorrowVo();
			borrow.setId(borrowVo.getId());
			borrow.setAccountYes(borrowVo.getAccountYes().add(tenderBorrowCnd.getTenderMoney()));
			borrow.setTenderTimes(borrowVo.getTenderTimes()+1);
			borrowMapper.updateBorrow(borrow);
			//更新投资人账户资金
			AccountVo av=new AccountVo();
			av.setId(accountvo.getId());
			av.seteUseMoney(accountvo.geteUseMoney().subtract(tenderBorrowCnd.getTenderMoney()));
			av.seteFreezeMoney(accountvo.geteFreezeMoney().add(tenderBorrowCnd.getTenderMoney()));
			accountMapper.updateCGAccount(av);
			//添加账户日志
			AccountLog accountLog=new AccountLog();
			accountLog.setUserId(shiroUser.getUserId());
			accountLog.setType(CGBusinessConstants.CG_TB);
			accountLog.setMoney(tenderBorrowCnd.getTenderMoney());
			accountLog.setToUser(borrowVo.getUserId());
			accountLog.setIdType(0);
			accountLog.setRemark("投标冻结");
			accountLog.setBorrowId(borrowVo.getId());
			accountLog.setBorrowName(borrowVo.getName());
			accountLog.setAddip(addip);
			accountLog.setIsCustody(1);//浙商存管
			baseAccountLogMapper.insertAccountLog(accountLog);
			
			//判断是否是最后一笔
			BigDecimal accountYes=borrowVo.getAccountYes().add(tenderBorrowCnd.getTenderMoney());
			if(accountYes.compareTo(borrowVo.getAccount())==0){
				//更新借款标的状态我满标复审中
				BorrowVo bv=new BorrowVo();
				bv.setId(borrowVo.getId());
				bv.setTimeLimit(borrowVo.getTimeLimit());
				bv.setStatus(3);//满标复审中
				if(borrowVo.getStyle()==4){
					borrowMapper.updateBorrowStutusDay(bv);
				}else{
					borrowMapper.updateBorrowStutus(bv);
				}
				return "checkBorrow";
				
			}
			
		}else if(fBRes.getBlockStatus().equals("30")){
			// 冻结失败，预计已投资总金额减去投资金额
			BorrowVo borrow=new BorrowVo();
			borrow.setId(borrowVo.getId());
			borrow.setAdvance(borrowVo.getAdvance().subtract(tenderBorrowCnd.getTenderMoney()));
			borrowMapper.updateBorrowAdvance(borrow);
			return "投资失败";
		}else{
			// 冻结失败，预计已投资总金额减去投资金额
			BorrowVo borrow=new BorrowVo();
			borrow.setId(borrowVo.getId());
			borrow.setAdvance(borrowVo.getAdvance().subtract(tenderBorrowCnd.getTenderMoney()));
			borrowMapper.updateBorrowAdvance(borrow);
			return "银行处理中";
		}
		return BusinessConstants.SUCCESS;
		
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:调用项目登记接口<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param shiroUser
	 * @param mode
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String savePIReq(BorrowVo borrowVo,ShiroUser shiroUser,String mode,String remark,String relateNo) throws Exception{
		String reqMessage= createPIReqXml(borrowVo);
		System.out.println(reqMessage);
		String reqXml= XmlUtil.sign(reqMessage, CGBusinessConstants.PIREQ);
		//调用项目登记接口
		String rep= XmlUtil.send(reqXml);
		cGUtilService.insertMsg(reqXml, mode, 1, shiroUser, remark, relateNo,borrowVo.getId());
		return rep;
	}
	
	
	
	/**
	 * 项目投资信息登记接口调用
	 */
	public String savePTRReq(BorrowVo borrowVo,List<InvestBorrow> list,ShiroUser shiroUser,String remark,String mode,String relateNo) throws Exception{
		//生成项目投资报文
		String reqMessage= this.createPTRReqXml(borrowVo, list);
		System.out.println(reqMessage);
		String reqXml=XmlUtil.sign(reqMessage, CGBusinessConstants.PTRReq);
		//调用项目登记接口
		String rep= XmlUtil.send(reqXml);
		cGUtilService.insertMsg(reqXml, mode, 1, shiroUser, remark, relateNo,borrowVo.getId());
		return rep;
		
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:解析项目投资登记报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param repXml
	 * @param mode
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * PTRRes
	 */
	public PTRRes parsePTRResXml(String repXml,String mode,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId) throws Exception{
		String msg= cGUtilService.saveResXml(repXml, mode, shiroUser, remark, relateNo,borrowId);
		if(!msg.equals(BusinessConstants.SUCCESS)){
			return null;
		}
		PTRRes pTRRes=new PTRRes();
		List<PTRResList> pTRResList = new ArrayList<PTRResList>();
		Map<String, Object> map= XmlUtil.toBiz(repXml, CGBusinessConstants.PTRRes);
		pTRRes.setSuccount(Integer.parseInt((String)map.get("succount")) );
		pTRRes.setSucsum(Integer.parseInt((String)map.get("sucsum")));
		pTRRes.setFailcount(Integer.parseInt((String)map.get("failcount")));
		pTRRes.setFailsum(Integer.parseInt((String)map.get("failsum")));
		
		List<Map> mapList= XmlUtil.toDetail(repXml, CGBusinessConstants.PTRRes);	
		for (Map map2 : mapList) {
			PTRResList pl=new PTRResList();
			pl.setP2PserialNo((String)map2.get("P2PserialNo"));
			pl.setInvestmentStatus((String)map2.get("InvestmentStatus"));
			pl.setInvestmentSerialNo((String)map2.get("InvestmentSerialNo"));
			pl.setInstSettleDate((String)map2.get("instSettleDate"));
			pTRResList.add(pl);
		}
		pTRRes.setpTRResList(pTRResList);
		
		return pTRRes;
	}
	
	
	/**
	 * 后台复审，解析项目投资响应报文
	 */
	public PTRRes reviewParsePTRResXml(String repXml) throws Exception{
		
		PTRRes pTRRes=new PTRRes();
		List<PTRResList> pTRResList = new ArrayList<PTRResList>();
		Map<String, Object> map= XmlUtil.toBiz(repXml, CGBusinessConstants.PTRRes);
		pTRRes.setSuccount(Integer.parseInt((String)map.get("succount")) );
		pTRRes.setSucsum(Integer.parseInt((String)map.get("sucsum")));
		pTRRes.setFailcount(Integer.parseInt((String)map.get("failcount")));
		pTRRes.setFailsum(Integer.parseInt((String)map.get("failsum")));
		
		List<Map> mapList= XmlUtil.toDetail(repXml, CGBusinessConstants.PTRRes);	
		for (Map map2 : mapList) {
			PTRResList pl=new PTRResList();
			pl.setP2PserialNo((String)map2.get("P2PserialNo"));
			pl.setInvestmentStatus((String)map2.get("InvestmentStatus"));
			pl.setInvestmentSerialNo((String)map2.get("InvestmentSerialNo"));
			pl.setInstSettleDate((String)map2.get("instSettleDate"));
			pTRResList.add(pl);
		}
		pTRRes.setpTRResList(pTRResList);
		
		return pTRRes;
	}
	
	
	
	
	
	/**
	 * 
	 * <p>
	 * Description:更新投标记录信息<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param list
	 * @throws Exception
	 * void
	 */
	public void updateTenderrecord(BorrowVo borrowVo,PTRRes pTRRes,List<InvestBorrow> InvestBorrowList) throws Exception{
		//并锁定借款标
		BorrowVo bv = borrowService.selectByPrimaryKeyForUpdate(borrowVo.getId());
		//更新投资信息（投资成功笔数.....）
		BorrowVo bvo=new BorrowVo();
		bvo.setId(bv.getId());
		bvo.setSuccount(pTRRes.getSuccount());
		bvo.setSucsum(new BigDecimal(pTRRes.getSucsum()/100));
		bvo.setFailcount(pTRRes.getFailcount());
		bvo.setFailsum(new BigDecimal(pTRRes.getFailsum()/100)  );
		borrowMapper.updateCGBorrowZS(bvo);
		
		
		List<PTRResList> list=pTRRes.getpTRResList();
		//查询投标记录并锁定
		List<TenderRecord> tv= tenderRecordMapper.findTenderrecordForUpdate(borrowVo.getId());
		for (TenderRecord tr : tv) {
			for (PTRResList ptrResList : list) {
				if(tr.getBizInvestNo().equals(ptrResList.getP2PserialNo())){
					//更新投标记录
					TenderRecord tenderRecord=new TenderRecord();
					//tenderRecord.seteInvestDate(ptrResList.getInstSettleDate());
					tenderRecord.seteInvestNo(ptrResList.getInvestmentSerialNo());
					tenderRecord.seteInvestStatus(Integer.parseInt(ptrResList.getInvestmentStatus()));
					tenderRecord.setId(tr.getId());
					tenderRecordMapper.updateTenderrecordZS(tenderRecord);
				}
				
			}
			
		}
		
		
		try {
			BigDecimal account = BigDecimal.ZERO;
			for (InvestBorrow investBorrow : InvestBorrowList) {
				account=account.add(investBorrow.getInvestmentAmount());
			}
			if(pTRRes.getFailsum()>0 || pTRRes.getFailcount()>0 || pTRRes.getSucsum()!=account.multiply(new BigDecimal(100)).intValue() || pTRRes.getSuccount()!=InvestBorrowList.size()){
				//新增邮件待发记录 
				MailSendRecord mailSendRecord=new MailSendRecord();
				mailSendRecord.setTypeId(borrowVo.getId());
				mailSendRecord.setType(8);//项目投资信息上报未全部成功
				mailSendRecord.setStatus(0);
				mailSendRecord.setAddtime(new Date());
				baseMailSendRecordMapper.insertEntity(mailSendRecord);
				
			}
		} catch (Exception e) {
			logger.error("新增邮件待发记录 ",e);
		}
		
		
		
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:后台复审，更新投标记录<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年6月14日
	 * @param borrowVo
	 * @param pTRRes
	 * @throws Exception
	 * void
	 */
	public void reviewUpdateTenderrecord(BorrowVo borrowVo,PTRRes pTRRes) throws Exception{
		//并锁定借款标
		BorrowVo bv = borrowService.selectByPrimaryKeyForUpdate(borrowVo.getId());
		//更新投资信息（投资成功笔数.....）
		BorrowVo bvo=new BorrowVo();
		bvo.setId(bv.getId());
		bvo.setSuccount(pTRRes.getSuccount());
		bvo.setSucsum(new BigDecimal(pTRRes.getSucsum()/100));
		bvo.setFailcount(pTRRes.getFailcount());
		bvo.setFailsum(new BigDecimal(pTRRes.getFailsum()/100)  );
		borrowMapper.updateCGBorrowZS(bvo);
		
		
		List<PTRResList> list=pTRRes.getpTRResList();
		//查询投标记录并锁定
		List<TenderRecord> tv= tenderRecordMapper.findTenderrecordForUpdate(borrowVo.getId());
		for (TenderRecord tr : tv) {
			for (PTRResList ptrResList : list) {
				if(tr.getBizInvestNo().equals(ptrResList.getP2PserialNo())){
					//更新投标记录
					TenderRecord tenderRecord=new TenderRecord();
					//tenderRecord.seteInvestDate(ptrResList.getInstSettleDate());
					tenderRecord.seteInvestNo(ptrResList.getInvestmentSerialNo());
					tenderRecord.seteInvestStatus(Integer.parseInt(ptrResList.getInvestmentStatus()));
					tenderRecord.setId(tr.getId());
					tenderRecordMapper.updateTenderrecordZS(tenderRecord);
				}
				
			}
			
		}
		
		
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:更新标的状态<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param pTRRes
	 * void
	 */
	public void updateBorrowStatus(BorrowVo borrowVo) throws Exception{
			//将标的状态更新为银行复审中
			BorrowVo bv=new BorrowVo();
			bv.setId(borrowVo.getId());
			bv.setStatus(6);//6（银行复审中）
			borrowMapper.updateBorrow(bv);
			
	}
	
	
	
	/**
	 * 
	 * <p>
	 * Description:投资资金冻结解冻接口<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月26日
	 * @param borrowVo
	 * @param oriSerialNo
	 * @param investmentAmount
	 * @param shiroUser
	 * @param remark
	 * @param mode
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveUFBReq(BorrowVo borrowVo,String oriSerialNo,Integer investmentAmount,ShiroUser shiroUser,String remark,String mode,String relateNo) throws Exception{
		//生成项目投资报文
		BaseEBankInfo baseEBankInfo=baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		String reqMessage= this.createUFBReqXml(borrowVo, baseEBankInfo, oriSerialNo, investmentAmount);
		System.out.println(reqMessage);
		String reqXml=XmlUtil.sign(reqMessage, CGBusinessConstants.UFBReq);
		//调用项目登记接口
		String rep= XmlUtil.send(reqXml);
		cGUtilService.insertMsg(reqXml, mode, 1, shiroUser, remark, relateNo,borrowVo.getId());
		return rep;
		
	} 
	
	
	
	/**
	 * 
	 * <p>
	 * Description:组建项目投资信息登记报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param list
	 * @return
	 * String
	 */
	public String createPTRReqXml(BorrowVo borrowVo,List<InvestBorrow> list){
		 XStream xstream = new XStream();
		 xstream = new XStream(new DomDriver());
		 Finance finance =new Finance();
		 Message message =new Message();
		 message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		 PTRReq pTRReq =new PTRReq();
		 pTRReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		 pTRReq.setProjectId(borrowVo.geteProjectId());
		 pTRReq.setProjectName(borrowVo.getName());
		 pTRReq.setProjectStatus("1");//满标
		 pTRReq.setRepayName(borrowVo.getRepayName());
		 pTRReq.setRepayAcct(borrowVo.getRepayAcct());
		 pTRReq.setCount(list.size());
		
		 Integer amout = 0;
		 List<Record> record=new ArrayList<Record>();
		 for (InvestBorrow investBorrow : list) {
			 Record r=new Record();
			// r.setP2PserialNo(UUIDGenerator.getProjectId(CGBusinessConstants.P2P_SERIAL_NO, investBorrow.getInvestBorrowId()));
			 r.setP2PserialNo(investBorrow.getP2pSerialNo());
			 r.setRealname(investBorrow.getRealname());
			 r.setCertType(investBorrow.getCertType());
			 r.setCertNo(investBorrow.getCertNo());
			 r.setBindSerialNo(investBorrow.getBindSerialNo());
			 r.setPayNum(investBorrow.getPayNum());
			 r.setInvestmentAmount((investBorrow.getInvestmentAmount().multiply(new BigDecimal(100))).intValue());
			 r.setInterestCapital(investBorrow.getInterestCapital().multiply(new BigDecimal(100)).intValue());
			 amout=amout+r.getInvestmentAmount();
			 record.add(r);
		}
		 pTRReq.setAmout(amout);
		 RecordList recordList = new RecordList();
		 recordList.setRecordList(record);
		 pTRReq.setList(recordList);
		 
			xstream.autodetectAnnotations(true);
	        xstream.aliasField(CGBusinessConstants.PTRReq, Message.class, "Mode");
	        xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]  
	        message.setMode(pTRReq);
	        finance.setMessage(message);
		
		return xstream.toXML(finance);
		 
	
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:生成项目登记报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月18日
	 * @param borrowVo
	 * @return
	 * String
	 */
	public String createPIReqXml(BorrowVo borrowVo){
		
		 XStream xstream = new XStream();
		 xstream = new XStream(new DomDriver());
		 Finance finance =new Finance();
		 Message message =new Message();
		 message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		 PIReq pIReq =new PIReq();
			pIReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
			pIReq.setProjectId(borrowVo.geteProjectId());
		    pIReq.setProjectName(borrowVo.getName());
			pIReq.setProjectBeginTime(DateUtils.timeStampToDate(borrowVo.getPublishTime(), DateUtils.YMD));
			pIReq.setInvestDuration(DateUtils.dayDiff( DateUtils.monthOffset(DateUtils.timeStampToDate(borrowVo.getPublishTime()), borrowVo.getTimeLimit()),DateUtils.timeStampToDate(borrowVo.getPublishTime())));//融资期限
			pIReq.setAmount(borrowVo.getAccount().multiply(new BigDecimal(100)).intValue());
			pIReq.setAdvanceRepayment("1");
			pIReq.setOverdueRepayment("1");
			pIReq.setIsTransfer("1");
			
			pIReq.setProjectEndDate(DateUtils.timeStampToDate(borrowVo.getSuccessTime(), DateUtils.YMD));
			pIReq.setInterestBeginDate(DateUtils.timeStampToDate(((Integer)(Integer.parseInt(borrowVo.getSuccessTime()))).toString(),DateUtils.YMD));
			pIReq.setInterestEndDate(DateUtils.timeStampToDate(borrowVo.getEndTime(),DateUtils.YMD));
			pIReq.setRepayDate(DateUtils.timeStampToDate(borrowVo.getEndTime(),DateUtils.YMD));
			pIReq.setRepayRate(borrowVo.getApr());
			pIReq.setRepayName(borrowVo.getRepayName());
			pIReq.setRepayAcct(borrowVo.getRepayAcct());
			pIReq.setAdvancePay("1");//默认不垫资，1垫资
			
			xstream.autodetectAnnotations(true);
	        xstream.aliasField(CGBusinessConstants.PIREQ, Message.class, "Mode");
	        xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]  
	        message.setMode(pIReq);
	        finance.setMessage(message);
		
		return xstream.toXML(finance);
		
	}
	
	
	
	/**
	 * 
	 * <p>
	 * Description:投资资金冻结解冻报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月18日
	 * @param borrowVo
	 * @return
	 * String
	 */
	public String createUFBReqXml(BorrowVo borrowVo,BaseEBankInfo baseEBankInfo,String oriSerialNo,Integer investmentAmount){
		
		 XStream xstream = new XStream();
		 xstream = new XStream(new DomDriver());
		 Finance finance =new Finance();
		 Message message =new Message();
		 message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		 UFBReq uFBReq =new UFBReq();
		 	uFBReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		 	uFBReq.setProjectId(borrowVo.geteProjectId());
		 	uFBReq.setBindSerialNo(baseEBankInfo.getBindNo());
		 	uFBReq.setOriSerialNo(oriSerialNo);
		 	uFBReq.setInvestmentAmount(investmentAmount);
		 	
			xstream.autodetectAnnotations(true);
	        xstream.aliasField(CGBusinessConstants.UFBReq, Message.class, "Mode");
	        xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]  
	        message.setMode(uFBReq);
	        finance.setMessage(message);
		
		return xstream.toXML(finance);
		
	}
	
	
	
	
	
	/**
	 * 投标校验
	 */
	public String validateTenderData(TenderBorrowCnd tenderBorrowCnd, Integer memberId) throws Exception {

		// 查询借款标并锁定
		BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
		if (null == borrowVo) {
			return "未找到此借款标！";
		}
		if (borrowVo.getUserId().equals(memberId)) {
			return "不允许投自己的标！";
		}
		if (borrowVo.getStatus() != Constants.BORROW_STATUS_TEND_CODE) {
			return "借款标剩余金额已变，请重试！";
		}

		if (borrowVo.getIsAutotender() == 1) {
			return "正在自动投标中，请稍后!";
		}

		if (borrowVo.getBidPassword() != null && !"".equals(borrowVo.getBidPassword())) {
			if (!MD5.toMD5(tenderBorrowCnd.getBidPassword()).equals(borrowVo.getBidPassword())) {
				return "定向标密码错误！";
			}
		}
		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(memberId);
		BigDecimal eUseMoney = accountvo.geteUseMoney();
	
		if (eUseMoney.compareTo(tenderBorrowCnd.getTenderMoney()) == -1) {
			return "账户余额不足，无法投标！";
		}
		BigDecimal money = this.isEffectiveMoney(borrowVo, memberId, tenderBorrowCnd.getTenderMoney(), accountvo);
		if (money.compareTo(BigDecimal.ZERO) == 0) {
			return "投标资金错误！";
		}

		return BusinessConstants.SUCCESS;
	}




	/**
	 * 判断输入金额是否有效金额
	 * 
	 * @param borrow
	 * @param user
	 * @param money
	 * @return
	 */
	public BigDecimal isEffectiveMoney(BorrowVo borrow, Integer userId, BigDecimal money, AccountVo account) {
		List<TenderRecord> list = tenderRecordMapper.selectByUserIdAndBorrowId(userId, borrow.getId());
		BigDecimal tendered = BigDecimal.ZERO;
		for (TenderRecord btr : list) {
			tendered = tendered.add(btr.getAccount());
		}
		System.out.println("list.size()=" + list.size());

		System.out.println("tendered=" + tendered);
		// 标目前所剩余额
		BigDecimal syje = borrow.getAccount().subtract(borrow.getAccountYes());
		BigDecimal eUseMoney = account.geteUseMoney();
		
		BigDecimal max = borrow.getMostAccount();
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			max = syje;
		} else {
			max = max.subtract(tendered);
		}
		BigDecimal min = borrow.getLowestAccount();
		BigDecimal inputMoney = money;

		BigDecimal returnValue = BigDecimal.ZERO;

		BigDecimal result1 = min;
		BigDecimal result2 = max;

		boolean isNoMoreMoney = false;
		if (max.compareTo(BigDecimal.ZERO) == 0) {
			result1 = BigDecimal.ZERO;
			result2 = BigDecimal.ZERO;
			isNoMoreMoney = true;
		}

		if (!isNoMoreMoney) {

			boolean isfinal = false;

			if (syje.compareTo(min) < 0) {
				isfinal = true;
			}

			if ((money.subtract(syje)).compareTo(new BigDecimal("0.01")) < 0) {
				isfinal = true;
			}

			if (result2.compareTo(BigDecimal.ZERO) < 0) {
				result2 = BigDecimal.ZERO;
				result1 = BigDecimal.ZERO;
			} else {
				if (result2.compareTo(BigDecimal.ZERO) == 0) {
					max = syje;
				}

				if (eUseMoney.compareTo(max) >= 0) {
					if (max.compareTo(syje) >= 0) {
						result2 = syje;
					} else if (max.compareTo(syje) < 0) {
						if (max.compareTo(min) < 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else {
							result2 = max;
						}
					}
				} else if (eUseMoney.compareTo(max) < 0) {
					result2 = eUseMoney;
					if (eUseMoney.compareTo(min) >= 0) {
						if (eUseMoney.compareTo(syje) >= 0) {
							result2 = syje;
						} else if (eUseMoney.compareTo(syje) < 0) {
							result2 = eUseMoney;
						}
					} else if (eUseMoney.compareTo(min) < 0) {
						if (syje.compareTo(min) >= 0) {
							result1 = BigDecimal.ZERO;
							result2 = BigDecimal.ZERO;
						} else if (syje.compareTo(min) < 0) {
							isfinal = true;
							if (eUseMoney.compareTo(syje) >= 0) {
								result1 = syje;
								result2 = syje;
							} else if (eUseMoney.compareTo(syje) < 0) {
								result1 = BigDecimal.ZERO;
								result2 = BigDecimal.ZERO;
							}
						}
					}
				}
			}

			if (isfinal) {
				if (result1.compareTo(result2) > 0) {
					result1 = result2;
				}
			} else {
				if (result1.compareTo(result2) > 0) {
					result1 = BigDecimal.ZERO;
					result2 = BigDecimal.ZERO;
				}
			}

			if (isfinal) {
				result1 = result1.setScale(2, BigDecimal.ROUND_UP);
				result2 = result2.setScale(2, BigDecimal.ROUND_UP);
			}
			System.out.println("最小=" + result1);
			System.out.println("最大=" + result2);
			if (result1.compareTo(BigDecimal.ZERO) != 0 && result2.compareTo(BigDecimal.ZERO) != 0) {
				if (inputMoney.compareTo(result1) >= 0 && inputMoney.compareTo(result2) <= 0) {
					returnValue = inputMoney;
				}
			}
		}
		return returnValue;
	}


	
	
	public Page findTenderrecordInfo(Integer borrowId, Page page) throws Exception{
		
		List<InvestBorrow> list= borrowMapper.findTenderrecordInfo(borrowId, page);
		Integer count =borrowMapper.findTenderrecordInfoCount(borrowId);
		page.setResult(list);
		page.setTotalCount(count);
		return page;
	}


	/**
	 * 获取总页数
	 */
	public Integer findTenderrecordInfoCount(Integer borrowId,Integer pageSize) throws Exception{
		Integer count= borrowMapper.findTenderrecordInfoCount(borrowId);
		Page page=new Page();
		page.setPageSize(pageSize);
		page.setTotalCount(count);
		return page.getTotalPage();
	}


	
	public Integer findCheckBorrow(Integer userId) {
		
		return tenderRecordMapper.findCheckBorrow(userId);
	}
	
	
}
