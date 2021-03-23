package com.dxjr.common.custody;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <p>
 * Description: 单笔交易流水查询请求<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/18
 * @title dxjr_interface
 * @package com.dxjr.common.custody
 */
@XStreamAlias("TSQReq")
public class TsqReq extends BaseReq {

    @XStreamAsAttribute
    private String id = "TSQReq";

    /** 订单号 **/
    private String orderNo;

    /** 类型
     * 1充值 2提现 3冻结 4解冻
     * 5投资 6还款 7转让 8分账
     & 9批量扣款 10批量还款 11流标解冻
     */
    private String type;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
