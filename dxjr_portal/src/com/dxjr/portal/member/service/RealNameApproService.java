package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.dxjr.base.entity.RealNameAppro;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.member.vo.RealNameApproVo;

/**
 * <p>
 * Description:实名认证业务类<br />
 * </p>
 * 
 * @title RealNameApproService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月24日
 */
public interface RealNameApproService {
	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param realNameApproCnd
	 * @return List<RealNameApproVo>
	 */
	public List<RealNameApproVo> queryRealNameApproList(
			RealNameApproCnd realNameApproCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return RealNameApproVo
	 */
	public RealNameApproVo queryRealNameApproByUserId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:保存用户实名认证，包括新增和更新<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月24日
	 * @param files
	 * @param realNameAppro
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveRealNameAppr(MultipartFile[] files,
			RealNameAppro realNameAppro, HttpServletRequest request)
			throws Exception;

	/**
	 * <p>
	 * Description:验证认证的重复数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月21日
	 * @param realNameApproCnd
	 * @return String
	 */
	public String queryRealNameApproRepeat(RealNameApproCnd realNameApproCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:进行实名认证<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月22日
	 * @param userid
	 * @param realname
	 * @param idcard
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String realnameAppro(int userid, String realname, String idcard,
			String ip) throws Exception;
}
