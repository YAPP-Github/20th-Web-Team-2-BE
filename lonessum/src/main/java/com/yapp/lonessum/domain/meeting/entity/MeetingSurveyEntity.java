package com.yapp.lonessum.domain.meeting.entity;

import com.yapp.lonessum.domain.constant.*;
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
@Table(name = "meeting_survey")
public class MeetingSurveyEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private TypeOfMeeting typeOfMeeting;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long averageAge;

    @ElementCollection
    private List<Long> ourUniversities;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Department> ourDepartments;

    private Long averageHeight;

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

    @Enumerated(EnumType.STRING)
    private Mindset mindSet;

    @Enumerated(EnumType.STRING)
    private Play play;

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
