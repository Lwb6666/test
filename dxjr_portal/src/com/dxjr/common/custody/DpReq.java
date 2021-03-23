package com.dxjr.common.custody;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <p>
 * Description: 充值请求<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/18
 * @title dxjr_interface
 * @package com.dxjr.common.custody
 */
@XStreamAlias("DPReq")
public class DpReq extends BaseReq {

    @XStreamAsAttribute
    private String id = "DPReq";

    private String bindSerialNo;

    @XStreamAlias("OrderNo")
    private String orderNo;

    @XStreamAlias("Amount")
    private String amount;

    @XStreamAlias("MobileCode")
    private String mobileCode;

    public String getBindSerialNo() {
        return bindSerialNo;
    }

    public void setBindSerialNo(String bindSerialNo) {
        this.bindSerialNo = bindSerialNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}
