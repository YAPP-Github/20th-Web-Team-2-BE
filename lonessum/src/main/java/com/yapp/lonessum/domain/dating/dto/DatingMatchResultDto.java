package com.yapp.lonessum.domain.dating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatingMatchResultDto {
    private int code;
    private String message;
    private DatingPartnerSurveyDto partnerSurvey;
}
