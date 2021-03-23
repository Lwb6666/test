package com.dxjr.portal.fix.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.base.entity.FixBorrow;
import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.DoubleElevenVo;
import com.dxjr.portal.fix.vo.FixActivityCnd;
import com.dxjr.portal.fix.vo.FixActivityVo;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.fix.vo.FixBorrowStaticVo;
import com.dxjr.portal.fix.vo.FixBorrowVo;


/**
 * <p>
 * Description:定期宝信息数据库接口类<br />
 * </p>
 * 
 * @title FixBorrowMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixBorrowMapper {
	
	/**
	 * 统计查询预发的，立即加入，收益中，已退出的认购总金额
	 * @return
	 */
	public BigDecimal queryTotalAccountYes() throws Exception;
	
	/**
	 * 统计查询正在投标中的金额
	 * @return
	 */
	public BigDecimal queryTotalAccountInUse() throws Exception;
	
	/**
	 * 统计查询锁定中的定期宝总金额
	 * @return
	 */
	public BigDecimal queryTotalAccountIsLocked() throws Exception;
	
	/**
	 * 查询定期的总记录数
	 * @return
	 */
	public Integer queryFixBorrowCount(FixBorrowCnd fixBorrowCnd) throws Exception;
	
	/**
	 * 分页查询定期宝信息
	 * @param fixBorrowCnd
	 * @param page
	 * @return
	 */
	public List<FixBorrowVo>  queryFixBorrowVoList(FixBorrowCnd fixBorrowCnd,Page page) throws Exception;

	
	/**
	 * 根据定期宝id查询定期宝信息
	 * @param id
	 * @return
	 */
	public FixBorrowVo getFixBorrowById(Integer id) throws Exception;
	
	
	/**
	 * 统计定期宝认购总金额
	 * @return
	 */
	public BigDecimal totalJoinMoney() throws Exception;
	
	/**
	 * 统计定期宝用户收益总金额
	 * @return
	 */
	public BigDecimal totalInterest() throws Exception;
	
	
	/**
	 * 更新用户账户信息
	 * @param accountVo
	 */
	public void updateAccount(FixTenderDetail fixTenderDetail) throws Exception;
	
	/**
	 * 更新定期宝实际认购总额和认购次数
	 * @param fixTenderDetail
	 */
	public void updateFixBorrow(FixTenderDetail fixTenderDetail) throws Exception;
	
	/**
	 * 更新定期宝状态
	 * @param fixBorrowVo 
	 */
	public void updateFixBorrowStatus(FixBorrowVo fixBorrowVo) throws Exception;
	
	
	/**
	 * 根据定期宝id查询定期宝信息并锁定
	 * @param id
	 * @return
	 */
	public FixBorrowVo getFixBorrowByIdForUpdate(Integer id) throws Exception;
	
	/**
	 * 查询定期宝的账户信息
	 */
	public List<FixBorrowStaticVo> queryFixAccountInfoMap(FixBorrowCnd fixBorrowCnd) throws Exception ;
	
	/**
	 * 
	 * <p>
	 * Description:根据用户ID获取分类定期宝总额<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月19日
	 * @param fixBorrowCnd
	 * @return 各定期宝分类、总额
	 * @throws Exception
	 * List<Map<String,Object>>
	 */
	public List<Map<String, Object>> querySumAccountByLimit(FixBorrowCnd fixBorrowCnd) throws Exception;
	/**
	 * 查询定期宝加入次数，收益次数，退出次数
	 */
	public FixBorrowStaticVo queryFixStatusCount(FixBorrowCnd fixBorrowCnd) throws Exception ;

	
	
	
	/**
	 * 根据定期宝查询条件查询记录
	 * @param id
	 * @return
	 */
	public List<FixBorrowVo> getFixBorrowByCnd(FixBorrowCnd fixBorrowCnd) throws Exception;
	
	/**
	 * 查询锁定中和已结束的定期宝用户加入总金额
	 * @return
	 */
	public BigDecimal querySumAccountYes() throws Exception;
	
	/**
	 * 查询用户已认购的金额
	 * @return
	 */
	public BigDecimal querySumAccountByUserId(FixBorrowCnd fixBorrowCnd) throws Exception;
	
	
	/**
	 * 添加定期宝满宝待发邮件记录
	 * @param id
	 */
	public void insertMailRecord(Integer id);
	
	/**
	 * 查询定期宝用户最大可投金额
	 */
	public BigDecimal queryRemainAccountByUserId(FixBorrowCnd fixBorrowCnd) throws Exception;
	
	/**
	 * 修改定期宝状态
	 * @author WangQianJin
	 * @title @param fixBorrow
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public void updateFixBorrowStatusById(FixBorrow fixBorrow) throws Exception;
	
	/**
	 * 根据定期宝ID查询即将到期的定期宝
	 * @author WangQianJin
	 * @title @param id
	 * @title @return
	 * @date 2015年9月15日
	 */
	public FixBorrowVo queryMatureFixBorrowById(Integer id);
	
	/**
	 * 根据锁定期限获取定期宝投资排名
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年11月9日
	 */
	public List<DoubleElevenVo> queryFixForDoubleElevenList(Integer lockLimit) throws Exception;
	
	/**
	 * 根据用户ID获取投资数量
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月2日
	 */
	public Integer getTenderCountByUserId(Integer userId);
	
	/**
	 * 根据用户ID获取投资新手宝的数量
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月2日
	 */
	public Integer getNewTenderCountByUserId(Integer userId);
	
	/**
	 * 获取新手宝定期宝
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月2日
	 */
	public FixBorrowVo getNewFixBorrow();
	
	public FixBorrowVo getLimitFixBorrow(int limit);
	
	/**
	 * 根据多个期限获取定期宝投资排名
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年1月27日
	 */
	public List<DoubleElevenVo> queryFixForNewYearList(FixActivityCnd cnd) throws Exception;
	
	/**
	 * 查询2月1号以后当月注册并投资的金额大于等于1W的被邀请人信息
	 * @author WangQianJin
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年1月27日
	 */
	public List<FixActivityVo> queryFixForInvitedList(Integer userId) throws Exception;
}
