package com.dxjr.base.entity;

import java.util.Date;

public class BaseEMessageRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.MODE
     *
     * @mbggenerated
     */
    private String mode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.TYPE
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.MSG_TYPE
     *
     * @mbggenerated
     */
    private Integer msgType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.ORDER_NO
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.BIND_NO
     *
     * @mbggenerated
     */
    private String bindNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.OPT_USERID
     *
     * @mbggenerated
     */
    private Integer optUserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.STATUS
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.PLATFORM
     *
     * @mbggenerated
     */
    private Integer platform;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.REMARK
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.RELATE_NO
     *
     * @mbggenerated
     */
    private String relateNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.ADDTIME
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column e_message_record.MSG
     *
     * @mbggenerated
     */
    private String msg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.ID
     *
     * @return the value of e_message_record.ID
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.ID
     *
     * @param id the value for e_message_record.ID
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.MODE
     *
     * @return the value of e_message_record.MODE
     *
     * @mbggenerated
     */
    public String getMode() {
        return mode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.MODE
     *
     * @param mode the value for e_message_record.MODE
     *
     * @mbggenerated
     */
    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.TYPE
     *
     * @return the value of e_message_record.TYPE
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.TYPE
     *
     * @param type the value for e_message_record.TYPE
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.MSG_TYPE
     *
     * @return the value of e_message_record.MSG_TYPE
     *
     * @mbggenerated
     */
    public Integer getMsgType() {
        return msgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.MSG_TYPE
     *
     * @param msgType the value for e_message_record.MSG_TYPE
     *
     * @mbggenerated
     */
    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.ORDER_NO
     *
     * @return the value of e_message_record.ORDER_NO
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.ORDER_NO
     *
     * @param orderNo the value for e_message_record.ORDER_NO
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.BIND_NO
     *
     * @return the value of e_message_record.BIND_NO
     *
     * @mbggenerated
     */
    public String getBindNo() {
        return bindNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.BIND_NO
     *
     * @param bindNo the value for e_message_record.BIND_NO
     *
     * @mbggenerated
     */
    public void setBindNo(String bindNo) {
        this.bindNo = bindNo == null ? null : bindNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.OPT_USERID
     *
     * @return the value of e_message_record.OPT_USERID
     *
     * @mbggenerated
     */
    public Integer getOptUserid() {
        return optUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.OPT_USERID
     *
     * @param optUserid the value for e_message_record.OPT_USERID
     *
     * @mbggenerated
     */
    public void setOptUserid(Integer optUserid) {
        this.optUserid = optUserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.STATUS
     *
     * @return the value of e_message_record.STATUS
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.STATUS
     *
     * @param status the value for e_message_record.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.PLATFORM
     *
     * @return the value of e_message_record.PLATFORM
     *
     * @mbggenerated
     */
    public Integer getPlatform() {
        return platform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.PLATFORM
     *
     * @param platform the value for e_message_record.PLATFORM
     *
     * @mbggenerated
     */
    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.REMARK
     *
     * @return the value of e_message_record.REMARK
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.REMARK
     *
     * @param remark the value for e_message_record.REMARK
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.RELATE_NO
     *
     * @return the value of e_message_record.RELATE_NO
     *
     * @mbggenerated
     */
    public String getRelateNo() {
        return relateNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.RELATE_NO
     *
     * @param relateNo the value for e_message_record.RELATE_NO
     *
     * @mbggenerated
     */
    public void setRelateNo(String relateNo) {
        this.relateNo = relateNo == null ? null : relateNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.ADDTIME
     *
     * @return the value of e_message_record.ADDTIME
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.ADDTIME
     *
     * @param addtime the value for e_message_record.ADDTIME
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column e_message_record.MSG
     *
     * @return the value of e_message_record.MSG
     *
     * @mbggenerated
     */
    public String getMsg() {
        return msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column e_message_record.MSG
     *
     * @param msg the value for e_message_record.MSG
     *
     * @mbggenerated
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}