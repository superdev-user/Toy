package com.superdev.toy.app.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="tb_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}