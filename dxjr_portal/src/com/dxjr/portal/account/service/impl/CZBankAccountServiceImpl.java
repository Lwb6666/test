package com.dxjr.portal.account.service.impl;

import com.dxjr.base.entity.*;
import com.dxjr.base.mapper.*;
import com.dxjr.common.custody.FinanceFactory;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.mapper.CashRecordMapper;
import com.dxjr.portal.account.service.*;
import com.dxjr.portal.account.vo.*;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.custody.XML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.dxjr.common.custody.xml.XmlUtil.getFirstElementValue;

/**
 * <p>
 * Description: 保存银行绑定信息<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/19
 * @title dxjr_interface
 * @package com.dxjr.portal.account.service.impl
 */
@Service
public class CZBankAccountServiceImpl implements CZBankAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;
    @Autowired
    private BaseEBankInfoLogMapper baseEBankInfoLogMapper;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private BaseRechargeRecordMapper baseRechargeRecordMapper;
    @Autowired
    private BaseAccountMapper baseAccountMapper;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountErrorMapper accountErrorMapper;
    @Autowired
    private BaseAccountLogMapper baseAccountLogMapper;
    @Autowired
    private CashRecordMapper cashRecordMapper;
    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private BaseCashRecordMapper baseCashRecordMapper;
    @Autowired
    private BaseMailSendRecordMapper baseMailSendRecordMapper;

    @Override
    public void saveEBankInfo(FinanceFactory.FinanceResponse res,
                              Integer userId) throws Exception {
        Assert.notNull(res, "res cannot null");
        Assert.isTrue(!res.isHasError(), "res has error");

        Document resDoc = res.getResDocument();
        String stt = getFirstElementValue(resDoc, "stt");

        if ("1".equals(stt)) {
            this.saveEBankInfo(resDoc, userId);
        }
    }

    private void saveEBankInfo(Document resDoc, Integer userId) throws Exception {
        Assert.notNull(resDoc, "resDoc cannot null");

        String accountName = getFirstElementValue(resDoc, "accountName");
        String certType = getFirstElementValue(resDoc, "certType");
        String certNo = getFirstElementValue(resDoc, "certNo");
        String mobile = getFirstElementValue(resDoc, "mobile");
        String stt = getFirstElementValue(resDoc, "stt");
        String bindSerialNo = getFirstElementValue(resDoc, "bindSerialNo");
        String eCardNo = getFirstElementValue(resDoc, "EcardNo");
        String otherAccno = getFirstElementValue(resDoc, "otherAccno");
        String bankName = getFirstElementValue(resDoc, "bankName");
        String cstNo = getFirstElementValue(resDoc, "Cstno");
        String extension = getFirstElementValue(resDoc, "extension");
        Assert.hasLength(certType, "certType mustbe have length");
        Assert.hasLength(extension, "extension mustbe have length");
        Assert.hasLength(bindSerialNo, "bindSerialNo mustbe have length");

        if (userId == null) {
            Assert.hasLength(cstNo, "cstNo must be have length");
            userId = Integer.parseInt(cstNo);
        }

        // 查询用户手机号及实名认证信息，用于组装报文
        MemberVo memberVo = memberMapper.queryUserBaseInfo(userId);
        mobile = StringUtils.hasLength(mobile) ? mobile : memberVo.getMobile();
        certNo = StringUtils.hasLength(certNo) ? certNo : memberVo.getIdCardNo();
        accountName = StringUtils.hasLength(accountName) ? accountName : memberVo.getRealName();
        if ("1".equals(stt)) {
            // 锁定用户并更新存管类型
            memberVo = memberMapper.queryMemberByIdForUpdate(userId);
            Assert.notNull(memberVo, "mmemberVo cannot null");
            memberVo.setIsCustody(1);
            memberVo.setEType(Integer.parseInt(extension) + 1);
            // 更新条件
            memberVo.setStatus(0);
            int i = memberMapper.updateMemberForCustody(memberVo);
            if (i > 0) {
                // 更新成功后处理
                BaseEBankInfo eBankInfo = baseEBankInfoMapper.selectByUserIdForUpdate(userId);
                boolean isUpdate = true;
                if (eBankInfo == null) {
                    isUpdate = false;
                    eBankInfo = new BaseEBankInfo();
                }
                eBankInfo.setUserId(userId);
                eBankInfo.setBankname("浙商银行");
                eBankInfo.setEcardNo(eCardNo);
                eBankInfo.setRealname(accountName);
                eBankInfo.setCerttype(Integer.parseInt(certType));
                eBankInfo.setCertNo(certNo);
                eBankInfo.setMobile(mobile);
                eBankInfo.setStatus(1);
                eBankInfo.setBindNo(bindSerialNo);
                eBankInfo.setCardtype(memberVo.getEType());
                eBankInfo.setCustodyBindName(bankName);
                eBankInfo.setCustodyBindNo(otherAccno);
                if (isUpdate) {
                    baseEBankInfoMapper.updateByPrimaryKeySelective(eBankInfo);
                } else {
                    baseEBankInfoMapper.insertSelective(eBankInfo);
                }
                // 记录变更日志
                BaseEBankInfoLog log = new BaseEBankInfoLog();
                log.setEbankinfoId(eBankInfo.getId());
                log.setUserId(userId);
                log.setBankname("浙商银行");
                log.setEcardNo(eCardNo);
                log.setRealname(accountName);
                log.setCerttype(Integer.parseInt(certType));
                log.setCertNo(certNo);
                log.setMobile(mobile);
                log.setStatus(1);
                log.setBindNo(bindSerialNo);
                log.setCardtype(memberVo.getEType());
                log.setCustodyBindName(bankName);
                log.setCustodyBindNo(otherAccno);
                baseEBankInfoLogMapper.insertSelective(log);
            }
        }
    }

    @Override
    public void saveEBankInfo(String res) throws Exception {
        Assert.hasLength(res, "res must be have length");

        try {
            XML xml = XML.readFrom(res);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document resDoc = docBuilder.parse(new InputSource(new StringReader(xml.getXmlStr())));

            this.saveEBankInfo(resDoc, null);
        } catch (Exception e) {
            logger.error("send error", e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public String saveEBankAccount(FinanceFactory.FinanceResponse res,
                                   BaseEBankInfo bankInfo, String ip, String scene) throws Exception {
        String result = "success";
        Integer userId = bankInfo.getUserId();
        Document resDoc = res.getResDocument();
        Assert.notNull(resDoc, "resDoc cannot null");

        String totalAmount = getFirstElementValue(resDoc, "totalAmount");
        String freezeAmount = getFirstElementValue(resDoc, "freezeAmout");
        String withdrawAmount = getFirstElementValue(resDoc, "withdrawAmount");
        Assert.hasLength(totalAmount, "totalAmount must be have length");
        Assert.hasLength(freezeAmount, "freezeAmount must be have length");
        Assert.hasLength(withdrawAmount, "fwithdrawAmount must be have length");
        BigDecimal useMoney = new BigDecimal(totalAmount).divide(BigDecimal.valueOf(100));
        BigDecimal freezeMoney = new BigDecimal(freezeAmount).divide(BigDecimal.valueOf(100));
        BigDecimal withdrawMoney = new BigDecimal(withdrawAmount).divide(BigDecimal.valueOf(100));
        AccountVo accountVo = accountMapper.findAccountByUserIdForUpdate(userId);
        AccountVo account = new AccountVo();
        account.seteUseMoney(useMoney);
        account.seteFreezeMoney(freezeMoney);
        account.setEWithdraw(withdrawMoney);

        if (bankInfo.getCardtype() == 1) {
            // 如果是E户，金额不对等则记录错误日志并邮件和短信通知
            // 可用金额或冻结不相等
            if (accountVo.geteUseMoney().compareTo(useMoney) != 0 ||
                    accountVo.geteFreezeMoney().compareTo(freezeMoney) != 0) {
                List<AccountError> list = accountErrorMapper.findAccountErrorByUserId(accountVo.getUserId());

                if (list.size() > 0) {
                    AccountError ae = list.get(0);
                    //判断账户异常未处理的记录与存管金额对比
                    if (ae.getZsEUseMoney().compareTo(useMoney) != 0 ||
                            ae.getZsEFreezeMoney().compareTo(freezeMoney) != 0 ||
                            ae.getZsWithdrawamount().compareTo(withdrawMoney) != 0) {
                        //邮件提醒相关人员
                        //this.sendEmail(userId);
                        //记录账户异常信息日志
                        this.addAccountError(accountVo, account, ip, 3, scene);
                    }
                } else {
                    //邮件提醒相关人员
                    //this.sendEmail(userId);
                    //记录账户异常信息日志
                    this.addAccountError(accountVo, account, ip, 3, scene);
                }
                // return "账户异常，请联系客服!";
            }
        } else if (bankInfo.getCardtype() == 2) {
            // 如果是商卡，更新账户金额，并记录资金日志
            //以存管账户为主，更新平台账户
            if (accountVo.geteUseMoney().compareTo(useMoney) != 0) {
                BigDecimal money = useMoney.subtract(accountVo.geteUseMoney());
                Account at = new Account();
                at.setId(accountVo.getId());
                at.setEUseMoney(useMoney);
                at.setTotal(accountVo.getTotal().add(money));
                baseAccountMapper.updateEntity(at);
                //记录账户日志
                AccountLog accountLog = new AccountLog();
                accountLog.setUserId(userId);
                accountLog.setType(CGBusinessConstants.SK_SYN);
                accountLog.setMoney(new BigDecimal(Math.abs(money.doubleValue())));
                accountLog.setToUser(-2);//-2:存管系统
                accountLog.setIdType(20);//存管系统
                accountLog.setRemark("与存管系统商卡可用同步");
                accountLog.setAddip(ip);
                accountLog.setIsCustody(1);
                baseAccountLogMapper.insertAccountLog(accountLog);
            }

            if (accountVo.geteFreezeMoney().compareTo(freezeMoney) != 0) {
                BigDecimal money = freezeMoney.subtract(accountVo.geteFreezeMoney());
                Account at = new Account();
                at.setId(accountVo.getId());
                at.setEFreezeMoney(freezeMoney);
                at.setTotal(accountVo.getTotal().add(money));
                baseAccountMapper.updateEntity(at);
                //记录账户日志
                AccountLog accountLog = new AccountLog();
                accountLog.setUserId(userId);
                accountLog.setType(CGBusinessConstants.SK_SYN);
                accountLog.setMoney(new BigDecimal(Math.abs(money.doubleValue())));
                accountLog.setToUser(-2);//-2:存管系统
                accountLog.setIdType(20);//存管系统
                accountLog.setRemark("与存管系统商卡冻结同步");
                accountLog.setAddip(ip);
                accountLog.setIsCustody(1);
                baseAccountLogMapper.insertAccountLog(accountLog);
            }
        }
        return result;
    }

    private void sendEmail(Integer userId) throws Exception {
        //新增邮件待发记录
        MailSendRecord mailSendRecord = new MailSendRecord();
        mailSendRecord.setTypeId(userId);
        mailSendRecord.setType(9);//平台与存管账户资金不等
        mailSendRecord.setStatus(0);
        mailSendRecord.setAddtime(new Date());
        baseMailSendRecordMapper.insertEntity(mailSendRecord);
    }

    @Override
    public String saveTopup(FinanceFactory.FinanceResponse res, Integer type,
                            String tradeNo, String ip) throws Exception {
        Document resDoc = res.getResDocument();
        Assert.notNull(resDoc, "resDoc cannot null");

        String[] values = getFieldByType(type);
        String statusField = values[0];
        String serialNoField = values[1];

        // 提现结果
        String deductStatus = getFirstElementValue(resDoc, statusField);
        // 提现流水号
        String deductSerialNo = getFirstElementValue(resDoc, serialNoField);

        return this.saveTopup(deductStatus, deductSerialNo, tradeNo, ip);
    }

    private String[] getFieldByType(Integer type) {
        String[] values = new String[2];
        // 根据类型获取字段值
        if (type == 1) {
            // 查询充值响应
            values[0] = "DeductStatus";
            values[1] = "DeductSerialNo";
        } else {
            // 查询交易响应
            values[0] = "stt";
            values[1] = "extension";
        }
        return values;
    }

    private String saveTopup(String deductStatus, String deductSerialNo,
                             String bizNo, String ip) throws Exception {
        String result = "success";
        // 找到充值记录并锁定
        RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
        rechargeRecordCnd.setBizNo(bizNo);
        rechargeRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
        RechargeRecordVo rechargeRecordVo = rechargeRecordService.queryRechargeRecordVoByCnd(rechargeRecordCnd);
        // 如果不是待付款
        if (null == rechargeRecordVo ||
                rechargeRecordVo.getStatus() != Constants.RECHARGE_STATUS_ONLINE_WAIT &&
                        rechargeRecordVo.getStatus() != 10 &&
                        rechargeRecordVo.getStatus() != 40) {
            return "支付失败";// 支付失败
        }

        rechargeRecordVo.setETradeNo(deductSerialNo);
        if ("20".equals(deductStatus)) {
            // 充值成功
            // 更新充值状态
            updateRechargeStatus(rechargeRecordVo, Constants.RECHARGE_STATUS_SUCCESS);
            // 更新用户账户金额，并记录资金明细
            updateAccount(rechargeRecordVo, ip);
        } else if ("30".equals(deductStatus)) {
            // 充值失败
            // 更新充值状态
            updateRechargeStatus(rechargeRecordVo, Constants.RECHARGE_STATUS_FAILURE);
            return "充值失败";
        } else if ("10".equals(deductStatus) || "40".equals(deductStatus)) {
            // 后续进行补单处理
            // 更新充值状态
            updateRechargeStatus(rechargeRecordVo, Integer.parseInt(deductStatus));
            return "充值处理中";
        } else {
            return "不能存在或未处理";
        }
        return result;
    }

    @Override
    public String saveTakeCash(FinanceFactory.FinanceResponse res, Integer type,
                               String bizNo, String ip) throws Exception {
        Document resDoc = res.getResDocument();
        Assert.notNull(resDoc, "resDoc cannot null");

        String[] values = getFieldByType(type);
        String statusField = values[0];
        String serialNoField = values[1];

        // 提现结果
        String deductStatus = getFirstElementValue(resDoc, statusField);
        // 提现流水号
        String deductSerialNo = getFirstElementValue(resDoc, serialNoField);

        return this.saveTakeCash(deductStatus, deductSerialNo, bizNo, ip);
    }

    private String saveTakeCash(String deductStatus, String deductSerialNo,
                                String bizNo, String ip) throws Exception {
        String result = "success";

        // 找出提现记录并锁定
        CashRecordCnd cashRecordCnd = new CashRecordCnd();
        cashRecordCnd.setBizNo(bizNo);
        cashRecordCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
        CashRecordVo cashRecordVo = cashRecordService.queryCashRecordVoByCnd(cashRecordCnd);
        if (null == cashRecordVo ||
                cashRecordVo.getStatus() != Constants.CASH_RECORD_STATUS_APPLYING &&
                        cashRecordVo.getStatus() != 10 &&
                        cashRecordVo.getStatus() != 40) {
            return "提现记录未找到或状态已变更,请刷新数据或稍后重试！";
        }

        cashRecordVo.seteTradeNo(deductSerialNo);
        if ("20".equals(deductStatus)) {
            // 提现成功
            // 更新提现状态
            //updateCashRecordStatus(cashRecordVo, Constants.CASH_RECORD_STATUS_APPROVE_SUCCESS);
            updateCashRecordStatus(cashRecordVo, Constants.CASH_RECORD_STATUS_CASH_FINISH);
            // 更新用户账户金额，并记录资金明细
            updateAccount(cashRecordVo, ip);
        } else if ("30".equals(deductStatus)) {
            // 提现失败
            // 更新提现状态
            updateCashRecordStatus(cashRecordVo, Constants.CASH_RECORD_STATUS_APPROVE_FAILURE);
            return "提现失败";
        } else if ("10".equals(deductStatus) || "40".equals(deductStatus)) {
            // 后续进行补单处理
            // 更新提现状态
            updateCashRecordStatus(cashRecordVo, Integer.parseInt(deductStatus));
            return "提现处理中";
        } else {
            return "不能存在或未处理";
        }

        return result;
    }

    /**
     * 记录账户异常日志
     *
     * @param accountVo
     * @param ip
     * @param platform
     * @param scene
     */
    private void addAccountError(AccountVo accountVo, AccountVo account,
                                 String ip, Integer platform, String scene) {
        AccountError accountError = new AccountError();
        accountError.setUserId(accountVo.getUserId());
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
        accountError.setZsEUseMoney(account.geteUseMoney());
        accountError.setZsEFreezeMoney(account.geteFreezeMoney());
        accountError.setZsWithdrawamount(account.getEWithdraw());
        accountError.setStatus(0);//未处理
        accountError.setAddip(ip);
        accountError.setPlatform(platform);
        accountError.setScene(scene);
        accountErrorMapper.insert(accountError);
    }

    /**
     * <p>
     * Description:更新充值记录的状态<br />
     * </p>
     *
     * @param rechargeRecordVo
     * @return
     * @throws Exception Integer
     * @author zhaowei
     * @version 0.1 2016年5月21日
     */
    private Integer updateRechargeStatus(RechargeRecordVo rechargeRecordVo, Integer status) throws Exception {
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setId(rechargeRecordVo.getId());
        rechargeRecord.setETradeNo(rechargeRecordVo.getETradeNo());
        rechargeRecord.setStatus(status);
        rechargeRecord.setVerifyTime(DateUtils.getTime());
        rechargeRecord.setVerifyRemark(Constants.ONLINE_TYPE_CZBANK_NAME.concat("调用接口调用自动审核"));
        rechargeRecord.setVerifyTime2(DateUtils.getTime());
        rechargeRecord.setVerifyRemark2(Constants.ONLINE_TYPE_CZBANK_NAME.concat("调用接口调用自动审核"));
        return baseRechargeRecordMapper.updateEntity(rechargeRecord);
    }

    /**
     * <p>
     * Description:更新提现记录状态<br />
     * </p>
     *
     * @param cashRecordVo 提现记录
     * @param status       状态
     * @return
     * @throws Exception Integer
     * @author zhaowei
     * @version 0.1 2016年5月21日
     */
    private Integer updateCashRecordStatus(CashRecordVo cashRecordVo, Integer status) throws Exception {
        CashRecord cashRecord = new CashRecord();
        cashRecord.setId(cashRecordVo.getId());
        cashRecord.setStatus(status);
        cashRecord.seteTradeNo(cashRecordVo.geteTradeNo());
        cashRecord.setVerifyUserid(cashRecordVo.getUserId());
        cashRecord.setVerifyTime(DateUtils.getTime());
        cashRecord.setVerifyRemark(Constants.ONLINE_TYPE_CZBANK_NAME.concat("调用接口调用自动审核"));
        cashRecord.setVerifyTime2(DateUtils.getTime());
        cashRecord.setVerifyRemark(Constants.ONLINE_TYPE_CZBANK_NAME.concat("调用接口调用自动审核"));
        return baseCashRecordMapper.updateEntity(cashRecord);
    }

    /**
     * <p>
     * Description:更新用户帐号的可用余额和总金额<br />
     * </p>
     *
     * @param rechargeRecordVo
     * @return
     * @throws Exception
     * @author zhaowei
     * @version 0.1 2016年5月21日
     */
    private void updateAccount(RechargeRecordVo rechargeRecordVo, String ip) throws Exception {
        // 目前无手续费
        AccountVo sourceAccount = accountService.queryAccountByUserIdForUpdate(rechargeRecordVo.getUserId());
        Account account = new Account();
        BeanUtils.copyProperties(sourceAccount, account);
        account.setEUseMoney(account.getEUseMoney().add(rechargeRecordVo.getMoney()));
        account.setTotal(account.getTotal().add(rechargeRecordVo.getMoney()));
        baseAccountMapper.updateEntity(account);

        sourceAccount.seteUseMoney(account.getEUseMoney());
        sourceAccount.setTotal(account.getTotal());
        // 插入资金记录
        String remark = "在线充值成功,本次使用的是"
                .concat(Constants.ONLINE_TYPE_CZBANK_NAME).concat("进行充值.");
        accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(),
                rechargeRecordVo.getMoney(), remark, ip,
                "online_recharge", 20, null, null, 1);
    }

    private void updateAccount(CashRecordVo cashRecordVo, String ip) throws Exception {
        // 目前无手续费
        AccountVo sourceAccount = accountService.queryAccountByUserIdForUpdate(cashRecordVo.getUserId());
        Account account = new Account();
        BeanUtils.copyProperties(sourceAccount, account);
        account.setEUseMoney(account.getEUseMoney().subtract(cashRecordVo.getCredited()));
        account.setTotal(account.getTotal().subtract(cashRecordVo.getCredited()));
        baseAccountMapper.updateEntity(account);

        sourceAccount.seteUseMoney(account.getEUseMoney());
        sourceAccount.setTotal(account.getTotal());
        // 插入资金记录
        String remark = "在线提现成功,本次使用的是"
                .concat(Constants.ONLINE_TYPE_CZBANK_NAME).concat("进行提现.");
        accountLogService.saveAccountLogByParams(sourceAccount, sourceAccount.getUserId(),
                cashRecordVo.getCredited(), remark, ip,
                "cash_success", 20, null, null, 1);
    }

}
