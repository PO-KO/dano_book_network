package com.dano.dano_book_social.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum ErrorCodes {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Email or password is incorrect"),
    ;
    @Getter
    private final Integer code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;
    private ErrorCodes(int code,HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    


}
