package com.dxjr.portal.account.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dxjr.base.entity.Member;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.AccountCnd;
import com.dxjr.portal.account.vo.LoginRemindLogVo;
import com.dxjr.portal.account.vo.MyAccountApproMsgVo;
import com.dxjr.portal.account.vo.YearCollect;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.NewLoactionConstants;
import com.dxjr.utils.file.FileUtil;

/**
 * <p>
 * Description:我的帐号业务类<br />
 * </p>
 * 
 * @title MyAccountServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
@Service
public class MyAccountServiceImpl implements MyAccountService {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	public MyAccountApproMsgVo validateAccountAppro(Integer memberId) throws Exception {
		MyAccountApproMsgVo myAccountApproMsgVo = new MyAccountApproMsgVo();
		// 默认认证不通过
		myAccountApproMsgVo.setResult("failure");
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(memberId);

		if (null == memberApproVo) {
			myAccountApproMsgVo.setMsg("未找到用户,请确认！");
			return myAccountApproMsgVo;
		}
		// 判断是否通过邮箱认证
		if (null == memberApproVo.getEmailChecked() || memberApproVo.getEmailChecked() != Constants.YES) {
			myAccountApproMsgVo.setMsg("您还没有进行邮箱认证，请先进行邮箱认证");
			myAccountApproMsgVo.setLinkUrl("account/approve/email.html");
			myAccountApproMsgVo.setLinkName("去邮箱认证");
			return myAccountApproMsgVo;
		}
		// 判断是否通过了实名认证
		if (null == memberApproVo.getNamePassed()) {
			myAccountApproMsgVo.setMsg("您还没有进行实名认证，请先进行实名认证");
			myAccountApproMsgVo.setLinkName("去实名认证");
			myAccountApproMsgVo.setLinkUrl("account/approve/realname/display.html");
			return myAccountApproMsgVo;
		}
		// 实名认证未审核
		if (memberApproVo.getNamePassed() == Constants.REALNAME_APPR_ISPASSED_WAIT_APPROVE) {
			myAccountApproMsgVo.setMsg("您的实名认证正在审核中，请稍后刷新查看，如有疑问请联系客服人员！");
			myAccountApproMsgVo.setLinkName("去实名认证");
			myAccountApproMsgVo.setLinkUrl("account/approve/realname/display.html");
			return myAccountApproMsgVo;
		}
		// 实名认证审核未通过
		if (memberApproVo.getNamePassed() == Constants.REALNAME_APPR_ISPASSED_REJECT) {
			myAccountApproMsgVo.setMsg("您的实名认证审核不通过，请重新进行实名认证！");
			myAccountApproMsgVo.setLinkName("去实名认证");
			myAccountApproMsgVo.setLinkUrl("account/approve/realname/display.html");
			return myAccountApproMsgVo;
		}
		// 判断是否通过了手机认证
		if (null == memberApproVo.getMobilePassed() || memberApproVo.getMobilePassed() != Constants.YES) {
			myAccountApproMsgVo.setMsg("您还没有进行手机认证，请先进行手机认证");
			myAccountApproMsgVo.setLinkUrl("account/approve/mobile/mobileforinsert.html");
			myAccountApproMsgVo.setLinkName("去手机认证");
			return myAccountApproMsgVo;
		}
		// 设为验证通过
		myAccountApproMsgVo.setResult("success");
		// 设置认证信息情况
		myAccountApproMsgVo.setMemberApproVo(memberApproVo);
		return myAccountApproMsgVo;
	}

	@Override
	public String saveHeadImg(MultipartFile file, int userId, HttpServletRequest request) throws Exception {
		String result = "success";
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(userId);
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 上传文件
		result = this.uploadHeadImgFiles(file, memberVo, request);
		if (!result.equals("success")) {
			return result;
		}
		if (memberVo.getHeadimg() == null || memberVo.getHeadimg().equals("")) {
			return "文件不存在，请重新上传头像！";
		}
		// 更新member对象
		Member member = new Member();
		BeanUtils.copyProperties(memberVo, member);
		memberService.updateEntity(member);
		return result;
	}

	private String uploadHeadImgFiles(MultipartFile file, MemberVo memberVo, HttpServletRequest request) throws Exception {
		String result = "success";
		if (null == file || file.isEmpty() == true) {
			return "请选择需要上传的头像！";
		}
		// 验证文件格式和大小是否正确
		result = FileUtil.validateFileInfo(file, BusinessConstants.IMAGE_MAX_SIZE, BusinessConstants.IMAGE_FILE_TYPE_LIST);
		if (!result.equals("success")) {
			return result;
		}
		// 改变文件名
		String filename = file.getOriginalFilename();
		String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
		String lastFileName = UUID.randomUUID().toString() + extName;
		// 上传图片
		FileUtil.upload(file, request, NewLoactionConstants.HEADIMG_UPLOAD_FILE_PATH, lastFileName);
		// 正面
		memberVo.setHeadimg("/" + NewLoactionConstants.HEADIMG_UPLOAD_FILE_PATH + "/" + lastFileName);
		return result;
	}

	@Override
	public void saveLoginRemindLog(LoginRemindLogVo lv) throws Exception {
		
		accountMapper.insert(lv);

	}
	@Override
	public List<YearCollect> queryYearCollect(String year,Integer userId) throws Exception {
		AccountCnd accountCnd=new AccountCnd();
		accountCnd.setUserId(userId);
		accountCnd.setYear(year);
		List<YearCollect> yearCollects=accountMapper.queryYearCollect(accountCnd);
		return yearCollects;

	}
	public YearCollect queryDayCollect(String dateTime,Integer userId) throws Exception {
		YearCollect yearCollect=null;
		if (StringUtils.isNotEmpty(dateTime)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dateTime=format.format(format.parse(dateTime));
			AccountCnd accountCnd=new AccountCnd();
			accountCnd.setUserId(userId);
			accountCnd.setDateTime(dateTime);
			yearCollect=accountMapper.queryDayCollect(accountCnd);
			if(format.format(new Date()).compareTo(format.format(format.parse(dateTime)))==0){
				yearCollect.setType("3");//当天待收
			}
			if(format.format(new Date()).compareTo(format.format(format.parse(dateTime)))>0){
				yearCollect.setType("2");//已收
			}
			if(format.format(new Date()).compareTo(format.format(format.parse(dateTime)))<0){
				yearCollect.setType("1");//待收
			}
		}
		return yearCollect;

	}
}
