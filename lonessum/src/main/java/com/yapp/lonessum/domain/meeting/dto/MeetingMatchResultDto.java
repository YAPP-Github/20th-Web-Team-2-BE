package com.yapp.lonessum.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MeetingMatchResultDto {
    private int code;
    private String message;
    private MeetingPartnerSurveyDto partnerSurvey;
    private LocalDateTime payDeadLine;
    private String payName;
}
