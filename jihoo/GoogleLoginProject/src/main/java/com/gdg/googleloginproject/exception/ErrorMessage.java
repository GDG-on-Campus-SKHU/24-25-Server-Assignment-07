package com.gdg.googleloginproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    PASSWORDS_DO_NOT_MATCH(401,"비밀번호가 일치 하지 않습니다."),
    INVALID_TOKEN(401,"유효하지 않은 토큰입니다."),
    AUTHORIZATION_EXCEPTION(403,"접근할 수 있는 권한이 없습니다."),
    POST_IS_EMPTY(404,"해당 레시피가 존재 하지 않습니다."),
    USER_IS_NOT_EXIST(404,"회원이 존재하지 않습니다."),
    USEREMAIL_IS_EXIST(409,"이미 등록된 회원입니다.");

    private final int statusCode;
    private final String message;
}
