package com.superdev.toy.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class SuccessResponse<T> extends BaseResponse {
    private T data;
    private String message;

    public SuccessResponse() {
        super(ResponseCodes.OK);
        data = null;
    }

    public SuccessResponse(T data) {
        super(ResponseCodes.OK);
        this.data = data;
    }

    public SuccessResponse(int code, String message){
        super(code);
        this.message = message;
    }
}
