package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    NEED_EMAIL_AUTH(HttpStatus.FORBIDDEN, "이메일 인증되지 않은 유저입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
