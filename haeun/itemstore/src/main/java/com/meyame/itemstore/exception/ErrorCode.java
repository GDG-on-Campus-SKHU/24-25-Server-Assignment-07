package com.meyame.itemstore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorInterface {

    GOOGLE_ACCESS_TOKEN_ERROR("GOOGLE_001", "구글 액세스 토큰을 가져오는데 실패하였습니다."),
    GOOGLE_USER_INFO_ERROR("GOOGLE_002","구글 사용자 정보를 가져오는데 실패했습니다."),
    GOOGLE_EMAIL_ERROR("GOOGLE_003","이메일 인증이 되지 않은 유저입니다."),
    GOOGLE_UNAUTHORIZED("GOOGLE_004","구글 로그인이 필요합니다."),

    DUPLICATE_EMAIL("AUTH_001","중복되는 이메일입니다."),
    USER_NOT_FOUND("AUTH_002","해당하는 유저가 존재하지 않습니다."),
    INCORRECT_PASSWORD("AUTH_003","비밀번호가 존재하지 않습니다."),
    INCORRECT_REFRESH_TOKEN("AUTH_004","Refresh Token이 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN("AUTH_005", "유효하지 않은 Refersh Token 입니다."),

    ITEM_NOT_FOUND("ITEM_001","찾으시는 아이템이 없습니다."),
    INVALID_ITEM_QUANTITY("ITEM_002","아이템의 수량은 1이상이어야합니다."),
    USER_ITEM_NOT_FOUND("ITEM_003","사용자가 보관중인 아이템이 아닙니다."),
    DELETE_INVALID_ITEM_QUANTITY("ITEM_004","삭제하려는 수량을 1이상으로 입력해주세요."),
    DELETE_INVALID_LESS_QUANTITY("ITEM_005","현재 보관중인 수량이 삭제하려는 수량보다 적습니다."),

    ROLE_NOT_AUTHORIZATION("ROLE_001","역할 검증에 실패하였습니다.");

    private final String code;
    private final String message;
}
