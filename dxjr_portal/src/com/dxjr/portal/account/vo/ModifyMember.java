package com.dxjr.portal.account.vo;

public class ModifyMember {
    private Integer id;

    private Integer userid;

    private String nweusername;

    private String oldusername;

    private String addtime;

    private String addip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getNweusername() {
        return nweusername;
    }

    public void setNweusername(String nweusername) {
        this.nweusername = nweusername;
    }

    public String getOldusername() {
        return oldusername;
    }

    public void setOldusername(String oldusername) {
        this.oldusername = oldusername;
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
}