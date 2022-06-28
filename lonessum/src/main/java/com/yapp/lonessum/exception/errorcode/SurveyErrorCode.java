package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SurveyErrorCode implements ErrorCode {

    NO_EXIST_SURVEY(HttpStatus.BAD_REQUEST, "작성한 설문이 없습니다."),
    ZERO_SURVEY(HttpStatus.BAD_REQUEST, "대기 중인 설문이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
