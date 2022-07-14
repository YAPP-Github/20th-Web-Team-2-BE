package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "존재하지 않는 유저입니다."),
    WRONG_PASSWORD(HttpStatus.FORBIDDEN, "비밀번호가 틀렸습니다."),
    NEED_EMAIL_AUTH(HttpStatus.FORBIDDEN, "이메일 인증되지 않은 유저입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 형식입니다."),
    UNSUPPORTED_EMAIL(HttpStatus.BAD_REQUEST, "지원하지 않는 대학입니다."),
    EXPIRED_AUTHCODE(HttpStatus.BAD_REQUEST, "인증코드의 유효기간이 만료되었습니다. 이메일 전송을 다시 요청해주세요."),
    INVALID_AUTHCODE(HttpStatus.BAD_REQUEST, "인증코드가 일치하지 않습니다. 인증코드를 다시 입력해주세요."),
    NEED_AGE_AGREE(HttpStatus.BAD_REQUEST, "유저의 나이 정보 제공 동의가 필요합니다."),
    AGE_TOO_YOUNG(HttpStatus.BAD_REQUEST, "만 19세 이상의 유저만 사용 가능한 서비스 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
