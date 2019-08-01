package com.superdev.toy.web.domain;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private String userNm;

    private String token;

    public UserResponse(String userNm, String token) {
        this.userNm = userNm;
        this.token = token;
    }
}
