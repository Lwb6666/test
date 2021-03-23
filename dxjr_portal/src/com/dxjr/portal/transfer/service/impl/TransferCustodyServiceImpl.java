package com.dxjr.portal.transfer.service.impl;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.entity.MessageRecord;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.base.mapper.MessageRecordMapper;
import com.dxjr.common.custody.CustodyBiz;
import com.dxjr.common.custody.Finance;
import com.dxjr.common.custody.Message;
import com.dxjr.common.custody.xml.XmlUtil;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.vo.CommonCollectionVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.portal.transfer.constant.TransferConstant;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.entity.Etransfer;
import com.dxjr.portal.transfer.entity.TransferApproved;
import com.dxjr.portal.transfer.mapper.BSubscribeMapper;
import com.dxjr.portal.transfer.mapper.BTransferMapper;
import com.dxjr.portal.transfer.mapper.BtransferApprovedMapper;
import com.dxjr.portal.transfer.mapper.EtransferMapper;
import com.dxjr.portal.transfer.service.TransferCustodyService;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.*;
import com.dxjr.portal.util.StringUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.UUIDGenerator;
import com.dxjr.utils.exception.AppException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dxjr.portal.statics.CGBusinessConstants.CURRENCY_RMB;


@Service
public class TransferCustodyServiceImpl implements TransferCustodyService {

    Logger logger = LoggerFactory.getLogger(TransferCustodyServiceImpl.class);


    @Autowired
    BTransferMapper bTransferMapper;

    @Autowired
    BtransferApprovedMapper btransferApprovedMapper;

    @Autowired
    CollectionRecordService collectionRecordService;

    @Autowired
    BSubscribeMapper bSubscribeMapper; // 最好使用service

    @Autowired
    FirstTransferBorrowMapper firstTransferBorrowMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;

    @Autowired
    private TransferService transferService;
    @Autowired
    private EtransferMapper etransferMapper;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private MessageRecordMapper messageRecordMapper;


    public String saveCustodyTransfer(SubscribeTransferVo stf, Integer userId, String realIpAddr,
                                      Integer tenderTypeManual, Integer checkUserId,
                                      String checkRemark, String addIp, ShiroUser shiroUser) throws Exception {
        String result = BusinessConstants.SUCCESS;
        String mobileCode = stf.getMobileCode();//测试用

        logger.info("---------执行存管债权转让认购存储过程---------");
        saveManualCustodySubscribe(stf, userId, realIpAddr, tenderTypeManual);

        logger.info("---------执行存管债权转让复审存储过程---------");
        saveApproveCustodyTransferRecheck(stf.getTransferid(), checkUserId, checkRemark, addIp, shiroUser.getPlatform());

        BaseEBankInfo baseEBankInfoSubscribe = baseEBankInfoMapper.selectByUserId(userId);//债权认购人
        BTransfer bTransfer = transferService.getTransferDetailById(stf.getTransferid());
        BaseEBankInfo baseEBankInfoTranfer = baseEBankInfoMapper.selectByUserId(bTransfer.getUserId());//债权转让人
        Etransfer etransfer = etransferMapper.selectByTransferId(stf.getTransferid());
        BorrowVo borrowVo = borrowService.selectByPrimaryKey(bTransfer.getBorrowId());

        //上报项校验
        if(baseEBankInfoSubscribe == null || bTransfer ==null || baseEBankInfoTranfer ==null
                || etransfer ==null || borrowVo==null){
            throw new Exception("数据上报前对象校验未通过，没有找到对象。");
        }

        String eProjectId=borrowVo.geteProjectId();
        String subBindNo=baseEBankInfoSubscribe.getBindNo();
        String transBindNo=baseEBankInfoTranfer.getBindNo();


        //上报必输项校验
        if(StringUtils.isEmpty(eProjectId) || StringUtils.isEmpty(subBindNo)
                || StringUtils.isEmpty(transBindNo) ||  StringUtils.isEmpty(mobileCode)
                || StringUtils.isEmpty(etransfer.geteInvestNo())){
            throw new Exception("数据上报必输项不能为空。");
        }

        //组装报文
        String requestXML = createPPTReq(etransfer, eProjectId,subBindNo, transBindNo, mobileCode);
        //报文签名
        String requestXMLSign =  XmlUtil.sign(requestXML,CGBusinessConstants.PPTReq);
        //生成请求响应关联号
        String relateNo=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
        //保存请求
        insertMsg(requestXMLSign,"20",CGBusinessConstants.MSG_TYPE_ONE,shiroUser,"存管债权转让-请求",relateNo);

        String respones;
        try {
            respones = XmlUtil.send(requestXMLSign);
        } catch (ConnectTimeoutException e) {
            throw new Exception("请求超时");
        } catch (Exception e) {
            throw new Exception("请求异常");
        }
        //保存响应
        insertMsg(respones,"20",CGBusinessConstants.MSG_TYPE_TWO,shiroUser,"存管债权转让-响应",relateNo);
        //响应后处理
        savePost(respones);
        return result;
    }

    private String saveManualCustodySubscribe(SubscribeTransferVo stf, Integer userId, String realIpAddr,
                                              Integer tenderTypeManual) throws Exception {
        String result = BusinessConstants.SUCCESS;
        // 查询债转记录并锁定
        BTransferVo bTransferVo = bTransferMapper.selectByIdForUpdate(stf.getTransferid());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transferid", bTransferVo.getId());
        params.put("tenderMoney", stf.getTenderMoney());
        params.put("memberId", userId);
        params.put("addip", realIpAddr);
        params.put("tenderType", tenderTypeManual);
        params.put("platform", stf.getPlatform());
        bTransferMapper.saveManualCustodySubscribe(params);

        String msg = params.get("msg").toString();

        if ("0000".equals(msg)) {
            throw new Exception("存管债权转让认购存储过程，执行失败");
        } else if ("00002".equals(msg)) {
            throw new Exception("存管债权转让认购存储过程，验证认购金额错误");
        } else if ("00003".equals(msg)) {
            throw new Exception("存管债权转让认购存储过程，验证认购金额超过标剩余金额,请重新操作");
        } else if ("00004".equals(msg)) {
            throw new Exception("存管债权转让认购存储过程，验证账户余额小于投标金额,请重新操作");
        } else if ("00005".equals(msg)) {
            throw new Exception("认购超过最大认购额度,请重新操作");
        }

        return result;

    }


    public String saveApproveCustodyTransferRecheck(Integer transferId, Integer checkUserId, String checkRemark, String addIp, Integer platForm)
            throws Exception {

        BTransferVo transferVo = bTransferMapper.selectByIdForUpdate(transferId);
        if (!transferVo.getStatus().equals(Constants.TRANSFER_FULL_RECHECK) || !transferVo.getAccountReal().equals(transferVo.getAccountYes())) {
            return "存管债权转让状态不是满标复审中";
        }
        // 查询已投标总额 (logic for leader)
        BigDecimal total = bTransferMapper.querySubscribeTotalByTransferId(transferId);
        if (null == total || !total.equals(transferVo.getAccountReal())) {
            return "存管债权转让状态不是满标复审中";
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transferId", transferId);
        params.put("addIp", addIp);
        params.put("checkUserId", checkUserId);
        params.put("checkRemark", checkRemark);
        params.put("platForm", platForm);
        bTransferMapper.saveApproveCustodyTransferRecheck(params);
        String msg = params.get("msg").toString();
        if (!"00001".equals(msg)) {
            throw new AppException("存管债权转让复审失败");
        }
        return BusinessConstants.SUCCESS;
    }

    /**
     *
     * @param etransfer
     * @param eProjectId 项目编号
     * @param subBindNo 受让人绑定协议号
     * @param transBindNo 转让人绑定协议号
     * @param mobileCode 手机验证码
     * @return
     */
    public String createPPTReq(Etransfer etransfer,String eProjectId,
                               String subBindNo,String transBindNo,String mobileCode){
        XStream xstream = new XStream();
        xstream = new XStream(new DomDriver());

        Finance finance = new Finance();
        Message message = new Message();
        message.setId(UUIDGenerator.generate(CGBusinessConstants.MSGID));

        EtransferBiz custodyBiz = new EtransferBiz();
        custodyBiz.setVersion(BusinessConstants.CZBANK_VERSION);//版本号
        custodyBiz.setInstId(BusinessConstants.CZBANK_INSTID); //机构标识
        custodyBiz.setCertId(BusinessConstants.CZBANK_CERTID);//数字证书标识
        custodyBiz.setDate(DateUtils.formatDateForCustody());//发送日期时间 yyyyMMdd HH:mm:ss

        custodyBiz.setProjectId(eProjectId); //项目ID
        custodyBiz.setOutBindSerialNo(transBindNo);//转让人绑定协议号
        custodyBiz.setOriInvestmentSerialNo(etransfer.geteInvestNo());//转让份额原投资流水号
        custodyBiz.setInBindSerialNo(subBindNo);//受让人绑定协议号
        custodyBiz.setOrderNo(etransfer.getSubscribeId()+"");//转让订单号

        custodyBiz.setPortion(1);//转让份额
        custodyBiz.setAmount(etransfer.getAmount().multiply(new BigDecimal(100)).intValue());//转让金额
        custodyBiz.setCoupon(0);//现金券
        custodyBiz.setFee(etransfer.getManageFee().multiply(new BigDecimal(100)).intValue());//转让手续费
        custodyBiz.setCurrency(CGBusinessConstants.CURRENCY_RMB);//币种
        custodyBiz.setMobileCode(mobileCode);
        custodyBiz.setAllFlag("1");//是否已完全转让 0未全部转让 1已全部转让
        custodyBiz.setExtension("存管债转"+etransfer.getSubscribeId());

        message.setMode(custodyBiz);
        finance.setMessage(message);

        xstream.autodetectAnnotations(true);
        xstream.aliasField(CGBusinessConstants.PPTReq, Message.class, "Mode");
        xstream.addDefaultImplementation(EtransferBiz.class, CustodyBiz.class);
        return  xstream.toXML(finance);
    }
    /**
     *
     * <p>
     * Description:记录报文<br />
     * </p>
     * @version 0.1 2016年5月21日
     * @param reqXml
     * @param mode
     * @param type
     * @param shiroUser
     * @param remark
     * @param relateNo
     * void
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertMsg(String reqXml, String mode, int type, ShiroUser shiroUser, String remark, String relateNo){
        //记录项目登记日志
        MessageRecord messageRecord=new MessageRecord();
        messageRecord.setMode(mode);//场景
        messageRecord.setType((byte) type);//0:主动，1:被动
        messageRecord.setMsg(reqXml);//报文体
        messageRecord.setOrderNo(shiroUser.getUserId().toString());
        messageRecord.setOptUserid(shiroUser.getUserId());
        messageRecord.setPlatform(shiroUser.getPlatform());
        messageRecord.setRemark(remark);
        messageRecord.setRelateNo(relateNo);//调用关联号
        messageRecordMapper.insert(messageRecord);
    }

    /**
     * 响应后置处理
     */
    public void savePost(String responesXML) throws Exception {

        //验签
        boolean istrue = XmlUtil.checkYq(responesXML);
        if (!istrue) {
            throw new Exception("验签失败");
        }
        //判断响应报文
        boolean isError = XmlUtil.checkXml(responesXML);
        if (isError) {
            Map<String, Object> maperr = XmlUtil.toError(responesXML);
            String errorCode = (String) maperr.get("errorCode");
            String errorDetail = (String) maperr.get("errorDetail");
            String err = "银行响应异常：[" + errorCode + "]" + errorDetail;
            throw new Exception(err);
        }

    }
}