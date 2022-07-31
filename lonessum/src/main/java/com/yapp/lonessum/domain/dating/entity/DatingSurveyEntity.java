package com.yapp.lonessum.domain.dating.entity;

import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.dating.dto.DatingPartnerSurveyDto;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dating_survey")
public class DatingSurveyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "datingSurvey")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    private DatingMatchingEntity datingMatching;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private Long myUniversity;
    
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

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    private Boolean isRandom;

    public void changeMyUniversity(Long myUniversity) {
        this.myUniversity = myUniversity;
    }

    public void changeMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void changeDatingMatching(DatingMatchingEntity datingMatching) {
        this.datingMatching = datingMatching;
    }

    public DatingPartnerSurveyDto toPartnerSurveyDto() {
        return DatingPartnerSurveyDto.builder()
                .age(this.age)
                .height(this.myHeight)
                .body(this.myBody)
                .department(this.myDepartment)
                .dateCount(this.myDateCount)
                .characteristic(this.characteristic)
                .isSmoke(this.mySmoke)
                .kakaoId(this.kakaoId)
                .build();
    }
}
