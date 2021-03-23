package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.base.entity.Bank;
import com.dxjr.base.entity.BankInfo;
import com.dxjr.base.entity.BankinfoLog;
import com.dxjr.portal.member.vo.BankInfoCnd;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:银行信息业务类<br />
 * </p>
 * 
 * @title BankInfoService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface BankInfoService {

	/**
	 * <p>
	 * Description:根据用户ID,银行账号查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return BankInfoVo
	 */
	public BankInfoVo queryBankInfoByUserIdPrivateKey(Integer memberId, Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年9月3日
	 * @param memberId
	 * @return
	 * @throws Exception List
	 */
	public List<BankInfoVo> queryBankInfosByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:保存银行卡信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月24日
	 * @param bankInfo
	 * @param payPassword
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	// public String saveBankcard(MemberVo memberVo, BankInfo bankInfo, String
	// payPassword, HttpServletRequest request) throws Exception;
	public String saveBankcard(BankInfo bankInfo, String payPassword, String activeCode) throws Exception;
	
	/**
	 * 保存绑定银行卡信息
	 * @author WangQianJin
	 * @title @param bankInfo
	 * @title @param payPassword
	 * @title @param activeCode
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月6日
	 */
	public String saveBankcardNew(BankInfo bankInfo, String payPassword, String activeCode,String bankPhone) throws Exception;
	
	/**
	 * 保存绑定银行卡信息，人工审核
	 * @author WangQianJin
	 * @title @param bankInfo
	 * @title @param payPassword
	 * @title @param activeCode
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月6日
	 */
	public String saveBankcardAppro(BankInfo bankInfo, String payPassword, String activeCode,String bankPhone) throws Exception;

	/**
	 * <p>
	 * Description:发送手机认证短信<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月29日
	 * @param mobile
	 * @param modelName 功能名称
	 * @return
	 * @throws Exception String
	 */
	public String sendBankInfoValidate(String mobile, HttpServletRequest request, MemberVo memberVo, String modelName) throws Exception;

	/**
	 * <p>
	 * Description:删除银行卡记录<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年9月3日
	 * @param userId 用户id
	 * @return
	 * @throws Exception String
	 */
	public String saveDeleteBankinfoById(Integer userId, Integer id) throws Exception;

	public List<Bank> queryProvinceList();

	public List<Bank> queryCityList(String province);

	public List<Bank> queryDistrictList(String city);

	public List<Bank> queryBankList(String district);

	public List<Bank> queryBranchList(String district, String bankCode);

	public List<Bank> queryBranchList(String district, String bankCode, String branchKey);

	public Bank queryBankInfoByCnapsCode(long parseLong);

	/**
	 * <p>
	 * Description:更具code 查询 出多个支行<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年9月2日
	 * @param parseLong
	 * @return List<Bank>
	 */
	public List<Bank> queryBankInfosByCnapsCode(long parseLong);

	/**
	 * <p>
	 * Description:锁定银行卡新增功能<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月24日
	 * @param memberId
	 * @return String
	 */
	public String insertBankCardLock(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:已有的银行卡数量<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月24日
	 * @param memberId
	 * @return Integer
	 */
	public Integer querytBankCardNum(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询银行卡信息日志表-用户锁定的记录<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月24日
	 * @param memberId
	 * @return BankinfoLog
	 */
	public BankinfoLog querytBankCardLogLock(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询银行卡操作日志中的锁定记录(type=0的记录)<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年11月25日
	 * @param userId
	 * @return Integer
	 */
	public int querytBankCardLock(Integer userId);

	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param bankInfoCnd
	 * @return
	 * @throws Exception List<BankInfoVo>
	 */
	public List<BankInfoVo> queryBankInfoList(BankInfoCnd bankInfoCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据id更新银行卡状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月1日
	 * @param verifyStatus
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateBankInfoStatus(Integer id, Integer verifyStatus) throws Exception;

	/**
	 * <p>
	 * Description:查询用户银行卡信息<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月8日
	 * @param userId
	 * @return
	 * @throws Exception BankInfoVo
	 */
	public BankInfoVo getUserCurrentCard(int userId) throws Exception;
	
	/**
	 * 获取当前用户银行卡（包含审核中）
	 * @author WangQianJin
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月22日
	 */
	public BankInfoVo getUserCurrentCardAppro(int userId) throws Exception;
}
