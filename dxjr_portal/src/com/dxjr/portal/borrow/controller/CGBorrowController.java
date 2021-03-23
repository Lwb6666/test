
package com.dxjr.portal.borrow.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.MailSendRecord;
import com.dxjr.base.entity.MessageRecord;
import com.dxjr.base.mapper.BaseMailSendRecordMapper;
import com.dxjr.common.custody.xml.FBRes;
import com.dxjr.common.custody.xml.PTRRes;
import com.dxjr.common.custody.xml.XmlUtil;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.controller.ToubiaoController;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.CGBorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.InvestBorrow;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.UUIDGenerator;

/**   
 * <p>
 * Description:存管标<br />
 * </p>
 * @title CGBorrowController.java
 * @package com.dxjr.portal.borrow.controller 
 * @author tanghaitao
 * @version 0.1 2016年5月21日
 */
@Controller
@RequestMapping(value="/CGBorrow")
public class CGBorrowController extends BaseController{

	public Logger logger = Logger.getLogger(ToubiaoController.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private TransferService transferService;
    @Autowired
    private TendRecordService tendRecordService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private CGUtilService cGUtilService;
    @Autowired
    private CGBorrowService cGBorrowService;
    @Autowired
    private BankInfoService bankInfoService;
	@Autowired
	private BaseMailSendRecordMapper baseMailSendRecordMapper;
	
	
    
    /**
     * 
     * <p>
     * Description:发送短信<br />
     * </p>
     * @author tanghaitao
     * @version 0.1 2016年5月21日
     * @param request
     * @param tenderBorrowCnd
     * @return
     * MessageBox
     */
	@RequestMapping(value="/sendMobileCode")
	@ResponseBody
	public MessageBox sendMobileCode(HttpServletRequest request,TenderBorrowCnd tenderBorrowCnd){
		MessageBox msg = null;
		try {
			ShiroUser shiroUser = currentUser();
			msg=this.investCheck(tenderBorrowCnd, request);
			//如果校验通过，则调用短信接口
			if(msg.getCode().equals("1")){
				String remark="投标短信发送";
				String relateNo=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
				//接口调用
				String res= cGUtilService.saveMobileCode(shiroUser, remark, relateNo,tenderBorrowCnd.getBorrowid());
				//接口响应
				String mode="13";//场景 13:短信发送
				String mg= cGUtilService.saveResXml(res, mode, shiroUser, remark, relateNo,tenderBorrowCnd.getBorrowid());
				if(!mg.equals(BusinessConstants.SUCCESS)){
					logger.error(mg);
					return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
				}
				msg=new MessageBox("1", "短信发送成功");
			}
		} catch (Exception e) {
			logger.error("短信发送失败", e);
			return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
		}
		
		return msg;
		
	}
	
	
	
	/**
	 * 
	 * <p>
	 * Description:手动投存管标<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param request
	 * @param tenderBorrowCnd
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value="/tenderCGBorrow")
	@ResponseBody
	public MessageBox tenderCGBorrow(HttpServletRequest request, TenderBorrowCnd tenderBorrowCnd) {
		MessageBox msg = null;
		try {
			ShiroUser shiroUser = currentUser();
			//投资前校验
			msg=this.investCheck(tenderBorrowCnd, request);
			if(msg.getCode().equals("1")){
				if(tenderBorrowCnd.getMobileCode()==null || tenderBorrowCnd.getMobileCode().length()<=0){
					return MessageBox.build("0", "请输入短信验证码");
				}
				//校验投标金额
				String msg2= cGBorrowService.validateTenderData(tenderBorrowCnd, shiroUser.getUserId());
				if(!msg2.equals(BusinessConstants.SUCCESS)){
					return MessageBox.build("0", msg2);
				}
					String relateNo=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
					String remark="投标余额查询";
					//调用存管余额查询接口
					String rep= cGUtilService.saveCGAccount(shiroUser, relateNo,remark,tenderBorrowCnd.getBorrowid());
					//解析报文
					Account account= cGUtilService.parseAQReqXml(rep, shiroUser, remark, relateNo,tenderBorrowCnd.getBorrowid());
					if(account==null){
						logger.error("返回ERROR报文或验签失败");
						return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
					}
					
					//平台与存管资金校验
					String scene="投标";//业务发生场景
					account.setUserId(shiroUser.getUserId());
					String mg= cGUtilService.savecheckAccount(account, HttpTookit.getRealIpAddr(request), shiroUser.getPlatform(), scene);
					/*if(!mg.equals("success")){
						return MessageBox.build("0", "您的账户异常，请联系客服");
					}*/
					if(account.getEUseMoney().compareTo(tenderBorrowCnd.getTenderMoney())==-1){
						return MessageBox.build("0", "您的账户可用余额不足");
					}
					//调用银行资金冻结接口
					String mode="8";//场景  资金冻结
					String relateNoFBReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
					String remarkFBReq="投标资金冻结";
					String p2pDJ= UUIDGenerator.generate(CGBusinessConstants.P2P_SERIAL_DJ);
					String resXml= cGUtilService.saveFBReq(shiroUser, relateNoFBReq, remarkFBReq, tenderBorrowCnd, mode,p2pDJ);
					//解析报文
					FBRes fBRes = cGUtilService.parseFBResXml(resXml, shiroUser, remarkFBReq, relateNoFBReq, mode,tenderBorrowCnd);
					if(fBRes==null){
						logger.error("返回ERROE报文或验签失败");
						return MessageBox.build("0", "投标失败");
					}
					
					//添加投标记录
					String ms = null;
					try {
						ms= cGBorrowService.tenderCGBorrow(fBRes, tenderBorrowCnd, shiroUser, HttpTookit.getRealIpAddr(request),p2pDJ);
					} catch (Exception e) {
						logger.error(e);
						//调用资金解冻接口
						BorrowVo borrowVo = borrowService.selectByPrimaryKey(tenderBorrowCnd.getBorrowid());
						String modeUFBReq="10";//场景  资金解冻
						String relateNoUFBReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
						String remarkUFBReq="投标资金冻结解冻";
						Integer investmentAmount=tenderBorrowCnd.getTenderMoney().multiply(new BigDecimal(100)).intValue();
						String repXml= cGBorrowService.saveUFBReq(borrowVo, fBRes.getSerialNo(), investmentAmount, shiroUser, remarkUFBReq, modeUFBReq, relateNoUFBReq);
						String uFBResMsg= cGUtilService.parseUFBResXml(repXml, shiroUser, remarkUFBReq, relateNoUFBReq, modeUFBReq, tenderBorrowCnd, fBRes.getSerialNo());
						if(!uFBResMsg.equals(BusinessConstants.SUCCESS)){
							logger.error("投标资金冻结解冻异常");
							return MessageBox.build("0", "投标失败，投标资金冻结解冻异常");
						}
						return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
						
					}
					
					//投标成功
					if(BusinessConstants.SUCCESS.equals(ms)){
						return MessageBox.build("1", "投标成功");
					//满标复审中	
					}else if(ms.equals("checkBorrow")){
						//满标复审校验
						BorrowVo borrowVo = borrowService.selectByPrimaryKey(tenderBorrowCnd.getBorrowid());
						if (!borrowVo.getStatus().equals(Constants.BORROW_STATUS_SUCCESS_CODE) || borrowVo.getAccount().compareTo(borrowVo.getAccountYes()) != 0) {
							return	MessageBox.build("0", "借款标异常,借款标状态不是满标复审中");
						}
						// 查询投标记录已投总金额
						BigDecimal tenderTotal = tendRecordService.getTenderAccountYesByBorrowId(tenderBorrowCnd.getBorrowid());
						if (null == tenderTotal || tenderTotal.compareTo(borrowVo.getAccount()) != 0) {
							return	MessageBox.build("0", "借款标异常,借款标金额与投标金额不相等");
						}
						
						//调用银行项目登记接口，完善项目登记信息
						String modePIReq="6.2";//场景  6(项目基本信息登记)
						String relateNoPIReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
						String remarkPIReq="项目第二次登记完善";
						String resXmlPIReq= cGBorrowService.savePIReq(borrowVo, shiroUser, modePIReq, remarkPIReq, relateNoPIReq);
						//记录银行项目登记接口响应日志
						String m= cGUtilService.saveResXml(resXmlPIReq, modePIReq, shiroUser, remarkPIReq, relateNoPIReq,borrowVo.getId());
						if(!m.equals(BusinessConstants.SUCCESS)){
							logger.error(m);
							return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
						}
						/**调用银行项目投资信息登记服务**/
						Integer pagetotal= cGBorrowService.findTenderrecordInfoCount(tenderBorrowCnd.getBorrowid(), 200);
						for(int i=1;i<=pagetotal;i++){
							//根据借款标ID查询投标记录及投资人实名信息（分页查询，pageSize=200）
							Page page= cGBorrowService.findTenderrecordInfo(tenderBorrowCnd.getBorrowid(), new Page(i, 200));
							//调用项目投资信息登记接口
							String modePTRReq="9";//场景  9项目投资信息登记
							String relateNoPTRReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
							String remarkPTRReq="项目投资信息登记";
							List<InvestBorrow> list=(List<InvestBorrow>) page.getResult();
							String repXml= cGBorrowService.savePTRReq(borrowVo, list, shiroUser, remarkPTRReq, modePTRReq, relateNoPTRReq);
							// 判断响应报文
							boolean isError = XmlUtil.checkXml(repXml);
							if (isError) {
								Map<String, Object> maperr = XmlUtil.toError(repXml);
								String errorCode = (String) maperr.get("errorCode");
								String errorDetail = (String) maperr.get("errorDetail");
								String err = "银行响应异常：[" + errorCode + "]" + errorDetail;

								// 新增邮件待发记录
								MailSendRecord mailSendRecord = new MailSendRecord();
								mailSendRecord.setTypeId(tenderBorrowCnd.getBorrowid());
								mailSendRecord.setType(15);// 项目投资登记银行响应异常
								mailSendRecord.setStatus(0);
								mailSendRecord.setAddtime(new Date());
								baseMailSendRecordMapper.insertEntity(mailSendRecord);

								return MessageBox.build("0", err);
							}
							//记录项目投资信息登记响应日志,并解析
							PTRRes pTRRes=cGBorrowService.parsePTRResXml(repXml, modePTRReq, shiroUser, remarkPTRReq, relateNoPTRReq,borrowVo.getId());
							if(pTRRes==null){
								logger.error("项目投资信息登记失败");
								return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
							}
							
							cGBorrowService.updateTenderrecord(borrowVo, pTRRes,list);
						}
							//将标的状态更新为银行复审中
							cGBorrowService.updateBorrowStatus(borrowVo);
						
						return MessageBox.build("1", "投标成功");
						
					//投标失败	
					}else{
						return MessageBox.build("0", ms);
					}
				
					
					
			}
			
		} catch (Exception e) {
			logger.error("投标失败", e);
			return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
		}
		
		return msg;
		
	} 
	
	
	
	/**
	 * 
	 * <p>
	 * Description:投标前校验<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param tenderBorrowCnd
	 * @param request
	 * @return
	 * MessageBox
	 */
	public MessageBox investCheck(TenderBorrowCnd tenderBorrowCnd, HttpServletRequest request){
		try {
			ShiroUser shiroUser = currentUser();
			// 判断当前用户是否能投标
			String result = this.judgTender();
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			
			//判断该用户是否是存管账户
			// 查询用户信息
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			if(memberVo.getIsCustody()!=1){
				return MessageBox.build("0", "您未开通存管账户");
			}
			
			//校验该用户账户是否异常
			/*Integer count= cGUtilService.findAccountErrorByUserIdCount(shiroUser.getUserId());
			if(count>0){
				return MessageBox.build("0", "您的存管账户异常，请联系客服");
			}*/
			
			
			// 验证投标数据是否正确
			result = this.validateManualTenderData(shiroUser, tenderBorrowCnd, request);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_BORROW)) {
				return new MessageBox("0", "网络连接异常，请稍后再试!");
			}
			// 增加md5判断，用于检验是否是从本页面进入
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(tenderBorrowCnd.getBorrowid());
			
			//校验是否是存管标
			if(borrowVo.getIsCustody()!=1){
				return MessageBox.build("0", "该借款标不是存管标");
			}
			
			Integer sysSecond= (int) (Integer.parseInt(borrowVo.getPublishTime())+borrowVo.getValidTime()*24*60*60-(new Date().getTime()/1000L));
			if(sysSecond<=0){
				return MessageBox.build("0", "已过投资时间，无法投资");
			}
			
			if (borrowVo.getBorrowtype() == 3) { // 净值标
				// 2015-06-01 所有用户不允许发起净值
				String time0601Str = "2015-06-01";
				Date date0601 = DateUtils.parse(time0601Str, "yyyy-MM-dd");
				long time0601Long = date0601.getTime();
				if (time0601Long <= new Date().getTime()) {
					return MessageBox.build("0", "2015年6月1日起所有用户不能投净值标！");
				}
			}


			if (null == borrowVo || null == request.getParameter(borrowVo.getUuid())
					|| !request.getParameter(borrowVo.getUuid()).toString().equals(borrowVo.getUuid())) {
				return MessageBox.build("0", "数据不正确,请确认！");
			}
			// 查询投标记录已投总金额
			BigDecimal tenderAccountYes = tendRecordService.getTenderAccountYesByBorrowId(tenderBorrowCnd.getBorrowid());
			if (tenderAccountYes != null) {
				if (borrowVo.getAccountYes().compareTo(tenderAccountYes) != 0) {
					return MessageBox.build("0", "该标已投金额错误，请联系客服！");
				}
			} else {
				if (borrowVo.getAccountYes().compareTo(new BigDecimal(0)) == 1) {
					return MessageBox.build("0", "该标已投金额错误，请联系客服！");
				}
			}
			if (borrowVo.getAreaType() != null && borrowVo.getAreaType() == 1) { // 新手标
				if (tendRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0) {
					return MessageBox.build("0", "您不是新手，无法投新手标！");
				}
				if (transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
					return MessageBox.build("0", "您不是新手，无法投新手标！");
				}
			}
			
			//校验该用户是否有投银行审核中的标
		/*	Integer checkBorrowCount= cGBorrowService.findCheckBorrow(shiroUser.getUserId());
			if(checkBorrowCount>0){
				return MessageBox.build("0", "您有所投的标正在银行审核中,审核期间无法投新标");
			}*/
			
			
			
		}catch (Exception e) {
				logger.error(e);
				return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
			}

			return MessageBox.build("1", "success");
		
	}
	
		
		/**
		 * <p>
		 * Description:判断当前用户是否能投标<br />
		 * </p>
		 * 
		 * @author justin.xu
		 * @version 0.1 2014年10月29日
		 * @param request
		 * @return
		 * @throws Exception
		 *             String
		 */
		private String judgTender() throws Exception {
			String result = BusinessConstants.SUCCESS;
			if (!isLogin()) {
				return ("请先登录");
			}

			if (!hasRole("invest")) {
				return ("您是借款用户,不能进行此操作");
			}

			ShiroUser shiroUser = currentUser();
			// 查询用户认证信息
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			// 查询用户信息
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			if ((memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				return ("请先进行手机认证！");
			}

			// 检查实名认证
			if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
				return ("请先进行实名认证");
			}

			if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
				return ("您还没有设置交易密码，请先设置");
			}

			// 检查是否绑定了银行卡
			BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
			if (null == bankInfoVo) {
				return "请先绑定银行卡!";
			}
			
			return result;
		}
		
		
		
		/**
		 * <p>
		 * Description:验证手动投标数据是否正确<br />
		 * </p>
		 * 
		 * @author justin.xu
		 * @version 0.1 2014年8月14日
		 * @return
		 * @throws Exception
		 *             String
		 */
		private String validateManualTenderData(ShiroUser shiroUser, TenderBorrowCnd tenderBorrowCnd, HttpServletRequest request) throws Exception {
			String result = BusinessConstants.SUCCESS;
			if (shiroUser.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
				return "借款用户不允许投标！";
			}
			if (null == tenderBorrowCnd.getBorrowid()) {
				return "参数异常！";
			}
			if (null == tenderBorrowCnd.getTenderMoney()) {
				return "投标金额不能为空！";
			}

			// 股权众筹标只能是100的整数倍
			if (tenderBorrowCnd.getBorrowid().intValue() == 77345) {
				if (tenderBorrowCnd.getTenderMoney().doubleValue() % 100 != 0) {
					return "投标金额必须是100的整数倍";
				}
			}
			
			return result;
		}
		
		
		/**
		 * 
		 * <p>
		 * Description:后台复审<br />
		 * </p>
		 * @author tanghaitao
		 * @version 0.1 2016年6月7日
		 * @param tenderBorrowCnd
		 * @param shiroUser
		 * @return
		 * Map<String,Object>
		 */
		@RequestMapping(value = "/CGcheckBorrowReCheck")
		@ResponseBody
		public Map<String, Object> CGcheckBorrowReCheck(Integer borrowId,Integer userId,Integer platform) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				ShiroUser shiroUser=new ShiroUser();
				shiroUser.setUserId(userId);
				shiroUser.setPlatform(platform);
				MessageRecord messageRecord=new MessageRecord();
				messageRecord.setType((byte) 2);
				messageRecord.setMode("6.2");//场景  6.2(项目第二次基本信息登记)
				messageRecord.setOrderNo(borrowId.toString());
				List<MessageRecord> messageRecordList=cGUtilService.findMessageRecord(messageRecord);
				
				BorrowVo borrowVo = borrowService.selectByPrimaryKey(borrowId);
				//满标复审校验
				if (!borrowVo.getStatus().equals(Constants.BORROW_STATUS_SUCCESS_CODE) || borrowVo.getAccount().compareTo(borrowVo.getAccountYes()) != 0) {
					map.put("code", "借款标异常,借款标状态不是满标复审中");
					return	map;
				}
				// 查询投标记录已投总金额
				BigDecimal tenderTotal = tendRecordService.getTenderAccountYesByBorrowId(borrowId);
				if (null == tenderTotal || tenderTotal.compareTo(borrowVo.getAccount()) != 0) {
					map.put("code", "借款标异常,借款标金额与投标金额不相等");
					return	map;
				}
			if(messageRecordList.size()<=0){
				//调用银行项目登记接口，完善项目登记信息
				String modePIReq="6.2";//场景  6(项目基本信息登记)
				String relateNoPIReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
				String remarkPIReq="后台复审，项目第二次登记完善";
				String resXmlPIReq= cGBorrowService.savePIReq(borrowVo, shiroUser, modePIReq, remarkPIReq, relateNoPIReq);
				//记录银行项目登记接口响应日志
				String m= cGUtilService.saveResXml(resXmlPIReq, modePIReq, shiroUser, remarkPIReq, relateNoPIReq,borrowVo.getId());
				if(!m.equals(BusinessConstants.SUCCESS)){
					logger.error(m);
					map.put("code", "系统繁忙,请刷新页面或稍后重试！");
				}
			}	
			
			/**判断是否已投资登记,并根据响应报文更新投标记录**/
			MessageRecord mr=new MessageRecord();
			mr.setType((byte) 2);
			mr.setMode("9");//场景  9项目投资信息登记
			mr.setOrderNo(borrowId.toString());
			List<MessageRecord> mrList=cGUtilService.findMessageRecord(mr);
			for (MessageRecord messageRecord2 : mrList) {
				PTRRes pTRRes=cGBorrowService.reviewParsePTRResXml(messageRecord2.getMsg());
				//根据响应记录更新投标记录信息
				cGBorrowService.reviewUpdateTenderrecord(borrowVo, pTRRes);
			}	
				/**调用银行项目投资信息登记服务**/
				Integer pagetotal= cGBorrowService.findTenderrecordInfoCount(borrowId, 200);
				for(int i=1;i<=pagetotal;i++){
					//根据借款标ID查询投标记录及投资人实名信息（分页查询，pageSize=200）
					Page page= cGBorrowService.findTenderrecordInfo(borrowId, new Page(i, 200));
					//调用项目投资信息登记接口
					String modePTRReq="9";//场景  9项目投资信息登记
					String relateNoPTRReq=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
					String remarkPTRReq="项目投资信息登记";
					List<InvestBorrow> list=(List<InvestBorrow>) page.getResult();
					String repXml= cGBorrowService.savePTRReq(borrowVo, list, shiroUser, remarkPTRReq, modePTRReq, relateNoPTRReq);
					// 判断响应报文
					boolean isError = XmlUtil.checkXml(repXml);
					if (isError) {
						Map<String, Object> maperr = XmlUtil.toError(repXml);
						String errorCode = (String) maperr.get("errorCode");
						String errorDetail = (String) maperr.get("errorDetail");
						String err = "银行响应异常：[" + errorCode + "]" + errorDetail;
						 map.put("code",err);

						// 新增邮件待发记录
						MailSendRecord mailSendRecord = new MailSendRecord();
						mailSendRecord.setTypeId(borrowId);
						mailSendRecord.setType(15);// 项目投资登记银行响应异常
						mailSendRecord.setStatus(0);
						mailSendRecord.setAddtime(new Date());
						baseMailSendRecordMapper.insertEntity(mailSendRecord);

						return map;
					}
					//记录项目投资信息登记响应日志,并解析
					PTRRes pTRRes=cGBorrowService.parsePTRResXml(repXml, modePTRReq, shiroUser, remarkPTRReq, relateNoPTRReq,borrowVo.getId());
					if(pTRRes==null){
						logger.error("项目投资信息登记失败");
						map.put("code", "系统繁忙,请刷新页面或稍后重试！");
						return map;

					}
					//根据响应记录更新投标记录信息
					cGBorrowService.updateTenderrecord(borrowVo, pTRRes,list);
				}
				//将标的状态更新为银行复审中
				cGBorrowService.updateBorrowStatus(borrowVo);
				
				map.put("code", BusinessConstants.SUCCESS);
			
			} catch (Exception e) {
				map.put("code", "系统繁忙,请刷新页面或稍后重试！");
			}
			
			return map;
		}
		
}
