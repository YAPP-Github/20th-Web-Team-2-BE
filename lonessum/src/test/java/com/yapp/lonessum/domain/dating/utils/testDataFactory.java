package com.yapp.lonessum.domain.dating.utils;


import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.dating.dto.DatingSurveyDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testDataFactory {
    public static List<DatingSurveyDto> getTestData() {
        List<DatingSurveyDto> list = new ArrayList<>();

        DatingSurveyDto dto = new DatingSurveyDto();
        dto.setId(1L);
        dto.setGender(Gender.MALE);
        dto.setAge(20);
        dto.setMyUniversity(1L);
        dto.setMyDepartment(Department.ART);
        dto.setMyHeight(170);
        dto.setAvoidUniversities(Arrays.asList(4L, 5L));
        dto.setPreferUniversities(Arrays.asList(7L, 9L));
        dto.setPreferAge(Arrays.asList(20L, 30L));
        dto.setPreferHeight(Arrays.asList(20L, 30L));
        dto.setPreferDepartments(Arrays.asList(Department.ATHLETIC));
        dto.setMindSet(Mindset.FRIEND);
        dto.setPlay(Play.GAME);
        dto.setIsAbroad(Boolean.TRUE);
        dto.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto.setAbroadAreas(Arrays.asList(3L));
        dto.setAgreement(Boolean.TRUE);
        dto.setIsSmokeOk(Boolean.TRUE);
        dto.setMySmoke(Boolean.TRUE);
        dto.setCharacteristic(Characteristic.VERY_ACTIVE);
        dto.setPreferCharacteristics(Arrays.asList(Characteristic.VERY_ACTIVE, Characteristic.A_LITTLE_ACTIVE));
        dto.setMyDateCount(DateCount.ONETWO);
        dto.setPreferDateCount(DateCount.ONETWO);
        dto.setMyBody(Body.CHUBBY);
        dto.setPreferBodies(Arrays.asList(Body.CHUBBY, Body.SKINNY));

        DatingSurveyDto dto2 = new DatingSurveyDto();
        dto2.setId(2L);
        dto2.setGender(Gender.FEMALE);
        dto2.setAge(20);
        dto2.setMyUniversity(15L);
        dto2.setMyDepartment(Department.ART);
        dto2.setMyHeight(170);
        dto2.setAvoidUniversities(Arrays.asList(1L, 5L));
        dto2.setPreferUniversities(Arrays.asList(7L, 9L));
        dto2.setPreferAge(Arrays.asList(20L, 30L));
        dto2.setPreferHeight(Arrays.asList(20L, 30L));
        dto2.setPreferDepartments(Arrays.asList(Department.ATHLETIC));
        dto2.setMindSet(Mindset.FRIEND);
        dto2.setPlay(Play.GAME);
        dto2.setIsAbroad(Boolean.TRUE);
        dto2.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto2.setAbroadAreas(Arrays.asList(3L));
        dto2.setAgreement(Boolean.TRUE);
        dto2.setIsSmokeOk(Boolean.TRUE);
        dto2.setMySmoke(Boolean.TRUE);
        dto2.setCharacteristic(Characteristic.VERY_ACTIVE);
        dto2.setPreferCharacteristics(Arrays.asList(Characteristic.VERY_ACTIVE, Characteristic.A_LITTLE_ACTIVE));
        dto2.setMyDateCount(DateCount.FIVE);
        dto2.setPreferDateCount(DateCount.FIVE);
        dto2.setMyBody(Body.CHUBBY);
        dto2.setPreferBodies(Arrays.asList(Body.SKINNY));

        DatingSurveyDto dto3 = new DatingSurveyDto();
        dto3.setId(3L);
        dto3.setGender(Gender.FEMALE);
        dto3.setAge(20);
        dto3.setMyUniversity(20L);
        dto3.setMyDepartment(Department.ART);
        dto3.setMyHeight(170);
        dto3.setAvoidUniversities(Arrays.asList(2L, 5L));
        dto3.setPreferUniversities(Arrays.asList(15L, 9L));
        dto3.setPreferAge(Arrays.asList(20L, 30L));
        dto3.setPreferHeight(Arrays.asList(20L, 30L));
        dto3.setPreferDepartments(Arrays.asList(Department.ATHLETIC));
        dto3.setMindSet(Mindset.FRIEND);
        dto3.setPlay(Play.GAME);
        dto3.setIsAbroad(Boolean.TRUE);
        dto3.setDomesticAreas(Arrays.asList(DomesticArea.GN));
        dto3.setAbroadAreas(Arrays.asList(3L));
        dto3.setAgreement(Boolean.TRUE);
        dto3.setIsSmokeOk(Boolean.FALSE);
        dto3.setMySmoke(Boolean.FALSE);
        dto3.setCharacteristic(Characteristic.VERY_QUIET);
        dto3.setPreferCharacteristics(Arrays.asList(Characteristic.A_LITTLE_QUIET, Characteristic.VERY_QUIET));
        dto3.setMyDateCount(DateCount.ONETWO);
        dto3.setPreferDateCount(DateCount.ONETWO);
        dto3.setMyBody(Body.CHUBBY);
        dto3.setPreferBodies(Arrays.asList(Body.CHUBBY, Body.SKINNY));

        list.add(dto);
        list.add(dto2);
        list.add(dto3);

        return list;
    }
}
