package com.isoland.meetingservice.dto;

import com.isoland.meetingservice.entity.constant.*;
import lombok.Getter;

import java.util.Set;

@Getter
public class SurveyRequest {
    private TypeOfMeeting typeOfMeeting;

    private Gender gender;

    private Long averageAge;

    private Set<Long> ourUniversities;

    private Set<Department> ourDepartments;

    private Long averageHeight;

    private Set<Long> avoidUniversities;

    private Set<Long> preferUniversities;

    private Set<Long> preferAge;

    private Set<Long> preferHeight;

    private Set<Department> preferDepartments;

    private Mindset mindSet;

    private Play play;

    private Boolean isAbroad;

    private Set<DomesticArea> domesticAreas;

    private Set<Long> abroadAreas;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
