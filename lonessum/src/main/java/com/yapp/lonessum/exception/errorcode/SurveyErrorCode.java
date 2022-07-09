package com.yapp.lonessum.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SurveyErrorCode implements ErrorCode {

    NO_EXISTING_SURVEY(HttpStatus.BAD_REQUEST, "작성한 설문이 없습니다."),
    NO_WAITING_SURVEY(HttpStatus.BAD_REQUEST, "대기 중인 설문이 없습니다."),
    WAITING_FOR_MATCH(HttpStatus.ACCEPTED, "매칭 대기중입니다."),
    MATCH_SUCCESS(HttpStatus.ACCEPTED, "매칭이 성사되었습니다."),
    PAY_FOR_MATCH(HttpStatus.ACCEPTED, "매칭 결과를 확인하기 위해 결제를 진행해주세요."),
    WAITING_FOR_PAY(HttpStatus.ACCEPTED, "상대방이 결제할 때까지 대기중입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
