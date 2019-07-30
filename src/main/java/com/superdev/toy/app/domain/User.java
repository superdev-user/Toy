package com.superdev.toy.app.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(unique = true)
    private String userId;

    @Column
    private String userNm;

    @Column
    private String userPwd;

    @Column
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updDt;

}