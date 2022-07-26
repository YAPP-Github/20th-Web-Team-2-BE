package com.yapp.lonessum.domain.meeting.dto;

import com.yapp.lonessum.common.dto.SurveyDto;
import com.yapp.lonessum.domain.constant.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MeetingSurveyDto extends SurveyDto {

    private TypeOfMeeting typeOfMeeting;

    private Integer averageAge;

    private List<Long> ourUniversities;

    private List<Department> ourDepartments;

    private Integer averageHeight;

    private List<Long> avoidUniversities;

    private List<Long> preferUniversities;

    private List<Integer> preferAge;

    private List<Integer> preferHeight;

    private List<Department> preferDepartments;

    private Mindset mindSet;

    private Play play;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
