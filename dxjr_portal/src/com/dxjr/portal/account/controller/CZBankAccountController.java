package com.dxjr.portal.account.controller;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.entity.BaseEMessageRecord;
import com.dxjr.base.entity.CashRecord;
import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.mapper.BaseCashRecordMapper;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.base.mapper.BaseEMessageRecordMapper;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.common.custody.*;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.CZBankAccountService;
import com.dxjr.portal.account.service.CashRecordService;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.vo.*;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.PropertiesUtil;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.dxjr.common.custody.xml.XmlUtil.getFirstElementValue;

/**
 * <p>
 * Description:浙商银行账户Controller<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016年5月18日
 * @title CZBankAccountController.java
 * @package com.dxjr.portal.account.controller
 */
@Controller
@RequestMapping(value = "/account/czbank")
public class CZBankAccountController extends BaseController {

    public Logger logger = Logger.getLogger(CZBankAccountController.class);

    @Autowired
    private BankInfoService bankInfoService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;
    @Autowired
    private BaseEMessageRecordMapper baseEMessageRecordMapper;
    @Autowired
    private CZBankAccountService czBankAccountService;
    @Autowired
    private BaseRechargeRecordMapper baseRechargeRecordMapper;
    @Autowired
    private BaseCashRecordMapper baseCashRecordMapper;
    @Autowired
    private CashRecordService cashRecordService;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * <p>
     * Description:开户验证<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月18日
     */
    @RequestMapping("/validateAccount")
    @RequiresAuthentication
    @ResponseBody
    public MessageBox validateAccount(HttpServletRequest req) {
        try {
            req.getSession().removeAttribute("openAccountIsValid");
            // 判断当前用户是否能开通存管账户
            String result = this.validateBase(1);
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return MessageBox.build("0", result);
            }

            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 验证用户是否已经开通账户
            BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
            if (baseEBankInfo != null && baseEBankInfo.getStatus() == 1) {
                return MessageBox.build("0", "用户已开通存管账户");
            }
            // 调用浙商银行接口验证是否已开通
            QcReq qcReq = new QcReq();
            qcReq.setCstno(userId.toString());
            qcReq.setDate(new Date());
            // 创建请求实例
            FinanceFactory.FinanceProxy financeProxy = FinanceFactory.getInstance()
                    .create()
                    .setModel(qcReq)
                    .aliasMode("QCReq");

            // 首先调用接口前插入一条通讯报文记录
            String relateNo = getRelateNo();
            this.addMessageRecord(1, "15", financeProxy.toXML(),
                    relateNo, userId, "开户信息查询请求");
            // 请求浙商银行接口
            FinanceFactory.FinanceResponse res = financeProxy.send();
            // 接口返回报文记录
            this.addMessageRecord(2, "15", res.getResponse(),
                    relateNo, userId, "开户信息查询响应");

            if (res.isHasError() && "3325".equals(res.getErrorCode())) {
                req.getSession().setAttribute("openAccountIsValid", true);
                return MessageBox.build("1", "开户验证成功");
            } else if (res.isHasError()) {
                return MessageBox.build("0", res.getErrorMsg());
            }
            // 更新返回结果
            czBankAccountService.saveEBankInfo(res, userId);
        } catch (Exception e) {
            logger.error("开通账户验证失败", e);
            return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
        }
        return MessageBox.build("0", "用户已开通存管账户");
    }

    /**
     * <p>
     * Description:异步请求浙商银行接口<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    @RequestMapping("/dispatcher")
    @RequiresAuthentication
    public ModelAndView dispatcher(HttpServletRequest req) throws UnsupportedEncodingException {
        Object isValid = req.getSession().getAttribute("openAccountIsValid");
        if (isValid != null && "true".equals(isValid.toString())) {
            String url = PropertiesUtil.getValue("custody_req_async_url");
            if (!StringUtils.hasLength(url)) {
                throw new RuntimeException("custody_req_async_url not found");
            }
            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 查询用户手机号及实名认证信息，用于组装报文
            MemberVo memberVo = memberMapper.queryUserBaseInfo(userId);
            String mobile = memberVo.getMobile();
            String idCardNo = memberVo.getIdCardNo();
            String realName = memberVo.getRealName();
            // 封装报文对象
            OcReq ocReq = new OcReq();
            ocReq.setMobile(mobile);
            ocReq.setCertNo(idCardNo);
            ocReq.setAccountName(realName);
            ocReq.setCstno(userId.toString());
            String xml = FinanceFactory.getInstance()
                    .create()
                    .setModel(ocReq)
                    .aliasMode("OCReq")
                    .toSignedXML();
            // 首先调用接口前插入一条通讯报文记录
            this.addMessageRecord(1, "1", xml, null, userId, "开立存管账户请求");
            String encodedData = URLEncoder.encode(URLEncoder.encode(xml, "utf-8"), "utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("url", url);
            map.put("reqMsg", encodedData);
            return forword("/account/czbank/dispatcher").addAllObjects(map);
        }
        return redirect("/home");
    }

    /**
     * <p>
     * Description:浙商银行充值接口<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    @RequestMapping("/topup")
    @RequiresAuthentication
    @ResponseBody
    public MessageBox topup(TopupVo topupVo) {
        try {
            // 判断当前用户是否能充值
            String result = this.validateBase(4);
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return MessageBox.build("0", result);
            }
            // 短信验证码不能为空
            if (!StringUtils.hasLength(topupVo.getMobileCode())) {
                return MessageBox.build("0", "短信验证码不能为空");
            }
            // 充值金额必须大于等于500
            if (topupVo.getAmount() != null && topupVo.getAmount().compareTo(BigDecimal.valueOf(500)) < 0) {
                return MessageBox.build("0", "充值金额必须大于等于500");
            }
            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 验证用户是否已经开通账户
            BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
            if (baseEBankInfo == null || baseEBankInfo.getStatus() == 0) {
                return MessageBox.build("0", "请先开通存管账户");
            }
            // 非E户不能进行充值
            if (baseEBankInfo.getCardtype().compareTo(1) != 0) {
                return MessageBox.build("0", "仅开通E户方可进行充值");
            }
            if (baseEBankInfo.getStatus().compareTo(2) == 0) {
                return MessageBox.build("0", "已解绑用户无法进行充值");
            }

            // 验证本系统中资金余额是否与浙商银行中一致
            result = verifyAccountBalance(baseEBankInfo, 1);
            if (!"success".equals(result)) {
                return MessageBox.build("0", result);
            }
            // 进行充值
            result = toTopup(baseEBankInfo, topupVo);
            if (!"success".equals(result)) {
                logger.error("充值失败：" + result);
                return MessageBox.build("0", result);
            }

        } catch (Exception e) {
            logger.error("充值异常", e);
            return MessageBox.build("0", "充值失败");
        }
        return MessageBox.build("1", "充值成功");
    }

    /**
     * <p>
     * Description:浙商银行补单回调接口<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    @RequestMapping(value = "/loseOrder", produces = {"text/javascript;charset=UTF-8"})
    @ResponseBody
    public String loseOrder(HttpServletRequest request) {
        String id = WebUtils.findParameterValue(request, "id");
        String type = WebUtils.findParameterValue(request, "type");
        String callbackFunc = WebUtils.findParameterValue(request, "callback");

        try {
            String relateNo = getRelateNo();
            TsqReq tsqReq = new TsqReq();
            tsqReq.setOrderNo(id);
            tsqReq.setType(type);
            FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                    .create()
                    .setModel(tsqReq)
                    .aliasMode("TSQReq");
            String signedXml = proxy.toSignedXML();
            // 调用接口前记录
            this.addMessageRecord(1, "16", signedXml, relateNo, -1, "单笔交易查询请求");
            // 请求发送短信接口
            FinanceFactory.FinanceResponse res = proxy.send();
            // 接口返回后记录
            this.addMessageRecord(2, "16", res.getResponse(), relateNo, -1, "单笔交易查询响应");
            // 流水状态
            String stt = getFirstElementValue(res.getResDocument(), "stt");
            if (res.isHasError()) {
                return callback(MessageBox.build("0", res.getErrorMsg()), callbackFunc);
            } else if ("20".equals(stt)) {
                // 充值成功验证金额
                Integer userId = 0;
                BigDecimal operationMoney = BigDecimal.ZERO;
                if ("1".equals(type)) {
                    // 充值补单
                    RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
                    rechargeRecordCnd.setBizNo(id);
                    RechargeRecordVo rechargeRecordVo = rechargeRecordService
                            .queryRechargeRecordVoByCnd(rechargeRecordCnd);
                    if (rechargeRecordVo == null) {
                        return callback(MessageBox.build("0", "该充值记录不存在"), callbackFunc);
                    }
                    userId = rechargeRecordVo.getUserId();
                    operationMoney = rechargeRecordVo.getMoney();
                } else if ("2".equals(type)) {
                    // 提现补单
                    CashRecordCnd cashRecordCnd = new CashRecordCnd();
                    cashRecordCnd.setBizNo(id);
                    CashRecordVo cashRecordVo = cashRecordService.queryCashRecordVoByCnd(cashRecordCnd);
                    if (cashRecordVo == null) {
                        return callback(MessageBox.build("0", "该提现记录不存在"), callbackFunc);
                    }
                    userId = cashRecordVo.getUserId();
                    operationMoney = cashRecordVo.getTotal();
                }

                // 在本系统中查询该用户的余额信息
                AccountVo accountVo = accountMapper.queryByUserId(userId);
                BigDecimal euseMoney = accountVo.geteUseMoney();

                BaseEBankInfo bankInfo = baseEBankInfoMapper.selectByUserId(userId);
                // 从浙商银行查询该用户余额信息
                FinanceFactory.FinanceResponse res2 = this.queryAccount(bankInfo);
                if (res2.isHasError()) {
                    return callback(MessageBox.build("0", res2.getErrorMsg()), callbackFunc);
                }
                // 账户总额
                String totalAmount = getFirstElementValue(res2.getResDocument(), "totalAmount");
                Assert.hasLength(totalAmount, "totalAmount must be have length");
                BigDecimal useMoney = new BigDecimal(totalAmount).divide(BigDecimal.valueOf(100));

                // 补单后的金额
                BigDecimal compareMoney = "1".equals(type) ?
                        euseMoney.add(operationMoney) : euseMoney.subtract(operationMoney);

                if (compareMoney.compareTo(useMoney) != 0) {
                    return callback(MessageBox.build("0",
                            "补单失败，异常订单"), callbackFunc);
                }
            }

            // 处理补单
            String result = BusinessConstants.SUCCESS;
            if ("1".equals(type)) {
                // 充值补单
                result = czBankAccountService.saveTopup(res, 2, id, currentRequest().getRemoteHost());
            } else if ("2".equals(type)) {
                // 提现补单
                result = czBankAccountService.saveTakeCash(res, 2, id, currentRequest().getRemoteHost());
            }
            if (BusinessConstants.SUCCESS.equals(result)) {
                return callback(MessageBox.build("1", "处理成功"), callbackFunc);
            } else {
                return callback(MessageBox.build("0", result), callbackFunc);
            }
        } catch (Exception e) {
            logger.error("补单异常", e);
            return callback(MessageBox.build("0", e.getMessage()), callbackFunc);
        }

    }

    private String callback(MessageBox messageBox, String callbackFunc) {
        String json = JsonUtils.bean2Json(messageBox);
        return callbackFunc + "(" + json + ");";
    }

    /**
     * <p>
     * Description:浙商银行充值成功页面<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    @RequestMapping("/topupSuccess")
    @RequiresAuthentication
    public ModelAndView topupSuccess() {
        return forword("/account/topup/topupResult").addObject("topupResult", "success");
    }

    /**
     * <p>
     * Description:浙商银行提现成功页面<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    @RequestMapping("/takeCashSuccess")
    @RequiresAuthentication
    public ModelAndView takeCashSuccess() {
        return forword("/account/cash/takeCashResult");
    }

    /**
     * <p>
     * Description:浙商银行提现接口<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月31日
     */
    @RequestMapping("/saveTakeCash")
    @RequiresAuthentication
    @ResponseBody
    public MessageBox saveTakeCash(TakeCashVo takeCashVo) {
        try {
            // 判断当前用户是否能提现
            String result = this.validateBase(5);
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return MessageBox.build("0", result);
            }
            // 短信验证码不能为空
            if (!StringUtils.hasLength(takeCashVo.getMobileCode())) {
                return MessageBox.build("0", "短信验证码不能为空");
            }
            // 提现金额必须大于0
            if (takeCashVo.getAmount() != null && takeCashVo.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return MessageBox.build("0", "提现金额必须大于0");
            }
            // 提现金额不能超过5万
            if (takeCashVo.getAmount() != null && takeCashVo.getAmount().compareTo(BigDecimal.ZERO) > 50000) {
                return MessageBox.build("0", "单笔提现金额不能超过5万");
            }
            // 交易密码不能为空
            if (!StringUtils.hasLength(takeCashVo.getPaypassword())) {
                return MessageBox.build("0", "交易密码不能为空");
            }
            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 验证用户是否已经开通账户
            BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
            if (baseEBankInfo == null || baseEBankInfo.getStatus() == 0) {
                return MessageBox.build("0", "请先开通存管账户");
            }
            // 非E户不能进行提现
            if (baseEBankInfo.getCardtype().compareTo(1) != 0) {
                return MessageBox.build("0", "仅开通E户方可进行提现");
            }
            if (baseEBankInfo.getStatus().compareTo(2) == 0) {
                return MessageBox.build("0", "已解绑用户无法进行提现");
            }

            // 查询用户信息
            MemberCnd memberCnd = new MemberCnd();
            memberCnd.setId(shiroUser.getUserId());
            MemberVo member = memberService.queryMemberByCnd(memberCnd);
            // 交易密码验证
            if (!MD5.toMD5(takeCashVo.getPaypassword()).equals(member.getPaypassword())) {
                return MessageBox.build("0",
                        "您的交易密码输入错误！【如果您登录后修改过交易密码，请重新登录后再试！】");
            }

            // 验证本系统中资金余额是否与浙商银行中一致
            result = verifyAccountBalance(baseEBankInfo, 2);
            if (!"success".equals(result)) {
                return MessageBox.build("0", result);
            }
            // 进行提现
            result = toSaveTakeCash(baseEBankInfo, takeCashVo);
            if (!"success".equals(result)) {
                logger.error("提现失败：" + result);
                return MessageBox.build("0", result);
            }

        } catch (Exception e) {
            logger.error("提现异常", e);
            return MessageBox.build("0", "提现失败");
        }
        return MessageBox.build("1", "提现成功");
    }

    /**
     * <p>
     * Description:浙商银行发送短信接口<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月21日
     */
    @RequestMapping("/sendMessage/{smsType}")
    @RequiresAuthentication
    @ResponseBody
    public MessageBox sendMessage(@PathVariable(value = "smsType") String smsType) {
        try {
            // 判断当前用户是否能发送短信
            String result = this.validateBase(Integer.parseInt(smsType));
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return MessageBox.build("0", result);
            }
            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 验证用户是否已经开通账户
            BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
            if (baseEBankInfo == null || baseEBankInfo.getStatus() == 0) {
                return MessageBox.build("0", "请先开通存管账户");
            }
            // 充值：非E户不能发短信
            if ("4".equals(smsType) && baseEBankInfo.getCardtype().compareTo(1) != 0) {
                return MessageBox.build("0", "仅开通E户方可发短信");
            }
            if (baseEBankInfo.getStatus().compareTo(2) == 0) {
                return MessageBox.build("0", "已解绑用户无法发短信");
            }
            // 查询用户手机号，用于组装报文
            MemberVo memberVo = memberMapper.queryUserBaseInfo(userId);
            String mobile = memberVo.getMobile();
            String relateNo = getRelateNo();
            SsReq ssReq = new SsReq();
            ssReq.setMobile(mobile);
            ssReq.setSmsType(smsType);
            FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                    .create()
                    .setModel(ssReq)
                    .aliasMode("SSReq");
            String signedXml = proxy.toSignedXML();
            // 调用接口前记录
            this.addMessageRecord(1, "13", signedXml, relateNo, userId, "发短信请求");
            // 请求发送短信接口
            FinanceFactory.FinanceResponse res = proxy.send();
            // 接口返回后记录
            this.addMessageRecord(2, "13", res.getResponse(), relateNo, userId, "发短信响应");
            if (res.isHasError()) {
                return MessageBox.build("0", res.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("发送短信异常", e);
            return MessageBox.build("0", "发送短信失败");
        }
        return MessageBox.build("1", "发送成功");
    }

    /**
     * 调用浙商银行接口进行提现
     */
    private String toSaveTakeCash(BaseEBankInfo bankInfo, TopupVo topupVo) throws Exception {
        Integer userId = bankInfo.getUserId();
        String bizNo = getBizNo();
        WdReq wdReq = new WdReq();
        wdReq.setOrderNo(bizNo);
        // 提现金额转化为分
        wdReq.setAmount(topupVo.getAmount()
                .multiply(BigDecimal.valueOf(100))
                .toBigInteger().toString());
        wdReq.setBindSerialNo(bankInfo.getBindNo());
        wdReq.setMobileCode(topupVo.getMobileCode());
        FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                .create()
                .setModel(wdReq)
                .aliasMode("WDReq");
        String signedXml = proxy.toSignedXML();
        String relateNo = getRelateNo();
        // 调用接口前记录
        this.addMessageRecord(1, "4", signedXml, relateNo, userId, "提现请求");
        // 请求提现接口
        FinanceFactory.FinanceResponse res = proxy.send();
        // 接口返回后记录
        this.addMessageRecord(2, "4", res.getResponse(), relateNo, userId, "提现响应");
        if (res.isHasError()) {
            return res.getErrorMsg();
        }
        // 新增提现记录
        this.addCashRecord(bankInfo, bizNo, userId, topupVo);
        // 保存提现信息
        return czBankAccountService.saveTakeCash(res, 1, bizNo, currentRequest().getRemoteHost());
    }

    /**
     * 调用浙商银行接口进行充值
     */
    private String toTopup(BaseEBankInfo bankInfo, TopupVo topupVo) throws Exception {
        Integer userId = bankInfo.getUserId();
        String bizNo = getBizNo();
        String orderNo = getOrderNo(userId);
        DpReq dpReq = new DpReq();
        dpReq.setOrderNo(bizNo);
        // 充值金额转化为分
        dpReq.setAmount(topupVo.getAmount()
                .multiply(BigDecimal.valueOf(100))
                .toBigInteger().toString());
        dpReq.setBindSerialNo(bankInfo.getBindNo());
        dpReq.setMobileCode(topupVo.getMobileCode());
        FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                .create()
                .setModel(dpReq)
                .aliasMode("DPReq");
        String signedXml = proxy.toSignedXML();
        String relateNo = getRelateNo();
        // 调用接口前记录
        this.addMessageRecord(1, "3", signedXml, relateNo, userId, "充值请求");
        // 请求充值接口
        FinanceFactory.FinanceResponse res = proxy.send();
        // 接口返回后记录
        this.addMessageRecord(2, "3", res.getResponse(), relateNo, userId, "充值响应");
        if (res.isHasError()) {
            return res.getErrorMsg();
        }
        // 新增充值记录
        this.addRechargeRecord(bankInfo, orderNo, bizNo, userId, topupVo);
        // 保存充值信息
        return czBankAccountService.saveTopup(res, 1, bizNo,
                currentRequest().getRemoteHost());
    }

    /**
     * 新增充值记录
     */
    private void addRechargeRecord(BaseEBankInfo bankInfo, String orderNo, String bizNo,
                                   Integer userId, TopupVo topupVo) throws Exception {
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setTradeNo(orderNo);
        rechargeRecord.setBizNo(bizNo);
        rechargeRecord.setUserId(userId);
        rechargeRecord.setEBankInfoId(bankInfo.getId());
        rechargeRecord.setType(Constants.RECHARGE_TYPE_ONLINE);
        rechargeRecord.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
        rechargeRecord.setMoney(topupVo.getAmount());
        rechargeRecord.setPayment(Constants.ONLINE_TYPE_CZBANK_NAME);
        rechargeRecord.setIsCustody(1);
        // 手续费千分之二,最低1分钱
        BigDecimal fee = new BigDecimal("0.01");
        BigDecimal chargeFee = topupVo.getAmount().divide(new BigDecimal(1000))
                .setScale(4, RoundingMode.DOWN).multiply(new BigDecimal(2));
        if (chargeFee.compareTo(fee) == 1) {
            fee = chargeFee;
        }
        // rechargeRecord.setFee(fee.setScale(2, RoundingMode.UP));// 手续费
        rechargeRecord.setFee(new BigDecimal("0"));// 在线支付没有手续费
        rechargeRecord.setAddip(currentRequest().getRemoteHost());
        rechargeRecord.setAddtime(DateUtils.getTime());
        rechargeRecord.setOnlinetype(Constants.ONLINE_TYPE_CZBANK);// 浙商存管
        rechargeRecord.setVersion(1);
        rechargeRecord.setPlatform(1);
        baseRechargeRecordMapper.insertEntity(rechargeRecord);
    }

    /**
     * 新增提现记录
     *
     * @param bankInfo 银行卡信息
     * @param bizNo    业务流水号
     * @param userId   用户ID
     * @param topupVo  提现信息
     * @throws Exception
     */
    private CashRecord addCashRecord(BaseEBankInfo bankInfo, String bizNo,
                                     Integer userId, TopupVo topupVo) throws Exception {
        // 提现目前没有手续费
        BigDecimal fee = BigDecimal.ZERO;
        CashRecord cashRecord = new CashRecord();
        cashRecord.setAccount(bankInfo.getCustodyBindNo());
        cashRecord.setAddtime(DateUtils.getTime());
        cashRecord.setAddip(currentRequest().getRemoteHost());
        cashRecord.setBank(bankInfo.getBankname());
        cashRecord.setBranch(bankInfo.getCustodyBindName());
        cashRecord.setCnapsCode(null);
        cashRecord.setUserId(userId);
        cashRecord.setStatus(Constants.CASH_RECORD_STATUS_APPLYING);
        cashRecord.setTotal(topupVo.getAmount());
        cashRecord.setFee(fee);
        cashRecord.setCredited(topupVo.getAmount().subtract(fee));
        cashRecord.setVersion(1);
        cashRecord.setIsCustody(1);
        cashRecord.setBizNo(bizNo);
        cashRecord.seteBankinfoId(bankInfo.getId());
        cashRecord.setPlatform(((ShiroUser) SecurityUtils.getSubject()
                .getPrincipal())
                .getPlatform());
        baseCashRecordMapper.insertEntity(cashRecord);
        return cashRecord;
    }

    /**
     * 验证账户余额
     *
     * @param bankInfo 账户信息
     * @return
     */
    private String verifyAccountBalance(BaseEBankInfo bankInfo, Integer type) throws Exception {
        FinanceFactory.FinanceResponse res = this.queryAccount(bankInfo);
        if (res.isHasError()) {
            return res.getErrorMsg();
        }
        // 保存银行反馈的余额信息
        String result = czBankAccountService.saveEBankAccount(res, bankInfo,
                currentRequest().getRemoteHost(), type == 1 ? "进行充值" : "进行提现");
        return result;
    }

    /**
     * 调浙商银行接口查询余额
     *
     * @param bankInfo 银行卡绑定信息
     * @return
     */
    protected FinanceFactory.FinanceResponse queryAccount(BaseEBankInfo bankInfo) {
        String bindSerialNum = bankInfo.getBindNo();
        String accNum = bankInfo.getEcardNo();
        // 组装报文对象
        AqReq aqReq = new AqReq();
        aqReq.setBindSerialNo(bindSerialNum);
        aqReq.setAccNo(accNum);
        FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                .create()
                .setModel(aqReq)
                .aliasMode("AQReq");
        String signedXml = proxy.toSignedXML();
        String relateNo = getRelateNo();
        Integer userId = bankInfo.getUserId();
        // 调用接口前记录
        this.addMessageRecord(1, "5", signedXml, relateNo, userId, "余额查询请求");
        FinanceFactory.FinanceResponse res = proxy.send();
        // 接口返回后记录
        this.addMessageRecord(2, "5", res.getResponse(), relateNo, userId, "余额查询响应");
        return res;
    }

    /**
     * 获取可提金额
     *
     * @param bankInfo 银行卡信息
     * @return 可提金额
     */
    public BigDecimal getDrawMoney(BaseEBankInfo bankInfo) {
        if (bankInfo == null) {
            return BigDecimal.ZERO;
        }
        String bindSerialNum = bankInfo.getBindNo();
        String accNum = bankInfo.getEcardNo();
        // 组装报文对象
        AqReq aqReq = new AqReq();
        aqReq.setBindSerialNo(bindSerialNum);
        aqReq.setAccNo(accNum);
        FinanceFactory.FinanceProxy proxy = FinanceFactory.getInstance()
                .create()
                .setModel(aqReq)
                .aliasMode("AQReq");
        String signedXml = proxy.toSignedXML();
        String relateNo = getRelateNo();
        Integer userId = bankInfo.getUserId();
        // 调用接口前记录
        this.addMessageRecord(1, "5", signedXml, relateNo, userId, "余额查询请求");
        FinanceFactory.FinanceResponse res = proxy.send();
        // 接口返回后记录
        this.addMessageRecord(2, "5", res.getResponse(), relateNo, userId, "余额查询响应");
        if (res.isHasError()) {
            return BigDecimal.ZERO;
        }
        String withdrawAmount = getFirstElementValue(res.getResDocument(), "withdrawAmount");
        return new BigDecimal(withdrawAmount).divide(BigDecimal.valueOf(100));
    }


    /**
     * @param type     1: 主动；2：被动
     * @param relateNo 关联号
     */
    private void addMessageRecord(Integer type, String model, String msg, String relateNo,
                                  Integer userId, String remark) {
        // 接口返回报文记录
        BaseEMessageRecord messageRecord = new BaseEMessageRecord();
        // 开户查询
        messageRecord.setMode(model);
        // 主动
        messageRecord.setType(type);
        messageRecord.setMsg(msg);
        messageRecord.setOptUserid(userId);
        messageRecord.setPlatform(1);
        messageRecord.setRelateNo(relateNo);
        messageRecord.setRemark(remark);
        baseEMessageRecordMapper.insertSelective(messageRecord);
    }

    /**
     * <p>
     * Description:基础验证，四要素等...<br />
     * </p>
     *
     * @return
     * @throws Exception String
     * @author zhaowei
     * @version 0.1 2016年5月18日
     */
    private String validateBase(int type) throws Exception {
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
        if ((memberApproVo == null || memberApproVo.getEmailChecked() == null ||
                memberApproVo.getEmailChecked() != 1) && (memberApproVo.getMobilePassed() == null ||
                memberApproVo.getMobilePassed() != 1)) {
            return ("请先进行邮箱或手机认证！");
        }

        // 检查实名认证
        if (null == memberApproVo.getNamePassed() ||
                memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
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

        Integer blackType = 0;
        if (type == 4) {
            blackType = BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE;
        } else if (type == 5) {
            blackType = BusinessConstants.BLACK_TYPE_CASH;
        } else {
            // 两者其中一个是黑名单都无法开户
            if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
                return "";
            }
            if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_CASH)) {
                return "";
            }
        }
        // 黑名单判断
        if (super.judgeBlackByType(blackType)) {
            return "";
        }
        return result;
    }

    private String getRelateNo() {
        return uuid();
    }

    private String getBizNo() {
        return uuid();
    }

    private String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String getOrderNo(Integer userId) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd" +
                BusinessConstants.CZBANK_CERTID + "HHmmssSSS", Locale.US);
        return sf.format(new Date()) + userId;
    }
}
