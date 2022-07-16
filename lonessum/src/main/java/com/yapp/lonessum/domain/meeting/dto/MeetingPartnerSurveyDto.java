package com.yapp.lonessum.domain.meeting.dto;

import com.yapp.lonessum.domain.constant.Department;
import com.yapp.lonessum.domain.constant.Mindset;
import com.yapp.lonessum.domain.constant.Play;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MeetingPartnerSurveyDto {
    private int averageAge;
    private int averageHeight;
    private List<String> universities;
    private List<Department> departments;
    private List<String> areas;
    private Mindset mindset;
    private Play play;
    private String kakaoId;
}
