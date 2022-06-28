package com.yapp.lonessum.domain.meeting.entity;

import com.yapp.lonessum.domain.constant.*;
import com.yapp.lonessum.domain.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting_survey")
public class MeetingSurveyEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private TypeOfMeeting typeOfMeeting;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer averageAge;

    @ElementCollection
    @CollectionTable(name = "meeting_our_universities", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Long> ourUniversities;

    @ElementCollection
    @CollectionTable(name = "meeting_our_departments", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Department> ourDepartments;

    private Integer averageHeight;

    @ElementCollection
    @CollectionTable(name = "meeting_avoid_universities", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Long> avoidUniversities;

    @ElementCollection
    @CollectionTable(name = "meeting_prefer_universities", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Long> preferUniversities;

    @ElementCollection
    @CollectionTable(name = "meeting_prefer_age", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Integer> preferAge;

    @ElementCollection
    @CollectionTable(name = "meeting_prefer_height", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Integer> preferHeight;

    @ElementCollection
    @CollectionTable(name = "meeting_prefer_departments", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<Department> preferDepartments;

    @Enumerated(EnumType.STRING)
    private Mindset mindSet;

    @Enumerated(EnumType.STRING)
    private Play play;

    private Boolean isAbroad;

    @ElementCollection
    @CollectionTable(name = "meeting_doemstic_areas", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    @Enumerated(EnumType.STRING)
    private List<DomesticArea> domesticAreas;

    @ElementCollection
    @CollectionTable(name = "meeting_aborad_areas", joinColumns = @JoinColumn(name = "meeting_survey_id"))
    private List<Long> abroadAreas;

    @Enumerated(EnumType.STRING)
    private Channel channel;

    private Boolean agreement;

    private String kakaoId;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    private Boolean isRandom;

    public void changeUser(UserEntity user) {
        this.user = user;
    }

    public void changeMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }
}
