package com.yapp.lonessum.domain.dating.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DatingScore {
    AVOID_UNIVERSITY_SCORE(-52, "기피 학교 점수"),
    PREFER_AGE_SCORE(+10, "선호 나이 점수"),
    PREFER_BODY_SCORE(+10, "선호 체형 점수"),
    PREFER_HEIGHT_SCORE(+10, "선호 키 점수"),
    PREFER_DATE_COUNT_SCORE(+5, "선호 연애 횟수 점수"),
    PREFER_CHARACTERISTIC_SCORE(+5, "선호 성격 점수"),
    PREFER_UNIVERSITY_SCORE(+3, "선호 학교 점수"),
    PREFER_DEPARTMENT_SCORE(+2, "선호 학과 점수"),
    AVOID_SMOKE_SCORE(-5, "기피 흡연 여부 점수"),
    ZERO_SCORE(0, "기본 점수");

    private int score;
    private String description;
}
