/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGBorrowService.java
 * @package com.dxjr.portal.borrow.service 
 * @author tanghaitao
 * @version 0.1 2016年5月23日
 */
package com.dxjr.portal.borrow.service;

import java.util.List;

import com.dxjr.common.custody.xml.FBRes;
import com.dxjr.common.custody.xml.PTRRes;
import com.dxjr.common.custody.xml.PTRResList;
import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.InvestBorrow;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.security.ShiroUser;

/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title CGBorrowService.java
 * @package com.dxjr.portal.borrow.service 
 * @author tanghaitao
 * @version 0.1 2016年5月23日
 */

public interface CGBorrowService {

	/**
	 * 
	 * <p>
	 * Description:投标校验<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月23日
	 * @param tenderBorrowCnd
	 * @param memberId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String validateTenderData(TenderBorrowCnd tenderBorrowCnd, Integer memberId) throws Exception;
	
	public String tenderCGBorrow(FBRes fBRes,TenderBorrowCnd tenderBorrowCnd,ShiroUser shiroUser,String addip,String p2pDJ) throws Exception;
	
	
	public String savePIReq(BorrowVo borrowVo,ShiroUser shiroUser,String mode,String remark,String relateNo) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据借款标ID查询投标记录<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowId
	 * @param page
	 * @return
	 * List<InvestBorrow>
	 */
	public Page findTenderrecordInfo(Integer borrowId, Page page) throws Exception;
	
	public Integer findTenderrecordInfoCount(Integer borrowId,Integer pageSize) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:项目投资登记接口调用<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param list
	 * @param shiroUser
	 * @param remark
	 * @param mode
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String savePTRReq(BorrowVo borrowVo,List<InvestBorrow> list,ShiroUser shiroUser,String remark,String mode,String relateNo) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:解析项目投资登记响应报文<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param repXml
	 * @param mode
	 * @param shiroUser
	 * @param remark
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * PTRRes
	 */
	public PTRRes parsePTRResXml(String repXml,String mode,ShiroUser shiroUser,String remark,String relateNo,Integer borrowId) throws Exception;
	
	
	/**
	 * 
	 * <p>
	 * Description:更新投标记录信息<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月25日
	 * @param borrowVo
	 * @param list
	 * @throws Exception
	 * void
	 */
	public void updateTenderrecord(BorrowVo borrowVo,PTRRes pTRRes,List<InvestBorrow> InvestBorrowList) throws Exception;
	
	
	public void updateBorrowStatus(BorrowVo borrowVo)throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:调用投资冻结解冻接口<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月26日
	 * @param borrowVo
	 * @param oriSerialNo
	 * @param investmentAmount
	 * @param shiroUser
	 * @param remark
	 * @param mode
	 * @param relateNo
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveUFBReq(BorrowVo borrowVo,String oriSerialNo,Integer investmentAmount,ShiroUser shiroUser,String remark,String mode,String relateNo) throws Exception;
	
	Integer findCheckBorrow(Integer userId);
	
	public PTRRes reviewParsePTRResXml(String repXml) throws Exception;
	
	public void reviewUpdateTenderrecord(BorrowVo borrowVo,PTRRes pTRRes) throws Exception;
}
