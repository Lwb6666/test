package com.dxjr.portal.account.vo;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HtmlUtils;
import com.dxjr.utils.MoneyUtil;


/**
 * @author 胡建盼
 * @date 2014-8-5
 */
public class AccountOperateVO {
	/**用户操作日志ID*/
	private Integer id;
	private Integer isCustody;
	public Integer getIsCustody() {
		return isCustody;
	}

	public void setIsCustody(Integer isCustody) {
		this.isCustody = isCustody;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	private String subTypeStr;
	
	public String getSubTypeStr() {
		if(StringUtils.isEmpty(type)){
			return "";
		}
		if(type.length()>14){
			return type.substring(0, 13)+"..";
		}
		return type;
	}
	/**不可提金额*/
	private BigDecimal noDrawMoney;
	/**可提金额*/
	private BigDecimal drawMoney;
	private int userId;
	private String username;
	/** 日誌類型 */
	private String type;
	/** 帳戶總額 */
	private BigDecimal total;
	/** 操作金額 */
	private BigDecimal money;
	/** 可用餘額 */
	private BigDecimal use_money;
	/** 凍結金額 */
	private BigDecimal no_use_money;
	/** 待收總額 */
	private BigDecimal collection;
	/** 日誌備註 */
	private String remark;
	/** 操作時間 */
	private String addtime;
	/** 插入IP */
	private String addip;
	/** 交易对象 */
	private int to_user;
	/** 借款标ID  */
	private Integer borrowId;
	/** 借款标NAME */
	private String borrowName;
	/** 交易对象名称 */
	private String to_username;
	/** 优先计划总可用金额 */
	private BigDecimal first_borrow_use_money;
	/** 导出Excel需要转换的字段 begin  */
	private String addtimeFMT;
	private String totalStr;
	private String moneyStr;
	private String use_moneyStr;
	private String no_use_moneyStr;
	private String collectionStr;
	private String usernameStr;
	private String remarkStr;
	/**0:借款标ID, 1:直通车id, 2:待还记录id  null:其他资金操作日志,例如：网站奖励成功，资金已入账*/
	private Integer idType;
	private String first_borrow_use_moneyStr;
	private String noDrawMoneyStr;
	private String drawMoneyStr;
    private BigDecimal eUseMoney;
    private BigDecimal eFreezeMoney;
    private BigDecimal eCollection;
    private String eUseMoneyStr;
    private String eFreezeMoneyStr;
    private String eCollectionStr;

	public String getNoDrawMoneyStr() {
		if(noDrawMoney != null){
			return MoneyUtil.fmtMoneyByDot(noDrawMoney);
		}
		return "0.00";
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getDrawMoneyStr() {
		if(noDrawMoney != null){
			return MoneyUtil.fmtMoneyByDot(drawMoney);
		}
		return "0.00";
	}

	/** 导出Excel需要转换的字段 end  */

	public int getUserId() {
		return userId;
	}

	public BigDecimal getNoDrawMoney() {
		return noDrawMoney;
	}

	public void setNoDrawMoney(BigDecimal noDrawMoney) {
		this.noDrawMoney = noDrawMoney;
	}

	public BigDecimal getDrawMoney() {
		return drawMoney;
	}

	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AccountOperateVO() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getUse_money() {
		return use_money;
	}

	public void setUse_money(BigDecimal useMoney) {
		use_money = useMoney;
	}

	public BigDecimal getNo_use_money() {
		return no_use_money;
	}

	public void setNo_use_money(BigDecimal noUseMoney) {
		no_use_money = noUseMoney;
	}

	public BigDecimal getCollection() {
		return collection;
	}

	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public int getTo_user() {
		return to_user;
	}

	public void setTo_user(int to_user) {
		this.to_user = to_user;
	}

	public String getTo_username() {
		return to_username;
	}

	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}

	public String getTotalStr() {
		return MoneyUtil.fmtMoneyByDot(total);
	}

	public String getMoneyStr() {
		return MoneyUtil.fmtMoneyByDot(money);
	}

	public String getUse_moneyStr() {
		return MoneyUtil.fmtMoneyByDot(use_money);
	}

	public String getNo_use_moneyStr() {
		return MoneyUtil.fmtMoneyByDot(no_use_money);
	}

	public String getCollectionStr() {
		return  MoneyUtil.fmtMoneyByDot(collection);
	}

	public String getUsernameStr() {
		if (null == to_username) {
			return "admin";
		}
		return to_username;
	}

	public String getRemarkStr() {
		return HtmlUtils.getText(remark);
	}

	public void setTotalStr(String totalStr) {
		this.totalStr = totalStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}

	public void setUse_moneyStr(String use_moneyStr) {
		this.use_moneyStr = use_moneyStr;
	}

	public void setNo_use_moneyStr(String no_use_moneyStr) {
		this.no_use_moneyStr = no_use_moneyStr;
	}

	public void setCollectionStr(String collectionStr) {
		this.collectionStr = collectionStr;
	}

	public void setUsernameStr(String usernameStr) {
		this.usernameStr = usernameStr;
	}

	public void setRemarkStr(String remarkStr) {
		this.remarkStr = remarkStr;
	}

	public BigDecimal getFirst_borrow_use_money() {
		return first_borrow_use_money;
	}

	public void setFirst_borrow_use_money(BigDecimal first_borrow_use_money) {
		this.first_borrow_use_money = first_borrow_use_money;
	}
	public String getFirst_borrow_use_moneyStr() {
		if(first_borrow_use_money == null){
			return MoneyUtil.fmtMoneyByDot(BigDecimal.ZERO);
		}
		
		return MoneyUtil.fmtMoneyByDot(first_borrow_use_money);
	}

	public void setFirst_borrow_use_moneyStr(String first_borrow_use_moneyStr) {
		this.first_borrow_use_moneyStr = first_borrow_use_moneyStr;
	}

	public String getAddtimeFMT() {
		return DateUtils.timeStampToDate(addtime, DateUtils.YMD_HMS);
	}

    public BigDecimal geteUseMoney() {
        return eUseMoney;
    }

    public void seteUseMoney(BigDecimal eUseMoney) {
        this.eUseMoney = eUseMoney;
    }

    public BigDecimal geteFreezeMoney() {
        return eFreezeMoney;
    }

    public void seteFreezeMoney(BigDecimal eFreezeMoney) {
        this.eFreezeMoney = eFreezeMoney;
    }

    public BigDecimal geteCollection() {
        return eCollection;
    }

    public void seteCollection(BigDecimal eCollection) {
        this.eCollection = eCollection;
    }

    public String geteUseMoneyStr() {
        if(eUseMoney != null){
			return MoneyUtil.fmtMoneyByDot(eUseMoney);
		}
		return "0.00";
    }

    public void seteUseMoneyStr(String eUseMoneyStr) {
        this.eUseMoneyStr = eUseMoneyStr;
    }

    public String geteFreezeMoneyStr() {
        if(eFreezeMoney != null){
			return MoneyUtil.fmtMoneyByDot(eFreezeMoney);
		}
		return "0.00";
    }

    public void seteFreezeMoneyStr(String eFreezeMoneyStr) {
        this.eFreezeMoneyStr = eFreezeMoneyStr;
    }

    public String geteCollectionStr() {
        if(eCollection != null){
			return MoneyUtil.fmtMoneyByDot(eCollection);
		}
		return "0.00";
    }

    public void seteCollectionStr(String eCollectionStr) {
        this.eCollectionStr = eCollectionStr;
    }
}
