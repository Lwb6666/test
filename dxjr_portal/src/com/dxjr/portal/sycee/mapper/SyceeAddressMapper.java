package com.dxjr.portal.sycee.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.dxjr.portal.sycee.entity.SyceeAddress;

public interface SyceeAddressMapper {

	/**
	 * 根据userId查询用户是否已经登记过联系方式
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月6日
	 * @param userId
	 * @return SyceeAddress
	 */
	SyceeAddress getById(@Param("userId") int userId);

	/**
	 * 添加用户联系方式
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月6日
	 * @param SyceeAddress
	 * @return int
	 */
	int add(SyceeAddress address);

	/**
	 * 修改用户联系方式
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月7日
	 * @param name
	 * @param address
	 * @param phone
	 * @param zip_code
	 * @param user_id
	 * @return int
	 */
	@Update("UPDATE t_sycee_addr SET name=#{name},address=#{address},updatetime=NOW(),phone=#{phone},zip_code=#{zipCode} WHERE user_id=#{userId}")
	@ResultType(Integer.class)
	int updateAddr(@Param("name") String name, @Param("address") String address, @Param("phone") String phone, @Param("zipCode") String zipCode,
			@Param("userId") int userId);
}
