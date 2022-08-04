package com.yapp.lonessum.domain.dating.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDatingSurveyDto extends DatingSurveyDto {
    private List<String> stringAbroadAreas;

    public MyDatingSurveyDto (DatingSurveyDto datingSurveyDto, List<String> stringAbroadAreas) {
        this.stringAbroadAreas = stringAbroadAreas;
        this.setGender(datingSurveyDto.getGender());
        this.setIsAbroad(datingSurveyDto.getIsAbroad());
        this.setDomesticAreas(datingSurveyDto.getDomesticAreas());
        this.setAbroadAreas(datingSurveyDto.getAbroadAreas());
        this.setAge(datingSurveyDto.getAge());
        this.setMyUniversity(datingSurveyDto.getMyUniversity());
        this.setMyDepartment(datingSurveyDto.getMyDepartment());
        this.setCharacteristic(datingSurveyDto.getCharacteristic());
        this.setMbti(datingSurveyDto.getMbti());
        this.setMyHeight(datingSurveyDto.getMyHeight());
        this.setMyBody(datingSurveyDto.getMyBody());
        this.setMySmoke(datingSurveyDto.getMySmoke());
        this.setMyDateCount(datingSurveyDto.getMyDateCount());
        this.setIsSmokeOk(datingSurveyDto.getIsSmokeOk());
        this.setAvoidUniversities(datingSurveyDto.getAvoidUniversities());
        this.setPreferUniversities(datingSurveyDto.getPreferUniversities());
        this.setPreferAge(datingSurveyDto.getPreferAge());
        this.setPreferHeight(datingSurveyDto.getPreferHeight());
        this.setPreferDepartments(datingSurveyDto.getPreferDepartments());
        this.setPreferCharacteristics(datingSurveyDto.getPreferCharacteristics());
        this.setPreferBodies(datingSurveyDto.getPreferBodies());
        this.setPreferDateCount(datingSurveyDto.getPreferDateCount());
        this.setChannel(datingSurveyDto.getChannel());
        this.setAgreement(datingSurveyDto.getAgreement());
        this.setKakaoId(datingSurveyDto.getKakaoId());
    }
}
