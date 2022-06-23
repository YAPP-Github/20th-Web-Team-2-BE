package com.yapp.lonessum.domain.meeting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting_matching")
public class MeetingMatchingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity surveyA;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity surveyB;

    private Boolean isPaidA;

    private Boolean isPaidB;

    private LocalDateTime matchedAt;
}
