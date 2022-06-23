package com.yapp.lonessum.common.algorithm;

import com.yapp.lonessum.domain.meeting.entity.MeetingMatchingEntity;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class MatchingInfo<T> implements Comparable<MatchingInfo> {
    private int score;
    private T first;
    private T second;

    @Override
    public int compareTo(MatchingInfo o) {
        return o.getScore() - score;
    }

    public MeetingMatchingEntity toMeetingMatchingEntity() {
        return MeetingMatchingEntity.builder()
                .surveyA((MeetingSurveyEntity) this.first)
                .surveyB((MeetingSurveyEntity) this.second)
                .matchedAt(LocalDateTime.now())
                .build();
    }
}
