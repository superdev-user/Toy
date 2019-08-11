package com.superdev.toy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_user")
public class User {
    @Id
    @Column(name="user_id")
    private String userId;
    @Column(name="user_nm")
    private String userNm;
    @Column(name="user_pwd")
    private String userPwd;
    @Column(name="reg_dt")
    private String regDt;
    @Column(name="upd_dt")
    private String updDt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUpdDt() {
        return updDt;
    }

    public void setUpdDt(String updDt) {
        this.updDt = updDt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userNm='" + userNm + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", regDt='" + regDt + '\'' +
                ", updDt='" + updDt + '\'' +
                '}';
    }
}
