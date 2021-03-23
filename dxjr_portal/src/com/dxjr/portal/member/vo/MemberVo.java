package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:会员类Vo<br />
 * </p>
 * @title MemberVo.java
 * @package com.dxjr.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月11日
 */
public class MemberVo implements Serializable {

	private static final long serialVersionUID = -5890980057423538506L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 登录密码
	 */
	private String logpassword;
	/**
	 * 交易密码
	 */
	private String paypassword;
	/**
	 * 用户头像路径
	 */
	private String headimg;
	/**
	 * email
	 */
	private String email;
	/**
	 * 账户状态（-1：账户注销；0：正常状态）
	 */
	private Integer status;
	/**
	 * 借款次数
	 */
	private Integer borrowtimes;
	/**
	 * 投资次数
	 */
	private Integer investtimes;
	/**
	 * 登陆次数
	 */
	private Integer logintimes;
	/**
	 * 账户注册时间
	 */
	private String addtime;
	/**
	 * 账户注册IP
	 */
	private String addip;
	/**
	 * 推荐人，推荐码
	 */
	private String shareperson;
	/**
	 * 最后登陆时间
	 */
	private String lastlogintime;
	/**
	 * 最后登陆IP
	 */
	private String lastloginip;
	/**
	 * 用户IDMD5值
	 */
	private String useridmd5;
	/**
	 * 邀请人ID
	 */
	private Integer inviterid;
	/**
	 * 赠送金额
	 */
	private BigDecimal awardmoney;
	/**
	 * 会员总积分
	 */
	private Integer accumulatepoints;
	/**
	 * 会员类型（0：普通会员 1：新手导航版主 2：平台心声版主 3：报道领工资版主 4：你问我答版主 5：投资者交流区版主 6：投资者考察区版主 7：平台交易数据统计区版主 8：娱乐灌水专区版主 9：超级管理员 10:平台公告区版主）
	 */
	private Integer type;
	/**
	 * 会员来源（0：网站注册 1：网贷第三方）
	 */
	private Integer source;
	/**
	 * 会员可兑换积分
	 */
	private Integer gainaccumulatepoints;
	/**
	 * 注册时的国家或省份
	 */
	private String province;
	/**
	 * 注册时的登录城市
	 */
	private String city;
	/**
	 * 注册时的登录区域
	 */
	private String area;

	/**
	 * 是否是内部员工 (1 是 0 否)
	 */
	private Integer isEmployeer;

	/**
	 * 理财/借款用户（1 理财用户 0 借款用户）
	 */
	private Integer isFinancialUser;

	/**
	 * QQ号码
	 */
	private String qq;
	/**
	 * 微信号码
	 */
	private String wxNo;
	/**
	 * 邮件打开密码
	 */
	private String emailPassword;
	/** 平台来源 1：官网 2、微信，即用户登录的客户端 */
	private Integer platform;
	private Boolean isModify;

	private String userNameSecret; // 隐藏用户名
	private String userNameEncrypt; // 加密用户名
 
	private Integer honor;// 会元总荣誉
	
    private String checkCode;//验证码
    private Integer counter;//登陆错误出现2次，出现验证码

	private Integer isCustody; // 是否开立存管账户（0：未开立；1：已开立）
    private Integer eType; // 存管类型（1：e户；2商卡）

    private String mobile;
    private String realName;
    private String idCardNo;
    private Integer plNumber;
    public Integer getPlNumber() {
		return plNumber;
	}

	public void setPlNumber(Integer plNumber) {
		this.plNumber = plNumber;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Boolean getIsModify() {
		return isModify;
	}

	public void setIsModify(Boolean isModify) {
		this.isModify = isModify;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	/********************************************************************/
	private String addtimeFmt;// 格式化注册时间
	private String lastlogintimeFmt;// 格式化的最后登入时间

	public String getAddtimeFmt() {
		return DateUtils.timeStampToDate(addtime, DateUtils.YMD_HMS);
	}

	public String getLastlogintimeFmt() {
		return DateUtils.timeStampToDate(lastlogintime, DateUtils.YMD_HMS);
	}

	/********************************************************************/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogpassword() {
		return logpassword;
	}

	public void setLogpassword(String logpassword) {
		this.logpassword = logpassword;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBorrowtimes() {
		return borrowtimes;
	}

	public void setBorrowtimes(Integer borrowtimes) {
		this.borrowtimes = borrowtimes;
	}

	public Integer getInvesttimes() {
		return investtimes;
	}

	public void setInvesttimes(Integer investtimes) {
		this.investtimes = investtimes;
	}

	public Integer getLogintimes() {
		return logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes = logintimes;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getShareperson() {
		return shareperson;
	}

	public void setShareperson(String shareperson) {
		this.shareperson = shareperson;
	}

	public String getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public String getUseridmd5() {
		return useridmd5;
	}

	public void setUseridmd5(String useridmd5) {
		this.useridmd5 = useridmd5;
	}

	public Integer getInviterid() {
		return inviterid;
	}

	public void setInviterid(Integer inviterid) {
		this.inviterid = inviterid;
	}

	public BigDecimal getAwardmoney() {
		return awardmoney;
	}

	public void setAwardmoney(BigDecimal awardmoney) {
		this.awardmoney = awardmoney;
	}

	public Integer getAccumulatepoints() {
		return accumulatepoints;
	}

	public void setAccumulatepoints(Integer accumulatepoints) {
		this.accumulatepoints = accumulatepoints;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getGainaccumulatepoints() {
		return gainaccumulatepoints;
	}

	public void setGainaccumulatepoints(Integer gainaccumulatepoints) {
		this.gainaccumulatepoints = gainaccumulatepoints;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getIsEmployeer() {
		return isEmployeer;
	}

	public void setIsEmployeer(Integer isEmployeer) {
		this.isEmployeer = isEmployeer;
	}

	public Integer getIsFinancialUser() {
		return isFinancialUser;
	}

	public void setIsFinancialUser(Integer isFinancialUser) {
		this.isFinancialUser = isFinancialUser;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWxNo() {
		return wxNo;
	}

	public void setWxNo(String wxNo) {
		this.wxNo = wxNo;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getUserNameSecret() {
		userNameSecret = this.getUsername();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}

	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUsername();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}

	public Integer getHonor() {
		return honor;
	}

	public void setHonor(Integer honor) {
		this.honor = honor;
	}

    public Integer getIsCustody() {
        return isCustody;
    }

    public void setIsCustody(Integer isCustody) {
        this.isCustody = isCustody;
    }

    public Integer getEType() {
        return eType;
    }

    public void setEType(Integer eType) {
        this.eType = eType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}