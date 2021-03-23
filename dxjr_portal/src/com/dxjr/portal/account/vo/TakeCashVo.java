package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description: 提现请求参数<br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/20
 * @title dxjr_interface
 * @package com.dxjr.portal.account.vo
 */
public class TakeCashVo extends TopupVo {

    /**
     * 交易密码
     */
    private String paypassword;

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }
}
