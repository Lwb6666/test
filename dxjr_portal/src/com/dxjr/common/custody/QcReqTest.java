package com.dxjr.common.custody;

import org.w3c.dom.Document;

import java.util.Date;

/**
 * <p>
 * Description: <br />
 * </p>
 *
 * @author zhaowei
 * @version 0.1 2016/5/18
 * @title dxjr_interface
 * @package com.dxjr.common.custody
 */
public class QcReqTest {

    public static void main(String[] args) {
        QcReq qcReq = new QcReq();
        qcReq.setCstno("1");
        qcReq.setDate(new Date());

        FinanceFactory.FinanceResponse res = FinanceFactory.getInstance()
                .create()
                .setModel(qcReq)
                .aliasMode("QCReq")
                .handler(FinanceFactory.getInstance().new FinanceHandler() {
                    @Override
                    protected void onResponse(FinanceFactory.FinanceResponse res) {
                        if (res.isHasError()) {
                            System.out.println(res.getErrorCode());
                            System.out.println(res.getErrorMsg());
                        } else{
                            System.out.println(res.getResponse());
                        }
                        res.addObject("test", "haha");
                    }
                }).send();

        System.out.println(res.getObject("test"));
    }
}
