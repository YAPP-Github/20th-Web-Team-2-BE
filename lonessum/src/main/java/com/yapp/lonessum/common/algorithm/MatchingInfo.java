package com.yapp.lonessum.common.algorithm;

import com.yapp.lonessum.domain.constant.Gender;
import com.yapp.lonessum.domain.dating.entity.DatingMatchingEntity;
import com.yapp.lonessum.domain.dating.entity.DatingSurveyEntity;
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
        MeetingSurveyEntity first = (MeetingSurveyEntity) this.first;
        MeetingSurveyEntity second = (MeetingSurveyEntity) this.second;
        if (first.getGender() == Gender.MALE) {
            return MeetingMatchingEntity.builder()
                    .maleSurvey(first)
                    .femaleSurvey(second)
                    .matchedAt(LocalDateTime.now())
                    .build();
        }
        else {
            return MeetingMatchingEntity.builder()
                    .maleSurvey(second)
                    .femaleSurvey(first)
                    .matchedAt(LocalDateTime.now())
                    .build();
        }
    }

    public DatingMatchingEntity toDatingMatchingEntity() {
        DatingSurveyEntity first = (DatingSurveyEntity) this.first;
        DatingSurveyEntity second = (DatingSurveyEntity) this.second;
        if (first.getGender() == Gender.MALE) {
            return DatingMatchingEntity.builder()
                    .maleSurvey(first)
                    .femaleSurvey(second)
                    .matchedAt(LocalDateTime.now())
                    .build();
        }
        else {
            return DatingMatchingEntity.builder()
                    .maleSurvey(second)
                    .femaleSurvey(first)
                    .matchedAt(LocalDateTime.now())
                    .build();
        }
    }
}
