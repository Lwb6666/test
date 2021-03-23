/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGUtilService.java
 * @package com.dxjr.portal.account.service 
 * @author tanghaitao
 * @version 0.1 2016年5月19日
 */
package com.dxjr.portal.account.service;

import java.util.List;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.entity.MessageRecord;
import com.dxjr.common.custody.xml.FBRes;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.security.ShiroUser;

/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGUtilService.java
 * @package com.dxjr.portal.account.service 
 * @author tanghaitao
 * @version 0.1 2016年5月19日
 */

public interface CGUtilService {
	
	/**
	 * 
	 * <p>
	 * Description:调用余额查询接口<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @param shiroUser
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveCGAccount(ShiroUser shiroUser,String relateNo,String remark,Integer borrowId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:解析余额报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月19日
	 * @param repXml
	 * @return
	 * Account
	 * @throws Exception 
	 */
	public Account parseAQReqXml(String repXml,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:平台与存管资金校验<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月20日
	 * @param account
	 * @param addip
	 * @param platform
	 * @param scene
	 * @return
	 * @throws Exception
	 * String
	 */
	public String savecheckAccount(Account account,String addip,Integer platform,String scene) throws Exception;
	
	public BaseEBankInfo eUserInfo(Integer userId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:查询该用户是否异常<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param userId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public Integer findAccountErrorByUserIdCount(Integer userId) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:短信接口调用<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveMobileCode(ShiroUser shiroUser,String remark,String relateNo,Integer borrowId) throws Exception;
	
	public String saveMobileCode(ShiroUser shiroUser,String remark,String relateNo,Integer borrowId,String sType) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:记录响应报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月21日
	 * @param repXml
	 * @param mode
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @return 
	 * @throws Exception
	 * void
	 */
	public String saveResXml(String repXml,String mode,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId) throws Exception;
	
	
	
	/**
	 * 
	 * <p>
	 * Description:资金冻结接口调用<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param shiroUser
	 * @param relateNo
	 * @param remark
	 * @param borrowVo
	 * @param tenderBorrowCnd
	 * @param mode
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveFBReq(ShiroUser shiroUser,String relateNo,String remark,TenderBorrowCnd tenderBorrowCnd,String mode,String p2pDJ) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:解析投资资金冻结响应报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param repXml
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @param mode
	 * @return
	 * @throws Exception
	 * FBRes
	 */
	public FBRes parseFBResXml(String repXml,ShiroUser shiroUser,String remark,String relateNo,String mode,TenderBorrowCnd tenderBorrowCnd) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:记录报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param reqXml
	 * @param mode
	 * @param type
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * void
	 */
	public void insertMsg(String reqXml,String mode,int type,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId);
	
	public void insertMsg(String reqXml,String mode,int type,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId,Integer msgType);
	
	/**
	 * 
	 * <p>
	 * Description:投资冻结解冻<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年6月8日
	 * @param repXml
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @param mode
	 * @param tenderBorrowCnd
	 * @param oriSerialNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String parseUFBResXml(String repXml,ShiroUser shiroUser,String remark,String relateNo,String mode,TenderBorrowCnd tenderBorrowCnd,String oriSerialNo) throws Exception;
	
	
	public List<MessageRecord> findMessageRecord(MessageRecord messageRecord);

	/**
	 * 
	 * <p>
	 * Description:自动投标设置接口调用<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年6月27日
	 * @param shiroUser
	 * @param mobileCode
	 * @param flag
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveABSReq(ShiroUser shiroUser,String mobileCode,String flag,String remark,String relateNo) throws Exception;
}
