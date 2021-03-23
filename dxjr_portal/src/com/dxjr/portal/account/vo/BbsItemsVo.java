package com.dxjr.portal.account.vo;

import java.util.Date;

import com.dxjr.utils.StrinUtils;

/**
 * @desc 账户详情，帖子对象
 * @author hujianpan
 * @since 1.0.1
 *
 */
public class BbsItemsVo {
	/**bbs帖子ID*/
	private Integer id;
	/**帖子标题*/
	private String title;
	/**帖子点击次数*/
	private Integer clickNum;
	/**发帖时间*/
	private Date createTime;
	/**帖子编号*/
	private Integer columnId;
	/**帖子状态*/
	private Integer status;
	/**是否屏蔽*/
	private Integer isScreen;
	/**是否删除*/
	private Integer isDelete;
	/**回复次数*/
	private Integer noteCount;
	/**栏目名-板块名称*/
	private String columnName;
	/**发帖用户名*/
	private String userName;
	private String userIdMD5;
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名
	public String getUserIdMD5() {
		return userIdMD5;
	}
	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getClickNum() {
		return clickNum;
	}
	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getColumnId() {
		return columnId;
	}
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsScreen() {
		return isScreen;
	}
	public void setIsScreen(Integer isScreen) {
		this.isScreen = isScreen;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getNoteCount() {
		return noteCount;
	}
	public void setNoteCount(Integer noteCount) {
		this.noteCount = noteCount;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
	
}
