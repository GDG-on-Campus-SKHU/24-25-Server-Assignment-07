package com.gdg.googleloginproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum SuccessMessage {
    JOIN_SUCCESS_MESSAGE(HttpStatus.CREATED, "회원가입에 성공하셨습니다."),
    LOGIN_SUCCESS_MESSAGE(HttpStatus.OK, "로그인에 성공하셨습니다.");

    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String message;

    SuccessMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
