package com.superdev.toy.web.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private String userId;

    private String token;

    private int statusCode;

    public UserResponse(int statusCode){
        this.statusCode = statusCode;
    }

    public UserResponse(String userId, String token , int statusCode) {
        this.userId = userId;
        this.token = token;
        this.statusCode = statusCode;
    }
}
