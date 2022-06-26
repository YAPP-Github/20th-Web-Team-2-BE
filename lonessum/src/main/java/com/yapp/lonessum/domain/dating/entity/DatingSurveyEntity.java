package com.yapp.lonessum.domain.dating.entity;

import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dating_survey")
public class DatingSurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToMany(mappedBy = "surveyA")
    private List<DatingMatchingEntity> datingMatchingEntityListA = new ArrayList<>();

    @OneToMany(mappedBy = "surveyB")
    private List<DatingMatchingEntity> datingMatchingEntityList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    // 내 학교 정보 알아야함
    
    @Enumerated(EnumType.STRING)
    private Department myDepartment;

    @Enumerated(EnumType.STRING)
    private Characteristic characteristic;

    private String mbti;

    private Integer myHeight;

    @Enumerated(EnumType.STRING)
    private Body myBody;

    private Boolean mySmoke;

    @Enumerated(EnumType.STRING)
    private DateCount myDateCount;

    private Boolean isSmokeOk;

    @ElementCollection
    @CollectionTable(name = "dating_avoid_universities", joinColumns = @JoinColumn(name = "dating_survey_id"))
    private List<Long> avoidUniversities;

    @ElementCollection
    @CollectionTable(name = "dating_prefer_universities", joinColumns = @JoinColumn(name = "dating_survey_id"))
    private List<Long> preferUniversities;

    @ElementCollection
    @CollectionTable(name = "dating_prefer_age", joinColumns = @JoinColumn(name = "dating_survey_id"))
    private List<Integer> preferAge;

    @ElementCollection
    @CollectionTable(name = "dating_prefer_height", joinColumns = @JoinColumn(name = "dating_survey_id"))
    private List<Integer> preferHeight;

    @ElementCollection
    @CollectionTable(name = "dating_departments", joinColumns = @JoinColumn(name = "dating_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<Department> preferDepartments;

    @ElementCollection
    @CollectionTable(name = "dating_prefer_characteristics", joinColumns = @JoinColumn(name = "dating_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<Characteristic> preferCharacteristics;

    @ElementCollection
    @CollectionTable(name = "dating_prefer_bodies", joinColumns = @JoinColumn(name = "dating_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<Body> preferBodies;

    @Enumerated(EnumType.STRING)
    private DateCount preferDateCount;

    private Boolean isAbroad;

    @ElementCollection
    @CollectionTable(name = "dating_domestic_areas", joinColumns = @JoinColumn(name = "dating_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<DomesticArea> domesticAreas;

    @ElementCollection
    @CollectionTable(name = "dating_abroad_areas", joinColumns = @JoinColumn(name = "dating_survey_id"))
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
