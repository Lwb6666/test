package com.dxjr.portal.lottery.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.portal.lottery.vo.LotteryGoodsVo;

public interface LotteryGoodsMapper {

    LotteryGoodsVo selectByPrimaryKey(Integer id);

    /**
     * 
     * <p>
     * Description:获取8种一级奖品信息（用户系统抽奖）<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param lotteryChanceRuleInfoId
     * @return
     * @throws Exception
     * LotteryGoodsVo
     */
    public List<LotteryGoodsVo> selectLotteryGoodsByLotteryChanceRuleInfoId(Integer lotteryChanceRuleInfoId) throws Exception;
   
    /**
     * 
     * <p>
     * Description:获取8种一级奖品总中奖概率（用户系统抽奖）<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param lotteryChanceRuleInfoId
     * @return
     * @throws Exception
     * BigDecimal
     */
    public BigDecimal queryTotalChanceByLotteryChanceRuleInfoId(Integer lotteryChanceRuleInfoId) throws Exception;
    
    /**
     * 
     * <p>
     * Description:获取某一级奖品的二级奖品信息（用户系统抽奖）<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param goodsId
     * @return
     * @throws Exception
     * List<LotteryGoodsVo>
     */
    public List<LotteryGoodsVo> selectLotteryGoodsByParentId(Integer goodsId) throws Exception;
    
    /**
     * 
     * <p>
     * Description:获取某一级奖品的二级奖品的总中奖概率（用户系统抽奖）<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param goodsId
     * @return
     * @throws Exception
     * BigDecimal
     */
    public BigDecimal queryTotalChanceByParentId(Integer goodsId) throws Exception;
    
    /**
     * 
     * <p>
     * Description:获取谢谢参与抽奖类型记录<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @return
     * @throws Exception
     * LotteryGoodsVo
     */
    public LotteryGoodsVo selectForThankYou() throws Exception;
}