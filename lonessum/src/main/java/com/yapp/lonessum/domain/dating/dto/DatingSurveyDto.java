package com.yapp.lonessum.domain.dating.dto;

import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DatingSurveyDto {
    private Long id;

    private UserEntity user;

    private Gender gender;

    private Long age;

    private Department myDepartment;

    private Characteristic characteristic;

    private String mbti;

    private Long myHeight;

    private Body myBody;

    private Boolean mySmoke;

    private DateCount myDateCount;

    private Boolean isSmokeOk;

    private List<Long> avoidUniversities;

    private List<Long> preferUniversities;

    private List<Long> preferAge;

    private List<Long> preferHeight;

    private List<Department> preferDepartments;

    private List<Characteristic> preferCharacteristics;

    private List<Body> preferBodies;

    private DateCount preferDateCount;

    private Boolean isAbroad;

    private List<DomesticArea> domesticAreas;

    private List<Long> abroadAreas;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
