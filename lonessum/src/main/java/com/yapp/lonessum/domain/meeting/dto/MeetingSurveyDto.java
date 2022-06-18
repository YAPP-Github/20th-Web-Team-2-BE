package com.yapp.lonessum.domain.meeting.dto;

import com.yapp.lonessum.domain.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingSurveyDto {

    private Long id;

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
