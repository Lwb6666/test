package com.dxjr.wx.entry.bind.service;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.wx.entry.bind.vo.BindVo;

public interface BindService {
	/**
	 * 
	 * <p>
	 * Description:根据微信id查询是否绑定<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月22日
	 * @param openId
	 * @return MemberVo 绑定的官方账号
	 */
	MemberVo queryIsBind(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:账号绑定<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月22日
	 * @param blindVo
	 * @return MessageBox
	 */
	MessageBox saveBind(BindVo bindVo, String modelName, int smsTemplateType, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:绑定时检查参数是否对应<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月22日
	 * @param bindVo
	 * @return String
	 */
	MessageBox verfiBindInfo(BindVo bindVo) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:解绑<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月22日
	 * @param bindVo
	 * @return String
	 */
	MessageBox updateUnBind(BindVo bindVo) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:取消关注自动解绑<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月23日
	 * @param bindVo
	 * @return String
	 */
	MessageBox updateUnBindUnsubscribe(BindVo bindVo) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据登录名查询用户<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param name
	 * @return MemberVo
	 */
	MemberVo queryMemberByLoginname(String name) throws Exception;

	/**
	 * 项目启动时启动 用于官网维护时 用户关注和取消关注 自动解绑失败后执行
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月30日 void
	 */
	void updateBindStartUp(String ids);

	/**
	 * 根据用户id查询绑定的微信id
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月31日
	 * @param userId
	 * @return Integer
	 */
	Integer queryWxIdByUserId(Integer userId);

	/**
	 * 查询是否绑定
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2015年3月12日
	 * @param wxId
	 * @return Integer
	 */
	Integer queryHadBind(Integer wxId);
}
