package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "존재하지 않는 유저입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
    UNSUPPORTED_EMAIL(HttpStatus.BAD_REQUEST, "지원하지 않는 대학입니다."),
    EXPIRED_AUTHCODE(HttpStatus.BAD_REQUEST, "인증코드의 유효기간이 만료되었습니다. 이메일 전송을 다시 요청해주세요."),
    INVALID_AUTHCODE(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다. 인증코드를 다시 입력해주세요."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
