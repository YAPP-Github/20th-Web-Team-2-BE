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

    private TypeOfMeeting typeOfMeeting;

    private Gender gender;

    private Long averageAge;

    @ElementCollection
    private Set<Long> ourUniversities;

    @ElementCollection
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
    private Set<Department> preferDepartments;

    private Mindset mindSet;

    private Play play;

    private Boolean isAbroad;

    @ElementCollection
    private Set<DomesticArea> domesticAreas;

    @ElementCollection
    private Set<Long> abroadAreas;

    private Channel channel;

    private Boolean agreement;

    private String kakaoId;
}
