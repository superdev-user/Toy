package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {

    private String userId;

    private String userNm;

    private String userPwd;

    public UserRequest(User user){
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
    }

}
