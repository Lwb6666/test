/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title NewPServiceImpl.java
 * @package com.dxjr.portal.newP.service.impl 
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */
package com.dxjr.portal.newP.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.newP.service.NewPService;

/**
 * <p>
 * Description:新手专区BLL<br />
 * </p>
 * 
 * @title NewPServiceImpl.java
 * @package com.dxjr.portal.newP.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */
@Service
public class NewPServiceImpl implements NewPService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private BorrowMapper borrowMapper;

	/**
	 * 
	 * <p>
	 * Description:验证实名认证<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月8日
	 * @param userId
	 * @return 0 : 没认证，1 ：已认证
	 * @throws Exception
	 *             int
	 */
	@Override
	public int validRealName(Integer userId) throws Exception {
		int rst = memberMapper.queryRealNameIspassed(userId);
		return rst;
	}

	
	@Override
	public BorrowVo getAdvancedNew() throws Exception {
		return borrowMapper.getAdvancedNew();
	}

}
