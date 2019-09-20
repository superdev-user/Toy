package com.superdev.toy.web.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SigninResponse {

    private String userId;

    private int statusCode;

    public SigninResponse(int statusCode){
        this.statusCode = statusCode;
    }

    public SigninResponse(String userId, int statusCode) {
        this.userId = userId;
        this.statusCode = statusCode;
    }
}
