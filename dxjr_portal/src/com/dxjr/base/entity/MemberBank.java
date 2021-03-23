package com.dxjr.base.entity;

/**
 * <p>
 * Description:用户银行卡号表<br />
 * </p>
 * 
 * @title MemberBank.java
 * @package com.dxjr.base.entity
 * @author qiongbiao.zhang
 * @version 0.1 2014年9月6日
 */
public class MemberBank {
    private Integer id; // 主键

    private Integer userId; // 用户ID

    private String cardNo; // 卡号

    private Long cnapsCode; // 联行号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getCnapsCode() {
        return cnapsCode;
    }

    public void setCnapsCode(Long cnapsCode) {
        this.cnapsCode = cnapsCode;
    }
}