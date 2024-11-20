package com.gdg.googleloginproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    //유저
    PASSWORDS_DO_NOT_MATCH(401,"비밀번호가 일치 하지 않습니다."),
    AUTHORIZATION_EXCEPTION(403,"접근할 수 있는 권한이 없습니다."),
    USEREMAIL_IS_EXIST(409,"이미 등록된 회원입니다."),
    USER_IS_NOT_EXIST(404,"회원이 존재하지 않습니다."),
    EMAIL_NOT_VERIFIED(403, "이메일 인증이 완료되지 않았습니다."),
    FAILED_TO_FETCH_USER_INFO(500, "유저 정보를 가져오지 못했습니다."),

    //레시피
    RECIPE_NOT_FOUND(404, "요청하신 레시피를 찾을 수 없습니다."),
    NO_PERMISSION_TO_EDIT(403, "수정할 권한이 없습니다."),
    NO_PERMISSION_TO_DELETE(403, "삭제할 권한이 없습니다."),

    //토큰
    FAILED_TO_FETCH_GOOGLE_TOKEN(500, "구글 액세스 토큰을 가져오지 못했습니다."),
    INVALID_TOKEN(401,"유효하지 않은 토큰입니다."),
    TOKEN_MISSING_AUTHORITY(401, "권한 정보가 없는 토큰입니다."),
    TOKEN_DECODING_FAILED(400, "토큰 복호화에 실패했습니다.");

    private final int statusCode;
    private final String message;
}
