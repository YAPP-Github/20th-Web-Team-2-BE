package com.yapp.lonessum.domain.meeting.dto;

import com.yapp.lonessum.domain.constant.Mindset;
import com.yapp.lonessum.domain.constant.Play;
import com.yapp.lonessum.domain.constant.TypeOfMeeting;
import com.yapp.lonessum.domain.meeting.entity.constant.*;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateSurveyReq {
    private TypeOfMeeting typeOfMeeting;

    private Gender gender;

    private Long averageAge;

    private List<Long> ourUniversities;

    private List<Department> ourDepartments;

    private Long averageHeight;

    private List<Long> avoidUniversities;

    private List<Long> preferUniversities;

    private List<Long> preferAge;

    private List<Long> preferHeight;

    private List<Department> preferDepartments;

    private Mindset mindSet;

    private Play play;

    private Boolean isAbroad;

    private List<DomesticArea> domesticAreas;

    private List<Long> abroadAreas;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
