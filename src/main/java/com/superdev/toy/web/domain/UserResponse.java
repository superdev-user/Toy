package com.superdev.toy.web.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private String userNm;

    private String token;

    private int statusCode;

    public UserResponse(int statusCode){
        this.statusCode = statusCode;
    }

    public UserResponse(String userNm, String token , int statusCode) {
        this.userNm = userNm;
        this.token = token;
        this.statusCode = statusCode;
    }
}
