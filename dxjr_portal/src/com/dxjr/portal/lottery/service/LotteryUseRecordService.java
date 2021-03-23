package com.dxjr.portal.lottery.service;

import java.util.Date;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.lottery.vo.LotteryGoodsSendInfoVo;
import com.dxjr.portal.lottery.vo.LotteryUseRecordCnd;
import com.dxjr.portal.lottery.vo.LotteryUseRecordVo;

/**
 * 
 * <p>
 * Description:抽奖使用记录业务接口<br />
 * </p>
 * 
 * @title LotteryUseRecordService.java
 * @package com.dxjr.portal.lottery.service
 * @author YangShiJin
 * @version 0.1 2015年4月11日
 */
public interface LotteryUseRecordService {

	// 现金
	public LotteryUseRecordVo queryMoneyS_xj(LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception;

	public Page queryPageByLotteryUseRecordCnd_xj(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception;

	public boolean up_xj_money(LotteryUseRecordCnd lotteryUseRecordCnd,String ipStr) throws Exception;

	// 实物
	// 几次抽奖机会
	public LotteryUseRecordVo lottCount_sw(LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception;

	// count ,list
	public Page queryPageByLotteryUseRecordCnd_sw(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception;

	// 实物， 领取， 提交 + //领取后状态更新 status0-2
	public boolean lingqu_inst(LotteryGoodsSendInfoVo lotteryGoodsSendInfoVo, LotteryUseRecordCnd lotteryUseRecordCnd) throws Exception;

	
	/**
	 * <p>
	 * Description: 确认收货， 更新status,remark <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param lotteryUseRecordCnd
	 * @return
	 * @throws Exception
	 * int
	 */
	public int upStatusRemarkByQrsh(LotteryUseRecordCnd  lotteryUseRecordCnd) throws Exception;
	
	//实物 领取查看详情  
	public List<LotteryUseRecordVo> sw_lingqu_detail(LotteryUseRecordCnd  lotteryUseRecordCnd) throws Exception;
		
		
		
	// 奖励记录
	public Page queryPageByLotteryUseRecordCnd_jljl(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据时间查询大于该时间的获奖记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月22日
	 * @param date
	 * @return
	 * @throws Exception
	 *             List<LotteryUseRecordVo>
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

	public Page queryPageByLotteryUseRecordCnd_reward(LotteryUseRecordCnd lotteryUseRecordCnd, Page page) throws Exception;
}
