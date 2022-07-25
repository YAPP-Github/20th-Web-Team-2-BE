package com.yapp.lonessum.domain.dating.dto;

import com.yapp.lonessum.common.dto.SurveyDto;
import com.yapp.lonessum.domain.constant.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DatingSurveyDto extends SurveyDto {
    private Integer age;

    private Long myUniversity;

    private Department myDepartment;

    private Characteristic characteristic;

    private String mbti;

    private Integer myHeight;

    private Body myBody;

    private Boolean mySmoke;

    private DateCount myDateCount;

    private Boolean isSmokeOk;

    private List<Long> avoidUniversities;

    private List<Long> preferUniversities;

    private List<Integer> preferAge;

    private List<Integer> preferHeight;

    private List<Department> preferDepartments;

    private List<Characteristic> preferCharacteristics;

    private List<Body> preferBodies;

    private DateCount preferDateCount;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
