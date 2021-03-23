package com.dxjr.portal.fix.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.Percentage;
import com.dxjr.portal.fix.vo.DoubleElevenVo;
import com.dxjr.portal.fix.vo.FixActivityCnd;
import com.dxjr.portal.fix.vo.FixActivityVo;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.fix.vo.FixBorrowOpenCnd;
import com.dxjr.portal.fix.vo.FixBorrowStaticVo;
import com.dxjr.portal.fix.vo.FixBorrowVo;

/**
 * <p>
 * Description:定期宝信息接口类<br />
 * </p>
 * @title FixBorrowService.java
 * @package com.dxjr.portal.fix1.service
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixBorrowService {

	/**
	 * 统计查询预发的，立即加入，收益中，已退出的认购总金额
	 * @return
	 */
	public BigDecimal queryTotalAccountYes() throws Exception;

	/**
	 * 统计查询正在投标中的金额
	 * @return
	 */
	public String queryTotalAccountInUseRate() throws Exception;

	/**
	 * 根据定期宝id查询定期宝信息
	 * @param id
	 * @return
	 */
	public FixBorrowVo getFixBorrowById(Integer id) throws Exception;

	/**
	 * 分页查询定期宝信息
	 * @param fixBorrowCnd
	 * @param page
	 * @return
	 */
	public Page queryFixBorrowVoList(FixBorrowCnd fixBorrowCnd, Page page) throws Exception;

	/**
	 * 开通(认购)定期宝
	 * @param userId
	 * @param fixtBorrowOpenCnd
	 * @return
	 */
	public String saveTenderFixtBorrow(Integer userId, FixBorrowOpenCnd fixtBorrowOpenCnd, Integer platForm, String ip) throws Exception;

	/**
	 * 更新账户定额信息
	 * @param accountVo
	 */
	public void packegeUpdateAccount(FixTenderDetail fixTenderDetail) throws Exception;

	/**
	 * 更新定期宝实际认购总额和认购次数
	 * @param fixTenderDetail
	 */
	public void packageUpdateFixBorrow(FixTenderDetail fixTenderDetail) throws Exception;

	/**
	 * 查询用户定期宝的信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月21日
	 * @param curAccountCnd
	 * @return
	 * @throws Exception List<Map>
	 */
	public List<FixBorrowStaticVo> queryFixAccountInfoMap(FixBorrowCnd fixBorrowCnd) throws Exception;

	/**
	 * 根据用户ID获取分类定期宝总额
	 * <p>
	 * Description:根据用户ID获取分类定期宝总额<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月19日
	 * @param fixBorrowCnd
	 * @return 各定期宝分类、总额
	 * @throws Exception
	 * List<Map>
	 */
	public Percentage querySumAccountByLimit(FixBorrowCnd fixBorrowCnd) throws Exception;
	/**
	 * 首页取数，取第一个月的数据
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月25日
	 * @param fixBorrowCnd
	 * @return List<Map>
	 */
	public List<FixBorrowVo> queryFixBorrowData(FixBorrowCnd fixBorrowCnd) throws Exception;

	/**
	 * 查询锁定中和已结束的定期宝用户加入总金额
	 * @return
	 */
	public BigDecimal querySumAccountYes() throws Exception;

	/**
	 * 定期宝满宝复审操作
	 * @param fixBorrowOpenCnd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateForReCheck(FixBorrowOpenCnd fixBorrowOpenCnd, HttpServletRequest request, Integer platForm) throws Exception;

	/**
	 * 查看协议
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年6月9日
	 * @param borrowId
	 * @param contextPath
	 * @param userId
	 * @return
	 * @throws Exception String
	 */
	public String queryBorrowProtocol(Integer fix_borrow_id, String contextPath, Integer userId, String money) throws Exception;

	/**
	 * 查询定期宝加入次数，收益次数，退出次数
	 */
	public FixBorrowStaticVo queryFixStatusCount(FixBorrowCnd fixBorrowCnd) throws Exception;

	/**
	 * 查询定期宝用户最大可投金额
	 */
	public BigDecimal queryRemainAccountByUserId(FixBorrowCnd fixBorrowCnd) throws Exception;

	/**
	 * 查询用户的最大可投定期宝有效金额
	 * @author WangQianJin
	 * @title @param firstBorrow
	 * @title @param userId
	 * @title @param money
	 * @title @param account
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月24日
	 */
	public BigDecimal getMaxeffectiveMoneyForTenderReal(FixBorrowVo fixBorrowVo, Integer userId, BigDecimal money, AccountVo account) throws Exception;

	/**
	 * 计算开通的最大有效金额
	 * @author WangQianJin
	 * @title @param fixBorrowVo
	 * @title @param userId
	 * @title @param money
	 * @title @param account
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月26日
	 */
	public BigDecimal isEffectiveMoneyForTenderReal(FixBorrowVo fixBorrowVo, Integer userId, BigDecimal money, AccountVo account) throws Exception;

	/**
	 * 定期宝自动发起转让
	 * @author WangQianJin
	 * @title @param id
	 * @title @param ip
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	public String saveTransfer(Integer id, String ip) throws Exception;
	
	/**
	 * 根据锁定期限获取双十一活动列表
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
