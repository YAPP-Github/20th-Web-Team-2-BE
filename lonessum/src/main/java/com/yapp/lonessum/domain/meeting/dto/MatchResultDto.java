package com.yapp.lonessum.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchResultDto {
    private int code;
    private String message;
    private PartnerSurveyDto partnerSurvey;
}
