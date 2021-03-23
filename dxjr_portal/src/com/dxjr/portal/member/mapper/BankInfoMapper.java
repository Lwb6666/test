package com.dxjr.portal.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.Bank;
import com.dxjr.portal.member.vo.BankInfoCnd;
import com.dxjr.portal.member.vo.BankInfoVo;

/**
 * <p>
 * Description:银行信息数据访问类<br />
 * </p>
 * @title BankInfoMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface BankInfoMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param bankInfoCnd
	 * @return
	 * @throws Exception List<BankInfoVo>
	 */
	public List<BankInfoVo> queryBankInfoList(BankInfoCnd bankInfoCnd);

	/**
	 * <p>
	 * Description:根据用户ID,银行编号查询对象<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @param cnapsCode
	 * @return BankInfoVo
	 */
	public BankInfoVo queryBankInfoByUserIdCardNum(@Param("userId") Integer userId, @Param("cardNum") String cardNum) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param BankInfoCnd bankInfoCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBankInfoCount(BankInfoCnd bankInfoCnd) throws Exception;

	/**
	 * <p>
	 * Description:查询用户的银行卡数量<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2014年11月19日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryBankCardCountByUserId(@Param("userId") Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:删除银行卡记录<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param account
	 * @return
	 * @throws Exception int
	 */
	public int deleteBankinfoById(@Param("id") Integer id) throws Exception;

	public Bank queryBankByCnapsCodeAndBranch(@Param("cnapsCode") Long cnapsCode, @Param("branch") String branch);

	public int countCardByCardNum(@Param("cardNum") String cardNum);

	public List<Bank> queryProvinceList();

	public List<Bank> queryCityList(@Param("province") String province);

	public List<Bank> queryDistrictList(@Param("city") String city);

	public List<Bank> queryBankList(@Param("district") String district);

	public List<Bank> queryBranchList(@Param("district") String district, @Param("bankCode") String bankCode);

	public List<Bank> queryBranchListLike(@Param("district") String district, @Param("bankCode") String bankCode, @Param("branchKey") String branchKey);

	public List<Bank> queryBankInfosByCnapsCode(long parseLong);

	/**
	 * <p>
	 * Description:根据用户更新支付时的签约协议号<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2015年3月28日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateNoAgreeByUserId(@Param("userId") Integer userId, @Param("noAgree") String noAgree) throws Exception;

	public BankInfoVo getUserCurrentCard(int userId) throws Exception;
	
	public BankInfoVo getUserCurrentCardAppro(int userId) throws Exception;	

	/**
	 * <p>
	 * Description:根据id更新银行卡状态<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2015年4月1日
	 * @param verifyStatus
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateBankInfoStatus(@Param("id") Integer id, @Param("verifyStatus") Integer verifyStatus) throws Exception;
}
