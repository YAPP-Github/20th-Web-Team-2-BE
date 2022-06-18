package com.yapp.lonessum.domain.dating.dto;

import com.yapp.lonessum.domain.constant.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.Character;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatingSurveyDto {
    private Long id;

    private Gender gender;

    private Integer age;

    private Long myUniversity;

    private Department myDepartment;

    private Character character;

    private String mbti;

    private Integer myHeight;

    private Body myBody;

    private Boolean mySmoke;

    private DateCount myDateCount;

    private Boolean isSmokeOk;

    private List<Long> avoidUniversities;

    private List<Long> preferUniversities;

    private List<Long> preferAge;

    private List<Long> preferHeight;

    private List<Department> preferDepartments;

    private List<Character> preferCharacters;

    private List<Body> preferBodies;

    private DateCount preferDateCount;

    private Boolean isAbroad;

    private List<DomesticArea> domesticAreas;

    private List<Long> abroadAreas;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;

    private Mindset mindSet;

    private Play play;
}
