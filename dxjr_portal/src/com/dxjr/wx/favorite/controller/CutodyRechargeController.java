package com.dxjr.wx.favorite.controller;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Comments
 * <p>
 * Description :这里写描述<br/>
 * @author wangbo
 * @version 2016/8/4
 * @package com.dxjr.wx.favorite.controller
 */
@Controller
@RequestMapping("/wx/cutodyrecharge")
public class CutodyRechargeController extends BaseController{
    private Logger logger=Logger.getLogger(CutodyRechargeController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private BaseEBankInfoMapper baseEBankInfoMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 存管充值页面初始化
     * <p>
     * Description:这里写描述
     * </p>
     * @return the map
     * @throws Exception the exception
     * @author wangbo
     * @version 2016年08月04日
     */
    @RequestMapping("/init")
    @ResponseBody
    public Map<String, Object> init() {
        Map<String, Object> map=new HashMap();
        try {
            if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_ONLINE_RECHARGE)) {
                map.put("isBlack", true);
                return map;
            } else {
                map.put("isBlack", false);
            }
            ShiroUser shiroUser = currentUser();
            Integer userId = shiroUser.getUserId();
            // 当前用户资金帐号
            AccountVo accountVo = accountService.queryAccountByUserId(userId);
            // 验证用户是否已经开通账户
            BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
            MemberVo memberVo = memberMapper.queryUserBaseInfo(userId);
            map.put("member", memberVo);
            map.put("bankInfo", baseEBankInfo);
            if (baseEBankInfo != null && baseEBankInfo.getStatus() == 1) {
                map.put("isCustody", true);
            } else {
                map.put("isCustody", false);
            }
            map.put("account", accountVo);
        }catch (Exception e){
            logger.error("微信-初始化存管充值失败",e);
        }

        return map;
    }
}
