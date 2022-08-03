package com.yapp.lonessum.domain.meeting.utils;


import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.meeting.dto.MeetingSurveyDto;
import com.yapp.lonessum.domain.meeting.entity.MeetingSurveyEntity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testDataFactory {
    public static List<MeetingSurveyDto> getTestData() {
        List<MeetingSurveyDto> list = new ArrayList<>();

        MeetingSurveyDto dto = new MeetingSurveyDto();
        dto.setId(1L);
        dto.setTypeOfMeeting(TypeOfMeeting.TWO);
        dto.setGender(Gender.MALE);
        dto.setAverageAge(15);
        dto.setOurUniversities(Arrays.asList(1L, 3L));
        dto.setOurDepartments(Arrays.asList(Department.ATHLETIC));
        dto.setAverageHeight(150);
        dto.setAvoidUniversities(Arrays.asList(4L, 5L));
        dto.setPreferUniversities(Arrays.asList(7L, 9L));
        dto.setPreferAge(Arrays.asList(20, 30));
        dto.setPreferHeight(Arrays.asList(20, 30));
        dto.setPreferDepartments(Arrays.asList(Department.ATHLETIC));
        dto.setMindSet(Mindset.FRIEND);
        dto.setPlay(Play.GAME);
        dto.setIsAbroad(Boolean.TRUE);
        dto.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto.setAbroadAreas(Arrays.asList(3L));
        dto.setAgreement(Boolean.TRUE);

        MeetingSurveyDto dto2 = new MeetingSurveyDto();
        dto2.setId(2L);
        dto2.setTypeOfMeeting(TypeOfMeeting.TWO);
        dto2.setGender(Gender.FEMALE);
        dto2.setAverageAge(20);
        dto2.setOurUniversities(Arrays.asList(4L, 3L));
        dto2.setOurDepartments(Arrays.asList(Department.ATHLETIC));
        dto2.setAverageHeight(150);
        dto2.setAvoidUniversities(Arrays.asList(4L, 5L));
        dto2.setPreferUniversities(Arrays.asList(1L, 3L));
        dto2.setPreferAge(Arrays.asList(22, 30));
        dto2.setPreferHeight(Arrays.asList(24, 30));
        dto2.setPreferDepartments(Arrays.asList(Department.ATHLETIC, Department.ART));
        dto2.setMindSet(Mindset.FRIEND);
        dto2.setPlay(Play.GAME);
        dto2.setIsAbroad(Boolean.TRUE);
        dto2.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto2.setAbroadAreas(Arrays.asList(3L));
        dto2.setAgreement(Boolean.TRUE);

        MeetingSurveyDto dto3 = new MeetingSurveyDto();
        dto3.setId(3L);
        dto3.setTypeOfMeeting(TypeOfMeeting.TWO);
        dto3.setGender(Gender.FEMALE);
        dto3.setAverageAge(10);
        dto3.setOurUniversities(Arrays.asList(4L, 3L));
        dto3.setOurDepartments(Arrays.asList(Department.LIBERAL));
        dto3.setAverageHeight(150);
        dto3.setAvoidUniversities(Arrays.asList(222L, 2223L));
        dto3.setPreferUniversities(Arrays.asList(1L, 3L));
        dto3.setPreferAge(Arrays.asList(22, 30));
        dto3.setPreferHeight(Arrays.asList(24, 30));
        dto3.setPreferDepartments(Arrays.asList(Department.LIBERAL));
        dto3.setMindSet(Mindset.FRIEND);
        dto3.setPlay(Play.GAME);
        dto3.setIsAbroad(Boolean.TRUE);
        dto3.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto3.setAbroadAreas(Arrays.asList(3L));
        dto3.setAgreement(Boolean.TRUE);

        list.add(dto);
        list.add(dto2);
        list.add(dto3);

        return list;
    }

    public static List<MeetingSurveyEntity> getTestDataEntity() {
        List<MeetingSurveyEntity> list = new ArrayList<>();

        MeetingSurveyEntity survey = new MeetingSurveyEntity();
//        survey.setId(1L);
        survey.setTypeOfMeeting(TypeOfMeeting.TWO);
        survey.setGender(Gender.MALE);
        survey.setAverageAge(15);
        survey.setOurUniversities(Arrays.asList(1L, 3L));
        survey.setOurDepartments(Arrays.asList(Department.ATHLETIC));
        survey.setAverageHeight(150);
        survey.setAvoidUniversities(Arrays.asList(4L, 5L));
        survey.setPreferUniversities(Arrays.asList(7L, 9L));
        survey.setPreferAge(Arrays.asList(20, 30));
        survey.setPreferHeight(Arrays.asList(20, 30));
        survey.setPreferDepartments(Arrays.asList(Department.ATHLETIC));
        survey.setMindSet(Mindset.FRIEND);
        survey.setPlay(Play.GAME);
        survey.setIsAbroad(Boolean.TRUE);
        survey.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        survey.setAbroadAreas(Arrays.asList(3L));
        survey.setAgreement(Boolean.TRUE);
        survey.setMatchStatus(MatchStatus.WAITING);

        MeetingSurveyEntity survey2 = new MeetingSurveyEntity();
//        survey2.setId(2L);
        survey2.setTypeOfMeeting(TypeOfMeeting.TWO);
        survey2.setGender(Gender.FEMALE);
        survey2.setAverageAge(20);
        survey2.setOurUniversities(Arrays.asList(4L, 3L));
        survey2.setOurDepartments(Arrays.asList(Department.ATHLETIC));
        survey2.setAverageHeight(150);
        survey2.setAvoidUniversities(Arrays.asList(4L, 5L));
        survey2.setPreferUniversities(Arrays.asList(1L, 3L));
        survey2.setPreferAge(Arrays.asList(22, 30));
        survey2.setPreferHeight(Arrays.asList(24, 30));
        survey2.setPreferDepartments(Arrays.asList(Department.ATHLETIC, Department.ART));
        survey2.setMindSet(Mindset.FRIEND);
        survey2.setPlay(Play.GAME);
        survey2.setIsAbroad(Boolean.TRUE);
        survey2.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        survey2.setAbroadAreas(Arrays.asList(3L));
        survey2.setAgreement(Boolean.TRUE);
        survey2.setMatchStatus(MatchStatus.WAITING);

        MeetingSurveyEntity survey3 = new MeetingSurveyEntity();
//        survey3.setId(3L);
        survey3.setTypeOfMeeting(TypeOfMeeting.TWO);
        survey3.setGender(Gender.FEMALE);
        survey3.setAverageAge(10);
        survey3.setOurUniversities(Arrays.asList(4L, 3L));
        survey3.setOurDepartments(Arrays.asList(Department.LIBERAL));
        survey3.setAverageHeight(150);
        survey3.setAvoidUniversities(Arrays.asList(222L, 2223L));
        survey3.setPreferUniversities(Arrays.asList(1L, 3L));
        survey3.setPreferAge(Arrays.asList(22, 30));
        survey3.setPreferHeight(Arrays.asList(24, 30));
        survey3.setPreferDepartments(Arrays.asList(Department.LIBERAL));
        survey3.setMindSet(Mindset.FRIEND);
        survey3.setPlay(Play.GAME);
        survey3.setIsAbroad(Boolean.TRUE);
        survey3.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        survey3.setAbroadAreas(Arrays.asList(3L));
        survey3.setAgreement(Boolean.TRUE);
        survey3.setMatchStatus(MatchStatus.WAITING);

        list.add(survey);
        list.add(survey2);
        list.add(survey3);

        return list;
    }
}
