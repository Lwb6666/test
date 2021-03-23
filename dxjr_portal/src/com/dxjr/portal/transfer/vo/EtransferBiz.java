package com.dxjr.portal.transfer.vo;

import com.dxjr.common.custody.CustodyBiz;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by Administrator on 2016/6/6.
 */
public class EtransferBiz extends CustodyBiz {

    @XStreamAsAttribute
    String id = "PPTReq";
    public String version;
    public String instId;
    public String certId;
    public String date;
    public String ProjectId;
    public String outBindSerialNo;//转让人绑定协议号
    public String oriInvestmentSerialNo;//转让份额原投资流水号
    public String inBindSerialNo;//受让人绑定协议号
    public String orderNo;//平台转让订单号
    public Integer portion;//转让份额
    public Integer amount;//转让金额
    public Integer coupon;//现金券
    public Integer fee;//转让手续费
    public String currency;//转让币种
    public String mobileCode;//短信验证码
    public String allFlag;//是否已完全转让
    public String extension;//消息扩展


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getOutBindSerialNo() {
        return outBindSerialNo;
    }

    public void setOutBindSerialNo(String outBindSerialNo) {
        this.outBindSerialNo = outBindSerialNo;
    }

    public String getOriInvestmentSerialNo() {
        return oriInvestmentSerialNo;
    }

    public void setOriInvestmentSerialNo(String oriInvestmentSerialNo) {
        this.oriInvestmentSerialNo = oriInvestmentSerialNo;
    }

    public String getInBindSerialNo() {
        return inBindSerialNo;
    }

    public void setInBindSerialNo(String inBindSerialNo) {
        this.inBindSerialNo = inBindSerialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPortion() {
        return portion;
    }

    public void setPortion(Integer portion) {
        this.portion = portion;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getAllFlag() {
        return allFlag;
    }

    public void setAllFlag(String allFlag) {
        this.allFlag = allFlag;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }






}
