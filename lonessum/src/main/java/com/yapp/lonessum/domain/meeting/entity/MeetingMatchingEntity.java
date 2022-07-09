package com.yapp.lonessum.domain.meeting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meeting_matching")
public class MeetingMatchingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity maleSurvey;

    @OneToOne(fetch = FetchType.LAZY)
    private MeetingSurveyEntity femaleSurvey;

    private LocalDateTime matchedAt;
}
