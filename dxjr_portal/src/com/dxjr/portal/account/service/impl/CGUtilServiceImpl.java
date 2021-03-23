/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGUtilServiceImpl.java
 * @package com.dxjr.portal.account.service.impl 
 * @author tanghaitao
 * @version 0.1 2016年5月19日
 */
package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountError;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.entity.MailSendRecord;
import com.dxjr.base.entity.MessageRecord;
import com.dxjr.base.mapper.AccountErrorMapper;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.base.mapper.BaseMailSendRecordMapper;
import com.dxjr.base.mapper.MessageRecordMapper;
import com.dxjr.common.custody.xml.ABSReq;
import com.dxjr.common.custody.xml.AQReq;
import com.dxjr.common.custody.xml.FBReq;
import com.dxjr.common.custody.xml.FBRes;
import com.dxjr.common.custody.xml.Finance;
import com.dxjr.common.custody.xml.Message;
import com.dxjr.common.custody.xml.SSReq;
import com.dxjr.common.custody.xml.UFBRes;
import com.dxjr.common.custody.xml.XmlUtil;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.entity.BorrowerDealError;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.BorrowerDealErrorMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.UUIDGenerator;
import com.dxjr.utils.exception.AppException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title CGUtilServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author tanghaitao
 * @version 0.1 2016年5月19日
 */

@Service
@Transactional
public class CGUtilServiceImpl implements CGUtilService {

	public Logger logger = Logger.getLogger(CGUtilServiceImpl.class);

	@Autowired
	private BaseEBankInfoMapper baseEBankInfoMapper;
	@Autowired
	private MessageRecordMapper messageRecordMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;
	@Autowired
	private AccountErrorMapper accountErrorMapper;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private BorrowMapper borrowMapper;
	@Autowired
	private BaseMailSendRecordMapper baseMailSendRecordMapper;
	@Autowired
	private BorrowerDealErrorMapper borrowerDealErrorMapper;

	/**
	 * <p>
	 * Description:查询存管账户余额<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @param userId
	 * @return Account
	 */
	public String saveCGAccount(ShiroUser shiroUser, String relateNo, String remark, Integer borrowId) throws Exception {
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());

		String reqMessage = this.createPIReqXml(baseEBankInfo);
		System.out.println(reqMessage);
		String reqXml = XmlUtil.sign(reqMessage, CGBusinessConstants.AQReq);
		// 余额接口调用
		String rep = XmlUtil.send(reqXml);
		// 记录项目登记日志
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setMode("5");// 场景 5:余额查询
		messageRecord.setType((byte) 1);// 1:主动，2:被动
		messageRecord.setMsg(reqXml);// 报文体
		messageRecord.setOrderNo(borrowId.toString());
		messageRecord.setOptUserid(shiroUser.getUserId());
		messageRecord.setPlatform(shiroUser.getPlatform());
		messageRecord.setRemark(remark);
		messageRecord.setRelateNo(relateNo);// 调用关联号
		messageRecord.setBindNo(baseEBankInfo.getBindNo());
		messageRecordMapper.insert(messageRecord);
		return rep;

	}

	/**
	 * <p>
	 * Description:调用资金冻结接口<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param shiroUser
	 * @param relateNo
	 * @param remark
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveFBReq(ShiroUser shiroUser, String relateNo, String remark, TenderBorrowCnd tenderBorrowCnd, String mode, String p2pDJ) throws Exception {
		// 查询借款标并锁定
		BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
		BigDecimal syAccount = borrowVo.getAccount().subtract(borrowVo.getAdvance());
		if (tenderBorrowCnd.getTenderMoney().compareTo(syAccount) == 1) {
			throw new AppException("剩余投标金额不足");
		}
		BorrowVo borrow = new BorrowVo();
		borrow.setId(borrowVo.getId());
		borrow.setAdvance(borrowVo.getAdvance().add(tenderBorrowCnd.getTenderMoney()));
		borrowMapper.updateBorrow(borrow);
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		String reqMessage = this.createFBReq(baseEBankInfo, borrowVo, tenderBorrowCnd, p2pDJ);
		System.out.println(reqMessage);
		String reqXml = XmlUtil.sign(reqMessage, CGBusinessConstants.FBReq);
		// 资金冻结接口调用
		String rep = XmlUtil.send(reqXml);
		this.insertMsg(reqXml, mode, 1, shiroUser, remark, relateNo, tenderBorrowCnd.getBorrowid());

		return rep;

	}

	/**
	 * <p>
	 * Description:解析余额报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @return Account
	 */
	public Account parseAQReqXml(String repXml, ShiroUser shiroUser, String remark, String relateNo, Integer borrowId) throws Exception {
		// 记录响应报文
		this.insertMsg(repXml, "5", 2, shiroUser, remark, relateNo, borrowId);
		// 判断响应报文
		boolean isError = XmlUtil.checkXml(repXml);
		if (isError) {
			logger.error("响应ERROR报文:" + repXml);
		}
		// 验签
		boolean istrue = XmlUtil.checkYq(repXml);
		if (!istrue) {
			logger.error("验签失败");
		}
		if (!isError && istrue) {
			Account account = new Account();
			Map<String, Object> map = XmlUtil.toBiz(repXml, CGBusinessConstants.AQRes);
			account.setEUseMoney(new BigDecimal((String) map.get("totalAmount")).divide(new BigDecimal(100)));
			account.setEFreezeMoney(new BigDecimal((String) map.get("freezeAmout")).divide(new BigDecimal(100)));
			account.setZsWithdrawamount(new BigDecimal((String) map.get("withdrawAmount")).divide(new BigDecimal(100)));
			return account;
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * Description:解析投资资金冻结响应报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param repXml
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @param mode
	 * @return
	 * @throws Exception
	 *             FBRes
	 */
	public FBRes parseFBResXml(String repXml, ShiroUser shiroUser, String remark, String relateNo, String mode, TenderBorrowCnd tenderBorrowCnd)
			throws Exception {
		try {
			String msg = this.saveResXml(repXml, mode, shiroUser, remark, relateNo, tenderBorrowCnd.getBorrowid());
			if (!msg.equals(BusinessConstants.SUCCESS)) {
				// 查询借款标并锁定
				BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
				BorrowVo borrow = new BorrowVo();
				borrow.setId(borrowVo.getId());
				borrow.setAdvance(borrowVo.getAdvance().subtract(tenderBorrowCnd.getTenderMoney()));
				borrowMapper.updateBorrowAdvance(borrow);
				return null;
			}
			FBRes fBRes = new FBRes();
			Map<String, Object> map = XmlUtil.toBiz(repXml, CGBusinessConstants.FBRes);
			fBRes.setSerialNo((String) map.get("serialNo"));
			fBRes.setBlockStatus((String) map.get("BlockStatus"));
			fBRes.setInstSettleDate((String) map.get("instSettleDate"));
			return fBRes;
		} catch (Exception e) {
			logger.error(repXml);
			logger.error(e);
			// 查询借款标并锁定
			BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
			BorrowVo borrow = new BorrowVo();
			borrow.setId(borrowVo.getId());
			borrow.setAdvance(borrowVo.getAdvance().subtract(tenderBorrowCnd.getTenderMoney()));
			borrowMapper.updateBorrowAdvance(borrow);
			return null;
		}

	}

	/**
	 * <p>
	 * Description:投资冻结解冻<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年6月7日
	 * @param repXml
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @param mode
	 * @param tenderBorrowCnd
	 * @param oriSerialNo
	 * @return
	 * @throws Exception
	 *             UFBRes
	 */
	public String parseUFBResXml(String repXml, ShiroUser shiroUser, String remark, String relateNo, String mode, TenderBorrowCnd tenderBorrowCnd,
			String oriSerialNo) throws Exception {

		String msg = this.saveResXml(repXml, mode, shiroUser, remark, relateNo, tenderBorrowCnd.getBorrowid());
		if (!msg.equals(BusinessConstants.SUCCESS)) {
			// 新增邮件待发记录
			// 添加异常记录，并邮件和短信通知相关人员，后台处理
			BorrowerDealError borrowerDealError = new BorrowerDealError();
			borrowerDealError.setStatus(2);// 投标冻结解冻
			borrowerDealError.setBorrowId(tenderBorrowCnd.getBorrowid());
			//borrowerDealError.setSerialno(uFBRes.getSerialNo());
			borrowerDealError.setAccount(tenderBorrowCnd.getTenderMoney());
			borrowerDealError.setAdduser(-2);
			borrowerDealError.setDisposeStatus(0);// 未处理
			borrowerDealError.setUserId(shiroUser.getUserId());
			borrowerDealError.setOriSerialNo(oriSerialNo);
			borrowerDealError.setRemark("投标冻结");
			borrowerDealErrorMapper.insert(borrowerDealError);
			
			MailSendRecord mailSendRecord = new MailSendRecord();
			mailSendRecord.setTypeId(tenderBorrowCnd.getBorrowid());
			mailSendRecord.setType(10);// 投标冻结解冻失败
			mailSendRecord.setStatus(0);
			mailSendRecord.setAddtime(new Date());
			baseMailSendRecordMapper.insertEntity(mailSendRecord);
			return null;
		}
		try {

			UFBRes uFBRes = new UFBRes();
			Map<String, Object> map = XmlUtil.toBiz(repXml, CGBusinessConstants.UFBRes);
			uFBRes.setUnfreezeStatus((String) map.get("unfreezeStatus"));
			uFBRes.setSerialNo((String) map.get("serialNo"));
			// 解冻成功
			if (uFBRes.getUnfreezeStatus().equals("20")) {
				try {
					// 投资失败，冻结总金额减去投资金额
					BorrowVo borrowVo = borrowService.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
					BorrowVo borrow = new BorrowVo();
					borrow.setId(borrowVo.getId());
					borrow.setAdvance(borrowVo.getAdvance().subtract(tenderBorrowCnd.getTenderMoney()));
					borrowMapper.updateBorrowAdvance(borrow);
				} catch (Exception e) {
					logger.error("投资失败，冻结总金额减去投资金额操作异常", e);
					return "冻结总金额减去投资金额操作异常";
				}

			} else {
				// 记录解冻失败记录。后台手动解冻
				// 添加异常记录，并邮件和短信通知相关人员，后台处理
				BorrowerDealError borrowerDealError = new BorrowerDealError();
				borrowerDealError.setStatus(2);// 投标冻结解冻
				borrowerDealError.setBorrowId(tenderBorrowCnd.getBorrowid());
				borrowerDealError.setSerialno(uFBRes.getSerialNo());
				borrowerDealError.setAccount(tenderBorrowCnd.getTenderMoney());
				borrowerDealError.setAdduser(-2);
				borrowerDealError.setDisposeStatus(0);// 未处理
				borrowerDealError.setUserId(shiroUser.getUserId());
				borrowerDealError.setOriSerialNo(oriSerialNo);
				borrowerDealError.setRemark("投标冻结");
				borrowerDealErrorMapper.insert(borrowerDealError);

				// 新增邮件待发记录
				MailSendRecord mailSendRecord = new MailSendRecord();
				mailSendRecord.setTypeId(tenderBorrowCnd.getBorrowid());
				mailSendRecord.setType(10);// 投标冻结解冻失败
				mailSendRecord.setStatus(0);
				mailSendRecord.setAddtime(new Date());
				baseMailSendRecordMapper.insertEntity(mailSendRecord);
			}

		} catch (Exception e) {
			logger.error("投资冻结解冻操作异常", e);
			return "投资冻结解冻操作异常";
		}

		return BusinessConstants.SUCCESS;

	}

	/**
	 * <p>
	 * Description:对比用户在平台与存管账户<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @param account
	 *            void
	 */
	public String savecheckAccount(Account account, String addip, Integer platform, String scene) throws Exception {
		String msg = "success";

		AccountVo accountVo = accountMapper.findAccountByUserIdForUpdate(account.getUserId());

		MemberVo memberVo = memberMapper.queryPasswordInfoById(account.getUserId());
		// E户
		if (memberVo.getEType() == 1) {
			if (accountVo.geteUseMoney().compareTo(account.getEUseMoney()) != 0 || accountVo.geteFreezeMoney().compareTo(account.getEFreezeMoney()) != 0) {
				List<AccountError> list = accountErrorMapper.findAccountErrorByUserId(account.getUserId());
				if (list.size() > 0) {
					AccountError ae = list.get(0);
					// 判断账户异常未处理的记录与存管金额对比
					if (ae.getZsEUseMoney().compareTo(account.getEUseMoney()) != 0 || ae.getZsEFreezeMoney().compareTo(account.getEFreezeMoney()) != 0
							|| ae.getZsWithdrawamount().compareTo(account.getZsWithdrawamount()) != 0) {
						// 邮件提醒相关人员
						// 新增邮件待发记录
						/*
						 * MailSendRecord mailSendRecord=new MailSendRecord(); mailSendRecord.setTypeId(account.getUserId()); mailSendRecord.setType(9);//平台与存管账户资金不等 mailSendRecord.setStatus(0);
						 * mailSendRecord.setAddtime(new Date()); baseMailSendRecordMapper.insertEntity(mailSendRecord);
						 */

						// 记录账户异常信息日志
						accountErrorMapper.insert(this.beanAccountError(accountVo, account, addip, platform, scene));
					}
				} else {
					// 新增邮件待发记录
					/*
					 * MailSendRecord mailSendRecord=new MailSendRecord(); mailSendRecord.setTypeId(account.getUserId()); mailSendRecord.setType(9);//平台与存管账户资金不等 mailSendRecord.setStatus(0);
					 * mailSendRecord.setAddtime(new Date()); baseMailSendRecordMapper.insertEntity(mailSendRecord);
					 */
					// 记录账户异常信息日志
					accountErrorMapper.insert(this.beanAccountError(accountVo, account, addip, platform, scene));
				}
				// return "账户异常，请联系客服!";
			}

		} else if (memberVo.getEType() == 2) {// 商卡
			// 以存管账户为主，更新平台账户
			if (accountVo.geteUseMoney().compareTo(account.getEUseMoney()) != 0) {
				BigDecimal money = account.getEUseMoney().subtract(accountVo.geteUseMoney());
				Account at = new Account();
				at.setId(accountVo.getId());
				at.setEUseMoney(account.getEUseMoney());
				at.setTotal(accountVo.getTotal().add(money));
				baseAccountMapper.updateEntity(at);
				// 记录账户日志
				AccountLog accountLog = new AccountLog();
				accountLog.setUserId(account.getUserId());
				accountLog.setType(CGBusinessConstants.SK_SYN);
				accountLog.setMoney(new BigDecimal(Math.abs(money.doubleValue())));
				accountLog.setToUser(-2);// -2:存管系统
				accountLog.setIdType(20);// 存管系统
				accountLog.setRemark("与存管系统商卡可用同步");
				accountLog.setAddip(addip);
				accountLog.setIsCustody(memberVo.getIsCustody());
				baseAccountLogMapper.insertAccountLog(accountLog);

			}

			if (accountVo.geteFreezeMoney().compareTo(account.getEFreezeMoney()) != 0) {
				BigDecimal money = account.getEFreezeMoney().subtract(accountVo.geteFreezeMoney());
				Account at = new Account();
				at.setId(accountVo.getId());
				at.setEFreezeMoney(account.getEFreezeMoney());
				at.setTotal(accountVo.getTotal().add(money));
				baseAccountMapper.updateEntity(at);
				// 记录账户日志
				AccountLog accountLog = new AccountLog();
				accountLog.setUserId(account.getUserId());
				accountLog.setType(CGBusinessConstants.SK_SYN);
				accountLog.setMoney(new BigDecimal(Math.abs(money.doubleValue())));
				accountLog.setToUser(-2);// -2:存管系统
				accountLog.setIdType(21);// 存管系统
				accountLog.setRemark("与存管系统商卡冻结同步");
				accountLog.setAddip(addip);
				accountLog.setIsCustody(memberVo.getIsCustody());
				baseAccountLogMapper.insertAccountLog(accountLog);
			}

		}
		return msg;

	}

	/**
	 * <p>
	 * Description:组建余额查询报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @param baseEBankInfo
	 * @return String
	 */
	public String createPIReqXml(BaseEBankInfo baseEBankInfo) {

		XStream xstream = new XStream();
		xstream = new XStream(new DomDriver());
		Finance finance = new Finance();
		Message message = new Message();
		message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		AQReq aQReq = new AQReq();
		aQReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		aQReq.setBindSerialNo(baseEBankInfo.getBindNo());
		aQReq.setAccNo(baseEBankInfo.getEcardNo());
		xstream.autodetectAnnotations(true);
		xstream.aliasField(CGBusinessConstants.AQReq, Message.class, "Mode");
		xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]
		message.setMode(aQReq);
		finance.setMessage(message);

		return xstream.toXML(finance);

	}

	/**
	 * <p>
	 * Description:组建资金冻结报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param baseEBankInfo
	 * @return String
	 */
	public String createFBReq(BaseEBankInfo baseEBankInfo, BorrowVo borrowVo, TenderBorrowCnd tenderBorrowCnd, String p2pDJ) {
		XStream xstream = new XStream();
		xstream = new XStream(new DomDriver());
		Finance finance = new Finance();
		Message message = new Message();
		message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		FBReq fBReq = new FBReq();
		fBReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		fBReq.setProjectId(borrowVo.geteProjectId());
		fBReq.setProjectName(borrowVo.getName());
		fBReq.setRealname(baseEBankInfo.getRealname());
		fBReq.setCertType(baseEBankInfo.getCerttype().toString());
		fBReq.setCertNo(baseEBankInfo.getCertNo());
		fBReq.setBindSerialNo(baseEBankInfo.getBindNo());
		fBReq.setP2PserialNo(p2pDJ);
		fBReq.setInvestmentAmount(tenderBorrowCnd.getTenderMoney().multiply(new BigDecimal(100)).intValue());
		fBReq.setCurrency("156");// 156(人民币元)
		fBReq.setMobileCode(tenderBorrowCnd.getMobileCode());
		xstream.autodetectAnnotations(true);
		xstream.aliasField(CGBusinessConstants.FBReq, Message.class, "Mode");
		xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]
		message.setMode(fBReq);
		finance.setMessage(message);

		return xstream.toXML(finance);

	}

	public AccountError beanAccountError(AccountVo accountVo, Account account, String addip, Integer platform, String scene) {
		AccountError accountError = new AccountError();
		accountError.setUserId(account.getUserId());
		accountError.setTotal(accountVo.getTotal());
		accountError.setP2pNetvalue(accountVo.getNetvalue());
		accountError.setP2pUseMoney(accountVo.getUseMoney());
		accountError.setP2pNoUseMoney(accountVo.getNoUseMoney());
		accountError.setP2pCollection(accountVo.getCollection());
		accountError.setP2pFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountError.setP2pVersion(accountVo.getVersion());
		accountError.setP2pDrawMoney(accountVo.getDrawMoney());
		accountError.setP2pNoDrawMoney(accountVo.getNoDrawMoney());
		accountError.setP2peUseMoney(accountVo.geteUseMoney());
		accountError.setP2peNoUseMoney(accountVo.geteFreezeMoney());
		accountError.setP2peCollection(accountVo.geteCollection());
		accountError.setZsEUseMoney(account.getEUseMoney());
		accountError.setZsEFreezeMoney(account.getEFreezeMoney());
		accountError.setZsWithdrawamount(accountVo.geteCollection());
		accountError.setZsWithdrawamount(account.getZsWithdrawamount());
		accountError.setStatus(0);// 未处理
		accountError.setAddip(addip);
		accountError.setPlatform(platform);
		accountError.setScene(scene);
		return accountError;
	}

	public BaseEBankInfo eUserInfo(Integer userId) throws Exception {
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
		return baseEBankInfo;
	}

	/**
	 * <p>
	 * Description:查询该用户是否异常<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer findAccountErrorByUserIdCount(Integer userId) throws Exception {
		return accountErrorMapper.findAccountErrorByUserIdCount(userId);
	}

	/**
	 * <p>
	 * Description:短信接口调用<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveMobileCode(ShiroUser shiroUser, String remark, String relateNo, Integer borrowId) throws Exception {
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		// 组建报文
		String reqMessage = this.createSSReqXml(baseEBankInfo);
		System.out.println(reqMessage);
		String reqXml = XmlUtil.sign(reqMessage, CGBusinessConstants.SSReq);
		// 短信接口调用
		String rep = XmlUtil.send(reqXml);
		// 记录接口调用记录
		this.insertMsg(reqXml, "13", 1, shiroUser, remark, relateNo, borrowId);
		return rep;

	}
	
	
	public String saveMobileCode(ShiroUser shiroUser, String remark, String relateNo, Integer borrowId,String sType) throws Exception {
		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		// 组建报文
		String reqMessage = this.createSSReqXml(baseEBankInfo,sType);
		System.out.println(reqMessage);
		String reqXml = XmlUtil.sign(reqMessage, CGBusinessConstants.SSReq);
		// 短信接口调用
		String rep = XmlUtil.send(reqXml);
		// 记录接口调用记录
		this.insertMsg(reqXml, "13", 1, shiroUser, remark, relateNo, borrowId);
		return rep;

	}
	

	/**
	 * <p>
	 * Description:组建短信接口报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param baseEBankInfo
	 * @return String
	 */
	public String createSSReqXml(BaseEBankInfo baseEBankInfo) {

		XStream xstream = new XStream();
		xstream = new XStream(new DomDriver());
		Finance finance = new Finance();
		Message message = new Message();
		message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		SSReq sSReq = new SSReq();
		sSReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		sSReq.setMobile(baseEBankInfo.getMobile());
		sSReq.setSmsType("2");// 2:资金冻结
		xstream.autodetectAnnotations(true);
		xstream.aliasField(CGBusinessConstants.SSReq, Message.class, "Mode");
		xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]
		message.setMode(sSReq);
		finance.setMessage(message);

		return xstream.toXML(finance);

	}
	
	
	

	/**
	 * <p>
	 * Description:记录报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param reqXml
	 * @param mode
	 * @param type
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 *            void
	 */
	public void insertMsg(String reqXml, String mode, int type, ShiroUser shiroUser, String remark, String relateNo, Integer borrowId, Integer msgType) {

		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		// 记录项目登记日志
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setMode(mode);// 场景
		messageRecord.setType((byte) type);// 1:主动，2:被动
		messageRecord.setMsg(reqXml);// 报文体
		messageRecord.setMsgType(msgType);
		messageRecord.setOrderNo(borrowId.toString());
		messageRecord.setOptUserid(shiroUser.getUserId());
		messageRecord.setPlatform(shiroUser.getPlatform());
		messageRecord.setRemark(remark);
		messageRecord.setRelateNo(relateNo);// 调用关联号
		if (baseEBankInfo != null) {
			messageRecord.setBindNo(baseEBankInfo.getBindNo());
		}
		messageRecordMapper.insert(messageRecord);
	}

	public void insertMsg(String reqXml, String mode, int type, ShiroUser shiroUser, String remark, String relateNo, Integer borrowId) {

		BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		// 记录项目登记日志
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setMode(mode);// 场景
		messageRecord.setType((byte) type);// 1:主动，2:被动
		messageRecord.setMsg(reqXml);// 报文体
		messageRecord.setOrderNo(borrowId.toString());
		messageRecord.setOptUserid(shiroUser.getUserId());
		messageRecord.setPlatform(shiroUser.getPlatform());
		messageRecord.setRemark(remark);
		messageRecord.setRelateNo(relateNo);// 调用关联号
		if (baseEBankInfo != null) {
			messageRecord.setBindNo(baseEBankInfo.getBindNo());
		}
		messageRecordMapper.insert(messageRecord);
	}

	/**
	 * <p>
	 * Description:记录响应报文<br />
	 * </p>
	 * 
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param repXml
	 * @param mode
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @throws Exception
	 *             void
	 */
	public String saveResXml(String repXml, String mode, ShiroUser shiroUser, String remark, String relateNo, Integer borrowId) throws Exception {
		String msg = BusinessConstants.SUCCESS;
		boolean isError = XmlUtil.checkXml(repXml);
		boolean istrue = XmlUtil.checkYq(repXml);

		// 判断响应报文
		if (isError) {
			logger.info("响应ERROR报文:" + repXml);
			msg = "响应ERROR报文";
		}
		// 验签
		if (!istrue) {
			logger.info("验签失败" + repXml);
			msg = "验签失败";
		}
		if (msg.equals(BusinessConstants.SUCCESS)) {
			// 记录响应报文
			this.insertMsg(repXml, mode, 2, shiroUser, remark, relateNo, borrowId);
		} else {
			// 记录响应报文
			this.insertMsg(repXml, mode, 2, shiroUser, remark, relateNo, borrowId, 1);
		}

		return msg;

	}

	public List<MessageRecord> findMessageRecord(MessageRecord messageRecord) {
		return messageRecordMapper.findMessageRecord(messageRecord);
	}

	
	/**
	 * 
	 * <p>
	 * Description:自动投标设置接口调用<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年6月27日
	 * @param shiroUser
	 * @param mobileCode
	 * @param flag
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveABSReq(ShiroUser shiroUser,String mobileCode,String flag,String remark,String relateNo) throws Exception{
		
		BaseEBankInfo baseEBankInfo=baseEBankInfoMapper.selectByUserId(shiroUser.getUserId());
		//组建报文
		String reqMessage=this.createABSReqXml(baseEBankInfo,mobileCode,flag);
		System.out.println(reqMessage);
		String reqXml=XmlUtil.sign(reqMessage, CGBusinessConstants.ABSReq);
		//短信接口调用
		String rep= XmlUtil.send(reqXml);
		//记录接口调用记录
		this.insertMsg(reqXml, "18", 1, shiroUser, remark, relateNo,shiroUser.getUserId());
		return rep;
		
	}
	
	
	/**
	 * 
	 * <p>
	 * Description:自动投标设置报文组建<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年6月27日
	 * @param baseEBankInfo
	 * @param mobileCode
	 * @param flag
	 * @return
	 * String
	 */
	public String createABSReqXml(BaseEBankInfo baseEBankInfo,String mobileCode,String flag){
		
		XStream xstream = new XStream();
		 xstream = new XStream(new DomDriver());
		 Finance finance =new Finance();
		 Message message =new Message();
		 message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		 ABSReq aBSReq =new ABSReq();
		 aBSReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		 aBSReq.setBindSerialNo(baseEBankInfo.getBindNo());
		 aBSReq.setMobileCode(mobileCode);
		 aBSReq.setFlag(flag);
			xstream.autodetectAnnotations(true);
	        xstream.aliasField(CGBusinessConstants.ABSReq, Message.class, "Mode");
	        xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]  
	        message.setMode(aBSReq);
	        finance.setMessage(message);
		
		return xstream.toXML(finance);
		
	}
	
	
	public String createSSReqXml(BaseEBankInfo baseEBankInfo,String sType) {

		XStream xstream = new XStream();
		xstream = new XStream(new DomDriver());
		Finance finance = new Finance();
		Message message = new Message();
		message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));
		SSReq sSReq = new SSReq();
		sSReq.setDate(DateUtils.format(new Date(), DateUtils.YMDH_M_S));
		sSReq.setMobile(baseEBankInfo.getMobile());
		sSReq.setSmsType(sType);
		xstream.autodetectAnnotations(true);
		xstream.aliasField(CGBusinessConstants.SSReq, Message.class, "Mode");
		xstream.aliasSystemAttribute(null, "class"); // 去掉 class 属性[/color]
		message.setMode(sSReq);
		finance.setMessage(message);

		return xstream.toXML(finance);

	}
	
	
}
