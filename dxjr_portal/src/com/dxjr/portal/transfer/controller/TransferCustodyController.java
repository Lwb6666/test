package com.dxjr.portal.transfer.controller;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowDetailService;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.service.TransferCustodyService;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SubscribeTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.portal.util.StringUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.UUIDGenerator;
import com.dxjr.utils.exception.AppException;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/6.
 */
@Controller
@RequestMapping(value = "/custody/zhaiquan")
public class TransferCustodyController extends BaseController  {


    Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    RealNameApproService realNameApproService;

    @Autowired
    private LotteryChanceInfoService lotteryChanceInfoService;

    @Autowired
    private BorrowDetailService borrowDetailService;

    @Autowired
    private BankInfoService bankInfoService;
    @Autowired
    private CGUtilService cGUtilService;
    @Autowired
    private TransferCustodyService transferCustodyService;



    /**
     * 进入债权转让详情页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    public ModelAndView totransferdetail(@PathVariable("id") Integer id,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("transfer/transferdetail_cg");

        BigDecimal alsoNeedMoney = new BigDecimal(0);
        AccountVo accountVo = new AccountVo();
        BTransferVo bTransferVo = new BTransferVo();
        try {
            // 查询 转让信息
            BTransfer bTransfer = transferService.getTransferDetailById(id);
            if (bTransfer == null) {
                mv = new ModelAndView("page/common/404");
                return mv;
            }
            mv.addObject("bTransfer", bTransfer);

            // 查询 原始标信息
            Integer borrowId = bTransfer.getBorrowId();
            // 获取借款标详情 copy myaccountcontroller 类 toInvest
            Map<String, ?> resultMap = borrowDetailService.queryBorrowDetailInfo(borrowId);
            if (null == resultMap || null == resultMap.get("borrowDetailVo")) {
                mv = new ModelAndView("page/common/404");
                return mv;
            }
            int isCustody= 0;

            BorrowVo borrowVo =  borrowService.selectByPrimaryKey(borrowId);
            if(borrowVo != null && borrowVo.getIsCustody() !=null && borrowVo.getIsCustody() ==1){
                isCustody=1;
                mv.addObject("isCustody",isCustody);
            }else{
                return redirect("/zhaiquan/"+id+".html");
            }

            ShiroUser shiroUser = currentUser();
            // 投资人账户余额
            if (shiroUser != null) {
                accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());

                MemberCnd memberCnd = new MemberCnd();
                memberCnd.setId(shiroUser.getUserId());
                MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
                if(memberVo.getIsCustody()!=null && memberVo.getIsCustody().intValue() == 1){
                    String relateNo= UUIDGenerator.generate(CGBusinessConstants.RELATENO);
                    String remark="进入债转认购页面，余额查询";
                    //调用存管余额查询接口
                    String rep= cGUtilService.saveCGAccount(shiroUser, relateNo,remark,id);
                    //解析报文
                    Account account= cGUtilService.parseAQReqXml(rep, shiroUser, remark, relateNo,id);
                    if(account==null){
                        logger.error("返回ERROR报文或验签失败");
                        mv = new ModelAndView("page/common/500");
                        return mv;
                    }
                    //平台与存管资金校验
                    String scene="进入债转认购页面，余额查询";//业务发生场景
                    account.setUserId(shiroUser.getUserId());
                    String msg= cGUtilService.savecheckAccount(account, HttpTookit.getRealIpAddr(request), shiroUser.getPlatform(), scene);
                    BaseEBankInfo eUserInfo= cGUtilService.eUserInfo(shiroUser.getUserId());
                   // mv.addObject("useMoney", account.getEUseMoney());
                    mv.addObject("useMoney",  account.getEUseMoney());
                    mv.addObject("mobile", eUserInfo.getMobileStr());
                    mv.addObject("isCustodyMember", 1);

                }else{
                    mv.addObject("useMoney", accountVo.getUseMoney());
                    mv.addObject("isCustodyMember", 0);
                }

                // 获取个人账户余额
                // 债权转让信息
                bTransferVo = transferService.selectByPrimaryKey(id);
                // 剩余可认购金额
                alsoNeedMoney = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
                mv.addObject("alsoNeed", alsoNeedMoney);

                // 个人已认购金额
                TransferCnd transferCnd = new TransferCnd();
                transferCnd.setTransferId(id);
                transferCnd.setUserId(shiroUser.getUserId());
                String sumAccount = transferService.querySumAccountByUserId(transferCnd);
                mv.addObject("sumAccount", sumAccount);

            }

            mv.addObject("nowTime", new Date());
            mv.addObject("loginMember", shiroUser);
            mv.addObject("borrow", resultMap.get("borrowDetailVo"));
            // 待还本息统计数据
            mv.addObject("borrowDetailRepayVo", resultMap.get("borrowDetailRepayVo"));
            // 已还本息总额
            mv.addObject("repaymentYesAccountTotal", resultMap.get("repaymentYesAccountTotal"));
            // 垫付统计数据
            mv.addObject("borrowDetailWebPayVo", resultMap.get("borrowDetailWebPayVo"));
            // 借款者信用档案
            mv.addObject("borrowDetailCreditVo", resultMap.get("borrowDetailCreditVo"));
            // 借款者信息
            mv.addObject("borrower", resultMap.get("borrowerVo"));
            // 借款者抵押信息
            mv.addObject("mortgageVo", resultMap.get("mortgageVo"));
            // 借款者认证信息
            mv.addObject("accountUploadDocs", resultMap.get("accountUploadDocs"));
        } catch (Exception e) {
            logger.error("转让详情", e);
            mv = new ModelAndView("page/common/500");
            return mv;
        }
        return mv;
    }

    /**
     * <p>
     * Description:债权转让-认购<br />
     * </p>
     *
     * @author chenpeng
     * @version 0.1 2014年10月27日
     * @param request
     *            、SubscribeTransferBean
     * @return String
     */
    @RequiresAuthentication
    @RequestMapping(value = "/subscribeTransfer")
    @ResponseBody
    public MessageBox subscribeTransferForCustody(HttpServletRequest request, SubscribeTransferVo stf, String ip) {

        ShiroUser shiroUser = currentUser();
        try {
            if (StringUtils.isEmpty(ip)) {
                ip = HttpTookit.getRealIpAddr(request);
            }
            stf.setPlatform(shiroUser.getPlatform());
            if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_TRANSFER)) {
                return new MessageBox("-99", "");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return new MessageBox("-99", "");
        }
        try {
            // 验证认购数据；
            String result = this.validateCustodyData(shiroUser, stf, request,true);
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return new MessageBox("0", result);
            }
            if(stf.getMobileCode()==null || stf.getMobileCode().length()<=0){
                return MessageBox.build("0", "请输入短信验证码");
            }
            Account zscgAccount = getEBankAccount(shiroUser,stf.getTransferid());
            if(zscgAccount == null){
                return new MessageBox("0", "获取浙商存管账户金额异常");
            }else{
                BigDecimal useMoney =  stf.getUseMoney();
                BigDecimal zgyhUseMoney = zscgAccount.getEUseMoney();
                if(zgyhUseMoney.compareTo(useMoney) !=0 ){
                    return new MessageBox("0", "存管账户可用金额与界面显示金额不一致");
                }
                BigDecimal tenderMoney = stf.getTenderMoney();
                if(tenderMoney.compareTo(zgyhUseMoney) >0 ){
                    return new MessageBox("0", "认购金额不能大于存管银行可用金额");
                }
            }

            transferCustodyService.saveCustodyTransfer(stf, shiroUser.getUserId(), ip,
                    Constants.TENDER_TYPE_MANUAL, Constants.AUTO_CHECK_USERID,
                    Constants.AUTO_CHECK_REMARK, HttpTookit.getRealIpAddr(request),shiroUser);


        } catch (AppException ae) {
            logger.error("手动认购失败", ae.getMessage());
            return new MessageBox("0", ae.getMessage());
        } catch (Exception e) {
            logger.error("手动认购失败", e);
            return new MessageBox("0", "认购操作失败，请联系管理人员！");
        }

        return new MessageBox("1", "认购成功");
    }






    private String validateCustodyData(ShiroUser shiroUser, SubscribeTransferVo stf, HttpServletRequest request,boolean b) throws Exception {

        // 查询用户认证信息
        MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
        if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
                && (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
            return "请先进行邮箱或手机认证！";
        }

        // 检查实名认证
        if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
            return "请先进行实名认证";
        }


        //判断该用户是否是存管账户
        // 查询用户信息
        MemberCnd memberCnd = new MemberCnd();
        memberCnd.setId(shiroUser.getUserId());
        MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
        if(memberVo.getIsCustody() == null || memberVo.getIsCustody().intValue() !=1){
            return "您未开通存管账户!";
        }

        BTransfer bTransfer = transferService.getTransferDetailById(stf.getTransferid());
        BorrowVo borrowVo = borrowService.selectByPrimaryKey(bTransfer.getBorrowId());
        if(borrowVo.getIsCustody()==null || borrowVo.getIsCustody().intValue()  !=1){
            return "非存管标不能调用存管债转服务！";
        }

        String result = BusinessConstants.SUCCESS;
        // 购买金额
        BigDecimal tenderMoney = stf.getTenderMoney();
        BigDecimal sumAccountMoney = new BigDecimal(0);

        if (shiroUser.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
            return "借款用户不允许认购！";
        }
        if (null == tenderMoney) {
            return "购买金额不能为空！";
        }

        // 检查是否绑定了银行卡
        BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
        if (null == bankInfoVo) {
            return "请先绑定银行卡!";
        }

        // 购买金额 | 不能小于最小认购金额，大于可认购金额
        BTransferVo bTransferVo = transferService.selectByPrimaryKey(stf.getTransferid());

        if (shiroUser.getUserId().equals(bTransferVo.getUserId())) {
            return "不能认购本人的债权转让";
        }

        BigDecimal alsoNeed = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
        if (tenderMoney.compareTo(alsoNeed) != 0) { // 最大可认购金额小于最小认购金额时，认购金额必须等于最大可认购金额；
            return "认购存管债转时，购买金额必须等于债转金额！";
        }
        if (tenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
            return "购买金额大于最大认购额度，无法认购！";
        }
        if (stf.getSumAccount() != null) {
            sumAccountMoney = stf.getSumAccount();
        }
        if (sumAccountMoney.add(tenderMoney).compareTo(bTransferVo.getMostAccount()) == 1) {
            return "累计购买金额大于最大认购额度，无法认购！";
        }

        // 账户余额| 不能小于最小认购额度，小于购买金额；
        AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
        BigDecimal useMoney = accountVo.geteUseMoney();

        if (useMoney.compareTo(bTransferVo.getLowestAccount()) == -1 && useMoney.compareTo(stf.getTenderMoney()) == -1) {
            return "账户余额小于可购买金额，无法认购！";
        }

        // 认购密码
        if (b){
            if (bTransferVo.getBidPassword() != null && !bTransferVo.getBidPassword().equals("")) {
                if (null == stf.getBidPassword() || !bTransferVo.getBidPassword().equals(MD5.toMD5(stf.getBidPassword()))) {
                    return "认购密码有误";
                }
            }
        }


        if (bTransferVo.getStatus() != 2) {
            return "非转让中债权不可认购！";
        }

        return result;
    }


    @RequestMapping(value="/sendMobileCode")
    @ResponseBody
    public MessageBox sendMobileCode(HttpServletRequest request,SubscribeTransferVo stf, String ip ){
        MessageBox msg = null;
        try {
            ShiroUser shiroUser = currentUser();
            try {
                if (StringUtils.isEmpty(ip)) {
                    ip = HttpTookit.getRealIpAddr(request);
                }
                stf.setPlatform(shiroUser.getPlatform());
                if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_TRANSFER)) {
                    return new MessageBox("-99", "");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return new MessageBox("-99", "");
            }

            String result = this.validateCustodyData(shiroUser, stf, request,false);
            if (!BusinessConstants.SUCCESS.equals(result)) {
                return new MessageBox("0", result);
            }

            String remark="存管债转短信发送";
            String relateNo=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
            //接口调用
            String res= cGUtilService.saveMobileCode(shiroUser, remark, relateNo,stf.getTransferid());
            //接口响应
            String mode="13";//场景 13:短信发送
            String mg= cGUtilService.saveResXml(res, mode, shiroUser, remark, relateNo,stf.getTransferid());
            if(!mg.equals(BusinessConstants.SUCCESS)){
                logger.error(mg);
                return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
            }
            msg=new MessageBox("1", "短信发送成功");

        } catch (Exception e) {
            logger.error("短信发送失败", e);
            return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
        }

        return msg;

    }


    public Account getEBankAccount(ShiroUser shiroUser,Integer bizId) throws Exception {
        String relateNo= UUIDGenerator.generate(CGBusinessConstants.RELATENO);
        String remark="债转认购金额校验，余额查询";
        //调用存管余额查询接口
        String rep= cGUtilService.saveCGAccount(shiroUser, relateNo,remark,bizId);
        //解析报文
        Account account= cGUtilService.parseAQReqXml(rep, shiroUser, remark, relateNo,bizId);
        return account;
    }

}
