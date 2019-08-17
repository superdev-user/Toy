package com.superdev.toy.web.domain;

/**
 * Created by kimyc on 15/08/2019.
 */
public class BaseResponse {
    int code;

    public BaseResponse(int code) {
        this.code = code;
    }

    public int BaseResponse() {
        return code;
    }

    public int getCode() {
        return code;
    }
}
