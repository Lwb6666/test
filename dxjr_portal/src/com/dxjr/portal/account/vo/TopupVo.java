package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description: 充值请求参数<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/20
 * @title dxjr_interface
 * @package com.dxjr.portal.account.vo
 */
public class TopupVo implements Serializable {

    /**
     * 充值金额
     */
    private BigDecimal amount;
    /**
     * 短信验证码
     */
    private String mobileCode;

    public  BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}
