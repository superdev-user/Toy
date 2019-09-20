package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SigninRequest {

    private String userId;
    private String userPwd;

}
