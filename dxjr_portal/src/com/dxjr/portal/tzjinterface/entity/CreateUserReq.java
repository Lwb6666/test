package com.dxjr.portal.tzjinterface.entity;

public class CreateUserReq {
	// 投之家用户名
	private String username;
	
	// 手机号码
	private String telephone;
	
	// 电子邮箱
	private String email;
	
	// 身份证信息
	private IdCard idCard;
	
	// 银行卡信息
	private BankCard bankCard;
	private String[] tags;
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	// 登录类型：0:PC, 1:WAP
	private int type;
	public int getType() {
		if(tags!=null&&tags.length>0){
			boolean flag=false;
			for(int i=0;i<tags.length;i++){
				if(tags[i].equals("pc")){
					flag=true;
					break;
				}else{
					flag=false;
				}
			}
			if(flag){
				return 0;
			}else{
				return 1;
			}
		}else{
			return 0;
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}

	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	}
}
