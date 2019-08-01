package com.superdev.toy.web.common.jwt.exception;

import javax.naming.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
