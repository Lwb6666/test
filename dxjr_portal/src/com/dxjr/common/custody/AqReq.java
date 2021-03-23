package com.dxjr.common.custody;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <p>
 * Description: 余额查询请求<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/18
 * @title dxjr_interface
 * @package com.dxjr.common.custody
 */
@XStreamAlias("AQReq")
public class AqReq extends BaseReq {

    @XStreamAsAttribute
    private String id = "AQReq";

    private String bindSerialNo;

    @XStreamAlias("AccNo")
    private String accNo;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBindSerialNo() {
        return bindSerialNo;
    }

    public void setBindSerialNo(String bindSerialNo) {
        this.bindSerialNo = bindSerialNo;
    }
}
