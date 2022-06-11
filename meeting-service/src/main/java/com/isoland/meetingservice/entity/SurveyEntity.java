package com.isoland.meetingservice.entity;

import com.isoland.meetingservice.entity.constant.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeOfMeeting typeOfMeeting;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long averageAge;

    @ElementCollection
    private Set<Long> ourUniversities;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Department> ourDepartments;

    private Long averageHeight;

    @ElementCollection
    private Set<Long> avoidUniversities;

    @ElementCollection
    private Set<Long> preferUniversities;

    @ElementCollection
    private Set<Long> preferAge;

    @ElementCollection
    private Set<Long> preferHeight;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Department> preferDepartments;

    @Enumerated(EnumType.STRING)
    private Mindset mindSet;

    @Enumerated(EnumType.STRING)
    private Play play;

    private Boolean isAbroad;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DomesticArea> domesticAreas;

    @ElementCollection
    private Set<Long> abroadAreas;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
