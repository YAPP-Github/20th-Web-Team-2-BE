package com.yapp.lonessum.domain.meeting.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
public class MyMeetingSurveyDto {
    private MeetingSurveyDto meetingSurveyDto;
    private List<String> stringAbroadAreas;
}
