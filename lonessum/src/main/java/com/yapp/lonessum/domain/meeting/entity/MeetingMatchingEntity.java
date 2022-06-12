package com.yapp.lonessum.domain.meeting.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_matching")
public class MeetingMatchingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity surveyA;

    @ManyToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity surveyB;

    private Boolean isPaidA;

    private Boolean isPaidB;

    private LocalDateTime matchedAt;
}
