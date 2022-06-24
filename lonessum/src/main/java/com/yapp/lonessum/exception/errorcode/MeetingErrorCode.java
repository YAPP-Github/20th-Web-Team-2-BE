package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MeetingErrorCode implements ErrorCode {

    ZERO_SURVEY(HttpStatus.BAD_REQUEST, "대기 중인 설문이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
