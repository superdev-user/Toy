package com.superdev.toy.app.exception;

/**
 * Created by kimyc on 15/08/2019.
 */
public class BaseException extends RuntimeException {
    private int errCode;
    public BaseException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public int errCode() {
        return this.errCode;
    }
}
