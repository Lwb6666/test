package com.dxjr.common.custody;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * <p>
 * Description: 发送短信请求<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/18
 * @title dxjr_interface
 * @package com.dxjr.common.custody
 */
@XStreamAlias("SSReq")
public class SsReq extends BaseReq {

    @XStreamAsAttribute
    private String id = "SSReq";

    /**
     * 手机号
     */
    @XStreamAlias("Mobile")
    private String mobile;

    /**
     * 短信类型：1、绑定浙商E卡；2、资金冻结；4、充值；5、提现；6、转让；7、自动投标设置；8、解绑；9、修改手机号码
     */
    @XStreamAlias("SmsType")
    private String smsType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
}
