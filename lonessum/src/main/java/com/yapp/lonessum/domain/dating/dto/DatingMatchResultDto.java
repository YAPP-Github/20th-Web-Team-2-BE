package com.yapp.lonessum.domain.dating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DatingMatchResultDto {
    private int code;
    private String message;
    private DatingPartnerSurveyDto partnerSurvey;
    private LocalDateTime payDeadLine;
}
