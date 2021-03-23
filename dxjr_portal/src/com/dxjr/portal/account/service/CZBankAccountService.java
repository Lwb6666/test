package com.dxjr.portal.account.service;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.common.custody.FinanceFactory;

/**
 * <p>
 * Description:浙商银行账户操作service<br/>
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016年5月19日
 * @title CZBankAccountService.java
 * @package com.dxjr.portal.account.service
 */
public interface CZBankAccountService {

    /**
     * <p>
     * Description: 保存开户绑定信息<br />
     * </p>
     *
     * @param res
     * @param userId
     * @return
     * @throws Exception BigDecimal
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    public void saveEBankInfo(FinanceFactory.FinanceResponse res, Integer userId) throws Exception;

    /**
     * <p>
     * Description: 保存开户绑定信息<br />
     * </p>
     *
     * @param res
     * @return
     * @throws Exception BigDecimal
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    public void saveEBankInfo(String res) throws Exception;

    /**
     * <p>
     * Description: 保存账户信息<br />
     * </p>
     *
     * @param res
     * @return
     * @throws Exception BigDecimal
     * @author zhaowei
     * @version 0.1 2016年5月19日
     */
    public String saveEBankAccount(FinanceFactory.FinanceResponse res,
                                   BaseEBankInfo bankInfo, String ip, String scene) throws Exception;

    /**
     * <p>
     * Description: 保存充值信息<br />
     * </p>
     *
     * @param res
     * @return
     * @throws Exception BigDecimal
     * @author zhaowei
     * @version 0.1 2016年5月21日
     */
    String saveTopup(FinanceFactory.FinanceResponse res, Integer type,
                     String bizNo, String ip) throws Exception;

    /**
     * <p>
     * Description: 保存提现信息<br />
     * </p>
     *
     * @param res
     * @return
     * @throws Exception BigDecimal
     * @author zhaowei
     * @version 0.1 2016年5月31日
     */
    public String saveTakeCash(FinanceFactory.FinanceResponse res, Integer type,
                               String bizNo, String ip) throws Exception;

}
