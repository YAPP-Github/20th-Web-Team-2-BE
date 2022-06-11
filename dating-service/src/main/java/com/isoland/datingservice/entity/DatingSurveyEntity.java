package com.isoland.datingservice.entity;

import com.isoland.datingservice.entity.constant.*;
import com.isoland.datingservice.entity.constant.Character;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatingSurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long age;

    // 내 학교 정보 알아야함
    
    @Enumerated(EnumType.STRING)
    private Department myDepartment;

    private Character character;

    private String mbti;

    private Long myHeight;

    private Body mybody;

    private Boolean mySmoke;

    private DateCount myDateCount;

    private Boolean isSmokeOk;

    @ElementCollection
    private List<Long> avoidUniversities;

    @ElementCollection
    private List<Long> preferUniversities;

    @ElementCollection
    private List<Long> preferAge;

    @ElementCollection
    private List<Long> preferHeight;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Department> preferDepartments;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Character> preferCharacters;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Body> preferBodies;

    @Enumerated(EnumType.STRING)
    private DateCount preferDateCount;

    private Boolean isAbroad;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DomesticArea> domesticAreas;

    @ElementCollection
    private List<Long> abroadAreas;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private Boolean agreement;

    private String kakaoId;

    private Boolean isMatched;

    private Boolean isPaid;

    private Boolean isRandom;

    private LocalDateTime createdAt;
}
