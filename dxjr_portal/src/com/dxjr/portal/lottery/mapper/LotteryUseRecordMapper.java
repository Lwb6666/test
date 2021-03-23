package com.dxjr.portal.lottery.mapper;

import java.util.Date;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.lottery.vo.LotteryUseRecordCnd;
import com.dxjr.portal.lottery.vo.LotteryUseRecordVo;
import com.dxjr.portal.lottery.vo.Myreward;

public interface LotteryUseRecordMapper {
	LotteryUseRecordVo selectByPrimaryKey(Integer id);
	
	//现金
	public Integer queryLottcount_xj(LotteryUseRecordCnd lotteryUseRecordCnd);
	public List<LotteryUseRecordVo> queryLottList_xj(LotteryUseRecordCnd lotteryUseRecordCnd,Page page);
	
	//更新可用余额
	public int up_xj_money(LotteryUseRecordCnd lotteryUseRecordCnd);
	//根据id 查询奖励使用记录对象
	public LotteryUseRecordVo queryXJById(String id);
	
	
	//实物 ，现金， 剩余几次抽奖机会 
	public List<LotteryUseRecordVo> lottCount_sw(LotteryUseRecordCnd  lotteryUseRecordCnd);
	
	public Integer queryLottcount_sw(LotteryUseRecordCnd lotteryUseRecordCnd);
	public List<LotteryUseRecordVo> queryLottList_sw(LotteryUseRecordCnd lotteryUseRecordCnd,Page page);
	
	 //领取后状态更新 status0-2 
	public int up_lingqu_status(LotteryUseRecordCnd  lotteryUseRecordCnd);
	
	/**
	 * <p>
	 * Description:  确认收货， 更新status,remark <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param lotteryUseRecordCnd
	 * @return
	 * int
	 */
	public int upStatusRemarkByQrsh(LotteryUseRecordCnd  lotteryUseRecordCnd);
	
	//实物 领取查看详情  
	public List<LotteryUseRecordVo> sw_lingqu_detail(LotteryUseRecordCnd  lotteryUseRecordCnd);
	
	
	//奖励记录
	public Integer queryLottcount_jljl(LotteryUseRecordCnd lotteryUseRecordCnd);
	public List<LotteryUseRecordVo> queryLottList_jljl(LotteryUseRecordCnd lotteryUseRecordCnd,Page page);


    
    /**
     * 
     * <p>
     * Description:根据时间查询大于该时间的获奖记录<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月22日
     * @param date
     * @return
     * @throws Exception
     * List<LotteryUseRecordVo>
     */
    public List<LotteryUseRecordVo> selectNewUseRecordByDate(Date date) throws Exception;
    
    
    /**
	 * 
	 * <p>
	 * Description:查询10条最新的大奖记录（IPhone6、1888元）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月22日
	 * @param date
	 * @return
	 * @throws Exception
	 *             List<LotteryUseRecordVo>
	 */
	public List<LotteryUseRecordVo> selectNewUseRecordForMaxGoods() throws Exception;
	//奖励记录
	public Integer queryLottCount_Reward(LotteryUseRecordCnd lotteryUseRecordCnd);
	public List<Myreward> queryLottList_Reward(LotteryUseRecordCnd lotteryUseRecordCnd,Page page);
}