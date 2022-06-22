package com.yapp.lonessum.domain.meeting.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeetingScore {
    AVOID_UNIVERSITY_SCORE(-21, "기피 학교 점수"),
    PREFER_AGE_SCORE(+8, "선호 나이 점수"),
    PREFER_HEIGHT_SCORE(+5, "선호 키 점수"),
    PREFER_UNIVERSITY_SCORE(+3, "선호 학교 점수"),
    PREFER_DEPARTMENT_SCORE(+2, "선호 학과 점수"),
    MINDSET_SCORE(+1, "선호 마인드 셋 점수"),
    PLAY_SCORE(+1, "선호 게임 점수"),
    ZERO_SCORE(0, "기본 점수");

    private int score;
    private String description;
}
