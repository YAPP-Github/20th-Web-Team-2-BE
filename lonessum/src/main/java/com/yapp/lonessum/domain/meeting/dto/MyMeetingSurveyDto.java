package com.yapp.lonessum.domain.meeting.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyMeetingSurveyDto extends MeetingSurveyDto {
    private List<String> stringAbroadAreas;

    public MyMeetingSurveyDto (MeetingSurveyDto meetingSurveyDto, List<String> stringAbroadAreas) {
        this.stringAbroadAreas = stringAbroadAreas;
        this.setGender(meetingSurveyDto.getGender());
        this.setIsAbroad(meetingSurveyDto.getIsAbroad());
        this.setDomesticAreas(meetingSurveyDto.getDomesticAreas());
        this.setAbroadAreas(meetingSurveyDto.getAbroadAreas());
        this.setTypeOfMeeting(meetingSurveyDto.getTypeOfMeeting());
        this.setAverageAge(meetingSurveyDto.getAverageAge());
        this.setOurUniversities(meetingSurveyDto.getOurUniversities());
        this.setOurDepartments(meetingSurveyDto.getOurDepartments());
        this.setAverageHeight(meetingSurveyDto.getAverageHeight());
        this.setAvoidUniversities(meetingSurveyDto.getAvoidUniversities());
        this.setPreferUniversities(meetingSurveyDto.getPreferUniversities());
        this.setPreferAge(meetingSurveyDto.getPreferAge());
        this.setPreferHeight(meetingSurveyDto.getPreferHeight());
        this.setPreferDepartments(meetingSurveyDto.getPreferDepartments());
        this.setMindSet(meetingSurveyDto.getMindSet());
        this.setPlay(meetingSurveyDto.getPlay());
        this.setChannel(meetingSurveyDto.getChannel());
        this.setAgreement(meetingSurveyDto.getAgreement());
        this.setKakaoId(meetingSurveyDto.getKakaoId());
    }
}
