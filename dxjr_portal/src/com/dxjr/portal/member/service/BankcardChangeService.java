package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.vo.BankcardChange;
import com.dxjr.portal.member.vo.BankcardPic;
import com.dxjr.security.ShiroUser;

public interface BankcardChangeService {

	/**
	 * 添加点击错误日志
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年4月9日
	 * @param change
	 * @param user
	 * @param addIp
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox addCheckErrorLog(BankcardChange change, ShiroUser user, String addIp) throws Exception;

	/**
	 * 上传图片
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年4月9日
	 * @param files
	 * @param change
	 * @param picType
	 * @param request
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox addPics(MultipartFile[] files, BankcardChange change, String picType, HttpServletRequest request, Integer userId) throws Exception;

	/**
	 * 保存换卡申请
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年4月9日
	 * @param change
	 * @param pics
	 * @param user
	 * @param addIp
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox add(BankcardChange change, List<BankcardPic> pics, ShiroUser user, String addIp) throws Exception;

	
	/**
	 * 更换资料银行卡保存
	 */
	
	public MessageBox addModify(BankcardChange change, List<BankcardPic> pics, ShiroUser user, String addIp) throws Exception;
	/**
	 * 银行卡状态检查
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年4月9日
	 * @param userId
	 * @return
	 * @throws Exception String
	 */
	public String bankinfoCheck(int userId) throws Exception;

	/**
	 * <p>
	 * Description:查询当前正在提交审核银行卡的图片 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年4月28日
	 * @param userId
	 * @return List<BankcardPic>
	 */
	public List<BankcardPic> queryBankcardPicsByUserId(Integer userId);

	public MessageBox saveEditPics(List<Integer> waitDeleteIds, BankcardChange change, List<BankcardPic> pics, ShiroUser currentUser, String realIpAddr);

	public void removePicById(int id);

	// public List<String> addPics(MultipartFile[] files, HttpServletRequest request, Integer userId) throws Exception;
	
	/**
	 * 取更換銀行卡的原因
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月8日
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String getReasonByUserId(int userId) ;
	
}
