package com.yapp.lonessum.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestMeetingMatchingResultDto {
    private Long matchId;
    private Long maleSurveyId;
    private Long femaleSurveyId;
}
